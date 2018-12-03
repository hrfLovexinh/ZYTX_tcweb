<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<%
String jspType = request.getParameter("jspType");
System.out.println("index1.jsp----jspType---"+jspType);

%>
<script type="text/javascript"> 

$(function(){
	 
}	
);
function userLogin(){
	 var jspType ='<%=jspType%>';
	$('#ff').form({  
		url:'/czweb/join/login',
		onSubmit:function(){
     //   return $(this).form('validate');
    },	  
		success:function(data){   
		eval("data="+"'"+data+"'");   
		if("success"==data){
			if("image"==jspType){
			location.href="<%=request.getContextPath()%>/views/image/imagelist.jsp";
			}
			else{
			location.href="<%=request.getContextPath()%>/views/car/carlist2.jsp";
				}
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

<style type="text/css">
body {
background-image:url(images/indexbg.bmp);
background-repeat: no-repeat;
}
input.zhanghao{
position:absolute;
left:600px;
top:330px;
}
input.mima{
position:absolute;
left:600px;
top:370px;
font-family:'Verdana';
}
.denglu{
position:absolute;
left:630px;
top:410px;
}
.tuichu{
position:absolute;
left:700px;
top:410px;
font-family:'Verdana';
}
a.a{
font-family:Microsoft YaHei;
position:absolute;
left:550px;
top:331px;
}
a.b{
font-family:Microsoft YaHei;
position:absolute;
left:550px;
top:372px;
}
</style>

</head>

<body>
<form id="ff" method="post">

<a class="a">账号：</a><input class="zhanghao" type="text" name="USER_NAME" /><br/>
<a class="b">密码：</a><input class="mima" type="password" name="USER_PASS" /><br/>
<input type="image" class="denglu" src="images/login.png" onclick="userLogin()"></input>
<input type="image" class="tuichu" src="images/exit.png" onclick="closeLogin()"></input>
 </form>
</body>

</html>










