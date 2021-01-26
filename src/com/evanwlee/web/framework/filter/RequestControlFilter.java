/**
 * $Id: RequestControlFilter.java,v 1.1 2010/03/29 23:50:17 evanl Exp $
 *
 * Based on http://www.onjava.com/pub/a/onjava/2004/03/24/loadcontrol.html
 *
 * (c) 2004, Kevin Chipalowsky (kevin@farwestsoftware.com) and Ivelin Ivanov
 * (ivelin@apache.org)
 *
 * Released under terms of the Artistic License
 * http://www.opensource.org/licenses/artistic-license.php
 */
package com.evanwlee.web.framework.filter;


    import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;




    /**
     * Use this filter to synchronize requests to your web application and
     * reduce the maximum load that each individual user can put on your web
     * application. Requests will be synchronized per session. When more than
     * one additional requests are made while a request is in process, only the
     * most recent of the additional requests will actually be processed.
     * <p>
     * If a user makes two requests, A and B, then A will be processed first
     * while B waits. When A finishes, B will be processed.
     * <p>
     * If a user makes three or more requests (e.g. A, B, and C), then the first
     * will be processed (A), and then after it finishes the last will be
     * processed (C), and any intermediate requests will be skipped (B).
     * <p>
     * There are two additional limitiations:
     * <ul>
     * <li>Requests will be excluded from filtering if their URI matches one of
     * the exclusion patterns. There will be no synchronization performed if a
     * request matches one of those patterns.</li>
     * <li>Requests wait a maximum of 5 seconds, which can be overridden per
     * URI pattern in the filter's configuration.</li>
     * </ul>
     *
     * @author Kevin Chipalowsky and Ivelin Ivanov
     *
     *
     * Changed to use a Doug Lea's Concurrent FIFOSemaphore to synch requests,
     * and buffer multiple requests.
     *
     * @author Michael Reardon
     */
    public class RequestControlFilter implements Filter {


        /**
         * The session attribute key for the request currently waiting in the
         * queue
         */
        private static final String REQUEST_QUEUE = "RequestControlFilter.requestQueue";

        /** The session attribute key for the synchronization object */
        private static final String SYNC_OBJECT_KEY = "RequestControlFilter.sessionSync";

        /** A list of Pattern objects that match paths to exclude */
        private LinkedList<Pattern> excludePatterns;


        /**
         * Initialize this filter by reading its configuration parameters
         *
         * @param config
         *            Configuration from web.xml file
         */
        @SuppressWarnings("unchecked")
        public void init(FilterConfig config) throws ServletException  {
            // parse all of the initialization parameters, collecting the
            // exclude patterns and the max wait parameters
            excludePatterns = new LinkedList<Pattern>();
            Enumeration e = config.getInitParameterNames();
            while (e.hasMoreElements())  {
                String paramName = (String) e.nextElement();
                String paramValue = config.getInitParameter(paramName);

                if (paramName.startsWith("excludePattern")) {
                    // compile the pattern only this once
                    Pattern excludePattern = Pattern.compile(paramValue);
                    excludePatterns.add(excludePattern);
                }
            }
        }

        /**
         * Called with the filter is no longer needed.
         */
        public void destroy() {
            // there is nothing to do
        }

        /**
         * Synchronize the request and then either process it or skip it,
         * depending on what other requests current exist for this session. See
         * the description of this class for more details.
         */
        public void doFilter(ServletRequest request, ServletResponse response,
                FilterChain chain) throws IOException, ServletException  {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession session = httpRequest.getSession();

            // if this request is excluded from the filter, then just process it
            if (!isFilteredRequest(httpRequest)) {

                chain.doFilter(request, response);
                return;
            }

            Semaphore semaphore = getSynchronizationObject(session);
            try {
                // if another request is being processed, then wait
                if (semaphore.tryAcquire(0, TimeUnit.SECONDS) == false) {
                    // Put this request in the queue and wait
                    enqueueRequest(httpRequest);

                    if(waitForPermit(httpRequest) == false) {

                        return; // ignore this request
                    }
                }
                chain.doFilter(request, response);
            } catch (InterruptedException e) {
                return;
            } finally {
                semaphore.release();
            }
        }

        /**
         * Get a synchronization object for this session
         *
         * @param session
         */
        private static synchronized Semaphore getSynchronizationObject(HttpSession session) {
            // get the object from the session. If it does not yet exist, then
            // create one.
            FIFOSemaphoreFactory syncObjFactory = (FIFOSemaphoreFactory) session.getAttribute(SYNC_OBJECT_KEY);

            if (syncObjFactory == null) {
                syncObjFactory = new FIFOSemaphoreFactory();
                session.setAttribute(SYNC_OBJECT_KEY, syncObjFactory);
            }
            return syncObjFactory.getSemaphore();
        }

        /**
         * Wait for this server to finish with its current request so that it
         * can begin processing our next request. This method also detects if
         * its request is replaced by another request in the queue.
         *
         * @param request
         *            Wait for this request to be ready to run
         * @return true if this request may be processed, or false if this
         *         request should not be processed
         * @throws InterruptedException
         */
        private boolean waitForPermit(HttpServletRequest request) throws InterruptedException{
            HttpSession session = request.getSession();

            Semaphore semaphore = getSynchronizationObject(session);
            semaphore.acquire();

            HttpServletRequest bufferedRequest = (HttpServletRequest)session.getAttribute(REQUEST_QUEUE);
            if (bufferedRequest == request){

                return true;
            }

            // we were kicked out of the request buffer
            return false;
        }

        /**
         * Put a new request in the queue. This new request will replace any
         * other requests that were waiting.
         *
         * @param request
         *            The request to queue
         */
        private void enqueueRequest(HttpServletRequest request) {
            HttpSession session = request.getSession();

            // Put this request in the queue, replacing whoever was there before
            session.setAttribute(REQUEST_QUEUE, request);


        }

        /**
         * Look through the filter's configuration, and determine whether or not
         * it should synchronize this request with others.
         *
         * @param httpRequest
         * @return
         */
        private boolean isFilteredRequest(HttpServletRequest request) {
            // iterate through the exclude patterns. If one matches this path,
            // then the request is excluded.
            // String path = request.getRequestURI();
            String path = request.getQueryString();
            if (path != null) {
               Iterator<Pattern> patternIter = excludePatterns.iterator();
               while (patternIter.hasNext()) {
                   if (patternIter.next().matcher(path).matches()) {
                       // at least one of the patterns excludes this request
                       return false;
                   }
               }
            }
            // this path is not excluded
            return true;
        }

        /**
         * FIFOSemaphore needs to be associated with the HTTP session, but is not serializable,
         * so this class knows how to deal.
         */
        protected static class FIFOSemaphoreFactory implements Serializable {

            private static final long serialVersionUID = 1L;

            private transient Semaphore semaphore;

            /**
             * Get the semaphore
             * @return a non-null Semaphore.
             */
            public synchronized Semaphore getSemaphore() {
                if (semaphore == null){
                    semaphore = new Semaphore(1);
                }
                return semaphore;
            }
        }
    }

