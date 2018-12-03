<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">  -->
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">
 <style>
    .datagrid-header-row td{background-color:rgb(226,237,255);color:#0E2D5F;}
  </style>
<% 
String cityName = GlobalFunction.cityName;
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int role=0;
int isliulan = 0;
int userId=0;
if(userinfo!=null){
 role = userinfo.getRole(); 
 userId =userinfo.getId();
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
	    userId = user.getId();
		}
		 else
			    response.sendRedirect(request.getContextPath() +"/index.jsp");
			 
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
	 var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	 if(r==null)
	 return false; 
	 var d= new Date(r[1], r[3]-1, r[4]); 
	 return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
	 }

	 $(function(){

			$.ajaxSetup ({
			    cache: false 
			});
			
		$('#btn-save,#btn-save2,#btn-save3').linkbutton(); 
		win = $('#eachRecordEditWin').window({closed:true,draggable:false,modal:true });
		winst = $('#st-window').window({  closed:true,draggable:true,modal:false }); 
		form = win.find('form');
		<%  if(role !=10 && role !=11){%>
		 $('#areainfo').combobox({   
		        url:'/tcweb/elevator/areaInfoList',   
		        valueField:'area',   
		        textField:'area'  
		    }); 

		 $('#areainfo2').combobox({   
		        url:'/tcweb/elevator/areaInfoList',   
		        valueField:'area',   
		        textField:'area'  
		    });    
          <%}%>
		  $('#areaInfoEdit').combobox({   
		        url:'/tcweb/elevator/areaInfoList',   
		        valueField:'area',   
		        textField:'area'  
		    });

		  $('#area').combobox({   
		        url:'/tcweb/elevator/areaInfoList',   
		        valueField:'area',   
		        textField:'area'  
		    }); 
		    
		 
		//批量导入
		winDr = $('#car-windowDr').window({  closed:true,draggable:false,modal:true,minimizable:false,collapsible:false,maximizable:false});
		    
	    grid=$('#zhantiett').datagrid({
			    title:'核实数据（电梯标签粘贴数据已上传,但在系统核实中...，双击可补全相应电梯信息）',
			    pageSize:10,
			    pageList:[10,20,40],
			    url:'',
			    queryParams:{},
			    idField:'id',
			    frozenColumns:[[
                    {field:'id',checkbox:true}, 
                    {field:'useNumber',align:'left',halign:'center',title:'内部编号'}, 
                    {field:'registCode',align:'left',halign:'center',title:'注册编号',width:170,styler:function(value,row,index){
                    	 if (row.registCodeCFFlag==1){return 'background-color:#FF7A7A;';} 
                        }}
			    ]], 
			    columns:[[ 
				    {field:'buildingName',align:'left',halign:'center',title:'楼盘名称',width:80},
			        {field:'address',align:'left',halign:'center',title:'地址',width:160},
			        {field:'building',align:'left',halign:'center',title:'栋',width:40},
			        {field:'unit',align:'left',halign:'center',title:'单元',width:50},
			        {field:'registNumber',align:'center',halign:'center',title:'电梯编号',width:80,formatter: function(value,rec,index) {
			        	  <% if("1".equals(cityName)){ %>
                             return "N"+value;
                             <% } else {%>
                             return value;
                             <% }%>   
				        }},
				        <% if(!"0".equals(cityName)){ %>
				     {field:'shibieCode',align:'center',halign:'center',title:'识别码',width:80},  
				        <% }%>
			        {field:'mobileUploadbeizhu',align:'left',halign:'center',title:'上传备注',width:150},
			        {field:'area',align:'center',title:'区域',width:50}
			    ]],
			    nowrap:true,
			    pagination:true,
			    <% if(isliulan == 0){%>
			    <%  if(role ==1 || role ==2){%>
			    toolbar:[{
			        text:'修改',
			        iconCls:'icon-edit',
			        handler:function(){
			    	var row = grid.datagrid('getSelected');   
			    	if (row){ 
			    		loupanEdit();
			    	 } else {  
			    		 $.messager.show({   
			    			 title:'警告',
			    			 msg:'请先选择记录行。' 
			    		 });   
			    	 }  
			        }
			    }
               <% if ("0".equals(cityName)){%>
			    ,'-',{
		            text: '单边入库',
		            iconCls: 'icon-ok',
		            handler: function () {
			    	  var row = grid.datagrid('getSelected'); 
			    	  if(row){
                            dbRuku();
				    	  }
			    	  else{
			    		  $.messager.show({   
				    			 title:'警告',
				    			 msg:'请先选择记录行。' 
				    		 });   
				    	  }
		            }
		        } <% } %>
		        ,'-',{
		            text: '删除',
		            iconCls: 'icon-cut',
		            handler: function () {
			    	  var row = grid.datagrid('getSelected'); 
			    	  if(row){
                            deletezhantieEle();
				    	  }
			    	  else{
			    		  $.messager.show({   
				    			 title:'警告',
				    			 msg:'请先选择记录行。' 
				    		 });   
				    	  }
		            }
		        }
			    ],
			    <% } else {%>
			    toolbar:[{text:''}],
			    <% }%>
			    <% } %>    
			    onDblClickRow :function(rowIndex,rowData){
			    	    $('#eachRecordEditWin').window('open');
			    	    $('#useNumber').attr("value",rowData.useNumber);
			    	    $('#registCode').attr("value",rowData.registCode);
			    	    $('#buildingName').attr("value",rowData.buildingName);
			    	    $('#address').attr("value",rowData.address);
			    	    $('#building').attr("value",rowData.building);
			    	    $('#unit').attr("value",rowData.unit);
			    	    $('#id').attr("value",rowData.id);
			    // 	    $('#area').val(rowData.area);
			     	    $('#area').combobox('select', rowData.area);
			    	    $('#registNumber').attr("value",rowData.registNumber);

			    	   
			    	    
					//    form.form('load', '/tcweb/elevator/ddedit2/'+rowData.id);
					   showpicWinDetail(rowData.registNumber,rowData.subTime2.substring(0,16),rowData.picregistNumber);  
					//    rukuid=rowData.id;   
					//    ruKuValid=rowData.ruKuValid;
						
				    }
			});	
			$('#zhantiett').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,40]});     	  

		grid2=$('#yuanshitt').datagrid({
				    title:'监察网未关联电梯标签的数据',
				    pageSize:10,
				    pageList:[10,20,40],
				    url:'',
				    queryParams:{},
				    idField:'id',
				    frozenColumns:[[
				         {field:'id',checkbox:true},
			             {field:'useNumber',align:'left',halign:'center',title:'内部编号'},
					     {field:'registCode',align:'left',halign:'center',title:'注册编号',width:180},
					     <% if(!"0".equals(cityName)){ %>
					     {field:'shibieCode',align:'center',halign:'center',title:'识别码',width:80},  
					        <% }%>
				    ]], 
				    columns:[[
					    {field:'wgCompanyName',align:'left',halign:'center',title:'使用单位',width:150},
				        {field:'address',align:'left',halign:'center',title:'地址',width:180},
				        {field:'buildingName',align:'left',halign:'center',title:'楼盘',width:80},
				        {field:'area',align:'center',title:'行政区划',width:80}
				    ]],
				    nowrap:true,
				    pagination:true,
				    toolbar:[ {
				        text:'导入',
				        iconCls:'icon-redo',
				        handler:function(){
				        	winDr.window('open');
				        }
				    } ]
				});	
				$('#yuanshitt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,40]});     	  
		    
		grid3=$('#stelelisttt').datagrid({
				    title:'区县质监未粘贴标签的停用电梯列表',
				    pageSize:10,
				    pageList:[10,20,40],
				    url:'',
				    queryParams:{},
				    idField:'id',
				    frozenColumns:[[
				         {field:'id',checkbox:true},
					     {field:'registCode',align:'center',title:'登记编号',width:130},
				    ]], 
				    columns:[[
				        {field:'address',align:'center',title:'地址',width:180},
				        {field:'buildingName',align:'center',title:'楼盘名称',width:80},
				        {field:'area',align:'center',title:'行政区划',width:80}
				    ]],
				    nowrap:true,
				    pagination:true,
				    toolbar:[{text:'关联入库',
			            iconCls: 'icon-ok',
			            handler: function () {
				    	  var row = grid3.datagrid('getSelected'); 
				    	  if(row){
	                            sglRuku();
					    	  }
				    	  else{
				    		  $.messager.show({   
					    			 title:'警告',
					    			 msg:'请先选择要关联入库停用记录行。' 
					    		 });   
					    	  }
			            }}]
				});	
				$('#stelelisttt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,40]});     	  
		    
		});

	  

 function query(){
	 $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');    
	 var address=$('#addressinfo').attr("value");
	 var buildingName=$('#buildingNameInfo').attr("value");
//	 var area=$('#areainfo option:selected').val();
    <%  if(role !=10 && role !=11){%>
     var area =$('#areainfo').combobox('getValue'); 
     <% } %>
    
	 var registCode =$('#registCodeinfo').attr("value"); 
	 var shibieCode = $("#shibieCodeinfo").attr("value");
	 var registNumber = $('#registNumberinfo').attr("value"); 
	 grid.datagrid("options").url='/tcweb/elevator/zhantieddquery';
	 <%  if(role !=10 && role !=11){%>
     grid.datagrid("options").queryParams={'area':area,'address':address,'buildingName':buildingName,'registCode':registCode,'shibieCode':shibieCode,'registNumber':registNumber};
     <% } else {%>
     grid.datagrid("options").queryParams={'address':address,'buildingName':buildingName,'registCode':registCode,'shibieCode':shibieCode,'registNumber':registNumber};
     <% } %>
     $('#zhantiett').datagrid('reload');
	 }

 function clearQuery(){
	$('#addressinfo').attr("value","");	 
	$('#buildingNameInfo').attr("value","");
//	$('#areainfo option:first').attr('selected','selected');
   <%  if(role !=10 && role !=11){%>
    $('#areainfo').combobox('clear');
    <% } %>
	$('#registCodeinfo').attr("value","");
	$("#shibieCodeinfo").attr("value","");
	$("#registNumberinfo").attr("value","");
}

 function query2(){
	 $("#yuanshitt").datagrid('clearSelections').datagrid('clearChecked');
	 var address=$('#addressinfo2').attr("value");
	 var address2=$('#addressinfo3').attr("value");
	 var wgCompanyName=$('#wgCompanyName').attr("value");
 //	 var area=$('#areainfo2 option:selected').val();
   <%  if(role !=10 && role !=11){%>
     var area =$('#areainfo2').combobox('getValue'); 
     <% } %>
	 var registCode =$('#registCodeinfo2').attr("value"); 
	 var shibieCode = $("#shibieCodeinfo3").attr("value");
	 grid2.datagrid("options").url='/tcweb/elevator/yuanshiddquery';
	 <%  if(role !=10 && role !=11){%>
     grid2.datagrid("options").queryParams={'area':area,'address':address,'address2':address2,'wgCompanyName':wgCompanyName,'registCode':registCode,'shibieCode':shibieCode};
     <% } else {%>
     grid2.datagrid("options").queryParams={'address':address,'address2':address2,'wgCompanyName':wgCompanyName,'registCode':registCode,'shibieCode':shibieCode};
     
     <% } %>
     $('#yuanshitt').datagrid('reload');
	 }
 
 function querynj(){
	 $("#yuanshitt").datagrid('clearSelections').datagrid('clearChecked');
	 var address=$('#addressinfo2').attr("value");
	 var buildingName=$('#yuanshibuildingNameinfo').attr("value");
	 var wgCompanyName=$('#wgCompanyName').attr("value");
 //	 var area=$('#areainfo2 option:selected').val();
   <%  if(role !=10 && role !=11){%>
     var area =$('#areainfo2').combobox('getValue'); 
     <% } %>
	 var registCode =$('#registCodeinfo2').attr("value"); 
	 var shibieCode = $("#shibieCodeinfo3").attr("value");
	 grid2.datagrid("options").url='/tcweb/elevator/yuanshiddquery2';
	 <%  if(role !=10 && role !=11){%>
     grid2.datagrid("options").queryParams={'area':area,'address':address,'buildingName':buildingName,'wgCompanyName':wgCompanyName,'registCode':registCode,'shibieCode':shibieCode};
     <% } else {%>
     grid2.datagrid("options").queryParams={'address':address,'buildingName':buildingName,'wgCompanyName':wgCompanyName,'registCode':registCode,'shibieCode':shibieCode};
     
     <% } %>
     $('#yuanshitt').datagrid('reload');
	 }

 function clearQuery2(){
		$('#addressinfo2').attr("value","");
		/* $('#addressinfo3').attr("value",""); */	 
		$('#wgCompanyName').attr("value","");
		$("#yuanshibuildingNameinfo").val("");
		$("#shibieCodeinfo3").attr("value","");
	//	$('#areainfo2 option:first').attr('selected','selected');
	 <%  if(role !=10 && role !=11){%>
	    $('#areainfo2').combobox('clear');
	    <% } %>
		$('#registCodeinfo2').attr("value","");
	}

 function autorelationinfo(){
//	 $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');    
	 grid.datagrid("options").url='/tcweb/elevator/autozhantieddquery';
     $('#zhantiett').datagrid('reload');
     
//     $("#yuanshitt").datagrid('clearSelections').datagrid('clearChecked');
     grid2.datagrid("options").url='/tcweb/elevator/autoyuanshiddquery';
     $('#yuanshitt').datagrid('reload');
	 }


 function autorelationinfo2(){ 
//	 var area=$('#areainfo option:selected').val();
     var area =$('#areainfo').combobox('getValue');
	 grid.datagrid("options").url='/tcweb/elevator/autozhantieddquery3';
	 grid.datagrid("options").queryParams={'area':area};
     $('#zhantiett').datagrid('reload');  
     
    grid2.datagrid("options").url='/tcweb/elevator/autoyuanshiddquery3';
    grid2.datagrid("options").queryParams={'area':area};
    $('#yuanshitt').datagrid('reload');

	 }

 function stautorelationinfo2(){
	 winst.window('open'); 
	 grid.datagrid("options").url='/tcweb/elevator/stautozhantieddquery2';
	// grid.datagrid("options").queryParams={'area':area};
     $('#zhantiett').datagrid('reload');
     

     grid3.datagrid("options").url='/tcweb/elevator/stautoyuanshiddquery2';
  //   grid3.datagrid("options").queryParams={'area':area};
     $('#stelelisttt').datagrid('reload');
	 }


 var registNumberrestr;
 var registCoderestr;
 function dbRuku(){
	 var dzarea = 0;
	 $.messager.confirm('','确定单边入库',function(data){if(data){
     $.messager.progress();
	 var checkedItems = $('#zhantiett').datagrid('getChecked');
	 var names="";  
	 var num1 =0;
	 $.each(checkedItems, function(index, item){
			names=names+item.id+",";
		//	alert(item.id);
			if(item.area == ""){
				  dzarea = 1;
				}
			num1 =num1 + 1;
		}); 

	  if(dzarea == 1){
		   $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');
	       dzarea = 0;
	       $.messager.alert('失败','粘贴数据记录中含有区域为空的记录，请重新选择记录','error');
	       return;
		   }

	 if(names!="")
			names=names.substring(0,names.length-1); 

	 if(num1 > 40){
		 $.messager.alert('失败','选择的粘贴数据记录数不能大于40条','error');
		  return;
		 }
    //dbrelationrukudelevattor
    /*
	 jQuery.post('/tcweb/elevator/dbrelationrukudelevattorRet',
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
		            $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');
		   		}
		   		else{
		   			$.messager.alert('操作失败','操作失败','error');
			    		}
			       }); 	 */
	 registNumberrestr ='';
	 registCoderestr ='';
	 jQuery.post('/tcweb/elevator/dbrelationrukudelevattorRet',
		   	 {'names':names},
		   	 function(data){
		   		 data = eval(data);//POST方法必加，ajax方法自动处理了   
		   		 registNumberrestr =data.registNumberrestr;
		     	 registCoderestr = data.registCoderestr;
		     	 
		         if(registNumberrestr != ''){
		          var registNumberrestrs = registNumberrestr.split(",");
		   		  var registNumberrestrsR ="";
		   		  for(var i =0; i<registNumberrestrs.length; i++){
			   		if(i % 4 == 0 && i != 0)
			   			registNumberrestrsR =registNumberrestrsR+","+registNumberrestrs[i]+"<br>";
			   		else 
			   			registNumberrestrsR =registNumberrestrsR+","+registNumberrestrs[i];
			   		  }
		   		    registNumberrestrsR =registNumberrestrsR.substring(1,registNumberrestrsR.length);
		   		     $.messager.alert('二维码编号重复',registNumberrestrsR,'error'); 
		         }
		      
		         if(registCoderestr != ''){
		        	 var registCoderestrs = registCoderestr.split(",");
		        	 var registCoderestrsR ="";
		        	 for(var i =0; i<registCoderestrs.length; i++){	
					   	registCoderestrsR =registCoderestrsR+","+registCoderestrs[i]+"<br>";
		        	 }
		        	 registCoderestrsR =registCoderestrsR.substring(1,registCoderestrsR.length);  //alert('注册代码重复'+registCoderestrsR);
		        	 $.messager.alert('注册代码重复',registCoderestrsR,'error');
		         }
		         $.messager.progress('close'); 
	        	 if(registCoderestr =='' && registNumberrestr =='')
	        		 $.messager.show({   
	    				 title:'提示信息',
	    				 timeout:1000,
	    				 msg:'操作成功，谢谢。' 
	    			 });  

	        	 grid.datagrid('reload');
		         $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');	 
			   }, 'json');
		   		 
	 }}
       );
	 }

 function relationinfo(){ 
	 var checkedItems = $('#zhantiett').datagrid('getChecked');
	 var names="";  
	 var num1 =0;
	 var num2 =0;
	 var zarea = 0;
	 $.each(checkedItems, function(index, item){
			names=names+item.id+",";
			if( item.area == "" ){
				  zarea = 1;
				}
		//	alert(item.id);
			num1 =num1 + 1;
		}); 
   if(zarea == 1){
	   $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');
       $("#yuanshitt").datagrid('clearSelections').datagrid('clearChecked'); 
       zarea = 0;
       $.messager.alert('失败','粘贴数据记录中含有区域为空的记录，请重新选择记录','error');
       return;
	   }
   
	
  if(names!="")
		names=names.substring(0,names.length-1); 

  var checkedItems2 = $('#yuanshitt').datagrid('getChecked');
	 var names2="";  
	 $.each(checkedItems2, function(index, item){
			names2=names2+item.id+",";
			//alert(item.id);
			num2 =num2 + 1;
		}); 
	
if(names2 !="")
		names2=names2.substring(0,names2.length-1); 



 if(num1 > 40){
	 $.messager.alert('失败','选择的粘贴数据记录数不能大于40条','error');
	  return;
	 }

 if(num2 > 40){
	 $.messager.alert('失败','选择的原始数据记录条数不能大于40条','error');
	  return;
	 }
   
 if(num1 > num2){
  $.messager.alert('失败','粘贴数据数目必须小于或等于原始数据数目','error');
  return;
	 }
 if(num1 == 0){

	 $.messager.alert('失败','请选择粘贴数据记录','error');
	  return;
	 }

jQuery.post('/tcweb/elevator/relationrukudelevattor',
   	 {'names':names,'names2':names2},
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
            grid2.datagrid('reload');
            $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');
            $("#yuanshitt").datagrid('clearSelections').datagrid('clearChecked');
   		}
   		else{
   			$.messager.alert('操作失败','操作失败','error');
	    		}
	       }); 
 
 }
 

