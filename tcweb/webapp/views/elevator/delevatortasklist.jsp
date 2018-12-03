<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<!--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"> -->
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
int isliulan = 0;
int userId=0;
if(userinfo!=null){
 role = userinfo.getRole(); 
 userId =userinfo.getId();
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
	    userId = user.getId();
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

	 function strDateTime(str)
	 {
	 var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	 if(r==null)
	 return false; 
	 var d= new Date(r[1], r[3]-1, r[4]); 
	 return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
	 }




var ywName="";
// var areaName="";
var opt =0; //0:增加 ；1：编辑
$(function(){

	$.ajaxSetup ({
	    cache: false 
	});


	comb2 =$('#pastePersonID').combobox({
		url:'/tcweb/elevator/getYwAllCompanyList',
	    valueField:'id',
	    textField:'companyName'
	});

	$('#pastePersonID').combobox({
    	filter: function(q, row){
    	//    ywName = q;
    		var opts = $(this).combobox('options');
    		return row[opts.textField].indexOf(q) >= 0;
    	}
    });

    /*
	 comb7=$('#ywCompanyIdinfo').combobox({
			url:'/tcweb/elevator/getYwCompanyList',
		    valueField:'id',
		    textField:'companyName'
		});

	    $('#ywCompanyIdinfo').combobox({
	    	filter: function(q, row){
	    	    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });
     */

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
	
	$('#btn-save,#btn-cancel').linkbutton(); 
	win = $('#task-window1').window({  closed:true,draggable:false,modal:true }); 
	form = win.find('form');

	win8d = $('#car-window8d').window({  closed:true,draggable:false,modal:true }); 
	anzhuangwin = $('#anzhuang-window').window({  closed:true,draggable:false,modal:true });
	grid=$('#dtt').datagrid({
	    title:'电梯列表',
	    fitColumns:true,
 	    striped:true,
	    pageSize:15,
	    pageList:[15,20,25,30,35,40,250,500,1000],
	    url:'/tcweb/elevator/ddelevatortasklist',
	    queryParams:{},
	    idField:'id',
	    columns:[[
	        {field:'id',checkbox:true},
	        {field:'shibieCode',align:'left',halign:'center',title:'识别码',width:100},
	        {field:'registNumber',align:'left',halign:'center',title:'电梯编号',width:80,styler:function(value,row,index){if (row.ruKuValid==1){return 'background-color:#99cc99;';}},formatter: function(value,rec,index) {
	        	 <% if("1".equals(cityName)){ %>
	        	 return "N"+value;
	        	 <% } else {%>
	        	 return value;
	        	  <% }%>
		         }},
	        {field:'buildingName',align:'left',halign:'center',title:'楼盘名称',width:200},
	        {field:'address',align:'left',halign:'center',title:'地址',width:200},
	        {field:'useNumber',align:'left',halign:'center',title:'单位内部编号'},
	        {field:'building',align:'left',halign:'center',title:'栋',width:30},
	        {field:'unit',align:'left',halign:'center',title:'单元',width:30},
	        {field:'registCode',align:'left',halign:'center',title:'登记编号',width:200},
	        {field:'jdbCompanyName',align:'left',halign:'center',title:'街道办',width:150},
	        {field:'wgCompanyName',align:'left',halign:'center',title:'使用单位',width:150},
	        {field:'ywCompanyName',align:'left',halign:'center',title:'维保单位',width:110},
	        {field:'zzCompanyName',align:'left',halign:'center',title:'制造单位',width:70},
	        {field:'azCompanyName',align:'left',halign:'center',title:'安装单位',width:70},
	        {field:'jyCompanyName',align:'left',halign:'center',title:'检验单位',width:55},
	        {field:'inspector',align:'left',halign:'center',title:'检验人员',width:55},
	        {field:'inspectDate',align:'center',title:'检验日期',width:55},
	        {field:'subTime',align:'center',title:'最近一次维保日期',width:135,formatter: function(value,rec,index) {
                if(value!=null)
                    return value.substring(0,16);
		         }},
	        {field:'zjCompanyName',align:'left',halign:'center',title:'质监局'},
	        {field:'deviceId',align:'left',halign:'center',title:'黑匣子设备'}
	    ]],
	    onLoadSuccess:function(data){$('#dtt').datagrid('clearSelections')},
	    singleSelect: false,
	    selectOnCheck: true,
	    checkOnSelect: true,
	    pagination:true,
	    <% if(isliulan == 0){%>
	    toolbar:[{
	        text:'分配',
	        iconCls:'icon-add',
	        handler:function(){
		    var row = grid.datagrid('getSelected'); 
		    if (row){       
	    	win.window('open');  
	    //	form.form('clear');
	    //  addFun();	
		    }
		    else{
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。' 
	    		 });   
		     }	 
	    }
	    }
	    ]
	    <% } %> 
	});	
	$('#dtt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40,250,500,1000]}); 
      	  
}
);

