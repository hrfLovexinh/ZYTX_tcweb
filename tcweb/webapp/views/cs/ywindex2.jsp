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
<!-- <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>  -->
 <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>  
<!--<script type="text/javascript" src="<%=request.getContextPath()%>/theme/wintage.js"></script>-->
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
   String jjcqday  = request.getParameter("jjcqday");
   int companyId = 0;
   int cqday =3;     //即将超期的天数
   
   if(jjcqday != null)
	   cqday = Integer.parseInt(jjcqday);
	   
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

$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	   

	var h = ($("body").height()-92)* 0.5;
	$('#contextDivs').layout('panel','south').panel('resize',{height:h});
	$('#contextDivs').layout('resize');

	var w =  $("body").width()* 0.5;
	$('#norcontextDivs').layout('panel','west').panel('resize',{width:w});
	$('#norcontextDivs').layout('resize');

	$('#cencontextDivs').layout('panel','west').panel('resize',{width:w});
	$('#cencontextDivs').layout('resize');

	
	   
	compute1(); 
	compute2(); 
  });

var  jjdtCount =0;
var  qydtCount =0;
var  jndtCount =0;
var  whdtCount =0;
var  chdtCount =0;
var gxdtCount =0;
var  tfxdtCount =0;
var lqydtCount =0;
var qbjdtCount =0;
var  xddtCount =0;
var wjdtCount =0;
var jtdtCount =0;
var sldtCount =0;
var pddtCount =0;
var dydtCount =0;
var  pjdtCount =0;
var  xjdtCount =0;
var djydtCount =0;
var  pzdtCount =0;
var qldtCount =0;
var czdtCount =0;
var jydtCount =0;
var yqcdtCount =0;
var erqcdtCount =0;
var sqcdtCount =0;


var dataAll  = new Array();
var yAxisData =new Array();

