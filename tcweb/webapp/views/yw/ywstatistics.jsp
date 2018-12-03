<%@ page import="com.zytx.models.UserInfo,com.zytx.init.GlobalFunction" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>维保超期管理</title>
<!--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"> -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/myeasyuiicon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">
 <style type="text/css">

        .subtotal { font-weight: bold; }/*合计单元格样式*/
  </style>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>

<!-- <script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script> -->
<% 

String cityName = GlobalFunction.cityName;
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

	  $('#areainfo').combobox({   
	        url:'/tcweb/elevator/areaInfoList',   
	        valueField:'area',   
	        textField:'area'  
	    }); 

	win = $('#ncqetotal-window').window({  closed:true,draggable:false,modal:true }); 
	uwin = $('#uncqetotal-window').window({  closed:true,draggable:false,modal:true });
	nuwin = $('#nutotal-window').window({  closed:true,draggable:false,modal:true });
	uywnnuwin = $('#uywncqetotal-window').window({  closed:true,draggable:false,modal:true });
	
	grid=$('#ywstatisticstt').datagrid({
	    title:'运维统计列表',
	    fitColumns:true,
 	    striped:true,
	    pageSize:15,
	    pageList:[15,20,25,30,35,40],
	    url:'/tcweb/yw/ywstatisticslist',
	    queryParams:{},
	    columns:[[
	        {field:'companyName',align:'left',halign:'center',title:'维保单位',width:$(this).width() * 0.11},
	        {field:'etotal',align:'center',title:'电梯总数',width:$(this).width() * 0.11},
	        {field:'eywtotal',align:'center',title:'运维电梯数',width:$(this).width() * 0.11}, 
	        {field:'dailingetotal',align:'center',title:'停用电梯数',width:$(this).width() * 0.11},   
	        {field:'uncqetotal',align:'center',title:'未运维电梯数',width:$(this).width() * 0.11,formatter: function(value,rec,index) {
		        if(value == -1){
	            var ywcompanyId = rec.ywcompanyId;
	            var etotal = rec.etotal;
	            var eywtotal =rec.eywtotal;
	            var dailingetotal = rec.dailingetotal;
	            var uncqetotal = etotal - eywtotal - dailingetotal;
                   if (uncqetotal > 0){
                 //      return  uncqetotal+" "+"<img src='<%=request.getContextPath()%>/images/uncqetotal.png' alt='未运维总数'  style='cursor:hand;' onclick='uncqetotal("+ywcompanyId+")'/>";
                	   return  uncqetotal+'</a>&nbsp;&nbsp;&nbsp;&nbsp;'+ '<a href="#" onclick="uncqetotal('+ywcompanyId+')"><i class="fa fa-inbox" aria-hidden="true" style="color:#61B5CF;"></i></a>';
                       
                       }
                   else{
                       return uncqetotal;
                       }
		         }
		        else{
                    return value;
			        }
		         }},
	        {field:'ncqetotal',align:'center',title:'当前超期数',width:$(this).width() * 0.11,formatter: function(value,rec,index) {
		            var ywcompanyId = rec.ywcompanyId;
                   if (value > 0){
               //        return  value+" "+"<img src='<%=request.getContextPath()%>/images/ncqetotal.png' alt='当前超期'  style='cursor:hand;' onclick='ncqetotal("+ywcompanyId+")'/>";
                	   return  value+'</a>&nbsp;&nbsp;&nbsp;&nbsp;'+ '<a href="#" onclick="ncqetotal('+ywcompanyId+')"><i class="fa fa-exclamation-triangle" aria-hidden="true" style="color:#61B5CF;"></i></a>';
                       
                        }
                   else{
                       return value;
                       }
		         }},
		         <%if(role !=10 && role != 11){ %>
	        {field:'utotal',align:'center',title:'总维保人员数',width:$(this).width() * 0.11},
	        <% }%>
	        {field:'nutotal',align:'center',title:'在维保人员数',width:$(this).width() * 0.11,formatter: function(value,rec,index) {
	            var ywcompanyId = rec.ywcompanyId;
                if (value > 0){
             //       return  value+" "+"<img src='<%=request.getContextPath()%>/images/nutotal.png' alt='当前运维人员'  style='cursor:hand;' onclick='nutotal("+ywcompanyId+")'/>";
                	 return  value+'</a>&nbsp;&nbsp;&nbsp;&nbsp;'+ '<a href="#" onclick="nutotal('+ywcompanyId+')"><i class="fa fa-male" aria-hidden="true" style="color:#61B5CF;"></i></a>';
                     
                     }
                else{
                    return value;
                    }
		         }},
		     {field:'ywcountbyperson',align:'center',title:'人均维保数',width:$(this).width() * 0.11,formatter: function(value,rec,index) { 
				            var eywtotal =rec.eywtotal;
				            var nutotal =rec.nutotal;
				            if(eywtotal > 0 && nutotal > 0)
				                var ywcountbyperson = Number(eywtotal/nutotal).toFixed(2);
				            else
				            	var ywcountbyperson ="";  
			                 return ywcountbyperson;
			              
					         }},
	       
	    ]],
	    toolbar:[
	   	     	    {
	   	        text:'导出excel',
	   	        iconCls:'icon-excel',
	   	        handler:function(){
	   	     	    ExporterExcel();
	   	        }
	   	    }], 
	//  rowStyler:function(index,row){ {return 'color:#ff0000;';}} , 
	//  pagination:true,
	    singleSelect:true,
	    onLoadSuccess:function() { 
            //添加“合计”列
            $('#ywstatisticstt').datagrid('appendRow', {
            	companyName:'<span class="subtotal">'+'合计'+ '</span>',
            	etotal: '<span class="subtotal">'+ compute("etotal") + '</span>',
            	eywtotal: '<span class="subtotal">'+ compute("eywtotal") + '</span>',
            	dailingetotal: '<span class="subtotal">'+ compute("dailingetotal") + '</span>',
            	uncqetotal: '<span class="subtotal">'+ compute("uncqetotal") + '</span>',
            	ncqetotal: '<span class="subtotal">'+ compute("ncqetotal") + '</span>',
            	utotal: '',
            	nutotal:''
            });
        }	      
	});	
	$('#ywstatisticstt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  
 	  	  
}
);

