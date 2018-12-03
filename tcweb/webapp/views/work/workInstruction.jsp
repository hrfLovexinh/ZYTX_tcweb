<%@ page import="com.zytx.models.UserInfo" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统统计</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>
<style type="text/css">
li{font-size: 14px;}
a{font-size: 14px; cursor:pointer;text-decoration: none; }

@media screen and (min-width: 1300px) { 
li{font-size: 14px;}
a{font-size: 14px;cursor:pointer;text-decoration: none; }
}


@media screen and (max-width: 1300px) {
li{font-size: 12px;}
a{font-size: 12px;cursor:pointer;text-decoration: none; }
}
</style>
<% 
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int  role = 0; 
int  userId=0;
if(userinfo!=null){
	 role = userinfo.getRole(); 
	 userId=userinfo.getId();
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
		    userId =user.getId();
	}


%>
<script type="text/javascript">

$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	var contextDivContainer = document.getElementById('contextDiv');   
	var h = contextDivContainer.clientHeight * 0.48;  
	$('#contextDiv').layout('panel','north').panel('resize',{height:h});
	$('#contextDiv').layout('resize');

	var stlcontextDivContainer = document.getElementById('stscontextDiv');
	var w = contextDivContainer.clientWidth * 0.48; 
	$('#stscontextDiv').layout('panel','west').panel('resize',{width:w});
	$('#stscontextDiv').layout('resize');

	var stlcontextDivContainer = document.getElementById('stxcontextDiv');
	var w = contextDivContainer.clientWidth * 0.48; 
	$('#stxcontextDiv').layout('panel','west').panel('resize',{width:w});
	$('#stxcontextDiv').layout('resize');

	gzzdgrid=$('#gzzdtt').datagrid({
	    title:'工作指导',
	    striped:true,
	    url:'',
	    columns:[[
           {field:'ywManagerEleCount',title:'管理电梯数',width:$(this).width() * 0.5,align:'center'},
           {field:'ywEleCount',title:'维保数',width:$(this).width() * 0.5,align:'center'}
		      	    ]]
	});
});
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
 <div region="north"  border="0" style="height:60px;;background-color:rgb(201,220,245);">
 
   <table border="0"> 
   <tr> 
   <td nowrap>关键字：</td> 
   <td nowrap><input id="keyword"></input></td>
	 <td colspan="2">		
	 <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
	 <a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 					
   </td>				
   </tr>
     </table>
