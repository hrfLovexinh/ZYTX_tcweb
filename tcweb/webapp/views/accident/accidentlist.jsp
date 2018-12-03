<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>车载系统</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript">
$(function(){
	 
	grid=$('#tt').datagrid({
	    title:'图像记录列表',
	    pageSize:15,
	    pageList:[15,20,25,30,35,40],
	    url:'/czweb/image/imagelist',
	    queryParams:{},
	    columns:[[
	        {field:'carnum',title:'车牌号',width:80},
	        {field:'dev_id',title:'设备ID',width:115},
	        {field:'image_id',title:'图像ID',width:80},
	     //   {field:'image_type',title:'图像类型',width:80},
	       {field:'image_type',title:'图像类型',width:80,
			        formatter:function(value,rec,index) {
			        var id =0;
			        id=rec.image_type;
			        if (id==0)
				       return "未知";
				    if (id==1)
					   return "实时提取"; 
					if (id==2)
						return "紧急报警图片";
					if (id==3)
						return "事故报警图片";
					}},
	        {field:'image_time',title:'图像时间',width:110,
			          formatter:function(value,rec,index) {
				        var imagetimeValue;
				        imagetimeValue =rec.image_time;
				        return imagetimeValue.substring(0,19);
				       
						}},
	        {field:'receive_time',title:'记录时间',width:110,
	        	 formatter:function(value,rec,index) {
		        var receiveValue;
		        receiveValue =rec.receive_time;
		        return receiveValue.substring(0,19);
		       
				}},
	       // {field:'image_format',title:'图像格式',width:80},
	         {field:'image_format',title:'图像格式',width:80,
			        formatter:function(value,rec,index) {
			        var id =0;
			        id=rec.image_format;
			        if (id==0)
				       return "未知";
				    if (id==1)
					   return "352*288"; 
					if (id==2)
						return "640*480";
					}},
	        {field:'momo',title:'说明',width:80},
	        {field:'null',title:'操作',width:80,align:'center',
	        	formatter: function(value,rec,index) {
	        	var id = rec.data_id;//获取属性值
	        	var image_time=rec.image_time; //获取图片时间
	        //	return "<a href='customerRemind/toadd.do?data_id="+id+"'>详情</a>";
	       //   return "<img src='<%=request.getContextPath()%>/images/unfold.gif' alt='查看' style='cursor:hand;' onclick='openImageDetail("+id+")'/>";
		       return "<img src='<%=request.getContextPath()%>/images/unfold.gif' alt='查看' style='cursor:hand;' onclick='openImageDetail("+id+","+"\""+image_time+"\""+ " )'/>";
            }}
	    ]],
	    pagination:true
	});	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页'});  
  
});
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
   <td nowrap>&nbsp;&nbsp;&nbsp;&nbsp;车牌号：</td> 
   <td nowrap><input id="carnum" name="carnum" size="12" class="easyui-validatebox"></input></td>
   <td nowrap>&nbsp;&nbsp;&nbsp;&nbsp;设备ID：</td> 
   <td nowrap><input id="dev_id" name="dev_id" size="12" class="easyui-validatebox"></input></td> 
   </tr>
   <tr>      
   <td nowrap>开始时间：</td> 
   <td nowrap>
   <!-- <input type="text" id="startTime" name="startTime" size="12"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"
					readonly />-->
   <input id="startTime" class="easyui-datebox" size="12">
					</td>
   <td nowrap>结束时间：</td> 
   <td nowrap>
   <!--  <input type="text" id="endTime" name="endTime" size="12"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"
					readonly />-->
	<input id="endTime" class="easyui-datebox" size="12"></input>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
</body>
</html>