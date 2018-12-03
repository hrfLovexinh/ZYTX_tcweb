<%@ page import="com.zytx.models.*" language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>成都市电梯安全公共服务平台</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>

<!--  <script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script> -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=6Kx0GQ8wm05QPnrnpoy9IvfLmDxNMxc0"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/TrafficControl/1.4/src/TrafficControl_min.js"></script>


<style type="text/css">
body, html, #testMap{width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
.anchorBL{display:none}
</style>
<meta http-equiv="pragma" content="no-cache">

<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">





</head>
<body>
<div class="easyui-layout" style="width:98%;height:850px;">
<div region="west" split="true" title="操作" style="width:150px;">
<!--  
		<p></p>
		<ul>
			<li><a href="javascript:void(0)" onclick="showcontent('alarm')">报警电梯</a></li>
			<p></p>
			<li><a href="javascript:void(0)" onclick="showcontent('tousu')">投诉电梯</a></li>
			
		</ul>  -->
	<div id="titlebar" style="padding:2px">
      <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100%" data-options="iconCls:'layout-button-down'" onclick="$('#cc').layout('expand','west')"></a>
        <a href="javascript:void(0)" onclick="showcontent('alarm')" class="easyui-linkbutton" style="width:100%" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'">报警电梯</a>
        <a href="javascript:void(0)" onclick="showcontent('tousu')"  class="easyui-linkbutton" style="width:100%" data-options="iconCls:'icon-large-shapes',size:'large',iconAlign:'top'">投诉电梯</a>
  <!--    <a href="javascript:void(0)"  onclick="showcontent('all')" class="easyui-linkbutton" style="width:100%" data-options="iconCls:'icon-large-smartart',size:'large',iconAlign:'top'">所有电梯</a> -->   
        <a href="javascript:void(0)" onclick="showcontent('all')" class="easyui-linkbutton"   style="width:100%"  data-options="iconCls:'icon-large-chart',size:'large',iconAlign:'top'">所有电梯</a> 
    
    </div>
   
		
	</div>
<div id="testMap" region="center" title="电梯标注点分布" style="padding:5px;"></div>

</div>
<script type="text/javascript">
var count = 0;
var map;
map = new BMap.Map("testMap"); //创建Map实例
map.centerAndZoom(new BMap.Point(104.047, 30.6285), 15); //初始化地图，设置中心点坐标和地图级别


map.addControl(new BMap.MapTypeControl()); //添加地图类型控件
//map.addControl(new BMap.OverviewMapControl()); //添加缩略地图控件

map.addControl(new BMap.NavigationControl()); //添加地图缩放控件
map.addControl(new BMap.ScaleControl()); //添加比例尺控件

map.setCurrentCity("成都"); //设置地图显示的城市，这项是必须的
map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放

//对地图级别变化、移动结束和图块加载完毕后，进行添加marker的操作
//map.addEventListener("tilesloaded", showLayer);
map.addEventListener("zoomend", showLayer);
map.addEventListener("moveend", showLayer);

/*
//添加一个矩形覆盖物
var polyline = new BMap.Polyline([
                                  new BMap.Point(116.279655,40.020499),
                                  new BMap.Point(116.260683,39.833259),
                                  new BMap.Point(116.532043,39.830599),
                                  new BMap.Point(116.526869,40.021383),
                                  new BMap.Point(116.279655,40.020499)
                                ], {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});
                                map.addOverlay(polyline); */

var layerName ="";
function showcontent(opt){
	layerName = opt;
	showLayer();
}


function showLayer(){	
	if("alarm" == layerName ){
		map.clearOverlays();  
		markers.splice(0,markers.length);//清空数组
		if (customLayer) {
			map.removeTileLayer(customLayer);
		}
		addCustomLayer();
		}
	if("tousu" == layerName){ 
		map.clearOverlays(); 
		markers.splice(0,markers.length);//清空数组
		if (customLayer) {
			map.removeTileLayer(customLayer);
		}
		addTouSuCustomLayer();
	}

	if("all" == layerName){
		map.clearOverlays(); 
		addAllCustomLayer();
		}
}



var customLayer;

function addAllCustomLayer(){
		if (customLayer) { 
			map.removeTileLayer(customLayer);
		}
		customLayer=new BMap.CustomLayer({
			geotableId: 167274,
			q: '', //检索关键字
			tags: '', //空格分隔的多字符串
			filter: '' //过滤条件,参考http://developer.baidu.com/map/lbs-geosearch.htm#.search.nearby
		});   
		map.addTileLayer(customLayer);  
		
	}


