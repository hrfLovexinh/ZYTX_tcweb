<%@ page import="com.zytx.models.UserInfo" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工作绩效</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>

<!-- <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>  -->
 <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>  
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

.top{
 width:100%;
 margin:auto;
 border:0px;
 height:60%; 
 }
 
.bottom{
 width:100%
 margin:auto;
 border:0px;
 height:40%;  
 text-align:center;
}

.datagrid-header-row td{background-color:#F5FFFA;font-weight:bold;}
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
<script type="text/javascript">
$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	var xdata =['锦江区','青羊区','金牛区','武侯区','成华区','高新区','天府新区','龙泉驿区','青白江区','新都区','温江区','金堂县','双流区','郫县','大邑县','蒲江县','新津县','都江堰市','彭州市','邛崃市','崇州市','简阳市'];
    var ydata = [666,778,836, 996, 915,1111,396,537,157,758,470,172,871,623,113,84,134,203,157,101,138,127];
    var ydata2 =[600,701,754, 899, 823,1008,330,485,142,684,415,156,786,562,102,40,121,184,142,92,125,115];
    var ydata3 =[60,70,75, 89, 82,100,33,48,14,68,41,15,78,56,10,40,12,18,14,9,12,11];  
    var ydata4 =[6,7, 7, 8, 8,10,3,4,1,6,4,1,7,5,1,4,1,1,1,0,1,1];  

	// 路径配置
//	require.config({
	//    paths: {
	  //      echarts: 'http://echarts.baidu.com/build/dist'
	//    }
	//});

	//使用
//	require(
//	    [
	//        'echarts',
	 //       'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
	 //   ],
	//    function (ec) {
	        // 基于准备好的dom，初始化echarts图表
	//        var myChart = ec.init(document.getElementById('jptjbarDiv')); 
	       var myChart = echarts.init(document.getElementById('jptjbarDiv'));
	       
	        var option = {
	            title: {  
                text: '全市电梯应急平台救援情况', 
                x: 'center',
                y: 'top', 
                textStyle: {  
                    color: '#3366CC',  
                    fontSize: 18 
	            } 
                },  
	            tooltip: {
	                show: true
	            },
	      //      color:['#08a9f2','#FF6633'],
	            legend: {
	                data:['救援案件数','一级救援','二级救援','三级救援'],
	                x: 'right',
	                textStyle: {  
	                    color: '#3366CC',  
	                    fontSize: 12 
		            }  
	         //       height: testMaptjContainer.style.height,
	         //        width: testMaptjContainer.style.width
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
	                		interval:0, 
	                		rotate:0,//倾斜度 -90 至 90 默认为0
	                		margin:2,   
	                		textStyle: {
	                		color: '#3366CC',  
	                        fontSize: 12 
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
	                    "name":"救援案件数",
	                    "type":"bar",
	                    "barWidth": 20,//固定柱子宽度
	                    "barGap": '0%',  //设置相同标记位柱形图之间的相对位置
	                    "data":ydata,
	                    itemStyle: {
	                        normal: {
	                            barBorderRadius: 20,
	                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
	                                offset: 0,
	                                color: '#FFFFCC'    // 0% 处的颜色
	                            }, {
	                                offset: 1,
	                                color: '#FFFF00'    // 100% 处的颜色
	                            }],false),
	                            shadowColor: 'rgba(0, 0, 0, 0.4)',
	                            shadowBlur: 20,
	                            label: {  
                                show: true,//是否展示  
                                position:'outer',
                                textStyle: {  
                                    fontWeight:'bolder',  
                                    fontSize : '12',  
                                    fontFamily : '微软雅黑',
                                    color:'#3366CC'
                                    
                                }  
                                }  
	                        }
	                    }
	                },
	                {
	                	 "name":"一级救援",
		                 "type":"bar",
		                 "barWidth": 15,//固定柱子宽度
		                 "barGap": '0%',  //设置相同标记位柱形图之间的相对位置
		                 "data":ydata2,
		               //设置柱体颜色  
                          itemStyle: {
	                        normal: {
	                            barBorderRadius: 20,
	                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
	                                offset: 0,
	                                color: '#99d9ea'    // 0% 处的颜色
	                            }, {
	                                offset: 1,
	                                color: '#3fa7dc'    // 100% 处的颜色
	                            }],false),
	                            shadowColor: 'rgba(0, 0, 0, 0.4)',
	                            shadowBlur: 20,
	                            label: {  
                                show: true,//是否展示  
                                position:'outer',
                                textStyle: {  
                                    fontWeight:'bolder',  
                                    fontSize : '12',  
                                    fontFamily : '微软雅黑',
                                    color:'#c23531'
                                    
                                }  
                                }  
	                        }
	                    }
		                
		            }, {
	                	 "name":"二级救援",
		                 "type":"bar",
		                 "barWidth": 10,//固定柱子宽度
		                 "barGap": '0%',  //设置相同标记位柱形图之间的相对位置
		                 "data":ydata3,
		               //设置柱体颜色  
                          itemStyle: {
	                        normal: {
	                            barBorderRadius: 20,
	                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
	                                offset: 0,
	                                color: '#CCFFCC'    // 0% 处的颜色
	                            }, {
	                                offset: 1,
	                                color: '#CCFFCC'    // 100% 处的颜色
	                            }],false),
	                            shadowColor: 'rgba(0, 0, 0, 0.4)',
	                            shadowBlur: 20,
	                            label: {  
                                show: true,//是否展示  
                                position:'outer',
                                textStyle: {  
                                    fontWeight:'bolder',  
                                    fontSize : '12',  
                                    fontFamily : '微软雅黑',
                                    color:'#8B0000'
                                    
                                }  
                                }  
	                        }
	                    }
		                
		            }, {
	                	 "name":"三级救援",
		                 "type":"bar",
		                 "barWidth": 5,//固定柱子宽度
		                 "barGap": '0%',  //设置相同标记位柱形图之间的相对位置
		                 "data":ydata4,
		               //设置柱体颜色  
                          itemStyle: {
	                        normal: {
	                            barBorderRadius: 20,
	                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
	                                offset: 0,
	                                color: '#FFFACD'    // 0% 处的颜色
	                            }, {
	                                offset: 1,
	                                color: '#FFDEAD'    // 100% 处的颜色
	                            }],false),
	                            shadowColor: 'rgba(0, 0, 0, 0.4)',
	                            shadowBlur: 20,
	                            label: {  
                                show: true,//是否展示  
                                position:'outer',
                                textStyle: {  
                                    fontWeight:'bolder',  
                                    fontSize : '12',  
                                    fontFamily : '微软雅黑',
                                    color:'#000000'
                                    
                                }  
                                }  
	                        }
	                    }
		                
		            }
	            ]
	        };

	        myChart.setOption(option);  $("#jptt").height($('#tdiv').height()*0.8);
	        jpttdata();
