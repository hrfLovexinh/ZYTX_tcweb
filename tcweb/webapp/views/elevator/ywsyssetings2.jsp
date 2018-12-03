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
	 
	  
	$('#bztb').datagrid({   
	    url:'',   
	    columns:[[   
	        {field:'itemName',title:'名称',width:$(this).width() * 0.15},
	        {field:'itemContent',title:'评分内容',width:$(this).width() * 0.15,formatter: function(value,rec,index) {
		        var itemName =rec.itemName;
		        if('人均维保数量'==itemName){
                    if(value== '0')
                        return '';
			        }
		        else
			        return value;
		        }},
	        {field:'itembz',title:'评分备注',width:$(this).width() * 0.15},  
	        {field:'ratingCompanyName',title:'评分单位',width:$(this).width() * 0.15},    
	        {field:'ratingUserName',title:'评分人',width:$(this).width() * 0.15},   
	        {field:'detailratingDate',title:'评分时间',width:$(this).width() * 0.15}   
	    ]],
	    singleSelect:true,
	    striped:true   
	});  
		
	 $('.datatable tr').click(function() {
    //     alert($(this).find("td :eq(1)").html().trim());  //.find("input").val();
   //      alert($(this).find("td :eq(1)").find("input").attr("id"));
  //       alert($(this).find("td :eq(4)").attr("id"));
   //	   alert($(this).index()+1);
			
		   var columnName ="";
		   var index =0;
		   index =$(this).index()+1;

		   if($(this).index()+1 == 1)
			   columnName = "officeSpace"; 
		/*   if($(this).index()+1 == 2)
			   columnName ="headQuarters"; */
		   if($(this).index()+1 == 2)
			   columnName ="maintenanceEleCount";
		   if($(this).index()+1 == 3)
			   columnName ="avgmaintenanceEleCount";
		   if($(this).index()+1 == 4)
			   columnName ="fixedTelOnDuty";
		   if($(this).index()+1 == 5)
			   columnName ="telOnDutyunattendedTimes";
		   if($(this).index()+1 == 6)
			   columnName ="enterpriseChangeTimes";
		   if($(this).index()+1 == 7)
			   columnName ="enterpriseRecord";
		   if($(this).index()+1 == 8)
			   columnName ="infoComRate";
		   if($(this).index()+1 == 9)
			   columnName ="sweepCodeRate";
		   if($(this).index()+1 == 10)
			   columnName ="sweepCodeInTimeRate";
		   if($(this).index()+1 == 11)
			   columnName ="alarmDealwith";
		   if($(this).index()+1 == 12)
			   columnName ="regularInspectionTimes";
		   if($(this).index()+1 == 13)
			   columnName ="inspectElevatorTimes";
		   if($(this).index()+1 == 14)
			   columnName ="acceptInspElevatorTimes";   
		   if($(this).index()+1 == 15)
			   columnName ="maintenSceneInfoTimes";
		   if($(this).index()+1 == 16)
			   columnName ="malignantEventsTimes";
		   if($(this).index()+1 == 17)
			   columnName ="complaintsEventsTimes";
		   if($(this).index()+1 == 18)
			   columnName ="maintenBusinessTimes";
		   if($(this).index()+1 == 19)
			   columnName ="honestTimes";
		   if($(this).index()+1 == 20)
			   columnName ="punishmentTimes";
		   if($(this).index()+1 == 21)
			   columnName ="firstRescueTimes";
		   if($(this).index()+1 == 22)
			   columnName ="secondRescueTimes";
		   if($(this).index()+1 == 23)
			   columnName ="secondRescuePoint";
		   if($(this).index()+1 == 24)
			   columnName ="rescueResponseTimes";
		   if($(this).index()+1 == 25)
			   columnName ="tiringPeopleTimes";
		   if($(this).index()+1 == 26)
			   columnName ="positiveEnergyTimes";
		   if($(this).index()+1 == 27)
			   columnName ="expertsSuggestionTimes";
		   if($(this).index()+1 == 28)
			   columnName ="positiveWork";
		   if($(this).index()+1 == 29)
			   columnName ="remoteMonitor";
		   if($(this).index()+1 == 30)
			   columnName ="elevatorInsurance";
		   if($(this).index()+1 == 31)
			   columnName ="techinnovation";
           
		   if(columnName != ""){
			   var ywCompanyID = $('#ywCompanyIdinfo2').attr("value");
			   var ratingDate  = $('#ratingDate').attr("value");  
		//     var ratingDate  = $('#ratingDate2').datebox("getValue");
			   if(ywCompanyID > 0){
			   $('#bztb').datagrid({   
				    url:'/tcweb/elevator/ywSysSetingsItemDetail',   
				    queryParams:{"ItemName":columnName,"ywCompanyID":$('#ywCompanyIdinfo2').attr("value"),"ratingDate":ratingDate}
				});  
			   }
		   }
     });
    
	roleControl();
    
	form =$("form[name='myform']");
	form.url='/tcweb/elevator/ywSysSetings';

	$('#sbtn-save').linkbutton();
	$('#bzsbtn-save').linkbutton();

	$('#myform').form({onLoadSuccess:sjhjtotal});  

    

	$('#hiddentr').hide();
	
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

         /*
	        $('#headQuarters').combobox({
	        	onChange: function (n,o) {
	             var headQuarters=$('#headQuarters').combobox('getValue');  
	        	 var headQuartersjc = $('#headQuartersjc').attr("value");
	        	 if(headQuarters == 0)
	        		 $('#headQuarterssj').attr("value",parseInt(headQuartersjc)+10);
	        	 else
	        		 $('#headQuarterssj').attr("value",headQuartersjc);

	        	 sjhjtotal();
	        }
		        }); */

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
	        
	 /*
	  $('#telOnDutyunattendedButton').click(function (event) {  
		                  //取消事件冒泡  
		             //     event.stopPropagation();  
		                  //设置弹出层的位置  
		                 var offset = $(event.target).offset();  
		                 $('#telOnDutyunattendedWin').window('open').window('resize',{top:offset.top,left:offset.left});
		        
		              }); 

	  
	  $('#enterpriseChangeButton').click(function (event) {  
         var offset = $(event.target).offset();  
         $('#enterpriseChangeWin').window('open').window('resize',{top:offset.top,left:offset.left});

      });
      
	  
	  $('#regularInspectionButton').click(function (event) {  
         var offset = $(event.target).offset();  
         $('#regularInspectionWin').window('open').window('resize',{top:offset.top,left:offset.left});

      });
     */

	  $('#telOnDutyunattendedTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var telOnDutyunattended=$('#telOnDutyunattended option:selected').val();
		    var telOnDutyunattendedjc =$('#telOnDutyunattendedjc').attr("value");
		//	if(telOnDutyunattended == 1 ){  //无人值守
		    $('#telOnDutyunattendedsj').attr("value",telOnDutyunattendedjc-5*newValue);
		    sjhjtotal();
		//	}
		//	else{
		//    $('#telOnDutyunattendedsj').attr("value",telOnDutyunattendedjc);
		//		}
		    }
	    });  

	  
	  $('#enterpriseChangeTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var enterpriseChange=$('#enterpriseChange option:selected').val();
		    var enterpriseChangejc =$('#enterpriseChangejc').attr("value");
	//		if(enterpriseChange == 1 ){  //
		    $('#enterpriseChangesj').attr("value",enterpriseChangejc-10*newValue);
		    sjhjtotal();
		//	}
		//	else{
		//    $('#enterpriseChangesj').attr("value",enterpriseChangejc);
		//		}
		    }
	    });  
	  
	  $('#regularInspectionTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var regularInspection=$('#regularInspection option:selected').val();    
			var regularInspectionjc =$('#regularInspectionjc').attr("value");
		//	if(regularInspection == 1 ){  //不合格检验
		    $('#regularInspectionsj').attr("value",regularInspectionjc-2*newValue);
		    sjhjtotal();
		//	}
		//	else{
		//    $('#regularInspectionsj').attr("value",regularInspectionjc);
		//		}
		    }
	    }); 

	  
	  $('#inspectElevatorTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var inspectElevator=$('#inspectElevator option:selected').val();    
			var inspectElevatorjc =$('#inspectElevatorjc').attr("value");
		//	if(inspectElevator == 1 ){  //不合格
		    $('#inspectElevatorsj').attr("value",inspectElevatorjc-5*newValue-20*$('#inspectElevatorTimes2').numberspinner('getValue'));
		    sjhjtotal();
		//	}
		//	else{
		//    $('#inspectElevatorsj').attr("value",inspectElevatorjc);
		//		}
		    }
	    });  

	  $('#inspectElevatorTimes2').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var inspectElevator=$('#inspectElevator option:selected').val();    
			var inspectElevatorjc =$('#inspectElevatorjc').attr("value");
		//	if(inspectElevator == 1 ){  //不合格
		    $('#inspectElevatorsj').attr("value",inspectElevatorjc-20*newValue-5*$('#inspectElevatorTimes').numberspinner('getValue'));
		    sjhjtotal();
		//	}
		//	else{
		 //   $('#inspectElevatorsj').attr("value",inspectElevatorjc);
		//		}
		    }
	    });  
	      
	  
	  $('#acceptInspElevatorTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
	//	    var acceptInspElevator=$('#acceptInspElevator option:selected').val();    
			var acceptInspElevatorjc =$('#acceptInspElevatorjc').attr("value");
	//		if(acceptInspElevator == 1 ){  //不合格
		    $('#acceptInspElevatorsj').attr("value",acceptInspElevatorjc-10*newValue);
		    sjhjtotal();
	//		}
	//		else{
	//	    $('#acceptInspElevatorsj').attr("value",acceptInspElevatorjc);
	//			}
		    }
	    });  

	  $('#maintenSceneInfoTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
	//	    var maintenSceneInfo=$('#maintenSceneInfo option:selected').val();    
			var maintenSceneInfojc =$('#maintenSceneInfojc').attr("value");
	//		if(maintenSceneInfo == 1 ){  //不合格
		    $('#maintenSceneInfosj').attr("value",maintenSceneInfojc-10*newValue);
		    sjhjtotal();
	//		}
	//		else{
	//	    $('#maintenSceneInfosj').attr("value",maintenSceneInfojc);
	//			}
		    }
	    });  

	  
	  $('#malignantEventsTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var malignantEvents=$('#malignantEvents option:selected').val();    
			var malignantEventsjc =$('#malignantEventsjc').attr("value");
		//	if(malignantEvents == 1 ){  //有
		    $('#malignantEventssj').attr("value",malignantEventsjc-30*newValue-50*$('#malignantEventsTimes2').numberspinner('getValue')-1000*$('#malignantEventsTimes3').numberspinner('getValue'));
		    sjhjtotal();
		//	}
		//	else{
		//    $('#malignantEventssj').attr("value",malignantEventsjc);
		//		}
		    }
	    }); 

	  
	  $('#malignantEventsTimes2').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		 //   var malignantEvents=$('#malignantEvents option:selected').val();    
			var malignantEventsjc =$('#malignantEventsjc').attr("value");
		//	if(malignantEvents == 1 ){  //有
		    $('#malignantEventssj').attr("value",malignantEventsjc-50*newValue-30*$('#malignantEventsTimes').numberspinner('getValue')-1000*$('#malignantEventsTimes3').numberspinner('getValue'));
		    sjhjtotal();
		//	}
		//	else{
		//    $('#malignantEventssj').attr("value",malignantEventsjc);
		//		}
		    }
	    });  

	  $('#malignantEventsTimes3').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var malignantEvents=$('#malignantEvents option:selected').val();    
			var malignantEventsjc =$('#malignantEventsjc').attr("value");
		//	if(malignantEvents == 1 ){  //有
		    $('#malignantEventssj').attr("value",malignantEventsjc-1000*newValue-30*$('#malignantEventsTimes').numberspinner('getValue')-50*$('#malignantEventsTimes2').numberspinner('getValue'));
		    sjhjtotal();
	//		}
	//		else{
	//	    $('#malignantEventssj').attr("value",malignantEventsjc);
		//		}
		    }
	    });    

	  
	  $('#complaintsEventsTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var complaintsEvents=$('#complaintsEvents option:selected').val();    
			var complaintsEventsjc =$('#complaintsEventsjc').attr("value");
		//	if(complaintsEvents == 1 ){  //有
		    $('#complaintsEventssj').attr("value",complaintsEventsjc-5*newValue-20*$('#complaintsEventsTimes2').numberspinner('getValue'));
		    sjhjtotal();
		//	}
		//	else{
		//    $('#complaintsEventssj').attr("value",complaintsEventsjc);
		//		}
		    }
	    }); 

	  $('#complaintsEventsTimes2').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var complaintsEvents=$('#complaintsEvents option:selected').val();    
			var complaintsEventsjc =$('#complaintsEventsjc').attr("value");
		//	if(complaintsEvents == 1 ){  //有
		    $('#complaintsEventssj').attr("value",complaintsEventsjc-20*newValue-5*$('#complaintsEventsTimes').numberspinner('getValue'));
		    sjhjtotal();
		//	}
		//	else{
		//    $('#complaintsEventssj').attr("value",complaintsEventsjc);
		//		}
		    }
	    });

	  
	  $('#maintenBusinessTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var maintenBusiness=$('#maintenBusiness option:selected').val();    
			var maintenBusinessjc =$('#maintenBusinessjc').attr("value");
		//	if(maintenBusiness == 1 ){  //有
		    $('#maintenBusinesssj').attr("value",maintenBusinessjc-20*newValue);
		    sjhjtotal();
		//	}
		//	else{
		//    $('#maintenBusinesssj').attr("value",maintenBusinessjc);
		//		}
		    }
	    });    

	  
	  $('#honestTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
	//	    var honest=$('#honest option:selected').val();    
			var honestjc =$('#honestjc').attr("value");
	//		if(honest == 1 ){  //有
		    $('#honestsj').attr("value",honestjc-20*newValue);
		    sjhjtotal();
	//		}
	//		else{
	//	    $('#honestsj').attr("value",honestjc);
	//			}
		    }
	    });  

	  $('#punishmentTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var punishment=$('#punishment option:selected').val();    
			var punishmentjc =$('#punishmentjc').attr("value");
		//	if(punishment == 1 ){  //有
		    $('#punishmentsj').attr("value",punishmentjc-2*newValue-5*$('#punishmentTimes2').numberspinner('getValue')-20*$('#punishmentTimes3').numberspinner('getValue')-20*$('#punishmentTimes4').numberspinner('getValue'));
		    sjhjtotal();
		//	}
		//	else{
		//    $('#punishmentsj').attr("value",punishmentjc);
		//		}
		    }
	    }); 

	  $('#punishmentTimes2').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
	//	    var punishment=$('#punishment option:selected').val();    
			var punishmentjc =$('#punishmentjc').attr("value");
	//		if(punishment == 1 ){  //有
		    $('#punishmentsj').attr("value",punishmentjc-5*newValue-2*$('#punishmentTimes').numberspinner('getValue')-20*$('#punishmentTimes3').numberspinner('getValue')-20*$('#punishmentTimes4').numberspinner('getValue'));
		    sjhjtotal();
	//		}
	//		else{
	//	    $('#punishmentsj').attr("value",punishmentjc);
	//			}
		    }
	    });

	  $('#punishmentTimes3').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
	//	    var punishment=$('#punishment option:selected').val();    
			var punishmentjc =$('#punishmentjc').attr("value");
	//		if(punishment == 1 ){  //有
		    $('#punishmentsj').attr("value",punishmentjc-20*newValue-2*$('#punishmentTimes').numberspinner('getValue')-5*$('#punishmentTimes2').numberspinner('getValue')-20*$('#punishmentTimes3').numberspinner('getValue'));
		    sjhjtotal();
	//		}
	//		else{
	//	    $('#punishmentsj').attr("value",punishmentjc);
	//			}
		    }
	    });  

	  $('#punishmentTimes4').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
	//	    var punishment=$('#punishment option:selected').val();    
			var punishmentjc =$('#punishmentjc').attr("value");
	//		if(punishment == 1 ){  //有
		    $('#punishmentsj').attr("value",punishmentjc-20*newValue-2*$('#punishmentTimes').numberspinner('getValue')-5*$('#punishmentTimes2').numberspinner('getValue')-20*$('#punishmentTimes3').numberspinner('getValue'));
		    sjhjtotal();
	//		}
	//		else{
	//	    $('#punishmentsj').attr("value",punishmentjc);
	//			}
		    }
	    });               

	  
	  $('#firstRescueTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var firstRescue=$('#firstRescue option:selected').val();    
			var firstRescuejc =$('#firstRescuejc').attr("value");
		//	if(firstRescue == 1 ){  //有
		    $('#firstRescuesj').attr("value",firstRescuejc-5*newValue);
		    sjhjtotal();
		//	}
	//		else{
	//	    $('#firstRescuesj').attr("value",firstRescuejc);
	//			}
		    }
	    });  

	  $('#secondRescueTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var secondRescue=$('#secondRescue option:selected').val();  
		//    var secondRescue2=$('#secondRescue2 option:selected').val();     
			var secondRescuejc =$('#secondRescuejc').attr("value");
		//	if(secondRescue == 1 ){  //有
		//		if(secondRescue2 == 1){
		           $('#secondRescuesj').attr("value",parseInt(secondRescuejc-5*newValue)+3*$('#secondRescueTimes2').numberspinner('getValue'));
		           sjhjtotal();
			//	}
		//		else{
		//		   $('#secondRescuesj').attr("value",secondRescuejc-5*newValue);
		//			}
		//	}
		/*	else{
				if(secondRescue2 == 1){
					$('#secondRescuesj').attr("value",parseInt(secondRescuejc)+3*$('#secondRescueTimes2').numberspinner('getValue'));
					}
				else{
		           $('#secondRescuesj').attr("value",secondRescuejc);
				}
				} */
		    }
	    });

	  $('#secondRescueTimes2').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		//    var secondRescue=$('#secondRescue option:selected').val(); 
		//    var secondRescue2=$('#secondRescue2 option:selected').val();    
			var secondRescuejc =$('#secondRescuejc').attr("value");
		//	if(secondRescue2 == 1 ){  //有
		//		if(secondRescue == 1 ){
				 $('#secondRescuesj').attr("value",parseInt(secondRescuejc)+3*newValue-5*$('#secondRescueTimes').numberspinner('getValue'));
				 sjhjtotal();
			//		}
			//	else{
		    //       $('#secondRescuesj').attr("value",parseInt(secondRescuejc)+3*newValue);
			//	}
		//	}
	/*		else{
				if(secondRescue == 1 ){
					$('#secondRescuesj').attr("value",secondRescuejc-5*$('#secondRescueTimes').numberspinner('getValue'));
					}
				else{
		            $('#secondRescuesj').attr("value",secondRescuejc);
				}
				} */
		    }
	    });           

	  
	  $('#rescueResponseTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var rescueResponse=$('#rescueResponse option:selected').val();    
			var rescueResponsejc =$('#rescueResponsejc').attr("value");
		//	if(rescueResponse == 1 ){  //有
		    $('#rescueResponsesj').attr("value",rescueResponsejc-10*newValue);
		    sjhjtotal();
		//	}
		//	else{
		//    $('#rescueResponsesj').attr("value",rescueResponsejc);
		//		}
		    }
	    });  

	  
	  $('#tiringPeopleTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var tiringPeople=$('#tiringPeople option:selected').val();    
			var tiringPeoplejc =$('#tiringPeoplejc').attr("value");
		//	if(tiringPeople == 1 ){  //有
		    $('#tiringPeoplesj').attr("value",tiringPeoplejc-3*newValue);
		    sjhjtotal();
		//	}
		//	else{
		//    $('#tiringPeoplesj').attr("value",tiringPeoplejc);
		//		}
		    }
	    });

	   
	  $('#positiveEnergyTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
	//	    var positiveEnergy=$('#positiveEnergy option:selected').val();    
			var positiveEnergyjc =$('#positiveEnergyjc').attr("value");
	//		if(positiveEnergy == 1 ){  //有
		    $('#positiveEnergysj').attr("value",parseInt(positiveEnergyjc)+parseInt(10*newValue));
		    sjhjtotal();
	//		}
	//		else{
	//	    $('#positiveEnergysj').attr("value",positiveEnergyjc);
	//			}
		    }
	    }); 

	  
	  $('#expertsSuggestionTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
	//	    var expertsSuggestion=$('#expertsSuggestion option:selected').val();    
			var expertsSuggestionjc =$('#expertsSuggestionjc').attr("value");
	//		if(expertsSuggestion == 1 ){  //有
		    $('#expertsSuggestionsj').attr("value",parseInt(expertsSuggestionjc)+5*newValue);
		    sjhjtotal();
	//		}
	//		else{
	//	    $('#expertsSuggestionsj').attr("value",expertsSuggestionjc);
	//			}
		    }
	    }); 

	  
	  $('#techinnovationTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var techinnovation=$('#techinnovation option:selected').val();    
			var techinnovationjc =$('#techinnovationjc').attr("value");
		//	if(techinnovation == 1 ){  //有
		    $('#techinnovationsj').attr("value",parseInt(techinnovationjc)+1*newValue+parseInt(2*$('#techinnovationTimes2').numberspinner('getValue'))+parseInt(3*$('#techinnovationTimes3').numberspinner('getValue'))+parseInt(4*$('#techinnovationTimes4').numberspinner('getValue'))+parseInt(5*$('#techinnovationTimes5').numberspinner('getValue')));
		    sjhjtotal();
		//	}
		//	else{
		//    $('#techinnovationsj').attr("value",techinnovationjc);
		//		}
		    }
	    }); 

	  $('#techinnovationTimes2').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var techinnovation=$('#techinnovation option:selected').val();    
			var techinnovationjc =$('#techinnovationjc').attr("value");
		//	if(techinnovation == 1 ){  //有
		    $('#techinnovationsj').attr("value",parseInt(techinnovationjc)+2*newValue+parseInt(1*$('#techinnovationTimes').numberspinner('getValue'))+parseInt(3*$('#techinnovationTimes3').numberspinner('getValue'))+parseInt(4*$('#techinnovationTimes4').numberspinner('getValue'))+parseInt(5*$('#techinnovationTimes5').numberspinner('getValue')));
		    sjhjtotal();
		//	}
		//	else{
		//    $('#techinnovationsj').attr("value",techinnovationjc);
		//		}
		    }
	    }); 

	  $('#techinnovationTimes3').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var techinnovation=$('#techinnovation option:selected').val();    
			var techinnovationjc =$('#techinnovationjc').attr("value");
		//	if(techinnovation == 1 ){  //有
		    $('#techinnovationsj').attr("value",parseInt(techinnovationjc)+3*newValue+parseInt(1*$('#techinnovationTimes').numberspinner('getValue'))+parseInt(2*$('#techinnovationTimes2').numberspinner('getValue'))+parseInt(4*$('#techinnovationTimes4').numberspinner('getValue'))+parseInt(5*$('#techinnovationTimes5').numberspinner('getValue')));
		    sjhjtotal();
		//	}
		//	else{
	//	    $('#techinnovationsj').attr("value",techinnovationjc);
	//			}
		    }
	    }); 

	  $('#techinnovationTimes4').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var techinnovation=$('#techinnovation option:selected').val();    
			var techinnovationjc =$('#techinnovationjc').attr("value");
		//	if(techinnovation == 1 ){  //有
		    $('#techinnovationsj').attr("value",parseInt(techinnovationjc)+4*newValue+parseInt(1*$('#techinnovationTimes').numberspinner('getValue'))+parseInt(2*$('#techinnovationTimes2').numberspinner('getValue'))+parseInt(3*$('#techinnovationTimes3').numberspinner('getValue'))+parseInt(5*$('#techinnovationTimes5').numberspinner('getValue')));
		    sjhjtotal();
		//	}
		//	else{
		//    $('#techinnovationsj').attr("value",techinnovationjc);
		//		}
		    }
	    }); 

	  $('#techinnovationTimes5').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var techinnovation=$('#techinnovation option:selected').val();    
			var techinnovationjc =$('#techinnovationjc').attr("value");
		//	if(techinnovation == 1 ){  //有
		    $('#techinnovationsj').attr("value",parseInt(techinnovationjc)+5*newValue+parseInt(1*$('#techinnovationTimes').numberspinner('getValue'))+parseInt(2*$('#techinnovationTimes2').numberspinner('getValue'))+parseInt(3*$('#techinnovationTimes3').numberspinner('getValue'))+parseInt(4*$('#techinnovationTimes4').numberspinner('getValue')));
		    sjhjtotal();
		//	}
		//	else{
		//    $('#techinnovationsj').attr("value",techinnovationjc);
		//		}
		    }
	    });   
	 
   //合计基础总分
	hjtotal();
	//初始化实际得分
	initial();
	
	