function compute1(){   
	 jQuery.post('/tcweb/cs/dthz2',{'ywcompanyId':<%=companyId%>,'cqday':<%=cqday%>}, function(data){ 
	        data = eval(data);//POST方法必加，ajax方法自动处理了    
	    //    $('#dtzs').attr("value",data.dtzs);  
	        $("#dtzs").text(data.dtzs);
	   //     $('#rysl').attr("value",data.rysl);
	        $("#rysl").text(data.rysl);
	        $("#ywdt").text(data.dtzs - data.dailingCount);
	        $("#ywdt2").text(data.dtzs - data.dailingCount);
	     //   $('#rtpb').attr("value",data.rtpb);
	        $("#dailing").text(data.dailingCount);
	        $("#shemi").text(data.shemiCount);
	        $("#rtpb").text(data.rtpb);

	        $("#wdq").text(data.dtzs-data.ywcqCount-data.dailingCount);  
	        $("#jjdq").text(data.ywjjcqCount);
	        $("#cq").text(data.ywcqCount);   
	        $("#tspj").text(data.remarkCount);
	        var dtzs = data.dtzs;
	        var dailingCount = 0;
	        var shemiCount = 0;
	        var ywcqCount = 0;
	        var ywjjcqCount =0;
	        
	        dailingCount = data.dailingCount;
	        shemiCount = data.shemiCount;
	        ywcqCount  = data.ywcqCount;
	        ywjjcqCount = data.ywjjcqCount;  

	        jjdtCount =data.jjdtCount;
            if(jjdtCount > 0){
            	dataAll.push(jjdtCount);
            	yAxisData.push("锦江区");
                }
                  	        
	        qydtCount =data.qydtCount;
	        if(qydtCount > 0){
            	dataAll.push(qydtCount);
            	yAxisData.push("青羊区");
                }
	        
	        jndtCount =data.jndtCount;
	        if(jndtCount > 0){
            	dataAll.push(jndtCount);
            	yAxisData.push("金牛区");
                }
	        
	        whdtCount =data.whdtCount;
	        if(whdtCount > 0){
            	dataAll.push(whdtCount);
            	yAxisData.push("武侯区");
                }
	        
	        chdtCount =data.chdtCount;
	        if(chdtCount > 0){
            	dataAll.push(chdtCount);
            	yAxisData.push("成华区");
                }
	        
	        gxdtCount =data.gxdtCount;
	        if(gxdtCount > 0){
            	dataAll.push(gxdtCount);
            	yAxisData.push("高新区");
                }
	        
	        tfxdtCount =data.tfxdtCount;
	        if(tfxdtCount > 0){
            	dataAll.push(tfxdtCount);
            	yAxisData.push("天府新区");
                }
	        
	        lqydtCount =data.lqydtCount;
	        if(lqydtCount > 0){
            	dataAll.push(lqydtCount);
            	yAxisData.push("龙泉驿区");
                }
	        
	        qbjdtCount =data.qbjdtCount;
	        if(qbjdtCount > 0){
            	dataAll.push(qbjdtCount);
            	yAxisData.push("青白江区");
                }
	        
	        xddtCount =data.xddtCount;
	        if(xddtCount > 0){
            	dataAll.push(xddtCount);
            	yAxisData.push("新都区");
                }

	        
	        wjdtCount =data.wjdtCount;
	        if(wjdtCount > 0){
            	dataAll.push(wjdtCount);
            	yAxisData.push("温江区");
                }
	        
	        jtdtCount =data.jtdtCount;
	        if(jtdtCount > 0){
            	dataAll.push(jtdtCount);
            	yAxisData.push("金堂县");
                }
	        
	        sldtCount =data.sldtCount;
	        if(sldtCount > 0){
            	dataAll.push(sldtCount);
            	yAxisData.push("双流区");
                }
	        
	        pddtCount =data.pddtCount;
	        if(pddtCount > 0){
            	dataAll.push(pddtCount);
            	yAxisData.push("郫都区");
                }
	        
	        dydtCount =data.dydtCount;
	        if(dydtCount > 0){
            	dataAll.push(dydtCount);
            	yAxisData.push("大邑县");
                }
	        
	        pjdtCount =data.pjdtCount;
	        if(pjdtCount > 0){
            	dataAll.push(pjdtCount);
            	yAxisData.push("蒲江县");
                }
	        
	        xjdtCount =data.xjdtCount;
	        if(xjdtCount > 0){
            	dataAll.push(xjdtCount);
            	yAxisData.push("新津县");
                }

	        djydtCount =data.djydtCount;
	        if(djydtCount > 0){
            	dataAll.push(djydtCount);
            	yAxisData.push("都江堰市");
                }
	        
	        pzdtCount =data.pzdtCount;
	        if(pzdtCount > 0){
            	dataAll.push(pzdtCount);
            	yAxisData.push("彭州市");
                }
	        
	        qldtCount =data.qldtCount;
	        if(qldtCount > 0){
            	dataAll.push(qldtCount);
            	yAxisData.push("邛崃市");
                }
	        
	        czdtCount =data.czdtCount;
	        if(czdtCount > 0){
            	dataAll.push(czdtCount);
            	yAxisData.push("崇州市");
                }
            
	        jydtCount =data.jydtCount;
	        if(jydtCount > 0){
            	dataAll.push(jydtCount);
            	yAxisData.push("简阳市");
                }
	        
	        yqcdtCount =data.yqcdtCount;
	        erqcdtCount =data.erqcdtCount;
	        sqcdtCount =data.sqcdtCount;

	        bubbleSort(dataAll,yAxisData);
	        
	        draw12(dtzs,dailingCount,shemiCount,ywcqCount,ywjjcqCount); 
		   }, 'json');
}

function bubbleSort(array,array2){
    /*给每个未确定的位置做循环*/
    for(var unfix=array.length-1; unfix>0; unfix--){
      /*给进度做个记录，比到未确定位置*/
      for(var i=0; i<unfix;i++){
        if(array[i]>array[i+1]){
          var temp = array[i];
          array.splice(i,1,array[i+1]);
          array.splice(i+1,1,temp);

          var temp2 = array2[i];
          array2.splice(i,1,array2[i+1]);
          array2.splice(i+1,1,temp2);
        }
      }
    }
  }



