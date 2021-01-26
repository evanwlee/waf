//*----------> Start general.js include -------------------------

/***************************************************************
 * GENERAL SCRIPTS USED ACCROSS MANY IF NOT ALL PAGES
 * 
 ***************************************************************/

/*
 * -------------    submitForm(form,action,actionAttribute)-------------------------
 * 
 * Submits the form to the controller with the action specified.
 * 
 * @param theForm to be submitted
 * @param Action to submit the form to
 * @param attribute that reprsents the interpreted action by the controller
 * 
 * @returns false so that the form will not post the action, allows the servlet to do so.
 * 				If it is set to true then double submit will occur!!
 */
function submitForm(theForm, theAction, theAttribute) {
 	theForm.target = "_self";
 	theAttribute.value = theAction;
    theForm.submit();
    return false;
}

function handleTableRowDeleteClick(theForm, theAction, theAttribute, theRowIdAttribute,theRowId) {
 	theRowIdAttribute.value = theRowId;
    return submitForm(theForm, theAction, theAttribute);
}


function setFormAttribute(theAction, theAttribute) {
 	theAttribute.value = theAction;
}

function editEntry(theForm, theAction, theActionValue, theId, theIdValue){
	theForm.target = "_self";
	theAction.value = theActionValue;
 	theId.value = theIdValue;
    theForm.submit();
    return false;
}

function handleFirstButtonClick(thePagingForm,action){
	thePagingForm.ACTION.value = action;

	thePagingForm.PAGE_DIRECTION.value = "FIRST";
	thePagingForm.CURRENT_PAGE_INDEX.value = "1";
	thePagingForm.submit();
	
	return false;
}
function handlePreviousButtonClick(thePagingForm,indx,action){
	thePagingForm.ACTION.value = action;
	//alert("Previous (current):" + indx);
	thePagingForm.PAGE_DIRECTION.value = "PREVIOUS";
	thePagingForm.CURRENT_PAGE_INDEX.value = indx;
	thePagingForm.submit();
	
	return false;
}
function handleGotoPage(selector,thePagingForm,action){
	
	var indx = selector.selectedIndex;
	thePagingForm.ACTION.value = action;
	thePagingForm.PAGE_DIRECTION.value = "GOTO";
	thePagingForm.CURRENT_PAGE_INDEX.value = selector[indx].value;
	//alert(selector[indx].value)
	thePagingForm.submit();
	
	return false;
}
function handleNextButtonClick(thePagingForm,indx,action){
	thePagingForm.ACTION.value = action;
	//alert("Next (current):" + indx);
	thePagingForm.PAGE_DIRECTION.value = "NEXT";
	thePagingForm.CURRENT_PAGE_INDEX.value = indx;
	thePagingForm.submit();
	
	return false;
}
function handleEndButtonClick(thePagingForm,indx,action){
	thePagingForm.ACTION.value = action;
	//alert("Last");
	thePagingForm.PAGE_DIRECTION.value = "LAST";
	thePagingForm.CURRENT_PAGE_INDEX.value = "1";
	thePagingForm.submit();
	
	return true;
}

function maxLength(src,textAreaCount){
    var tex = src.value;
    var len = tex.length;
    if(len > textAreaCount){
	      tex = tex.substring(0,textAreaCount);
	      src.value =tex;   
	      return false;
      }
    return true;
}
function limiter(src,textAreaCount){
    var tex = src.value;
    var len = tex.length;
    if(len > textAreaCount){
      tex = tex.substring(0,textAreaCount);
      
      src.value =tex;   
      return false;
      
      
    }
    document.getElementById(src.name + "Count").innerHTML = textAreaCount-len;
    return true;
}
//---------------< End general.js include -------------------------------------