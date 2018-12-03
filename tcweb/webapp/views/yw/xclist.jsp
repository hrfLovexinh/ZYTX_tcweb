<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserRinghtInfo,com.zytx.init.GlobalFunction" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">  -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">

<script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script> 
 <style>
    .datagrid-header-row td{background-color:rgb(226,237,255);color:#0E2D5F;}
  </style>
<% 
String cityName = GlobalFunction.cityName;
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int  role = 0; 
int  userId=0;
if(userinfo!=null){
	 role = userinfo.getRole(); 
	 userId=userinfo.getId();
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
		    userId =user.getId();
	}

boolean flag =false;
UserRinghtInfo userRinghtInfo =null;

if(userId>0){
	String sql ="select  isnull(tr.ywtx,0) as ywtx from TwoCodeUserInfo  t left join TwoCodeRightsTable tr on t.id =tr.userId where t.id = ?";
	userRinghtInfo =UserRinghtInfo.findFirstBySql(UserRinghtInfo.class, sql, new Object[] {userId});
	if(userRinghtInfo != null){
		if(userRinghtInfo.getYwtx()==1)
			flag=true; //有权利查看	
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

	 function strDateTime(str)
	 {
//	 var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
//   var r = str.match(/^(((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\d):[0-5]?\d:[0-5]?\d)$/);
	 var r = str.match(/^[1-9][0-9]{3}-(0?[1-9]|1[0|1|2])-(0?[1-9]|[1|2][0-9]|3[0|1])\s(0?[1-9]|1[0-9]|2[0-3]):(0?[0-9]|[1|2|3|4|5][0-9])$/);
	 if(r==null)
	 return false; 
	/* var d= new Date(r[1], r[3]-1, r[4]); 
	 return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);*/
	 return true;
	 }



var opt =0; //0:增加 ；1：编辑
$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	var inputWidth = $('#registNumber').width(); 
	 $("#areainfo").css("width", inputWidth);
	 $("#qtownshipStreets").css("width", inputWidth);

	//$('#imgpic').bind("click",testDid);
    
    
	ccomb =$('#userId').combobox({
		url:'/tcweb/user/getYwUserList',
	    valueField:'id',
	    textField:'userName'
	}
	);
    
  //  ccomb =$('#userId').combobox();
	ccomb2 =$('#ywCompanyId').combobox({
		url:'/tcweb/elevator/getYwCompanyList',
		valueField:'id',
		textField:'companyName',
		onSelect:function(record){ 
		ccomb.combobox({
            url: '/tcweb/user/getUserListByCompanyId?companyId='+record.id,
            valueField: 'id',
            textField: 'userName'
        }).combobox('reload');
	    }
		});
    /*
	ccomb2 =$('#ywCompanyId').combobox({
		url:'/tcweb/elevator/getYwCompanyList',
	    valueField:'id',
	    textField:'companyName',
	    onSelect:function(record){ 
		ccomb.combobox({
            url: '/tcweb/user/getUserListByCompanyId?companyId='+record.id,
            valueField: 'id',
            textField: 'userName'
        }).combobox('reload');

	    }
	}
	);
  */
  combt=$('#qtownshipStreets').combobox({
  	valueField:'id',
	textField:'companyName'
	});
		    
  $('#qtownshipStreets').combobox({
  	filter: function(q, row){
  	//    ywName = q;
  		var opts = $(this).combobox('options');
  		return row[opts.textField].indexOf(q) >= 0;
  	}
  });

  <%if(role !=10 && role != 11){ %>
  $('#areainfo').combobox({   
      url:'/tcweb/elevator/areaInfoList',   
      valueField:'area',   
      textField:'area'  
  });  
  <%}%>

  <%if((role == 1 || role ==2)||(role == 22 || role ==23)) { %>  
  comba =$('#areainfo').combobox({
		 onSelect: function (record) {
	     combt.combobox({
 //    url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.areaCode),
     url: '/tcweb/elevator/getjdbCompanyListByNewarea?companyArea='+encodeURI(record.area),
     valueField: 'id',
     textField: 'companyName'
 }).combobox('clear');
  }
	});
  <% } else if(role == 10 || role ==11){ %>
  combt=$('#qtownshipStreets').combobox({
	  url:'/tcweb/elevator/getjdbCompanyListByarea2',
	  	valueField:'id',
		textField:'companyName'
		});
			    
	  $('#qtownshipStreets').combobox({
	  	filter: function(q, row){
	  	//    ywName = q;
	  		var opts = $(this).combobox('options');
	  		return row[opts.textField].indexOf(q) >= 0;
	  	}
	  });
  <% }%>
/*
  comba =$('#areainfo').combobox({
		 onSelect: function (record) {
		 combt.combobox({
     url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.value),
     valueField: 'id',
     textField: 'companyName'
 }).combobox('clear');

	     }
		});  */

	$('#btn-save,#btn-cancel,#btn-ok').linkbutton(); 
	win = $('#car-window3').window({  closed:true,draggable:false }); 
	form = win.find('form');
	
	
	shenhewin = $('#shenhe-window').window({  closed:true,draggable:false,modal:true,onClose:function(){$('#ywPoint').empty(); 
  //  $('#map_X').attr("value",plng);
  //  $('#map_Y').attr("value",plat);
    } });
	
	grid=$('#xctt').datagrid({
	    title:'巡检记录列表',
	    fitColumns:true,
 	    striped:true,
	    pageSize:25,
	    pageList:[15,25,30,35,40],
	    url:'/tcweb/yw/xclist',
	    queryParams:{},
	    columns:[[
	        {field:'registNumber',align:'center',halign:'center',title:'电梯编号',width:$(this).width() * 0.076,formatter: function(value,rec,index) {
            	<% if("1".equals(cityName)){ %>
                return "N"+value;
                <% } else {%>
                return value;
                <% }%>
           }},
	        {field:'address',align:'left',halign:'center',title:'地址',width:$(this).width() * 0.076},
	        {field:'buildingName',align:'left',halign:'center',title:'楼盘名称',width:$(this).width() * 0.076},
	        {field:'area',align:'center',title:'行政区划',width:$(this).width() * 0.076},
	        {field:'jdbCompanyName',align:'left',halign:'center',title:'乡镇或街办',width:$(this).width() * 0.076},
	        {field:'ywKind',align:'center',title:'种类',width:$(this).width() * 0.076},
	  //    {field:'maintainTypecode',title:'类型',width:50},
	        {field:'startTime',align:'left',halign:'center',title:'巡检时间',width:$(this).width() * 0.076},
	//        {field:'endTime',title:'结束时间',width:120},
	//       {field:'dateSpan',title:'时长（分钟）',width:70},
	        {field:'sPosition',align:'center',title:'位置',width:$(this).width() * 0.076},
	  //      {field:'ePosition',title:'结束位置',width:60},
	        {field:'userName',align:'center',halign:'center',title:'巡检人员',width:$(this).width() * 0.076},
	        {field:'contactPhone',align:'center',halign:'center',title:'巡检人员电话',width:$(this).width() * 0.076},
	        {field:'remark',align:'left',halign:'center',title:'内容',width:$(this).width() * 0.076},
	        
	  //      {field:'companyName',title:'巡查人员所属单位',width:150},
	        {field:'subTime',align:'left',halign:'center',width:$(this).width() * 0.076,title:'上传时间',formatter: function(value,rec,index) {
                 if(value)
                     return value.substring(0,16);
                 else
                     return value;
		         }},
		   {field:'ywstatus',align:'center',title:'状态',width:100,formatter: function(value,rec,index) {
		        	 var registNumber = ''+rec.registNumber;
			         var startTime =''+rec.startTime;
			         var picNum =rec.picNum;
			         var id = rec.id;
			         var flexStartx =rec.flexStartx;
			         var flexStarty =rec.flexStarty;
			         var flexEndx =rec.flexEndx;
			         var flexEndy =rec.flexEndy;
			         var ywResult =rec.ywResult;
			         var role  =<%=role%>;
			         var userId =<%=userId%>;
	                 if('0'==value){
		                // if(picNum>0)   
	              //       return "审核中"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"0"+")'/>";
	             //       return "审核中"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"0"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+2+")'/>";
	                    return '审核中'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'0'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+2+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
		                  
	                    // else
		                // return "审核中";     
		             }else if('4'==value){
		            	// if(picNum>0)
                  //       return "无效"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"4"+")'/>";
		          //  	  return "无效"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"4"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+2+")'/>";
	 	                  return '无效'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'4'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+2+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	          
		            	// else 	   
	                    // return "无效";
		             }
	                 else{
		                // if(picNum>0)
                    //     return "通过"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"1"+")'/>";
		           //        return "通过"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"1"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+2+")'/>";
	 	                     return '通过'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'1'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+2+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	    
		                // else
	                	// return "通过";
	                 }
			         }}     
	    ]],
	    pagination:true,
	    singleSelect:true
	});	
	$('#xctt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  
 	  	  
}
);

function clearQuery(){
	$('#registNumber').attr("value","");
	$('#buildingName').attr("value","");
	$("#qstartTime").datebox("setValue","");  
	$("#qendTime").datebox("setValue","");  
	 <%if(role !=10 && role != 11){ %>
	$('#areainfo').combobox('clear');
	 <% } %>
	$('#qtownshipStreets').combobox('clear');  
	$('#quserName').attr("value","");
}

function query(){  
	var registNumber=$('#registNumber').attr("value");
	var buildingName=$('#buildingName').attr("value");
	var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue"); 
	var area = "";
	  <%if(role !=10 && role != 11){ %>
	  area =$('#areainfo').combobox('getValue'); 
	 <% } %>
     var qtownshipStreets =$('#qtownshipStreets').combobox('getValue'); 
     var userName =$('#quserName').attr("value");
     if(qtownshipStreets==""){
	    	qtownshipStreets = 0;
	        }
     
     grid.datagrid("options").url='/tcweb/yw/xcquery';
     grid.datagrid("options").queryParams={'registNumber':registNumber,'buildingName':buildingName,'area':area,'qstartTime':qstartTime,'qendTime':qendTime,'townshipStreets':qtownshipStreets,"userName":userName};
     
    $('#xctt').datagrid('reload');
	}

var shenheid=0;
var ywstatusValue=0;
function openShenheInfo(registNumber,startTime,id,ywstatus,flexStartx,flexStarty,flexEndx,flexEndy,ywResult,role,userId,windex){
	shenheid=id;
	ywstatusValue=ywstatus;
	shenhewin.window('open');
	var ywimg="";
//	ywing ='<img width="400" height="480" align="center" src="<%=request.getContextPath()%>'+'/servlet/ywImage.jpg?registNumber='+registNumber+'&startTime='+startTime+'"/>';
	ywing ="<iframe id='flex' src='flex.jsp?registNumber="+registNumber+"&startTime="+startTime+"&id="+id+"&flexStartx="+flexStartx+"&flexStarty="+flexStarty+"&flexEndx="+flexEndx+"&flexEndy="+flexEndy+"&ywResult="+ywResult+"&role="+role+"&userId="+userId+"' width='100%' height='100%' scrolling='no' ></iframe>";
	document.all.ywImg.innerHTML=ywing;
	if(ywstatus==0){
		document.all.shenheDiv.innerHTML='<a href="javascript:void(0)" onclick="shenhe()" id="btn-ok" icon="icon-ok">审核</a>';
	 //   $('#btn-ok').html("审核");
		$('#btn-ok').linkbutton();
		}
	else{ 
		document.all.shenheDiv.innerHTML='<a href="javascript:void(0)" onclick="shenhe()" id="btn-no" icon="icon-no">关闭</a>';
	//	$('#btn-no').html("关闭");
		$('#btn-no').linkbutton();
	}
	ywPoint();
}
	


function closeWindow(){ 
	win.window('close');
	}



function colseWinDetail(){
	$("form input").css({border:'1px solid' });
	
	 $(".fontShow").show();	
	 $('#btn-save,#btn-cancel').show();  
	// $('#registNumberinfo').attr("disabled","disabled");
     /*
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
	*/
	$('#table2').hide();

//	$('#qy_nameinfo').combobox('enable'); 
}

function saveCar3(){ 
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){
     //   return $(this).form('validate');
		var registNumber=$('#registNumberinfo').attr("value"); 
		var ywKind= $('#ywKind').combobox('getValue'); 
		var maintainTypecode =$('#maintainTypecode').combobox('getValue');
		var startTime =$('#startTime').datetimebox('getValue'); 
		var endTime =$('#endTime').datetimebox('getValue');
		var sPosition =$('#sPosition').combobox('getValue');
		var ePosition =$('#ePosition').combobox('getValue');
		
		var ywCompanyId =$('#ywCompanyId').combobox('getValue');
	//	alert("ywCompanyId:"+ywCompanyId);
		
		var userId =$('#userId').combobox('getValue');
	//	alert("userId:"+userId);
       
			if (!registNumber) {
				$.messager.alert('操作失败', '电梯编码不能为空', 'error');
				return false;
			}
			if (!ywKind) {
				$.messager.alert('操作失败', '维保种类不能为空', 'error');
				return false;
			}

			if (!maintainTypecode) {
				$.messager.alert('操作失败', '维保类型不能为空', 'error');
				return false;
			}

			if (!startTime) {
				$.messager.alert('操作失败', '开始时间不能为空', 'error');
				return false;
			}

			if(startTime != ""){
				if (!strDateTime(startTime)) {
					$.messager.alert('操作失败', '开始时间格式形如：2013-01-04 22:14', 'error');
					return false;
				}
				}

			if (!endTime) {
				$.messager.alert('操作失败', '结束时间不能为空', 'error');
				return false;
			}

			if(endTime != ""){
				if (!strDateTime(endTime)) {
					$.messager.alert('操作失败', '结束时间格式形如：2013-01-04 22:14', 'error');
					return false;
				}
				}

			if(datecompare(startTime,endTime) !=1){
				$.messager.alert('操作失败', '开始时间必须小于结束时间', 'error');
				}

			if (!sPosition) {
				$.messager.alert('操作失败', '开始位置不能为空', 'error');
				return false;
			}

			if (!ePosition) {
				$.messager.alert('操作失败', '结束位置不能为空', 'error');
				return false;
			}

			if (!ywCompanyId) {
				$.messager.alert('操作失败', '维保单位不能为空', 'error');
				return false;
			}
	

				if (!userId) {
					$.messager.alert('操作失败', '维保人员不能为空', 'error');
					return false;
				}

			

				

			  

			return true;
			//	return false;
		},

		success : function(data) {
			eval("data=" + "'" + data + "'");
			if ("exist" == data) {
				$.messager.alert('操作失败', '不存在该二维码标签，不能添加维保信息', 'error');
			} else if ("success" == data) {
				//	$.messager.alert("操作成功",'谢谢');    
			$.messager.show( {
				title : '提示信息',
				timeout : 1000,
				msg : '操作成功，谢谢。'
			});
			testDid="";
			grid.datagrid('reload');
			win.window('close');
		}  else if("failure2"== data){
			$.messager.alert('操作失败', '所选择的维保单位不属于标签所在的维保单位', 'error');
			}
		   else if("failure3"== data){
			$.messager.alert('操作失败', '所选择维保人员不属于标签所在维保单位的维保人员', 'error');
			}
			else {
			$.messager.alert('操作失败', '添加标签维保信息', 'error');
		}
	}
		});
	}

  var testDid;
  function testDid(){  
	  var deviceId="";
      deviceId =$('#deviceId').attr("value"); 
      if(deviceId !=""){  
          if(deviceId.length ==16){
        	  $.get("/tcweb/elevator/queryDid",{deviceId:deviceId},function (data, textStatus){
            	 if(data=="0") { //alert(data);
            		$.messager.alert('操作失败', '该设备已经关联了，不能重复添加', 'error'); 
            		$('#deviceId').focus();
            		return false;
            		}
         		else{
         			testDid=$('#deviceId').attr("value");
         			$.messager.alert('设备号查询', '该设备号可用', 'info'); 
             		}
        	  });
              }
          else{
        	 $.messager.alert('操作失败', '设备号不可用', 'error');
        	 $('#deviceId').focus();
        	 return false;
              }
	    }
      
	  }


  function addFun(){
	  opt =0;
  
	  }

  var oldRegistNumber;
  function editFun(registNumber){
      opt =1;
      oldRegistNumber =registNumber;
	  }


