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
	form.url='/tcweb/elevator/ywSysSetingstjy';

	$('#sbtn-save').linkbutton();
	$('#bzsbtn-save').linkbutton();

//	$('#myform').form({onLoadSuccess:sjhjtotal});  
    $('#myform').form({onLoadSuccess:bzsjhjtotal}); 
    

	$('#hiddentr').hide();

	/*
	 $('#ratingDate2').datebox({
	       //显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
	       onShowPanel: function () {
	          //触发click事件弹出月份层
	          span.trigger('click'); 
	          if (!tds)
	            //延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
	            setTimeout(function() { 
	                tds = p.find('div.calendar-menu-month-inner td');
	                tds.click(function(e) {
	                   //禁止冒泡执行easyui给月份绑定的事件
	                   e.stopPropagation(); 
	                   //得到年份
	                   var year = /\d{4}/.exec(span.html())[0] ,
	                   //月份
	                   //之前是这样的month = parseInt($(this).attr('abbr'), 10) + 1; 
	                   month = parseInt($(this).attr('abbr'), 10);  

	         //隐藏日期对象                     
	         $('#ratingDate2').datebox('hidePanel') 
	           //设置日期的值
	           .datebox('setValue', year + '-' + month); 
	                        });
	                    }, 0);
	            },
	            //配置parser，返回选择的日期
	            parser: function (s) {
	                if (!s) return new Date();
	                var arr = s.split('-');
	                return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
	            },
	            //配置formatter，只返回年月 之前是这样的d.getFullYear() + '-' +(d.getMonth()); 
	            formatter: function (d) { 
	                var currentMonth = (d.getMonth()+1);
	                var currentMonthStr = currentMonth < 10 ? ('0' + currentMonth) : (currentMonth + '');
	                return d.getFullYear() + '-' + currentMonthStr; 
	            }
	        });

	        //日期选择对象
	        var p = $('#ratingDate2').datebox('panel'), 
	        //日期选择对象中月份
	        tds = false, 
	        //显示月份层的触发控件
	        span = p.find('span.calendar-text'); 
	        var curr_time = new Date();

	        //设置前当月
	        $("#ratingDate2").datebox("setValue", myformatter(curr_time));
	       */
	       $("#ratingDate2").val(myformatter(new Date(), "yyyy-MM"));  //设置查询条件的默认时间

	    $('#regularInspectionTimes').numberspinner({    
			    "onChange": function changePer(newValue,oldValue){
			   
				var regularInspectionjc =$('#regularInspectionjc').attr("value");
			    $('#regularInspectionsj').attr("value",regularInspectionjc-2*newValue);
			    sjhjtotal();
			    }
		    }); 
	  
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
	    		  //  	 query();
	    		        }	     
	    			      
	    		});   
	 
   //合计基础总分
	hjtotal();
	//初始化实际得分
	initial();
	
	
//	form.form('load', '/tcweb/elevator/sysYwcqSetingsEdit/'); 

	
    /*
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
      */

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


