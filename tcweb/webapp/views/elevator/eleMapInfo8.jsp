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


<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=6Kx0GQ8wm05QPnrnpoy9IvfLmDxNMxc0"></script> 
<script type="text/javascript" src="http://api.map.baidu.com/library/TrafficControl/1.4/src/TrafficControl_min.js"></script>
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>


<style type="text/css">
body, html, #testMap{width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";padding:0}
.anchorBL{display:none}
.abc{ font-size: 14px;}
.bcd{ font-size: 6px;} 
.shuju{font-size: 14px;}
.piontimg{
display:block;
height:13px;
width:13px;
background:url("area.png") left no-repeat;
}
@media screen and (min-width: 1300px) { 
.abc {font-size: 14px;} 
.bcd {font-size: 6px;}
.piontimg{
display:block;
height:13px;
width:13px;
background:url("area.png") left no-repeat;
}

} 
/* css注释：设置了浏览器宽度不小于1300px时  */ 

@media screen and (max-width: 1300px) {
 
.abc {font-size: 2px;-webkit-transform:scale(0.8);} 
.bcd {font-size: 1px;-webkit-transform:scale(0.8);}
.piontimg{
display:block;
height:6px;
width:6px;
background:url("area2.png") left no-repeat;
} 

} 
/* 设置了浏览器宽度不大于1300px时  */ 

body, html,#testMap{width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
</style>
<meta http-equiv="pragma" content="no-cache">

<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">





</head>
<body class="easyui-layout" data-options="fit:true" style="background-color:rgb(240,252,255);">
<div id="testMap" region="center" title="电梯分布数量及比例" style="padding:0px;margin:0px;width:50%" ></div>

<div id ="shuju" data-options="region:'east',split:false" title="电梯实时数据" style="background-color:rgb(240,252,255);width:40%">
<div class="easyui-layout" style="height:100%;" data-options="fit:true">
 <div id="testMapsj" class="testMapsj" region="north" title="" style="padding:0px;background-color:rgb(240,252,255);height:100%">
<div class="easyui-panel" title="" style="padding:5px;background-color:rgb(240,252,255);overflow: hidden;text-align:center;" data-options="fit:true,border:false">
<table border=0 style="width:100% ">
<tr>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju" style="width:80px;">电梯总数:&nbsp;&nbsp;&nbsp;</span></td>
<td align="left"><font color='#3366FF' class="shuju"><input id="etotal"  style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:100px;" readOnly></font></td>
</tr>
<tr>
</tr>

<tr>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju">在用电梯:&nbsp;&nbsp;&nbsp;</span></td>
<td><font color='#3366FF' class="shuju"><input id="zetotal"  style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:100px;" readOnly></font></td>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju">停用电梯:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="setotal"  class="shuju" style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;" readOnly></td>

</tr>
<tr>
</tr>
<tr>
<td style="background-color:#F0F8FF" nowrap><span class="shuju">涉密电梯:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="smetotal"  class="shuju" style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;"  readOnly></td>
<td style="background-color:#F0F8FF" nowrap><span class="shuju">超期电梯:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="ncqetotal" class="shuju"  style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;"  readOnly></td>

</tr>
<tr>
</tr>
<tr>
<td style="background-color:#F0F8FF" nowrap><span class="shuju">隐患电梯率:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="yhletotal"  class="shuju" style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;" value=""  readOnly></td>
<td style="background-color:#F0F8FF" nowrap><span class="shuju">隐患电梯数:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="yhsetotal" class="shuju"  style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;" value=""  readOnly></td>
</tr>
<tr>
</tr>

<tr>
<td style="background-color:#F0F8FF" nowrap><span class="shuju">电梯检验率:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="jyletotal"  class="shuju" style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;" value=""  readOnly></td>
<td style="background-color:#F0F8FF" nowrap><span class="shuju">电梯报检率:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="bjletotal" class="shuju"  style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;" value=""  readOnly></td>
</tr>
<tr>
</tr>

