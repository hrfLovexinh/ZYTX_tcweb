<%@ page import="com.zytx.models.UserInfo" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全服务平台</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>

<% 
String userName = "";
String password = "";
String registNumber="";
String beginTime="";
int  role = 0; 
userName = request.getParameter("loginName");
password = request.getParameter("password");
registNumber =request.getParameter("registNumber");
beginTime=request.getParameter("beginTime");
if(registNumber==null)
	registNumber ="";
if(beginTime==null)
	beginTime ="";
UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select t.*,te.userName from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id=te.userid where loginName= ?",new Object[] { userName });
if(user!=null){
  if(user.getPassword().equals(password)){
	  System.out.println(user.getLoginName()+"cs登陆成功");
	  role = user.getRole();
	  request.getSession().setAttribute("sessionAccount", user);   
	  Cookie uNCookie = new Cookie("userName", userName); 
	  Cookie pWCookie = new Cookie("password", password); 
	  uNCookie.setPath("/"); 
	  pWCookie.setPath("/");
	  response.addCookie(uNCookie);
	  response.addCookie(pWCookie);
  }	
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

   winyw = $('#yw-window').window({  closed:true,draggable:false,modal:true }); 
   winywtj = $('#ywtjwindow').window({  closed:true,draggable:false,modal:true }); 
   $('#btn-save,#btn-cancel,#btn-ok,#btn-no,#btn-no2').linkbutton(); 
   win = $('#car-window3').window({  closed:true,draggable:false }); 
   form = win.find('form');
	
	grid=$('#tt').datagrid({
	    title:'维保信息列表',
	    pageSize:15,
	    pageList:[15,20,25,30,35,40],
	    url:'/tcweb/yw/csywlist',
	    queryParams:{'registNumber':'<%=registNumber%>','beginTime':'<%=beginTime%>'},
	    columns:[[
	        {field:'registNumber',title:'电梯编号',width:60},
	        {field:'address',title:'地址',width:160},
	        {field:'maintainTypecode',title:'类型',width:50},
	        {field:'startTime',title:'开始时间',width:120},
	        {field:'endTime',title:'结束时间',width:120},
	        {field:'dateSpan',title:'时长（分钟）',width:70,formatter: function(value,rec,index) { 
                var startTime =rec.startTime;
                var endTime = rec.endTime;
                if(startTime)
                	startTime=Date.parse(startTime.replace(/-/g,'/').replace(/：/g,":"));
                if(endTime)
                	endTime=Date.parse(endTime.replace(/-/g,'/').replace(/：/g,":"));
                var date3=endTime-startTime;
              //计算出相差分钟数
                var days=parseInt(date3/(60*1000));
                return days; 
		        }},
	       {field:'userName',title:'维保人员',width:100},
	        {field:'companyName',title:'维保单位',width:150},
	       {field:'subTime',title:'上传时间',formatter: function(value,rec,index) {
                if(value)
                     return value.substring(0,16);
                 else
                     return value;
		         }},
		     {field:'ywstatus',title:'状态',width:80,formatter: function(value,rec,index) {
		        	 var registNumber = ''+rec.registNumber;
		        	 var startTime =''+rec.startTime;
		        	 var picNum =rec.picNum;
		        	 var id = rec.id;
	                 if('0'==value){
		                 return "审核中";  
		             }else if('4'==value){
		            	   return "无效";
		            	 
		             }
	                 else{
		            	   return "通过";  
	                 }
			         }}
	    ]],
	    rowStyler:function(index,row){  //暂时取消颜色超期
		 //   var endTime = row.endTime;
		//    if(getDateDiff(endTime))
		 //   	return 'color:#ff0000;';
		//    else
		//    	return 'color:#000000;';
		    
	     },
	    pagination:true  
	});	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  	  	  
}
);

function clearQuery(){
	$("#qstartTime").datebox("setValue","");  
	$("#qendTime").datebox("setValue","");  
}

function query(){ 
	var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue"); 
	var registNumber ='<%=registNumber%>';
	var beginTime='<%=beginTime%>';
    grid.datagrid("options").url='/tcweb/yw/csquery';
    grid.datagrid("options").queryParams={'registNumber':registNumber,'qstartTime':qstartTime,'qendTime':qendTime,'beginTime':beginTime}; 
    $('#tt').datagrid('reload');
	}


