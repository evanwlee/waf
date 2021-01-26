package com.evanwlee.web.framework.components;

import org.apache.log4j.Logger;

import com.evanwlee.logging.LoggerFactory;
import com.evanwlee.persistence.IWrapper;
import com.evanwlee.web.framework.widget.SecureHtmlWidget;

public class HTMLTable extends SecureHtmlWidget{
	java.util.List<IWrapper> rows;
	private static Logger log = LoggerFactory.getLogger(HTMLTable.class.getName());
	public HTMLTable(java.util.List<IWrapper> rowValues){
		rows=rowValues;
	}
	public String render(){
		return render("");
	}
	public String render(String cssClass){
		log.debug("Rendering HTML Table");
		String result = "";
		
		if(rows.size()> 0 ){
		IWrapper headers = (IWrapper) rows.get(0);
		
		//Set-up Header
		java.util.Set<String> columns = headers.getColumnNames();
		java.util.Iterator<String> colIterator = columns.iterator();
		result = "<table class=\""+cssClass+"\">";
		result = result + "<tr>";
		while(colIterator.hasNext()){
			 result = result + "<th class=\""+cssClass+"\">";
			 String title = (String)colIterator.next();
			 title = title.replaceAll("_", " ");
			 result = result + title;
			 result = result + "</th>\n";
		}
		result = result + "</tr>";
		
		//Populate Valus
		java.util.Iterator<IWrapper> rowIterator = rows.iterator();
		while(rowIterator.hasNext()){
			
			 result = result + "<tr>";
			 IWrapper row = (IWrapper)rowIterator.next();
			 
			colIterator = columns.iterator();
			while(colIterator.hasNext()){
				String val =  row.getColumnValue((String)colIterator.next());
				if(val == null ){
					val = "&nbsp;";
				}else if("".equals(val.trim())){
					val = "&nbsp;";
				}
				 result = result + "<td class=\""+cssClass+"\">";
				 result = result + val;
				 result = result + "</td>";
			}
			 result = result + "</tr>\n";
		}
		 result = result + "</table>";
		
		}
		log.debug(result);
		return result;
	}

}
