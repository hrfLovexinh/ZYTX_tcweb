<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成都市电梯安全公共服务平台</title>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>


 <style type="text/css">
<!--
* {
	margin: 0;
	padding: 0;
}

.loginbody {
	background: url(images/login_r1_c1.jpg) repeat-x;
	font-size: 12px;
	color: #000000;
	text-align:center;
}

.logindiv {
	margin: 0 auto;
	width: 735px;
}

.floatleft {
	float: left;
}

.logindiv .userdiv {
	background: url(images/login.jpg) no-repeat;
	width: 511px;
	height: 312px;
}

.logindiv .userdiv .userleft {
	width: 195px;
	margin-left: 100px;
	margin-top: 160px;
	padding-left: 8px;
	border:0px solid red;
}
.subutton{
  background:url(images/subutton.jpg);
  border-top-width: 0px;
  border-right-width: 0px;
  border-bottom-width: 0px;
  border-left-width: 0px;
  border-top-style: solid;
  border-right-style: solid;
  
  border-left-style: solid;
  cursor:pointer;
}

.transparent_class {
	filter:alpha(opacity=50);
	-moz-opacity:0.5;
	-khtml-opacity: 0.5;
	opacity: 0.5;
}
-->
</style>
<script type="text/javascript">
$(function(){
//	  location.href="http://119.6.254.76:80/tcweb/index.jsp";
//    location.href="http://182.150.40.154:8081/tcweb/index.jsp";
	
}	
);
function userLogin(){ 
	$('#ff').form({  
		url:'/tcweb/join/login',
		onSubmit:function(){
     //   return $(this).form('validate');
    },	  
		success:function(data){   
		eval("data="+"'"+data+"'");   
		if("success"==data){
			location.href="<%=request.getContextPath()%>/mmain.jsp";
		}
		else{
			$.messager.alert('操作失败','请核对用户名和密码','error');
    		}	
		} 
	});
	
}

function closeLogin(){
	window.opener=null;
	window.close();
}
</script>
</head>
<body class="loginbody">
<div class="logindiv">
<div style="margin-top: 65px;"><img src="images/login_r2_c4.jpg"
	width="735" height="170" /></div>
<div class="floatleft"><img src="images/login_r5_c4.png"
	width="224" height="80" /></div>
<div  class="floatleft userdiv">

<div class="userleft floatleft">
<form id="ff" method="post">
<table border="0" cellspacing="3" cellpadding="0">
	<tr>
	    <td width="150" style="font:bold icon" nowrap>页面跳转中>>...</td>	
	</tr>
	<tr>
	    <td width="150" style="font:bold icon" nowrap>如长时间为跳转，请手动点击<a href="http://182.150.40.154:8081/tcweb/index.jsp";>跳转</a></td>	
	</tr>
	
	
</table>
</form>
</div>

</div>

</div>

</body>
</html>