<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.zytx.util.YwtjDisplayChart" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<style type="text/css">   
html{height:100%}   
body{height:100%;margin:0px;padding:0px}     
</style> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>




</head> 
<body style="margin-top: 0;margin-left: 0;"> 
<% 
String userId =request.getParameter("userId");
session.setAttribute("userId",userId);
String graphURL="";
String filename="";
System.out.println(userId);
if(!"".equals(userId) && userId !=null){	
YwtjDisplayChart  chart= new YwtjDisplayChart(Integer.parseInt(userId));
// chart.setValue("一月",500.00);
// chart.setValue("二月",230.00);
// chart.setValue("三月",350.00);
filename =chart.generatePieChart("运维统计表",session,new PrintWriter(out));
graphURL =request.getContextPath()+"/servlet/DisplayChart?filename="+filename;
}
%>
<%if(!"".equals(filename) && filename!=null) {%>
<img src ="<%=graphURL %>" width="100%" height="350" border="0" usemap="#<%=filename %>"/>
<%} else { %>
近一个月没有运维电梯,无统计图
<% } %>

</body>
</html>