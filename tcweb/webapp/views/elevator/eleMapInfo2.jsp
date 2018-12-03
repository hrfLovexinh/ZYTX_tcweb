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
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>


<style type="text/css">
body, html, #testMap{width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";padding:0}
.anchorBL{display:none}
</style>
<meta http-equiv="pragma" content="no-cache">

<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">





</head>
<body>
<div class="easyui-layout" style="width:100%;height:100%;">
<!-- 
<div region="west" split="true" title="操作" style="width:150px;background-color:#696969">

	<div id="titlebar" style="padding:2px">
      <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100%" data-options="iconCls:'layout-button-down'" onclick="$('#cc').layout('expand','west')"></a>
        <a href="javascript:void(0)" onclick="showcontent('alarm')" class="easyui-linkbutton" style="width:100%" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'">报警电梯</a>
        <a href="javascript:void(0)" onclick="showcontent('tousu')"  class="easyui-linkbutton" style="width:100%" data-options="iconCls:'icon-large-shapes',size:'large',iconAlign:'top'">投诉电梯</a>
       <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100%" data-options="iconCls:'icon-large-smartart',size:'large',iconAlign:'top'">SmartArt</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"   style="width:100%"  data-options="iconCls:'icon-large-chart',size:'large',iconAlign:'top'">Chart</a> 
    
    </div>
   
		
	</div>    -->
<div id="testMap" region="center" title="电梯分布" style="padding:0px;"></div>
<div data-options="region:'east',split:true" title="电梯实时数据" style="width:300px;">
<div class="easyui-panel" title="" style="width:290px;padding:30px 60px;border:0px">
<div style="margin-bottom:20px">
<div style="background-color:#F0F8FF">电梯总数:</div>
			<input id="etotal" class="easyui-textbox"  style="width:100%;height:32px;border:0px;" readOnly>
</div>
<div style="margin-bottom:20px">
<div style="background-color:#F0F8FF">停用电梯总数:</div>
			<input id="setotal" class="easyui-textbox"  style="width:100%;height:32px;border:0px;" readOnly>
</div>
<div style="margin-bottom:20px">
<div style="background-color:#F0F8FF">涉密电梯总数:</div>
			<input id="smetotal" class="easyui-textbox"  style="width:100%;height:32px;border:0px;" readOnly>
</div>
<div style="margin-bottom:20px">
			<div style="background-color:#F0F8FF">超期电梯数:</div>
			<input id="ncqetotal" class="easyui-textbox" style="width:100%;height:32px;border:0px;" readOnly>
</div>
<!--  
<div style="margin-bottom:20px">
			<div>投诉未处理电梯数:</div>
			<input id="undotsrtotal" class="easyui-textbox" style="width:100%;height:32px;border:0px;" readOnly>
</div>  -->
</div>
</div>

<div id ="testMaptj" region="south" split="false" collapsed="true" title="区域统计" style="padding:0px;height:250px;"></div>

</div>
<script type="text/javascript">
Number.prototype.toFixed=function (d) { 
	                  var s=this+""; 
	                  if(!d)d=0; 
	                  if(s.indexOf(".")==-1)s+="."; 
	                  s+=new Array(d+1).join("0"); 
	                 if(new RegExp("^(-|\\+)?(\\d+(\\.\\d{0,"+(d+1)+"})?)\\d*$").test(s)){
	                     var s="0"+RegExp.$2,pm=RegExp.$1,a=RegExp.$3.length,b=true;
	                     if(a==d+2){
                         a=s.match(/\d/g); 
	                         if(parseInt(a[a.length-1])>4){
	                             for(var i=a.length-2;i>=0;i--){
	                                 a[i]=parseInt(a[i])+1;
	                                 if(a[i]==10){
	                                     a[i]=0;
	                                     b=i!=1;
	                                }else break;
                                }
	                         }
	                         s=a.join("").replace(new RegExp("(\\d+)(\\d{"+d+"})\\d$"),"$1.$2");
	 
	                     }if(b)s=s.substr(1); 
	                     return (pm+s).replace(/\.$/,"");
	                 }return this+"";
	 
	             };

function noMenuOne()
	    {
	       return false;
	    }
document.oncontextmenu = noMenuOne;

var count = 0;
var map;
map = new BMap.Map("testMap"); //创建Map实例
map.centerAndZoom(new BMap.Point(104.047, 30.6285), 10); //初始化地图，设置中心点坐标和地图级别


