<%@ page import="com.zytx.models.UserInfo,com.zytx.init.GlobalFunction,com.zytx.models.CompanyInfoVO" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	form.url='/tcweb/elevator/queryKpDetailinfoList';

	$('#myform').form({onLoadSuccess:bzImg});  
	
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
	
	 comb=$('#companyId').combobox({
			url:'/tcweb/elevator/kpCompanyinfo',
		    valueField:'id',
		    textField:'companyName'
		});

	    $('#townshipStreets').combobox({
	    	filter: function(q, row){
	    	//    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });
	    
	$('#kptb').datagrid({   
	    url:'/tcweb/elevator/kpinfoList',   
	    columns:[[  
	        {field:'ratingCompanyName',title:'评分单位',align:'center',width:$(this).width() * 0.19},  
	        {field:'ywCompanyName',title:'运维公司',align:'center',width:$(this).width() * 0.19},
	        {field:'scoreTotal',title:'评分总计',align:'center',width:$(this).width() * 0.19,formatter: function(value,rec,index) {
                  
                 return '<a href="#" onclick = "scoreTotalDetail('+rec.ratingCompanyId+','+rec.ywCompanyID+',\''+rec.ratingDate+'\''+',\''+rec.type+'\')" style =" text-decoration: none;">'+value+'</a>';
		        }},   
	        {field:'ratingUserName',title:'评分人',align:'center',width:$(this).width() * 0.19},   
	        {field:'detailratingDate',title:'评分时间',align:'center',width:$(this).width() * 0.19,formatter: function(value,rec,index) {
	        	if(value!=null)
                    return value.substring(0,16);
		        }}  
	    ]],
	    singleSelect:true,
	    striped:true   
	}); 


	initial();

}); 

//格式化日期
function myformatter(date) {
    //获取年份
    var y = date.getFullYear();
    //获取月份
    var m = date.getMonth() + 1;
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

function query(){
	    var companyId =$('#companyId').combobox('getValue'); 
	    if(companyId == "")
		    companyId = 0;
	   
	    var ywCompanyId = $('#ywCompanyIdinfo2').attr("value");
	    if(ywCompanyId == "")
	    	ywCompanyId = 0;
	    var ratingDate =$('#ratingDate2').datebox("getValue");
	    $('#kptb').datagrid("options").url='/tcweb/elevator/queryKpinfoList';
	    $('#kptb').datagrid("options").queryParams={'companyId':companyId,'ywCompanyId':ywCompanyId,'ratingDate':ratingDate};
		
	    $('#kptb').datagrid('reload');
}

function initial(){
	$('#officeSpace').css("background-color","#D3D3D3"); 
    $('#officeSpace').attr('readonly',true); 
  /*
    $('#headQuarters').css("background-color","#D3D3D3"); 
    $("#headQuarters").combobox({  
                    disabled:true  
                });  
    */
    $('#fixedTelOnDuty').css("background-color","#D3D3D3"); 
	$('#fixedTelOnDuty').attr('readonly',true);

	$('#telOnDutyunattendedTimes').css("background-color","#D3D3D3");
    $('#telOnDutyunattendedTimes').numberspinner({disabled: true}); 

    $('#enterpriseChangeTimes').css("background-color","#D3D3D3");
    $('#enterpriseChangeTimes').numberspinner({disabled: true}); 

    $('#enterpriseRecord').css("background-color","#D3D3D3"); 
    $("#enterpriseRecord").combobox({  
                    disabled:true  
                });  
    
	$('#regularInspectionTimes').css("background-color","#D3D3D3");
    $('#regularInspectionTimes').numberspinner({disabled: true});
	
	$('#inspectElevatorTimes').css("background-color","#D3D3D3");
    $('#inspectElevatorTimes').numberspinner({disabled: true});
    $('#inspectElevatorTimes2').css("background-color","#D3D3D3");
    $('#inspectElevatorTimes2').numberspinner({disabled: true});
 

    $('#acceptInspElevatorTimes').css("background-color","#D3D3D3");
    $('#acceptInspElevatorTimes').numberspinner({disabled: true});
    
    $('#maintenSceneInfoTimes').css("background-color","#D3D3D3");
    $('#maintenSceneInfoTimes').numberspinner({disabled: true}); 

    $('#malignantEventsTimes').css("background-color","#D3D3D3");
    $('#malignantEventsTimes').numberspinner({disabled: true});
    $('#malignantEventsTimes2').css("background-color","#D3D3D3");
    $('#malignantEventsTimes2').numberspinner({disabled: true});
    $('#malignantEventsTimes3').css("background-color","#D3D3D3");
    $('#malignantEventsTimes3').numberspinner({disabled: true});

    $('#complaintsEventsTimes').css("background-color","#D3D3D3");
    $('#complaintsEventsTimes').numberspinner({disabled: true});
    $('#complaintsEventsTimes2').css("background-color","#D3D3D3");
    $('#complaintsEventsTimes2').numberspinner({disabled: true});

    $('#maintenBusinessTimes').css("background-color","#D3D3D3");
    $('#maintenBusinessTimes').numberspinner({disabled: true});

    $('#honestTimes').css("background-color","#D3D3D3");
    $('#honestTimes').numberspinner({disabled: true});

    $('#punishmentTimes').css("background-color","#D3D3D3");
    $('#punishmentTimes').numberspinner({disabled: true});
    $('#punishmentTimes2').css("background-color","#D3D3D3");
    $('#punishmentTimes2').numberspinner({disabled: true});
    $('#punishmentTimes3').css("background-color","#D3D3D3");
    $('#punishmentTimes3').numberspinner({disabled: true});
    $('#punishmentTimes4').css("background-color","#D3D3D3");
    $('#punishmentTimes4').numberspinner({disabled: true});

    $('#secondRescuePoint').css("background-color","#D3D3D3"); 
    $('#secondRescuePoint').attr('readonly',true);

    $('#positiveEnergyTimes').css("background-color","#D3D3D3");
    $('#positiveEnergyTimes').numberspinner({disabled: true}); 

    $('#expertsSuggestionTimes').css("background-color","#D3D3D3");
    $('#expertsSuggestionTimes').numberspinner({disabled: true}); 

    $('#positiveWork').css("background-color","#D3D3D3"); 
    $('#positiveWork').attr('readonly',true);

    $('#remoteMonitor').css("background-color","#D3D3D3"); 
    $('#remoteMonitor').attr('readonly',true);

    $('#elevatorInsurance').attr('readonly',true);
    $('#elevatorInsurance').css("background-color","#D3D3D3");

    $('#techinnovationTimes').css("background-color","#D3D3D3");
    $('#techinnovationTimes').numberspinner({disabled: true}); 
    $('#techinnovationTimes2').css("background-color","#D3D3D3");
    $('#techinnovationTimes2').numberspinner({disabled: true});
    $('#techinnovationTimes3').css("background-color","#D3D3D3");
    $('#techinnovationTimes3').numberspinner({disabled: true});
    $('#techinnovationTimes4').css("background-color","#D3D3D3");
    $('#techinnovationTimes4').numberspinner({disabled: true});
    $('#techinnovationTimes5').css("background-color","#D3D3D3");
    $('#techinnovationTimes5').numberspinner({disabled: true});
    $('#techinnovationbz').css("background-color","#D3D3D3");
}

function scoreTotalDetail(ratingCompanyId,ywCompanyID,ratingDate,ratingCompanyIdtype){
	
	$('#kpdWin').window('open');
	$('#myform').form('clear');

	if("市质监" == ratingCompanyIdtype){
		$('#officeSpacetr').show();
	//	$('#headQuarterstr').show();
		$('#fixedTelOnDutytr').show();
		$('#telOnDutyunattendedtr').show();
		$('#enterpriseChangetr').show();
		$('#enterpriseRecordtr').show();
	    $('#regularInspectiontr').hide();
	    $('#inspectElevatortr').hide();
		$('#acceptInspElevatortr').hide();
		$('#maintenSceneInfotr').hide();
		$('#malignantEventstr').show();
		$('#complaintsEventstr').show();
		$('#maintenBusinesstr').show();
		$('#honesttr').show();
		$('#punishmenttr').show();
		$('#secondRescuePointtr').show();
		$('#positiveEnergytr').show();
		$('#expertsSuggestiontr').show();
		$('#positiveWorktr').show();
		$('#remoteMonitortr').show();
		$('#elevatorInsurancetr').show();
		$('#techinnovationtr').show();
		  
	}


	else if("质监" == ratingCompanyIdtype){
		$('#officeSpacetr').hide();
	//	$('#headQuarterstr').hide();
		$('#fixedTelOnDutytr').hide();
		$('#telOnDutyunattendedtr').hide();
		$('#enterpriseChangetr').hide();
		$('#enterpriseRecordtr').hide();
		$('#regularInspectiontr').hide();
		$('#inspectElevatortr').show();
		$('#acceptInspElevatortr').show();
		$('#maintenSceneInfotr').show();
		$('#malignantEventstr').hide();
		$('#complaintsEventstr').show();
		$('#maintenBusinesstr').show();
		$('#honesttr').show();
		$('#punishmenttr').show();
		$('#secondRescuePointtr').hide();
		$('#positiveEnergytr').hide();
		$('#expertsSuggestiontr').hide();
		$('#positiveWorktr').hide();
		$('#remoteMonitortr').hide();
		$('#elevatorInsurancetr').hide();
		$('#techinnovationtr').hide();
		  
	}  
    
	else if("检验" == ratingCompanyIdtype){
	  $('#officeSpacetr').hide();
	//  $('#headQuarterstr').hide();
	  $('#fixedTelOnDutytr').hide();
	  $('#telOnDutyunattendedtr').hide();
	  $('#enterpriseChangetr').hide();
	  $('#enterpriseRecordtr').hide();
	  $('#regularInspectiontr').show();
	  $('#inspectElevatortr').show();
	  $('#acceptInspElevatortr').show();
	  $('#maintenSceneInfotr').show();
	  $('#malignantEventstr').hide();
	  $('#complaintsEventstr').hide();
	  $('#maintenBusinesstr').hide();
	  $('#honesttr').hide();
	  $('#punishmenttr').hide();
	  $('#secondRescuePointtr').hide();
	  $('#positiveEnergytr').hide();
	  $('#expertsSuggestiontr').hide();
	  $('#positiveWorktr').hide();
	  $('#remoteMonitortr').hide();
	  $('#elevatorInsurancetr').hide();
	  $('#techinnovationtr').hide();
	 }

	else{
		$('#kpdWin').window('close');
		}

	
		
	$('#myform').form('load', '/tcweb/elevator/queryKpDetailinfoList/?ywCompanyID='+ywCompanyID+'&ratingDate='+ratingDate+'&ratingCompanyId='+ratingCompanyId);
	
}

var beizhuid ="";

function addBeiZhu(id){  
    beizhuid = id;    
    $('#beiZhuWin').window('open');
    var beizhugs ="";
 
	if(beizhuid=="officeSpaceButton"){
		$('#beiZhuWin').window('resize',{
	         width: 350,
	         height: 400,
	         top:($(window).height() - 420) * 0.5,   
	         left:($(window).width() - 450) * 0.5
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

	    $("#ofs").val(data.officeSpacezyfs);
	    readImg(data.imgName);

	}

	if(beizhuid=="fixedTelOnDutyButton"){
		//	$('#beizhutext').attr("value",$('#fixedTelOnDutybz').attr("value"));
		 $('#beiZhuWin').window('resize',{
         width: 350,
         height: 200,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
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
//		$('#beizhutext').attr("value",$('#telOnDutyunattendedbz').attr("value"));
         $('#beiZhuWin').window('resize',{
         width: 500,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
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
		 $('#beiZhuWin').window('resize',{
         width: 350,
         height: 200,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
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
//		$('#beizhutext').attr("value",$('#enterpriseRecordbz').attr("value"));
        $('#beiZhuWin').window('resize',{
         width: 350,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
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
	if(beizhuid=="regularInspectionButton"){
		$('#beiZhuWin').window('resize',{
	         width: 600,
	         height: 400,
	         top:($(window).height() - 420) * 0.5,   
	         left:($(window).width() - 450) * 0.5
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
		$('#beiZhuWin').window('resize',{
	         width: 900,
	         height: 400,
	         top:($(window).height() - 420) * 0.5,   
	         left:($(window).width() - 450) * 0.5
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
		$('#beiZhuWin').window('resize',{
	         width: 600,
	         height: 400,
	         top:($(window).height() - 420) * 0.5,   
	         left:($(window).width() - 450) * 0.5
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
		 $('#beiZhuWin').window('resize',{
	         width: 600,
	         height: 400,
	         top:($(window).height() - 420) * 0.5,   
	         left:($(window).width() - 450) * 0.5
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
	if(beizhuid=="malignantEventsButton"){
//		$('#beizhutext').attr("value",$('#malignantEventsbz').attr("value"));
        $('#beiZhuWin').window('resize',{
         width: 900,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
      });
		var data;
	    if($('#malignantEventsbz').attr("value")==""){
	    	data={'list': [
	   	    	   	    {
	   	    	   	  'mrg': '',
		 	            'mt': '',
		 	            'mde': '',
		 	            'mr': '',
		 	            'ml': ''   
		           }]};
			   }
		  else{ 
		     data = JSON.parse($('#malignantEventsbz').attr("value")); 
		   }       
	
	        //获取模板
	        jsRenderTpl = $.templates('#malignantEventsTemp'),
	        //末班与数据结合
	        finalTpl = jsRenderTpl(data);

	    $('#beizhuDivnr').html(finalTpl);
	  

	 //   $("#malignantEventsLevel").val(data.malignantEventsLevel);
	    $.each(data,function(i){  
             $.each(data[i],function(i,v){
                  var selectV = v.ml;   
             //     var trobj =$('#myTable tr:eq(1+i)').getElementsByTagName('select');
             //     trobj.getElementsByTagName('select')[0].value=selectV; 
               $("select[name='ml']").eq(i).val(selectV);
                  
	          });  
	        }
         );   
	
	}
	if(beizhuid=="complaintsEventsButton"){
//		$('#beizhutext').attr("value",$('#complaintsEventsbz').attr("value"));
         $('#beiZhuWin').window('resize',{
         width: 900,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
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
	
	}
	if(beizhuid=="maintenBusinessButton"){
//		$('#beizhutext').attr("value",$('#maintenBusinessbz').attr("value"));
     $('#beiZhuWin').window('resize',{
         width: 900,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
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
	
	}
	if(beizhuid=="honestButton"){
//		$('#beizhutext').attr("value",$('#honestbz').attr("value"));
        $('#beiZhuWin').window('resize',{
         width: 600,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
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
	
	}
	if(beizhuid=="punishmentButton"){
//		$('#beizhutext').attr("value",$('#punishmentbz').attr("value"));
        $('#beiZhuWin').window('resize',{
         width: 900,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
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
	
	}
	if(beizhuid=="firstRescueButton")
		$('#beizhutext').attr("value",$('#firstRescuebz').attr("value"));
	if(beizhuid=="secondRescueButton")
		$('#beizhutext').attr("value",$('#secondRescuebz').attr("value"));
	
	if(beizhuid=="secondRescuePointButton"){
//		$('#beizhutext').attr("value",$('#secondRescuePointbz').attr("value"));
         $('#beiZhuWin').window('resize',{
         width: 600,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
      });
		var data;
	    if($('#secondRescuePointbz').attr("value")==""){
	    	data = {
	    			 'sr': ''
		        };
			   }
		  else{ 
		     data = JSON.parse($('#secondRescuePointbz').attr("value")); 
		   }    
		
	        //获取模板
	        jsRenderTpl = $.templates('#secondRescuePointTemp'),
	        //末班与数据结合
	        finalTpl = jsRenderTpl(data);

	    $('#beizhuDivnr').html(finalTpl);

	    $('#sr').attr("value",data.sr);
	
	}
	if(beizhuid=="rescueResponseButton")
		$('#beizhutext').attr("value",$('#rescueResponsebz').attr("value"));
	if(beizhuid=="tiringPeopleButton")
		$('#beizhutext').attr("value",$('#tiringPeoplebz').attr("value"));
	if(beizhuid=="positiveEnergyButton"){
//		$('#beizhutext').attr("value",$('#positiveEnergybz').attr("value"));
         $('#beiZhuWin').window('resize',{
         width: 600,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
      });
		var data;
	    if($('#positiveEnergybz').attr("value")==""){
	    	data={'list': [
	    	               {
	    	            	   'pet': '',
	    		 	            'pede': '',
	    		 	            'per': ''
	    	               }]};
		   }
		  else{ 
		     data = JSON.parse($('#positiveEnergybz').attr("value")); 
		   }   
		
	        //获取模板
	        jsRenderTpl = $.templates('#positiveEnergyTemp'),
	        //末班与数据结合
	        finalTpl = jsRenderTpl(data);

	    $('#beizhuDivnr').html(finalTpl);
	
	}
	if(beizhuid=="expertsSuggestionButton"){
//		$('#beizhutext').attr("value",$('#expertsSuggestionbz').attr("value"));
         $('#beiZhuWin').window('resize',{
         width: 600,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
      });
		var data;
	    if($('#expertsSuggestionbz').attr("value")==""){
	    	data={'list': [
	    	               {
	    	            	   'et': '',
	    		  	            'ede': '',
	    		  	            'er': ''
	    	               }]};
		   }
		  else{ 
		     data = JSON.parse($('#expertsSuggestionbz').attr("value")); 
		   }   
		
	        //获取模板
	        jsRenderTpl = $.templates('#expertsSuggestionTemp'),
	        //末班与数据结合
	        finalTpl = jsRenderTpl(data);

	    $('#beizhuDivnr').html(finalTpl);
	
	}
	if(beizhuid=="positiveWorkButton"){
//		$('#beizhutext').attr("value",$('#positiveWorkbz').attr("value"));
         $('#beiZhuWin').window('resize',{
         width: 600,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
      });
		var data;
	    if($('#positiveWorkbz').attr("value")==""){
	    	data = {
	    			 'pwr': ''
		        };
			   }
		  else{ 
		     data = JSON.parse($('#positiveWorkbz').attr("value")); 
		   }   
		   
		
	        //获取模板
	        jsRenderTpl = $.templates('#positiveWorkTemp'),
	        //末班与数据结合
	        finalTpl = jsRenderTpl(data);
	    
	    $('#beizhuDivnr').html(finalTpl);
	    $('#pwr').attr("value",data.pwr);
	
	}
	if(beizhuid=="remoteMonitorButton"){
//		$('#beizhutext').attr("value",$('#remoteMonitorbz').attr("value"));
         $('#beiZhuWin').window('resize',{
         width: 600,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
      });
		var data;
	    if($('#remoteMonitorbz').attr("value")==""){
	    	data = {
	    			 'rr': ''
		        };
			   }
		  else{ 
		     data = JSON.parse($('#remoteMonitorbz').attr("value")); 
		   }   
		
	        //获取模板
	        jsRenderTpl = $.templates('#remoteMonitorTemp'),
	        //末班与数据结合
	        finalTpl = jsRenderTpl(data);

	    $('#beizhuDivnr').html(finalTpl);
	    $('#rr').attr("value",data.rr);
	
	}
	if(beizhuid=="elevatorInsuranceButton"){
//		$('#beizhutext').attr("value",$('#elevatorInsurancebz').attr("value"));
        $('#beiZhuWin').window('resize',{
         width: 350,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
      });
		var data;
	    if($('#elevatorInsurancebz').attr("value")==""){
	    	data = {
	    			'eir': '',
	 	            'eimg':'',
		            'imgName':''
		        };
			   }
		  else{ 
		     data = JSON.parse($('#elevatorInsurancebz').attr("value")); 
		   }   
		
	        //获取模板
	        jsRenderTpl = $.templates('#elevatorInsuranceTemp'),
	        //末班与数据结合
	        finalTpl = jsRenderTpl(data);

	    $('#beizhuDivnr').html(finalTpl);   
	    readImg(data.imgName);
	}
	if(beizhuid=="techinnovationButton"){  
//		$('#beizhutext').attr("value",$('#techinnovationbz').attr("value"));
        $('#beiZhuWin').window('resize',{
         width: 600,
         height: 400,
         top:($(window).height() - 420) * 0.5,   
         left:($(window).width() - 450) * 0.5
      });
		var data;
	    if($('#techinnovationbz').attr("value")==""){
	/*   	data = {
	    			'techinnovationTime': '',
	 	            'techinnovationAddress': '',
	 	            'techinnovationReason': ''
		        };  */
	    	data={'list': [
	                       {
	                    	   'tt': '',
		                        'tde': '',
		                        'tr': ''
	                         }
				 		    ]
				   };
	    }
		  else{ 
	     data = JSON.parse($('#techinnovationbz').attr("value")); 
			
		   }   
		
	        //获取模板
	        jsRenderTpl = $.templates('#techinnovationTemp'),
	        //末班与数据结合
	        finalTpl = jsRenderTpl(data);

	    $('#beizhuDivnr').html(finalTpl);  

	}
}

/*
function addBeiZhu(id){
    beizhuid = id;
    $('#beiZhuWin').window('open');
	
    if(beizhuid=="officeSpaceButton")
		$('#beizhutext').attr("value",$('#officeSpacebz').attr("value"));
	if(beizhuid=="headQuartersButton")
		$('#beizhutext').attr("value",$('#headQuartersbz').attr("value"));
	if(beizhuid=="fixedTelOnDutyButton")
		$('#beizhutext').attr("value",$('#fixedTelOnDutybz').attr("value"));
	if(beizhuid=="telOnDutyunattendedButton")
		$('#beizhutext').attr("value",$('#telOnDutyunattendedbz').attr("value"));
	if(beizhuid=="enterpriseChangeButton")
		$('#beizhutext').attr("value",$('#enterpriseChangebz').attr("value"));
	if(beizhuid=="enterpriseChangeButton")
		$('#beizhutext').attr("value",$('#enterpriseChangebz').attr("value"));
	if(beizhuid=="enterpriseRecordButton")
		$('#beizhutext').attr("value",$('#enterpriseRecordbz').attr("value"));
	if(beizhuid=="regularInspectionButton")
		$('#beizhutext').attr("value",$('#regularInspectionbz').attr("value"));
	if(beizhuid=="inspectElevatorButton")
		$('#beizhutext').attr("value",$('#inspectElevatorbz').attr("value"));
	if(beizhuid=="acceptInspElevatorButton")
		$('#beizhutext').attr("value",$('#acceptInspElevatorbz').attr("value"));
	if(beizhuid=="maintenSceneInfoButton")
		$('#beizhutext').attr("value",$('#maintenSceneInfobz').attr("value"));
	if(beizhuid=="malignantEventsButton")
		$('#beizhutext').attr("value",$('#malignantEventsbz').attr("value"));
	if(beizhuid=="complaintsEventsButton")
		$('#beizhutext').attr("value",$('#complaintsEventsbz').attr("value"));
	if(beizhuid=="maintenBusinessButton")
		$('#beizhutext').attr("value",$('#maintenBusinessbz').attr("value"));
	if(beizhuid=="honestButton")
		$('#beizhutext').attr("value",$('#honestbz').attr("value"));
	if(beizhuid=="punishmentButton")
		$('#beizhutext').attr("value",$('#punishmentbz').attr("value"));
	if(beizhuid=="firstRescueButton")
		$('#beizhutext').attr("value",$('#firstRescuebz').attr("value"));
	if(beizhuid=="secondRescueButton")
		$('#beizhutext').attr("value",$('#secondRescuebz').attr("value"));
	if(beizhuid=="secondRescuePointButton")
		$('#beizhutext').attr("value",$('#secondRescuePointbz').attr("value"));
	if(beizhuid=="rescueResponseButton")
		$('#beizhutext').attr("value",$('#rescueResponsebz').attr("value"));
	if(beizhuid=="tiringPeopleButton")
		$('#beizhutext').attr("value",$('#tiringPeoplebz').attr("value"));
	if(beizhuid=="positiveEnergyButton")
		$('#beizhutext').attr("value",$('#positiveEnergybz').attr("value"));
	if(beizhuid=="expertsSuggestionButton")
		$('#beizhutext').attr("value",$('#expertsSuggestionbz').attr("value"));
	if(beizhuid=="positiveWorkButton")
		$('#beizhutext').attr("value",$('#positiveWorkbz').attr("value"));
	if(beizhuid=="remoteMonitorButton")
		$('#beizhutext').attr("value",$('#remoteMonitorbz').attr("value"));
	if(beizhuid=="elevatorInsuranceButton")    
		$('#beizhutext').attr("value",$('#elevatorInsurancebz').attr("value"));
	if(beizhuid=="techinnovationButton")    
		$('#beizhutext').attr("value",$('#techinnovationbz').attr("value"));
		
}
*/

function  bzImg(){
	 if($('#officeSpacebz').attr("value") != "")  
		   $("#officeSpaceButton").attr('src','../../images/youbeizhu.png');
      else
         $("#officeSpaceButton").attr('src','../../images/beizhu.png'); 
	/*	
		if($('#headQuartersbz').attr("value") != "")  
			$("#headQuartersButton").attr('src','../../images/youbeizhu.png'); 
		else
			$("#headQuartersButton").attr('src','../../images/beizhu.png'); 
	*/	 
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
	   
	   if($('#regularInspectionbz').attr("value") != "")
    	   $("#regularInspectionButton").attr('src','../../images/youbeizhu.png'); 
       else
    	   $("#regularInspectionButton").attr('src','../../images/beizhu.png');

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

	  if($('#malignantEventsbz').attr("value") !="")
		   $("#malignantEventsButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#malignantEventsButton").attr('src','../../images/beizhu.png'); 
	   
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

       if($('#secondRescuePointbz').attr("value") != "")
		   $("#secondRescuePointButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#secondRescuePointButton").attr('src','../../images/beizhu.png'); 

       if($('#positiveEnergybz').attr("value") !="")
    	   $("#positiveEnergyButton").attr('src','../../images/youbeizhu.png');
       else
    	   $("#positiveEnergyButton").attr('src','../../images/beizhu.png');
	
	   if($('#expertsSuggestionbz').attr("value") != "")
		   $("#expertsSuggestionButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#expertsSuggestionButton").attr('src','../../images/beizhu.png');  
	
	   if($('#positiveWorkbz').attr("value") != "")
		   $("#positiveWorkButton").attr('src','../../images/youbeizhu.png');
	   else
		   $("#positiveWorkButton").attr('src','../../images/beizhu.png');
	
	   if($('#remoteMonitorbz').attr("value") != "")
		   $("#remoteMonitorButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#remoteMonitorButton").attr('src','../../images/beizhu.png'); 
	
	   if($('#elevatorInsurancebz').attr("value") != "")
		   $("#elevatorInsuranceButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#elevatorInsuranceButton").attr('src','../../images/beizhu.png');
		   
	   if($('#techinnovationbz').attr("value") != "")
		   $("#techinnovationButton").attr('src','../../images/youbeizhu.png');
	   else
		   $("#techinnovationButton").attr('src','../../images/beizhu.png');
	
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
   <td><input id="imgName" type="hidden"  name="imgName" value="{{:imgName}}"/></td>
    </tr>
   <tr>
   <td colspan="2">
    <img src="" id="companyImg"/>
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
  <!--   <tr><td><a href="#" name="addRow" onclick="addRow()">增加行</a></td><td><a href="#" name="deleteRow" onclick="deleteRow()">删除行</a></td></tr> -->
    {{for list}}
    <tr>
       <td align="right">呼出电话:<input type="text" id="tct" name="tct" value="{{:tct}}"></td>
       <td align="right">拨打时间:<input type="text" id="tcte" name="tcte" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:tcte}}"></td>
    </tr>
   {{/for}}
</table>  
</script>    
<script type="text/x-jsrender" id="enterpriseChangeTemp">
     <table>
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
   <!--  <tr>
    <td align="right">图片:</td>
   <td><input id="enterpriseRecordImg" type="file" size="45" name="enterpriseRecordImg" onchange="Javascript:validate_img(this);">
    </td>
    </tr>  -->
   <tr>
   <td><input id="imgName" type="hidden"  name="imgName" value="{{:imgName}}"/></td>
    <tr>
   <tr>
   <td colspan="2">
    <img src="" id="companyImg"/>
   </td>
   </tr>
    </tr>
</table>
</script>
<script type="text/x-jsrender" id="regularInspectionTemp">
    <table id="myTable">
<!--  <tr><td><a href="#" name="addRow" class="bztoolbutton" onclick="addRow()">添加</a><a href="#" name="deleteRow" class="bztoolbutton" onclick="deleteRow()">删除</a></td></tr> -->
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
 <!-- <tr>
     <td>
   <a href="#" name="addRow" class='bztoolbutton'  onclick="addRow()">添加</a>
   <a href="#" name="deleteRow"  class='bztoolbutton' onclick="deleteRow()">删除</a></li>
   
    </td>
   </tr>  -->
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
<!-- <tr><td><a href="#" name="addRow" class="bztoolbutton" onclick="addRow()">添加</a><a href="#" name="deleteRow" class="bztoolbutton" onclick="deleteRow()">删除</a></td></tr> -->
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
 <!-- <tr><td><a href="#" name="addRow" class="bztoolbutton" onclick="addRow()">添加</a><a href="#" name="deleteRow" class="bztoolbutton" onclick="deleteRow()">删除</a></td></tr> -->
     {{for list}}
    <tr>
       <td>时间: <input type="text" id="mst" name="mst" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:mst}}"></td>
       <td>地点: <input type="text" id="msde" name="msde" value="{{:msde}}"></td>
       <td>原因: <input type="text" id="msr" name="msr" value="{{:msr}}"></td>
    </tr>
{{/for}}
</table>
</script>
<script type="text/x-jsrender" id="malignantEventsTemp">
    <table id="myTable">
 <!--  <tr><td><a href="#" name="addRow" onclick="addRow()">Add Row</a></td><td><a href="#" name="deleteRow" onclick="deleteRow()">Delete Row</a></td></tr> -->
    {{for list}}
   <tr>
       <td>二维码编号: <input type="text" id="mrg" name="mrg" value="{{:mrg}}"></input></td>
       <td>时间: <input type="text" id="mt" name="mt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:mt}}"></input></td>
       <td>地点: <input type="text" id="mde" name="mde" value="{{:mde}}"></input></td>
       <td>原因: <input type="text" id="mr" name="mr" value="{{:mr}}"></input></td>
       <td>级别:  <select name="ml" id="ml" value="{{:ml}}">  
                      <option value="0">一般事故</option>  
                      <option value="1">较大事故</option>
                      <option value="2">重大事故</option>
                     </select>   
         </td>
    </tr>
    {{/for}}
</table>
</script>
<script type="text/x-jsrender" id="complaintsEventsTemp">
   <table id="myTable">
 <!--     <tr><td><a href="#" name="addRow" onclick="addRow()">Add Row</a></td><td><a href="#" name="deleteRow" onclick="deleteRow()">Delete Row</a></td></tr>  -->
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
<!--  <tr><td><a href="#" name="addRow" onclick="addRow()">Add Row</a></td><td><a href="#" name="deleteRow" onclick="deleteRow()">Delete Row</a></td></tr>  -->
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
<!--  <tr><td><a href="#" name="addRow" onclick="addRow()">Add Row</a></td><td><a href="#" name="deleteRow" onclick="deleteRow()">Delete Row</a></td></tr>  -->
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
<!--  <tr><td><a href="#" name="addRow" onclick="addRow()">Add Row</a></td><td><a href="#" name="deleteRow" onclick="deleteRow()">Delete Row</a></td></tr>  -->
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
<script type="text/x-jsrender" id="secondRescuePointTemp">
     <table style="100%">
 <tr>
       <td>说明:</td>
       <td><textarea rows="20" cols="82"  id="sr" name="sr" value="{{:sr}}"></textarea></td>
    </tr>
</table>
</script>
<script type="text/x-jsrender" id="positiveEnergyTemp">
     <table id="myTable">
<!--  <tr><td><a href="#" name="addRow" onclick="addRow()">Add Row</a></td><td><a href="#" name="deleteRow" onclick="deleteRow()">Delete Row</a></td></tr>  -->
     {{for list}}
  <tr>
       <td>时间: <input type="text" id="pet" name="pet" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:pet}}"></td>
       <td>地点: <input type="text" id="pede" name="pede" value="{{:pede}}"></td>
       <td>原因: <input type="text" id="per" name="per" value="{{:per}}"></td>
    </tr>
{{/for}}
</table>
</script>
<script type="text/x-jsrender" id="expertsSuggestionTemp">
    <table id="myTable">
<!--  <tr><td><a href="#" name="addRow" onclick="addRow()">Add Row</a></td><td><a href="#" name="deleteRow" onclick="deleteRow()">Delete Row</a></td></tr>  -->
     {{for list}}
  <tr>
       <td>时间: <input type="text" id="et" name="et" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:et}}"></td>
       <td>地点: <input type="text" id="ede" name="ede" value="{{:ede}}"></td>
       <td>原因: <input type="text" id="er" name="er" value="{{:er}}"></td>
    </tr>
{{/for}}
</table>
</script>
<script type="text/x-jsrender" id="positiveWorkTemp">
    <table>
<tr>
       <td>说明:</td>
       <td><textarea rows="20" cols="82"   id="pwr" name="pwr" value="{{:pwr}}"></textarea></td>
    </tr>
</table>
</script>
<script type="text/x-jsrender" id="remoteMonitorTemp">
    <table>
   <td>说明:</td>
       <td><textarea rows="20" cols="82"  id="rr" name="rr" value="{{:rr}}"></textarea></td>
    </tr>
</table>
</script>
<script type="text/x-jsrender" id="elevatorInsuranceTemp">
    <table>
 <tr>
     <td>保险单号:</td>
     <td><input type="text" id="eir" name="eir"  value="{{:eir}}"></td>
    </tr>
 <!--    <tr>
    <td align="right">图片:</td>
    <td><input id="elevatorInsuranceImg" type="file" size="45" name="elevatorInsuranceImg" onchange="Javascript:validate_img(this);">
   </tr>  -->
   <tr>
   <td><input id="imgName" type="hidden"  name="imgName" value="{{:imgName}}"/></td>
    <tr>
   <tr>
   <td colspan="2">
    <img src="" id="companyImg"/>
   </td>
   </tr>
</table>
</script>
<script type="text/x-jsrender" id="techinnovationTemp">
  <table id="myTable">
<!--  <tr><td><a href="#" name="addRow" onclick="addRow()">Add Row</a></td><td><a href="#" name="deleteRow" onclick="deleteRow()">Delete Row</a></td></tr> -->
    {{for list}}
     <tr>
       <td>时间: <input type="text" id="tt" name="tt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" value="{{:tt}}"></td>
    
       <td>地点: <input type="text" id="tde" name="tde" value="{{:tde}}"></td>
   
       <td>原因: <input type="text" id="tr" name="tr" value="{{:tr}}"></td>
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
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">  
       <center>
        <table>
        <tr>
        <td nowrap height="70px">考评单位：</td>
        <td><select id="companyId"  class="easyui-combobox" name="townshipStreets" style="width:200px;"></select></td> 
        <td nowrap height="70px">维保单位：</td>
        <td><input id="ywCompanyIdinfo" style="width:152px;background-color:#87CEEB;" >
        <input type ="hidden" id="ywCompanyIdinfo2"></td>
        <td nowrap>时间：</td>
        <td><select id="ratingDate2"  class="easyui-datebox" name="ratingDate2" style="width:152px;" data-options="editable:false"> 
        </select></td>
        <td><a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a></td>  
       </tr>
       </table>
       </center>
</div>

<div region="center"> 
   <table id="kptb"></table>
</div>

<div id="kpdWin" class="easyui-window" title="详情" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false" style="width:800px;height:400px;padding:10px;">
  <form id="myform" name="myform"> 
  <table id="kpdtb" border ="1" style="border-width: 1px; border-style: ridge; border-collapse:collapse;"  width="100%" cellspacing="0" cellpadding="1">
    <tr id="officeSpacetr">   
            <td align="center" style="background-color:#F0FFFF">办公面积</td>  
            <td align="left"><input id="officeSpace"  type="text" class="easyui-validatebox" name="officeSpace" style="width:102px;text-align:center;" value="0" onchange="officeSpaceValue()"></input>㎡  </td>
            <td align="center">
            <input id="officeSpacebz"  type="hidden" class="easyui-validatebox" name="officeSpacebz" style="width:152px;text-align:center;"></input>
        <!--     <input type="button" id="officeSpaceButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> --> 
            <img src="../../images/beizhu.png" id="officeSpaceButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
           <td align="center"><input id="officeSpacesj"  type="text"  name="officeSpacesj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
   <!--   <tr id="headQuarterstr">         
            <td align="center" style="background-color:#F0FFFF">总部所在地</td>  
             <td align="left">
        <select id="headQuarters"  class="easyui-combobox"  name="headQuarters" style="width:70px;">
            <option value="0">成都</option>
            <option value="1">外地</option> 
            </select>  
            </td>
            <td align="center">
            <input id="headQuartersbz"  type="hidden" class="easyui-validatebox" name="headQuartersbz" style="width:152px;text-align:center;"></input>
            <img src="../../images/beizhu.png" id="headQuartersButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
           <td align="center"><input id="headQuarterssj"  type="text"  name="headQuarterssj" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="10"  readOnly></input></td>
        </tr>    --> 
     <tr id="fixedTelOnDutytr">  
           
            <td align="center" style="background-color:#F0FFFF">值班固定电话</td>  
           <td align="left"><input id="fixedTelOnDuty"  type="text"  name="fixedTelOnDuty" style="width:102px;" onchange="fixedTelOnDutyValue()"></input></td>
            <td align="center">
            <input id="fixedTelOnDutybz"  type="hidden" class="easyui-validatebox" name="fixedTelOnDutybz" style="width:152px;text-align:center;"></input>
          <!--  <input type="button" id="fixedTelOnDutyButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  -->  
             <img src="../../images/beizhu.png" id="fixedTelOnDutyButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
           <td align="center"><input id="fixedTelOnDutysj"  type="text"  name="fixedTelOnDutysj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        
     <tr id="telOnDutyunattendedtr">  
            <td align="center" style="background-color:#F0FFFF">电话值守<br>无人接听</td>  
           <td align="left"><input id="telOnDutyunattendedTimes" name="telOnDutyunattendedTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>次</td>
           <td align="center">
           <input id="telOnDutyunattendedbz"  type="hidden" class="easyui-validatebox" name="telOnDutyunattendedbz" style="width:152px;text-align:center;"></input>
        <!-- <input type="button" id="telOnDutyunattendedButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->   
           <img src="../../images/beizhu.png" id="telOnDutyunattendedButton" title="" onclick="addBeiZhu(this.id)" />
           </td>
           <td align="center"><input id="telOnDutyunattendedsj"  type="text"  name="telOnDutyunattendedsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        
     <tr id="enterpriseChangetr">      
            <td align="center" style="background-color:#F0FFFF">维保企业变更</td>  
           <td align="left"><input id="enterpriseChangeTimes" name="enterpriseChangeTimes" class="easyui-numberspinner"  style="width:50px;"  value="0" data-options="min:0,editable:false"></input>宗</td>
            <td align="center">
             <input id="enterpriseChangebz"  type="hidden" class="easyui-validatebox" name="enterpriseChangebz" style="width:152px;text-align:center;"></input> 
         <!--         <input type="button" id="enterpriseChangeButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->
             <img src="../../images/beizhu.png" id="enterpriseChangeButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
           <td align="center"><input id="enterpriseChangesj"  type="text"  name="enterpriseChangesj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
        
     <tr id="enterpriseRecordtr">      
            <td align="center" style="background-color:#F0FFFF">外地企业备案</td>  
            <td align="left"><select id="enterpriseRecord"  name="enterpriseRecord" class="easyui-combobox" style="width:50px;" onchange="enterpriseRecordValue()">
            <option value="0">有</option>
            <option value="1">无</option> 
            </select></td>
            <td align="center">
            <input id="enterpriseRecordbz"  type="hidden" class="easyui-validatebox" name="enterpriseRecordbz" style="width:152px;text-align:center;"></input>
          <!--   <input type="button" id="enterpriseRecordButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->  
            <img src="../../images/beizhu.png" id="enterpriseRecordButton" title="" onclick="addBeiZhu(this.id)" />
             </td>
           <td align="center"><input id="enterpriseRecordsj"  type="text"  name="enterpriseRecordsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        
        
   <tr id="regularInspectiontr">     
            <td align="center" style="background-color:#F0FFFF">电梯定期检验情况</td>  
            <td align="left"><input id="regularInspectionTimes" name="regularInspectionTimes" class="easyui-numberspinner" style="width:50px;" value="0" data-options="min:0,editable:false"></input>台</td>
            <td align="center">
            <input id="regularInspectionbz"  type="hidden" class="easyui-validatebox" name="regularInspectionbz" style="width:152px;text-align:center;"></input>
          <!--  <input type="button" id="regularInspectionButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->   
            <img src="../../images/beizhu.png" id="regularInspectionButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
          <td align="center"><input id="regularInspectionsj"  type="text"  name="regularInspectionsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
           
        </tr>
    <tr id="inspectElevatortr">    
            <td align="center" style="background-color:#F0FFFF">在用电梯监督抽查情况</td>  
             <td align="left">
            <input id="inspectElevatorTimes" name="inspectElevatorTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input> 严重隐患&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="inspectElevatorTimes2" name="inspectElevatorTimes2" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>回路短接</td>
            <td align="center">
            <input id="inspectElevatorbz"  type="hidden" class="easyui-validatebox" name="inspectElevatorbz" style="width:152px;text-align:center;"></input>
          <!--     <input type="button" id="inspectElevatorButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->
           <img src="../../images/beizhu.png" id="inspectElevatorButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
              <td align="center"><input id="inspectElevatorsj"  type="text"  name="inspectElevatorsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
          <tr id="acceptInspElevatortr">    
            <td align="center" style="background-color:#F0FFFF">接受监督检查情况</td>  
            <td align="left"><input id="acceptInspElevatorTimes" name="acceptInspElevatorTimes" class="easyui-numberspinner" style="width:50px;"  value="0" data-options="min:0,editable:false"></input>次</td>
           <td align="center">
           <input id="acceptInspElevatorbz"  type="hidden" class="easyui-validatebox" name="acceptInspElevatorbz" style="width:152px;text-align:center;"></input>
         <!--  <input type="button" id="acceptInspElevatorButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  -->  
            <img src="../../images/beizhu.png" id="acceptInspElevatorButton" title="" onclick="addBeiZhu(this.id)" />
           </td>
           <td align="center"><input id="acceptInspElevatorsj"  type="text"  name="acceptInspElevatorsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
          <tr id="maintenSceneInfotr">    
            <td align="center" style="background-color:#F0FFFF">维保现场防护情况</td>  
           <td align="left"><input id="maintenSceneInfoTimes" name="maintenSceneInfoTimes" class="easyui-numberspinner" style="width:50px;"  value="0" data-options="min:0,editable:false"></input>次</td>
            <td align="center">
            <input id="maintenSceneInfobz"  type="hidden" class="easyui-validatebox" name="maintenSceneInfobz" style="width:152px;text-align:center;"></input>
          <!--   <input type="button" id="maintenSceneInfoButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->
          <img src="../../images/beizhu.png" id="maintenSceneInfoButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
           <td align="center"><input id="maintenSceneInfosj"  type="text"  name="maintenSceneInfosj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
         <tr id="malignantEventstr">    
            <td align="center" style="background-color:#F0FFFF">因维保原因发生安全事故<br>重大社会影响事件</td>  
            <td align="left">
            <input id="malignantEventsTimes" name="malignantEventsTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>一般事故&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="malignantEventsTimes2" name="malignantEventsTimes2" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input> 较大事故&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input id="malignantEventsTimes3" name="malignantEventsTimes3" class="easyui-numberspinner" style="width:50px;" value="0" data-options="min:0,editable:false"></input>重大事故</td>
            <td align="center">
            <input id="malignantEventsbz"  type="hidden" class="easyui-validatebox" name="malignantEventsbz" style="width:152px;text-align:center;"></input>
         <!--  <input type="button" id="malignantEventsButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  -->    
            <img src="../../images/beizhu.png" id="malignantEventsButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center"><input id="malignantEventssj"  type="text"  name="malignantEventssj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>  
         <tr id="complaintsEventstr">    
            <td align="center" style="background-color:#F0FFFF">因维保质量而引发的投诉<br>（领导信箱、公开电话、网络理政平台）</td>  
            <td align="left">
            <input id="complaintsEventsTimes" name="complaintsEventsTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>一般投诉&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="complaintsEventsTimes2" name="complaintsEventsTimes2" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>连续投诉</td>
            <td align="center">
            <input id="complaintsEventsbz"  type="hidden" class="easyui-validatebox" name="complaintsEventsbz" style="width:152px;text-align:center;"></input>
         <!--     <input type="button" id="complaintsEventsButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> --> 
           <img src="../../images/beizhu.png" id="complaintsEventsButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center"><input id="complaintsEventssj"  type="text"  name="complaintsEventssj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
         <tr id="maintenBusinesstr">    
            <td align="center" style="background-color:#F0FFFF">维保业务管理</td>  
           <td align="left"><input id="maintenBusinessTimes" name="maintenBusinessTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>宗</td>
            <td align="center">
            <input id="maintenBusinessbz"  type="hidden" class="easyui-validatebox" name="maintenBusinessbz" style="width:152px;text-align:center;"></input>
        <!--   <input type="button" id="maintenBusinessButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->    
             <img src="../../images/beizhu.png" id="maintenBusinessButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
           <td align="center"><input id="maintenBusinesssj"  type="text"  name="maintenBusinesssj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        <tr id="honesttr">    
            <td align="center" style="background-color:#F0FFFF">诚实守信情况</td>  
            <td align="left"><input id="honestTimes" name="honestTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>宗</td>
            <td align="center">
            <input id="honestbz"  type="hidden" class="easyui-validatebox" name="honestbz" style="width:152px;text-align:center;"></input>
         <!--    <input type="button" id="honestButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  --> 
             <img src="../../images/beizhu.png" id="honestButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center"><input id="honestsj"  type="text"  name="honestsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        <tr id="punishmenttr">    
            <td align="center" style="background-color:#F0FFFF">行政查处及处罚情况</td>  
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
            <td align="center"><input id="punishmentsj"  type="text"  name="punishmentsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
         <tr id="secondRescuePointtr">   
            <td align="center" style="background-color:#F0FFFF">参与二级救援布点</td>  
           <td><input id="secondRescuePoint"  type="text"  name="secondRescuePoint" style="width:102px;text-align:center;" value="0" onchange="secondRescuePointValue()"></input>个</td>
            <td align="center">
            <input id="secondRescuePointbz"  type="hidden" class="easyui-validatebox" name="secondRescuePointbz" style="width:152px;text-align:center;"></input>
           <!--   <input type="button" id="secondRescuePointButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> --> 
            <img src="../../images/beizhu.png" id="secondRescuePointButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
             <td align="center"><input id="secondRescuePointsj"  type="text"  name="secondRescuePointsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
         <tr id="positiveEnergytr">   
            <td align="center" style="background-color:#F0FFFF">献计献策、举报违法违规、<br>行业潜规则</td>  
             <td align="left"><input id="positiveEnergyTimes" name="positiveEnergyTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>宗</td>
            <td align="center">
            <input id="positiveEnergybz"  type="hidden" class="easyui-validatebox" name="positiveEnergybz" style="width:152px;text-align:center;"></input>
         <!--   <input type="button" id="positiveEnergyButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>   -->
         <img src="../../images/beizhu.png" id="positiveEnergyButton" title="" onclick="addBeiZhu(this.id)" />  
            </td>
            <td align="center"><input id="positiveEnergysj"  type="text"  name="positiveEnergysj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
            
        </tr>
         <tr id="expertsSuggestiontr">   
            <td align="center" style="background-color:#F0FFFF">提供专家及技术支持，<br>参与故障及事故调查处理</td>  
            <td><input id="expertsSuggestionTimes" name="expertsSuggestionTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>宗</td>
            <td align="center">
            <input id="expertsSuggestionbz"  type="hidden" class="easyui-validatebox" name="expertsSuggestionbz" style="width:152px;text-align:center;"></input>
         <!--     <input type="button" id="expertsSuggestionButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  -->
            <img src="../../images/beizhu.png" id="expertsSuggestionButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center"><input id="expertsSuggestionsj"  type="text"  name="expertsSuggestionsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
         <tr id="positiveWorktr">   
            <td align="center" style="background-color:#F0FFFF">积极承接监管部门指定电梯维保</td>  
           <td><input id="positiveWork"  type="text"  name="positiveWork" style="width:152px;text-align:center;" value="0" onchange="positiveWorkValue()"></input>台</td>
            <td align="center">
            <input id="positiveWorkbz"  type="hidden" class="easyui-validatebox" name="positiveWorkbz" style="width:152px;text-align:center;"></input>
         <!--   <input type="button" id="positiveWorkButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->
            <img src="../../images/beizhu.png" id="positiveWorkButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
           <td align="center"><input id="positiveWorksj"  type="text"  name="positiveWorksj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
        <tr id="remoteMonitortr">   
            <td align="center" style="background-color:#F0FFFF">采用远程监控、机器人保安</td>  
           <td><input id="remoteMonitor"  type="text"  name="remoteMonitor" style="width:152px;text-align:center;" value="0" onchange="remoteMonitorValue()"></input>台</td>
            <td align="center">
            <input id="remoteMonitorbz"  type="hidden" class="easyui-validatebox" name="remoteMonitorbz" style="width:152px;text-align:center;"></input>
        <!--     <input type="button" id="remoteMonitorButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  -->
           <img src="../../images/beizhu.png" id="remoteMonitorButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
           <td align="center"><input id="remoteMonitorsj"  type="text"  name="remoteMonitorsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
         <tr id="elevatorInsurancetr">   
            <td align="center" style="background-color:#F0FFFF">购买电梯责任保险</td>  
           <td><input id="elevatorInsurance"  type="text"  name="elevatorInsurance" style="width:152px;text-align:center;" value="0" onchange="elevatorInsuranceValue()"></input>台</td>
            <td align="center">
            <input id="elevatorInsurancebz"  type="hidden" class="easyui-validatebox" name="elevatorInsurancebz" style="width:152px;text-align:center;"></input>
        <!--     <input type="button" id="elevatorInsuranceButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>   -->
             <img src="../../images/beizhu.png" id="elevatorInsuranceButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center"><input id="elevatorInsurancesj"  type="text"  name="elevatorInsurancesj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        <tr id="techinnovationtr">   
            <td align="center" style="background-color:#F0FFFF">其他开展技术创新及工作创新</td>  
           <td><input id="techinnovationTimes" name="techinnovationTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>1分&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="techinnovationTimes2" name="techinnovationTimes2" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>2分&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="techinnovationTimes3" name="techinnovationTimes3" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>3分&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input id="techinnovationTimes4" name="techinnovationTimes4" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>4分&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="techinnovationTimes5" name="techinnovationTimes5" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>5分</td>
            <td align="center">
            <input id="techinnovationbz"  type="hidden" class="easyui-validatebox" name="techinnovationbz" style="width:152px;text-align:center;"></input>
         <!--      <input type="button" id="techinnovationButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  -->
            <img src="../../images/beizhu.png" id="techinnovationButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
           <td align="center"><input id="techinnovationsj"  type="text"  name="techinnovationsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
  </table>
  </form>
  </div>
  
   <div id="beiZhuWin" class="easyui-window" title="备注" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false" style="width:800px;height:400px;padding:10px;">
		 <div id="beizhuDiv" class="easyui-layout" fit="true"> 
		<div id="beizhuDivnr" region="center"  style="overflow:auto;">
	<!-- 	 <textarea rows="12" cols="57" id="beizhutext"></textarea>   -->
         </div>
        
      </div>  
 </div>
</body>
</html>