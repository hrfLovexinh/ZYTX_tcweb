<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
 <!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"> -->
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/myeasyuiicon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">
<link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.no-icons.min.css" rel="stylesheet"> 
<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
 <style>
    .datagrid-header-row td{background-color:rgb(226,237,255);color:#0E2D5F;}
  </style>

<% 
String cityName = GlobalFunction.cityName;

UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int isliulan = 0;
int role=0;
if(userinfo!=null){
	 role = userinfo.getRole(); 
	 isliulan =userinfo.getIsliulan();
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
		    if(user != null)
			    role = user.getRole();
		    else
		    	response.sendRedirect(request.getContextPath() +"/index.jsp");
		    
		    UserInfoVO user2 =UserInfoVO.findFirstBySql(UserInfoVO.class, "select isliulan from  TCUserInfo where loginName= ? and isinvalid = 0 ",new Object[] { userName });
		    if(user2 != null)
		    	isliulan = user2.getIsliulan();
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

	 var format = function(time, format){
		    var t = new Date(time);
		    var tf = function(i){return (i < 10 ? '0' : '') + i};
		    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
		        switch(a){
		            case 'yyyy':
		                return tf(t.getFullYear());
		                break;
		            case 'MM':
		                return tf(t.getMonth() + 1);
		                break;
		            case 'mm':
		                return tf(t.getMinutes());
		                break;
		            case 'dd':
		                return tf(t.getDate());
		                break;
		            case 'HH':
		                return tf(t.getHours());
		                break;
		            case 'ss':
		                return tf(t.getSeconds());
		                break;
		        }
		    })
		};

