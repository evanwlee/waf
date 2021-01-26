<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<script type="text/javascript" language="JavaScript" src="/waf/scripts/ajax.js"></script>
	        <script>
	        	var myCountArray = [0,0,0,0,0];
	        	var myStatusArray = ['','','','',''];
                function checkRequestStatus(theIdOfRequestToCheck){
    		     	request = createRequest();
    		  		if (request == null) {
    		  			hanleError("Unable to create request!");
    		  		}
    		  		var url= "status";
    				var requestData = "ID="+theIdOfRequestToCheck;

    				request.onreadystatechange = handleStatusResponse;
   					var nextValue = myCountArray[theIdOfRequestToCheck] +1;
   					myCountArray[theIdOfRequestToCheck] = nextValue;
   					
    		  		request.open("POST", url, true);
    		  		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    		  		request.send(requestData);
                }

                function handleStatusResponse(){
                	if (request.readyState == 4) { // onComplete

                		//check request status
                	    switch (request.status) {
                	          case 200:
                	        	 hanleOkResponse(request.responseText);
                	             break;
                	          case 500:
                	        	 hanleError(request.responseText);
                	             break;
                		  	  default:
                	             alert(request.status);
                		      break;
                		}
    		  		}    	
                }

                function hanleOkResponse(response){
                	
                    try{
		   				var responseValues = response.split(":");
		   				var id = responseValues[0];
		   				var status = responseValues[1];
		   				myStatusArray[id] = status;
		   				if(status == "DONE"){
		   				 	printStatus(status, id);
		   				}else{
		   					printStatus(status, id);
		   					setTimeout("checkRequestStatus("+id+")",3000);
		   					
		   				}
                    }catch(err){
	   					detailDiv.innerHTML = "Error....";
                    }
                }

                function printStatus(status, id){
                	var detailDiv = document.getElementById("StatusReport");

                	var statusHtml = "";
                	//statusHtml += id + " is " +status +" through "+myCountArray[id]+" checks.<br/>";
                	for (var i=1; i<myCountArray.length; i++) {
                    	var curStatus = myStatusArray[i];
                    	if( myStatusArray[i] != ""){
                			statusHtml += i + " is " +myStatusArray[i] +" through "+myCountArray[i]+" checks.<br/>";
                    	}
	   				}
   					detailDiv.innerHTML = statusHtml;
                }
                    

                function hanleError(response){
                	var detailDiv = document.getElementById("StatusReport");
	   				detailDiv.innerHTML = "Error....";
                }


        </script>
</head>
<body onload="checkRequestStatus('1')">
<div id="StatusReport" name="StatusReport">
</div>

<select id="queueId">
  <option selected value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option>
</select> 

<input type="button" onclick="checkRequestStatus(document.getElementById('queueId')[document.getElementById('queueId').selectedIndex].value)" value="Check onother ID"></input>
</body>
</html>