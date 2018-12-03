<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>成都市电梯安全公共服务平台</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>
<% 
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int  role = 0; 
int isliulan = 0;
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
			if(user != null){
			role = user.getRole();
			}
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

	
	
	form =$("form[name='pcywform']");
	form.url='/tcweb/yw/pcSysYwSetings';	
	$('#sbtn-save').linkbutton();

	

	 var url = encodeURI(encodeURI('/tcweb/elevator/getAutoYwCompanyList5')); 
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
	        	 pcywsofteffInitial(formatted);
	            }
	            else{
	             $('#ywCompanyIdinfo2').attr("value",'');
	 	            }
  	        });

     $('#begintime').datebox({onChange: function (n,o) {
    	 var nd = n;
         var ndaytime =$('#daytime').numberbox('getValue');
         if(!ndaytime){
        	 ndaytime = 0;
             }
         if(n){
          nd = AddDays(nd,ndaytime);
          $('#jztime').html("到期时间:"+nd);
             }
    	 }

    	 });

     $('#daytime').numberbox({onChange: function (n,o) {
    	 var nd=$('#begintime').datebox('getValue');
    	 var ndaytime =n;
         if(!ndaytime){
        	 ndaytime = 0;
             }
         if(nd){
          nd = AddDays(nd,ndaytime);
          $('#jztime').html("到期时间:"+nd);
             }
    	 }

    	 });
     

});

function AddDays(date,days){
	var nd = new Date(date);
	   nd = nd.valueOf();
	   nd = nd + days * 24 * 60 * 60 * 1000;
	   nd = new Date(nd);
	   //alert(nd.getFullYear() + "年" + (nd.getMonth() + 1) + "月" + nd.getDate() + "日");
	var y = nd.getFullYear();
	var m = nd.getMonth()+1;
	var d = nd.getDate();
	if(m <= 9) m = "0"+m;
	if(d <= 9) d = "0"+d; 
	var cdate = y+"-"+m+"-"+d;
	return cdate;
	}

function strDateTime(str)
{
var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
if(r==null)
return false; 
var d= new Date(r[1], r[3]-1, r[4]); 
return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
}

function pcsaveSetings(){
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){  
		var effective=$('#effective').combobox('getValue');
        var begintime=$('#begintime').datebox('getValue');
        var daytime=$('#daytime').numberbox('getValue');

		if (begintime != "") {
			if (!strDateTime(begintime)) {
				 $.messager.alert('操作失败', '开始时间形如：2013-01-04', 'error');
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



function pcywsofteffInitial(companyId){
	 if(companyId  > 0){
	 form.form('load', '/tcweb/yw/pcywsofteffInitial/'+companyId);
	 form.url = '/tcweb/yw/pcywsoftupdate/'; 
	 }
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
</style>
</head>
<body class="easyui-layout" >
 <div id="yw" class="easyui-tabs" style="width:700px;height:250px;">   
    <div title="PC软件开关" style="padding:20px;">  
        <form method="post" id="pcywform" name="pcywform">
        <table>
         <tr>
            <td nowrap>维保单位：</td>
   <td> 
  <input id="ywCompanyIdinfo" style="background-color:#87CEEB;width:152px;"></input>
  <input type ="hidden" id="ywCompanyIdinfo2" name="ywCompanyIdinfo2"></input></td>
  <td nowrap colspan="2"><span id="jztime"></span></td>
            </tr>    
           <tr>
            <td n="right">版本：</td>  
            <td><select id="effective"  class="easyui-combobox" name="effective" style="width:152px;">
            <option value="0">试用版</option>
            <option value="1">正式版</option> 
            </select>
            </td>
            </tr>
            <tr>
            <td  nowrap>开始时间：</td>
   <td><input id="begintime"  type="text" class="easyui-datebox" name="begintime" style="width:152px;"></input></td></tr>
   <tr>
   <td align="left" nowrap>使用天数：</td>
   <td><input id="daytime"  type="text" class="easyui-numberbox" value="60" data-options="min:0,precision:0" name="daytime" style="width:156px;"></input></td>
   </tr>
            <tr>
            </tr>
            <tr>
            </tr>
            <tr>
            </tr>
               <% if(isliulan == 0){%>
          <tr>
           <td align="center" colspan="4">
            <a href="javascript:void(0)" onclick="pcsaveSetings()" id="sbtn-save" icon="icon-save">保存</a>  
           </td>
         </tr>
             <% } %>
      </table>
     </form>
   </div>
    
    
</div>  
</body>
</html>