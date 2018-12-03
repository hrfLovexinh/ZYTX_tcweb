<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>电梯安全公共服务平台</title>
 <!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/> -->
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">

 <style>
    .datagrid-header-row td{background-color:rgb(226,237,255);color:#0E2D5F;}
  </style>
<% 
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int isliulan = 0;
int role=0;
int ispcsuper = 0;
if(userinfo!=null){
	 role = userinfo.getRole(); 
	 ispcsuper =userinfo.getIspcsuper();
	 isliulan = userinfo.getIsliulan();
	}
	else{
		 Cookie[] cookies =  request.getCookies();
		 String userName = "";
			 String password = "";
			if (cookies != null) {
			   for (Cookie c : cookies) {
				if (c.getName().equals("userName")){
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
			   UserInfoVO user2 =UserInfoVO.findFirstBySql(UserInfoVO.class, "select ispcsuper,isliulan from  TCUserInfo where loginName= ? and isinvalid = 0 ",new Object[] { userName });
			    if(user2 != null){
			    	isliulan = user2.getIsliulan();
			    	ispcsuper = user2.getIspcsuper();
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

var opt =0; //0:增加 ；1：编辑
$(function(){
	$.ajaxSetup ({
	    cache: false 
	});

//	$('#btn-save,#btn-cancel,#btn-save3').linkbutton(); 
    $('#btn-save3').linkbutton(); 
	win = $('#car-window').window({closed:true,draggable:false,modal:true,minimizable:false,collapsible:false,maximizable:false }); 
	wincc = $('#company-window8').window({  closed:true,draggable:false,modal:true,minimizable:false,collapsible:false,maximizable:false }); 
	awindow =$('#attributewindow').window({  closed:true,draggable:false,modal:true,minimizable:false,collapsible:false,maximizable:false }); 
	form = win.find('form');
	
	$('#parea').combobox({   
        url:'/tcweb/elevator/areaInfoList',   
        valueField:'area',   
        textField:'area'  
    });  

	
	grid=$('#tt').datagrid({
	    title:'单位列表',
        fitColumns:true,
	    pageSize:25,
	    pageList:[15,25,30,35,40],
	    url:'/tcweb/company/companylist',
	    queryParams:{},
	    columns:[[
	        {field:'companyCode',align:'left',halign:'center',title:'单位编码',width:$(this).width() * 0.07},
	        {field:'companyName',align:'left',halign:'center',title:'单位名称',width:400,formatter: function(value,rec,index) {
	        	var isyanshi = rec.isyanshi; 
	        	var companyId =rec.id;
	        	var str="";

	        	  <% if(ispcsuper == 1){ %>  
                  if(isyanshi == 1){
                      str="演示权限";  
                  }
               
                if(str !=""){
                	if(rec.ischangeFlag==1)
                   //     return "<a href = '#' onclick='zhanghaoSettings("+companyId+")' style='text-decoration:none'>" +rec.companyName+"("+str+")"+"</a>"+"<img src='<%=request.getContextPath()%>/images/yulan.png' alt='查看' style='cursor:hand;' onclick='openCompanyNameDetail("+"\""+rec.id+"\""+")'/>";
                      return '<a href = "#" onclick="zhanghaoSettings('+companyId+')" style="text-decoration:none">'+rec.companyName+'<a href="#" onclick="openCompanyNameDetail('+'\''+rec.id+'\''+')"><i class="fa fa-exchange" aria-hidden="true" style="color:#61B5CF;"></i></a>';
               //    
                    else
                        return "<a href = '#' onclick='zhanghaoSettings("+companyId+")' style='text-decoration:none'>" +rec.companyName+"("+str+")"+"</a>";
                 
                    }
                else{
                if(rec.ischangeFlag==1)
                 //   return "<a href = '#' onclick='zhanghaoSettings("+companyId+")' style='text-decoration:none'>" +rec.companyName+"</a>"+"<img src='<%=request.getContextPath()%>/images/yulan.png' alt='查看' style='cursor:hand;' onclick='openCompanyNameDetail("+"\""+rec.id+"\""+")'/>";
                return '<a href = "#" onclick="zhanghaoSettings('+companyId+')" style="text-decoration:none">'+rec.companyName+'<a href="#" onclick="openCompanyNameDetail('+'\''+rec.id+'\''+')"><i class="fa fa-exchange" aria-hidden="true" style="color:#61B5CF;"></i></a>';
           
                else
                    return "<a href = '#' onclick='zhanghaoSettings("+companyId+")' style='text-decoration:none'>" +rec.companyName+"</a>";
		         }
		         
                <%} else {  %> 
                if(rec.ischangeFlag==1)
                	  return rec.companyName+'<a href="#" onclick="openCompanyNameDetail('+'\''+rec.id+'\''+')"><i class="fa fa-exchange" aria-hidden="true" style="color:#61B5CF;"></i></a>';
               //     return rec.companyName+"<img src='<%=request.getContextPath()%>/images/yulan.png' alt='查看' style='cursor:hand;' onclick='openCompanyNameDetail("+"\""+rec.id+"\""+")'/>";
                else
                    return rec.companyName;
                <%}%>
	        }},
	        {field:'address',align:'left',halign:'center',title:'单位地址',width:$(this).width() * 0.13},
	        {field:'representativor',align:'center',halign:'center',title:'负责人',width:$(this).width() * 0.07},
	        {field:'contact',align:'center',halign:'center',title:'联系人',width:$(this).width() * 0.04},
	        {field:'phone',align:'left',halign:'center',title:'移动电话',width:$(this).width() * 0.07},
	        {field:'telephone',align:'left',halign:'center',title:'固定电话',width:$(this).width() * 0.07},
	        {field:'zip',align:'left',halign:'center',title:'邮编',width:$(this).width() * 0.07},
	        {field:'certificateName',align:'left',halign:'center',title:'证书名称',width:$(this).width() * 0.07},
	        {field:'certificateCode',align:'left',halign:'center',title:'证书编码',width:$(this).width() * 0.07},
	        {field:'qualification',align:'left',halign:'center',title:'资质',width:$(this).width() * 0.07},
	        {field:'qlevel',align:'center',title:'级别',width:$(this).width() * 0.04},
	        {field:'validity',align:'center',title:'有效期',width:$(this).width() * 0.07},
	        {field:'type',align:'center',title:'类型',width:$(this).width() * 0.07}
	    ]],
	    pagination:true,
	    singleSelect:true,
	    striped:true,
	    <% if(isliulan == 0){%>
	    toolbar:[{
	        text:'新增',
	        iconCls:'icon-add', 
	        handler:function(){
	    	isCommitted = false;  
	    	opt =0;
	    	$('#zhaopian').hide();
	    	$('#userPic').attr("value","");
	    	$('#userPic').hide();
	    	document.all.companyImg.innerHTML="";
	    	win.window('open');  
	    	form.form('clear');
	    	form.url ='/tcweb/company/add';	
	    	ispasteyw2();
	    	colseWinDetail();	 
	        }
	    },'-',{
	        text:'修改',
	        iconCls:'icon-edit',
	        handler:function(){
	    	var row = grid.datagrid('getSelected');   
	    	if (row){ 
	    		isCommitted = false;  
	    		 opt=1;
	    		$('#zhaopian').show();
	 	    	$('#userPic').show();
	 	    	$('#userPic').attr("value",""); 
	    		 win.window('open');  
	    		 showCompanyPic(row.id); 
	    		 colseWinDetail();
	    		 form.form('load', '/tcweb/company/edit/'+row.id);
	    		 form.url = '/tcweb/company/update/'+row.id; 
	    		 opt =1;
	    		
	    	 } else {  
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。' 
	    		 });   
	    	 }  
	        }
	    }]
	    <% } %> 
	});	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
  
});

function clearQuery(){
	$('#companyCode').attr("value","");
	$('#companyName').attr("value","");
	$('#companyType').combobox('clear');
	
}

function query(){  
	
    var companyCode=$('#companyCode').attr("value");
    var companyName=$('#companyName').attr("value");
    var companyType=$('#companyType').combobox('getValue');
    if(companyType == null)
    	companyType ="";
    
 
     grid.datagrid("options").url='/tcweb/company/query';
     grid.datagrid("options").queryParams={'companyCode':companyCode,'companyName':companyName,'type':companyType};
     
    $('#tt').datagrid('reload');
	}
	

	


function closeWindow(){ 
	win.window('close');
	}


function colseWinDetail(){
	$("form input").css({border:'1px solid' });
	
	 $(".fontShow").show();	
	 $('#btn-save,#btn-cancel').show(); 

	 $('#longitudeinfo').attr("value",0);   
	 $('#latitudeinfo').attr("value",0);
	 $('#speedinfo').attr("value",0);
	 $('#angleinfo').attr("value",0);  
	 $('#gps_timeinfo').attr("value","2011-08-080");  
	 
	 $('#dev_idinfo').attr("readonly","");
	 $('#carnuminfo').attr("readonly","");
	 $('#qy_nameinfo').attr("readonly","");
	 $('#simnuminfo').attr("readonly","");
	 $('#dev_typeinfo').attr("readonly","");
	 $('#car_typeinfo').attr("readonly","");
	 $('#carnum_colorinfo').attr("readonly","");
	 $('#car_colorinfo').attr("readonly","");
	 $('#longitudeinfo').attr("readonly","");
	 $('#latitudeinfo').attr("readonly","");
	 $('#angleinfo').attr("readonly","");
	 $('#speedinfo').attr("readonly","");
	 $('#gps_timeinfo').attr("readonly","");
	 $('#personinfo').attr("readonly","");
	 $('#phoneinfo').attr("readonly","");    

	 
	// $('#latitudeinfo').hide();
	$('#table2').hide();

	$('#qy_nameinfo').combobox('enable'); 
}

var isCommitted = false;//表单是否已经提交标识，默认为false
function saveCar(){  // alert("181");
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){
		 if(isCommitted==false){
			       isCommitted = true;//提交表单后，将表单是否已经提交标识设置为true    
			   }else{
			       return false;//返回false那么表单将不提交
			        }
     //   return $(this).form('validate');
		var companyName=$('#companyNameinfo').attr("value"); 
	//	var type=$('#typeinfo').combobox('getValue');
	    var type= $('#typeinfo').val();  
		var companyCode= $('#companyCodeinfo').attr("value"); 
		var address =$('#address').attr("value");
		
        var phone =$('#phone').attr("value");
		/*
		if(opt==1){
			$('#qy_nameinfo').combobox('setValue',qy_id2);
		//	alert("----qyName---"+$('#qy_nameinfo').combobox('getValue'));
			opt=0;
			}  */
		var ispasteyw= $('#ispasteyw').val(); 
	    var parea =$('#parea').combobox('getValue');
		if(!companyName){
		  $.messager.alert('操作失败','单位名称不能为空','error');
		  isCommitted = false;
		  return false;
			}
		if(!type){
		  $.messager.alert('操作失败','单位类别不能为空','error');
		  isCommitted = false;
		  return false;
			}
		if("维保"==type){
			if(ispasteyw !=0){
                 if(ispasteyw != 1){
                	 $.messager.alert('操作失败','维保单位请选择是否具有粘贴权限','error');
                	 isCommitted = false;
                	 return false;
                     }
				}
			}
		if("街道办"==type){
             if(parea ==""){
            	 $.messager.alert('操作失败','街道办请选择所属行政区划','error');
            	 isCommitted = false;
            	 return false;
                 }  
			}
		return true;  
	//	return false;
    },
		  
		success:function(data){ 
    	isCommitted = false;  
		eval("data="+"'"+data+"'"); 
		if("exist"==data){
			$.messager.alert('操作失败','已经存在该单位，不能重复添加','error');
			} 
		else if("ImageType"==data){$.messager.alert('操作失败','图片格式必须为jpeg格式','error');}
		else if("ImageSize"==data){$.messager.alert('操作失败','图片不能大于2M','error');}
		else if("success"==data){
		//	$.messager.alert("操作成功",'谢谢');    
		 $.messager.show({   
	    			 title:'提示信息',
	    			 timeout:1000,
	    			 msg:'操作成功，谢谢。' 
	    		 }); 
			grid.datagrid('reload');
			win.window('close');   
			}
		else{
			$.messager.alert('操作失败','添加单位','error');
			}
		} 
	});
	}

function openCompanyNameDetail(id){
	wincc.window('open'); 
	gridcc=$('#ccmap').datagrid({
	    title:'',
	    pageSize:5,
	    pageList:[5,10,15,20,25,30],
	    url:'/tcweb/company/companyChangelist',
	    queryParams:{'companyid':id},
	    columns:[[
	        {field:'oldName',title:'原名称',width:250},
	        {field:'nowName',title:'变更后名称',width:250},
	        {field:'updateTime',title:'变更时间'},
         ]],
         nowrap:true,
	    pagination:true
	
});
	  $('#ccmap').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[5,10,15,20,25,30]});  
}

