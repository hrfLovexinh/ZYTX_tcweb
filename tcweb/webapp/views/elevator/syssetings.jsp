<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>成都市电梯安全公共服务平台</title>
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"> -->
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">
<% 
String cityName = GlobalFunction.cityName;
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int isliulan = 0;
int  role = 0; 
if(userinfo!=null){
	 role = userinfo.getRole(); 
	 isliulan = userinfo.getIsliulan();
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
			 if(user != null)
			    role = user.getRole();
			 else
			    response.sendRedirect(request.getContextPath() +"/index.jsp");
			 
			 UserInfoVO user2 =UserInfoVO.findFirstBySql(UserInfoVO.class, "select isliulan from  TCUserInfo where loginName= ? and isinvalid = 0 ",new Object[] { userName });
			 if(user2 != null)
			    isliulan = user2.getIsliulan();
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

$(function(){
	$.ajaxSetup ({
	    cache: false 
	});
    /*
	 $('#yw').tabs({  
         width: $("#yw").parent().width(),   
       height: "auto"  
        });  
	*/
	
	form =$("form[name='ywcqform']");
	form.url='/tcweb/elevator/sysYwcqSetings';	

	tcform =$("form[name='tcchangeform']");
	tcform.url='/tcweb/elevator/eletcinfochange';
//	$('#sbtn-save').linkbutton();

//	$('#ywsbtn-save, #tcchangebtn-save').linkbutton();

	form.form('load', '/tcweb/elevator/sysYwcqSetingsEdit/'); 

	ywgsform =$("form[name='ywgsform']");
	ywgsform.url='/tcweb/elevator/sysYwgshbSetings';	

	
     var pmheight = $(window).height()-150;
	 $('#setingsLayout').layout('panel', 'north').panel('resize',{height:pmheight*0.35});
	 $('#setingsLayout').layout('panel', 'south').panel('resize',{height:pmheight*0.55});
	 $('#setingsLayout').layout('resize');

	 var url = encodeURI(encodeURI('/tcweb/elevator/getAutoYwCompanyList')); 
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

	 $("#nywCompanyIdinfo").autocomplete(  
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
	        	 $('#nywCompanyIdinfo2').attr("value",formatted);
	            }
	            else{
	             $('#nywCompanyIdinfo2').attr("value",'');
	 	            }
	        });
});

function strDateTime(str)
{
var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
if(r==null)
return false; 
var d= new Date(r[1], r[3]-1, r[4]); 
return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
}

function saveSetings(){
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){  
		var ywcqswitch=$('#ywcqswitch').combobox('getValue');
        var qstartTime=$('#qstartTime').datebox('getValue');
        var qendTime=$('#qendTime').datebox('getValue');

		if (qstartTime != "") {
			if (!strDateTime(qstartTime)) {
				 $.messager.alert('操作失败', '开始时间形如：2013-01-04', 'error');
				   return false;
					}
			}

		if (qendTime != "") {
			if (!strDateTime(qendTime)) {
				$.messager.alert('操作失败', '结束时间形如：2013-01-04', 'error');
				return false;
			}
		}

			return true;
			
		},

		success : function(data) {
			eval("data=" + "'" + data + "'");
		    if ("success" == data) {
			$.messager.show( {
				title : '提示信息',
				timeout : 1000,
				msg : '操作成功，谢谢。'
			});
		
		}
	}
		});
	}

function saveYwgsHbSetings(){
	 var ywCompanyname=trim($('#ywCompanyIdinfo').attr("value")); 
	 var nywCompanyname=trim($('#nywCompanyIdinfo').attr("value"));  
	 if(ywCompanyname == nywCompanyname){
		 $.messager.alert('操作失败', '相同名称的公司不能进行合并', 'error');
		 return ;
		 }
	 $.messager.confirm('','确定要进行公司合并',function(data){if(data){
	 jQuery.post('/tcweb/elevator/sysYwgshbSetings',
	    	 {'companyName':ywCompanyname,'ncompanyName':nywCompanyname},
	    	 function(data){
	    		eval("data="+"'"+data+"'");  
	    		if("success"==data){
	    		 $.messager.show({   
			 title:'提示信息',
			 timeout:1000,
			 msg:'操作成功，谢谢。' 
		 });  	
	    		}
	    		else{
	    			$.messager.alert('操作失败','操作失败','error');
    	    		}
    	       }); 
     }});
} 