function sglRuku(){
	 $.messager.confirm('','确定关联入库',function(data){if(data){
	 var checkedItems = $('#zhantiett').datagrid('getChecked');
	 var names="";  
	 var num1 =0;
	 var num2 =0;
	 $.each(checkedItems, function(index, item){
			names=names+item.id+",";
		//	alert(item.id);
			num1 =num1 + 1;
		}); 
	
  if(names!="")
		names=names.substring(0,names.length-1); 

  var checkedItems2 = $('#stelelisttt').datagrid('getChecked');
	 var names2="";  
	 $.each(checkedItems2, function(index, item){
			names2=names2+item.id+",";
			//alert(item.id);
			num2 =num2 + 1;
		}); 
	
if(names2 !="")
		names2=names2.substring(0,names2.length-1); 

 if(num1 > 40){
	 $.messager.alert('失败','选择的粘贴数据记录数不能大于40条','error');
	  return;
	 }

 if(num2 > 40){
	 $.messager.alert('失败','选择的停用数据记录条数不能大于40条','error');
	  return;
	 }

 if(num1 > num2){
  $.messager.alert('失败','粘贴数据数目必须小于或等于停用数据数目','error');
  return;
	 }
 if(num1 == 0){

	 $.messager.alert('失败','请选择粘贴数据记录','error');
	  return;
	 }

jQuery.post('/tcweb/elevator/strelationrukudelevattor',
   	 {'names':names,'names2':names2},
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
            grid3.datagrid('reload');
            $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');
            $("#stelelisttt").datagrid('clearSelections').datagrid('clearChecked');
   		}
   		else{
   			$.messager.alert('操作失败','操作失败','error');
	    		}
	       }); 
	 }});
}