//指定列求和
function compute(colName) {  
     var rows = $('#ywstatisticstt').datagrid('getRows');
     var total = 0;
     var total2 = 0;
     var total3 =0;
     if(colName =="uncqetotal"){
    	 for (var i = 0; i < rows.length; i++) {
             total += parseFloat(rows[i]["etotal"]);
             total2 += parseFloat(rows[i]["eywtotal"]);
             total3 += parseFloat(rows[i]["dailingetotal"]);
            }   
          total =  total-total2-total3;  
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
	$('#areainfo').combobox('clear');
	$("#qstartTime").datebox("setValue","");  
	$("#qendTime").datebox("setValue","");  
}

//导出查询条件
var dywCompanyId = 0;
var darea ="";
var dqstartTime="";
var dqendTime="";


function query(){  
	
    var ywCompanyId=$('#ywCompanyIdinfo2').attr("value"); 
    if (!ywCompanyId){
    	ywCompanyId =0;
    	} 
  
    <%if(role ==10 || role == 11){ %>
    var area ="";
    <% }else {%>
    var area =$('#areainfo').combobox('getValue');
    zjqueryByarea =area;
    <% } %>
    var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue");
    //
	 dywCompanyId =ywCompanyId;
	 darea =area;
	 dqstartTime =qstartTime; 
	 dqendTime=qendTime;
	 
     grid.datagrid("options").url='/tcweb/yw/ywstatisticsquery';
     grid.datagrid("options").queryParams={'companyId':ywCompanyId,'area':area,'qstartTime':qstartTime,'qendTime':qendTime};
     
    $('#ywstatisticstt').datagrid('reload');
	}

function ncqetotalclearQuery(){
	$('#registNumber').attr("value","");
	$('#qaddress').attr("value","");
	$('#buildingName').attr("value","");
}

function ncqetotalquery(){  
	var registNumber=$('#registNumber').attr("value");
	var address=$('#qaddress').attr("value");
	var buildingName=$('#buildingName').attr("value");
	var ywCompanyId=clickYwcompanyId;  
	var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue");                           
	 $('#tncqetotal').datagrid("options").url='/tcweb/yw/ncqetotalquery';
	 $('#tncqetotal').datagrid("options").queryParams={'registNumber':registNumber,'address':address,'buildingName':buildingName,'ywcompanyId':ywCompanyId,'qstartTime':qstartTime,'qendTime':qendTime};
	 
	 $('#tncqetotal').datagrid('reload');
}



function uncqetotalclearQuery(){
	$('#uregistNumber').attr("value","");
	$('#uqaddress').attr("value","");
	$('#ubuildingName').attr("value","");
}

function uncqetotalquery(){  
	var registNumber=$('#uregistNumber').attr("value");
	var address=$('#uqaddress').attr("value");
	var buildingName=$('#ubuildingName').attr("value");
	var ywCompanyId=clickYwcompanyId;  
	var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue");                            
	 $('#tuncqetotal').datagrid("options").url='/tcweb/yw/uncqetotalquery';
	 $('#tuncqetotal').datagrid("options").queryParams={'registNumber':registNumber,'address':address,'buildingName':buildingName,'ywcompanyId':ywCompanyId,'qstartTime':qstartTime,'qendTime':qendTime,'area':zjqueryByarea};
	 
	 $('#tuncqetotal').datagrid('reload');
}


function nutotalclearQuery(){
	$('#loginName').attr("value","");
	
}

function nutotalquery(){ 
	var loginName=$('#loginName').attr("value"); 
	var ywCompanyId=clickYwcompanyId;
	 $('#tnutotal').datagrid("options").url='/tcweb/yw/nutotalquery'; 
	 $('#tnutotal').datagrid("options").queryParams={'loginName':loginName,'ywcompanyId':ywCompanyId};
	 $('#tnutotal').datagrid('reload');
	
}

function uywncqetotalclearQuery(){
	$('#uywregistNumber').attr("value","");
	$('#uywqaddress').attr("value","");
	$('#uywbuildingName').attr("value","");
}

function uywncqetotalquery(){  
	var registNumber=$('#uywregistNumber').attr("value");
	var address=$('#uywqaddress').attr("value");
	var buildingName=$('#uywbuildingName').attr("value");
	var ywCompanyId=clickYwcompanyId; 
	var userId =clickYwUserId;                        
	 $('#tuywncqetotal').datagrid("options").url='/tcweb/yw/uywncqetotalquery';
	 $('#tuywncqetotal').datagrid("options").queryParams={'registNumber':registNumber,'address':address,'buildingName':buildingName,'ywcompanyId':ywCompanyId,'userId':userId};
	 
	 $('#tuywncqetotal').datagrid('reload');
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


var clickYwcompanyId =0;
var zjqueryByarea = "";
function ncqetotal(ywcompanyId){
	clickYwcompanyId =ywcompanyId;
	var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue");
	//alert(ywcompanyId);
	win.window('open'); 
//	$('#registNumberMap').html(registNumber);
     gridncqetotal=$('#tncqetotal').datagrid({
	    title:'',
	    pageSize:10,
	    pageList:[10,20,30,40],
	    url:'/tcweb/yw/ncqetotallist',
	    queryParams:{'ywcompanyId':ywcompanyId,'qstartTime':qstartTime,'qendTime':qendTime,'area':zjqueryByarea},
	    columns:[[
	        {field:'registNumber',title:'电梯编号',width:100,formatter: function(value,rec,index) {
	        	<% if("1".equals(cityName)){ %>
	        	 return "N"+value;
	        	 <% } else {%>
                return value;
                <% }%>
	            }},
	        {field:'address',title:'地址',width:200},
	        {field:'buildingName',title:'楼盘',width:200},
	        {field:'endTime',title:'上一次运维结束时间'}
         ]],
         fitColumns:true,
         nowrap:true,
	    pagination:true
	
});
     $('#tncqetotal').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,30,40]});  
	
}