var shenheid=0;
var ywstatusValue=0;


function closeWindow(){ 
	win.window('close');
	}

function showWinDetail(){
	$("form input").css({border:'0px solid' });
	
	$('#dev_idinfo').attr("readonly","readonly");
	$('#carnuminfo').attr("readonly","readonly");
	$('#qy_nameinfo').attr("readonly","readonly");
	$('#simnuminfo').attr("readonly","readonly");
	$('#dev_typeinfo').attr("readonly","readonly");
	$('#car_typeinfo').attr("readonly","readonly");
	$('#carnum_colorinfo').attr("readonly","readonly");
	$('#car_colorinfo').attr("readonly","readonly");
	$('#longitudeinfo').attr("readonly","readonly");
	$('#latitudeinfo').attr("readonly","readonly");
	$('#angleinfo').attr("readonly","readonly");
	$('#speedinfo').attr("readonly","readonly");
	$('#gps_timeinfo').attr("readonly","readonly");
	$('#personinfo').attr("readonly","readonly");
	$('#phoneinfo').attr("readonly","readonly");
	
	$(".fontShow").hide();
	$('#btn-save').hide();
	$('#btn-cancel').show();
	$('#table2').show();

	$('#qy_nameinfo').combobox('disable');   
}

function colseWinDetail(){
	$("form input").css({border:'1px solid' });
	
	 $(".fontShow").show();	
	 $('#btn-save,#btn-cancel').show();  
	// $('#registNumberinfo').attr("disabled","disabled");
     /*
	 $('#longitudeinfo').attr("value",0);   
	 $('#latitudeinfo').attr("value",0);
	 $('#speedinfo').attr("value",0);
	 $('#angleinfo').attr("value",0);  
	 $('#gps_timeinfo').attr("value","2011-08-080");  
	 
	 $('#dev_idinfo').attr("readonly","");
	 $('#carnuminfo').attr("readonly","");
	 $('#qy_nameinfo').attr("readonly","");
	 $('#simnuminfo').attr("readonly","");
	 $('#dev_typeinfo').attr("readonly","");
	 $('#car_typeinfo').attr("readonly","");
	 $('#carnum_colorinfo').attr("readonly","");
	 $('#car_colorinfo').attr("readonly","");
	 $('#longitudeinfo').attr("readonly","");
	 $('#latitudeinfo').attr("readonly","");
	 $('#angleinfo').attr("readonly","");
	 $('#speedinfo').attr("readonly","");
	 $('#gps_timeinfo').attr("readonly","");
	 $('#personinfo').attr("readonly","");
	 $('#phoneinfo').attr("readonly","");    

	 
	// $('#latitudeinfo').hide();
	*/
	$('#table2').hide();

//	$('#qy_nameinfo').combobox('enable'); 
}