function loupanEdit(){	 
	 $('#loupanEditWin').window('open');
	 
}

function saveloupan(){
	var checkedItems = $('#zhantiett').datagrid('getChecked');  
	var names="";
	 $.each(checkedItems, function(index, item){
			names=names+item.id+",";
		}); 
	
	 if(names!="")
			names=names.substring(0,names.length-1); 

	 var buildingNameInfoEdit=$('#buildingNameInfoEdit').attr("value");

	 jQuery.post('/tcweb/elevator/realationLouPanEdit',
	    	 {'names':names,'buildingName':buildingNameInfoEdit},
	    	 function(data){
	    		eval("data="+"'"+data+"'");  
	    		if("success"==data){
	    		//	$.messager.alert("操作成功",'谢谢');
	    		 $.messager.show({   
			 title:'提示信息',
			 timeout:1000,
			 msg:'操作成功，谢谢。' 
		 });  	
	    		
	    		$('#loupanEditWin').window('close');
 	            grid.datagrid('reload');
	    		}
	    		else{
	    			$.messager.alert('操作失败','操作失败','error');
    	    		}
    	       });
	    $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');
}

function savearea(){
	var checkedItems = $('#zhantiett').datagrid('getChecked');  
	var names="";
	 $.each(checkedItems, function(index, item){
			names=names+item.id+",";
		}); 
	
	 if(names!="")
			names=names.substring(0,names.length-1); 

//	 var areaEdit=$('#areaInfoEdit option:selected').val();
     var areaEdit=$('#areaInfoEdit').combobox('getValue'); 
    
     if(""==areaEdit){
    	 $.messager.alert('失败','行政区划不能为空','error');
    	 return ;
         }

	 jQuery.post('/tcweb/elevator/realationAreaEdit',
	    	 {'names':names,'area':areaEdit},
	    	 function(data){
	    		eval("data="+"'"+data+"'");  
	    		if("success"==data){
	    		//	$.messager.alert("操作成功",'谢谢');
	    		 $.messager.show({   
			 title:'提示信息',
			 timeout:1000,
			 msg:'操作成功，谢谢。' 
		 });  	
	    		
	    		$('#loupanEditWin').window('close');
 	            grid.datagrid('reload');
	    		}
	    		else{
	    			$.messager.alert('操作失败','操作失败','error');
    	    		}
    	       });
	    $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');
}