<tr>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju">维保单位:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="ywcompanytotal" class="shuju"  style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;"  readOnly></td>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju">维保合同有效率:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="ywcompanytotal" class="shuju"  style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;"  readOnly></td>
</tr>

<tr>
</tr>
<tr>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju">使用单位:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="wgcompanytotal"   class="shuju" style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;"  value="" readOnly></td>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju">未落实安全责任单位:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="unsaftywgcompanytotal"   class="shuju" style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;"  value="" readOnly></td>
</tr>
<tr>
</tr>
<tr>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju">本年度事故数:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="sgetotal" class="shuju"  style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;" value=""  readOnly></td>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju">本年度案件数:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="ajetotal"   class="shuju" style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;"  value="" readOnly></td>
</tr>
<tr>
</tr>

</table>
</div> 
</div> 
<div id ="testMaptjDiv" region="center" split="false" title="区域统计" style="padding:0px;background-color:rgb(240,252,255)">
<div id ="tt" class="easyui-tabs" style="height:100%;" data-options="fit:true">
<div title="中心城区" style="padding:0px;" id="testMaptj" data-options="tabWidth:($(document.body).width()-150)*0.12"></div>
<div title="二圈层区" style="padding:0px;" id="testMaptj2" data-options="tabWidth:($(document.body).width()-150)*0.12"></div>
<div  title="三圈层区" style="padding:0px;" id="testMaptj3" data-options="tabWidth:($(document.body).width()-150)*0.12"></div>  
</div>
</div>

</div>
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

    $("#shuju").width(($(document.body).width()-150)*0.4);
    $("#testMapsj").height($(document.body).height()*0.4);
    $("#testMaptj").height($(document.body).height()*0.4);
    $("#testMaptj2").height($(document.body).height()*0.4);
    $("#testMaptj3").height($(document.body).height()*0.4);   


var count = 0;
var map;
map = new BMap.Map("testMap"); //创建Map实例
map.centerAndZoom(new BMap.Point(104.047, 30.6285), 10); //初始化地图，设置中心点坐标和地图级别
map.setCurrentCity("成都");   


var scale = new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT}); //左下角，添加比例尺
map.addControl(scale);

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
 
$('#tt').tabs({
    border:false,
    onSelect:function(title){
        if('中心城区' == title){
        	eletjEcharts(1);
            }
		if('二圈层区' == title){
			eletjEcharts(2);
			}
		if('三圈层区' == title){
			eletjEcharts(3);
			}
    }
});



//区域图
function madeBoundary() {
    
var datas = new Array("成都市-#CCCCCC","武侯区-rgb(242,189,192)","金牛区-rgb(242,189,192)","青羊区-rgb(242,189,192)","锦江区-rgb(242,189,192)","成华区-rgb(242,189,192)","龙泉驿区-rgb(198,237,130)","青白江区-rgb(198,237,130)","新都区-rgb(198,237,130)","温江区-rgb(198,237,130)","金堂县-rgb(146,215,252)","双流区-rgb(198,237,130)","郫县-rgb(198,237,130)","大邑县-rgb(146,215,252)","蒲江县-rgb(146,215,252)","新津县-rgb(146,215,252)","彭州市-rgb(146,215,252)","邛崃市-rgb(146,215,252)","崇州市-rgb(146,215,252)","都江堰市-rgb(146,215,252)","简阳市-rgb(146,215,252)");
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
        count = 1;
    //    var pointArray = [];  
   //     var  pointArray = new Array();        //用来保存路线上面的点
        for (var i = 0; i < count; i++) {
            var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#FFFFFF",fillOpacity:0.5,fillColor:data[1]}); //建立多边形覆盖物
            
            map.addOverlay(ply);  //添加覆盖物   
         
            PolygonCenter(ply,data[0]);  //覆盖物添加label
            
        
         
        }  

      //创建控件
        var myZoomCtrl = new ZoomControl(); 
        // 添加到地图当中
       map.addControl(myZoomCtrl);
                              
    });    
}



