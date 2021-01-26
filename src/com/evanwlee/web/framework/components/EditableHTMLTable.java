package com.evanwlee.web.framework.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.evanwlee.logging.LoggerFactory;
import com.evanwlee.persistence.IWrapper;
import com.evanwlee.persistence.constants.PersistenceConstants;
import com.evanwlee.string.StringUtils;
import com.evanwlee.web.framework.Action;
import com.evanwlee.web.framework.UserProfile;
import com.evanwlee.web.framework.security.permission.AllowAccessPermission;
import com.evanwlee.web.framework.security.permission.ColumnPermission;
import com.evanwlee.web.framework.security.permission.Permission;
import com.evanwlee.web.hos.database.model.UserQBE;
import com.evanwlee.web.view.View;

public class EditableHTMLTable extends HTMLTable{
	private static Logger log = LoggerFactory.getLogger(EditableHTMLTable.class.getName());
	public EditableHTMLTable(java.util.List<IWrapper> rowValues){
		super(rowValues);
	}
	public String render(){
		return render("");
	}
	//Use ID column as PK
	//Make all columns editable
	public String render(String cssClass,String saveAction){
		Map<String, ColumnConfig> colConfig = new HashMap<String, ColumnConfig>();
		ColumnConfig idColumn = new ColumnConfig();
		idColumn.setPrimaryKey(true);
		idColumn.setEditable(false);
		colConfig.put(PersistenceConstants.ID_COLUMN_NAME, idColumn);
		return render(cssClass,colConfig,new AlphColumnComparator(),saveAction,"", null);
	}
	
	//Use ID column as PK
	//Make all columns editable
	public String render(String cssClass,Comparator<String> columnOrder,String saveAction){
		Map<String, ColumnConfig> colConfig = new HashMap<String, ColumnConfig>();
		ColumnConfig idColumn = new ColumnConfig();
		idColumn.setPrimaryKey(true);
		idColumn.setEditable(false);
		colConfig.put(PersistenceConstants.ID_COLUMN_NAME, idColumn);
		return render(cssClass,colConfig,columnOrder,saveAction,"", null);
	}