function eachRecordsave(){
	form.url ='/tcweb/elevator/ddeachRecordupdate';
	form.form('submit', {  
		url:form.url,
		success : function(data) {
			eval("data=" + "'" + data + "'");  
			if("success" == data) {   
			$.messager.show( {
				title : '提示信息',
				timeout : 1000,
				msg : '操作成功，谢谢。'
			});
			grid.datagrid('reload');
			win.window('close');
		} else {
			$.messager.alert('操作失败', '操作失败', 'error');
		}
	}
		});
}

function showpicWinDetail(registNumber,subTime,picregistNumber){
	//展示上传图片
	 if(picregistNumber.length == 6){
	  $('#img4').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+picregistNumber+"&subTime="+subTime+"&index="+1);
	  $('#img5').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+picregistNumber+"&subTime="+subTime+"&index="+2);	
	  $('#img6').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+picregistNumber+"&subTime="+subTime+"&index="+3);		
	  }
	 else{
	 $('#img4').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+registNumber+"&subTime="+subTime+"&index="+1);
	 $('#img5').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+registNumber+"&subTime="+subTime+"&index="+2);	
	 $('#img6').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+registNumber+"&subTime="+subTime+"&index="+3);	
	 }
}

function  deletezhantieEle(){  
		$.messager.confirm('','确定要删除',function(data){if(data){
		 var checkeddItems = $('#zhantiett').datagrid('getChecked');
		 var names="";  
		 var num1 =0;
		 $.each(checkeddItems, function(index, item){
				names=names+item.id+",";
			//	alert(item.id);
				num1 =num1 + 1;
			}); 

		 if(names!="")
				names=names.substring(0,names.length-1);  

		 if(num1 > 1){
			 $.messager.alert('失败','只能逐条选择要删除的记录','error');
			  return;
			 }
    
		 jQuery.post('/tcweb/elevator/deletezhantieEle',
			   	 {'id':names},
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
			            $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');
			   		}
			   		else{
			   			$.messager.alert('操作失败','操作失败','error');
				    		}
				       }); 	 
		 }}
	       );  
}