function tasklist(){
	var checkedItems = $('#dtt').datagrid('getChecked');
	var names="";
	var pastePersonID =0;
	var arrangePersonID =<%=userId%>;
	var arrageType =1;
	$.each(checkedItems, function(index, item){
		names=names+item.id+",";
	//	alert(item.id);
	}); 
	if(names!="")
		names=names.substring(0,names.length-1); 
	
	arrageType = $('input:radio:checked').val();
	pastePersonID =$('#pastePersonID').combobox('getValue');  
 //	alert("pastePersonID"+pastePersonID);
 //	console.log(names.join(","));
     
	 jQuery.post('/tcweb/elevator/ddtasklistarrange',
	    	 {'arrangePersonID':arrangePersonID,'arrageType':arrageType,'pastePersonID':pastePersonID,'names':names},
	    	 function(data){
	    		eval("data="+"'"+data+"'");  
	    		if("success"==data){
	    		//	$.messager.alert("操作成功",'谢谢');
	    		 $.messager.show({   
			 title:'提示信息',
			 timeout:1000,
			 msg:'操作成功，谢谢。' 
		 });  	
	    		
	    		win.window('close');
 	            grid.datagrid('reload');
	    		}
	    		else{
	    			$.messager.alert('操作失败','操作失败','error');
    	    		}
    	       }); 
	
}

function arrageTask(){
	tasklist();
}

function openPicInfo(registNumber,subTime){
	anzhuangwin.window('open');
	var ywimg="";
	var ywimg2="";
	ywing ='<img height="480" align="center" style="width:100%" src="<%=request.getContextPath()%>'+'/servlet/ywImage2.jpg?registNumber='+registNumber+'&subTime='+subTime+'&index='+1+'"/>';
	ywing2 ='<img height="480" align="center" style="width:100%" src="<%=request.getContextPath()%>'+'/servlet/ywImage2.jpg?registNumber='+registNumber+'&subTime='+subTime+'&index='+2+'"/>';
	document.all.ywImg.innerHTML=ywing;
	document.all.ywImg2.innerHTML=ywing2;
	$('#btn-no2').linkbutton();
}

function closepic(){
	anzhuangwin.window('close'); 
}

function clearQuery(){
	$('#addressinfo').attr("value","");
	$('#buildingName').attr("value","");
	$('#building').attr("value","");
	$('#unit').attr("value","");
	/*
	$('#ywCompanyIdinfo').combobox('clear');
	$('#ywCompanyIdinfo').combobox({
		url:'/tcweb/elevator/getYwCompanyList',
	    valueField:'id',
	    textField:'companyName'
	});
	*/
	$('#ywCompanyIdinfo').attr("value","");
	$('#ywCompanyIdinfo2').attr("value","");
	// $('#ywCompanyIdinfo').combobox('clear');
	// $('#areainfo option:first').attr('selected','selected');
}

