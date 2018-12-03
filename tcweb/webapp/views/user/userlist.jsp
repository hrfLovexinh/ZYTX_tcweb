<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache"> 
     <meta http-equiv="cache-control" content="no-cache"> 
     <meta http-equiv="expires" content="0">   
<title>电梯安全公共服务平台</title>
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"> -->
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
int role=0;
int ispcsuper = 0;
int isliulan = 0;
int myid = 0;
String userName = "";
String password = "";
if(userinfo!=null){
	 role = userinfo.getRole(); 
	 ispcsuper =userinfo.getIspcsuper();
	 isliulan = userinfo.getIsliulan();
	 myid = userinfo.getId();
	}
	else{
		 Cookie[] cookies =  request.getCookies();
		
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
		    
		    UserInfoVO user2 =UserInfoVO.findFirstBySql(UserInfoVO.class, "select ispcsuper,isliulan,userid as id from  TCUserInfo where loginName= ? and isinvalid = 0 ",new Object[] { userName });
		    if(user2 != null){
		   	  ispcsuper = user2.getIspcsuper();
		   	  isliulan = user2.getIsliulan();
		      myid =user2.getId();
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

var opt =0; //0:增加 ；1：编辑

$(function(){
	$.ajaxSetup ({
	    cache: false 
	});

    
    $('#ispcsuper').attr("value",<%=ispcsuper%>);
	$('#imgpicu').bind("click",testDidu);

    <% if(role ==10 || role ==11){%>
    var wgurl = encodeURI(encodeURI('/tcweb/elevator/findAutoUserCompanyList')); 
    <%} else if(role ==22 || role ==23) { %>
    var wgurl = encodeURI(encodeURI('/tcweb/elevator/findAutoUserCompanyList')); 
    <%} else {%>
  
    var wgurl = encodeURI(encodeURI('/tcweb/elevator/findAutoUserCompanyList2')); 
    <% }%> 

    <% if(role ==10 || role ==11){%>
    $('#companyId').combobox({
    	url:'/tcweb/elevator/getZjCompanyList',
	    valueField:'id',
	    textField:'companyName'
	});

	$('#companyId').combobox({
  	filter: function(q, row){
  		var opts = $(this).combobox('options');
  		return row[opts.textField].indexOf(q) >= 0;
  	}
  });

	 $('#companyName').combobox({
	    	url:'/tcweb/elevator/getZjCompanyList',   
		    valueField:'companyName',
		    textField:'companyName'
		});

		$('#companyName').combobox({
	  	filter: function(q, row){
	  		var opts = $(this).combobox('options');
	  		return row[opts.textField].indexOf(q) >= 0;
	  	}
	  });
	  
	<%} else if(role ==22 || role ==23) { %>
	 $('#companyId').combobox({
		    url : '/tcweb/elevator/getZjCompanyList',
		    valueField:'id',
		    textField:'companyName'
		});

		$('#companyId').combobox({
	  	filter: function(q, row){
	  		var opts = $(this).combobox('options');
	  		return row[opts.textField].indexOf(q) >= 0;
	  	}
	 });

		 $('#companyName').combobox({
		//    	url:'/tcweb/elevator/getnewZjCompanyList',
		        url:'/tcweb/elevator/getKpZjCompanyList',
			    valueField:'companyName',
			    textField:'companyName'
			});

			$('#companyName').combobox({
		  	filter: function(q, row){
		  		var opts = $(this).combobox('options');
		  		return row[opts.textField].indexOf(q) >= 0;
		  	}
		  });
	  	
    <% } else { %> 
   
    $("#companyId2").autocomplete(  
		    wgurl,  
            {  
            scroll: false,  
                matchContains: true,  
                width: 188,  
                minChars: 2,
                max:20,
                scrollHeight:100,  
                extraParams: {	q: function() {return $("#this").val();},
			    compayType: function(){ return $('#companyType option:selected').val(); }
			    },   
                dataType: "json",  
                mustMatch:true,  
                matchSubset:false,
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
        	 $('#companyId').attr("value",formatted);     
            }
            else{
            $('#companyId').attr("value",0);
 	            }
	        }); 
    <% }%> 

   // $("#companyId2").flushCache();   
	
  //	$('#btn-save,#btn-cancel').linkbutton(); 
	$('#btn-save2,#btn-save3').linkbutton(); 
	win = $('#user-window').window({  closed:true,draggable:false,minimizable:false,collapsible:false,maximizable:false });     
	rwindow =$('#rightswindow').window({  closed:true,draggable:false,modal:true }); 
	awindow =$('#attributewindow').window({  closed:true,draggable:false,modal:true }); 
	form = win.find('form');

	 
	grid=$('#tt').datagrid({
	    title:'用户列表',
	    fitColumns:true,
	    striped:true,
	    pageSize:25,
	    pageList:[15,25,30,35,40],
	    url:'/tcweb/user/userlist',
	    queryParams:{},
	    columns:[[
            {field:'loginName',align:'center',halign:'center',title:'登陆账号',width:$(this).width() * 0.09,formatter: function(value,rec,index) {
                var isyanshi = rec.isyanshi; 
                var isliulan = rec.isliulan;
                var userId =rec.id;
                var str=""
                <% if(ispcsuper == 1){ %>    
                      if(isyanshi == 1){
                          str="演示账号";
                      }
                      
                      if(isliulan == 1){
                         if(str != "")
                             str = str+","+"浏览账号";
                         else
                             str ="浏览账号";
                          }

                     if(str !="")
                       return "<a href = '#' onclick='zhanghaoSettings("+userId+")' style='text-decoration:none'>"+value+"("+str+")"+"</a>";
                     else
                       return "<a href = '#' onclick='zhanghaoSettings("+userId+")' style='text-decoration:none'>"+value+"</a>";  
                    
                <%} else {  %>   
                    return value;
                <%}%>
            }
                },
	        {field:'userName',align:'center',halign:'center',title:'姓名',width:$(this).width() * 0.09,formatter: function(value,rec,index) {
                var iskaoping = rec.iskaoping;
                if(rec.iskaoping == 1)
                   return value+"<img src='<%=request.getContextPath()%>/images/rights.png' title='考评人员'";
                else
                    return value;
                  }},
	        {field:'idCard',align:'center',halign:'center',title:'身份证',width:$(this).width() * 0.09},
	        {field:'companyName',align:'left',halign:'center',title:'单位名称',width:$(this).width() * 0.13},
	        {field:'comanyType',align:'center',title:'单位类型',width:$(this).width() * 0.05},       
	        <%if(role==1 || role==2){
	           if(ispcsuper == 1) {%>
	          {field:'password',align:'center',title:'密码',width:$(this).width() * 0.09},
	          <% }else {%>
	          {field:'password',align:'center',title:'密码',width:$(this).width() * 0.09,formatter: function(value,rec,index) {
                  var myid =<%=myid%>;
                  var uid =rec.id;
                  var role = rec.role;
                  if(myid == rec.id)
                      return value;
                  else if((role == 1 || role == 2))
                      return "";
                  else
                      return value;
		          }},
	        <% }}%>
	        {field:'contactPhone',align:'center',halign:'center',title:'联系电话',width:$(this).width() * 0.09},
	        {field:'telephonemobile',align:'center',halign:'center',title:'移动电话',width:$(this).width() * 0.09},
	        {field:'qualificationvalidate',align:'center',title:'资质有效期',width:$(this).width() * 0.09},
	        {field:'qregistereddate',align:'center',title:'注册日期',width:$(this).width() * 0.09}, 
	        {field:'type',align:'center',title:'角色',width:$(this).width() * 0.09,formatter: function(value,rec,index) {
		        var userId =rec.id;
		        var selfrole=rec.role;
		        if(value=='管理员'){
	        	<%if(role==1 || role==2){%>
	        	 if(selfrole==10 || selfrole==11)
	        	     return  value+" "+"<img src='<%=request.getContextPath()%>/images/rights.png' alt='统计'  style='cursor:hand;' onclick='rightDistribut("+userId+")' />"; 
	        	 else if(selfrole==22 || selfrole==23) 
	        		 return  value+" "+"<img src='<%=request.getContextPath()%>/images/rights.png' alt='统计'  style='cursor:hand;' onclick='rightDistribut("+userId+")' />";
	        	 else if(selfrole==2){ 
                      if(rec.ispcsuper == 1)
                          return "超级"+value;
                      else
                          return value;
		        	 }
		        	 
		         else
		        	 return value; 
		        	 
	        	<%} else {%>
                return value;
                <%}%>
		        }
		        else{
                return value;
			        }
		        }} 
	    ]],
	    nowrap:true,
	    pagination:true,
	    singleSelect:true,
	    <% if(isliulan == 0){%>
	    toolbar:[{
	        text:'新增',
	        iconCls:'icon-add',
	        handler:function(){
	    	win.window('open');  
	 //   	form.form('clear');
	    	addFun();
	    	form.url ='/tcweb/user/add';	
	    	colseWinDetail();	 
	        }
	    },{
	        text:'删除',
	        iconCls:'icon-cut',
	        handler:function(){
	    	 var row = grid.datagrid('getSelected'); 
	    	 if(row){
	    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
	    	 jQuery.post('/tcweb/user/delete',
	    	    	 {'id':row.id},
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
	    },'-',{
	        text:'修改',
	        iconCls:'icon-edit',
	        handler:function(){
	    	var row = grid.datagrid('getSelected');   
	    	if (row){   
	    		 win.window('open');   
	    		 colseWinDetail();
	    		 ubianjixiala(row.companyId,row.companyName);
	    		 unbindingUId = row.id;
	    	//	 companyNameList(row.id);  
	    	
	    <%	if(role ==22 || role ==23) {  %>
	             companyIdcombox(row.comanyType);
	      <% } 
	      if(role == 10 || role ==11) {%>
	             companyIdcombox2(row.comanyType);
	      <%}%>	     
	    		 form.form('load', '/tcweb/user/edit/'+row.id);
	    		 form.url = '/tcweb/user/update/'+row.id; 
	    		 opt =1;
	    		 <% if(role !=1 && role != 2){%>
	    		 $('#loginName').attr("disabled","disabled");
	    		 <%}%>
	    	 } else {  
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。' 
	    		 });   
	    	 }  
	        }
	    }
      <% if(role==22 || role==23) {
    	  if("0".equals(cityName)){
      %>
      ,'-',{
    	  text:'授予考评',
	        iconCls:'icon-edit',
	        handler:function(){
	    	var row = grid.datagrid('getSelected'); 
	    	if (row){
	    		 $.messager.confirm('','确定要授予考评',function(data){if(data){
	    		 jQuery.post('/tcweb/user/updateKPRightsById',
	    		    	 {'id':row.id},
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
	    		    			$.messager.alert('操作失败','权限更新失败','error');
	    	    	    		}
	    	    	       });
	    		 }});
	    		 }
	    	else {  
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。' 
	    		 });   
	    	 }  
          }
      },'-',{
    	  text:'取消考评',
	        iconCls:'icon-edit',
	        handler:function(){
	    	var row = grid.datagrid('getSelected'); 
	    	if (row){
	    		 $.messager.confirm('','确定要取消考评',function(data){if(data){
	    		 jQuery.post('/tcweb/user/updateKPRightsById2',
	    		    	 {'id':row.id},
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
	    		    			$.messager.alert('操作失败','权限更新失败','error');
	    	    	    		}
	    	    	       });
	    		 }});
		    	}
	    	else {  
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。' 
	    		 });   
	    	 }  
        }
    }    
      <%} }%>
	    ]
	    <% } %> 
	});	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  

});