function showJyregistInfo() {
	$("#zhData").html($("#addDiv1").get(0));
	//清空数据
	$('#yuanshitt').datagrid('loadData',[]);  
	$("#autorelationinfo3Id").attr("onclick","");
	$("#newrelationinfoId").attr("onclick","");
	$("#autorelationinfoId").attr("onclick","");
	$("#autorelationinfoId").click(function(){
		 grid.datagrid("options").url='/tcweb/elevator/xzautozhantieddquery';
	     $('#zhantiett').datagrid('reload');
	     grid2.datagrid("options").url='/tcweb/elevator/xzautoyuanshiddquery';
	     $('#yuanshitt').datagrid('reload');
	});
	$("#autorelationinfo3Id").click(function(){
		 //alert("检察网数据模糊查询");
		var area =$('#areainfo').combobox('getValue');
		 grid.datagrid("options").url='/tcweb/elevator/autozhantieddquery4';
		 grid.datagrid("options").queryParams={'area':area};
	     $('#zhantiett').datagrid('reload');  
	     
	    grid2.datagrid("options").url='/tcweb/elevator/autoyuanshiddquery4';
	    $('#yuanshitt').datagrid('reload');
	});
	$("#newrelationinfoId").click(function(){
		//alert("检察网数据关联入库");
		 
		 var checkedItems = $('#zhantiett').datagrid('getChecked');
		 var names="";  
		 var num1 =0;
		 var num2 =0;
		 var zarea = 0;
		 $.each(checkedItems, function(index, item){
				names=names+item.id+",";
				if( item.area == "" ){
					  zarea = 1;
					}
			//	alert(item.id);
				num1 =num1 + 1;
			}); 
	   if(zarea == 1){
		   $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');
	       $("#yuanshitt").datagrid('clearSelections').datagrid('clearChecked'); 
	       zarea = 0;
	       $.messager.alert('失败','粘贴数据记录中含有区域为空的记录，请重新选择记录','error');
	       return;
		   }
	   
		
	  if(names!="")
			names=names.substring(0,names.length-1); 

	  var checkedItems2 = $('#yuanshitt').datagrid('getChecked');
		 var names2="";  
		 $.each(checkedItems2, function(index, item){
				names2=names2+item.id+",";
				//alert(item.id);
				num2 =num2 + 1;
			}); 
		
	if(names2 !="")
			names2=names2.substring(0,names2.length-1); 



	 if(num1 > 40){
		 $.messager.alert('失败','选择的粘贴数据记录数不能大于40条','error');
		  return;
		 }

	 if(num2 > 40){
		 $.messager.alert('失败','选择的原始数据记录条数不能大于40条','error');
		  return;
		 }
	   
	 if(num1 > num2){
	  $.messager.alert('失败','粘贴数据数目必须小于或等于原始数据数目','error');
	  return;
		 }
	 if(num1 == 0){

		 $.messager.alert('失败','请选择粘贴数据记录','error');
		  return;
		 }

	jQuery.post('/tcweb/elevator/relationrukudelevattor3',
	   	 {'names':names,'names2':names2},
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
	            grid2.datagrid('reload');
	            $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');
	            $("#yuanshitt").datagrid('clearSelections').datagrid('clearChecked');
	   		}
	   		else{
	   			$.messager.alert('操作失败','操作失败','error');
		    		}
		       }); 
	});
	$('#yuanshitt').datagrid({
	    title:'行政省批数据',
	    pageSize:10,
	    pageList:[10,20,40],
	    url:'',
	    queryParams:{},
	    idField:'id',
	    frozenColumns:[[
	         {field:'id',checkbox:true},
	    ]], 
	    columns:[[
	    	{field:'registCode',align:'left',halign:'center',title:'注册编码',width:170},
	    	{field:'registNumber',align:'left',halign:'center',title:'电梯编号',width:60},
		    {field:'registor',align:'center',halign:'center',title:'注册登记人',width:70},
	        {field:'address',align:'left',halign:'center',title:'地址',width:180},
	        {field:'registDate',align:'left',halign:'center',title:'注册时间',width:90},
	        {field:'registCompanyName',align:'center',title:'注册机构',width:180}
	    ]],
	    nowrap:true,
	    pagination:true,
	    toolbar:[{text:''}]
	});	
	$('#yuanshitt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,40]});
}