function regularInspectionValue(){
	var regularInspection=$('#regularInspection option:selected').val();
	var regularInspectionjc =$('#regularInspectionjc').attr("value");
	if(regularInspection == 1 ){
		$('#regularInspectionTimes').numberspinner('enable');
		$('#regularInspectionsj').attr("value",regularInspectionjc-2*$('#regularInspectionTimes').numberspinner('getValue'));
		}
	else{
		$('#regularInspectionTimes').numberspinner('setValue',0);     
		$('#regularInspectionTimes').numberspinner('disable');
		$('#regularInspectionsj').attr("value",regularInspectionjc);
		
		}
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


//初始化实际总分
function initial(){

	var regularInspectionTimes=$('#regularInspectionTimes').numberspinner('getValue');
	var regularInspectionsj =$('#regularInspectionjc').attr("value")-2*regularInspectionTimes;
	$('#regularInspectionsj').attr("value",regularInspectionsj);
	
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

	var sjhjtotal = 0;
	sjhjtotal = parseInt(regularInspectionsj)+parseInt(inspectElevatorsj)+parseInt(acceptInspElevatorsj)+parseInt(maintenSceneInfosj);
	
	$('#sjhjtotal').attr("value",sjhjtotal);
	$('#sjhjtotal').hide();
	sjhjjjtotal();   //合计实际加减分
}

//合计基础总分
function hjtotal(){
	var regularInspectionjc =$('#regularInspectionjc').attr("value");
	var inspectElevatorjc =$('#inspectElevatorjc').attr("value");
	var acceptInspElevatorjc =$('#acceptInspElevatorjc').attr("value");
	var maintenSceneInfojc =$('#maintenSceneInfojc').attr("value");
	
	var hjtotal = 0;
	
	hjtotal = parseInt(regularInspectionjc)+parseInt(inspectElevatorjc)+parseInt(acceptInspElevatorjc)+parseInt(maintenSceneInfojc);

	$('#hjtotal').attr("value",hjtotal);
	$('#hjtotal').hide();
}

function bzsjhjtotal(){
	
    initialBzCount();    //初始画备注的记录条数
	 sjhjtotal();   //合计实际总分
}

function initialBzCount(){
	var regularInspectionTimesv = $('#regularInspectionTimes').numberspinner('getValue');
	var vinspectElevatorTimes = $('#inspectElevatorTimes').numberspinner('getValue');
	var vinspectElevatorTimes2 = $('#inspectElevatorTimes2').numberspinner('getValue');
	var inspectElevatorTimesv = (parseInt(vinspectElevatorTimes)+parseInt(vinspectElevatorTimes2));
	var acceptInspElevatorTimesv = $('#acceptInspElevatorTimes').numberspinner('getValue');

	var maintenSceneInfoTimesv = $('#maintenSceneInfoTimes').numberspinner('getValue');
	
	 regularInspectionBzCount = regularInspectionTimesv;
	 inspectElevatorBzCount = inspectElevatorTimesv;
	 acceptInspElevatorBzCount = acceptInspElevatorTimesv;
	 maintenSceneInfoBzCount = maintenSceneInfoTimesv;

	 inspectElevatorTimesBzCount = vinspectElevatorTimes;
	 inspectElevatorTimes2BzCount = vinspectElevatorTimes2;
}

//合计实际总分
function sjhjtotal(){ 

	var regularInspectionsj =$('#regularInspectionsj').attr("value");
	var inspectElevatorsj =$('#inspectElevatorsj').attr("value");
	var acceptInspElevatorsj =$('#acceptInspElevatorsj').attr("value");
	var maintenSceneInfosj =$('#maintenSceneInfosj').attr("value");
	
	var sjhjtotal = 0;
	
	sjhjtotal =parseInt(regularInspectionsj)+parseInt(inspectElevatorsj)+parseInt(acceptInspElevatorsj)+parseInt(maintenSceneInfosj);
    
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
	var regularInspectionTimesv = $('#regularInspectionTimes').numberspinner('getValue');
	var vinspectElevatorTimes = $('#inspectElevatorTimes').numberspinner('getValue');
	var vinspectElevatorTimes2 = $('#inspectElevatorTimes2').numberspinner('getValue');
	var inspectElevatorTimesv = (parseInt(vinspectElevatorTimes)+parseInt(vinspectElevatorTimes2));
	var acceptInspElevatorTimesv = $('#acceptInspElevatorTimes').numberspinner('getValue');

	var maintenSceneInfoTimesv = $('#maintenSceneInfoTimes').numberspinner('getValue');

	if(regularInspectionBzCount != regularInspectionTimesv){
		$.messager.alert('操作失败', '电梯定期检验情况备注记录数与考评不一致，请修改', 'error');
		return 0;
		}
	
	if(inspectElevatorBzCount != inspectElevatorTimesv){
		$.messager.alert('操作失败', '在用电梯监督抽查情况备注记录数与考评不一致，请修改', 'error');
		return 0;
		}

	if(inspectElevatorTimesBzCount != vinspectElevatorTimes){
		$.messager.alert('操作失败', '在用电梯监督抽查情况(严重隐患 )备注记录数与考评不一致，请修改', 'error');
		return 0;
		}

	if(inspectElevatorTimes2BzCount != vinspectElevatorTimes2){
		$.messager.alert('操作失败', '在用电梯监督抽查情况(回路短接 )备注记录数与考评不一致，请修改', 'error');
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
	
	//var ratingDate2 =$('#ratingDate2').datebox("getValue");
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
 
//    $('#myform').form.url='/tcweb/elevator/ywSysSetingsqzj?ywCompanyID='+ywCompanyId+'&ratingDate='+ratingDate;
     $('#myform').form.url='/tcweb/elevator/ywSysSetingstjy?ywCompanyID='+ywCompanyId+'&ratingDate='+ratingDate2;
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
  //   var ratingDate = $('#ratingDate2').datebox("getValue");  
    var ratingDate = $('#ratingDate2').attr("value"); 
    $('#ratingDate').attr("value",ratingDate);
	$('#myform').form('load', '/tcweb/elevator/ywsyssetings2ByComIdRatDate/?ywCompanyID='+ywCompanyID+'&ratingDate='+ratingDate);
	
}



var beizhuid ="";
function addBeiZhu(id){ 
    beizhuid = id;
    $('#beiZhuWin').window('open');

    if(beizhuid=="regularInspectionButton") { 
    	 $("#beiZhuWin").width(800).height(400);   //恢复备注窗口的初始画大小，免得resize时候根据备注窗口计算出来的偏移量是变化的
    	  var dtop = ($(window).height() - $('#beiZhuWin').height())/2;  
          var dleft = ($(window).width() - $('#beiZhuWin').width())/2;    
          var scrollTop = $(document).scrollTop();      
          var scrollLeft = $(document).scrollLeft(); 
          dtop = dtop+ scrollTop;                      
          dleft =dleft + scrollLeft; 
    	 $('#beiZhuWin').window('resize',{
	         width: 800,
	         height: 400,
	    	 top:dtop,
	    	 left:dleft
	      });
	      
		var data;
	    if($('#regularInspectionbz').attr("value")==""){  
	    	data={'list': [
	   	    	   	    {
	    			'rrg': '',
	 	            'rt': '',
	 	            'rde': '',
	 	            'rr':''  
		           }]};
			   }
		  else{ 
		     data = JSON.parse($('#regularInspectionbz').attr("value"));   
		   }       
	
	        //获取模板
	        jsRenderTpl = $.templates('#regularInspectionTemp'),
	        //末班与数据结合
	        finalTpl = jsRenderTpl(data);

	    $('#beizhuDivnr').html(finalTpl);
	//	$('#beizhutext').attr("value",$('#regularInspectionbz').attr("value")); 
		}
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
	//	$('#beizhutext').attr("value",$('#acceptInspElevatorbz').attr("value"));
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


var regularInspectionBzCount = 0;
var inspectElevatorBzCount = 0;
var acceptInspElevatorBzCount = 0;
var maintenSceneInfoBzCount =0;

var inspectElevatorTimesBzCount = 0;   //(在用电梯监督抽查情况 0:严重隐患 1:回路短接)
var inspectElevatorTimes2BzCount = 0;   //(在用电梯监督抽查情况 0:严重隐患 1:回路短接)

//验证备注数和考评扣分次数是否一致
function kpCountValidate(itemName){  
	if(itemName == "regularInspectionButton"){
        var v1 = $('#regularInspectionTimes').numberspinner('getValue');   
        var v = (parseInt(v1));
        var trcount = $("#myTable").find("tr").length -1 ;    
        if(v == 0){
        	regularInspectionBzCount =trcount -1;
        	trcount =trcount-1;
        	return 2;     //一致，需要通过      	
            }
       
           if(v != trcount)
                 return 0;       //不一致
            else{ 
        	    regularInspectionBzCount =trcount;
                 return 1;       //一致
             }
       
       }

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

    
}

function registNumberValidate(itemName){
	var v = 0;
	if(itemName == "regularInspectionButton"){
     var v1 = $('#regularInspectionTimes').numberspinner('getValue');   
     v = (parseInt(v1));
	}

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
	 
     if(v>0){  
	 var tl = $("#myTable").find("tr").length ; 
	 var reg =/^\d{6}$/; 
	 
	 for(var i =1;i < tl;i++ ){
	 var v = $("#myTable tr").eq(i).find("input:first").val();

	 if(itemName == "regularInspectionButton"){
	 if(!reg.test(v)){	
		 return 0;
		 }
	
	 var vtime =$("#myTable tr").eq(i).children().find(".Wdate").val();  
	 if("" == vtime){
		 return 2;
		 }
	 var vaddress =$("#myTable tr").eq(i).children().find("#rde").val(); 
	 if("" == vaddress){    //地址为空
		 return 3;
		 }
	 var virea =$("#myTable tr").eq(i).children().find("#rr").val(); 
	 if("" == virea){    //原因为空
		 return 4;
		 }
	 }

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
	 
	 }

     }
	return 1;	
}



function bzsave(){   
//	var beizhutext = $('#beizhutext').attr("value"); 
    
	 var  bz =""; 

	 if(beizhuid=="regularInspectionButton"){
		 bz =TableToJson("myTable");
		 if(checkRepeatitem(bz)>0){
			   $.messager.alert('操作失败','存在相同备注记录','error');
	             return; 
			   }

		 if(kpCountValidate("regularInspectionButton") == 0){
	    	  $.messager.alert('操作失败','备注记录条数和考评记录数不一致,请修改','error');
	          return; 
	         }

		 if(kpCountValidate("regularInspectionButton") == 2){
			  bz ="";
	         }
        
         
        /*
		 if(registNumberValidate("regularInspectionButton") == 0){
	 		 $.messager.alert('操作失败','二维码编号只能为6位数字','error');
	 	       return ;
	 	        } */

		  var rv = registNumberValidate("regularInspectionButton");
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
	 	        } */
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
    
//  var  beizhutext = JSON.stringify($("#bzform").serializeObject());   //json字符串   
  
  
 // var len =beizhutext.length;
  var bzlen=bz.length;
  
	 if(beizhuid=="regularInspectionButton"){
		 if(bzlen >=3000){
			   $.messager.alert('操作失败','备注内容超出最大字符长度限制，请精简备注内容','error');
			   return;
			   }
		$('#regularInspectionbz').attr("value",bz); 
		//	 if(beizhutext != "") 
		       if(bz != "")      
				    $("#regularInspectionButton").attr('src','../../images/youbeizhu.png'); 
				   else
					$("#regularInspectionButton").attr('src','../../images/beizhu.png');
		}
		   
	
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
//	 if(beizhutext != "") 
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
//	 if(beizhutext != "") 
     if(bz != "")     
			    $("#maintenSceneInfoButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#maintenSceneInfoButton").attr('src','../../images/beizhu.png');
	}
	
	
	 $('#beiZhuWin').window('close');
}

function  bzImg(){
	
	 <% if (role ==16 || role ==17) { %>    
	 if($('#regularInspectionbz').attr("value") != ""){
  	   $("#regularInspectionButton").attr('src','../../images/youbeizhu.png'); 
	 }
     else{   
  	   $("#regularInspectionButton").attr('src','../../images/beizhu.png');
     }
	   
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
	  
	<% } %>

}

//var oId = 1; 
function addRow(){ 
   var tr = $("#myTable tr").eq(1).clone();   
   tr.appendTo("#myTable");  
 //  oId =oId+1;
   var lastTr =$("#myTable tr").eq($("#myTable tr").length - 1); 
   lastTr.find("input").val("");
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
        r = r.substring(0, r.length - 1);
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
        r = r.substring(0, r.length - 1)
        r += "},";
        txt += r;
    }

    txt = txt.substring(0, txt.length - 1);

    txt += "]}";

    return txt; 

}
</script>
<!-- 模板 -->
<script type="text/x-jsrender" id="regularInspectionTemp">
    <table id="myTable">
  <tr><td><a href="#" name="addRow" class="bztoolbutton" onclick="addRow()">添加</a><a href="#" name="deleteRow" class="bztoolbutton" onclick="deleteRow()">删除</a><a href="#" name="queryRow"  class='bztoolbutton' onclick="queryRow()">查询二维码</a></td></tr>
    {{for list}}
    <tr>
       <td>二维码编号: <input type="text" id="mrg" name="rrg" value="{{:rrg}}"></input></td>
       <td>时间: <input type="text" id="rt" name="rt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:rt}}"></input></td>
       <td>地点: <input type="text" id="rde" name="rde" value="{{:rde}}"></input></td>
       <td>原因: <input type="text" id="rr" name="rr" value="{{:rr}}"></input></td>
    </tr>
    {{/for}}
</table>
</script>
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
        <td><input id="ywCompanyIdinfo" style="width:252px;background-color:#87CEEB;"></input>
        <input type ="hidden" id="ywCompanyIdinfo2"></input></td>
        <td nowrap>时间：</td>
        <td>
      <!-- <select id="ratingDate2"  class="easyui-datebox" name="ratingDate2" style="width:152px;" data-options="editable:false"></select> -->
         <input type="text" id="ratingDate2" name="ratingDate2" onfocus="WdatePicker({dateFmt:'yyyy-MM', onpicked:ywcompanySelectRefresh})" class="Wdate" ></input>
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
            <td align="center" style="background-color:#F0FFFF">电梯定期检验情况</td>  
            <td align="center">合格</td> 
            <td align="center"><input id="regularInspectionjc"  type="text"  name="regularInspectionjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left"><input id="regularInspectionTimes" name="regularInspectionTimes" class="easyui-numberspinner" style="width:50px;" value="0" data-options="min:0,editable:false"></input>台</td>
            <td align="center">
            <input id="regularInspectionbz"  type="hidden" class="easyui-validatebox" name="regularInspectionbz" style="width:152px;text-align:center;"></input>
          <!--  <input type="button" id="regularInspectionButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->   
            <img src="../../images/beizhu.png" id="regularInspectionButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">一次性检验不合格&nbsp;&nbsp;<b>-2分/台</b> </td>  
            <td align="center"><input id="regularInspectionsj"  type="text"  name="regularInspectionsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
           
        </tr>
         <tr> 
            <td align="center" style="background-color:#F0FFFF">在用电梯监督抽查情况</td>  
            <td align="center">合格</td> 
            <td align="center"><input id="inspectElevatorjc"  type="text"  name="inspectElevatorjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left">
            <input id="inspectElevatorTimes" name="inspectElevatorTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input> 严重隐患&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="inspectElevatorTimes2" name="inspectElevatorTimes2" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>回路短接</td>
            <td align="center">
            <input id="inspectElevatorbz" type="hidden"  class="easyui-validatebox" name="inspectElevatorbz" style="width:152px;text-align:center;"></input>
          <!--     <input type="button" id="inspectElevatorButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->
           <img src="../../images/beizhu.png" id="inspectElevatorButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">定期检验中发现严重隐患，&nbsp;&nbsp;<b>-5分/台</b><br>发现安全回路短接，&nbsp;&nbsp;<b>-20分/台</b> </td>  
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
  
 <div id="beiZhuWin" class="easyui-window" title="备注" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false" style="width:400px;height:400px;padding:10px;">
		 <div id="beizhuDiv" class="easyui-layout" fit="true"> 
		 <div id="beizhuDivnr" region="center"  style="overflow:auto;">
	<!--  	 <textarea rows="12" cols="57" id="beizhutext"></textarea>  -->
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