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

			tdetotalwindow =$('#tdetotal-window').window({  closed:true,draggable:false,modal:true }); 
			ztsetwindow =$('#ztsettingwindow').window({  closed:true,draggable:false,modal:true });  
			  grid=$('#lydltt').datagrid({
			 	    title:'两院粘贴电梯列表',
			 	    singleSelect:true,
				    striped:true,
			 	    pageSize:25,
			 	    pageList:[15,25,30,35,40],
			 	    url:'/tcweb/elevator/lyztstatisticslist',
			 	    queryParams:{},
			 	    columns:[[
						        {field:'ywCompanyName',align:'left',halign:'center',title:'单位名称',width:$(this).width() * 0.33,formatter: function(value,rec,index) {
							            var id = rec.id;
					               //     return  value+" "+"<img src='<%=request.getContextPath()%>/images/ywsetting.png' alt='发放标签总数设置'  style='cursor:hand;' onclick='issueetotal("+id+")'/>";
					                     return  '<a href="#" style="cursor:hand;text-decoration:none;color: #000000;" onclick="issueetotal('+id+')">'+value+'</a>';
							         }},
						        {field:'issueetotal',align:'center',title:'发放标签总数',width:$(this).width() * 0.33,formatter: function(value,rec,index) {
								            var id = rec.id;
								            if(value >0)
						                      return  value+" "+"<img src='<%=request.getContextPath()%>/images/tdetotal.png' title='发放标签'  style='cursor:hand;' onclick='pastereleaselist("+id+")'/>";
						                    else
							                  return value; 
								         }},
						        {field:'tdetotal',align:'center',title:'粘贴标签总数',width:$(this).width() * 0.33,formatter: function(value,rec,index) {
						            var id = rec.id;
						            if(value >0)
				                      return  value+" "+"<img src='<%=request.getContextPath()%>/images/tdetotal.png' title='粘贴标签总数'  style='cursor:hand;' onclick='tdetotal("+id+")'/>";
				                    else
					                  return value; 
						         }}
						        
						    ]], 
			 	   rowStyler:function(index,row){  
					    var  source = row.source; 
					       if( source == 0)
					    	return 'color:#ff0000;';
					    else
					    	return 'color:#000000;';
					    
				     },
			 	    pagination:true,
			 	   singleSelect:true
			 	   
			 	    
			 	});	
			 	
			 	$('#lydltt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});   
			    	  

	 });

	 var queryArea="";
	 function tdetotal(id){ 
			tdetotalwindow.window('open');
			gridtdetotal=$('#ttdetotal').datagrid({
			    title:'',
			    pageSize:10,
			    pageList:[10,20,30,40],
			    url:'/tcweb/elevator/lytdetotallistByOrder',
			    queryParams:{'ywCompanyId':id,'area':queryArea},
			    columns:[[
			        {field:'registNumber',title:'电梯编号↑↓',sortable : true,width:60},
			        {field:'registCode',title:'注册代码↑↓',sortable : true,width:135}, 
			        {field:'checkReportNum',title:'报告编号↑↓',sortable : true,width:135},
			        {field:'recordSate',title:'状态',formatter: function(value,rec,index) {
				        if(value == 3)
					        return "已入库";
				        else
					        return "已粘贴";
				        }},
			        {field:'subTime2',title:'上传时间↑↓',sortable : true,formatter: function(value,rec,index) {
				        	if(value)
			                     return value.substring(0,16);
			                 else
			                     return value;
					        }}
		         ]],
		         fitColumns:true,
		         nowrap:true,
			    pagination:true
			
		});
		     $('#ttdetotal').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,30,40]});  
			
		}

    function pastereleaselist(id){
    	companyId =id;
    	ztsetwindow.window('open');
    	gridpasterelease = $('#pasterelease').datagrid({
    	    title:'',
    	    url:'/tcweb/company/pastereleaselist',
    	    queryParams:{'id':companyId},
    	    columns:[[
    	        {field:'indexId',title:'序号',width:100},
    	        {field:'rcount',title:'数量',width:100},
    	        {field:'rhaoduan',title:'号段',width:200},
    	        {field:'receivor',title:'领取人',width:200},
    	        {field:'receiveTime',title:'领取日期',formatter: function(value,rec,index) {
    		        	if(value)
    	                     return value.substring(0,10);
    	                 else
    	                     return value;
    			        }}
             ]],
            fitColumns:true,
            nowrap:true
    	});
        }
	 
	 function clearQuery(){
			$('#areainfo option:first').attr('selected','selected');
		}


		var queryArea="";
		function query(){ 
			
		    var area=$('#areainfo option:selected').val();
		    queryArea =area;         //记录查询的时候用的区域
		     grid.datagrid("options").url='/tcweb/elevator/lyztstatisticsquery';
		     grid.datagrid("options").queryParams={'area':area};
		    $('#lydltt').datagrid('load');
			}
</script>
</head>
<body class="easyui-layout">
<!--
<div region="north" style="overflow:hidden">  
 <fieldset id="addDiv" style="width: 100%;margin:0px"><legend>查询条件</legend>
    
     <table> 
  
  <tr>  
 <td nowrap>所在区域：</td>
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
</td>
 <td colspan="2">
				<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 
			
					
</td>
					
   </tr>
   
     </table>
  
  </fieldset>
</div>  -->	
<div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div style="margin-top:1px;">  
       <table id="lydltt"></table>
   </div>  
        
    </div>  
</div> 
 <div id="tdetotal-window" title="粘贴标签记录" style="width:780px;height:450px;" >
   <div style="margin-top:1px;">  
       <table id="ttdetotal"></table>
   </div>
   </div>
   
  <div id="ztsettingwindow" title="发放标签数目" style="width:580px;height:350px;overflow-x:hidden;overflow-y:hidden;">
 <table id="pasterelease"></table>
 </div>
</body>
</html>