function uncqetotal(ywcompanyId){
	clickYwcompanyId =ywcompanyId;
	var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue");
	uwin.window('open'); 
    griduncqetotal=$('#tuncqetotal').datagrid({
	    title:'',
	    pageSize:10,
	    pageList:[10,20,30,40],
	    url:'/tcweb/yw/uncqetotallist',
	    queryParams:{'ywcompanyId':ywcompanyId,'qstartTime':qstartTime,'qendTime':qendTime,'area':zjqueryByarea},
	    columns:[[
	        {field:'registNumber',title:'电梯编号',width:100,formatter: function(value,rec,index) {
	        	<% if("1".equals(cityName)){ %>
	        	 return "N"+value;
	        	 <% } else {%>
                return value;
                <% }%>
	            }},
	        {field:'address',title:'地址',width:200},
	        {field:'buildingName',title:'楼盘',width:200}
         ]],
         fitColumns:true,
         nowrap:true,
	    pagination:true
	
});
     $('#tuncqetotal').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,30,40]});  
	
}

var clickYwUserId =0;
function uywncqetotal(ywcompanyId,userId){   
	clickYwUserId = userId;
	uywnnuwin.window('open');
	griduywncqetotal=$('#tuywncqetotal').datagrid({
		    title:'',
		    pageSize:10,
		    pageList:[10,20,30,40],
		    url:'/tcweb/yw/uywncqetotallist',
		    queryParams:{'ywcompanyId':ywcompanyId,'userId':userId},
		    columns:[[
		        {field:'registNumber',title:'电梯编号',width:100,formatter: function(value,rec,index) {
		        	<% if("1".equals(cityName)){ %>
		        	 return "N"+value;
		        	 <% } else {%>
	                 return value;
	                 <% }%>
		            }},
		        {field:'address',title:'地址',width:200},
		        {field:'buildingName',title:'楼盘',width:200},
		        {field:'endTime',title:'上一次运维结束时间'}
	         ]],
	         fitColumns:true,
	         nowrap:true,
		    pagination:true
		
	});
	     $('#tuywncqetotal').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,30,40]});  
		
}

