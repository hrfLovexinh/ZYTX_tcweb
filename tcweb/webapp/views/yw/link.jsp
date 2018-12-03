<%@ page import="com.zytx.models.UserInfo" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% 
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int  role = 0; 
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
   String userId = (String)session.getAttribute("userId"); 
   System.out.println("userId---"+userId);
%>
<head>
<title>Insert title here</title>
</head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript">
$(function(){
	$.ajaxSetup ({
	    cache: false 
	});

	shenhewin = $('#shenhe-window').window({  closed:true,draggable:false,modal:true,onClose:function(){$('#ywPoint').empty(); } }); 	

	grid=$('#lkttmap').datagrid({
	    title:'运维质量低低于50分维保记录',
	    pageSize:15,
	    pageList:[15,20,25,30,35,40],
	    url:'/tcweb/yw/lkywlist',
	    queryParams:{'userId':'<%=userId%>'},
	    columns:[[
	        {field:'registNumber',title:'电梯编号',width:60},
	        {field:'address',title:'地址',width:160},
	        {field:'ywKind',title:'种类',width:50},
	        {field:'maintainTypecode',title:'类型',width:50},
	        {field:'startTime',title:'开始时间',width:120},
	        {field:'endTime',title:'结束时间',width:120},
	        {field:'dateSpan',title:'时长（分钟）',width:70},
	        {field:'sPosition',title:'开始位置',width:55},
	        {field:'ePosition',title:'结束位置',width:55},
	        {field:'userName',title:'维保人员',width:100},
	        {field:'companyName',title:'维保单位',width:150},
	        {field:'subTime',title:'上传时间',formatter: function(value,rec,index) {
                 if(value)
                     return value.substring(0,16);
                 else
                     return value;
		         }},
		     {field:'ywstatus',title:'状态',width:80,formatter: function(value,rec,index) {
		        	 var registNumber = ''+rec.registNumber;
		        	 var startTime =''+rec.startTime;
		        	 var picNum =rec.picNum;
		        	 var id = rec.id;
	                 if('0'==value){
		                // if(picNum>0)
		                 <%if(role==1 || role==2){%>   
	                     return "审核中"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"0"+")'/>";
		                 <%} 
		                 else { %>
		                 return "审核中";
		                 <%}%>
	                    // else
		                // return "审核中";     
		             }else if('4'==value){
		            	// if(picNum>0)
		            	 <%if(role==1 || role==2){%>
                         return "无效"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"4"+")'/>";
		            	// else 	   
	                    // return "无效";
                         <%} 
		            	   else { %>
		            	   return "无效";
		            	   <%}%> 
		             }
	                 else{
		                // if(picNum>0)
		                 <%if(role==1 || role==2){%>
                         return "通过"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"1"+")'/>";
		                // else
	                	// return "通过";
                         <%} 
		            	   else { %>
		            	   return "通过";
		            	   <%}%> 
	                 }
			         }},
			         {field:'ywResult',title:'结论',formatter: function(value,rec,index) {
					        var ywResult = rec.ywResult;
					        if(ywResult==100)
					  		return "正常";
					        if(ywResult==90)
						  		return "丢失";
					        if(ywResult==80)
						  		return "未知";
					        if(ywResult==50)
						  		return "图异常";
					        if(ywResult==40)
						  		return "号输错";
					        if(ywResult==30)
						  		return "位置错";
					        if(ywResult==20)
						  		return "复制";
					        if(ywResult==10)
						  		return "破坏";
					  		       
						         }}	          
	    ]],
	    rowStyler:function(index,row){  //暂时取消颜色超期
		 //   var endTime = row.endTime;
		//    if(getDateDiff(endTime))
		 //   	return 'color:#ff0000;';
		//    else
		//    	return 'color:#000000;';
		    
	     },
	    pagination:true
	});	
	$('#lkttmap').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  	
}
	);

var shenheid=0;
var ywstatusValue=0;
function openShenheInfo(registNumber,startTime,id,ywstatus){
	shenheid=id;
	ywstatusValue=ywstatus;
	shenhewin.window('open');
	var ywimg="";
	ywing ='<img height="480" align="center" style="width:100%" src="<%=request.getContextPath()%>'+'/servlet/ywImage.jpg?registNumber='+registNumber+'&startTime='+startTime+'"/>';
	document.all.ywImg.innerHTML=ywing;
	if(ywstatus==0){
		<%if(role==2 || role==1) {%>
		document.all.shenheDiv.innerHTML='<a href="javascript:void(0)" onclick="shenhe()" id="btn-ok" icon="icon-ok">通过</a><a href="javascript:void(0)" onclick="noshenhe()" id="btn-no" icon="icon-no">不通过</a><a href="javascript:void(0)" onclick="closeshenhe()" id="btn-no2" icon="icon-no">关闭</a>';
	 //   $('#btn-ok').html("审核");
		$('#btn-ok').linkbutton();
		$('#btn-no').linkbutton();
		$('#btn-no2').linkbutton();
		<%} else {%>
		document.all.shenheDiv.innerHTML='<a href="javascript:void(0)" onclick="shenhe()" id="btn-no" icon="icon-ok">关闭</a>';
		$('#btn-no').linkbutton();
		<%}%>
		}
	else{ 
		document.all.shenheDiv.innerHTML='<a href="javascript:void(0)" onclick="shenhe()" id="btn-no" icon="icon-no">关闭</a>';
	//	$('#btn-no').html("关闭");
		$('#btn-no').linkbutton();
	}
	ywPoint();
}

