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
  //  location.href="http://182.150.40.154:8081/tcweb/index.jsp";
	
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
			
		//	location.href="<%=request.getContextPath()%>/mmain.jsp";
	        window.open('<%=request.getContextPath()%>/mmain.jsp','newwindow','width='+(window.screen.availWidth)+',height='+(window.screen.availHeight-30)+ ',top=0,left=0,fullscreen = yes,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');  
	       
		
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
<body class="easyui-layout" style="background:url(cetiao.png);background-repeat:repeat-x;text-align:center" fit="true">
  <div style="background:url(mengban.png);background-repeat:repeat-x;">
     <div  region="north" style="height:100px"></div>
     
     <div region="center">
     <div id="main-center" style="height:100%; width:100%;padding:0px;position:relative">
     <div><img src="logo1.png" width="880px" height="130px"></img></div> 
    <!--   <div style="margin-top:100px"><img src="jianzhu.png" width="1756px" height="380px"></img></div> -->
    <div style="margin-top:100px" style="background:url(jianzhu.png);"></div>
     <div style="margin-top:10px;margin-left:700px"><img src="logform.png" width="605px" height="373px"></img></div>
       <!--        <div id="logform" style="height:100%;margin-left:0px;margin-top:0px; padding:1px;">
       <div id="logu"  style="width:248px;height:36px;border: thick double yellow;margin-left:146px;margin-top:59px; ">
           <input name="loginName" type="text" id="loginName" class="transparent_class"  maxlength="30" style="width:100%;height:35px;border-style:none;margin-top:1px"/>
           </div>
           <div id="logu"  style="width:248px;height:36px;border: thick double yellow;margin-left:146px;margin-top:27px; ">
            <input name="password" type="password" id="password" class="transparent_class" maxlength="30" style="width:100%;height:35px;border-style:none;margin-top:1px "/>
           </div>
           <div id="logbutton"  style="width:248px;height:50px;border: thick double yellow;margin-left:146px;margin-top:18px; ">
            
           </div>
         </div> -->
    </div>  
     </div>  

 </div>
</body>
</html>