function query(){  
	var address=$('#addressinfo').attr("value");
	var buildingName=$('#buildingName').attr("value");
	var building=$('#building').attr("value");
	var unit=$('#unit').attr("value");
//	var ywCompanyId=$('#ywCompanyIdinfo').combobox('getValue'); 
    var ywCompanyId=$('#ywCompanyIdinfo2').attr("value");
	if (!ywCompanyId){
    	ywCompanyId =0;
    	}
    grid.datagrid("options").url='/tcweb/elevator/ddtaskquery';
    grid.datagrid("options").queryParams={'address':address,'buildingName':buildingName,'building':building,'unit':unit,'ywCompanyId':ywCompanyId};  
    $('#dtt').datagrid('reload');
	}
	

	
function openCarinfoDetail(registNumber){ 
	 win8d.window('open'); 
    
     gridmap=$('#ttmapd').datagrid({
	    title:'',
	    pageSize:5,
	    pageList:[5,10,15,20,25,30],
	    url:'/tcweb/elevator/elechangelist',
	    queryParams:{'registNumber':registNumber},
	    columns:[[
	        {field:'changeItem',title:'变更项目',formatter: function(value,rec,index) { 
		        var str = Array();
		        var changeItemStr="";
		        str=value.split(";");
		        for(var i=0;i<str.length;i++){
		        	changeItemStr=changeItemStr+str[i]+"<br>";
			        }
		   //     alert(changeItemStr);
		        return changeItemStr;
  	            }},
	        {field:'operator',title:'操作人员'},
	        {field:'operatorTime',title:'操作时间',width:200,formatter: function(value,rec,index) {
                if(value)
                    return value.substring(0,16);
                else
                    return value;
		         }},
		     {field:'changeWay',title:'变更方式'},
		     {field:'handleCompany',title:'承办单位'},
		     {field:'handleCompanyCode',title:'承办单位代码'} 
         ]],
         nowrap:true,
	    pagination:true
	
});
     $('#ttmapd').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[5,10,15,20,25,30]});  


     gridmap2=$('#ttmap2').datagrid({
 	    title:'',
 	    pageSize:5,
 	    pageList:[5,10,15,20,25,30],
 	    url:'/tcweb/yw/ywlistByreg',
 	    queryParams:{'registNumber':registNumber},
 	    columns:[[
 		        {field:'registNumber',title:'电梯编号',width:60},
 		        {field:'address',title:'地址',width:160},
 		        {field:'ywKind',title:'种类',width:50},
 		        {field:'maintainTypecode',title:'类型',width:50},
 		        {field:'startTime',title:'开始时间',width:120},
 		        {field:'endTime',title:'结束时间',width:120},
 		        {field:'dateSpan',title:'时长（分钟）',width:70},
 		        {field:'sPosition',title:'开始位置',width:55},
 		        {field:'ePosition',title:'结束位置',width:55},
 		        {field:'userName',title:'维保人员',width:55},
 		        {field:'companyName',title:'维保单位',width:150},
 		        {field:'subTime',title:'上传时间',formatter: function(value,rec,index) {
 	                 if(value)
 	                     return value.substring(0,16);
 	                 else
 	                     return value;
 			         }}
 		    ]],
          nowrap:true,
 	    pagination:true
 	
 });

     $('#ttmap2').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[5,10,15,20,25,30]});  
}
function closeWindow(){ 
	win.window('close');
	}

function showWinDetail(){
	$("form input").css({border:'0px solid' });

	$('input').attr("disabled","disabled");
	$('#eleType').combobox('disable');
	$('#inoutDoor').combobox('disable');
	$('#wgCompanyId').combobox('disable');
	$('#zzCompanyId').combobox('disable');
	$('#jyCompanyId').combobox('disable');
	$('#azCompanyId').combobox('disable');
	$('#ywCompanyId').combobox('disable');
	$('#zjCompanyId').combobox('disable');
	$('#townshipStreets').combobox('disable');

	$('#manufactDate').datebox('disable');
	$('#nextInspectDate').datebox('disable');
	$('#inspectDate').datebox('disable');
	
	$(".fontShow").hide();
	$('#btn-save').hide();
	$('#btn-cancel').show();
	
	$('#imgpic').hide();
		
}

