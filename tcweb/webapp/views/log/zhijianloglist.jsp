<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">  -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">

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
	 var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	 if(r==null)
	 return false; 
	 var d= new Date(r[1], r[3]-1, r[4]); 
	 return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
	 }

	 $(function(){

			$.ajaxSetup ({
			    cache: false 
			});

			 var inputWidth = $('#logPerson').width(); 

			 $('#qstartTime').datebox({     
			       width:inputWidth 
			   }); 

			 $('#qendTime').datebox({     
			       width:inputWidth 
			   }); 

			grid=$('#ltt').datagrid({
			    title:'日志列表',
			    singleSelect:true,
			    striped:true,
			    pageSize:25,
			    pageList:[15,25,30,35,40],
			    url:'/tcweb/log/zhijianloglist',
			    queryParams:{},
			    columns:[[
			        {field:'logPerson',align:'center',title:'账号',width:$(this).width() * 0.2},
			        {field:'logPersonCompany',align:'center',title:'单位',width:$(this).width() * 0.2},
			        {field:'logTime',align:'center',title:'时间',width:$(this).width() * 0.2},
			        {field:'logAction',align:'center',title:'操作',width:$(this).width() * 0.2},
			        {field:'logContext',align:'center',title:'内容',width:$(this).width() * 0.2}
			    ]],
			    pagination:true		    
			});
			$('#ltt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
			
	 });

	 function query(){ 
		 var logPerson=$('#logPerson').attr("value");  
		 var logPersonCompany=$('#logPersonCompany').attr("value"); 
		 var qstartTime=$('#qstartTime').datebox("getValue");  
	     var qendTime=$('#qendTime').datebox("getValue");  
	     grid.datagrid("options").url='/tcweb/log/zhijianlogquery';
	     grid.datagrid("options").queryParams={'logPerson':logPerson,'logPersonCompany':logPersonCompany,'qstartTime':qstartTime,'qendTime':qendTime};  
	     $('#ltt').datagrid('reload');
		 }

	 function clearQuery(){
		 $('#logPerson').attr("value","");
		 $('#logPersonCompany').attr("value","");
		 $("#qstartTime").datebox("setValue","");  
		 $("#qendTime").datebox("setValue","");  
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
/*  background: #F9F0DA; */
  padding-left: 15px;
}
  
 .form_input {
  display: block;
  width: 100%;
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
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);height:80px;"> 
 <fieldset id="addDiv" style="height:100%;margin:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
   <table id="sousuo" style="width:100%">
    <tr>      
    <td align="right" nowrap>账号：</td> 
    <td nowrap><input id="logPerson" name="logPerson" ></input></td>
    <td align="right" nowrap>单位：</td> 
   <td nowrap><input id="logPersonCompany" name="logPersonCompany"></input></td> 
  
   <td align="right" nowrap>开始时间：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" ></input></td>
   <td align="right" nowrap>结束时间：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime"></input></td>
   <td colspan="2">
    <!--  <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
	<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 	-->
	<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
		<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>				
   </td>				
   </tr>
  </table>
  
  </fieldset>
</div>

 <div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div style="margin-top:1px;">  
       <table id="ltt"></table>
   </div>  
        
    </div>  
</div> 
</body>
</html>