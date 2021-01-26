package com.evanwlee.web.framework.components;

import java.util.Comparator;


public abstract class ColumnComparator implements Comparator<String> {


	public int compare(String o1, String o2) {

	    if(getNumberValue(o1) > getNumberValue(o2)){
	    	return 1;
	    }
	    if(getNumberValue(o2) < getNumberValue(o1)){
	    	return -1;
	    }
	    if(getNumberValue(o2) == getNumberValue(o1)){
	    	return 0;
	    }
	    return 0;
	}
	
	public abstract int getNumberValue(String column);

}
