<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO,com.zytx.init.GlobalFunction" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<!--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"> -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
 <style type="text/css">

        .subtotal { font-weight: bold; }/*合计单元格样式*/
  </style>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">

<script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script> 
 <style>
    .datagrid-header-row td{background-color:rgb(226,237,255);color:#0E2D5F;}
  </style>
<% 
String cityName = GlobalFunction.cityName;

UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int isliulan = 0;
int  role = 0; 
if(userinfo!=null){
	 role = userinfo.getRole(); 
	 isliulan = userinfo.getIsliulan();
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
			 if(user != null){
			role = user.getRole();
			 }
			 else{
			    	response.sendRedirect(request.getContextPath() +"/index.jsp");
			    }
			    UserInfoVO user2 =UserInfoVO.findFirstBySql(UserInfoVO.class, "select isliulan from  TCUserInfo where loginName= ? and isinvalid = 0 ",new Object[] { userName });
				 if(user2 != null)
				    isliulan = user2.getIsliulan();
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

	var inputWidth = $('#registNumber').width(); 
	 $('#qstartTime').datebox({     
	       width:inputWidth 
	   }); 

	 $('#qendTime').datebox({     
	       width:inputWidth 
	   }); 

	 $('#buildingName').width(inputWidth);
	 $("#eucompanyName").css("width", inputWidth); 

	$('#btn-ok,#btn-ok2,#btn-no,#btn-no2').linkbutton(); 
	shenhewin = $('#shenhe-window').window({  closed:true,draggable:false,modal:true,onClose:function(){$('#ywPoint').empty();} });
	win = $('#ruku-window').window({  closed:true,draggable:false,modal:true });
	form = win.find('form');
	grid=$('#tt').datagrid({
	    title:'临时维保信息列表（红色:超期）'+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#ff0000"><span id= "fanhong" style="display:none;"></span></font>',
	    fitColumns:true,
 	    striped:true,
	    pageSize:25,
	    pageList:[15,25,30,35,40],
	    url:'/tcweb/yw/tempywlist',
	    queryParams:{},
	    idField:'id',
	    frozenColumns:[[
	        {field:'id',checkbox:true},
            {field:'registNumber',align:'left',halign:'center',title:'电梯编号',width:60,formatter: function(value,rec,index) {
	        	<% if("1".equals(cityName)){ %>
	        	 return "N"+value;
	        	 <% } else {%>
                 return value;
                 <% }%>
	            }},
            {field:'address',align:'left',halign:'center',title:'地址',width:160},
	        {field:'buildingName',align:'left',halign:'center',title:'楼盘名称',width:60}
	                ]],      	    
	    columns:[[
	        {field:'area',align:'center',title:'行政区划',width:60},
	        {field:'companyName',align:'left',halign:'center',title:'电梯对应运维公司',width:200,formatter: function(value,rec,index) {
                if(value && value != rec.companyName2)
                    return value+"<img src='<%=request.getContextPath()%>/images/eyunknown.png' title='与运维员工维保单位不一致' style='cursor:hand;display:block;float:right'/> ";
                else
                    return value;
		         }},
	    //    {field:'ywKind',title:'种类',width:50},
	        {field:'maintainTypecode',align:'center',title:'类型',width:50},
	        {field:'startTime',align:'left',halign:'center',title:'开始时间',width:120},
	        {field:'endTime',align:'left',halign:'center',title:'结束时间',width:120},
	        {field:'dateSpan',align:'center',title:'时长（分钟）',width:70},
	  //      {field:'sPosition',title:'开始位置',width:60},
	  //      {field:'ePosition',title:'结束位置',width:60},
	        {field:'userName',align:'left',halign:'center',title:'维保人员',width:60},
	        {field:'companyName2',align:'left',halign:'center',title:'维保人员单位',width:200,formatter: function(value,rec,index) {
                if('未知' ==value )
                    return value+"<img src='<%=request.getContextPath()%>/images/unknown.png' title='维保人员单位不能为未知' style='cursor:hand;'/> ";
                else
                    return value;
		         }},
	        <% if (role == 1 || role ==2) {%>
	        {field:'tcdaddress',align:'left',halign:'center',title:'粘贴地址',width:160},
	        {field:'tcdbuildingName',align:'left',halign:'center',title:'粘贴楼盘名称',width:60},
	        {field:'subCompanyName',align:'left',halign:'center',title:'粘贴单位',width:200},
	        <% } %>
	        {field:'subTime',align:'left',halign:'center',title:'上传时间',formatter: function(value,rec,index) {
                 if(value)
                     return value.substring(0,16);
                 else
                     return value;
		         }}
	    ]],
	 //   rowStyler:function(index,row){ {if(row.ywSource==1)return 'color:#33CCFF;';}},
	    rowStyler:function(index,row){  //暂时取消颜色超期
		 
	//	 if((row.companyName!=row.companyName2) && (row.companyName)){ 
	//		 return 'color:#3399FF;';
	//	 }
	//	 else {
		    var endTime = row.endTime;
		    if(getDateDiff(endTime))
		    	return 'color:#ff0000;';
		    else
		    	return 'color:#000000;';
	//	 }   
	     },
	    pagination:true,
	    <% if(isliulan == 0){%>
	    <% if(role==2 || role==1){%>
	    toolbar:[{
	        text:'移入正式库',
	        iconCls:'icon-edit',
	        handler:function(){
		    var row = grid.datagrid('getSelected'); 
		    if (row){       
		    //	tempYwRuku();
		        $.messager.confirm('','确定要移入正式库',function(data){if(data){	
		    	tempYwRuku2();
		    	}});
		    }
		    else{
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。' 
	    		 });   
		     }	 
	    }
	    },'-',{
	        text:'删除',
	        iconCls:'icon-cut',
	        handler:function(){
	    	 var row = grid.datagrid('getSelected'); 
	    	 if(row){
	    	 $.messager.confirm('','确定要删除',function(data){if(data){	
	    		 var dcheckedItems = $('#tt').datagrid('getChecked');
	    		 var dnum =0;
	    		 $.each(dcheckedItems, function(index, item){
	    				dnum =dnum + 1;
	    			}); 
	    	  if(dnum >1){
	    		  $.messager.alert('操作失败','只能单条删除','error');
	    		  return ;
	    	  }
	    		    	     
	    	 jQuery.post('/tcweb/yw/tempydelete',
	    	    	 {'id':row.id,'registNumber':row.registNumber},
	    	    	 function(data){
	    	    		eval("data="+"'"+data+"'");  
	    	    		if("success"==data){
	    	    		//	$.messager.alert("操作成功",'谢谢');
	    	    		 $.messager.show({   
	    			 title:'提示信息',
	    			 timeout:1000,
	    			 msg:'操作成功，谢谢。' 
	    		 });  	
		    	            grid.datagrid('reload');
		    	            $("#tt").datagrid('clearSelections').datagrid('clearChecked');
	    	    		}
	    	    		else{
	    	    			$.messager.alert('操作失败','没有删除','error');
		    	    		}
		    	       });}}
  	       );
	    	 }else{
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。' 
	    		 });   
		     }
	    	 
	        }
	    },
	    ],
	    <%}%>
	    <%}%>
	    onLoadSuccess:function() {
	    	fanHongcomputeTotal();
		    }
	   
	});	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
	
}
);