var yqc="锦江区,青羊区,武侯区,成华区,高新区,天府新区,金牛区";
//var eqc="龙泉驿区,青白江区,新都区,温江区,双流县,郫县";
var eqc="龙泉驿区,青白江区,新都区,温江区,双流区,郫都区";
var sqc="大邑县,蒲江县,金堂县,新津县,彭州市,邛崃市,崇州市,简阳市,都江堰市";

var	xdata = new Array();
var ydata = new Array();
var ydata2 =new Array();
var ydata3 = new Array();

var	xdata_2 = new Array();
var ydata_2 = new Array();
var ydata2_2 =new Array();
var ydata3_2 = new Array();


var	xdata_3 = new Array();
var ydata_3 = new Array();
var ydata2_3 =new Array();
var ydata3_3 = new Array();

var eletjEchartsFlag = false;   //eletjEcharts数据被查询过没有的标志
function eletjEcharts(index){
	if(!eletjEchartsFlag){
	jQuery.post('/tcweb/elevator/eletjEcharts', function(data){ 
      data = eval(data);//POST方法必加，ajax方法自动处理了    
     
      var datalist = data.tjEcharts;     
      for(var i=0;i<datalist.length;i++){ 
         var xname =datalist[i].area;
         var yname =datalist[i].etotal;
         var ncqetotal= datalist[i].ncqetotal;
         var tsrtotal=datalist[i].tsrtotal;  
         if(yqc.indexOf(xname) >= 0){  
         xdata.push(xname);
       
         ydata.push(yname);
         ydata2.push(ncqetotal);
         ydata3.push(tsrtotal);
         }
         else if(eqc.indexOf(xname) >= 0){
      	xdata_2.push(xname);
             
          ydata_2.push(yname);
          ydata2_2.push(ncqetotal);
          ydata3_2.push(tsrtotal);   
             }
         else if(sqc.indexOf(xname) >= 0){
      	   xdata_3.push(xname);
             
             ydata_3.push(yname);
             ydata2_3.push(ncqetotal);
             ydata3_3.push(tsrtotal);    
             }
         mapAreaDataInitial(xname,yname);
          }     //    alert("xname2---"+xdata);    
      etotalPercentByArea();
      drawEcharts(index);
      eletjEchartsFlag = true;
 //     drawPie();
	   }, 'json');
	}
	else{
		drawEcharts(index);
		}
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
	if("金牛区" == area){
		jnetotal = etotal;		
	}
	if("青羊区" == area)
		qyetotal = etotal;
	if("高新区" == area)
		gxetotal = etotal;
	if("彭州市" == area)
		pzetotal = etotal;
	if("锦江区" == area)
		jjetotal = etotal;
	if("武侯区" == area)
		whetotal = etotal;
	if("成华区" == area)
		chetotal = etotal;
	if("龙泉驿区" == area)
		lqyetotal = etotal;
	if("金堂县" == area)
		jtetotal = etotal;
	if("青白江区" == area)
		qbjetotal = etotal;
//	if("郫县" == area)
	if("郫都区" == area){
		pxetotal = etotal;  
	}
	if("新都区" == area)
		xdetotal = etotal;
	if("温江区" == area)
		wjetotal = etotal;
//	if("双流县" == area)
	if("双流区" == area)
		sletotal = etotal;
	if("大邑县" == area)
		dyetotal = etotal;
	if("蒲江县" == area)
		pjetotal = etotal;
	if("新津县" == area)
		xjetotal = etotal;
	if("都江堰市" == area)
		djyetotal = etotal;
	if("邛崃市" == area)
		qletotal = etotal;
	if("崇州市" == area)
		czetotal = etotal;
	if("简阳市" == area)
		jyetotal = etotal;
	if("天府新区" == area)
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
	madeBoundary();
}

var myChartFlag =false;
var myChart2Flag =false;
var myChart3Flag =false;

