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

	 var w = ($("body").width())* 0.5;

	 $('#kpcontext').layout('panel','west').panel('resize',{width:w});
	 $('#kpcontext').layout('resize');
	 
	 

});

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
    var ywCompanyId =<%=companyId%>; 
    if(ywCompanyId == "")
    	ywCompanyId = 0;
   
   
    var ratingDate = $('#ratingDate2').attr("value"); 
    jQuery.post('/tcweb/cs/ywkp',{'ywcompanyId':ywCompanyId,'ratingDate':ratingDate}, function(data){ 
        data = eval(data);//POST方法必加，ajax方法自动处理了                                             
    //    $('#ratingDate').attr("value",data.ratingDate);  
    //    $('#tScore').attr("value",data.tScore);
    //    $('#tSort').attr("value",data.tSort);
          
          $("#ratingDate").text(data.ratingDate);
          $("#tScore").text(data.tScore);
          $("#tSort").text(data.tSort); 
          $("#jcf").text(1500); 

        kpDetail(ywCompanyId,ratingDate);
	   }, 'json');
    
   
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
<body class="easyui-layout" style="height:100%;" fit="true">
<div region="north" style="overflow:hidden;position:relative; height:60px;">
       <center>
        <table style="margin-bottom:4px;">
        <tr>   
       <td valign="middle">
       <input type="text" id="ratingDate2" name="ratingDate2" onclick="WdatePicker({dateFmt:'yyyy-MM'})" class="Wdate" style="height:40px;width:200px;margin-top:4px;" >
       
    <!--      <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  -->
    <a class="btn btn-large btn-info" href="#"  onclick="query()"><i class="icon-search icon-large pull-left"></i>查询</a>
        </td>  
       </tr>
       </table>
       </center>
</div>

<div region="center" >
   <div class="easyui-layout" fit="true">
   <div region="north" style="height:60px;background:#ebf9ff;">
   <table style="width:100%;margin-top:10px" >
    <tr>      
       <td nowrap align="center" style="heiht:34px;font-size: 20px;">时间</td>      
       <td>
   <!--    <input class="form_input" id="ratingDate" name="ratingDate"></input>  --> 
       <span id="ratingDate" style="heiht:34px;font-size: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
       </td>
       <td nowrap align="center" style="heiht:34px;font-size: 20px;">排名</td>      
       <td>
     <!--   <input class="form_input" id="tSort" name="tSort"></input> --> 
      <span id="tSort" style="heiht:34px;font-size: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
       </td>
       <td nowrap align="center" style="heiht:34px;font-size: 20px;">基础分</td>
       <td><span id="jcf" style="heiht:34px;font-size: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</span></td> 
          
       <td  nowrap align="center" style="heiht:34px;font-size: 20px;">加减合计分</td>      
       <td>
    <!--   <input class="form_input" id="tScore" name="tScore"></input> --> 
    <span id="tScore" style="heiht:34px;font-size: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
       </td>
      </tr>
   </table>
   </div>


