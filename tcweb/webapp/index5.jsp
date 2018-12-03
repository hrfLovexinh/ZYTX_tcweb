<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<script type="text/javascript">
$(function(){
	
}	
);
function userLogin(){
	$('#ff').form({  
		url:'/czweb/join/login',
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

</script>
<style type="text/css">
td{
font-size:12px;
	overflow:hidden;
	padding:0;
	margin:0;
	}
	

input{font-family:'Verdana';}

body{
	margin:0;
	padding:0;
	font-size:12px;
	background-color:#f4f4f4;
	background:url("images/indexbg2.jpg") no-repeat  top right;
}
#main{	
	width:100%;
    height:100px;
	line-height:100px;
	
	border-top:1px solid #fff;
	border-bottom:0px solid #fff;
	text-align:center;
	position:absolute;
	top:60%;
}

</style>

</head>

<body>
 
  <div id="main" style="text-align:center;">

 
 <form id="ff" method="post">
           <table width="100%" border="0" cellspacing="0" cellpadding="0">
             <tr>
               <td height="10"></td>
             </tr>
             <tr>
               <td>用户名：<input name="USER_NAME" type="text" class="loginput" id="textfield" size="20"/></td>
             </tr>
             <tr>
               <td>&nbsp;&nbsp;&nbsp;&nbsp;密码：<input name="USER_PASS" type="password" class="loginput" id="textfield3" size="20"/></td>
             </tr>
             <tr>
               <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="image" src="images/login_r6_c12.jpg" width="62" height="26" name="button" id="button" value="提交" onclick="userLogin()" />
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="image" src="images/login_r9_c12.jpg" width="62" height="26" name="button" id="button" value="提交" /></td>
             </tr>
           </table>
           </form>
        
</div>

</body>
</html>