function showCompanyPic(id){
	var companyImg="";
	companyImg ='<img height="480" align="center" style="width:700px" onerror=this.style.display="none" src="<%=request.getContextPath()%>'+'/servlet/companyImage.jpg?id='+id+'"/>';
	document.all.companyImg.innerHTML=companyImg;
}

function ispasteyw2(){
	var selectval = $('#typeinfo').val(); 
	
	if("维保"==selectval){
		$("#pasteyw2").show();
		$("#area").hide();
	}
	else if("街道办"==selectval){ 
		$("#area").show();
		$("#pasteyw2").hide();
		}
	else{	
	    $("#pasteyw2").hide();
	    $("#area").hide();
	}
	
}

function areaSlect(){
	var selectval = $('#typeinfo').val(); 
	if("街道办"==selectval)
		$("#area").show();
	else	
	    $("#area").hide();
}

function ispasteywselect(){
	if($("#ispasteyw").attr("checked")==true)
		$("#ispasteyw").attr("value",1);   //选中为1
	else
		$("#ispasteyw").attr("value",0);  
}

function ispasteywselect2(){ 
	// var ispasteyw =$('#ispasteyw').attr("value");
	// alert("ispasteyw---->"+ispasteyw);
	var ispasteyw =document.getElementById("ispasteyw").value;
	if(ispasteyw==1){	
	//	 $("#ispasteyw").attr("checked",true);
		 document.getElementById("ispasteyw").checked=true;
	 }
	 if(ispasteyw==0){
	//	 $('#ispasteyw').attr("checked",false); 
		 document.getElementById("ispasteyw").checked=false;
	 }
}