map.enableScrollWheelZoom(true);//开启鼠标滚轮缩放
//比例尺
var scale = new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT}); //左下角，添加比例尺
map.addControl(scale);
//地图平移缩放控件
var control = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_LEFT});  //左上角，添加默认缩放平移控件
map.addControl(control);
//缩略地图控件，鹰眼
//var overView = new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT});
//map.addControl(overView);

/*
map.setMapStyle({
    styleJson:[
      {
                "featureType": "road",
                "elementType": "all",
                "stylers": {
                          //"color": "#ffffff",
                          "visibility": "off"
                }
      }
]
}); 
*/

map.setMapStyle({
	   styleJson:[
	         {
             "featureType": "poi",
             "elementType": "all",
             "stylers": {
                       "color": "#ffffff",
                       "visibility": "off"
             }
	          },
	          {
	                    "featureType": "road",
	                    "elementType": "all",
	                    "stylers": {
	                              "color": "#ffffff",
	                              "visibility": "off"
	                    }
	          },
	          {
	                    "featureType": "background",
	                    "elementType": "all",
	                    "stylers": {
	                              "color": "#ffffff"
	                    }
	          },
	          {
	                    "featureType": "administrative",
	                    "elementType": "all",
	                    "stylers": {
	                              "color": "#ffffff",
	                              "visibility": "off"
	                    }
	          }
		]
	}); 

eletjEcharts();
madeBoundary();
eleMapRightTJ();   //右侧电梯实时数据面板统计
// addAllCustomLayer();   //添加麻点图
/*
var bdary = new BMap.Boundary();
bdary.get("成都市", function(rs){       //获取行政区域      
	var count = rs.boundaries.length; //行政区域的点有多少个
	if (count === 0) {
		return ;
	}
  var pointArray = [];
		for (var i = 0; i < count; i++) {
			var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor:""}); //建立多边形覆盖物
			map.addOverlay(ply); //添加覆盖物
			ply.type = "distinct";
			ply.disableMassClear();
			pointArray = pointArray.concat(ply.getPath());
		}    
});
*/


// var	xdata =[ '锦江','青羊','金牛','武侯','成华','高新','龙泉驿','青白江','新都','温江','金堂','双流','郫县','大邑','蒲江','新津','都江堰','彭州','邛崃','崇州','简阳','天府新'];
// var    ydata =[1, 6, 3, 4, 5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,22,21];




//区域图
function madeBoundary() {
    
var datas = new Array("成都市-#CCCCCC","武侯区-#1199cc","金牛区-#1199cc","青羊区-#1199cc","锦江区-#1199cc","成华区-#1199cc","龙泉驿区-#CCCCCC","青白江区-#CCCCCC","新都区-#CCCCCC","温江区-#CCCCCC","金堂县-#E0FFFF","双流区-#CCCCCC","郫县-#CCCCCC","大邑县-#E0FFFF","蒲江县-#E0FFFF","新津县-#E0FFFF","彭州市-#D8BFD8","邛崃市-#D8BFD8","崇州市-#D8BFD8","都江堰市-#D8BFD8","简阳市-#D8BFD8");
    var bdary = new BMap.Boundary();
    for(var i=0;i<datas.length;i++){
    getBoundary(datas[i],bdary);
        
    } 
    
} 
                      
//设置区域图
function getBoundary(data,bdary){    
  data = data.split("-");
    bdary.get(data[0], function(rs){       //获取行政区域
        var count = rs.boundaries.length; //行政区域的点有多少个

        var pointArray = [];
        for (var i = 0; i < count; i++) {
            var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#0099FF",fillOpacity:0.5,fillColor:data[1]}); //建立多边形覆盖物
            
            map.addOverlay(ply);  //添加覆盖物
            PolygonCenter(ply,data[0]);  //覆盖物添加label
        }                        
    });   
}