function draw12(dtzs,dailingCount,shemiCount,ywcqCount,ywjjcqCount){
     
	 var wnorcontextDivContainer = document.getElementById('wnorcontextDiv'); 
	 $('#dtsl').height(wnorcontextDivContainer.clientHeight * 0.98);  

	 var wcencontextDivContainer = document.getElementById('wcencontextDiv'); 
	 $('#dtwb').height(wcencontextDivContainer.clientHeight * 1);  


	  var myChart = echarts.init(document.getElementById('dtsl'));  
	  var myChart2 = echarts.init(document.getElementById('dtwb')); 
	   
          
        
   //    var dataAll = [jjdtCount, qydtCount, jndtCount,whdtCount,chdtCount,gxdtCount,tfxdtCount,lqydtCount,qbjdtCount,xddtCount,wjdtCount,jtdtCount,sldtCount,pddtCount,dydtCount,pjdtCount,xjdtCount,djydtCount,pzdtCount,qldtCount,czdtCount,jydtCount];
  //     var yAxisData = ['锦江区', '青羊区', '金牛区','武侯区','成华区','高新区','天府新区','龙泉驿区','青白江区','新都区','温江区','金堂县','双流区','郫都区','大邑县','蒲江县','新津县','都江堰市','彭州市','邛崃市','崇州市','简阳市'];
       var height = document.querySelector('#dtsl').offsetHeight;
       var width = document.querySelector('#dtsl').offsetWidth;
       var radiusMax = width >=height ? height : width;
       var itemStyle = {
        normal: {
        color: new echarts.graphic.RadialGradient(width/3,height/3,radiusMax*0.5,[{
            offset:0,
            color:'rgba(23,193,249,1)'
        },{
            offset:1,
            color:'rgba(23,193,249,0.1)'
        }],true),
        borderWidth: 1,
        borderColor: '#235894'
       }
       };

       
        var option = {
            backgroundColor: '#F5F5F5',
            title: [{
                    text: "电梯数量占比",
                    x: '2%',
                    y: '1%',
                    textStyle: {
                        color: "#000000",
                        fontSize: "14"
                    }
                },
                {
                    text: "行政区划电梯数量",
                    x: '40%',
                    y: '1%',
                    textStyle: {
                        color: "#000000",
                        fontSize: "14"
                    }
                },
              
            ],
            grid: [{
                x: '50%',
                y: '7%',
                width: '45%',
                height: '90%'
            }, ],
            tooltip: {
                formatter: '{b} ({c})'
            },
            xAxis: [{
                gridIndex: 0,
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: false
                },
                splitLine: {
                    show: false
                },
                axisLine: {
                    show: false
                }
            }, ],
            yAxis: [{
                gridIndex: 0,
                interval: 0,
           //     data: yAxisData.reverse(),
                data: yAxisData,
           
               
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    interval: 0
             //       rotate: 40
                },
                splitLine: {
                    show: false
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: "#6173a3"
                    }
                }
            }],
            series: [{
                    name: '电梯数量占比',
                    type: 'pie',
                    radius: '40%',
                    center: ['18%', '55%'],
                    color: ['#337ab7', '#4da8ec', '#3a91d2'],
                    data: [{
                            value: dtzs-dailingCount,
                            name: '在用'
                        },
                        {
                            value: dailingCount,
                            name: '停用'
                        },
                        {
                            value: shemiCount,
                            name: '涉密'
                        },
                    ],
                    labelLine: {
                        normal: {
                            lineStyle: {
                                color: '#235894'
                            }
                        }
                    },
                /*    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: '{b} \n ({d}%)',
                                textStyle: {
                                    color: '#0066FF'
                                }
                            }
                        }
                    } */
                    itemStyle: itemStyle
                },
                
                {
                    name: '行政区划电梯数量',
                    type: 'bar',
                    xAxisIndex: 0,
                    yAxisIndex: 0,
                    barWidth: '45%',
              /*      itemStyle: {
                        normal: {
                            color: '#337ab7'
                        }
                    }, */
                    itemStyle: {
                        normal: {
                            // barBorderRadius: 5,
                            color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                                    offset: 0,
                                    color: 'transparent'
                                }, {
                                    offset: 0.2,
                                    color: 'rgba(79, 168, 249, 0.2)'
                                }, {
                                    offset: 0.4,
                                    color: 'rgba(79, 168, 249, 0.4)'
                                }, {
                                    offset: 0.8,
                                   color: 'rgba(79, 168, 249, 0.6)'
                                }, {
                                    offset: 1,
                                    color: 'rgba(79, 168, 249, 0.7)'
                            },  {
                                    offset: 1,
                                    color: 'rgba(79, 168, 249, 0.9)'
                            },{
                                    offset: 1,
                                    color: 'rgba(79, 168, 249, 1)'
                            }])
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: "right",
                            textStyle: {
                                color: "#9EA7C4"
                            }
                        }
                    },
                //    data: dataAll.sort(),
                //    data: dataAll.reverse()
                    data: dataAll
                },

            ]
        };
	         			
	         			
                 /*
	              var option2 = {
	                  backgroundColor: '#F5F5F5',
	         	      tooltip: {
	         			        trigger: 'item',
	         			        formatter: "{a} <br/>{b}: {c} ({d}%)"
	         			    },
	         		  legend: {
	        			       orient: 'vertical', 
	        			//     orient: 'horizontal',  
	        			       x: 'left',
	        			//     x: 'center',
	        			        data:['未到期','即将到期','超期'],
	        			        textStyle: {
	        			            fontSize: 18
	        			        }
	        			          
	        			    }, 
	         		  series: [
	         			        {
	         			            name:'',
	         			            type:'pie',
	         			            radius: ['30%', '55%'],
	         			            center: ['50%', '50%'],//饼图的位置
	         			           label: {
	         			                normal: {
	         			                    formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
	         			                    backgroundColor: '#eee',
	         			                    borderColor: '#aaa',
	         			                    borderWidth: 1,
	         			                    borderRadius: 4,
	         			                    // shadowBlur:3,
	         			                    // shadowOffsetX: 2,
	         			                    // shadowOffsetY: 2,
	         			                    // shadowColor: '#999',
	         			                    // padding: [0, 7],
	         			                    rich: {
	         			                        a: {
	         			                            color: '#999',
	         			                            lineHeight: 22,
	         			                            align: 'center'
	         			                        },
	         			                        // abg: {
	         			                        //     backgroundColor: '#333',
	         			                        //     width: '100%',
	         			                        //     align: 'right',
	         			                        //     height: 22,
	         			                        //     borderRadius: [4, 4, 0, 0]
	         			                        // },
	         			                        hr: {
	         			                            borderColor: '#aaa',
	         			                            width: '100%',
	         			                            borderWidth: 0.5,
	         			                            height: 0
	         			                        },
	         			                        b: {
	         			                            fontSize: 16,
	         			                            lineHeight: 33
	         			                        },
	         			                        per: {
	         			                            color: '#eee',
	         			                            backgroundColor: '#334455',
	         			                            padding: [2, 4],
	         			                            borderRadius: 2
	         			                        }
	         			                    }
	         			                },
	         			                emphasis: {
	         			                    show: true,
	         			                    textStyle: {
	         			                        fontSize: '30',
	         			                        fontWeight: 'bold'
	         			                    }
	         			                }
	         			            },
	         			            data:[
	         			                {value:dtzs-ywcqCount-dailingCount, name:'未到期',itemStyle:{ normal:{color:'#99FF99'} }},
	         			                {value:ywjjcqCount, name:'即将到期',itemStyle:{ normal:{color:'#FF9933'} } },
	         			                {value:ywcqCount, name:'超期',itemStyle:{ normal:{color:'#ff0000'} } }         
	         			            ]
	         			        },
	         			       {
	         			            name:'radial gradient',
	         			            type:'pie',
	         			            radius: '55%',
	         			            avoidLabelOverlap: false,
	         			            itemStyle: {
	         			                normal: {
	         			                    color: {
	         			                        type: 'radial',
	         			                        x: 0.5,
	         			                        y: 0.5,
	         			                        r: 0.5,
	         			                        colorStops: [{
	         			                            offset: 0, color: 'rgba(255,255,255,0)'
	         			                        }, {
	         			                            offset: 0.55, color: 'rgba(255,255,255,0.5)'
	         			                        }, {
	         			                            offset: 0.65, color: 'rgba(255,255,255,0.5)' 
	         			                        }, {
	         			                            offset: 0.95, color: 'rgba(255,255,255,0)' 
	         			                        }],
	         			                        globalCoord: false 
	         			                    }
	         			                }
	         			            },
	         			            silent: true,
	         			            z: 999,
	         			            data:[
	         			                {value:1, name: ''}
	         			            ]
	         			        }        
	         			        
	         			    ] 
	         			};    */

	         			 var option2 = {
	         				     backgroundColor: '#F5F5F5',
	         				     tooltip: {
	         				         trigger: 'item',
	         				         formatter: "{a} <br/>{b}: {c} ({d}%)"
	         				     },
	         				     legend: {
	         				         orient: 'vertical',
	         				         //     orient: 'horizontal',  
	         				         x: 'left',
	         				         //     x: 'center',
	         				         data: ['未到期', '即将到期', '超期'],
	         				         textStyle: {
	         				             fontSize: 18
	         				         }

	         				     },
	         				     series: [{
	         				         type: 'pie',
	         				         radius: ['20%', '60%'],
	         				         roseType: 'area',
	         				         zlevel: 2,
	         				         tooltip: {
	         				             formatter: '{b}: {d}%'
	         				         },
	         				         label: {
	         				             normal: {
	         				                 show: true,
	         				                 formatter: '{b}\n{d}%'
	         				             }
	         				         },
	         				        data:[
		         			                {value:dtzs-ywcqCount-dailingCount, name:'未到期',itemStyle:{ normal:{color:'#00ff00'} }},
		         			                {value:ywjjcqCount, name:'即将到期',itemStyle:{ normal:{color:'#FF9933'} } },
		         			                {value:ywcqCount, name:'超期',itemStyle:{ normal:{color:'#ff0000'} } }         
		         			            ]
	         				     },
	         				    {
		         			            name:'radial gradient',
		         			            type:'pie',
		         			            radius: '55%',
		         			            avoidLabelOverlap: false,
		         			            itemStyle: {
		         			                normal: {
		         			                    color: {
		         			                        type: 'radial',
		         			                        x: 0.5,
		         			                        y: 0.5,
		         			                        r: 0.5,
		         			                        colorStops: [{
		         			                            offset: 0, color: 'rgba(255,255,255,0)'
		         			                        }, {
		         			                            offset: 0.55, color: 'rgba(255,255,255,0.5)'
		         			                        }, {
		         			                            offset: 0.65, color: 'rgba(255,255,255,0.5)' 
		         			                        }, {
		         			                            offset: 0.95, color: 'rgba(255,255,255,0)' 
		         			                        }],
		         			                        globalCoord: false 
		         			                    }
		         			                }
		         			            },
		         			            silent: true,
		         			            z: 999,
		         			            data:[
		         			                {value:1, name: ''}
		         			            ]
		         			        } 
	         				     ]
	         				 };
                      
                  myChart.setOption(option); 
	              myChart2.setOption(option2); 
	      //        myChart3.setOption(option3); 
	      //        myChart4.setOption(option4);
	           
		 //         }
	   //     );

	
}


