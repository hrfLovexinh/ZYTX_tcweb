<%@ page import="com.zytx.models.UserInfo" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>电梯安全公共服务平台</title>
<!--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/> -->
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">
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

var opt =0; //0:增加 ；1：编辑
$(function(){
	$.ajaxSetup ({
	    cache: false 
	});

	 var inputWidth = $('#qcompanyName').width(); 
	
	 $('#qstartTime').datebox({     
	       width:inputWidth 
	   }); 

	 $('#qendTime').datebox({     
	       width:inputWidth 
	   }); 

	$('#btn-save,#btn-cancel').linkbutton(); 
	win = $('#car-window').window({closed:true,draggable:false,modal:true }); 
	wincc = $('#company-window8').window({  closed:true,draggable:false,modal:true });   
	hmdWin = $('#hmdWin').window({  closed:true,draggable:false,modal:true });
	form = win.find('form');
	
	
	
	grid=$('#tt').datagrid({
	    title:'单位列表<span style="color:#ff0000;">(红色：维保信用黑名单单位)</span>',
	    singleSelect:true,
	    striped:true,
	    pageSize:25,
	    pageList:[15,25,30,35,40],
	    url:'/tcweb/company/zjtypecompanylist',
	    queryParams:{'type':"维保"},
	    columns:[[
	        {field:'companyName',align:'left',halign:'center',title:'单位名称',width:200,formatter: function(value,rec,index) {
                if(rec.ischangeFlag==1)
                    return rec.companyName+"<img src='<%=request.getContextPath()%>/images/yulan.png' alt='查看' style='cursor:hand;' onclick='openCompanyNameDetail("+"\""+rec.id+"\""+")'/>";
                else
                    return rec.companyName;
		         }},
	        {field:'address',align:'left',halign:'center',title:'单位地址',width:$(this).width() * 0.16},
	        {field:'representativor',align:'center',halign:'center',title:'负责人',width:$(this).width() * 0.09},
	        {field:'contact',align:'center',halign:'center',title:'联系人',width:$(this).width() * 0.09},
	        {field:'phone',align:'center',halign:'center',title:'移动电话',width:$(this).width() * 0.09},
	        {field:'telephone',align:'center',halign:'center',title:'固定电话',width:$(this).width() * 0.09},
	        {field:'zip',align:'center',halign:'center',title:'邮编',width:$(this).width() * 0.09},
	        {field:'qualification',align:'left',halign:'center',title:'资质',width:$(this).width() * 0.09},
	        {field:'qlevel',align:'center',halign:'center',title:'级别',width:$(this).width() * 0.02},
	        {field:'validity',align:'center',halign:'center',title:'有效期',width:$(this).width() * 0.09},
	        {field:'certificateCode',align:'center',halign:'center',title:'证书编码',width:$(this).width() * 0.09}
	    ]],
	    pagination:true,
	    <%if(role == 22 || role ==23){ %>
	    toolbar:[{
   	        text:'移入黑名单',
   	        iconCls: 'icon-no',
   	         handler: function () {
   		    	var row = grid.datagrid('getSelected'); 
   		    	if(row.hmdFlag == 1){
   		    		$.messager.alert('操作失败','该单位已在黑名单中，不需要再移入','info');
   		    		return; 
   			    	}
   		    	  
   		    	if (row){
   		    		 $.messager.confirm('','确定要移入黑名单',function(data){if(data){	 
   		    			hmdWin.window('open');
   		    			$('#ywcompanyIdhmd').attr("value",row.id);
   		    			$('#ywcompanyNamehmd').attr("value",row.companyName);
   		    	    	 }}
   		      	       );  
   		    	 } else {  
   		    		 $.messager.show({   
   		    			 title:'警告',
   		    			 msg:'请先选择记录行。' 
   		    		 });   
   		    	 }    
   	            }
   	    }, {
            text: '移出黑名单',
            iconCls: 'icon-redo',
            handler: function () {
	    	var row = grid.datagrid('getSelected'); 
	    	if(row.hmdFlag == 0){
	    		$.messager.alert('操作失败','该单位不是黑名单单位，不需要移出','info');
	    		return; 
		    	}
	    	if (row){
	    		 $.messager.confirm('','确定移出黑名单',function(data){if(data){	 
	    	    	 jQuery.post('/tcweb/elevator/outhmd',
	    	    	    	 {'ywcompanyId':row.id},
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
	    	    	    			$.messager.alert('操作失败','移出黑名单失败','error');
	    		    	    		}
	    		    	       });}}
	      	       );  
	    	 } else {  
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。' 
	    		 });   
	    	 }    
            }
        }],
        <%}%>
        rowStyler:function(index,row){  //暂时取消颜色超期
		    var hmdFlag = row.hmdFlag;
		    if(hmdFlag ==1)
		    	return 'color:#ff0000;';
		    else
		    	return 'color:#000000;';
		    
	     }
	});	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
  
});

