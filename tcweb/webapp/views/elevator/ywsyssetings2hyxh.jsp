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
	form.url='/tcweb/elevator/ywSysSetingshyxh';

	$('#sbtn-save').linkbutton();
	$('#bzsbtn-save').linkbutton();

//	$('#myform').form({onLoadSuccess:sjhjtotal}); 
    $('#myform').form({onLoadSuccess:bzsjhjtotal}); 
	form2 =$("form[name='bzform']");
    

	$('#hiddentr').hide();


	$("#ratingDate2").val(myformatter(new Date(), "yyyy-MM"));  //设置查询条件的默认时间

	   $('#enterpriseRecord').combobox({
       	onChange: function (n,o) {
            var enterpriseRecord=$('#enterpriseRecord').combobox('getValue');  
       	 var enterpriseRecordjc = $('#enterpriseRecordjc').attr("value");
       	 if(enterpriseRecord == 1)
       		 $('#enterpriseRecordsj').attr("value",parseInt(enterpriseRecordjc)-10);
       	 else
       		 $('#enterpriseRecordsj').attr("value",enterpriseRecordjc);
       	 sjhjtotal();
       }
	        });

 $('#telOnDutyunattendedTimes').numberspinner({    
	    "onChange": function changePer(newValue,oldValue){
	    
	    var telOnDutyunattendedjc =$('#telOnDutyunattendedjc').attr("value");
	
	    $('#telOnDutyunattendedsj').attr("value",telOnDutyunattendedjc-5*newValue);
	    sjhjtotal();
	    }
   });  

 
 $('#enterpriseChangeTimes').numberspinner({    
	    "onChange": function changePer(newValue,oldValue){
	    
	    var enterpriseChangejc =$('#enterpriseChangejc').attr("value");
	    $('#enterpriseChangesj').attr("value",enterpriseChangejc-10*newValue);
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

function  officeSpaceValue(){
	var officeSpace = $('#officeSpace').attr("value");
	var officeSpacejc = $('#officeSpacejc').attr("value");
	if(officeSpace >= 300)
		$('#officeSpacesj').attr("value", parseInt(officeSpacejc)+10);
	else if(officeSpace >= 200)
		$('#officeSpacesj').attr("value", parseInt(officeSpacejc)+5);
	else if(officeSpace < 120)
		$('#officeSpacesj').attr("value",officeSpacejc-5);
	else
		$('#officeSpacesj').attr("value",officeSpacejc);

	sjhjtotal();
}

function fixedTelOnDutyValue(){
	
//	var pattern =/(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
	
//	var reg=/^(\d{3,4})-(\d{7,8})/;  //验证电话号码
//	var regph=/[1][3-9][0-9]{9,9}/;   //验证手机号码
    var reg =/^(1[3,5,8,7]{1}[\d]{9})|(((400)-(\d{3})-(\d{4}))|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{3,7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/;
    var v = $("#fixedTelOnDuty").attr("value");
    if(v != ''){
       var re = v.match(reg);
  //     var re2 =v.match(regph);
  //     if(re == null && re2 == null){
          if(re == null){
    //	   $.messager.alert('格式不正确','请输入固定电话形如：028-8888888<br>或者手机号码','error');
           $.messager.alert('格式不正确','请输入固定电话或者手机号码,固定电话形如:028-XXXXXXXX<BR>或:400-XXX-XXXX','error');
           $("#fixedTelOnDuty").attr("value",'');
           $("#fixedTelOnDuty").focus();
           return;
           }
	      } 
	
	var fixedTelOnDuty=$('#fixedTelOnDuty').attr("value");
	var fixedTelOnDutyjc=$('#fixedTelOnDutyjc').attr("value");
	if(fixedTelOnDuty == "")
		 $('#fixedTelOnDutysj').attr("value",fixedTelOnDutyjc-10);
	  else
		 $('#fixedTelOnDutysj').attr("value",fixedTelOnDutyjc);
	sjhjtotal();
    };

    function telOnDutyunattendedValue(){  
    	var telOnDutyunattended=$('#telOnDutyunattended option:selected').val();
    	var telOnDutyunattendedjc =$('#telOnDutyunattendedjc').attr("value");
    	if(telOnDutyunattended == 1){ 
    		 $('#telOnDutyunattendedTimes').numberspinner('enable');
    		 $('#telOnDutyunattendedsj').attr("value",telOnDutyunattendedjc-5*$('#telOnDutyunattendedTimes').numberspinner('getValue'));		
    	}
      else{
    	$('#telOnDutyunattendedTimes').numberspinner('setValue',0);     
    	$('#telOnDutyunattendedTimes').numberspinner('disable');
    	$('#telOnDutyunattendedsj').attr("value",telOnDutyunattendedjc);
    	}
    	sjhjtotal();
    }

    function  enterpriseChangeValue(){
    	var enterpriseChange=$('#enterpriseChange option:selected').val();
    	var enterpriseChangejc =$('#enterpriseChangejc').attr("value");
    	if(enterpriseChange == 1){
    		 $('#enterpriseChangeTimes').numberspinner('enable');
    		 $('#enterpriseChangesj').attr("value",enterpriseChangejc-10*$('#enterpriseChangeTimes').numberspinner('getValue'));
    		}
    	else{
    		$('#enterpriseChangeTimes').numberspinner('setValue',0);     
    		$('#enterpriseChangeTimes').numberspinner('disable');
    		 $('#enterpriseChangesj').attr("value",enterpriseChangejc);
    		}
    }

    function enterpriseRecordValue(){  
    	var enterpriseRecord=$('#enterpriseRecord option:selected').val();
    	var enterpriseRecordjc =$('#enterpriseRecordjc').attr("value");
    	if(enterpriseRecord == 1){
    		 $('#enterpriseRecordsj').attr("value",enterpriseRecordjc-10);
    		}
    	else{
    		 $('#enterpriseRecordsj').attr("value",enterpriseRecordjc);
    		}
    }



//初始化实际总分
function initial(){

	var officeSpacesj =$('#officeSpacejc').attr("value");
	var officeSpace =$('#officeSpace').attr("value");
	if(officeSpace == "")
		officeSpace = 0;
	if(officeSpace >= 200)
		officeSpacesj =parseInt(officeSpacesj) +5;
	if(officeSpace < 120)
		officeSpacesj =parseInt(officeSpacesj) -5;
	
	$('#officeSpacesj').attr("value",officeSpacesj);

	/*
	var headQuarterssj =$('#headQuartersjc').attr("value");
    var headQuarters=$('#headQuarters').combobox('getValue'); 
	if(headQuarters == 0){ //成都本地
     headQuarterssj =parseInt(headQuarterssj)+10;
	}
	
	
    $('#headQuarterssj').attr("value",headQuarterssj);

    */
		
	
	var fixedTelOnDutysj =$('#fixedTelOnDutyjc').attr("value");
	var fixedTelOnDuty =$('#fixedTelOnDuty').attr("value");
	if(fixedTelOnDuty == ""){
		fixedTelOnDutysj =fixedTelOnDutysj-10;
		}
	$('#fixedTelOnDutysj').attr("value",fixedTelOnDutysj);


	var telOnDutyunattendedTimes =$('#telOnDutyunattendedTimes').numberspinner('getValue');
	var telOnDutyunattendedsj =$('#telOnDutyunattendedjc').attr("value")-5*telOnDutyunattendedTimes;
	$('#telOnDutyunattendedsj').attr("value",telOnDutyunattendedsj);

	var enterpriseChangeTimes=$('#enterpriseChangeTimes').numberspinner('getValue');
	var enterpriseChangesj =$('#enterpriseChangejc').attr("value")-10*enterpriseChangeTimes;;
	$('#enterpriseChangesj').attr("value",enterpriseChangesj);


	var enterpriseRecord=$('#enterpriseRecord').attr("value");
	var enterpriseRecordsj =$('#enterpriseRecordjc').attr("value")-10*enterpriseRecord;
	
	$('#enterpriseRecordsj').attr("value",enterpriseRecordsj);


	var sjhjtotal = 0;
	sjhjtotal = parseInt(officeSpacesj)+parseInt(fixedTelOnDutysj)+parseInt(telOnDutyunattendedsj)+parseInt(enterpriseChangesj)+parseInt(enterpriseRecordsj);
	
	$('#sjhjtotal').attr("value",sjhjtotal);
	$('#sjhjtotal').hide();
	sjhjjjtotal();   //合计实际加减分
}



//合计基础总分
function hjtotal(){
	var officeSpacejc =$('#officeSpacejc').attr("value");
	var fixedTelOnDutyjc =$('#fixedTelOnDutyjc').attr("value");
	var telOnDutyunattendedjc =$('#telOnDutyunattendedjc').attr("value");
	var enterpriseChangejc =$('#enterpriseChangejc').attr("value");
	var enterpriseRecordjc =$('#enterpriseRecordjc').attr("value");
	
	var hjtotal = 0;
	
	hjtotal = parseInt(officeSpacejc)+parseInt(fixedTelOnDutyjc)+parseInt(telOnDutyunattendedjc)+parseInt(enterpriseChangejc)+parseInt(enterpriseRecordjc);

	$('#hjtotal').attr("value",hjtotal);
	$('#hjtotal').hide();
}


function bzsjhjtotal(){
	
    initialBzCount();    //初始画备注的记录条数
	 sjhjtotal();   //合计实际总分
}

function initialBzCount(){
	var telOnDutyunattendedTimesv =$('#telOnDutyunattendedTimes').numberspinner('getValue');
	telOnDutyunattendedBzCount =telOnDutyunattendedTimesv;

	var enterpriseChangeTimesv =$('#enterpriseChangeTimes').numberspinner('getValue');
	if(enterpriseChangeTimesv > 0)
		enterpriseChangeBzCount =1;
	else
		enterpriseChangeBzCount =0;
}

//合计实际总分
function sjhjtotal(){ 

	var officeSpacesj =$('#officeSpacesj').attr("value");
	var fixedTelOnDutysj =$('#fixedTelOnDutysj').attr("value");
	var telOnDutyunattendedsj =$('#telOnDutyunattendedsj').attr("value");
	var enterpriseChangesj =$('#enterpriseChangesj').attr("value");
	var enterpriseRecordsj =$('#enterpriseRecordsj').attr("value");
	
	var sjhjtotal = 0;
	
	sjhjtotal =parseInt(officeSpacesj)+parseInt(fixedTelOnDutysj)+parseInt(telOnDutyunattendedsj)+parseInt(enterpriseChangesj)+parseInt(enterpriseRecordsj);
    
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
	var telOnDutyunattendedTimesv =$('#telOnDutyunattendedTimes').numberspinner('getValue');
	if(telOnDutyunattendedBzCount != telOnDutyunattendedTimesv){
		$.messager.alert('操作失败', '电话值守无人接听备注记录数与考评次数不一致，请修改', 'error');
		return 0;
		}

	var enterpriseChangeTimesv =$('#enterpriseChangeTimes').numberspinner('getValue');
	if(enterpriseChangeTimesv ==0 && enterpriseChangeBzCount == 1){
		$.messager.alert('操作失败', '维保企业变更为0,请清空备注保存', 'error');
		return 0;
		}
	if(enterpriseChangeTimesv >0 && enterpriseChangeBzCount == 0){
		$.messager.alert('操作失败', '维保企业有变更，请补全备注保存', 'error');
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
    $('#myform').form.url='/tcweb/elevator/ywSysSetingshyxh?ywCompanyID='+ywCompanyId+'&ratingDate='+ratingDate2;
	$('#myform').form('submit', {  
		url:form.url,
		onSubmit:function(){  
			return true;
		},

		success : function(data) {
			eval("data=" + "'" + data + "'");
		    if ("success" == data) {
	/*		$.messager.show( {
				title : '提示信息',
				timeout : 1000,
				msg : '操作成功，谢谢。'
			}); */
			$.messager.alert('操作成功', '保存成功！', 'info');
			$('#ywCompanyIdinfo').combobox('clear');  
			$('#ywCompanyIdinfo').combobox('reload'); 
		//	$('#myform').form('clear');
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

    if(beizhuid=="officeSpaceButton"){
    	 $("#beiZhuWin").width(400).height(600);   //恢复备注窗口的初始画大小，免得resize时候根据备注窗口计算出来的偏移量是变化的
   	     var dtop = ($(window).height() - $('#beiZhuWin').height())/2;  
         var dleft = ($(window).width() - $('#beiZhuWin').width())/2;    
         var scrollTop = $(document).scrollTop();      
         var scrollLeft = $(document).scrollLeft(); 
         dtop = dtop+ scrollTop;                      
         dleft =dleft + scrollLeft; 
        $('#beiZhuWin').window('resize',{
            width: 400,
            height: 600,
            top:dtop, 
            left:dleft
         });
   		var data;
   	//	$('#beizhutext').attr("value",$('#officeSpacebz').attr("value"));
   	   if($('#officeSpacebz').attr("value")==""){
   		    data = {
   		            'odre': '',
   		            'omj': '',
   		            'ofs':'',
   		            'odate':'',
   		            'omg':'',
   		            'imgName':''
   		        };
   		   }
   	   else{ 
   	     data = JSON.parse($('#officeSpacebz').attr("value")); 
   	    
   	   }
   	
   	        //获取模板
   	        jsRenderTpl = $.templates('#officeSpaceTemp');
   	        //末班与数据结合
   	        finalTpl = jsRenderTpl(data);

   	    $('#beizhuDivnr').html(finalTpl);  

   	    $("#ofs").val(data.ofs);
   	    readImg(data.imgName);

   	}

   	if(beizhuid=="fixedTelOnDutyButton"){
   		//	$('#beizhutext').attr("value",$('#fixedTelOnDutybz').attr("value"));
   		$("#beiZhuWin").width(400).height(200);   //恢复备注窗口的初始画大小，免得resize时候根据备注窗口计算出来的偏移量是变化的
   	     var dtop = ($(window).height() - $('#beiZhuWin').height())/2;  
         var dleft = ($(window).width() - $('#beiZhuWin').width())/2;    
         var scrollTop = $(document).scrollTop();      
         var scrollLeft = $(document).scrollLeft(); 
         dtop = dtop+ scrollTop;                      
         dleft =dleft + scrollLeft; 
   		  $('#beiZhuWin').window('resize',{
            width: 400,
            height: 200,
            top:dtop,   
            left:dleft
         });
   		var data;
   	    if($('#fixedTelOnDutybz').attr("value")==""){
   			    data = {
   			    		'fhc': '',
   			            'fht': ''
   			        };
   			   }
   		  else{ 
   		     data = JSON.parse($('#fixedTelOnDutybz').attr("value")); 
   		   }        
   	        //获取模板                                              
   	       jsRenderTpl = $.templates('#fixedTelOnDutyTemp'),
   	        //末班与数据结合
   	       finalTpl = jsRenderTpl(data);

   	    $('#beizhuDivnr').html(finalTpl);  
   		

   	}
   	if(beizhuid=="telOnDutyunattendedButton"){
//   		$('#beizhutext').attr("value",$('#telOnDutyunattendedbz').attr("value"));
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
   	    if($('#telOnDutyunattendedbz').attr("value")==""){
   	    	data={'list': [
   	    	   	    {
   	    			'tct': '',
   		            'tcte': ''        
   		            }
   		            ]};
   			   }
   		  else{ 
   		     data = JSON.parse($('#telOnDutyunattendedbz').attr("value")); 
   		   }        
   		
   	        //获取模板
   	        jsRenderTpl = $.templates('#telOnDutyunattendedTemp'),
   	        //末班与数据结合
   	        finalTpl = jsRenderTpl(data);

   	    $('#beizhuDivnr').html(finalTpl);  
   		
   	
   	}

   	
   	if(beizhuid=="enterpriseChangeButton"){
   		//	$('#beizhutext').attr("value",$('#enterpriseChangebz').attr("value"));
   		$("#beiZhuWin").width(400).height(200);   //恢复备注窗口的初始画大小，免得resize时候根据备注窗口计算出来的偏移量是变化的
   	     var dtop = ($(window).height() - $('#beiZhuWin').height())/2;  
         var dleft = ($(window).width() - $('#beiZhuWin').width())/2;    
         var scrollTop = $(document).scrollTop();      
         var scrollLeft = $(document).scrollLeft(); 
         dtop = dtop+ scrollTop;                      
         dleft =dleft + scrollLeft; 
   		  $('#beiZhuWin').window('resize',{
            width: 400,
            height: 200,
            top:dtop,   
            left:dleft
         });
   		var data;
   	    if($('#enterpriseChangebz').attr("value")==""){
   	    	data = {
   	    			'eon': '',
   	 	            'enn': ''
   		        };
   			   }
   		  else{ 
   		     data = JSON.parse($('#enterpriseChangebz').attr("value")); 
   		   }     
   		
   	        //获取模板
   	        jsRenderTpl = $.templates('#enterpriseChangeTemp'),
   	        //末班与数据结合
   	        finalTpl = jsRenderTpl(data);

   	    $('#beizhuDivnr').html(finalTpl);
   		

   		}
   	
   	if(beizhuid=="enterpriseRecordButton"){
//   		$('#beizhutext').attr("value",$('#enterpriseRecordbz').attr("value"));
            $("#beiZhuWin").width(400).height(600);   //恢复备注窗口的初始画大小，免得resize时候根据备注窗口计算出来的偏移量是变化的
   	        var dtop = ($(window).height() - $('#beiZhuWin').height())/2;  
            var dleft = ($(window).width() - $('#beiZhuWin').width())/2;    
            var scrollTop = $(document).scrollTop();      
            var scrollLeft = $(document).scrollLeft(); 
            dtop = dtop+ scrollTop;                      
            dleft =dleft + scrollLeft; 
            $('#beiZhuWin').window('resize',{
            width: 400,
            height: 600,
            top:dtop,   
            left:dleft
         });
   		var data;
   	    if($('#enterpriseRecordbz').attr("value")==""){
   	    	data = {
   	    			'ern': '',
   	    			'emg':'',
   	    			 'imgName':''
   	 	           
   		        };
   			   }
   		  else{ 
   		     data = JSON.parse($('#enterpriseRecordbz').attr("value")); 
   		   }     
   		
   	        //获取模板
   	        jsRenderTpl = $.templates('#enterpriseRecordTemp'),
   	        //末班与数据结合
   	        finalTpl = jsRenderTpl(data);

   	    $('#beizhuDivnr').html(finalTpl);
   	    readImg(data.imgName);
   	
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

var telOnDutyunattendedBzCount =0;
var enterpriseChangeBzCount =0;

//验证备注数和考评扣分次数是否一致
function kpCountValidate(itemName){
	 if(itemName == "telOnDutyunattendedButton"){
		 var v1 = $('#telOnDutyunattendedTimes').numberspinner('getValue'); 
		 var v = (parseInt(v1));
		 var trcount = $("#myTable").find("tr").length -1 ;
		 if(v == 0){
			    telOnDutyunattendedBzCount =trcount -1;
	        	trcount =trcount-1;
	        	return 2;     //一致，需要通过      	
	            }
		 
		 if(v != trcount)
             return 0;       //不一致
         else{ 
        	 telOnDutyunattendedBzCount =trcount;
             return 1;       //一致
         }
     }

	 if(itemName == "enterpriseChangeButton"){
		 var v1 = $('#enterpriseChangeTimes').numberspinner('getValue'); 
		 var v = (parseInt(v1));
		 if(v == 0){
			 $("#myTable tr input").val("");
			 enterpriseChangeBzCount = 0;
			 return 2; 
			 }
		 else{
			 enterpriseChangeBzCount = 1;
			 return 1;   //一致
			 }
		 }
    
}

function registNumberValidate(itemName){
	var v = 0;
	if(itemName == "telOnDutyunattendedButton"){
	     var v1 = $('#telOnDutyunattendedTimes').numberspinner('getValue');   
	     v = (parseInt(v1));
	}

	if(itemName == "enterpriseChangeButton"){
	     var v1 = $('#enterpriseChangeTimes').numberspinner('getValue');   
	     v = (parseInt(v1));
	}
	
	
	if(v>0){  
		var tl = $("#myTable").find("tr").length ; 
		for(var i =1;i < tl;i++ ){
			if(itemName == "telOnDutyunattendedButton"){ 
			 var vaddress =$("#myTable tr").eq(i).children().find("#tct").val(); 
			 if("" == vaddress){    //电话为空
				 return 3;
				 }

		     var vtime =$("#myTable tr").eq(i).children().find(".Wdate").val();  
		     if("" == vtime){
			  return 2;
		     }
		   }
			   
			if(itemName == "enterpriseChangeButton"){
				var vaddress =$("#myTable tr").eq(i-1).children().find("#eon").val();  
				 if("" == vaddress){    //变更前名称为空
					 return 2;
					 }
				 var vaddress2 =$("#myTable tr").eq(i).children().find("#enn").val(); 
				 if("" == vaddress2){    //变更后名称为空
					 return 3;
					 }
			}
			 
		}

	}
	return 1;
	
}

function bzsave(){   
var  bz =""; 
	
//	var beizhutext = $('#beizhutext').attr("value"); 
//  var beizhutext = $("#bzform").serializeObject();  //json对象
   if(beizhuid=="officeSpaceButton"){
	   imgSub("omg");
	   }

   if(beizhuid=="telOnDutyunattendedButton"){
	   bz =TableToJson("myTable");
	   if(checkRepeatitem(bz)>0){
		   $.messager.alert('操作失败','存在相同备注记录','error');
             return; 
		   }

	   if(kpCountValidate("telOnDutyunattendedButton") == 0){
	    	  $.messager.alert('操作失败','备注记录条数和考评记录次数不一致,请修改','error');
	          return; 
	         }

	   if(kpCountValidate("telOnDutyunattendedButton") == 2){
			  bz ="";
	         }
	   var rv = registNumberValidate("telOnDutyunattendedButton");
 	   if(rv == 3){
    	   $.messager.alert('操作失败','备注呼出电话不能为空','error');
 	       return ;
 	   }
       if(rv == 2){
    	   $.messager.alert('操作失败','备注拨打时间不能为空','error'); 
    	   return ;
       }
		   
	   }

   if(beizhuid=="enterpriseChangeButton"){
	   var rv = registNumberValidate("enterpriseChangeButton");   
	   if(rv == 2){
    	   $.messager.alert('操作失败','备注变更前名称不能为空','error'); 
    	   return ;
       } 
 	   if(rv == 3){
    	   $.messager.alert('操作失败','备注变更后名称不能为空','error');
 	       return ;
 	   }
       
	   }

   if(beizhuid=="enterpriseRecordButton"){
	   imgSub("emg");
	   }

    
  var beizhutext = JSON.stringify($("#bzform").serializeObject());   //json字符串   
  var len =beizhutext.length;
  var bzlen=bz.length;
  
  if(beizhuid=="officeSpaceButton"){
	  if(len >=500){
		   $.messager.alert('操作失败','备注内容超出最大字符长度限制，请精简备注内容','error');
		   return;
		   }
	   $('#officeSpacebz').attr("value",beizhutext);
	   if(beizhutext != "")  
	    $("#officeSpaceButton").attr('src','../../images/youbeizhu.png'); 
	   else
		$("#officeSpaceButton").attr('src','../../images/beizhu.png'); 	
	}

if(beizhuid=="fixedTelOnDutyButton"){
	 if(len >=200){
		   $.messager.alert('操作失败','备注内容超出最大字符长度限制，请精简备注内容','error');
		   return;
		   }
	$('#fixedTelOnDutybz').attr("value",beizhutext);  
	  if(beizhutext != "")  
		    $("#fixedTelOnDutyButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#fixedTelOnDutyButton").attr('src','../../images/beizhu.png'); 
}

if(beizhuid=="telOnDutyunattendedButton"){
	 if(bzlen >=3000){
		   $.messager.alert('操作失败','备注内容超出最大字符长度限制，请精简备注内容','error');
		   return;
		   } 
   $('#telOnDutyunattendedbz').attr("value",bz);
//	 if(beizhutext != "") 
   if(bz != "")    
	    $("#telOnDutyunattendedButton").attr('src','../../images/youbeizhu.png'); 
	   else
		$("#telOnDutyunattendedButton").attr('src','../../images/beizhu.png'); 
  
}



if(beizhuid=="enterpriseChangeButton"){
	if(len >=500){
		   $.messager.alert('操作失败','备注内容超出最大字符长度限制，请精简备注内容','error');
		   return;
		   }
	if(kpCountValidate("enterpriseChangeButton") ==  2)
		beizhutext = "";
	   
	$('#enterpriseChangebz').attr("value",beizhutext); 
	 if(beizhutext != "")  
		    $("#enterpriseChangeButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#enterpriseChangeButton").attr('src','../../images/beizhu.png');
}

if(beizhuid=="enterpriseRecordButton"){
	if(len >=500){
		   $.messager.alert('操作失败','备注内容超出最大字符长度限制，请精简备注内容','error');
		   return;
		   }
	$('#enterpriseRecordbz').attr("value",beizhutext); 
	 if(beizhutext != "")  
		    $("#enterpriseRecordButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#enterpriseRecordButton").attr('src','../../images/beizhu.png');
}

	
	 $('#beiZhuWin').window('close');
}

function  bzImg(){
	
	 if($('#officeSpacebz').attr("value") != "")  
		   $("#officeSpaceButton").attr('src','../../images/youbeizhu.png');
      else
         $("#officeSpaceButton").attr('src','../../images/beizhu.png'); 

	 if($('#telOnDutyunattendedbz').attr("value") != "")
	        $("#telOnDutyunattendedButton").attr('src','../../images/youbeizhu.png'); 
		else
			$("#telOnDutyunattendedButton").attr('src','../../images/beizhu.png'); 
	 
	    if($('#fixedTelOnDutybz').attr("value") != "" )
	    	$("#fixedTelOnDutyButton").attr('src','../../images/youbeizhu.png'); 
	    else
	    	$("#fixedTelOnDutyButton").attr('src','../../images/beizhu.png'); 

	   if($('#enterpriseChangebz').attr("value") != "")
		   $("#enterpriseChangeButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#enterpriseChangeButton").attr('src','../../images/beizhu.png'); 
	
	   if($('#enterpriseRecordbz').attr("value") != "")
		   $("#enterpriseRecordButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#enterpriseRecordButton").attr('src','../../images/beizhu.png');  

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

function imgSub(itemName){ 
    var timestamp = Date.parse(new Date());
        timestamp = timestamp / 1000;
        console.log("当前时间戳为：" + timestamp);
        
    var imgName=""; 
//      imgName="officeSpaceImg"+"/"+$('#ywCompanyIdinfo2').attr("value")+"/"+$('#ratingDate2').datebox("getValue")+"/"+'-'+timestamp;
//     imgName=itemName+"/"+$('#ywCompanyIdinfo2').attr("value")+"/"+$('#ratingDate2').datebox("getValue")+"/"+'-'+timestamp;
     imgName=itemName+"/"+$('#ywCompanyIdinfo2').attr("value")+"/"+$('#ratingDate2').attr("value")+"/"+'-'+timestamp;
     if($(":file").val()!=""){
        
        if($('#ywCompanyIdinfo2').attr("value") ==""){
    	$.messager.alert('操作失败', '没有选择维保单位，图片不能保存,请先选择维保单位', 'error');
    	return;
        }

  
    $('#imgName').attr("value",imgName); 
    form2.form('submit', {  
//		url:'/tcweb/elevator/ywsyssetingImgUpload?name=officeSpaceImg',
        url:'/tcweb/elevator/ywsyssetingImgUpload',
		onSubmit:function(){},
		success:function(data){
    		
    		}
    		
    }); 
    }

}  

function validate_img(ele){
	   
    var file = ele.value;      
    if(!/.(gif|jpg|jpeg|png|GIF|JPG|bmp)$/.test(file)){
      ele.value="";        
      $.messager.alert('操作失败','图片类型必须是.gif,jpeg,jpg,png,bmp中的一种','error');
           return false;
           
     }else{
        if(((ele.files[0].size).toFixed(2))>=(2*1024*1024)){
          ele.value="";         
          $.messager.alert('操作失败','请上传小于2M的图片','error');
                 return false;
         }
     }
          
}

function readImg(imgName){ 
	if(imgName != ""){   
		var companyImg="";
		var path='<%=request.getContextPath()%>'+'/servlet/ywRateCompanyImage.jpg?name='+imgName;  
		$("#companyImg").attr('src',path);  
	}
}
</script>
<!-- 模板 -->
<script type="text/x-jsrender" id="officeSpaceTemp">
   <table>
    <tr>   
       <td align="right">地址:</td>
       <td><input type="text" id="odre" name="odre" value="{{:odre}}"></input></td>  
    </tr>
    <tr>
        <td align="right">面积:</td>
        <td> <input type="text" id="omj" name="omj" value="{{:omj}}"></input></td>
    </tr>
    <tr>
        <td align="right">占用方式:</td>
        <td><select name="ofs" id="ofs" value="{{:ofs}}">  
                      <option value="0">租赁</option>  
                      <option value="1">购买</option>
                     </select>   
         </td>
    </tr>
    <tr>
        <td align="right">到期日期:</td>
        <td><input type="text" id="odate" name="odate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" value="{{:odate}}"></input> 
        </td>
    </tr>  
    <tr>
    <td align="right">图片:</td>
    <td><input id="omg" type="file" size="45" name="omg" onchange="Javascript:validate_img(this);">
    </td>
    </tr>
   <tr>
   <td><input id="imgName" type="hidden"  name="imgName" value="{{:imgName}}"/></td>
    <tr>
   <tr>
   <td colspan="2">
    <img src="" id="companyImg" height="300" width="300"/>
   </td>
   </tr>
</table>
</script>
<script type="text/x-jsrender" id="fixedTelOnDutyTemp">
    <table>
    <tr>
       <td align="right">呼出电话:</td> 
       <td><input type="text" id="fhc" name="fhc" value="{{:fhc}}"></td>
    </tr>
    <tr>
       <td align="right">拨打时间:</td> 
       <td><input type="text" id="fht" name="fht" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:fht}}"></td>
   </tr> 
</table>  
</script>
<script type="text/x-jsrender" id="telOnDutyunattendedTemp">
    <table  id="myTable">
    <tr><td><a href="#" name="addRow" class="bztoolbutton" onclick="addRow()">添加</a><a href="#" name="deleteRow" class="bztoolbutton" onclick="deleteRow()">删除</a></td></tr>
    {{for list}}
     <tr>
       <td align="right">呼出电话:<input type="text" id="tct" name="tct" value="{{:tct}}"></td>
       <td align="right">拨打时间:<input type="text" id="tcte" name="tcte" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:tcte}}"></td>
    </tr>
   {{/for}}
</table>  
</script>    
<script type="text/x-jsrender" id="enterpriseChangeTemp">
    <table id="myTable">
    <tr>
       <td>变更前名称:</td> 
       <td><input type="text" id="eon" name="eon" value="{{:eon}}"></td>
    </tr>
    <tr>
       <td>变更后名称:</td> 
       <td> <input type="text" id="enn" name="enn" value="{{:enn}}"></td>
   </tr> 
</table>
</script>
<script type="text/x-jsrender" id="enterpriseRecordTemp">
    <table>
    <tr>
       <td>备案编号:</td>
    <td><input type="text" id="ern" name="ern" value="{{:ern}}"></td>
    </tr>
    <tr>
    <td align="right">图片:</td>
    <td><input id="emg" type="file" size="45" name="emg" onchange="Javascript:validate_img(this);">
    </td>
    </tr>
   <tr>
   <td><input id="imgName" type="hidden"  name="imgName" value="{{:imgName}}"/></td>
    <tr>
   <tr>
   <td colspan="2">
    <img src="" id="companyImg" height="300" width="300"/>
   </td>
   </tr>
    </tr>
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
            <td  rowspan="7" align="center" style="background-color:#F0FFFF">基本条件</td>  
            <td align="center" style="background-color:#F0FFFF">办公面积</td>  
            <td align="center">120㎡</td> 
            <td align="center"><input id="officeSpacejc"  type="text"  name="officeSpacejc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left"><input id="officeSpace"  type="text" class="easyui-validatebox" name="officeSpace" style="width:102px;text-align:center;" value="0" onchange="officeSpaceValue()"></input>㎡  </td>
            <td align="center">
            <input id="officeSpacebz"  type="hidden" class="easyui-validatebox" name="officeSpacebz" style="width:152px;text-align:center;"></input>
        <!--     <input type="button" id="officeSpaceButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> --> 
            <img src="../../images/beizhu.png" id="officeSpaceButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">≥300 ㎡ &nbsp;&nbsp;<b>+10分</b>&nbsp;&nbsp;&nbsp;;&nbsp;&nbsp;&nbsp;≥200 ㎡ &nbsp;&nbsp;<b>+5分</b>&nbsp;&nbsp;&nbsp;;&nbsp;&nbsp;&nbsp;<120 ㎡ &nbsp;&nbsp;<b>-5分</b>" </td>  
            <td align="center"><input id="officeSpacesj"  type="text"  name="officeSpacesj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
    
        </tr> 
           <tr>  
           
            <td align="center" style="background-color:#F0FFFF">值班固定电话</td>  
            <td align="center">有</td>
            <td align="center"><input id="fixedTelOnDutyjc"  type="text"  name="fixedTelOnDutyjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td align="left"><input id="fixedTelOnDuty"  type="text"  name="fixedTelOnDuty" style="width:102px;" onchange="fixedTelOnDutyValue()"></input></td>
            <td align="center">
            <input id="fixedTelOnDutybz"   type="hidden" class="easyui-validatebox" name="fixedTelOnDutybz" style="width:152px;text-align:center;"></input>
          <!--  <input type="button" id="fixedTelOnDutyButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  -->  
             <img src="../../images/beizhu.png" id="fixedTelOnDutyButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">无固定电话，&nbsp;&nbsp;<b>-10分</b> </td>  
            <td align="center"><input id="fixedTelOnDutysj"  type="text"  name="fixedTelOnDutysj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
          <tr>  
            <td align="center" style="background-color:#F0FFFF">电话值守<br>无人接听</td>  
            <td align="center">值守</td>
            <td align="center"><input id="telOnDutyunattendedjc"  type="text"  name="telOnDutyunattendedjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td align="left"><input id="telOnDutyunattendedTimes" name="telOnDutyunattendedTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>次</td>
           <td align="center">
           <input id="telOnDutyunattendedbz"  type="hidden"  class="easyui-validatebox" name="telOnDutyunattendedbz" style="width:152px;text-align:center;"></input>
        <!-- <input type="button" id="telOnDutyunattendedButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->   
           <img src="../../images/beizhu.png" id="telOnDutyunattendedButton" title="" onclick="addBeiZhu(this.id)" />
           </td>
            <td align="center">无人接听，&nbsp;&nbsp;<b>-5分/次</b> </td>  
            <td align="center"><input id="telOnDutyunattendedsj"  type="text"  name="telOnDutyunattendedsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
         <tr>      
            <td align="center" style="background-color:#F0FFFF">维保企业变更</td>  
            <td align="center">办理变更</td>
            <td align="center"><input id="enterpriseChangejc"  type="text"  name="enterpriseChangejc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td align="left"><input id="enterpriseChangeTimes" name="enterpriseChangeTimes" class="easyui-numberspinner"  style="width:50px;"  value="0" data-options="min:0,editable:false"></input>宗</td>
            <td align="center">
             <input id="enterpriseChangebz"   type="hidden" class="easyui-validatebox" name="enterpriseChangebz" style="width:152px;text-align:center;"></input> 
         <!--         <input type="button" id="enterpriseChangeButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->
             <img src="../../images/beizhu.png" id="enterpriseChangeButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">发生变化未办理变更手续，&nbsp;&nbsp;<b>-10分/宗</b> </td>  
            <td align="center"><input id="enterpriseChangesj"  type="text"  name="enterpriseChangesj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
        <tr>      
            <td align="center" style="background-color:#F0FFFF">外地企业备案</td>  
            <td align="center">备案</td>
            <td align="center"><input id="enterpriseRecordjc"  type="text"  name="enterpriseRecordjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td align="left"><select id="enterpriseRecord"  name="enterpriseRecord" class="easyui-combobox" style="width:50px;" onchange="enterpriseRecordValue()">
            <option value="0">有</option>
            <option value="1">无</option> 
            </select></td>
            <td align="center">
            <input id="enterpriseRecordbz"   type="hidden" class="easyui-validatebox" name="enterpriseRecordbz" style="width:152px;text-align:center;"></input>
          <!--   <input type="button" id="enterpriseRecordButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->  
            <img src="../../images/beizhu.png" id="enterpriseRecordButton" title="" onclick="addBeiZhu(this.id)" />
             </td>
            <td align="center">未备案或未办理变更备案&nbsp;&nbsp;<b>-10分</b></td>  
            <td align="center"><input id="enterpriseRecordsj"  type="text"  name="enterpriseRecordsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
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
		  <form id="bzform" name="bzform" method="post" enctype="multipart/form-data">
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
        </form>
      </div>  
 </div>
 
 
  

</body>
</html>