var comId = 0;
function ubianjixiala(companyId,companyName){
	$('#companyId2').attr("value",companyName);
		
}

function comxialafuzhi(id,zzName){   
	  $('#companyId2').attr("value",zzName);
	  $('#companyId').attr("value",id);
	 }

function clearQuery(){
	$('#loginNameinfo').attr("value","");
	$('#userNameinfo').attr("value","");
	<% if(role ==10 || role ==11){%>
	var companyName=$('#companyName').combobox('setValue',""); 
	<%} else if(role ==22 || role ==23) { %>
	var companyName=$('#companyName').combobox('setValue',"");
	<%} else {%>
	$('#companyName').attr("value","");
	<% } %>
}

function query(){  
	 var loginName=$('#loginNameinfo').attr("value");
	 var idCard=$('#loginNameinfo').attr("value");
	 var userName=$('#userNameinfo').attr("value");
	 <% if(role ==10 || role ==11){%>
		var companyName=$('#companyName').combobox('getValue'); 
		<%} else if(role ==22 || role ==23) { %>
		var companyName=$('#companyName').combobox('getValue'); 
		<%} else {%>
	 var companyName=$('#companyName').attr("value");
	 <% } %>
     grid.datagrid("options").url='/tcweb/user/query';
     grid.datagrid("options").queryParams={'loginName':loginName,'idCard':idCard,'userName':userName,'companyName':companyName};
     
    $('#tt').datagrid('reload');
	}
	

	
