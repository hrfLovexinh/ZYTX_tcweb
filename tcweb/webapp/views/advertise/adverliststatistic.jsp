<%@ page import="com.zytx.models.UserInfo" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>


<% 
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
    }   System.out.println("123");
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

			comb2 =$('#shehePersonId2').combobox({
				url:'/tcweb/advertise/allUserByCompanyId',
			    valueField:'id',
			    textField:'userName'
			});

			$('#shehePersonId2').combobox({
		    	filter: function(q, row){
		    	//    ywName = q;
		    		var opts = $(this).combobox('options');
		    		return row[opts.textField].indexOf(q) >= 0;
		    	}
		    });
		    
			grid=$('#dtt').datagrid({
			    title:'电梯列表',
			    pageSize:10,
			    pageList:[10,20,30,40,50],
			    url:null,
			    queryParams:{},
			    columns:[[
			        {field:'registNumber',title:'电梯编号',width:60,styler:function(value,row,index){
				        if (row.ruKuValid==1){return 'background-color:#99cc99;';} 
				        if (row.ruKuValid==2){return 'background-color:#FF3333;';}
				        if (row.isnormalFlag==1){return 'background-color:#999999;';}},
				        formatter: function(value,rec,index) {   
				        	 if(rec.ruKuValid==2)
				                    return value+"<img src='<%=request.getContextPath()%>/images/yulan.png' alt='查看' style='cursor:hand;' onclick='tishi("+"\""+rec.rukubeizhu+"\""+")'/>";
				                else
				                    return value;
			            }
				        },
				    {field:'buildingName',title:'楼盘名称',width:100},
			        {field:'address',title:'地址',width:200,formatter: function(value,rec,index) {   
			        	 if(rec.shenhe==1)
			                    return value+"<img src='<%=request.getContextPath()%>/images/usercheck.png' alt='现场核查' style='cursor:hand;' onclick='checktishi("+rec.id+")'/>";
			                else
			                    return value;
		         }},
			        {field:'useNumber',title:'内部编号',width:60},
			        {field:'building',title:'栋',width:40},
			        {field:'unit',title:'单元',width:40},
			        {field:'userName',title:'关联人'},
			        {field:'shenheTime2',title:'关联时间',width:200},
			        {field:'deviceId2',title:'关联编号',width:200}
			        
			    ]],
			    nowrap:false,
			    pagination:true
			});	
			$('#dtt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,30,40,50]});     	  
	 });

	 function clearQuery(){
		 $('#addressinfo').attr("value","");
		 $('#buildingNameInfo').attr("value","");
		 $('#areainfo option:first').attr('selected','selected');
		 $("#qstartTime").datebox("setValue","");  
		 $("#qendTime").datebox("setValue","");  
		 $('#shehePersonId2').combobox('clear');
			$('#shehePersonId2').combobox({
				url:'/tcweb/elevator/allUserByCompanyId',
			    valueField:'id',
			    textField:'userName'
			});
		 }

     function query(){
    	 var address=$('#addressinfo').attr("value");
    	 var buildingName=$('#buildingNameInfo').attr("value");
    	 var area=$('#areainfo option:selected').val();
    	 var qstartTime=$('#qstartTime').datebox("getValue");  
    	 var qendTime=$('#qendTime').datebox("getValue"); 

    	 var shehePersonId2=$('#shehePersonId2').combobox('getValue');

    	 if(!shehePersonId2)
    		 shehePersonId2 = 0;

 		/*	
    	 if(address==""){
        	 if(buildingName==""){
                if(area==""){
                	$.messager.alert('Warning','查询条件中地址，楼盘，所在区域不能全部为空');
                	return; 
                  }   
            	 }
        	 }
        */
    	 grid.datagrid("options").url='/tcweb/advertise/statisticquery';
         grid.datagrid("options").queryParams={'area':area,'address':address,'buildingName':buildingName,'qstartTime':qstartTime,'qendTime':qendTime,'shehePersonId2':shehePersonId2};
         $('#dtt').datagrid('reload');
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
<body class="easyui-layout">
<div region="north" style="overflow:hidden"> 
 <fieldset id="addDiv" style="width: 100%;margin:0px"><legend>查询条件</legend>
   <table> 
     <tr>  
   <td  nowrap>开始时间：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" style="width:152px;"></input></td>
   <td align="right" nowrap>结束时间：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime" style="width:152px;"></input></td>
   <td>人员：</td>
   <td nowrap><select id="shehePersonId2"  class="easyui-combobox" name="shehePersonId2" style="width:152px;"></select></td>
  </tr>
  <tr>
   <td nowrap>所在区域：</td>
   <td> 
   <select id="areainfo"   name="areainfo" style="width:153px;">
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
 <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="addressinfo" name="addressinfo" size="23" class="easyui-validatebox"></input></td>
   <td align="right" nowrap>楼盘：</td>
   <td nowrap><input id="buildingNameInfo" name="buildingName" size="24" class="easyui-validatebox"></input></td>
 <td colspan="2">
    <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>   
	<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 				
</td>
   </tr>
   </table>
 </fieldset>
</div> 
 <div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div style="margin-top:1px;">  
       <table id="dtt"></table>
   </div>  
        
    </div>  
</div>   

</body>
</html>