$(function(){

	$.ajaxSetup ({
		cache: false
	});

	win = $('#windowform').window({minimizable:false,maximizable:false,collapsible:false,closed:true,draggable:false,modal:true }); 
	form = win.find('form');
	
	win2 = $('#windowform2').window({minimizable:false,maximizable:false,collapsible:false,closed:true,draggable:false,modal:true });
    form2 =win2.find('form');
	 
	win4 = $('#windowform3').window({minimizable:false,maximizable:false,collapsible:false,closed:true,draggable:false,modal:true });    
	

	win3 = $('#failure-window').window({minimizable:false,maximizable:false,collapsible:false,closed:true,draggable:false,modal:true,onClose:function(){$('#tt').datagrid('reload')} });   

     var url = encodeURI(encodeURI('/tcweb/elevator/getAutoYwCompanyList')); 
 	 $("#ywCompanyIdinfo").autocomplete(  
 	            url,  
 	            {  
 	            scroll: false,  
 	                matchContains: true,  
 	                width: 188,  
 	                minChars: 2, 
 	                max:20, 
 	                scrollHeight: 100,
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
 	        	 $('#ywCompanyIdinfo2').attr("value",formatted);
 	            }
 	            else{
 	             $('#ywCompanyIdinfo2').attr("value",'');
 	 	            }
     	        });

 	 $("#ywCompanyIdinfo3").autocomplete(  
	            url,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2, 
	                max:20, 
	                scrollHeight: 100,
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
	        	 $('#ywCompanyIdinfo4').attr("value",formatted);
	            }
	            else{
	             $('#ywCompanyIdinfo4').attr("value",'');
	 	            }
  	        });

 	 var ywurl = encodeURI(encodeURI('/tcweb/elevator/getAutoYwCompanyList')); 
	 $("#ywCompanyId2").autocomplete(  
			    ywurl,  
	            {  
	            scroll: true,  
	                matchContains: true,  
	                width: 220,  
	                minChars: 2,
	                max:50,
	                scrollHeight:200,  
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
	        	 $('#ywCompanyId').attr("value",formatted);
	            }
	            else{
	             $('#ywCompanyId').attr("value",'');
	 	            }
    	        });

	 $("#ywCompanyId4").autocomplete(  
			    ywurl,  
	            {  
	            scroll: true,  
	                matchContains: true,  
	                width: 220,  
	                minChars: 2,
	                max:50,
	                scrollHeight:200,  
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
	        	 $('#ywCompanyId3').attr("value",formatted);
	            }
	            else{
	             $('#ywCompanyId3').attr("value",'');
	 	            }
 	        });
     
	
	grid=$('#tt').datagrid({
	    title:'标签入库记录',
	    pageSize:25,
	    pageList:[15,25,30,35,40],
        url:'/tcweb/twocode/twocode96333list',
        fitColumns: true,
	    fit:false, 
	    queryParams:{},
	    columns:[[   
	  	        {field:'puserName',align:'center',title:'入库人',width:$(this).width() * 0.25},
	  	        {field:'ptime',align:'center',halign:'center',title:'入库时间',width:$(this).width() * 0.25},
	  	        {field:'pcount',align:'center',halign:'center',title:'入库数量',width:$(this).width() * 0.24},
	  	        {field:'pbeizhu',align:'center',halign:'center',title:'入库备注',width:$(this).width() * 0.24}
	  	        ]],
	  	 pagination:true,
	  	 singleSelect:true,
	  	 striped:true,
	  	 toolbar:[
	  	       {
	  		        text:'入库',
	  		        iconCls:'e-icon fa fa-plus',
	  		        handler:function(){
	  		    	win.window('open');  
	  		    //	form.form('clear');
	  		    	form.url ='/tcweb/twocode/add96333';	
	  		        }
	  		    },{
	  		        text:'删除',
	  		        iconCls:'e-icon fa fa-minus',
	  		        handler:function(){
	  		    	 var row = grid.datagrid('getSelected'); 
	  		    	 if(row){
	  		    	 $.messager.confirm('','确定要删除',function(data){if(data){	
                     var  pcount = row.pcount;
			  		 if(checkCount(pcount)){ 
	  		    	 jQuery.post('/tcweb/twocode/delete96333',
	  		    	    	 {'id':row.id},
	  		    	    	 function(data){
	  		    	    		eval("data="+"'"+data+"'");  
	  		    	    		if("success"==data){
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
	  			    	       });
			  		 }
			  		 else{
			  			$.messager.alert('操作失败','库存数少于已经领用数量，不能删除','error');
				  		 }
		    	       }}
	  	  	       );}
	  		    	else{
	  		    		 $.messager.show({   
	  		    			 title:'警告',
	  		    			 msg:'请先选择记录行。' 
	  		    		 });   
	  			     }
	  		    }}
	  		   
	  		    ],
	  		   onLoadSuccess: function(data) {
	  		   var rows=$('#tt').datagrid("getRows");  
	  		   if (rows.length > 0) {          
	  			  $('#tt').datagrid('selectRow',0);//grid加载完成后自动选中第一行
	  			  var row=$('#tt').datagrid('getSelections');//获取选中的数据
	              $('#dtsl').html(row[0].ptcount);
	              $('#lysl').html(row[0].rtcount);
	              $('#sysl').html(Number($('#dtsl').html())-Number($('#lysl').html()));
	              $('#ztsl').html(row[0].pztcount);
	              $('#dhssl').html(row[0].dhspcount);
	              $('#hssl').html(row[0].pztcount - row[0].dhspcount);
		  		   }
	  		   }
});	
	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});   
     
	$('#etab').tabs({
	    border:false,
	    onSelect:function(title,index){
	//      alert(title+' is selected');
	       tabSelectfun(title,index);   
	    }
	});
}
);

function saveTwoCode96333Info(){
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){
		var pcount=$('#pcount').attr("value"); 
		var pbeizhu=$('#pbeizhu').attr("value");     
		

		return true;
		
	},

	success : function(data) {
		eval("data=" + "'" + data + "'");
		if ("exist" == data) {
			$.messager.alert('操作失败', '不能添加', 'error');
		}else if("exist2" == data){
			$.messager.alert('操作失败', '不能重复添加', 'error');
			}
		 else if ("success" == data) {  
		$.messager.show( {
			title : '提示信息',
			timeout : 1000,
			msg : '操作成功，谢谢。'
		});
		win.window('close');
		grid.datagrid('reload');
	} else {
		$.messager.alert('操作失败', '添加标签', 'error');
	}
}
	});
}

