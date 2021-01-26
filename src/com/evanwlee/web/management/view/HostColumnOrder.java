package com.evanwlee.web.management.view;


import com.evanwlee.management.HostQBE;
import com.evanwlee.management.StatusQBE;
import com.evanwlee.web.framework.components.ColumnComparator;

public class HostColumnOrder extends ColumnComparator{

	
	public int getNumberValue(String column){
		if(column == null){
			return 8;
		}
		if(column.equals(HostQBE.COLUMN_FULL_NAME)){
			return 1;
		}
		if(column.equals(HostQBE.COLUMN_IP_ADDRESS)){
			return 3;
		}
		if(column.equals(HostQBE.COLUMN_NAME)){
			return 2;
		}
		if(column.equals(HostQBE.COLUMN_MAC)){
			return 4;
		}

		return 8;
	}

}