function compute2(){  
	var	xdata3 = new Array();
	var ydata3 = new Array();
	var	xdata4 = new Array();
	var ydata4 = new Array();
	 jQuery.post('/tcweb/cs/ywxyhz',{'ywcompanyId':<%=companyId%>}, function(data){ 
	        data = eval(data);//POST方法必加，ajax方法自动处理了    
	        var datalist = data.ywxyhz; 
	        for(var i=0;i<datalist.length;i++){ 
	        	var xname =datalist[i].ratingDate;
	        	var yname =datalist[i].tSort;
                var xname2 =datalist[i].ratingDate;
                var yname2 =datalist[i].tScore;
	        	xdata3.push(xname);          
	        	ydata3.push(yname); 
	        	xdata4.push(xname2);          
	        	ydata4.push(yname2); 
	        	if(i == datalist.length-1){
            //      $('#sypm').attr("value",yname); 
                  $("#sypm").text(yname);
                  $("#pm").text(yname);
            //      $('#sydf').attr("value",yname2); 
                  $("#sydf").text(yname2); 
		        	}
		        }

	      draw3(xdata3,ydata3,xdata4,ydata4);
		   }, 'json');
}

function draw3(xdata3,ydata3,xdata4,ydata4){
	 var cnorcontextDivContainer = document.getElementById('cnorcontextDiv'); 
	 $('#wbpm').height(cnorcontextDivContainer.clientHeight * 1);  
	
	 var ccencontextDivContainer = document.getElementById('ccencontextDiv'); 
	 $('#wbdf').height(ccencontextDivContainer.clientHeight * 1);  

	  var myChart3 = echarts.init(document.getElementById('wbpm'));
	  var myChart4 = echarts.init(document.getElementById('wbdf'));

	  var colors = ['#5793f3', '#d14a61', '#675bba'];
	  var    option3 = {
			     color: ['#2b542c'],
	             backgroundColor: '#F5F5F5',
	             xAxis : [
	                  {   splitLine:{show: true,lineStyle: {  
	                    // 使用深浅的间隔色  
	                       color: ['#F5F5F5']  
	                   }},
	              type : 'category',
	              data : xdata3
	                   }
	               ],
	               
	               yAxis : [
	               {   
	                   type: 'value',
	                   name: '排名',
	               //    min: 0,
	               //    max: 500,
	              //     interval: 50,
	                   inverse:'true',
	                   axisLabel: {
	                       formatter: '{value}'
	                   }
	                }
	                ],
	                series: [
	     	            {
	                	label: {
	                    normal: {
	                        show: true,
	                        position: 'top'
	                    }
	                    },
	                   data: ydata3,
	                   type:'bar'
	                   }
	           
		             ],
		             itemStyle: {
		                 normal: {
		                  
		                     color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
		                         offset: 0,
		                         color: 'rgba(17, 168,171, 1)'
		                     }, {
		                         offset: 1,
		                         color: 'rgba(17, 168,171, 0.1)'
		                     }]),
		                     shadowColor: 'rgba(0, 0, 0, 0.1)',
		                     shadowBlur: 10
		                 }
		             }
	             };  


	  var option4 = {
			    color: ['#777'],
			    backgroundColor: '#F5F5F5',
			    xAxis: [{
			        splitLine: {
			            show: true,
			            lineStyle: {
			                // 使用深浅的间隔色  
			                //    color: ['#ffffff']  
			                color: ['#F5F5F5']
			            }
			        },
			        type: 'category',
			        data: xdata4
			        
			    }],
			    yAxis: [{   
	                   type: 'value',
	                   name: '得分',
	                   axisLabel: {
	                       formatter: '{value}'
	                   }
	                }],
			    series: [{
                	label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                    },
                    itemStyle: {
                        normal: {
                            barBorderRadius: 30,
                            color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1, [{
                                        offset: 0,
                                        color: '#00feff'
                                    },
                                    {
                                        offset: 0.5,
                                        color: '#027eff'
                                    },
                                    {
                                        offset: 1,
                                        color: '#0286ff'
                                    }
                                ]
                            )
                        }
                    },
                   data: ydata4,
                   type:'bar'
                   }]
			};
	                  
	  	             
	  	              myChart3.setOption(option3); 
	  	              myChart4.setOption(option4);
	  	           
	  	//	          }
	  	//        );

	  	
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

