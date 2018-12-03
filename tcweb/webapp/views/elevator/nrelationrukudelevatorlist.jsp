<%@ page import="com.zytx.models.UserInfo,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>


<% 
String cityName = GlobalFunction.cityName;
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int role=0;
int userId=0;
if(userinfo!=null){
 role = userinfo.getRole(); 
 userId =userinfo.getId();
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
	    userId = user.getId();
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
		form = win.find('form');

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
		    

		
        <%if(role ==  1 || role ==2){%>
        $('#mhppButton').linkbutton('disable');
        <%}%>
	    grid=$('#zhantiett').datagrid({
			    title:'核实数据（电梯标签粘贴数据已上传,但在系统核实中...，双击可补全相应电梯信息）',
			    pageSize:10,
			    pageList:[10,20,40],
			    url:'',
			    queryParams:{},
			    idField:'id',
			    frozenColumns:[[
                    {field:'id',checkbox:true}, 
                    {field:'shibieCode',title:'96333识别码'},
                    {field:'registCode',title:'登记编号',width:180}
			    ]], 
			    columns:[[ 
				    {field:'buildingName',title:'楼盘名称',width:80},
			        {field:'address',title:'地址',width:160},
			        {field:'building',title:'栋',width:40},
			        {field:'unit',title:'单元',width:50},
			        {field:'useNumber',title:'内部编号'}, 
			        {field:'registNumber',title:'电梯编号',width:60,formatter: function(value,rec,index) {
				        return "N"+value;
			        }},
			        {field:'mobileUploadbeizhu',title:'上传备注',width:150},
			        {field:'area',title:'区域',width:50}
			    ]],
			    nowrap:true,
			    pagination:true,
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
			  /*  ,'-',{
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
		        } */
			    ],
			    <% } else {%>
			    toolbar:[{text:''}],
			    <% }%>
			    onDblClickRow :function(rowIndex,rowData){
			    	    $('#eachRecordEditWin').window('open');
			    	    $('#useNumber').attr("value",rowData.useNumber);
			    	    $('#registCode').attr("value",rowData.registCode);
			    	    $('#buildingName').attr("value",rowData.buildingName);
			    	    $('#address').attr("value",rowData.address);
			    	    $('#building').attr("value",rowData.building);
			    	    $('#unit').attr("value",rowData.unit);
			    	    $('#id').attr("value",rowData.id);
			     	//    $('#area').val(rowData.area);
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
				         {field:'shibieCode',title:'96333识别码'},
					     {field:'registCode',title:'登记编号',width:180},
				    ]], 
				    columns:[[
				        {field:'eleType',title:'设备名称',width:150},
				        {field:'neleType',title:'新设备类别',width:150},
				        {field:'eleMode',title:'规格型号',width:150},
					    {field:'wgCompanyName',title:'使用单位',width:150},
					    {field:'wgContactor',title:'联系人',width:150},
					    {field:'wgPhone',title:'联系电话',width:150},
				        {field:'address',title:'使用单位地址',width:180},
				        {field:'address2',title:'使用地点',width:80},
				        {field:'eleStop',title:'层数',width:80},
				        {field:'acceptanceReportNum',title:'报告书编号',width:80},
				        {field:'inspectDate',title:'检验日期',width:80},
				        {field:'dueDate',title:'到期日期',width:80},
				        {field:'checkResult',title:'检验结论',width:80},
				        {field:'factoryNum',title:'出厂编号',width:80},
				        {field:'manufactDate',title:'出厂日期',width:80},
				        {field:'zzCompanyName',title:'制造单位',width:150},
				        {field:'ywCompanyName',title:'维保单位',width:150},
				        {field:'ywLevel',title:'维保星级',width:150},
				        {field:'ywContactor',title:'维保单位联系人',width:150},
					    {field:'ywPhone',title:'维保单位联系电话',width:150},
					    {field:'azCompanyName',title:'安装单位',width:150},
					    {field:'speed',title:'额定速度',width:80},
					    {field:'eleLoad',title:'额定载重量',width:80},
					    {field:'jyCompanyName',title:'验收检验单位',width:150},
				        {field:'area',title:'区域',width:80},
				        {field:'townshipStreetsName',title:'街道办',width:80},
				        {field:'useNumber',title:'内部编号'}
				    ]],
				    nowrap:true,
				    pagination:true,
				    toolbar:[{text:''}]
				});	
				$('#yuanshitt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,40]});     	  
		    

		});

	  

 function query(){
	 $("#zhantiett").datagrid('clearSelections').datagrid('clearChecked');    
	 var address=$('#addressinfo').attr("value");
	 var buildingName=$('#buildingNameInfo').attr("value");
	// var area=$('#areainfo option:selected').val();
	var area =$('#areainfo').combobox('getValue'); 
	 var registCode =$('#registCodeinfo').attr("value"); 
	 grid.datagrid("options").url='/tcweb/elevator/zhantieddquery';
     grid.datagrid("options").queryParams={'area':area,'address':address,'buildingName':buildingName,'registCode':registCode};
     $('#zhantiett').datagrid('reload');
	 }

 function clearQuery(){
	$('#addressinfo').attr("value","");	 
	$('#buildingNameInfo').attr("value","");
//	$('#areainfo option:first').attr('selected','selected');
    $('#areainfo').combobox('clear');
	$('#registCodeinfo').attr("value","");
}

 function query2(){
	 $("#yuanshitt").datagrid('clearSelections').datagrid('clearChecked');
	 var address=$('#addressinfo2').attr("value");
	 var address2=$('#addressinfo3').attr("value");
	 var wgCompanyName=$('#wgCompanyName').attr("value");
	// var area=$('#areainfo2 option:selected').val();
	 var area =$('#areainfo2').combobox('getValue'); 
	 var registCode =$('#registCodeinfo2').attr("value"); 
	 grid2.datagrid("options").url='/tcweb/elevator/yuanshiddquery';
     grid2.datagrid("options").queryParams={'area':area,'address':address,'address2':address2,'wgCompanyName':wgCompanyName,'registCode':registCode};
     $('#yuanshitt').datagrid('reload');
	 }

 function clearQuery2(){
		$('#addressinfo2').attr("value","");
		$('#addressinfo3').attr("value","");	 
		$('#wgCompanyName').attr("value","");
	//	$('#areainfo2 option:first').attr('selected','selected');
	    $('#areainfo2').combobox('clear');
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
	// var area=$('#areainfo option:selected').val();
	 var area =$('#areainfo').combobox('getValue');
	 grid.datagrid("options").url='/tcweb/elevator/autozhantieddquery2';
	 grid.datagrid("options").queryParams={'area':area};
     $('#zhantiett').datagrid('reload');
     

     grid2.datagrid("options").url='/tcweb/elevator/autoyuanshiddquery2';
     grid2.datagrid("options").queryParams={'area':area};
     $('#yuanshitt').datagrid('reload');
	 }

 function dbRuku(){
	 $.messager.confirm('','确定单边入库',function(data){if(data){
	 var checkedItems = $('#zhantiett').datagrid('getChecked');
	 var names="";  
	 var num1 =0;
	 $.each(checkedItems, function(index, item){
			names=names+item.id+",";
		//	alert(item.id);
			num1 =num1 + 1;
		}); 

	 if(names!="")
			names=names.substring(0,names.length-1); 

	 if(num1 > 40){
		 $.messager.alert('失败','选择的粘贴数据记录数不能大于40条','error');
		  return;
		 }

	 jQuery.post('/tcweb/elevator/dbrelationrukudelevattor',
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
			       }); 	
	 }}
       );
	 }
 function relationinfo(){ 
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
       <fieldset id="addDiv" style="margin:0px"><legend>核实数据</legend>
       <table>
       <tr><%  if(role !=10 && role !=11){%>
       <td nowrap>行政区划:</td>
        <!--  
   <td> 
   <select id="areainfo"   name="areainfo" style="width:100px;">
   <option value=""></option>
   <option value="崇川区">崇川区</option>
   <option value="南通开发区">南通开发区</option>
   <option value="港闸区">港闸区</option>
   <option value="通州区">通州区</option>
   <option value="通州湾示范区">通州湾示范区</option>
   <option value="海安市">海安市</option>
   <option value="如东市">如东市</option>
   <option value="启东市">启东市</option>
   <option value="如皋市">如皋市</option> 
   <option value="海门市">海门市</option> 
</select>   </td> -->
 <td><input id="areainfo" name="areainfo" /></td><% } %>
<td align="right" nowrap>地址:</td> 
   <td nowrap><input id="addressinfo" name="addressinfo" size="15" class="easyui-validatebox"></input></td>
   <td align="right" nowrap>楼盘:</td>
   <td nowrap><input id="buildingNameInfo" name="buildingName" size="15" class="easyui-validatebox"></input></td>
  
</tr>
<tr>
<td nowrap>登记编号:</td>
 <td nowrap><input id="registCodeinfo" name="registCodeinfo" size="15" class="easyui-validatebox"></input></td>
  <td colspan="2"><a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
	<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 				
</td>
</tr>
       </table>
       </fieldset> 
   </div>
   
   
   <div class="column" style="width:50%">
     <fieldset id="addDiv" style="margin:0px"><legend>监察网数据</legend>
     <table>
      <tr><%  if(role !=10 && role !=11){%>
       <td nowrap>行政区划:</td>
        <!-- <td> 
   <td> 
   <select id="areainfo2"   name="areainfo2" style="width:100px;">
   <option value=""></option>
   <option value="崇川区">崇川区</option>
   <option value="南通开发区">南通开发区</option>
   <option value="港闸区">港闸区</option>
   <option value="通州区">通州区</option>
   <option value="通州湾示范区">通州湾示范区</option>
   <option value="海安市">海安市</option>
   <option value="如东市">如东市</option>
   <option value="启东市">启东市</option>
   <option value="如皋市">如皋市</option> 
   <option value="海门市">海门市</option> 
  </select></td>  -->
  <td><input id="areainfo2" name="areainfo2" /></td>
  <% } %>
  <td align="right" nowrap>地址:</td> 
   <td nowrap><input id="addressinfo2" name="addressinfo2" size="15" class="easyui-validatebox"></input></td>
   <td align="right" nowrap>使用单位:</td>
   <td nowrap><input id="wgCompanyName" name="wgCompanyName" size="15" class="easyui-validatebox"></input></td>
  </tr>
  <tr>
<td nowrap>登记编号:</td>
 <td nowrap><input id="registCodeinfo2" name="registCodeinfo2" size="15" class="easyui-validatebox"></input></td>
 <td align="right" nowrap>地点:</td> 
   <td nowrap><input id="addressinfo3" name="addressinfo3" size="15" class="easyui-validatebox"></input></td>
  <td colspan="2"><a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query2()">查询</a>  
	<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery2()">清空</a> 				
</td>
</tr>
  </table>
     </fieldset> 
   
    </div> 
     
</div>
<!--  
<div region="center">
       <div id="main-center"  fit="true" border="false" style="width:49%;float:left;margin:0px;height:395px;overflow:auto;">      
       <table id="zhantiett"></table>    
    </div>  
    
    <div id="main-center2"  fit="true" border="false" style="float:left;margin:0px;height:395px;overflow:auto;">      
       <table id="yuanshitt"></table>    
    </div>
</div>
-->
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
     <%  if(userId == 1){%>
     
     <a href="#" class="easyui-linkbutton" icon="icon-ok" onclick="autorelationinfo()"><font color="#0033FF">自动匹配</font></a> <% } %>
     <a href="#" class="easyui-linkbutton" icon="icon-ok" onclick="autorelationinfo2()" id="mhppButton"><font color="#808080">模糊匹配</font></a>&nbsp;&nbsp;&nbsp;&nbsp;   
     <a href="#" class="easyui-linkbutton" icon="icon-ok" onclick="relationinfo()"><font color="#00ff00">关联入库</font></a>  
     </center>
     
</div> 
<% } %>  
<div id="loupanEditWin" class="easyui-dialog" title="修改" closed="true" style="width:300px;height:200px;padding:5px;">
<div class="easyui-layout" data-options="fit:true">  
     <div data-options="region:'north',split:true" style="height:80px">
     <table>
     <tr>
      <td align="right" nowrap>新楼盘名称:<input id="buildingNameInfoEdit" name="buildingNameInfoEdit"  class="easyui-validatebox"></input></td>
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
         <!-- 
      <td align="left" nowrap style="text-indent:24;">新区域:<select id="areaInfoEdit"   name="areaInfoEdit" style="width:100px;">
   <option value=""></option>
   <option value="崇川区">崇川区</option>
   <option value="南通开发区">南通开发区</option>
   <option value="港闸区">港闸区</option>
   <option value="通州区">通州区</option>
   <option value="通州湾示范区">通州湾示范区</option>
   <option value="海安市">海安市</option>
   <option value="如东市">如东市</option>
   <option value="启东市">启东市</option>
   <option value="如皋市">如皋市</option> 
   <option value="海门市">海门市</option> 
  </select></td>  -->
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
  <!--   <td>
   <select id="area" name="area" style="width:102px;">
   <option value="崇川区">崇川区</option>
   <option value="南通开发区">南通开发区</option>
   <option value="港闸区">港闸区</option>
   <option value="通州区">通州区</option>
   <option value="通州湾示范区">通州湾示范区</option>
   <option value="海安市">海安市</option>
   <option value="如东市">如东市</option>
   <option value="启东市">启东市</option>
   <option value="如皋市">如皋市</option> 
   <option value="海门市">海门市</option> 
</select></td> --> 
      <td><input id="area" name="area" /></td>
     <td align="left" nowrap>电梯编号:</td>
     <td><input id="registNumber" name="registNumber" size="15" class="easyui-validatebox"></input></td>
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
<
</body>
</html>