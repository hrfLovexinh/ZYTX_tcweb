<%@ page import="com.zytx.models.UserInfo" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统统计</title>
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
margin-top: 20px;
float:right;
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

	
	
	var contextDivContainer = document.getElementById('contextDivs');   
	var h = contextDivContainer.clientHeight * 0.33;     
	$('#contextDivs').layout('panel','north').panel('resize',{height:h});
	$('#contextDivs').layout('resize');   

	$('#contextDivs').layout('panel','south').panel('resize',{height:h});
	$('#contextDivs').layout('resize'); 

	var stlcontextDivContainer = document.getElementById('contextDiv');
	var w = contextDivContainer.clientWidth * 0.48;                 
	$('#contextDiv').layout('panel','west').panel('resize',{width:w});
	$('#contextDiv').layout('resize');  

	var stlcontextDivContainer = document.getElementById('contextDiv2');
	var w = contextDivContainer.clientWidth * 0.48; 
	$('#contextDiv2').layout('panel','west').panel('resize',{width:w});
	$('#contextDiv2').layout('resize');    

	var stlcontextDivContainer = document.getElementById('contextDiv3');
	var w = contextDivContainer.clientWidth * 0.48; 
	$('#contextDiv3').layout('panel','west').panel('resize',{width:w});
	$('#contextDiv3').layout('resize');

	compute();
	
});

