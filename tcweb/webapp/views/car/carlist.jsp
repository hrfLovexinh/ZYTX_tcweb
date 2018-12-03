<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>车载系统</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script src="<%=request.getContextPath()%>/scripts/My97DatePicker4/WdatePicker.js"></script>
<% 
String carnum = request.getParameter("carnum");
System.out.println("carnum----"+carnum);

%>
<script type="text/javascript">
$(function(){
	$('#btn-save,#btn-cancel').linkbutton(); 
	win = $('#car-window').window({  closed:true,draggable:false }); 
	form = win.find('form');
	
	var carnum='<%=carnum%>';
	if(carnum!='null'){ 
		$('#carnum').attr("value",carnum);
		grid=$('#tt').datagrid({
		    title:'车辆记录列表',
		    pageSize:15,
		    pageList:[15,20,25,30,35,40],
		    url:'/czweb/car/query',		
            queryParams:{'carnum':carnum,'dev_id':"",'simnum':"",'dev_type':"",'qy_name':"",'car_type':"",'car_color':"",'person':"",'phone':""},
		    columns:[[
		  	        {field:'carnum',title:'车牌号',width:60},
		  	        {field:'dev_id',title:'设备ID',width:110},
		  	        {field:'qy_name',title:'所属部门',width:70},
		  	        {field:'simnum',title:'通讯卡号',width:110},
		  	        {field:'dev_type',title:'终端类型',width:70},
		  	        {field:'car_type',title:'车辆类型',width:70},
		  	        {field:'car_color',title:'车身颜色',width:55},
		  	        {field:'carnum_color',title:'车牌颜色',width:55},
		        {field:'null',title:'操作',width:80,align:'center',
		        	formatter: function(value,rec,index) {
		  	        	var carnum = rec.data_id;//获取属性值
		  		        //	return "<a href='customerRemind/toadd.do?data_id="+id+"'>详情</a>";
		  			        return "<img src='<%=request.getContextPath()%>/images/unfold.gif' alt='查看' style='cursor:hand;' onclick='openCarinfoDetail("+carnum+")'/>";
		  	            }}
		    ]],
		    pagination:true
		});	
		$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  
	}
	else{ 
	grid=$('#tt').datagrid({
	    title:'车辆记录列表',
	    pageSize:15,
	    pageList:[15,20,25,30,35,40],
	    url:'/czweb/car/carlist',
	    queryParams:{},
	    columns:[[
	        {field:'carnum',title:'车牌号',width:60},
	        {field:'dev_id',title:'设备ID',width:110},
	        {field:'qy_name',title:'所属部门',width:70},
	        {field:'simnum',title:'通讯卡号',width:110},
	        {field:'dev_type',title:'终端类型',width:70},
	        {field:'car_type',title:'车辆类型',width:70},
	        {field:'car_color',title:'车身颜色',width:55},
	        {field:'carnum_color',title:'车牌颜色',width:55},
	       {field:'null',title:'操作',width:70,align:'center',
	        	formatter: function(value,rec,index) {
	        	var carnum = rec.data_id;//获取属性值  
	        //	return "<a href='customerRemind/toadd.do?data_id="+id+"'>详情</a>";
		        return "<img src='<%=request.getContextPath()%>/images/unfold.gif' alt='查看' style='cursor:hand;' onclick='openCarinfoDetail("+carnum+")'/>";
		    
            }}
	    ]],
	    pagination:true
	});	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  
  }
});

function clearQuery(){
	$('#dev_id').attr("value","");
	$('#carnum').attr("value","");
	$('#simnum').attr("value","");
	$('#dev_type').attr("value","");
	$('#qy_name').attr("value","");
	$('#car_type').attr("value","");
	$('#car_color').attr("value","");
	$('#person').attr("value","");
	$('#phone').attr("value","");	
}

function query(){  
	var dev_id=$('#dev_id').attr("value");
    var carnum=$('#carnum').attr("value");
    var simnum=$('#simnum').attr("value");
    var dev_type=$('#dev_type').attr("value");
    var qy_name=$('#qy_name').attr("value");
    var car_type=$('#car_type').attr("value");
    var car_color=$('#car_color').attr("value");
    var person=$('#person').attr("value");
    var phone=$('#phone').attr("value");
 //   alert('----'+$('#tt').datagrid.attr("value"));
  //  $('#tt').datagrid.queryParams={'carnum':carnum,'dev_id':dev_id,'startTime':startTime,'endTime':endTime};
 //  $('#tt').datagrid.attr($('#test').datagrid('options').url,'/czweb/image/query');
     grid.datagrid("options").url='/czweb/car/query';
     grid.datagrid("options").queryParams={'carnum':carnum,'dev_id':dev_id,'simnum':simnum,'dev_type':dev_type,'qy_name':qy_name,'car_type':car_type,'car_color':car_color,'person':person,'phone':phone};
     
    $('#tt').datagrid('reload');
	}
	

	
