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
int userId=0;
if(userinfo!=null){
 role = userinfo.getRole(); 
 userId =userinfo.getId();
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
	    userId = user.getId();
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

			 grid=$('#elett').datagrid({
			 	    title:'两院新增电梯',
			 	    fitColumns:true,
			 	    pageSize:25,
			 	    pageList:[15,25,30,35,40],
			 	    url:'/tcweb/elevator/tjyaddelevatorlistByOrder',
			 	    queryParams:{},
			 	    columns:[[
		                {field:'registNumber',align:'center',halign:'center',title:'电梯编号↑↓',sortable : true,width:$(this).width() * 0.20},
		                {field:'registCode',align:'center',halign:'center',title:'登记编号↑↓',sortable : true,width:$(this).width() * 0.20},
		                {field:'address',align:'left',halign:'center',title:'地址',width:$(this).width() * 0.20},
		                {field:'registor',align:'center',halign:'center',title:'登记人员',width:$(this).width() * 0.20},
		                {field:'registDate',align:'center',halign:'center',title:'登记日期',width:$(this).width() * 0.20}
		               
			 	       	 	   ]],
			 	    pagination:true,
			 	    singleSelect:true,
			 	    striped:true
			 	    
			 	});	
			 	
			 	$('#elett').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});   
			    	  
			 });

	 function query(){  
			var registNumber=$('#registNumber').attr("value");
			var address=$('#addressinfo').attr("value");
			var registCode =$('#registCodeinfo').attr("value");
		   
		    grid.datagrid("options").url='/tcweb/elevator/tjyaddelevatorlistqueryByOrder';
		    grid.datagrid("options").queryParams={'registNumber':registNumber,'address':address,'registCode':registCode};
		
		    $('#elett').datagrid('reload');
			}

	 function clearQuery(){
			$('#registNumber').attr("value","");
			$('#addressinfo').attr("value","");
			$('#registCodeinfo').attr("value","");
		}
	 

	 </script>
	 </head>
<body class="easyui-layout" data-options="fit:true">
 <div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">  
 <fieldset id="addDiv" style="width: 100%;margin:0px"><legend>查询条件</legend>
    
     <table> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
   <td nowrap><input id="registNumber" name="registNumber" size="24" class="easyui-validatebox"></input></td>
    <td nowrap>登记编号：</td>
 <td nowrap><input id="registCodeinfo" name="registCodeinfo" size="24" class="easyui-validatebox"></input></td>
    <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="addressinfo" name="addressinfo" size="24" class="easyui-validatebox"></input></td>
   
  
 <td colspan="2">
				<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 
			<!--		<input type="submit" value="Submit Comment" /> -->	
					
</td>
					
   </tr>
   
     </table>
  
  </fieldset>
</div> 
<div region="center" style="width:100%;">
       <div id="main-center"  fit="true" border="false">  
        
          <div style="margin-top:1px;">  
       <table id="elett"></table>
   </div>  
        
    </div>  
</div> 

</body>
</html>