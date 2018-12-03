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
<% double map_x =Double.parseDouble(request.getParameter("map_x"));
   double map_y =Double.parseDouble(request.getParameter("map_y"));
   double map_x0 =Double.parseDouble(request.getParameter("map_x0"));
   double map_y0 =Double.parseDouble(request.getParameter("map_y0"));
   
  System.out.println("map_x---"+map_x);
  System.out.println("map_y---"+map_y);
  System.out.println("map_x0---"+map_x0);
  System.out.println("map_y0---"+map_y0);
 
  /*
  if(0.0==longitude)
	  longitude=104.06;
  if(0.0==latitude)
	  latitude=30.67;
  */
%> 
<script type="text/javascript">  
var map = new BMap.Map("container");// 创建地图实例   
var point_x ='<%=map_x%>';
var point_y ='<%=map_y%>';
var point_x0 ='<%=map_x0%>';
var point_y0 ='<%=map_y0%>';

//alert("point_x"+point_x);
//alert("point_y"+point_y);
//var point = new BMap.Point(104.06, 30.67);  // 创建点坐标   
var point;
var point0;
var point1;
var point2;

if(point_x>0 && point_y>0){
var point = new BMap.Point(point_x, point_y);
map.centerAndZoom(point, 15); // 初始化地图，设置中心点坐标和地图级别   
}
else{
	map.centerAndZoom(new BMap.Point(104.06, 30.67),15);	
}
if(point_x0>0 && point_y0>0){
	point0 = new BMap.Point(point_x0, point_y0);
//	map.centerAndZoom(point0, 15); // 初始化地图，设置中心点坐标和地图级别   
	}

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

if(point){ 
var marker = new BMap.Marker(point);        // 创建标注
map.addOverlay(marker);   // 将标注添加到地图中 
} 

if(point0){
var iconImg0 = createIcon();
var infowindow0 =crateInfo("报警点");
var marker0 = new BMap.Marker(point0,{icon:iconImg0});        // 创建标注
map.addOverlay(marker0);  // 将标注添加到地图中 
marker0.addEventListener("click", function(){ this.openInfoWindow(infowindow0);});
}


                     

//监听标注事件
/*
marker.addEventListener("click", function(){     
	 alert("您点击了标注");     
	});    
*/

/*
var lng;
var lat;
if(marker){
marker.enableDragging();     
marker.addEventListener("dragend", function(e){     
lng =e.point.lng;
lat =e.point.lat;

window.parent.plng=lng;
window.parent.plat=lat;
}
);    
}
*/
//创建一个Icon
function createIcon(){
    var icon = new BMap.Icon("http://app.baidu.com/map/images/us_mk_icon.png", new BMap.Size(21,21),{ offset: new BMap.Size(10, 25), imageOffset: new BMap.Size(0, 0 - 1* 25)});
    return icon;
}

//创建信息窗口
function crateInfo(info){ 
	var opts = {
			 width : 5,     // 信息窗口宽度 
			 height: 5,     // 信息窗口高度 
			 title : ""  // 信息窗口标题 
	};
var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>"+info,opts);
return infoWindow;
}
</script>   





</body>
</html>