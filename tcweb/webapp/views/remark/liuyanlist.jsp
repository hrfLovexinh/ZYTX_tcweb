<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserRinghtInfo,com.zytx.init.GlobalFunction" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全服务平台</title>
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"> -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">

<script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script> 
 <style>
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
//	 var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
//   var r = str.match(/^(((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\d):[0-5]?\d:[0-5]?\d)$/);
	 var r = str.match(/^[1-9][0-9]{3}-(0?[1-9]|1[0|1|2])-(0?[1-9]|[1|2][0-9]|3[0|1])\s(0?[1-9]|1[0-9]|2[0-3]):(0?[0-9]|[1|2|3|4|5][0-9])$/);
	 if(r==null)
	 return false; 
	/* var d= new Date(r[1], r[3]-1, r[4]); 
	 return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);*/
	 return true;
	 }



var opt =0; //0:增加 ；1：编辑
$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	 var inputWidth = $('#registNumber').width(); 
	 $("#areainfo").css("width", inputWidth);      
	 $("#qtownshipStreets").css("width", inputWidth);
	 $("#remarkLevel").css("width", inputWidth);

	 $('#qstartTime').datebox({     
	       width:inputWidth 
	   }); 

	 $('#qendTime').datebox({     
	       width:inputWidth 
	   }); 

	combt=$('#qtownshipStreets').combobox({
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

	    /*
	  comba =$('#areainfo').combobox({
			 onSelect: function (record) {
			 combt.combobox({
	     url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.value),
	     valueField: 'id',
	     textField: 'companyName'
	 }).combobox('clear');
		     }
			});  */
	<%if((role == 1 || role ==2)||(role == 22 || role ==23)) { %> 
		$('#areainfo').combobox({   
			      url:'/tcweb/elevator/areaInfoList',   
			      valueField:'area',   
			      textField:'area'  
			  });  

		comba =$('#areainfo').combobox({
					 onSelect: function (record) {
				     combt.combobox({
			 //    url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.areaCode),
			     url: '/tcweb/elevator/getjdbCompanyListByNewarea?companyArea='+encodeURI(record.area),
			     valueField: 'id',
			     textField: 'companyName'
			 }).combobox('clear');
			  }
				});
		 <% } else if(role == 10 || role ==11){ %>
		  combt=$('#qtownshipStreets').combobox({
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
				
	replywin = $('#reply-window').window({  closed:true,draggable:false,modal:true });
    $('#btn-save').linkbutton(); 		
	grid=$('#liuyantt').datagrid({
	    title:'留言信息列表',
	    fitColumns:false,
 	    striped:true,
	    pageSize:25,
	    pageList:[15,25,30,35,40],
	    url:'/tcweb/remark/liuyanlist',
	    queryParams:{},
	    frozenColumns:[[
	              {field:'registNumber',align:'left',halign:'center',title:'电梯编号',width:80,formatter: function(value,rec,index) {
	                	<% if("1".equals(cityName)){ %>
                        return "N"+value;
                        <% } else {%>
                        return value;
                        <% }%>
	                }},
	        	  {field:'address',align:'left',halign:'center',title:'地址',width:200},
	        	  {field:'buildingName',align:'left',halign:'center',title:'楼盘名称',width:140} 	  
	                  ]],
	    columns:[[
            {field:'remarkDate',align:'center',halign:'center',title:'留言日期',width:140,formatter: function(value,rec,index) {
               if(value!="0")
                  return value.substring(0,16);
               }},
           {field:'process_time',align:'center',halign:'center',title:'运维回复日期',width:140,formatter: function(value,rec,index) {
    	       if(value!=null)
               return value.substring(0,16);
	         }},
          {field:'remarkInfo',align:'left',halign:'center',title:'留言内容',width:200},
          {field:'process_remark',align:'left',halign:'center',title:'运维回复',width:160,formatter:function(value,rec,index){
             var id =rec.id;
           if("" != value && value != null)
	          return value+" "+"<img src='<%=request.getContextPath()%>/images/replylist.png' title='所有回复'  style='cursor:hand;display:block;float:right' onclick='replylist("+id+")'/>";
	        else
		      return "";
           }},    
		    {field:'remarkLevel',align:'center',title:'满意度',align:'center',width:60,formatter: function(value,rec,index) {
                if(value=="0")
                    return "满意";
                if(value=="1")
                    return "一般";
                if(value=="2")
                    return "不满意";
                if(value=="3")
                    return "投诉";
		         }},
		    {field:'warnTelephone',align:'left',halign:'center',title:'留言电话',width:100}, 
	        {field:'area',align:'center',title:'行政区划',width:60},
	        {field:'jdbCompanyName',align:'left',halign:'center',title:'街道办',width:100},
	        {field:'wgCompanyName',align:'left',halign:'center',title:'使用公司',width:100},
	        {field:'userName',align:'left',halign:'center',title:'维保人员',width:60},
	        {field:'telephonemobile',align:'left',halign:'center',title:'维保人员手机',width:100},
	        {field:'companyName',align:'left',halign:'center',title:'运维公司',width:200},
	        {field:'checkadvice',align:'left',halign:'center',title:'质监处理意见',width:200},
	        {field:'checkadviceTime',align:'left',halign:'center',title:'质监处理意见时间',width:120,formatter: function(value,rec,index) {
		    	 if(value!=null)
		               return value.substring(0,16);
			         }}
	    ]],
	    rowStyler:function(index,row){  //暂时取消颜色超期
		    var process_type = row.process_type;
		    var remarkLevel = row.remarkLevel;
		    if(process_type == 0 && remarkLevel =="3")
		    	return 'color:#ff0000;'; 
	     },
	    pagination:true,
	    singleSelect:true,
	    toolbar:[
	             <% if(role==22 || role==23){%>
	     	     	    {
	     	     	        text:'删除',
	     	     	        iconCls:'icon-cut',
	     	     	        handler:function(){
	     	     	    	 var row = grid.datagrid('getSelected'); 
	     	     	    	if(row){
	     	     		    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
	     	     		    	 jQuery.post('/tcweb/remark/remarkDelete',
	     	     		    	    	 {'id':row.id,'registNumber':row.registNumber},
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
	     	     	    },'-',
	     	     	  {
	     	     	        text:'处理意见',
	     	     	        iconCls:'icon-back',
	     	     	        handler:function(){
	     	     	    	 var row = grid.datagrid('getSelected'); 
	     	     	    	if(row){
	     	     	    		 remarkZjReply();
	     	     		    	 }
	     	     	  	       else{
	     	     		    		 $.messager.show({   
	     	     		    			 title:'警告',
	     	     		    			 msg:'请先选择要回复的记录行。' 
	     	     		    		 });   
	     	     			     }
	     	     	    }
	            	  }  
	     	     	   <% } if((role ==10 || role == 11)) {%>
	     	     	 {
	     	     	        text:'处理意见',
	     	     	        iconCls:'icon-back',
	     	     	        handler:function(){
	     	     	    	 var row = grid.datagrid('getSelected'); 
	     	     	    	if(row){
	     	     	    		 remarkZjReply();
	     	     		    	 }
	     	     	  	       else{
	     	     		    		 $.messager.show({   
	     	     		    			 title:'警告',
	     	     		    			 msg:'请先选择要回复的记录行。' 
	     	     		    		 });   
	     	     			     }
	     	     	    }
	            	  }  
	            	  <%}%>
	     	     	    ]
	         	});		    	    
	$('#liuyantt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
 	  	  
}
);

function remarkZjReply(){	 
	 $('#remarkZjReplyWin').window('open');
	 
}

function saveremarkZjReply(){ 
	 var row = grid.datagrid('getSelected'); 
	var checkadvice =$('#checkadvice').val();   
	jQuery.post('/tcweb/remark/remarkzjReply',
 	    	 {'id':row.id,'checkadvice':checkadvice},
 	    	 function(data){
 	    		eval("data="+"'"+data+"'");  
 	    		if("success"==data){
 	    		 $.messager.show({   
 			 title:'提示信息',
 			 timeout:1000,
 			 msg:'操作成功，谢谢。' 
 		       });  
 	    		   $('#remarkZjReplyWin').window('close');	
	    	       grid.datagrid('reload');
 	    		}
 	    		else{
 	    			$.messager.alert('操作失败','回复失败','error');
	    	    		}
	    	       });
}

function clearQuery(){
	$('#registNumber').attr("value","");
	$('#buildingName').attr("value","");
	$('#address').attr("value","");
	$("#qstartTime").datebox("setValue","");  
	$("#qendTime").datebox("setValue","");  
	 <%if(role !=10 && role != 11){ %> 
	$('#areainfo').combobox('clear');
	 <% } %>
	$('#qtownshipStreets').combobox('clear'); 
	$('#remarkLevel option:first').attr('selected','selected');
}

function query(){  
	var registNumber=$('#registNumber').attr("value");
	var buildingName=$('#buildingName').attr("value");  
	var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue"); 
	var area = "";
	  <%if(role !=10 && role != 11){ %>
	var area =$('#areainfo').combobox('getValue');     
	<% } %>
	var address =$('#address').attr("value");     
    var qtownshipStreets =$('#qtownshipStreets').combobox('getValue'); 
    if(qtownshipStreets==""){
    	qtownshipStreets = 0;
        }
    var remarkLevel =$('#remarkLevel option:selected').val();
	
     grid.datagrid("options").url='/tcweb/remark/query';
     grid.datagrid("options").queryParams={'registNumber':registNumber,'remarkLevel':remarkLevel,'buildingName':buildingName,'address':address,'area':area,'qstartTime':qstartTime,'qendTime':qendTime,'townshipStreets':qtownshipStreets};
     
    $('#liuyantt').datagrid('reload');
	}

function replylist(id){
	replywin.window('open'); 
//	$('#registNumberMap').html(registNumber);
     gridreply=$('#replylisttab').datagrid({
	    title:'',
	    pageSize:10,
	    pageList:[10,20,30,40],
	    url:'/tcweb/remark/replylistById',
	    queryParams:{'id':id},
	    columns:[[ 
	        {field:'replyinfo',align:'left',title:'回复内容',width:$(this).width() * 0.20},
	        {field:'replyTime',align:'center',title:'回复时间',width:$(this).width() * 0.1},
	        {field:'userName',align:'center',title:'回复人',width:$(this).width() * 0.1} 
         ]],
     //    fitColumns:true,
    //     nowrap:true,
	    pagination:true,
	    singleSelect:true
	
});
     $('#replylisttab').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,30,40]});  
	
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
 /* background: #F9F0DA; */
   padding-left: 2px; 
}

.form_input {
  display: block;
 /* width: 100%; */
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
 <fieldset id="addDiv" style="margin-left:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
     <table border="0"  style="border-collapse:separate; border-spacing:0px 5px;width:100%;">
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
   <% if("1".equals(cityName)){ %>
   <td nowrap>N<input id="registNumber" name="registNumber"  class="easyui-validatebox"></input></td>
    <% } else {%>
 <td nowrap><input id="registNumber" name="registNumber"  class="easyui-validatebox"></input></td>
 <% }%>
   <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="address" name="address"  class="easyui-validatebox"></input></td>
   <td align="right" nowrap>楼盘名称：</td> 
   <td nowrap><input id="buildingName" name="buildingName"  class="easyui-validatebox"></input></td>
   </tr>
   <tr>
    <%if(role !=10 && role != 11){ %>
   <td align="right"  nowrap>行政区划：</td>
   <td> 
  <!--  <select id="areainfo"   name="areainfo" style="width:152px;" onchange="getCompanyListByArea()"> -->
  <!-- <select id="areainfo"   name="areainfo" style="width:152px;">
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
</select> -->
<input id="areainfo" name="areainfo" />
</td>
  <% } %> 
  <td align="right">乡镇街道办：</td>
 <td><select id="qtownshipStreets"  class="easyui-combobox" name="qtownshipStreets" style="height:25px;"></select></td>
   <td nowrap align="right">满意度</td>
   <td> <select id="remarkLevel"  name="remarkLevel" style="height:25px;">
    <option value=""></option>
    <option value="0">满意</option>
    <option value="1">一般</option>
    <option value="2">不满意</option>
 <!--   <option value="3">投诉</option> -->
</select>
</td>
</tr>
<tr><td  nowrap align="right">留言日期起：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime"  data-options="editable:false" style="height:25px;"></input></td>
   <td align="right" nowrap>止：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime"  data-options="editable:false" style="height:25px;"></input></td>
   <td></td>
   <td></td>
    <td>
		<!--		<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a>  -->	
	<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>
					
</td>
					
   </tr>
   
     </table>
  
  </fieldset>
</div> 
   <div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div style="overflow-x:hidden;">  
       <table id="liuyantt"></table>
   </div>  
        
    </div>  
</div> 
<div id="remarkZjReplyWin" class="easyui-dialog" title="质监回复意见" closed="true" style="width:300px;height:200px;padding:5px;"> 
<div class="easyui-layout" data-options="fit:true">

<div data-options="region:'center',split:false" style="height:80px">
   
     <table style="width:100%">
     <tr>
    <td><textarea rows="7" cols="40" id="checkadvice" name="checkadvice"></textarea></td>
     </tr>
     </table>
     </div>
    <div data-options="region:'south',split:false" style="height:40px">
     <table>
    
     <tr>
       <td>&nbsp;&nbsp;&nbsp;</td>
       <td align="right" style="width:100%"> <a href="javascript:void(0)" onclick="saveremarkZjReply()" id="btn-save" icon="icon-save">保存</a></td>  
      </tr>
     </table>
   </div>
  </div>
  
 </div>

  <div id="reply-window" title="回复列表" style="width:780px;height:450px;" class="easyui-layout">
  <div style="margin:10px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;height:400px;">
   <div style="margin-top:1px;" title=" 回复列表" region="center" >  
       <table id="replylisttab"></table>
   </div>
  </div> 
 </div>  

</body>
</html>