//startdate和enddate的格式为：yyyy-MM-dd hh:mm:ss  
//当date1在date2之前时，返回1;当date1在date2之后时，返回-1；相等时，返回0  
function datecompare(date1, date2){  
    var strdt1=date1.replace("-","/");  
    var strdt2=date2.replace("-","/");     
    
    var d1 = new Date(Date.parse(strdt1));  
    var d2 = new Date(Date.parse(strdt2));  
    if(d1<d2){  
        return 1;  
    } else if ( d1>d2 ){  
        return -1;  
    } else{   
        return 0;  
    }   
} 

function queryYwCompanyByReg(){ 
	var registNumber=$('#registNumberinfo').attr("value"); 
	if(registNumber.length==6){ 
	   $('#ywCompanyId').combobox('clear');  
       $('#ywCompanyId').combobox('reload','/tcweb/elevator/queryYwCompanyByReg?registNumber='+registNumber); 
		}
}

function shenhe(){
	 if(ywstatusValue==0){ //判断状态，如果是待审核状态才进行状态更新
    
     if(plng>0 && plat>0){
		   if(plng!=map_x || plat!=map_y){
			   $.messager.confirm('Confirm','你将通过审核修订电梯位置坐标 ?',function(r){
				   if (r){ 
	    jQuery.post('/tcweb/yw/shenheByid',
	    	 {'id':shenheid,'map_x':plng,'map_y':plat},
	    	 function(data){
	    		eval("data="+"'"+data+"'");  
	    		if("success"==data){
	    		//	$.messager.alert("操作成功",'谢谢');
	    		 $.messager.show({   
			 title:'提示信息',
			 timeout:1000,
			 msg:'操作成功，谢谢。' 
		 });  	
	    		plng=0.0;
	    		plat=0.0;
	    		$('#ywPoint').empty();
	    		shenhewin.window('close');
 	            grid.datagrid('reload');
	    		}
	    		else{
	    			$.messager.alert('操作失败','电梯位置坐标修订失败','error');
    	    		}
    	       });
				   }
			   });
		   }
		   }
       else{
    	   jQuery.post('/tcweb/yw/shenheByid2',
    		    	 {'id':shenheid},
    		    	 function(data){
    		    		eval("data="+"'"+data+"'");  
    		    		if("success"==data){
    		    		//	$.messager.alert("操作成功",'谢谢');
    		    		 $.messager.show({   
    				 title:'提示信息',
    				 timeout:1000,
    				 msg:'操作成功，谢谢。' 
    			 });  	
    		    		plng=0.0;
    		    		plat=0.0;
    		    		$('#ywPoint').empty();
    		    		shenhewin.window('close');
    	 	            grid.datagrid('reload');
    		    		}
    		    		else{
    		    			$.messager.alert('操作失败','审核失败','error');
    	    	    		}
    	    	       });

           }
		 	 
	 } 
	 else{  //无效和通过状态只查看图像
		 $('#ywPoint').empty();
		 shenhewin.window('close');
		 }
}