function saveCar3(){ 
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){
     //   return $(this).form('validate');
		var registNumber=$('#registNumberinfo').attr("value"); 
		var ywKind= $('#ywKind').combobox('getValue'); 
		var maintainTypecode =$('#maintainTypecode').combobox('getValue');
		var startTime =$('#startTime').datetimebox('getValue'); 
		var endTime =$('#endTime').datetimebox('getValue');
		var sPosition =$('#sPosition').combobox('getValue');
		var ePosition =$('#ePosition').combobox('getValue');
		
		var ywCompanyId =$('#ywCompanyId').combobox('getValue');
	//	alert("ywCompanyId:"+ywCompanyId);
		
		var userId =$('#userId').combobox('getValue');
	//	alert("userId:"+userId);
       
			if (!registNumber) {
				$.messager.alert('操作失败', '电梯编码不能为空', 'error');
				return false;
			}
			if (!ywKind) {
				$.messager.alert('操作失败', '维保种类不能为空', 'error');
				return false;
			}

			if (!maintainTypecode) {
				$.messager.alert('操作失败', '维保类型不能为空', 'error');
				return false;
			}

			if (!startTime) {
				$.messager.alert('操作失败', '开始时间不能为空', 'error');
				return false;
			}

			if(startTime != ""){
				if (!strDateTime(startTime)) {
					$.messager.alert('操作失败', '开始时间格式形如：2013-01-04 22:14', 'error');
					return false;
				}
				}

			if (!endTime) {
				$.messager.alert('操作失败', '结束时间不能为空', 'error');
				return false;
			}

			if(endTime != ""){
				if (!strDateTime(endTime)) {
					$.messager.alert('操作失败', '结束时间格式形如：2013-01-04 22:14', 'error');
					return false;
				}
				}

			if(datecompare(startTime,endTime) !=1){
				$.messager.alert('操作失败', '开始时间必须小于结束时间', 'error');
				}

			if (!sPosition) {
				$.messager.alert('操作失败', '开始位置不能为空', 'error');
				return false;
			}

			if (!ePosition) {
				$.messager.alert('操作失败', '结束位置不能为空', 'error');
				return false;
			}

			if (!ywCompanyId) {
				$.messager.alert('操作失败', '维保单位不能为空', 'error');
				return false;
			}
	

				if (!userId) {
					$.messager.alert('操作失败', '维保人员不能为空', 'error');
					return false;
				}

			

				

			  

			return true;
			//	return false;
		},

		success : function(data) {
			eval("data=" + "'" + data + "'");
			if ("exist" == data) {
				$.messager.alert('操作失败', '不存在该二维码标签，不能添加维保信息', 'error');
			} else if ("success" == data) {
				//	$.messager.alert("操作成功",'谢谢');    
			$.messager.show( {
				title : '提示信息',
				timeout : 1000,
				msg : '操作成功，谢谢。'
			});
			testDid="";
			grid.datagrid('reload');
			win.window('close');
		}  else if("failure2"== data){
			$.messager.alert('操作失败', '所选择的维保单位不属于标签所在的维保单位', 'error');
			}
		   else if("failure3"== data){
			$.messager.alert('操作失败', '所选择维保人员不属于标签所在维保单位的维保人员', 'error');
			}
			else {
			$.messager.alert('操作失败', '添加标签维保信息', 'error');
		}
	}
		});
	}

  var testDid;
  function testDid(){  
	  var deviceId="";
      deviceId =$('#deviceId').attr("value"); 
      if(deviceId !=""){  
          if(deviceId.length ==16){
        	  $.get("/tcweb/elevator/queryDid",{deviceId:deviceId},function (data, textStatus){
            	 if(data=="0") { //alert(data);
            		$.messager.alert('操作失败', '该设备已经关联了，不能重复添加', 'error'); 
            		$('#deviceId').focus();
            		return false;
            		}
         		else{
         			testDid=$('#deviceId').attr("value");
         			$.messager.alert('设备号查询', '该设备号可用', 'info'); 
             		}
        	  });
              }
          else{
        	 $.messager.alert('操作失败', '设备号不可用', 'error');
        	 $('#deviceId').focus();
        	 return false;
              }
	    }
      
	  }


  function addFun(){
	  opt =0;
  
	  }

  var oldRegistNumber;
  function editFun(registNumber){
      opt =1;
      oldRegistNumber =registNumber;
	  }


//startdate和enddate的格式为：yyyy-MM-dd hh:mm:ss  
//当date1在date2之前时，返回1;当date1在date2之后时，返回-1；相等时，返回0  
function datecompare(date1, date2){  
    var strdt1=date1.replace("-","/");  
    var strdt2=date2.replace("-","/");     
    
    var d1 = new Date(Date.parse(strdt1));  
    var d2 = new Date(Date.parse(strdt2));  
    if(d1<d2){  
        return 1;  
    } else if ( d1>d2 ){  
        return -1;  
    } else{   
        return 0;  
    }   
} 

function queryYwCompanyByReg(){ 
	var registNumber=$('#registNumberinfo').attr("value"); 
	if(registNumber.length==6){ 
	   $('#ywCompanyId').combobox('clear');  
       $('#ywCompanyId').combobox('reload','/tcweb/elevator/queryYwCompanyByReg?registNumber='+registNumber); 
		}
}

function closeshenhe(){
	 shenhewin.window('close'); 
}

var plng=0.0;
var plat=0.0;
var map_x;
var map_y;
var map_x0;
var map_y0;
var map_x1;
var map_y1;
var map_x2;
var map_y2;