function nutotal(ywcompanyId){ 
 clickYwcompanyId =ywcompanyId;
 nuwin.window('open'); 
 gridtnutotal=$('#tnutotal').datagrid({
    title:'',
    pageSize:15,
    pageList:[15,20,25,30,35,40],
    url:'/tcweb/yw/nutotallist',
    queryParams:{'ywcompanyId':ywcompanyId},
    columns:[[
        {field:'loginName',title:'登陆名称',width:150},
        {field:'userName',title:'姓名',width:200},
        {field:'qualificationvalidate',title:'资质有效期',width:150},
        {field:'uywetotal',title:'运维电梯数',width:100}, 
        {field:'uywcqetotal',title:'超期电梯数',width:100,formatter: function(value,rec,index) {
            var userId = rec.userId;  
            var ywcompanyId = clickYwcompanyId;
            if (value > 0){
                return  value+" "+"<img src='<%=request.getContextPath()%>/images/ncqetotal.png' alt='当前超期'  style='cursor:hand;' onclick='uywncqetotal("+ywcompanyId+","+userId+")'/>";
                }
            else{
                return value;
                }
	         }}
     ]],
     nowrap:true,
    pagination:true

});
 $('#tnutotal').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  

}



function ExporterExcel(){ 
 // url ='/tcweb/yw/ywstatexportExcel';
    url ='/tcweb/yw/ywstatexportExcel?companyId='+dywCompanyId+"&area="+darea+"&qstartTime="+dqstartTime+"&qendTime="+dqendTime;
	window.location.href = url;
	
}
</script>
<style type="text/css">
td{
font-size:12px;
	overflow:hidden;
	padding:0;
	margin:0;
	}
	
#sousuo input {
  width: 100%; 
  height: 25px; 
 /* background: #F9F0DA; */
   padding-left: 2px; 
}

.form_input {
  display: block;
 /* width: 100%; */
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
<body class="easyui-layout" data-options="fit:true">
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);height:80px;">  
 <fieldset id="addDiv" style="margin-left:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
     <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%;"> 
    <tr>
     <td nowrap align="right">维保单位：</td>
    <td>
    <input id="ywCompanyIdinfo"   placeholder="输入至少两个关键字从下拉列表中选择"></input>
  <input type ="hidden" id="ywCompanyIdinfo2"></input>
   </td>
   <%if(role !=10 && role != 11){ %>
   <td nowrap align="right">行政区划：</td>
  <!--  <td>
   <select id="areainfo"   name="areainfo" class="easyui-combobox" style="width:152px;">
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
 <td><input id="areainfo" name="areainfo" style="width:154px;"/></td>
 <% } %> 
   <td  nowrap align="right">开始时间：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" style="height:25px;"></input></td>
   <td align="right" nowrap>结束时间：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime" style="height:25px;"></input></td>
   
   <td colspan="2">
    <!--
				<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a>  -->
		  <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>
					
</td>
					
   </tr>
   
     </table>
  
  </fieldset>
</div> 
   <div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div>  
       <table id="ywstatisticstt"></table>
   </div>  
        
    </div>  
