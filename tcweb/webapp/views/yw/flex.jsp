<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<%@ page import="com.zytx.init.GlobalConfig" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<style type="text/css">   
html{height:100%}   
body{height:100%;margin:0px;padding:0px}  
#container{height:100%}   
</style> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<%
int id =Integer.parseInt(request.getParameter("id"));
int flexStartx =Integer.parseInt(request.getParameter("flexStartx"));
int flexStarty =Integer.parseInt(request.getParameter("flexStarty"));
int flexEndx =Integer.parseInt(request.getParameter("flexEndx"));
int flexEndy =Integer.parseInt(request.getParameter("flexEndy"));
int ywResult=Integer.parseInt(request.getParameter("ywResult"));
int role=Integer.parseInt(request.getParameter("role"));
//int userId=Integer.parseInt(request.getParameter("userId"));
String registNumber=request.getParameter("registNumber");
String startTime =request.getParameter("startTime");
/*
String imagePath="";
if(!"".equals(registNumber) && !"".equals(startTime)){
	imagePath =GlobalConfig.twoCodeImageSource(registNumber,startTime);
} */
System.out.println("flexStartx---"+flexStartx);
System.out.println("flexStarty---"+flexStarty);
System.out.println("flexEndx---"+flexEndx);
System.out.println("flexEndy---"+flexEndy);
System.out.println("role---"+role);
%>
<script type="text/javascript" src="swfobject.js"></script>
        <script type="text/javascript">
            <!-- For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection. --> 
            var swfVersionStr = "10.0.0";
            <!-- To use express install, set to playerProductInstall.swf, otherwise the empty string. -->
            var xiSwfUrlStr = "playerProductInstall.swf";
            var flashvars = {};
            var params = {};
            params.quality = "high";
            params.bgcolor = "#ffffff";
            params.allowscriptaccess = "sameDomain";
            params.allowfullscreen = "true";
            var attributes = {};
            attributes.id = "TwoCode";
            attributes.name = "TwoCode";
            attributes.align = "middle";
            swfobject.embedSWF(
                "TwoCode.swf", "flashContent", 
                "382", "480", 
                swfVersionStr, xiSwfUrlStr, 
                flashvars, params, attributes);
			<!-- JavaScript enabled so display the flashContent div in case it is not replaced with a swf object. -->
			swfobject.createCSS("#flashContent", "display:block;text-align:left;");
        </script>
 
 <script type="text/javascript">
 var imagePah='<%=request.getContextPath()%>/servlet/ywImage.jpg?';
 var ywResult;
 var role;
 
 function init(){
	 detectFlashPlayer();
	 lastImagePath=imagePah+'registNumber='+'<%=registNumber%>'+'&startTime='+'<%=startTime%>'; 
	 ywResult=<%=ywResult%>;
	 role=<%=role%>;
 }

 var lastImagePath;  //actionScript调用
 var flashPlayer;
 function detectFlashPlayer() {
 if(navigator.appName.indexOf("Microsoft") != -1) {
 flashPlayer = window.TwoCode;

 }
 else {
 flashPlayer = window.document.TwoCode;

 }
 }

 var pointcoordinatesStr;
 function getPointString(){
	 pointcoordinatesStr =flashPlayer.getPointString();
	 return pointcoordinatesStr;
	 }

 function showflashState(){
	// alert("<%=flexStartx%>"+","+"<%=flexStarty%>"+","+"<%=flexEndx%>"+","+"<%=flexEndy%>");
	 return "<%=flexStartx%>"+","+"<%=flexStarty%>"+","+"<%=flexEndx%>"+","+"<%=flexEndy%>";
 }

 function getImageRouce(){
	 // alert("开始调用getImageRouce");   
	//  alert("返回的lastImagePath"+lastImagePath);
	  return lastImagePath;
		
	}

 
 function getYwResult(){
	 // alert("开始调用getYwResult");   
		//  alert("返回的ywResult"+ywResult);
		return ywResult;
	 }

 function getRole(){

     return  role;
	 }
 </script>
</head> 
<body onload="init()"> 
   <div id="flashContent">
        	<p>
	        	To view this page ensure that Adobe Flash Player version 
				10.0.0 or greater is installed. 
			</p>
			<script type="text/javascript"> 
				var pageHost = ((document.location.protocol == "https:") ? "https://" :	"http://"); 
				document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
								+ pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" ); 
			</script> 
        </div>
<div id="container">
<noscript>
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="550" id="TwoCode">
                <param name="movie" value="TwoCode.swf" />
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="sameDomain" />
                <param name="allowFullScreen" value="true" />
                <!--[if !IE]>-->
                <object type="application/x-shockwave-flash" data="TwoCode.swf" width="100%" height="100%">
                    <param name="quality" value="high" />
                    <param name="bgcolor" value="#ffffff" />
                    <param name="allowScriptAccess" value="sameDomain" />
                    <param name="allowFullScreen" value="true" />
                <!--<![endif]-->
                <!--[if gte IE 6]>-->
                	<p> 
                		Either scripts and active content are not permitted to run or Adobe Flash Player version
                		10.0.0 or greater is not installed.
                	</p>
                <!--<![endif]-->
                    <a href="http://www.adobe.com/go/getflashplayer">
                        <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
                    </a>
                <!--[if !IE]>-->
                </object>
                <!--<![endif]-->
            </object>
	    </noscript>		
</div> 


</body>
</html>