function ywPoint(){
	jQuery.post('/tcweb/yw/shenhePointByReg', {'id':shenheid},function(data){ 
		          data = eval(data);//POST方法必加，ajax方法自动处理了   
		          map_x=data.map_x; 
		          map_y=data.map_y;
		          map_x0=data.map_x0; 
		          map_y0=data.map_y0;
		          map_x1=data.map_x1; 
		          map_y1=data.map_y1;
		          map_x2=data.map_x2; 
		          map_y2=data.map_y2;
		          showYwPointMap();
			   }, 'json');
}

function showYwPointMap(){
	$('#ywPoint').append("<iframe src='mapinfo.jsp?map_x="+map_x+"&map_y="+map_y+"&map_x0="+map_x0+"&map_y0="+map_y0+"&map_x1="+map_x1+"&map_y1="+map_y1+"&map_x2="+map_x2+"&map_y2="+map_y2+"' width='100%' height='100%' ></iframe>");
    
}

function showMap(){
	var map = new BMap.Map("container");// 创建地图实例
	var point = new BMap.Point(map_x, map_y);
	map.centerAndZoom(point, 15); // 初始化地图，设置中心点坐标和地图级别   
}

function openYwDetail(registNumber){
	winyw.window('open'); 
	 gridyw=$('#ywttmap').datagrid({
	 	    title:'',
	 	    pageSize:15,
	 	    pageList:[15,20,25,30,35,40],
	 	    url:'/tcweb/yw/ywlistByreg',
	 	    queryParams:{'registNumber':registNumber},
	 	   columns:[[
	 		        {field:'registNumber',title:'电梯编号',width:60},
	 		        {field:'address',title:'地址',width:160},
	 		        {field:'ywKind',title:'种类',width:50},
	 		        {field:'maintainTypecode',title:'类型',width:50},
	 		        {field:'startTime',title:'开始时间',width:120},
	 		        {field:'endTime',title:'结束时间',width:120},
	 		        {field:'dateSpan',title:'时长（分钟）',width:70},
	 		        {field:'sPosition',title:'开始位置',width:55},
	 		        {field:'ePosition',title:'结束位置',width:55},
	 		        {field:'userName',title:'维保人员',width:55},
	 		        {field:'companyName',title:'维保单位',width:150},
	 		        {field:'subTime',title:'上传时间',formatter: function(value,rec,index) {
	 	                 if(value)
	 	                     return value.substring(0,16);
	 	                 else
	 	                     return value;
	 			         }}
	 		    ]],
	          nowrap:true,
	 	    pagination:true
	 	
	 });

	     $('#ywttmap').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  
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
 //   return result;   
    if(dayC>15)
        return true;
    else if(dayC ==15){
        if(minC>=1)
            return true;
        else 
            return false;
        }
    else
        return false;
        
        
}   

