<%@ page import="com.zytx.models.UserInfo,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
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
int userId=0;
if(userinfo!=null){
 role = userinfo.getRole(); 
 userId =userinfo.getId();
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
	    userId = user.getId();
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
	 
	 $(function(){

			$.ajaxSetup ({
			    cache: false 
			});

			 var inputWidth = $('#registNumber').width();   
				$("#areainfo").css("width", inputWidth);
			   
			    $('#qstartTime').datebox({     
				       width:inputWidth 
				   }); 

				 $('#qendTime').datebox({     
				       width:inputWidth 
				   }); 

			$('#imgpic').bind("click",testDid);
			$('#imgpic2').bind("click",testDid2);
			$('#imgpic3').bind("click",testDid3);	

			 $('#areainfo').combobox({   
			        url:'/tcweb/elevator/areaInfoList',   
			        valueField:'area',   
			        textField:'area'  
			    });  

			 var ywurl = encodeURI(encodeURI('/tcweb/elevator/getAutoYwCompanyList')); 
			 $("#ywCompanyIdinfo").autocomplete(  
					    ywurl,  
			            {  
			            scroll: true,  
			                matchContains: true,  
			                width: 188,  
			                minChars: 2,
			                max:50,
			                scrollHeight:200,  
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
		              
			anzhuangwin = $('#anzhuang-window').window({  closed:true,draggable:false,modal:true });
			grid=$('#rukudtt').datagrid({
			    title:'入库电梯列表',
			    fitColumns:true,
		 	    striped:true,
			    pageSize:5,
			    pageList:[5,10,15,20,25,30,35,40,250],
			    url:'/tcweb/elevator/rukuddelevatorlist',
			    queryParams:{},
			    columns:[[
			        {field:'registNumber',align:'left',halign:'center',title:'电梯编号',width:80,formatter: function(value,rec,index) {
			        	 <% if("1".equals(cityName)){ %>
			        	 return "N"+value;
			        	 <% } else {%>
                         return value;
			        	 <% }%>
				        }},
				    {field:'buildingName',align:'left',halign:'center',title:'楼盘名称',width:150},
			        {field:'address',align:'left',halign:'center',title:'地址',width:200},
			        {field:'useNumber',align:'center',halign:'center',title:'单位内部编号'},
			        {field:'building',align:'center',halign:'center',title:'栋',width:40},
			        {field:'unit',align:'center',halign:'center',title:'单元',width:50},
			        {field:'recordSate',align:'center',title:'状态',width:60,formatter: function(value,rec,index) {
		                if(value==0)
		                    return "已创建";
		                if(value==1)
		                	 return "已经分配，未粘贴";
		                if(value==2)
		               	 return "已粘贴";
		               	if(value==3)
			               return "已入库";
				         }},
				    {field:'mobileUploadbeizhu',align:'left',halign:'center',title:'备注',width:200,styler:function(value,row,index){if(value){return 'background-color:#c8d9f5;';}}},
			//	    {field:'arrangePersonName',title:'任务分配人',width:80},
			/*	    {field:'arrangeTime2',title:'任务分配时间',width:140,formatter: function(value,rec,index) {
		                if(value!=null)
		                    return value.substring(0,16);
				         }}, */
			//	    {field:'pastePersonName',title:'粘贴人',width:100},
				    {field:'pasteTime',align:'center',halign:'center',title:'粘贴时间',width:100}, 
			        {field:'registCode',align:'left',halign:'center',title:'登记编号',width:150},
			        {field:'jdbCompanyName',align:'left',halign:'center',title:'街道办',width:150},
			        {field:'wgCompanyName',align:'left',halign:'center',title:'使用单位',width:150},
			        {field:'ywCompanyName',align:'left',halign:'center',title:'维保单位',width:110},
			        {field:'zzCompanyName',align:'left',halign:'center',title:'制造单位',width:70},
			        {field:'azCompanyName',align:'left',halign:'center',title:'安装单位',width:70},
			        {field:'jyCompanyName',align:'left',halign:'center',title:'检验单位',width:55},
			        {field:'inspector',align:'center',halign:'center',title:'检验人员',width:55},
			        {field:'inspectDate',align:'center',halign:'center',title:'检验日期',width:55},
			        {field:'zjCompanyName',align:'left',halign:'center',title:'质监局'}
			    ]],
			    nowrap:true,
			    pagination:true,
			    singleSelect:true
			    
			});	
			$('#rukudtt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[5,10,15,20,25,30,35,40,250]});     	  
		}
	 );


	 function clearQuery(){
	 	$('#registNumber').attr("value","");
	 	$('#addressinfo').attr("value","");
	 
	 	$('#buildingNameInfo').attr("value","");
	 	$('#registCodeinfo').attr("value","");
	 	$('#ywCompanyIdinfo').attr("value","");
		$('#ywCompanyIdinfo2').attr("value","");
		/*
	 	$('#ywCompanyIdinfo').combobox('clear');
	 	$('#ywCompanyIdinfo').combobox({
	 		url:'/tcweb/elevator/getYwCompanyList',
	 	    valueField:'id',
	 	    textField:'companyName'
	 	});
	 	*/
	 	$('#areainfo').combobox('clear'); 
	// 	$('#areainfo option:first').attr('selected','selected');
	 	$("#qstartTime").datebox("setValue","");  
		$("#qendTime").datebox("setValue","");  
	 }

	 

	 function query(){  
	 	var registNumber=$('#registNumber').attr("value");
	 	var address=$('#addressinfo').attr("value");
	 	var buildingName=$('#buildingNameInfo').attr("value");
	//    var ywCompanyId=$('#ywCompanyIdinfo').combobox('getValue'); 
	    var ywCompanyId=$('#ywCompanyIdinfo2').attr("value"); 
	//    var area=$('#areainfo option:selected').val();
	    var area =$('#areainfo').combobox('getValue'); 
	 
	     var registCode =$('#registCodeinfo').attr("value"); 
	     if (!ywCompanyId){
	     	ywCompanyId =0;
	     	}
	    var qstartTime=$('#qstartTime').datebox("getValue");  
	 	var qendTime=$('#qendTime').datebox("getValue"); 
	 
	    /*
	 	if(""==registNumber && ywCompanyId==0){
	 	   if(""==ywName && ""==area){	
	 		   if(""==address && ""==buildingName){
	 			   if(""==registCode){	
	 	            grid.datagrid("options").url='/tcweb/elevator/rukuddelevatorlist';
	 			   }
	 			   else{
	 			   grid.datagrid("options").url='/tcweb/elevator/rukuddquery';
	 			   grid.datagrid("options").queryParams={'registCode':registCode};  
	 			   }
	 			   
	 	       }
	 		   else{
	 			   grid.datagrid("options").url='/tcweb/elevator/rukuddquery';
	 			   grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'buildingName':buildingName,'registCode':registCode};  
	 			   }
	 	   }
	 	   else{
	 		   if(""==address){
	 		   grid.datagrid("options").url='/tcweb/elevator/rukuddquery';
	 		   grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'registCode':registCode};
	 		   }
	 		   else{
	 			grid.datagrid("options").url='/tcweb/elevator/rukuddquery';
	 			grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'buildingName':buildingName,'registCode':registCode};
	 			   }
	 	  }
	 	}
	    else{    
	      grid.datagrid("options").url='/tcweb/elevator/rukuddquery';
	      grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'buildingName':buildingName,'registCode':registCode};
	    }
	    */
	    grid.datagrid("options").url='/tcweb/elevator/rukuddquery';
	    grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'buildingName':buildingName,'registCode':registCode,'qstartTime':qstartTime,'qendTime':qendTime};
	   
	    $('#rukudtt').datagrid('reload');
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

	  function showWinDetail(registNumber,subTime){
			$("form input").css({border:'0px solid' });

			$('input').attr("disabled","disabled");
			
			$('#registNumber').attr("disabled","");
			$('#addressinfo').attr("disabled","");
			$('#buildingNameInfo').attr("disabled","");
			$('#registCodeinfo').attr("disabled","");
			$('#ywCompanyIdinfo').attr("disabled","");
			$('#ywCompanyIdinfo2').attr("disabled","");
			
			
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
			$('#btn-cancel').show();

			$('#imgpic').hide();

			//展示上传图片
			
			 $('#img4').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+registNumber+"&subTime="+subTime+"&index="+1);
			 $('#img5').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+registNumber+"&subTime="+subTime+"&index="+2);	
			
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
			$('#btn-cancel').show();  
			

			$('#imgpic').show();
			
		}

	  function closeWindow(){ 
			win.window('close');
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
 <fieldset id="addDiv" style="margin:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
     <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%"> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
   <% if("1".equals(cityName)){ %>
   <td nowrap>N<input id="registNumber" name="registNumber"  class="easyui-validatebox"></input></td>
    <% } else {%>
   <td nowrap><input id="registNumber" name="registNumber"  class="easyui-validatebox"></input></td>
    <% }%>
     <td nowrap align="right">所在区域：</td> 
    <td><input id="areainfo" name="areainfo" style="height:25px;"/></td>
   <td align="right" nowrap>楼盘：</td>
   <td nowrap><input id="buildingNameInfo" name="buildingName"  class="easyui-validatebox"></input></td>
     
   </tr>  
  <tr>  
  <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="addressinfo" name="addressinfo"  class="easyui-validatebox"></input></td>
 <td align="right" nowrap>开始时间：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" style="height:25px;"></input></td>
 
 <td nowrap align="right">登记编号：</td>
 <td nowrap><input id="registCodeinfo" name="registCodeinfo" style="height:25px;" class="easyui-validatebox"></input></td>
 
					
   </tr>
   <tr>
   <td nowrap align="right">维保单位：</td>
   <td> 
   <input id="ywCompanyIdinfo" style="height:25px;" placeholder="输入至少两个关键字从下拉列表中选择">
   <input type ="hidden" id="ywCompanyIdinfo2">
 </td>
    
   <td align="right" nowrap>结束时间：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime" style="height:25px;"></input></td> 
   <td></td>
    <td></td>
	<td> <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-search fa-lg" onclick="query()" style="width:100px;color:#3399FF;">查询</a>
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-cut fa-lg" onclick="clearQuery()" style="width:100px;">清空</a>
     </td>
   </tr>
     </table>
  
  </fieldset>
</div> 
   <div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div style="margin-top:1px;">  
       <table id="rukudtt"></table>
   </div>  
        
    </div>  
</div>

   
 <div id="anzhuang-window" title="安装图片详细信息" style="width:780px;height:500px;overflow-x:auto;overflow-y:hidden">
 <div style="width:1300px;">
 <div id="ywImg" style="float:left"><img src="" id="img1" style="width:400px;height:430px"></div>
 <div id="ywImg2" style="float:left"><img src="" id="img2" style="width:400px;height:430px"></div>
 <div id="ywImg3" style="float:left"><img src="" id="img3" style="width:400px;height:430px"></div>
 </div> 
 <div align="center" id="shenheDiv" style="both:clear"><a href="javascript:void(0)" onclick="closepic()" id="btn-no2" icon="icon-no">关闭</a></div>
</div>
</body>
</html>