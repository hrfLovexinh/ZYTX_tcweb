<%@ page import="com.zytx.models.UserInfo" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统统计</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
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
</style>
<% 
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


%>
<script>

$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	$('#area').combobox({   
        url:'/tcweb/elevator/areaInfoList',   
        valueField:'area',   
        textField:'area'  
    }); 
	
	var contextDivContainer = document.getElementById('contextDiv');   
	var h = contextDivContainer.clientHeight * 0.48;  
	$('#contextDiv').layout('panel','north').panel('resize',{height:h});
	$('#contextDiv').layout('resize');

	var stlcontextDivContainer = document.getElementById('stscontextDiv');
	var w = contextDivContainer.clientWidth * 0.48; 
	$('#stscontextDiv').layout('panel','west').panel('resize',{width:w});
	$('#stscontextDiv').layout('resize');

	var stlcontextDivContainer = document.getElementById('stxcontextDiv');
	var w = contextDivContainer.clientWidth * 0.48; 
	$('#stxcontextDiv').layout('panel','west').panel('resize',{width:w});
	$('#stxcontextDiv').layout('resize');

	

	compute();
});

function compute(){  
//	var xdata2 = document.all.xdata.value;
//	var ydata2 = document.all.ydata.value;    

    var stldivContainer = document.getElementById('stldiv'); 
   $('#sbyhl').height(stldivContainer.clientHeight * 0.9);  
   
   var stlcdivContainer = document.getElementById('stlcdiv'); 
   $('#sbdjl').height(stlcdivContainer.clientHeight * 0.9);  

   var stxldivContainer = document.getElementById('stxldiv'); 
   $('#sbjyl').height(stxldivContainer.clientHeight * 0.9);  

   var stxlcdivContainer = document.getElementById('stxlcdiv'); 
   $('#sbwcjys').height(stxlcdivContainer.clientHeight * 0.9);  
  
	var xdata =['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'];
    var ydata =[7, 6,11, 8, 2,9,7,8,9,11,11];

    var ydata2 =[100, 99, 100, 98, 99,99,98,100,99,99,98];  
    var ydata3 =[100, 98, 95, 98, 96,100,98,96,100,98,99];  
    var ydata4 =[10050, 12806, 18205, 15423, 18902,15842,16058,17085,18200,15428,17600];  
   //   eval("var xdata ="+xdata2);
    //  eval("var ydata ="+ydata2);
	  require.config({
          paths: {
              echarts: 'http://echarts.baidu.com/build/dist'
          }
      });
    
   // 使用
      require(
          [
              'echarts',
              'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
          ],
          function (ec) {
              // 基于准备好的dom，初始化echarts图表
              var myChart = ec.init(document.getElementById('sbyhl'));  
              var myChart2 = ec.init(document.getElementById('sbdjl'));  
              var myChart3 = ec.init(document.getElementById('sbjyl'));
              var myChart4 = ec.init(document.getElementById('sbwcjys'));
              
              var option = {
            	  title: {
                  text: '电梯隐患率'
              },
                  tooltip: {
                      show: true
                  },
                  legend: {
                      data:['']
                  },
                  xAxis : [
                      {
                          type : 'category',
                          data : xdata
                      }
                  ],
                  yAxis : [
                      {   splitLine:{show: false},
                          type : 'value',
                          axisLabel: {
                              formatter: '{value} %'
                          }
                      }
                  ],
                  series : [
                      {
                          "name":"隐患率",
                          "type":"line",
                          "data":ydata
                      }
                  ]
              };
       
              var option2 = {
                	  title: {
                      text: '电梯登记率'
                  },
                      tooltip: {
                          show: true
                      },
                      legend: {
                          data:['']
                      },
                      xAxis : [
                          {
                              type : 'category',
                              data : xdata
                          }
                      ],
                      yAxis : [
                          {   splitLine:{show: false},
                              type : 'value',
                              axisLabel: {
                                  formatter: '{value} %'
                              }
                          }
                      ],
                      series : [
                          {
                              "name":"登记率",
                              "type":"line",
                              "data":ydata2
                          }
                      ]
                  };   

              var option3 = {
                	  title: {
                      text: '电梯检验率'
                  },
                      tooltip: {
                          show: true
                      },
                      legend: {
                          data:['']
                      },
                      xAxis : [
                          {
                              type : 'category',
                              data : xdata
                          }
                      ],
                      yAxis : [
                          {   splitLine:{show: false},
                              type : 'value',
                              axisLabel: {
                                  formatter: '{value} %'
                              }
                          }
                      ],
                      series : [
                          {
                              "name":"检验率",
                              "type":"line",
                              "data":ydata3
                          }
                      ]
                  };   


              var option4 = {
                	  title: {
                      text: '电梯完成检验数'
                  },
                      tooltip: {
                          show: true
                      },
                      legend: {
                          data:['']
                      },
                      xAxis : [
                          {
                              type : 'category',
                              data : xdata
                          }
                      ],
                      yAxis : [
                          {   splitLine:{show: false},
                              type : 'value'
                          }
                      ],
                      series : [
                          {
                              "name":"完成检验数",
                              "type":"line",
                              "data":ydata4
                          }
                      ]
                  };   
                  
              // 为echarts对象加载数据   
              myChart.setOption(option); 
              myChart2.setOption(option2); 
              myChart3.setOption(option3); 
              myChart4.setOption(option4);
          }
      );
}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
 <div region="north"  border="0" style="height:60px;;background-color:rgb(201,220,245);">
 
   <table border="0"> 
   <tr> 
   <td nowrap> 
   <select id="trcondition"  class="easyui-combobox" name="inoutDoor" style="width:152px;">
    <option value="指标概况">指标概况</option>
   </select>
    </td>
    <td>行政区划：
    </td>

 <!-- <td><select id="area"  class="easyui-combobox" name="area" style="width:152px;">
   <option value=""></option>
   <option value="锦江">锦江</option>
   <option value="青羊">青羊</option>
   <option value="金牛">金牛</option>
   <option value="武侯">武侯</option>
   <option value="成华">成华</option>
   <option value="高新">高新</option>
   <option value="龙泉驿">龙泉驿</option>
   <option value="青白江">青白江</option>
   <option value="新都">新都</option> 
   <option value="温江">温江</option> 
   <option value="金堂">金堂</option>
   <option value="双流">双流</option> 
   <option value="郫县">郫县</option> 
   <option value="大邑">大邑</option>
   <option value="蒲江">蒲江</option>
   <option value="新津">新津</option>
   <option value="都江堰">都江堰</option>
   <option value="彭州">彭州</option>
   <option value="邛崃">邛崃</option>
   <option value="崇州">崇州</option>
   <option value="简阳">简阳</option>
   <option value="天府新">天府新</option> 