function compute(){    
//	var xdata2 = document.all.xdata.value;
//	var ydata2 = document.all.ydata.value;    
//  offsetHeight 
    var wcontextDivContainer = document.getElementById('wcontextDiv'); 
    $('#dtsl').height(wcontextDivContainer.clientHeight * 1);   
    $('#cqyj').height(wcontextDivContainer.clientHeight * 1);  
 

   var wcontextDiv2Container = document.getElementById('wcontextDiv2'); 
   $('#zgts').width(wcontextDiv2Container.clientWidth * 1);   
   $('#zgts').height(wcontextDiv2Container.clientHeight);   
 //  $('#sbwcjys').height(stxlcdivContainer.clientHeight * 0.9); 
  // $('#jyzt').width(wcontextDiv2Container.clientWidth * 1);   
   $('#jyzt').height(wcontextDiv2Container.clientHeight);  

   var wcontextDiv3Container = document.getElementById('wcontextDiv3'); 
   $('#bjtj').width(wcontextDiv3Container.clientWidth * 1);   
   $('#bjtj').height(wcontextDiv3Container.clientHeight);  
 //  $('#gzfx').width(wcontextDiv3Container.clientWidth * 1);   
   $('#gzfx').height(wcontextDiv3Container.clientHeight);  
  
//	var xdata =['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'];
//  var ydata =[7, 6,11, 8, 2,9,7,8,9,11,11];

    var xdata =['在用','停用','拆除','新装','改造'];
    var ydata =[205,10,2,30,9];

 //   var ydata2 =[100, 99, 100, 98, 99,99,98,100,99,99,98]; 
    var xdata2 =[0, 0,0,0,13];  
    var ydata2 =['未经检验','检验超期','检验不合格','电梯未维保','办理使用登记'];  
 //   var ydata3 =[100, 98, 95, 98, 96,100,98,96,100,98,99];  
  //  var ydata4 =[10050, 12806, 18205, 15423, 18902,15842,16058,17085,18200,15428,17600];  
   //   eval("var xdata ="+xdata2);
    //  eval("var ydata ="+ydata2);
    
  var xdata4 =['已报检','未报检','检验中','检验完成'];
  var ydata4 =[109, 10,79,50];

  var xdata5 =['卡布里城一期','卡布里城二期','黄山城一期','黄山城二期'];
  var ydata5_1 =[3, 2,1,3];
  var ydata5_2 =[1, 2,1,1];
  var ydata5_3 =[3, 1,2,1];
  var ydata5_4 =[1, 1,2,1];
  
	  require.config({
          paths: {
              echarts: 'http://echarts.baidu.com/build/dist'
          }
      });
    
   // 使用
      require(
          [
              'echarts',
              'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
              'echarts/chart/pie',
              'echarts/chart/line'
          ],
          function (ec) {
              // 基于准备好的dom，初始化echarts图表
              var myChart = ec.init(document.getElementById('dtsl'));  
              var myChart2 = ec.init(document.getElementById('cqyj'));  
              var myChart3 = ec.init(document.getElementById('zgts'));
              var myChart4 = ec.init(document.getElementById('jyzt'));
              var myChart5 = ec.init(document.getElementById('bjtj'));
              var myChart6 = ec.init(document.getElementById('gzfx'));
              
              var option = {
                  backgroundColor: '#c8d9f5',            
            	  title: {
                  text: '电梯数量',
                  textStyle: {
            	    fontSize: 18,               //标题颜色
      	        	color: '#fbf202'
      	      	},
                        
              },
                  tooltip: {
                      show: true
                  },
                  legend: {
                      data:['']
                //	  x:'left',
                //      data:['设备总量','在用设备'],
                //	  selected:{'设备总量':true,'在用设备':false}
                      
                  },
                  xAxis : [
                      {   
                          type : 'category',
                        //设置坐标轴字体颜色和宽度
          		/*	    axisLine:{
          			        lineStyle:{
          			            color:'yellow',
          			            width:2
          			        }
          			    }, */
                                    
              //            data : xdata,
              //  var xdata =['在用','停用','拆除','新装','改造'];
                          data :[{
                        	    value: '在用',
                        	    textStyle: {
                        	        fontSize: 14,
                        	        color: '#ffffff'
                        	       
                        	    }
                        	},{
                        	    value: '停用',
                        	    textStyle: {
                        	        fontSize: 14,
                        	        color: '#ffffff'
                        	       
                        	    }
                        	},{
                        	    value: '拆除',
                        	    textStyle: {
                        	        fontSize: 14,
                        	        color: '#ffffff'
                        	       
                        	    }
                        	},{
                        	    value: '新装',
                        	    textStyle: {
                        	        fontSize: 14,
                        	        color: '#ffffff'
                        	       
                        	    }
                        	},{
                        	    value: '改造',
                        	    textStyle: {
                        	        fontSize: 14,
                        	        color: '#ffffff'
                        	       
                        	    }
                        	}], 
                          splitLine:{show: false}
                     
                      }
                  ],
                  yAxis : [
                      {   
                          splitLine:{show: true,lineStyle: {  
                          // 使用深浅的间隔色  
                          color: ['#ffffff']  
                          }},
                          type : 'value',
                          axisLabel : {textStyle: {
                              color: '#ffffff'
                          }       
                          }
                      }
                  ],
                  series : [
                           {
                           "name":"电梯数量",
                           "type":"bar",
                           "barWidth": 35,//固定柱子宽度
                           "data":ydata,
                           itemStyle:{  
                              normal:{
                                //      color:'#08a9f2',
                                       color: function (params){
  	                        var colorList = ['#0000ff','#ff00ff','#808000','#ffff00','#00ff00'];
  	                        return colorList[params.dataIndex];},
                                      label : {show: true, position: 'top'}
                                     
                                   }  
                            }  
                           }
                  ]
                  
              };
       
              var option2 = {
            		  backgroundColor: '#c8d9f5',
         		  title: {  
                  text: '超期预警',  
                  textStyle: {  
                      fontSize: 18, 
                      color: '#fbf202'
                  } 
                  },  
                 
         			    tooltip: {
         			        trigger: 'item',
         			        formatter: "{a} <br/>{b}: {c} ({d}%)"
         			    },
         			   legend: {
        			        orient: 'vertical',   //horizontal  vertical
        			        x: 'right',
        			        data:['当前维保中','当前维保提醒','检验超期'],
        			        textStyle: {
        			            fontSize: 18,
        			            color: '#ffffff'
        			        }
        			          
        			    },
        			    
         			    series: [
         			        {
         			            name:'超期预警',
         			            type:'pie',
         			            radius: ['0%', '55%'],
         			            avoidLabelOverlap: false,
         			            itemStyle : {//图形样式  
                                     normal : {//正常时的样式  
                                         label : {  
                                             show : true,
                                             textStyle:{
         			        	                 color: '#ffffff',
                                                 fontSize: 18 
                                                 },
                                               formatter: function(params){

                                                   return params.name+':'+params.value+'台';
                                                     } 
                                       //      position: 'inner'
                                         },  
                                         labelLine : {  
                                             show : true,
                                             length:20  
                                         }  
                                     }
         			        }, 
         			            data:[
         			                {value:10, name:'当前维保超期'},
         			                {value:72, name:'当前维保提醒',itemStyle:{ normal:{color:'#0066FF'} } },
         			                {value:0, name:'检验超期',itemStyle:{ normal:{color:'#99FF33'} } }         
         			            ]
         			        }
         			        
         			    ]
         			};   

              var option3 = {
            		  backgroundColor: '#c8d9f5',
                	  title: {
                      text: '整改提示',
                      textStyle: {  
                          fontSize: 18, 
                          color: '#fbf202'
                      } 
                  },
                      tooltip: {
                          show: true
                      },
                     
                      legend: {
                          data:['']
                      },
                      xAxis : [
                          {   
                        	  splitLine:{show: true,lineStyle: {  
                              // 使用深浅的间隔色  
                              color: ['#ffffff']  
                          }},
                              type : 'value',
                              axisLabel: {
                                  textStyle: {
                                      color: '#ffffff',
                                      fontSize:'12'
                                    
                                      
                                  }
                              }
                          }
                      ],
                      yAxis : [
                          {   splitLine:{show: true, lineStyle: {  
                              // 使用深浅的间隔色  
                              color: ['#ffffff']  
                          }  },
                              type : 'category',
                      //        data:ydata2
                      // var ydata2 =['未经检验','检验超期','检验不合格','电梯未维保','未办理使用登记'];  
                              data :[{
                          	    value: '未经检验',
                          	    textStyle: {
                          	        fontSize: 12,
                          	      color: '#ffffff'
                          	       
                          	    }
                          	},{
                          	    value: '检验超期',
                          	    textStyle: {
                          	        fontSize: 12,
                          	      color: '#ffffff'
                          	       
                          	    }
                          	},{
                          	    value: '检验不合格',
                          	    textStyle: {
                          	        fontSize: 12,
                          	      color: '#ffffff'
                          	       
                          	    }
                          	},{
                          	    value: '电梯未维保',
                          	    textStyle: {
                          	        fontSize: 12,
                          	      color: '#ffffff'
                          	       
                          	    }
                          	},{
                          	    value: '未办使用登记',
                          	    textStyle: {
                          	        fontSize: 12,
                          	      color: '#ffffff'
                          	       
                          	    }
                          	}]
                          }
                      ],
                      series : [
                          {
                              "name":"检验率",
                              "type":"bar",
                              "barWidth": 20,//固定柱子宽度
                              itemStyle:{  
                                  normal:{
                                      color:function (params){
                                	  var colorList = ['#f54882','#47d1de','#8058bd','#0099CC','#ab78ba'];                    
                                	   return colorList[params.dataIndex];       	                 
                                	  },   //柱状颜色
                                      label : {
                                          show : true,  //柱头数字
                                          position : 'right'
                                          
                                      }
                                  }
                              }, 
                              "data":xdata2
                          }
                      ]
                  };   

             /*
              var option4 = {
            		  backgroundColor: '#c8d9f5',
                	  title: {
                      text: '检验状态',
                      textStyle: {  
                          fontSize: 18, 
                          color: '#fbf202'
                      } 
                  },
                      tooltip: {
                          show: true
                      },
                      legend: {
                          data:['']
                      },
             //         var xdata4 =['已报检','未报检','检验中','检验完成'];
                      xAxis : [
                          {   splitLine:{show: true,lineStyle: {  
                              // 使用深浅的间隔色  
                              color: ['#ffffff']  
                          }},
                              type : 'category',
                      //        data : xdata4
                              data :[{
                          	    value: '已报检',
                          	    textStyle: {
                          	        fontSize: 14,
                          	      color: '#ffffff'
                          	       
                          	    }
                          	},{
                          	    value: '未报检',
                          	    textStyle: {
                          	        fontSize: 14,
                          	      color: '#ffffff'
                          	       
                          	    }
                          	},{
                          	    value: '检验中',
                          	    textStyle: {
                          	        fontSize: 14,
                          	      color: '#ffffff'
                          	       
                          	    }
                          	},{
                          	    value: '检验完成',
                          	    textStyle: {
                          	        fontSize: 14,
                          	      color: '#ffffff'
                          	       
                          	    }
                          	}]
                          }
                      ],
                      yAxis : [
                          {   splitLine:{show: false,lineStyle: {  
                              // 使用深浅的间隔色  
                              color: ['#ffffff']  
                          }},
                              type : 'value',
                              axisLabel: {
                                  textStyle: {
                                      color: '#ffffff',
                                      fontSize:'12'
                                    
                                      
                                  }
                              }
                          }
                      ],
                      series : [
                          {
                              "name":"",
                              "type":"line",
                              itemStyle:{  
                                  normal:{
                                      label : {
                                          show : true,  //柱头数字
                                          position : 'top'
                                          
                                      }
                                  }
                              }, 
                              "data":ydata4
                          }
                      ]
                  };   */

              var    option4 = {
                	/*	    xAxis: {
                		        type: 'category',
                		        data: ['已报检','未报检','检验中','检验完成']
                		    },*/
                		  backgroundColor: '#c8d9f5',
                      	  title: {
                            text: '检验状态',
                            textStyle: {  
                                fontSize: 18, 
                                color: '#fbf202'
                            }}, 
                		    xAxis : [
                                     {   splitLine:{show: true,lineStyle: {  
                                         // 使用深浅的间隔色  
                                         color: ['#ffffff']  
                                     }},
                                         type : 'category',
                                 //        data : xdata4
                                         data :[{
                                     	    value: '已报检',
                                     	    textStyle: {
                                     	        fontSize: 14,
                                     	      color: '#ffffff'
                                     	       
                                     	    }
                                     	},{
                                     	    value: '未报检',
                                     	    textStyle: {
                                     	        fontSize: 14,
                                     	      color: '#ffffff'
                                     	       
                                     	    }
                                     	},{
                                     	    value: '检验中',
                                     	    textStyle: {
                                     	        fontSize: 14,
                                     	      color: '#ffffff'
                                     	       
                                     	    }
                                     	},{
                                     	    value: '检验完成',
                                     	    textStyle: {
                                     	        fontSize: 14,
                                     	      color: '#ffffff'
                                     	       
                                     	    }
                                     	}]
                                     }
                                 ],
                                 yAxis : [
                                          {   splitLine:{show: true,lineStyle: {  
                                              // 使用深浅的间隔色  
                                              color: ['#ffffff']  
                                          }},
                                              type : 'value',
                                              axisLabel: {
                                                  textStyle: {
                                                      color: '#ffffff',
                                                      fontSize:'12'
                                                    
                                                      
                                                  }
                                              }
                                          }
                                      ],
                		    series: [{
                		        data: [56, 0, 120, 80, 70, 110, 130],
                		        type: 'line',
                		        symbol: 'triangle',
                		        symbolSize: 10,
                		        lineStyle: {
                		            normal: {
                		                color: 'green',
                		                width: 4,
                		                type: 'dashed'
                		            }
                		        },
                		        itemStyle: {
                		            normal: {
                		                borderWidth: 3,
                		                borderColor: 'blue',
                		                color: '#ffff00',
                		                label : {
                                            show : true,  //柱头数字
                                            position : 'top'
                                          
                                        }
                		            }
                		        }
                		    }]
                		};

             
                  
              var option5 = {
            		  backgroundColor: '#c8d9f5',
      	            title: {  
                  text: '报警统计', 
                  x: 'left',
                  y: 'top', 
                  textStyle: {  
                      fontSize: 18,
                      color: '#fbf202' 
  	            } 
                  },  
  	            tooltip: {
  	                show: true
  	            },
  	          legend: {
			        orient: 'horizontal',   //horizontal  vertical
			        x: 'center',
			        data:['app报警','一键报警','语音报警','智能报警'],
			        textStyle: {
			            "fontSize": 18,
			            color: '#ffffff'
			        }  
			    },
  	      //      color:['#08a9f2','#FF6633'],
  	            grid: {
  	                left: '3%',
  	                right: '4%',
  	                bottom: '3%',
  	                containLabel: true
  	            },
  	            xAxis : [
  	                {   
  	                	splitLine:{show: true,lineStyle: {  
                        // 使用深浅的间隔色  
                        color: ['#ffffff']  
                    }},//去除网格线
  	                    type : 'category',
  	              //      data : xdata5,
  	             //  var xdata5 =['卡布里城一期','卡布里城二期','黄山城一期','黄山城二期'];
  	                     data :[{
                          	    value: '卡布里城一期',
                          	    textStyle: {
                          	        fontSize: 14,
                          	      color: '#ffffff'
                          	       
                          	    }
                          	},{
                          	    value: '卡布里城二期',
                          	    textStyle: {
                          	        fontSize: 14,
                          	      color: '#ffffff'
                          	       
                          	    }
                          	},{
                          	    value: '黄山城一期',
                          	    textStyle: {
                          	        fontSize: 14,
                          	      color: '#ffffff'
                          	       
                          	    }
                          	},{
                          	    value: '黄山城二期',
                          	    textStyle: {
                          	        fontSize: 14,
                          	      color: '#ffffff'
                          	       
                          	    }
                          	}],
  	                    axisLabel:{
  	                		interval:0, 
  	                		rotate:0,//倾斜度 -90 至 90 默认为0
  	                		margin:2,   
  	                		textStyle: {
  	                	//	color: '#3366CC',  
  	                        fontSize: 12,
  	                      color: '#ffffff' 
  	    	            } 
  	                
  	                   }
  	                }
  	            ],
  	            yAxis : [
  	                {
  	                	splitLine:{show: true,lineStyle: {  
                        // 使用深浅的间隔色  
                        color: ['#ffffff']  
                    }},//去除网格线
  	                    type : 'value',
  	                    axisLabel:{
  	                        //X轴刻度配置
  	                  //      formatter: '{value} °C',
  	                  //      interval:'3' //0：表示全部显示不间隔；auto:表示自动根据刻度个数和宽度自动设置间隔个数
  	                    	textStyle: {  
  	                        fontSize: 6 ,
  	                      color: '#ffffff'
  	    	            } 
  	                       
  	                   }
  	                    
  	                }
  	            ],
  	            series : [
  	                {
  	                    "name":"app报警",
  	                    "type":"bar",
  	                    "barWidth": 20,//固定柱子宽度
  	                    "barGap":0,  //设置相同标记位柱形图之间的相对位置
  	                    "data":ydata5_1,
  	                    itemStyle: {
  	                        normal: {
  	                //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
  	               /*     color: function (params){
  	                        var colorList = ['#0000ff','#ff00ff','#808000','#800080'];
  	                        return colorList[params.dataIndex];
  	                    },   */  
  	                            color:'#0000ff',        
  	                            barBorderRadius: 0,
  	                            shadowColor: '#ff00ff',
  	                            shadowBlur: 20,
  	                            label: {
                                   
                                  show: true,//是否展示  
                                  position:'inside',
                                  textStyle: {  
                                      fontWeight:'bolder',  
                                      fontSize : '12',  
                                      fontFamily : '微软雅黑',
                                      color:'#0000ff'
                                      
                                  }  
                                  }  
  	                        }
  	                    }
  	                },
  	                {
  	                	 "name":"一键报警",
  		                 "type":"bar",
  		                 "barWidth": 20,//固定柱子宽度
  		               "barGap":0,  //设置相同标记位柱形图之间的相对位置  
  		                 "data":ydata5_2,
  		               //设置柱体颜色  
                            itemStyle: {
  	                        normal: {
  	                	        color:'#00ff00',  
  	                            barBorderRadius: 0,
  	                            shadowColor: '#ff00ff',
  	                            shadowBlur: 20,
  	                            label: {  
                                  show: true,//是否展示  
                                  position:'inside',
                                  textStyle: {  
                                      fontWeight:'bolder',  
                                      fontSize : '12',  
                                      fontFamily : '微软雅黑',
                                      color:'#0000ff'
                                      
                                  }  
                                  }  
  	                        }
  	                    }
  		                
  		            }, {
  	                	 "name":"语音报警",
  		                 "type":"bar",
  		                 "barWidth": 20,//固定柱子宽度
  		               "barGap":0,  //设置相同标记位柱形图之间的相对位置
  		                 "data":ydata5_3,
  		               //设置柱体颜色  
                            itemStyle: {   
  	                        normal: {
  		            	        color:'#800000',
  	                            barBorderRadius: 0,
  	                            shadowColor: '#ff00ff',
  	                            shadowBlur: 20,
  	                            label: {  
                                  show: true,//是否展示  
                                  position:'inside',
                                  textStyle: {  
                                      fontWeight:'bolder',  
                                      fontSize : '12',  
                                      fontFamily : '微软雅黑',
                                      color:'#0000ff'
                                      
                                  }  
                                  }  
  	                        }
  	                    }
  		                
  		            }, {
  	                	 "name":"智能报警",
  		                 "type":"bar",
  		                 "barWidth": 20,//固定柱子宽度
  		                 "barGap" :0,  //设置相同标记位柱形图之间的相对位置
  		                 "data":ydata5_4,
  		               //设置柱体颜色  
                            itemStyle: {
  	                        normal: {
  		            	        color:'#808000',
  	                            barBorderRadius: 0,
  	                            shadowColor: 'rgba(0, 0, 0, 0.4)',
  	                            shadowBlur: 20,
  	                            label: {  
                                  show: true,//是否展示  
                                  position:'inside',
                                  textStyle: {  
                                      fontWeight:'bolder',  
                                      fontSize : '12',  
                                      fontFamily : '微软雅黑',
                                      color:'#0000ff'
                                      
                                  }  
                                  }  
  	                        }
  	                    }
  		                
  		            }
  	            ]
  	        };

              var option6 = {
            		  backgroundColor: '#c8d9f5',
          			 title: {  
                   text: '故障分析',  
                   textStyle: {  
            	       color: '#fbf202',
                       fontSize: 18,
                       fontStyle:'normal',
                       fontWeight:'bold', 
                       fontFamily:'sans-serif'
                           
                   },
                   x : 'left'
                  
                   },  
          			    tooltip: {
          			        trigger: 'item',
          			        formatter: "{a} <br/>{b}: {c} ({d}%)"
          			    },
          			  legend: {
        			        orient: 'vertical',   //horizontal  vertical
        			        x: 'right',
        			        data:['人为原因','外部原因','门系统','拽引系统','导向系统','轿厢','控制系统','电气系统','安全保护装置'],
        			        textStyle: {
        			            fontSize: 18,
        			            color:'#ffffff'
        			        }
        			          
        			    },
          			    series: [
          			        {
          			            name:'故障分析',
          			            type:'pie',
          			            radius: ['0%', '55%'],
          			            avoidLabelOverlap: false,
          			            itemStyle : {//图形样式  
                                      normal : {//正常时的样式  
                                          label : {  
                                              show : true,
                                              textStyle:{
                                                  color:'#ffffff',
                                                  fontSize: 18 
                                                  },
                                                formatter: function(params){

                                                    return params.value+'%';
                                                      } 
                                        //      position: 'inner'
                                          },  
                                          labelLine : {  
                                              show : true,
                                              length:15
                                          }  
                                      }
          			        }, 
          			            data:[
          			                {value:10.2, name:'人为原因'},
          			                {value:17, name:'外部原因',itemStyle:{ normal:{color:'#0066FF'} }},
          			                {value:21.53, name:'门系统',itemStyle:{ normal:{color:'#808000'} }},
          			                {value:1.98, name:'拽引系统'},
          			                {value:3.12, name:'导向系统'},
          			                {value:5.67, name:'轿厢'},
          			                {value:23.23, name:'控制系统',itemStyle:{ normal:{color:'#800080'} }},  
          			                {value:5.95, name:'电气系统'}, 
          			                {value:11.33, name:'安全保护装置'} 
          			            ]
          			        }
          			        
          			    ]
          			};   
                  
              // 为echarts对象加载数据   
              myChart.setOption(option);   
              myChart2.setOption(option2); 
              myChart3.setOption(option3); 
              myChart4.setOption(option4);
              myChart5.setOption(option5);
              myChart6.setOption(option6);
          }
      );
}