</div> 

 <div region="center">
     <div class="easyui-layout" data-options="fit:true" id="contextDiv"> 
     
        <div region="north"  border="0" id ="stdiv">
            <div class="easyui-layout" data-options="fit:true" id="stscontextDiv" >
                   <div region="west"  id ="stldiv">
                       <div id ="gzzd"  style="padding:20px;">
                                                               工作指导  
                          <table id="gzzd" style="width:100%">
                          
                         <tr><td><a href="#">&bull;质检总局办公厅关于做好2017年电梯安全监管工作的通知（质检办特〔2017〕332号）</a></td><td align="right" nowrap>2017-04-17</td></tr>
                         <tr><td><a href="#">&bull;质检总局关于2016年全国特种设备安全状况情况的通报&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></td><td align="right"  nowrap>2017-04-17</td></tr>
                        <tr><td><a href="#">&bull;质检总局办公厅关于开展2017年特种设备安全隐患排查和治理的通知（质检办特〔2017〕146号）</a></td><td align="right"  nowrap>2017-02-20</td></tr>
                        <tr><td><a href="#">&bull;质检总局关于印发《质检总局2016年电梯安全攻坚战工作方案》的通知(国质检特〔2016〕166号)</a></td><td align="right"  nowrap>2016-04-01</td></tr>
                        <tr><td><a href="#">&bull;质检总局特种设备局关于鼓励使用无纸化电梯维保记录的指导意见（质检特函〔2016〕3号）</a></td><td align="right"  nowrap>2016-02-22</td></tr>
                        <tr><td><a href="#">&bull;质检总局办公厅关于印发《质检总局电梯安全监管大会战工作方案》《全面深化改革创新构建电梯安全监管长效机制工作方案》的通知(质检办特〔2015〕197号)</a></td><td align="right" nowrap>2015-03-16</td></tr>
                         <tr><td><a href="#">&bull;中华人民共和国特种设备安全法</a></td><td align="right" nowrap>2013-07-10</td></tr>
                          </table>
                       </div>
                    </div> 
                    <div region="center"> 
                        <div id="tzgg"  style="overflow:auto;padding:20px;">  
                                                               通知公告
                        <table id="tzgg" style="width:100%">
                          	
                         <tr><td><a href="#">&bull;成都市质量技术监督局关于2017年4月《电梯安全公共服务平台》电梯维保信息公示情况的通报（成质监特〔2017〕27号）</a></td><td align="right"  nowrap>2017-05-05</td></tr>
                         <tr><td><a href="#">&bull;成都市质量技术监督局关于成都电梯安全公共服务平台开通运行通知（成质监特〔2016〕46号）</a></td><td align="right"  nowrap>2016-06-08</td></tr>
                         <tr><td><a href="#">&bull;成都市质量技术监督局关于全面推行电梯3D防伪激光二维码电子标签管理工作的通知（成质监特〔2015〕80号）</a></td><td align="right"  nowrap>2015-11-10</td></tr>
                         <tr><td><a href="#">&bull;成都市人民政府关于加强全市住宅电梯安全管理工作的意见（成府发〔2015〕29号）</a></td><td align="right"  nowrap>2015-08-21</td></tr>
                           <tr><td><a href="#">&bull;成都市电梯安全监督管理办法（成都市人民政府令第179号）</a></td><td align="right"  nowrap>2013-07-29</td></tr>
                      </table>
                       
                        </div>
                    </div>
             </div>       
        </div>
        
        
        <div region="center">
            <div class="easyui-layout" data-options="fit:true" id="stxcontextDiv" >
                 <div region="west"  id ="stxldiv">
                     <div id="gzzd"  style="padding:20px;">  
                                                         规章制度 
                          <table id="gzzd" style="width:100%">
                         
                          <tr><td><a href="#">&bull;电梯维护保养规则（TSG T5002-2017）</a></td><td align="right">2017-05-15</td></tr>
                          <tr><td><a href="#">&bull;特种设备使用管理规则(TSG 08-2017)</a></td><td align="right">2017-04-18</td></tr>
                          <tr><td><a href="#">&bull;电梯型式试验规则（TSG T7007-2016）</a></td><td align="right">2016-07-25</td></tr>
                          <tr><td><a href="#">&bull;特种设备作业人员考核规则(TSG Z6001-2013)</a></td><td align="right">2013-05-13</td></tr>
                          </table>  
                      </div>  
                  </div>
                  <div region="center"> 
                    <div id="cjwt"  style="padding:20px;">
                                                          常见问题  
                         <table id="cjwt" style="width:100%">                                 
                          	
                          <tr><td><a href="#">&bull;关于电梯标签缺失补贴问题</a></td><td align="right">2016-06-20</td></tr>
                          <tr><td><a href="#">&bull;关于电梯维保单位变更问题</a></td><td align="right">2016-06-15</td></tr>
                          <tr><td><a href="#">&bull;关于维保人员单位变更问题</a></td><td align="right">2016-06-15</td></tr>
                          <tr><td><a href="#">&bull;关于维保单位名称变更问题</a></td><td align="right">2016-06-10</td></tr>
                          <tr><td><a href="#">&bull;关于扫描运维数据上传问题</a></td><td align="right">2015-12-02</td></tr>
                          <tr><td><a href="#">&bull;关于标签粘贴数据上传问题</a></td><td align="right">2015-11-15</td></tr>
                          <tr><td><a href="#">&bull;关于手机软件账号绑定问题</a></td><td align="right">2015-11-12</td></tr>
                          <tr><td><a href="#">&bull;关于标签粘贴安装规范问题</a></td><td align="right">2015-09-25</td></tr>
                      </table>  
                     </div> 
                 </div>   
         </div>  
       </div>    
    </div>  
 </div>

</body>
</html>