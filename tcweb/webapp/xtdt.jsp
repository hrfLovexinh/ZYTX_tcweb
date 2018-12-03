<%@ page import="com.zytx.models.UserInfo" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/myeasyuiicon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
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

.exitinHead {
	margin:10px;  
	padding: 5px 0px 0px 20px;
	background: transparent url('images/tuichu.png') no-repeat center  left;
	text-decoration:none;
	color:#ffffff;
	border:0px solid #91a7b4;
}
.tileHead {
    margin:10px; 
	padding: 5px 5px 0px 20px;
	background: transparent url('images/guanliyuan.png') no-repeat center  left;
	text-decoration:none;
	color:#ffffff;
	border:0px solid #91a7b4;
}

.tileHead2 { 
    margin:10px; 
	padding: 5px 40px 0px 20px;
	text-decoration:none;
	color:#FFCC33;
	border:0px solid #91a7b4;
	font-size: 22px;
}

/* tabmenu style */
.tabmenu {position:absolute;top:72px;right:1%;margin:0;}
.tabmenu li{display:inline-block;}

ul.daohangl
{
list-style-type:none;
margin:0;
padding:0;
overflow:hidden;
margin-top: 20px;
float:left;
}
	
ul.daohang
{
list-style-type:none;
margin:0;
padding:0;
overflow:hidden;
position:absolute;
right:1%;
top:72px;
margin:0;
}
ul.daohang li
{
float:left;
}
ul.daohang li a:link,a:visited
{
display:block;
width:120px;
font-weight:bold;
color:#FFFFFF;
background-color:#0855a3;
text-align:center;
padding:4px;
text-decoration:none;
text-transform:uppercase;
}
ul.daohang li a:hover,a:active
{
background-color:#e5e5e5;
color:#0855a3;
}

</style>
<% 
/*
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

*/
%>
<script>

$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	
	
	
	
});




</script>
</head>
<body class="easyui-layout" data-options="fit:true" style="text-align:center;">

<!-- 头部，电梯安全平台标题-->
 <div region="north"  border="0" style="height:100px;background-color:rgb(201,220,245);">
   <div style="height:100px;float:right;both:clear;width:100%;background-color: silver;background-repeat: no-repeat;background-positon: 100%, 100%; position:relative;">
    <img src="images/wgpt.png" style="width:100%;height:100%"/>
  <!--     <ul class="tabmenu"> --> 
   <ul class="daohang">  
    <li><a href="#" style="background-color:#e5e5e5;color:#0855a3;">系统地图</a></li>
    <li><a href="#">系统统计</a></li>
    <li><a href="#">电梯分布</a></li>
    </ul>
    
    </div>  
 
</div> 


<!-- 中部，电梯维保单位数量，电梯地图展示，使用单位数据 -->
 <div region="center" data-options="border:false">
 
     <div class="easyui-layout" data-options="fit:true">
     
  <!--   <div region="north"  border="0" style="height:60px;background-color:rgb(201,220,245);"> --> 
    <div region="west"  border="0" style="height:60px;background-color:#0855a3;background-image:url('images/wgptbj.png');" >
  <!--   <ul class="daohangl">
     <li style="color:#ffffff;font-weight:bold;">所属项目： <select id="shxm"  class="easyui-combobox" name="shxm" style="width:252px;">
    <option value="全部">全部</option>
     </select></li>
     </ul> -->  
    
    </div> 
    
       
    <div region="center">
     <div class="easyui-layout" data-options="fit:true" id="ditu"> 
        <div region="north"  border="0" id ="norcontextDivs">
            <div class="easyui-layout" data-options="fit:true" id="contextDiv" >
              <!--  <div region="west"  title="电梯数量"  id ="wcontextDiv" style="overflow:hidden" collapsible="false" data-options="closable:true,tools:'#tt'">  -->    
                    <div region="west"  title=""  id ="wcontextDiv" style="overflow:hidden;position:relative;" collapsible="false" data-options="tools:'#tt'">
                   
                    
                   <div style="position:absolute; right:5px; top:5px;font-size:18px;color:#ffffff">
                    <input type="radio" name="dtslro" value="0" checked="checked"/>设备总量
                    <input type="radio" name="dtslro" value="1" />在用设备&nbsp;&nbsp;&nbsp;&nbsp;
                                                    电梯类别
                   <select id="dtslst"  class="easyui-combobox" name="dtslst" style="width:152px;">
                   <option value="全部">全部</option>
                   </select>
                    </div>
                    
                    
                       <div id ="dtsl"  style="padding:20px;background-image:url('images/wgptbj.png');">
                                                              
                         
                         
                       </div>
                    </div> 
                    <div region="center" title="" id="ccontextDiv" style="overflow:hidden" data-options="tools:'#tt2'"> 
                        <div id="cqyj"  style="padding:20px;background-image:url('images/wgptbj.png');">  
                                                            
                      
                       
                        </div>
                    </div>
             </div>       
        </div>
        
        
        <div region="center">
            <div class="easyui-layout" data-options="fit:true" id="contextDiv2" >
                 <div region="west"  title="" id ="wcontextDiv2" style="overflow:hidden" collapsible="false" data-options="tools:'#tt3'">
                     <div id="zgts"  style="padding:20px;background-image:url('images/wgptbj.png');">  
                                                       
                        
                      </div>  
                  </div>
                  <div region="center" title="" id="ccontextDiv2" style="overflow:hidden;position:relative;" data-options="tools:'#tt4'"> 
                    <div style="position:absolute; right:25px; top:5px;font-size:18px;color:#ffffff">
                    <input type="radio" name="jyztro" value="0" checked="checked" />定期检验
                    <input type="radio" name="jyztro" value="1" />安装检验
                    </div>
                    <div id="jyzt"  style="padding:20px;background-image:url('images/wgptbj.png');">
                                                         
                       
                     </div> 
                 </div>   
         </div>  
       </div> 
       
          
    </div> 
    </div>
    
         <div region="east"></div>
    </div>
 </div>
  
  <!-- 底部，电梯维保单位，电梯实时数据，区域统计 -->
   <div region="south">
   </div>

  

</body>
</html>