</select>
   </td>  --> 
   <td><input id="area" name="area" style="width:154px;"/></td>
	 <td colspan="2">		
	 <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
	 <a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 					
   </td>				
   </tr>
     </table>
</div> 

 <div region="center">
     <div class="easyui-layout" data-options="fit:true" id="contextDiv"> 
     
        <div region="north"  border="0" id ="stdiv">
            <div class="easyui-layout" data-options="fit:true" id="stscontextDiv" >
                   <div region="west"  id ="stldiv" style="overflow:hidden">
                       <div id ="sbyhl"  style="padding:20px;">
                                                              
                         
                         
                       </div>
                    </div> 
                    <div region="center" id="stlcdiv" style="overflow:hidden"> 
                        <div id="sbdjl"  style="padding:20px;">  
                                                              本年度设备登记率
                      
                       
                        </div>
                    </div>
             </div>       
        </div>
        
        
        <div region="center">
            <div class="easyui-layout" data-options="fit:true" id="stxcontextDiv" >
                 <div region="west"  id ="stxldiv" style="overflow:hidden">
                     <div id="sbjyl"  style="padding:20px;">  
                                                       本年度设备检验率
                        
                      </div>  
                  </div>
                  <div region="center" id="stxlcdiv" style="overflow:hidden"> 
                    <div id="sbwcjys"  style="padding:20px;">
                                                         本年度设备完成检验数  
                       
                     </div> 
                 </div>   
         </div>  
       </div>    
    </div>  
 </div>

</body>
</html>