var acompanyId = 0 ;
function zhanghaoSettings(companyId){
	acompanyId = companyId;
	awindow.window('open');
	jQuery.post('/tcweb/company/attributeById', {'id':companyId},function(data){ 
        data = eval(data);//POST方法必加，ajax方法自动处理了 
       var isyanshi =data.isyanshi; 
       var threedscanning =data.threedscanning; 
       var isshengji = data.isshengji;
        $('#isyanshi').val(isyanshi);
        $('#threedscanning').val(threedscanning);
        $('#isshengji').val(isshengji);
	   }, 'json');
}

function saveattribute(){
	jQuery.post('/tcweb/company/updateattributeById',
	    	 {'id':acompanyId,'isyanshi':$('#isyanshi option:selected').val(),'threedscanning':$('#threedscanning option:selected').val(),'isshengji':$('#isshengji option:selected').val()},
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
	    			$.messager.alert('操作失败','更新失败','error');
  	    		}
  	       });
	awindow.window('close');
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
  padding-left: 15px;
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
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);height:80px;">  
 <fieldset id="addDiv" style="height:100%;margin:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
    <table id="sousuo"> 
     <tr>      
   <td align="right" nowrap>单位编码：</td> 
   <td nowrap align="left"><input id="companyCode" name="companyCode" style="width:220px" class="easyui-validatebox"></input></td>
   <td align="right" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位名称：</td> 
   <td nowrap align="left"><input id="companyName" name="companyName" style="width:220px" class="easyui-validatebox"></input></td>
   <td nowrap align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位类别：</td> 
   <td nowrap align="left">
   <select id="companyType"  class="easyui-combobox" name="type" style="width:220px;height:25px;" >
    <option value="制造">制造</option>
    <option value="安装">安装</option> 
    <option value="使用">使用</option> 
    <option value="检验">检验</option> 
    <option value="维保">维保</option>
    <option value="质监">质监</option>
    <option value="街道办">街道办</option> 
    <option value="行业协会">行业协会</option>   