var plng=0.0;
var plat=0.0;
var map_x;
var map_y;
var map_x0;
var map_y0;
var map_x1;
var map_y1;
var map_x2;
var map_y2;


function ywPoint(){
	jQuery.post('/tcweb/yw/shenhePointByXcReg', {'id':shenheid},function(data){ 
		          data = eval(data);//POST方法必加，ajax方法自动处理了   
		          map_x=data.map_x; 
		          map_y=data.map_y;
		          map_x0=data.map_x0; 
		          map_y0=data.map_y0;
		          map_x1=data.map_x1; 
		          map_y1=data.map_y1;
		          map_x2=data.map_x2; 
		          map_y2=data.map_y2;
		          showYwPointMap();
			   }, 'json');
}

function showYwPointMap(){
	$('#ywPoint').append("<iframe src='mapinfo.jsp?map_x="+map_x+"&map_y="+map_y+"&map_x0="+map_x0+"&map_y0="+map_y0+"&map_x1="+map_x1+"&map_y1="+map_y1+"&map_x2="+map_x2+"&map_y2="+map_y2+"' width='100%' height='100%' ></iframe>");
    
}

function showMap(){
	var map = new BMap.Map("container");// 创建地图实例
	var point = new BMap.Point(map_x, map_y);
	map.centerAndZoom(point, 15); // 初始化地图，设置中心点坐标和地图级别   
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
<body class="easyui-layout" data-options="fit:true">
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">  
 <fieldset id="addDiv" style="margin-left:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
     <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%;"> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
   <% if("1".equals(cityName)){ %>
   <td nowrap>N<input id="registNumber" name="registNumber"  class="easyui-validatebox"></input></td>
    <% } else {%>
   <td nowrap><input id="registNumber" name="registNumber"  class="easyui-validatebox"></input></td>
 <% }%>
   <%if(role !=10 && role != 11){ %>
   <td nowrap align="right">行政区划：</td>
   <td> 
  <!--  <select id="areainfo"   name="areainfo" style="width:152px;" onchange="getCompanyListByArea()"> -->
  <!--  <select id="areainfo"   name="areainfo" style="width:152px;">
  <option value=""></option>
   <option value="锦江">锦江</option>
   <option value="青羊">青羊</option>
   <option value="金牛">金牛</option>
   <option value="武侯">武侯</option>
   <option value="成华">成华</option>
   <option value="高新">高新</option>
   <option value="龙泉驿">龙泉驿</option>
   <option value="青白江">青白江</option>
   <option value="新都">新都</option> 
   <option value="温江">温江</option> 
   <option value="金堂">金堂</option>
   <option value="双流">双流</option> 
   <option value="郫县">郫县</option> 
   <option value="大邑">大邑</option>
   <option value="蒲江">蒲江</option>
   <option value="新津">新津</option>
   <option value="都江堰">都江堰</option>
   <option value="彭州">彭州</option>
   <option value="邛崃">邛崃</option>
   <option value="崇州">崇州</option>
   <option value="简阳">简阳</option>
   <option value="天府新">天府新</option> 
</select> -->
 <input id="areainfo" name="areainfo" style="height:25px;"/>
</td>
  <% } %> 
   <td  nowrap align="right">开始时间：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime"  style="height:25px;"></input></td>
    <td align="right" nowrap>巡检人员：</td> 
   <td nowrap><input id="quserName" name="quserName"  class="easyui-validatebox"></input></td>
  
   </tr>
   <tr>
   <td align="right" nowrap>楼盘名称：</td> 
   <td nowrap><input id="buildingName" name="buildingName"  class="easyui-validatebox"></input></td>
   
  <td align="right">乡镇街道办：</td>
 <td><select id="qtownshipStreets"  class="easyui-combobox" name="qtownshipStreets"  style="height:25px;"></select></td>
  <td align="right" nowrap>结束时间：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime"  style="height:25px;"></input></td>
  <td>
  </td>
    <td>
  </td>
   <td>	
		<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>	
					
</td>
					
   </tr>
   
     </table>
  
  </fieldset>
</div> 
   <div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div>  
       <table id="xctt"></table>
   </div>  
        
    </div>  
</div> 
<div id="car-window3" title="详细信息" style="width:750px;height:550px;"> 
  <div style="padding:20px 20px 40px 80px;">   
  <form method="post">    
  <table>    
   <tr>      
   <td width="70">电梯编号：</td> 
   <% if("1".equals(cityName)){ %>     
   <td>N<input id="registNumberinfo" name="registNumber" onchange="queryYwCompanyByReg()"></input><span class="fontShow"><font color="red">*</font></span></td>  
     <% } else {%>
   <td><input id="registNumberinfo" name="registNumber" onchange="queryYwCompanyByReg()"></input><span class="fontShow"><font color="red">*</font></span></td>  
   
 <% }%>
    <td></td>
    <td></td>
    </tr>
    <tr>
    <td>种类：</td>      
   <td><select id="ywKind"  class="easyui-combobox" name="ywKind" style="width:152px;">
    <option value="0">维保</option>
     <option value="1">巡检</option> 
</select><span class="fontShow"><font color="red">*</font></span></td> 
    <td>类型：</td>    
 <td><select id="maintainTypecode"  class="easyui-combobox" name="maintainTypecode" style="width:152px;">
    <option value="半月保">半月保</option>
    <option value="季保">季保</option> 
    <option value="半年保">半年保</option>
    <option value="年保">年保</option> 
</select><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
   <tr> 
    <td>开始时间：</td>      
   <td><input id="startTime" name="startTime" class="easyui-datetimebox" style="width:152px;" showSeconds="false"></input><span class="fontShow"><font color="red">*</font></span></td> 
   
   <td>结束时间：</td>      
   <td> 
   <input id="endTime" name="endTime" class="easyui-datetimebox" style="width:152px;" showSeconds="false"></input><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
   <tr>
   <td>开始位置：</td>      
   <td><select id="sPosition"  class="easyui-combobox" name="sPosition" style="width:152px;">
    <option value="0">机房</option>
    <option value="1">轿厢</option>  
</select><span class="fontShow"><font color="red">*</font></span>
   </td>
    
    <td>结束位置：</td>      
   <td>
   <select id="ePosition"  class="easyui-combobox" name="ePosition" style="width:152px;">
    <option value="0">机房</option>
    <option value="1">轿厢</option>  
</select><span class="fontShow"><font color="red">*</font></span> 
   </td>
   </tr>
    <tr> 
    <td>维保单位:</td>
<td>  
<select id="ywCompanyId"  class="easyui-combobox" name="ywCompanyId" style="width:152px;"> 
</select>
<span class="fontShow"><font color="red">*</font></span></td>      
   <td>维保人员：</td>      
   <td><select id="userId"  class="easyui-combobox" name="userId" style="width:152px;"> 
</select><span class="fontShow"><font color="red">*</font></span></td>   
   </tr>
   <tr>
   <td>备注:</td>
   <td colspan="3"><textarea cols="50" rows="6" id="remark" name="remark" ></textarea></td>
   </tr> 
   </table>
   <table width=70%>
    <tr>
    <td align="center">
      <a href="javascript:void(0)" onclick="saveCar3()" id="btn-save" icon="icon-save">保存</a>  
     <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">取消</a> 
    </td>
    </tr> 
   </table>   
   </form> 
    </div> 
     
   </div>
  
  
   <!--  
    <div style="text-align:center;padding:5px;">    
     <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">取消</a>  </div> 
    </div> -->
 <div id="shenhe-window" title="审核详细信息" style="width:780px;height:550px;">
 <div style="both:clear">
 <div id="ywImg"  style="width:350px;float:left;border:1px solid #000;"></div>
 <div id="ywPoint" style="height:482px;float:right;border:1px solid #00f;">
</div>
 </div> 
 <div style="both:clear"></div>
 <div align="center" id="shenheDiv" style="both:clear"><a href="javascript:void(0)" onclick="shenhe()" id="btn-ok" icon="icon-ok">审核</a> </div>
</div>
</body>
</html>