//   });
});

function jpttdata(){ 
	grid=$('#jptt').datagrid({
	    title:"",
	    striped:true,
	    url:'/tcweb/job/jobPerformance',
	    queryParams:{},
	    columns:[[
            {field:'itemName',title:'行政区划',width:$(this).width() * 0.0416,align:'center'},
            {field:'jjtotal',title:'锦江区',width:$(this).width() * 0.0416,align:'center'},
	        {field:'qytotal',title:'青羊区',width:$(this).width() * 0.0416,align:'center'},
	        {field:'jntotal',title:'金牛区',width:$(this).width() * 0.0416,align:'center'},
	        {field:'whtotal',title:'武侯区',width:$(this).width() * 0.0416,align:'center'},
	        {field:'chtotal',title:'成华区',width:$(this).width() * 0.0416,align:'center'},
	        {field:'gxtotal',title:'高新区',width:$(this).width() * 0.0416,align:'center'},
	        {field:'tfxtotal',title:'天府新区',width:$(this).width() * 0.0416,align:'center'},
	        {field:'lqytotal',title:'龙泉驿区',width:$(this).width() * 0.0416,align:'center'},
	        {field:'qbjtotal',title:'青白江区',width:$(this).width() * 0.0416,align:'center'},
	        {field:'xdtotal',title:'新都区',width:$(this).width() * 0.0416,align:'center'},
	        {field:'wjtotal',title:'温江区',width:$(this).width() * 0.0416,align:'center'},
	        {field:'jttotal',title:'金堂县',width:$(this).width() * 0.0416,align:'center'},
	        {field:'sltotal',title:'双流县',width:$(this).width() * 0.0416,align:'center'},
	        {field:'pxtotal',title:'郫县',width:$(this).width() * 0.0416,align:'center'},
	        {field:'dytotal',title:'大邑县',width:$(this).width() * 0.0416,align:'center'},
	        {field:'xjtotal',title:'新津县',width:$(this).width() * 0.0416,align:'center'},
	        {field:'pjtotal',title:'蒲江县',width:$(this).width() * 0.0416,align:'center'},
	        {field:'djytotal',title:'都江堰市',width:$(this).width() * 0.0416,align:'center'},
	        {field:'pztotal',title:'彭州市',width:$(this).width() * 0.0416,align:'center'},	
	        {field:'qltotal',title:'邛崃市',width:$(this).width() * 0.0416,align:'center'},	
	        {field:'cztotal',title:'崇州市',width:$(this).width() * 0.0416,align:'center'},	
	        {field:'jytotal',title:'简阳市',width:$(this).width() * 0.0416,align:'center'},
	        {field:'hejitotal',title:'合计',width:$(this).width() * 0.0416,align:'center',formatter: function(value,rec,index) { 
		  //      return parseInt(rec.jjtotal)+parseInt(rec.qytotal)+parseInt(rec.qytotal)+parseInt(rec.jntotal)+parseInt(rec.whtotal)+parseInt(rec.chtotal)+parseInt(rec.gxtotal)+parseInt(rec.tfxtotal)+parseInt(rec.lqytotal)+parseInt(rec.qbjtotal)+parseInt(rec.xdtotal)+parseInt(rec.wjtotal)+parseInt(rec.jttotal)+parseInt(rec.sltotal)+parseInt(rec.pxtotal)+parseInt(rec.dytotal)+parseInt(rec.xjtotal)+parseInt(rec.pjtotal)+parseInt(rec.djytotal)+parseInt(rec.pztotal)+parseInt(rec.qltotal)+parseInt(rec.cztotal)+parseInt(rec.jytotal);
                  return  parseInt(rec.jjtotal)+parseInt(rec.qytotal)+parseInt(rec.qytotal)+parseInt(rec.jntotal)+parseInt(rec.whtotal)+parseInt(rec.chtotal)+parseInt(rec.gxtotal)+parseInt(rec.tfxtotal)+parseInt(rec.lqytotal)+parseInt(rec.qbjtotal)+parseInt(rec.xdtotal)+parseInt(rec.wjtotal)+parseInt(rec.jttotal)+parseInt(rec.sltotal)+parseInt(rec.pxtotal)+parseInt(rec.dytotal)+parseInt(rec.xjtotal)+parseInt(rec.pjtotal)+parseInt(rec.djytotal)+parseInt(rec.pztotal)+parseInt(rec.qltotal)+parseInt(rec.cztotal)+parseInt(rec.jytotal);
		        },styler: function(value,row,index){
					
						return 'color:#3366CC;font-weight:bold';
					
				}
	        }
	        	 	       
	    ]], 
	    
	        fitColumns:true, 
		    pagination:false,
		    singleSelect:true,
		    striped:true,
		    nowrap:false
		    
	});	

	
}