function clearQuery(){
	$('#qcompanyName').attr("value","");
	$('#qaddress').attr("value","");
	$("#qstartTime").datebox("setValue","");  
	$("#qendTime").datebox("setValue","");  
	
}

function query(){  
	  
    var qcompanyName=$('#qcompanyName').attr("value");
    var qaddress=$('#qaddress').attr("value");
    var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue"); 
     grid.datagrid("options").url='/tcweb/company/zjtypequery';
     grid.datagrid("options").queryParams={'companyName':qcompanyName,'address':qaddress,'qstartTime':qstartTime,'qendTime':qendTime};
     
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

function saveCar(){  // alert("181");
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){
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
		if(!companyName){
		  $.messager.alert('操作失败','单位名称不能为空','error');
		  return false;
			}
		if(!type){
		  $.messager.alert('操作失败','单位类别不能为空','error');
		  return false;
			}
		if("维保"==type){
			if(ispasteyw !=0){
                 if(ispasteyw != 1){
                	 $.messager.alert('操作失败','维保单位请选择是否具有粘贴权限','error');
                	 return false;
                     }
				}
			}
		return true;  
	//	return false;
    },
		  
		success:function(data){   
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
	
	if("维保"==selectval)
		$("#pasteyw2").show();
	else	
	    $("#pasteyw2").hide();
	
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

function savehmd(){
	  var hmdreason =$('#hmdreason').attr("value");
	  var ywcompanyIdhmd=$('#ywcompanyIdhmd').attr("value");
	  var ywcompanyNamehmd=$('#ywcompanyNamehmd').attr("value");
	  var len = hmdreason.length;  
	  if(len == 0){
		  $.messager.alert('操作失败','请填写入黑名单原因','error'); 
	      return;
		  }
	  if(len > 1000){
		 $.messager.alert('操作失败','超过最大字符限制数1000','error'); 
         return;
		  }
	 jQuery.post('/tcweb/elevator/intohmd',
	    	 {'ywcompanyId':ywcompanyIdhmd,'ywCompanyName':ywcompanyNamehmd,'hmdReason':hmdreason},
	    	 function(data){
	    		eval("data="+"'"+data+"'");  
	    		if("success"==data){
	    		 $.messager.show({   
			      title:'提示信息',
			      timeout:1000,
			        msg:'操作成功，谢谢。' 
		 });  	    
	    		   $('#hmdreason').attr("value","");
	    		    hmdWin.window('close');
    	            grid.datagrid('reload');
	    		}
	    		else{
	    			$.messager.alert('操作失败','移入黑名单失败','error');
    	    		}
    	       });
	
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
    <table id="sousuo" style="width:100%">  
     <tr>      
   <td nowrap align="right" style="150">单位名称：</td> 
   <td nowrap><input id="qcompanyName" name="qcompanyName"   class="easyui-validatebox"></input></td>
   <td nowrap align="right" style="150">单位地址：</td> 
   <td nowrap><input id="qaddress" name="qaddress"   class="easyui-validatebox"></input></td>
  
   <td  nowrap align="right" style="150">资质有效期从：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime"></input></td>
   <td align="right" nowrap style="150">到:</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime"></input></td>
   <td> 
		<!--			<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a>  -->	
		<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
		<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>
  </td>
   </tr>
   </table>
  </fieldset>
</div> 
   <div region="center" style="width:100%;">
       
       <table id="tt"></table>
   
</div> 
<div id="car-window" title="详细信息" style="width:750px;height:470px;"> 
  <div style="padding:20px 20px 40px 80px;">   
  <form method="post" enctype="multipart/form-data">    
  <table>    
   <tr>      
   <td width="70">单位名称：</td>      
   <td><input id="companyNameinfo" name="companyName"></input><span class="fontShow"><font color="red">*</font></span></td>  
    <td>单位编码：</td>      
   <td><input id="companyCodeinfo" name="companyCode"></input></td> 
   </tr>
   <tr> 
   <td>单位地址：</td>      
   <td><input id="address" name="address"></input></td>     
   <td>单位类型：</td>      
  <!--  <td><input id="qy_nameinfo" name="qy_name"></input><span class="fontShow"><font color="red">*</font></span></td>   -->
   <%if(role==2 || role==1){%>  
   <td><select id="typeinfo"   name="type" style="width:130px;" onchange="ispasteyw2()">
    <option value="制造" selected ="selected">制造</option>
    <option value="安装">安装</option> 
    <option value="使用">使用</option> 
    <option value="检验">检验</option> 
    <option value="维保">维保</option>
    <option value="市质监">市质监</option> 
    <option value="质监">质监</option>
    <option value="街道办">街道办</option> 
    <option value="粘贴">粘贴</option>          
</select><span class="fontShow"><font color="red">*</font></span>
</td> 
<%} else {%>
 <td><select id="typeinfo"   name="type" style="width:110px;" > 
 <option value="制造" selected ="selected">制造</option>
    <option value="安装">安装</option> 
    <option value="使用">使用</option> 
    <option value="检验">检验</option> 
    <option value="维保">维保</option>
    <option value="质监">质监</option>
    <option value="街道办">街道办</option>   
   </select><span class="fontShow"><font color="red">*</font></span>
</td>
 <%}%> 
   <td><div id="pasteyw2">
   <select name="ispasteyw" id="ispasteyw">
   <option value="0">不粘贴</option> 
   <option value="1">粘贴</option> 
   </select>
   </div></td> 
   </tr>
   <tr>
    <td>联系电话：</td>      
   <td><input id="phone" name="phone"></input></td>  
    <td>联系人：</td>      
   <td><input id="contact" name="contact"></input></td> 
   </tr>
      <tr>  
   <td>证书名称：</td>      
   <td><input id="certificateName" name="certificateName"></input></td>       
   <td>证书编号：</td>      
   <td><input id="certificateCode" name="certificateCode"></input></td>  
       
   </tr>
    <tr>      
   <td>邮编：</td>      
   <td><input id="zip" name="zip"></input></td>  
    <td id="zhaopian">照片：</td>      
   <td><input type="file" id="userPic" name="userPic"></td>
   </tr>    
   </table>
   <table width=70%>
    <tr>
    <td align="center">
      <a href="javascript:void(0)" onclick="saveCar()" id="btn-save" icon="icon-save">保存</a>  
     <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">关闭</a> 
    </td>
    </tr> 
   </table>   
   </form> 
    </div> 
     <div id="companyImg"  style="overflow:hidden;float:left;border:0px solid #000;"></div>
   </div>
   <div id="company-window8" title="公司名称变更详细信息" style="width:780px;height:550px;">
   <div style="margin-top:1px;" title="变更信息列表">  
       <table id="ccmap"></table>
   </div> 
   </div>
   
    <div id="hmdWin" class="easyui-window" title="进入黑名单原因" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false" style="width:400px;height:400px;padding:10px;">
     <div class="easyui-layout" data-options="fit:true">
     <div region="center">
      <table style="100%">
     <tr>
       <td><textarea rows="20" cols="56"  id="hmdreason" style="border:0;overflow:hidden"></textarea></td>
    </tr>
    <tr><td><input type="hidden" id="ywcompanyIdhmd"><input type="hidden" id="ywcompanyNamehmd"></td>
    </tr>
     </table>
     </div>
     <div region="south" style="height:52px">
     <table  style="width: 100%;margin:auto">
      <tr>
    <td align="center">
      <a href="javascript:void(0)" onclick="savehmd()" id="btn-save" icon="icon-save">确定</a>  
    </td>
    </tr> 
     </table>
     </div>
     </div> 
   </div>
</body>
</html>