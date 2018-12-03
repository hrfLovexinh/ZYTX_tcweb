<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserRinghtInfo" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
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
	 var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	 if(r==null)
	 return false; 
	 var d= new Date(r[1], r[3]-1, r[4]); 
	 return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
	 }

	 var format = function(time, format){
		    var t = new Date(time);
		    var tf = function(i){return (i < 10 ? '0' : '') + i};
		    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
		        switch(a){
		            case 'yyyy':
		                return tf(t.getFullYear());
		                break;
		            case 'MM':
		                return tf(t.getMonth() + 1);
		                break;
		            case 'mm':
		                return tf(t.getMinutes());
		                break;
		            case 'dd':
		                return tf(t.getDate());
		                break;
		            case 'HH':
		                return tf(t.getHours());
		                break;
		            case 'ss':
		                return tf(t.getSeconds());
		                break;
		        }
		    })
		};

	$(function(){
		$.ajaxSetup ({
		    cache: false 
		});

		  <%if((role == 1 || role ==2)||(role == 22 || role ==23)) { %>  
		    comb72=$('#qtownshipStreets').combobox({
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

		  
		    comb102 =$('#areainfo').combobox({
				 onSelect: function (record) {
				 comb72.combobox({
		       url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.value),
		       valueField: 'id',
		       textField: 'companyName'
		   }).combobox('clear');

			     }
				});
		     <% } else if(role == 10 || role ==11){ %>
		     comb72=$('#qtownshipStreets').combobox({
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

		     winyw = $('#yw-window').window({  closed:true,draggable:false,modal:true });
             
		     grid=$('#tt').datagrid({
		 	    title:'注销电梯列表',
		 	    fitColumns:true,
		 	    striped:true,
		 	    pageSize:25,
		 	    pageList:[15,25,30,35,40],
		        url:'/tcweb/elevator/zxelevatorlistByOrder',
		 	    queryParams:{},
		 	    frozenColumns:[[
		            {field:'registNumber',align:'left',halign:'center',title:'电梯编号↑↓',sortable : true,width:$(this).width() * 0.076},
		            {field:'registCode',align:'left',halign:'center',title:'注册代码↑↓',sortable : true,width:$(this).width() * 0.076},
		            {field:'address',align:'left',halign:'center',title:'地址↑↓',sortable : true,width:$(this).width() * 0.076},
		            {field:'buildingName',align:'left',halign:'center',title:'楼盘名称',width:$(this).width() * 0.076}
		 	        	      ]],
		 	    columns:[[   
		 	        {field:'area',align:'center',title:'行政区划',width:$(this).width() * 0.076},
		 	        {field:'jdbCompanyName',align:'left',halign:'center',title:'街道办',width:$(this).width() * 0.076},
		 	        {field:'useNumber',align:'left',halign:'center',title:'单位内部编号',width:$(this).width() * 0.076},
		 	        {field:'nextInspectDate',align:'center',title:'下次检验日期',formatter: function(value,rec,index) {
		                 if(value){
		                     return value;
		                 }
		                 else{
		                       if(rec.inspectDate){ 
		                     	  var d = new Date(rec.inspectDate);   
		                     	  d.setYear(Number(d.getFullYear())+1);
		                 //    	  var nd=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();//新日期
		                           return format(d,'yyyy-MM-dd');
		                       }
		                       else
		                          return ""; 
		                     }
		 		         }},
		 	        {field:'wgCompanyName',align:'left',halign:'center',title:'使用单位',width:$(this).width() * 0.076},
		 	        {field:'ywCompanyName',align:'left',halign:'center',title:'维保单位',width:$(this).width() * 0.076},	
		 		    {field:'zzCompanyName',align:'left',halign:'center',title:'制造单位',width:$(this).width() * 0.076},
		 	        {field:'azCompanyName',align:'left',halign:'center',title:'安装单位',width:$(this).width() * 0.076},
		 	        {field:'"详情"',align:'center',title:'维保记录',align:'center',formatter: function(value,rec,index) {
			        	 var registNumber = ''+rec.registNumber;
			  			 return "<img src='<%=request.getContextPath()%>/images/yulan.png' title='查看' style='cursor:hand;' onclick='openYwDetail("+"\""+registNumber+"\""+")'/>";       
				         }}
		 	    ]],
		 	    pagination:true,
		 	   singleSelect:true
		     });	
		 	
		 	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});       	  
		 });

	function clearQuery(){
		$('#registNumber').attr("value","");
		$('#addressinfo').attr("value","");
		$('#qbuildingName').attr("value","");
		$('#registCodeinfo').attr("value","");

	     <%if((role == 1 || role ==2) || (role ==22 || role ==23)) {%>
		$('#areainfo').combobox('clear');
		<% }%>

		 <% if (role != 20 && role != 21) { %>
		$('#qtownshipStreets').combobox('clear');
		<% } %>	
	}

	function query(){  
		var registNumber=$('#registNumber').attr("value");
		var address=$('#addressinfo').attr("value");
		var qbuildingName=$('#qbuildingName').attr("value");
		var registCode =$('#registCodeinfo').attr("value");
	 
	    <%if((role == 1 || role ==2) || (role ==22 || role ==23)) {%>
	    var area =$('#areainfo').combobox('getValue'); 
	    <% } else {%>
	    var area ="";
	    <% }%>

	    <% if (role == 20 || role == 21) { %>
	    var qtownshipStreets = "";
	    <%} else {%>
	    var qtownshipStreets =$('#qtownshipStreets').combobox('getValue'); 
	    <% }%>
	 

	    if(qtownshipStreets==""){
	    	qtownshipStreets = 0;
	        }
	   
		
	    grid.datagrid("options").url='/tcweb/elevator/queryZXByOrder';
	    grid.datagrid("options").queryParams={'registNumber':registNumber,'area':area,'address':address,'buildingName':qbuildingName,'registCode':registCode,'townshipStreets':qtownshipStreets};
		
	    $('#tt').datagrid('reload');
		}

	function openYwDetail(registNumber){
		winyw.window('open'); 
		 gridyw=$('#ywttmap').datagrid({
		 	    title:'',
		 	    pageSize:15,
		 	    pageList:[15,20,25,30,35,40],
		 	    url:'/tcweb/yw/zxywlistByreg',
		 	    queryParams:{'registNumber':registNumber},
		 	   columns:[[
		 		        {field:'registNumber',title:'电梯编号',width:60},
		 		        {field:'address',title:'地址',width:160},
		 		        {field:'ywKind',title:'种类',width:50},
		 		        {field:'maintainTypecode',title:'类型',width:50},
		 		        {field:'startTime',title:'开始时间',width:120},
		 		        {field:'endTime',title:'结束时间',width:120},
		 		        {field:'dateSpan',title:'时长（分钟）',width:70},
		 		        {field:'sPosition',title:'开始位置',width:55},
		 		        {field:'ePosition',title:'结束位置',width:55},
		 		        {field:'userName',title:'维保人员',width:55},
		 		        {field:'companyName',title:'维保单位',width:150},
		 		        {field:'subTime',title:'上传时间',formatter: function(value,rec,index) {
		 	                 if(value)
		 	                     return value.substring(0,16);
		 	                 else
		 	                     return value;
		 			         }}
		 		    ]],
		          nowrap:true,
		 	    pagination:true
		 	
		 });

		     $('#ywttmap').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  
		}
		 	    
	</script>	
</head>
<body class="easyui-layout" data-options="fit:true">
 <div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">  
 <fieldset id="addDiv" style="width: 100%;margin:0px"><legend>查询条件</legend>
    
     <table> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
   <td nowrap><input id="registNumber" name="registNumber" size="15" class="easyui-validatebox"></input></td>
   <td nowrap>注册代码：</td>
 <td nowrap><input id="registCodeinfo" name="registCodeinfo" size="24" class="easyui-validatebox"></input></td>
    <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="addressinfo" name="addressinfo" size="24" class="easyui-validatebox"></input></td>
    <td align="right" nowrap>楼盘名称：</td> 
   <td nowrap><input id="qbuildingName" name="qbuildingName" size="24" class="easyui-validatebox"></input></td>
 
   </tr>  
  <tr>
  <% if((role == 1 || role ==2) || (role == 22 || role ==23)){ %> 
 <td nowrap>行政区划：</td>
   <td>
   <select id="areainfo"   name="areainfo" style="width:100px;">
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
   <!--  <option value="安仁">安仁</option> -->
   <!-- <option value="大丰">大丰</option>  -->
</select>
</td>
<% }    %>  
 <% if (role != 20 && role != 21) { %>
 <td>乡镇街道办：</td>
 <td><select id="qtownshipStreets"  class="easyui-combobox" name="qtownshipStreets" style="width:154px;"></select></td>
 <% } %>

 <td colspan="2">
				<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 
					
					
</td>
					
   </tr>
   
     </table>
  
  </fieldset>
</div> 
   <div region="center" style="width:100%;">
       <div id="main-center"  fit="true" border="false">  
        
          <div style="margin-top:1px;">  
       <table id="tt"></table>
   </div>  
        
    </div>  
</div>
<div id="yw-window" title="维保信息" style="width:780px;height:500px;">
        <table id="ywttmap"></table>  
   </div>
</body>
</html>