</style>
</head>
<body class="easyui-layout" data-options="fit:true" id="contextDivs">
     <div region="north" border="0" id ="soucontextDiv" style="height:92px;margin-top:4px;">
     
       <table style="width:100%">
       <tr>
       <td align="center"><a class="btn btn-large btn-primary" href="#"><i class="icon-Inbox icon-3x pull-left"></i>电梯<br>数量<br><span style="font-size: 20px;" id="dtzs"></span></a></td>
       <td align="center"><a class="btn btn-large btn-default" href="#"><i class="icon-wrench icon-3x pull-left"></i>在用<br>电梯<br><span style="font-size: 20px;" id="ywdt"></span></a></td>
       <td align="center"><a class="btn btn-large btn-info" href="#"><i class="icon-user icon-3x pull-left"></i>注册<br>人员<br><span style="font-size: 20px;" id="rysl"></span></a></td>
       <td align="center"><a class="btn btn-large btn-danger" href="#"><i class="icon-Fire icon-3x pull-left"></i>报警<br>记录<br><span style="font-size: 20px;" id="bjsl">0</span></a></td>
       <td align="center"><a class="btn btn-large btn-warning" href="#"><i class="icon-comment icon-3x pull-left"></i>投诉<br>评价<br><span style="font-size: 20px;" id="tspj">0</span></a></td>
       <td align="center"><a class="btn btn-large btn-success" href="#"><i class="icon-flag icon-3x pull-left"></i>上月<br>排名<br><span style="font-size: 20px;" id="pm">0</span></a></td>
       </tr>
        </table>
        
    </div>
     
   <div region="center" border="0" id ="norcontextDiv">
            <div class="easyui-layout" data-options="fit:true" id="norcontextDivs" style="overflow:hidden;" >   
                    <div region="west" title="电梯数量" id ="wnorcontextDiv" style="overflow:hidden;position:relative;" collapsible="false" >
                   
                    <div class="easyui-layout" data-options="fit:true" id="norcontextDivs2" style="overflow:hidden;" > 
                       
                    <div   region="west" style="font-size:18px;background: #ebf9ff;border:0;width:200px"   collapsible="false"  data-options="headerCls:'aaaa'">
                    <table>
                    <tr>      
                     <td  nowrap align="center" style="heiht:34px;font-size: 20px;"><i class="fa fa-check" aria-hidden="true" style="font-size:20px;color:#00ff00;"></i>在用电梯</td>   
                      <td colspan="2" style="heiht:34px;font-size: 20px;" >&nbsp;&nbsp;<span id="ywdt2" style="font-size: 20px;color:#0066FF"></span></td>
                      <td style="heiht:34px;font-size: 20px;">台</td>
                    </tr>
                    <tr>
                    <td style="height:15px;" colspan="3"></td>
                    </tr>
                     <tr>      
                     <td  nowrap align="center" style="heiht:34px;font-size: 20px;"><i class="fa  fa-times" aria-hidden="true" style="font-size:20px;color:#ff0000;"></i>停&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用</td>      
                     <td colspan="2" style="heiht:34px;font-size: 20px;" >&nbsp;&nbsp;<span id="dailing" style="font-size: 20px;color:#0066FF"></span></td>
                    <td style="heiht:34px;font-size: 20px;">台</td>
                    </tr>
                     <tr>
                    <td style="height:15px;" colspan="3"></td>
                    </tr>
                     <tr>      
                     <td  nowrap align="center" style="heiht:34px;font-size: 20px;"><i class="fa fa-exclamation" aria-hidden="true" style="font-size:20px;color:#ff9900;"></i>涉&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;秘</td>      
                     <td colspan="2" style="heiht:34px;font-size: 20px;" >&nbsp;&nbsp;<span id="shemi" style="font-size: 20px;color:#0066FF"></span></td>
                     <td style="heiht:34px;font-size: 20px;">台</td>
                    </tr>
                    <tr>
                    <td style="height:15px;" colspan="3"></td>
                    </tr>
                    <tr>      
                     <td  nowrap align="center" style="heiht:34px;font-size: 20px;"><i class="fa fa-users" aria-hidden="true" style="font-size:20px;color:#0066FF;"></i>人梯配比</td>      
                     <td colspan="2" style="heiht:34px;font-size: 20px;" >&nbsp;&nbsp;<span id="rtpb" style="font-size: 20px;color:#0066FF"></span></td>
                     <td style="heiht:34px;font-size: 20px;">台/人</td>
                    </tr>
                    
                    </table>
                    </div>
                    
                    <div region="center" id ="dtsl"  style="padding:20px;overflow:hidden;"></div>
                   </div>
                    </div> 
                    
                    <div region="center" title="信用排名" id="cnorcontextDiv" style="overflow:hidden;position:relative;background:#6fa8dc;"> 
                         <div style="position:absolute; left:120px; top:20px;font-size:18px;color:#F5F5F5;">
                          <table>
                          <tr>      
                           <td width="100" nowrap align="center" style="font-size: 20px;">上月排名</td>                                
                          <td colspan="2"><span style="font-size: 20px;color:#0066FF" id ="sypm"></span></td>
                          </tr>
                          </table>
                        </div>
                        
                        <div id ="wbpm"  style="padding:20px;"></div>
                        
                        
                    </div>
             </div>       
     </div>
     
     
        
         <div region="south" border="0" id ="cencontextDiv">
            <div class="easyui-layout" data-options="fit:true" id="cencontextDivs" >
                 <div region="west"  title="运维统计" id ="wcencontextDiv" style="overflow:hidden;position:relative;background:#6fa8dc;" collapsible="false" >
                 
                  <div class="easyui-layout" data-options="fit:true" id="cencontextDivs2" style="overflow:hidden;" > 
                    <div   region="west" style="font-size:18px;background: #ebf9ff;border:0;width:200px"   collapsible="false"  data-options="headerCls:'aaaa'">
                    <table>
                    <tr>      
                     <td  nowrap align="center" style="heiht:34px;font-size: 20px;"><i class="fa fa-check" aria-hidden="true" style="font-size:20px;color:#00ff00;"></i>未&nbsp;&nbsp;到&nbsp;&nbsp;期</td>   
                      <td colspan="2" style="heiht:34px;font-size: 20px;" >&nbsp;&nbsp;<span id="wdq" style="font-size: 20px;color:#0066FF"></span></td>
                       <td style="heiht:34px;font-size: 20px;">台</td>
                    </tr>
                    <tr>
                    <td style="height:15px;" colspan="3"></td>
                    </tr>
                     <tr>      
                     <td  nowrap align="center" style="heiht:34px;font-size: 20px;"><i class="fa  fa-check" aria-hidden="true" style="font-size:20px;color:#ff9900;"></i>即将到期</td>      
                     <td colspan="2" style="heiht:34px;font-size: 20px;" >&nbsp;&nbsp;<span id="jjdq" style="font-size: 20px;color:#0066FF"></span></td>
                      <td style="heiht:34px;font-size: 20px;">台</td>
                    </tr>
                     <tr>
                    <td style="height:15px;" colspan="3"></td>
                    </tr>
                     <tr>      
                     <td  nowrap align="center" style="heiht:34px;font-size: 20px;"><i class="fa fa-exclamation" aria-hidden="true" style="font-size:20px;color:#ff0000;"></i>超&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期</td>      
                     <td colspan="2" style="heiht:34px;font-size: 20px;" >&nbsp;&nbsp;<span id="cq" style="font-size: 20px;color:#0066FF"></span></td>
                      <td style="heiht:34px;font-size: 20px;">台</td>
                    </tr>
                       
                    </table>
                    </div>
                    
                      <div id ="dtwb" region="center" style="padding:20px;"></div>
                      </div>
                     
                 </div>
                 <div region="center" title="信用得分" id="ccencontextDiv" style="overflow:hidden;position:relative;"> 
                     <div style="position:absolute; left:120px; top:20px;font-size:18px;color:#F5F5F5;">
                          <table>
                          <tr>      
                           <td width="100" nowrap align="center" style="font-size: 20px;">上月得分</td>                                
                          <td colspan="2"><span id="sydf" style="font-size: 20px;color:#0066FF"></span></td>
                          </tr>
                          </table>
                        </div>
                        
                         <div id ="wbdf"  style="padding:20px;"></div>
                                                         
                       
                   </div> 
              </div>   
         </div> 
         
       
   

</body>
</html>