</script>
</head>
<body class="easyui-layout" data-options="fit:true" style="text-align:center;">
 <div region="north"  border="0" style="height:100px;background-color:rgb(201,220,245);">
   <div style="height:100px;float:right;both:clear;width:100%;background-color: silver;background-repeat: no-repeat;background-positon: 100%, 100%; position:relative;">
    <img src="images/wgpt.png" style="width:100%;height:100%"/>
      <ul class="tabmenu">
      <li><a href="#tab2" id="tUName2" class="tileHead2"  style="width:40px;color:#00ff00;font-weight:bold;">使用单位：成都家园经营管理有限公司</a></li>
      <li></li>
      <li><a href="#tab3" class="exitinHead" style="width:40px;" onclick="exitSystem()">退出</a></li>
     </ul>
    </div>  
 
</div> 

 <div region="center" data-options="border:false">
 
     <div class="easyui-layout" data-options="fit:true">
     
  <!--   <div region="north"  border="0" style="height:60px;background-color:rgb(201,220,245);"> --> 
    <div region="north"  border="0" style="height:60px;background-color:#0855a3;background-image:url('images/wgptbj.png');" >
     <ul class="daohangl">
     <li style="color:#ffffff;font-weight:bold;">所属项目： <select id="shxm"  class="easyui-combobox" name="shxm" style="width:252px;">
    <option value="全部">全部</option>
     </select></li>
     </ul>
    <ul class="daohang">
    <li><a href="#" style="background-color:#e5e5e5;color:#0855a3;">统计概况</a></li>
    <li><a href="#">法律法规</a></li>
    <li><a href="#">网上报检查</a></li>
    <li><a href="#">应急报警</a></li>
    <li><a href="#">事故分析</a></li>
    <li><a href="#">使用状态变更申请</a></li>
    </ul>
    </div>    
    <div region="center">
     <div class="easyui-layout" data-options="fit:true" id="contextDivs"> 
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
       
       <div region="south">
            <div class="easyui-layout" data-options="fit:true" id="contextDiv3" >
                 <div region="west"  title="" id ="wcontextDiv3" style="overflow:hidden;position:relative;" collapsible="false" data-options="tools:'#tt5'">
                    <div style="position:absolute; right:5px; top:5px;font-size:18px;color:#ffffff">
                    <input type="radio" name="bjtjro" value="0" checked="checked" />半年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" name="bjtjro" value="1" />年
                    </div>
                     <div id="bjtj"  style="padding:20px;background-image:url('images/wgptbj.png');">  
                                                       
                        
                      </div>  
                  </div>
                  <div region="center"  title="" id="ccontextDiv3" style="overflow:hidden" data-options="tools:'#tt6'"> 
                    <div id="gzfx"  style="padding:20px;background-image:url('images/wgptbj.png');">
                                                          
                       
                     </div> 
                 </div>   
         </div>  
       </div>      
    </div> 
    </div> 
    </div>
 </div>
