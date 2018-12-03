<%@ page import="com.zytx.models.UserInfo" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>电梯安全公共服务平台</title>
<!--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/> -->
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">
<% 
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int role=0;
if(userinfo!=null){
	 role = userinfo.getRole(); 
	}
	else{
		 Cookie[] cookies =  request.getCookies();
		 String userName = "";
			 String password = "";
			if (cookies != null) {
			   for (Cookie c : cookies) {
				if (c.getName().equals("userName")) {
				    userName = c.getValue();
			      }
				if (c.getName().equals("password")) {
				   password = c.getValue();
			      }
			    }
	    }
			UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select * from TwoCodeUserInfo where loginName= ?",new Object[] { userName });
		    role = user.getRole();
	}

%>
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

var opt =0; //0:增加 ；1：编辑
$(function(){
	$.ajaxSetup ({
	    cache: false 
	});

	

	$('#btn-save,#btn-cancel').linkbutton(); 
	
	grid=$('#tt').datagrid({
	    title:'维保单位备案信息',
	    singleSelect:true,
	    striped:true,
	    pageSize:25,
	    pageList:[15,25,30,35,40],
	    url:'',
	    frozenColumns:[[
			{field:'companyName',align:'left',halign:'center',title:'单位名称',width:200},
			{field:'registAddress',align:'left',halign:'center',title:'单位注册地址',width:150},
			{field:'address',align:'left',halign:'center',title:'单位地址',width:150},
	    ]],
	    columns:[[
			{field:'companyCode',align:'left',halign:'center',title:'单位编码',width:100},
			{field:'area',align:'left',halign:'center',title:'所在区域',width:60},
	        {field:'representativor',align:'center',halign:'center',title:'负责人',width:50},
	        {field:'representativorTel',align:'center',halign:'center',title:'负责人电话',width:85},
	        {field:'contact',align:'center',halign:'center',title:'联系人',width:50},
	        {field:'contactTel',align:'center',halign:'center',title:'联系人电话',width:85},
	        {field:'phone',align:'center',halign:'center',title:'值班电话',width:85},
	        {field:'telephone',align:'center',halign:'center',title:'办公电话',width:85},
	        {field:'qlevel',align:'center',halign:'center',title:'级别',width:40},
	        {field:'type',align:'center',halign:'center',title:'单位类型',width:50},
	        {field:'validity',align:'center',halign:'center',title:'有效期',width:80},
	        {field:'certificateCode',align:'center',halign:'center',title:'证书编码',width:100},
	        {field:'filingDate',align:'center',halign:'center',title:'备案时间',width:80},
	        {field:'filingPerson',align:'center',halign:'center',title:'备案负责人',width:50},
	        {field:'filingPersonTel',align:'center',halign:'center',title:'备案负责人电话',width:85},
	        {field:'officeProof',align:'center',halign:'center',title:'办公场所证明',width:90},
	        {field:'note',align:'center',halign:'center',title:'备注',width:150}
	    ]],
	    pagination:true,
	    toolbar:[],
	});	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
	query();

});


function clearQuery(){
	$('#qcompanyName').attr("value","");
	$('#qaddress').attr("value","");
	$("#filingPerson").attr("value","");
	$("#companyCode").attr("value","");
	
}

function query(){  
	  
    var qcompanyName=$('#qcompanyName').attr("value");
    var qaddress=$('#qaddress').attr("value");
    var filingPerson=$('#filingPerson').attr("value"); 
	var companyCode=$('#companyCode').attr("value");
     grid.datagrid("options").url='/tcweb/company/badCompanyList';
     grid.datagrid("options").queryParams={'companyName':qcompanyName,'address':qaddress,'filingPerson':filingPerson,'companyCode':companyCode};
     
    $('#tt').datagrid('reload');
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
   <td nowrap align="right" style="150">单位名称：</td> 
   <td nowrap><input id="qcompanyName" name="qcompanyName"   class="easyui-validatebox"></input></td>
   <td nowrap align="right" style="150">单位地址：</td> 
   <td nowrap><input id="qaddress" name="qaddress"   class="easyui-validatebox"></input></td>
  
   <td  nowrap align="right" style="150">备案负责人：</td>
   <td><input id="filingPerson" name="filingPerson"   class="easyui-validatebox"></input></td>
   <td align="right" nowrap style="150">单位编码</td>
   <td><input id="companyCode" name="companyCode" class="easyui-validatebox"></input></td>
    <td> 
		<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
		<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>
  </td>
   </tr>
   </table>
  </fieldset>
</div> 
   <div region="center" style="width:100%;">
       
       <table id="tt"></table>
   
</div> 
  
</body>
</html>