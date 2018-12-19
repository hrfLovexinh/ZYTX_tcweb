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
		var daoRuUrl = "";
		var daoRuWay = "";
$(function(){

	$.ajaxSetup ({
		cache: false
	});

	win = $('#windowform').window({minimizable:false,maximizable:false,collapsible:false,closed:true,draggable:false,modal:true }); 
	form = win.find('form');
	
	win2 = $('#windowform2').window({minimizable:false,maximizable:false,collapsible:false,closed:true,draggable:false,modal:true }); 
	win4 = $('#windowform3').window({minimizable:false,maximizable:false,collapsible:false,closed:true,draggable:false,modal:true });    
	$('#lyflt').datagrid

	win3 = $('#failure-window').window({minimizable:false,maximizable:false,collapsible:false,closed:true,draggable:false,modal:true,onClose:function(){$('#tt').datagrid('reload')} });   
	//	form2 = win2.find('form'); 
	//批量导入
	winDr = $('#car-windowDr').window({  closed:true,draggable:false,modal:true,minimizable:false,collapsible:false,maximizable:false});
	winDrResult = $('#failure-window2').window({minimizable:false,maximizable:false,collapsible:false,closed:true,draggable:false,modal:true,onClose:function(){$('#tt').datagrid('reload')} });   
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
	
	grid=$('#tt').datagrid({
	    title:'标签列表( <a class="btn" href="#" style="text-decoration: none"><i class="icon-repeat"></i>入库</a> ---> <a class="btn" href="#" style="text-decoration: none"><i class="icon-repeat"></i>领用</a>---> <a class="btn" href="#" style="text-decoration: none"><i class="icon-repeat"></i> 粘贴</a>---> <a class="btn" href="#" style="text-decoration: none"><i class="icon-repeat"></i>核实</a> )',
	    pageSize:25,
	    pageList:[15,25,30,35,40],
//	    url:'/tcweb/elevator/elevatorlist',
        url:'/tcweb/twocode/twocodelist',
	    queryParams:{},
	    columns:[[   
	  	        {field:'registNumber',align:'center',title:'标签编号',width:$(this).width() * 0.09},
	  	        {field:'infoState',align:'center',halign:'center',title:'标签状态',width:$(this).width() * 0.07,formatter: function(value,rec,index) {
	                if(value == 0)
	                    return "入库";
	                else if(value == 1)
	  	                return "领用";
			         },styler: function (value, row, index) {
			        	 if(value==0){
			                 return 'background-color:#e6e6e6;';
			        	  }
			        	 else if(value == 1){
			        		 return 'background-color:#95B8E7;';
				         }

			          }},
	  	        {field:'ptime',align:'center',halign:'center',title:'标签入库时间',width:$(this).width() * 0.12},
	  	        {field:'puserName',align:'center',halign:'center',title:'标签入库人',width:$(this).width() * 0.11},
	  	        {field:'ywCompanyName',align:'center',halign:'center',title:'标签领用人公司',width:$(this).width() * 0.11},
	  	        {field:'userName',align:'center',halign:'center',title:'标签领用人',width:$(this).width() * 0.08},
	  	        {field:'rtime',align:'center',halign:'center',title:'标签领用时间',width:$(this).width() * 0.12},
	  	        {field:'useNumber',align:'left',halign:'center',title:'标签粘贴时间',width:$(this).width() * 0.08},
	  	        {field:'useNumber',align:'left',halign:'center',title:'标签粘贴人',width:$(this).width() * 0.11}
	  	        ]],
	  	 pagination:true,
	  	 singleSelect:true,
	  	 striped:true,
	  	 toolbar:[	
	  	       {
	  		        text:'入库',
	  		        iconCls:'icon-add',
	  		        handler:function(){
	  		    	win.window('open');  
	  		    //	form.form('clear');
	  		    	form.url ='/tcweb/twocode/add';	
	  		        }
	  		    },{
	  		 		text:'标签入库excel模板',
	  		 		iconCls:'icon-redo',
	  		 		handler: function() {
	  		 			window.location.href="/tcweb/twocode/downLoad?way=ruku";
	  		 		}
	  	 		},{
	  		    	text:'标签入库导入',
	  		    	iconCls:'icon-redo',
	  		    	handler: function() {
	  		    		//$("#exportId").attr("onclick","rukuExport()");
						//$('#exportId').unbind("click");
	  		    		//$("#exportId").bind();
	  		    		//$("#exportId").bind('click',rukuExport);
	  		    		//$("#exportId").bind('change',rukuExport);
	  		    		//$("#exportId").change(rukuExport);
	  		    		daoRuUrl = "/tcweb/twocode/rukuExport";
	  		    		daoRuWay = "ruku";
	  		    		//winDr.window("open");
	  		    		$("#daoruId").val("");
	  		    		$("#daoruId").click();
	  		    	}
	  		    },{
	  		        text:'删除',
	  		        iconCls:'icon-cut',
	  		        handler:function(){
	  		    	 var row = grid.datagrid('getSelected'); 
	  		    	 if(row){
	  		    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
	  		    	 jQuery.post('/tcweb/twocode/delete',
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
	  	  	       );}
	  		    	else{
	  		    		 $.messager.show({   
	  		    			 title:'警告',
	  		    			 msg:'请先选择记录行。' 
	  		    		 });   
	  			     }
	  		    }}
	  		    ,'-',
	  		   {
	  		        text:'领用',
	  		        iconCls:'icon-lingyong',
	  		        handler:function(){
	  		    	win2.window('open');  
	  		 //   	form2.url ='/tcweb/twocode/lingyong';	
	  		        }
	  		    },{
	  		 		text:'标签领用excel模板',
	  		 		iconCls:'icon-redo',
	  		 		handler: function() {
	  		 			window.location.href="/tcweb/twocode/downLoad?way=chuku";
	  		 		}
	  	 		},{
	  		    	text:'标签领用导入',
	  		    	iconCls:'icon-redo',
	  		    	handler: function() {
	  		    		//$('#exportId').unbind("click");
	  		    		//$("#exportId").click(chukuExport);
	  		    		//$("#exportId").on('click',chukuExport);
	  		    		daoRuUrl = "/tcweb/twocode/chukuExport";
	  		    		daoRuWay = "chuku";
	  		    		//winDr.window("open");
	  		    		$("#daoruId").val("");
	  		    		$("#daoruId").click();
	  		    	}
	  		    }
	  		    ]
});	
	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});   

	$('#failDatatt').datagrid({
		    title:'已经被领用不能再领用标签',
		    fit:true,
	        url:'',
	        columns:[[   
	  	      {field:'registNumber',align:'center',title:'标签编号',width:100},
	  	      {field:'infoState',align:'center',halign:'center',title:'标签状态',width:$(this).width() * 0.07,formatter: function(value,rec,index) {
	  	    	if(value == 0)
                    return "入库";
                else if(value == 1)
  	                return "领用";
                else if(value == 2)
  	                return "粘贴";
                else if(value == 3)
  	                return "有效";
                else if(value == 4)
  	                return "无效";
			         },styler: function (value, row, index) {
			        	 if(value==0){
			                 return 'background-color:#e6e6e6;';
			        	  }
			        	 else if(value == 1){
			        		 return 'background-color:#95B8E7;';
				         }
			        	 else if(value == 2){
			        		 return 'background-color:#fff3f3;';
				         }
			        	 else if(value == 3){
			        		 return 'background-color:#bbb;';
				         }
			        	 else if(value == 4){
			        		 return 'background-color:#ffa8a8;';
				         }

			          }},
	  	        {field:'ywCompanyName',align:'center',halign:'center',title:'标签领用人公司',width:$(this).width() * 0.11},
	  	        {field:'userName',align:'center',halign:'center',title:'标签领用人',width:80},
	  	        {field:'rtime',align:'center',halign:'center',title:'标签领用时间',width:140},
	  	        {field:'gailing',align:'center',title:'改领',width:80,
	  	        	formatter: function(value,rec,index) { 
		        	var registNumber = ''+rec.registNumber;
		        	var infoState = rec.infoState;
		        	if(infoState == 1)
	  	  			return '<a href="#" onclick="altertwocode('+'\''+registNumber+'\''+','+infoState+')"><i class="fa fa-exchange" aria-hidden="true" style="color:#61B5CF;"></i></a>';
		        	else
			  	  		  return '';   
	  	            }}
	  	        ]],
	  	 		singleSelect:true,
	  	 		striped:true
	  });
}
);