function openCarinfoDetail(id){ 
	win.window('open'); 
	
	form.form('load', '/czweb/car/edit/'+id);
	showWinDetail();
}

function closeWindow(){ 
	win.window('close');
	}

function showWinDetail(){
	$("form input").css({border:'0px solid' });
	
	$('#dev_idinfo').attr("readonly","readonly");
	$('#carnuminfo').attr("readonly","readonly");
	$('#qy_nameinfo').attr("readonly","readonly");
	$('#simnuminfo').attr("readonly","readonly");
	$('#dev_typeinfo').attr("readonly","readonly");
	$('#car_typeinfo').attr("readonly","readonly");
	$('#carnum_colorinfo').attr("readonly","readonly");
	$('#car_colorinfo').attr("readonly","readonly");
	$('#longitudeinfo').attr("readonly","readonly");
	$('#latitudeinfo').attr("readonly","readonly");
	$('#angleinfo').attr("readonly","readonly");
	$('#speedinfo').attr("readonly","readonly");
	$('#gps_timeinfo').attr("readonly","readonly");
	$('#personinfo').attr("readonly","readonly");
	$('#phoneinfo').attr("readonly","readonly");
	
	$(".fontShow").hide();
//	$('#btn-save').hide();
	$('#btn-cancel').show();
	$('#table2').show();

	$('#qy_nameinfo').combobox('disable');   
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

function isCardNo(card)  
{  
   // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X   
   var reg = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;  
   if(!reg.test(card))  
   {  
   //   alert("身份证输入不合法");  
       return  false;  
   } 
  
	   return true; 
}  


function saveUser(){ 
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){
     //   return $(this).form('validate');
		var loginName=$('#loginName').attr("value"); 
		var password=$('#password').attr("value"); 
		var userName=$('#userName').attr("value");  
		<% if(role ==10 || role ==11){%>
		var companyId=$('#companyId').combobox('getValue'); 
		<%} else if(role ==22 || role ==23) { %>
		var companyId=$('#companyId').combobox('getValue'); 
		<%} else {%>
		var companyId=$('#companyId').attr("value");
		<%}%>
	//	
	//	var type= $('#type').combobox('getValue'); 
	//	var threedscanning=$('#threedscanning').combobox('getValue');
	  
	    var type= $('#type option:selected').val();
	    <% if("0".equals(cityName)){ %>
	    var threedscanning=$('#threedscanning option:selected').val();
	    <% }%>
		var idCard =$('#idCard').attr("value"); 
		var speEquQualification=$('#speEquQualification').attr("value"); 
		var contactPhone=$('#contactPhone').attr("value");  
	//	var binding =$('#binding').combobox('getValue');
		var qualificationvalidate =$('#qualificationvalidate').datebox('getValue');   
	//	var qualificationvalidate =$('#qualificationvalidate').datetimebox('getValue');  alert("623---"+qualificationvalidate);
		if(!loginName){
		  $.messager.alert('操作失败','登陆名称不能为空','error');
		  return false;
			}
		if(!password){
		  $.messager.alert('操作失败','密码不能为空','error');
		  return false;
			}
		if(!userName){
			  $.messager.alert('操作失败','用户名不能为空','error');
			  return false;
				}
	/*	if(!binding){
			 $.messager.alert('操作失败','绑定状态不能为空','error');
			  return false;
			}  */
		
		if(!companyId || companyId == 0){
			  $.messager.alert('操作失败','所在单位不能为空','error');
			  return false;
				}
		if(!type){
			  $.messager.alert('操作失败','类型不能为空','error');
			  return false;
				}
		 <% if(ispcsuper==1){ %>
		if(!threedscanning){
			  $.messager.alert('操作失败','3D扫描不能为空','error');
			  return false;
				}
        <%}%>
		if("员工"==type){
			if(!idCard){
				  $.messager.alert('操作失败','添加职员时身份证号不能为空','error');
				  return false;
					}
			if(!speEquQualification){
				  $.messager.alert('操作失败','添加职员时维保人员资质作业种类不能为空','error');
				  return false;
					}
			if(!contactPhone){
				  $.messager.alert('操作失败','添加职员时联系电话不能为空','error');
				  return false;
					}
			if(!isCardNo(loginName)){ 
				 $.messager.alert('操作失败','添加职员时登录名必须为身份证号','error');
				 return false;
				}
			}
     /*
		if(opt==0){ //添加的时候，不能选择已经绑定
           if(binding==2){
        	   $.messager.alert('操作失败','添加时电话绑定状态不能选择已绑定，请重新选择电话绑定状态','error');
        	   return false;
               }
			}  */
		 var validator = form.form('validate');
	        if(validator){
	          $.messager.progress();
	        }
	    
		return true;  
	//	return false;
    },
		  
		success:function(data){ 
    	$.messager.progress('close');   
		eval("data="+"'"+data+"'"); 
		if("exist"==data){
			$.messager.alert('操作失败','已经存在该登陆名，不能重复添加','error');
			} 
		else if("failure2"==data){
			$.messager.alert('操作失败','系统目前只有维保公司才能添加员工','error');
			}
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
			$.messager.alert('操作失败','添加用户','error');
			}
		} 
	});
	}