function colseWinDetail(){
	$("form input").css({border:'1px solid' });

	$('input').attr("disabled","");
	$('#eleType').combobox('enable');
	$('#inoutDoor').combobox('enable');
	$('#wgCompanyId').combobox('enable');
	$('#zzCompanyId').combobox('enable');
	$('#jyCompanyId').combobox('enable');
	$('#azCompanyId').combobox('enable');
	$('#ywCompanyId').combobox('enable');
	$('#zjCompanyId').combobox('enable');
	$('#townshipStreets').combobox('enable');

	$('#manufactDate').datebox('enable');
	$('#nextInspectDate').datebox('enable');
	$('#inspectDate').datebox('enable');
	
	$(".fontShow").show();	
	$('#btn-save,#btn-cancel').show();  

	$('#imgpic').show();
	
}

function saveCar2d(){ 
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){
		var registNumber=$('#registNumberinfo').attr("value"); 
		var address= $('#address').attr("value"); 
		var area=$('#area').combobox('getValue');
	
		/*
		var registCode =$('#registCode').attr("value");
		var useNumber =$('#useNumber').attr("value");
		var safetyManDepart =$('#safetyManDepart').attr("value");
		var safetyManPerson =$('#safetyManPerson').attr("value");
		var safetyManPersonTel =$('#safetyManPersonTel').attr("value");
		var name =$('#name').attr("value");
		var eleMode =$('#eleMode').attr("value");
		var eleStop =$('#eleStop').attr("value");
		var speed =$('#speed').attr("value");
		var eleLoad =$('#eleLoad').attr("value");

        var eleType =$('#eleType').combobox('getValue');
        
       
        var wgCompanyId=$('#wgCompanyId').combobox('getValue');  
        var ywCompanyId=$('#ywCompanyId').combobox('getValue'); 
        var zzCompanyId=$('#zzCompanyId').combobox('getValue'); 
        var azCompanyId=$('#azCompanyId').combobox('getValue'); 
        var jyCompanyId=$('#jyCompanyId').combobox('getValue'); 
        var zjCompanyId=$('#zjCompanyId').combobox('getValue');
        var townshipStreets=$('#townshipStreets').combobox('getValue');
       
      
    	
        
        var townshipStreets=$('#townshipStreets').combobox('getValue');  
        var inoutDoor=$('#inoutDoor').combobox('getValue');
       

        var eleheight =$('#eleheight').attr("value");
        var elewidth  =$('#elewidth').attr("value");
        var inspector =$('#inspector').attr("value");
       */
       /*
        if(opt==1){
        	if(oldRegistNumber != registNumber){
            	$('#registNumberinfo').attr("value",oldRegistNumber);
        		$.messager.alert('操作失败', '电梯编码不能修改', 'error');
        		return false;
            	}
            }
		*/
        if($('#deviceId').attr("value")!=""){
               if(opt==0){
			   if ( testDid != $('#deviceId').attr("value")){
			//		alert("请先测试本设备编号是否正确");
					$.messager.alert('操作失败', '设备号不可用', 'error');
					return false;
				}
               }
               if(opt==1){
                   if(oldDeviceId !=$('#deviceId').attr("value")){
                	   if ( testDid != $('#deviceId').attr("value")){
               			//		alert("请先测试本设备编号是否正确");
               					$.messager.alert('操作失败', '设备号不可用', 'error');
               					return false;
               				}

                       }
                   }
			   }
			
           /*
			if (!registNumber) {
				$.messager.alert('操作失败', '电梯编码不能为空', 'error');
				return false;
			}
			*/
			if (!address) {
				$.messager.alert('操作失败', '地址不能为空', 'error');
				return false;
			}
          /*
			if (!area) {
				$.messager.alert('操作失败', '所属区域不能为空', 'error');
				return false;
			}
          */
			
         /*
			if (!townshipStreets) {
				$.messager.alert('操作失败', '街道办不能为空', 'error');
				return false;
			}
			

			if (!eleType) {
				$.messager.alert('操作失败', '类别不能为空', 'error');
				return false;
			}

			if (!inoutDoor) {
				$.messager.alert('操作失败', '室内外不能为空', 'error');
				return false;
			}

			if (!zzCompanyId) {
				$.messager.alert('操作失败', '制造单位不能为空', 'error');
				return false;
			}

			if (!wgCompanyId) {
				$.messager.alert('操作失败', '使用单位不能为空', 'error');
				return false;
			}

			if (!jyCompanyId) {
				$.messager.alert('操作失败', '检验单位不能为空', 'error');
				return false;
			}
           */
			 var nextInspectDate = $("#nextInspectDate").attr("value");
				if (nextInspectDate != "") {
					if (!strDateTime(nextInspectDate)) {
						$.messager.alert('操作失败', '下次检验日期格式形如：2013-01-04', 'error');
						return false;
					}
				}

				 var completeAcceptanceDate = $("#completeAcceptanceDate").attr("value");
					if (completeAcceptanceDate != "") {
						if (!strDateTime(completeAcceptanceDate)) {
							$.messager.alert('操作失败', '竣工验收日期格式形如：2013-01-04', 'error');
							return false;
						}
					}
              /*
				if (!azCompanyId) {
					$.messager.alert('操作失败', '安装单位不能为空', 'error');
					return false;
				}

				if (!ywCompanyId) {
					$.messager.alert('操作失败', '维保单位不能为空', 'error');
					return false;
				}

				if (!zjCompanyId) {
					$.messager.alert('操作失败', '质监单位不能为空', 'error');
					return false;
				}
               */
			  

			return true;
			//	return false;
		},

		success : function(data) {
			eval("data=" + "'" + data + "'");
			if ("exist" == data) {
				$.messager.alert('操作失败', '已经存在该二维码标签，不能重复添加', 'error');
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
		} else {
			$.messager.alert('操作失败', '添加二维码标签', 'error');
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
  var oldDeviceId;
  function editFun(registNumber,deviceId){
      opt =1;
      oldRegistNumber =registNumber;
      oldDeviceId =deviceId;
	  }

  function testDid2(){
	  var longitude=$('#map_X').attr("value");
	  if(""==longitude)
		  longitude=0; 
	  var latitude=$('#map_Y').attr("value");
	  if(""==longitude)
		  latitude=0;
	//  $('#p').append("<iframe src='../../test3.jsp?longitude=123&latitude=234' width='100%' height='100%' ></iframe>");
	
	 $('#p').append("<iframe src='../../test3.jsp?longitude="+longitude+"&latitude="+latitude+"' width='100%' height='100%' ></iframe>");
	/*  $('#p').panel({   
       href:'../../test3.jsp',   
       onLoad:function(){   
       alert('loaded successfully');   
      }   
      });  
   */
      $('#p').window({
          onClose:function(){$('#p').empty(); 
      //   alert("plng--"+plng);
      //    $('#coordinates').attr("value",plng);
          $('#map_X').attr("value",plng);
          $('#map_Y').attr("value",plat);
          }
      });
   
      $('#p').window('open');
	  }

  var plng;
  var plat;

  function getCompanyListByArea(){  
	  $('#ywCompanyIdinfo').combobox({
          url: '/tcweb/elevator/getCompanyListByArea?companyArea='+encodeURI($('#areainfo option:selected').val()),
          valueField: 'id',
          textField: 'companyName'
      }).combobox('clear');
	  }

  function testDid3(){
	  var registNumber =$('#registNumberinfo').attr("value"); 
	  $.getJSON("/tcweb/elevator/queryYwinfoByReg",{registNumber:registNumber},function (data, textStatus){
		  var tt="";
		  $.each(data, function(k, v) {
	            tt += k + "：" + v + "<br>";
	        })
	        $.messager.alert('运维人员信息', tt, 'info');
 	  });

	  }

  function showItem(v){
	  if(v==1){
		  $('#pastePersonID').combobox({
	          url: '/tcweb/elevator/getYwAllCompanyList',
	          valueField: 'id',
	          textField: 'companyName'
	      }).combobox('clear');
		  }
	  else{
		  $('#pastePersonID').combobox({
	          url: '/tcweb/elevator/getpastePersonList',
	          valueField: 'id',
	          textField: 'userName'
	      }).combobox('clear');
		  }
		  
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
/*  background: #F9F0DA; */
  padding-left: 2px;
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
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">  
 <fieldset id="addDiv"  style="margin:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
     <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%"> 
     <tr>      
    <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="addressinfo" name="addressinfo" class="form_input" ></input></td>
    <td align="right" nowrap>楼盘名称：</td> 
   <td nowrap><input id="buildingName" name="buildingName"  cclass="form_input"></input></td>
   <td nowrap align="right">维保单位：</td>
   <!--   <select id="ywCompanyIdinfo"  class="easyui-combobox" name="ywCompanyIdinfo" style="width:152px;"></select> -->
  <td> <input id="ywCompanyIdinfo"  style="height:25px;" placeholder="输入至少两个关键字从下拉列表中选择" >
  <input type ="hidden" id="ywCompanyIdinfo2">
 </td>
  <!--  <td nowrap>状态：
   <select id="recordSate"   name="recordSate" style="width:100px;">
   <option value=""></option>
   <option value="0">未分配</option>
   <option value="1">已分配</option>
   <option value="2">已完成</option>
</select>
</td> -->
   </tr>  
  <tr>  
  <td align="right" nowrap>栋：</td> 
  <td nowrap><input id="building" name="building" class="form_input"></input></td>
 
  <td align="right" nowrap>单元：</td> 
   <td nowrap><input id="unit" name="unit"  class="form_input"></input></td>
 
  <td></td>
  <td></td>
 <td>
		<!-- 	<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a>   -->	
			<a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
            <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>					
</td>
					
   </tr>
   
     </table>
  
  </fieldset>
</div> 
   <div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div style="margin-top:1px;">  
       <table id="dtt"></table>
   </div>  
        
    </div>  
</div> 
<div id="task-window1" title="任务分配" style="width:350px;height:350px;">
 <div style="padding:20px 20px 40px 80px;"> 
 <table>    
   <tr>
   <td nowrap>类型：
   <input type="radio" name="arrageType" value="1"  checked onclick="showItem(1)"/>运维公司&nbsp;&nbsp;&nbsp;<input type="radio" name="arrageType" value="2"  onclick="showItem(2)" />个人</td>
   </tr>
 
   <tr><td  nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <select id="pastePersonID"  class="easyui-combobox" name="pastePersonID" style="width:152px;"> 
</select>
   </td>
   </tr>
     <tr>
   <td><hr>
   </td>
   </tr>
   <tr> <td align="center">
    <a href="javascript:void(0)" onclick="arrageTask()" id="btn-save" icon="icon-save">保存</a>
    </td>  
   </tr>
   </table>
 </div>
</div>
  
   <!--  
    <div style="text-align:center;padding:5px;">    
     <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">取消</a>  </div> 
    </div> -->
    <div id="p" class="easyui-window" title="地图信息" closed="true" style="width:600px;height:480px;padding:5px;">   
    </div>  
    
   <div id="car-window8d" title="变更详细信息" style="width:780px;height:550px;"> 
   <div style="padding:20px 10px 40px 10px;">
   
   <div id="bb" class="easyui-accordion">
   <div style="margin-top:1px;" title="变更信息列表">  
       <table id="ttmapd"></table>
   </div> 
   </div>
   
   
   <div id="aa" class="easyui-accordion" style="border-top:0px;">
   <div title="维保信息">  
        <table id="ttmap2"></table>
    </div>    
   </div>  
    
   </div>
   </div>   
   
 <div id="anzhuang-window" title="安装图片详细信息" style="width:780px;height:550px;">
 <div style="both:clear">
 <div id="ywImg"  style="overflow:hidden;width:49%;float:left;border:1px solid #000;"></div>
 <div id="ywImg2" style="overflow:hidden;width:50%;height:480px;float:right;border:1px solid #00f;"></div>
 </div> 
 <div style="both:clear"></div>
 <div align="center" id="shenheDiv" style="both:clear"><a href="javascript:void(0)" onclick="closepic()" id="btn-no2" icon="icon-no">关闭</a></div>
</div>
</body>
</html>