function drawEcharts(index){  // alert("xdata--->"+xdata);
	
	var testMapsjContainer = document.getElementById('testMapsj');     
	var testMaptjContainer = document.getElementById('testMaptj');   
	var testMaptjContainer2 = document.getElementById('testMaptj2');
	var testMaptjContainer3 = document.getElementById('testMaptj3');  
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
	        if(index == 1 && !myChartFlag){
	        var myChart = ec.init(document.getElementById('testMaptj')); 
	        var option = {
		            title: {  
	                text: '中心城区',  
	                textStyle: {  
	                    color: '#c23531',  
	                    fontSize: 12 
		            } 
	                },  
		            tooltip: {
		                show: true
		            },
		      //      color:['#08a9f2','#FF6633'],
		            legend: {
		                data:['电梯数量','运维超期数量','投诉未处理数量'],
		                height: testMaptjContainer.style.height,
		                width: testMaptjContainer.style.width
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
		                    data : xdata,
		                    axisLabel:{
		                		textStyle: {  
		                        fontSize: 6 
		    	            } 
		                
		                   }
		                }
		            ],
		            yAxis : [
		                {
		               // 	splitLine:{show: false},//去除网格线
		                    type : 'value',
		                    axisLabel:{
		                        //X轴刻度配置
		                  //      formatter: '{value} °C',
		                  //      interval:'3' //0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
		                    	textStyle: {  
		                        fontSize: 6 
		    	            } 
		                       
		                   }
		                    
		                }
		            ],
		            series : [
		                {
		                    "name":"电梯数量",
		                    "type":"bar",
		                    "barWidth": 10,//固定柱子宽度
		                    "data":ydata,
		                    itemStyle:{  
	                        normal:{color:'#08a9f2'}  
	                    }  
		                },
		                {
		                	 "name":"运维超期数量",
			                 "type":"bar",
			                 "barWidth": 10,//固定柱子宽度
			                 "data":ydata2,
			               //设置柱体颜色  
	                         itemStyle:{  
	                             normal:{color:'#FF6633'}  
	                         }  
			                
			            },
			            {
		                	 "name":"投诉未处理数量",
			                 "type":"bar",
			                 "barWidth": 10,//固定柱子宽度
			                 "data":ydata3,
			               //设置柱体颜色  
	                        itemStyle:{  
	                            normal:{color:'#33CC66'}  
	                        }  
			                
			            }
		            ]
		        };
	        myChart.setOption(option);
	        myChartFlag = true;
	        }
	        if(index == 2 && !myChart2Flag){
	        var myChart2 = ec.init(document.getElementById('testMaptj2')); 
	        var option2 = {
	        		title: {  
                text: '二圈层区',  
                textStyle: {  
                    color: '#c23531',  
                    fontSize: 12 
	            } 
                },  
		            tooltip: {
		                show: true
		            },
		      //      color:['#08a9f2','#FF6633'],
		            legend: {
		                data:['电梯数量','运维超期数量','投诉未处理数量'],
		                height: testMaptjContainer2.style.height,
		                width: testMaptjContainer2.style.width
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
		                    data : xdata_2,
		                    axisLabel:{
		                		textStyle: {  
		                        fontSize: 6 
		    	            } 
		                
		                   }
		                   
		                }
		            ],
		            yAxis : [
		                {
		               // 	splitLine:{show: false},//去除网格线
		                    type : 'value',
		                    axisLabel:{
		                        //X轴刻度配置
		                //        interval:3 //0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
		                    	textStyle: {  
		                        fontSize: 6 
		    	            } 
		                   }
		                    
		                }
		            ],
		            series : [
		                {
		                    "name":"电梯数量",
		                    "type":"bar",
		                    "barWidth": 10,//固定柱子宽度
		                    "data":ydata_2,
		                    itemStyle:{  
	                        normal:{color:'#08a9f2'}  
	                    }  
		                },
		                {
		                	 "name":"运维超期数量",
			                 "type":"bar",
			                 "barWidth": 10,//固定柱子宽度
			                 "data":ydata2_2,
			               //设置柱体颜色  
	                         itemStyle:{  
	                             normal:{color:'#FF6633'}  
	                         }  
			                
			            },
			            {
		                	 "name":"投诉未处理数量",
			                 "type":"bar",
			                 "barWidth": 10,//固定柱子宽度
			                 "data":ydata3_2,
			               //设置柱体颜色  
	                        itemStyle:{  
	                            normal:{color:'#33CC66'}  
	                        }  
			                
			            }
		            ]
		        };
	        myChart2.setOption(option2); 
	        myChart2Flag = true;
	        }
	        if(index == 3 && !myChart3Flag){
	        var myChart3 = ec.init(document.getElementById('testMaptj3')); 
	        var option3 = {
	        		title: {  
                text: '三圈层区',  
                textStyle: {  
                    color: '#c23531',  
                    fontSize: 12 
	            } 
                },  
		            tooltip: {
		                show: true
		            },
		      //      color:['#08a9f2','#FF6633'],
		            legend: {
		                data:['电梯数量','运维超期数量','投诉未处理数量'],
		                height: testMaptjContainer3.style.height,
		                width: testMaptjContainer3.style.width
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
		                    data : xdata_3,
		                    axisLabel:{
		                		textStyle: {  
		                        fontSize: 6 
		    	            } 
		                
		                   }
		                }
		            ],
		            yAxis : [
		                {
		               // 	splitLine:{show: false},//去除网格线
		                    type : 'value',
		                    axisLabel:{
		                        //X轴刻度配置
		                  //      interval:3 //0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
		                    	textStyle: {  
		                        fontSize: 6 
		    	            } 
		                   }
		                    
		                }
		            ],
		            series : [
		                {
		                    "name":"电梯数量",
		                    "type":"bar",
		                    "barWidth": 10,//固定柱子宽度
		                    "data":ydata_3,
		                    itemStyle:{  
	                        normal:{color:'#08a9f2'}  
	                    }  
		                },
		                {
		                	 "name":"运维超期数量",
			                 "type":"bar",
			                 "barWidth": 10,//固定柱子宽度
			                 "data":ydata2_3,
			               //设置柱体颜色  
	                         itemStyle:{  
	                             normal:{color:'#FF6633'}  
	                         }  
			                
			            },
			            {
		                	 "name":"投诉未处理数量",
			                 "type":"bar",
			                 "barWidth": 10,//固定柱子宽度
			                 "data":ydata3_3,
			               //设置柱体颜色  
	                        itemStyle:{  
	                            normal:{color:'#33CC66'}  
	                        }  
			                
			            }
		            ]
		        };
	        myChart3.setOption(option3); 
	        myChart3Flag = true;
	        }

	    }
	);
}


