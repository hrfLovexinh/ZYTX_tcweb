<%@ page import="com.zytx.models.UserInfo,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jsrender.js"></script>
<% 
String cityName = GlobalFunction.cityName;
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int  role = 0; 
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
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

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
	 
    
	form =$("form[name='myform']");
	form.url='/tcweb/elevator/ywSysSetingsqzj';

	$('#sbtn-save').linkbutton();
	$('#bzsbtn-save').linkbutton();

//	$('#myform').form({onLoadSuccess:sjhjtotal});  
    $('#myform').form({onLoadSuccess:bzsjhjtotal});
    

	$('#hiddentr').hide();
	
	  $("#ratingDate2").val(myformatter(new Date(), "yyyy-MM"));  //设置查询条件的默认时间
	  
	  $('#inspectElevatorTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
			var inspectElevatorjc =$('#inspectElevatorjc').attr("value");
		    $('#inspectElevatorsj').attr("value",inspectElevatorjc-5*newValue-20*$('#inspectElevatorTimes2').numberspinner('getValue'));
		    sjhjtotal();
		    }
	    });  

	  $('#inspectElevatorTimes2').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		      
			var inspectElevatorjc =$('#inspectElevatorjc').attr("value");
		    $('#inspectElevatorsj').attr("value",inspectElevatorjc-20*newValue-5*$('#inspectElevatorTimes').numberspinner('getValue'));
		    sjhjtotal();
		    }
	    });  
	      
	  
	  $('#acceptInspElevatorTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
	
			var acceptInspElevatorjc =$('#acceptInspElevatorjc').attr("value");
		    $('#acceptInspElevatorsj').attr("value",acceptInspElevatorjc-10*newValue);
		    sjhjtotal();
		    }
	    });  

	  $('#maintenSceneInfoTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
	   
			var maintenSceneInfojc =$('#maintenSceneInfojc').attr("value");
		    $('#maintenSceneInfosj').attr("value",maintenSceneInfojc-10*newValue);
		    sjhjtotal();
		    }
	    });  

	  
	  $('#complaintsEventsTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		   
			var complaintsEventsjc =$('#complaintsEventsjc').attr("value");
		    $('#complaintsEventssj').attr("value",complaintsEventsjc-5*newValue-20*$('#complaintsEventsTimes2').numberspinner('getValue'));
		    sjhjtotal();
		    }
	    }); 

	  $('#complaintsEventsTimes2').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		   
			var complaintsEventsjc =$('#complaintsEventsjc').attr("value");
		    $('#complaintsEventssj').attr("value",complaintsEventsjc-20*newValue-5*$('#complaintsEventsTimes').numberspinner('getValue'));
		    sjhjtotal();
		    }
	    });

	  
	  $('#maintenBusinessTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		  
			var maintenBusinessjc =$('#maintenBusinessjc').attr("value");
		    $('#maintenBusinesssj').attr("value",maintenBusinessjc-20*newValue);
		    sjhjtotal();
		    }
	    });    

	  
	  $('#honestTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
	
			var honestjc =$('#honestjc').attr("value");
		    $('#honestsj').attr("value",honestjc-20*newValue);
		    sjhjtotal();
	
		    }
	    });  

	  $('#punishmentTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		     
			var punishmentjc =$('#punishmentjc').attr("value");
		    $('#punishmentsj').attr("value",punishmentjc-2*newValue-5*$('#punishmentTimes2').numberspinner('getValue')-20*$('#punishmentTimes3').numberspinner('getValue')-20*$('#punishmentTimes4').numberspinner('getValue'));
		    sjhjtotal();
		    }
	    }); 

	  $('#punishmentTimes2').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		      
			var punishmentjc =$('#punishmentjc').attr("value");
		    $('#punishmentsj').attr("value",punishmentjc-5*newValue-2*$('#punishmentTimes').numberspinner('getValue')-20*$('#punishmentTimes3').numberspinner('getValue')-20*$('#punishmentTimes4').numberspinner('getValue'));
		    sjhjtotal();
		    }
	    });

	  $('#punishmentTimes3').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
	   
			var punishmentjc =$('#punishmentjc').attr("value");
		    $('#punishmentsj').attr("value",punishmentjc-20*newValue-2*$('#punishmentTimes').numberspinner('getValue')-5*$('#punishmentTimes2').numberspinner('getValue')-20*$('#punishmentTimes4').numberspinner('getValue'));
		    sjhjtotal();
		    }
	    });  

	  $('#punishmentTimes4').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
			var punishmentjc =$('#punishmentjc').attr("value");
		    $('#punishmentsj').attr("value",punishmentjc-20*newValue-2*$('#punishmentTimes').numberspinner('getValue')-5*$('#punishmentTimes2').numberspinner('getValue')-20*$('#punishmentTimes3').numberspinner('getValue'));
		    sjhjtotal();
		    }
	    });               

	  $('#ywCompanyIdinfo').combobox({   
	    	//	    url:'/tcweb/elevator/getYwQuaRateCompanyList?ratingDate='+$('#ratingDate2').datebox("getValue"), 
	    	       url:'/tcweb/elevator/getYwQuaRateCompanyList?ratingDate='+$('#ratingDate2').attr("value"),   
	    		    valueField:'id',   
	    		    textField:'companyName',
	    		    filter: function(q, row){
	    			     var opts = $(this).combobox('options');
	    			    return row[opts.textField].indexOf(q) >= 0;
	    		     },
	    		     formatter: function(row){
	    		 		var opts = $(this).combobox('options');
	    		 		if(row[opts.textField].indexOf("已考评") > -1){
	    		 			return '<font color="red">'+row[opts.textField]+'</font>'; 
	    		 		}
	    		 		else{
	    			 		return row[opts.textField];
	    		 		}
	    		     },
	    		     onSelect: function(rec){   
	    		    	 $('#ywCompanyIdinfo2').attr("value",rec.id);
	    		//    	 query();  
	    		        }	     
	    			      
	    		});   
	    
	 
   //合计基础总分
	hjtotal();
	//初始化实际得分
	initial();

	 var rows = document.getElementsByTagName('tr');//取得行
	    for(var i=0 ;i<rows.length; i++)
	    {
	        rows[i].onmouseover = function(){//鼠标移上去,添加一个类'hilite'
	            this.className += 'hilite';
	        };
	        rows[i].onmouseout = function(){//鼠标移开,改变该类的名称
	            this.className = this.className.replace('hilite','');
	        };
	    }

	    //限制不能输入单引号
	    $("#beizhutext").keyup(function(){ 
	          $(this).val($(this).val().replace(/\'/g,'')); 
	          }).bind("paste",function(){
	            $(this).val($(this).val().replace(/\'/g,'')); 
	          });

	   
	    
  });

function ywcompanySelectRefresh(){  
	$('#ywCompanyIdinfo').combobox('clear');  
	$('#ywCompanyIdinfo').combobox('reload','/tcweb/elevator/getYwQuaRateCompanyList?ratingDate='+$('#ratingDate2').attr("value"));
	
}

//格式化日期
function myformatter(date) {
    //获取年份
    var y = date.getFullYear();
    //获取月份
    var m = date.getMonth() + 1;
    if(m<10)
        return y + '-0' + m;
    else
        return y + '-' + m;
}
function strDateTime(str)
{
var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
if(r==null)
return false; 
var d= new Date(r[1], r[3]-1, r[4]); 
return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
}




//删除左右两端的空格  
function trim(str)  
{  
     return str.replace(/(^\s*)|(\s*$)/g,"");  
} 


function inspectElevatorValue(){
	var inspectElevator=$('#inspectElevator option:selected').val();
	var inspectElevatorjc =$('#inspectElevatorjc').attr("value");
	if(inspectElevator == 1 ){
		$('#inspectElevatorTimes').numberspinner('enable');
		$('#inspectElevatorTimes2').numberspinner('enable');
		$('#inspectElevatorsj').attr("value",inspectElevatorjc-5*$('#inspectElevatorTimes').numberspinner('getValue')-20*$('#inspectElevatorTimes2').numberspinner('getValue'));
		}
	else{
		$('#inspectElevatorTimes').numberspinner('setValue',0);  
		$('#inspectElevatorTimes2').numberspinner('setValue',0);    
		$('#inspectElevatorTimes').numberspinner('disable');
		$('#inspectElevatorTimes2').numberspinner('disable');
		$('#inspectElevatorsj').attr("value",inspectElevatorjc);	
		}
}


function acceptInspElevatorValue(){
	var acceptInspElevator=$('#acceptInspElevator option:selected').val();
	var acceptInspElevatorjc =$('#acceptInspElevatorjc').attr("value");
	if(acceptInspElevator == 1 ){
		$('#acceptInspElevatorTimes').numberspinner('enable');
		$('#acceptInspElevatorsj').attr("value",acceptInspElevatorjc-10*$('#acceptInspElevatorTimes').numberspinner('getValue'));
		}
	else{
		$('#acceptInspElevatorTimes').numberspinner('setValue',0);    
		$('#acceptInspElevatorTimes').numberspinner('disable');
		$('#acceptInspElevatorsj').attr("value",acceptInspElevatorjc);	
		}
}


function maintenSceneInfoValue(){
	var maintenSceneInfo=$('#maintenSceneInfo option:selected').val();
	var maintenSceneInfojc =$('#maintenSceneInfojc').attr("value");
	if(maintenSceneInfo == 1 ){
		$('#maintenSceneInfoTimes').numberspinner('enable');
		$('#maintenSceneInfosj').attr("value",maintenSceneInfojc-10*$('#maintenSceneInfoTimes').numberspinner('getValue'));
		}
	else{
		$('#maintenSceneInfoTimes').numberspinner('setValue',0);    
		$('#maintenSceneInfoTimes').numberspinner('disable');
		$('#maintenSceneInfosj').attr("value",maintenSceneInfojc);	
		}
}


function complaintsEventsValue(){
	var complaintsEvents=$('#complaintsEvents option:selected').val();
	var complaintsEventsjc =$('#complaintsEventsjc').attr("value");
	if(complaintsEvents == 1 ){
		$('#complaintsEventsTimes').numberspinner('enable');
		$('#complaintsEventsTimes2').numberspinner('enable');
		$('#complaintsEventssj').attr("value",complaintsEventsjc-5*$('#complaintsEventsTimes').numberspinner('getValue')-20*$('#complaintsEventsTimes2').numberspinner('getValue'));
		}
	else{
		$('#complaintsEventsTimes').numberspinner('setValue',0);  
		$('#complaintsEventsTimes2').numberspinner('setValue',0);    
		$('#complaintsEventsTimes').numberspinner('disable');
		$('#complaintsEventsTimes2').numberspinner('disable');
		$('#complaintsEventssj').attr("value",complaintsEventsjc);	
		}
}


function maintenBusinessValue(){
	var maintenBusiness=$('#maintenBusiness option:selected').val();
	var maintenBusinessjc =$('#maintenBusinessjc').attr("value");
	if(maintenBusiness == 1 ){
		$('#maintenBusinessTimes').numberspinner('enable');
		$('#maintenBusinesssj').attr("value",maintenBusinessjc-20*$('#maintenBusinessTimes').numberspinner('getValue'));
		}
	else{
		$('#maintenBusinessTimes').numberspinner('setValue',0);   
		$('#maintenBusinessTimes').numberspinner('disable');
		$('#maintenBusinesssj').attr("value",maintenBusinessjc);	
		}
}


function honestValue(){
	var honest=$('#honest option:selected').val();
	var honestjc =$('#honestjc').attr("value");
	if(honest == 1 ){
		$('#honestTimes').numberspinner('enable');
		$('#honestsj').attr("value",honestjc-20*$('#honestTimes').numberspinner('getValue'));
		}
	else{
		$('#honestTimes').numberspinner('setValue',0);   
		$('#honestTimes').numberspinner('disable');
		$('#honestsj').attr("value",honestjc);	
		}
}


function punishmentValue(){
	var punishment=$('#punishment option:selected').val();
	var punishmentjc =$('#punishmentjc').attr("value");
	if(punishment == 1 ){
		$('#punishmentTimes').numberspinner('enable');
		$('#punishmentTimes2').numberspinner('enable');
		$('#punishmentTimes3').numberspinner('enable');
		$('#punishmentTimes4').numberspinner('enable');
		$('#punishmentsj').attr("value",punishmentjc-2*$('#punishmentTimes').numberspinner('getValue')-5*$('#punishmentTimes2').numberspinner('getValue')-20*$('#punishmentTimes3').numberspinner('getValue')-20*$('#punishmentTimes4').numberspinner('getValue'));
		}
	else{
		$('#punishmentTimes').numberspinner('setValue',0);  
		$('#punishmentTimes2').numberspinner('setValue',0);  
		$('#punishmentTimes3').numberspinner('setValue',0); 
		$('#punishmentTimes4').numberspinner('setValue',0);
		$('#punishmentTimes').numberspinner('disable'); 
		$('#punishmentTimes2').numberspinner('disable');  
		$('#punishmentTimes3').numberspinner('disable');
		$('#punishmentTimes4').numberspinner('disable');
		$('#punishmentsj').attr("value",punishmentjc);	
		}
}




//初始化实际总分
function initial(){
	
	var inspectElevatorTimes=$('#inspectElevatorTimes').numberspinner('getValue');
	var inspectElevatorTimes2=$('#inspectElevatorTimes').numberspinner('getValue');
	var inspectElevatorsj =$('#inspectElevatorjc').attr("value")-5*inspectElevatorTimes-20*inspectElevatorTimes2;
	$('#inspectElevatorsj').attr("value",inspectElevatorsj);


	var acceptInspElevatorTimes=$('#acceptInspElevatorTimes').numberspinner('getValue');
	var acceptInspElevatorsj =$('#acceptInspElevatorjc').attr("value")-10*acceptInspElevatorTimes;
	$('#acceptInspElevatorsj').attr("value",acceptInspElevatorsj);


	var maintenSceneInfoTimes=$('#maintenSceneInfoTimes').numberspinner('getValue');
	var maintenSceneInfosj =$('#maintenSceneInfojc').attr("value")-10*maintenSceneInfoTimes;
	$('#maintenSceneInfosj').attr("value",maintenSceneInfosj);


	var complaintsEventsTimes =$('#complaintsEventsTimes').numberspinner('getValue');
	var complaintsEventsTimes2 =$('#complaintsEventsTimes2').numberspinner('getValue');
	var complaintsEventssj =$('#complaintsEventsjc').attr("value")-5*complaintsEventsTimes-20*complaintsEventsTimes2;
	$('#complaintsEventssj').attr("value",complaintsEventssj);

	var maintenBusinessTimes =$('#maintenBusinessTimes').numberspinner('getValue');
	var maintenBusinesssj =$('#maintenBusinessjc').attr("value")-20*maintenBusinessTimes;
	$('#maintenBusinesssj').attr("value",maintenBusinesssj);

	var honestTimes =$('#honestTimes').numberspinner('getValue');
	var honestsj =$('#honestjc').attr("value")-20*honestTimes;
	$('#honestsj').attr("value",honestsj);

	
	var punishmentTimes =$('#punishmentTimes').numberspinner('getValue');
	var punishmentTimes2 =$('#punishmentTimes2').numberspinner('getValue');
	var punishmentTimes3 =$('#punishmentTimes3').numberspinner('getValue');
	var punishmentTimes4 =$('#punishmentTimes4').numberspinner('getValue');
	var punishmentsj =$('#punishmentjc').attr("value")-2*punishmentTimes-5*punishmentTimes2-20*punishmentTimes3-20*punishmentTimes4;
	$('#punishmentsj').attr("value",punishmentsj);


	var sjhjtotal = 0;
	sjhjtotal = parseInt(inspectElevatorsj)+parseInt(acceptInspElevatorsj)+parseInt(maintenSceneInfosj)+parseInt(complaintsEventssj)+parseInt(maintenBusinesssj)+parseInt(honestsj)+parseInt(punishmentsj);
	
	$('#sjhjtotal').attr("value",sjhjtotal);
	$('#sjhjtotal').hide();
	sjhjjjtotal();   //合计实际加减分
}

//合计基础总分
function hjtotal(){
	
	var inspectElevatorjc =$('#inspectElevatorjc').attr("value");
	var acceptInspElevatorjc =$('#acceptInspElevatorjc').attr("value");
	var maintenSceneInfojc =$('#maintenSceneInfojc').attr("value");
	var complaintsEventsjc =$('#complaintsEventsjc').attr("value");
	var maintenBusinessjc =$('#maintenBusinessjc').attr("value");
	var honestjc =$('#honestjc').attr("value");
	var punishmentjc =$('#punishmentjc').attr("value");

	var hjtotal = 0;
	
	hjtotal = parseInt(inspectElevatorjc)+parseInt(acceptInspElevatorjc)+parseInt(maintenSceneInfojc)+parseInt(complaintsEventsjc)+parseInt(maintenBusinessjc)+parseInt(honestjc)+parseInt(punishmentjc);

	$('#hjtotal').attr("value",hjtotal);
	$('#hjtotal').hide();
}


function bzsjhjtotal(){
	
     initialBzCount();    //初始画备注的记录条数
	 sjhjtotal();   //合计实际总分
}


function initialBzCount(){
	var vinspectElevatorTimes = $('#inspectElevatorTimes').numberspinner('getValue');
	var vinspectElevatorTimes2 = $('#inspectElevatorTimes2').numberspinner('getValue');
	var inspectElevatorTimesv = (parseInt(vinspectElevatorTimes)+parseInt(vinspectElevatorTimes2));

	var acceptInspElevatorTimesv = $('#acceptInspElevatorTimes').numberspinner('getValue');

	var maintenSceneInfoTimesv = $('#maintenSceneInfoTimes').numberspinner('getValue');

	 var vcomplaintsEventsTimes = $('#complaintsEventsTimes').numberspinner('getValue');
	 var vcomplaintsEventsTimes2 = $('#complaintsEventsTimes2').numberspinner('getValue');
	 var complaintsEventsTimesv = (parseInt(vcomplaintsEventsTimes)+parseInt(vcomplaintsEventsTimes2));

	 var maintenBusinessTimesv = $('#maintenBusinessTimes').numberspinner('getValue');
	 
	 var honestTimesv = $('#honestTimes').numberspinner('getValue');

	 var vpunishmentTimes = $('#punishmentTimes').numberspinner('getValue');
	 var vpunishmentTimes2 = $('#punishmentTimes2').numberspinner('getValue');
	 var vpunishmentTimes3 = $('#punishmentTimes3').numberspinner('getValue');
	 var vpunishmentTimes4 = $('#punishmentTimes4').numberspinner('getValue');
	 var punishmentTimesv = (parseInt(vpunishmentTimes)+parseInt(vpunishmentTimes2)+parseInt(vpunishmentTimes3)+parseInt(vpunishmentTimes4));
	 
	 inspectElevatorBzCount =inspectElevatorTimesv;
	 acceptInspElevatorBzCount =acceptInspElevatorTimesv;
	 maintenSceneInfoBzCount =maintenSceneInfoTimesv;
	 complaintsEventsBzCount =complaintsEventsTimesv;
	 maintenBusinessBzCount =maintenBusinessTimesv;
	 honestBzCount =honestTimesv;
	 punishmentBzCount =punishmentTimesv;

	 inspectElevatorTimesBzCount = vinspectElevatorTimes;
	 inspectElevatorTimes2BzCount = vinspectElevatorTimes2;

	 complaintsEventsTimesBzCount = vcomplaintsEventsTimes;
	 complaintsEventsTimes2BzCount = vcomplaintsEventsTimes2;

	 punishmentTimesBzCount = vpunishmentTimes;
	 punishmentTimes2BzCount = vpunishmentTimes2;
	 punishmentTimes3BzCount = vpunishmentTimes3;
	 punishmentTimes4BzCount = vpunishmentTimes4;
	
}


//合计实际总分
function sjhjtotal(){ 
	
	var inspectElevatorsj =$('#inspectElevatorsj').attr("value");
	var acceptInspElevatorsj =$('#acceptInspElevatorsj').attr("value");
	var maintenSceneInfosj =$('#maintenSceneInfosj').attr("value");
	var complaintsEventssj =$('#complaintsEventssj').attr("value");
	var maintenBusinesssj =$('#maintenBusinesssj').attr("value");
	var honestsj =$('#honestsj').attr("value");
	var punishmentsj =$('#punishmentsj').attr("value");
	
	var sjhjtotal = 0;
	
	sjhjtotal =parseInt(inspectElevatorsj)+parseInt(acceptInspElevatorsj)+parseInt(maintenSceneInfosj)+parseInt(complaintsEventssj)+parseInt(maintenBusinesssj)+parseInt(honestsj)+parseInt(punishmentsj);
    
	$('#sjhjtotal').attr("value",sjhjtotal);
	sjhjjjtotal();
    //备注图标
	bzImg();
}

//合计实际加减分
function sjhjjjtotal(){
	var dqkpdw =$('#ywCompanyIdinfo').combobox('getText'); 
	$('#dqkpdw').text(dqkpdw);
	var sjhjjjtotal =  $('#sjhjtotal').attr("value") - $('#hjtotal').attr("value");  
	$('#sjhjjjtotal').attr("value",sjhjjjtotal);
}

//保存的时候验证考评的扣分次数和备注次数是否一致
function saveStringsValidateBz(){
	var vinspectElevatorTimes = $('#inspectElevatorTimes').numberspinner('getValue');
	var vinspectElevatorTimes2 = $('#inspectElevatorTimes2').numberspinner('getValue');
	var inspectElevatorTimesv = (parseInt(vinspectElevatorTimes)+parseInt(vinspectElevatorTimes2));

	var acceptInspElevatorTimesv = $('#acceptInspElevatorTimes').numberspinner('getValue');

	var maintenSceneInfoTimesv = $('#maintenSceneInfoTimes').numberspinner('getValue');

	 var vcomplaintsEventsTimes = $('#complaintsEventsTimes').numberspinner('getValue');
	 var vcomplaintsEventsTimes2 = $('#complaintsEventsTimes2').numberspinner('getValue');
	 var complaintsEventsTimesv = (parseInt(vcomplaintsEventsTimes)+parseInt(vcomplaintsEventsTimes2));

	 var maintenBusinessTimesv = $('#maintenBusinessTimes').numberspinner('getValue');
	 
	 var honestTimesv = $('#honestTimes').numberspinner('getValue');

	 var vpunishmentTimes = $('#punishmentTimes').numberspinner('getValue');
	 var vpunishmentTimes2 = $('#punishmentTimes2').numberspinner('getValue');
	 var vpunishmentTimes3 = $('#punishmentTimes3').numberspinner('getValue');
	 var vpunishmentTimes4 = $('#punishmentTimes4').numberspinner('getValue');
	 var punishmentTimesv = (parseInt(vpunishmentTimes)+parseInt(vpunishmentTimes2)+parseInt(vpunishmentTimes3)+parseInt(vpunishmentTimes4));

	if(inspectElevatorBzCount != inspectElevatorTimesv){
		$.messager.alert('操作失败', '在用电梯监督抽查情况备注记录数与考评不一致，请修改', 'error');
		return 0;
		}

	if(inspectElevatorTimesBzCount != parseInt(vinspectElevatorTimes)){
		$.messager.alert('操作失败', '在用电梯监督抽查情况备注严重隐患记录数与考评不一致，请修改', 'error');
		return 0;
		}

	if(inspectElevatorTimes2BzCount != parseInt(vinspectElevatorTimes2)){
		$.messager.alert('操作失败', '在用电梯监督抽查情况备注回路短接记录数与考评不一致，请修改', 'error');
		return 0;
		}

	if(acceptInspElevatorBzCount != acceptInspElevatorTimesv){
		$.messager.alert('操作失败', '接受监督检查情况备注记录数与考评不一致，请修改', 'error');
		return 0;
		}

	if(maintenSceneInfoBzCount != maintenSceneInfoTimesv){
		$.messager.alert('操作失败', '维保现场防护情况备注记录数与考评不一致，请修改', 'error');
		return 0;
		}

	if(complaintsEventsBzCount != complaintsEventsTimesv){
		$.messager.alert('操作失败', '因维保质量而引发的投诉备注记录数与考评不一致，请修改', 'error');
		return 0;
		}

	  if(complaintsEventsTimesBzCount != parseInt(vcomplaintsEventsTimes)){
			$.messager.alert('操作失败', '因维保质量而引发的投诉备注一般投诉 记录数与考评次数不一致，请修改', 'error');
			return 0;
			}

	  if(complaintsEventsTimes2BzCount != parseInt(vcomplaintsEventsTimes2)){
			$.messager.alert('操作失败', '因维保质量而引发的投诉备注连续投诉 记录数与考评次数不一致，请修改', 'error');
			return 0;
			}

	if(maintenBusinessBzCount  != maintenBusinessTimesv){
		$.messager.alert('操作失败', '维保业务管理备注记录数与考评不一致，请修改', 'error');
		return 0;
		}

	if(honestBzCount != honestTimesv){
		$.messager.alert('操作失败', '诚实守信情况备注记录数与考评不一致，请修改', 'error');
		return 0;
		}

	if(punishmentBzCount != punishmentTimesv){
		$.messager.alert('操作失败', '行政查处及处罚情况备注记录数与考评不一致，请修改', 'error');
		return 0;
		}
	if(punishmentTimesBzCount != parseInt(vpunishmentTimes)){
		$.messager.alert('操作失败', '行政查处及处罚情况备注维保管理及维保质量被整改记录数与考评不一致，请修改', 'error');
		return 0;
		}

	if(punishmentTimes2BzCount != parseInt(vpunishmentTimes2)){
		$.messager.alert('操作失败', '行政查处及处罚情况备注被下达监察意见过通报 记录数与考评不一致，请修改', 'error');
		return 0;
		}

	if(punishmentTimes3BzCount != parseInt(vpunishmentTimes3)){
		$.messager.alert('操作失败', '行政查处及处罚情况备注连续被下达监察指令或通报2次及以上 记录数与考评不一致，请修改', 'error');
		return 0;
		}

	if(punishmentTimes4BzCount != parseInt(vpunishmentTimes4)){
		$.messager.alert('操作失败', '行政查处及处罚情况备注违反法律、法规及技术规范 记录数与考评不一致，请修改', 'error');
		return 0;
		}

	return 1;
}



//保存
function saveSetings(){
	var dqkpdw =$('#dqkpdw').text();
    var sdqkpdw =$('#ywCompanyIdinfo').combobox('getText');

    if(dqkpdw != sdqkpdw){
   	 $.messager.alert('操作失败', '请先查询当前维保公司后再考评保存！', 'error');
   	 return;
        }
	
	 $.messager.confirm('','确定要保存',function(data){if(data){

	
	
	var ywCompanyId=$('#ywCompanyIdinfo2').attr("value");  
	if(ywCompanyId == 0)
		ywCompanyId = 0;  
//	var ratingDate2 =$('#ratingDate2').datebox("getValue");
    var ratingDate2 =$('#ratingDate2').attr("value");
   
	$('#ywCompanyID').attr("value",ywCompanyId);  
	$('#ratingDate').attr("value",ratingDate2); 

	if(ywCompanyId == "" || ywCompanyId == 0){
		$.messager.alert('操作失败', '请从维保单位栏中选择维保单位', 'error');
		$('#ywCompanyIdinfo').focus();
		return;
	}

	if(ratingDate2 == ""){
		$.messager.alert('操作失败', '请选择年月', 'error');
		$('#ratingDate2').focus();
		return;
		
	}

	if(saveStringsValidateBz()==0){
         return;
		}
	 
//	$('#myform').form.url='/tcweb/elevator/ywSysSetings?ywCompanyID='+ywCompanyId+'&ratingDate='+ratingDate; 
    $('#myform').form.url='/tcweb/elevator/ywSysSetingsqzj?ywCompanyID='+ywCompanyId+'&ratingDate='+ratingDate2;
	$('#myform').form('submit', {  
		url:form.url,
		onSubmit:function(){  
			return true;
		},

		success : function(data) {
			eval("data=" + "'" + data + "'");
		    if ("success" == data) {
		/*	$.messager.show( {
				title : '提示信息',
				timeout : 1000,
				msg : '操作成功，谢谢。'
			}); */
			$.messager.alert('操作成功', '保存成功！', 'info');
			$('#ywCompanyIdinfo').combobox('clear');  
			$('#ywCompanyIdinfo').combobox('reload'); 
			$('#myform').form('load', '/tcweb/elevator/ywsyssetings2ByComIdRatDate/?ywCompanyID=0'+'&ratingDate='+ratingDate);
		    }
		    else if("failure2" == data){
		    	$.messager.alert('操作失败', '只能保存本月评分！', 'error');
			    }
		    else{
		    	$.messager.alert('操作失败', '保存维保信用评分', 'error');
			    }
	}
		});
	 }});
	}



//查询
function query(){
     var ywCompanyID =$('#ywCompanyIdinfo2').attr("value");
     if(ywCompanyID == '')
    	 ywCompanyID =0;

     if(ywCompanyID ==0){
			$.messager.alert('操作失败','请从维保单位下拉列表中选择维保单位','info'); 
			return;
	 }
   //  var ratingDate = $('#ratingDate2').datebox("getValue");  
      var ratingDate = $('#ratingDate2').attr("value"); 
     $('#ratingDate').attr("value",ratingDate);
	$('#myform').form('load', '/tcweb/elevator/ywsyssetings2ByComIdRatDate/?ywCompanyID='+ywCompanyID+'&ratingDate='+ratingDate);
	
}



var beizhuid ="";
function addBeiZhu(id){ 
    beizhuid = id;
    $('#beiZhuWin').window('open');  

   
	if(beizhuid=="inspectElevatorButton"){
		 $("#beiZhuWin").width(1000).height(400);   //恢复备注窗口的初始画大小，免得resize时候根据备注窗口计算出来的偏移量是变化的
   	     var dtop = ($(window).height() - $('#beiZhuWin').height())/2;  
         var dleft = ($(window).width() - $('#beiZhuWin').width())/2;    
         var scrollTop = $(document).scrollTop();      
         var scrollLeft = $(document).scrollLeft(); 
         dtop = dtop+ scrollTop;                      
         dleft =dleft + scrollLeft; 
		 $('#beiZhuWin').window('resize',{
	         width: 1000,
	         height: 400,
	         top:dtop,   
	         left:dleft
	      });  
		var data;
	    if($('#inspectElevatorbz').attr("value")==""){
	    	data={'list': [
	   	    	   	    {
	    			'iReg': '',
	 	            'iTime': '',
	 	            'iAdd': '',
	 	            'iRea': '',
	 	            'iLev': ''  
		           }]};
			   }
		  else{ 
		     data = JSON.parse($('#inspectElevatorbz').attr("value")); 
		   }       
	
	        //获取模板
	        jsRenderTpl = $.templates('#inspectElevatorTemp'),
	        //末班与数据结合
	        finalTpl = jsRenderTpl(data);

	    $('#beizhuDivnr').html(finalTpl);
	    $.each(data,function(i){  
             $.each(data[i],function(i,v){
                  var selectV = v.iLev;   
             //     var trobj =$('#myTable tr:eq(1+i)').getElementsByTagName('select');
             //     trobj.getElementsByTagName('select')[0].value=selectV; 
               $("select[name='iLev']").eq(i).val(selectV);
                  
	          });  
	        }
         );   
		
	//	$('#beizhutext').attr("value",$('#inspectElevatorbz').attr("value"));
	}
	if(beizhuid=="acceptInspElevatorButton"){
	//	$('#beizhutext').attr("value",$('#acceptInspElevatorbz').attr("value"));
	    $("#beiZhuWin").width(600).height(400);   //恢复备注窗口的初始画大小，免得resize时候根据备注窗口计算出来的偏移量是变化的
   	     var dtop = ($(window).height() - $('#beiZhuWin').height())/2;  
         var dleft = ($(window).width() - $('#beiZhuWin').width())/2;    
         var scrollTop = $(document).scrollTop();      
         var scrollLeft = $(document).scrollLeft(); 
         dtop = dtop+ scrollTop;                      
         dleft =dleft + scrollLeft; 
	     $('#beiZhuWin').window('resize',{
	         width: 600,
	         height: 400,
	         top:dtop,   
	         left:dleft
	      });
		var data;
	    if($('#acceptInspElevatorbz').attr("value")==""){
	    	data={'list': [
	   	    	   	    {
	    			'at': '',
	 	            'ade': '',
	 	            'ar': ''  
		           }]};
			   }
		  else{ 
		     data = JSON.parse($('#acceptInspElevatorbz').attr("value")); 
		   }       
	
	        //获取模板
	        jsRenderTpl = $.templates('#acceptInspElevatorTemp'),
	        //末班与数据结合
	        finalTpl = jsRenderTpl(data);

	    $('#beizhuDivnr').html(finalTpl);
	    
	}
	if(beizhuid=="maintenSceneInfoButton"){
		 $("#beiZhuWin").width(600).height(400);   //恢复备注窗口的初始画大小，免得resize时候根据备注窗口计算出来的偏移量是变化的
   	     var dtop = ($(window).height() - $('#beiZhuWin').height())/2;  
         var dleft = ($(window).width() - $('#beiZhuWin').width())/2;    
         var scrollTop = $(document).scrollTop();      
         var scrollLeft = $(document).scrollLeft(); 
         dtop = dtop+ scrollTop;                      
         dleft =dleft + scrollLeft; 
		 $('#beiZhuWin').window('resize',{
	         width: 600,
	         height: 400,
	         top:dtop,   
	         left:dleft
	      });
		var data;
	    if($('#maintenSceneInfobz').attr("value")==""){
	    	data={'list': [
	   	    	   	    {
	    			'mst': '',
	 	            'msde': '',
	 	            'msr': ''  
		           }]};
			   }
		  else{ 
		     data = JSON.parse($('#maintenSceneInfobz').attr("value")); 
		   }       
	
	        //获取模板
	        jsRenderTpl = $.templates('#maintenSceneInfoTemp'),
	        //末班与数据结合
	        finalTpl = jsRenderTpl(data);

	    $('#beizhuDivnr').html(finalTpl);
		
	//	$('#beizhutext').attr("value",$('#maintenSceneInfobz').attr("value"));
		}
	if(beizhuid=="complaintsEventsButton"){
		$("#beiZhuWin").width(1000).height(400);   //恢复备注窗口的初始画大小，免得resize时候根据备注窗口计算出来的偏移量是变化的
  	    var dtop = ($(window).height() - $('#beiZhuWin').height())/2;  
        var dleft = ($(window).width() - $('#beiZhuWin').width())/2;    
        var scrollTop = $(document).scrollTop();      
        var scrollLeft = $(document).scrollLeft(); 
        dtop = dtop+ scrollTop;                      
        dleft =dleft + scrollLeft;
	     $('#beiZhuWin').window('resize',{
	         width: 1000,
	         height: 400,
	         top:dtop,   
	         left:dleft
	      });
			var data;
		    if($('#complaintsEventsbz').attr("value")==""){
		    	data={'list': [
		    	               {
		    			'crg': '',
		 	            'ct': '',
		 	            'cde': '',
		 	            'cr': '',
		 	            'cl': ''
			 	            }]};
			   }
			  else{ 
			     data = JSON.parse($('#complaintsEventsbz').attr("value")); 
			   }       
			
		        //获取模板
		        jsRenderTpl = $.templates('#complaintsEventsTemp'),
		        //末班与数据结合
		        finalTpl = jsRenderTpl(data);

		    $('#beizhuDivnr').html(finalTpl);

		    $.each(data,function(i){  
	            $.each(data[i],function(i,v){
	                 var selectV = v.cl;   
	              $("select[name='cl']").eq(i).val(selectV);
	                 
		          });  
		        }
	        ); 
		
	//	$('#beizhutext').attr("value",$('#complaintsEventsbz').attr("value"));
		}
	if(beizhuid=="maintenBusinessButton"){
		 $("#beiZhuWin").width(1000).height(400);   //恢复备注窗口的初始画大小，免得resize时候根据备注窗口计算出来的偏移量是变化的
   	     var dtop = ($(window).height() - $('#beiZhuWin').height())/2;  
         var dleft = ($(window).width() - $('#beiZhuWin').width())/2;    
         var scrollTop = $(document).scrollTop();      
         var scrollLeft = $(document).scrollLeft(); 
         dtop = dtop+ scrollTop;                      
         dleft =dleft + scrollLeft; 
	    $('#beiZhuWin').window('resize',{
	         width: 1000,
	         height: 400,
	         top:dtop,   
	         left:dleft
	      });
			var data;
		    if($('#maintenBusinessbz').attr("value")==""){
		    	data={'list': [
		    	               {
		    			'mc': '',
			            'mbt': '',
			            'mbde': '',
			            'mbn': '',
			            'mba': '',
			            'mbc': ''
		    	               }]};
			   }
			  else{ 
			     data = JSON.parse($('#maintenBusinessbz').attr("value")); 
			   }       
			
		        //获取模板
		        jsRenderTpl = $.templates('#maintenBusinessTemp'),
		        //末班与数据结合
		        finalTpl = jsRenderTpl(data);

		    $('#beizhuDivnr').html(finalTpl);
	//	$('#beizhutext').attr("value",$('#maintenBusinessbz').attr("value"));
		}
	if(beizhuid=="honestButton"){
		 $("#beiZhuWin").width(600).height(400);   //恢复备注窗口的初始画大小，免得resize时候根据备注窗口计算出来的偏移量是变化的
   	     var dtop = ($(window).height() - $('#beiZhuWin').height())/2;  
         var dleft = ($(window).width() - $('#beiZhuWin').width())/2;    
         var scrollTop = $(document).scrollTop();      
         var scrollLeft = $(document).scrollLeft(); 
         dtop = dtop+ scrollTop;                      
         dleft =dleft + scrollLeft; 
		 $('#beiZhuWin').window('resize',{
	         width: 600,
	         height: 400,
	         top:dtop,   
	         left:dleft
	      });
			var data;
		    if($('#honestbz').attr("value")==""){
		    	data={'list': [
		    	               {
		    			'ht': '',
			            'hde': '',
			            'hr': ''
		    	               }]};
			   }
			  else{ 
			     data = JSON.parse($('#honestbz').attr("value")); 
			   }    
			
		        //获取模板
		        jsRenderTpl = $.templates('#honestTemp'),
		        //末班与数据结合
		        finalTpl = jsRenderTpl(data);

		    $('#beizhuDivnr').html(finalTpl);
	//	$('#beizhutext').attr("value",$('#honestbz').attr("value"));
		}
	if(beizhuid=="punishmentButton") {
		 $("#beiZhuWin").width(1100).height(400);   //恢复备注窗口的初始画大小，免得resize时候根据备注窗口计算出来的偏移量是变化的
   	     var dtop = ($(window).height() - $('#beiZhuWin').height())/2;  
         var dleft = ($(window).width() - $('#beiZhuWin').width())/2;    
         var scrollTop = $(document).scrollTop();      
         var scrollLeft = $(document).scrollLeft(); 
         dtop = dtop+ scrollTop;                      
         dleft =dleft + scrollLeft; 
		 $('#beiZhuWin').window('resize',{
	         width: 1100,
	         height: 400,
	         top:dtop,   
	         left:dleft
	      });
			var data;
		    if($('#punishmentbz').attr("value")==""){
		    	data={'list': [
		    	               {
		    			'prg': '',
		 	            'pt': '',
		 	            'pde': '',
		 	            'pr': '',
		 	            'pl':''
		    	               }]};
			   }
			  else{ 
			     data = JSON.parse($('#punishmentbz').attr("value")); 
			   }    
			
		        //获取模板
		        jsRenderTpl = $.templates('#punishmentTemp'),
		        //末班与数据结合
		        finalTpl = jsRenderTpl(data);

		    $('#beizhuDivnr').html(finalTpl);

		    $.each(data,function(i){  
	            $.each(data[i],function(i,v){
	                 var selectV = v.pl;   
	              $("select[name='pl']").eq(i).val(selectV);
	                 
		          });  
		        }
	        );   
	//	$('#beizhutext').attr("value",$('#punishmentbz').attr("value"));
		}
	
}

function checkRepeatitem(bz){  
	  var result = 0;
	  var jsObject = JSON.parse(bz);
	      
	  for(var i=0;i<jsObject.list.length;i++){
		for(var j = i+1;j<jsObject.list.length; j++){
		    var item = JSON.stringify(jsObject.list[i]);
		    var item2 = JSON.stringify(jsObject.list[j]);
		    if(item == item2)
			       result =1;
		       }
		}
	return result;
}

var inspectElevatorBzCount =0;
var acceptInspElevatorBzCount =0;
var maintenSceneInfoBzCount =0;
var complaintsEventsBzCount =0;
var maintenBusinessBzCount =0;
var honestBzCount =0;
var punishmentBzCount =0;

var inspectElevatorTimesBzCount = 0;   //(在用电梯监督抽查情况 0:严重隐患 1:回路短接)
var inspectElevatorTimes2BzCount = 0;   //(在用电梯监督抽查情况 0:严重隐患 1:回路短接)
var complaintsEventsTimesBzCount = 0;  //(因维保质量而引发的投诉0:一般投诉1:连续投诉)
var complaintsEventsTimes2BzCount = 0;  //(因维保质量而引发的投诉0:一般投诉1:连续投诉)
var punishmentTimesBzCount = 0;   //(行政查处及处罚情况 0:维保管理及维保质量被整改  1:被下达监察意见过通报2:连续被下达监察指令或通报2次及以上3:违反法律、法规及技术规范)
var punishmentTimes2BzCount = 0;   //(行政查处及处罚情况 0:维保管理及维保质量被整改  1:被下达监察意见过通报2:连续被下达监察指令或通报2次及以上3:违反法律、法规及技术规范)
var punishmentTimes3BzCount = 0;   //(行政查处及处罚情况 0:维保管理及维保质量被整改  1:被下达监察意见过通报2:连续被下达监察指令或通报2次及以上3:违反法律、法规及技术规范)
var punishmentTimes4BzCount = 0;   //(行政查处及处罚情况 0:维保管理及维保质量被整改  1:被下达监察意见过通报2:连续被下达监察指令或通报2次及以上3:违反法律、法规及技术规范)


//验证备注数和考评扣分次数是否一致
function kpCountValidate(itemName){
    if(itemName == "inspectElevatorButton"){
    	 var v1 = $('#inspectElevatorTimes').numberspinner('getValue');
     	 var v2 = $('#inspectElevatorTimes2').numberspinner('getValue');
         var v = (parseInt(v1)+parseInt(v2));
         var trcount = $("#myTable").find("tr").length-1 ;   //添加，删除按钮占了一行

         if(v == 0){
        	inspectElevatorBzCount =trcount -1;

        	inspectElevatorTimesBzCount =trcount -1;
        	inspectElevatorTimes2BzCount =trcount -1; 
        	
         	trcount =trcount-1;
         	return 2;     //一致，需要通过      	
             }

         if(trcount >0 && v > 0){
             var sv0Count = 0;
             var sv1Count = 0;
         	for(var i=1;i<= trcount;i++ ){
         		var tr = $("#myTable tr").eq(i);
         		var sv = tr.find("select").val();
                  if(sv == 0)
                 	 sv0Count=sv0Count+1 ;
             	 if(sv == 1)
             		 sv1Count=sv1Count+1 ;
         		}
         	 if(v1 != sv0Count || v2 != sv1Count)
                  return 3;    //不一致(多项选择0：  严重隐患  1 ：回路短接)
              else{
             	 inspectElevatorTimesBzCount = sv0Count;
             	 inspectElevatorTimes2BzCount = sv1Count;
                  }
             }
         
         if(v != trcount)
             return 0;       //不一致
         else {
        	 inspectElevatorBzCount =trcount;
             return 1;       //一致
         }
        }

    if(itemName == "acceptInspElevatorButton"){
         var v1 = $('#acceptInspElevatorTimes').numberspinner('getValue');
         var v = (parseInt(v1));
         var trcount = $("#myTable").find("tr").length -1 ;

         if(v == 0){
        	acceptInspElevatorBzCount =trcount -1;
         	trcount =trcount-1;
         	return 2;     //一致，需要通过      	
             }
         
         if(v != trcount)
             return 0;       //不一致
         else{ 
        	 acceptInspElevatorBzCount =trcount;
             return 1;       //一致
         }
        }

    if(itemName == "maintenSceneInfoButton"){
        var v1 = $('#maintenSceneInfoTimes').numberspinner('getValue');
        var v = (parseInt(v1));
        var trcount = $("#myTable").find("tr").length - 1 ;

        if(v == 0){
        	maintenSceneInfoBzCount =trcount -1;
         	trcount =trcount-1;
         	return 2;     //一致，需要通过      	
             }
      
        if(v != trcount)
            return 0;       //不一致
        else {
        	maintenSceneInfoBzCount =trcount;
            return 1;       //一致
        }
       }
    
    if(itemName == "complaintsEventsButton"){
   	    var v1 = $('#complaintsEventsTimes').numberspinner('getValue');
    	var v2 = $('#complaintsEventsTimes2').numberspinner('getValue');
        var v = (parseInt(v1)+parseInt(v2));
        var trcount = $("#myTable").find("tr").length-1 ;   //添加，删除按钮占了一行

        if(v == 0){
        	complaintsEventsBzCount =trcount -1;

        	complaintsEventsTimesBzCount =trcount -1;
        	complaintsEventsTimes2BzCount=trcount -1;
         	trcount =trcount-1;
         	return 2;     //一致，需要通过      	
             }

        if(trcount >0 && v > 0){
            var sv0Count = 0;
            var sv1Count = 0;
        	for(var i=1;i<= trcount;i++ ){
        		var tr = $("#myTable tr").eq(i);
        		var sv = tr.find("select").val();
                 if(sv == 0)
                	 sv0Count=sv0Count+1 ;
            	 if(sv == 1)
            		 sv1Count=sv1Count+1 ;
        		}
        	 if(v1 != sv0Count || v2 != sv1Count)
                 return 3;    //不一致(多项选择0：一般投诉  1 ：连续投诉)
             else{
            	 complaintsEventsTimesBzCount = sv0Count;
            	 complaintsEventsTimes2BzCount = sv1Count;
                 }
            }
       
        if(v != trcount)
            return 0;       //不一致
        else {
        	 complaintsEventsBzCount =trcount;
            return 1;       //一致
        }
       }

    
    if(itemName == "maintenBusinessButton"){
        var v1 = $('#maintenBusinessTimes').numberspinner('getValue');
        var v = (parseInt(v1));
        var trcount = $("#myTable").find("tr").length-1 ;

        if(v == 0){
        	maintenBusinessBzCount =trcount -1;
         	trcount =trcount-1;
         	return 2;     //一致，需要通过      	
             }
      
        if(v != trcount)
            return 0;       //不一致
        else {
        	maintenBusinessBzCount =trcount; 
            return 1;       //一致
        }
       }

    
    if(itemName == "honestButton"){
        var v1 = $('#honestTimes').numberspinner('getValue');
        var v = (parseInt(v1));
        var trcount = $("#myTable").find("tr").length-1 ;

        if(v == 0){
        	honestBzCount =trcount -1;
         	trcount =trcount-1;
         	return 2;     //一致，需要通过      	
             }
        
        if(v != trcount)
            return 0;       //不一致
        else {
        	honestBzCount =trcount;
            return 1;       //一致
        }
       }

    
    if(itemName == "punishmentButton"){
   	    var v1 = $('#punishmentTimes').numberspinner('getValue');
    	var v2 = $('#punishmentTimes2').numberspinner('getValue');
    	var v3 = $('#punishmentTimes3').numberspinner('getValue');
    	var v4 = $('#punishmentTimes4').numberspinner('getValue');
        var v = (parseInt(v1)+parseInt(v2)+parseInt(v3)+parseInt(v4));
        var trcount = $("#myTable").find("tr").length-1 ;   //添加，删除按钮占了一行

        if(v == 0){
        	punishmentBzCount =trcount -1;

        	punishmentTimesBzCount =trcount -1;
        	punishmentTimes2BzCount =trcount -1;
        	punishmentTimes3BzCount =trcount -1;
        	punishmentTimes4BzCount =trcount -1;
        	
         	trcount =trcount-1;
         	return 2;     //一致，需要通过      	
             }
        if(trcount >0 && v > 0){
            var sv0Count = 0;
            var sv1Count = 0;
            var sv2Count = 0;
            var sv3Count = 0;
        	for(var i=1;i<= trcount;i++ ){
        		var tr = $("#myTable tr").eq(i);
        		var sv = tr.find("select").val();
                 if(sv == 0)
                	 sv0Count=sv0Count+1 ;
            	 if(sv == 1)
            		 sv1Count=sv1Count+1 ;
            	 if(sv == 2)
            		 sv2Count=sv2Count+1 ;
            	 if(sv == 3)
            		 sv3Count=sv3Count+1 ;
        		}
        	 if((v1 != sv0Count || v2 != sv1Count) || (v3 != sv2Count || v4 != sv3Count))
                 return 3;    //不一致(多项选择0：  维保管理及维保质量被整改   1 ：被下达监察意见过通报,2:连续被下达监察指令或通报2次及以上,3:违反法律、法规及技术规范)
             else{
            	 punishmentTimesBzCount = sv0Count;
            	 punishmentTimes2BzCount = sv1Count;
            	 punishmentTimes3BzCount = sv2Count;
            	 punishmentTimes4BzCount = sv3Count;
                 }
            }
      
        if(v != trcount)
            return 0;       //不一致
        else {
            punishmentBzCount =trcount; 
            return 1;       //一致
        }
       }
}


function registNumberValidate(itemName){
	 var v = 0;

	 if(itemName == "inspectElevatorButton"){
    	 var v1 = $('#inspectElevatorTimes').numberspinner('getValue');
     	 var v2 = $('#inspectElevatorTimes2').numberspinner('getValue');
          v = (parseInt(v1)+parseInt(v2));
		}
	 if(itemName == "acceptInspElevatorButton"){
         var v1 = $('#acceptInspElevatorTimes').numberspinner('getValue');
          v = (parseInt(v1));
         }

	 if(itemName == "maintenSceneInfoButton"){
	        var v1 = $('#maintenSceneInfoTimes').numberspinner('getValue');
	         v = (parseInt(v1));
	 }

	 if(itemName == "complaintsEventsButton"){
	   	    var v1 = $('#complaintsEventsTimes').numberspinner('getValue');
	    	var v2 = $('#complaintsEventsTimes2').numberspinner('getValue');
	         v = (parseInt(v1)+parseInt(v2));
	 }
	 if(itemName == "maintenBusinessButton"){
	        var v1 = $('#maintenBusinessTimes').numberspinner('getValue');
	         v = (parseInt(v1));
	 }
	 if(itemName == "honestButton"){
	        var v1 = $('#honestTimes').numberspinner('getValue');
	         v = (parseInt(v1));
	 }
	 if(itemName == "punishmentButton"){
	   	    var v1 = $('#punishmentTimes').numberspinner('getValue');
	    	var v2 = $('#punishmentTimes2').numberspinner('getValue');
	    	var v3 = $('#punishmentTimes3').numberspinner('getValue');
	    	var v4 = $('#punishmentTimes4').numberspinner('getValue');
	         v = (parseInt(v1)+parseInt(v2)+parseInt(v3)+parseInt(v4));
	 }
	if(v>0){
	var tl = $("#myTable").find("tr").length ; 
	var reg =/^\d{6}$/; 
	 
	 for(var i =1;i < tl;i++ ){
	 var v = $("#myTable tr").eq(i).find("input:first").val();

	 if(itemName == "inspectElevatorButton"){
	 if(!reg.test(v)){
		 return 0;
		 }
	 
	 var vtime =$("#myTable tr").eq(i).children().find(".Wdate").val();  
	 if("" == vtime){
	//	 $.messager.alert('操作失败','时间为空','error');
		 return 2;
		 }
	 var vaddress =$("#myTable tr").eq(i).children().find("#iAdd").val(); 
	 if("" == vaddress){    //地址为空
		 return 3;
		 }
	 var virea =$("#myTable tr").eq(i).children().find("#iRea").val(); 
	 if("" == virea){    //原因为空
		 return 4;
		 }
	 } 

	 if(itemName == "acceptInspElevatorButton"){
		 var vtime =$("#myTable tr").eq(i).children().find(".Wdate").val(); 
		 if("" == vtime){
			 return 2;
			 }
		 var vaddress =$("#myTable tr").eq(i).children().find("#ade").val(); 
		 if("" == vaddress){    //地址为空
			 return 3;
			 }
		 var virea =$("#myTable tr").eq(i).children().find("#ar").val(); 
		 if("" == virea){    //原因为空
			 return 4;
			 }
		 } 


	 if(itemName == "maintenSceneInfoButton"){
		 var vtime =$("#myTable tr").eq(i).children().find(".Wdate").val(); 
		 if("" == vtime){
			 return 2;
			 }
		 var vaddress =$("#myTable tr").eq(i).children().find("#msde").val(); 
		 if("" == vaddress){    //地址为空
			 return 3;
			 }
		 var virea =$("#myTable tr").eq(i).children().find("#msr").val(); 
		 if("" == virea){    //原因为空
			 return 4;
			 }
		 } 

	 if(itemName == "complaintsEventsButton"){
		 if(!reg.test(v)){
			 return 0;
			 }
		 
		 var vtime =$("#myTable tr").eq(i).children().find(".Wdate").val(); 
		 if("" == vtime){
			 return 2;
			 }
		 var vaddress =$("#myTable tr").eq(i).children().find("#cde").val(); 
		 if("" == vaddress){    //地址为空
			 return 3;
			 }
		 var virea =$("#myTable tr").eq(i).children().find("#cr").val(); 
		 if("" == virea){    //原因为空
			 return 4;
			 }
		 } 

	 if(itemName == "maintenBusinessButton"){
         var  vc =$("#myTable tr").eq(i).children().find("#mc").val();
         if("" == vc){   //转包单位为空
              return 5;
             }
		 
		 var vtime =$("#myTable tr").eq(i).children().find(".Wdate").val(); 
		 if("" == vtime){
			 return 2;
			 }
		 var vaddress =$("#myTable tr").eq(i).children().find("#mbde").val(); 
		 if("" == vaddress){    //地址为空
			 return 3;
			 }
		 var virea =$("#myTable tr").eq(i).children().find("#mbn").val(); 
		 if("" == virea){    //楼盘为空
			 return 4;
			 }
		 var vbarea = $("#myTable tr").eq(i).children().find("#mba").val();
		 if("" == vbarea){    //区域为空
			 return 6;
		 }
		 var vbc = $("#myTable tr").eq(i).children().find("#mbc").val();
		 if("" == vbc){    //台数为空
			 return 7;
		 }
		 } 

	 if(itemName == "honestButton"){
		
		 var vtime =$("#myTable tr").eq(i).children().find(".Wdate").val(); 
		 if("" == vtime){
			 return 2;
			 }
		 var vaddress =$("#myTable tr").eq(i).children().find("#hde").val(); 
		 if("" == vaddress){    //地址为空
			 return 3;
			 }
		 var virea =$("#myTable tr").eq(i).children().find("#hr").val(); 
		 if("" == virea){    //原因为空
			 return 4;
			 }
		 } 

	 if(itemName == "punishmentButton"){
		 if(!reg.test(v)){
			 return 0;
			 }
		 
		 var vtime =$("#myTable tr").eq(i).children().find(".Wdate").val(); 
		 if("" == vtime){
			 return 2;
			 }
		 var vaddress =$("#myTable tr").eq(i).children().find("#pde").val(); 
		 if("" == vaddress){    //地址为空
			 return 3;
			 }
		 var virea =$("#myTable tr").eq(i).children().find("#pr").val(); 
		 if("" == virea){    //原因为空
			 return 4;
			 }
		 } 
	 
	 
	}
	 }
	return 1;	
}

function bzsave(){   
//	var beizhutext = $('#beizhutext').attr("value"); 
    var  bz =""; 

  
    
    if(beizhuid=="inspectElevatorButton"){
 	   bz =TableToJsonSelect("myTable","iLev");
 	  if(checkRepeatitem(bz)>0){
		   $.messager.alert('操作失败','存在相同备注记录','error');
            return; 
		   }
	   
 	   if(kpCountValidate("inspectElevatorButton") == 0){
    	  $.messager.alert('操作失败','备注记录条数和考评记录数不一致,请修改','error');
          return; 
         }

 	  if(kpCountValidate("inspectElevatorButton") == 3){
    	  $.messager.alert('操作失败','备注中对应级别选项数与考评中选择不一致请修改','error');
          return; 
         }

 	  if(kpCountValidate("inspectElevatorButton") == 2){
		  bz ="";
         }

      /*
 	  if(registNumberValidate("inspectElevatorButton") == 0){
 		 $.messager.alert('操作失败','二维码编号只能为6位数字','error');
 	       return ;
 	        }  */
       
 	   var rv = registNumberValidate("inspectElevatorButton");
 	   if(rv == 0){
    	   $.messager.alert('操作失败','备注二维码编号只能为6位数字','error');
 	       return ;
 	   }
       if(rv == 2){
    	   $.messager.alert('操作失败','备注时间不能为空','error'); 
    	   return ;
       }
       if(rv == 3){
    	   $.messager.alert('操作失败','备注地点不能为空','error'); 
    	   return ;
       }
       if(rv == 4){
    	   $.messager.alert('操作失败','备注原因不能为空','error'); 
    	   return ;
       }
    	
 	   }

    /*
	 var len = $('#beizhutext').val().length; 
	 if(len > 200){
		 $('#beizhutext').val($('#beizhutext').val().substring(0,200));
		 $.messager.alert('保存失败','最大只能输入200个字符','error');
		 $('#beizhutext').focus();
	    	return;
		 } 
      */
      
     if(beizhuid=="acceptInspElevatorButton"){
    	 bz =TableToJson("myTable");
    	 if(checkRepeatitem(bz)>0){
  		   $.messager.alert('操作失败','存在相同备注记录','error');
               return; 
  		   }

    	 if(kpCountValidate("acceptInspElevatorButton") == 0){
       	  $.messager.alert('操作失败','备注记录条数和考评记录数不一致,请修改','error');
             return; 
            }

    	 if(kpCountValidate("acceptInspElevatorButton") == 2){
   		  bz ="";
            }

    	 var rv = registNumberValidate("acceptInspElevatorButton");  
   	     
         if(rv == 2){
      	   $.messager.alert('操作失败','备注时间不能为空','error'); 
      	   return ;
         }
         if(rv == 3){
      	   $.messager.alert('操作失败','备注地点不能为空','error'); 
      	   return ;
         }
         if(rv == 4){
      	   $.messager.alert('操作失败','备注原因不能为空','error'); 
      	   return ;
         }
         
          }

    if(beizhuid=="maintenSceneInfoButton"){
    	 bz =TableToJson("myTable");
    	 if(checkRepeatitem(bz)>0){
  		   $.messager.alert('操作失败','存在相同备注记录','error');
               return; 
  		   }

    	 if(kpCountValidate("maintenSceneInfoButton") == 0){
          	  $.messager.alert('操作失败','备注记录条数和考评记录数不一致,请修改','error');
                return; 
               }
    	 if(kpCountValidate("maintenSceneInfoButton") == 2){
      		  bz ="";
               }

         var rv = registNumberValidate("maintenSceneInfoButton");  
   	     
         if(rv == 2){
      	   $.messager.alert('操作失败','备注时间不能为空','error'); 
      	   return ;
         }
         if(rv == 3){
      	   $.messager.alert('操作失败','备注地点不能为空','error'); 
      	   return ;
         }
         if(rv == 4){
      	   $.messager.alert('操作失败','备注原因不能为空','error'); 
      	   return ;
         }
          }
    if(beizhuid=="complaintsEventsButton"){
 	   bz =TableToJsonSelect("myTable","cl"); 
 	  if(checkRepeatitem(bz)>0){
		   $.messager.alert('操作失败','存在相同备注记录','error');
            return; 
		   }

 	 if(kpCountValidate("complaintsEventsButton") == 0){
     	  $.messager.alert('操作失败','备注记录条数和考评记录数不一致,请修改','error');
           return; 
          }

 	if(kpCountValidate("complaintsEventsButton") == 3){
  	  $.messager.alert('操作失败','备注中对应级别选项数与考评中选择不一致请修改','error');
        return; 
       }
    
 	 if(kpCountValidate("complaintsEventsButton") == 2){
 		  bz ="";
          }

 	var rv = registNumberValidate("complaintsEventsButton");
	   if(rv == 0){
 	   $.messager.alert('操作失败','备注二维码编号只能为6位数字','error');
	       return ;
	   }
    if(rv == 2){
 	   $.messager.alert('操作失败','备注时间不能为空','error'); 
 	   return ;
    }
    if(rv == 3){
 	   $.messager.alert('操作失败','备注地点不能为空','error'); 
 	   return ;
    }
    if(rv == 4){
 	   $.messager.alert('操作失败','备注原因不能为空','error'); 
 	   return ;
    }
	   
 	   }
    if(beizhuid=="maintenBusinessButton"){ 
 	   bz =TableToJson("myTable"); 
 	  if(checkRepeatitem(bz)>0){
		   $.messager.alert('操作失败','存在相同备注记录','error');
            return; 
		   }

 	 if(kpCountValidate("maintenBusinessButton") == 0){
    	  $.messager.alert('操作失败','备注记录条数和考评记录数不一致,请修改','error');
          return; 
         }
 	if(kpCountValidate("maintenBusinessButton") == 2){
		  bz ="";
        }

 	 var rv = registNumberValidate("maintenBusinessButton");
 	if(rv == 5){
   	   $.messager.alert('操作失败','备注转包单位不能为空','error'); 
   	   return ;
      }  
	     
     if(rv == 2){
  	   $.messager.alert('操作失败','备注转包时间不能为空','error'); 
  	   return ;
     }
     if(rv == 3){
  	   $.messager.alert('操作失败','备注地点不能为空','error'); 
  	   return ;
     }
     if(rv == 4){
  	   $.messager.alert('操作失败','备注楼盘不能为空','error'); 
  	   return ;
     }
     if(rv == 6){
    	   $.messager.alert('操作失败','备注区域不能为空','error'); 
    	   return ;
       }
     if(rv == 7){
    	   $.messager.alert('操作失败','备注台数不能为空','error'); 
    	   return ;
       }
     
	   
 	    }

    if(beizhuid=="honestButton"){
 	   bz =TableToJson("myTable"); 
 	  if(checkRepeatitem(bz)>0){
		   $.messager.alert('操作失败','存在相同备注记录','error');
            return; 
		   }
 	 if(kpCountValidate("honestButton") == 0){
   	  $.messager.alert('操作失败','备注记录条数和考评记录数不一致,请修改','error');
         return; 
        }
 	if(kpCountValidate("honestButton") == 2){
		  bz ="";
      }

 	 var rv = registNumberValidate("honestButton");  
	     
     if(rv == 2){
  	   $.messager.alert('操作失败','备注时间不能为空','error'); 
  	   return ;
     }
     if(rv == 3){
  	   $.messager.alert('操作失败','备注地点不能为空','error'); 
  	   return ;
     }
     if(rv == 4){
  	   $.messager.alert('操作失败','备注原因不能为空','error'); 
  	   return ;
     }
	   
 	   }

    if(beizhuid=="punishmentButton"){  
 	   bz =TableToJsonSelect("myTable","pl"); 
 	  if(checkRepeatitem(bz)>0){
		   $.messager.alert('操作失败','存在相同备注记录','error');
            return; 
		   }

 	 if(kpCountValidate("punishmentButton") == 0){
 	   	  $.messager.alert('操作失败','备注记录条数和考评记录数不一致,请修改','error');
 	         return; 
 	        }
 	if(kpCountValidate("punishmentButton") == 3){
 	  	  $.messager.alert('操作失败','备注中对应级别选项数与考评中选择不一致请修改','error');
 	        return; 
 	       }
     
 	if(kpCountValidate("punishmentButton") == 2){
		  bz ="";
    }
     /*
 	 if(registNumberValidate("punishmentButton") == 0){
 		 $.messager.alert('操作失败','二维码编号只能为6位数字','error');
 	       return ;
 	        }  */

 	 var rv = registNumberValidate("punishmentButton");  
 	 if(rv == 0){
 	 	   $.messager.alert('操作失败','备注二维码编号只能为6位数字','error');
 		       return ;
 		   }
     if(rv == 2){
  	   $.messager.alert('操作失败','备注时间不能为空','error'); 
  	   return ;
     }
     if(rv == 3){
  	   $.messager.alert('操作失败','备注地点不能为空','error'); 
  	   return ;
     }
     if(rv == 4){
  	   $.messager.alert('操作失败','备注原因不能为空','error'); 
  	   return ;
     }
    }

    
 //  var  beizhutext = JSON.stringify($("#bzform").serializeObject());   //json字符串     
  

 //  var len =beizhutext.length;
   var bzlen=bz.length;
	
	if(beizhuid=="inspectElevatorButton"){
		 if(bzlen >=3000){
			   $.messager.alert('操作失败','备注内容超出最大字符长度限制，请精简备注内容','error');
			   return;
			   } 
	    $('#inspectElevatorbz').attr("value",bz);
	    
//		 if(beizhutext != "") 
        if(bz != "")  
			    $("#inspectElevatorButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#inspectElevatorButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="acceptInspElevatorButton"){
		 if(bzlen >=3000){
			   $.messager.alert('操作失败','备注内容超出最大字符长度限制，请精简备注内容','error');
			   return;
			   }
	    $('#acceptInspElevatorbz').attr("value",bz); 
//		 if(beizhutext != "") 
        if(bz != "") 
			    $("#acceptInspElevatorButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#acceptInspElevatorButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="maintenSceneInfoButton"){
		 if(bzlen >=3000){
			   $.messager.alert('操作失败','备注内容超出最大字符长度限制，请精简备注内容','error');
			   return;
			   }
	     $('#maintenSceneInfobz').attr("value",bz);
//		 if(beizhutext != "") 
         if(bz != "")  
			    $("#maintenSceneInfoButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#maintenSceneInfoButton").attr('src','../../images/beizhu.png');
	}
	
	
	if(beizhuid=="complaintsEventsButton"){
		 if(bzlen >=3000){
			   $.messager.alert('操作失败','备注内容超出最大字符长度限制，请精简备注内容','error');
			   return;
			   }
	    $('#complaintsEventsbz').attr("value",bz);
//		 if(beizhutext != "") 
        if(bz != "") 
			    $("#complaintsEventsButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#complaintsEventsButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="maintenBusinessButton"){
		 if(bzlen >=3000){
			   $.messager.alert('操作失败','备注内容超出最大字符长度限制，请精简备注内容','error');
			   return;
			   }
	     $('#maintenBusinessbz').attr("value",bz);
//		 if(beizhutext != "") 
         if(bz != "") 
			    $("#maintenBusinessButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#maintenBusinessButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="honestButton"){
		 if(bzlen >=3000){
			   $.messager.alert('操作失败','备注内容超出最大字符长度限制，请精简备注内容','error');
			   return;
			   }
	    $('#honestbz').attr("value",bz);
//		 if(beizhutext != "") 
        if(bz != "")   
			    $("#honestButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#honestButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="punishmentButton"){
		 if(bzlen >=3000){
			   $.messager.alert('操作失败','备注内容超出最大字符长度限制，请精简备注内容','error');
			   return;
			   }
	   $('#punishmentbz').attr("value",bz);
//		 if(beizhutext != "") 
       if(bz != "")  
		    $("#punishmentButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#punishmentButton").attr('src','../../images/beizhu.png');
	}
	
	 $('#beiZhuWin').window('close');
}

function  bzImg(){
	
	 <% if (role ==10 || role ==11) { %>
 	   if($('#inspectElevatorbz').attr("value") != "")
 		  $("#inspectElevatorButton").attr('src','../../images/youbeizhu.png');
 	   else
 		  $("#inspectElevatorButton").attr('src','../../images/beizhu.png');

 	   if($('#acceptInspElevatorbz').attr("value") != "")
 		  $("#acceptInspElevatorButton").attr('src','../../images/youbeizhu.png'); 
 	   else
 		  $("#acceptInspElevatorButton").attr('src','../../images/beizhu.png');  
	
       if($('#maintenSceneInfobz').attr("value") != "")
    	   $("#maintenSceneInfoButton").attr('src','../../images/youbeizhu.png');
       else
    	   $("#maintenSceneInfoButton").attr('src','../../images/beizhu.png'); 
	   
       if($('#complaintsEventsbz').attr("value") != "")
		   $("#complaintsEventsButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#complaintsEventsButton").attr('src','../../images/beizhu.png');
	   
       if($('#maintenBusinessbz').attr("value") != "")
		   $("#maintenBusinessButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#maintenBusinessButton").attr('src','../../images/beizhu.png'); 
	
	   if($('#honestbz').attr("value") != "")
		   $("#honestButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#honestButton").attr('src','../../images/beizhu.png'); 
	
       if($('#punishmentbz').attr("value") != "")
    	   $("#punishmentButton").attr('src','../../images/youbeizhu.png'); 
       else
    	   $("#punishmentButton").attr('src','../../images/beizhu.png');  
	  
	<% } %>

}

//var oId = 1; 
function addRow(){ 
   var tr = $("#myTable tr").eq(1).clone();   
   tr.appendTo("#myTable");  
 //  oId =oId+1;   
   var lastTr =$("#myTable tr").eq($("#myTable tr").length - 1); 
   lastTr.find("input").val("");
 //  lastTr.find("select").find("option").first().attr("selected", true);
   
    
}

function deleteRow(){  
	 if($("#myTable tr").length>2){
	 $("#myTable tr").eq($("#myTable tr").length - 1).remove();
	// oId =oId-1;
	 }
	 else{    //第一行就清空备注
		    var firstTr =$("#myTable tr").eq(1); 
		    firstTr.find("input").val("");
			 }
	 }

function queryRow(){
	$('#queryWin').window('open');  
}

function queryRegistNumber(){
	jQuery.post('/tcweb/elevator/queryinfoByReg', {'registNumber':$('#qregistNumber').attr("value")},function(data){ 
        data = eval(data);//POST方法必加，ajax方法自动处理了   
        $('#qaddress').attr("value",data.address);
        $('#qbuildingName').attr("value",data.buildingName);
        $('#qarea').attr("value",data.area);
        $('#qregistCode').attr("value",data.registCode);
	   }, 'json');	
}



function TableToJson(tableid) {
    var txt = "{\"list\":[";
    var table = document.getElementById(tableid);
    var row = table.getElementsByTagName("tr");
    var col = row[1].getElementsByTagName("td");
    for (var j = 1; j < row.length; j++) {
        var r = "{";
        for (var i = 0; i < col.length; i++) {
            var tds = row[j].getElementsByTagName("input");
            r += "\"" + tds[i].name + "\"\:\"" + tds[i].value + "\",";

        }
        r = r.substring(0, r.length - 1)
        r += "},";
        txt += r;
    }

    txt = txt.substring(0, txt.length - 1);

    txt += "]}";

    return txt; 

}

function TableToJsonSelect(tableid,itemName) {
    var txt = "{\"list\":[";
    var table = document.getElementById(tableid);
    var row = table.getElementsByTagName("tr");
    var col = row[1].getElementsByTagName("td");
    for (var j = 1; j < row.length; j++) {
        var r = "{";
        for (var i = 0; i < col.length; i++) {
            if(i<4){
            var tds = row[j].getElementsByTagName("input");
            r += "\"" + tds[i].name + "\"\:\"" + tds[i].value + "\",";
            }
            else 
            {
           var tds = row[j].getElementsByTagName("select");      
      //      r += "\"" + "malignantEventsLevel" + "\"\:\"" +tds[0].value + "\",";
              r += "\"" + itemName + "\"\:\"" +tds[0].value + "\",";
             }  
       //     r += "\"" + tds[i].name + "\"\:\"" + tds[i].value + "\",";
        }
        r = r.substring(0, r.length - 1);
        r += "},";
        txt += r;
    }

    txt = txt.substring(0, txt.length - 1);

    txt += "]}";

    return txt; 

}
</script>
<!-- 模板 -->
<script type="text/x-jsrender" id="inspectElevatorTemp">
   <table id="myTable">
     <tr>
     <td>
   <a href="#" name="addRow" class='bztoolbutton'  onclick="addRow()">添加</a>
   <a href="#" name="deleteRow"  class='bztoolbutton' onclick="deleteRow()">删除</a>
   <a href="#" name="queryRow"  class='bztoolbutton' onclick="queryRow()">查询二维码</a>
    </td>
   </tr>
     {{for list}}
    <tr>
       <td>二维码编号: <input type="text" id="iReg" name="iReg" value="{{:iReg}}"></td>
       <td>时间: <input type="text" id="iTime" name="iTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:iTime}}"></td>
       <td>地点: <input type="text" id="iAdd" name="iAdd" value="{{:iAdd}}"></td>
       <td>原因: <input type="text" id="iRea" name="iRea" value="{{:iRea}}"></td>
       <td>级别:  <select name="iLev" id="iLev" value="{{:iLev}}">  
                      <option value="0">严重隐患</option>  
                      <option value="1">回路短接</option>
                     </select>   
         </td>
    </tr>
  {{/for}}
</table>
</script>
<script type="text/x-jsrender" id="acceptInspElevatorTemp">
   <table id="myTable">
 <tr><td><a href="#" name="addRow" class="bztoolbutton" onclick="addRow()">添加</a><a href="#" name="deleteRow" class="bztoolbutton" onclick="deleteRow()">删除</a></td></tr>
     {{for list}}
    <tr>
       <td>时间: <input type="text" id="at" name="at" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:at}}"></td>
       <td>地点: <input type="text" id="ade" name="ade" value="{{:ade}}"></td>
       <td>原因: <input type="text" id="ar" name="ar" value="{{:ar}}"></td>
    </tr>
{{/for}}
</table>
</script>
<script type="text/x-jsrender" id="maintenSceneInfoTemp">
   <table id="myTable">
 <tr><td><a href="#" name="addRow" class="bztoolbutton" onclick="addRow()">添加</a><a href="#" name="deleteRow" class="bztoolbutton" onclick="deleteRow()">删除</a></td></tr>
     {{for list}}
    <tr>
       <td>时间: <input type="text" id="mst" name="mst" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:mst}}"></td>
       <td>地点: <input type="text" id="msde" name="msde" value="{{:msde}}"></td>
       <td>原因: <input type="text" id="msr" name="msr" value="{{:msr}}"></td>
    </tr>
{{/for}}
</table>
</script>
<script type="text/x-jsrender" id="complaintsEventsTemp">
   <table id="myTable">
  <tr><td><a href="#" name="addRow" class="bztoolbutton" onclick="addRow()">添加</a><a href="#" name="deleteRow" class="bztoolbutton" onclick="deleteRow()">删除</a> <a href="#" name="queryRow"  class='bztoolbutton' onclick="queryRow()">查询二维码</a></td></tr>
     {{for list}}
    <tr>
       <td>二维码编号: <input type="text" id="crg" name="crg" value="{{:crg}}"></td>
       <td>时间: <input type="text" id="ct" name="ct" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:ct}}"></td>
       <td>地点: <input type="text" id="cde" name="cde" value="{{:cde}}"></td>
       <td>原因: <input type="text" id="cr" name="cr" value="{{:cr}}"></td>
       <td>级别:  <select name="cl" id="cl" value="{{:cl}}">  
                      <option value="0">一般投诉</option>  
                      <option value="1">连续投诉</option>
                     </select>   
         </td>
    </tr>
  {{/for}}
</table>
</script>
<script type="text/x-jsrender" id="maintenBusinessTemp">
    <table id="myTable">
 <tr><td><a href="#" name="addRow" class="bztoolbutton" onclick="addRow()">添加</a><a href="#" name="deleteRow" class="bztoolbutton" onclick="deleteRow()">删除</a></td></tr>
     {{for list}}
    <tr>
       <td>转包单位: <input type="text" id="mc" name="mc" value="{{:mc}}"></td>
       <td>转包时间: <input type="text" id="mbt" name="mbt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:mbt}}"></td>
       <td>地点: <input type="text" id="mbde" name="mbde" value="{{:mbde}}"></td>
       <td>楼盘: <input type="text" id="mbn" name="mbn" value="{{:mbn}}" style="width:100px;"></td>
       <td>区域:  <input type="text" id="mba" name="mba" value="{{:mba}}" style="width:50px;"></td>
       <td>台数:  <input type="text" id="mbc" name="mbc" value="{{:mbc}}" style="width:50px;"></td>
    </tr>
 {{/for}}
</table>
</script>
<script type="text/x-jsrender" id="honestTemp">
   <table id="myTable">
 <tr><td><a href="#" name="addRow" class="bztoolbutton" onclick="addRow()">添加</a><a href="#" name="deleteRow" class="bztoolbutton" onclick="deleteRow()">删除</a></td></tr>
     {{for list}}
    <tr>
       <td>时间: <input type="text" id="ht" name="ht" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:ht}}"></td>
       <td>地点: <input type="text" id="hde" name="hde" value="{{:hde}}"></td>
       <td>原因: <input type="text" id="hr" name="hr" value="{{:hr}}"></td>
    </tr>
{{/for}}
</table>
</script>
<script type="text/x-jsrender" id="punishmentTemp">
    <table id="myTable">
 <tr><td><a href="#" name="addRow" class="bztoolbutton" onclick="addRow()">添加</a><a href="#" name="deleteRow" class="bztoolbutton" onclick="deleteRow()">删除</a> <a href="#" name="queryRow"  class='bztoolbutton' onclick="queryRow()">查询二维码</a></td></tr>
     {{for list}}
     <tr>
       <td>二维码编号: <input type="text" id="prg" name="prg"  value="{{:prg}}"></td>
       <td>时间: <input type="text" id="pt" name="pt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:pt}}"></td>
       <td>地点: <input type="text" id="pde" name="pde" value="{{:pde}}"></td>
       <td>原因: <input type="text" id="pr" name="pr" value="{{:pr}}"></td>
       <td>级别: <select name="pl" id="pl" value="{{:pl}}">  
                      <option value="0">维保管理及维保质量被整改</option>  
                      <option value="1">被下达监察意见过通报</option>
                      <option value="2">连续被下达监察指令或通报2次及以上</option>
                      <option value="3">违反法律、法规及技术规范</option>
                     </select>
      </td>
    </tr>
{{/for}}
</table>
</script>
<style type="text/css">
td{
  margin: 0;
  padding: 0 4px;
  white-space: nowrap;
  word-wrap: normal;
  overflow: hidden;
  height: 18px;
  line-height: 30px;
  font-size: 12px;
  border-collapse:collapse;
	}
