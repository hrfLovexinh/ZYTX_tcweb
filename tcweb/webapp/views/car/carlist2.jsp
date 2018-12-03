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
var opt =0; //0:增加 ；1：编辑
$(function(){

	$.ajaxSetup ({
	    cache: false 
	});
	
	comb =$('#qy_nameinfo').combobox({
		url:'/czweb/car/getQyNameList',
	    valueField:'qy_id',
	    textField:'qy_name'
	   	
	});
	
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
		    url:'/czweb/image/query',
		    queryParams:{'carnum':carnum,'dev_id':"",'startTime':"",'endTime':""},
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
	    pagination:true,
	    toolbar:[{
	        text:'新增',
	        iconCls:'icon-add',
	        handler:function(){
	    	win.window('open');  
	    	form.form('clear');
	    	form.url ='/czweb/car/add';	
	    	colseWinDetail();	 
	        }
	    },{
	        text:'删除',
	        iconCls:'icon-cut',
	        handler:function(){
	    	 var row = grid.datagrid('getSelected'); 
	    	 if(row){
	    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
	    	 jQuery.post('/czweb/car/delete',
	    	    	 {'data_id':row.data_id},
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
	    },'-',{
	        text:'修改',
	        iconCls:'icon-edit',
	        handler:function(){
	    	var row = grid.datagrid('getSelected');   
	    	if (row){   
	    		 win.window('open');   
	    		 colseWinDetail();
	    		 form.form('load', '/czweb/car/edit/'+row.data_id);
	    		 form.url = '/czweb/car/update/'+row.data_id; 
	    		 opt =1;
	    	 } else {  
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。' 
	    		 });   
	    	 }  
	        }
	    }]
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
	
	form.form('load', '/czweb/car/edit/'+id);
	showWinDetail();
	
/*	$.post("/czweb/image/findImagePath",{"id":id},
			  function(data){
		     $('#image_path').attr("src",data);   
		         },"text");	 
	*/
	
}

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
	$('#table2').hide();

	$('#qy_nameinfo').combobox('enable'); 
}

function saveCar(){ 
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){
     //   return $(this).form('validate');
		var carnum=$('#carnuminfo').attr("value"); 
		var qyName=$('#qy_nameinfo').combobox('getValue'); 
		var devid= $('#dev_idinfo').attr("value"); 
		var simnum =$('#simnuminfo').attr("value");
		
        var qy_id2 =$('#qy_id2').attr("value");
		
		if(opt==1){
			$('#qy_nameinfo').combobox('setValue',qy_id2);
		//	alert("----qyName---"+$('#qy_nameinfo').combobox('getValue'));
			opt=0;
			}
		
		if(!carnum){
		  $.messager.alert('操作失败','车牌号不能为空','error');
		  return false;
			}
		if(!qyName){
		  $.messager.alert('操作失败','所属部门不能为空','error');
		  return false;
			}
		if(!devid){
		  $.messager.alert('操作失败','设备号不能为空','error');
		  return false;
				}
		if(!simnum){
		  $.messager.alert('操作失败','通讯卡号不能为空','error');
		  return false;
					}
		return true;  
	//	return false;
    },
		  
		success:function(data){   
		eval("data="+"'"+data+"'"); 
		if("exist"==data){
			$.messager.alert('操作失败','已经存在该车牌号，不能重复添加','error');
			} 
		else if("success"==data){
		//	$.messager.alert("操作成功",'谢谢');    
		 $.messager.show({   
	    			 title:'提示信息',
	    			 timeout:1000,
	    			 msg:'操作成功，谢谢。' 
	    		 }); 
			grid.datagrid('reload');
			win.window('close');   
			}
		else{
			$.messager.alert('操作失败','添加车辆失败','error');
			}
		} 
	});
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
  <table>    
   <tr>      
   <td width="70">车牌号：</td>      
   <td><input id="carnuminfo" name="carnum"></input><span class="fontShow"><font color="red">*</font></span></td>  
   <td>所属部门：</td>      
  <!--  <td><input id="qy_nameinfo" name="qy_name"></input><span class="fontShow"><font color="red">*</font></span></td>   -->  
   <td><select id="qy_nameinfo"  class="easyui-combobox" name="qy_name" style="width:110px;"> 
</select><span class="fontShow"><font color="red">*</font></span></td>   
   </tr>
   <tr> 
   <td>设备ID：</td>      
   <td><input id="dev_idinfo" name="dev_id"></input><span class="fontShow"><font color="red">*</font></span></td>     
  <td>车辆类型：</td>      
   <td><input id="car_typeinfo" name="car_type"></input></td>     
   </tr>
    <tr>      
   <td>联系人：</td>      
   <td><input id="personinfo" name="person"></input></td>  
   <td>联系电话：</td>      
   <td><input id="phoneinfo" name="phone"></input></td>    
   </tr>
   <tr>      
   <td>通讯卡号：</td>      
   <td><input id="simnuminfo" name="simnum"></input><span class="fontShow"><font color="red">*</font></span></td>  
   <td>终端类型：</td>      
   <td><input id="dev_typeinfo" name="dev_type"></input></td>    
   </tr>
   <tr>      
   <td>车身颜色：</td>      
   <td><input id="car_colorinfo" name="car_color"></input></td>   
   <td>车牌颜色：</td>      
   <td><input id="carnum_colorinfo" name="carnum_color"></input></td>    
   </tr>
   </table>
   <table id="table2">
   <tr>        
   <td width="70">经度：</td>      
   <td><input id="longitudeinfo" name="longitude"></input></td>
   <td style="padding-left:23px">纬度：</td>      
   <td><input id="latitudeinfo" name="latitude"></input></td>   
   </tr>
   <tr>      
   <td>速度：</td>      
   <td><input id="speedinfo" name="speed"></input></td>  
   <td style="padding-left:23px">方向：</td>      
   <td><input id="angleinfo" name="angle"></input></td>    
   </tr>
   <tr>      
   <td>时间：</td>      
   <td><input id="gps_timeinfo" name="gps_time"></input></td> 
  <!--   <td>&nbsp;</td> -->
  <td><input type="hidden" id="qy_id2" name="qy_id2"></input></td>
   <td>&nbsp;</td>    
   </tr>
   </table>
   <table width=70%>
    <tr>
    <td align="center">
      <a href="javascript:void(0)" onclick="saveCar()" id="btn-save" icon="icon-save">保存</a>  
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
</body>
</html>