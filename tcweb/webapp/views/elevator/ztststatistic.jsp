<%@ page import="com.zytx.models.UserInfo,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<!--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"> -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">
 <style>
    .subtotal { font-weight: bold; }/*合计单元格样式*/
    .datagrid-header-row td{background-color:rgb(226,237,255);color:#0E2D5F;}
  </style>
<% 
String cityName = GlobalFunction.cityName;
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int  role = 0; 
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
//	 var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
//   var r = str.match(/^(((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\d):[0-5]?\d:[0-5]?\d)$/);
	 var r = str.match(/^[1-9][0-9]{3}-(0?[1-9]|1[0|1|2])-(0?[1-9]|[1|2][0-9]|3[0|1])\s(0?[1-9]|1[0-9]|2[0-3]):(0?[0-9]|[1|2|3|4|5][0-9])$/);
	 if(r==null)
	 return false; 
	/* var d= new Date(r[1], r[3]-1, r[4]); 
	 return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);*/
	 return true;
	 }

	 $(function(){

			$.ajaxSetup ({
			    cache: false 
			});

			
			var url = encodeURI(encodeURI('/tcweb/elevator/getAutoYwCompanyList4')); 
			 $("#ywCompanyIdinfo").autocomplete(  
			            url,  
			            {  
			            scroll: false,  
			                matchContains: true,  
			                width: 188,  
			                minChars: 2, 
			                max:20, 
			                scrollHeight: 100,
			                extraParams: {	q: function() {
							return $("#this").val();
						    }},   
			                dataType: "json",  
			                mustMatch:false,  
			                parse: function(data) {  
			                    var rows = [];  
			                    for(var i=0; i<data.length; i++){  
			                     rows[rows.length] = {   
			                       data:data[i].id +"-"+data[i].companyName,   
			                       value:data[i].id,   
			                       result:data[i].companyName   
			                       };   
			                     }  
			                  return rows;  
			                    },  
			                formatItem: function(row, i, n) {  
			                    return row;  
			                },
			                formatResult: function(row){return row.id; }    
			            }  
			        ).result(function(event, data, formatted) {
			        	
			            if(data)
			            {
			        	 $('#ywCompanyIdinfo2').attr("value",formatted);
			            }
			            else{
			             $('#ywCompanyIdinfo2').attr("value",'');
			 	            }
			        });
		        
			 $('#areainfo').combobox({   
			        url:'/tcweb/elevator/areaInfoList',   
			        valueField:'area',   
			        textField:'area'  
			    });  
						
			
			ztsetwindow =$('#ztsettingwindow').window({  closed:true,draggable:false,modal:true });   
			tdetotalwindow =$('#tdetotal-window').window({  closed:true,draggable:false,modal:true }); 
			pretdetotalwindow =$('#pretdetotal-window').window({  closed:true,draggable:false,modal:true }); 
			ztworkerwindow =$('#ztworker-window').window({  closed:true,draggable:false,modal:true });
			utdetotalwindow =$('#utdetotal-window').window({  closed:true,draggable:false,modal:true }); 
			upretdetotalwindow =$('#upretdetotal-window').window({  closed:true,draggable:false,modal:true }); 
			pastereleaseInfowindow =$('#pastereleaseInfo').window({  closed:true,draggable:false,modal:true }); 
	//		$('#btn-save').linkbutton();
	        form = pastereleaseInfowindow.find('form');
			grid=$('#ztstatisticstt').datagrid({
			    title:'粘贴统计列表',
			    fitColumns:true,
		 	    striped:true,
			    pageSize:25,
			    pageList:[15,25,30,35,40],
			    url:'/tcweb/elevator/ztstatisticslist',
			    queryParams:{},
			    columns:[[
			        {field:'ywCompanyName',align:'left',halign:'center',title:'单位名称',width:$(this).width() * 0.2,formatter: function(value,rec,index) {
				            var id = rec.id;
		               //     return  value+" "+"<img src='<%=request.getContextPath()%>/images/ywsetting.png' alt='发放标签总数设置'  style='cursor:hand;' onclick='issueetotal("+id+")'/>";
		                     return  '<a href="#" style="cursor:hand;text-decoration:none;color: #000000;" onclick="issueetotal('+id+')">'+value+'</a>';
				         }},
			        {field:'issueetotal',align:'center',title:'发放标签总数',width:$(this).width() * 0.2},
			        {field:'tdetotal',align:'center',title:'粘贴标签总数',width:$(this).width() * 0.2,formatter: function(value,rec,index) {
			            var id = rec.id;
			            if(value >0)
	                      return  value+" "+"<img src='<%=request.getContextPath()%>/images/tdetotal.png' alt='粘贴标签总数'  style='cursor:hand;' onclick='tdetotal("+id+")'/>";
	                    else
		                  return value; 
			         }},
			        {field:'pretdetotal',align:'center',title:'上个时间段标签粘贴总数',width:$(this).width() * 0.2,formatter: function(value,rec,index) {
				            var id = rec.id;
				            if(value >0)
		                      return  value+" "+"<img src='<%=request.getContextPath()%>/images/tdetotal.png' alt='上个时间段标签粘贴'  style='cursor:hand;' onclick='pretdetotal("+id+")'/>";
		                    else
			                  return value; 
				         }},
			        {field:'eywtotal',align:'center',title:'粘贴人员',width:$(this).width() * 0.2,formatter: function(value,rec,index) {
			        	       var id = rec.id;
			        	       if(value =='')
				        	       return '';
			        	       else
		                       return  "<img src='<%=request.getContextPath()%>/images/ztworker.png' alt='当前超期'  style='cursor:hand;' onclick='ztworker("+id+")'/>";
			             }
				     }
			    ]], 
			//  rowStyler:function(index,row){ {return 'color:#ff0000;';}} , 
			    pagination:true,
			    singleSelect:true,
			    onLoadSuccess:function() {  
		            //添加“合计”列
		            $('#ztstatisticstt').datagrid('appendRow', { 
		            	ywCompanyName:'<span class="subtotal">'+'合计'+ '</span>',
		                issueetotal: '<span class="subtotal">'+ compute("issueetotal") + '</span>',
		                tdetotal: '<span class="subtotal">'+ compute("tdetotal") + '</span>',
		                pretdetotal: '<span class="subtotal">'+ compute("pretdetotal") + '</span>',
		                eywtotal: ''  
		            });  
		        //    var indexpage = $('#ztstatisticstt').datagrid('getPager');
		        //    alert(indexpage);
		            
		        }	    
			});	
			$('#ztstatisticstt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
		 	  	  
		}
		);

	 //指定列求和
function compute(colName) { 
     var rows = $('#ztstatisticstt').datagrid('getRows');
     var total = 0;
     for (var i = 0; i < rows.length; i++) {
         total += parseFloat(rows[i][colName]);
        }
     return total;
     }
	 
var companyId;
function issueetotal(id){
	companyId =id;
	ztsetwindow.window('open');
	gridpasterelease = $('#pasterelease').datagrid({
	    title:'',
	    url:'/tcweb/company/pastereleaselist',
	    queryParams:{'id':companyId},
	    columns:[[
	        {field:'indexId',title:'序号',width:80},
	        {field:'rcount',title:'数量',width:100},
	        {field:'rhaoduan',title:'号段'},
	        {field:'receivor',title:'领取人',width:100},
	        {field:'receiveTime',title:'领取日期',formatter: function(value,rec,index) {
		        	if(value)
	                     return value.substring(0,10);
	                 else
	                     return value;
			        }}
         ]],
        fitColumns:true,
        singleSelect:true,
	    striped:true,
        nowrap:true
        <% if(role!=20 && role!=21){%>
        ,
        toolbar:[{
	        text:'新增',
	        iconCls:'icon-add',
	        handler:function(){
	       
        	pastereleaseInfowindow.window('open');  
        	$('#companyId').attr("value",companyId);
	    //	form.form('clear');     
	    	form.url ='/tcweb/company/pastereleaseadd';	
	    		 
	        }
	    },{
	        text:'删除',
	        iconCls:'icon-cut',
	        handler:function(){
	    	 var row = gridpasterelease.datagrid('getSelected'); 
	    	 if(row){
	    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
	    	 jQuery.post('/tcweb/company/pastereleasedelete',
	    	    	 {'id':row.id,'companyId':row.companyId},
	    	    	 function(data){
	    	    		eval("data="+"'"+data+"'");  
	    	    		if("success"==data){
	    	    		//	$.messager.alert("操作成功",'谢谢');
	    	    		 $.messager.show({   
	    			 title:'提示信息',
	    			 timeout:1000,
	    			 msg:'操作成功，谢谢。' 
	    		 });  	
	    	    		 gridpasterelease.datagrid('reload');
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
	    	var row = gridpasterelease.datagrid('getSelected');   
	    	if (row){
	    		 pastereleaseInfowindow.window('open');    		
	    		 form.form('load', '/tcweb/company/pastereleaseedit/'+row.id);
	    		 form.url = '/tcweb/company/pastereleaseupdate/'+row.id; 
	    	 } else {  
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
}

function saveIssueetotal(){
	 jQuery.post('/tcweb/company/updateIssueetotalById',
	    	 {'id':companyId,'issueetotal':$('#issueetotal').attr("value")},
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
	    			$.messager.alert('操作失败','发放标签数目更新失败','error');
    	    		}
    	       });
	 ztsetwindow.window('close');
}

function nsaveIssueetotal(){
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){ 

		var pattern = /((\d{6}-\d{6},)*(\d{6}-\d{6}))$/;
		var str =$('#rhaoduan').attr("value");
	    if(pattern.test(str)) 
		  return form.form('validate');
		else{
		  $.messager.alert('操作失败', '标签号段格式为<br>xxxxxx-xxxxxx <br> 多个号段，中间用逗号隔开<br>xxxxxx-xxxxxx,xxxxxx-xxxxxx', 'error');
		  return false;	
		  }
		
		
	},
	success : function(data) {
		eval("data=" + "'" + data + "'");
		if ("success" == data) {
		$.messager.show( {
			title : '提示信息',
			timeout : 1000,
			msg : '操作成功，谢谢。'
		});
		testDid="";
		gridpasterelease.datagrid('reload');
		pastereleaseInfowindow.window('close');
		grid.datagrid('reload');
	} else {
		$.messager.alert('操作失败', '添加记录', 'error');
	}
   }
	});
}


function tdetotal(id){ 
	tdetotalwindow.window('open');
	gridtdetotal=$('#ttdetotal').datagrid({
	    title:'',
	    pageSize:10,
	    pageList:[10,20,30,40],
	    url:'/tcweb/elevator/tdetotallist',
	    queryParams:{'ywCompanyId':id,'area':queryArea},
	    columns:[[
	        {field:'registNumber',title:'电梯编号',width:100,formatter: function(value,rec,index) {
            	<% if("1".equals(cityName)){ %>
                return "N"+value;
                <% } else {%>
                return value;
                <% }%>
           }},
	        {field:'registCode',title:'注册代码',width:100},
	        {field:'address',title:'地址',width:200},
	        {field:'buildingName',title:'楼盘',width:200},
	        {field:'recordSate',title:'状态',formatter: function(value,rec,index) {
		        if(value == 3)
			        return "已入库";
		        else
			        return "已粘贴";
		        }},
	        {field:'subTime2',title:'上传时间',formatter: function(value,rec,index) {
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

function pretdetotal(id){
	pretdetotalwindow.window('open');
	 var qstartTime=$('#qstartTime').datebox("getValue");  
     var qendTime=$('#qendTime').datebox("getValue");  
    gridpretdetotal=$('#tpretdetotal').datagrid({
    title:'',
    pageSize:10,
    pageList:[10,20,30,40],
    url:'/tcweb/elevator/pretdetotallist',
    queryParams:{'ywCompanyId':id,'qstartTime':qstartTime,'qendTime':qendTime},
    columns:[[
        {field:'registNumber',title:'电梯编号',width:100,formatter: function(value,rec,index) {
        	<% if("1".equals(cityName)){ %>
            return "N"+value;
            <% } else {%>
            return value;
            <% }%>
       }},
        {field:'registCode',title:'注册代码',width:100},
        {field:'address',title:'地址',width:200},
        {field:'buildingName',title:'楼盘',width:200},
        {field:'recordSate',title:'状态',formatter: function(value,rec,index) {
	        if(value == 3)
		        return "已入库";
	        else
		        return "已粘贴";
	        }},
        {field:'subTime2',title:'上传时间',formatter: function(value,rec,index) {
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
 $('#tpretdetotal').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,30,40]});  

}

function ztworker(id){
	 companyId =id;
	 ztworkerwindow.window('open');
	 gridztworker=$('#tztworker').datagrid({
		    title:'',
		    pageSize:10,
		    pageList:[10,20,30,40],
		    url:'/tcweb/elevator/ztworkerlist',
		    queryParams:{'ywCompanyId':id},
		    columns:[[
		        {field:'loginName',title:'账号',width:100},
		        {field:'userName',title:'姓名',width:200},
		        {field:'utdetotal',title:'粘贴总数',width:200,formatter: function(value,rec,index) {
		            var id = rec.userid;
		            if(value >0)
                      return  value+" "+"<img src='<%=request.getContextPath()%>/images/tdetotal.png' alt='粘贴标签总数'  style='cursor:hand;' onclick='utdetotal("+id+")'/>";
                    else
	                  return value; 
		         }},
		        {field:'upretdetotal',title:'时间段粘贴总数',formatter: function(value,rec,index) {
			            var id = rec.userid;
			            if(value >0)
	                      return  value+" "+"<img src='<%=request.getContextPath()%>/images/tdetotal.png' alt='上个时间段标签粘贴'  style='cursor:hand;' onclick='upretdetotal("+id+")'/>";
	                    else
		                  return value; 
			         }}
		     ]],
		     fitColumns:true,
		     nowrap:true,
		    pagination:true

		});
		 $('#tztworker').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,30,40]});  
			
}

function utdetotal(id){ 
	utdetotalwindow.window('open');
	gridutdetotal=$('#tutdetotal').datagrid({
	    title:'',
	    pageSize:10,
	    pageList:[10,20,30,40],
	    url:'/tcweb/elevator/utdetotallist',
	    queryParams:{'userid':id},
	    columns:[[
	        {field:'registNumber',title:'电梯编号',width:100,formatter: function(value,rec,index) {
            	<% if("1".equals(cityName)){ %>
                return "N"+value;
                <% } else {%>
                return value;
                <% }%>
           }},
	        {field:'registCode',title:'注册代码',width:100},
	        {field:'address',title:'地址',width:200},
	        {field:'buildingName',title:'楼盘',width:200},
	        {field:'recordSate',title:'状态',formatter: function(value,rec,index) {
		        if(value == 3)
			        return "已入库";
		        else
			        return "已粘贴";
		        }},
	        {field:'subTime2',title:'上传时间',formatter: function(value,rec,index) {
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
     $('#tutdetotal').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,30,40]});  
	
}


var puserid =0;
function upretdetotal(id){
	puserid =id;
	upretdetotalwindow.window('open');
	 var qstartTime=$('#uqstartTime').datebox("getValue");  
     var qendTime=$('#uqendTime').datebox("getValue");  
    gridupretdetotal=$('#tupretdetotal').datagrid({
    title:'',
    pageSize:10,
    pageList:[10,20,30,40],
    url:'/tcweb/elevator/upretdetotallist',
    queryParams:{'userid':id,'qstartTime':qstartTime,'qendTime':qendTime},
    columns:[[
        {field:'registNumber',title:'电梯编号',width:100,formatter: function(value,rec,index) {
        	<% if("1".equals(cityName)){ %>
            return "N"+value;
            <% } else {%>
            return value;
            <% }%>
       }},
        {field:'registCode',title:'注册代码',width:100},
        {field:'address',title:'地址',width:200},
        {field:'buildingName',title:'楼盘',width:200},
        {field:'recordSate',title:'状态',formatter: function(value,rec,index) {
	        if(value == 3)
		        return "已入库";
	        else
		        return "已粘贴";
	        }},
        {field:'subTime2',title:'上传时间',formatter: function(value,rec,index) {
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
 $('#tupretdetotal').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,30,40]});  

}


function clearQuery(){
	$('#ywCompanyIdinfo').attr("value","");
	$('#ywCompanyIdinfo2').attr("value","");
	$("#qstartTime").datebox("setValue","");  
	$("#qendTime").datebox("setValue","");  
//	$('#areainfo option:first').attr('selected','selected');
	$('#areainfo').combobox('clear');
}


var queryArea="";
function query(){ 
	var ywCompanyId=$('#ywCompanyIdinfo2').attr("value"); 
    if (!ywCompanyId){
    	ywCompanyId =0;
    	} 
    var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue");    
  //  var area=$('#areainfo option:selected').val();
      var area =$('#areainfo').combobox('getValue'); 
    queryArea =area;         //记录查询的时候用的区域
     grid.datagrid("options").url='/tcweb/elevator/ztstatisticsquery';
     grid.datagrid("options").queryParams={'companyId':ywCompanyId,'qstartTime':qstartTime,'qendTime':qendTime,'area':area};
   // $('#ztstatisticstt').datagrid('reload');
    $('#ztstatisticstt').datagrid('load');
	}


function uclearQuery(){
	$("#uqstartTime").datebox("setValue","");  
	$("#uqendTime").datebox("setValue","");  
}

function uquery(){
	
    var qstartTime=$('#uqstartTime').datebox("getValue");  
	var qendTime=$('#uqendTime').datebox("getValue");     
	gridztworker.datagrid("options").url='/tcweb/elevator/ztworkerlistquery';
	gridztworker.datagrid("options").queryParams={'companyId':companyId,'qstartTime':qstartTime,'qendTime':qendTime};
    
    $('#tztworker').datagrid('reload');
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);height:80px;">  
 <fieldset id="addDiv" style="margin:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
     <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%"> 
    <tr>
    <td nowrap align="right">行政区划：</td>
    <!-- 
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
</select>
</td> -->
    <td><input id="areainfo" name="areainfo" style="height:25px;"/></td>
    <td nowrap align="right">单位名称：</td>
    <td><input id="ywCompanyIdinfo" style="height:25px;"  placeholder="输入至少两个关键字从下拉列表中选择"></input>
  <input type ="hidden" id="ywCompanyIdinfo2"></input>
   </td>
   <td  nowrap align="right">开始时间：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" style="height:25px;"></input></td>
   <td align="right" nowrap>结束时间：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime" style="height:25px;"></input></td>
   
   <td colspan="2">

		<!--  <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> -->		
			<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
            <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>	
					
</td>
					
   </tr>
   
     </table>
  
  </fieldset>
</div> 
 <div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div>  
       <table id="ztstatisticstt"></table>
   </div>  
        
    </div>  
</div> 
 <div id="ztsettingwindow" title="发放标签数目" style="width:780px;height:350px;">
 <table id="pasterelease"></table>
 </div>
   
  
   
   <div id="tdetotal-window" title="粘贴标签记录" style="width:780px;height:450px;" >
   <div style="margin-top:1px;">  
       <table id="ttdetotal"></table>
   </div>
   </div>
   
    <div id="pretdetotal-window" title="上个时段粘贴标签记录" style="width:780px;height:450px;" >
   <div style="margin-top:1px;">  
       <table id="tpretdetotal"></table>
   </div>
   </div>
   
   
   
    <div id="ztworker-window" title="人员粘贴标签记录" style="width:780px;height:450px;"   class="easyui-layout">
    
   <div region="north" style="overflow:hidden">  
 <fieldset id="addDiv" style="width: 100%;margin:0px"><legend>查询条件</legend>
    
     <table border="0"> 
    <tr>
   <td  nowrap>开始时间：</td>
   <td><input id="uqstartTime"  type="text" class="easyui-datebox" name="uqstartTime" style="width:152px;"></input></td>
   <td align="right" nowrap>结束时间：</td>
   <td><input id="uqendTime"  type="text" class="easyui-datebox" name="uqendTime" style="width:152px;"></input></td>
   
   <td colspan="2">

				<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="uquery()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="uclearQuery()">清空</a> 
			<!--		<input type="submit" value="Submit Comment" /> -->	
					
</td>
					
   </tr>
   
     </table>
  
  </fieldset>
</div> 
    
    <div region="center">
   <div style="margin-top:1px;">  
       <table id="tztworker"></table>
   </div>
   </div>
   </div>
   
    <div id="utdetotal-window" title="粘贴标签记录" style="width:780px;height:450px;" >
   <div style="margin-top:1px;">  
       <table id="tutdetotal"></table>
   </div>
   </div>
   
    <div id="upretdetotal-window" title="上个时段粘贴标签记录" style="width:780px;height:450px;" >
   <div style="margin-top:1px;">  
       <table id="tupretdetotal"></table>
   </div>
   
   </div>
   
    <div id="pastereleaseInfo" title="详细信息" style="width:500px;height:250px;overflow-x:hidden;overflow-y:hidden;">
   <form method="post" id="form">
   <table>
     <tr> 
      <td align="right">发放标签数目：</td> 
       <td><input id="rcount"  type="text" class="easyui-validatebox" data-options="required:true" name="rcount"  style="width:152px;"></input></td>
    </tr>
    <tr> 
      <td align="right">发放标签号段：</td> 
       <td><input id="rhaoduan"  type="text"  name="rhaoduan"  class="easyui-validatebox" data-options="required:true,validType:'length[1,2000]'"  style="width:152px;"></input></td>
    </tr>
     <tr> 
      <td align="right">领取人：</td> 
       <td><input id="receivor"  type="text"  name="receivor"  class="easyui-validatebox" data-options="required:true" style="width:152px;"></input></td>
    </tr>
     <tr> 
      <td align="right">领取日期：</td> 
       <td><select id="receiveTime" class="easyui-datebox" type="text"  name="receiveTime"  style="width:152px;" data-options="editable:false,required:true"></select></td>
       <td><input id="companyId"  type="hidden" name="companyId"></input></td>
    </tr>
   <tr>
   <td  align="right">
    
    <a href="#" class="easyui-linkbutton" icon="icon-ok" onclick="nsaveIssueetotal()">确定</a>  
   </td>
   </tr>
     </table>
    </form>
   </div>
</body>
</html>