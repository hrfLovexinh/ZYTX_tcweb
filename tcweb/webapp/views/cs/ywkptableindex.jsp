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
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/My97DatePicker/WdatePicker.js"></script>
 <!-- <script src="http://echarts.baidu.com/build/dist/echarts.js"></script> -->
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
   int companyId = 0;
   
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
<script type="text/javascript">
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

$.fn.datebox.defaults.formatter = function(date){ 
	 var y = date.getFullYear(); 
	 var m = date.getMonth()+1; 
	 var d = date.getDate(); 
	 return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d); 
	 }; 
	 $.fn.datebox.defaults.parser = function(s){ 
	 if (!s) return new Date(); 
	 var ss = s.split('-'); 
	 var y = parseInt(ss[0],10); 
	 var m = parseInt(ss[1],10); 
	 var d = parseInt(ss[2],10); 
	 if (!isNaN(y) && !isNaN(m) && !isNaN(d)){ 
	 return new Date(y,m-1,d); 
	 } else { 
	 return new Date(); 
	 } 
	 };
	 
$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	 $("#ratingDate2").val(myformatter(new Date(), "yyyy-MM"));  //设置查询条件的默认时间
   
	 grid=$('#tt').datagrid({
		    title:'维保信用',
	        fitColumns:true,
		    pageSize:25,
		    pageList:[15,25,30,35,40],
		    url:'/tcweb/cs/ywkptable',
		    queryParams:{'ywcompanyId':'<%=companyId%>'},
		    columns:[[
		        {field:'ratingDate',align:'center',halign:'center',title:'时间',width:$(this).width() * 0.33},
		        {field:'tScore',align:'center',title:'得分',width:$(this).width() * 0.33},
		        {field:'tSort',align:'center',title:'名次',width:$(this).width() * 0.33}
		    ]],
		    pagination:true,
		    singleSelect:true,
		    striped:true,
		    onLoadSuccess:function(data){
			    if(index ==0){
		    	var rows = $("#tt").datagrid("getRows"); 
		    	xdata.splice(0,xdata.length);
		    	ydata.splice(0,ydata.length);
		    	for(var i=0;i<rows.length;i++){
		    		xdata.push(rows[i].ratingDate);
		    		ydata.push(rows[i].tSort);
		    	}
			    
		    	compute1();  
			    }
			}
			     
		});	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  


	
	});

var xdata =  new Array();
var ydata =  new Array();
var index =0;

function compute1(){ 
	 var h = ($("body").height()-60)* 0.5;
	$('#contextDivs').layout('panel','south').panel('resize',{height:h});
	$('#contextDivs').layout('resize');

	 var southcontextDivContainer = document.getElementById('southcontextDiv'); 
	 $('#kpqs').height(southcontextDivContainer.clientHeight * 0.98); 
	 
	  var myChart = echarts.init(document.getElementById('kpqs'));  
	  var  option = {
			    xAxis: {
			        type: 'category',
			        boundaryGap: false,
			        data: xdata.reverse()
			    },
			    yAxis: {
			        type: 'value',
			        name: '排名',
			        splitLine: {
			            lineStyle: {
			                type: 'dashed'
			            }
			        },
			        axisLine: {
			            show: false
			        },
			        axisTick: {
			            show: false
			        },
			        axisLabel: {
			            formatter: '{value}'
			        }
			    },
			    series: [{
			        data: ydata.reverse(),
			        type: 'line',
			        showSymbol: false,
			        smooth: true,
			        itemStyle: {
			            normal: {
			                color: "#16D9F0"
			            }
			        },
			        areaStyle: {
			            normal: {
			                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
			                    offset: 0,
			                    color: 'rgba(17, 168,171, 1)'
			                }, {
			                    offset: 1,
			                    color: '#fff'
			                }])
			            }
			        }
			    }]
			};
           myChart.setOption(option);     
	
}

//格式化日期
function myformatter(date) {
    //获取年份
    var y = date.getFullYear();
    //获取月份
    var m = date.getMonth() + 1;
    if(m<10)
        return y + '-0' + m;
    else
        return y + '-' + m;
}

function query(){
	index = 1;
    var ywCompanyId =<%=companyId%>; 
    var ratingDate = $('#ratingDate2').attr("value"); 
    if(ywCompanyId == "")
    	ywCompanyId = 0;
	
    grid.datagrid("options").url='/tcweb/cs/ywkptablequery';
    grid.datagrid("options").queryParams={'ywcompanyId':ywCompanyId,'ratingDate':ratingDate};
    
   $('#tt').datagrid('reload');
}