function checkCount(pcount){
   var dtsl = Number($('#dtsl').html());
   var lysl = Number($('#lysl').html());
   var checkResult =dtsl - lysl - pcount;
   if(checkResult > 0)
	 return true;
   else
	 return false;
}
	
function check(value){  
	var str = value;   
	var pattern = /^\d{6}$/;    
	if(pattern.test(str)){
	     return true;
	}
	else{
	    return false;
	} 
}  	

function query(){
	var ywCompanyId=$('#ywCompanyId').attr("value");

	if (!ywCompanyId){
	    	ywCompanyId =0;
	    }
	
	$('#ttly').datagrid("options").url='/tcweb/twocode/query96333ly';
	$('#ttly').datagrid("options").queryParams={'ywCompanyIdinfo2':ywCompanyId};
		
	$('#ttly').datagrid('reload');
	}

function clearQuery(){
	$('#ywCompanyId').attr("value","");
	$('#ywCompanyId2').attr("value","");
}

function queryZhanTie(){
	var ywCompanyId=$('#ywCompanyId3').attr("value");

	if (!ywCompanyId){
	    	ywCompanyId =0;
	    }
	
	$('#ttzt').datagrid("options").url='/tcweb/twocode/query96333zt';
	$('#ttzt').datagrid("options").queryParams={'ywCompanyIdinfo2':ywCompanyId};
		
	$('#ttzt').datagrid('reload');
	}

function clearZhanTieQuery(){
	$('#ywCompanyId3').attr("value","");
	$('#ywCompanyId4').attr("value","");
}


function saveTwoCode96333Info2(){
   
	var rtime =$('#rtime').datebox("getValue");  
	var ywCompanyId =0;
    ywCompanyId=$('#ywCompanyIdinfo2').attr("value");  
	 if(!ywCompanyId){
		   $.messager.alert('操作失败', '请选择领用标签的运维公司', 'error');
            return;
		   }

     if(rtime == ""){
    	 $.messager.alert('操作失败', '请选择领用日期', 'error');
    	 return;

         }

     form2.form('submit', {  
 		url:form2.url,
 		onSubmit:function(){
 		
 		return true;
 		
 	},

 	success : function(data) {
 		eval("data=" + "'" + data + "'");
 		if ("exist" == data) {
 			$.messager.alert('操作失败', '不能添加', 'error');
 		}else if("exist2" == data){
 			$.messager.alert('操作失败', '不能重复添加', 'error');
 			}
 		 else if ("success" == data) {  
 		$.messager.show( {
 			title : '提示信息',
 			timeout : 1000,
 			msg : '操作成功，谢谢。'
 		});
 		win2.window('close');
 		gridly.datagrid('reload');
 	} else {
 		$.messager.alert('操作失败', '添加标签', 'error');
 	}
 }
 	});
     
}

function altertwocode(registNumber,infoState){
	 if(infoState > 2){
		 $.messager.alert('操作失败', '此二维码便签已经被核实，不能改领', 'error'); 
		 return;
	 }
	 else{
	 win4.window('open'); 
	 $('#sregistNumber4').attr("value",registNumber);
	 }
}