var lng0;
var lat0;
var lng1;
var lat1;
var lng2;
var lat2;
var lng3;
var lat3;
var lng4;
var lat4;
var lng5;
var lat5;
var lng6;
var lat6;
var lng7;
var lat7;
var lng8;
var lat8;


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

	 //地图上的marker定位 
     if(areaName=="大邑县"){
         var marker = new BMap.Marker(new BMap.Point(plCenterlng/path.length, (plCenterlat/path.length)+0.0785),{ icon: tagMarkerIcon });
         lng3=plCenterlng/path.length;
         lat3=plCenterlat/path.length;
     }
     else if(areaName=="邛崃市"){
    	 var marker = new BMap.Marker(new BMap.Point(plCenterlng/path.length, (plCenterlat/path.length)+0.0785),{ icon: tagMarkerIcon });
    	 lng4=plCenterlng/path.length;
         lat4=plCenterlat/path.length;
     }
     else if(areaName=="蒲江县"){
    	 var marker = new BMap.Marker(new BMap.Point(plCenterlng/path.length, plCenterlat/path.length),{ icon: tagMarkerIcon });
    	 lng5=plCenterlng/path.length;
         lat5=plCenterlat/path.length;
    	 
     }
     else if(areaName=="新津县"){
    	 var marker = new BMap.Marker(new BMap.Point((plCenterlng/path.length)+0.0185, (plCenterlat/path.length)+0.0585),{ icon: tagMarkerIcon });
    	 lng6=plCenterlng/path.length;
    	 lat6=plCenterlat/path.length;
     }
     else if(areaName=="简阳市"){
    	 var marker = new BMap.Marker(new BMap.Point(plCenterlng/path.length, (plCenterlat/path.length)+0.0785),{ icon: tagMarkerIcon });
    	 lng7=plCenterlng/path.length;
    	 lat7=plCenterlat/path.length;
    	     	 
     }
     else if(areaName=="崇州市"){
    	 var marker = new BMap.Marker(new BMap.Point(plCenterlng/path.length, (plCenterlat/path.length)+0.1285),{ icon: tagMarkerIcon });
    	 lng2=plCenterlng/path.length;
         lat2=plCenterlat/path.length;
    	 }
     
     else if(areaName=="彭州市"){
    	 var marker = new BMap.Marker(new BMap.Point(plCenterlng/path.length, plCenterlat/path.length),{ icon: tagMarkerIcon });
    	 lng0=plCenterlng/path.length;
    	 lat0=plCenterlat/path.length;
    	
     }
     else if(areaName=="都江堰市"){
    	 var marker = new BMap.Marker(new BMap.Point(plCenterlng/path.length, plCenterlat/path.length),{ icon: tagMarkerIcon });
    	 var lng1=plCenterlng/path.length;
    	 var lat1=plCenterlat/path.length;
     }
     else if(areaName=="新都区"){
    	 var marker = new BMap.Marker(new BMap.Point((plCenterlng/path.length), (plCenterlat/path.length)+0.0785),{ icon: tagMarkerIcon });
    	
     }
     else if(areaName=="青白江区"){
    	 var marker = new BMap.Marker(new BMap.Point((plCenterlng/path.length)+0.0585, (plCenterlat/path.length)-0.0385),{ icon: tagMarkerIcon }); 
     }
     else if(areaName=="龙泉驿区"){
    	 var marker = new BMap.Marker(new BMap.Point((plCenterlng/path.length)+0.0585, (plCenterlat/path.length)-0.0405),{ icon: tagMarkerIcon });
    	
    	  }
    	 
     else if(areaName=="金堂县"){
    	 var marker = new BMap.Marker(new BMap.Point((plCenterlng/path.length)-0.0385, (plCenterlat/path.length)+0.1885),{ icon: tagMarkerIcon });
    	 
     }
    
     else if(areaName=="成华区")
    	 var marker = new BMap.Marker(new BMap.Point((plCenterlng/path.length)+0.0385, (plCenterlat/path.length)),{ icon: tagMarkerIcon }); 
     else if(areaName=="锦江区")
    	 var marker = new BMap.Marker(new BMap.Point((plCenterlng/path.length)+0.0595, (plCenterlat/path.length)-0.0385),{ icon: tagMarkerIcon }); 
     else if(areaName=="金牛区")
    	 var marker = new BMap.Marker(new BMap.Point((plCenterlng/path.length)+0.1145, (plCenterlat/path.length)+0.0285),{ icon: tagMarkerIcon }); 
     else if(areaName=="青羊区")
    	 var marker = new BMap.Marker(new BMap.Point((plCenterlng/path.length)+0.0295, (plCenterlat/path.length)),{ icon: tagMarkerIcon }); 
     else if(areaName=="武侯区")
    	 var marker = new BMap.Marker(new BMap.Point((plCenterlng/path.length)+0.0695, (plCenterlat/path.length)),{ icon: tagMarkerIcon });   
      else
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
        //	 var label = new BMap.Label("<span class='bcd'>"+areaName+"</span>"+"<img src='area2.png'></img>"+"<br>"+"<span class='bcd'>"+"高新区"+"</span>"+"<img src='area2.png'></img>;"+"<br>"+"&nbsp;&nbsp;"+"<span class='bcd'>"+"天府新区"+"</span>"+"<img src='area2.png'></img>"+"&nbsp;",  { offset: new BMap.Size(-15, 2) });   	
        //	 creat(plCenterlng/path.length, plCenterlat/path.length); 
        //     var label = new BMap.Label("<img src='area2.png'></img>",  { offset: new BMap.Size(-15, 2) });
             var label = new BMap.Label("<a href='#' class='piontimg'></a>",  { offset: new BMap.Size(-15, 2) });
        	 create2((plCenterlng/path.length)+0.0495, plCenterlat/path.length,areaName); 
              }
         else if(areaName=="金牛区"){
        //	 var label = new BMap.Label("<span class='bcd'>"+areaName+"</span>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;<img src='area2.png'></img>", { offset: new BMap.Size(-15, 2) });
       //      var label = new BMap.Label("<img src='area2.png'></img>",  { offset: new BMap.Size(-15, 2) });
              var label = new BMap.Label("<a href='#' class='piontimg'></a>",  { offset: new BMap.Size(-15, 2) });
        	 create2((plCenterlng/path.length)+0.0945, (plCenterlat/path.length)+0.0285,areaName);
            
             }
         else if(areaName=="青羊区"){
       // 	 var label = new BMap.Label("<span class='bcd'>"+areaName+"</span>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;<img src='area2.png'></img>", { offset: new BMap.Size(-15, 2) });
      //  	 var label = new BMap.Label("<img src='area2.png'></img>",  { offset: new BMap.Size(-15, 2) });
             var label = new BMap.Label("<a href='#' class='piontimg'></a>",  { offset: new BMap.Size(-15, 2) });
        	 create2(plCenterlng/path.length, plCenterlat/path.length,areaName);
             }
         else if(areaName=="锦江区"){ 
        //	 var label = new BMap.Label("&nbsp;&nbsp;&nbsp;<img src='area2.png'></img><br>"+"<span class='bcd'>"+areaName+"</span>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;", { offset: new BMap.Size(-15, 2) });
        //	 var label = new BMap.Label("<img src='area2.png'></img>",  { offset: new BMap.Size(-15, 2) });
             var label = new BMap.Label("<a href='#' class='piontimg'></a>",  { offset: new BMap.Size(-15, 2) });
        	 create2((plCenterlng/path.length)+0.0215, (plCenterlat/path.length)-0.0485,areaName); 
             }
         else if(areaName=="成华区"){ 
        //	 var label = new BMap.Label("<span class='bcd'>"+areaName+"</span>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;<img src='area2.png'></img>", { offset: new BMap.Size(-15, 2) });
        	 startlng=plCenterlng/path.length;  
        	 startlat=plCenterlat/path.length;
        //	 var label = new BMap.Label("<img src='area2.png'></img>",  { offset: new BMap.Size(-15, 2) });
             var label = new BMap.Label("<a href='#' class='piontimg'></a>",  { offset: new BMap.Size(-15, 2) });
        	 create2(plCenterlng/path.length, (plCenterlat/path.length)+0.0185,areaName); 
             }
         else  if(areaName=="简阳市"){  
        	    //      var label = new BMap.Label(areaName+":"+areaEtotal+"<br>"+"("+areaEtotalp+"%"+")",  { offset: new BMap.Size(-15, 2) });
        	      //  	var label = new BMap.Label("<img src='area.png'></img><font color='red' font-weight='bold' >&nbsp;&nbsp;&nbsp;&nbsp;</font>"+"<br>"+"<span class='abc'>"+areaName+"</span>"+"<br>"+"<font color='#3366FF'>"+areaEtotal+"("+areaEtotalp+"%"+")</font>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", { offset: new BMap.Size(-15, 2) });
        	        	 var label = new BMap.Label("<a href='#'  class='piontimg'></a>"+"<span class='abc'>"+areaName+"</span>"+"<br>"+"<font color='#3366FF'  class='abc'><span id =\'"+areaName+"\'>"+areaEtotal+"</span>(<span id =\'"+areaName+"p\'>"+areaEtotalp+"</span>%"+")</font>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", { offset: new BMap.Size(-15, 2) });
        	    	  
        	         }
         else  if(areaName=="龙泉驿区"){  
     	    //      var label = new BMap.Label(areaName+":"+areaEtotal+"<br>"+"("+areaEtotalp+"%"+")",  { offset: new BMap.Size(-15, 2) });
     	      //  	var label = new BMap.Label("<img src='area.png'></img><font color='red' font-weight='bold' >&nbsp;&nbsp;&nbsp;&nbsp;</font>"+"<br>"+"<span class='abc'>"+areaName+"</span>"+"<br>"+"<font color='#3366FF'>"+areaEtotal+"("+areaEtotalp+"%"+")</font>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", { offset: new BMap.Size(-15, 2) });
     	        	 var label = new BMap.Label("<a href='#'  class='piontimg'></a>"+"<span class='abc'>"+areaName+"</span>"+"<br>"+"<font color='#3366FF'  class='abc'><span id =\'"+areaName+"\'>"+areaEtotal+"</span>(<span id =\'"+areaName+"p\'>"+areaEtotalp+"</span>%"+")</font>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", { offset: new BMap.Size(-15, 2) });
     	    	  
     	         }
         else  if(areaName=="郫县"){  
      	    //      var label = new BMap.Label(areaName+":"+areaEtotal+"<br>"+"("+areaEtotalp+"%"+")",  { offset: new BMap.Size(-15, 2) });
      	      //  	var label = new BMap.Label("<img src='area.png'></img><font color='red' font-weight='bold' >&nbsp;&nbsp;&nbsp;&nbsp;</font>"+"<br>"+"<span class='abc'>"+areaName+"</span>"+"<br>"+"<font color='#3366FF'>"+areaEtotal+"("+areaEtotalp+"%"+")</font>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", { offset: new BMap.Size(-15, 2) });
      	   //     	 var label = new BMap.Label("<a href='#'  class='piontimg'></a>"+"<span class='abc'>"+areaName+"</span>"+"<br>"+"<font color='#3366FF'  class='abc'><span id =\'"+areaName+"\'>"+areaEtotal+"</span>(<span id =\'"+areaName+"p\'>"+areaEtotalp+"</span>%"+")</font>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", { offset: new BMap.Size(-15, 2) });
        	 var label = new BMap.Label("<a href='#'  class='piontimg'></a>"+"<span class='abc'>"+"郫都区"+"</span>"+"<br>"+"<font color='#3366FF'  class='abc'><span id =\'"+areaName+"\'>"+areaEtotal+"</span>(<span id =\'"+areaName+"p\'>"+areaEtotalp+"</span>%"+")</font>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", { offset: new BMap.Size(-15, 2) });
 	    	  
      	         }
         else{  
    //      var label = new BMap.Label(areaName+":"+areaEtotal+"<br>"+"("+areaEtotalp+"%"+")",  { offset: new BMap.Size(-15, 2) });
      //  	var label = new BMap.Label("<img src='area.png'></img><font color='red' font-weight='bold' >&nbsp;&nbsp;&nbsp;&nbsp;</font>"+"<br>"+"<span class='abc'>"+areaName+"</span>"+"<br>"+"<font color='#3366FF'>"+areaEtotal+"("+areaEtotalp+"%"+")</font>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", { offset: new BMap.Size(-15, 2) });
            var label = new BMap.Label("<a href='#'  class='piontimg'></a>"+"<span class='abc'>"+areaName+"</span>"+"<br>"+"<font color='#3366FF'  class='abc'><span id =\'"+areaName+"\'>"+areaEtotal+"</span>(<span id =\'"+areaName+"p\'>"+areaEtotalp+"</span>%"+")</font>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", { offset: new BMap.Size(-15, 2) });
    	  
         }
         label.setStyle({ 
        	 color : "#333300", 
        	 fontSize : "7px", 
        	 backgroundColor :"0.05",
        	 border :"0", 
        	 fontWeight :"bold" 
        	 });
     marker.setLabel(label);
     map.addOverlay(marker);
   
     }
  
     if(data[0]=="成都市"){
   	  
         
         map.setViewport(ply.getPath()); //调整视野 
      
          var view = map.getViewport(ply.getPath()); 
          var mapSize = view.zoom;    
          if(window.screen.width == 1440 && window.screen.height == 900)
          	mapSize = 10;
          map.setZoom(mapSize); 
          map.disableDoubleClickZoom();  //	禁用双击放大
          map.disableDragging(); //禁用地图拖拽
          map.disableScrollWheelZoom();  //禁用滚轮放大缩小
  	    
        }
}


</script>

</body>
</html>
