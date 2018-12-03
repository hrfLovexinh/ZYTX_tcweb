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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">

<script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script> 
 <style>
    .datagrid-header-row td{background-color:rgb(226,237,255);color:#0E2D5F;}
  </style>
<% 
String cityName = GlobalFunction.cityName;

UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int role=0;
if(userinfo!=null){
	 role = userinfo.getRole(); 
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
	 $("#ncqtype").css("width", inputWidth);
	 $("#qtownshipStreets").css("width", inputWidth);

	 $('#qstartTime').datebox({     
	       width:inputWidth 
	   }); 

	 $('#qendTime').datebox({     
	       width:inputWidth 
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

	 combt=$('#qtownshipStreets').combobox({
	    	valueField:'id',
		    textField:'companyName'
		});
			    
	    $('#qtownshipStreets').combobox({
	    	filter: function(q, row){
	    	//    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });
	    <%if((role == 1 || role ==2)||(role == 22 || role ==23)) { %>  
	    $("#areainfo").css("width", inputWidth);
	    $('#areainfo').combobox({   
	        url:'/tcweb/elevator/areaInfoList',   
	        valueField:'area',   
	        textField:'area'  
	    }); 

	    comba =$('#areainfo').combobox({
			 onSelect: function (record) {
			 combt.combobox({
	//       url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.value),
	       url: '/tcweb/elevator/getjdbCompanyListByNewarea?companyArea='+encodeURI(record.area),
	       valueField: 'id',
	       textField: 'companyName'
	   }).combobox('clear');

		     }
			});
	    <% } else if(role == 10 || role ==11){ %>
	    combt=$('#qtownshipStreets').combobox({
	    	url:'/tcweb/elevator/getjdbCompanyListByarea2',
	    	valueField:'id',
		    textField:'companyName'
		});
			    
	    $('#qtownshipStreets').combobox({
	    	filter: function(q, row){
	    	//    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });
	    <% }%>
	grid=$('#nywcqtt').datagrid({
	    title:'当前维保超期列表',
	    fitColumns:true,
 	    striped:true,
	    pageSize:25,
	    pageList:[15,25,30,35,40],
	    url:'/tcweb/yw/nywcqlist',
	    queryParams:{},
	    columns:[[
	        {field:'registNumber',align:'center',halign:'center',title:'电梯编号',width:$(this).width() * 0.1,formatter: function(value,rec,index) {
	        	<% if("1".equals(cityName)){ %>
	        	 return "N"+value;
	        	 <% } else {%>
                return value;
                <% }%>
		        }},
		    {field:'registCode',align:'center',halign:'center',title:'注册代码',width:$(this).width() * 0.15},
	        {field:'address',align:'left',halign:'center',title:'地址',width:$(this).width() * 0.1},
	        {field:'buildingName',align:'left',halign:'center',title:'楼盘名称',width:$(this).width() * 0.1},
	        {field:'companyName',align:'left',halign:'center',title:'维保公司',width:$(this).width() * 0.1},
	        {field:'area',align:'center',title:'行政区划',width:$(this).width() * 0.08},
	        {field:'jdbCompanyName',align:'left',halign:'center',title:'乡镇街道办',width:$(this).width() * 0.1},
	        {field:'userName',align:'center',halign:'center',title:'维保人员',width:$(this).width() * 0.1},
	        {field:'telephonemobile',align:'left',halign:'center',title:'维保人员手机',width:$(this).width() * 0.1},
	        {field:'endTime',align:'center',halign:'center',title:'上一次维保结束时间',width:$(this).width() * 0.1},
		    {field:'超期时间',align:'center',title:'超期时间(天)',width:$(this).width() * 0.1,formatter: function(value,rec,index) {
			    if(rec.endTime != '')
                return getDateDiff(rec.endTime);
			    else
				return '';
		         }
	         } 
	    ]], 
	    rowStyler:function(index,row){ {return 'color:#ff0000;';}} , 
	    pagination:true,
	    singleSelect:true,
	    toolbar:[{
   	        text:'导出excel',
   	        iconCls:'icon-excel',
   	        handler:function(){
   	     	    ExporterExcel();
   	        }
   	    }]	    
	});	
	$('#nywcqtt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
 	  	  
}
);

function clearQuery(){
	$('#registNumber').attr("value","");
//	$('#ncqtype').combobox('select','0');
    $('#ncqtype option:first').attr('selected','selected');
	$("#qstartTime").datebox("setValue","");  
	$("#qendTime").datebox("setValue",""); 
	 <%if((role == 1 || role ==2) || (role ==22 || role ==23)) {%>
	$('#areainfo').combobox('clear');
	<% }%>
	$('#qtownshipStreets').combobox('clear');
	$('#ywCompanyIdinfo').attr("value","");
	$('#ywCompanyIdinfo2').attr("value","");
}


//导出查询条件
var dregistNumber ="";
var dncqtype ="";
var dqstartTime="";
var dqendTime="";
var dywCompanyId = 0;
var darea ="";
var dqtownshipStreets = 0;

function query(){    
	var registNumber=$('#registNumber').attr("value"); 
  //  var ncqtype =$('#ncqtype').combobox('getValue'); 
    var ncqtype =$('#ncqtype option:selected').val();
	var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue");  
	var area ="";
	<%if((role == 1 || role ==2) || (role ==22 || role ==23)) {%>
	 area =$('#areainfo').combobox('getValue'); 
	 <% } %>  
    var qtownshipStreets =$('#qtownshipStreets').combobox('getValue'); 
    var ywCompanyId=$('#ywCompanyIdinfo2').attr("value"); 
    if (!ywCompanyId){
    	ywCompanyId =0;
    	} 
    if(qtownshipStreets==""){
    	qtownshipStreets = 0;
        }

  //导出查询条件fuzhi
    dregistNumber =registNumber;
    dncqtype =ncqtype;
    dqstartTime=qstartTime;
    dqendTime=qendTime;
    dywCompanyId = ywCompanyId;
    darea =area;
    dqtownshipStreets = qtownshipStreets;
	
     grid.datagrid("options").url='/tcweb/yw/ncqquery';
     grid.datagrid("options").queryParams={'registNumber':registNumber,'ncqtype':ncqtype,'qstartTime':qstartTime,'qendTime':qendTime,'companyId':ywCompanyId,'area':area,'townshipStreets':qtownshipStreets};
     
    $('#nywcqtt').datagrid('reload');
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


function ExporterExcel(){
	 <% if((role == 1 || role == 2) ||(role == 22 || role == 23)) { %>
	    var exportFlag =false;

	    if(dregistNumber != "" && !exportFlag){
	    	exportFlag = true;
		    }
	    if(darea != "" && !exportFlag){
	    	exportFlag = true;
		    }
	    if(dqstartTime != "" && !exportFlag){
	    	exportFlag = true;
		    }

	    if(dqendTime != "" && !exportFlag){
	    	exportFlag = true;
		    }
	    if(dywCompanyId != 0 && !exportFlag){
	    	exportFlag = true;
		    }
	    if(dqtownshipStreets != 0 && !exportFlag){
	    	exportFlag = true;
		    }
	    

	    if(!exportFlag){
	    	$.messager.alert('操作失败','请选择至少一个条件查询后导出','error');
	    	return;
		    }
	    else{ 
		    url ='/tcweb/yw/ncqqueryexportExcel?ncqtype='+dncqtype+"&registNumber="+dregistNumber+"&area="+darea+"&qstartTime="+dqstartTime+"&qendTime="+dqendTime+"&companyId="+dywCompanyId+"&townshipStreets="+dqtownshipStreets;
		    var dquerytotal = $('#nywcqtt').datagrid('getData').total;  
		    var intervalTimes =parseInt(dquerytotal/1000); 
	    //    alert("intervalTimes---"+intervalTimes);
		    if(intervalTimes > 0){
		    $.messager.progress({
		        title:'导出中,请等待...',
		        msg:'导出进度：',
		        interval: 180*intervalTimes
		    });
		    $.messager.progress('bar').progressbar({
		        onChange: function(value){
		            if(value == 100){
		                $.messager.show({
		                    title:'导出完毕',
		                    msg:'导出完毕，请保存！',
		                    timeout:2000,
		                    showType:'fade',
		                    style:{
		                        top:'45%'
		                    }
		                });
		                $.messager.progress('close');
		            }
		        }
		    }); }
		    
		 	window.location.href = url;
		    }
		 	<% } else {%>   
	//	 	var dncqtype =$('#ncqtype').combobox('getValue'); 
	        var dncqtype =$('#ncqtype option:selected').val();
		    url ='/tcweb/yw/ncqqueryexportExcel?ncqtype='+dncqtype+"&registNumber="+dregistNumber+"&area="+darea+"&qstartTime="+dqstartTime+"&qendTime="+dqendTime+"&companyId="+dywCompanyId+"&townshipStreets="+dqtownshipStreets;		  
		 	var dquerytotal = $('#nywcqtt').datagrid('getData').total;  
			    var intervalTimes =parseInt(dquerytotal/1000); 
		    //    alert("intervalTimes---"+intervalTimes);
			    if(intervalTimes > 0){
			    $.messager.progress({
			        title:'导出中,请等待...',
			        msg:'导出进度：',
			        interval: 200*intervalTimes
			    });
			    $.messager.progress('bar').progressbar({
			        onChange: function(value){
			            if(value == 100){
			                $.messager.show({
			                    title:'导出完毕',
			                    msg:'导出完毕，请保存！',
			                    timeout:2000,
			                    showType:'fade',
			                    style:{
			                        top:'45%'
			                    }
			                });
			                $.messager.progress('close');
			            }
			        }
			    }); }
			  
			window.location.href = url;
		 	
		 	<% } %>
		   
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
/*  background: #F9F0DA; */
  padding-left: 2px;
}

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
</style>
</head>
<body class="easyui-layout" data-options="fit:true">
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">  
 <fieldset id="addDiv" style="margin:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
     <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%">
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
    <% if("1".equals(cityName)){ %> 
   <td nowrap>N<input id="registNumber" name="registNumber"  class="easyui-validatebox"></input></td>
   <% } else {%>
   <td nowrap><input id="registNumber" name="registNumber"  class="easyui-validatebox"></input></td>
    <% }%>
   <td align="right" nowrap>超期类型：</td> 
  <td> <select id="ncqtype"   name="ncqtype" style="height:25px;">
    <option value="0">运维超期</option>
    <option value="1">从未运维</option>
    </select>
    </td>
    <td  nowrap>开始时间：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" ></input></td>
   <td align="right" nowrap>结束时间：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime" ></input></td>
   </tr>
    <!--  <td nowarp>是否处理</td>
   <td> <select id="isProcessFlag"  class="easyui-combobox" name="is_Process" style="width:152px;">
    <option value="2">全部</option>
    <option value="0">未处理</option>
    <option value="1">已处理</option>
    </select>
    </td>  -->
    <tr>
     <td align="right" nowrap>维保单位：</td>
    <td><input id="ywCompanyIdinfo" style="height:25px;" placeholder="输入至少两个关键字从下拉列表中选择"></input>
  <input type ="hidden" id="ywCompanyIdinfo2"></input>
 </td>
 <% if((role == 1 || role ==2) || (role == 22 || role ==23)){ %> 
 <td nowrap align="right">行政区划：</td>
 <!--  
   <td>
   <select id="areainfo"   name="areainfo" style="width:152px;">
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
</td> -->
  <td><input id="areainfo" name="areainfo" style="height:25px;"/></td>  <% }    %>  
 <td align="right">乡镇街道办：</td>
 <!--  <td><select id="qtownshipStreets"  class="easyui-combobox" name="qtownshipStreets" style="width:154px;"></select></td>-->
 <td><input id="qtownshipStreets"  name="qtownshipStreets" style="height:25px;" /></td>
 <td></td>
  <td></td>
   <td>

		<!-- 	<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
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
       <table id="nywcqtt"></table>
   </div>  
        
    </div>  
</div> 

</body>
</html>