<!--  <div id="kpdWin" style="position:absolute; left:10px; top:60px;font-size:18px;color:#F5F5F5;background: #F5F5F5;width:100%;">
  <form id="myform" name="myform"> 
  <table id="kpdtb" border ="1" style="border-width: 1px; border-style: ridge; border-collapse:collapse;background: #F5F5F5;margin:auto"  width="60%"  cellspacing="0" cellpadding="1">
    <tr id="officeSpacetr">   
            <td align="center" >办公面积</td>  
            <td align="left" nowrap><input class="form_input" id="officeSpace"  type="text"  name="officeSpace" ></input>㎡ </td>
           <td align="center"><input class="form_input" id="officeSpacesj"  type="text"  name="officeSpacesj"></input></td>
        </tr>
     <tr id="fixedTelOnDutytr">         
           <td align="center">值班固定电话</td>  
           <td align="left"><input class="form_input" id="fixedTelOnDuty"  type="text"  name="fixedTelOnDuty" ></input></td>
           <td align="center"><input class="form_input" id="fixedTelOnDutysj"  type="text"  name="fixedTelOnDutysj"></input></td>
        </tr>
        
     <tr id="telOnDutyunattendedtr">  
            <td align="center">电话值守<br>无人接听</td>  
           <td align="left"><input class="form_input" id="telOnDutyunattendedTimes" name="telOnDutyunattendedTimes"></input>次</td>
           <td align="center"><input class="form_input" id="telOnDutyunattendedsj"  type="text"  name="telOnDutyunattendedsj"></input></td>
        </tr>
        
     <tr id="enterpriseChangetr">      
            <td align="center">维保企业变更</td>  
           <td align="left"><input class="form_input" id="enterpriseChangeTimes" name="enterpriseChangeTimes"></input>宗</td>
           <td align="center"><input class="form_input" id="enterpriseChangesj"  type="text"  name="enterpriseChangesj"></input></td>
        </tr> 
        
     <tr id="enterpriseRecordtr">      
            <td align="center">外地企业备案</td>  
            <td align="left"><input class="form_input" id="enterpriseRecord"  name="enterpriseRecord" class="easyui-combobox"></input></td>
           <td align="center"><input class="form_input" id="enterpriseRecordsj"  type="text"  name="enterpriseRecordsj"></input></td>
        </tr>
        
        
   <tr id="regularInspectiontr">     
            <td align="center">电梯定期检验情况</td>  
            <td align="left"><input class="form_input" id="regularInspectionTimes" name="regularInspectionTimes"></input>台</td>
          <td align="center"><input  class="form_input" id="regularInspectionsj"  type="text"  name="regularInspectionsj"></input></td>
           
        </tr>
    <tr id="inspectElevatortr">    
            <td align="center">在用电梯监督抽查情况</td>  
             <td align="left">
            <input  class="form_input" id="inspectElevatorTimes" name="inspectElevatorTimes" ></input>严重隐患&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input  class="form_input" id="inspectElevatorTimes2" name="inspectElevatorTimes2" ></input>回路短接</td>
              <td align="center"><input class="form_input" id="inspectElevatorsj"  type="text"  name="inspectElevatorsj"></input></td>
        </tr>
          <tr id="acceptInspElevatortr">    
            <td align="center">接受监督检查情况</td>  
            <td align="left"><input class="form_input" id="acceptInspElevatorTimes" name="acceptInspElevatorTimes" ></input>次</td>
           <td align="center"><input class="form_input" id="acceptInspElevatorsj"  type="text"  name="acceptInspElevatorsj"></input></td>
        </tr> 
          <tr id="maintenSceneInfotr">    
            <td align="center">维保现场防护情况</td>  
           <td align="left"><input class="form_input" id="maintenSceneInfoTimes" name="maintenSceneInfoTimes"></input>次</td>
          
           <td align="center"><input class="form_input" id="maintenSceneInfosj"  type="text"  name="maintenSceneInfosj"></input></td>
        </tr>
         <tr id="malignantEventstr">    
            <td align="center">因维保原因发生安全事故<br>重大社会影响事件</td>  
            <td align="left">
            <input class="form_input" id="malignantEventsTimes" name="malignantEventsTimes" ></input>一般事故&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input class="form_input" id="malignantEventsTimes2" name="malignantEventsTimes2" ></input>较大事故&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input class="form_input" id="malignantEventsTimes3" name="malignantEventsTimes3" ></input>重大事故</td>
           
            <td align="center"><input class="form_input" id="malignantEventssj"  type="text"  name="malignantEventssj" ></input></td>
        </tr>  
         <tr id="complaintsEventstr">    
            <td align="center">因维保质量而引发的投诉<br>（领导信箱、公开电话、网络理政平台）</td>  
            <td align="left">
            <input class="form_input" id="complaintsEventsTimes" name="complaintsEventsTimes" ></input>一般投诉&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input class="form_input" id="complaintsEventsTimes2" name="complaintsEventsTimes2" ></input>连续投诉</td>
           
            <td align="center"><input class="form_input" id="complaintsEventssj"  type="text"  name="complaintsEventssj"></input></td>
        </tr>
         <tr id="maintenBusinesstr">    
            <td align="center">维保业务管理</td>  
           <td align="left"><input class="form_input" id="maintenBusinessTimes" name="maintenBusinessTimes" ></input>宗</td>
           
           <td align="center"><input class="form_input" id="maintenBusinesssj"  type="text"  name="maintenBusinesssj" ></input></td>
        </tr>
        <tr id="honesttr">    
            <td align="center" >诚实守信情况</td>  
            <td align="left"><input class="form_input" id="honestTimes" name="honestTimes" ></input>宗</td>
          
            <td align="center"><input class="form_input" id="honestsj"  type="text"  name="honestsj" ></input></td>
        </tr>
        <tr id="punishmenttr">    
            <td align="center">行政查处及处罚情况</td>  
           <td align="left"><br>
            <input class="form_input" id="punishmentTimes"  name="punishmentTimes"  ></input>维保管理及维保质量被整改&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input class="form_input" id="punishmentTimes2" name="punishmentTimes2" ></input>被下达监察意见过通报&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input class="form_input" id="punishmentTimes3" name="punishmentTimes3" ></input>连续被下达监察指令或通报2次及以上&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input class="form_input" id="punishmentTimes4" name="punishmentTimes4" ></input>违反法律、法规及技术规范<br><br></td>
          
            <td align="center"><input class="form_input" id="punishmentsj"  type="text"  name="punishmentsj" ></input></td>
        </tr>
         <tr id="secondRescuePointtr">   
            <td align="center" >参与二级救援布点</td>  
           <td><input class="form_input" id="secondRescuePoint"  type="text"  name="secondRescuePoint" ></input>个</td>
           
             <td align="center"><input class="form_input" id="secondRescuePointsj"  type="text"  name="secondRescuePointsj" ></input></td>
        </tr> 
         <tr id="positiveEnergytr">   
            <td align="center" >献计献策、举报违法违规、<br>行业潜规则</td>  
             <td align="left"><input class="form_input" id="positiveEnergyTimes" name="positiveEnergyTimes" ></input>宗</td>
           
            <td align="center"><input class="form_input" id="positiveEnergysj"  type="text"  name="positiveEnergysj" ></input></td>
            
        </tr>
         <tr id="expertsSuggestiontr">   
            <td align="center" >提供专家及技术支持，<br>参与故障及事故调查处理</td>  
            <td><input class="form_input" id="expertsSuggestionTimes" name="expertsSuggestionTimes" ></input>宗</td>
            
            <td align="center"><input class="form_input" id="expertsSuggestionsj"  type="text"  name="expertsSuggestionsj" ></input></td>
        </tr>
         <tr id="positiveWorktr">   
            <td align="center" >积极承接监管部门指定电梯维保</td>  
           <td><input class="form_input" id="positiveWork"  type="text"  name="positiveWork" ></input>台</td>
           
           <td align="center"><input class="form_input" id="positiveWorksj"  type="text"  name="positiveWorksj" ></input></td>
        </tr> 
        <tr id="remoteMonitortr">   
            <td align="center" >采用远程监控、机器人保安</td>  
           <td><input class="form_input" id="remoteMonitor"  type="text"  name="remoteMonitor" ></input>台</td>
          
           <td align="center"><input class="form_input" id="remoteMonitorsj"  type="text"  name="remoteMonitorsj" ></input></td>
        </tr>
         <tr id="elevatorInsurancetr">   
            <td align="center">购买电梯责任保险</td>  
           <td><input class="form_input" id="elevatorInsurance"  type="text"  name="elevatorInsurance" ></input>台</td>
           
            <td align="center"><input class="form_input" id="elevatorInsurancesj"  type="text"  name="elevatorInsurancesj" ></input></td>
        </tr>
        <tr id="techinnovationtr">   
            <td align="center">其他开展技术创新及工作创新</td>  
           <td><input class="form_input" id="techinnovationTimes" name="techinnovationTimes" ></input>1分&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input class="form_input" id="techinnovationTimes2" name="techinnovationTimes2" ></input>2分&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input class="form_input" id="techinnovationTimes3" name="techinnovationTimes3" ></input>3分&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input class="form_input" id="techinnovationTimes4" name="techinnovationTimes4" ></input>4分&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input class="form_input" id="techinnovationTimes5" name="techinnovationTimes5" ></input>5分</td>
           
           <td align="center"><input class="form_input" id="techinnovationsj"  type="text"  name="techinnovationsj" ></input></td>
        </tr>
  </table>
  </form>
  </div>    -->
 