</select>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<!--	<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  -->	
			    <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
			<!--	<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a>  -->	
			    <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>
			<!--		<input type="submit" value="Submit Comment" /> -->	
					
</td>
					
   </tr>
   
     </table>
  
  </fieldset>
</div> 
   <div region="center" style="width:100%;">   
       <table id="tt"></table> 
  </div> 
<div id="car-window" title="详细信息" style="width:600px;height:500px;"> 
  <div class="easyui-layout" data-options="fit:true">
  <div data-options="region:'north'" style="height:490px;text-align:center;border:0px;" >     
  <form method="post" enctype="multipart/form-data">   
  <table style="width: 100%;margin:auto;"> 
   <tr>      
   <td width="150" nowrap align="center" style="background: #F5F5F5;">单位名称</td>      
   <td colspan="3"><input class="form_input" id="companyNameinfo" name="companyName"></input></td>
   </tr>
   <tr>  
    <td width="150" nowrap align="center" style="background: #F5F5F5;">单位编码</td>      
   <td><input class="form_input" id="companyCodeinfo" name="companyCode"></input></td> 
   </tr>
   <tr> 
   <td width="150" align="center" style="background: #F5F5F5;">单位地址</td>      
   <td colspan="3"><input class="form_input" id="address" name="address"></input></td>
   </tr>
   <tr>     
   <td align="center" style="background: #F5F5F5;">单位类型</td>      
  <!--  <td><input id="qy_nameinfo" name="qy_name"></input><span class="fontShow"><font color="red">*</font></span></td>   -->
   <%if(role==2 || role==1){%>  
   <td><select id="typeinfo"   name="type" style="width:130px;height: 34px;" onchange="ispasteyw2()">
    <option value="制造" selected ="selected">制造</option>
    <option value="安装">安装</option> 
    <option value="使用">使用</option> 
    <option value="检验">检验</option> 
    <option value="维保">维保</option>
    <option value="市质监">市质监</option> 
    <option value="质监">质监</option>
    <option value="街道办">街道办</option> 
    <option value="粘贴">粘贴</option>
    <option value="行业协会">行业协会</option>          
</select>
</td> 
<%} else {%>
 <td><select id="typeinfo"   name="type" style="width:130px;height: 34px;"> 
 <option value="制造" selected ="selected">制造</option>
    <option value="安装">安装</option> 
    <option value="使用">使用</option> 
    <option value="检验">检验</option> 
    <option value="维保">维保</option>
    <option value="质监">质监</option>
    <option value="街道办">街道办</option>   
   </select>
</td>
 <%}%> 
   <td colspan="2">
   <div id="pasteyw2" style="display:inline;">
   <select name="ispasteyw" id="ispasteyw" style="width:110px;height: 34px;">
   <option value="0">不粘贴</option> 
   <option value="1">粘贴</option> 
   </select>
   </div>
   <div id="area" style="display:inline;">
   <input id="parea" name="parea" style="height:34px;"></input>
   </div>
   </td> 
   </tr>
   <tr>
    <td width="150" align="center" style="background: #F5F5F5;">联系人</td>      
   <td><input class="form_input" id="contact" name="contact"></input></td> 
    <td width="150" align="center" style="background: #F5F5F5;">负责人</td>      
   <td><input class="form_input" id="representativor" name="representativor"></input></td> 
   </tr>
   <tr>
    <td width="150" align="center" style="background: #F5F5F5;">移动电话电话</td>      
   <td><input class="form_input" id="phone" name="phone"></input></td>  
   <td width="150" align="center" style="background: #F5F5F5;">固定电话</td>      
   <td><input class="form_input" id="telephone" name="telephone"></input></td>  
   </tr>
    <tr>  
   <td width="150" align="center" style="background: #F5F5F5;">证书名称</td>      
   <td><input class="form_input" id="certificateName" name="certificateName"></input></td>       
   <td width="150" align="center" style="background: #F5F5F5;">证书编号</td>      
   <td><input class="form_input" id="certificateCode" name="certificateCode"></input></td>      
   </tr>
   <tr>  
   <td width="150" align="center" style="background: #F5F5F5;">资质</td>      
   <td><input class="form_input" id="qualification" name="qualification"></input></td>       
   <td width="150" align="center" style="background: #F5F5F5;">等级</td>      
   <td><input class="form_input" id="qlevel" name="qlevel"></input></td>      
   </tr>
    <tr>
    <td width="150" align="center" style="background: #F5F5F5;">资质有效期</td>      
   <td><input class="form_input" id="validity" name="validity" class="easyui-datebox"  data-options="editable:false"></input></td>        
   <td width="150" align="center" style="background: #F5F5F5;">邮编</td>      
   <td><input class="form_input" id="zip" name="zip"></input></td>  
   </tr> 
   <tr>
   <td id="zhaopian" width="150" align="center" style="background: #F5F5F5; ">照片</td>      
   <td colspan="3"><input class="form_input" type="file" id="userPic" name="userPic"></td>
   </tr>   
   </table>
   <table width=100%>
   <tr style="height:10px"></tr>
    <tr>
    <td align="center">
    <!--  <a href="javascript:void(0)" onclick="saveCar()" id="btn-save" icon="icon-save">保存</a>  
     <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">关闭</a> -->
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-save fa-lg" onclick="saveCar()" style="width:100px;color:#3399FF;">保存</a>
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-close fa-lg" onclick="closeWindow()" style="width:100px">取消</a>
    </td>
    </tr> 
   </table>   
   </form>
   </div> 
   </div>
 </div>
     
     <div id="companyImg"  style="overflow:hidden;float:left;border:0px solid #000;"></div>
   
   <div id="company-window8" title="公司名称变更详细信息" style="width:780px;height:550px;">
   <div style="margin-top:1px;" title="变更信息列表">  
       <table id="ccmap"></table>
   </div> 
   </div>
   
  <div id="attributewindow" title="账号属性" style="width:380px;height:350px;overflow-x:hidden;overflow-y:hidden;"> 
   <div class="easyui-layout" data-options="fit:true"> 
    <div data-options="region:'north'" style="height:250px;text-align:center;" > 
     <table style="width: 100%;margin:auto">
    <tr> 
    <td align="center" style="background: #F5F5F5;width:50%">演示权限</td> 
    <td>
    <select id="isyanshi"  name="isyanshi" style="width:100%;height:34px">
    <option value="0">不是</option>
    <option value="1">是</option>  
    </select>
    </td>
    </tr>
    <tr style="height:10px">
    </tr>
    <tr> 
      <td align="center" style="background: #F5F5F5;width:50%">3D扫描</td>
