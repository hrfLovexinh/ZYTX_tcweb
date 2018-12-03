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
$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

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
	 
	 var editIndex = undefined;
	 function endEditing(){
	 	if (editIndex == undefined){return true}
	 	if ($('#wbpersontt').datagrid('validateRow', editIndex)){
	 		$('#wbpersontt').datagrid('endEdit', editIndex);
	 		editIndex = undefined;
	 		return true;
	 	} else {
	 		return false;
	 	}
	 }

	 function strDateTime(str)
	 {
	 var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	 if(r==null)
	 return false; 
	 var d= new Date(r[1], r[3]-1, r[4]); 
	 return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
	 }
	 
	 function wbCompanyBa(id) {
			$.post("/tcweb/company/wbBeian?id=" + id,function(data){
				$("#beianFromId").val(data.id);
				$("#beianFrom").form('load',data);
			},"json");
			 win.window('open'); 
		}
	 function wbCompanyBad(id) {
			$.post("/tcweb/company/wbBeiand?id=" + id,function(data){
				$("#beianFromId").val(data.id);
				$("#beianFrom").form('load',data);
			},"json");
			 win.window('open'); 
		}


var opt =0; //0:增加 ；1：编辑
$(function(){
	$.ajaxSetup ({
	    cache: false 
	});

	 var inputWidth = $('#qcompanyName').width(); 
	
	 $('#qstartTime').datebox({     
	       width:inputWidth 
	   }); 

	 $('#qendTime').datebox({     
	       width:inputWidth 
	   }); 

	$('#btn-save,#btn-cancel').linkbutton(); 
	
	win = $('#car-window').window({closed:true,draggable:false,modal:true,resizable:false,minimizable:false,collapsible:false,maximizable:false,onClose: function(){
		$("#checkBoxba").attr('checked',false);
		$("#beianFrom").form("clear");
		$("#xxba").linkbutton('disable');}});
	wbwin = $("#personba-window").window({closed:true,draggable:false,resizable:false,modal:true,minimizable:false,collapsible:false,maximizable:false,onClose: function(){
		location.reload();} });
	
	grid=$('#tt').datagrid({
	    title:'单位列表<span style="color:#ff0000;">(红色：维保信用黑名单单位)</span>',
	    singleSelect:true,
	    striped:true,
	    pageSize:25,
	    pageList:[15,25,30,35,40],
	    url:'',
	    frozenColumns:[[
	        {field:'companyName',align:'left',halign:'center',title:'单位名称',width:250},
	        {field:'2',align:'left',halign:'center',title:'人<br/>员',width:20,formatter: function(value,rec,index) {
        		return "<a href='#' onclick='ywPersonBa(" + rec.id + ")'><i class='fa fa-user-o fa-lg' aria-hidden='true' title='维保人员备案' style='color:#61B5CF;'></i></a>";
            }},
	        {field:'address',align:'left',halign:'center',title:'单位办公地址',width:150},
	        {field:'1',align:'center',halign:'center',title:'是否备案',width:60,formatter: function(value,rec,index){
	        	if(rec.isBeian == 1) {
	        		return "已核对";
	        	} else {
	        		return "<a style='color:blue;'>未核对</a>";
	        	}
	        }},
	        {field:'filingDate',align:'center',halign:'center',title:'备案时间',width:80}
	        	    ]],
	    columns:[[
	        {field:'companyCode',align:'left',halign:'center',title:'单位编码',width:100},
			{field:'registAddress',align:'left',halign:'center',title:'单位注册地址',width:150},
			{field:'area',align:'left',halign:'center',title:'所在区域',width:60},
		    {field:'phone',align:'center',halign:'center',title:'值班电话',width:85},
		    {field:'telephone',align:'center',halign:'center',title:'办公电话',width:85},
		    {field:'representativor',align:'center',halign:'center',title:'负责人',width:50},
		    {field:'representativorTel',align:'center',halign:'center',title:'负责人电话',width:85},
		    {field:'contact',align:'center',halign:'center',title:'联系人',width:50},
		    {field:'contactTel',align:'center',halign:'center',title:'联系人电话',width:85},
		    {field:'officeProof',align:'center',halign:'center',title:'办公场所证明',width:90},
		    {field:'certificateCode',align:'center',halign:'center',title:'许可证编号',width:100},
		    {field:'type',align:'center',halign:'center',title:'资质类型',width:50},
		    {field:'qlevel',align:'center',halign:'center',title:'资质级别',width:40},
		    {field:'validity',align:'center',halign:'center',title:'资质有效期',width:80},
		    {field:'note',align:'center',halign:'center',title:'备注',width:150},
		    {field:'filingPerson',align:'center',halign:'center',title:'备案负责人',width:50}/* ,
		    {field:'filingPersonTel',align:'center',halign:'center',title:'备案负责人电话',width:85}, */
	    ]],
	    pagination:true,
	    toolbar:[{
            text: '单位备案',
            iconCls: 'icon-edit',
            handler: function () {
            	var row = grid.datagrid('getSelected');
            	if(row == null) {
            		$.messager.alert("警告","请先选择记录行","warning");
            	} else {
            		if(row.isBeian == 1) {
            			wbCompanyBad(row.id);
            		} 
            		wbCompanyBa(row.id);
            	}
            }
        },'-',
        {
            text: 'excel表导出',
            iconCls:'icon-excel',
            handler: function () {
            	$.messager.confirm('Confirm','确认导出备案数据?',function(r){ 
            		if(r) {
            			location.href="/tcweb/company/wbCompanyDc";
            			$.messager.show({
		                    title:'提示信息',
		                    msg:'请耐心等待!,正在导出...',
		                    timeout:2000,
		                    showType:'fade',
		                    style:{
		                        top:'45%'
		                    }
		                });
            		}
            	});
            }
        }],
        rowStyler:function(index,row){  //暂时取消颜色超期
		    var hmdFlag = row.hmdFlag;
		    if(hmdFlag ==1)
		    	return 'color:#ff0000;';
		    else
		    	return 'color:#000000;';
		    
	     }
	});	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
	query();

});