function clearQuery(){
	$('#ratingDate2').attr("value","");
}

function kpDetail(ywCompanyId,ratingDate){
	
	 jQuery.post('/tcweb/cs/kpDetail',{'ywcompanyId':ywCompanyId,'ratingDate':ratingDate}, function(data){ 
	        data = eval(data);//POST方法必加，ajax方法自动处理了                ywkpDetail                             
	        var datalist = data.ywkpDetail;
	        var addtable = $('#addtable'); 
	        var subtracttable = $('#subtracttable'); 

	  //      $("#addtable tr").not(':eq(0)').empty();
	 //       $("#subtracttable tr").not(':eq(0)').empty();
	        $('#addtable tbody').html('');
	        $('#subtracttable tbody').html('');

	        $("#hjaddtable").text('');
		 	$("#hjsubtracttable").text('');

	        if(datalist){ 
	        var officeSpacesj =datalist.officeSpacesj;
	        var maintenanceEleCountsj =datalist.maintenanceEleCountsj;
	        var avgmaintenanceEleCountsj =datalist.avgmaintenanceEleCountsj;
	        var fixedTelOnDutysj=datalist.fixedTelOnDutysj;
	        var telOnDutyunattendedsj =datalist.telOnDutyunattendedsj;
	        var enterpriseChangesj =datalist.enterpriseChangesj;
	        var enterpriseRecordsj =datalist.enterpriseRecordsj;
	        var infoComRatesj =datalist.infoComRatesj;
	        var sweepCodeRatesj =datalist.sweepCodeRatesj;
	        var sweepCodeInTimeRatesj =datalist.sweepCodeInTimeRatesj;
	        var alarmDealwithsj =datalist.alarmDealwithsj;
	        var regularInspectionsj=datalist.regularInspectionsj;
	        var inspectElevatorsj =datalist.inspectElevatorsj;
	        var acceptInspElevatorsj=datalist.acceptInspElevatorsj;
	        var maintenSceneInfosj = datalist.maintenSceneInfosj;
	        var malignantEventssj =datalist.malignantEventssj;
	        var complaintsEventssj =datalist.complaintsEventssj;
	        var maintenBusinesssj=datalist.maintenBusinesssj;
	        var honestsj =datalist.honestsj;
	        var punishmentsj=datalist.punishmentsj;
	        var secondRescuePointsj=datalist.secondRescuePointsj;
	        var positiveEnergysj= datalist.positiveEnergysj;
	        var expertsSuggestionsj= datalist.expertsSuggestionsj;
	        var positiveWorksj =datalist.positiveWorksj;
	        var remoteMonitorsj =datalist.remoteMonitorsj;
	        var elevatorInsurancesj =datalist.elevatorInsurancesj;
	        var techinnovationsj =datalist.techinnovationsj;

	        var hjaddtable =0;
            var hjsubtracttable =0;
        	  
	        if(officeSpacesj > 100){
	          var $tr = $("<tr>"+
	        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>办公面积</td>"+
	        	  "<td class='form_td'>"+(officeSpacesj-100)+"</td>"+
	        	  "</tr>");
	        addtable.append($tr);
	        hjaddtable =Number(hjaddtable)+Number(officeSpacesj-100);
	        	}
	        if(officeSpacesj < 100){
	        	var $tr = $("<tr>"+
	        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>办公面积</td>"+
	        		"<td class='form_td'>"+(officeSpacesj-100)+"</td>"+
	        		"</tr>");
	        		subtracttable.append($tr);
	        		hjsubtracttable =Number(hjsubtracttable)+Number(officeSpacesj-100);
		        	}
            
	        if(maintenanceEleCountsj > 100){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>维保电梯数量</td>"+
		        	  "<td class='form_td'>"+(maintenanceEleCountsj-100)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(maintenanceEleCountsj-100);	
		        	}
		     if(maintenanceEleCountsj < 100){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>维保电梯数量</td>"+
		        		"<td class='form_td'>"+(maintenanceEleCountsj-100)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(maintenanceEleCountsj-100);
			        	}
		   
		     if(avgmaintenanceEleCountsj > 100){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>人均维保数量</td>"+
		        	  "<td class='form_td'>"+(avgmaintenanceEleCountsj-100)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(avgmaintenanceEleCountsj-100);			
		        	}
		     if(avgmaintenanceEleCountsj < 100){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>人均维保数量</td>"+
		        		"<td class='form_td'>"+(avgmaintenanceEleCountsj-100)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(avgmaintenanceEleCountsj-100);
			        	}
		    
	        if(fixedTelOnDutysj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>值班固定电话</td>"+
		        	  "<td class='form_td'>"+(fixedTelOnDutysj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(fixedTelOnDutysj-0);			
		        	}
		      if(fixedTelOnDutysj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>值班固定电话</td>"+
		        		"<td class='form_td'>"+(fixedTelOnDutysj-0)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(fixedTelOnDutysj-0);
			        	}
		     
		     if(telOnDutyunattendedsj > 0){
			          var $tr = $("<tr>"+
			        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>电话值守<br>无人接听</td>"+
			        	  "<td class='form_td'>"+(telOnDutyunattendedsj-0)+"</td>"+
			        	  "</tr>");
			        addtable.append($tr);
			        hjaddtable =Number(hjaddtable)+Number(telOnDutyunattendedsj-0);		
			        	}
			  if(telOnDutyunattendedsj < 0){
			        	var $tr = $("<tr>"+
			        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>电话值守<br>无人接听</td>"+
			        		"<td class='form_td'>"+(telOnDutyunattendedsj-0)+"</td>"+
			        		"</tr>");
			        subtracttable.append($tr);
			        hjsubtracttable =Number(hjsubtracttable)+Number(telOnDutyunattendedsj-0);
				        	}
			  if(enterpriseChangesj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>维保企业变更</td>"+
		        	  "<td class='form_td'>"+(enterpriseChangesj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(enterpriseChangesj-0);			
		        	}
		    if(enterpriseChangesj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' style='heiht:34px;font-size: 20px;'>维保企业变更</td>"+
		        		"<td class='form_td'>"+(enterpriseChangesj-0)+"</td>"+
		        		"</tr>");
		        subtracttable.append($tr);
		        hjsubtracttable =Number(hjsubtracttable)+Number(enterpriseChangesj-0);
			        	}
        

		    if(enterpriseRecordsj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>外地企业备案</td>"+
		        	  "<td class='form_td'>"+(enterpriseRecordsj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(enterpriseRecordsj-0);		
		        	}
		    if(enterpriseRecordsj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>外地企业备案</td>"+
		        		"<td class='form_td'>"+(enterpriseRecordsj-0)+"</td>"+
		        		"</tr>");
		        subtracttable.append($tr);
		        hjsubtracttable =Number(hjsubtracttable)+Number(enterpriseRecordsj-0);	
			        	}
		    
		    if(infoComRatesj > 100){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>信息完整率</td>"+
		        	  "<td class='form_td'>"+Number(infoComRatesj-100).toFixed(2)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(Number(infoComRatesj-100).toFixed(2));			
		        	}
		     if(infoComRatesj < 100){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>信息完整率</td>"+
		        		"<td class='form_td'>"+Number(infoComRatesj-100).toFixed(2)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		 hjsubtracttable =Number(hjsubtracttable)+Number(Number(infoComRatesj-100).toFixed(2));	
			        	}
		     
		     if(sweepCodeRatesj > 100){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>扫码维保率</td>"+
		        	  "<td class='form_td'>"+Number(sweepCodeRatesj-100).toFixed(2)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(Number(sweepCodeRatesj-100).toFixed(2));			
		        	}
		     if(sweepCodeRatesj < 100){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>扫码维保率</td>"+
		        		"<td class='form_td'>"+Number(sweepCodeRatesj-100).toFixed(2)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		 hjsubtracttable =Number(hjsubtracttable)+Number(Number(sweepCodeRatesj-100).toFixed(2));	
			        	}

		     if(sweepCodeInTimeRatesj > 100){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>按时扫码维保率</td>"+
		        	  "<td class='form_td'>"+Number(sweepCodeInTimeRatesj-100).toFixed(2)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(Number(sweepCodeInTimeRatesj-100).toFixed(2));			
		        	}
		     if(sweepCodeInTimeRatesj < 100){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>按时扫码维保率</td>"+
		        		"<td class='form_td'>"+Number(sweepCodeInTimeRatesj-100).toFixed(2)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		 hjsubtracttable =Number(hjsubtracttable)+Number(Number(sweepCodeInTimeRatesj-100).toFixed(2));
			        	}  
		   	
			   if(alarmDealwithsj > 100){
					  var $tr = $("<tr>"+
					      "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>平台报警处理情况</td>"+
					      "<td class='form_td'>"+(alarmDealwithsj-100)+"</td>"+
					      "</tr>");
					 addtable.append($tr);
					 hjaddtable =Number(hjaddtable)+Number(alarmDealwithsj-100);	      		
					        	}
			   if(alarmDealwithsj < 100){
					 var $tr = $("<tr>"+
					      "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>平台报警处理情况</td>"+
					       "<td class='form_td'>"+(alarmDealwithsj-100)+"</td>"+
					        "</tr>");
					 subtracttable.append($tr);
					 hjsubtracttable =Number(hjsubtracttable)+Number(alarmDealwithsj-100);
						        	}  
			 
		    if(regularInspectionsj > 100){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>电梯定期检验情况</td>"+
		        	  "<td class='form_td'>"+(regularInspectionsj-100)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(regularInspectionsj-100);	   		
		        	}
		     if(regularInspectionsj < 100){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>电梯定期检验情况</td>"+
		        		"<td class='form_td'>"+(regularInspectionsj-100)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		 hjsubtracttable =Number(hjsubtracttable)+Number(regularInspectionsj-100);
			        	}

		     if(inspectElevatorsj > 100){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>在用电梯监督抽查情况</td>"+
		        	  "<td class='form_td'>"+(inspectElevatorsj-100)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(inspectElevatorsj-100);	 		
		        	}
		     if(inspectElevatorsj < 100){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>在用电梯监督抽查情况</td>"+
		        		"<td class='form_td'>"+(inspectElevatorsj-100)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		 hjsubtracttable =Number(hjsubtracttable)+Number(inspectElevatorsj-100);
			        	}
		         
	
		     if(acceptInspElevatorsj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>在用电梯监督抽查情况</td>"+
		        	  "<td class='form_td'>"+(acceptInspElevatorsj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(acceptInspElevatorsj-0);			
		        	}
		     if(acceptInspElevatorsj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>在用电梯监督抽查情况</td>"+
		        		"<td class='form_td'>"+(acceptInspElevatorsj-0)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(acceptInspElevatorsj-0);
			        	}

		 	
		     if(maintenSceneInfosj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>维保现场防护情况</td>"+
		        	  "<td class='form_td'>"+(maintenSceneInfosj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(maintenSceneInfosj-0);		
		        	}
		     if(maintenSceneInfosj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>维保现场防护情况</td>"+
		        		"<td class='form_td'>"+(maintenSceneInfosj-0)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(maintenSceneInfosj-0);
			        	}
	        	
		     if(malignantEventssj > 100){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>因维保原因发生安全事故<br>重大社会影响事件</td>"+
		        	  "<td class='form_td'>"+(malignantEventssj-100)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(malignantEventssj-100);			
		        	}
		     if(malignantEventssj < 100){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>因维保原因发生安全事故<br>重大社会影响事件</td>"+
		        		"<td class='form_td'>"+(malignantEventssj-100)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(malignantEventssj-100);
			        	}
		        
		     if(complaintsEventssj > 100){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>因维保质量而引发的投诉<br>（领导信箱、公开电话、网络理政平台）</td>"+
		        	  "<td class='form_td'>"+(complaintsEventssj-100)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(complaintsEventssj-100);					
		        	}
		     if(complaintsEventssj < 100){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>因维保质量而引发的投诉<br>（领导信箱、公开电话、网络理政平台）</td>"+
		        		"<td class='form_td'>"+(complaintsEventssj-100)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(complaintsEventssj-100);
			        	}

		     if(maintenBusinesssj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>维保业务管理</td>"+
		        	  "<td class='form_td'>"+(maintenBusinesssj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(maintenBusinesssj-0);		
		        	}
		      if(maintenBusinesssj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>维保业务管理</td>"+
		        		"<td class='form_td'>"+(maintenBusinesssj-0)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(maintenBusinesssj-0);
			        	}
	        	
		      if(honestsj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>诚实守信情况</td>"+
		        	  "<td class='form_td'>"+(honestsj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(honestsj-0);			
		        	}
		      if(honestsj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>诚实守信情况</td>"+
		        		"<td class='form_td'>"+(honestsj-0)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(honestsj-0);
			        	}

		      if(punishmentsj > 100){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>行政查处及处罚情况</td>"+
		        	  "<td class='form_td'>"+(punishmentsj-100)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(punishmentsj-100);		
		        	}
		     if(punishmentsj < 100){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>行政查处及处罚情况</td>"+
		        		"<td class='form_td'>"+(punishmentsj-100)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(punishmentsj-100);
			        	}

		     if(secondRescuePointsj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>参与二级救援布点</td>"+
		        	  "<td class='form_td'>"+(secondRescuePointsj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(secondRescuePointsj-0);			
		        	}
		      if(secondRescuePointsj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>参与二级救援布点</td>"+
		        		"<td class='form_td'>"+(secondRescuePointsj-0)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(secondRescuePointsj-0);
			        	}
		      
		      if(positiveEnergysj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>献计献策、举报违法违规、<br>行业潜规则</td>"+
		        	  "<td class='form_td'>"+(positiveEnergysj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(positiveEnergysj-0);			
		        	}
		      if(positiveEnergysj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>献计献策、举报违法违规、<br>行业潜规则</td>"+
		        		"<td class='form_td'>"+(positiveEnergysj-0)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(positiveEnergysj-0);
			        	}

		      if(expertsSuggestionsj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>提供专家及技术支持，<br>参与故障及事故调查处理</td>"+
		        	  "<td class='form_td'>"+(expertsSuggestionsj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(expertsSuggestionsj-0);		
		        	}
		      if(expertsSuggestionsj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>提供专家及技术支持，<br>参与故障及事故调查处理</td>"+
		        		"<td class='form_td'>"+(expertsSuggestionsj-0)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(expertsSuggestionsj-0);
			        	}

		      if(positiveWorksj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>积极承接监管部门指定电梯维保</td>"+
		        	  "<td class='form_td'>"+(positiveWorksj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(positiveWorksj-0); 		
		        	}
		      if(positiveWorksj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>积极承接监管部门指定电梯维保</td>"+
		        		"<td class='form_td'>"+(positiveWorksj-0)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(positiveWorksj-0);
			        	}
		      
		      if(remoteMonitorsj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>采用远程监控、机器人保安</td>"+
		        	  "<td class='form_td'>"+(remoteMonitorsj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(remoteMonitorsj-0); 			
		        	}
		      if(remoteMonitorsj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>采用远程监控、机器人保安</td>"+
		        		"<td class='form_td'>"+(remoteMonitorsj-0)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(remoteMonitorsj-0);
			        	}

		      if(elevatorInsurancesj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>购买电梯责任保险</td>"+
		        	  "<td class='form_td'>"+(elevatorInsurancesj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(elevatorInsurancesj-0); 		
		        	}
		      if(elevatorInsurancesj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>购买电梯责任保险</td>"+
		        		"<td class='form_td'>"+(elevatorInsurancesj-0)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(elevatorInsurancesj-0);
			        	}
		     
		      if(techinnovationsj > 0){
		          var $tr = $("<tr>"+
		        	  "<td align='center' width='150' style='heiht:34px;font-size: 20px;'>其他开展技术创新及工作创新</td>"+
		        	  "<td class='form_td'>"+(techinnovationsj-0)+"</td>"+
		        	  "</tr>");
		        addtable.append($tr);
		        hjaddtable =Number(hjaddtable)+Number(techinnovationsj-0); 		
		        	}
		      if(techinnovationsj < 0){
		        	var $tr = $("<tr>"+
		        		"<td align='center' width='150' style='heiht:34px;font-size: 20px;'>其他开展技术创新及工作创新</td>"+
		        		"<td class='form_td'>"+(techinnovationsj-0)+"</td>"+
		        		"</tr>");
		        		subtracttable.append($tr);
		        		hjsubtracttable =Number(hjsubtracttable)+Number(techinnovationsj-0);
			        	}
              
		     
		     $("#hjaddtable").text(Number(hjaddtable).toFixed(2));
		 	 $("#hjsubtracttable").text(Number(hjsubtracttable).toFixed(2));
	        }
		   }, 'json');
	 	
	
	
}
</script>
<style type="text/css">
.form_input {
 /* display: block;*/
  width: 150px; 
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

.form_td {
 /* display: block;*/
  width: 150px; 
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

</style>
</head>
<body class="easyui-layout" style="height:100%;" fit="true" id="contextDivs">
<div region="north" style="overflow:hidden;position:relative; height:60px;">
       <center>
        <table style="margin-bottom:4px;">
        <tr>   
       <td valign="middle">
       <input type="text" id="ratingDate2" name="ratingDate2" onClick="WdatePicker({dateFmt:'yyyy-MM'})" class="Wdate" style="height:40px;width:200px;margin-top:4px;" >&nbsp;&nbsp;&nbsp;&nbsp;
       
    <!--      <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  -->
    <a class="btn btn-large btn-info" href="#"  onclick="query()"><i class="icon-search icon-large pull-left"></i>查询</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <a class="btn btn-large btn-default" href="#"  onclick="clearQuery()"><i class="icon-remove icon-large pull-left"></i>清空</a>
        </td>  
       </tr>
       </table>
       </center>
</div>

<div region="center" >
      <table id="tt" style="margin-top:10px;">  
      </table>
</div>

<div region="south" id="southcontextDiv">
     <div id ="kpqs"></div>
</div>
</body>
</html>