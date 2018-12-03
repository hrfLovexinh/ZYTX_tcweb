<%@ page import="com.zytx.models.UserInfo" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>维保排位</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">
 <style type="text/css">

        .subtotal { font-weight: bold; }/*合计单元格样式*/
      
  </style>
  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/My97DatePicker/WdatePicker.js"></script>

<!-- <script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script> -->
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

SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
String tjtime =df.format(new Date());// new Date()为获取当前系统时间
%>
<script type="text/javascript">
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

	 function strDateTime(str)
	 {
//	 var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
//   var r = str.match(/^(((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\d):[0-5]?\d:[0-5]?\d)$/);
	 var r = str.match(/^[1-9][0-9]{3}-(0?[1-9]|1[0|1|2])-(0?[1-9]|[1|2][0-9]|3[0|1])\s(0?[1-9]|1[0-9]|2[0-3]):(0?[0-9]|[1|2|3|4|5][0-9])$/);
	 if(r==null)
	 return false; 
	/* var d= new Date(r[1], r[3]-1, r[4]); 
	 return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);*/
	 return true;
	 }



var opt =0; //0:增加 ；1：编辑
$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	 $("#ratingDate2").val(myformatter(new Date(), "yyyy-MM"));  //设置查询条件的默认时间
	var url = encodeURI(encodeURI('/tcweb/elevator/getAutoYwCompanyList')); 
	 $("#ywCompanyIdinfo").autocomplete(  
	            url,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2, 
	                max:20, 
	                scrollHeight: 100,
	                extraParams: {	q: function() {
					return $("#this").val();
				    }},   
	                dataType: "json",  
	                mustMatch:false,  
	                parse: function(data) {  
	                    var rows = [];  
	                    for(var i=0; i<data.length; i++){  
	                     rows[rows.length] = {   
	                       data:data[i].id +"-"+data[i].companyName,   
	                       value:data[i].id,   
	                       result:data[i].companyName   
	                       };   
	                     }  
	                  return rows;  
	                    },  
	                formatItem: function(row, i, n) {  
	                    return row;  
	                },
	                formatResult: function(row){return row.id; }    
	            }  
	        ).result(function(event, data, formatted) {
	        	
	            if(data)
	            {
	        	 $('#ywCompanyIdinfo2').attr("value",formatted);
	            }
	            else{
	             $('#ywCompanyIdinfo2').attr("value",'');
	 	            }
	        });
	
	 $('input:radio[name=chkItem]')[0].checked = true;

	 $('input:radio[name=chkItem]').change( function(){
       
		var v = $('input:radio[name=chkItem]:checked').val();
		 var ratingDate =$('#ratingDate2').attr("value");
		 grid.datagrid("options").url='/tcweb/yw/newywranking2';
	     grid.datagrid("options").queryParams={'sv':v,'ratingDate':ratingDate};   
	//    $('#ywrankingtt').datagrid('reload');
	     $('#ywrankingtt').datagrid('load');
		 });
	
	grid=$('#ywrankingtt').datagrid({
	    title:'维保单位综合排位',
	    fit: true,
	    striped:true,
	//    pageSize:40,
	    pageList:[15,20,25,30,35,40],
	    url:'/tcweb/yw/newywranking',
	    queryParams:{},
	    columns:[[{title:"统计时间:<input  id='tjtime' style='border:0' readOnly/>","colspan":50}],
	     	    [
            {field:'ywCompanyName',title:'维保单位',width:150,align:'left',halign:'center',formatter: function(value,rec,index) {
               if('未知' == value)
                   return '无维保单位';
               else
                   return value;
                }},
            {field:'officeSpacesj',title:'办公面积',width:60,align:'center'},
        //    {field:'headQuarterssj',title:'总部<br>所在地',width:60,align:'center'},
            {field:'maintenanceEleCount',title:'维保电梯<br>数量',width:60,align:'center'},
            {field:'maintenanceEleCountsj',title:'维保电梯<br>数量得分',width:60,align:'center'},
	        {field:'avgmaintenanceEleCountsj',title:'人均维保<br>数量得分',width:60,align:'center'},
	        {field:'fixedTelOnDutysj',title:'值班<br>固定电话',width:60,align:'center'},
	        {field:'telOnDutyunattendedsj',title:'电话值守',width:60,align:'center'},
	        {field:'enterpriseChangesj',title:'企业变更',width:60,align:'center'},
	        {field:'enterpriseRecordsj',title:'企业备案',width:60,align:'center'},
	        {field:'infoComRatesj',title:'信息<br>完整率',width:60,rowspan:3,align:'center'},
	        {field:'sweepCodeRatesj',title:'扫码<br>维保率',width:60,rowspan:3,align:'center'},
	        {field:'sweepCodeInTimeRatesj',title:'按时扫码<br>维保率',width:80,rowspan:3,align:'center'},
	        {field:'alarmDealwithsj',title:'平台报警<br>处理情况',width:80,rowspan:3,align:'center'},
	        {field:'regularInspectionsj',title:'电梯定期<br>检验情况',width:80,rowspan:3,align:'center'},
	        {field:'inspectElevatorsj',title:'在用电梯<br>监督抽查<br>情况',width:80,rowspan:3,align:'center'},
	        {field:'acceptInspElevatorsj',title:'接受监督<br>检查情况',width:80,rowspan:3,align:'center'},
	        {field:'maintenSceneInfosj',title:'维保现场<br>防护情况',width:80,rowspan:3,align:'center'},
	        {field:'malignantEventssj',title:'因维保原因发生安全事故<br>重大社会影响事件',width:140,rowspan:4,align:'center'},
	        {field:'complaintsEventssj',title:'因维保质量引发的投诉<br>（领导信箱、公开电话<br>网络理政平台）',width:140,rowspan:5,align:'center'},
	        {field:'maintenBusinesssj',title:'维保<br>业务管理',width:60,rowspan:3,align:'center'},
	        {field:'honestsj',title:'诚实守信',width:60,rowspan:3,align:'center'},
	        {field:'punishmentsj',title:'行政查处<br>及处罚',width:60,rowspan:3,align:'center'},
	        {field:'firstRescuesj',title:'一级救援',width:60,rowspan:3,align:'center'},
	        {field:'secondRescuesj',title:'二级救援',width:60,rowspan:2,align:'center'},
	        {field:'secondRescuePointsj',title:'参与二级<br>救援布点',width:80,rowspan:2,align:'center'},
	        {field:'rescueResponsesj',title:'应急救援<br>响应',width:60,rowspan:2,align:'center'},
	        {field:'tiringPeoplesj',title:'因维保质量<br>引发困人故障',width:140,rowspan:2,align:'center'},
	        {field:'positiveEnergysj',title:'献计献策<br>举报违法违规<br>行业潜规则',width:120,rowspan:3,align:'center'},
	        {field:'expertsSuggestionsj',title:'提供专家<br>及技术支持，<br>参与故障及事故<br>调查处理',width:120,rowspan:3,align:'center'},
	        {field:'positiveWorksj',title:'积极承接<br>监管部门指定<br>电梯维保',width:120,rowspan:3,align:'center'},
	        {field:'remoteMonitorsj',title:'采用远程监控<br>机器人保安',width:120,rowspan:3,align:'center'},
	        {field:'elevatorInsurancesj',title:'购买电梯<br>责任保险',width:60,rowspan:3,align:'center'},
	        {field:'techinnovationsj',title:'其他开展<br>技术创新<br>及工作创新',width:120,rowspan:3,align:'center'},
	        {field:'tScore',title:'综合得分',width:80,align:'center',formatter: function(value,rec,index) {
	               if(value == -1)
	                   return '/';
	               else
		               return value;
	                }},
	        <%if(role == 10 || role ==11){%>
	        {field:'tSort',title:'区县排位',width:80,align:'center'}	
	        <% } else {%>
	        {field:'tSort',title:'全市排位',width:80,align:'center'}	
	        <% }%>       
	    ]], 
	    
	        fitColumns:false,  //设置为true，列将根据内容来定列宽，列的属性width不起作用
		    pagination:false,
		    singleSelect:true,
		    striped:true,
		    nowrap:true,
		    onLoadSuccess:function() {
	    	jQuery.post('/tcweb/yw/ywRankingtjtime',
			    	 function(data){
			    		 $('#tjtime').attr("value",data.tjtime);
			    		},'json'
		    	        );
            //添加“合计”列
            $('#ywrankingtt').datagrid('appendRow', {
            	ywCompanyName:'<span class="subtotal">'+'合计'+ '</span>', 
            	maintenanceEleCount: '<span class="subtotal">'+ compute("maintenanceEleCount") + '</span>'
            });
        }	    
	});	