function saveTwoCodeInfo(){
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){
		var sregistNumber=$('#sregistNumber').attr("value"); 
		var eregistNumber=$('#eregistNumber').attr("value");     
		if(!check(sregistNumber)){   
			$.messager.alert('操作失败', '电梯号段起点必须为6位整数', 'error');
			return false;
		}

		if(!check(eregistNumber)){  
			$.messager.alert('操作失败', '电梯号段终点必须为6位整数', 'error');
			return false;
		}

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
		testDid="";
		grid.datagrid('reload');
		win.window('close');
	} else {
		$.messager.alert('操作失败', '添加标签', 'error');
	}
}
	});
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
	var qsregistNumber=$('#qsregistNumber').attr("value");
	var qeregistNumber=$('#qeregistNumber').attr("value");

    if(qsregistNumber != ''){
	if(!check(qsregistNumber)){   
		$.messager.alert('操作失败', '电梯号段起点必须为6位整数', 'error');
		return;
	}
    }

    if(qeregistNumber != ''){
	if(!check(qeregistNumber)){  
		$.messager.alert('操作失败', '电梯号段终点必须为6位整数', 'error');
		return;
	}
    }
	
	$('#tt').datagrid("options").url='/tcweb/twocode/query';
	$('#tt').datagrid("options").queryParams={'sregistNumber':qsregistNumber,'eregistNumber':qeregistNumber};
		
	$('#tt').datagrid('reload');
	}

