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
			UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select role from TwoCodeUserInfo where loginName= ?",new Object[] { userName });
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

	 $(function(){
		 $.ajaxSetup ({
			    cache: false 
			});

		 
		 win = $('#contract-window').window({  closed:true,draggable:false,modal:true});
		 win2 = $('#contract-window2').window({  closed:true,draggable:false,modal:true});
		 win3 = $('#contract-window3').window({  closed:true,draggable:false,modal:true});
		 win4 = $('#contract-window4').window({  closed:true,draggable:false,modal:true});
		 win5 = $('#contract-window5').window({  closed:true,draggable:false,modal:true});
         
		 
		 $('#btn-save,#btn-save2').linkbutton(); 
		 form = win.find('myform');
		 form2 = win5.find('paymentform');

		 grid=$('#tt').datagrid({
			    title:'合同列表',
			    pageSize:10,
			    pageList:[10,20,25,30,35,40],
			    url:'/tcweb/cs/contractlist',
			    queryParams:{},
			    columns:[[
			        {field:'contractNumber',title:'合同编号',width:160},
			        {field:'contractName',title:'合同名称',width:100},
			        {field:'contractAttribute',title:'合同属性',width:80},
			        {field:'contractCompanyName',title:'公司名称',width:160},
			        {field:'contractCustomerName',title:'客户单位',width:160},
			        {field:'contractSigneddate',title:'合同签订日期',width:140},
			        {field:'contractEnddate',title:'合同到期日期',width:140},
			        {field:'contractEnddate2',title:'到期天数',
			        	formatter: function(value,rec,index) {
			        	return  DateDiff(rec.contractEnddate,rec.contractSigneddate); }
				    },
			        {field:'1',title:'电梯列表',width:60,
				    	formatter: function(value,rec,index) {
				  //  	var contractNumber =''+rec.contractNumber;
			        	return  "<img src='<%=request.getContextPath()%>/images/cselevatorlist.png' alt='电梯列表' style='cursor:hand;' onclick='cselevatorlist("+rec.id+")'/>";     
			            }
				        },
			        {field:'2',title:'付款情况',width:160,      
				        formatter: function(value,rec,index) {  
				        return  "<img src='<%=request.getContextPath()%>/images/cspayment.png' alt='付款情况' style='cursor:hand;' onclick='cspaymentlist("+rec.id+","+"\""+rec.contractNumber+"\""+","+"\""+rec.contractName+"\""+","+"\""+rec.contractAmount+"\""+")'/>";    

					       }
					        }
			        
			    ]],
			    nowrap:true,
			    pagination:true,
			    toolbar:[{
			        text:'新增',
			        iconCls:'icon-add',
			        handler:function(){
			    	win.window('open');  
			    //	form.form('clear');
			    	form.url ='/tcweb/cs/add';	
			        }
			    },{
			        text:'删除',
			        iconCls:'icon-cut',
			        handler:function(){
			    	 var row = grid.datagrid('getSelected'); 
			    	 if(row){
			    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
			    	 jQuery.post('/tcweb/cs/delete',
			    	    	 {'id':row.id,'registNumber':row.registNumber},
			    	    	 function(data){
			    	    		eval("data="+"'"+data+"'");  
			    	    		if("success"==data){
			    	    		//	$.messager.alert("操作成功",'谢谢');
			    	    		 $.messager.show({   
			    			 title:'提示信息',
			    			 timeout:1000,
			    			 msg:'操作成功，谢谢。' 
			    		 });  	
				    	            grid.datagrid('reload');
			    	    		}
			    	    		else{
			    	    			$.messager.alert('操作失败','没有删除','error');
				    	    		}
				    	       });}}
		  	       );
			    	 }else{
			    		 $.messager.show({   
			    			 title:'警告',
			    			 msg:'请先选择记录行。' 
			    		 });   
				     }
			    	 
			        }
			    },'-',{
			        text:'修改',
			        iconCls:'icon-edit',
			        handler:function(){
			    	var row = grid.datagrid('getSelected');   
			    	if (row){
			    		 win.window('open');   
			    		 colseWinDetail();
			    		 editFun(row.registNumber,row.deviceId);
			    		 form.form('load', '/tcweb/elevator/edit/'+row.id);
			    		 form.url = '/tcweb/elevator/update/'+row.id; 
			    		 opt =1;
			    	 } else {  
			    		 $.messager.show({   
			    			 title:'警告',
			    			 msg:'请先选择记录行。' 
			    		 });   
			    	 }  
			        }
			    }]
		 });
		 $('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,25,30,35,40]});   
	   	  	 
	 });

	 function contractsave(){
		 var contractform =$('#myform'); 
		 contractform.form('submit', {  
				url:form.url,
				onSubmit:function(){
		             //表单验证
		            return $("#myform").form('validate');
				},
				success : function(data) { 
					eval("data=" + "'" + data + "'");  
					if ("exist" == data) {
						$.messager.alert('操作失败', '该合同编号已经存在，不能重复添加', 'error');
					} else if ("success" == data) {   
					$.messager.show( {
						title : '提示信息',
						timeout : 1000,
						msg : '操作成功，谢谢。'
					});
					grid.datagrid('reload');
					win.window('close');
				} else {
					$.messager.alert('操作失败', '操作失败', 'error');
				}
			}
				});
		 }

	//计算天数差的函数，通用  
	   function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2002-12-18格式  
	       var  aDate,  oDate1,  oDate2,  iDays;  
	       aDate  =  sDate1.split("-");  
	       oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);    //转换为12-18-2002格式  
	       aDate  =  sDate2.split("-");  
	       oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);  
	       iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数  
	       return  iDays;  
	  }

	   function query(){
		   var contractNumber=$('#contractNumberinfo').attr("value");
		   var contractAttribute = $('#contractAttributeinfo option:selected').val();
		   var contractCustomerName=$('#contractCustomerNameinfo').attr("value");
		   var qstartTime=$('#qstartTime').datebox("getValue");  
		   var qendTime=$('#qendTime').datebox("getValue");
		   grid.datagrid("options").url='/tcweb/cs/query';
		   grid.datagrid("options").queryParams={'contractNumber':contractNumber,'contractAttribute':contractAttribute,'contractCustomerName':contractCustomerName,'qstartTime':qstartTime,'qendTime':qendTime};  
		   $('#tt').datagrid('reload');
		   }

	  function clearQuery(){
		    $('#contractNumberinfo').attr("value","");
			$('#contractCustomerNameinfo').attr("value","");
			$('#qstartTime').attr("value","");
			$('#qendTime').attr("value","");
		  } 

	  function cselevatorlist(id){ 
		  win2.window('open'); 
		  grid2=$('#contract-elevator').datagrid({
			    title:'',
			    pageSize:10,
			    pageList:[10,20,25,30,35,40],
			    url:'/tcweb/elevator/contractElevatorList',
			    queryParams:{"id":id},
			    columns:[[
                  {field:'registNumber',title:'电梯编号',width:60},
                  {field:'address',title:'地址',width:200},
                  {field:'buildingName',title:'楼盘名称',width:200},
                  {field:'registCode',title:'登记编号',width:200},
                  {field:'useNumber',title:'单位内部编号'}
			    ]],
			    nowrap:true,
			    pagination:true,
			    toolbar:[{
			        text:'新增',
			        iconCls:'icon-add',
			        handler:function(){
			    	win3.window('open');  
			        contractelevatorList(id);
			        }
			    },{
			        text:'删除',
			        iconCls:'icon-cut',
			        handler:function(){
			    	 var row = grid.datagrid('getSelected'); 
			    	 if(row){
			    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
			    	 jQuery.post('/tcweb/cs/delete',
			    	    	 {'id':row.id,'registNumber':row.registNumber},
			    	    	 function(data){
			    	    		eval("data="+"'"+data+"'");  
			    	    		if("success"==data){
			    	    		//	$.messager.alert("操作成功",'谢谢');
			    	    		 $.messager.show({   
			    			 title:'提示信息',
			    			 timeout:1000,
			    			 msg:'操作成功，谢谢。' 
			    		 });  	
				    	            grid.datagrid('reload');
			    	    		}
			    	    		else{
			    	    			$.messager.alert('操作失败','没有删除','error');
				    	    		}
				    	       });}}
		  	       );
			    	 }else{
			    		 $.messager.show({   
			    			 title:'警告',
			    			 msg:'请先选择记录行。' 
			    		 });   
				     }
			    	 
			        }
			    },'-',{
			        text:'修改',
			        iconCls:'icon-edit',
			        handler:function(){
			    	var row = grid.datagrid('getSelected');   
			    	if (row){
			    		 win.window('open');   
			    		 colseWinDetail();
			    		 editFun(row.registNumber,row.deviceId);
			    		 form.form('load', '/tcweb/elevator/edit/'+row.id);
			    		 form.url = '/tcweb/elevator/update/'+row.id; 
			    		 opt =1;
			    	 } else {  
			    		 $.messager.show({   
			    			 title:'警告',
			    			 msg:'请先选择记录行。' 
			    		 });   
			    	 }  
			        }
			    }]
		 });
		 $('#contract-elevator').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,25,30,35,40]});   

		  }

	  function contractelevatorList(id){
		  grid3=$('#elevatorlist').datagrid({
			    title:'',
			    pageSize:10,
			    pageList:[10,20,25,30,35,40],
			    url:'/tcweb/elevator/csywElevatorlist',
			    queryParams:{},
			    idField:'id',
			    columns:[[
			    {field:'id',checkbox:true}, 
                {field:'registNumber',title:'电梯编号',width:60},
                {field:'area',title:'区域',width:60},
                {field:'address',title:'地址',width:200},
                {field:'buildingName',title:'楼盘名称',width:200},
                {field:'registCode',title:'登记编号',width:200},
                {field:'useNumber',title:'单位内部编号'}
			    ]],
			    nowrap:true,
			    pagination:true,
			    toolbar:[{
			        text:'添加',
			        iconCls:'icon-add',
			        handler:function(){ 
			    	var row = grid3.datagrid('getSelected'); 
			    	if (row){ 
			        selectcontractelevatorList(id);
			    	}
			    	else {  
			    		 $.messager.show({   
			    			 title:'警告',
			    			 msg:'请先选择记录行。' 
			    		 });   
			    	 }  
			        }
			    }]
		 });
		 $('#elevatorlist').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,25,30,35,40]});   
	   	  	 
		  }

	  function selectcontractelevatorList(id){
		  var checkedItems = $('#elevatorlist').datagrid('getChecked');  
		  var names="";
			 $.each(checkedItems, function(index, item){
					names=names+item.id+",";
				}); 
			
		 if(names!="")
			names=names.substring(0,names.length-1); 

     //    alert(names);
		 jQuery.post('/tcweb/elevator/eleRealationContract',
		    	 {'names':names,'id':id},
		    	 function(data){
		    		eval("data="+"'"+data+"'");  
		    		if("success"==data){
		    		 $.messager.show({   
				        title:'提示信息',
				        timeout:1000,
				        msg:'操作成功，谢谢。' 
			          });  	
		    		
		    		win3.window('close'); 	
	 	            grid2.datagrid('reload');
		    		}
		    		else{
		    			$.messager.alert('操作失败','操作失败','error');
	    	    		}
	    	       });
		  }

	 function cspaymentlist(id,contractNumber,contractName,contractAmount){
		 win4.window('open'); 
		 $('#pcontractNumber').attr("value",contractNumber);
		 $('#pcontractName').attr("value",contractName);
		 $('#pcontractAmount').attr("value",contractAmount);    
		 grid4=$('#contract-payment').datagrid({
			    title:'',
			    pageSize:10,
			    pageList:[10,20,25,30,35,40],
			    url:'/tcweb/cs/contractPaymentList',
			    queryParams:{"id":id},
			    columns:[[
            {field:'category',title:'类别'},
            {field:'paymentiname',title:'名称',width:100},
            {field:'actualAmount',title:'付款金额'},
            {field:'conditiondate',title:'付款时间',width:150},
            {field:'directionPayment',title:'收支方向'}
		    
			    ]],
			    nowrap:true,
			    pagination:true,
			    toolbar:[{
			        text:'新增',
			        iconCls:'icon-add',
			        handler:function(){
			    	win5.window('open');  
			    	$('#contractinfoId').attr("value",id);
			    	$('#paymentcontractNumber').attr("value",contractNumber);
			    	form2.url ='/tcweb/cs/paymentadd';	
			        }
			    },{
			        text:'删除',
			        iconCls:'icon-cut',
			        handler:function(){
			    	 var row = grid.datagrid('getSelected'); 
			    	 if(row){
			    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
			    	 jQuery.post('/tcweb/cs/delete',
			    	    	 {'id':row.id,'registNumber':row.registNumber},
			    	    	 function(data){
			    	    		eval("data="+"'"+data+"'");  
			    	    		if("success"==data){
			    	    		//	$.messager.alert("操作成功",'谢谢');
			    	    		 $.messager.show({   
			    			 title:'提示信息',
			    			 timeout:1000,
			    			 msg:'操作成功，谢谢。' 
			    		 });  	
				    	            grid.datagrid('reload');
			    	    		}
			    	    		else{
			    	    			$.messager.alert('操作失败','没有删除','error');
				    	    		}
				    	       });}}
		  	       );
			    	 }else{
			    		 $.messager.show({   
			    			 title:'警告',
			    			 msg:'请先选择记录行。' 
			    		 });   
				     }
			    	 
			        }
			    },'-',{
			        text:'修改',
			        iconCls:'icon-edit',
			        handler:function(){
			    	var row = grid.datagrid('getSelected');   
			    	if (row){
			    		 win.window('open');   
			    		 colseWinDetail();
			    		 editFun(row.registNumber,row.deviceId);
			    		 form.form('load', '/tcweb/elevator/edit/'+row.id);
			    		 form.url = '/tcweb/elevator/update/'+row.id; 
			    		 opt =1;
			    	 } else {  
			    		 $.messager.show({   
			    			 title:'警告',
			    			 msg:'请先选择记录行。' 
			    		 });   
			    	 }  
			        }
			    }]
			  
		 });
		 $('#contract-payment').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,25,30,35,40]});   
	   	   
		 }

	 function computepaymenttotal(colName){
		 var payrows = $('#contract-payment').datagrid('getRows');
         var paytotal = 0;
         for (var i = 0; i < payrows.length; i++) {
        	 paytotal += parseFloat(payrows[i][colName]);
         }
         return paytotal;

		 }

	 function  query3(){
		 var address=$('#addressinfo').attr("value");
		 var area=$('#areainfo option:selected').val();
		 grid3.datagrid("options").url='/tcweb/elevator/csywElevatorquery';
		 grid3.datagrid("options").queryParams={'area':area,'address':address};
		 $('#elevatorlist').datagrid('reload');
		 
		 }

	 function clearQuery3(){
		 $('#addressinfo').attr("value","");
		 $('#areainfo option:first').attr('selected','selected');
		 }


	  function contractpaymentsave(){
		    var paymentform =$('#paymentform');	
		    paymentform.form('submit', {  
				url:form2.url,
				onSubmit:function(){  
		             //表单验证
		            return $("#paymentform").form('validate'); 	 
				},
				success : function(data) {  
					eval("data=" + "'" + data + "'");  
					if ("success" == data) {   
					$.messager.show( {
						title : '提示信息',
						timeout : 1000,
						msg : '操作成功，谢谢。'
					});
					win5.window('close');
					grid4.datagrid('reload');
					
				} else {
					$.messager.alert('操作失败', '操作失败', 'error');
				}
			}
				});  
		 }
