<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
	<%@page import ="com.zytx.init.GlobalFunction" %>
	
<html xmlns="http://www.w3.org/1999/xhtml">

<head>


<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/flowplayer-3.2.13.min.js"></script>
<script type="text/javascript">

</script>
</head>
<% 
String registNumber = request.getParameter("registNumber");
System.out.println("registNumber1=="+registNumber);
int picDataType =0;
if(request.getParameter("picDataType")!=null && !"".equals(request.getParameter("picDataType")))
	picDataType=Integer.parseInt(request.getParameter("picDataType"));
System.out.println("picDataType1=="+picDataType);
String onePath ="";
String twoPath ="";
String threePath="";
String fourPath ="";
String startTime="";

if(registNumber != null && !"".equals(registNumber)){
 onePath =String.valueOf(registNumber.charAt(0));
 twoPath =String.valueOf(registNumber.charAt(1));
 threePath=String.valueOf(registNumber.charAt(2));
 fourPath =String.valueOf(registNumber.charAt(3));
}
startTime = request.getParameter("startTime");

String path = GlobalFunction.tcAlarmPath;
String fileName="";
if(picDataType==1){
	if(startTime!=null && !"".equals(startTime)){
	fileName=startTime.replace("-", "").replace(" ","").replace(":", "")+".mp4";
	}
}
else{
	if(startTime!=null && !"".equals(startTime)){
	fileName=startTime.replace("-", "").replace(" ","").replace(":", "")+".mp3";
	}
}
  
  path =path+onePath+"/"+twoPath+"/"+threePath+"/"+fourPath+"/"+registNumber+"/"+fileName;
// path="1429503765774.mp4";
  System.out.println("path=="+path);
//  path="http://192.168.1.200:8081/tc/0/0/0/0/000010/recording.mp3";
// path="E:/DeviceSoft/tc/0/0/0/0/000010/201504151408.mp3";
  
%>
<body>

<div style="overflow-x:auto;overflow-y:hidden;width:100%;">
<table align="center" border="0" cellpadding="0" cellspacing="0">
	<tr class="t2">
		<td>
		
		<table>
		
			    
				<tr class="t2">
				<td>
				<div style="display:block;width:325px;height:380px" id="player"></div>
				<script type="text/javascript">


		flowplayer("player", "flowplayer-3.2.18.swf", {
			  clip: {
			    url: '<%=path%>'
			  },
			  plugins: {
			        audio: {
			            url: 'flowplayer.audio-3.2.11.swf',
			            provider: "audio"
			        },
			        controls: {
			            fullscreen: true,
			            height: 30,
			            autoHide: false
			        }
			  
			    }
			});



</script>
        </table>
				</td>
				
			
	</tr>
</table>

</div>



</body>
</html>