function clearQuery(){
	$('#qsregistNumber').attr("value","");
	$('#qeregistNumber').attr("value","");
}

function saveTwoCodeInfo2(){
   var val = $('input[name="lingyongway"]:checked').val();
  // alert(val);
   if(val == 0){
    var sregistNumber2 =$('#sregistNumber2').attr("value");
    var eregistNumber2 =$('#eregistNumber2').attr("value");
    var lingyongType = $('#lingyongType option:selected').val();
  
    if(!check(sregistNumber2)){   
		$.messager.alert('操作失败', '电梯号段起点必须为6位整数', 'error');
		return false;
	}

	if(!check(eregistNumber2)){  
		$.messager.alert('操作失败', '电梯号段终点必须为6位整数', 'error');
		return false;
	}

	  var userName = $('#userName').attr("value");  
	   if(userName == ""){
		   $.messager.alert('操作失败', '请填写领用人姓名', 'error');
          return;  
	   }

	var rtime =$('#rtime').datebox("getValue");  
    

	var ywCompanyId =0;
	if(lingyongType == 0){
	   ywCompanyId=$('#ywCompanyIdinfo2').attr("value");  
	   if(!ywCompanyId){
		   $.messager.alert('操作失败', '请选择领用标签的运维公司', 'error');
            return;
		   }
	 }

     if(rtime == ""){
    	 $.messager.alert('操作失败', '请选择领用日期', 'error');
    	 return;

         }

     var loginName = $('#loginName').attr("value");
     if(lingyongType == 1 && loginName == ""){
    	 $.messager.alert('操作失败', '领用人账号必填', 'error');
    	 return;
         }
         
 	/*
	jQuery.post('/tcweb/twocode/lingyong',
		   	 {'sregistNumber':sregistNumber2,'eregistNumber':eregistNumber2,'ywCompanyId':ywCompanyId,'userName':userName,'rtime':rtime},
		   	 function(data){
		    // 	eval("data="+"'"+data+"'");  
		   	if("success"==data){
		   		 $.messager.show({   
				 title:'提示信息',
				 timeout:1000,
				 msg:'操作成功，谢谢。' 
			 });  
		   		 win2.window('close');  	
		         grid.datagrid('reload');
		   		}
		   		else{
		   			$.messager.alert('操作失败','操作失败','error');
			    		}   
			       });     */  
	      win2.window('close'); 
	 //     grid.datagrid('reload');
		  win3.window('open'); 
		  grid2=$('#lyflt').datagrid({
			    title:'已经被领用不能再领用标签',
			    fit:true,
		        url:'/tcweb/twocode/lingyong',
			    queryParams:{'sregistNumber':sregistNumber2,'eregistNumber':eregistNumber2,'ywCompanyId':ywCompanyId,'userName':userName,'rtime':rtime,'loginName':loginName},
		        columns:[[   
		  	      {field:'registNumber',align:'center',title:'标签编号',width:100},
		  	      {field:'infoState',align:'center',halign:'center',title:'标签状态',width:$(this).width() * 0.07,formatter: function(value,rec,index) {
		                if(value == 0)
		                    return "入库";
		                else if(value == 1)
		  	                return "领用";
				         },styler: function (value, row, index) {
				        	 if(value==0){
				                 return 'background-color:#e6e6e6;';
				        	  }
				        	 else if(value == 1){
				        		 return 'background-color:#95B8E7;';
					         }

				          }},
		  	        {field:'ywCompanyName',align:'center',halign:'center',title:'标签领用人公司',width:$(this).width() * 0.11},
		  	        {field:'userName',align:'center',halign:'center',title:'标签领用人',width:80},
		  	        {field:'rtime',align:'center',halign:'center',title:'标签领用时间',width:140},
		  	        {field:'gailing',align:'center',title:'改领',width:80,
		  	        	formatter: function(value,rec,index) { 
			        	var registNumber = ''+rec.registNumber;
			        	var infoState = rec.infoState;
		  	  			return '<a href="#" onclick="altertwocode('+'\''+registNumber+'\''+','+infoState+')"><i class="fa fa-exchange" aria-hidden="true" style="color:#61B5CF;"></i></a>';
		  	        	   
		  	            }}
		  	        ]],
		  	 singleSelect:true,
		  	 striped:true,
		  	 onLoadSuccess:function() {       
			         $('#lysuccess').attr("value",parseInt(eregistNumber2)-parseInt(sregistNumber2)-$("#lyflt").datagrid('getRows').length+1);           
			         $('#lyfailuer').attr("value",$("#lyflt").datagrid('getRows').length);  
			        }	 
		  });	
	
          
	}
	
   else{
	   var sregistNumber3 =$('#sregistNumber3').attr("value");	   
	   var rtime =$('#rtime').datebox("getValue");  
	   var lingyongType = $('#lingyongType option:selected').val();
	   if(!check(sregistNumber3)){  
			$.messager.alert('操作失败', '标签二维码编号必须为6位整数', 'error');
			return false;
		}
		
	    var ywCompanyId =0;
		if(lingyongType == 0){
		   ywCompanyId=$('#ywCompanyIdinfo2').attr("value");  
		   if(!ywCompanyId){
			   $.messager.alert('操作失败', '请选择领用标签的运维公司', 'error');
	            return;
			   }
		 }
		 
	   if(rtime == ""){
	    	 $.messager.alert('操作失败', '请选择领用日期', 'error');
	    	 return;

	         }
	   var userName = $('#userName').attr("value");  
	   if(userName == ""){
		   $.messager.alert('操作失败', '请填写领用人姓名', 'error');
          return;  
	   }

	   var loginName = $('#loginName').attr("value");
	     if(lingyongType == 1 && loginName == ""){
	    	 $.messager.alert('操作失败', '领用人账号必填', 'error');
	    	 return;
	         }
	         

	      /*
	   jQuery.post('/tcweb/twocode/dlingyong',
			   	 {'sregistNumber':sregistNumber3,'ywCompanyId':ywCompanyId,'userName':userName,'rtime':rtime},
			   	 function(data){
			   		eval("data="+"'"+data+"'");  
			   		if("success"==data){
			   		 $.messager.show({   
					 title:'提示信息',
					 timeout:1000,
					 msg:'操作成功，谢谢。' 
				 });  
			   		 win2.window('close');  	
			         grid.datagrid('reload');
			   		}
			   		else{
			   			$.messager.alert('操作失败','操作失败','error');
				    		}
				       });   */
	      win2.window('close'); 
	 //     grid.datagrid('reload');
		  win3.window('open'); 
		  grid2=$('#lyflt').datagrid({
			    title:'已经被领用标签',
		        url:'/tcweb/twocode/dlingyong',
			    queryParams:{'sregistNumber':sregistNumber3,'ywCompanyId':ywCompanyId,'userName':userName,'rtime':rtime,'loginName':loginName},
		        columns:[[   
		  	      {field:'registNumber',align:'center',title:'标签编号',width:100},
		  	      {field:'infoState',align:'center',halign:'center',title:'标签状态',width:$(this).width() * 0.07,formatter: function(value,rec,index) {
		                if(value == 0)
		                    return "入库";
		                else if(value == 1)
		  	                return "领用";
				         },styler: function (value, row, index) {
				        	 if(value==0){
				                 return 'background-color:#e6e6e6;';
				        	  }
				        	 else if(value == 1){
				        		 return 'background-color:#95B8E7;';
					         }

				          }},
		  	        {field:'ywCompanyName',align:'center',halign:'center',title:'标签领用人公司',width:$(this).width() * 0.11},
		  	        {field:'userName',align:'center',halign:'center',title:'标签领用人',width:80},
		  	        {field:'rtime',align:'center',halign:'center',title:'标签领用时间',width:140},
		  	        {field:'gailing',align:'center',title:'改领',width:80,
			        	formatter: function(value,rec,index) { 
			        	var registNumber = ''+rec.registNumber;
		  	        	var infoState = rec.infoState;
		  	  			  return '<a href="#" onclick="altertwocode('+'\''+registNumber+'\''+','+infoState+')"><i class="fa fa-exchange" aria-hidden="true" style="color:#61B5CF;"></i></a>';
		  	        	   
		  	            }}
		  	        ]],
		  	 singleSelect:true,
		  	 striped:true,
		  	 onLoadSuccess:function() {       
		           $('#lysuccess').attr("value",1-$("#lyflt").datagrid('getRows').length);           
		           $('#lyfailuer').attr("value",$("#lyflt").datagrid('getRows').length);  
		        }	   
		  });

		  

	   }
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

function rukuExport() {
	//winDr.window("close");
	//alert("入库导入");
	$("#drFrom").form('submit',{
		url: daoRuUrl,
		onSubmit: function(){ 
			   var filename = $("#daoruId").val();
			   var ext = filename.substring(filename.lastIndexOf("\\") + 1);
			   //alert(ext);
			   //var ext = filename.substring(filename.lastIndexOf(".") + 1);
			   //alert(ext);
			   //if(ext == "xlsx" || ext == 'xls' ){
			   if(ext == "标签入库信息.xlsx"){
				    $.messager.progress({
				    	text:'正在导入...'
				    });   
				    //alert("tongduo");
			   } else {
				    $.messager.alert('Warning','上传文件有误,请下载相应模板!','warning'); 
			   		return false;
			   }
			   
		   }, 
		   success:function(data){ 
			   $.messager.progress('close');  
			   $.messager.alert("信息",data,"info"); 

			   } 

	});
}

function chukuExport() {
	//alert("领用导入");
	//winDr.window("close");
	$("#drFrom").form('submit',{
		url: daoRuUrl,
		onSubmit: function(){ 
			   var filename = $("#daoruId").val();
			   var ext = filename.substring(filename.lastIndexOf("\\") + 1);
			   //alert(ext);
			   //var ext = filename.substring(filename.lastIndexOf(".") + 1);
			   //alert(ext);
			   //if(ext == "xlsx" || ext == 'xls' ){
			   if(ext == "标签领用信息.xlsx"){
				    $.messager.progress({
				    	text:'正在导入...'
				    });   
				    //alert("tongduo");
			   } else {
				    $.messager.alert('Warning','上传文件有误,请下载相应模板!','warning'); 
			   		return false;
			   }
			   
		   }, 
		   success:function(data){ 
			   $.messager.progress('close');  
			   var dataObject = JSON.parse(data);
			   if(dataObject.success == "failer") {
				   $.messager.alert("error","导入异常,请与管理员联系!","error");
			   } else {
				   $("#exportSuccess").val(dataObject.exportSuccess);
				   $("#exportFailer").val(dataObject.exportFailer);
				   //$("#registNumberNoExistCount").html(dataObject.registNumberNoExistCount);
				   $("#registNumberNoExist").val(dataObject.registNumberNoExist);
				   $("#registNumberNoExist").attr('title',dataObject.registNumberNoExist);
				   //$("#companyNoExistCount").html(dataObject.companyNoExistCount);
				   $("#companyNoExist").val(dataObject.companyNoExist);
				   $("#companyNoExist").attr('title',dataObject.companyNoExist);
				   //$("#incompleteCount").html(dataObject.incompleteCount);
				   $("#incomplete").val(dataObject.incomplete);
				   $("#incomplete").attr('title',dataObject.incomplete);
				   $('#failDatatt').datagrid('loadData',dataObject.failData);
				   winDrResult.window('open');
			   }

			} 

	});
}

function submitFile() {
	//alert("上传文件");
	//winDr.window("open");
	var filename = $("#daoruId").val();
	//alert(filename);
	if(filename != "" && filename != null) {
		//$("#exportId").click();
		   //alert(ext);
		   //var ext = filename.substring(filename.lastIndexOf(".") + 1);
		   //alert(ext);
		   //if(ext == "xlsx" || ext == 'xls' ){
		//alert(ext);
		   if(daoRuWay == "ruku"){
			    rukuExport();
			    //alert("tongduo");
		   } else if(daoRuWay == "chuku") {
			   chukuExport();
			} else {
			    $.messager.alert('Warning','未知错误,请与管理员联系!','warning'); 
		   }
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
 <fieldset id="addDiv" style="margin:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
     <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%"> 
     <tr>
     <td align="right" nowrap>电梯号段起点：</td> 
      <td nowrap><input id="qsregistNumber" name="qsregistNumber"  class="easyui-validatebox"></input></td>
     <td align="right" nowrap>电梯号段终点：</td> 
      <td nowrap><input id="qeregistNumber" name="qeregistNumber"  class="easyui-validatebox"></input></td>
      <td>&nbsp;&nbsp;&nbsp;</td>
      <td colspan="3" nowrap>
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
           <table id="tt"></table>
         </div>  
        
    </div>  
</div>

<div id="windowform" title="标签入库" style="width:450px;height:250px;"> 
  <div style="padding:20px 20px 40px 10px;">   
  <form method="post" id="myform"> 
   <table>
    <tr> 
      <td width="150" align="center" style="background-color:#F5F5F5;" nowrap>电梯号段起点：</td> 
      <td nowrap>
      <input id="sregistNumber" name="sregistNumber"  class="form_input"></input>
      </td>
     </tr>
    <tr>
     <td width="150" align="center" style="background-color:#F5F5F5;"  nowrap>电梯号段终点：</td> 
     <td nowrap><input id="eregistNumber" name="eregistNumber"  class="form_input"></input></td>
  </tr>
   <tr></tr>
   <tr></tr>
   <tr></tr>
   <tr></tr>
   <tr></tr>
   <tr>
    <td align="center" colspan="4">
      <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-save fa-lg" onclick="saveTwoCodeInfo()" style="width:100px;color:#3399FF;">保存</a>
      
    </td>
    </tr> 
  </table> 
  </form>
  </div>
  </div>
  
  <div id="windowform2" title="标签领用" style="width:450px;height:680px;"> 
  <div class="easyui-layout" data-options="fit:true">   
  <div data-options="region:'north',split:true" style="height:350px;text-align:center;border:0px;" >   
  <form method="post" id="myform2"> 
   <table style="width:100%">
   <tr>
    <td colspan="2" align="left" style="background-color:#0081c2;height:34px;" nowrap>领用方式</td>
   </tr>
   <tr>
   <td align="center"  colspan="2" style="height:34px;">
   <input type="radio" checked name="lingyongway" value="0"/><label>号码段领取</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <input type="radio" name="lingyongway" value="1"/><label>单个标签领取</label>
   </td>
   </tr>
    <tr>
   <td style="height:20px;"></td>
   </tr>
    <tr>
    <td colspan="2" align="left" style="background-color:#0081c2;height:34px;" nowrap>号码段领取</td>
   </tr>
    <tr> 
      <td width="150" align="center" style="background-color:#F5F5F5;" nowrap>电梯号段起点：</td> 
      <td nowrap>
      <input id="sregistNumber2" name="sregistNumber2"  class="form_input"></input>
      </td>
     </tr>
    <tr>
     <td width="150" align="center" style="background-color:#F5F5F5;"  nowrap>电梯号段终点：</td> 
     <td nowrap><input id="eregistNumber2" name="eregistNumber2"  class="form_input"></input></td>
    </tr>
   <tr>
   <td style="height:20px;"></td>
   </tr>
    <tr>
    <td colspan="2" align="left" style="background-color:#0081c2;height:34px;" nowrap>单个标签领取</td>
   </tr>
    <tr> 
      <td width="150" align="center" style="background-color:#F5F5F5;" nowrap>标签二维码编号：</td> 
      <td nowrap>
      <input id="sregistNumber3" name="sregistNumber3"  class="form_input"></input>
      </td>
     </tr>
  </table> 
  </form>
  </div>
  
   <div data-options="region:'center'" style="overflow-x:auto;">
    <table style="width:100%">
   <tr>
     <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用人类型：</td> 
    <td> 
     <select id="lingyongType"  name="lingyongType" style="width:186px;height:34px;">
    <option value="0">公司</option>
    <option value="1">个人</option>  
    </select>
   </td>
   </tr>
  <tr>
     <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用人单位：</td> 
      <td> 
      <input id="ywCompanyIdinfo" style="height:34px;" placeholder="输入至少两个关键字从下拉列表中选择" class="form_input">
      <input type ="hidden" id="ywCompanyIdinfo2">
      </td>
  </tr>
  <tr>
    <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用人姓名：</td> 
    <td nowrap><input id="userName" name="userName"  class="form_input"></input></td>
  </tr>
   <tr>
    <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用人账号：</td> 
    <td nowrap><span style="color: red"> (领用人类型为"个人"时必填)</span><br><input id="loginName" name="loginName"  class="form_input"></input></td>
  </tr>
   <tr>
    <td width="150" align="center" style="background-color:#F5F5F5;height:34px;"  nowrap>领用日期：</td> 
    <td nowrap><input id="rtime"  type="text" class="easyui-datebox" name="rtime" style="width:186px;height:34px;"></input></td>
  </tr>
  <tr>
  <td style="height:20px;"></td>
  </tr>
   <tr>
    <td align="center" colspan="4">
      <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-save fa-lg" onclick="saveTwoCodeInfo2()" style="width:100px;color:#3399FF;">保存</a>  
    </td>
    </tr> 
   </table>
   </div>  
  </div>
  
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
  
   <!--  批量导入 -->
  <div id="car-windowDr" title="详细信息" style="width:300px;height:200px;">
   		<form id='drFrom' enctype="multipart/form-data" action="" method="post">
   			<table align="center" id="drTable">
   				<tr>
   					<td>
   				
	   					<input type="file" name="file" id="daoruId" onchange="submitFile()"/>
   					</td>
   				</tr>
   				<tr align="center">
   					<td>
				   		<input id="exportId" type="button" value="导入"/>
   					</td>
   				</tr>
   			</table>
   		</form>
   </div>
   
   <!-- 领用导入返回结果 -->
   <div id="failure-window2" title="领用导入结果" style="width:800px;height:500px;">
      <div class="easyui-layout" data-options="fit:true"> 
         <div data-options="region:'north'" style="height:50px;text-align:center;" > 
            <table style="width:100%;height:100%;">
             <tr>
                <td width="150" align="center" style="background-color:#67C23A;height:34px;"  nowrap>领用成功：</td>
                <td nowrap><input id="exportSuccess" name="exportSuccess"  class="form_input" readonly="readonly" style="border:none;outline:medium;height:100%;"></input></td>
                <td width="150" align="center" style="background-color:#F56C6C;height:34px;"  nowrap>领用失败：</td>
                <td nowrap><input id="exportFailer" name="exportFailer"  class="form_input" readonly="readonly" style="border:none;outline:medium;height:100%;"></input></td>
             </tr>
            </table>
         </div>
         <div data-options="region:'center'" style="height:400px;text-align:center;">
          <table style="width:100%;height:20%">
             <tr>
                <td width="150" align="center" style="background-color:#F5F5F5;height:10px;"  nowrap>标签没有入库的标签编号<!-- :<span id="registNumberNoExistCount"></span> --></td>
                <td nowrap><!-- 标签编号: --><input id="registNumberNoExist" name="registNumberNoExist" type="text"  class="form_input" readonly="readonly" style="border:none;outline:medium;height:100%;"></input></td>
             </tr>
             <tr>
                <td width="150" align="center" style="background-color:#F5F5F5;height:10px;"  nowrap>领用人单位有误的标签编号<!-- :<span id="companyNoExistCount"></span> --></td>
                <td nowrap><!-- 标签编号: --><input id="companyNoExist" name="companyNoExist" type="text"  class="form_input" readonly="readonly" style="border:none;outline:medium;height:100%;"></input></td>
             </tr>
             <tr>
             	<td width="150" align="center" style="background-color:#F5F5F5;height:10px;"  nowrap>导入数据不全或者有误的标签<!-- :<span id="incompleteCount"></span> --></td>
                <td nowrap><!-- 标签编号: --><input id="incomplete" name="incomplete" title="" type="text"  class="form_input" readonly="readonly" style="border:none;outline:medium;height:100%;"></input></td>
             </tr>
            </table> 
            <div id="main-center" style="float:left;margin:0px;overflow:auto;width:100%;height:75%" class="column">      
	       		<table id="failDatatt"></table>    
	    	</div> 
         </div> 
    
         <!-- <div data-options="region:'south'" style="overflow-x:auto;height:500px;">
          	<div id="main-center" style="float:left;margin:0px;overflow:auto;width:50%" class="column">      
	       		<table id="failDatatt"></table>    
	    	</div> 
         </div>  -->
   </div>
   </div>
</body>
</html>