<td>
 <select id="threedscanning"  name="threedscanning" style="width:100%;height:34px">
    <option value="0">关闭</option>
    <option value="1">开启(低)</option> 
    <option value="2">开启(中)</option> 
    <option value="3">开启(高)</option> 
</select> 
</td> 
    </tr>
     <tr style="height:10px">
    </tr>
       <tr> 
      <td align="center" style="background: #F5F5F5;width:50%">升级</td>
<td>
 <select id="isshengji"  name="isshengji" style="width:100%;height:34px">
    <option value="0">不升级</option>
    <option value="1">升级</option> 
</select> 
</td> 
    </tr>
    <tr>
   </tr>
   <tr>
   </tr>
   <tr>
   </tr>
   </table>
  </div>
 
  <div data-options="region:'center'" style="overflow-x:auto;overflow-y:hidden">
    <table style="text-align:center;width:100%">
     <tr style="height:10px"></tr>
    <tr>
    <td  align="center">
  <!--   <a href="javascript:void(0)" onclick="saveattribute()" id="btn-save3" icon="icon-ok">确定</a> --> 
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-save fa-lg" onclick="saveattribute()" style="width:100px">保存</a> 
    </td>
    </tr>
   </table>
   </div>
   
   </div>
   </div>
   
</body>
</html>