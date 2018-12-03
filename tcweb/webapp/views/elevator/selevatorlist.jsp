<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">  -->
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">
 <style>
    .datagrid-header-row td{background-color:rgb(226,237,255);color:#0E2D5F;}
  </style>

<% 

String cityName = GlobalFunction.cityName;
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int isliulan = 0;
int role=0;

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
			 if(user != null)
			    role = user.getRole();
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
	 
	 var ywName="";
	 
	 $(function(){

	     $.ajaxSetup ({
			 cache: false 
		 });
	     
	     var inputWidth = $('#registCodeinfo').width();  
		 $("#registNumber").css("width", inputWidth);
		
		 
	     <%if((role == 1 || role ==2)||(role == 22 || role ==23)) { %>  
	     $("#areainfo").css("width", inputWidth);
	     $('#areainfo').combobox({   
	         url:'/tcweb/elevator/areaInfoList',   
	         valueField:'area',   
	         textField:'area'  
	     });  
        <%}%>
	     $('#area').combobox({   
		        url:'/tcweb/elevator/areaInfoList',   
		        valueField:'area',   
		        textField:'area'  
		    });  
     
	     comb7=$('#townshipStreets').combobox({
		    	valueField:'id',
			    textField:'companyName'
			});
				    
		    $('#townshipStreets').combobox({
		    	filter: function(q, row){
		    	//    ywName = q;
		    		var opts = $(this).combobox('options');
		    		return row[opts.textField].indexOf(q) >= 0;
		    	}
		    });
        
	    comb10 =$('#area').combobox({
			 onSelect: function (record) {
			 comb7.combobox({
	    //    url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.value),
	       url: '/tcweb/elevator/getjdbCompanyListByNewarea?companyArea='+encodeURI(record.area),
	        valueField: 'id',
	        textField: 'companyName'
	    }).combobox('clear'); 

		     }
			}); 

	     $('#btn-save,#btn-cancel').linkbutton(); 
	     win = $('#selevatorinfo').window({  closed:true,draggable:false,modal:true,onClose:function(){$('input').attr("disabled","");} }); 
	     form = win.find('form');

	     grid=$('#dltt').datagrid({
	 	    title:'电梯停用列表(红色记录为已经粘贴标签记录，可以进行取消停用操作)',
	 	    fitColumns:true,
	 	    pageSize:25,
	 	    pageList:[15,25,30,35,40],
	 	    url:'/tcweb/elevator/sdailingelevatorlistByOrder',
	 	    queryParams:{},
	 	    frozenColumns:[[
              <% if("1".equals(cityName)){ %>
                {field:'registNumber',align:'center',halign:'center',title:'电梯编号↑↓',sortable : true,width:$(this).width() * 0.09,formatter: function(value,rec,index) {
                	return "N"+value;
                    }},
                    <% } else {%>  
                    {field:'registNumber',align:'center',halign:'center',title:'电梯编号↑↓',sortable : true,width:$(this).width() * 0.09},
                    <% }%>
                {field:'registCode',align:'center',halign:'center',title:'登记编号↑↓',sortable : true,width:$(this).width() * 0.15},
                {field:'address',align:'left',halign:'center',title:'地址',width:$(this).width() * 0.09},
                {field:'buildingName',align:'center',halign:'center',title:'楼盘名称↑↓',sortable : true,width:$(this).width() * 0.09},
                {field:'unit',align:'left',halign:'center',title:'单元',width:$(this).width() * 0.09},
                {field:'useNumber',align:'left',halign:'center',title:'单位内部编号',width:$(this).width() * 0.09},
                {field:'endTime',align:'center',title:'上一次运维结束时间↑↓',sortable : true,width:$(this).width() * 0.09} 
	 	       	 	   ]],
	 	    columns:[[ 
	 	        {field:'area',align:'center',title:'行政区划',width:$(this).width() * 0.06},
	 	        {field:'wgCompanyName',align:'left',halign:'center',title:'使用单位',width:$(this).width() * 0.09},
	 	        {field:'ywCompanyName',align:'left',halign:'center',title:'维保单位',width:$(this).width() * 0.09},
	 	       {field:'beizhu',align:'left',halign:'center',title:'备注',width:$(this).width() * 0.09}
	 	    ]],
	 	   rowStyler:function(index,row){  
			    var  source = row.source; 
			       if( source == 0)
			    	return 'color:#ff0000;';
			    else
			    	return 'color:#000000;';
			    
		     },
	 	    pagination:true,
	 	    singleSelect:true,
	 	    striped:true,
	 	   <% if(isliulan == 0){%>
	 	    toolbar:[ {
		        text:'新增',
		        iconCls:'icon-add',
		        handler:function(){
		    	win.window('open');  
		        jdbSelectValue();
		       
		    	form.url ='/tcweb/elevator/selevatorinfoadd';	
		    	colseWinDetail();	 
		        }
		    },{
		        text:'删除',
		        iconCls:'icon-cut',
		    	handler:function(){
		    	 var row = grid.datagrid('getSelected'); 
		    	 if(row){
			     if(row.source == 1){
		    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
		    	 jQuery.post('/tcweb/elevator/sdelete',
		    	    	 {'id':row.id,'source':row.source},
		    	    	 function(data){
		    	    		eval("data="+"'"+data+"'");  
		    	    		if("success"==data){
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
	  	       );}
			     else{
			    	 $.messager.alert('操作失败','该记录为已经粘贴标签电梯，不能删除，只能进行取消停用操作','error');

				     }
		    	 }else{
		    		 $.messager.show({   
		    			 title:'警告',
		    			 msg:'请先选择记录行。' 
		    		 });   
			     }
		    	 
		        }
		    },{
		        text:'修改',
		        iconCls:'icon-edit',
		        handler:function(){
		    	var row = grid.datagrid('getSelected');   
		    	if (row){
		    		if(row.source == 1){
		    			jdbSelectValue2(row.area);
		    			win.window('open');   
		    			form.form('load', '/tcweb/elevator/sedit/'+row.id);
			    		form.url = '/tcweb/elevator/supdate/'+row.id; 
				    	}
					else{
					   $.messager.alert('操作失败','该记录为已经粘贴标签电梯，不能在停用电梯列表中修改','error');
				     }
			    	}
		    	else{
		    		 $.messager.show({   
		    			 title:'警告',
		    			 msg:'请先选择记录行。' 
		    		 });   
			     }
		      }
		    },'-', 
	 		 	{
	 	        text:'取消停用',
	 	        iconCls:'icon-redo',
	 	        handler:function(){
	 	    	var row = grid.datagrid('getSelected');   
		    	if (row){
			    	 var source = row.source;  
			    	 if(source == 0){
		    		 $.messager.confirm('','确定要取消停用电梯',function(data){if(data){
		    		jQuery.post('/tcweb/elevator/redotingYong',
		    			   	 {'id':row.id,'registNumber':row.registNumber},
		    			   	 function(data){
		    			   		eval("data="+"'"+data+"'");  
		    			   		if("success"==data){
		    			   		 $.messager.show({   
		    					 title:'提示信息',
		    					 timeout:1000,
		    					 msg:'操作成功，谢谢。' 
		    				 });  	
		    			         grid.datagrid('reload');
		    			   		}
		    			   		else{
		    			   			$.messager.alert('操作失败','操作失败','error');
		    				    		}
		    				       }); }}
		    	       );}
			    	 else{
			    			$.messager.alert('操作失败','该条记录属于未粘贴电梯标签记录，不能取消停用','error');
                      
				    	 }

			    	
			    	}
		    	else {  
		    		 $.messager.show({   
		    			 title:'警告',
		    			 msg:'请先选择记录行。' 
		    		 });   
		    	 } 
	 	    		 
	 	        }
	 	    }  
		 	    ]
		 	   <% } %> 
	 	});	
	 	
	 	$('#dltt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});   
	    	  
	 });

	 function query(){  
			var registNumber=$('#registNumber').attr("value");
			var address=$('#addressinfo').attr("value");
			var registCode =$('#registCodeinfo').attr("value");
		    var ywCompanyId=$('#ywCompanyIdinfo2').attr("value");
		//    var area=$('#areainfo option:selected').val();
		   var area = "";
		   <%if((role == 1 || role ==2)||(role == 22 || role ==23)) { %>  
		    area =$('#areainfo').combobox('getValue'); 
		    <%}%>
		    var useNumber =$('#useNumberInfo').attr("value");
	        var buildingName=$('#buildingName').attr("value");
		    if (!ywCompanyId){
		    	ywCompanyId =0;
		    	}
		    
		//  grid.datagrid("options").url='/tcweb/elevator/sdailingelequery';
		    grid.datagrid("options").url='/tcweb/elevator/sdailingelequeryByOrder';
		    grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'registCode':registCode,'useNumber':useNumber,'buildingName':buildingName};
		
		    $('#dltt').datagrid('reload');
			}

	 function clearQuery(){
			$('#registNumber').attr("value","");
			$('#addressinfo').attr("value","");
			$('#buildingName').attr("value","");
			$('#registCodeinfo').attr("value","");
			$('#useNumberInfo').attr("value","");
			$('#ywCompanyIdinfo').attr("value","");
			$('#ywCompanyIdinfo2').attr("value","");
	//		$('#areainfo option:first').attr('selected','selected');
			$('#areainfo').combobox('clear');
		}

	function jdbSelectValue(){
		   comb7.combobox({
	          url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI($('#area').combobox('getValue')),
	          valueField: 'id',
	          textField: 'companyName'
	      }).combobox('clear');
		   }

	function saveSelevatorinfo(){
		form.form('submit', {  
			url:form.url,
			onSubmit:function(){
	     //   return $(this).form('validate');
		
	        var townshipStreets=$('#townshipStreets').combobox('getValue'); 
	        var area=$('#area').combobox('getValue');
		
		    if (!area) {
					$.messager.alert('操作失败', '所属区域不能为空', 'error');
					return false;
			}

			if (!townshipStreets) {
					$.messager.alert('操作失败', '街道办不能为空', 'error');
					return false;
			}
		  
	          return form.form('validate');
				//return true;
				
			},

			success : function(data) {
				eval("data=" + "'" + data + "'");
				if ("exist" == data) {
					$.messager.alert('操作失败', '已经存在相同注册代码的停用电梯，不能重复添加', 'error');
				} else if ("success" == data) {  
				$.messager.show( {
					title : '提示信息',
					timeout : 1000,
					msg : '操作成功，谢谢。'
				});
				
				 grid.datagrid('reload');
				 win.window('close');
			} else {
				$.messager.alert('操作失败', '添加停用电梯失败', 'error');
			}
		}
			});
		}

	function closeWindow(){
		win.window('close');
 
		}
	 function jdbSelectValue2(area){ 
		   comb7.combobox({
	    //      url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(area),
	            url: '/tcweb/elevator/getjdbCompanyListByNewarea?companyArea='+encodeURI(area),
	          valueField: 'id',
	          textField: 'companyName'
	      }).combobox('clear');
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
    <td nowrap align="right">登记编号：</td>
 <td nowrap><input id="registCodeinfo" name="registCodeinfo"  class="easyui-validatebox"></input></td>
    <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="addressinfo" name="addressinfo"  class="easyui-validatebox"></input></td>
   <td align="right" nowrap>楼盘：</td> 
   <td nowrap><input id="buildingName" name="buildingName"  class="easyui-validatebox"></input></td>
   </tr> 
    
  <tr> 
   <%if((role == 1 || role ==2)||(role == 22 || role ==23)) { %> 
 <td nowrap align="right">所在区域：</td>
 <!--  
   <td> 
   <select id="areainfo"   name="areainfo" style="width:154px;" onchange="getCompanyListByArea()">
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
<td><input id="areainfo" name="areainfo" style="height:25px;"/></td> <% } %>
<!--  
 <td nowrap>维保单位：</td>
   <td> 
  <input id="ywCompanyIdinfo" style="width:154px;">
  <input type ="hidden" id="ywCompanyIdinfo2">
 </td>  -->
 <td align="right" nowrap>内部编号：</td>
   <td nowrap><input id="useNumberInfo" name="useNumberName"  class="easyui-validatebox"></input></td>
  <td></td>
  <td></td>
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
<div region="center" style="width:100%;">
       <div id="main-center"  fit="true" border="false">  
        
          <div style="margin-top:1px;">  
       <table id="dltt"></table>
   </div>  
        
    </div>  
</div> 
<div id="selevatorinfo" title="详细信息" style="width:750px;height:500px;"> 
  <div style="padding:20px 20px 40px 80px;">   
  <form method="post" id="myform">    
  <table>
 
   <tr>        
    <td>注册代码：</td>      
   <td><input id="registCode" name="registCode" class="easyui-validatebox" data-options="required:true"></input><span class="fontShow"><font color="red">*</font></span></td>
   <td>地址：</td>      
   <td><input id="address" name="address" class="easyui-validatebox" data-options="required:true"></input><span class="fontShow"><font color="red">*</font></span></td> 
   </tr>
   <tr> 
    <td>楼盘名称：</td>      
   <td><input id="buildingName" name="buildingName"></input></td> 
   <td>所在栋：</td>      
   <td><input id="building" name="building" ></input></td> 
   </tr>
   <tr>   
   <td>所在单元：</td>      
   <td><input id="unit" name="unit"></input></td>
    <td>单位内部编号：</td>      
   <td><input id="useNumber" name="useNumber"></input></td>
   </tr>
   <tr> 
    <td>行政区划：</td>      
 <!-- <td><select id="area"  class="easyui-combobox" name="area" style="width:132px;">
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
</select><span class="fontShow"><font color="red">*</font></span></td>   -->  
  <td><input id="area" name="area" /><span class="fontShow"><font color="red">*</font></span></td>
   <td>所在乡镇/街道：</td>      
     <td>
     <select id="townshipStreets"  class="easyui-combobox" name="townshipStreets" style="width:132px;"></select> 
     <span class="fontShow"><font color="red">*</font></span>
     </td>
   </tr>
    
   <tr> 
   <td>备注：</td> 
   <td><textarea class="easyui-validatebox" id="beizhu" name="beizhu" class="easyui-validatebox" style="height:60px;width:200px"></textarea></td>  
   </tr>
   </table>
   <table width=70%>
    <tr>
    <td align="center">
      <a href="javascript:void(0)" onclick="saveSelevatorinfo()" id="btn-save" icon="icon-save">保存</a>  
     <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">关闭</a> 
    </td>
    </tr> 
   </table>   
   </form> 
    </div> 
     
   </div>
</body>
</html>