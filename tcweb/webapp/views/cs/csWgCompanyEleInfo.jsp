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
String userName = "";
String password = "";
String registNumber="";
String beginTime="";
int  wgCompanyId = 0;
String companyId ="";
int  role = 0; 
userName = request.getParameter("loginName");
password = request.getParameter("password");
companyId =request.getParameter("companyId");
if(!"".equals(companyId))
	wgCompanyId =Integer.parseInt(companyId);

/* registNumber =request.getParameter("registNumber");
   beginTime=request.getParameter("beginTime");
if(registNumber==null)
	registNumber ="";
if(beginTime==null)
	beginTime ="";   */
UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select t.*,te.userName from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id=te.userid where loginName= ?",new Object[] { userName });
if(user!=null){
  if(user.getPassword().equals(password)){
	  System.out.println(user.getLoginName()+"cs登陆成功");
	  role = user.getRole();
	  request.getSession().setAttribute("sessionAccount", user);   
	  Cookie uNCookie = new Cookie("userName", userName); 
	  Cookie pWCookie = new Cookie("password", password); 
	  uNCookie.setPath("/"); 
	  pWCookie.setPath("/");
	  response.addCookie(uNCookie);
	  response.addCookie(pWCookie);
  }	
  else{
	  System.out.println(user.getLoginName()+"cs密码错误");
		return; 
  }
}
else{
	System.out.println("cs用户名不存在");
	return;
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
//	 var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
//   var r = str.match(/^(((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\d):[0-5]?\d:[0-5]?\d)$/);
	 var r = str.match(/^[1-9][0-9]{3}-(0?[1-9]|1[0|1|2])-(0?[1-9]|[1|2][0-9]|3[0|1])\s(0?[1-9]|1[0-9]|2[0-3]):(0?[0-9]|[1|2|3|4|5][0-9])$/);
	 if(r==null)
	 return false; 
	/* var d= new Date(r[1], r[3]-1, r[4]); 
	 return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);*/
	 return true;
	 }

	 $(function(){
			$.ajaxSetup ({
			    cache: false 
			});

			grid=$('#tt').datagrid({
			    title:'电梯列表(红色代表停用,电梯编号处有黄色图标的记录为涉秘电梯)',
			    pageSize:25,
			    pageList:[15,25,30,35,40],
		        url:'/tcweb/elevator/cselevatorlistByCompanyId',
			    queryParams:{'companyId':<%=wgCompanyId%>},
			    columns:[[
		           {field:'registNumber',align:'left',halign:'center',title:'电梯编号',width:100,formatter: function(value,rec,index) {
		              var shemiFlag =0;
		              shemiFlag = rec.shemiFlag;
		              if(shemiFlag ==1){
		                  return value+"<img src='<%=request.getContextPath()%>/css/icons/tip.png' ";
		                  }
		              else
		                  return value;
		               }},
		           {field:'registCode',align:'left',halign:'center',title:'注册代码',width:180},
		           {field:'address',align:'left',halign:'center',title:'地址',width:160},
		           {field:'buildingName',align:'left',halign:'center',title:'楼盘名称',width:100},
			        {field:'area',align:'center',title:'行政区划',width:60},
			        {field:'useNumber',align:'left',halign:'center',title:'单位内部编号'},
			        {field:'nextInspectDate',align:'center',title:'下次检验日期',formatter: function(value,rec,index) {
		                if(value){
		                    return value;
		                }
		                else{
		                      if(rec.inspectDate){ 
		                    	  var d = new Date(rec.inspectDate);   
		                    	  d.setYear(Number(d.getFullYear())+1);
		                //    	  var nd=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();//新日期
		                          return format(d,'yyyy-MM-dd');
		                      }
		                      else
		                         return ""; 
		                    }
				         }},
			        {field:'ywCompanyName',align:'left',halign:'center',title:'维保单位',width:200},
			        {field:'subTime',align:'center',title:'维保日期',width:160,formatter: function(value,rec,index) {
		                if(value!=null)
		                    return value.substring(0,16);
				         }}
			    ]],
			    rowStyler:function(index,row){  
				    var  dailingFlag = row.dailingFlag; 
				       if( dailingFlag == 1)
				    	return 'color:#ff0000;';
				    else
				    	return 'color:#000000;';
				    
			     },
			    pagination:true,
			    singleSelect:true,
			    striped:true
			});	
			
			$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});   
		   	  
	 }
	 );

	 </script>
	 <style type="text/css">
	 td{
	 font-size:12px;
	 	overflow:hidden;
	 	padding:0;
	 	margin:0;
	 	}
	 </style>
</head>
<body class="easyui-layout">
<!--  
<div region="north" style="overflow:hidden">  
 <fieldset id="addDiv" style="width: 100%;margin:0px"><legend>查询条件</legend>
    
   <table border="0"> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
   <td nowrap><input id="registNumber" name="registNumber" size="15" class="easyui-validatebox"></input></td>
   <td nowrap>注册代码：</td>
 <td nowrap><input id="registCodeinfo" name="registCodeinfo" size="24" class="easyui-validatebox"></input></td>
    <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="addressinfo" name="addressinfo" size="24" class="easyui-validatebox"></input></td>
    <td align="right" nowrap>楼盘名称：</td> 
   <td nowrap><input id="qbuildingName" name="qbuildingName" size="24" class="easyui-validatebox"></input></td>
 
  <td>
				<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 
			
					
</td>
					
   </tr>
   
     </table>
  
  </fieldset>
</div> -->
   <div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div>  
       <table id="tt"></table>
   </div>  
        
    </div>  
</div> 
</body>
</html>