function clearQuery(){
	$('#qcompanyName').attr("value","");
	$('#qaddress').attr("value","");
	$("#qstartTime").datebox("setValue","");  
	$("#qendTime").datebox("setValue","");  
	$("#isBeianId").combobox("setValue","3");
}

function query(){  
	  
    var qcompanyName=$('#qcompanyName').attr("value");
    var qaddress=$('#qaddress').attr("value");
    var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue"); 
	var isBeianId = $("#isBeianId").combobox("getValue");
     grid.datagrid("options").url='/tcweb/company/wbtypequery';
     grid.datagrid("options").queryParams={'companyName':qcompanyName,'address':qaddress,'qstartTime':qstartTime,'qendTime':qendTime,'isBeian':isBeianId};
     
    $('#tt').datagrid('reload');
	}
	
function closeWindow(){ 
	win.window('close');
	$("#checkBoxba").attr('checked',false);
	$("#xxba").linkbutton('disable');
	$("#beianFrom").form("clear");
	}
	
function confirmba() {
	if($("#checkBoxba").attr('checked') == true) {
		$("#xxba").linkbutton('enable');  
	} else {
		$("#xxba").linkbutton('disable');  
	}
}

function saveCar() {
	if($("#companyNameinfo").val() != "" && $("#companyCodeinfo").val() != "" && $("#registAddress").val() != "" && $("#address").val() != "" && $("#contact").val() != "" && $("#contactTel").val() != "" && $("#representativor").val() != "" && $("#representativorTel").val() != "" && $("#phone").val() != "" && $("#telephone").val() != "" && $("#officeProof").val() != "" && $("#safeyManPerson").val() != "" && $("#area").val() != "" && $("#type").val() != "" && $("#certificateCode").val() != "" && $("#qlevel").val() != "" && $("#validity").val() != "" && $("#filingPerson").val() != "" && $("#filingPersonTel").val() != ""){
		$("#beianFrom").form("submit",{
			url:"/tcweb/company/wbCompanyBa",
			success: function(data){  
				if(data == "success") {
					$.messager.show({  	
						  title:'信息',  	
						  msg:'企业信息备案完成',  	
						  timeout:5000,  	
						  showType:'slide'  
						});
				} else {
					$.messager.alert("错误","操作异常,请尽快与管理员联系!");
				}
				win.window('close');  
				$("#beianFrom").form("clear");
				query();
		   } 
		});
	} else{
		$.messager.alert("warning","带<span style='color:red;'>*</span>号项必须填写,请完善信息之后确认备案","warning");
	}
}
function ywPersonBa(id) {
	grid=$('#wbpersontt').datagrid({
	    singleSelect:true,
	    striped:true,
	    pageSize:15,
	    pageList:[15,25,30,35,40],
	    url:"/tcweb/user/ywpersonlistByCompany?id=" + id,
	    columns:[[
	        {field:'userName',align:'center',halign:'center',title:'姓名',width:60,editor:'text'},
	        {field:'idCard',align:'center',halign:'center',title:'身份证',width:150,editor:'text'},
	        {field:'contactPhone',align:'center',halign:'center',title:'联系电话',width:110,editor:'numberbox'},
	        {field:'qregistereddate',align:'center',halign:'center',title:'注册日期',width:110},
	        {field:'1',align:'center',halign:'center',title:'人员备案',width:110,formatter: function(value,rec,index) {
        		if(rec.isBeian == 1) {
        			return "已核对";
        		} else {
        			return "<a href='#' onclick='wbpersonBa(" + rec.id + ")' style='color:red;'>核对确认</a>";
        		}
            }}
	    ]],
	    pagination:true,
	    toolbar:[],
	    onDblClickCell: function(index,field,value){
	    	var rows = $('#wbpersontt').datagrid('selectRow', index).datagrid('getSelections');
	    	if(rows[0].isBeian == 1) {
	    		$.messager.alert('warning','信息已经备案，无法修改！','warning');
	    		return;
	    	}
	    	if (endEditing()){
				$('#wbpersontt').datagrid('selectRow', index)
						.datagrid('editCell', {index:index,field:field});
				editIndex = index;
				isEdit = true;
			}
	  	},

	});
	$('#wbpersontt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
	wbwin.window('open');
}
function wbpersonBa(id){
	endEditing();
	$.messager.confirm('Confirm','确认备案?',function(r){ 
		if (r){ 
			var rows = $('#wbpersontt').datagrid('getSelections');
			$.post("/tcweb/user/wbpersonBa?id=" + id,{userName:rows[0].userName,idCard:rows[0].idCard,contactPhone:rows[0].contactPhone},function(data){
				if(data.msg != "success") {
					$.messager.alert("error","处理异常,请尽快与管理员联系！","error");
				} else {
					$('#wbpersontt').datagrid('reload');
				}
			},"json"); 
		} 
		});
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
  
   <td  nowrap align="right" style="150">资质有效期从：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime"></input></td>
   <td align="right" nowrap style="150">到:</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime"></input></td>
   </tr>
   <tr>
   	 <td nowrap align="right" style="150">是否备案：</td> 
   <td nowrap colspan="2">
	   <select id="isBeianId" name="isBeian" class="easyui-combobox" style="width:100px;"  >
	   		<option value="3">无</option>
	   		<option value="1">已备案</option>
	   		<option value="2">未备案</option>
	   </select></td>
   <td></td>
    <td></td>
     <td></td>
      <td></td>
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
  
  <div id="car-window" title="详细信息" style="width:600px;height:570px;"> 
  <div class="easyui-layout" data-options="fit:true">
  <div data-options="region:'north'" style="height:565px;text-align:center;border:0px;" >     
  <form method="post" id="beianFrom" action="/tcweb/company/wbCompanyBa">   
  <table style="width: 100%;margin:auto;"> 
  <tr><td><td><input id="beianFromId" name="id" type="hidden"></input></td></td></tr>
   <tr>      
   <td width="150" nowrap align="center" style="background: #F5F5F5;">单位名称<span style="color:red;">*</span></td>      
   <td colspan="3"><input class="form_input" id="companyNameinfo" name="companyName"></input></td>
   </tr>
   <tr>  
    <td width="150" nowrap align="center" style="background: #F5F5F5;">单位编码<span style="color:red;">*</span></td>      
   <td colspan="3"><input class="form_input" id="companyCodeinfo" name="companyCode"></input></td>
   </tr>
   <tr>      
   <td width="150" nowrap align="center" style="background: #F5F5F5;">单位注册地址<span style="color:red;">*</span></td>      
   <td colspan="3"><input class="form_input" id="registAddress" name="registAddress"></input></td>
   </tr>
   <tr> 
   <td width="150" align="center" style="background: #F5F5F5;">单位办公地址<span style="color:red;">*</span></td>      
   <td colspan="3"><input class="form_input" id="address" name="address"></input></td>
   </tr>
   <tr>
    <td width="150" align="center" style="background: #F5F5F5;">联系人<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="contact" name="contact"></input></td> 
    <td width="150" align="center" style="background: #F5F5F5;">联系人电话<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="contactTel" name="contactTel"></input></td> 
   </tr>
    <tr>
    <td width="150" align="center" style="background: #F5F5F5;">负责人<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="representativor" name="representativor"></input></td> 
    <td width="150" align="center" style="background: #F5F5F5;">负责人电话<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="representativorTel" name="representativorTel"></input></td> 
   </tr>
   <tr>
    <td width="150" align="center" style="background: #F5F5F5;">值班电话<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="phone" name="phone"></input></td>  
   <td width="150" align="center" style="background: #F5F5F5;">办公电话<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="telephone" name="telephone"></input></td>  
   </tr>
   <tr> 
   <td width="150" align="center" style="background: #F5F5F5;">办公场所证明<span style="color:red;">*</span></td>      
   <td colspan="3"><input class="form_input" id="officeProof" name="officeProof"></input></td>
   </tr>
   <tr>
  <td width="150" align="center" style="background: #F5F5F5;">所在区域<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="area" name="area"></input></td>   
    <td width="150" align="center" style="background: #F5F5F5;">安全管理员<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="safeyManPerson" name="safeyManPerson"></input></td>  
   </tr>
   <tr><td colspan="4">----------------------------------------------------------------------------------------------------------</td></tr>
    <tr>  
   <td width="150" align="center" style="background: #F5F5F5;">许可证编号<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="certificateCode" name="certificateCode"></input></td>      
   <td width="150" align="center" style="background: #F5F5F5;">资质类型<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="type" name="type"></input></td>       
   </tr>
   <tr>  
   <td width="150" align="center" style="background: #F5F5F5;">资质级别<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="qlevel" name="qlevel"></input></td>   
    <td width="150" align="center" style="background: #F5F5F5;">资质有效期<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="validity" name="validity" class="easyui-datebox"  data-options="editable:false"></input></td>   
   </tr>
   <!-- <tr>  
   <td width="150" align="center" style="background: #F5F5F5;">备案负责人<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="filingPerson" name="filingPerson"></input></td>   
    <td width="150" align="center" style="background: #F5F5F5;">备案人联系方式<span style="color:red;">*</span></td>      
   <td><input class="form_input" id="filingPersonTel" name="filingPersonTel" class="easyui-datebox"  data-options="editable:false"></input></td>   
   </tr> -->
   <tr> 
   <td width="150" align="center" style="background: #F5F5F5;">备注</td>      
   <td colspan="3"><input class="form_input" id="note" name="note"></input></td>
   </tr>
   </table>
   <table width=100%>
   <tr style="height:10px">
   	<td colspan="4" align="center"><input id="checkBoxba" type="checkbox" style="zoom:120%;" onclick="confirmba()"><span style="color:red;font-size: large;">我已确认备案信息真实有效,同意备案</span></td>
   </tr>
    <tr>
    <td align="center">
     <a id="xxba" href="#" class="easyui-linkbutton" disabled="true" icon="e-icon fa fa-save fa-lg" onclick="saveCar()" style="width:100px;color:#3399FF;">确认备案</a>
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-close fa-lg" onclick="closeWindow()" style="width:100px">取消</a>
    </td>
    </tr> 
   </table>   
   </form>
   </div> 
   </div>
 </div>
 
 	<!-- 维保人员确认 -->
 <div id="personba-window" class="easyui-layout" title="单位维保人员" style="width:600px;height:650px;overflow-y:hidden">
 	<div region="north" style="height:30px;"><span style="color:red;font-size:large;">双击单元格可修改信息（注意：修改之后点击备案才能生效）</span></div>
	<div region="center" style="width:100%;">
       
       <table id="wbpersontt" style="width:600px;height:570px;"></table>
   
</div> 
 
</div>
</body>
</html>