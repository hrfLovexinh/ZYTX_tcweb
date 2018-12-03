<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>电梯安全公共服务平台</title>
 <!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/> -->
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/theme/wintage.js"></script>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css"> 
<link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.no-icons.min.css" rel="stylesheet"> 
<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
 <style>
    .datagrid-header-row td{background-color:rgb(226,237,255);color:#0E2D5F;}
    .panel-title {
    background-color:#6d9eeb;
    font-size: 18px;
    color: #fff;
    height: 30px;
    line-height: 30px;
    }
  </style>

<% 
   UserInfo user =null;
   String userName = request.getParameter("userName");
  
   String password = request.getParameter("password");
   String registNumber = request.getParameter("registNumber");
   String startTime = request.getParameter("startTime");


   int companyId = 0;

	   
   if(!"".equals(userName) && !"".equals(password)){
   String companyIdValue = request.getParameter("companyId");
   
   if(!"".equals(companyIdValue) && (companyIdValue != null)){
    
    companyId =Integer.parseInt(companyIdValue);
    user =UserInfo.findFirstBySql(UserInfo.class, "select * from TwoCodeUserInfo where loginName= ? and password = ? and isinvalid = 0",new Object[] { userName,password });
   }
   }
   if(user == null)
	   response.sendRedirect(request.getContextPath() +"/index.jsp");
   else{
	   request.getSession().setAttribute("sessionAccount", user);   
		  
		  Cookie uNCookie = new Cookie("userName", userName); 
		  Cookie pWCookie = new Cookie("password", password); 
		  uNCookie.setPath("/"); 
		  pWCookie.setPath("/");
		  response.addCookie(uNCookie);
		  response.addCookie(pWCookie);
	   
   }
   
%>
<script>

$(window).load(function(){

    big_banner(".dtl_focusbox",".dtl_crtimg",".dtl_focuslist","bar_crt");  

});

//图片展示函数
//参数说明：banner_panel 图片展示容器 可以是class，也可以是ID如果是前者用“.”+样式名，如果后者用“#”+样式名
//img_panel 图片容器
//bar_list 标题容器
//bar_crt 当前展示标题样式

var big_banner=function(banner_panel,img_panel,bar_list,bar_crt){
    var allNum = 0;
    var i, j;
    //获取图片总数
   var indexNum = $(img_panel).find("li:last-child").index();

   //载入第一副图片
//   readywImage(1,"240002","2018-08-02 16:14");
   readywImage(1,'<%=registNumber%>','<%=startTime%>');
   $(img_panel).find("li:first-child").fadeIn(1000); 

   //为标题列表添加鼠标滑动事件

  $(bar_list).find("li").mouseover(function(){

    var num = $(this).index();
    allNum= num;
//    readywImage(Number(num)+1,"240002","2018-08-02 16:14");
   readywImage(Number(num)+1,'<%=registNumber%>','<%=startTime%>');
    $(this).addClass(bar_crt).siblings().removeClass(bar_crt);
    $(img_panel).find("li").eq(num).fadeIn(800).siblings().hide();
   });

 //图片切换效果，如果您对js非常熟悉可以添加不同的效果
   function imgScroll(){
 //  readywImage(Number(allNum)+1,"240002","2018-08-02 16:14");
   readywImage(Number(allNum)+1,'<%=registNumber%>','<%=startTime%>');
   $(bar_list).find("li").eq(allNum).addClass(bar_crt).siblings().removeClass(bar_crt);
   $(img_panel).find("li").eq(allNum).fadeIn(1000).siblings().hide();
   allNum += 1;
   if(allNum>indexNum) 
	  allNum=0;
    }  

//为图片展示系统添加自动切换效果，这是一个定时器 每3秒滑动一副图片       
     var anima = setInterval(imgScroll,3000);           
     $(banner_panel).hover(
         function () {
          clearInterval(anima);
         },

         function () {
              anima = setInterval(imgScroll,3000);

            }

       ); 

}


var imgindexreadFlag1 =0;
var imgindexreadFlag2 =0;
var imgindexreadFlag3 =0;
var imgindexreadFlag4 =0;
var imgindexreadFlag5 =0;
var imgindexreadFlag6 =0;

function readywImage(imgindex,registNumber,startTime){

  
/**	var ywimg="";
	ywing ='<img height="400" align="center"   onerror="javascript:this.style.display='+'\'none\''+'" src="<%=request.getContextPath()%>'+'/servlet/ywNewImage.jpg?registNumber='+registNumber+'&startTime='+startTime+'&index=1&cmd=ywxq'+'" />';
	document.all.ywImg.innerHTML=ywing;
 **/

   if(imgindex == 1){
	if(imgindexreadFlag1 == 0){
	document.all.img1.src ='<%=request.getContextPath()%>'+'/servlet/ywNewImage.jpg?registNumber='+registNumber+'&startTime='+startTime+'&index=1&cmd=ywxq'; 
	imgindexreadFlag1 = 1;
	}
   }

   if(imgindex == 2){
		if(imgindexreadFlag2 == 0){
		document.all.img2.src ='<%=request.getContextPath()%>'+'/servlet/ywNewImage.jpg?registNumber='+registNumber+'&startTime='+startTime+'&index=2&cmd=ywxq'; 
		imgindexreadFlag2 = 1;
		}
	   }

   if(imgindex == 3){
		if(imgindexreadFlag3 == 0){
		document.all.img3.src ='<%=request.getContextPath()%>'+'/servlet/ywNewImage.jpg?registNumber='+registNumber+'&startTime='+startTime+'&index=3&cmd=ywxq'; 
		imgindexreadFlag3 = 1;
		}
	   }

   if(imgindex == 4){
		if(imgindexreadFlag4 == 0){
		document.all.img4.src ='<%=request.getContextPath()%>'+'/servlet/ywNewImage.jpg?registNumber='+registNumber+'&startTime='+startTime+'&index=4&cmd=ywxq'; 
		imgindexreadFlag4 = 1;
		}
	   }

   if(imgindex == 5){
		if(imgindexreadFlag5 == 0){
		document.all.img5.src ='<%=request.getContextPath()%>'+'/servlet/ywNewImage.jpg?registNumber='+registNumber+'&startTime='+startTime+'&index=5&cmd=ywxq'; 
		imgindexreadFlag5 = 1;
		}
	   }

   if(imgindex == 6){
		if(imgindexreadFlag6 == 0){
		document.all.img6.src ='<%=request.getContextPath()%>'+'/servlet/ywNewImage.jpg?registNumber='+registNumber+'&startTime='+startTime+'&index=6&cmd=ywxq'; 
		imgindexreadFlag6 = 1;
		}
	   }
 }

</script>
<style type="text/css">
.form_input {
  display: block;
  width: 100%;
  height: 34px;
  padding: 6px 12px;
  font-size: 14px;
  line-height: 1.42857143;
  color: #555;
  background-color: #fff;
  background-image: none;
  border: 1px solid #ccc;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
   box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
 -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
 -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
 transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
 }


.aaaa div{font-size:18px;
          border:0px;
          color:#000000;
          backgroundColor: '#F5F5F5',
          }


 ul{padding:0px; margin:0px;}

 li{margin:0px; padding:0px;}

.dtl_focusbox{ height:285px; width:543px; overflow:hidden; margin:0 auto;}

.dtl_focuslist{ height:30px; position:absolute; z-index:100; top:255px; width:543px;}

.bar_crt{color:#fff; background-color:#F00;}

.dtl_focuslist ul{ height:30px; background:#CCC; margin:0px; padding:0px; line-height:30px;filter:alpha(Opacity=60);-moz-opacity:0.4;opacity: 0.4; z-index:100;}

.dtl_focuslist li{display:inline; float:right; width:20px; height:20px; text-align:center; border:1px #FFF solid; margin:2px; line-height:20px; cursor:pointer;}

</style>
</head>
<body class="easyui-layout" data-options="fit:true" id="contextDivs">
<div id="cc" class="easyui-layout"  data-options="fit:true">
  <div data-options="region:'center',title:'',split:true" style="height:400px;">
    <div class="easyui-tabs" style="width:100%;height:250px" data-options="fit:true">
    
   <div title="运维详情图片">  
   <!--图片展示容器-->
   <div class="dtl_focusbox">
   
    <!--图片容器-->
    <div class="dtl_crtimg">
        <ul>                       
            <li><a href="" target="_self"><img id="img1" src="" width="543" height="385" border="0" alt=""/></a></li>                       
            <li><a href="" target="_self"><img id="img2" src="" width="543" height="385" border="0" alt=""/></a></li>                       
            <li><a href="" target="_self"><img id="img3" src="" width="543" height="385" border="0" alt=""/></a></li>                        
            <li><a href="" target="_self"><img id="img4" src="" width="543" height="385" border="0" alt=""/></a></li>                       
            <li><a href="" target="_self"><img id="img5" src="" width="543" height="385" border="0" alt=""/></a></li>  
            <li><a href="" target="_self"><img id="img6" src="" width="543" height="385" border="0" alt=""/></a></li>             
        </ul>
    </div>
    
    <!--标题容器-->
    <div class="dtl_focuslist">
        <ul>
            <li class="bar_crt">1</li>
            <li>2</li>
            <li>3</li>
            <li>4</li>
            <li>5</li>           
            <li>6</li>
        </ul>
    </div>
    
       
   </div>
   
   </div> 
       

    </div>
   </div>  
</div>
</body>
</html>