//	$('#systongjitt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  
/*	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});	 */

 //	showLeftTime();	  
}
);

/*
function ywcompanySelectRefresh(){  
	$('#ywCompanyIdinfo').combobox('clear');  
	$('#ywCompanyIdinfo').combobox('reload','/tcweb/elevator/getYwQuaRateCompanyList?ratingDate='+$('#ratingDate2').attr("value"));
	
}
*/
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

//指定列求和
function compute(colName) {  
     var rows = $('#ywrankingtt').datagrid('getRows');
     var total = 0;
     var total2 = 0;
     if(colName =="uncqetotal"){
    	 for (var i = 0; i < rows.length; i++) {
             total += parseFloat(rows[i]["etotal"]);
             total2 += parseFloat(rows[i]["eywtotal"]);
            }
          total =  total-total2;  
         }
     else{  
     for (var i = 0; i < rows.length; i++) {
         total += parseFloat(rows[i][colName]);
        }
     }
     return total;
     }

function clearQuery(){
	$('#ywCompanyIdinfo').attr("value","");
	$('#ywCompanyIdinfo2').attr("value","");
}

function query(){  
	 var ywCompanyId=$('#ywCompanyIdinfo2').attr("value"); 
	 var ratingDate =$('#ratingDate2').attr("value");
	 if (!ywCompanyId){  
	    	ywCompanyId =0;
	    	}
 	 if(ywCompanyId == 0){
 		var v = $('input:radio[name=chkItem]:checked').val();
		 grid.datagrid("options").url='/tcweb/yw/newywranking2';
	     grid.datagrid("options").queryParams={'sv':v,'ratingDate':ratingDate};   
 	 	 }
 	 else{
     grid.datagrid("options").url='/tcweb/yw/newywrankingByCompanyName';
     grid.datagrid("options").queryParams={'ywCompanyId':ywCompanyId,'ratingDate':ratingDate};  
 	 } 
    $('#ywrankingtt').datagrid('reload');
	}