function saveTwoCodeInfo4(){
	var sregistNumber4 =$('#sregistNumber4').attr("value");	   
	var rtime4 =$('#rtime4').datebox("getValue"); 
	  var lingyongType = $('#lingyongType4 option:selected').val();
	   if(!check(sregistNumber4)){  
			$.messager.alert('操作失败', '标签二维码编号必须为6位整数', 'error');
			return false;
		}
		
	    var ywCompanyId =0;
		if(lingyongType == 0){
		   ywCompanyId=$('#ywCompanyIdinfo4').attr("value");  
		   if(!ywCompanyId){
			   $.messager.alert('操作失败', '请选择领用标签的运维公司', 'error');
	            return;
			   }
		 }
		 
	   if(rtime4 == ""){
	    	 $.messager.alert('操作失败', '请选择领用日期', 'error');
	    	 return;

	         }
	   var userName4 = $('#userName4').attr("value");  
	   if(userName4 == ""){
		   $.messager.alert('操作失败', '请填写领用人姓名', 'error');
         return;  
	   }

	   var loginName4 = $('#loginName4').attr("value");
	     if(lingyongType == 1 && loginName4 == ""){
	    	 $.messager.alert('操作失败', '领用人账号必填', 'error');
	    	 return;
	         }

	   win4.window('close'); 
	   
	   jQuery.post('/tcweb/twocode/alterlingyong',
			   	 {'sregistNumber':sregistNumber4,'ywCompanyId':ywCompanyId,'userName':userName4,'rtime':rtime4,'loginName':loginName4},
			   	 function(data){
			   		eval("data="+"'"+data+"'");  
			   		if("success"==data){
			   		 $.messager.show({   
					 title:'提示信息',
					 timeout:1000,
					 msg:'操作成功，谢谢。' 
				 });  
		           var rows = $('#lyflt').datagrid('getSelections');
	               for(i=0;i<rows.length;i++){//循环，把行的数组拆分。
			       var registNumber =rows[i].registNumber;
			       if(registNumber == sregistNumber4){
			 	   var index = $('#lyflt').datagrid('getRowIndex',rows[i]);//根据几条数据（几行）获取到对应数量的索引。         
			 	   $('#lyflt').datagrid('deleteRow',index);//根据索引删除对应的行。
			 	   $('#lysuccess').attr("value", Number($('#lysuccess').attr("value"))+1);           
			       $('#lyfailuer').attr("value",$("#lyflt").datagrid('getRows').length);         
			      }
		          }	 	     
		        }
			   		else{
			   			$.messager.alert('操作失败','操作失败','error');
				    		}
				       }); 	 
}


