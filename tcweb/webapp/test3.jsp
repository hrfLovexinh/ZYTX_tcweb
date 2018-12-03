<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
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

<script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script> 

<style type="text/css">
.anchorBL{display:none}
</style>
</head> 
<body> 

<div id="container"></div> 
<% double longitude =Double.parseDouble(request.getParameter("longitude"));
   double latitude = Double.parseDouble(request.getParameter("latitude"));
  System.out.println("longitude---"+longitude);
  System.out.println("latitude---"+latitude);
  if(0.0==longitude)
	  longitude=104.06;
  if(0.0==latitude)
	  latitude=30.67;
  
%> 
<script type="text/javascript">  
var map = new BMap.Map("container");// 创建地图实例   
var point_x ='<%=longitude%>';
var point_y ='<%=latitude%>';
//alert("point_x"+point_x);
//alert("point_y"+point_y);
//var point = new BMap.Point(104.06, 30.67);  // 创建点坐标   
var point = new BMap.Point(point_x, point_y);
map.centerAndZoom(point, 15); // 初始化地图，设置中心点坐标和地图级别   

/* panTo()方法将让地图平滑移动至新中心点
window.setTimeout(function(){     
  map.panTo(new BMap.Point(116.409, 39.918));     
  }, 2000);  
*/

//平移控件
map.addControl(new BMap.NavigationControl());  
//缩略图控件
map.addControl(new BMap.ScaleControl());     
//缩略图控件
//map.addControl(new BMap.OverviewMapControl()); 
//地图类型控件
map.addControl(new BMap.MapTypeControl());   

var marker = new BMap.Marker(point);        // 创建标注   
map.addOverlay(marker);                     // 将标注添加到地图中 

//监听标注事件
/*
marker.addEventListener("click", function(){     
	 alert("您点击了标注");     
	});    
*/

var lng;
var lat;
marker.enableDragging();     
marker.addEventListener("dragend", function(e){     
//alert("当前位置：" + e.point.lng + ", " + e.point.lat);   

lng =e.point.lng;
lat =e.point.lat;

window.parent.plng=lng;
window.parent.plat=lat;
}
);    


</script>   





</body>
</html>