function addFun(){
	  opt =0;   //0:代表增加，1代表修改
	  $('input').val("");  //让所有input框输入值清空
	 <% if(ispcsuper == 1){%>
      $('#ispcsuper').attr("value",1);
	 <%} else {%>
	 $('#ispcsuper').attr("value",0);
	 <%}%>
	  $(".selector").find("option[text='未绑定']").attr("selected",true);
	  $('#loginName').attr("disabled","");
	  }

function selectChecbox(obj){  //1代表选中绑定，0代表没有选中
	   if(obj){ 
	   if(obj.checked==true)
	   obj.value = 1;
	   else{
	    obj.value = 0;
		   }
		   }
	}

var unbindingUId = 0;
function testDidu(){
	 $.get("/tcweb/user/unbinding",{loginName:$('#loginName').attr("value"),'id':unbindingUId},function (data, textStatus){
    	 if(data=="0") { //alert(data);
    		$.messager.alert('操作失败', '解除绑定失败', 'error'); 
    		return false;
    		}
 		else{
 		//	$(".selector").removeAttr("disabled");
 			$(".selector").find("option[text='未绑定']").attr("selected",true);
 		//	$(".selector").attr("disabled","disabled");  
 			$.messager.alert('操作成功', '已经解除绑定', 'info'); 
     		}
	  });
}

