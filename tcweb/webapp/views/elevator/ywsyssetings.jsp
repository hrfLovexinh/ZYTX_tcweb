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

	 $('#ratingDate').datebox({
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
	         $('#ratingDate').datebox('hidePanel') 
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
	        var p = $('#ratingDate').datebox('panel'), 
	        //日期选择对象中月份
	        tds = false, 
	        //显示月份层的触发控件
	        span = p.find('span.calendar-text'); 
	        var curr_time = new Date();

	        //设置前当月
	        $("#ratingDate").datebox("setValue", myformatter(curr_time));
	
	 
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
      

	  $('#telOnDutyunattendedTimes').numberspinner({    
		    "onChange": function changePer(newValue,oldValue){
		    
		//    var telOnDutyunattended=$('#telOnDutyunattended option:selected').val();
		    var telOnDutyunattendedjc =$('#telOnDutyunattendedjc').attr("value");
		//	if(telOnDutyunattended == 1 ){  //无人值守
		    $('#telOnDutyunattendedsj').attr("value",telOnDutyunattendedjc-5*newValue);
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
	
	form =$("form[name='ywcqform']");
	form.url='/tcweb/elevator/sysYwcqSetings';	

	tcform =$("form[name='tcchangeform']");
	tcform.url='/tcweb/elevator/eletcinfochange';
	$('#sbtn-save').linkbutton();

	$('#ywsbtn-save, #tcchangebtn-save').linkbutton();

	form.form('load', '/tcweb/elevator/sysYwcqSetingsEdit/'); 

	ywgsform =$("form[name='ywgsform']");
	ywgsform.url='/tcweb/elevator/sysYwgshbSetings';	

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

function  officeSpaceValue(){
	var officeSpace = $('#officeSpace').attr("value");
	var officeSpacejc = $('#officeSpacejc').attr("value");
	if(officeSpace >= 200)
		$('#officeSpacesj').attr("value", parseInt(officeSpacejc)+5);
	else if(officeSpace < 120)
		$('#officeSpacesj').attr("value",officeSpacejc-5);
	else
		$('#officeSpacesj').attr("value",officeSpacejc);
}

function headQuartersValue(){
	 var headQuarters=$('#headQuarters option:selected').val();
	 var headQuartersjc = $('#headQuartersjc').attr("value");
	 if(headQuarters == 0)
		 $('#headQuarterssj').attr("value",parseInt(headQuartersjc)+10);
	 else
		 $('#headQuarterssj').attr("value",headQuartersjc);
     };

function fixedTelOnDutyValue(){
//	var pattern =/(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
	
	var fixedTelOnDuty=$('#fixedTelOnDuty').attr("value");
	var fixedTelOnDutyjc=$('#fixedTelOnDutyjc').attr("value");
	if(fixedTelOnDuty != ""){
//	var flag = pattern.test(fixedTelOnDuty);
//	if(flag){
	  if(headQuarters == 0)
		 $('#fixedTelOnDutysj').attr("value",fixedTelOnDutyjc-10);
	  else
		 $('#fixedTelOnDutysj').attr("value",fixedTelOnDutyjc);
//	 }
/*	else{
		$.messager.alert('操作失败', '电话号码格式不正确', 'error');
		$('#fixedTelOnDuty').attr("value","");
		}
	}
	else{
		 $('#fixedTelOnDutysj').attr("value",fixedTelOnDutyjc-10);
		} */
    };
    }
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
	
	var headQuarterssj =$('#headQuartersjc').attr("value");
	var headQuarters=$('#headQuarters option:selected').val();
	if(headQuarters == 0){ //成都本地
     headQuarterssj =parseInt(headQuarterssj)+10;
	}
	
	
    $('#headQuarterssj').attr("value",headQuarterssj);
		
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
	var positiveWorksj =parseInt($('#positiveWorkjc').attr("value"))+positiveWork;
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
	sjhjtotal = parseInt(officeSpacesj)+parseInt(headQuarterssj)+parseInt(maintenanceEleCountsj)+parseInt(avgmaintenanceEleCountsj)+parseInt(fixedTelOnDutysj)+parseInt(telOnDutyunattendedsj)+parseInt(enterpriseChangesj)+parseInt(enterpriseRecordsj)+parseInt(infoComRatesj)+parseInt(sweepCodeRatesj)+parseInt(sweepCodeInTimeRatesj)+parseInt(alarmDealwithsj)+parseInt(regularInspectionsj)+parseInt(inspectElevatorsj)+parseInt(acceptInspElevatorsj)+parseInt(maintenSceneInfosj)+parseInt(malignantEventssj)+parseInt(complaintsEventssj)+parseInt(maintenBusinesssj)+parseInt(honestsj)+parseInt(punishmentsj)+parseInt(firstRescuesj)+parseInt(secondRescuesj)+parseInt(secondRescuePointsj)+parseInt(rescueResponsesj)+parseInt(tiringPeoplesj)+parseInt(positiveEnergysj)+parseInt(expertsSuggestionsj)+parseInt(positiveWorksj)+parseInt(remoteMonitorsj)+parseInt(elevatorInsurancesj)+parseInt(techinnovationsj);
	
	$('#sjhjtotal').attr("value",sjhjtotal);
}

//合计基础总分
function hjtotal(){
	var officeSpacejc =$('#officeSpacejc').attr("value");
	var headQuartersjc =$('#headQuartersjc').attr("value");
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
	
	hjtotal = parseInt(officeSpacejc)+parseInt(headQuartersjc)+parseInt(maintenanceEleCountjc)+parseInt(avgmaintenanceEleCountjc)+parseInt(fixedTelOnDutyjc)+parseInt(telOnDutyunattendedjc)+parseInt(enterpriseChangejc)+parseInt(enterpriseRecordjc)+parseInt(infoComRatejc)+parseInt(sweepCodeRatejc)+parseInt(sweepCodeInTimeRatejc)+parseInt(alarmDealwithjc)+parseInt(regularInspectionjc)+parseInt(inspectElevatorjc)+parseInt(acceptInspElevatorjc)+parseInt(maintenSceneInfojc)+parseInt(malignantEventsjc)+parseInt(complaintsEventsjc)+parseInt(maintenBusinessjc)+parseInt(honestjc)+parseInt(punishmentjc)+parseInt(firstRescuejc)+parseInt(secondRescuejc)+parseInt(secondRescuePointjc)+parseInt(rescueResponsejc)+parseInt(tiringPeoplejc)+parseInt(positiveEnergyjc)+parseInt(expertsSuggestionjc)+parseInt(positiveWorkjc)+parseInt(remoteMonitorjc)+parseInt(elevatorInsurancejc)+parseInt(techinnovationjc);

	$('#hjtotal').attr("value",hjtotal);
}


//合计实际总分
function sjhjtotal(){
	var officeSpacesj =$('#officeSpacesj').attr("value");
	var headQuarterssj =$('#headQuarterssj').attr("value");
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
	
	sjhjtotal = parseInt(officeSpacesj)+parseInt(headQuarterssj)+parseInt(maintenanceEleCountsj)+parseInt(avgmaintenanceEleCountsj)+parseInt(fixedTelOnDutysj)+parseInt(telOnDutyunattendedsj)+parseInt(enterpriseChangesj)+parseInt(enterpriseRecordsj)+parseInt(infoComRatesj)+parseInt(sweepCodeRatesj)+parseInt(sweepCodeInTimeRatesj)+parseInt(alarmDealwithsj)+parseInt(regularInspectionsj)+parseInt(inspectElevatorsj)+parseInt(acceptInspElevatorsj)+parseInt(maintenSceneInfosj)+parseInt(malignantEventssj)+parseInt(complaintsEventssj)+parseInt(maintenBusinesssj)+parseInt(honestsj)+parseInt(punishmentsj)+parseInt(firstRescuesj)+parseInt(secondRescuesj)+parseInt(secondRescuePointsj)+parseInt(rescueResponsesj)+parseInt(tiringPeoplesj)+parseInt(positiveEnergysj)+parseInt(expertsSuggestionsj)+parseInt(positiveWorksj)+parseInt(remoteMonitorsj)+parseInt(elevatorInsurancesj)+parseInt(techinnovationsj);
    
	$('#sjhjtotal').attr("value",sjhjtotal);
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
<body class="easyui-layout" fit="true">
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">  
        <table>
        <tr>
        <td nowrap height="70px">维保单位：</td>
        <td><input id="ywCompanyIdinfo" style="width:152px;background-color:#87CEEB;" >
        <input type ="hidden" id="ywCompanyIdinfo2"></td>
        <td nowrap>时间：</td>
        <td><select id="ratingDate"  class="easyui-datebox" name="ratingDate" style="width:152px;" data-options="editable:false"> 
        </select></td>
        <td><a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a></td>  
       </tr>
       </table>
</div>
<div region="center"  border="false" style="overflow-Y: auto;"> 
      <div style="overflow-Y: auto; overflow-X:auto;">
       <table width="100%" style="overflow-Y: auto;">
       <tr> 
       <td style="background-color:#c8d9f5;" height="20px" colspan="6" align="center">基本条件</td>
       </tr>
       <tr>
          <th align="center">规则</th><th>项目</th><th align="left">实际情况</th><th>基础分</th><th>实得分</th>
          </tr>
           <tr>
           <td align="center">≥200 m2加5分,<120 m2扣5分" </td>
           <td align="center">办公面积（平方米）</td>
           <td align="left"><input id="officeSpace"  type="text" class="easyui-validatebox" name="officeSpace" style="width:152px;text-align:center;" onchange="officeSpaceValue()"></input></td>
           
           <td align="center"><input id="officeSpacejc"  type="text"  name="officeSpacejc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td>
          
           <td align="center"><input id="officeSpacesj"  type="text"  name="officeSpacesj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>
           
           </tr>
           <tr>
           <td align="center">在成都加10分</td>
           <td align="center">总部所在地</td>
           <td align="left">
           <select id="headQuarters"  name="headQuarters" style="width:152px;"  onchange="headQuartersValue()">
            <option value="0">成都</option>
            <option value="1">外地</option> 
            </select></td>
             <td align="center"><input id="headQuartersjc"  type="text"  name="headQuartersjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td>
          
           <td align="center"><input id="headQuarterssj"  type="text"  name="headQuarterssj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>
           
           </tr>
           <tr>
           <td align="center">≥500台，每多500台加1分;<100台，扣5分</td>
           <td align="center">维保电梯数量</td>
           <td align="left"><input id="maintenanceEleCount"  type="text"  name="maintenanceEleCount" style="width:152px;border-width :0px 0px 1px;"  readOnly></input></td>
            <td align="center"><input id="maintenanceEleCountjc"  type="text"  name="maintenanceEleCountjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td>
          
           <td align="center"><input id="maintenanceEleCountsj"  type="text"  name="maintenanceEleCountsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>
           
           </tr>
           <tr>
            <td align="center">≤20台，加3分,20-24台，加1分<br>31-40台扣5分，>40台扣10分</td>
           <td align="center">人均维保数量</td>
           <td align="left"><input id="avgmaintenanceEleCount"  type="text"  name="avgmaintenanceEleCount" style="width:152px;border-width :0px 0px 1px;" readOnly></input></td>
           <td align="center"><input id="avgmaintenanceEleCountjc"  type="text"  name="avgmaintenanceEleCountjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td>
          
           <td align="center"><input id="avgmaintenanceEleCountsj"  type="text"  name="avgmaintenanceEleCountsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>
           
           </tr> 
            <tr>
           <td align="center">无固定电话，扣10分</td>
           <td align="center">值班固定电话</td>
           <td align="left"><input id="fixedTelOnDuty"  type="text"  name="fixedTelOnDuty" style="width:152px;" onchange="fixedTelOnDutyValue()"></input></td>
             <td align="center"><input id="fixedTelOnDutyjc"  type="text"  name="fixedTelOnDutyjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td>
          
           <td align="center"><input id="fixedTelOnDutysj"  type="text"  name="fixedTelOnDutysj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>
           
           </tr>
           <tr>
           <td align="center">无人接听，每次扣5分</td>
           <td align="center">电话值守无人接听（次）</td>
           <td align="left">
     <!--       <select id="telOnDutyunattended"  name="telOnDutyunattended" style="width:152px;" onchange="telOnDutyunattendedValue()">
            <option value="2"></option>
            <option value="0">有</option>
            <option value="1">无</option> 
            </select>  -->
            <input id="telOnDutyunattendedTimes" class="easyui-numberspinner" style="width:152px;" value="0" data-options="min:0,editable:false" /></input>   
            <input type="button" id="telOnDutyunattendedButton" value="备注" style="width:60px;"/></td>
            <td align="center"><input id="telOnDutyunattendedjc"  type="text"  name="telOnDutyunattendedjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td>
          
           <td align="center"><input id="telOnDutyunattendedsj"  type="text"  name="telOnDutyunattendedsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>
           
           </tr> 
            <tr>
             <td align="center">发生变化未办理变更手续，每宗扣10分</td>
           <td align="center">企业变更未办理（宗）</td>
           <td align="left">
         <!--   <select id="enterpriseChange"  name="enterpriseChange" style="width:152px;" onchange="enterpriseChangeValue()">
            <option value="2"></option>
            <option value="0">办理变更</option>
            <option value="1">未办理变更</option> 
            </select>  -->
            <input id="enterpriseChangeTimes" class="easyui-numberspinner" style="width:152px;"  value="0" data-options="min:0,editable:false"></input>  
            <input type="button" id="enterpriseChangeButton" value="备注" style="width:60px;"/></td>
             <td align="center"><input id="enterpriseChangejc"  type="text"  name="enterpriseChangejc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td>
          
           <td align="center"><input id="enterpriseChangesj"  type="text"  name="enterpriseChangesj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>
           
           </tr>  
           <tr>
           <td align="center">未备案或未办理变更备案扣10分</td>
            <td align="center">企业备案</td>  
            <td align="left"><select id="enterpriseRecord"  name="enterpriseRecord" style="width:152px;" onchange="enterpriseRecordValue()">
            <option value="0">有</option>
            <option value="1">无</option> 
            </select>
            </td>
             <td align="center"><input id="enterpriseRecordjc"  type="text"  name="enterpriseRecordjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td>
          
           <td align="center"><input id="enterpriseRecordsj"  type="text"  name="enterpriseRecordsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>
           
            </tr>
            <tr>
            </tr>
            <tr>
            </tr>
            <tr>
            </tr>
            <tr>
            <td colspan="6"><hr></td>
            </tr>
             <tr> 
       <td style="background-color:#c8d9f5;" height="20px" align="center" colspan="6">电梯安全公共服务平台使用情况</td>
       </tr>
            <tr>
            <td align="center">按信息不完整率百分点数扣</td>
           <td align="center">信息完整率</td>
           <td align="left"><input id="infoComRate"  type="text"  name="infoComRate" style="width:152px;border-width :0px 0px 1px;" readOnly></input></td>
           <td align="center"><input id="infoComRatejc"  type="text"  name="infoComRatejc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td> 
           <td align="center"><input id="infoComRatesj"  type="text"  name="infoComRatesj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>
            </tr>
            
            <tr>
            <td align="center">按未扫码率的百分点数扣</td>
            <td align="center">扫码维保率</td>
           <td align="left"><input id="sweepCodeRate"  type="text"  name="sweepCodeRate" style="width:152px;border-width :0px 0px 1px;" readOnly></input></td>
           <td align="center"><input id="sweepCodeRatejc"  type="text"  name="sweepCodeRatejc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td> 
           <td align="center"><input id="sweepCodeRatesj"  type="text"  name="sweepCodeRatesj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
            </tr>
            
           <tr>
           <td align="center">按未按时扫码率的百分点数扣</td>
           <td align="center">按时扫码维保率</td>
           <td align="left"><input id="sweepCodeInTimeRate"  type="text"  name="sweepCodeInTimeRate" style="width:152px;border-width :0px 0px 1px;" readOnly></input></td>
           <td align="center"><input id="sweepCodeInTimeRatejc"  type="text"  name="sweepCodeInTimeRatejc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td> 
           <td align="center"><input id="sweepCodeInTimeRatesj"  type="text"  name="sweepCodeInTimeRatesj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
           </tr>
           
           <tr>
           <td align="center">未处理每条扣2分</td>
            <td align="center">平台报警处理情况</td>
           <td align="left"><input id="alarmDealwith"  type="text"  name="alarmDealwith" style="width:152px;border-width :0px 0px 1px;" readOnly></input></td>
            <td align="center"><input id="alarmDealwithjc"  type="text"  name="alarmDealwithjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td> 
           <td align="center"><input id="alarmDealwithsj"  type="text"  name="alarmDealwithsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
         
           </tr>
           <tr>
            <td colspan="6"><hr></td>
            </tr>
              <tr> 
       <td style="background-color:#c8d9f5;" height="20px" align="center" colspan="6">日常监管及投诉情况</td>
       </tr>
            <tr>
           <td align="center">一次性检验不合格每台扣2分</td> 
           <td align="center">电梯定期检验不合格（台）</td>
           <td align="left">
        <!--    <select id="regularInspection"  name="regularInspection" style="width:152px;" onchange="regularInspectionValue()">
            <option value="2"></option>
            <option value="0">合格</option>
            <option value="1">不合格</option> 
            </select>  -->
            <input id="regularInspectionTimes" class="easyui-numberspinner" style="width:152px;" value="0" data-options="min:0,editable:false">  
            <input type="button" id="regularInspectionButton" value="备注" style="width:60px;"/></td>
           <td align="center"><input id="regularInspectionjc"  type="text"  name="alarmDealwithjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td> 
           <td align="center"><input id="regularInspectionsj"  type="text"  name="alarmDealwithsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
            </tr>
            <tr>
            <td align="center">监督检查、执法检查、监督抽查、定期检验中发现严重隐患，每台扣5分<br>发现安全回路短接，每台扣20分</td>
          <td align="center">在用电梯监督抽查情况<br>严重隐患<br>回路短接</td>
           <td align="left">
        <!--    <select id="inspectElevator"  name="inspectElevator" style="width:152px;" onchange="inspectElevatorValue()">
            <option value="2"></option>
            <option value="0">合格</option>
            <option value="1">不合格</option> 
            </select> -->
          <br><input id="inspectElevatorTimes" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">  
          <br><input id="inspectElevatorTimes2" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <input type="button" id="inspectElevatorButton" value="备注" style="width:60px;"/></td> 
           <td align="center"><input id="inspectElevatorjc"  type="text"  name="inspectElevatorjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td> 
           <td align="center"><input id="inspectElevatorsj"  type="text"  name="inspectElevatorsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
            </tr>
            <tr>
             <td align="center">拒绝或不配合抽查、执法检查，每次扣10分</td>
            <td align="center">接受监督检查不配合（次）</td>
            <td align="left">
          <!--   <select id="acceptInspElevator"  name="acceptInspElevator" style="width:152px;" onchange="acceptInspElevatorValue()">
            <option value="2"></option>
            <option value="0">配合</option>
            <option value="1">不配合</option> 
            </select> -->
            <input id="acceptInspElevatorTimes" class="easyui-numberspinner" style="width:152px;"  value="0" data-options="min:0,editable:false">
            <input type="button" id="acceptInspElevatorButton" value="备注" style="width:60px;"/></td>
           <td align="center"><input id="acceptInspElevatorjc"  type="text"  name="acceptInspElevatorjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td> 
           <td align="center"><input id="acceptInspElevatorsj"  type="text"  name="acceptInspElevatorsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
         
           </tr>
           <tr>
           <td align="center">维保现场防护无防护</td>
            <td align="center">维保现场防护无防护</td>
            <td align="left">
        <!--     <select id="maintenSceneInfo"  name="maintenSceneInfo" style="width:152px;" onchange="maintenSceneInfoValue()">
            <option value="2"></option>
            <option value="0">有防护</option>
            <option value="1">没有防护</option> 
            </select> -->
            <input id="maintenSceneInfoTimes" class="easyui-numberspinner" style="width:152px;"  value="0" data-options="min:0,editable:false">
            <input type="button" id="maintenSceneInfoButton" value="备注" style="width:60px;"/></td>
           
            <td align="center"><input id="maintenSceneInfojc"  type="text"  name="maintenSceneInfojc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td> 
           <td align="center"><input id="maintenSceneInfosj"  type="text"  name="maintenSceneInfosj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
         
           </tr>
           <tr>
           <td align="center">一般事故每宗扣30分，较大事故扣50分<br>重大及以上或重大影响、年度2次以上事故、<br>事故后不配合调查处理的，直接列入最低信用等级</td>
           <td align="center">因维保原因发生安全事故重大社会影响事件<br>一般事故<br>较大事故<br>重大事故</td>
           <td align="left">
         <!--   <select id="malignantEvents"  name="malignantEvents" style="width:152px;" onchange="malignantEventsValue()">
            <option value="2"></option>
            <option value="0">没有</option>
            <option value="1">有</option> 
            </select> -->
           <br><br><input id="malignantEventsTimes" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">  
           <br><input id="malignantEventsTimes2" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <br><input id="malignantEventsTimes3" class="easyui-numberspinner" style="width:152px;" value="0" data-options="min:0,editable:false">
            <input type="button" id="malignantEventsButton" value="备注" style="width:60px;"/></td>  
           <td align="center"><input id="malignantEventsjc"  type="text"  name="malignantEventsjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td> 
           <td align="center"><input id="malignantEventssj"  type="text"  name="malignantEventssj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
         
           </tr>
             <tr>
             <td align="center">每宗扣5分，年度内连续被投诉2次及以上的，每宗扣20分</td>
           <td align="center">因维保质量而引发的投诉<br>（领导信箱、公开电话、网络理政平台）<br>一般投诉<br>连续投诉</td>
           <td align="left">
        <!--     <select id="complaintsEvents"  name="complaintsEvents" style="width:152px;" onchange="complaintsEventsValue()">
            <option value="2"></option>
            <option value="0">无</option>
            <option value="1">有</option> 
            </select> -->
           <br><input id="complaintsEventsTimes" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">  
           <br><input id="complaintsEventsTimes2" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <input type="button" id="complaintsEventsButton" value="备注" style="width:60px;"/></td>   
           <td align="center"><input id="complaintsEventsjc"  type="text"  name="complaintsEventssjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td> 
           <td align="center"><input id="complaintsEventssj"  type="text"  name="complaintsEventssj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
         
           </tr>
             <tr>
             <td align="center">存在转包或分包，每宗扣20分</td>
           <td align="center">维保业务转分包（宗）</td>
           
           <td align="left">
        <!-- <select id="maintenBusiness"  name="maintenBusiness" style="width:152px;" onchange="maintenBusinessValue()">
            <option value="2"></option>
            <option value="0">无转包</option>
            <option value="1">有转包</option> 
            </select>  -->
           <input id="maintenBusinessTimes" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <input type="button" id="maintenBusinessButton" value="备注" style="width:60px;"/></td>                       
           <td align="center"><input id="maintenBusinessjc"  type="text"  name="maintenBusinessjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td> 
           <td align="center"><input id="maintenBusinesssj"  type="text"  name="maintenBusinesssj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
         
           </tr>
            <tr>
            <td align="center">伪造涂改工作记录、检验报告、管理标识等技术资料，<br>不诚实手段获取资质，不正当手段参与市场竞争，每宗扣20分</td> 
           <td align="center">不诚实守信（宗）</td> 
           <td align="left">
           <!--   <select id="honest"  name="honest" style="width:152px;" onchange="honestValue()">
            <option value="2"></option>
            <option value="0">守信</option>
            <option value="1">不守信</option> 
            </select> -->
           <input id="honestTimes" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <input type="button" id="honestButton" value="备注" style="width:60px;"/></td>                       
          
           <td align="center"><input id="honestjc"  type="text"  name="honestjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td> 
           <td align="center"><input id="honestsj"  type="text"  name="honestsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
         
           </tr>
            <tr>
             <td align="center">现场监督检查、执法检查、重点时段抽查因维保管理及维保质量被整改，每扣2分；<br>被下达监察意见过通报，每宗扣5分；<br>本监督连续被下达监察指令或通报2次及以上，每宗扣20分；<br>违反法律、法规及技术规范，每宗扣20分</td> 
           <td align="center">行政查处及处罚情况<br>维保管理及维保质量被整改<br>被下达监察意见过通报<br>连续被下达监察指令或通报2次及以上<br>违反法律、法规及技术规范</td> 
           <td align="left">
      <!--       <select id="punishment"  name="punishment" style="width:152px;" onchange="punishmentValue()">
            <option value="2"></option>
            <option value="0">无</option>
            <option value="1">有</option> 
            </select> -->
            <br><br><input id="punishmentTimes" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">                      
            <br><input id="punishmentTimes2" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
            <br><input id="punishmentTimes3" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">                 
            <br><input id="punishmentTimes4" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false"> 
           <input type="button" id="punishmentButton" value="备注" style="width:60px;"/>
           </td> 
           <td align="center"><input id="punishmentjc"  type="text"  name="punishmentjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td> 
           <td align="center"><input id="punishmentsj"  type="text"  name="punishmentsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
         
           </tr>
           <tr>
            <td colspan="6"><hr></td>
            </tr>
               <tr> 
       <td style="background-color:#c8d9f5;" height="20px" align="center" colspan="6">应急救援处置情况</td>
       </tr>
             <tr>
             <td align="center">未实施，每次扣5分</td>
            <td align="center">一级救援情况</td>
            <td align="left">
         <!--    <select id="firstRescue"  name="firstRescue" style="width:152px;" onchange="firstRescueValue()">
            <option value="2"></option>
            <option value="0">实施</option>
            <option value="1">未实施</option> 
            </select> -->
           <input id="firstRescueTimes" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <input type="button" id="firstRescueButton" value="备注" style="width:60px;"/></td> 
           <td align="center"><input id="firstRescuejc"  type="text"  name="firstRescuejc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td> 
           <td align="center"><input id="firstRescuesj"  type="text"  name="firstRescuesj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
          
         </tr>  
         <tr>
         <td align="center">每次加3分,未实施，每次扣5分</td>
           <td align="center">二级救援情况<br>未实施（次）<br>实施（次）</td>
           <td align="left">
         <!--   <select id="secondRescue"  name="secondRescue" style="width:152px;" onchange="secondRescueValue()">
            <option value="2"></option>
            <option value="1">未实施</option> 
            </select> -->
          <br><br><input id="secondRescueTimes" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           
        <!--    <select id="secondRescue2"  name="secondRescue2" style="width:152px;" onchange="secondRescueValue2()">
            <option value="2"></option>
            <option value="1">实施</option> 
            </select> -->
           <br><input id="secondRescueTimes2" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <input type="button" id="secondRescueButton" value="备注" style="width:60px;"/>
           </td> 
           <td align="center"><input id="secondRescuejc"  type="text"  name="secondRescuejc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td> 
           <td align="center"><input id="secondRescuesj"  type="text"  name="secondRescuesj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
           
         </tr> 
         <tr>
         <td align="center">10-19加1分，20-29加2分，30-39加3分，40及以上加4分</td>
           <td align="center">参与二级救援布点</td>
           <td align="left"><input id="secondRescuePoint"  type="text"  name="secondRescuePoint" style="width:152px;text-align:center;"  onchange="secondRescuePointValue()"></input></td>  
           <td align="center"><input id="secondRescuePointjc"  type="text"  name="secondRescuePointjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td> 
           <td align="center"><input id="secondRescuePointsj"  type="text"  name="secondRescuePointsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
           
         </tr> 
         <tr>
         <td align="center">超过30分达到，每次扣10分</td>
           <td align="center">应急救援响应超过30分钟（次）</td>
           <td align="left">
       <!--      <select id="rescueResponse"  name="rescueResponse" style="width:152px;" onchange="rescueResponseValue()">
            <option value=""></option>
            <option value="0">30分钟内救援</option>
            <option value="1">超过30分钟</option> 
            </select> -->
           <input id="rescueResponseTimes" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <input type="button" id="rescueResponseButton" value="备注" style="width:60px;"/> </td> 
           <td align="center"><input id="rescueResponsejc"  type="text"  name="rescueResponsejc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td> 
           <td align="center"><input id="rescueResponsesj"  type="text"  name="rescueResponsesj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
             
         </tr> 
          <tr>
           <td align="center">每宗扣3分</td>
           <td align="center">因维保质量引发困人故障（宗）</td>
           <td align="left">
       <!--     <select id="tiringPeople"  name="tiringPeople" style="width:152px;" onchange="tiringPeopleValue()">
            <option value=""></option>
            <option value="0">无</option>
            <option value="1">有</option> 
            </select>  -->
           <input id="tiringPeopleTimes" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <input type="button" id="tiringPeopleButton" value="备注" style="width:60px;"/> </td> 
           <td align="center"><input id="tiringPeoplejc"  type="text"  name="tiringPeoplejc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="100" readOnly></input></td> 
           <td align="center"><input id="tiringPeoplesj"  type="text"  name="tiringPeoplesj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
             
         </tr>
         <tr>
            <td colspan="6"><hr></td>
            </tr>
             <tr> 
       <td style="background-color:#c8d9f5;" height="20px" align="center" colspan="6">单独加减分项</td>
       </tr>
             <tr>
             <td align="center">每宗加10分</td>
           <td align="center">献计献策、举报违法违规<br>行业潜规则</td>
           <td align="left">
        <!--      <select id="positiveEnergy"  name="positiveEnergy" style="width:152px;" onchange="positiveEnergyValue()">
            <option value=""></option>
            <option value="0">无</option>
            <option value="1">有</option> 
            </select> -->
           <input id="positiveEnergyTimes" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <input type="button" id="positiveEnergyButton" value="备注" style="width:60px;"/> </td> 
           <td align="center"><input id="positiveEnergyjc"  type="text"  name="positiveEnergyjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td> 
           <td align="center"><input id="positiveEnergysj"  type="text"  name="positiveEnergysj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
               
         </tr>
         <tr>
           <td align="center">每宗加5分</td>
           <td align="center">提供专家及技术支持<br>参与故障及事故调查处理</td>
           <td align="left">
         <!--    <select id="expertsSuggestion"  name="expertsSuggestion" style="width:152px;" onchange="expertsSuggestionValue()">
            <option value=""></option>
            <option value="0">无</option>
            <option value="1">有</option> 
            </select> -->
           <input id="expertsSuggestionTimes" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <input type="button" id="expertsSuggestionButton" value="备注" style="width:60px;"/> </td> 
           <td align="center"><input id="expertsSuggestionjc"  type="text"  name="expertsSuggestionjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td> 
           <td align="center"><input id="expertsSuggestionsj"  type="text"  name="expertsSuggestionsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
             
         </tr>
         <tr>
          <td align="center">每台加1分</td>
           <td align="center">积极承接监管部门指定电梯维保</td>
           <td align="left"><input id="positiveWork"  type="text"  name="positiveWork" style="width:152px;text-align:center;" onchange="positiveWorkValue()"></input></td> 
           <td align="center"><input id="positiveWorkjc"  type="text"  name="positiveWorkjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td> 
           <td align="center"><input id="positiveWorksj"  type="text"  name="positiveWorksj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
            
         </tr>
         <tr>
           <td align="center">每30台加1分</td>
           <td align="center">采用远程监控、机器人保安电梯（台）</td>
           <td align="left"><input id="remoteMonitor"  type="text"  name="remoteMonitor" style="width:152px;text-align:center;"  onchange="remoteMonitorValue()"></input></td>  
           <td align="center"><input id="remoteMonitorjc"  type="text"  name="remoteMonitorjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td> 
           <td align="center"><input id="remoteMonitorsj"  type="text"  name="remoteMonitorsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
            
         </tr>
          <tr>
          <td align="center">每50台加1分</td> 
           <td align="center">购买电梯责任保险（台）</td>
           <td align="left"><input id="elevatorInsurance"  type="text"  name="elevatorInsurance" style="width:152px;text-align:center;" onchange="elevatorInsuranceValue()"></input></td>
           <td align="center"><input id="elevatorInsurancejc"  type="text"  name="elevatorInsurancejc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td> 
           <td align="center"><input id="elevatorInsurancesj"  type="text"  name="elevatorInsurancesj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
              
         </tr>
          <tr>
          <td align="center">每项加1-5分</td> 
           <td align="center">其他开展技术创新及工作创新<br>1分项<br>2分项<br>3分项<br>4分项<br>5分项</td> 
            <td align="left">
        <!--     <select id="techinnovation"  name="techinnovation" style="width:152px;" onchange="techinnovationValue()">
            <option value=""></option>
            <option value="0">无</option>
            <option value="1">有</option> 
            </select>  -->
           <br><input id="techinnovationTimes" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <br><input id="techinnovationTimes2" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <br><input id="techinnovationTimes3" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <br><input id="techinnovationTimes4" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <br><input id="techinnovationTimes5" class="easyui-numberspinner" style="width:152px;" value="0"  data-options="min:0,editable:false">
           <input type="button" id="techinnovationButton" value="备注" style="width:60px;"/> </td> 
           <td align="center"><input id="techinnovationjc"  type="text"  name="techinnovationjc" style="width:152px;border-width :0px 0px 1px;text-align:center;"  value="0" readOnly></input></td> 
           <td align="center"><input id="techinnovationsj"  type="text"  name="techinnovationsj" style="width:152px;border-width :0px 0px 1px;text-align:center;"   readOnly></input></td>  
          
         </tr>
           
      </table>
    </div>
   </div> 
   <div region="south" style="height:60px;">
   <div>
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
   </div> 
<!--  <div region="south" fit="true" border="false" style="overflow-Y: auto; overflow-X:hidden;height:20px"></div>    -->
  
 <div id="telOnDutyunattendedWin" class="easyui-window" title="电话无人值守记录" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:200px;padding:10px;">
		 <div style="margin-top:1px;">  
       <table id="telOnDutyunattendedtt"></table>
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