<%@ page import="com.zytx.models.UserInfo" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>PC日志记录</title>
<!--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"> -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">
<script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script> 
<style>
    .datagrid-header-row td{background-color:rgb(226,237,255);color:#0E2D5F;}
  </style>
<script type="text/javascript">
$.fn.datebox.defaults.formatter = function(date){ 
	 var y = date.getFullYear(); 
	 var m = date.getMonth()+1; 
	 var d = date.getDate(); 
	 return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d); 
	 }; 
	 $.fn.datebox.defaults.parser = function(s){ 
	 if (!s) return new Date(); 
	 var ss = s.split('-'); 
	 var y = parseInt(ss[0],10); 
	 var m = parseInt(ss[1],10); 
	 var d = parseInt(ss[2],10); 
	 if (!isNaN(y) && !isNaN(m) && !isNaN(d)){ 
	 return new Date(y,m-1,d); 
	 } else { 
	 return new Date(); 
	 } 
	 }; 

	 function strDateTime(str)
	 {
//	 var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
//   var r = str.match(/^(((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\d):[0-5]?\d:[0-5]?\d)$/);
	 var r = str.match(/^[1-9][0-9]{3}-(0?[1-9]|1[0|1|2])-(0?[1-9]|[1|2][0-9]|3[0|1])\s(0?[1-9]|1[0-9]|2[0-3]):(0?[0-9]|[1|2|3|4|5][0-9])$/);
	 if(r==null)
	 return false; 
	/* var d= new Date(r[1], r[3]-1, r[4]); 
	 return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);*/
	 return true;
	 }



var opt =0; //0:增加 ；1：编辑
$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	
	grid=$('#pclogtt').datagrid({
	    title:'日志列表',
	    fitColumns:true,
 	    striped:true,
	    pageSize:25,
	    pageList:[15,25,30,35,40],
	    url:'/tcweb/yw/pcloglist',
	    queryParams:{},
	    columns:[[
	        {field:'logPerson',align:'center',title:'用户名',width:$(this).width() * 0.2},
	        {field:'logPersonCompany',align:'center',title:'公司',width:$(this).width() * 0.2},
	        {field:'logContext',align:'center',title:'描述',width:$(this).width() * 0.2},
	        {field:'beginTime',align:'center',title:'开始时间',width:$(this).width() * 0.2,formatter: function(value,rec,index) {
                if(value!="")
                    return value.substring(0,16);
		         }},
	        {field:'endTime',align:'center',title:'结束时间',width:$(this).width() * 0.2,formatter: function(value,rec,index) {
                if(value!="")
                    return value.substring(0,16);
		         }}
	    ]],
	    pagination:true	    
	});	
	$('#pclogtt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
 	  	  
}
);

function clearQuery(){
	$('#userName').attr("value","");
}

function query(){  
	
	var userName =$('#userName').attr("value"); 
	
     grid.datagrid("options").url='/tcweb/yw/pclogquery';
     grid.datagrid("options").queryParams={'userName':userName};
     
    $('#pclogtt').datagrid('reload');
	}

</script>
<style type="text/css">
td{
font-size:12px;
	overflow:hidden;
	padding:0;
	margin:0;
	}
	
#sousuo input {
  width: 100%; 
  height: 25px; 
 /* background: #F9F0DA; */
   padding-left: 2px; 
}

.form_input {
  display: block;
 /* width: 100%; */
  height: 34px;
  padding: 6px 12px;
  font-size: 14px;
  line-height: 1.42857143;
  color: #555;
  background-color: #fff;
  background-image: none;
  border: 1px solid #ccc;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
   box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
 -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
 -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
 transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
 }
 

</style>
</head>
<body class="easyui-layout" data-options="fit:true">
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">  
 <fieldset id="addDiv" style="margin-left:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
     <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%;"> 
     <tr>      
   <td nowarp align="right">用户名:</td>
   <td><input id="userName" name="userName" size="12" class="easyui-validatebox"></input></td>
   <td>&nbsp;&nbsp;</td>
   <td>&nbsp;&nbsp;</td>
    <td>
		<!--  <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a>   -->		
	 <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>
					
</td>
					
   </tr>
   
     </table>
  
  </fieldset>
</div> 
   <div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div>  
       <table id="pclogtt"></table>
   </div>  
        
    </div>  
</div> 

</body>
</html>