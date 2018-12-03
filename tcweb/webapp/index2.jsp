<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.net.URLDecoder,java.net.URLEncoder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
</head>

<%
  String userName = request.getParameter("userName");
  System.out.println("index2.jsp---userName----"+userName);
  String password = request.getParameter("password");
  String carnum = request.getParameter("carnum");
  System.out.println("index2.jsp---carnum----"+carnum);
  String jspType = request.getParameter("jspType");
  System.out.println("index2.jsp----jspType---"+jspType);
%>
<script type="text/javascript"> 
$(function(){
//  var param={};
  var userName='<%=userName%>';
  var password='<%=password%>';
  var carnum ='<%=carnum%>';
  var jspType ='<%=jspType%>';
  $.post("/czweb/join/login",{"USER_NAME":userName,"USER_PASS":password},
		  function(data){
	         if("success"==data){
		         if("image"==jspType){ //alert("1----"+jspType);
		            location.href="views/image/imagelist.jsp?carnum="+encodeURI(carnum);
		         }
		         else {  //alert("2----"+jspType);
                    location.href="views/car/carlist.jsp?carnum="+encodeURI(carnum);
			         }
	         }
	         else 
		         alert("非法用户，无权操作！");
	         },"text");	  
});
 
</script>
<body>
</body>
</html>