var plCenterlng = 0;
var plCenterlat = 0;
var tagMarkerIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
    imageOffset: new BMap.Size(0, 0 - 13 * 25) // 设置图片偏移  
});
function PolygonCenter(pl,areaName){
	 plCenterlng = 0;
	 plCenterlat = 0;
	 var path = pl.getPath();//Array<Point> 返回多边型的点数组
	 for(var i=0;i<path.length;i++){
     //    console.log("lng:"+path[i].lng+"\n lat:"+path[i].lat);
		 plCenterlng =plCenterlng + path[i].lng;
		 plCenterlat =plCenterlat + path[i].lat;
     }
   //  alert(plCenterlng/path.length);
  //   alert(plCenterlat/path.length);
     
     var marker = new BMap.Marker(new BMap.Point(plCenterlng/path.length, plCenterlat/path.length),{ icon: tagMarkerIcon });
     var areaEtotal =0;
     var areaEtotalp =0;
     if(areaName=="金牛区"){
		 areaEtotal=jnetotal;
		 areaEtotalp=jnetotalp;
		 }
     if(areaName=="青羊区"){
    	 areaEtotal=qyetotal;
    	 areaEtotalp=qyetotalp;
     }
     if(areaName=="高新区"){
    	 areaEtotal=gxetotal;
    	 areaEtotalp=gxetotalp;
     }
     if(areaName=="彭州市"){
    	 areaEtotal=pzetotal;
    	 areaEtotalp=pzetotalp;
     }
     if(areaName=="锦江区"){
    	 areaEtotal=jjetotal;
    	 areaEtotalp=jjetotalp;
     }
     if(areaName=="武侯区"){
    	 areaEtotal=whetotal;
    	 areaEtotalp=whetotalp;
     }
     if(areaName=="成华区"){
    	 areaEtotal=chetotal;
    	 areaEtotalp=chetotalp;
     }
     if(areaName=="龙泉驿区"){
    	 areaEtotal=lqyetotal;
    	 areaEtotalp=lqyetotalp;
     }
     if(areaName=="金堂县"){
    	 areaEtotal=jtetotal;
    	 areaEtotalp=jtetotalp;
     }
     if(areaName=="青白江区"){
    	 areaEtotal=qbjetotal;
    	 areaEtotalp=qbjetotalp;
     }
     if(areaName=="郫县"){
    	 areaEtotal=pxetotal;
    	 areaEtotalp=pxetotalp;
     }
     if(areaName=="新都区"){
    	 areaEtotal=xdetotal;
    	 areaEtotalp=xdetotalp;
     }
     if(areaName=="温江区"){
    	 areaEtotal=wjetotal;
    	 areaEtotalp=wjetotalp;
     }
     if(areaName=="双流区"){
    	 areaEtotal=sletotal;
    	 areaEtotalp=sletotalp;
     }
     if(areaName=="大邑县"){
    	 areaEtotal=dyetotal;
    	 areaEtotalp=dyetotalp;
     }
     if(areaName=="新津县"){
    	 areaEtotal=xjetotal;
    	 areaEtotalp=xjetotalp;
     }
     if(areaName=="蒲江县"){
    	 areaEtotal=pjetotal;
    	 areaEtotalp=pjetotalp;
     }
     if(areaName=="都江堰市"){
    	 areaEtotal=djyetotal;
    	 areaEtotalp=djyetotalp;
     }
     if(areaName=="邛崃市"){
    	 areaEtotal=qletotal;
    	 areaEtotalp=qletotalp;
     }
     if(areaName=="崇州市"){
    	 areaEtotal=czetotal;
    	 areaEtotalp=czetotalp;
     }
     if(areaName=="简阳市"){
    	 areaEtotal=jyetotal;
    	 areaEtotalp=jyetotalp;
     }
     if(areaName=="天府新区"){
    	 areaEtotal=tfxetotal;
    	 areaEtotalp=tfxetotalp;
     }

     if(areaName!="成都市"){
         if(areaName=="武侯区"){
        	 var label = new BMap.Label(areaName+":"+areaEtotal+"<br>"+"("+areaEtotalp+"%"+")"+"<br>"+"高新区"+":"+gxetotal+"<br>"+"("+gxetotalp+"%"+")"+"&nbsp;&nbsp;"+"天府新区"+":"+tfxetotal+"&nbsp;"+"("+tfxetotalp+"%"+")",  { offset: new BMap.Size(-15, 2) });
             }
         else{
          var label = new BMap.Label(areaName+":"+areaEtotal+"<br>"+"("+areaEtotalp+"%"+")",  { offset: new BMap.Size(-15, 2) });
         }
     label.setStyle({ 
    	 color : "#808080", 
    	 fontSize : "6px", 
    	 backgroundColor :"0.05",
    	 border :"0", 
    	 fontWeight :"bold" 
    	 });
     marker.setLabel(label);
     map.addOverlay(marker);
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

var jnetotal =0;   //金牛
var qyetotal =0;   //青羊
var gxetotal = 0;  //高新
var pzetotal =0;   //彭州
var jjetotal =0;   //锦江
var whetotal =0;   //武侯
var chetotal =0;   //成华
var lqyetotal =0;   //龙泉驿
var jtetotal =0;    //金堂
var qbjetotal =0;   //青白江
var pxetotal =0;   //郫县
var xdetotal =0;   //新都
var wjetotal =0;   //温江
var sletotal =0;   //双流
var dyetotal =0;   //大邑
var pjetotal =0;   //蒲江
var xjetotal =0;   //新津
var djyetotal =0;  //都江堰
var qletotal =0;   //邛崃
var czetotal =0;   //崇州
var jyetotal =0;   //简阳
var tfxetotal =0;  //天府新
function mapAreaDataInitial(area,etotal){
	if("金牛" == area){
		jnetotal = etotal;		
	}
	if("青羊" == area)
		qyetotal = etotal;
	if("高新" == area)
		gxetotal = etotal;
	if("彭州" == area)
		pzetotal = etotal;
	if("锦江" == area)
		jjetotal = etotal;
	if("武侯" == area)
		whetotal = etotal;
	if("成华" == area)
		chetotal = etotal;
	if("龙泉驿" == area)
		lqyetotal = etotal;
	if("金堂" == area)
		jtetotal = etotal;
	if("青白江" == area)
		qbjetotal = etotal;
	if("郫县" == area)
		pxetotal = etotal;
	if("新都" == area)
		xdetotal = etotal;
	if("温江" == area)
		wjetotal = etotal;
	if("双流" == area)
		sletotal = etotal;
	if("大邑" == area)
		dyetotal = etotal;
	if("蒲江" == area)
		pjetotal = etotal;
	if("新津" == area)
		xjetotal = etotal;
	if("都江堰" == area)
		djyetotal = etotal;
	if("邛崃" == area)
		qletotal = etotal;
	if("崇州" == area)
		czetotal = etotal;
	if("简阳" == area)
		jyetotal = etotal;
	if("天府新" == area)
		tfxetotal = etotal;
	
}

var jnetotalp =0;   //金牛
var qyetotalp =0;   //青羊
var gxetotalp = 0;  //高新
var pzetotalp =0;   //彭州
var jjetotalp =0;   //锦江
var whetotalp =0;   //武侯
var chetotalp =0;   //成华
var lqyetotalp =0;   //龙泉驿
var jtetotalp =0;    //金堂
var qbjetotalp =0;   //青白江
var pxetotalp =0;   //郫县
var xdetotalp =0;   //新都
var wjetotalp =0;   //温江
var sletotalp =0;   //双流
var dyetotalp =0;   //大邑
var pjetotalp =0;   //蒲江
var xjetotalp =0;   //新津
var djyetotalp =0;  //都江堰
var qletotalp =0;   //邛崃
var czetotalp =0;   //崇州
var jyetotalp =0;   //简阳
var tfxetotalp =0;  //天府新
function etotalPercentByArea(){
	var alletoal =jnetotal+qyetotal+gxetotal+pzetotal+jjetotal+whetotal+chetotal+lqyetotal+jtetotal+qbjetotal+pxetotal+xdetotal+wjetotal+sletotal+dyetotal+pjetotal+xjetotal+djyetotal+qletotal+czetotal+jyetotal+tfxetotal;
	jnetotalp = Number(Number(jnetotal/alletoal).toFixed(4)*100).toFixed(2);  
	qyetotalp = Number(Number(qyetotal/alletoal).toFixed(4)*100).toFixed(2);
	gxetotalp = Number(Number(gxetotal/alletoal).toFixed(4)*100).toFixed(2);
	pzetotalp = Number(Number(pzetotal/alletoal).toFixed(4)*100).toFixed(2);
	jjetotalp = Number(Number(jjetotal/alletoal).toFixed(4)*100).toFixed(2);
	whetotalp = Number(Number(whetotal/alletoal).toFixed(4)*100).toFixed(2);
	chetotalp = Number(Number(chetotal/alletoal).toFixed(4)*100).toFixed(2);
	lqyetotalp = Number(Number(lqyetotal/alletoal).toFixed(4)*100).toFixed(2);
	jtetotalp = Number(Number(jtetotal/alletoal).toFixed(4)*100).toFixed(2);
	qbjetotalp = Number(Number(qbjetotal/alletoal).toFixed(4)*100).toFixed(2);
	pxetotalp = Number(Number(pxetotal/alletoal).toFixed(4)*100).toFixed(2);
	xdetotalp = Number(Number(xdetotal/alletoal).toFixed(4)*100).toFixed(2);
	wjetotalp = Number(Number(wjetotal/alletoal).toFixed(4)*100).toFixed(2);
	sletotalp = Number(Number(sletotal/alletoal).toFixed(4)*100).toFixed(2);
	dyetotalp = Number(Number(dyetotal/alletoal).toFixed(4)*100).toFixed(2);
	pjetotalp = Number(Number(pjetotal/alletoal).toFixed(4)*100).toFixed(2);
	xjetotalp = Number(Number(xjetotal/alletoal).toFixed(4)*100).toFixed(2);
	djyetotalp = Number(Number(djyetotal/alletoal).toFixed(4)*100).toFixed(2);
	qletotalp = Number(Number(qletotal/alletoal).toFixed(4)*100).toFixed(2);
	czetotalp = Number(Number(czetotal/alletoal).toFixed(4)*100).toFixed(2);
	jyetotalp = Number(Number(jyetotal/alletoal).toFixed(4)*100).toFixed(2);
	tfxetotalp = Number(Number(tfxetotal/alletoal).toFixed(4)*100).toFixed(2);
}

var	xdata = new Array();

var ydata = new Array();
var ydata2 =new Array();
var ydata3 = new Array();
function eletjEcharts(){
	jQuery.post('/tcweb/elevator/eletjEcharts', function(data){ 
        data = eval(data);//POST方法必加，ajax方法自动处理了    
       
        var datalist = data.tjEcharts;     
        for(var i=0;i<datalist.length;i++){
           var xname =datalist[i].area;
           var yname =datalist[i].etotal;
           var ncqetotal= datalist[i].ncqetotal;
           var tsrtotal=datalist[i].tsrtotal;
           xdata.push(xname);
         
           ydata.push(yname);
           ydata2.push(ncqetotal);
           ydata3.push(tsrtotal);

           mapAreaDataInitial(xname,yname);
            }
        etotalPercentByArea();
        drawEcharts();
	   }, 'json');
}

function drawEcharts(){ // alert("xdata--->"+xdata);
	// 路径配置
	require.config({
	    paths: {
	        echarts: 'http://echarts.baidu.com/build/dist'
	    }
	});


	//使用
	require(
	    [
	        'echarts',
	        'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
	    ],
	    function (ec) {
	        // 基于准备好的dom，初始化echarts图表
	        var myChart = ec.init(document.getElementById('testMaptj')); 
	        
	        var option = {
	            tooltip: {
	                show: true
	            },
	      //      color:['#08a9f2','#FF6633'],
	            legend: {
	                data:['电梯数量','运维超期数量','投诉未处理数量']
	            },   
	            grid: {
	                left: '3%',
	                right: '4%',
	                bottom: '3%',
	                containLabel: true
	            },
	            xAxis : [
	                {   
	                	splitLine:{show: false},//去除网格线
	                    type : 'category',
	                    data : xdata
	                }
	            ],
	            yAxis : [
	                {
	               // 	splitLine:{show: false},//去除网格线
	                    type : 'value'
	                    
	                }
	            ],
	            series : [
	                {
	                    "name":"电梯数量",
	                    "type":"bar",
	                    "data":ydata,
	                    itemStyle:{  
                        normal:{color:'#08a9f2'}  
                    }  
	                },
	                {
	                	 "name":"运维超期数量",
		                 "type":"bar",
		                 "data":ydata2,
		               //设置柱体颜色  
                         itemStyle:{  
                             normal:{color:'#FF6633'}  
                         }  
		                
		            },
		            {
	                	 "name":"投诉未处理数量",
		                 "type":"bar",
		                 "data":ydata3,
		               //设置柱体颜色  
                        itemStyle:{  
                            normal:{color:'#33CC66'}  
                        }  
		                
		            }
	            ]
	        };
	        
	        // 为echarts对象加载数据 
	        myChart.setOption(option); 
	    }
	);
}

function eleMapRightTJ(){
	jQuery.post('/tcweb/elevator/eleMapRightTJ', function(data){ 
        data = eval(data);//POST方法必加，ajax方法自动处理了    
        $('#etotal').attr("value",data.etotal);
        $('#setotal').attr("value",data.setotal);
        $('#smetotal').attr("value",data.smetotal);
        $('#ncqetotal').attr("value",data.ncqetotal);
    //    $('#undotsrtotal').attr("value",data.undotsrtotal);
       
	   }, 'json');
}

</script>

</body>
</html>