	/**
	 * 
	 * Render table will all bells & whistles but no Row Delete.
	 * 
	 * @param cssClass
	 * @param columConfigs
	 * @param columnOrder
	 * @param saveAction
	 * @return
	 */
	public String render(String cssClass,Map<String, ColumnConfig> columConfigs,Comparator<String> columnOrder, String saveAction){
		return render(cssClass,columConfigs,columnOrder, saveAction, "", null);
	}
	public String render(String cssClass,Map<String, ColumnConfig> columConfigs,Comparator<String> columnOrder, String saveAction, String deleteAction, UserProfile user){

		log.debug("Rendering HTML Table");
		String result = "";
		String formName = "editTableForm";
		if((!StringUtils.isEmpty(saveAction)) && saveAction.indexOf(".")>-1){
			formName = saveAction.substring(saveAction.indexOf(".")+1);
		}
		
		if(rows.size()> 0 ){
			IWrapper headers = (IWrapper) rows.get(0);
			result = "<form action=\"main\" method=\"POST\" name=\""+formName+"\" id=\""+formName+"\">\n";
			result = result + "<input name=\""+Action.ACTION_ID+"\" id=\""+Action.ACTION_ID +"\" value=\""+saveAction+"\" type=\"hidden\"/>\n";
			result = result + "<input name=\""+View.HIDDEN_ROW_ID+"\" id=\""+View.HIDDEN_ROW_ID+"\" value=\""+(rows.size()+1)+"\" type=\"hidden\"/>\n";
			//Set-up Header
			//java.util.Set<String> columns = headers.getColumnNames();
			//add column order here
			Collection cl = null;
			if(columnOrder != null){
				ArrayList<String> cltmp = new ArrayList<String>(headers.getColumnNames());
				Collections.sort(cltmp, columnOrder);
				cl = cltmp;
			}else{
				cl = headers.getColumnNames();
			}
			
			java.util.Iterator<String> colIterator = cl.iterator();
			result = result + "<table class=\""+cssClass+"\">";
			result = result + "<tr>";
			while(colIterator.hasNext()){
				 result = result + "<th class=\""+cssClass+"\">";
				 String title = (String)colIterator.next();
				 title = title.replaceAll("_", " ");
				 result = result + title;
				 result = result + "</th>\n";
			}
			if(!StringUtils.isEmpty(deleteAction)){
				 result = result + "<th class=\""+cssClass+"\">";
				 result = result + "</th>\n";
			}
			result = result + "</tr>";
			
			//Populate Valus
			java.util.Iterator<IWrapper> rowIterator = rows.iterator();
			String ID = "";
			int currMaxId = 0;
			while(rowIterator.hasNext()){
				 result = result + "<tr>";
				 IWrapper row = (IWrapper)rowIterator.next();
				 
				colIterator = cl.iterator();
				
				while(colIterator.hasNext()){
					String colName = (String)colIterator.next();
					ColumnConfig colConfig = columConfigs.get(colName);
					
					if(!ColumnConfig.isConfigDefined(colConfig)){
						//Use a default config
						colConfig = new ColumnConfig();
						if( user == null){
							colConfig.setPermission(new AllowAccessPermission());
						}else{
							colConfig.setPermission(new ColumnPermission(user.getRole()));
						}
					}

					String val =  row.getColumnValue(colName);
					ID =  row.getColumnValue(PersistenceConstants.ID_COLUMN_NAME);
					
					if( Integer.parseInt(ID) > currMaxId) {
						currMaxId = Integer.parseInt(ID);
					}
					if(val == null ){
						val = "&nbsp;";
					}else if("".equals(val.trim())){
						val = "&nbsp;";
					}
					 result = result + "<td class=\""+cssClass+"\">";
					 
					 if(ColumnConfig.isConfigDefined(colConfig) && colConfig.isPrimaryKey()){
						 result = result +"<input type=\"hidden\" id=\""+colName+"__"+ID+"\" name=\""+colName+"__"+ID+"\" value=\""+val+"\"></input>"+ val;
					 }else{
						 String readOnlyString = "readonly=\"readonly\"";;
						 if(colConfig.isEditable()){
							 readOnlyString = "";
						 }
						 result = result + "<input id=\""+colName+"__"+ID+"\" name=\""+colName+"__"+ID+"\" value=\""+val+"\" "+readOnlyString+"></input>";
					 }
					 result = result + "</td>";
				}
				if(!StringUtils.isEmpty(deleteAction)){
					result = result +"<td>"+ View.renderTableRowDeleteButton(formName, deleteAction, ID, "delteRow", "","styledButton", "Delete")+"</td>";
				}
				result = result + "</tr>\n"; 
			}
			int maxIdPlusOne = currMaxId+1;
			//Add new entry row
			colIterator = cl.iterator();
			result = result + "<tr>\n";
			while(colIterator.hasNext()){
				String colName = (String)colIterator.next();
				ColumnConfig colConfig = columConfigs.get(colName);
				if(ColumnConfig.isConfigDefined(colConfig) && colConfig.isPrimaryKey()){
					result = result + "<td>";
				}else{
					result = result + "<td><input id=\""+colName+"__"+maxIdPlusOne+"\" name=\""+colName+"__"+maxIdPlusOne+"\"></input>";
				}
				result = result + "</td>\n";
			}
			result = result + "</tr>\n";
			result = result + "</table>";
		    //result = result + "<button class=\"styledButton\" type=\"submit\" accesskey=\"s\" title=\"Save Edits... (ALT + S)\">Save</button>";
		    result = result + View.renderSubmitButton(formName, saveAction, "saveTable", "","styledButton", "Save",user);
		}
		result = result + "</form>";
		log.debug(result);
		return result;
	}

}