var auserId=0;
function zhanghaoSettings(uId){
	auserId =uId;
	awindow.window('open');
	jQuery.post('/tcweb/user/attributeById', {'id':uId},function(data){ 
        data = eval(data);//POST方法必加，ajax方法自动处理了 
        isyanshi =data.isyanshi; 
        isliulan =data.isliulan; 
        $('#isyanshi').val(isyanshi);
        $('#isliulan').val(isliulan);
	   }, 'json');
}

function saveattribute(){
	jQuery.post('/tcweb/user/updateattributeById',
	    	 {'id':auserId,'isyanshi':$('#isyanshi option:selected').val(),'isliulan':$('#isliulan option:selected').val()},
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

var userId=0;
var ywtx =0;
function rightDistribut(uId){
	userId=uId;
	rwindow.window('open');
	jQuery.post('/tcweb/user/rightDistributById', {'id':uId},function(data){ 
        data = eval(data);//POST方法必加，ajax方法自动处理了 
        ywtx =data.ywtx;  
        $('#ywtx').val(ywtx);
	   }, 'json');
}



function saveRights(){
	 jQuery.post('/tcweb/user/updateRightsById',
	    	 {'id':userId,'ywtx':$('#ywtx option:selected').val()},
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
	    			$.messager.alert('操作失败','权限更新失败','error');
    	    		}
    	       });
	 rwindow.window('close');
}

function getzjAndjdbCompanyList(){
	$('#companyId').combobox({
        url: '/tcweb/elevator/getzjAndjdbCompanyList?companyType='+encodeURI($('#companyType option:selected').val()),
        valueField: 'id',
        textField: 'companyName'
    }).combobox('clear');
}


function getzjCompanyList(){
	$('#companyId').combobox({
        url: '/tcweb/elevator/getZjCompanyList2?companyType='+encodeURI($('#companyType option:selected').val()),
        valueField: 'id',
        textField: 'companyName'
    }).combobox('clear');
}