function clearQuery3() {
	$("#addDiv1 input").val("");
}

function query3(){
	$("#yuanshitt").datagrid('clearSelections').datagrid('clearChecked');
	//地址
	 var address=$('#addressId').attr("value");
	//注册编号
	 var registCode=$('#registCodeId').attr("value");
	 //电梯编号
	 var registNumber=$('#registNumberId').attr("value");
	 //注册人
	 var registor =$('#registorId').attr("value"); 
	 //注册公司
	 var registCompanyName=$('#registCompanyId').attr("value");
	 //注册时间
	  var registDate=$('#registDateId').datebox('getValue');
	 grid2.datagrid("options").url='/tcweb/elevator/Jyregistddquery';
     grid2.datagrid("options").queryParams={'address':address,'registCode':registCode,'registNumber':registNumber,'registor':registor,'registCompanyName':registCompanyName,'registDate':registDate};
     $('#yuanshitt').datagrid('reload');
}

//批量导入
function daoruJcData(){
	$('#drFrom').form('submit', {  	
		   url: '/tcweb/elevator/daoruJcData',  	
		   onSubmit: function(){ 
			   var filename = $("#jcData").val();
			   var ext = filename.substring(filename.lastIndexOf(".") + 1);
			   //alert(ext);
			   if(ext == "xlsx" || ext == 'xls' ){
				    $.messager.progress();   
				    //alert("tongduo");
			   } else {
				    $.messager.alert('Warning','上传文件格式错误！','warning'); 
			   		return false;
			   }
			   
		   },  	
		   success: function(data){
			   //eval("data="+"'"+ data +"'");
			   $.messager.progress('close');   
			   //关闭窗口
			   winDr.window('close');
			   //console.log(data);
			   //var obj = JSON.parse(data);
			   $.messager.alert("信息",data,"info");
			   //处理异常窗口
			   /* var dataObject = JSON.parse(data);
			   var shibieCodeyc = dataObject.shibieCodes;
			   var registCodeyc = dataObject.registCodes;
			   $('#shibieCodett').datagrid('loadData',shibieCodeyc);
			   $('#registCodett').datagrid('loadData',registCodeyc);
			   $('#rukuCount').html(dataObject.daoruCount);
			   winDryc.window('open'); */
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
	
.column{float:left;width:49%;box-sizing:border-box;}
.middle{flex:1;display:flex;}


</style>
</head>
<body class="easyui-layout" data-options="fit:true">
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">
    <div class="column" style="width:50%">
       <fieldset id="addDiv" style="margin:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">核实数据</legend>
       <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%"> 
       <tr><%  if(role !=10 && role !=11){%>
       <td nowrap>行政区划:</td>
<td><input id="areainfo" name="areainfo" style="height:25px;"/></td>
 <% } %>
<td align="right" nowrap>地址:</td> 
   <td nowrap><input id="addressinfo" name="addressinfo"  class="easyui-validatebox"></input></td>
   <td align="right" nowrap>楼盘:</td>
   <td nowrap><input id="buildingNameInfo" name="buildingName"  class="easyui-validatebox"></input></td>
  
</tr>
<tr>
<td nowrap align="right">注册编号:</td>
 <td nowrap><input id="registCodeinfo" name="registCodeinfo" size="20" class="easyui-validatebox"></input></td>
 <td nowrap align="right">识别码:</td>
 <td><input id="shibieCodeinfo" name="shibieCode" size="20" class="easyui-validatebox"></input></td>
 <td nowrap align="right">电梯编号:</td>
 <td nowrap><input id="registNumberinfo" name="registNumberinfo" size="20" class="easyui-validatebox"></input></td>
 <!--  <td>
   <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
  <a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 	
   <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>			
</td> -->
</tr>
<tr>
	<td></td><td></td><td></td><td></td>
	<td>
		<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
	</td>
	<td>
	     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>			
	</td>
</tr>
       </table>
       </fieldset> 
   </div>
   
   
   <div class="column" style="width:50%" id="zhData">
     <fieldset id="addDiv"  style="margin:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">监察网数据</legend>
     <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%">
      <tr><%  if(role !=10 && role !=11){%>
       <td nowrap align="right">行政区划:</td>
  
  <td><input id="areainfo2" name="areainfo2" style="height:25px;"/></td>
  <% } %>
  <td align="right" nowrap>地址:</td> 
   <td nowrap><input id="addressinfo2" name="addressinfo2"  class="easyui-validatebox"></input></td>
   <td align="right" nowrap>使用单位:</td>
   <td nowrap><input id="wgCompanyName" name="wgCompanyName"  class="easyui-validatebox"></input></td>
  </tr>
  <tr>
<td nowrap>注册编号:</td>
 <td nowrap><input id="registCodeinfo2" name="registCodeinfo2"  class="easyui-validatebox"></input></td>
 <td align="right" nowrap>楼盘:</td> 
   <td><input id="yuanshibuildingNameinfo" name="buildingNameinfo4"  class="easyui-validatebox"></input></td>
   <td nowrap align="right">识别码:</td>
 <td><input id="shibieCodeinfo3" name="shibieCode" size="20" class="easyui-validatebox"></input></td>
 
  <td>
 <!--     <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query2()">查询</a>  
	<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery2()">清空</a> 	-->	
	
</td>
</tr>
<tr>
	<% if("0".equals(cityName)){ %>
   <td colspan="3"><a href="#" class="easyui-linkbutton" icon="icon-ok" onclick="showJyregistInfo()">行政省批数据</a></td>
   <% }else{ %>
   <td></td><td></td><td></td>
   <%} %>
   <td></td>
	<td>
	<% if("0".equals(cityName)){ %>
	 <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query2()" style="width:100px;color:#3399FF;">查询</a>
	 <% }else{ %>
	 <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="querynj()" style="width:100px;color:#3399FF;">查询</a>
	 <% } %>
	 </td>
	 <td>
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery2()" style="width:100px;">清空</a>		
     </td>
</tr>
  </table>
     </fieldset> 
   
    </div> 
     
</div>
<div region="center" class="middle">
       <div id="main-center" style="float:left;margin:0px;overflow:auto;width:50%" class="column">      
       <table id="zhantiett"></table>    
    </div>  
    
    <div id="main-center2" style="float:left;margin:0px;overflow:auto;width:50%" class="column">      
       <table id="yuanshitt"></table>    
    </div>
</div>
<%  if(role ==1 || role ==2){%>
<div region="south">
     <center>
     <%  if(userId == 1526){%>
     
     <a id="autorelationinfoId" href="#" class="easyui-linkbutton" icon="icon-ok" onclick="autorelationinfo()"><font color="#0033FF">自动匹配</font></a> <% } %>
      <a href="#" class="easyui-linkbutton" icon="icon-ok" onclick="stautorelationinfo2()"><font color="#808080">停用匹配</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
     <a id="autorelationinfo3Id" href="#" class="easyui-linkbutton" icon="icon-ok" onclick="autorelationinfo2()"><font color="#808080">模糊匹配</font></a>&nbsp;&nbsp;&nbsp;&nbsp;   
     <a id="newrelationinfoId" href="#" class="easyui-linkbutton" icon="icon-ok" onclick="relationinfo()"><font color="#00ff00">关联入库</font></a> 
     </center>
     
</div> 
<% } %>  
<div id="loupanEditWin" class="easyui-dialog" title="修改" closed="true" style="width:300px;height:200px;padding:5px;">
<div class="easyui-layout" data-options="fit:true">  
     <div data-options="region:'north',split:true" style="height:80px">
     <table>
     <tr>
      <td align="right" nowrap>新楼盘名称:</td><td><input id="buildingNameInfoEdit" name="buildingNameInfoEdit" class="easyui-validatebox"></input></td>
     </tr>
     <tr height="10">
     </tr>
     <tr>
       <td align="left">
       <a href="javascript:void(0)" onclick="saveloupan()" id="btn-save" icon="icon-save">保存</a></td>  
      </tr>
     </table>
     </div>  
     
     <div data-options="region:'center'">  
      <table>  
      <tr>
     <td align="right" nowrap>新行政区划:</td><td><input id="areaInfoEdit" name="areaInfoEdit" /></td>
     </tr>
      <tr height="10">
      </tr>   
       <tr>
       <td align="left">
       <a href="javascript:void(0)" onclick="savearea()" id="btn-save3" icon="icon-save">保存</a></td>  
      </tr>
    </table>  
     </div>  
 </div>  
</div> 
<div id="eachRecordEditWin" class="easyui-dialog" title="记录修改" closed="true" style="width:560px;height:500px;padding:5px;">
<div class="easyui-layout" data-options="fit:true">  
     <div data-options="region:'north',split:true" style="height:170px">
       <form method="post" id="form1"> 
     <table style="width:100%">
     <tr>
      <td align="left" nowrap>内部编号:</td>
      <td><input id="useNumber" name="useNumber" size="15" class="easyui-validatebox"></input></td>
      <td align="left" nowrap>登记编号:</td>
      <td><input id="registCode" name="registCode" size="15" class="easyui-validatebox"></input></td>
     </tr>
     <tr>
      <td align="left" nowrap>楼盘名称:</td>
      <td><input id="buildingName" name="buildingName" size="15" class="easyui-validatebox"></input></td>
      <td align="left" nowrap>地址:</td>
      <td><input id="address" name="address" size="15" class="easyui-validatebox"></input></td>
     </tr>
     <tr>
      <td align="left" nowrap>栋:</td>
      <td><input id="building" name="building" size="15" class="easyui-validatebox"></input></td>
      <td align="left" nowrap>单元:</td>
      <td><input id="unit" name="unit" size="15" class="easyui-validatebox"></input></td>
     </tr>
     <tr>
      <td align="left" nowrap>区域:</td>
      <td><input id="area" name="area" /></td>
     <td align="left" nowrap>电梯编号:</td>
     <% if("1".equals(cityName)){ %>
     <td>N<input id="registNumber" name="registNumber" size="15" class="easyui-validatebox"></input></td>
     <% } else {%>
     <td><input id="registNumber" name="registNumber" size="15" class="easyui-validatebox"></input></td>
    <% }%>
    </tr>
     <tr>
     <td align="left" nowrap></td>
     <td><input type="hidden" id="id" name="id" size="15" class="easyui-validatebox"></input></td>
     </tr>
     </table>
     </form>
     </div>  
     <div data-options="region:'center'" style="overflow-x:auto;overflow-y:hidden"> 
     <div style="width:1300px;"> 
     <div id="i4" style="float:left"><img src="" id="img4" style="width:265px;height:240px;"></div>
     <div id="i5" style="float:left"><img src="" id="img5" style="width:265px;height:240px;"></div>
     <div id="i6" style="float:left"><img src="" id="img6" style="width:265px;height:240px;"></div>
     </div>
     </div>
     <div data-options="region:'south'" style="height:40px">  
      <table style="width:100%">     
       <tr>
       <td align="right">
       <a href="javascript:void(0)" onclick="eachRecordsave()" id="btn-save2" icon="icon-save">保存</a></td>  
      </tr>
    </table>  
     </div>  
 </div>  
</div> 
 <div id="st-window" title="停用信息" style="width:580px;height:400px;">
        <table id="stelelisttt"></table>  
   </div>
   
   <!-- 行政省批 -->
   <fieldset id="addDiv1"  style="margin:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">行政省批数据</legend>
     <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%">
      <tr>
       <td nowrap align="right">注册编号:</td>
  
	   <td nowrap><input id="registCodeId" name="registCode" style="height:25px;" class="easyui-validatebox"></input></td>
	    <td align="left" nowrap>电梯编号:</td><td><input id="registNumberId" name="registNumber"  class="easyui-validatebox" style="height:25px;"></input></td>
	    <td align="right" nowrap>注册登记人:</td>
 		<td nowrap><input id="registorId" name="registor"  class="easyui-validatebox" style="height:25px;"></input></td>
	   <!-- <td align="left" nowrap><input id="registNumberId" name="registNumber"  class="easyui-validatebox"></input></td> -->
      </tr>
  	<tr>
  	 <td align="right" nowrap>地址:</td> 
	   <td nowrap><input id="addressId" name="address"  class="easyui-validatebox" style="height:25px;"></input></td>
	   <td nowrap>注册时期:</td><td><input id="registDateId" type="text" class="easyui-datebox" required="required" style="height:25px;"></input></td>
 		<td align="right" nowrap>注册机构:</td> 
   		<td><input id="registCompanyId" name="registCompanyName"  class="easyui-validatebox" style="height:25px;"></input></td>
	</tr>
	<tr>
		<td></td><td></td><td></td><td></td>
		<td>
	 		<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query3()" style="width:100px;color:#3399FF;">查询</a>
	 	</td>
	 	<td>
     		<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery3()" style="width:100px;">清空</a>		
		</td>
	</tr>
  </table>
     </fieldset>
     
     <!--  批量导入 -->
  <div id="car-windowDr" title="详细信息" style="width:300px;height:200px;">
   		<form id='drFrom' enctype="multipart/form-data" action="/tcweb/elevator/daoruZhantieData" method="post">
   			<table align="center" id="drTable">
   				<tr>
   					<td>
	   					<input type="file" name="jcData" id='jcData'/>
   					</td>
   				</tr>
   				<tr align="center">
   					<td>
				   		<input type="button" value="导入" onclick='daoruJcData()'/>
   					</td>
   				</tr>
   			</table>
   		</form>
   </div>
</body>
</html>