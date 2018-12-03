<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css"> -->
<!--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css"> -->
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<!--<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>-->

<% 
String cityName = GlobalFunction.cityName;
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int isliulan = 0;
int role=0;
int userId=0;
if(userinfo!=null){
 role = userinfo.getRole(); 
 userId =userinfo.getId();
 isliulan = userinfo.getIsliulan();
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
		if(user != null){
		role = user.getRole();
	    userId = user.getId();
		}
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

	 $(function(){
		 
		$.ajaxSetup ({
			cache: false 
	    });

		grid=$('#jyelett').datagrid({
		 	    title:'两院校验电梯',
		 //	    fitColumns:true,
		 	    pageSize:25,
		 	    pageList:[15,25,30,35,40],
		 	    url:'/tcweb/elevator/tjyjyelevatorlist',
		 	    queryParams:{},
		 	   columns:[[ 
	                {field:'registNumber',align:'center',halign:'center',title:'电梯编号',width:80,formatter: function(value,rec,index) {
	                	<% if("1".equals(cityName)){ %>
                             return "N"+value;
                             <% } else {%>
                             return value;
                             <% }%>
		                }},
	                {field:'registCode',align:'center',halign:'center',title:'注册代码',width:180},
	                {field:'address',align:'left',halign:'center',title:'地址',width:200},
	                {field:'buildingName',align:'center',halign:'center',title:'楼盘',width:80},
	                {field:'building',align:'center',halign:'center',title:'栋',width:60},
	                {field:'unit',align:'center',halign:'center',title:'单元',width:60},
	                {field:'useNumber',align:'center',halign:'center',title:'内部编号',width:80},
	                {field:'jyjyFlag',align:'center',halign:'center',title:'状态',width:150,formatter: function(value,rec,index) { 
	      	        	if(value==2)
	      			            return "等待维保上传校验数据";
	      	        
	      	        	if(value==3)
	      	  			        return "维保校验数据已经上传";
	      	            }},
	                {field:'registCode2',align:'center',halign:'center',title:'特检院注册代码',width:180},
	                {field:'address2',align:'left',halign:'center',title:'特检院地址',width:200},
	                {field:'buildingName2',align:'center',halign:'center',title:'特检院楼盘',width:80},
	                {field:'building2',align:'center',halign:'center',title:'特检院栋',width:60},
	                {field:'unit2',align:'center',halign:'center',title:'特检院单元',width:60},
	                {field:'useNumber2',align:'center',halign:'center',title:'特检院内部编号',width:80},	
		 	             {field:'registCode3',align:'center',halign:'center',title:'维保核实注册代码',width:180},
			             {field:'address3',align:'left',halign:'center',title:'维保核实地址',width:200},
			             {field:'buildingName3',align:'center',halign:'center',title:'维保核实楼盘',width:80},
			             {field:'building3',align:'center',halign:'center',title:'维保核实栋',width:60},
			             {field:'unit3',align:'center',halign:'center',title:'维保核实单元',width:60},
			             {field:'useNumber3',align:'center',halign:'center',title:'维保核实内部编号',width:80}	
		 			 	]],
		 	    pagination:true,
		 	    singleSelect:true,
		 	    striped:true,
		 	   <% if(isliulan == 0){%>
		 	    toolbar:[
		 	            {
		 	   	        text:'通过',
		 	   	        iconCls:'icon-ok',
		 	   	        handler:function(){
		 	             var row = grid.datagrid('getSelected'); 
			 	   	      if(row){
			 	   	    	 $.messager.confirm('','确定通过',function(data){if(data){
			 	   	    		jQuery.post('/tcweb/elevator/tjyjyelevatorTongGuo',
			 	  	    	    	 {'id':row.id,'registNumber':row.registNumber,'registCode':row.registCode,'registCode2':row.registCode2,'address2':row.address2,'buildingName2':row.buildingName2,'building2':row.building2,'unit2':row.unit2,'useNumber2':row.useNumber2,'resouceFlag':row.resouceFlag},
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
			 	  	    	    			$.messager.alert('操作失败','没有通过','error');
			 	  		    	    		}
			 	  		    	       });	 
			 	   	    	}}
			 	     	       );
			 	   	    	}else{
			 	   	    		 $.messager.show({   
			 	   	    			 title:'警告',
			 	   	    			 msg:'请先选择记录行。' 
			 	   	    		 });   
			 	   		     }
		 	   	        }
		 	   	    },{
		 	   	        text:'不通过',
		 	   	        iconCls:'icon-no',
		 	   	        handler:function(){
		 	   	    	 var row = grid.datagrid('getSelected'); 
		 	   	    	 if(row){
		 	   	    	 $.messager.confirm('','确定不通过',function(data){if(data){	
		 	   	    		jQuery.post('/tcweb/elevator/tjyjyelevatorUnTongGuo',
			 	  	    	    	 {'id':row.id,'registNumber':row.registNumber,'registCode':row.registCode,'resouceFlag':row.resouceFlag},
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
			 	  	    	    			$.messager.alert('操作失败','作失败','error');
			 	  		    	    		}
			 	  		    	       });	  
		 	   	    	 }}
		 	     	       );
		 	   	    	 }else{
		 	   	    		 $.messager.show({   
		 	   	    			 title:'警告',
		 	   	    			 msg:'请先选择记录行。' 
		 	   	    		 });   
		 	   		     }
		 	   	    	 
		 	   	        }
		 	   	    }
			 		 	 ]
			 		<%}%>
		 	});	
		 	
		 	$('#jyelett').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});   
		    	  
		 });

	 </script>
	 </head>
<body class="easyui-layout">
<div region="center">    
    <div>  
       <table id="jyelett"></table>
   </div>  
</div> 

</body>
</html>