//	form.form('load', '/tcweb/elevator/sysYwcqSetingsEdit/'); 

	

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

	    //限制只能输入整数
	    $("#officeSpace").keyup(function(){ 
          $(this).val($(this).val().replace(/\D|^0/g,'')); 
          }).bind("paste",function(){
            $(this).val($(this).val().replace(/\D|^0/g,'')); 
          });

	    $("#positiveWork").keyup(function(){ 
	          $(this).val($(this).val().replace(/\D|^0/g,'')); 
	          }).bind("paste",function(){
	            $(this).val($(this).val().replace(/\D|^0/g,'')); 
	          });

	    $("#remoteMonitor").keyup(function(){ 
	          $(this).val($(this).val().replace(/\D|^0/g,'')); 
	          }).bind("paste",function(){
	            $(this).val($(this).val().replace(/\D|^0/g,'')); 
	          });

	    $("#elevatorInsurance").keyup(function(){ 
	          $(this).val($(this).val().replace(/\D|^0/g,'')); 
	          }).bind("paste",function(){
	            $(this).val($(this).val().replace(/\D|^0/g,'')); 
	          });
	    
	    //限制不能输入单引号
	    $("#beizhutext").keyup(function(){ 
	          $(this).val($(this).val().replace(/\'/g,'')); 
	          }).bind("paste",function(){
	            $(this).val($(this).val().replace(/\'/g,'')); 
	          });
	  
	    
	    
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
/*
function headQuartersValue(){
	 var headQuarters=$('#headQuarters option:selected').val();
	 var headQuartersjc = $('#headQuartersjc').attr("value");
	 if(headQuarters == 0)
		 $('#headQuarterssj').attr("value",parseInt(headQuartersjc)+10);
	 else
		 $('#headQuarterssj').attr("value",headQuartersjc);
     }; */

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

function malignantEventsValue(){
	var malignantEvents=$('#malignantEvents option:selected').val();
	var malignantEventsjc =$('#malignantEventsjc').attr("value");
	if(malignantEvents == 1 ){
		$('#malignantEventsTimes').numberspinner('enable');
		$('#malignantEventsTimes2').numberspinner('enable');
		$('#malignantEventsTimes3').numberspinner('enable');
		$('#malignantEventssj').attr("value",malignantEventsjc-30*$('#malignantEventsTimes').numberspinner('getValue')-50*$('#malignantEventsTimes2').numberspinner('getValue')-1000*$('#malignantEventsTimes3').numberspinner('getValue'));
		}
	else{
		$('#malignantEventsTimes').numberspinner('setValue',0);  
		$('#malignantEventsTimes2').numberspinner('setValue',0);   
		$('#malignantEventsTimes3').numberspinner('setValue',0);     
		$('#malignantEventsTimes').numberspinner('disable');
		$('#malignantEventsTimes2').numberspinner('disable');
		$('#malignantEventsTimes3').numberspinner('disable');
		$('#malignantEventssj').attr("value",malignantEventsjc);	
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

function firstRescueValue(){
	var firstRescue=$('#firstRescue option:selected').val();
	var firstRescuejc =$('#firstRescuejc').attr("value");
	if(firstRescue == 1 ){
		$('#firstRescueTimes').numberspinner('enable');
		$('#firstRescuesj').attr("value",firstRescuejc-5*$('#firstRescueTimes').numberspinner('getValue'));
		}
	else{
		$('#firstRescueTimes').numberspinner('setValue',0);   
		$('#firstRescueTimes').numberspinner('disable');
		$('#firstRescuesj').attr("value",firstRescuejc);	
		}
}

function secondRescueValue(){
	var secondRescue=$('#secondRescue option:selected').val();
	var secondRescue2=$('#secondRescue2 option:selected').val();
	var secondRescuejc =$('#secondRescuejc').attr("value");
	if(secondRescue == 1 ){
		if(secondRescue2 == 1){ 
			$('#secondRescueTimes').numberspinner('enable');
			$('#secondRescuesj').attr("value",parseInt(secondRescuejc)+3*$('#secondRescueTimes2').numberspinner('getValue')-5*$('#secondRescueTimes').numberspinner('getValue'));
			}
		else{
		    $('#secondRescueTimes').numberspinner('enable');
		    $('#secondRescuesj').attr("value",secondRescuejc-5*$('#secondRescueTimes').numberspinner('getValue'));
		}
		}
	else{
		if(secondRescue2 == 1){ 
			$('#secondRescueTimes').numberspinner('setValue',0);
			$('#secondRescueTimes').numberspinner('disable');  
			$('#secondRescuesj').attr("value",parseInt(secondRescuejc)+3*$('#secondRescueTimes2').numberspinner('getValue'));	 
			}
		else{
		   $('#secondRescueTimes').numberspinner('setValue',0);   
		   $('#secondRescueTimes').numberspinner('disable');
		   $('#secondRescuesj').attr("value",secondRescuejc);	
		}
		}
}

function secondRescueValue2(){
	var secondRescue=$('#secondRescue option:selected').val();
	var secondRescue2=$('#secondRescue2 option:selected').val();
	var secondRescuejc =$('#secondRescuejc').attr("value");
	if(secondRescue2 == 1 ){
		if(secondRescue == 1){ 
			$('#secondRescueTimes2').numberspinner('enable');
			$('#secondRescuesj').attr("value",parseInt(secondRescuejc)+3*$('#secondRescueTimes2').numberspinner('getValue')-5*$('#secondRescueTimes').numberspinner('getValue'));
			}
		else{
		    $('#secondRescueTimes2').numberspinner('enable');
		    $('#secondRescuesj').attr("value",parseInt(secondRescuejc)-3*$('#secondRescueTimes2').numberspinner('getValue'));
		}
		}
	else{
		if(secondRescue == 1){ 
			$('#secondRescueTimes2').numberspinner('setValue',0);
			$('#secondRescueTimes2').numberspinner('disable');  
			$('#secondRescuesj').attr("value",secondRescuejc-3*$('#secondRescueTimes').numberspinner('getValue'));	 
			}
		else{
		   $('#secondRescueTimes2').numberspinner('setValue',0);   
		   $('#secondRescueTimes2').numberspinner('disable');
		   $('#secondRescuesj').attr("value",secondRescuejc);	
		}
		}
}


function  secondRescuePointValue(){
	var secondRescuePoint = $('#secondRescuePoint').attr("value");
	var secondRescuePointjc = $('#secondRescuePointjc').attr("value");
	if(secondRescuePoint >= 10 && secondRescuePoint <= 19)
		$('#secondRescuePointsj').attr("value", parseInt(secondRescuePointjc)+1);
	else if(secondRescuePoint >= 20 && secondRescuePoint <= 29)
		$('#secondRescuePointsj').attr("value", parseInt(secondRescuePointjc)+2);
	else if(secondRescuePoint >= 30 && secondRescuePoint <= 39)
		$('#secondRescuePointsj').attr("value", parseInt(secondRescuePointjc)+3);
	else if(secondRescuePoint >= 40)
		$('#secondRescuePointsj').attr("value", parseInt(secondRescuePointjc)+4);
	else
		$('#secondRescuePointsj').attr("value",secondRescuePointjc);
	sjhjtotal();
}


function rescueResponseValue(){
	var rescueResponse=$('#rescueResponse option:selected').val();
	var rescueResponsejc =$('#rescueResponsejc').attr("value");
	if(rescueResponse == 1 ){
		$('#rescueResponseTimes').numberspinner('enable');
		$('#rescueResponsesj').attr("value",rescueResponsejc-5*$('#rescueResponseTimes').numberspinner('getValue'));
		}
	else{
		$('#rescueResponseTimes').numberspinner('setValue',0);   
		$('#rescueResponseTimes').numberspinner('disable');
		$('#rescueResponsesj').attr("value",rescueResponsejc);	
		}
}


function tiringPeopleValue(){
	var tiringPeople=$('#tiringPeople option:selected').val();
	var tiringPeoplejc =$('#tiringPeoplejc').attr("value");
	if(tiringPeople == 1 ){
		$('#tiringPeopleTimes').numberspinner('enable');
		$('#tiringPeoplesj').attr("value",tiringPeoplejc-3*$('#tiringPeopleTimes').numberspinner('getValue'));
		}
	else{
		$('#tiringPeopleTimes').numberspinner('setValue',0);   
		$('#tiringPeopleTimes').numberspinner('disable');
		$('#tiringPeoplesj').attr("value",tiringPeoplejc);	
		}
}


function positiveEnergyValue(){
	var positiveEnergy=$('#positiveEnergy option:selected').val();
	var positiveEnergyjc =$('#positiveEnergyjc').attr("value");
	if(positiveEnergy == 1 ){
		$('#positiveEnergyTimes').numberspinner('enable');
		$('#positiveEnergysj').attr("value",parseInt(positiveEnergyjc)+10*$('#positiveEnergyTimes').numberspinner('getValue'));
		}
	else{
		$('#positiveEnergyTimes').numberspinner('setValue',0);   
		$('#positiveEnergyTimes').numberspinner('disable');
		$('#positiveEnergysj').attr("value",positiveEnergyjc);	
		}
}


function expertsSuggestionValue(){
	var expertsSuggestion=$('#expertsSuggestion option:selected').val();
	var expertsSuggestionjc =$('#expertsSuggestionjc').attr("value");
	if(expertsSuggestion == 1 ){
		$('#expertsSuggestionTimes').numberspinner('enable');
		$('#expertsSuggestionsj').attr("value",parseInt(expertsSuggestionjc)+5*$('#expertsSuggestionTimes').numberspinner('getValue'));
		}
	else{
		$('#expertsSuggestionTimes').numberspinner('setValue',0);   
		$('#expertsSuggestionTimes').numberspinner('disable');
		$('#expertsSuggestionsj').attr("value",expertsSuggestionjc);	
		}
}



function positiveWorkValue(){
	var positiveWork=$('#positiveWork').attr("value");
	var positiveWorkjc =$('#positiveWorkjc').attr("value");
	if(positiveWork != "" ){
		$('#positiveWorksj').attr("value",parseInt(positiveWork)+parseInt(positiveWorkjc));
		}
	else{
		$('#positiveWorksj').attr("value",positiveWorkjc);	
		}
	sjhjtotal();
}

function remoteMonitorValue(){
	var remoteMonitor = $('#remoteMonitor').attr("value");
	var remoteMonitorjc = $('#remoteMonitorjc').attr("value");
	if(remoteMonitor !="" ){
		$('#remoteMonitorsj').attr("value",parseInt(remoteMonitor/30)*1+parseInt(remoteMonitorjc));
		}
	else{
		$('#remoteMonitorsj').attr("value",remoteMonitorjc);   
		}
	sjhjtotal();
}


function elevatorInsuranceValue(){
	var elevatorInsurance = $('#elevatorInsurance').attr("value");
	var elevatorInsurancejc = $('#elevatorInsurancejc').attr("value");
	if(elevatorInsurance !="" ){
		$('#elevatorInsurancesj').attr("value",parseInt(elevatorInsurance/50)*1+parseInt(elevatorInsurancejc));
		}
	else{
		$('#elevatorInsurancesj').attr("value",elevatorInsurancejc);   
		}
	sjhjtotal();
}

function techinnovationValue(){
	var techinnovation=$('#techinnovation option:selected').val();
	var techinnovationjc =$('#techinnovationjc').attr("value");
	if(techinnovation == 1 ){
		$('#techinnovationTimes').numberspinner('enable');
		$('#techinnovationTimes2').numberspinner('enable');
		$('#techinnovationTimes3').numberspinner('enable');
		$('#techinnovationTimes4').numberspinner('enable');
		$('#techinnovationTimes5').numberspinner('enable');
		$('#techinnovationsj').attr("value",parseInt(techinnovationjc)+1*$('#techinnovationTimes').numberspinner('getValue')+2*$('#techinnovationTimes2').numberspinner('getValue')+3*$('#techinnovationTimes3').numberspinner('getValue')+4*$('#techinnovationTimes4').numberspinner('getValue')+5*$('#techinnovationTimes5').numberspinner('getValue'));
		}
	else{
		$('#techinnovationTimes').numberspinner('setValue',0);   
		$('#techinnovationTimes').numberspinner('disable');
		$('#techinnovationTimes2').numberspinner('setValue',0);   
		$('#techinnovationTimes2').numberspinner('disable');
		$('#techinnovationTimes3').numberspinner('setValue',0);   
		$('#techinnovationTimes3').numberspinner('disable');
		$('#techinnovationTimes4').numberspinner('setValue',0);   
		$('#techinnovationTimes4').numberspinner('disable');
		$('#techinnovationTimes5').numberspinner('setValue',0);   
		$('#techinnovationTimes5').numberspinner('disable');
		$('#techinnovationsj').attr("value",techinnovationjc);	
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
		
	var maintenanceEleCountsj =$('#maintenanceEleCountjc').attr("value");
	$('#maintenanceEleCountsj').attr("value",maintenanceEleCountsj);
	var avgmaintenanceEleCountsj =$('#avgmaintenanceEleCountjc').attr("value");
	$('#avgmaintenanceEleCountsj').attr("value",avgmaintenanceEleCountsj);
	
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

	
	var infoComRatesj =$('#infoComRatejc').attr("value");
	$('#infoComRatesj').attr("value",infoComRatesj);
	var sweepCodeRatesj =$('#sweepCodeRatejc').attr("value");
	$('#sweepCodeRatesj').attr("value",sweepCodeRatesj);
	var sweepCodeInTimeRatesj =$('#sweepCodeInTimeRatejc').attr("value");
	$('#sweepCodeInTimeRatesj').attr("value",sweepCodeInTimeRatesj);
	var alarmDealwithsj =$('#alarmDealwithjc').attr("value");
	$('#alarmDealwithsj').attr("value",alarmDealwithsj);

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


	var malignantEventsTimes =$('#malignantEventsTimes').numberspinner('getValue');
	var malignantEventsTimes2 =$('#malignantEventsTimes2').numberspinner('getValue');
	var malignantEventsTimes3 =$('#malignantEventsTimes3').numberspinner('getValue');
	var malignantEventssj =$('#malignantEventsjc').attr("value")-30*malignantEventsTimes-50*malignantEventsTimes2-1000*malignantEventsTimes3;
	$('#malignantEventssj').attr("value",malignantEventssj);


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


	var firstRescueTimes =$('#firstRescueTimes').numberspinner('getValue');
	var firstRescuesj =$('#firstRescuejc').attr("value")-5*firstRescueTimes;
	$('#firstRescuesj').attr("value",firstRescuesj);

    var secondRescueTimes =$('#secondRescueTimes').numberspinner('getValue');
    var secondRescueTimes2 =$('#secondRescueTimes2').numberspinner('getValue');
	var secondRescuesj =parseInt($('#secondRescuejc').attr("value"))+parseInt(3*secondRescueTimes2)-5*secondRescueTimes;
	$('#secondRescuesj').attr("value",secondRescuesj);
	
	
	var secondRescuePointjc =$('#secondRescuePointjc').attr("value");
	var secondRescuePoint =$('#secondRescuePoint').attr("value");  
	if(secondRescuePoint == "")
		secondRescuePoint = 0;      
	if(secondRescuePoint >= 10 && secondRescuePoint <= 19)
		$('#secondRescuePointsj').attr("value", parseInt(secondRescuePointjc)+1);
	else if(secondRescuePoint >= 20 && secondRescuePoint <= 29)
		$('#secondRescuePointsj').attr("value", parseInt(secondRescuePointjc)+2);
	else if(secondRescuePoint >= 30 && secondRescuePoint <= 39)
		$('#secondRescuePointsj').attr("value", parseInt(secondRescuePointjc)+3);
	else if(secondRescuePoint >= 40)
		$('#secondRescuePointsj').attr("value", parseInt(secondRescuePointjc)+4);
	else
		$('#secondRescuePointsj').attr("value",secondRescuePointjc);
    var  secondRescuePointsj = $('#secondRescuePointsj').attr("value");
	
	
	
	var rescueResponseTimes =$('#rescueResponseTimes').numberspinner('getValue');
	var rescueResponsesj =$('#rescueResponsejc').attr("value")-10*rescueResponseTimes;
	$('#rescueResponsesj').attr("value",rescueResponsesj);

	
	var tiringPeopleTimes =$('#tiringPeopleTimes').numberspinner('getValue');
	var tiringPeoplesj =$('#tiringPeoplejc').attr("value")-3*tiringPeopleTimes;
	$('#tiringPeoplesj').attr("value",tiringPeoplesj);


	var positiveEnergyTimes =$('#positiveEnergyTimes').numberspinner('getValue');
	var positiveEnergysj =parseInt($('#positiveEnergyjc').attr("value"))+parseInt(10*positiveEnergyTimes);
	$('#positiveEnergysj').attr("value",positiveEnergysj);

	var expertsSuggestionTimes =$('#expertsSuggestionTimes').numberspinner('getValue');
	var expertsSuggestionsj =parseInt($('#expertsSuggestionjc').attr("value"))+parseInt(5*expertsSuggestionTimes);
	$('#expertsSuggestionsj').attr("value",expertsSuggestionsj);

	var positiveWork  =$('#positiveWork').attr("value");
	if(positiveWork == "")
		positiveWork = 0;
	var positiveWorksj =parseInt($('#positiveWorkjc').attr("value"))+parseInt(positiveWork);
	$('#positiveWorksj').attr("value",positiveWorksj);
	
	var remoteMonitor =$('#remoteMonitor').attr("value");
	if(remoteMonitor == "")
		remoteMonitor = 0;
	var remoteMonitorsj =parseInt($('#remoteMonitorjc').attr("value"))+parseInt((remoteMonitor/30)*1);
	$('#remoteMonitorsj').attr("value",remoteMonitorsj);

	var elevatorInsurance =$('#elevatorInsurance').attr("value");
	var elevatorInsurancesj =parseInt($('#elevatorInsurancejc').attr("value"))+parseInt((elevatorInsurance/50)*1);
	$('#elevatorInsurancesj').attr("value",elevatorInsurancesj);
	
	var techinnovationTimes =$('#techinnovationTimes').numberspinner('getValue');
	var techinnovationTimes2 =$('#techinnovationTimes2').numberspinner('getValue');
	var techinnovationTimes3 =$('#techinnovationTimes3').numberspinner('getValue');
	var techinnovationTimes4 =$('#techinnovationTimes4').numberspinner('getValue');
	var techinnovationTimes5 =$('#techinnovationTimes5').numberspinner('getValue');
	var techinnovationsj =parseInt($('#techinnovationjc').attr("value"))+parseInt(1*techinnovationTimes)+parseInt(2*techinnovationTimes2)+parseInt(3*techinnovationTimes3)+parseInt(4*techinnovationTimes4)+parseInt(5*techinnovationTimes5);
	$('#techinnovationsj').attr("value",techinnovationsj);
	

	var sjhjtotal = 0;
//	sjhjtotal = parseInt(officeSpacesj)+parseInt(headQuarterssj)+parseInt(maintenanceEleCountsj)+parseInt(avgmaintenanceEleCountsj)+parseInt(fixedTelOnDutysj)+parseInt(telOnDutyunattendedsj)+parseInt(enterpriseChangesj)+parseInt(enterpriseRecordsj)+parseInt(infoComRatesj)+parseInt(sweepCodeRatesj)+parseInt(sweepCodeInTimeRatesj)+parseInt(alarmDealwithsj)+parseInt(regularInspectionsj)+parseInt(inspectElevatorsj)+parseInt(acceptInspElevatorsj)+parseInt(maintenSceneInfosj)+parseInt(malignantEventssj)+parseInt(complaintsEventssj)+parseInt(maintenBusinesssj)+parseInt(honestsj)+parseInt(punishmentsj)+parseInt(firstRescuesj)+parseInt(secondRescuesj)+parseInt(secondRescuePointsj)+parseInt(rescueResponsesj)+parseInt(tiringPeoplesj)+parseInt(positiveEnergysj)+parseInt(expertsSuggestionsj)+parseInt(positiveWorksj)+parseInt(remoteMonitorsj)+parseInt(elevatorInsurancesj)+parseInt(techinnovationsj);
	sjhjtotal = parseInt(officeSpacesj)+parseInt(maintenanceEleCountsj)+parseInt(avgmaintenanceEleCountsj)+parseInt(fixedTelOnDutysj)+parseInt(telOnDutyunattendedsj)+parseInt(enterpriseChangesj)+parseInt(enterpriseRecordsj)+parseInt(infoComRatesj)+parseInt(sweepCodeRatesj)+parseInt(sweepCodeInTimeRatesj)+parseInt(alarmDealwithsj)+parseInt(regularInspectionsj)+parseInt(inspectElevatorsj)+parseInt(acceptInspElevatorsj)+parseInt(maintenSceneInfosj)+parseInt(malignantEventssj)+parseInt(complaintsEventssj)+parseInt(maintenBusinesssj)+parseInt(honestsj)+parseInt(punishmentsj)+parseInt(firstRescuesj)+parseInt(secondRescuesj)+parseInt(secondRescuePointsj)+parseInt(rescueResponsesj)+parseInt(tiringPeoplesj)+parseInt(positiveEnergysj)+parseInt(expertsSuggestionsj)+parseInt(positiveWorksj)+parseInt(remoteMonitorsj)+parseInt(elevatorInsurancesj)+parseInt(techinnovationsj);
	
	$('#sjhjtotal').attr("value",sjhjtotal);
}

//合计基础总分
function hjtotal(){
	var officeSpacejc =$('#officeSpacejc').attr("value");
//	var headQuartersjc =$('#headQuartersjc').attr("value");
	var maintenanceEleCountjc =$('#maintenanceEleCountjc').attr("value");
	var avgmaintenanceEleCountjc =$('#avgmaintenanceEleCountjc').attr("value");
	var fixedTelOnDutyjc =$('#fixedTelOnDutyjc').attr("value");
	var telOnDutyunattendedjc =$('#telOnDutyunattendedjc').attr("value");
	var enterpriseChangejc =$('#enterpriseChangejc').attr("value");
	var enterpriseRecordjc =$('#enterpriseRecordjc').attr("value");
	var infoComRatejc =$('#infoComRatejc').attr("value");
	var sweepCodeRatejc =$('#sweepCodeRatejc').attr("value");
	var sweepCodeInTimeRatejc =$('#sweepCodeInTimeRatejc').attr("value");
	var alarmDealwithjc =$('#alarmDealwithjc').attr("value");
	var regularInspectionjc =$('#regularInspectionjc').attr("value");
	var inspectElevatorjc =$('#inspectElevatorjc').attr("value");
	var acceptInspElevatorjc =$('#acceptInspElevatorjc').attr("value");
	var maintenSceneInfojc =$('#maintenSceneInfojc').attr("value");
	var malignantEventsjc =$('#malignantEventsjc').attr("value");
	var complaintsEventsjc =$('#complaintsEventsjc').attr("value");
	var maintenBusinessjc =$('#maintenBusinessjc').attr("value");
	var honestjc =$('#honestjc').attr("value");
	var punishmentjc =$('#punishmentjc').attr("value");
	var firstRescuejc =$('#firstRescuejc').attr("value");
	var secondRescuejc =$('#secondRescuejc').attr("value");
	var secondRescuePointjc =$('#secondRescuePointjc').attr("value");
	var rescueResponsejc =$('#rescueResponsejc').attr("value");
	var tiringPeoplejc =$('#tiringPeoplejc').attr("value");
	var positiveEnergyjc =$('#positiveEnergyjc').attr("value");
	var expertsSuggestionjc =$('#expertsSuggestionjc').attr("value");
	var positiveWorkjc =$('#positiveWorkjc').attr("value");
	var remoteMonitorjc =$('#remoteMonitorjc').attr("value");
	var elevatorInsurancejc =$('#elevatorInsurancejc').attr("value");
	var techinnovationjc =$('#techinnovationjc').attr("value");

	var hjtotal = 0;
	
//	hjtotal = parseInt(officeSpacejc)+parseInt(headQuartersjc)+parseInt(maintenanceEleCountjc)+parseInt(avgmaintenanceEleCountjc)+parseInt(fixedTelOnDutyjc)+parseInt(telOnDutyunattendedjc)+parseInt(enterpriseChangejc)+parseInt(enterpriseRecordjc)+parseInt(infoComRatejc)+parseInt(sweepCodeRatejc)+parseInt(sweepCodeInTimeRatejc)+parseInt(alarmDealwithjc)+parseInt(regularInspectionjc)+parseInt(inspectElevatorjc)+parseInt(acceptInspElevatorjc)+parseInt(maintenSceneInfojc)+parseInt(malignantEventsjc)+parseInt(complaintsEventsjc)+parseInt(maintenBusinessjc)+parseInt(honestjc)+parseInt(punishmentjc)+parseInt(firstRescuejc)+parseInt(secondRescuejc)+parseInt(secondRescuePointjc)+parseInt(rescueResponsejc)+parseInt(tiringPeoplejc)+parseInt(positiveEnergyjc)+parseInt(expertsSuggestionjc)+parseInt(positiveWorkjc)+parseInt(remoteMonitorjc)+parseInt(elevatorInsurancejc)+parseInt(techinnovationjc);
	hjtotal = parseInt(officeSpacejc)+parseInt(maintenanceEleCountjc)+parseInt(avgmaintenanceEleCountjc)+parseInt(fixedTelOnDutyjc)+parseInt(telOnDutyunattendedjc)+parseInt(enterpriseChangejc)+parseInt(enterpriseRecordjc)+parseInt(infoComRatejc)+parseInt(sweepCodeRatejc)+parseInt(sweepCodeInTimeRatejc)+parseInt(alarmDealwithjc)+parseInt(regularInspectionjc)+parseInt(inspectElevatorjc)+parseInt(acceptInspElevatorjc)+parseInt(maintenSceneInfojc)+parseInt(malignantEventsjc)+parseInt(complaintsEventsjc)+parseInt(maintenBusinessjc)+parseInt(honestjc)+parseInt(punishmentjc)+parseInt(firstRescuejc)+parseInt(secondRescuejc)+parseInt(secondRescuePointjc)+parseInt(rescueResponsejc)+parseInt(tiringPeoplejc)+parseInt(positiveEnergyjc)+parseInt(expertsSuggestionjc)+parseInt(positiveWorkjc)+parseInt(remoteMonitorjc)+parseInt(elevatorInsurancejc)+parseInt(techinnovationjc);
    
	$('#hjtotal').attr("value",hjtotal);
}


//合计实际总分
function sjhjtotal(){ 
	
	var officeSpacesj =$('#officeSpacesj').attr("value");
//	var headQuarterssj =$('#headQuarterssj').attr("value");
	var maintenanceEleCountsj =$('#maintenanceEleCountsj').attr("value");
	var avgmaintenanceEleCountsj =$('#avgmaintenanceEleCountsj').attr("value");
	var fixedTelOnDutysj =$('#fixedTelOnDutysj').attr("value");
	var telOnDutyunattendedsj =$('#telOnDutyunattendedsj').attr("value");
	var enterpriseChangesj =$('#enterpriseChangesj').attr("value");
	var enterpriseRecordsj =$('#enterpriseRecordsj').attr("value");
	var infoComRatesj =$('#infoComRatesj').attr("value");
	var sweepCodeRatesj =$('#sweepCodeRatesj').attr("value");
	var sweepCodeInTimeRatesj =$('#sweepCodeInTimeRatesj').attr("value");
	var alarmDealwithsj =$('#alarmDealwithsj').attr("value");
	var regularInspectionsj =$('#regularInspectionsj').attr("value");
	var inspectElevatorsj =$('#inspectElevatorsj').attr("value");
	var acceptInspElevatorsj =$('#acceptInspElevatorsj').attr("value");
	var maintenSceneInfosj =$('#maintenSceneInfosj').attr("value");
	var malignantEventssj =$('#malignantEventssj').attr("value");
	var complaintsEventssj =$('#complaintsEventssj').attr("value");
	var maintenBusinesssj =$('#maintenBusinesssj').attr("value");
	var honestsj =$('#honestsj').attr("value");
	var punishmentsj =$('#punishmentsj').attr("value");
	var firstRescuesj =$('#firstRescuesj').attr("value");
	var secondRescuesj =$('#secondRescuesj').attr("value");
	var secondRescuePointsj =$('#secondRescuePointsj').attr("value");
	var rescueResponsesj =$('#rescueResponsesj').attr("value");
	var tiringPeoplesj =$('#tiringPeoplesj').attr("value");
	var positiveEnergysj =$('#positiveEnergysj').attr("value");
	var expertsSuggestionsj =$('#expertsSuggestionsj').attr("value");
	var positiveWorksj =$('#positiveWorksj').attr("value");
	var remoteMonitorsj =$('#remoteMonitorsj').attr("value");
	var elevatorInsurancesj =$('#elevatorInsurancesj').attr("value");
	var techinnovationsj =$('#techinnovationsj').attr("value");

	var sjhjtotal = 0;
	
//	sjhjtotal = parseInt(officeSpacesj)+parseInt(headQuarterssj)+parseInt(maintenanceEleCountsj)+parseInt(avgmaintenanceEleCountsj)+parseInt(fixedTelOnDutysj)+parseInt(telOnDutyunattendedsj)+parseInt(enterpriseChangesj)+parseInt(enterpriseRecordsj)+parseInt(infoComRatesj)+parseInt(sweepCodeRatesj)+parseInt(sweepCodeInTimeRatesj)+parseInt(alarmDealwithsj)+parseInt(regularInspectionsj)+parseInt(inspectElevatorsj)+parseInt(acceptInspElevatorsj)+parseInt(maintenSceneInfosj)+parseInt(malignantEventssj)+parseInt(complaintsEventssj)+parseInt(maintenBusinesssj)+parseInt(honestsj)+parseInt(punishmentsj)+parseInt(firstRescuesj)+parseInt(secondRescuesj)+parseInt(secondRescuePointsj)+parseInt(rescueResponsesj)+parseInt(tiringPeoplesj)+parseInt(positiveEnergysj)+parseInt(expertsSuggestionsj)+parseInt(positiveWorksj)+parseInt(remoteMonitorsj)+parseInt(elevatorInsurancesj)+parseInt(techinnovationsj);
    sjhjtotal = parseInt(officeSpacesj)+parseInt(maintenanceEleCountsj)+parseInt(avgmaintenanceEleCountsj)+parseInt(fixedTelOnDutysj)+parseInt(telOnDutyunattendedsj)+parseInt(enterpriseChangesj)+parseInt(enterpriseRecordsj)+parseInt(infoComRatesj)+parseInt(sweepCodeRatesj)+parseInt(sweepCodeInTimeRatesj)+parseInt(alarmDealwithsj)+parseInt(regularInspectionsj)+parseInt(inspectElevatorsj)+parseInt(acceptInspElevatorsj)+parseInt(maintenSceneInfosj)+parseInt(malignantEventssj)+parseInt(complaintsEventssj)+parseInt(maintenBusinesssj)+parseInt(honestsj)+parseInt(punishmentsj)+parseInt(firstRescuesj)+parseInt(secondRescuesj)+parseInt(secondRescuePointsj)+parseInt(rescueResponsesj)+parseInt(tiringPeoplesj)+parseInt(positiveEnergysj)+parseInt(expertsSuggestionsj)+parseInt(positiveWorksj)+parseInt(remoteMonitorsj)+parseInt(elevatorInsurancesj)+parseInt(techinnovationsj);
    
	$('#sjhjtotal').attr("value",sjhjtotal);

    //备注图标
	bzImg();
}

//保存
function saveSetings(){
	 $.messager.confirm('','确定要保存',function(data){if(data){
	
	var ywCompanyId=$('#ywCompanyIdinfo2').attr("value");  
	if(ywCompanyId == 0)
		ywCompanyId = 0;  
	var ratingDate2 =$('#ratingDate2').datebox("getValue");
   
	$('#ywCompanyID').attr("value",ywCompanyId);  
	$('#ratingDate').attr("value",ratingDate2); 

	if(ywCompanyId == "" || ywCompanyId == 0){
		$.messager.alert('操作失败', '请从维保单位栏中输入至少2个关键字后选择维保单位', 'error');
		$('#ywCompanyIdinfo').focus();
		return;
	}

	if(ratingDate2 == ""){
		$.messager.alert('操作失败', '请选择年月', 'error');
		$('#ratingDate2').focus();
		return;
		
	}
	 
	$('#myform').form.url='/tcweb/elevator/ywSysSetings?ywCompanyID='+ywCompanyId+'&ratingDate='+ratingDate;  
	$('#myform').form('submit', {  
		url:form.url,
		onSubmit:function(){  
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

//权限控制
function roleControl(){
	//安全平台
	$('#maintenanceEleCount').css("background-color","#D3D3D3"); 
	$('#maintenanceEleCountjc').css("background-color","#D3D3D3"); 
	$('#avgmaintenanceEleCount').css("background-color","#D3D3D3"); 
	$('#avgmaintenanceEleCountjc').css("background-color","#D3D3D3"); 
	$('#infoComRate').css("background-color","#D3D3D3"); 
	$('#infoComRatejc').css("background-color","#D3D3D3"); 
	$('#sweepCodeRate').css("background-color","#D3D3D3"); 
	$('#sweepCodeRatejc').css("background-color","#D3D3D3");
	$('#sweepCodeInTimeRate').css("background-color","#D3D3D3"); 
	$('#sweepCodeInTimeRatejc').css("background-color","#D3D3D3");
	$('#alarmDealwith').css("background-color","#D3D3D3"); 
	$('#alarmDealwithjc').css("background-color","#D3D3D3");

	<% if((role ==22 || role ==23) || (role ==10 || role ==11)) { %>     //特检验打分
	$('#regularInspectionjc').css("background-color","#D3D3D3");
    $('#regularInspectionTimes').css("background-color","#D3D3D3");
    $('#regularInspectionTimes').numberspinner({disabled: true}); 
    $('#regularInspectionbz').css("background-color","#D3D3D3");
    $('#regularInspectionbz').attr('readonly',true);    
    $('#regularInspectionButton').attr('src','');   //控制不显示添加备注按钮
    
    $('#firstRescuejc').css("background-color","#D3D3D3");
    $('#firstRescueTimes').css("background-color","#D3D3D3");
    $('#firstRescueTimes').numberspinner({disabled: true});
    $('#firstRescuebz').css("background-color","#D3D3D3");
    $('#firstRescuebz').attr('readonly',true); 
    $('#firstRescueButton').attr('src',''); 

    $('#secondRescuejc').css("background-color","#D3D3D3");
    $('#secondRescueTimes').css("background-color","#D3D3D3");
    $('#secondRescueTimes').numberspinner({disabled: true}); 
    $('#secondRescueTimes2').css("background-color","#D3D3D3");
    $('#secondRescueTimes2').numberspinner({disabled: true});  
    $('#secondRescuebz').css("background-color","#D3D3D3");
    $('#secondRescuebz').attr('readonly',true);
    $('#secondRescueButton').attr('src',''); 

    $('#rescueResponsejc').css("background-color","#D3D3D3");
    $('#rescueResponseTimes').css("background-color","#D3D3D3");
    $('#rescueResponseTimes').numberspinner({disabled: true});
    $('#rescueResponsebz').css("background-color","#D3D3D3");
    $('#rescueResponsebz').attr('readonly',true);
    $('#rescueResponseButton').attr('src','');

    $('#tiringPeoplejc').css("background-color","#D3D3D3");
    $('#tiringPeopleTimes').css("background-color","#D3D3D3");
    $('#tiringPeopleTimes').numberspinner({disabled: true}); 
    $('#tiringPeoplebz').css("background-color","#D3D3D3");
    $('#tiringPeoplebz').attr('readonly',true); 
    $('#tiringPeopleButton').attr('src','');
    <% }%>

    <%if(role ==22 || role ==23){%>
    $('#inspectElevatorjc').css("background-color","#D3D3D3");
    $('#inspectElevatorTimes').css("background-color","#D3D3D3");
    $('#inspectElevatorTimes').numberspinner({disabled: true});
    $('#inspectElevatorTimes2').css("background-color","#D3D3D3");
    $('#inspectElevatorTimes2').numberspinner({disabled: true});
    $('#inspectElevatorbz').css("background-color","#D3D3D3");
    $('#inspectElevatorbz').attr('readonly',true);
    $('#inspectElevatorButton').attr('src','');

    
    $('#acceptInspElevatorjc').css("background-color","#D3D3D3");
    $('#acceptInspElevatorTimes').css("background-color","#D3D3D3");
    $('#acceptInspElevatorTimes').numberspinner({disabled: true}); 
    $('#acceptInspElevatorbz').css("background-color","#D3D3D3");
    $('#acceptInspElevatorbz').attr('readonly',true);
    $('#acceptInspElevatorButton').attr('src','');

    $('#maintenSceneInfojc').css("background-color","#D3D3D3");
    $('#maintenSceneInfoTimes').css("background-color","#D3D3D3");
    $('#maintenSceneInfoTimes').numberspinner({disabled: true}); 
    $('#maintenSceneInfobz').css("background-color","#D3D3D3");
    $('#maintenSceneInfobz').attr('readonly',true);
    $('#maintenSceneInfoButton').attr('src','');
    <%}%>
    
    <% if(role ==10 || role ==11) { %>                   //特检验和市质监打分
    $('#officeSpacejc').css("background-color","#D3D3D3");
    $('#officeSpace').css("background-color","#D3D3D3"); 
    $('#officeSpace').attr('readonly',true);  
    $('#officeSpacebz').css("background-color","#D3D3D3");
    $('#officeSpacebz').attr('readonly',true);
    $('#officeSpaceButton').attr('src','');
/*
    $('#headQuartersjc').css("background-color","#D3D3D3");
    $('#headQuarters').css("background-color","#D3D3D3"); 
    $("#headQuarters").combobox({  
                    disabled:true  
                });  
    $('#headQuartersbz').css("background-color","#D3D3D3");
    $('#headQuartersbz').attr('readonly',true);
    $('#headQuartersButton').attr('src',''); */
    
    $('#fixedTelOnDuty').css("background-color","#D3D3D3"); 
	$('#fixedTelOnDutyjc').css("background-color","#D3D3D3");
	$('#fixedTelOnDuty').attr('readonly',true);
	$('#fixedTelOnDutybz').css("background-color","#D3D3D3");
	$('#fixedTelOnDutybz').attr('readonly',true);
	$('#fixedTelOnDutyButton').attr('src','');

	$('#telOnDutyunattendedjc').css("background-color","#D3D3D3");
    $('#telOnDutyunattendedTimes').css("background-color","#D3D3D3");
    $('#telOnDutyunattendedTimes').numberspinner({disabled: true}); 
    $('#telOnDutyunattendedbz').css("background-color","#D3D3D3");
    $('#telOnDutyunattendedbz').attr('readonly',true);
    $('#telOnDutyunattendedButton').attr('src','');

    $('#enterpriseChangejc').css("background-color","#D3D3D3");
    $('#enterpriseChangeTimes').css("background-color","#D3D3D3");
    $('#enterpriseChangeTimes').numberspinner({disabled: true}); 
    $('#enterpriseChangebz').css("background-color","#D3D3D3");
    $('#enterpriseChangebz').attr('readonly',true);
    $('#enterpriseChangeButton').attr('src','');
    
    $('#enterpriseRecordjc').css("background-color","#D3D3D3");
    $('#enterpriseRecord').css("background-color","#D3D3D3"); 
    $("#enterpriseRecord").combobox({  
                    disabled:true  
                });  
    $('#enterpriseRecordbz').css("background-color","#D3D3D3");
    $('#enterpriseRecordbz').attr('readonly',true);
    $('#enterpriseRecordButton').attr('src','');
   
    $('#malignantEventsjc').css("background-color","#D3D3D3");
    $('#malignantEventsTimes').css("background-color","#D3D3D3");
    $('#malignantEventsTimes').numberspinner({disabled: true});
    $('#malignantEventsTimes2').css("background-color","#D3D3D3");
    $('#malignantEventsTimes2').numberspinner({disabled: true});
    $('#malignantEventsTimes3').css("background-color","#D3D3D3");
    $('#malignantEventsTimes3').numberspinner({disabled: true}); 
    $('#malignantEventsbz').css("background-color","#D3D3D3");
    $('#malignantEventsbz').attr('readonly',true);
    $('#malignantEventsButton').attr('src','');
   
    $('#secondRescuePointjc').css("background-color","#D3D3D3");
    $('#secondRescuePoint').css("background-color","#D3D3D3"); 
    $('#secondRescuePoint').attr('readonly',true);
    $('#secondRescuePointbz').css("background-color","#D3D3D3");
    $('#secondRescuePointbz').attr('readonly',true);
    $('#secondRescuePointButton').attr('src','');
    
    $('#positiveEnergyjc').css("background-color","#D3D3D3");
    $('#positiveEnergyTimes').css("background-color","#D3D3D3");
    $('#positiveEnergyTimes').numberspinner({disabled: true}); 
    $('#positiveEnergybz').css("background-color","#D3D3D3");
    $('#positiveEnergybz').attr('readonly',true);
    $('#positiveEnergyButton').attr('src','');
    
    $('#expertsSuggestionjc').css("background-color","#D3D3D3");
    $('#expertsSuggestionTimes').css("background-color","#D3D3D3");
    $('#expertsSuggestionTimes').numberspinner({disabled: true}); 
    $('#expertsSuggestionbz').css("background-color","#D3D3D3");
    $('#expertsSuggestionbz').attr('readonly',true);
    $('#expertsSuggestionButton').attr('src','');

    $('#positiveWorkjc').css("background-color","#D3D3D3");
    $('#positiveWork').css("background-color","#D3D3D3"); 
    $('#positiveWork').attr('readonly',true);
    $('#positiveWorkbz').css("background-color","#D3D3D3");
    $('#positiveWorkbz').attr('readonly',true);
    $('#positiveWorkButton').attr('src','');

    $('#remoteMonitorjc').css("background-color","#D3D3D3");
    $('#remoteMonitor').css("background-color","#D3D3D3"); 
    $('#remoteMonitor').attr('readonly',true);
    $('#remoteMonitorbz').css("background-color","#D3D3D3");
    $('#remoteMonitorbz').attr('readonly',true);
    $('#remoteMonitorButton').attr('src','');

    $('#elevatorInsurancejc').css("background-color","#D3D3D3");
    $('#elevatorInsurance').css("background-color","#D3D3D3"); 
    $('#elevatorInsurance').attr('readonly',true);
    $('#elevatorInsurancebz').css("background-color","#D3D3D3"); 
    $('#elevatorInsurancebz').attr('readonly',true);
    $('#elevatorInsuranceButton').attr('src','');

    $('#techinnovationjc').css("background-color","#D3D3D3");
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
    $('#techinnovationbz').attr('readonly',true);
    $('#techinnovationButton').attr('src','');
    <% }%>


    <% if(role ==16 || role ==17){ %>     
    $('#officeSpacejc').css("background-color","#D3D3D3");
    $('#officeSpace').css("background-color","#D3D3D3"); 
    $('#officeSpace').attr('readonly',true);  
    $('#officeSpacebz').css("background-color","#D3D3D3"); 
    $('#officeSpacebz').attr('readonly',true);
    $('#officeSpaceButton').attr('src','');
/*
    $('#headQuartersjc').css("background-color","#D3D3D3");
    $('#headQuarters').css("background-color","#D3D3D3"); 
    $("#headQuarters").combobox({  
                    disabled:true  
                });  
    $('#headQuartersbz').css("background-color","#D3D3D3");
    $('#headQuartersbz').attr('readonly',true);
    $('#headQuartersButton').attr('src','');  */
    
    $('#fixedTelOnDuty').css("background-color","#D3D3D3"); 
	$('#fixedTelOnDutyjc').css("background-color","#D3D3D3");
	$('#fixedTelOnDuty').attr('readonly',true);
	$('#fixedTelOnDutybz').css("background-color","#D3D3D3");
	$('#fixedTelOnDutybz').attr('readonly',true);
	$('#fixedTelOnDutyButton').attr('src','');

	$('#telOnDutyunattendedjc').css("background-color","#D3D3D3");
    $('#telOnDutyunattendedTimes').css("background-color","#D3D3D3");
    $('#telOnDutyunattendedTimes').numberspinner({disabled: true}); 
    $('#telOnDutyunattendedbz').css("background-color","#D3D3D3");
    $('#telOnDutyunattendedbz').attr('readonly',true);
    $('#telOnDutyunattendedButton').attr('src','');

    $('#enterpriseChangejc').css("background-color","#D3D3D3");
    $('#enterpriseChangeTimes').css("background-color","#D3D3D3");
    $('#enterpriseChangeTimes').numberspinner({disabled: true}); 
    $('#enterpriseChangebz').css("background-color","#D3D3D3");
    $('#enterpriseChangebz').attr('readonly',true);
    $('#enterpriseChangeButton').attr('src','');

    $('#enterpriseRecordjc').css("background-color","#D3D3D3");
    $('#enterpriseRecord').css("background-color","#D3D3D3"); 
    $("#enterpriseRecord").combobox({  
                    disabled:true  
                });  
    $('#enterpriseRecordbz').css("background-color","#D3D3D3");
    $('#enterpriseRecordbz').attr('readonly',true); 
    $('#enterpriseRecordButton').attr('src','');
   
    $('#malignantEventsjc').css("background-color","#D3D3D3");
    $('#malignantEventsTimes').css("background-color","#D3D3D3");
    $('#malignantEventsTimes').numberspinner({disabled: true});
    $('#malignantEventsTimes2').css("background-color","#D3D3D3");
    $('#malignantEventsTimes2').numberspinner({disabled: true});
    $('#malignantEventsTimes3').css("background-color","#D3D3D3");
    $('#malignantEventsTimes3').numberspinner({disabled: true}); 
    $('#malignantEventsbz').css("background-color","#D3D3D3");
    $('#malignantEventsbz').attr('readonly',true);
    $('#malignantEventsButton').attr('src','');

    $('#complaintsEventsjc').css("background-color","#D3D3D3");
    $('#complaintsEventsTimes').css("background-color","#D3D3D3");
    $('#complaintsEventsTimes').numberspinner({disabled: true});
    $('#complaintsEventsTimes2').css("background-color","#D3D3D3");
    $('#complaintsEventsTimes2').numberspinner({disabled: true});
    $('#complaintsEventsbz').css("background-color","#D3D3D3");
    $('#complaintsEventsbz').attr('readonly',true);
    $('#complaintsEventsButton').attr('src','');
   
    $('#maintenBusinessjc').css("background-color","#D3D3D3");
    $('#maintenBusinessTimes').css("background-color","#D3D3D3");
    $('#maintenBusinessTimes').numberspinner({disabled: true});
    $('#maintenBusinessbz').css("background-color","#D3D3D3");
    $('#maintenBusinessbz').attr('readonly',true);
    $('#maintenBusinessButton').attr('src','');

    $('#honestTimesjc').css("background-color","#D3D3D3");
    $('#honestTimes').css("background-color","#D3D3D3");
    $('#honestTimes').numberspinner({disabled: true});
    $('#honestbz').css("background-color","#D3D3D3");
    $('#honestbz').attr('readonly',true);
    $('#honestButton').attr('src','');

    $('#punishmentjc').css("background-color","#D3D3D3");
    $('#punishmentTimes').css("background-color","#D3D3D3");
    $('#punishmentTimes').numberspinner({disabled: true});
    $('#punishmentTimes2').css("background-color","#D3D3D3");
    $('#punishmentTimes2').numberspinner({disabled: true});
    $('#punishmentTimes3').css("background-color","#D3D3D3");
    $('#punishmentTimes3').numberspinner({disabled: true});
    $('#punishmentTimes4').css("background-color","#D3D3D3");
    $('#punishmentTimes4').numberspinner({disabled: true});
    $('#punishmentbz').css("background-color","#D3D3D3");
    $('#punishmentbz').attr('readonly',true);
    $('#punishmentButton').attr('src','');
   
    $('#secondRescuePointjc').css("background-color","#D3D3D3");
    $('#secondRescuePoint').css("background-color","#D3D3D3"); 
    $('#secondRescuePoint').attr('readonly',true);
    $('#secondRescuePointbz').css("background-color","#D3D3D3");
    $('#secondRescuePointbz').attr('readonly',true);
    $('#secondRescuePointButton').attr('src','');

    $('#firstRescuejc').css("background-color","#D3D3D3");
    $('#firstRescueTimes').css("background-color","#D3D3D3");
    $('#firstRescueTimes').numberspinner({disabled: true});
    $('#firstRescuebz').css("background-color","#D3D3D3");
    $('#firstRescuebz').attr('readonly',true);
    $('#firstRescueButton').attr('src','');

    $('#secondRescuejc').css("background-color","#D3D3D3");
    $('#secondRescueTimes').css("background-color","#D3D3D3");
    $('#secondRescueTimes').numberspinner({disabled: true}); 
    $('#secondRescueTimes2').css("background-color","#D3D3D3");
    $('#secondRescueTimes2').numberspinner({disabled: true});  
    $('#secondRescuebz').css("background-color","#D3D3D3");
    $('#secondRescuebz').attr('readonly',true);
    $('#secondRescueButton').attr('src','');

    $('#rescueResponsejc').css("background-color","#D3D3D3");
    $('#rescueResponseTimes').css("background-color","#D3D3D3");
    $('#rescueResponseTimes').numberspinner({disabled: true});
    $('#rescueResponsebz').css("background-color","#D3D3D3");
    $('#rescueResponsebz').attr('readonly',true);
    $('#rescueResponseButton').attr('src','');

    $('#tiringPeoplejc').css("background-color","#D3D3D3");
    $('#tiringPeopleTimes').css("background-color","#D3D3D3");
    $('#tiringPeopleTimes').numberspinner({disabled: true});   
    $('#tiringPeoplebz').css("background-color","#D3D3D3");
    $('#tiringPeoplebz').attr('readonly',true);
    $('#tiringPeopleButton').attr('src','');
    
    $('#positiveEnergyjc').css("background-color","#D3D3D3");
    $('#positiveEnergyTimes').css("background-color","#D3D3D3");
    $('#positiveEnergyTimes').numberspinner({disabled: true}); 
    $('#positiveEnergybz').css("background-color","#D3D3D3");
    $('#positiveEnergybz').attr('readonly',true);
    $('#positiveEnergyButton').attr('src','');
    
    $('#expertsSuggestionjc').css("background-color","#D3D3D3");
    $('#expertsSuggestionTimes').css("background-color","#D3D3D3");
    $('#expertsSuggestionTimes').numberspinner({disabled: true}); 
    $('#expertsSuggestionbz').css("background-color","#D3D3D3");
    $('#expertsSuggestionbz').attr('readonly',true);
    $('#expertsSuggestionButton').attr('src','');

    $('#positiveWorkjc').css("background-color","#D3D3D3");
    $('#positiveWork').css("background-color","#D3D3D3"); 
    $('#positiveWork').attr('readonly',true);
    $('#positiveWorkbz').css("background-color","#D3D3D3");
    $('#positiveWorkbz').attr('readonly',true);
    $('#positiveWorkButton').attr('src','');

    $('#remoteMonitorjc').css("background-color","#D3D3D3");
    $('#remoteMonitor').css("background-color","#D3D3D3"); 
    $('#remoteMonitor').attr('readonly',true);
    $('#remoteMonitorbz').css("background-color","#D3D3D3");
    $('#remoteMonitorbz').attr('readonly',true);
    $('#remoteMonitorButton').attr('src','');

    $('#elevatorInsurancejc').css("background-color","#D3D3D3");
    $('#elevatorInsurance').css("background-color","#D3D3D3"); 
    $('#elevatorInsurance').attr('readonly',true);
    $('#elevatorInsurancebz').css("background-color","#D3D3D3");
    $('#elevatorInsurancebz').attr('readonly',true);
    $('#elevatorInsuranceButton').attr('src','');

    $('#techinnovationjc').css("background-color","#D3D3D3");
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
    $('#techinnovationbz').attr('readonly',true);
    $('#techinnovationButton').attr('src','');
    <% }%>
}

//查询
function query(){
     var ywCompanyID =$('#ywCompanyIdinfo2').attr("value");
     if(ywCompanyID == '')
    	 ywCompanyID =0;    

	 if(ywCompanyID ==0){
			$.messager.alert('操作失败','请输入至少2个关键字从维保单位下拉列表中选择维保单位','info'); 
			return;
	 }
     var ratingDate = $('#ratingDate2').datebox("getValue");  
     $('#ratingDate').attr("value",ratingDate);
	$('#myform').form('load', '/tcweb/elevator/ywsyssetings2ByComIdRatDate/?ywCompanyID='+ywCompanyID+'&ratingDate='+ratingDate);
	
}



var beizhuid ="";  
function addBeiZhu(id){
    beizhuid = id;    
    $('#beiZhuWin').window('open');
    var beizhugs ="";
  //  beizhugs ='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地址：<input id="officeSpaceaddress"></input><br><br>'+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;面积：<input id="officeSpacemj"></input><br><br>'+'占用方式：<select  id="officeSpacezyfs"><option value="0">租用</option><option value="1">购买</option></select><br><br>'+'到期日期：<input id="officeSpaceDueDate"  class="easyui-datebox"  data-options="editable:false"></input>';

	if(beizhuid=="officeSpaceButton"){
		$('#beizhutext').attr("value",$('#officeSpacebz').attr("value"));
	//	$('#beizhuDivnr').html(beizhugs);
	}
/*	if(beizhuid=="headQuartersButton")
		$('#beizhutext').attr("value",$('#headQuartersbz').attr("value")); */
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

function bzsave(){   
	var beizhutext = $('#beizhutext').attr("value"); 

	 var len = $('#beizhutext').val().length; 
	 if(len > 200){
		 $('#beizhutext').val($('#beizhutext').val().substring(0,200));
		 $.messager.alert('保存失败','最大只能输入200个字符','error');
		 $('#beizhutext').focus();
	    	return;
		 } 
         
	
	if(beizhuid=="officeSpaceButton"){
		   $('#officeSpacebz').attr("value",beizhutext);
		   if(beizhutext != "")  
		    $("#officeSpaceButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#officeSpaceButton").attr('src','../../images/beizhu.png'); 
		}
    /*
	if(beizhuid=="headQuartersButton"){
		$('#headQuartersbz').attr("value",beizhutext);
		 if(beizhutext != "")  
			    $("#headQuartersButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#headQuartersButton").attr('src','../../images/beizhu.png'); 
	}
	 */
	if(beizhuid=="telOnDutyunattendedButton"){
	   $('#telOnDutyunattendedbz').attr("value",beizhutext);  
	   if(beizhutext != "")  
		    $("#telOnDutyunattendedButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#telOnDutyunattendedButton").attr('src','../../images/beizhu.png'); 
	   
	}

	if(beizhuid=="fixedTelOnDutyButton"){
		$('#fixedTelOnDutybz').attr("value",beizhutext);  
		  if(beizhutext != "")  
			    $("#fixedTelOnDutyButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#fixedTelOnDutyButton").attr('src','../../images/beizhu.png'); 
	}
	
	if(beizhuid=="enterpriseChangeButton"){
		$('#enterpriseChangebz').attr("value",beizhutext); 
		 if(beizhutext != "")  
			    $("#enterpriseChangeButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#enterpriseChangeButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="enterpriseRecordButton"){
		$('#enterpriseRecordbz').attr("value",beizhutext); 
		 if(beizhutext != "")  
			    $("#enterpriseRecordButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#enterpriseRecordButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="regularInspectionButton"){
		$('#regularInspectionbz').attr("value",beizhutext); 
		 if(beizhutext != "")  
			    $("#regularInspectionButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#regularInspectionButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="inspectElevatorButton"){
		$('#inspectElevatorbz').attr("value",beizhutext); 
		 if(beizhutext != "")  
			    $("#inspectElevatorButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#inspectElevatorButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="acceptInspElevatorButton"){
		$('#acceptInspElevatorbz').attr("value",beizhutext); 
		 if(beizhutext != "")  
			    $("#acceptInspElevatorButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#acceptInspElevatorButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="maintenSceneInfoButton"){
		$('#maintenSceneInfobz').attr("value",beizhutext);
		 if(beizhutext != "")  
			    $("#maintenSceneInfoButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#maintenSceneInfoButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="malignantEventsButton"){
		$('#malignantEventsbz').attr("value",beizhutext);
		 if(beizhutext != "")  
			    $("#malignantEventsButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#malignantEventsButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="complaintsEventsButton"){
		$('#complaintsEventsbz').attr("value",beizhutext);
		 if(beizhutext != "")  
			    $("#complaintsEventsButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#complaintsEventsButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="maintenBusinessButton"){
		$('#maintenBusinessbz').attr("value",beizhutext);
		 if(beizhutext != "")  
			    $("#maintenBusinessButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#maintenBusinessButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="honestButton"){
		$('#honestbz').attr("value",beizhutext);
		 if(beizhutext != "")  
			    $("#honestButton").attr('src','../../images/youbeizhu.png'); 
			   else
				$("#honestButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="punishmentButton"){
		$('#punishmentbz').attr("value",beizhutext);
		if(beizhutext != "")  
		    $("#punishmentButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#punishmentButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="firstRescueButton"){
		$('#firstRescuebz').attr("value",beizhutext);
		if(beizhutext != "")  
		    $("#firstRescueButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#firstRescueButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="secondRescueButton"){
		$('#secondRescuebz').attr("value",beizhutext);
		if(beizhutext != "")  
		    $("#secondRescueButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#secondRescueButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="secondRescuePointButton"){
		$('#secondRescuePointbz').attr("value",beizhutext);
		if(beizhutext != "")  
		    $("#secondRescuePointButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#secondRescuePointButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="rescueResponseButton"){
		$('#rescueResponsebz').attr("value",beizhutext);
		if(beizhutext != "")  
		    $("#rescueResponseButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#rescueResponseButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="tiringPeopleButton"){
		$('#tiringPeoplebz').attr("value",beizhutext);
		if(beizhutext != "")  
		    $("#tiringPeopleButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#tiringPeopleButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="positiveEnergyButton"){
		$('#positiveEnergybz').attr("value",beizhutext);
		if(beizhutext != "")  
		    $("#positiveEnergyButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#positiveEnergyButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="expertsSuggestionButton"){
		$('#expertsSuggestionbz').attr("value",beizhutext);
		if(beizhutext != "")  
		    $("#expertsSuggestionButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#expertsSuggestionButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="positiveWorkButton"){
		$('#positiveWorkbz').attr("value",beizhutext);
		if(beizhutext != "")  
		    $("#positiveWorkButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#positiveWorkButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="remoteMonitorButton"){
		$('#remoteMonitorbz').attr("value",beizhutext);
		if(beizhutext != "")  
		    $("#remoteMonitorButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#remoteMonitorButton").attr('src','../../images/beizhu.png');
	}
	if(beizhuid=="elevatorInsuranceButton"){    
		$('#elevatorInsurancebz').attr("value",beizhutext);
		if(beizhutext != "")  
		    $("#elevatorInsuranceButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#elevatorInsuranceButton").attr('src','../../images/beizhu.png');
	}
	
	if(beizhuid=="techinnovationButton"){    
		$('#techinnovationbz').attr("value",beizhutext);
		if(beizhutext != "")  
		    $("#techinnovationButton").attr('src','../../images/youbeizhu.png'); 
		   else
			$("#techinnovationButton").attr('src','../../images/beizhu.png');
	}

	 $('#beiZhuWin').window('close');
}

function  bzImg(){
	<%if(role ==22 || role ==23){%>
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
    <%} %>

    <% if(role ==16 || role ==17){ %> 
       if($('#regularInspectionbz').attr("value") != "")
    	   $("#regularInspectionButton").attr('src','../../images/youbeizhu.png'); 
       else
    	   $("#regularInspectionButton").attr('src','../../images/beizhu.png');  
	<% }%>

	 <% if ((role ==10 || role ==11) || (role ==16 || role ==17)) { %>
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

	<%if(role ==22 || role ==23){%>
	 if($('#malignantEventsbz').attr("value") !="")
		   $("#malignantEventsButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#malignantEventsButton").attr('src','../../images/beizhu.png'); 
	   
	   if($('#complaintsEventsbz').attr("value") != "")
		   $("#complaintsEventsButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#complaintsEventsButton").attr('src','../../images/beizhu.png'); 
	<%}%>

	 <% if ((role ==22 || role ==23) || (role ==10 || role ==11)) { %>
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
       <%}%>
      
	   <%if(role ==22 || role ==23){%>
	   if($('#secondRescuePointbz').attr("value") != "")
		   $("#secondRescuePointButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#secondRescuePointButton").attr('src','../../images/beizhu.png'); 
	   <%}%>
	   if($('#rescueResponsebz').attr("value") != "")
		   $("#rescueResponseButton").attr('src','../../images/youbeizhu.png'); 
	   else
		   $("#rescueResponseButton").attr('src','../../images/beizhu.png');  
	
	   if($('#tiringPeoplebz').attr("value") != "")
		   $("#tiringPeopleButton").attr('src','../../images/youbeizhu.png');
	   else
		   $("#tiringPeopleButton").attr('src','../../images/beizhu.png');
	   <%if(role ==22 || role ==23){%>
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
	   <%}%>


	   $('#firstRescueButton').attr('src',''); 
	   $('#secondRescueButton').attr('src',''); 
	   $('#rescueResponseButton').attr('src','');
	   $('#tiringPeopleButton').attr('src','');
}

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
</style>
</head>
<body class="easyui-layout" style="height:100%;" fit="true">
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">  
       <center>
        <table>
        <tr>
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
        <!--  
         <tr>      
            <td align="center" style="background-color:#F0FFFF">总部所在地</td>  
            <td align="center">无</td>
            <td align="center"><input id="headQuartersjc"  type="text"  name="headQuartersjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
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
            <td align="center">成都&nbsp;&nbsp;<b>+10分</b> </td>  
            <td align="center"><input id="headQuarterssj"  type="text"  name="headQuarterssj" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="10"  readOnly></input></td>
        </tr> -->
        <tr>  
           
            <td align="center" style="background-color:#F0FFFF">维保电梯数量</td>  
            <td align="center">100-500台</td>
            <td align="center"><input id="maintenanceEleCountjc"  type="text"  name="maintenanceEleCountjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left"><input id="maintenanceEleCount"  type="text"  name="maintenanceEleCount"  style="width:102px;border-width :0px 0px 0px;" readOnly></input></td>
            <td></td>
            <td align="center">≥500台，每多500台&nbsp;&nbsp;<b>+1分</b><br><100台，&nbsp;&nbsp;<b>-5分</b> </td>  
            <td align="center"><input id="maintenanceEleCountsj"  type="text"  name="maintenanceEleCountsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
         <tr>  
            
            <td align="center" style="background-color:#F0FFFF">人均维保数量</td>  
            <td align="center">25-30台</td>
            <td align="center"><input id="avgmaintenanceEleCountjc"  type="text"  name="avgmaintenanceEleCountjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left"> <input id="avgmaintenanceEleCount"  type="text"  name="avgmaintenanceEleCount" style="width:102px;border-width :0px 0px 0px;" readOnly></input></td>
            <td></td>
            <td align="center">≤20台，&nbsp;&nbsp;<b>+3分</b>,20-24台，&nbsp;&nbsp;<b>+1分</b><br>31-40台&nbsp;&nbsp;<b>-5分</b>，>40台&nbsp;&nbsp;<b>-10分</b> </td>  
            <td align="center"><input id="avgmaintenanceEleCountsj"  type="text"  name="avgmaintenanceEleCountsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
         <tr>  
           
            <td align="center" style="background-color:#F0FFFF">值班固定电话</td>  
            <td align="center">有</td>
            <td align="center"><input id="fixedTelOnDutyjc"  type="text"  name="fixedTelOnDutyjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td align="left"><input id="fixedTelOnDuty"  type="text"  name="fixedTelOnDuty" style="width:102px;" onchange="fixedTelOnDutyValue()"></input></td>
            <td align="center">
            <input id="fixedTelOnDutybz"  type="hidden" class="easyui-validatebox" name="fixedTelOnDutybz" style="width:152px;text-align:center;"></input>
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
           <input id="telOnDutyunattendedbz"  type="hidden" class="easyui-validatebox" name="telOnDutyunattendedbz" style="width:152px;text-align:center;"></input>
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
             <input id="enterpriseChangebz"  type="hidden" class="easyui-validatebox" name="enterpriseChangebz" style="width:152px;text-align:center;"></input> 
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
            <input id="enterpriseRecordbz"  type="hidden" class="easyui-validatebox" name="enterpriseRecordbz" style="width:152px;text-align:center;"></input>
          <!--   <input type="button" id="enterpriseRecordButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->  
            <img src="../../images/beizhu.png" id="enterpriseRecordButton" title="" onclick="addBeiZhu(this.id)" />
             </td>
            <td align="center">未备案或未办理变更备案&nbsp;&nbsp;<b>-10分</b></td>  
            <td align="center"><input id="enterpriseRecordsj"  type="text"  name="enterpriseRecordsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
      
        <tr>  
            <td  rowspan="4" align="center" style="background-color:#F0FFFF">电梯安全<br>公共服务平台<br>使用情况</td>  
            <td align="center" style="background-color:#F0FFFF">信息完整率</td>  
            <td align="center">信息完整</td> 
            <td align="center"><input id="infoComRatejc"  type="text"  name="infoComRatejc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left"><input id="infoComRate"  type="text"  name="infoComRate" style="width:102px;border-width :0px 0px 0px;" value="100" readOnly></input></td>
            <td></td>
            <td align="center">按信息不完整率百分点数扣 </td>  
            <td align="center"><input id="infoComRatesj"  type="text"  name="infoComRatesj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
        <tr>    
            <td align="center" style="background-color:#F0FFFF">扫码维保率</td>  
            <td align="center">全部扫码</td> 
            <td align="center"><input id="sweepCodeRatejc"  type="text"  name="sweepCodeRatejc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left"><input id="sweepCodeRate"  type="text"  name="sweepCodeRate" style="width:102px;border-width :0px 0px 0px;" value="100" readOnly></input></td>
            <td></td>
            <td align="center">按未扫码率的百分点数扣 </td>  
            <td align="center"><input id="sweepCodeRatesj"  type="text"  name="sweepCodeRatesj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
        <tr>    
            <td align="center" style="background-color:#F0FFFF">按时扫码维保率</td>  
            <td align="center">按时扫码</td> 
            <td align="center"><input id="sweepCodeInTimeRatejc"  type="text"  name="sweepCodeInTimeRatejc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left"><input id="sweepCodeInTimeRate"  type="text"  name="sweepCodeInTimeRate" style="width:102px;border-width :0px 0px 0px;" value="100" readOnly></input></td>
            <td></td>
            <td align="center">按未扫码率的百分点数扣 </td>  
            <td align="center"><input id="sweepCodeInTimeRatesj"  type="text"  name="sweepCodeInTimeRatesj" style="width:102px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>
        </tr> 
        <tr>    
            <td align="center" style="background-color:#F0FFFF">平台报警处理情况</td>  
            <td align="center">全部处理</td> 
            <td align="center"><input id="alarmDealwithjc"  type="text"  name="alarmDealwithjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left"><input id="alarmDealwith"  type="text"  name="alarmDealwith" style="width:102px;border-width :0px 0px 0px;" value="0" readOnly></input></td>
            <td></td>
            <td align="center">未处理每条扣2分 </td>  
            <td align="center"><input id="alarmDealwithsj"  type="text"  name="alarmDealwithsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
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
            <input id="inspectElevatorbz"  type="hidden" class="easyui-validatebox" name="inspectElevatorbz" style="width:152px;text-align:center;"></input>
          <!--     <input type="button" id="inspectElevatorButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->
           <img src="../../images/beizhu.png" id="inspectElevatorButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">监督检查、执法检查、监督抽查、定期检验中发现严重隐患，&nbsp;&nbsp;<b>-5分/台</b><br>发现安全回路短接，&nbsp;&nbsp;<b>-20分/台</b> </td>  
            <td align="center"><input id="inspectElevatorsj"  type="text"  name="inspectElevatorsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        <tr>    
            <td align="center" style="background-color:#F0FFFF">接受监督检查情况</td>  
            <td align="center">配合</td> 
            <td align="center"><input id="acceptInspElevatorjc"  type="text"  name="acceptInspElevatorjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td align="left"><input id="acceptInspElevatorTimes" name="acceptInspElevatorTimes" class="easyui-numberspinner" style="width:50px;"  value="0" data-options="min:0,editable:false"></input>次</td>
           <td align="center">
           <input id="acceptInspElevatorbz"  type="hidden" class="easyui-validatebox" name="acceptInspElevatorbz" style="width:152px;text-align:center;"></input>
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
            <input id="maintenSceneInfobz"  type="hidden" class="easyui-validatebox" name="maintenSceneInfobz" style="width:152px;text-align:center;"></input>
          <!--   <input type="button" id="maintenSceneInfoButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->
          <img src="../../images/beizhu.png" id="maintenSceneInfoButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">未设立警示标识及保护措施&nbsp;&nbsp;<b>-10分/次 </td>  
            <td align="center"><input id="maintenSceneInfosj"  type="text"  name="maintenSceneInfosj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
        <tr>    
            <td align="center" style="background-color:#F0FFFF">因维保原因发生安全事故<br>重大社会影响事件</td>  
            <td align="center">无</td> 
            <td align="center"><input id="malignantEventsjc"  type="text"  name="malignantEventsjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left">
            <input id="malignantEventsTimes" name="malignantEventsTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>一般事故&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="malignantEventsTimes2" name="malignantEventsTimes2" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input> 较大事故&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            <input id="malignantEventsTimes3" name="malignantEventsTimes3" class="easyui-numberspinner" style="width:50px;" value="0" data-options="min:0,editable:false"></input>重大事故</td>
            <td align="center">
            <input id="malignantEventsbz"  type="hidden" class="easyui-validatebox" name="malignantEventsbz" style="width:152px;text-align:center;"></input>
         <!--  <input type="button" id="malignantEventsButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  -->    
            <img src="../../images/beizhu.png" id="malignantEventsButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">一般事故<b>-30分/宗</b>，较大事故&nbsp;&nbsp;<b>-50分/宗</b><br>重大及以上或重大影响、年度2次以上事故、<br>事故后不配合调查处理的，直接列入最低信用等级 </td>  
            <td align="center"><input id="malignantEventssj"  type="text"  name="malignantEventssj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
        <tr>    
            <td align="center" style="background-color:#F0FFFF">因维保质量而引发的投诉<br>（领导信箱、公开电话、网络理政平台）</td>  
            <td align="center">无</td> 
            <td align="center"><input id="complaintsEventsjc"  type="text"  name="complaintsEventssjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left">
            <input id="complaintsEventsTimes" name="complaintsEventsTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>一般投诉&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="complaintsEventsTimes2" name="complaintsEventsTimes2" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>连续投诉</td>
            <td align="center">
            <input id="complaintsEventsbz"  type="hidden" class="easyui-validatebox" name="complaintsEventsbz" style="width:152px;text-align:center;"></input>
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
            <input id="honestbz"  type="hidden" class="easyui-validatebox" name="honestbz" style="width:152px;text-align:center;"></input>
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
            <td align="center">现场监督检查、执法检查、重点时段抽查因维保管理及维保质量被整改&nbsp;&nbsp;<b>2分/宗</b>；<br>被下达监察意见过通报，&nbsp;&nbsp;<b>-5分/宗</b>；<br>本监督连续被下达监察指令或通报2次及以上，&nbsp;&nbsp;<b>-20分/宗</b>；<br>违反法律、法规及技术规范，&nbsp;&nbsp;<b>-20分/宗</b> </td>  
            <td align="center"><input id="punishmentsj"  type="text"  name="punishmentsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
         <tr>  
            <td  rowspan="5" align="center" style="background-color:#F0FFFF">应急救援<br>处置情况</td>  
            <td align="center" style="background-color:#F0FFFF">一级救援情况</td>  
            <td align="center">实施</td> 
            <td align="center"><input id="firstRescuejc"  type="text"  name="firstRescuejc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td align="left"><input id="firstRescueTimes" name="firstRescueTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>次</td>
            <td align="center">
            <input id="firstRescuebz"  type="hidden" class="easyui-validatebox" name="firstRescuebz" style="width:152px;text-align:center;"></input>
          <!--   <input type="button" id="firstRescueButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->  
          <img src="../../images/beizhu.png" id="firstRescueButton" title="" onclick="addBeiZhu(this.id)" />
           </td> 
            <td align="center">未实施&nbsp;&nbsp;<b>-5分/次</b> </td>  
            <td align="center"><input id="firstRescuesj"  type="text"  name="firstRescuesj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
            
        </tr> 
         <tr>   
            <td align="center" style="background-color:#F0FFFF">二级救援情况</td>  
            <td align="center">实施</td> 
            <td align="center"><input id="secondRescuejc"  type="text"  name="secondRescuejc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td><input id="secondRescueTimes" name="secondRescueTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false" ></input>未实施&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="secondRescueTimes2" name="secondRescueTimes2" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>实施</td>
            <td align="center">
            <input id="secondRescuebz"  type="hidden" class="easyui-validatebox" name="secondRescuebz" style="width:152px;text-align:center;"></input>
          <!--     <input type="button" id="secondRescueButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->
            <img src="../../images/beizhu.png" id="secondRescueButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">实施&nbsp;&nbsp;<b>+3分/次</b>,未实施&nbsp;&nbsp;<b>-5分/次</b> </td>  
            <td align="center"><input id="secondRescuesj"  type="text"  name="secondRescuesj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
        <tr>   
            <td align="center" style="background-color:#F0FFFF">参与二级救援布点</td>  
            <td align="center">参与布点数</td> 
            <td align="center"><input id="secondRescuePointjc"  type="text"  name="secondRescuePointjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td><input id="secondRescuePoint"  type="text"  name="secondRescuePoint" style="width:102px;text-align:center;" value="0" onchange="secondRescuePointValue()"></input>个</td>
            <td align="center">
            <input id="secondRescuePointbz"  type="hidden" class="easyui-validatebox" name="secondRescuePointbz" style="width:152px;text-align:center;"></input>
           <!--   <input type="button" id="secondRescuePointButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> --> 
            <img src="../../images/beizhu.png" id="secondRescuePointButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">10-19<b>&nbsp;&nbsp;+1分</b>，20-29<b>&nbsp;&nbsp;+2分</b>，30-39<b>&nbsp;&nbsp;+3分</b>，40及以上<b>&nbsp;&nbsp;+4分</b> </td>  
            <td align="center"><input id="secondRescuePointsj"  type="text"  name="secondRescuePointsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
        <tr>   
            <td align="center" style="background-color:#F0FFFF">应急救援响应情况</td>  
            <td align="center">30分钟到达</td> 
            <td align="center"><input id="rescueResponsejc"  type="text"  name="rescueResponsejc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td><input id="rescueResponseTimes" name="rescueResponseTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>次</td>
            <td align="center">
            <input id="rescueResponsebz"  type="hidden" class="easyui-validatebox" name="rescueResponsebz" style="width:152px;text-align:center;"></input>
        <!--    <input type="button" id="rescueResponseButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> --> 
             <img src="../../images/beizhu.png" id="rescueResponseButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">超过30分达到&nbsp;&nbsp;<b>-10分/次</b> </td>  
            <td align="center"><input id="rescueResponsesj"  type="text"  name="rescueResponsesj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
         <tr>   
            <td align="center" style="background-color:#F0FFFF">因维保质量引发困人故障</td>  
            <td align="center">不发生</td> 
            <td align="center"><input id="tiringPeoplejc"  type="text"  name="tiringPeoplejc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="100" readOnly></input></td>
            <td><input id="tiringPeopleTimes" name="tiringPeopleTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>宗</td>
            <td align="center">
            <input id="tiringPeoplebz"  type="hidden" class="easyui-validatebox" name="tiringPeoplebz" style="width:152px;text-align:center;"></input>
         <!--    <input type="button" id="tiringPeopleButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->  
            <img src="../../images/beizhu.png" id="tiringPeopleButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">&nbsp;&nbsp;<b>-3分/宗</b> </td>  
            <td align="center"><input id="tiringPeoplesj"  type="text"  name="tiringPeoplesj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
         <tr>  
            <td  rowspan="6" align="center" style="background-color:#F0FFFF">单独加减分项</td>  
            <td align="center" style="background-color:#F0FFFF">献计献策、举报违法违规、<br>行业潜规则</td>  
            <td align="center">有效或属实</td> 
            <td align="center"><input id="positiveEnergyjc"  type="text"  name="positiveEnergyjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td align="left"><input id="positiveEnergyTimes" name="positiveEnergyTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>宗</td>
            <td align="center">
            <input id="positiveEnergybz"  type="hidden" class="easyui-validatebox" name="positiveEnergybz" style="width:152px;text-align:center;"></input>
         <!--   <input type="button" id="positiveEnergyButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>   -->
         <img src="../../images/beizhu.png" id="positiveEnergyButton" title="" onclick="addBeiZhu(this.id)" />  
            </td>
            <td align="center">&nbsp;&nbsp;<b>+10分/宗</b> </td>  
            <td align="center"><input id="positiveEnergysj"  type="text"  name="positiveEnergysj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
            
        </tr>
         <tr>   
            <td align="center" style="background-color:#F0FFFF">提供专家及技术支持，<br>参与故障及事故调查处理</td>  
            <td align="center">提供及参与</td> 
            <td align="center"><input id="expertsSuggestionjc"  type="text"  name="expertsSuggestionjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td><input id="expertsSuggestionTimes" name="expertsSuggestionTimes" class="easyui-numberspinner" style="width:50px;" value="0"  data-options="min:0,editable:false"></input>宗</td>
            <td align="center">
            <input id="expertsSuggestionbz"  type="hidden" class="easyui-validatebox" name="expertsSuggestionbz" style="width:152px;text-align:center;"></input>
         <!--     <input type="button" id="expertsSuggestionButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  -->
            <img src="../../images/beizhu.png" id="expertsSuggestionButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">&nbsp;&nbsp;<b>+5分/宗</b> </td>  
            <td align="center"><input id="expertsSuggestionsj"  type="text"  name="expertsSuggestionsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        <tr>   
            <td align="center" style="background-color:#F0FFFF">积极承接监管部门指定电梯维保</td>  
            <td align="center">承接</td> 
            <td align="center"><input id="positiveWorkjc"  type="text"  name="positiveWorkjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td><input id="positiveWork"  type="text"  name="positiveWork" style="width:152px;text-align:center;" value="0" onchange="positiveWorkValue()"></input>台</td>
            <td align="center">
            <input id="positiveWorkbz"  type="hidden" class="easyui-validatebox" name="positiveWorkbz" style="width:152px;text-align:center;"></input>
         <!--   <input type="button" id="positiveWorkButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/> -->
            <img src="../../images/beizhu.png" id="positiveWorkButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">&nbsp;&nbsp;<b>+1分/台</b> </td>  
            <td align="center"><input id="positiveWorksj"  type="text"  name="positiveWorksj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr> 
        <tr>   
            <td align="center" style="background-color:#F0FFFF">采用远程监控、机器人保安</td>  
            <td align="center">采用</td> 
            <td align="center"><input id="remoteMonitorjc"  type="text"  name="remoteMonitorjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td><input id="remoteMonitor"  type="text"  name="remoteMonitor" style="width:152px;text-align:center;" value="0" onchange="remoteMonitorValue()"></input>台</td>
            <td align="center">
            <input id="remoteMonitorbz"  type="hidden" class="easyui-validatebox" name="remoteMonitorbz" style="width:152px;text-align:center;"></input>
        <!--     <input type="button" id="remoteMonitorButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>  -->
           <img src="../../images/beizhu.png" id="remoteMonitorButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">&nbsp;&nbsp;<b>+1分/30台</b> </td>  
            <td align="center"><input id="remoteMonitorsj"  type="text"  name="remoteMonitorsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        <tr>   
            <td align="center" style="background-color:#F0FFFF">购买电梯责任保险</td>  
            <td align="center">购买</td> 
            <td align="center"><input id="elevatorInsurancejc"  type="text"  name="elevatorInsurancejc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
            <td><input id="elevatorInsurance"  type="text"  name="elevatorInsurance" style="width:152px;text-align:center;" value="0" onchange="elevatorInsuranceValue()"></input>台</td>
            <td align="center">
            <input id="elevatorInsurancebz"  type="hidden" class="easyui-validatebox" name="elevatorInsurancebz" style="width:152px;text-align:center;"></input>
        <!--     <input type="button" id="elevatorInsuranceButton" value="备注" style="width:60px;" onclick="addBeiZhu(this.id)"/>   -->
             <img src="../../images/beizhu.png" id="elevatorInsuranceButton" title="" onclick="addBeiZhu(this.id)" />
            </td>
            <td align="center">&nbsp;&nbsp;<b>+1分/50台</b> </td>  
            <td align="center"><input id="elevatorInsurancesj"  type="text"  name="elevatorInsurancesj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
         <tr>   
            <td align="center" style="background-color:#F0FFFF">其他开展技术创新及工作创新</td>  
            <td align="center">开展</td> 
            <td align="center"><input id="techinnovationjc"  type="text"  name="techinnovationjc" style="width:102px;border-width :0px 0px 0px;text-align:center;"  value="0" readOnly></input></td>
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
            <td align="center">每项&nbsp;&nbsp; <b>+(1-5)分<b> </td>  
            <td align="center"><input id="techinnovationsj"  type="text"  name="techinnovationsj" style="width:102px;border-width :0px 0px 0px;text-align:center;"   readOnly></input></td>
        </tr>
        <tr id="hiddentr">
        <td><input type ="hidden" id="ywCompanyID" name="ywCompanyID"></td>
        <td><input type ="hidden" id="ratingDate" name="ratingDate"></td>
        </tr>        
		</tbody>
	</table>
	</form>
 </div>
 <div region="south" style="height:200px;">
 <div id="cc" class="easyui-layout" data-options="fit:true">  
   <div region="north" style="height:80px;">
    <table width="100%">
          <tr>
          <td align="right" colspan="6">    
                           合计基础分&nbsp;&nbsp;<input id="hjtotal"  type="text"  name="hjtotal" style="width:152px;border-width :0px 0px 1px;" readOnly></input>
                           合计实得分<input id="sjhjtotal"  type="text"  name="sjhjtotal" style="width:152px;border-width :0px 0px 1px;" readOnly></input></td>
          
         </tr>
      </table>
   <table width="100%">
    <tr>
          <td align="center"><a href="javascript:void(0)" onclick="saveSetings()" id="sbtn-save" icon="icon-save">保存</a> </td>
          </tr>
   </table>
   </div>
    
  <div region="center" style="height:200px;">
  <table id="bztb"></table>
  </div>
   </div> 
</div> 
<!--  <div region="south" fit="true" border="false" style="overflow-Y: auto; overflow-X:hidden;height:20px"></div>    -->
  
 <div id="beiZhuWin" class="easyui-window" title="备注" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false" style="width:400px;height:275px;padding:10px;">
		 <div id="beizhuDiv" class="easyui-layout" fit="true"> 
		 <div id="beizhuDivnr" region="center"  style="overflow:auto;">
		 <textarea rows="12" cols="57" id="beizhutext"></textarea>  
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
 
  <div id="enterpriseChangeWin" class="easyui-window" title="未办理变更记录" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:200px;padding:10px;">
		 <div style="margin-top:1px;">  
       <table id="enterpriseChangett"></table>
   </div>  
	</div>
  
  <div id="regularInspectionWin" class="easyui-window" title="检验未合格记录" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:200px;padding:10px;">
		 <div style="margin-top:1px;">  
       <table id="regularInspectiontt"></table>
   </div>  
  </div>
  

</body>
</html>