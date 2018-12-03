<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserRinghtInfo,com.zytx.init.GlobalFunction" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全服务平台</title>
<!--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"> -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
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

boolean flag =false;
UserRinghtInfo userRinghtInfo =null;

if(userId>0){
	String sql ="select  isnull(tr.ywtx,0) as ywtx from TwoCodeUserInfo  t left join TwoCodeRightsTable tr on t.id =tr.userId where t.id = ?";
	userRinghtInfo =UserRinghtInfo.findFirstBySql(UserRinghtInfo.class, sql, new Object[] {userId});
	if(userRinghtInfo != null){
		if(userRinghtInfo.getYwtx()==1)
			flag=true; //有权利查看	
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

	 var inputWidth = $('#registNumber').width(); 
	 $("#areainfo").css("width", inputWidth);   
	 $("#qtownshipStreets").css("width", inputWidth);
	 $("#is_Process").css("width", inputWidth);

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

	     /*
	  comba =$('#areainfo').combobox({
			 onSelect: function (record) {
			 combt.combobox({
	     url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.value),
	     valueField: 'id',
	     textField: 'companyName'
	 }).combobox('clear');

		     }
			});
			*/
	<%if((role == 1 || role ==2)||(role == 22 || role ==23)) { %>  
		$('#areainfo').combobox({   
			      url:'/tcweb/elevator/areaInfoList',   
			      valueField:'area',   
			      textField:'area'  
			  });  

			  comba =$('#areainfo').combobox({
					 onSelect: function (record) {
				     combt.combobox({
			 //    url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.areaCode),
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

	alarmwin = $('#alarm-window').window({  closed:true,draggable:false,modal:true,onClose:function(){
		$('#alarmImg').empty();
		$('#alarmPoint').empty(); 
	    } });
	
	grid=$('#alarmtt').datagrid({
	    title:'报警信息列表',
	    fitColumns:true,
 	    striped:true,
	    pageSize:25,
	    pageList:[15,25,30,35,40],
	    url:'/tcweb/alarm/alarmlist',
	    queryParams:{},
	    columns:[[
	        {field:'registNumber',align:'center',halign:'center',title:'电梯编号',width:$(this).width() * 0.076,formatter: function(value,rec,index) {
            	<% if("1".equals(cityName)){ %>
                return "N"+value;
                <% } else {%>
                return value;
                <% }%>
           }},
	        {field:'address',align:'left',halign:'center',title:'地址',width:$(this).width() * 0.076},
	        {field:'buildingName',align:'left',halign:'center',title:'楼盘名称',width:$(this).width() * 0.076},
	        {field:'warnTime',align:'center',title:'报警时间',width:$(this).width() * 0.076,formatter: function(value,rec,index) {
                if(value!="")
                    return value.substring(0,16);
		         }},
		    {field:'process_Timer',align:'center',title:'处理时间',width:$(this).width() * 0.076,formatter: function(value,rec,index) {
		                if(value!='' && value != null) 
		                  return value.substring(0,16);
				         }},
	        {field:'area',align:'center',title:'行政区划',width:$(this).width() * 0.076},
	        {field:'jdbCompanyName',align:'left',halign:'center',title:'街道办',width:$(this).width() * 0.076},
	        {field:'userName',align:'center',halign:'center',title:'维保人员',width:$(this).width() * 0.076},
	        {field:'telephonemobile',align:'center',halign:'center',title:'维保人员手机',width:$(this).width() * 0.076},
		    {field:'warnTelephone',align:'center',halign:'center',title:'报警电话',width:$(this).width() * 0.076},
	        {field:'is_Process',align:'center',title:'是否处理',formatter: function(value,rec,index) {
                 if(value==0)
                     return "未处理";
                 else
                     return "已处理";
		         }},
		     {field:'userName',align:'center',halign:'center',title:'处理人',width:$(this).width() * 0.076}, 
		     {field:'process_Remark',align:'left',halign:'center',title:'备注',width:$(this).width() * 0.076},
		     {field:'"详情"',align:'center',title:'详情',formatter: function(value,rec,index) {
	        	 var registNumber = ''+rec.registNumber;
	        	 var startTime = rec.warnTime.substring(0,16);
	        	 var picDataType  =rec.picDataType;
	        	 var map_X =rec.map_X;
	        	 var map_Y =rec.map_Y;
	        	 var map_X0 =rec.map_X0;
	        	 var map_Y0 =rec.map_Y0;
	  		//	 return "<img src='<%=request.getContextPath()%>/images/yulan.png' alt='查看' style='cursor:hand;' onclick='openAlarmDetail("+"\""+registNumber+"\""+","+picDataType+","+"\""+startTime+"\""+","+"\""+map_X+"\""+","+"\""+map_Y+"\""+","+"\""+map_X0+"\""+","+"\""+map_Y0+"\""+")'/>";
	        	 return '<a href="#" onclick="openAlarmDetail('+'\''+registNumber+'\''+','+picDataType+','+'\''+startTime+'\''+','+'\''+map_X+'\''+','+'\''+map_Y+'\''+','+'\''+map_X0+'\''+','+'\''+map_Y0+'\''+')"><i class="fa fa-tasks fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
	  	  	              
		         }}	
		    
	    ]],
	    pagination:true,
	    singleSelect:true,
	    toolbar:[
        <% if(role==22 || role==23){%>
	     	    {
	     	        text:'删除',
	     	        iconCls:'icon-cut',
	     	        handler:function(){
	     	    	 var row = grid.datagrid('getSelected'); 
	     	    	if(row){
	     		    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
	     		    	 jQuery.post('/tcweb/alarm/alarmDelete',
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
	     	    }
	     	   <% }%>
	     	    ]	    
	});	
	$('#alarmtt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
 	  	  
}
);

function clearQuery(){
	$('#registNumber').attr("value","");
	$('#is_Process').combobox('select','2');
	$('#buildingName').attr("value","");
	$('#address').attr("value","");
	$("#qstartTime").datebox("setValue","");  
	$("#qendTime").datebox("setValue","");  
	 <%if(role !=10 && role != 11){ %> 
	$('#areainfo').combobox('clear');
	 <% } %>
	$('#qtownshipStreets').combobox('clear'); 
}

function query(){    
	var registNumber=$('#registNumber').attr("value");
	var buildingName=$('#buildingName').attr("value");  
	var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue"); 
	var area = "";
	  <%if(role !=10 && role != 11){ %>
	var area =$('#areainfo').combobox('getValue'); 
	 <% } %>    
	var address =$('#address').attr("value");     
    var qtownshipStreets =$('#qtownshipStreets').combobox('getValue'); 
    if(qtownshipStreets==""){
    	qtownshipStreets = 0;
        }
	var is_Process =$('#is_Process').combobox('getValue'); 
	
     grid.datagrid("options").url='/tcweb/alarm/query';
     grid.datagrid("options").queryParams={'registNumber':registNumber,'is_Process':is_Process,'buildingName':buildingName,'address':address,'area':area,'qstartTime':qstartTime,'qendTime':qendTime,'townshipStreets':qtownshipStreets};
     
    $('#alarmtt').datagrid('reload');
	}

function openAlarmDetail(registNumber,picDataType,startTime,map_X,map_Y,map_X0,map_Y0){ 
	alarmwin.window('open');
	var alarmimg="";
	if(picDataType>0){
		if(picDataType==1)
	      alarmimg ="<iframe id='alarmDetail' src='imageList.jsp?registNumber="+registNumber+"&startTime="+startTime+"&picDataType="+picDataType+"' width='100%' height='100%' scrolling='no' ></iframe>";
	    else
	      alarmimg ="<iframe id='alarmDetail' src='imageList3.jsp?registNumber="+registNumber+"&startTime="+startTime+"&picDataType="+picDataType+"' width='95%' height='100%' scrolling='no' ></iframe>";
		    
	}
	else
	alarmimg ='<img height="480" align="center" style="width:100%" src="<%=request.getContextPath()%>'+'/servlet/ywImage.jpg?registNumber='+registNumber+'&startTime='+startTime+'"/>';
	
	document.all.alarmImg.innerHTML=alarmimg;
	showYwPointMap(map_X,map_Y,map_X0,map_Y0);
}

function showYwPointMap(map_X,map_Y,map_X0,map_Y0){ 
	$('#alarmPoint').append("<iframe src='mapinfo.jsp?map_x="+map_X0+"&map_y="+map_Y0+"&map_x0="+map_X+"&map_y0="+map_Y+"' width='100%' height='100%' scrolling='no'></iframe>");
    
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
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">  
 <fieldset id="addDiv" style="margin-left:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
     <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%;">
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
   <% if("1".equals(cityName)){ %>
   <td nowrap>N<input id="registNumber" name="registNumber"  class="easyui-validatebox"></input></td>
    <% } else {%>
   <td nowrap><input id="registNumber" name="registNumber"  class="easyui-validatebox"></input></td>
 <% }%>
   <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="address" name="address" class="easyui-validatebox"></input></td>
   <td align="right" nowrap>楼盘名称：</td> 
   <td nowrap><input id="buildingName" name="buildingName"  class="easyui-validatebox"></input></td>
   </tr>
   <tr>
    <%if(role !=10 && role != 11){ %>
   <td align="right"  nowrap>行政区划：</td>
   <td> 
  <!--  <select id="areainfo"   name="areainfo" style="width:152px;" onchange="getCompanyListByArea()"> -->
 <!--  <select id="areainfo"   name="areainfo" style="width:152px;">
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
</select> -->
<input id="areainfo" name="areainfo" style="height:25px;"/>
</td>
  <% } %> 
  <td align="right">乡镇街道办：</td>
 <td><select id="qtownshipStreets"  class="easyui-combobox" name="qtownshipStreets" style="height:25px;"></select></td>
 <td nowrap align="right">是否处理：</td>
   <td> <select id="is_Process"  class="easyui-combobox" name="is_Process" style="height:25px;">
    <option value="2">全部</option>
    <option value="0">未处理</option>
    <option value="1">已处理</option>
</select></td>
</tr>
<tr><td  nowrap align="right">报警时间起：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime"  data-options="editable:false" style="height:25px;"></input></td>
   <td align="right" nowrap>止：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime"  data-options="editable:false" style="height:25px;"></input></td>
   <td></td>
   <td></td>
<td>
	<!-- 		<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
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
       <table id="alarmtt"></table>
   </div>  
        
    </div>  
</div> 
   <div id="alarm-window" title="报警详细信息" style="width:780px;height:500px;">
 <div style="both:clear">
 <div id="alarmImg"  style="overflow:hidden;width:49%;height:425px;float:left;border:1px solid #000;"></div>
 <div id="alarmPoint" style="overflow:hidden;width:50%;height:425px;float:right;border:1px solid #00f;"></div>
 </div> 
 </div>
</body>
</html>