var rp;
function ywtj2(userId){
	winywtj.window('open');
//	jQuery.post('/tcweb/yw/ywtj', {'id':userId},function(data){ 
//        data = eval(data);//POST方法必加，ajax方法自动处理了   
//	   }, 'json');
//	rp =data.xcb;
    $("#ywtjwindow").empty();
    
	$('#ywtjwindow').append("<iframe src='jfreeChart.jsp?userId="+userId+"' width='100%' height='100%' scrolling='no'></iframe>");
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
<body class="easyui-layout">
<div region="north" style="overflow:hidden">  
 <fieldset id="addDiv" style="width: 100%;margin:0px"><legend>查询条件</legend>
    
   <table border="0"> 
   <tr>
   <td align="right" nowrap>开始时间:</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" style="width:152px;"></input></td>
   <td align="right" nowrap>结束时间:</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime" style="width:152px;"></input></td>
   <td>
				<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 
			<!--		<input type="submit" value="Submit Comment" /> -->	
					
</td>
					
   </tr>
   
     </table>
  
  </fieldset>
</div> 
   <div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div>  
       <table id="tt"></table>
   </div>  
        
    </div>  
</div> 
<div id="car-window3" title="详细信息" style="width:750px;height:550px;"> 
  <div style="padding:20px 20px 40px 80px;">   
  <form method="post">    
  <table>    
   <tr>      
   <td width="70">电梯编号：</td>      
   <td><input id="registNumberinfo" name="registNumber" onchange="queryYwCompanyByReg()"></input><span class="fontShow"><font color="red">*</font></span></td>  
    <td></td>
    <td></td>
    </tr>
    <tr>
    <td>种类：</td>      
   <td><select id="ywKind"  class="easyui-combobox" name="ywKind" style="width:152px;">
    <option value="0">维保</option>
     <option value="1">巡检</option> 
</select><span class="fontShow"><font color="red">*</font></span></td> 
    <td>类型：</td>    
 <td><select id="maintainTypecode"  class="easyui-combobox" name="maintainTypecode" style="width:152px;">
    <option value="半月保">半月保</option>
    <option value="季保">季保</option> 
    <option value="半年保">半年保</option>
    <option value="年保">年保</option> 
</select><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
   <tr> 
    <td>开始时间：</td>      
   <td><input id="startTime" name="startTime" class="easyui-datetimebox" style="width:152px;" showSeconds="false"></input><span class="fontShow"><font color="red">*</font></span></td> 
   
   <td>结束时间：</td>      
   <td> 
   <input id="endTime" name="endTime" class="easyui-datetimebox" style="width:152px;" showSeconds="false"></input><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
   <tr>
   <td>开始位置：</td>      
   <td><select id="sPosition"  class="easyui-combobox" name="sPosition" style="width:152px;">
    <option value="0">机房</option>
    <option value="1">轿厢</option>  
</select><span class="fontShow"><font color="red">*</font></span>
   </td>
    
    <td>结束位置：</td>      
   <td>
   <select id="ePosition"  class="easyui-combobox" name="ePosition" style="width:152px;">
    <option value="0">机房</option>
    <option value="1">轿厢</option>  
</select><span class="fontShow"><font color="red">*</font></span> 
   </td>
   </tr>
    <tr> 
    <td>维保单位:</td>
<td>  
<select id="ywCompanyId"  class="easyui-combobox" name="ywCompanyId" style="width:152px;"> 
</select>
<span class="fontShow"><font color="red">*</font></span></td>      
   <td>维保人员：</td>      
   <td><select id="userId"  class="easyui-combobox" name="userId" style="width:152px;"> 
</select><span class="fontShow"><font color="red">*</font></span></td>   
   </tr>
   <tr>
   <td>备注:</td>
   <td colspan="3"><textarea cols="50" rows="6" id="remark" name="remark" ></textarea></td>
   </tr> 
   </table>
   <table width=70%>
    <tr>
    <td align="center">
      <a href="javascript:void(0)" onclick="saveCar3()" id="btn-save" icon="icon-save">保存</a>  
     <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">取消</a> 
    </td>
    </tr> 
   </table>   
   </form> 
    </div> 
     
   </div>
  
  
   <!--  
    <div style="text-align:center;padding:5px;">    
     <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">取消</a>  </div> 
    </div> -->
 <div id="shenhe-window" title="审核详细信息" style="width:780px;height:550px;">
 <div style="both:clear">
 <div id="ywImg"  style="overflow:hidden;width:49%;float:left;border:1px solid #000;"></div>
 <div id="ywPoint" style="overflow:hidden;width:50%;height:480px;float:right;border:1px solid #00f;"></div>
 </div> 
 <div style="both:clear"></div>
 <div style="float:left">结论：<select id="ywResult"  name="ywResult" style="width:80px;">
    <option value="100">正常</option>
    <option value="90">丢失</option>
    <option value="80" selected ="selected">未知</option> 
    <option value="50">图异常</option> 
    <option value="40">号输错</option>
    <option value="30">位置错</option>
    <option value="20">复制</option>
    <option value="10">破坏</option>   
</select></div>
 <div align="center" id="shenheDiv"><a href="javascript:void(0)" onclick="shenhe()" id="btn-ok" icon="icon-ok">通过</a><a href="javascript:void(0)" onclick="noshenhe()" id="btn-no" icon="icon-no">不通过</a><a href="javascript:void(0)" onclick="closeshenhe()" id="btn-no2" icon="icon-no">关闭</a></div>
</div>

  <div id="yw-window" title="维保信息" style="width:780px;height:550px;">
        <table id="ywttmap"></table>  
   </div>
   
   <div id="ywtjwindow" title="运维统计信息" style="width:780px;height:550px;overflow-x:hidden;overflow-y:hidden;"> 
   </div>  
</body>
</html>