<%@ page import="com.zytx.models.*" language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
        body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
    </style>
<script type="text/javascript" src="http://api.map.baidu.com/getscript?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY&services=&t=20151223175945"></script>
    <title>添加多个行政区划</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout" data-options="fit:true" style="background-color:rgb(240,252,255);">
    <div id="testMap" region="center" title="电梯分布数量及比例" style="padding:0px;margin:0px;" ></div>
    	<div id ="shuju" data-options="region:'east',split:false" title="电梯实时数据" style="background-color:rgb(240,252,255);">
<div class="easyui-layout" style="height:100%;" data-options="fit:true">
 <div id="testMapsj" class="testMapsj" region="north" title="" style="padding:0px;background-color:rgb(240,252,255);height:100%">
<div class="easyui-panel" title="" style="padding:5px;background-color:rgb(240,252,255);overflow: hidden;text-align:center;" data-options="fit:true,border:false">
<table border=0 style="width:100% ">
<tr>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju">电梯总数:&nbsp;&nbsp;&nbsp;</span></td>
<td><font color='#3366FF' class="shuju"><input id="etotal"  style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;" readOnly></font></td>
</tr>
<tr>
</tr>

<tr>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju">在用电梯:&nbsp;&nbsp;&nbsp;</span></td>
<td><font color='#3366FF' class="shuju"><input id="zetotal"  style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;" readOnly></font></td>
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
<td><input id="yhletotal"  class="shuju" style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;" value="?"  readOnly></td>
<td style="background-color:#F0F8FF" nowrap><span class="shuju">隐患电梯数:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="yhsetotal" class="shuju"  style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;" value="?"  readOnly></td>
</tr>
<tr>
</tr>

<tr>
<td style="background-color:#F0F8FF" nowrap><span class="shuju">电梯检验率:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="jyletotal"  class="shuju" style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;" value="?"  readOnly></td>
<td style="background-color:#F0F8FF" nowrap><span class="shuju">电梯报检率:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="bjletotal" class="shuju"  style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;" value="?"  readOnly></td>
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
<td><input id="wgcompanytotal"   class="shuju" style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;"  value="?" readOnly></td>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju">未落实安全责任单位:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="unsaftywgcompanytotal"   class="shuju" style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;"  value="?" readOnly></td>
</tr>
<tr>
</tr>
<tr>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju">本年度事故数:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="sgetotal" class="shuju"  style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;" value="?"  readOnly></td>
<td style="background-color:#F0F8FF;" nowrap><span class="shuju">本年度案件数:&nbsp;&nbsp;&nbsp;</span></td>
<td><input id="ajetotal"   class="shuju" style="border:0px;background-color:rgb(240,252,255);color:#3366FF;width:40px;"  value="?" readOnly></td>
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
</body>
</html>
<script type="text/javascript">
    $("#shuju").width(($(document.body).width()-150)*0.4);
    // 百度地图API功能
    var map = new BMap.Map("testMap");
    map.centerAndZoom(new BMap.Point(104.047, 30.6285), 10); //初始化地图，设置中心点坐标和地图级别
    map.setCurrentCity("成都"); 
    map.enableScrollWheelZoom();

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
    madeBoundary();
    

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

              var pointArray = [];
            for (var i = 0; i < count; i++) {
                var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillOpacity:0.5,fillColor:data[1]}); //建立多边形覆盖物
                
                map.addOverlay(ply);  //添加覆盖物
                if(data[0]=="成都市"){
	            	  
      	          
	           //     map.setViewport(ply.getPath()); //调整视野  
                }
	            /*
	                var view = map.getViewport(ply.getPath()); 
	                var mapSize = view.zoom;    
	                if(window.screen.width == 1440 && window.screen.height == 900)
	                	mapSize = 10;
	                map.setZoom(mapSize);
	                map.disableDoubleClickZoom();  //	禁用双击放大
	                map.disableDragging(); //禁用地图拖拽  */
	                map.disableScrollWheelZoom();  //禁用滚轮放大缩小
	        	    
	     //         }
            }    
            
    
                            
        }); 


        
    }


</script>