function companyIdcombox(comanyType){
	$('#companyId').combobox({
        url: '/tcweb/elevator/getZjCompanyList2?companyType='+encodeURI(comanyType),
        valueField: 'id',
        textField: 'companyName'
    }).combobox('clear');
}

function companyIdcombox2(comanyType){
	$('#companyId').combobox({
        url: '/tcweb/elevator/getZjCompanyList2?companyType='+encodeURI(comanyType),
        valueField: 'id',
        textField: 'companyName'
    }).combobox('clear');
}

function secAutoComplete(){
	 $("#companyId2").unautocomplete();
	 var wgurl = encodeURI(encodeURI('/tcweb/elevator/findAutoUserCompanyList2')); 
	  $("#companyId2").autocomplete(  
			    wgurl, 
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2,
	                max:20,
	                scrollHeight:100,  
	                extraParams: {	q: function() {return $("#this").val();},
				    compayType: function(){ return $('#companyType option:selected').val(); }
				    },   
	                dataType: "json",  
	                mustMatch:true,  
	                matchSubset:false,
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
	        	 $('#companyId').attr("value",formatted);     
	            }
	            else{
	            $('#companyId').attr("value",0);
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
 /* background: #F9F0DA; */
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
<div region="north" style="overflow:hidden;;background-color:rgb(201,220,245);height:80px;">  
 <fieldset id="addDiv" style="width: 100%;height:100%;margin:0px;border: 1px solid #61B5CF;"><legend style="color:#0E2D5F;font-size:12px;font-weight: bold;">查询条件</legend>
    
    <table id="sousuo"> 
     <tr> 
     <td align="right" nowrap>登陆名：</td> 
   <td nowrap><input id="loginNameinfo" name="loginName" style="width:220px" class="easyui-validatebox"></input></td>     
   <td align="right" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户姓名：</td> 
   <td nowrap><input id="userNameinfo" name="userName" style="width:220px" class="easyui-validatebox"></input></td>
   <td align="right" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位名称：</td> 
   <%if(role==10 || role==11){%>
   <td nowrap><select id="companyName" name="companyName" class="easyui-combobox" style="width:220px;height:30px;"></select></td>
   <% } else if(role ==22 || role ==23) {  %>
   <td nowrap><select id="companyName" name="companyName" class="easyui-combobox" style="width:220px;height:30px;"></select></td>
   <% }else { %>
   <td nowrap><input id="companyName" name="companyName" style="width:220px" class="easyui-validatebox"></input></td>
   <% }%>
   <td nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<!--		<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  -->
			 <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
			<!--	<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a>   -->
			<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>
			<!--		<input type="submit" value="Submit Comment" /> -->	
					
</td>
 <td>
 </td>
 <td>
 </td>
<td>
 </td>				
   </tr>
   
     </table>
  
  </fieldset>
</div> 
   <div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div style="width:100%;">  
       <table id="tt"></table>
   </div>  
        
    </div>  
</div> 
<div id="user-window" title="详细信息" style="width:600px;height:570px;"> 
<div class="easyui-layout" data-options="fit:true">  
  <form method="post"> 
  <div data-options="region:'north'" style="height:430px;text-align:center;" >   
  <table  style="width:100%;margin:auto">    
   <tr>      
   <td width="150" align="center" style="background: #F5F5F5;">登陆名</td>      
   <td><input class="form_input" id="loginName" name="loginName" class="easyui-validatebox" data-options="required:true,validType:'length[1,30]'"></input></td>  
    <td width="150" align="center" style="background: #F5F5F5;">密码</td>      
   <td><input class="form_input" id="password" name="password" type="password" class="easyui-validatebox" data-options="required:true,validType:'length[1,15]'"></input></td> 
   </tr>
   <tr> 
   <td align="center" style="background: #F5F5F5;">用户名</td>      
   <td><input class="form_input" id="userName" name="userName" class="easyui-validatebox" data-options="required:true,validType:'length[1,15]'"></input></td> 
   <td align="center" style="background: #F5F5F5;">身份证号</td>      
   <td><input class="form_input" id="idCard" name="idCard"></input></td> 
    </tr>
   <tr> 
    <td align="center" style="background: #F5F5F5;">联系电话</td>      
   <td><input class="form_input" id="contactPhone" name="contactPhone"></input></td>
   <td align="center" style="background: #F5F5F5;">移动电话</td>      
   <td><input class="form_input" id="telephonemobile" name="telephonemobile"></input></td>
   </tr>
  
    <tr>  
          
<td align="center" style="background: #F5F5F5;">单位类型</td>  
 <%if(role==10 || role==11){%> 
 <td><select id="companyType"   name="companyType" style="width:130px;height:34px" onchange="getzjAndjdbCompanyList()"> 
 <option value="质监">质监</option> 
 <option value="街道办">街道办</option>   
 </select></td>
 <%}
 else if (role==22 || role==23){%>
 <td><select id="companyType"   name="companyType" style="width:130px;height:34px" onchange="getzjCompanyList()">
 <option value="质监">质监</option>   
 <option value="市质监">市质监</option> 
 <option value="检验">检验</option>
  <option value="行业协会">行业协会</option>   
 </select></td>
 <%}
 else if(role == 2){ %>
 <td><select id="companyType"   name="companyType" style="width:130px;height:34px;" onchange="secAutoComplete()"> 
    <option value="制造">制造</option>
    <option value="安装">安装</option> 
    <option value="使用">使用</option> 
    <option value="检验">检验</option> 
    <option value="维保">维保</option>
    <option value="街道办">街道办</option>
    <option value="市质监">市质监</option> 
    <option value="质监">质监</option>   
    <option value="粘贴">粘贴</option> 
    <option value="系统组">系统组</option>
</select></td>
<%}
 else {%>	 
 <td><select id="companyType"   name="companyType" style="width:130px;height:34px;" onchange="secAutoComplete()"> 
    <option value="制造">制造</option>
    <option value="安装">安装</option> 
    <option value="使用">使用</option> 
    <option value="检验">检验</option> 
    <option value="维保">维保</option>
    <option value="街道办">街道办</option>
    <option value="市质监">市质监</option> 
    <option value="质监">质监</option>   
    <option value="粘贴">粘贴</option> 
</select></td>
    <%}%>
    </tr>
    <tr> 
    <td align="center" style="background: #F5F5F5;">所在单位</td> 
  <%if(role==10 || role==11){%>
      <td colspan="3"><select id="companyId" name="companyId" class="easyui-combobox" style="width:100%;height:34px;"></select></td>
    <%}
  else  if(role==22 || role==23){ %>
 <td colspan="3"><select id="companyId" name="companyId" class="easyui-combobox" style="width:100%;height:34px;"></select></td>
 <%}  
  else {%>
   <td colspan="3"><input id="companyId2"  name="companyId2" style="width:100%;height:34px;"></input>
   <input id="companyId" name="companyId" type="hidden"></input>
   </td>
   <%}%>
   </tr>
    <tr> 
     <td align="center" style="background: #F5F5F5;">类型</td>      
  <!--  <td><input id="qy_nameinfo" name="qy_name"></input><span class="fontShow"><font color="red">*</font></span></td>   -->  
    <%if(role==10 || role==11){%>
      <td><select id="type" name="type" style="width:130px;height:34px;">
    <option value="管理员">管理员</option>
    <option value="操作员">操作员</option> 
</select></td>
    <%} 
    else  if(role==22 || role==23){ %>
     <td><select id="type" name="type" style="width:130px;height:34px;">
    <option value="管理员">管理员</option>
    <option value="操作员">操作员</option> 
</select></td>
    <%}  
    else {%>
   <td><select id="type"  name="type" style="width:130px;height:34px;">
    <option value="管理员">管理员</option>
    <option value="操作员">操作员</option>
    <option value="员工">员工</option>    
</select>
</td>      
      <%}%>
 <%if(ispcsuper == 1){ %> 
 <% if(!"3".equals(cityName)){ %>
   <td align="center" style="background: #F5F5F5;">3D扫描</td>
<td>
 <select id="threedscanning"  name="threedscanning" style="width:132px;height:34px;">
    <option value="0">关闭</option>
    <option value="1">开启(低)</option> 
    <option value="2">开启(中)</option> 
    <option value="3">开启(高)</option> 
</select>
</td> 
<% } %> 
  <%}%>  
   </tr>
    <tr>
    <td align="center" style="background: #F5F5F5;">维保人员资质作业种类</td>      
   <td><input class="form_input" id="speEquQualification" name="speEquQualification"></input></td> 
   <td align="center" style="background: #F5F5F5;">资质有效期</td>      
   <td><input  id="qualificationvalidate" name="qualificationvalidate" class="easyui-datebox" data-options="editable:false" style="height:34px;"></input></td> 
    </tr>
     <tr>
     <td align="center" style="background: #F5F5F5;">注册日期</td>      
   <td><input  id="qregistereddate" name="qregistereddate" class="easyui-datebox" data-options="editable:false" style="height:34px;"></input></td>
   <td align="center" style="background: #F5F5F5;">电话绑定状态</td> 
   <td title="解除绑定"><select id="binding" name="binding"  class="selector" style="width:130px;height:34px;" disabled="disabled"/>
    <option value="0">已绑定</option>
    <option value="1">未绑定</option> 
   </select><img id="imgpicu"  name="imgpic2" src="../../images/html.png" style="cursor: hand" /></td> 
</tr>
   <tr>
   <td><input type="hidden" id="ispcsuper" name="ispcsuper" value="0"></td>
   </tr>
   </table>
   <table  style="width: 100%;margin:auto">
    <tr>
   </tr>
    <tr>
   </tr>
   <tr>
   </tr>
    <tr>
   </tr>
    <tr>
   </tr>
    <tr>
    <td align="center">
  <!--   <a href="javascript:void(0)" onclick="saveUser()" id="btn-save" icon="icon-save">保存</a>  
     <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">取消</a>   --> 
      <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-save fa-lg" onclick="saveUser()" style="width:100px;color:#3399FF;">保存</a>
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-close fa-lg" onclick="closeWindow()" style="width:100px">取消</a>
     
    </td>
    </tr> 
   </table>
   </div>
   <div data-options="region:'center'" style="overflow-x:auto;overflow-y:hidden">
   <span class="fontShow"><font color="red">说明：<br>
   1.修改用户资料的时候，“登录名”不能修改<br>
   2.“单位类型”一栏，市质监对应市质监局，质监对应各个区县质监局<br>
   3.“所在单位”一栏，请输入至少2个关键字从弹出的下拉列表中选择列表中选择</font></span>
   </div> 
     
   </form> 
   
     </div>
   </div>
  
  
   <!--  
    <div style="text-align:center;padding:5px;">    
     <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">取消</a>  </div> 
    </div> -->
    
    <div id="rightswindow" title="权限分配" style="width:380px;height:350px;overflow-x:hidden;overflow-y:hidden;"> 
    <div class="easyui-layout" data-options="fit:true"> 
     <div data-options="region:'north'" style="height:250px;text-align:center;" > 
     <table style="width: 100%;margin:auto">
     <tr> 
      <td align="center" style="background-color:#ffffcc;">维保记录状态图像查看：</td> 
       <td><select id="ywtx"  name="ywtx" style="width:110px;">
    <option value="0">关闭</option>
    <option value="1">打开</option>  
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
    <tr>
   <td  align="center">
    <a href="javascript:void(0)" onclick="saveRights()" id="btn-save2" icon="icon-ok">确定</a>  
   </td>
   </tr>
     </table>
   </div>
   </div>
   </div>
   
   <div id="attributewindow" title="账号属性" style="width:380px;height:350px;overflow-x:hidden;overflow-y:hidden;"> 
     <div class="easyui-layout" data-options="fit:true"> 
     <div data-options="region:'north'" style="height:250px;text-align:center;" > 
     <table style="width: 100%;margin:auto">
    <tr> 
    <td align="center" style="background-color:#ffffcc;">演示账号：</td> 
    <td>
    <select id="isyanshi"  name="isyanshi" style="width:110px;">
    <option value="0">不是</option>
    <option value="1">是</option>  
    </select>
    </td>
    </tr>
    <tr> 
    <td align="center" style="background-color:#ffffcc;">浏览账号：</td> 
    <td>
    <select id="isliulan"  name="isliulan" style="width:110px;">
    <option value="0">不是</option>
    <option value="1">是</option>  
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
    <tr>
   <td  align="center">
    <a href="javascript:void(0)" onclick="saveattribute()" id="btn-save3" icon="icon-ok">确定</a>  
   </td>
   </tr>
    </table>
   </div>
   </div>
   </div>
</body>
</html>