/**  
 * 计算传入时间和当前时间差  
 * @param d 时间 格式：2010-04-10 10:22:36  
 * @return  
 */  
function getDateDiff(d){      
    var now = new Date().getTime();   
    var diffValue = now - Date.parse(d.replace(/-/g,'/').replace(/：/g,":"));  
    if(diffValue < 0){        
        return "";       
    }      
    var minute = 1000 * 60;     
    var hour = minute * 60;     
    var day = hour * 24;    
    var halfamonth = day * 15;    
    var month = day * 30;    
    var monthC =diffValue/month;      
    var weekC =diffValue/(7*day);      
    var dayC =diffValue/day;      
    var hourC =diffValue/hour;     
    var minC =diffValue/minute;  
    /*        
    if(monthC>=1){       
       result=parseInt(monthC) + "个月前";       
    }else if(weekC>=1){   
       result=parseInt(weekC) + "个星期前";       
   }else if(dayC>=1){       
        result= parseInt(dayC) +"天前";       
   }else if(hourC>=1){       
      result= parseInt(hourC) +"个小时前";      
   }else if(minC>=1){       
      result= parseInt(minC) +"分钟前";       
   }else{   
       result="";       
    }  
    */
      
    return parseInt(dayC-15);  
  /*  if(dayC>15)
        return true;
    else if(dayC ==15){
        if(minC>=1)
            return true;
        else 
            return false;
        }
    else
        return false;
    */    
        
}   


</script>
<style type="text/css">
td{
font-size:12px;
	overflow:hidden;
	padding:0;
	margin:0;
	}
</style>
</head>
<body class="easyui-layout" data-options="fit:true">
 <div region="north"  border="0" style="height:60px;;background-color:rgb(201,220,245);">
 
   <table border="0"> 
   <tr>
   <td nowrap>时间：</td>
        <td>
      <input type="text" id="ratingDate2" name="ratingDate2" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="Wdate" >
       单位名称：
  <input id="ywCompanyIdinfo" style="background-color:#87CEEB;width:152px;"></input>
  <input type ="hidden" id="ywCompanyIdinfo2"></input></td>
	 <td colspan="2">		
	 <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
	 <a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 					
   </td>				
   </tr>
   <tr>
    <td nowrap>维保数：</td>  
     <td nowrap colspan="2"><input name="chkItem" type="radio" value="0" />所有
     <input name="chkItem" type="radio" value="1" />大型（大于500）
     <input name="chkItem" type="radio" value="2" />中型
     <input name="chkItem" type="radio" value="3" />小型（小于100）</td>   
   </tr>
     </table>


</div> 
   <div region="center">
       <div id="main-center" border="false">   
        
          <div style="width:100%;height:800px;">  
       <table id="ywrankingtt"></table>
   </div>  
        
    </div>  
</div> 
</body>
</html>