function shenhe(){
	 <%if(role==1 || role==2) {%>
	 if(ywstatusValue==0){ //判断状态，如果是待审核状态才进行状态更新
    
    if(plng>0 && plat>0){
		   if(plng!=map_x || plat!=map_y){
			   $.messager.confirm('Confirm','你将通过审核修订电梯位置坐标 ?',function(r){
				   if (r){ 
	    jQuery.post('/tcweb/yw/shenheByid',
	    	 {'id':shenheid,'map_x':plng,'map_y':plat,'ywResult':$('#ywResult option:selected').val()},
	    	 function(data){
	    		eval("data="+"'"+data+"'");  
	    		if("success"==data){
	    		//	$.messager.alert("操作成功",'谢谢');
	    		 $.messager.show({   
			 title:'提示信息',
			 timeout:1000,
			 msg:'操作成功，谢谢。' 
		 });  	
	    		plng=0.0;
	    		plat=0.0;
	    		$('#ywPoint').empty();
	    		shenhewin.window('close');
	            grid.datagrid('reload');
	    		}
	    		else{
	    			$.messager.alert('操作失败','电梯位置坐标修订失败','error');
   	    		}
   	       });
				   }
			   });
		   }
		   }
      else{
   	   jQuery.post('/tcweb/yw/shenheByid2',
   		    	 {'id':shenheid,'ywResult':$('#ywResult option:selected').val()},
   		    	 function(data){
   		    		eval("data="+"'"+data+"'");  
   		    		if("success"==data){
   		    		//	$.messager.alert("操作成功",'谢谢');
   		    		 $.messager.show({   
   				 title:'提示信息',
   				 timeout:1000,
   				 msg:'操作成功，谢谢。' 
   			 });  	
   		    		plng=0.0;
   		    		plat=0.0;
   		    		$('#ywPoint').empty();
   		    		shenhewin.window('close');
   	 	            grid.datagrid('reload');
   		    		}
   		    		else{
   		    			$.messager.alert('操作失败','审核失败','error');
   	    	    		}
   	    	       });

          }
		 	 
	 } 
	 else{  //无效和通过状态只查看图像
		 $('#ywPoint').empty();
		 shenhewin.window('close');
		 }
	 <%} else{%>
	 $('#ywPoint').empty();
	 shenhewin.window('close');
	 <%}%>
}

var plng=0.0;
var plat=0.0;
var map_x;
var map_y;
var map_x0;
var map_y0;
var map_x1;
var map_y1;
var map_x2;
var map_y2;


function ywPoint(){
	jQuery.post('/tcweb/yw/shenhePointByReg', {'id':shenheid},function(data){ 
		          data = eval(data);//POST方法必加，ajax方法自动处理了   
		          map_x=data.map_x; 
		          map_y=data.map_y;
		          map_x0=data.map_x0; 
		          map_y0=data.map_y0;
		          map_x1=data.map_x1; 
		          map_y1=data.map_y1;
		          map_x2=data.map_x2; 
		          map_y2=data.map_y2;
		          showYwPointMap();
			   }, 'json');
}

function showYwPointMap(){
	$('#ywPoint').append("<iframe src='mapinfo.jsp?map_x="+map_x+"&map_y="+map_y+"&map_x0="+map_x0+"&map_y0="+map_y0+"&map_x1="+map_x1+"&map_y1="+map_y1+"&map_x2="+map_x2+"&map_y2="+map_y2+"' width='100%' height='100%' ></iframe>");
    
}

function closeshenhe(){
	 shenhewin.window('close'); 
}
</script>
<body style="margin-top: 0;margin-left: 0;">


<div id="yw-window" title="运维质量小于50分维保记录" style="width:780px;height:550px;">
        <table id="lkttmap"></table>  
   </div>
 <div id="shenhe-window" title="审核详细信息" style="width:780px;height:550px;">
 <div style="both:clear">
 <div id="ywImg"  style="overflow:hidden;width:49%;float:left;border:1px solid #000;"></div>
 <div id="ywPoint" style="overflow:hidden;width:50%;height:480px;float:right;border:1px solid #00f;"></div>
 </div> 
 <div style="both:clear"></div>
 <div style="float:left">结论：<select id="ywResult"  name="ywResult" style="width:80px;">
    <option value="100">正常</option>
    <option value="90">丢失</option>
    <option value="80" selected ="selected">未知</option> 
    <option value="50">图异常</option> 
    <option value="40">号输错</option>
    <option value="30">位置错</option>
    <option value="20">复制</option>
    <option value="10">破坏</option>   
</select></div>
 <div align="center" id="shenheDiv"><a href="javascript:void(0)" onclick="shenhe()" id="btn-ok" icon="icon-ok">通过</a><a href="javascript:void(0)" onclick="noshenhe()" id="btn-no" icon="icon-no">不通过</a><a href="javascript:void(0)" onclick="closeshenhe()" id="btn-no2" icon="icon-no">关闭</a></div>
</div>
</body>
</html>