<div region="center">
<div class="easyui-layout"  data-options="fit:true"   id="kpcontext"> 

     <div  id="kpdWin" region="west" title="加分项" collapsible="false"> 
       <div class="easyui-layout"  data-options="fit:true" >
         <div region="center">
          <table id="addtable" style="margin-top:10px;">  
         </table>
         </div>
         
         <div region="south" style="height:200px;">
           <table style="margin-top:10px;">  
           <tr>
           <td align='center' width='150' style='heiht:34px;font-size: 20px;'>共计</td>
           <td class='form_td'><span id="hjaddtable"></span></td>
           </tr>
         </table>
         </div>
        </div>
     </div>
  

    <div id="kpdWin2" region="center" title="减分项" collapsible="false"> 
      <div class="easyui-layout"  data-options="fit:true" >
       <div region="center"> 
        <table id="subtracttable" style="margin-top:10px;">  
       </table>
       </div>
       
       <div region="south" style="height:200px;">
           <table style="margin-top:10px;">  
           <tr>
           <td align='center' width='150' style='heiht:34px;font-size: 20px;'>共计</td>
           <td class='form_td'><span id="hjsubtracttable"></span></td>
           </tr>
         </table>
         </div>
       
       
    </div>
 </div>
</div>
</div>

</div>
</div>
</body>
</html>