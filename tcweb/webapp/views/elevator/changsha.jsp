<%@ page import="com.zytx.models.*,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>长沙管理平台</title>
   <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/changsha.css"/>
   <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css"/>
   
   <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-2.1.1.min.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrap.min.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/echarts.min.js"></script>
   
   <script type="text/javascript" >
   /*
   $(function(){
	   document.addEventListener("fullscreenchange", function( event ) {
		   if (document.fullscreenElement) {
		    //   console.log('进入全屏');  
		       alert("16");
			   window.location.reload();
		   } else {
		     console.log('退出全屏');
		   }});
		 	   
   
   });  */
   
   function  tuichu(){
	   window.open('<%=request.getContextPath()%>/mmain.jsp','newwindow','width='+(window.screen.availWidth)+',height='+(window.screen.availHeight-30)+ ',top=0,left=0,fullscreen = yes,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');     
	   }
   </script>
  </head>
  <body>
    
<div class="changsha">
    <!-- 顶部 -->
    <div class="header">
        <div class="col col-lg-3 col-md-3 col-sm-2">
            
        </div>
        <div class="col col-lg-6 col-md-6 col-sm-6">
            长沙市电梯安全公共服务平台
        </div>
        <div class="col col-lg-3 col-md-3 col-sm-4">
           <ul>
               <li>
                    <img src="<%=request.getContextPath()%>/images/cstuichu.png" onclick ="tuichu()">
                    <span>退出</span>
               </li>
               <li>
                    <img src="<%=request.getContextPath()%>/images/csfb.png" alt="">
                    <span>监管概况</span>
               </li>
               <li>
                    <img src="<%=request.getContextPath()%>/images/cstj.png" alt="">
                    <span>系统统计</span>
               </li>
               <li> 
                    <img src="<%=request.getContextPath()%>/images/csgk.png" alt="">
                    <span>电梯分布</span>
               </li>
           </ul>
        </div>
    </div>

    <!-- 中间图表 -->
    <div class="middle">
        <div class="col col-lg-3 col-md-3 col-sm-12">
            <div class="mid-left" id="midLeftChart">
                
            </div>
        </div>
        <div class="col col-lg-6 col-md-6 col-sm-12">
            <!-- 时间 -->
            <div class="mid-time">
                运行时间<span id="daysold"></span>天<span id="hrsold"></span>小时<span 

id="minsold"></span>分<span id="seconds"></span>秒
            </div>
            <!-- 地图 -->
            <div class="map-con">
                <div id="mid-map">

                </div>
            </div>
        </div>
        <div class="col col-lg-3 col-md-3 col-sm-12">
            <div class="mid-right" id="midRightChart">
                
            </div>
        </div>
    </div>

    <!-- 底部图表 -->
    <div class="footer">
        <!-- 左侧 -->
        <div class="col col-lg-3 col-md-5 col-sm-12">
            <div  class="footer-left">
                <div class="col-lg-8 col-md-8 col-sm-12" id="footer-left-bar">

                </div>
                <div class="col-lg-4 col-md-4 col-sm-12">
                    <div class="weibao">   
                        <div class="weibaoTit">
                            登记维保人员
                        </div>
                        <div class="weibaoNum">
                            <span id="sweibaoNum">6406人</span>
                            <img src="<%=request.getContextPath()%>/images/csgr.png" alt="">
                        </div>

                        <div class="peibi">
                            <div>人梯配比：</div>
                            <div id="dpeibi">17.65台</div>
                            <img src="<%=request.getContextPath()%>/images/csgr.png" alt="">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col col-lg-5 col-md-5 col-sm-12">
            <div class="footer-left-mid">
                <div class="col-lg-7 col-md-7 col-sm-12">
                    <div class="footer-left-mid-real">
                        电梯实时数据
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 elevator-num">
                        <div class="elevator-num-real">
                            <span>电梯总数</span>
                            <span id="dtzs">116382</span>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12">
                        <!-- 在用电梯 -->
                        <div class="elevator-now">
                            <div class="col-lg-5 col-md-5 col-sm-12">
                                在用电梯
                            </div>
                            <div class="col-lg-7 col-md-7 col-sm-12" id="zydt">
                                112961
                            </div>
                        </div>
                        <!-- 停用电梯 -->
                        <div class="elevator-stop">
                            <div class="col-lg-5 col-md-5 col-sm-12">
                                停用电梯
                            </div>
                            <div class="col-lg-7 col-md-7 col-sm-12" id="tydt">
                                2600
                            </div>
                        </div>
                        <!-- 加密电梯 -->
                        <div  class="elevator-encrypt ">
                            <div class="col-lg-5 col-md-5 col-sm-12">
                                加密电梯
                            </div>
                            <div class="col-lg-7 col-md-7 col-sm-12" id="smdt">
                                231
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 电梯检验 -->
                <div class="col-lg-5 col-md-5 col-sm-12">
                    <div id="elevator-test">

                    </div>
                </div>
            </div>
        </div>
        <!-- 电梯使用单位 -->
        <div class="col col-lg-1 col-md-2 col-sm-12">
            <div id="ellipse">

            </div>
        </div>
        <div class="col col-lg-3 col-md-12 col-sm-12">

            <div class="footer-right">
                <div class="footer-right-span">
                    <span class="spanBg">电梯数量及预警</span>
                    
                </div>
                <div class="footer-right-bar" id="tab-one">

                </div>
               
            </div>

        </div>
    </div>
</div>



   
  
   

    <!-- 时间 -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/time.js"></script>
    <!-- 地图js -->

     <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/changsha.js"></script>
    <!-- 在保维保单位 -->
  
     <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/weibao.js"></script>
    <!-- 电梯检验 -->
  
     <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jianyan.js"></script>
    <!-- 左侧横向柱状图 -->
    
    <script type="text/javascript"  src="<%=request.getContextPath()%>/scripts/midLeftChart.js"></script> 
    <!-- 右侧横向柱状图 -->
  
     <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/midRightChart.js"></script>
    <!-- 电梯使用单位 -->
   
     <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ellipse.js"></script>
    <!-- tab切换图表 -->
    
     <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/tabCharts.js"></script>

  </body>
</html>