//指定列求和
function compute(colName) {
    var rows = $('#table').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
        total += parseFloat(rows[i][colName]);
    }
    return total;
}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
 <div region="north"  border="0" style="height:60px;;background-color:rgb(201,220,245);">
 
   <table border="0"> 
   <tr> 
    
   <td>电梯类别</td>
   <td nowrap><select id="eleType"  class="easyui-combobox" name="eleType" style="width:152px;">
    <option value="所有电梯">所有电梯</option>
    <option value="曳引与强制驱动电梯">曳引与强制驱动电梯</option>
    <option value="曳引驱动乘客电梯">曳引驱动乘客电梯</option>
    <option value="曳引驱动载货电梯">曳引驱动载货电梯</option>  
    <option value="强制驱动载货电梯">强制驱动载货电梯</option>
    <option value="液压驱动电梯">液压驱动电梯</option>  
    <option value="液压乘客电梯">液压乘客电梯</option>
    <option value="液压载货电梯">液压载货电梯</option> 
    <option value="自动扶梯与自动人行道">自动扶梯与自动人行道</option>
    <option value="自动扶梯">自动扶梯</option>
    <option value="自动人行道">自动人行道</option>
    <option value="其它类型电梯">其它类型电梯</option>
    <option value="防爆电梯">防爆电梯</option>
    <option value="消防员电梯">消防员电梯</option>
    <option value="杂物电梯">杂物电梯</option>
   </select></td>
   <td  nowrap>时间：</td>
   <td>从<input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" style="width:152px;"></input></td>
   <td>到<input id="qendTime"  type="text" class="easyui-datebox" name="qendTime" style="width:152px;"></input></td>
	 <td colspan="2">		
	 <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
	 <a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 					
   </td>				
   </tr>
     </table>
</div>
 <div region="center" style="text-align:center;overflow: hidden;">
      <div class="top" id="jptjbarDiv">
      </div>
      <div class="bottom">
      <div style="width:100%;margin:auto;height:40px;">
      <font style='color:#3366CC;font-size:18px;font-weight:bold'>救援案件数：11128,实施一级救援：9967(一级救援率89.56%),实施二级救援：989(二级救援率8.88%),实施三级救援：94(三级救援率0.84%)</font>
      </div>
       <div id ="tdiv" style="width:100%;margin:auto;height:100%;text-align:center;">
      <table id="jptt" style="margin:auto;" width="100%"></table>
     </div>
      </div>
 </div>
</body>
</html>