</script>
</head>
<body class="easyui-layout">
<div region="north" style="overflow:hidden">
<fieldset id="addDiv" style="width: 100%;margin:0px"><legend>查询条件</legend>
<table> 
     <tr>      
   <td align="right" nowrap>合同编号：</td> 
   <td nowrap><input id="contractNumberinfo" name="contractNumberinfo" size="24"></input></td>
   <td align="right" nowrap>合同属性：</td> 
   <td><select id="contractAttributeinfo" name="contractAttributeinfo">
   <option value="自营">自营</option>
   <option value="转包">转包</option>
   </select></td> 
    <td align="right" nowrap>客户单位：</td> 
   <td nowrap><input id="contractCustomerNameinfo" name="contractCustomerNameinfo" size="24" class="easyui-validatebox"></input></td>
   </tr>
   <tr>
   <td  nowrap>开始时间：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" style="width:152px;"></input></td>
   <td align="right" nowrap>结束时间：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime" style="width:152px;"></input></td>
   <td colspan="2"><a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
	<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 				
</td>
   </tr>
 </table>
</fieldset> 
</div>

<div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div style="margin-top:1px;">  
       <table id="tt"></table>
   </div>  
        
    </div>  
</div>
<div id="contract-window" title="电梯合同" style="width:750px;height:550px;"> 
<div class="easyui-layout" data-options="fit:true">  
<form method="post" id="myform"> 
  <div data-options="region:'north'" style="height:120px"> 
  
  <table>    
   <tr>      
   <td width="90" align="right" nowrap>合同编号：</td>      
   <td><input id="contractNumber" name="contractNumber" class="easyui-validatebox textbox"  data-options="required:true,validType:'length[1,50]'"></input></td> 
   <td width="90" align="right" nowrap>合同属性：</td>      
   <td><select id="contractAttribute" name="contractAttribute">
   <option value="自营">自营</option>
   <option value="转包">转包</option>
   </select></td> 
   <td width="90" align="right" nowrap>合同状态：</td>      
   <td><select id="contractState" name="contractState">
   <option value="可执行">可执行</option>
   <option value="待定">待定</option>
   </select>
   </td> 
   </tr>
   <tr>
   <td width="90" align="right" nowrap>合同类型：</td> 
   <td><select id="contractType" name="contractType">
   <option value="电梯维保合同">电梯维保合同</option>
   <option value="设备租赁合同">设备租赁合同</option>
   <option value="其它">其它</option>
   </select></td> 
   <td width="90" align="right" nowrap>维保频率：</td> 
   <td><input id="contractYwFrequency" name="contractYwFrequency" class="easyui-validatebox textbox"  data-options="required:true,validType:'length[1,30]'"></input>（/每月）</td>
   </tr>
   <tr>
   <td width="90" align="right" nowrap>合同名称：</td> 
   <td><input id="contractName" name="contractName" class="easyui-validatebox textbox"  data-options="required:true,validType:'length[1,50]'" ></input></td> 
  <td width="90" align="right" nowrap>公司名称：</td> 
  <td><input id="contractCompanyName" name="contractCompanyName" class="easyui-validatebox textbox" data-options="required:true,validType:'length[1,50]'"></input></td>
   </tr>
   <tr>
   <td width="90" align="right" nowrap>签订日期：</td> 
   <td><input id="contractSigneddate" name="contractSigneddate" class="easyui-datebox"></input></td> 
  <td width="90" align="right" nowrap>结束日期：</td> 
  <td><input id="contractEnddate" name="contractEnddate"  class="easyui-datebox"></input></td>
   </tr>
   </table>
   </div>
     <div data-options="region:'center'" style="overflow-x:auto;overflow-y:hidden"> 
     <table>
   <tr>
   <td width="90" align="right" nowrap>客户名称：</td> 
   <td><input id="contractCustomerName" name="contractCustomerName" class="easyui-validatebox textbox"  data-options="required:true,validType:'length[1,50]'"></input></td>
   <td width="90" align="right" nowrap>客户联系人：</td> 
   <td><input id="contractCustomerPerson" name="contractCustomerPerson" class="easyui-validatebox textbox"  data-options="validType:'length[0,20]'"></input></td>
   </tr>
    <tr>
   <td width="90" align="right" nowrap>客户单位地址：</td> 
   <td><input id="contractCustomerAddress" name="contractCustomerAddress" class="easyui-validatebox textbox"  data-options="validType:'length[0,50]'"></input></td>
   </tr>
   <tr>
   <td width="90" align="right" nowrap>客户电话：</td> 
   <td><input id="contractCustomerTel" name="contractCustomerTel" class="easyui-validatebox textbox"  data-options="validType:'length[0,50]'"></input></td>
   <td width="90" align="right" nowrap>客户传真：</td> 
   <td><input id="contractCustomerFax" name="contractCustomerFax" class="easyui-validatebox textbox"  data-options="validType:'length[0,30]'"></input></td>
   </tr>
   </table>
   </div>
     <div data-options="region:'south'" style="height:250px">  
   <table>
   <tr>
   <td width="90" align="right" nowrap>合同金额：</td> 
   <td><input id="contractAmount" name="contractAmount" class="easyui-validatebox textbox"  data-options="required:true,validType:'length[1,50]'"></input></td>
   <td width="90" align="right" nowrap>付款方式：</td> 
   <td><input id="contractPaymentWay" name="contractPaymentWay" class="easyui-validatebox textbox"  data-options="validType:'length[0,30]'"></input></td>
   </tr>
   </table>
   <table>
   <tr>
   <td width="90" align="right" nowrap>主要条款：</td> 
    <td colspan><textarea id="contractMainItems" name="contractMainItems" rows="2" cols="57" class="easyui-validatebox textbox"  data-options="validType:'length[0,500]'"></textarea></td>
   </tr> 
   <tr>
   <td width="90" align="right" nowrap>付款说明：</td> 
    <td colspan><textarea id="contractPaymentBeizhu" name="contractPaymentBeizhu" rows="2" cols="57"  class="easyui-validatebox textbox"  data-options="validType:'length[0,100]'"></textarea></td>
   </tr>
    <tr>
   <td width="90" align="right" nowrap>违约责任：</td> 
    <td colspan><textarea id="contractDefaultBeizhu" name="contractDefaultBeizhu" rows="2" cols="57" class="easyui-validatebox textbox"  data-options="validType:'length[0,100]'"></textarea></td>
   </tr>
       <tr>
       <td align="center" colspan="59">
       <a href="javascript:void(0)" onclick="contractsave()" id="btn-save" icon="icon-save">保存</a></td>  
      </tr>
    </table>  
     </div>  
  </form>  
  </div>
  </div>
  
  
   <div id="contract-window2" title="详细信息" style="width:780px;height:550px;"> 
   <div style="padding:20px 10px 40px 10px;">
   
   <div id="bb" class="easyui-accordion">
   <div style="margin-top:1px;" title="电梯列表" data-options="selected:true">  
       <table id="contract-elevator"></table>
   </div> 
    
   </div>
   
   
   
    
   </div>
   </div>
     
   <div id="contract-window4" title="付款情况" style="width:780px;height:550px;"> 
      <div class="easyui-layout" data-options="fit:true">
        <div region="north" style="overflow:hidden">
         <fieldset id="addDiv" style="width: 100%;margin:0px"><legend>合同信息</legend>
          <table>
           <tr>
           <td align="right" nowrap>合同编号：</td> 
           <td nowrap><input id="pcontractNumber" name="pcontractNumber"></input></td>
           <td align="right" nowrap>合同名称：</td> 
           <td nowrap><input id="pcontractName" name="pcontractName"></input></td>
           <td align="right" nowrap>总金额：</td> 
           <td nowrap><input id="pcontractAmount" name="pcontractAmount"></input></td>
           </tr>
          </table>
          </fieldset>
        </div>
        <div region="center">
         
            <div title="付款记录">  
             <table id="contract-payment"></table>
           </div>    
          
       </div>
     </div>
   </div>
   
   <div id="contract-window3" title="添加电梯" style="width:680px;height:480px;"> 
   <div region="north" style="overflow:hidden">
      <fieldset id="addDiv" style="width: 100%;margin:0px"><legend>查询条件</legend>
      <table> 
       <tr>      
       <td align="right" nowrap>地址：</td> 
       <td nowrap><input id="addressinfo" name="addressinfo" size="24" class="easyui-validatebox"></input></td>
       <td nowrap>所在区域：</td>
       <td> <select id="areainfo"   name="areainfo" style="width:100px;">
            <option value=""></option>
            <option value="锦江">锦江</option>
            <option value="青羊">青羊</option>
            <option value="金牛">金牛</option>
            <option value="成华">成华</option>
            <option value="武侯">武侯</option>
            <option value="高新">高新</option>
            <option value="天府新">天府新</option>
            <option value="青白江">青白江</option>
            <option value="龙泉驿">龙泉驿</option>
            <option value="新都">新都</option> 
            <option value="温江">温江</option> 
            <option value="双流">双流</option> 
            <option value="郫县">郫县</option> 
            <option value="金堂">金堂</option>
            <option value="大邑">大邑</option>
            <option value="蒲江">蒲江</option>
            <option value="新津">新津</option>
            <option value="都江堰">都江堰</option>
            <option value="崇州">崇州</option>
            <option value="邛崃">邛崃</option>
            <option value="彭州">彭州</option>
            <option value="安仁">安仁</option>
            <option value="大丰">大丰</option>  
         </select>
     </td>
      <td><a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query3()">查询</a>  
	 <a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery3()">清空</a> 				
    </td>
    </tr>
   </table>
   </fieldset> 
  </div>
   
   <div region="center">
   <div style="margin-top:1px;" title="电梯列表">  
       <table id="elevatorlist"></table>
   </div> 
   </div>
   
   </div>  
   
   <div id="contract-window5" title="合同付款" style="width:550px;height:350px;"> 
  <div class="easyui-layout" data-options="fit:true"> 
    <form method="post" id="paymentform">
    <div data-options="region:'north'" style="height:220px">
  <table>    
   <tr>      
   <td width="90" align="right" nowrap>类别：</td>      
   <td><select id="category" name="category">
   <option value="预付款">预付款</option>
   <option value="尾款">尾款</option>
   </select></td> 
   
   <td width="90" align="right" nowrap>名称：</td> 
   <td><input id="paymentiname" name="paymentiname" class="easyui-validatebox textbox"  data-options="required:true,validType:'length[1,30]'"></input></td>
   </tr>
   <tr>
   <td width="90" align="right" nowrap>金额：</td> 
   <td><input id="actualAmount" name="actualAmount" class="easyui-numberbox" data-options="min:0,precision:2"></input></td> 
   <td width="90" align="right" nowrap>付款日期：</td> 
   <td><input id="conditiondate" name="conditiondate" class="easyui-datebox" required="required"></input></td> 

  </tr>
   <tr>
    <td width="90" align="right" nowrap>收支方向：</td>      
   <td><select id="directionPayment" name="directionPayment">
   <option value="1">收入</option>
   <option value="0">支出</option>
   </select></td></tr>
   <tr>
   <td><input type="hidden" id="contractinfoId" name="contractinfoId"></input></td> 
   <td><input type="hidden" id="paymentcontractNumber" name="contractNumber"></input></td>
   </tr>
   </table>
   </div>
   
     <div data-options="region:'center'" style="overflow-x:auto;overflow-y:hidden"> 
     <table width="100%">
      <tr>
       <td align="right">
       <a href="javascript:void(0)" onclick="contractpaymentsave()" id="btn-save2" icon="icon-save">保存</a>
       </td>  
      </tr>
     </table>
     </div>
     </form>
  </div>
  </div> 	
 
</body>
</html>