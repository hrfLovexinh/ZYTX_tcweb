<%@ page import="com.zytx.models.UserInfo" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>电梯安全公共服务平台</title>
<!--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/> -->
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=6Kx0GQ8wm05QPnrnpoy9IvfLmDxNMxc0"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">
<% 
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
	 var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	 if(r==null)
	 return false; 
	 var d= new Date(r[1], r[3]-1, r[4]); 
	 return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
	 }

var opt =0; //0:增加 ；1：编辑

$(function(){
	$.ajaxSetup ({
	    cache: false 
	});

	

	$('#btn-save,#btn-cancel').linkbutton(); 
	
	grid=$('#tt').datagrid({
	    title:'电梯坐标统计',
	    singleSelect:true,
	    striped:true,
	    pageSize:25,
	    pageList:[15,25,30,35,40],
	    url:'',
	    columns:[[
			{field:'registNumber',align:'center',halign:'center',title:'电梯编号',width:200},
			{field:'registCode',align:'center',halign:'center',title:'注册编号',width:200},
			{field:'address',align:'center',halign:'center',title:'电梯地址',width:250},
			{field:'1',align:'center',halign:'center',title:'正式库坐标',width:150,formatter:function(value,rec,index){
				if(rec.emap_X==null || rec.emap_X=='' || rec.emap_X.substring(0,1)=='0' || rec.emap_Y==null || rec.emap_Y=='' || rec.emap_Y.substring(0,1)=='0') {
					return "<i class='fa fa-minus fa-2x' aria-hidden='true' style='color:red'></i>";
				}else {
					return "<i class='fa fa-check fa-2x' aria-hidden='true' style='color:blue'></i>";
				}
			}},
			{field:'2',align:'center',halign:'center',title:'标签粘贴坐标',width:150,formatter:function(value,rec,index){
				if(rec.dmap_X==null || rec.dmap_X=='' || rec.dmap_X.substring(0,1)=='0' || rec.dmap_Y==null || rec.dmap_Y=='' || rec.dmap_Y.substring(0,1)=='0') {
					return "<i class='fa fa-minus fa-2x' aria-hidden='true' style='color:red'></i>";
				}else {
					return "<i class='fa fa-check fa-2x' aria-hidden='true' style='color:blue'></i>";
				}
			}},
	        {field:'3',align:'center',halign:'center',title:'96933粘贴坐标',width:150,formatter:function(value,rec,index){
				if(rec.pmap_X==null || rec.pmap_X=='' || rec.pmap_X.substring(0,1)=='0' || rec.pmap_Y==null || rec.pmap_Y=='' || rec.pmap_Y.substring(0,1)=='0') {
					return "<i class='fa fa-minus fa-2x' aria-hidden='true' style='color:red'></i>";
				}else {
					return "<i class='fa fa-check fa-2x' aria-hidden='true' style='color:blue'></i>";
				}
			}},
	        {field:'4',align:'center',halign:'center',title:'电梯坐标人工确认',width:150,formatter:function(value,rec,index){
					return "<a href='#' onclick='showMap(" + rec.emap_X + "," + rec.emap_Y + "," + rec.dmap_X + "," + rec.dmap_Y + "," + rec.pmap_X + "," + rec.pmap_Y + ")' title='电梯坐标位置信息'><i class='fa fa-map-marker fa-2x' aria-hidden='true'></i></a>";
			}},
	    ]],
	    pagination:true,
	    toolbar:[],
	});	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
	query();
	//win.window('close');
});


function clearQuery(){
	$('#qcompanyName').attr("value","");
	$('#qaddress').attr("value","");
	$("#filingPerson").attr("value","");
	$("#companyCode").attr("value","");
	
}

function query(){  
	  
    /* var qcompanyName=$('#qcompanyName').attr("value");
    var qaddress=$('#qaddress').attr("value");
    var filingPerson=$('#filingPerson').attr("value"); 
	var companyCode=$('#companyCode').attr("value"); */
     grid.datagrid("options").url='/tcweb/elevator/elevatorCoordinate';
     //grid.datagrid("options").queryParams={'companyName':qcompanyName,'address':qaddress,'filingPerson':filingPerson,'companyCode':companyCode};
     
    $('#tt').datagrid('reload');
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
  padding-left: 15px;
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
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);height:80px;">  
 <fieldset id="addDiv" style="height:100%;margin:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend> 
    <table id="sousuo" style="width:100%">  
     <tr>      
   <td nowrap align="right" style="150">单位名称：</td> 
   <td nowrap><input id="qcompanyName" name="qcompanyName"   class="easyui-validatebox"></input></td>
   <td nowrap align="right" style="150">单位地址：</td> 
   <td nowrap><input id="qaddress" name="qaddress"   class="easyui-validatebox"></input></td>
  
   <td  nowrap align="right" style="150">备案负责人：</td>
   <td><input id="filingPerson" name="filingPerson"   class="easyui-validatebox"></input></td>
   <td align="right" nowrap style="150">单位编码</td>
   <td><input id="companyCode" name="companyCode" class="easyui-validatebox"></input></td>
    <td> 
		<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
		<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>
  </td>
   </tr>
   </table>
  </fieldset>
</div> 
   <div region="center" style="width:100%;">
       
       <table id="tt"></table>
   
</div> 
<div id="car-window" class="easyui-layout" title="单位维保人员" style="width:600px;height:650px;overflow-y:hidden">
		<div id="allmap" style="width:600px;height:650px"></div>  
</div> 
  <script type="text/javascript">
  win = $("#car-window").window({closed:true,draggable:true,resizable:false,modal:true,minimizable:false,collapsible:false,maximizable:false,onClose: function(){
	} });
  function showMap(emap_X,emap_Y,dmap_X,dmap_Y,pmap_X,pmap_Y) {
		// 百度地图API功能
		var map = new BMap.Map("allmap");
		map.centerAndZoom(new BMap.Point(emap_X,emap_Y), 15);  // 初始化地图,设置中心点坐标和地图级别
		//添加地图类型控件
		map.addControl(new BMap.MapTypeControl({
			mapTypes:[
	            BMAP_NORMAL_MAP,
	            BMAP_HYBRID_MAP
	        ]}));	  
		map.setCurrentCity("成都");          // 设置地图显示的城市 此项是必须设置的
		map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
		//添加点
		map.addOverlay(new BMap.Marker(new BMap.Point(emap_X,emap_Y)));
		//alert(dmap_X.toString().substring(0,1));
		if(dmap_X==null || dmap_X=='' || dmap_X.toString().substring(0,1)=='0' || dmap_Y==null || dmap_Y=='' || dmap_Y.toString().substring(0,1)=='0') {
		} else {
			map.addOverlay(new BMap.Marker(new BMap.Point(dmap_X,dmap_Y)));
		}
		if(pmap_X==null || pmap_X=='' || pmap_X.toString().substring(0,1)=='0' || pmap_Y==null || pmap_Y=='' || pmap_Y.toString().substring(0,1)=='0') {
		} else {
			map.addOverlay(new BMap.Marker(new BMap.Point(pmap_X,pmap_Y)));
		}
		map.addEventListener("click", showInfo);
		$('#car-window').window('open');
	}
  function showInfo(e){
		alert(e.point.lng + ", " + e.point.lat);
	}

</script>
</body>
</html>