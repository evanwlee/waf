package com.evanwlee.web.framework.components;

import java.util.Comparator;

public class AlphColumnComparator implements Comparator<String> {

	public int compare(String o1, String o2) {
		if(o1 == null && o2 != null ){
	    	return -1;
	    }
	    
	    if(o2 == null && o1 != null ){
	    	return 1;
	    }
	    
	    if(o2 == null && o1 == null ){
	    	return 0;
	    }

	    int      retValue  = o1.compareTo(o2);
	    return retValue;
	}

}