function fanHongcomputeTotal(){  
	 var rows = $('#tt').datagrid('getRows');
	 var total = 0;
	 var total2 = 0;
	 for (var i = 0; i < rows.length; i++) {
		 var endTime = rows[i]["endTime"];
		 if(getDateDiff(endTime))
         total += 1;
		 total2 += 1;
        } 
 // return total;
  
	 document.all.fanhong.innerHTML="(翻红"+total+")";
	 $('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to}'+'<font color="#ff0000">'+document.all.fanhong.innerHTML+'</font>'+'条,'+'  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
		
	
}

//指定列求和
function compute(colName) {  
     var rows = $('#tt').datagrid('getRows');
     var total = 0;
    	 for (var i = 0; i < rows.length; i++) {
   	      
             total += 1;
             
            } 
        
     return total;
     }

var whyruku ="";
function tempYwRuku2(){
	$.messager.prompt('请在下面输入框中填写', '入库原因:', function(r){
		if (r){	
			newtempYwRuku(r);
		}
		else{
			newtempYwRuku("");
			}
	});
}

function newtempYwRuku(r){
	
	 var checkedItems = $('#tt').datagrid('getChecked');
	 var names="";  
	 var ywSource;
	 var elecompanyName ="";
	 var usercompanyName ="";
	 var rukuFlag = true;
	 var usercompanyNameFlag = true;
	 var registNumberStr ="";   //写日志用，记录编号
	
	 $.each(checkedItems, function(index, item){
			names=names+item.id+",";
			registNumberStr = registNumberStr+item.registNumber+",";
			ywSource =item.ywSource;
			elecompanyName =item.companyName;
			usercompanyName=item.companyName2;
			
			if("未知"== usercompanyName){
				usercompanyNameFlag =false;
				$.messager.alert('操作失败','运维人员维保公司为未知，不能入库','error');
				return; 
			}
			if(ywSource!=1){
				if(""==elecompanyName)
				rukuFlag = false;
				}
		//	alert(item.id);
			
		}); 

 if(!rukuFlag){
	$.messager.alert('操作失败','非电梯标签对应运维公司和运维员工所在运维公司不一致进入临时运维的记录不能移入正式库','error');
	return;
 }
 
 if(names!="")
		names=names.substring(0,names.length-1); 
 if(registNumberStr != "")
	 registNumberStr=registNumberStr.substring(0,registNumberStr.length-1);

 if(usercompanyNameFlag && (""!=names)){
 jQuery.post('/tcweb/yw/newtempYwRuku',
		   	 {'names':names,'r':r,'registNumberStr':registNumberStr},
		   	 function(data){
		   		eval("data="+"'"+data+"'");  
		   		if("success"==data){
		   		//	$.messager.alert("操作成功",'谢谢');
		   		 $.messager.show({   
				 title:'提示信息',
				 timeout:1000,
				 msg:'操作成功，谢谢。' 
			 });  		
		            grid.datagrid('reload');
		            $("#tt").datagrid('clearSelections').datagrid('clearChecked');
		           
		   		}
		   		else{
		   			$.messager.alert('操作失败','操作失败','error');
			    		}
			       }); 
 }
}

function tempYwRuku(){
	
	 var checkedItems = $('#tt').datagrid('getChecked');
	 var names="";  
	 var ywSource;
	 var elecompanyName ="";
	 var usercompanyName ="";
	 var rukuFlag = true;
	 var usercompanyNameFlag = true;
	
	 $.each(checkedItems, function(index, item){
			names=names+item.id+",";
			ywSource =item.ywSource;
			elecompanyName =item.companyName;
			usercompanyName=item.companyName2;
			
			if("未知"== usercompanyName){
				usercompanyNameFlag =false;
				$.messager.alert('操作失败','运维人员维保公司为未知，不能入库','error');
				return; 
			}
			if(ywSource!=1){
				if(""==elecompanyName)
				rukuFlag = false;
				}
		//	alert(item.id);
			
		}); 

  if(!rukuFlag){
	$.messager.alert('操作失败','非电梯标签对应运维公司和运维员工所在运维公司不一致进入临时运维的记录不能移入正式库','error');
	return;
  }
  
  if(names!="")
		names=names.substring(0,names.length-1); 

  if(usercompanyNameFlag && (""!=names)){
  jQuery.post('/tcweb/yw/tempYwRuku',
		   	 {'names':names},
		   	 function(data){
		   		eval("data="+"'"+data+"'");  
		   		if("success"==data){
		   		//	$.messager.alert("操作成功",'谢谢');
		   		 $.messager.show({   
				 title:'提示信息',
				 timeout:1000,
				 msg:'操作成功，谢谢。' 
			 });  		
		            grid.datagrid('reload');
		            $("#tt").datagrid('clearSelections').datagrid('clearChecked');
		           
		   		}
		   		else{
		   			$.messager.alert('操作失败','操作失败','error');
			    		}
			       }); 
  }
}


function clearQuery(){ 
	$('#registNumber').attr("value","");
	$('#quserName').attr("value",""); 
	$('#buildingName').attr("value","");
	$("#qstartTime").datebox("setValue","");  
	$("#qendTime").datebox("setValue","");  
	$('#eucompanyName option:first').attr('selected','selected');
}

function query(){ 
	var registNumber=$('#registNumber').attr("value");
	var userName=$('#quserName').attr("value");
	var buildingName=$('#buildingName').attr("value");
	var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue");  

	 var eucompanyName=$('#eucompanyName option:selected').val();
     grid.datagrid("options").url='/tcweb/yw/tempquery';
     grid.datagrid("options").queryParams={'registNumber':registNumber,'userName':userName,'buildingName':buildingName,'qstartTime':qstartTime,'qendTime':qendTime,'eucompanyName':eucompanyName};
     
    $('#tt').datagrid('reload');
	}


var shenheid=0;
var ywstatusValue=0;
function openShenheInfo(registNumber,startTime,id,ywstatus){
	shenheid=id;
	ywstatusValue=ywstatus;
	shenhewin.window('open');
	var ywimg="";
	ywing ='<img height="480" align="center" style="width:100%" src="<%=request.getContextPath()%>'+'/servlet/ywImage.jpg?registNumber='+registNumber+'&startTime='+startTime+'"/>';
	document.all.ywImg.innerHTML=ywing;
	if(ywstatus==0){
		<%if(role==2 || role==1) {%>
		document.all.shenheDiv.innerHTML='<a href="javascript:void(0)" onclick="shenhe()" id="btn-ok" icon="icon-ok">通过</a><a href="javascript:void(0)" onclick="noshenhe()" id="btn-no" icon="icon-no">不通过</a><a href="javascript:void(0)" onclick="closeshenhe()" id="btn-no2" icon="icon-no">关闭</a>';
	 //   $('#btn-ok').html("审核");
		$('#btn-ok').linkbutton();
		$('#btn-no').linkbutton();
		$('#btn-no2').linkbutton();
		<%} else {%>
		document.all.shenheDiv.innerHTML ='<a href="javascript:void(0)" onclick="closeshenhe()" id="btn-no2" icon="icon-ok">关闭</a>';
		$('#btn-no2').linkbutton();
		<%}%>
		}
	else{ 
		document.all.shenheDiv.innerHTML='<a href="javascript:void(0)" onclick="closeshenhe()" id="btn-no2" icon="icon-no">关闭</a>';
	//	$('#btn-no').html("关闭");
		$('#btn-no2').linkbutton();
	}
	ywPoint();
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

function closeshenhe(){  
shenhewin.window('close');  
}



function rukushenhe(){
	var companyName=$('#companyName').attr("value");
	var companyName2=$('#companyName2').attr("value");
	var rukuid =$('#rukuid').attr("value");
	if(companyName != companyName2){
		$.messager.alert('操作失败', '运维员工不属于电梯运维公司', 'error');
		return;
	}
	  jQuery.post('/tcweb/yw/tempRuku',
		    	 {'id':rukuid,'companyName':companyName},
		    	 function(data){
		    		eval("data="+"'"+data+"'");  
		    		if("success"==data){
		    		//	$.messager.alert("操作成功",'谢谢');
		    		 $.messager.show({   
				       title:'提示信息',
				       timeout:1000,
				        msg:'操作成功，谢谢。' 
			        });  	
		    		 win.window('close');
	 	            grid.datagrid('reload');
		    		
		    		}
		    		else{
		    			$.messager.alert('操作失败','入库失败','error');
	    	    		}
	    	       });
	 
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
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">  
<fieldset id="addDiv" style="margin-left:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
  <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%;"> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td>   
   <% if("1".equals(cityName)){ %>
   <td nowrap>N<input id="registNumber" name="registNumber"></input></td>
    <% } else {%>
   <td nowrap><input id="registNumber" name="registNumber"></input></td>
 <% }%>
 <td align="right" nowrap>楼盘名称：</td> 
   <td nowrap><input id="buildingName" name="buildingName"></input></td>
    <td align="right" nowrap>(电梯与运维人员)单位：</td>
   <td>
    <select id="eucompanyName"   name="eucompanyName" style="height:25px;">
   <option value="100"></option>
   <option value="0">一致</option>
   <option value="1">不一致</option>
   </select>
   </td>
     
   </tr>
   <tr>
   <td align="right" nowrap>维保人员：</td> 
   <td nowrap><input id="quserName" name="quserName"></input></td>
    <td align="right" nowrap>开始时间：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" style="height:25px;"></input></td>
   <td align="right" nowrap>结束时间：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime" style="height:25px;"></input></td>
   
   <td>
	<!--			<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a>   -->
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
       <table id="tt"></table>
   </div>  
        
    </div>  
</div> 

  
 <div id="shenhe-window" title="审核详细信息" style="width:780px;height:550px;">
 <div style="both:clear">
 <div id="ywImg"  style="overflow:hidden;width:49%;float:left;border:1px solid #000;"></div>
 <div id="ywPoint" style="overflow:hidden;width:50%;height:480px;float:right;border:1px solid #00f;"></div>
 </div> 
 <div style="both:clear"></div>
 <div align="center" id="shenheDiv" style="both:clear"><a href="javascript:void(0)" onclick="shenhe()" id="btn-ok" icon="icon-ok">通过</a><a href="javascript:void(0)" onclick="noshenhe()" id="btn-no" icon="icon-no">不通过</a><a href="javascript:void(0)" onclick="closeshenhe()" id="btn-no2" icon="icon-no">关闭</a></div>
</div>

<div id="ruku-window" title="运维人员与二维码标签信息" style="width:480px;height:350px;" >
<div style="padding:20px 20px 40px 80px;">   
  <form method="post"> 
 <table>
   <tr>
    <td><input type="hidden" id="rukuid" name="rukuid"></input></td>
   </tr>    
   <tr>      
   <td nowrap align="right">电梯编号：</td>
    <% if("1".equals(cityName)){ %>      
   <td>N<input id="registNumberinfo" name="registNumber"></input></td> 
    <% } else {%>
  <td><input id="registNumberinfo" name="registNumber"></input></td> 
 <% }%>
   </tr>
   <tr>     
   <td nowrap align="right">电梯所属维保单位：</td>      
   <td><input id="companyName" name="companyName"  size="30"></input></td>
   </tr>
    <tr>  
    <td></td>
    </tr>
    <tr>    
   <td nowrap align="right">运维人员：</td>      
   <td><input  id="userName" name="userName"></input></td>
   </tr>
   <tr>
   <td nowrap align="right">运维人员所属维保单位：</td>      
   <td><input id="companyName2" name="companyName2"  size="30"></input></td>
   </tr>
   </table>
   </form>
    <%if(role==1 || role==2){%>
   <hr> 
    <center><span><a href="javascript:void(0)" onclick="rukushenhe()" id="btn-ok2" icon="icon-ok">移入正式运维库</a></span></center>
    <%} %>
   </div>
</div>
</body>
</html>