function tabSelectfun(title,index){ 
	if(index == 1){
		gridly=$('#ttly').datagrid({
		    title:'标签领用记录',
		    pageSize:25,
		    pageList:[15,25,30,35,40],
		    fitColumns: true,
		    fit:false, 
	        url:'/tcweb/twocode/twocode96333lylist',
		    queryParams:{},
		    columns:[[   
		  	         {field:'rcompanyName',align:'center',halign:'center',title:'标签领用人公司',width:$(this).width() * 0.29},
	  	             {field:'ruserName',align:'center',halign:'center',title:'标签领用人',width:$(this).width() * 0.15},
	  	             {field:'rtime',align:'center',halign:'center',title:'标签领用时间',width:$(this).width() * 0.25},
		  	         {field:'rcount',align:'center',halign:'center',title:'数量',width:$(this).width() * 0.10},
		  	         {field:'rtype',align:'center',halign:'center',title:'方式',width:$(this).width() * 0.10,formatter: function(value,rec,index) {
	 	                 if(value == 0)
	 	                     return "领用";
	 	                 else
	 	                     return "回收";
	 			         }},
		  	         {field:'rbeizhu',align:'center',halign:'center',title:'备注',width:$(this).width() * 0.10}
		  	        ]],
		  	 pagination:true,
		  	 singleSelect:true,
		  	 striped:true,
		  	 toolbar:[
		  	       {
		  		        text:'领用',
		  		        iconCls:'e-icon fa fa-user',
		  		        handler:function(){
		  		    	win2.window('open');  
		  		    	$('#windowform2').panel({title: "标签领用"});	 
		  		    	form2.url ='/tcweb/twocode/add96333ly';	
		  		        }
		  		    },{
		  		        text:'回收',
		  		        iconCls:'e-icon fa fa-minus',
		  		        handler:function(){
		  		    	 var row = grid.datagrid('getSelected'); 
		  		    	 if(row){
		  		    	 $.messager.confirm('','确定要回收',function(data){if(data){
		  		    		win2.window('open'); 
		  		    		$('#windowform2').panel({title: "标签回收"});	 	 
		  		    		form2.url ='/tcweb/twocode/add96333lyhs';	
			    	       }}
		  	  	       );}
		  		    	else{
		  		    		 $.messager.show({   
		  		    			 title:'警告',
		  		    			 msg:'请先选择记录行。' 
		  		    		 });   
		  			     }
		  		    }}   
		  		    ],
		  		   onLoadSuccess: function(data) {  
		  		   var rows=$('#ttly').datagrid("getRows");     
		  		   if (rows.length > 0) {           
		  			  $('#ttly').datagrid('selectRow',0);//grid加载完成后自动选中第一行
		  			  var row=$('#ttly').datagrid('getSelections');//获取选中的数据 
		              $('#lysl').html(row[0].rtcount);
		              $('#sysl').html(Number($('#dtsl').html())-Number($('#lysl').html()));
		              $('#ztsl').html(row[0].pztcount);
		              $('#dhssl').html(row[0].dhspcount);
		              $('#hssl').html(row[0].pztcount - row[0].dhspcount);
			  		   }
		  		   }
	});	
		
		$('#ttly').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});   
	     
		}

	if(index ==2){
		gridzt=$('#ttzt').datagrid({
		    title:'标签粘贴记录',
		    pageSize:25,
		    pageList:[15,25,30,35,40],
		    fitColumns: true,
		    fit:false, 
	        url:'/tcweb/twocode/twocode96333ztlist',
		    queryParams:{},
		    columns:[[   
		  	         {field:'registNumber',align:'center',halign:'center',title:'二维码编号',width:$(this).width() * 0.15},
	  	             {field:'pcompanyName',align:'center',halign:'center',title:'标签粘贴公司',width:$(this).width() * 0.15},
	  	             {field:'puserName',align:'center',halign:'center',title:'标签粘贴人',width:$(this).width() * 0.15},
		  	         {field:'ptime',align:'center',halign:'center',title:'标签粘贴时间',width:$(this).width() * 0.25},
		  	         {field:'subtime',align:'center',halign:'center',title:'上传时间',width:$(this).width() * 0.25},
		  	         {field:'psate',align:'center',halign:'center',title:'状态',width:$(this).width() * 0.10,formatter: function(value,rec,index) {
	 	                 if(value == 1)
	 	                     return "有效";
	 	                 else if(value == 2)
	 	                	return "无效";
	 	                 else
	 	                     return "审核中";
	 			         }},
		  	         {field:'pbeizhu',align:'center',halign:'center',title:'备注',width:$(this).width() * 0.10}
		  	        ]],
		  	 pagination:true,
		  	 singleSelect:true,
		  	 striped:true,
		  	 onLoadSuccess: function(data) {  
		  		   var rows=$('#ttzt').datagrid("getRows");     
		  		   if (rows.length > 0) {           
		  			  $('#ttzt').datagrid('selectRow',0);//grid加载完成后自动选中第一行
		  			  var row=$('#ttzt').datagrid('getSelections');//获取选中的数据 
		              $('#ztsl').html(row[0].pcount);
		              $('#dhssl').html(row[0].dhspcount);
		              $('#hssl').html(row[0].pcount - row[0].dhspcount);
			  		   }
		  		   }
		  		 
	});	
		
		$('#ttzt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});   
	     
		}
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
  padding-left: 2px;
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
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">  
     <table style="width:100%">
       <tr>
       <td align="center"><a class="btn btn-large btn-primary" href="#"><i class="icon-Inbox icon-2x pull-left"></i>标牌<br>数量<br><span style="font-size: 20px;" id="dtsl"></span></a></td>
       <td align="center"><a class="btn btn-large btn-default" href="#"><i class="icon-user icon-2x pull-left"></i>领用<br>数量<br><span style="font-size: 20px;" id="lysl">0</span></a></td>
       <td align="center"><a class="btn btn-large btn-info" href="#"><i class="icon-columns icon-2x pull-left"></i>剩余<br>数量<br><span style="font-size: 20px;" id="sysl"></span></a></td>
       <td align="center"><a class="btn btn-large btn-danger" href="#"><i class="icon-paste icon-2x pull-left"></i>粘贴<br>数量<br><span style="font-size: 20px;" id="ztsl">0</span></a></td>
        <td align="center"><a class="btn btn-large btn-success" href="#"><i class="icon-ok icon-2x pull-left"></i>核实<br>数量<br><span style="font-size: 20px;" id="hssl">0</span></a></td>
       <td align="center"><a class="btn btn-large btn-warning" href="#"><i class="icon-check icon-2x pull-left"></i>待核实<br>数量<br><span style="font-size: 20px;" id="dhssl">0</span></a></td>
      
       </tr>
        </table>
</div>   

 <div region="center">
  <div class="easyui-tabs" style="width:100%;height:250px" fit="true"  id="etab">
        <div title="入库" style="padding:5px" style="height:100%">
             <div>  
               <table id="tt"></table>
            </div>  
        </div>
        
        
        <div title="领用" style="padding:5px"  style="height:100%">
         <div class="easyui-layout" data-options="fit:true">
          
            <div data-options="region:'north'" style="height:50px;text-align:center;border:0px;" >
                 <center> 
                 <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;"> 
                 <tr>
                   <td nowrap align="right">维保单位：</td>
                   <td> 
                      <input id="ywCompanyId2"   placeholder="输入至少两个关键字从下拉列表中选择" style="height:34px;width:250px;"></input>
                      <input type ="hidden" id="ywCompanyId"></input>
                   </td>
                   <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                   <td colspan="3" nowrap>
	                   <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
                       <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>				
                   </td>
                   </tr>
                   </table> 
                   </center>     
            </div>
            
            <div data-options="region:'center'" style="overflow-x:auto;">
               <div>  
               <table id="ttly"></table>
               </div> 
            </div>
            
        </div>
        </div>
        
        <div title="粘贴">
            <div class="easyui-layout" data-options="fit:true">
          
            <div data-options="region:'north'" style="height:50px;text-align:center;border:0px;" >
                 <center> 
                 <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;"> 
                 <tr>
                   <td nowrap align="right">维保单位：</td>
                   <td> 
                      <input id="ywCompanyId4"   placeholder="输入至少两个关键字从下拉列表中选择" style="height:34px;width:250px;"></input>
                      <input type ="hidden" id="ywCompanyId3"></input>
                   </td>
                   <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                   <td colspan="3" nowrap>
	                   <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="queryZhanTie()" style="width:100px;color:#3399FF;">查询</a>
                       <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearZhanTieQuery()" style="width:100px;">清空</a>				
                   </td>
                   </tr>
                   </table> 
                   </center>     
            </div>
            
            <div data-options="region:'center'" style="overflow-x:auto;">
               <div>  
               <table id="ttzt"></table>
               </div> 
            </div>
            
        </div>
        </div>
        
        <div title="核实"></div>
  </div>
</div>

<div id="windowform" title="标签入库" style="width:450px;height:250px;"> 
  <div style="padding:20px 20px 40px 10px;">   
  <form method="post" id="myform"> 
   <table>
    <tr> 
      <td width="150" align="center" style="background-color:#F5F5F5;" nowrap>标签数量：</td> 
      <td nowrap>
      <input id="pcount" name="pcount"  class="form_input"></input>
      </td>
      <td width="150"></td>
     </tr>
     <tr> 
      <td width="150" align="center" style="background-color:#F5F5F5;" nowrap>备注：</td> 
      <td colspan="3">
      <input id="pbeizhu" name="pbeizhu"  class="form_input"></input>
      </td>
     </tr>
   <tr></tr>
   <tr></tr>
   <tr></tr>
   <tr></tr>
   <tr><td style="height:20px;"></td></tr>
   <tr>
    <td align="center" colspan="4">
      <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-save fa-lg" onclick="saveTwoCode96333Info()" style="width:100px;color:#3399FF;">保存</a>
      
    </td>
    </tr> 
  </table> 
  </form>
  </div>
  </div>
  
  <div id="windowform2" title="标签领用" style="width:450px;height:330px;"> 
   <form method="post" id="myform2">
    <table style="width:100%">
  <tr>
     <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用公司：</td> 
      <td> 
      <input id="ywCompanyIdinfo" style="height:34px;" placeholder="输入至少两个关键字从下拉列表中选择" class="form_input">
      <input type ="hidden" id="ywCompanyIdinfo2" name="ywCompanyIdinfo2">
      </td>
  </tr>
  <tr>
    <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用人姓名：</td> 
    <td nowrap><input id="ruserName" name="ruserName"  class="form_input"></input></td>
  </tr>

   <tr>
    <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用日期：</td> 
    <td nowrap><input id="rtime"  type="text" class="easyui-datebox" name="rtime" style="width:186px;height:34px;"></input></td>
  </tr>
   <tr>
    <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用数量：</td> 
    <td nowrap><input id="rcount" name="rcount"  class="form_input"></input></td>
  </tr>
  <tr> 
      <td width="150" align="center" style="background-color:#F5F5F5;" nowrap>领用备注：</td> 
      <td colspan="3">
      <input id="rbeizhu" name="rbeizhu"  class="form_input"></input>
      </td>
     </tr>
  <tr>
  <td style="height:20px;"></td>
  </tr>
   <tr>
    <td align="center" colspan="4">
      <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-save fa-lg" onclick="saveTwoCode96333Info2()" style="width:100px;color:#3399FF;">保存</a>  
    </td>
    </tr> 
   </table>
   </form>
  </div>
  

  
   <div id="failure-window" title="领用结果" style="width:800px;height:500px;">
      <div class="easyui-layout" data-options="fit:true"> 
         <div data-options="region:'north'" style="height:100px;text-align:center;" > 
            <table style="width:100%">
            <tr>
            </tr>
             <tr>
            </tr>
             <tr>
                <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用成功：</td>
                <td nowrap><input id="lysuccess" name="lysuccess"  class="form_input" readonly="readonly" style="border:none;outline:medium;"></input></td>
                <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用失败：</td>
                <td nowrap><input id="lyfailuer" name="lyfailuer"  class="form_input" readonly="readonly" style="border:none;outline:medium;"></input></td>
             </tr>
            </table>
         </div>
    
         <div data-options="region:'center'" style="overflow-x:auto;">
          <table id="lyflt"></table> 
         </div> 
   </div>
   </div>
   
  <div id="windowform3" title="标签改领" style="width:450px;height:380px;"> 
  <div class="easyui-layout" data-options="fit:true">   
  <div data-options="region:'north',split:true" style="height:370px;text-align:center;border:0px;" >   
  <form method="post" id="myform2"> 
   <table style="width:100%">
    <tr> 
      <td width="150" align="center" style="background-color:#F5F5F5;" nowrap>标签二维码编号：</td> 
      <td nowrap>
      <input id="sregistNumber4" name="sregistNumber4"  class="form_input" readonly="readonly"></input>
      </td>
     </tr>
       <tr>
     <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用人类型：</td> 
    <td> 
     <select id="lingyongType4"  name="lingyongType4" style="width:186px;height:34px;">
    <option value="0">公司</option>
    <option value="1">个人</option>  
    </select>
   </td>
   </tr>
    <tr>
     <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用人单位：</td> 
      <td> 
      <input id="ywCompanyIdinfo3" style="height:34px;" placeholder="输入至少两个关键字从下拉列表中选择" class="form_input">
      <input type ="hidden" id="ywCompanyIdinfo4">
      </td>
  </tr>
  <tr>
    <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用人姓名：</td> 
    <td nowrap><input id="userName4" name="userName4"  class="form_input"></input></td>
  </tr>
   <tr>
    <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用人账号：</td> 
    <td nowrap><span style="color: red"> (领用人类型为"个人"时必填)</span><br><input id="loginName4" name="loginName4"  class="form_input"></input></td>
  </tr>
   <tr>
    <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用日期：</td> 
    <td nowrap><input id="rtime4"  type="text" class="easyui-datebox" name="rtime4" style="width:186px;height:34px;"></input></td>
  </tr>
  <tr>
  <td style="height:20px;"></td>
  </tr>
   <tr>
    <td align="center" colspan="4">
      <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-save fa-lg" onclick="saveTwoCodeInfo4()" style="width:100px;color:#3399FF;">保存</a>  
    </td>
    </tr> 
   </table>
   </form>
   </div> 
    
  </div>
  
  </div>
</body>
</html>