<!--  
 <div id="tt">
        <a href="javascript:void(0)" class="icon-add" onclick="javascript:alert('add')"></a>
        <a href="javascript:void(0)" class="icon-edit" onclick="javascript:alert('edit')"></a>
        <a href="javascript:void(0)" class="icon-cut" onclick="javascript:alert('cut')"></a>
        <a href="javascript:void(0)" class="icon-help" onclick="javascript:alert('help')"></a>
  </div>  -->
  <div id="tt">
        <a href="javascript:void(0)" class="icon-xiangq" onclick="javascript:alert('明细')" style="display:block; width:90px;text-align:right;font-weight: bold;color: #0E2D5F;font-size: 12px;">明细</a>
        
  </div>
   <div id="tt2">
        <a href="javascript:void(0)" class="icon-xiangq" onclick="javascript:alert('明细')" style="display:block; width:90px;text-align:right;font-weight: bold;color: #0E2D5F;font-size: 12px;">明细</a>
        
  </div>
   <div id="tt3">
        <a href="javascript:void(0)" class="icon-xiangq" onclick="javascript:alert('明细')" style="display:block; width:90px;text-align:right;font-weight: bold;color: #0E2D5F;font-size: 12px;">明细</a>
        
  </div>
   <div id="tt4">
        <a href="javascript:void(0)" class="icon-xiangq" onclick="javascript:alert('明细')" style="display:block; width:90px;text-align:right;font-weight: bold;color: #0E2D5F;font-size: 12px;">明细</a>
        
  </div>
   <div id="tt5">
        <a href="javascript:void(0)" class="icon-xiangq" onclick="javascript:alert('明细')" style="display:block; width:90px;text-align:right;font-weight: bold;color: #0E2D5F;font-size: 12px;">明细</a>
        
  </div>
   <div id="tt6">
        <a href="javascript:void(0)" class="icon-xiangq" onclick="javascript:alert('明细')" style="display:block; width:90px;text-align:right;font-weight: bold;color: #0E2D5F;font-size: 12px;">明细</a>
        
  </div>
</body>
</html>