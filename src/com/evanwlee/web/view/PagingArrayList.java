package com.evanwlee.web.view;

import java.util.ArrayList;
import java.util.List;

public class PagingArrayList<E> extends ArrayList<E> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PagingArrayList(int pageSize){
		super();
		this.pageSize = pageSize;
	}
	
	
	private int currentStartIndex = 0;
	private int pageSize = 10;
	private int numberOfPages = 0;
	private boolean isLastPage = false;
	private boolean isFirstPage = true;
	
	
	

	public List<E> getFirstPage(){
		isFirstPage = true;
		if( this.getNumberOfItems() < getPageSize()){
			return this;
		}
		ArrayList<E> result = new ArrayList<E>();
		for (int i = 0; i < this.getPageSize(); i++) {
			result.add(this.get(i));
		}
		return result;
	}
	
	public List<E> getNextPage(int currentPage){
		return getPage(currentPage+1);
	}
	
	public List<E> getPreviousPage(int currentPage){
		return getPage(currentPage-1);
	}
	
	public List<E> getPage(int index){
		
		if( index <= 1){
			return getFirstPage();
		}else if( index >= getNumberOfPages()){
			return getLastPage();
		}else{
			ArrayList<E> result = new ArrayList<E>();
			

			int end = getPageSize() * index;
			int start = end - (getPageSize()-1); 
			
			for (int i = (start-1); i <= (end-1); i++) {
				result.add(this.get(i));
			}
			return result;
		}
		
		
	}
	
	public List<E> getLastPage(){
		isLastPage = true;
		if( this.size() < getPageSize()){
			return this;
		}
		ArrayList<E> result = new ArrayList<E>();
		for (int i = (getNumberOfItems() - getNumberOfItemsInLastPage()); i <= getNumberOfItems()-1; i++) {
			result.add(this.get(i));
		}
		return result;
	}
	
	public List<E> jumpToPage(int pageIndx){
		return new ArrayList<E>();
	}
	
	public int getNumberOfPages(){
		
		if( this.size() > pageSize){
			int offset = 0;
			if( this.size() % getPageSize() > 0){
				offset = 1;
			}
			int numOfPages = this.size() / getPageSize();
			return numOfPages + offset;
			
		}else{
			return -1;
		}
	}
	
	public int getNumberOfFullPages(){
		
		if( this.size() > pageSize){
			int numOfPages = this.size() / getPageSize();
			return numOfPages ;
			
		}else{
			return 1;
		}
	}
	
	public int getNumberOfItemsInLastPage(){
		
		if( getNumberOfPages() == getNumberOfFullPages()){
			return getPageSize();
		}else{
			return getNumberOfItems() - ( getNumberOfFullPages() * getPageSize());
		}

	}
	
	public String getRangeOfItemsInPage(int currentPage){
		
		if( currentPage > getNumberOfFullPages()){
			int beginOfLastPage = (getNumberOfItems() - getNumberOfItemsInLastPage()+1);
			return beginOfLastPage +"-"+getNumberOfItems();
		}else if( currentPage <= 1){
			return "1-"+getPageSize();
		}else {
			
			
			int end = currentPage * getPageSize();
			int start = (end +1) - getPageSize();
	
			return start+"-"+end;
		}
		
	}
	
	public int getNumberOfItems(){
		
		return this.size();
	}
	
	public int getPageSize(){
		return pageSize;
	}
	
	public boolean isFirstPage(){
		return isFirstPage;
	}
	
	public boolean isLastPage(){
		return isLastPage;
	}
	
	

}