function  addCustomLayer() { 
	alarmPoint();
}

function addTouSuCustomLayer(){
	tousuPoint();
}

/*
function callback(e)//单击热点图层
{
		var customPoi = e.customPoi;//poi的默认字段
		var contentPoi=e.content;//poi的自定义字段
		var content = '<p style="width:280px;margin:0;line-height:20px;">地址：' + customPoi.address + '<br/>价格:'+contentPoi.dayprice+'元'+'</p>';
		var searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
			title: customPoi.title, //标题
			width: 290, //宽度
			height: 40, //高度
			panel : "panel", //检索结果面板
			enableAutoPan : true, //自动平移
			enableSendToPhone: true, //是否显示发送到手机按钮
			searchTypes :[
				BMAPLIB_TAB_SEARCH,   //周边检索
				BMAPLIB_TAB_TO_HERE,  //到这里去
				BMAPLIB_TAB_FROM_HERE //从这里出发
			]
		});
		var point = new BMap.Point(customPoi.point.lng, customPoi.point.lat);
		searchInfoWindow.open(point);
}
*/

var markers = []; 
function tousuPoint(){
	if(markers.length ==0){
		jQuery.post('/tcweb/elevator/tousuListElevatorInfo',function(data){ 
			          data = eval(data);//POST方法必加，ajax方法自动处理了   
			          var datalist = data.tousuEleInfo;  
			         for(var i=0;i<datalist.length;i++){
			     //   	  alert(datalist[i].map_X+" "+datalist[i].map_Y);    
			             
			              var lng =datalist[i].map_X;
			              var lat =datalist[i].map_Y;  
			              var registNumber =datalist[i].registNumber;  
			        	  markers[i] = {position:{lat:lat,lng:lng},registNumber:registNumber};  
				          } 
			         addMapOverlay();
				   }, 'json');
		}
		else{
			 addMapOverlay();  
			}

}



/*
 * 查询报警信息
 */


function alarmPoint(){  
	if(markers.length ==0){
	jQuery.post('/tcweb/elevator/alarmListElevatorInfo',function(data){ 
		          data = eval(data);//POST方法必加，ajax方法自动处理了   
		          var datalist = data.alarmEleInfo;  
		         for(var i=0;i<datalist.length;i++){
		     //   	  alert(datalist[i].map_X+" "+datalist[i].map_Y);    
		             
		              var lng =datalist[i].map_X;
		              var lat =datalist[i].map_Y;
		              var registNumber =datalist[i].registNumber;
		        	  markers[i] = {position:{lat:lat,lng:lng},registNumber:registNumber};  
			          } 
		         addMapOverlay();
			   }, 'json');
	}
	else{
		 addMapOverlay();  
		}

  
}


//设置点Icon,marker
function addMapOverlay(){
	 map.clearOverlays();
	 var iconImg = createIcon();
	 /*
	 var bs = map.getBounds();   //获取可视区域
	 var bssw = bs.getSouthWest();   //可视区域左下角
	 var bsne = bs.getNorthEast();   //可视区域右上角   
	 alert("当前地图可视范围是：" + bssw.lng + "," + bssw.lat + "到" + bsne.lng + "," + bsne.lat);  */
	 for(var index = 0; index < markers.length; index++ ){
		    var bound=map.getBounds();//地图可视区域  
		    var point = new BMap.Point(markers[index].position.lng,markers[index].position.lat);
		    var registNumber = markers[index].registNumber;
		    
		    
		    if(bound.containsPoint(point)==true){ 
	 //       var marker = new BMap.Marker(point,{icon:iconImg}); 
	      
	        
	       
	 //       marker.setAnimation(BMAP_ANIMATION_BOUNCE); 
	 //       marker.enableDragging(); 
	       
	        map.addOverlay(creatMaker(point,iconImg,registNumber)); 
		    }
	             
	   };
}


function creatMaker(node,iconImg, info_html){
//	var _marker = new BMap.Marker(new BMap.Point(node.lng, node.lat),{icon:iconImg});
   var _marker = new BMap.Marker(new BMap.Point(node.lng, node.lat));
	 var infowindow =crateInfo("编号:"+info_html);
	_marker.addEventListener("click", function(){ this.openInfoWindow(infowindow);});
	return _marker;
}



//创建一个Icon
function createIcon(){
    var icon = new BMap.Icon("http://map.baidu.com/image/us_mk_icon.png", new BMap.Size(21,21),{ offset: new BMap.Size(10, 25), imageOffset: new BMap.Size(0, 0 - 1* 25)});
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