function openCarinfoDetail(id){ 
	win.window('open');
//	win.window('options').draggable=false; 
	form.form('load', '/czweb/car/edit/'+id);
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
	
/*	$.post("/czweb/image/findImagePath",{"id":id},
			  function(data){
		     $('#image_path').attr("src",data);   
		         },"text");	 
	*/
}

function closeWindow(){ 
	win.window('close');
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
<body>
<div region="north" style="background:#fafafa;color:#2d5593;height:40px;border-bottom: 1px solid #66CCFF;">  
 <fieldset id="addDiv" style="width: 100%;"><legend>查询条件</legend>
    
     <table> 
     <tr>      
   <td align="right" nowrap>车牌号：</td> 
   <td nowrap><input id="carnum" name="carnum" size="12" class="easyui-validatebox"></input></td>
   <td nowrap>车辆类型：</td> 
   <td nowrap><input id="car_type" name="car_type" size="12" class="easyui-validatebox"></input></td>
   <td nowrap>车身颜色：</td> 
   <td nowrap><input id="car_color" name="car_color" size="12" class="easyui-validatebox"></input></td>
   </tr>
   <tr>      
   <td nowrap>所属部门：</td> 
   <td nowrap><input id="qy_name" name="qy_name" size="12" class="easyui-validatebox"></input></td>
   <td align="right" nowrap>联系人：</td> 
   <td nowrap><input id="person" name="person" size="12" class="easyui-validatebox"></input></td>
   <td nowrap>联系电话：</td> 
   <td nowrap><input id="phone" name="phone" size="12" class="easyui-validatebox"></input></td>
   </tr>
   <tr>
    <td nowrap>&nbsp;&nbsp;&nbsp;&nbsp;设备ID：</td> 
   <td nowrap><input id="dev_id" name="dev_id" size="12" class="easyui-validatebox"></input></td>
   <td nowrap>通讯卡号：</td> 
   <td nowrap><input id="simnum" name="simnum" size="12" class="easyui-validatebox"></input></td>
    <td nowrap>终端类型：</td> 
   <td nowrap><input id="dev_type" name="dev_type" size="12" class="easyui-validatebox"></input>
  
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
        
          <div style="margin-top:1px;">  
       <table id="tt"></table>
   </div>  
        
    </div>  
</div> 
<div id="car-window" title="详细信息" style="width:750px;height:470px;"> 
  <div style="padding:20px 20px 40px 80px;">   
  <form method="post">    
  <table border="0">    
   <tr>      
   <td>车牌号：</td>      
   <td><input id="carnuminfo" name="carnum" style="border:0;margin:0;"></input></td>  
   <td>设备号：</td>      
   <td><input id="dev_idinfo" name="dev_id" style="border:0"></input></td>    
   </tr>
   <tr>      
   <td>所属部门：</td>      
   <td><input id="qy_nameinfo" name="qy_name" 

style="border:0;margin:0;"></input></td>  
   <td>&nbsp;</td>      
   <td>&nbsp;</td>    
   </tr>
   <tr>      
   <td>联系人：</td>      
   <td><input id="personinfo" name="person" style="border:0;margin:0;"></input></td>  
   <td>联系电话：</td>      
   <td><input id="phoneinfo" name="phone" style="border:0;margin:0;"></input></td>    
   </tr>
   <tr>      
   <td>通讯卡号：</td>      
   <td><input id="simnuminfo" name="simnum" 

style="border:0;margin:0;"></input></td>  
   <td>终端类型：</td>      
   <td><input id="dev_typeinfo" name="dev_type" 

style="border:0"></input></td>    
   </tr>
   <tr>      
   <td>车辆类型：</td>      
   <td><input id="car_typeinfo" name="car_type" 

style="border:0;margin:0;"></input></td>  
   <td>车牌颜色：</td>      
   <td><input id="carnum_colorinfo" name="carnum_color" 

style="border:0"></input></td>    
   </tr>
   <tr>      
   <td>车身颜色：</td>      
   <td><input id="car_colorinfo" name="car_color" 

style="border:0;margin:0;"></input></td>  
   <td>经度：</td>      
   <td><input id="longitudeinfo" name="longitude" 

style="border:0"></input></td>    
   </tr>
   <tr>      
   <td>纬度：</td>      
   <td><input id="latitudeinfo" name="latitude" 

style="border:0;margin:0;"></input></td>  
   <td>方向：</td>      
   <td><input id="angleinfo" name="angle" style="border:0"></input></td>    
   </tr>
   <tr>      
   <td>速度：</td>      
   <td><input id="speedinfo" name="speed" 

style="border:0;margin:0;"></input></td>  
   <td>时间：</td>      
   <td><input id="gps_timeinfo" name="gps_time" 

style="border:0"></input></td>    
   </tr>
     <tr>
     <td align="center" colspan="4">
       <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">返回</a> 
     </td>
     </tr>
   </table>   
   </form> 
    </div> 
   </div>
   
   <!--  
    <div style="text-align:center;padding:5px;">    
    </div> 
    </div> -->
</body>
</html>