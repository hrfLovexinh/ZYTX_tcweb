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

function compute1(){   
	 jQuery.post('/tcweb/cs/dthz',{'ywcompanyId':<%=companyId%>,'cqday':<%=cqday%>}, function(data){ 
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
	        draw12(dtzs,dailingCount,shemiCount,ywcqCount,ywjjcqCount); 
		   }, 'json');
}


function draw12(dtzs,dailingCount,shemiCount,ywcqCount,ywjjcqCount){
     
	 var wnorcontextDivContainer = document.getElementById('wnorcontextDiv'); 
	 $('#dtsl').height(wnorcontextDivContainer.clientHeight * 0.90);  

	 var wcencontextDivContainer = document.getElementById('wcencontextDiv'); 
	 $('#dtwb').height(wcencontextDivContainer.clientHeight * 0.90);  


	 
	 require.config({
         paths: {
             echarts: 'http://echarts.baidu.com/build/dist'
         }
     });

	 require(
	          [
	              'echarts',
	              'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
	              'echarts/chart/pie',
	              'echarts/chart/line'
	          ],
	          function (ec) {
	        	  var myChart = ec.init(document.getElementById('dtsl'));  
	        	  var myChart2 = ec.init(document.getElementById('dtwb')); 
	       // 	  var myChart3 = ec.init(document.getElementById('wbpm'));
	       // 	  var myChart4 = ec.init(document.getElementById('wbdf'));
          
         
	              var option = {
	            		  backgroundColor: '#F5F5F5',
	         		  title: {  
	              //    text: '电梯数量',  
	             //     backgroundColor: '#F5F5F5',
	                  textStyle: {  
	                      fontSize: 18
	                  } 
	                  },
	         			  tooltip: {
	         			        trigger: 'item',
	         			        formatter: "{a} <br/>{b}: {c} ({d}%)"
	         			    },
	         			   legend: {
	        			        orient: 'vertical',   //horizontal  vertical
	        			        x: 'right',
	        			        data:['在用','停用','涉秘'],
	        			        textStyle: {
	        			            fontSize: 18
	        			        }
	        			          
	        			    },
	        			    
	         			    series: [
	         			        {
	         			            name:'',
	         			            type:'pie',
	         			            radius: ['0%', '55%'],
	         			            center: ['40%', '50%'],//饼图的位置
	         			            avoidLabelOverlap: false,
	         			            itemStyle : {//图形样式  
	                                     normal : {//正常时的样式  
	                                         label : {  
	                                             show : true,
	                                             textStyle:{
	                                                 fontSize: 18 
	                                                 },
	                                               formatter: function(params){

	                                                   return params.name+':'+params.value+'台';
	                                                     } 
	                                      
	                                         },  
	                                         labelLine : {  
	                                             show : true,
	                                             length:20
	                                         }  
	                                     }
	         			        }, 
	         			            data:[
	         			                {value:dtzs-dailingCount, name:'在用',itemStyle:{ normal:{color:'#99FF99'} }},
	         			                {value:dailingCount, name:'停用',itemStyle:{ normal:{color:'#ff0000'} } },
	         			                {value:shemiCount, name:'涉秘',itemStyle:{ normal:{color:'#FF9933'} } }         
	         			            ]
	         			        }
	         			        
	         			    ]
	         			};    

	         			
             
	              var option2 = {
	                  backgroundColor: '#F5F5F5',
	         	/*	  title: {  
	                  text: '电梯维保',  
	                  backgroundColor: '#F5F5F5',
	                  textStyle: {  
	                      fontSize: 18
	                  } 
	                  }, */
	                
	         			    tooltip: {
	         			        trigger: 'item',
	         			        formatter: "{a} <br/>{b}: {c} ({d}%)"
	         			    },
	         			   legend: {
	        			//        orient: 'vertical', 
	        			        orient: 'horizontal',  
	        			//        x: 'right',
	        			         x: 'center',
	        			        data:['未到期','即将到期','超期'],
	        			        textStyle: {
	        			            fontSize: 18
	        			        }
	        			          
	        			    },
	        			    
	         			    series: [
	         			        {
	         			            name:'',
	         			            type:'pie',
	         			            radius: ['0%', '55%'],
	         			            center: ['50%', '50%'],//饼图的位置
	         			            avoidLabelOverlap: false,
	         			            itemStyle : {//图形样式  
	                                     normal : {//正常时的样式  
	                                         label : {  
	                                             show : true,
	                                             textStyle:{
	                                                 fontSize: 18 
	                                                 },
	                                               formatter: function(params){

	                                                   return params.name+':'+params.value+'台';
	                                                     } 
	                                         },  
	                                         labelLine : {  
	                                             show : true,
	                                             length:20  
	                                         }  
	                                     }
	         			        }, 
	         			            data:[
	         			                {value:dtzs-ywcqCount-dailingCount, name:'未到期',itemStyle:{ normal:{color:'#99FF99'} }},
	         			                {value:ywjjcqCount, name:'即将到期',itemStyle:{ normal:{color:'#FF9933'} } },
	         			                {value:ywcqCount, name:'超期',itemStyle:{ normal:{color:'#ff0000'} } }         
	         			            ]
	         			        }
	         			        
	         			    ] 
	         			};
                      
                  myChart.setOption(option); 
	              myChart2.setOption(option2); 
	      //        myChart3.setOption(option3); 
	      //        myChart4.setOption(option4);
	           
		          }
	        );

	
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
	 $('#wbpm').height(cnorcontextDivContainer.clientHeight * 0.90);  
	
	 var ccencontextDivContainer = document.getElementById('ccencontextDiv'); 
	 $('#wbdf').height(ccencontextDivContainer.clientHeight * 0.90);  

//	 var    xdata3 =[1, 2, 3, 4, 5,6,7,8,9,10,11,12];
//	 var    xdata4 =[1, 2, 3, 4, 5,6,7,8,9,10,11,12];

	 require.config({
         paths: {
             echarts: 'http://echarts.baidu.com/build/dist'
         }
     });

	 require(
	          [
	              'echarts',
	              'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
	              'echarts/chart/pie',
	              'echarts/chart/line'
	          ],
	          function (ec) {
	        	  var myChart3 = ec.init(document.getElementById('wbpm'));
	        	  var myChart4 = ec.init(document.getElementById('wbdf'));
	        	  var    option3 = {
	                  	//	  backgroundColor: '#c8d9f5',
	                  	      backgroundColor: '#F5F5F5',
	                  	    /*	  title: {
	                               text: '信用排名',
	                              backgroundColor: '#F5F5F5', 
	                              textStyle: {  
	                                  fontSize: 18 
	                            //      color: '#fbf202'
	                              }},  */
	                  		    xAxis : [
	                                       {   splitLine:{show: true,lineStyle: {  
	                                           // 使用深浅的间隔色  
	                                        //   color: ['#ffffff']
	                                             color: ['#F5F5F5']  
	                                       }},
	                                           type : 'category',
	                                           data : xdata3
	                                    /*       data :[{
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
	                                       	}] */
	                                       }
	                                   ],
	                                   yAxis : [
	                                            {   splitLine:{show: true,lineStyle: {  
	                                                // 使用深浅的间隔色  
	                                              //  color: ['#ffffff'] 
	                                            	color: ['#F5F5F5']   
	                                            }},
	                                                type : 'value',
	                                                axisLabel: {
	                                                    textStyle: {
	                                                        color: '#ffffff', 
	                                                    //    color:'#0066FF',
	                                                        fontSize:'12'
	                                                      
	                                                        
	                                                    }
	                                                }
	                                            }
	                                        ],
	                  		    series: [{
	                  		   //     data: [56, 0, 120, 80, 70, 110, 130],
	                  		        data: ydata3,
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
	                  		                color: '#c0c0c0',
	                  		                label : {
	                                              show : true,  //柱头数字
	                                              position : 'top',
	                                              textStyle: {
                                                     color:'#0066FF',
                                                      fontSize:'12'
                                                    
                                                      
                                                  }
	                                            
	                                          }
	                  		            }
	                  		        }
	                  		    }]
	                  		};  


	  	              var    option4 = {
	  	                		  backgroundColor: '#F5F5F5',
	  	                    /*  	  title: {
	  	                            text: '信用得分',
	  	                            backgroundColor: '#F5F5F5',
	  	                            textStyle: {  
	  	                                fontSize: 18 
	  	                       //         color: '#fbf202'
	  	                            }},  */
	  	                		    xAxis : [
	  	                                     {   splitLine:{show: true,lineStyle: {  
	  	                                         // 使用深浅的间隔色  
	  	                                     //    color: ['#ffffff']  
	  	                                    	color: ['#F5F5F5'] 
	  	                                     }},
	  	                                         type : 'category',
	  	                                         data : xdata4
	  	                                  /*       data :[{
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
	  	                                     	}]  */
	  	                                     }
	  	                                 ],
	  	                                 yAxis : [
	  	                                          {   splitLine:{show: true,lineStyle: {  
	  	                                              // 使用深浅的间隔色  
	  	                                        //      color: ['#ffffff']
	  	                                        	color: ['#F5F5F5']   
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
	  	                		 //       data: [56, 0, 120, 80, 70, 110, 130],
	  	                		        data : ydata4,
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
	  	                		                color: '#c0c0c0',
	  	                		                label : {
	  	                                            show : true,  //柱头数字
	  	                                            position : 'top',
	  	                                            textStyle: {
	                                                     color:'#0066FF',
	                                                      fontSize:'12'
	                                                    
	                                                      
	                                                  }
	  	                                          
	  	                                        }
	  	                		            }
	  	                		        }
	  	                		    }]
	  	                		};
	                  
	  	             
	  	              myChart3.setOption(option3); 
	  	              myChart4.setOption(option4);
	  	           
	  		          }
	  	        );

	  	
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
                    <div region="west" title="电梯运维" id ="wnorcontextDiv" style="overflow:hidden;position:relative;" collapsible="false" >
                   
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
                     <td  nowrap align="center" style="heiht:34px;font-size: 20px;"><i class="fa fa-users" aria-hidden="true" style="font-size:20px;color:#0066FF;"></i><br>人梯配比数</td>      
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