function tcchange(){ 
	 $.messager.confirm('','确定要标牌更换',function(data){if(data){
      tcform.form('submit', {  
		url:tcform.url,
		onSubmit:function(){
    	return tcform.form('validate');
	},
	success : function(data) {
		eval("data=" + "'" + data + "'");
		if ("success" == data) {
		$.messager.show( {
			title : '提示信息',
			timeout : 1000,
			msg : '操作成功，谢谢。'
		});
		
	} else {
		$.messager.alert('操作失败', '标牌更换', 'error');
	}
}
	});
	 }});
}

//删除左右两端的空格  
function trim(str)  
{  
     return str.replace(/(^\s*)|(\s*$)/g,"");  
}  
</script>
<style type="text/css">
td{
font-size:12px;
	overflow:hidden;
	padding:0;
	margin:0;
	}
	
 .sousuo input {
  width: 100%;
  height: 25px; 
  padding-left: 15px;
}

fieldset{
height:100%;
border: 1px solid #61B5CF;
margin-top: 16px;
padding: 8px;
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
<body class="easyui-layout" data-options="fit:true" id="setingsLayout">

<!--   <div id="yw" class="easyui-tabs" data-options="fit:true,justified:true" style="text-align:center">  --> 
  <!--   <div title="运维超期" style="padding:20px;margin:0 auto;">  -->
  <div region="north" style="overflow:hidden;height:200px;" border="0"> 
   <fieldset  style="width: 100%;margin: 0 auto;" align="center"><legend style="#c8d9f5;font-weight:bold">运维超期</legend>
        <form method="post" id="ywcqform" name="ywcqform">
        <table class="sousuo" style="margin: 0 auto;">    
           <tr>
            <td width="150" nowrap align="center" style="background: #F5F5F5;border-color: #e6e6e6 #D4D4D4 #D4D4D4 #D4D4D4;">开关</td>  
            <td><select id="ywcqswitch"  class="easyui-combobox" name="ywcqswitch" style="width:220px;height:34px;">
            <option value="0">关闭</option>
            <option value="1">打开</option> 
            </select>
            </td>
            </tr>
            <tr>
            <td  nowrap width="150" nowrap align="center" style="background: #F5F5F5;">开始时间</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" style="width:220px;height:34px;"></input></td>
   </tr>
   <tr>
   <td  nowrap width="150" nowrap align="center" style="background: #F5F5F5;">结束时间</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime" style="width:220px;height:34px;"></input></td>
            </tr>
            <tr>
            </tr>
            <tr>
            </tr>
            <tr>
            </tr>
             <tr style="height:10px"></tr>
            <% if(isliulan == 0){%>
          <tr>
           <td align="center" colspan="4">
      <!--     <a href="javascript:void(0)" onclick="saveSetings()" id="sbtn-save" icon="icon-save">保存</a>  -->  
           <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-save fa-lg" onclick="saveSetings()" style="width:100px">保存</a>  
           </td>
         </tr>
         <% } %>
      </table>
     </form>
     </fieldset>
   </div>
    
   <!--  <div title="维保单位名称合并" style="padding:20px;">  -->
   <div region="center" style="overflow:hidden;height:200px;" border="0"> 
   <fieldset  style="width: 100%;margin:0px"  align="center"><legend style="#c8d9f5;font-weight:bold">维保单位名称合并</legend>
        <form method="post" id="ywgsform" name="ywgsform">
        <table class="sousuo" style="margin: 0 auto;">    
           <tr>
            <td nowrap width="150" nowrap align="center" style="background: #F5F5F5;">原维保单位</td>
   <td> 
  <input id="ywCompanyIdinfo" style="width:220px;height:34px;" placeholder="输入至少两个关键字从下拉列表中选择"></input>
  <input type ="hidden" id="ywCompanyIdinfo2"></input>
            </tr>
            <tr>
            <td nowrap width="150" nowrap align="center" style="background: #F5F5F5;">新维保单位</td>
   <td> 
  <input id="nywCompanyIdinfo" style="width:220px;height:34px;" placeholder="输入至少两个关键字从下拉列表中选择"></input>
  <input type ="hidden" id="nywCompanyIdinfo2"></input></td>
            </tr>
            <tr>
            </tr>
            <tr>
            </tr>
            <tr>
            </tr>
          <tr style="height:10px"></tr>
              <% if(isliulan == 0){%>
          <tr>
           <td align="center" colspan="4">
        <!--     <a href="javascript:void(0)" onclick="saveYwgsHbSetings()" id="ywsbtn-save" icon="icon-save">合并</a> -->
         <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-save fa-lg" onclick="saveYwgsHbSetings()" style="width:100px">保存</a> 
           </td>
         </tr>
          <% } %>
      </table>
     </form>
     </fieldset>
   </div>   
   
  <!--    <div title="标牌更换" style="padding:20px;">  -->
   <div region="south" style="overflow:hidden;height:200px;" border="0"> 
   <fieldset  style="width: 100%;margin:0px"  align="center"><legend style="#c8d9f5;font-weight:bold">标牌更换</legend>
       <form method="post" id="tcchangeform" name="tcchangeform">
        <table class="sousuo" style="margin: 0 auto;">    
           <tr>
            <td nowrap width="150" nowrap align="center" style="background: #F5F5F5;">原标牌编号</td>
   <% if("1".equals(cityName)){ %>         
   <td> 
  N<input  id="registNumber" name="registNumber" style="width:220px;height:34px;border-color: #c0c0c0;background: #ffffff;" class="easyui-validatebox" data-options="required:true,validType:'length[6,6]',invalidMessage:'6位编号'"></input></td>
   <% } else {%>
  <td> 
  <input  id="registNumber" name="registNumber" style="width:220px;height:34px;border-color: #c0c0c0;border-width: 1px;background: #ffffff;" class="easyui-validatebox" data-options="required:true,validType:'length[6,6]',invalidMessage:'6位编号'"></input></td>
 <% }%>
  
            </tr>
            <tr>
            <td nowrap width="150" nowrap align="center" style="background: #F5F5F5;">新标牌编号</td>
             <% if("1".equals(cityName)){ %>    
   <td> 
  N<input  id="nregistNumber" name="nregistNumber" style="width:220px;height:34px;border-color: #c0c0c0;border-width: 1px;background: #ffffff;" class="easyui-validatebox" data-options="required:true,validType:'length[6,6]',invalidMessage:'6位编号'"></input></td>
<% } else {%>
 <td> 
  <input  id="nregistNumber" name="nregistNumber" style="width:220px;height:34px;border-color: #c0c0c0;border-width: 1px;background: #ffffff;" class="easyui-validatebox" data-options="required:true,validType:'length[6,6]',invalidMessage:'6位编号'"></input></td>

 <% }%>
            </tr>
            <tr>
            </tr>
            <tr>
            </tr>
            <tr>
            </tr>
             <tr style="height:10px"></tr>
             <% if(isliulan == 0){%>
          <tr>
           <td align="center" colspan="4">
          <!--   <a href="javascript:void(0)" onclick="tcchange()" id="tcchangebtn-save" icon="icon-save">更换</a>  --> 
           <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-save fa-lg" onclick="tcchange()" style="width:100px">保存</a>
           </td>
         </tr>
           <% } %>
      </table>
    </form>
    </fieldset>
   </div>       
<!--  </div>  -->
</body>
</html>