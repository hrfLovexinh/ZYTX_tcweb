<%@ page import="com.zytx.models.UserInfo" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>

<script type="text/javascript">
    $(function(){ 	

    	$('#btn-save,#btn-save2').linkbutton();
    	
         var url = encodeURI(encodeURI('/tcweb/elevator/getAutoYwCompanyList')); 
    	 $("#autocomplete").autocomplete(  
    	            url,  
    	            {  
    	            scroll: false,  
    	                matchContains: true,  
    	                width: 188,  
    	                minChars: 2,  
    	                extraParams: {	q: function() {
    					return $("#this").val();
    				    }},   
    	                dataType: "json",  
    	                mustMatch:false,  
    	                parse: function(data) {  
    	                    var rows = [];  
    	                    for(var i=0; i<data.length; i++){  
    	                     rows[rows.length] = {   
    	                       data:data[i].id +"-"+data[i].companyName,   
    	                       value:data[i].id,   
    	                       result:data[i].companyName   
    	                       };   
    	                     }  
    	                  return rows;  
    	                    },  
    	                formatItem: function(row, i, n) {  
    	                    return row;  
    	                },
    	                formatResult: function(row){return row.id; }    
    	            }  
    	        ).result(function(event, data, formatted) {
    	        	
    	            if(data)
    	            {
    	        	 $('#autocomplete2').attr("value",formatted);
    	            }
        	        });
    });

    function saveCar2d(){
    	var address= $('#autocomplete2').attr("value");
    	alert(address);
        }

    function clearquery(){
    	 $('#autocomplete').attr("value",'');
    	 $('#autocomplete2').attr("value",'');
    	 var address2= $('#autocomplete2').attr("value");
    	 alert(address2);
        }
  
 </script>
</head>
<body>

    <label for="autocomplete">选择一个运维公司：</label>
    <input id="autocomplete">
    <input type ="hidden" id="autocomplete2">
 <table>
 <tr>
 <td>
 <a href="javascript:void(0)" onclick="saveCar2d()" id="btn-save" icon="icon-save">保存</a>
 <a href="javascript:void(0)" onclick="clearquery()" id="btn-save2" icon="icon-save">清除</a>
 </td>
 </tr>
 </table>
</body>
</html>