/*
 .datatable input:hover,.datatable input.input3
    {
    background-color:#ffe48d;
    color:#0000CC;
    } */
	
 .datatable tr:hover,.datatable tr.hilite
    {
    background-color:#ffe48d;
    color:#0000CC;
    }
  
.bztoolbutton{
  line-height:31px;
  height:31px;
  width:72px;
  color:#ffffff;
  background-color:#ededed;
  font-size:15px;
  font-weight:lighter;
  font-family:Arial;
  background:-webkit-gradient(linear, left top, left bottom, color-start(0.05, #599bb3), color-stop(1, #408c99));
  background:-moz-linear-gradient(top, #599bb3 5%, #408c99 100%);
  background:-o-linear-gradient(top, #599bb3 5%, #408c99 100%);
  background:-ms-linear-gradient(top, #599bb3 5%, #408c99 100%);
  background:linear-gradient(to bottom, #599bb3 5%, #408c99 100%);
  background:-webkit-linear-gradient(top, #599bb3 5%, #408c99 100%);
  filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#599bb3', endColorstr='#408c99',GradientType=0);
  border:0px solid #dcdcdc;
  -webkit-border-top-left-radius:8px;
  -moz-border-radius-topleft:8px;
  border-top-left-radius:8px;
  -webkit-border-top-right-radius:8px;
  -moz-border-radius-topright:8px;
  border-top-right-radius:8px;
  -webkit-border-bottom-left-radius:8px;
  -moz-border-radius-bottomleft:8px;
  border-bottom-left-radius:8px;
  -webkit-border-bottom-right-radius:8px;
  -moz-border-radius-bottomright:8px;
  border-bottom-right-radius:8px;
  -moz-box-shadow:0px 10px 14px -7px #276873;
  -webkit-box-shadow:0px 10px 14px -7px #276873;
  box-shadow:0px 10px 14px -7px #276873;
  text-align:center;
  display:inline-block;
  text-decoration:none;
}
.bztoolbutton:hover{
  background-color:#f5f5f5;
  background:-webkit-gradient(linear, left top, left bottom, color-start(0.05, #408c99), color-stop(1, #599bb3));
  background:-moz-linear-gradient(top, #408c99 5%, #599bb3 100%);
  background:-o-linear-gradient(top, #408c99 5%, #599bb3 100%);
  background:-ms-linear-gradient(top, #408c99 5%, #599bb3 100%);
  background:linear-gradient(to bottom, #408c99 5%, #599bb3 100%);
  background:-webkit-linear-gradient(top, #408c99 5%, #599bb3 100%);
  filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#408c99', endColorstr='#599bb3',GradientType=0);
}
     
</style>
</head>
<body class="easyui-layout" style="height:100%;" fit="true">
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);height:100px;position:relative;">
 <div style="position:absolute; left:5px; bottom:5px;font-size:14px;font-weight:bold;color:#000000">当前考评维保单位&nbsp;&nbsp;<span id="dqkpdw" style="font-size:14px;font-weight:bold; color:#0000ff"></span></div>
     
       <center>
        <table style="margin-top:20px">
        <tr>
        <td nowrap>维保单位：</td>
        <td>
        <input id="ywCompanyIdinfo" style="width:252px;background-color:#87CEEB;" ></input>
        <input type ="hidden" id="ywCompanyIdinfo2"></input> </td>
        <td nowrap>时间：</td>
        <td>
    <!-- <select id="ratingDate2"  class="easyui-datebox" name="ratingDate2" style="width:152px;" data-options="editable:false"></select> -->
         <input type="text" id="ratingDate2" name="ratingDate2" onfocus="WdatePicker({dateFmt:'yyyy-MM', onpicked:ywcompanySelectRefresh})" class="Wdate" >
        </td>
        <td><a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a></td>  
       </tr>
       </table>
       </center>
</div>
<div region="center"  border="false"> 
 <!--   <table class="easyui-datagrid" title="电梯维保企业质量信用等级评价项目明细" 
			data-options="singleSelect:true">  -->
			<form id="myform" name="myform"> 
			<table  class="datatable" border ="1" style="border-width: 1px; border-style: ridge; border-collapse:collapse;"  width="100%" cellspacing="0" cellpadding="1"> 
		<thead>
			<tr>
				<th data-options="field:'评价大项',width:$(this).width() * 0.06,align:'center'" rowspan="2" align="center" style="background-color:rgb(201,220,245);height:30px">评价大项</th>
				<th data-options="field:'评价小项',width:$(this).width() * 0.10,align:'center'" rowspan="2" align="center" style="background-color:rgb(201,220,245);height:30px">评价小项</th>
				<th data-options="field:'基本要求',width:$(this).width() * 0.12,align:'center'" align="center" style="background-color:rgb(201,220,245);height:30px">基本要求</th>
				<th data-options="field:'基础分',width:$(this).width() * 0.12,align:'center'" align="center" style="background-color:rgb(201,220,245);height:30px">基础分</th>
				<th data-options="field:'考评'" align="center"  style="background-color:rgb(201,220,245);height:30px;">考评</th>
				<th data-options="field:'备注'" align="center" style="background-color:rgb(201,220,245);height:30px">备注</th>
				<th data-options="field:'加减分标准',align:'center'" align="center" style="background-color:rgb(201,220,245);height:30px">加减分标准</th>
				<th data-options="field:'合计',width:$(this).width() * 0.12,align:'center'" align="center" style="background-color:rgb(201,220,245);height:30px">合计</th>
				
			</tr>
		</thead>
		<tbody>
	
         <tr> 
            <td  rowspan="9" align="center" style="background-color:#F0FFFF">日常监管<br>及投诉情况</td>    
            <td align="center" style="background-color:#F0FFFF">在用电梯监督抽查情况</td>  
            <td align="center">合格</td> 
            <td align="center"><input id="inspectElevatorjc"  type="text"  name="inspectElevatorjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left">
            <input id="inspectElevatorTimes" name="inspectElevatorTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input> 严重隐患&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="inspectElevatorTimes2" name="inspectElevatorTimes2" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>回路短接</td>
            <td align="center">
            <input id="inspectElevatorbz"  type="hidden" class="easyui-validatebox" name="inspectElevatorbz" style="width:152px;text-align:center;"></input>
          <!--     <input type="button" id="inspectElevatorButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->
           <img src="../../images/beizhu.png" id="inspectElevatorButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">监督检查、执法检查、监督抽查中发现严重隐患，&nbsp;&nbsp;<b>-5分/台</b><br>发现安全回路短接，&nbsp;&nbsp;<b>-20分/台</b> </td>  
            <td align="center"><input id="inspectElevatorsj"  type="text"  name="inspectElevatorsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        <tr>    
            <td align="center" style="background-color:#F0FFFF">接受监督检查情况</td>  
            <td align="center">配合</td> 
            <td align="center"><input id="acceptInspElevatorjc"  type="text"  name="acceptInspElevatorjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td align="left"><input id="acceptInspElevatorTimes" name="acceptInspElevatorTimes" class="easyui-numberspinner" style="width:50px;"  value="0" data-options="min:0,editable:false"></input>次</td>
           <td align="center"> 
           <input id="acceptInspElevatorbz" type="hidden"  class="easyui-validatebox" name="acceptInspElevatorbz" style="width:152px;text-align:center;"></input>
         <!--  <input type="button" id="acceptInspElevatorButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  -->  
            <img src="../../images/beizhu.png" id="acceptInspElevatorButton" title="" onclick="addBeiZhu(this.id)" />
           </td>
            <td align="center">拒绝或不配合抽查、执法检查&nbsp;&nbsp;<b>-10分/次</b> </td>  
            <td align="center"><input id="acceptInspElevatorsj"  type="text"  name="acceptInspElevatorsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
         <tr>    
            <td align="center" style="background-color:#F0FFFF">维保现场防护情况</td>  
            <td align="center">有防护</td> 
            <td align="center"><input id="maintenSceneInfojc"  type="text"  name="maintenSceneInfojc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td align="left"><input id="maintenSceneInfoTimes" name="maintenSceneInfoTimes" class="easyui-numberspinner" style="width:50px;"  value="0" data-options="min:0,editable:false"></input>次</td>
            <td align="center">
            <input id="maintenSceneInfobz" type="hidden"  class="easyui-validatebox" name="maintenSceneInfobz" style="width:152px;text-align:center;"></input>
          <!--   <input type="button" id="maintenSceneInfoButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->
          <img src="../../images/beizhu.png" id="maintenSceneInfoButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">未设立警示标识及保护措施&nbsp;&nbsp;<b>-10分/次 </td>  
            <td align="center"><input id="maintenSceneInfosj"  type="text"  name="maintenSceneInfosj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>  
        <tr>    
            <td align="center" style="background-color:#F0FFFF">因维保质量而引发的投诉<br>（领导信箱、公开电话、网络理政平台）</td>  
            <td align="center">无</td> 
            <td align="center"><input id="complaintsEventsjc"  type="text"  name="complaintsEventssjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left">
            <input id="complaintsEventsTimes" name="complaintsEventsTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>一般投诉&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="complaintsEventsTimes2" name="complaintsEventsTimes2" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>连续投诉</td>
            <td align="center">
            <input id="complaintsEventsbz" type="hidden"  class="easyui-validatebox" name="complaintsEventsbz" style="width:152px;text-align:center;"></input>
         <!--     <input type="button" id="complaintsEventsButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> --> 
           <img src="../../images/beizhu.png" id="complaintsEventsButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">&nbsp;&nbsp;<b>-5分/宗</b>，年度内连续被投诉2次及以上的，&nbsp;&nbsp;<b>-20分/宗</b></td>  
            <td align="center"><input id="complaintsEventssj"  type="text"  name="complaintsEventssj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
         <tr>    
            <td align="center" style="background-color:#F0FFFF">维保业务管理</td>  
            <td align="center">不得转分包</td> 
            <td align="center"><input id="maintenBusinessjc"  type="text"  name="maintenBusinessjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td align="left"><input id="maintenBusinessTimes" name="maintenBusinessTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>宗</td>
            <td align="center">
            <input id="maintenBusinessbz"  type="hidden" class="easyui-validatebox" name="maintenBusinessbz" style="width:152px;text-align:center;"></input>
        <!--   <input type="button" id="maintenBusinessButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->    
             <img src="../../images/beizhu.png" id="maintenBusinessButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">存在转包或分包&nbsp;&nbsp;<b>-20分/宗</b> </td>  
            <td align="center"><input id="maintenBusinesssj"  type="text"  name="maintenBusinesssj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        <tr>    
            <td align="center" style="background-color:#F0FFFF">诚实守信情况</td>  
            <td align="center">守信</td> 
            <td align="center"><input id="honestjc"  type="text"  name="honestjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td align="left"><input id="honestTimes" name="honestTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>宗</td>
            <td align="center">
            <input id="honestbz" type="hidden"  class="easyui-validatebox" name="honestbz" style="width:152px;text-align:center;"></input>
         <!--    <input type="button" id="honestButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  --> 
             <img src="../../images/beizhu.png" id="honestButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">伪造涂改工作记录、检验报告、管理标识等技术资料<br>不诚实手段获取资质，不正当手段参与市场竞争&nbsp;&nbsp;<b>-20分/宗</b> </td>  
            <td align="center"><input id="honestsj"  type="text"  name="honestsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        <tr>    
            <td align="center" style="background-color:#F0FFFF">行政查处及处罚情况</td>  
            <td align="center">无</td> 
            <td align="center"><input id="punishmentjc"  type="text"  name="punishmentjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left"><br>
            <input id="punishmentTimes"  name="punishmentTimes"  class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>维保管理及维保质量被整改&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input id="punishmentTimes2" name="punishmentTimes2" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>被下达监察意见过通报&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input id="punishmentTimes3" name="punishmentTimes3" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>连续被下达监察指令或通报2次及以上&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input id="punishmentTimes4" name="punishmentTimes4" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>违反法律、法规及技术规范<br><br></td>
           <td align="center">
           <input id="punishmentbz"  type="hidden" class="easyui-validatebox" name="punishmentbz" style="width:152px;text-align:center;"></input>
        <!--     <input type="button" id="punishmentButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  -->
        <img src="../../images/beizhu.png" id="punishmentButton" title="" onclick="addBeiZhu(this.id)" />
           </td>
            <td align="center">现场监督检查、执法检查、重点时段抽查因维保管理及维保质量被整改&nbsp;&nbsp;<b>-2分/宗</b>；<br>被下达监察意见过通报，&nbsp;&nbsp;<b>-5分/宗</b>；<br>本监督连续被下达监察指令或通报2次及以上，&nbsp;&nbsp;<b>-20分/宗</b>；<br>违反法律、法规及技术规范，&nbsp;&nbsp;<b>-20分/宗</b> </td>  
            <td align="center"><input id="punishmentsj"  type="text"  name="punishmentsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        <tr id="hiddentr">
        <td><input type ="hidden" id="ywCompanyID" name="ywCompanyID"></td>
        <td><input type ="hidden" id="ratingDate" name="ratingDate"></td>
        </tr>        
		</tbody>
	</table>
	</form>
 </div>
 <div region="south" style="height:150px;">
 
    <table width="100%">
         <tr>
   <!--  <td align="right" colspan="4">    
                           合计基础分&nbsp;&nbsp;<input id="hjtotal"  type="text"  name="hjtotal" style="width:152px;border-width :0px 0px 1px;" readOnly></input>
                           合计实得分<input id="sjhjtotal"  type="text"  name="sjhjtotal" style="width:152px;border-width :0px 0px 1px;" readOnly></input></td>  -->      
           <td align="right" colspan="6"><input id="hjtotal"  type="text"  name="hjtotal" style="width:152px;border-width :0px 0px 1px;" readOnly></input>
           <input id="sjhjtotal"  type="text"  name="sjhjtotal" style="width:152px;border-width :0px 0px 1px;" readOnly></input>         
                           合计加减分 <input id="sjhjjjtotal"  type="text"  name="sjhjjjtotal" style="width:152px;border-width :0px 0px 1px;" readOnly></input>               
          </td>
          
         </tr>
      </table>
   <table width="100%">
    <tr>
          <td align="center"><a href="javascript:void(0)" onclick="saveSetings()" id="sbtn-save" icon="icon-save">保存</a> </td>
          </tr>
   </table>
  
</div> 
<!--  <div region="south" fit="true" border="false" style="overflow-Y: auto; overflow-X:hidden;height:20px"></div>    -->
  
 <div id="beiZhuWin" class="easyui-window" title="备注" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false" style="width:900px;height:400px;padding:10px;">
		 <div id="beizhuDiv" class="easyui-layout" fit="true"> 
		 <div id="beizhuDivnr" region="center"  style="overflow:auto;">
	<!--  <textarea rows="12" cols="57" id="beizhutext"></textarea>   -->	 
         </div>
         <div region="south" style="height:40px;">
         <table cellspacing="0" cellpadding="0" style="width:100%;">
           <tr>
               <td align="right" colspan="55">
               <a href="javascript:void(0)" onclick="bzsave()" id="bzsbtn-save" icon="icon-save">保存</a>
               </td>
           </tr>
        </table>
        </div>
      </div>  
 </div>
 
  <div id="queryWin" class="easyui-window" title="查询二维码信息" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false" style="width:500px;height:300px;padding:10px;">
		 <div  class="easyui-layout" fit="true"> 
		 <div  region="north"  style="overflow:auto;height:60px;">
		 <table> 
          <tr>      
		 <td align="right" nowrap>电梯编号：</td>
		 <td nowrap><input id="qregistNumber" name="qregistNumber" size="20" class="easyui-validatebox"></input></td>
		 <td colspan="2"><a href="#" class="easyui-linkbutton" icon="icon-search" onclick="queryRegistNumber()">查询</a></td>  
		 </tr> 
		 </table> 
         </div>
         <div region="center" style="height:40px;">
         <table>
           <tr>
           <td align="right">地址:</td>
           <td><input id="qaddress" name="qaddress"  readonly="readonly" style="width:300px;border-top: 0px;border-left: 0px;border-right: 0px; "></input></td>
           </tr>
            <tr>
           <td align="right">行政区划:</td>
           <td><input id="qarea" name="qarea"  readonly="readonly" style="width:300px;border-top: 0px;border-left: 0px;border-right: 0px;"></input></td>
           </tr>
            <tr>
           <td align="right">楼盘:</td>
           <td><input id="qbuildingName" name="qbuildingName"   readonly="readonly" style="width:300px;border-top: 0px;border-left: 0px;border-right: 0px;"></input></td>
           </tr>
             <tr>
           <td align="right">注册代码:</td>
           <td><input id="qregistCode" name="qregistCode"  readonly="readonly" style="width:300px;border-top: 0px;border-left: 0px;border-right: 0px; "></input></td>
           </tr>
        </table>
        </div>
      </div>  
 </div>
  
  

</body>
</html>