</div> 
 <div id="ncqetotal-window" title="当前超期" style="width:780px;height:450px;" class="easyui-layout">
  <div style="margin:10px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;height:400px;">
   <div region="north" style="overflow:hidden;width:748px">
     <fieldset id="addDiv"><legend>查询条件</legend>
      <table border="0"> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
   <% if("1".equals(cityName)){ %>
   <td nowrap>N<input id="registNumber" name="registNumber" size="18" class="easyui-validatebox"></input></td>
   <% } else {%>
   <td nowrap><input id="registNumber" name="registNumber" size="18" class="easyui-validatebox"></input></td>
   <% }%>
   <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="qaddress" name="qaddress" style="width:152px;" class="easyui-validatebox"></input></td>
   <td align="right" nowrap>楼盘名称：</td> 
   <td nowrap><input id="buildingName" name="buildingName" size="18" class="easyui-validatebox"></input>
   <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="ncqetotalquery()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="ncqetotalclearQuery()">清空</a></td> 
   </tr>
   </table>
     </fieldset>
   </div>
   <div style="margin-top:1px;" title="当前超期列表" region="center" >  
       <table id="tncqetotal"></table>
   </div>
  </div> 
 </div>
 
  <div id="uncqetotal-window" title="从未运维" style="width:780px;height:450px;" class="easyui-layout">
  <div style="margin:10px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;height:400px;">
   <div region="north" style="overflow:hidden;width:748px">
     <fieldset id="addDiv"><legend>查询条件</legend>
      <table border="0"> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
    <% if("1".equals(cityName)){ %>
   <td nowrap>N<input id="uregistNumber" name="uregistNumber" size="18" class="easyui-validatebox"></input></td>
    <% } else {%>
     <td nowrap><input id="uregistNumber" name="uregistNumber" size="18" class="easyui-validatebox"></input></td>
    <% }%>
   <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="uqaddress" name="uqaddress" style="width:152px;" class="easyui-validatebox"></input></td>
   <td align="right" nowrap>楼盘名称：</td> 
   <td nowrap><input id="ubuildingName" name="ubuildingName" size="18" class="easyui-validatebox"></input>
   <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="uncqetotalquery()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="uncqetotalclearQuery()">清空</a></td> 
   </tr>
   </table>
     </fieldset>
   </div>
   <div style="margin-top:1px;" title="从未运维列表" region="center" >  
       <table id="tuncqetotal"></table>
   </div>
  </div> 
 </div>
  <div id="nutotal-window" title="当前运维人员列表" style="width:780px;height:450px;" class="easyui-layout">
  <div style="margin:10px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;height:400px;">
   <div region="north" style="overflow:hidden;width:748px">
    <fieldset id="addDiv"><legend>查询条件</legend>
      <table border="0"> 
     <tr>      
   <td align="right" nowrap>登陆名称：</td> 
   <td nowrap><input id="loginName" name="loginName" size="18" class="easyui-validatebox"></input>
   <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="nutotalquery()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="nutotalclearQuery()">清空</a></td> 
   </tr>
   </table>
     </fieldset>
   </div>
   <div style="margin-top:1px;" title="当前运维人员列表" region="center" >  
       <table id="tnutotal"></table>
   </div>
   </div>  
 </div>
 
  <div id="uywncqetotal-window" title="运维超期" style="width:780px;height:450px;" class="easyui-layout">
  <div style="margin:10px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;height:400px;">
   <div region="north" style="overflow:hidden;width:748px">
     <fieldset id="addDiv"><legend>查询条件</legend>
      <table border="0"> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
   <% if("1".equals(cityName)){ %>
   <td nowrap>N<input id="uywregistNumber" name="uywregistNumber" size="18" class="easyui-validatebox"></input></td>
    <% } else {%>
    <td nowrap><input id="uywregistNumber" name="uywregistNumber" size="18" class="easyui-validatebox"></input></td>
     <% }%>  
   <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="uywqaddress" name="uywqaddress" style="width:152px;" class="easyui-validatebox"></input></td>
   <td align="right" nowrap>楼盘名称：</td> 
   <td nowrap><input id="uywbuildingName" name="uywbuildingName" size="18" class="easyui-validatebox"></input>
   <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="uywncqetotalquery()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="uywncqetotalclearQuery()">清空</a></td> 
   </tr>
   </table>
     </fieldset>
   </div>
   <div style="margin-top:1px;" title="运维超期列表" region="center" >  
       <table id="tuywncqetotal"></table>
   </div>
  </div> 
 </div>
</body>
</html>