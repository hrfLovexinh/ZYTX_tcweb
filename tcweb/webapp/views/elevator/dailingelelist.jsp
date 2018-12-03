<%@ page import="com.zytx.models.UserInfo" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>


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
	 
	 var ywName="";
	 
	 $(function(){

	     $.ajaxSetup ({
			 cache: false 
		 });

	     grid=$('#dltt').datagrid({
	 	    title:'待领电梯列表',
	 	    pageSize:10,
	 	    pageList:[10,20,25,30,35,40],
	 	    url:'/tcweb/elevator/dailingelevatorlist',
	 	    queryParams:{},
	 	    columns:[[
	 	        {field:'registNumber',title:'电梯编号',width:60},
	 	        {field:'address',title:'地址',width:200},
	 	        {field:'buildingName',title:'楼盘名称',width:200},
	 	        {field:'registCode',title:'登记编号',width:200},
	 	        {field:'useNumber',title:'单位内部编号'},
	 	        {field:'wgCompanyName',title:'使用单位',width:150},
	 	        {field:'ywCompanyName',title:'维保单位',width:110},
	 	        {field:'subTime',title:'最近一次维保日期',width:135,formatter: function(value,rec,index) {
	                 if(value!=null)
	                     return value.substring(0,16);
	 		         }},
	 	        {field:'ischangInfo',title:'变更信息',align:'center',
	 	        	formatter: function(value,rec,index) { 
	 	        	var registNumber = ''+rec.registNumber;
	   	        	if(value==1)
	   	        	   { 
	   			        return "<img src='<%=request.getContextPath()%>/images/yulan.png' alt='查看' style='cursor:hand;' onclick='openCarinfoDetail("+"\""+registNumber+"\""+")'/>";
	   	        	   }
	    			        else
	   	  			        return "";
	   	            }}
	 	    ]],
	 	    pagination:true,
	 	   
	 	    toolbar:[ <% if(role ==10 || role ==11){%>
	 		 	{
	 	        text:'认领',
	 	        iconCls:'icon-cut',
	 	        handler:function(){
	 	    	var row = grid.datagrid('getSelected');   
		    	if (row){
		    		 $.messager.confirm('','确定要认领该电梯',function(data){if(data){
		    		jQuery.post('/tcweb/elevator/claimdailingele',
		    			   	 {'id':row.id},
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
		    	       );

			    	
			    	}
		    	else {  
		    		 $.messager.show({   
		    			 title:'警告',
		    			 msg:'请先选择记录行。' 
		    		 });   
		    	 } 
	 	    		 
	 	        }
	 	    }  <% }%>
		 	    ]
	 	    
	 	});	
	 	
	 	$('#dltt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,25,30,35,40]});   
	    	  
	 });

	 function query(){  
			var registNumber=$('#registNumber').attr("value");
			var address=$('#addressinfo').attr("value");
			var registCode =$('#registCodeinfo').attr("value");
		    var ywCompanyId=$('#ywCompanyIdinfo2').attr("value");
		    var area=$('#areainfo option:selected').val();
		    var useNumber =$('#useNumberInfo').attr("value");
	
		    if (!ywCompanyId){
		    	ywCompanyId =0;
		    	}
	    	
			if(""==registNumber && ywCompanyId==0){
			   if(""==ywName && ""==area){
				   if(""==address){	
					   if(""==registCode){	
						   if(""==useNumber){	
				               grid.datagrid("options").url='/tcweb/elevator/dailingelevatorlist';
						   }
				            else{
				            	  grid.datagrid("options").url='/tcweb/elevator/dailingelequery';
					              grid.datagrid("options").queryParams={'useNumber':useNumber};
					            }
						   }
						   else{ 
			                grid.datagrid("options").url='/tcweb/elevator/dailingelequery';
			                grid.datagrid("options").queryParams={'registCode':registCode,'useNumber':useNumber};
						   }
			       }
				   else{
					  grid.datagrid("options").url='/tcweb/elevator/dailingelequery';
					  grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'registCode':registCode,'useNumber':useNumber};
				  }
			   }
			   else{
				 if(""==address){
					 grid.datagrid("options").url='/tcweb/elevator/dailingelequery';
					 grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'registCode':registCode,'useNumber':useNumber};
						 
				 }
				 else{
				    grid.datagrid("options").url='/tcweb/elevator/dailingelequery';
				    grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'registCode':registCode,'useNumber':useNumber};
				   }
			    }
			}
			else{
		     grid.datagrid("options").url='/tcweb/elevator/dailingelequery';
		     grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'registCode':registCode,'useNumber':useNumber};
			}  
		    $('#dltt').datagrid('reload');
			}

	 function clearQuery(){
			$('#registNumber').attr("value","");
			$('#addressinfo').attr("value","");
			$('#registCodeinfo').attr("value","");
			$('#useNumberInfo').attr("value","");
			$('#ywCompanyIdinfo').attr("value","");
			$('#ywCompanyIdinfo2').attr("value","");
			$('#areainfo option:first').attr('selected','selected');
		}
			
</script>
</head>
<body class="easyui-layout">
<div region="north" style="overflow:hidden">  
 <fieldset id="addDiv" style="width: 100%;margin:0px"><legend>查询条件</legend>
    
     <table> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
   <td nowrap><input id="registNumber" name="registNumber" size="12" class="easyui-validatebox"></input></td>
    <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="addressinfo" name="addressinfo" size="24" class="easyui-validatebox"></input></td>
   <td nowrap>登记编号：</td>
 <td nowrap><input id="registCodeinfo" name="registCodeinfo" size="24" class="easyui-validatebox"></input></td>
   </tr>  
  <tr>  
 <td nowrap>所在区域：</td>
   <td> 
   <select id="areainfo"   name="areainfo" style="width:100px;" onchange="getCompanyListByArea()">
   <option value=""></option>
   <option value="锦江">锦江</option>
   <option value="青羊">青羊</option>
   <option value="金牛">金牛</option>
   <option value="成华">成华</option>
   <option value="武侯">武侯</option>
   <option value="高新">高新</option>
   <option value="天府新">天府新</option>
   <option value="青白江">青白江</option>
   <option value="龙泉驿">龙泉驿</option>
   <option value="新都">新都</option> 
   <option value="温江">温江</option> 
   <option value="双流">双流</option> 
   <option value="郫县">郫县</option> 
   <option value="金堂">金堂</option>
   <option value="大邑">大邑</option>
   <option value="蒲江">蒲江</option>
   <option value="新津">新津</option>
   <option value="都江堰">都江堰</option>
   <option value="崇州">崇州</option>
   <option value="邛崃">邛崃</option>
   <option value="彭州">彭州</option>
   <option value="安仁">安仁</option>
   <option value="大丰">大丰</option>  
</select>
</td>
 <td nowrap>维保单位：</td>
   <td> 
  <!--  <select id="ywCompanyIdinfo"  class="easyui-combobox" name="ywCompanyIdinfo" style="width:152px;"> </select>-->
  <input id="ywCompanyIdinfo" style="width:152px;">
  <input type ="hidden" id="ywCompanyIdinfo2">
 </td>
 <td align="right" nowrap>内部编号：</td>
   <td nowrap><input id="useNumberInfo" name="useNumberName" size="24" class="easyui-validatebox"></input></td>
  
 <td colspan="2">
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
       <table id="dltt"></table>
   </div>  
        
    </div>  
</div> 
</body>
</html>