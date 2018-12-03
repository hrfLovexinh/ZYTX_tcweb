<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO,com.zytx.models.UserRinghtInfo,com.zytx.init.GlobalFunction" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.jfree.chart.servlet.ServletUtilities"%>
<%@ page import="org.jfree.data.general.DefaultPieDataset,org.jfree.chart.title.TextTitle,java.awt.Font,org.jfree.chart.plot.PiePlot,org.jfree.chart.JFreeChart,org.jfree.chart.ChartFactory,org.jfree.chart.ChartFrame" %>
<%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全服务平台</title>
<!--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"> -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.min.css">
<!-- <script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script>  -->
 <style>
    .datagrid-header-row td{background-color:rgb(226,237,255);color:#0E2D5F;}
  </style>
<% 
String ywshenhetuisongServer =GlobalFunction.ywshenhetuisongServer;
String cityName = GlobalFunction.cityName;
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int ispcsuper = 0;
int isliulan = 0;
int isyanshi = 0;
int  role = 0; 
int  userId=0;
if(userinfo!=null){
	 role = userinfo.getRole(); 
	 userId=userinfo.getId();
	 ispcsuper =userinfo.getIspcsuper();
	 isliulan = userinfo.getIsliulan();
	 isyanshi = userinfo.getIsyanshi();
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
		    userId =user.getId();
		    }
		    else{
		    	response.sendRedirect(request.getContextPath() +"/index.jsp");
		    }
		    UserInfoVO user2 =UserInfoVO.findFirstBySql(UserInfoVO.class, "select isliulan,ispcsuper,isyanshi from  TCUserInfo where loginName= ? and isinvalid = 0 ",new Object[] { userName });
			 if(user2 != null){
				ispcsuper = user2.getIspcsuper();
			    isliulan = user2.getIsliulan();
			    isyanshi = user2.getIsyanshi();
			 }
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
//   var r = str.match(/^[1-9][0-9]{3}-(0?[1-9]|1[0|1|2])-(0?[1-9]|[1|2][0-9]|3[0|1])\s([0-2][0-3]):(0?[0-9]|[1|2|3|4|5][0-9])$/);
	 
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

	 $('#qstartTime').datebox({     
	       width:inputWidth 
	   }); 

	 $('#qendTime').datebox({     
	       width:inputWidth 
	   }); 
	 
	 $("#ywResultinfo").css("width", inputWidth);  
	 $("#ywstatus").css("width", inputWidth); 
	 $("#userName").css("width", inputWidth); 

  jyinfowin = $('#jyinfo-window').window({  closed:true,draggable:false,modal:true });

  var url2 = encodeURI(encodeURI('/tcweb/user/getUserListByCompanyId2')); 
	 $("#userId2").autocomplete(  
	            url2,  
	            {  
	            scroll: false,  
	            matchCase: false,
            	matchSubset: true,
            	matchContains: false,  
	                width: 188,  
	                minChars: 2, 
	                max:20, 
	                scrollHeight: 100,
	                extraParams: {	q: function() {
					return $("#this").val();
				    },
	                companyId: function(){ return $('#ywCompanyId').attr("value");}
			        },   
	                dataType: "json",  
	                mustMatch:false,  
	                parse: function(data) {  
	                    var rows = [];  
	                    for(var i=0; i<data.length; i++){  
	                     rows[rows.length] = {   
	                       data:data[i].id +"-"+data[i].userName,   
	                       value:data[i].id,   
	                       result:data[i].userName   
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
	        	 $('#userId').attr("value",formatted);
	            }
	            else{
	             $('#userId').attr("value",'');
	 	            }
	        });
  

  var url = encodeURI(encodeURI('/tcweb/elevator/getAutoYwCompanyList')); 
	 $("#ywCompanyIdinfo").autocomplete(  
	            url,  
	            {  
	                scroll: false,  
	                matchCase: false,
	            	matchSubset: true,
	            	matchContains: false,
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

	 var wgurl = encodeURI(encodeURI('/tcweb/elevator/getAutoWgCompanyList')); 
	 $("#wgCompanyIdinfo").autocomplete(  
	            wgurl,  
	            {  
	                scroll: false, 
	                matchCase: false,
	            	matchSubset: true,
	            	matchContains: false, 
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
	        	 $('#wgCompanyIdinfo2').attr("value",formatted);
	            }
	            else{
	             $('#wgCompanyIdinfo2').attr("value",'');
	 	            }
  	        });

	 $("#ywCompanyId2").autocomplete(  
	            url,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                matchCase: false,
	            	matchSubset: true,
	            	matchContains: false,
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
	        	 $('#ywCompanyId').attr("value",formatted);
	            }
	            else{
	             $('#ywCompanyId').attr("value",'');
	 	            }
	        });
	/*		
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

	    comba =$('#areainfo').combobox({
			 onSelect: function (record) {
			 combt.combobox({
	       url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.value),
	       valueField: 'id',
	       textField: 'companyName'
	   }).combobox('clear');

		     }
			}); */
	 <%if((role == 1 || role ==2)||(role == 22 || role ==23)) { %>  
	 combt =$('#qtownshipStreets').combobox({
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

	    $('#areainfo').combobox({   
	        url:'/tcweb/elevator/areaInfoList',   
	        valueField:'area',   
	        textField:'area'  
	    }); 
	  
	    comba =$('#areainfo').combobox({
			 onSelect: function (record) {
	    	combt.combobox({
	 //      url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.value),
	       url: '/tcweb/elevator/getjdbCompanyListByNewarea?companyArea='+encodeURI(record.area),
	       valueField: 'id',
	       textField: 'companyName'
	   }).combobox('clear');

		     }
			});
	     <% } 
	    else if(role == 8 || role ==9){ %>
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

	    $('#areainfo').combobox({   
	        url:'/tcweb/elevator/areaInfoList',   
	        valueField:'area',   
	        textField:'area'  
	    }); 
	  
	    comba =$('#areainfo').combobox({
			 onSelect: function (record) {
	    	combt.combobox({
	 //      url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.value),
	       url: '/tcweb/elevator/getjdbCompanyListByNewarea?companyArea='+encodeURI(record.area),
	       valueField: 'id',
	       textField: 'companyName'
	   }).combobox('clear');

		     }
			});
	     <% } 
	     else if(role == 10 || role ==11){ %>
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
		
  
    winyw = $('#yw-window').window({  closed:true,draggable:false,modal:true }); 
    winywtj = $('#ywtjwindow').window({  closed:true,draggable:false,modal:true,minimizable:false,collapsible:false,maximizable:false });
    winywts = $('#ywtswindow').window({  closed:true,draggable:false,modal:true,minimizable:false,collapsible:false,maximizable:false }); 
    
	$('#btn-save,#btn-cancel,#btn-ok,#btn-no,#btn-no2').linkbutton(); 
	win = $('#car-window3').window({closed:true,draggable:false,minimizable:false,collapsible:false,maximizable:false }); 
	form = win.find('form');
	
	
	shenhewin = $('#shenhe-window').window({  closed:true,draggable:false,modal:true,onClose:function(){$('#ywPoint').empty(); 
  //  $('#map_X').attr("value",plng);
  //  $('#map_Y').attr("value",plat);
    } });

	 setwidthSousuoInput();
	
	grid=$('#tt').datagrid({
	    title:'维保信息列表',
	    fitColumns:false,
 	    striped:true,
	    pageSize:25,
	    pageList:[15,25,30,35,40],
	//    url:'/tcweb/yw/ywlist',
	    url:'/tcweb/yw/ywlistByOrder',
	    queryParams:{},
	    frozenColumns:[[
         {field:'registNumber',align:'center',halign:'center',title:'电梯编号↑↓',sortable : true,width:80,formatter: function(value,rec,index) {
        	 <% if("1".equals(cityName)){ %>
        	 return "N"+value;
        	 <% } else {%>
        	 return value;
        	  <% }%>
             }},
         {field:'address',align:'left',halign:'center',title:'地址',width:200},
         {field:'buildingName',align:'left',halign:'center',title:'楼盘名称',width:150},
         {field:'maintainTypecode',align:'center',halign:'center',title:'类型',width:50},
         {field:'startTime',align:'center',halign:'center',title:'开始时间',width:130},
         {field:'endTime',align:'center',halign:'center',title:'结束时间↑↓',sortable : true,width:130},
         {field:'dateSpan',align:'center',title:'时长（分钟）',width:70,formatter: function(value,rec,index) { 
      var startTime =rec.startTime;
      var endTime = rec.endTime;
      if(startTime)
        startTime=Date.parse(startTime.replace(/-/g,'/').replace(/：/g,":"));
      if(endTime)
         endTime=Date.parse(endTime.replace(/-/g,'/').replace(/：/g,":"));
       var date3=endTime-startTime;
     //计算出相差分钟数
       var days=parseInt(date3/(60*1000));
         return days; 
       }},
      {field:'userName',align:'center',halign:'center',title:'维保人员',width:80,formatter: function(value,rec,index) {
         var userId=rec.userId; 
         var loginName=rec.loginName;  
      <%if(role==1 || role==2){%> 
  //    return  value+ '<a href="#" onclick="ywtj2('+userId+','+'\''+loginName+'\''+')"><i class="fa fa-pie-chart" aria-hidden="true" style="color:#61B5CF;"></i></a>';
        return  '<a href="#" onclick="ywts('+userId+')">'+value+'</a>&nbsp;&nbsp;&nbsp;&nbsp;'+ '<a href="#" onclick="ywtj2('+userId+','+'\''+loginName+'\''+')"><i class="fa fa-pie-chart" aria-hidden="true" style="color:#61B5CF;"></i></a>';
        
      <%} 
      else { %>
        return value;  
     <% }%>
     }}
       ]],
	    columns:[[       
		    {field:'loginName',align:'center',halign:'center',title:'登陆账号',width:160},
	        {field:'companyName',align:'left',halign:'center',title:'维保单位',width:150},
	        {field:'wgcompanyName',align:'left',halign:'center',title:'使用单位',width:150},
	        {field:'jdbCompanyName',align:'left',halign:'center',title:'街道办',width:80},
	        {field:'subTime',align:'center',halign:'center',title:'上传时间↑↓',sortable : true,formatter: function(value,rec,index) {
                 if(value)
                     return value.substring(0,16);
                 else
                     return value;
		         }},

		    
		     {field:'ywstatus',align:'center',title:'状态',width:80,formatter: function(value,rec,index) {
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
		        	 if(('0' == value) || ('8' == value) ){
		                // if(picNum>0)
		                 <% if(role==1 || role==2){   
		            	     if(ispcsuper == 1){
		                 %> 
	                      //    return "审核中"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"0"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+1+")'/>";
	                         return '审核中'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'0'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+1+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
		                     
		                 <% }else {%>
		                	   return "审核中";
		                <% 
		                 } }
		                    else {  
		                	    if(flag){ %>
		                //    	 return "审核中"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"0"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+1+")'/>";	
		                         return '审核中'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'0'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+1+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
		                       <% }
		                	    else { %> 
		                        return "审核中";
		                        <% }%>
		                    <% }%>   
		             }else if('4'==value){
		            	// if(picNum>0)
		            	 <%if(role==1 || role==2){
		            		  if(ispcsuper == 1){
		            	 %>
                       //     return "无效"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"4"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+1+")'/>";
                            return '无效'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'4'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+1+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	  
                         <%} else { %>
                              return "无效";
                         <%
                         } }
		            	 
		            	 else {
                        	 if(flag) { %>
		            	//	   return "无效"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"4"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+1+")'/>";
		            		   return '无效'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'4'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+1+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	  
		            		   <% }	
		            		   else  { %>  
		            	        return "无效";
		            	      <% }%> 
		            	   <% }%>
		             }
	                 else{
		                 <%if(role==1 || role==2){
		                	  if(ispcsuper == 1){
		                 %>
                     //      return "通过"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"1"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+1+")'/>";
                          return '通过'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'1'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+1+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	  
                         <%}  else { %>
                        return "通过";
                        <%  }}
		                   else {   
		                	   if(flag) { %> 
		            	  //       return "通过"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"1"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+1+")'/>";
		            	          return '通过'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'1'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+1+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	  
		            	       <% }
			            	   else { %>      
		            	         return "通过";
		            	           <%}%> 
		            	   <% }%>
	                 }
			         }},  
                    <% if(isyanshi != 1){
                    if(role==1 || role==2){%>   
			         {field:'ywResult',align:'center',title:'结论',width:80,formatter: function(value,rec,index) {
			             var ywResult = rec.ywResult;
					        if(ywResult==17)
					  		    return "图象清晰正常";
					        else if(ywResult == 18)
						  		return "无法分辨数据";
					        else if(ywResult == 80)
						  		return "未知";
					        else if(ywResult == 19)
						  		return "遮挡数据";
					        else if(ywResult == 20)
						  		return "无图像数据";
					        else if(ywResult == 21)
						  		return "GIS位置错误";
					        else if(ywResult == 22)
						  		return "非现场运维数据";
					        else if(ywResult == 23)
						  		return "纸制复印件数据";
					        else if(ywResult == 24)
						  		return "电子复印件数据"; 
					        else if(ywResult == 0)
					        	return "未检测"; 
					        else if(ywResult == 1)
					        	return "有效图片"; 
					        else if(ywResult == 2)
					        	return "无效图片"; 
					        else if(ywResult == 4)
					        	return "疑似图片";
					        else if(ywResult == 8)
				        	    return "图片不存在"; 
					        else if(ywResult == 16)
				        	    return "前端GIS通过";
					       else if(ywResult == 25)
				        	    return "不需要审核";
					       else if(ywResult == 26)
				        	    return "前端防伪通过";
					        else
						        return "未知";     
						         }},
						         <%}
			            else {
						   if(flag){ %>
						   {field:'ywResult',align:'center',title:'结论',width:80,formatter: function(value,rec,index) {
						       var ywResult = rec.ywResult;
						        if(ywResult==17)
						  		    return "图象清晰正常";
						        else if(ywResult == 18)
							  		return "无法分辨数据";
						        else if(ywResult == 80)
							  		return "未知";
						        else if(ywResult == 19)
							  		return "遮挡数据";
						        else if(ywResult == 20)
							  		return "无图像数据";
						        else if(ywResult == 21)
							  		return "GIS位置错误";
						        else if(ywResult == 22)
							  		return "非现场运维数据";
						        else if(ywResult == 23)
							  		return "纸制复印件数据";
						        else if(ywResult == 24)
							  		return "电子复印件数据"; 
						        else if(ywResult == 0)
						        	return "未检测"; 
						        else if(ywResult == 1)
						        	return "有效图片"; 
						        else if(ywResult == 2)
						        	return "无效图片"; 
						        else if(ywResult == 4)
						        	return "疑似图片";
						        else if(ywResult == 8)
					        	    return "图片不存在"; 
						        else if(ywResult == 16)
					        	    return "前端GIS通过";
						       else if(ywResult == 25)
					        	    return "不需要审核";
						       else if(ywResult == 26)
					        	    return "前端防伪通过";
						        else
	 						        return "未知";   
							         }},   
							   		   
						 <%  }
						  
					  } }
					  %>
					
			         {field:'xiangqing',align:'center',title:'详情',formatter: function(value,rec,index) {
			        	 var registNumber = ''+rec.registNumber;
			  		//	 return "<img src='<%=request.getContextPath()%>/images/yulan.png' title='查看' style='cursor:hand;' onclick='openYwDetail("+"\""+registNumber+"\""+")'/>";   
			        	 return '<a href="#" onclick="openYwDetail('+'\''+registNumber+'\''+')"><i class="fa fa-info-circle fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
		  	        	     
				         }},
				     {field:'1',title:'检验信息',align:'center',
					            formatter: function(value,rec,index) {
					            return '<a href="#" onclick="jyinfo('+'\''+rec.registNumber+'\''+')"><i class="fa fa-bug fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
					  	        	
				 	            }
				          }
			         		          
	    ]],
	    rowStyler:function(index,row){  //暂时取消颜色超期
		    var endTime = row.endTime;
		    if(getDateDiff(endTime))
		    	return 'color:#ff0000;';
		    else
		    	return 'color:#000000;';
		    
	     },
	     pagination:true,
		 singleSelect:true,
		 <% if(isliulan == 0){%>
	    <% if(role==2 || role==1){%> 
	    toolbar:[
	     /*	{
	        text:'新增',
	        iconCls:'icon-add',
	        handler:function(){
	    	win.window('open');  
	    //	form.form('clear');
	        addFun();
	    	form.url ='/tcweb/yw/add';	
	    	colseWinDetail();	 
	        }
	    },*/
	    {
	        text:'删除',
	        iconCls:'icon-cut',
	        handler:function(){
	    	 var row = grid.datagrid('getSelected'); 
	    	 if(row){
	    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
	    	 jQuery.post('/tcweb/yw/delete',
	    	    	 {'id':row.id},
	    	    	 function(data){
	    	    		eval("data="+"'"+data+"'");  
	    	    		if("success"==data){
	    	    		//	$.messager.alert("操作成功",'谢谢');
	    	    		 $.messager.show({   
	    			 title:'提示信息',
	    			 timeout:1000,
	    			 msg:'操作成功，谢谢。' 
	    		 });  	
		    	            grid.datagrid('reload');
	    	    		}
	    	    		else{
	    	    			$.messager.alert('操作失败','没有删除','error');
		    	    		}
		    	       });}}
  	       );
	    	 }else{
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。' 
	    		 });   
		     }
	    	 
	        }
	    },'-',{
	        text:'修改',
	        iconCls:'icon-edit',
	        handler:function(){
	    	var row = grid.datagrid('getSelected');   
	    	if (row){   
	    		 win.window('open');   
	    		 colseWinDetail();
	    		 ybianjixiala(row.companyName,row.userName);
	    		 form.form('load', '/tcweb/yw/edit/'+row.id);
	    		 form.url = '/tcweb/yw/update/'+row.id; 
	    		 opt =1;
	    	 } else {  
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。' 
	    		 });   
	    	 }  
	        }
	    }] 
	    <%} else {%>
	    pagination:true,
	    singleSelect:true
	    <%}%>
	    <%}%>
	});	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,25,30,35,40]});  

	 	  
}
);



function clearQuery(){
	$('#registNumber').attr("value","");
	$('#qaddress').attr("value","");
	$('#loginName').attr("value","");
	$('#buildingName').attr("value","");
	$("#qstartTime").datebox("setValue","");  
	$("#qendTime").datebox("setValue","");  
	$('#ywCompanyIdinfo').attr("value","");
	$('#ywCompanyIdinfo2').attr("value","");
	$('#wgCompanyIdinfo').attr("value","");
	$('#wgCompanyIdinfo2').attr("value","");
	$('#userName').attr("value","");
	/*
	$('#ywCompanyIdinfo').combobox('clear');
	$('#ywCompanyIdinfo').combobox({
		url:'/tcweb/elevator/getYwCompanyList',
	    valueField:'id',
	    textField:'companyName'
	}); */
 //	$('#areainfo option:first').attr('selected','selected');
	 <%if((role == 1 || role ==2) || (role ==22 || role ==23)) {%>
	$('#areainfo').combobox('clear');
	<% }%>

	 <%if(role == 8 || role ==9) {%>
		$('#areainfo').combobox('clear');
		<% }%>

	 <% if (role != 20 && role != 21) { %>
	$('#qtownshipStreets').combobox('clear');
	<% } %>
	$('#ywResultinfo option:first').attr('selected','selected');
	$('#ywstatus option:first').attr('selected','selected');
	
}

function query(){ 
	var registNumber=$('#registNumber').attr("value");
	var address=$('#qaddress').attr("value");
	var buildingName=$('#buildingName').attr("value"); 
	var loginName=$('#loginName').attr("value");
//	var ywCompanyId=$('#ywCompanyIdinfo').combobox('getValue'); 
    var ywCompanyId=$('#ywCompanyIdinfo2').attr("value");  
    var wgCompanyId=$('#wgCompanyIdinfo2').attr("value");
	var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue");  
	 if (!ywCompanyId){
	    	ywCompanyId =0;
	    	}
	 if (!wgCompanyId){
	    	wgCompanyId =0;
	    	}                   
	// var area=$('#areainfo option:selected').val();
	 <%if((role == 1 || role ==2) || (role ==22 || role ==23)) {%>
	 var area =$('#areainfo').combobox('getValue'); 
	 <% } else if(role == 8 || role == 9){ %>
	 var area =$('#areainfo').combobox('getValue'); 
	 <% } else {%>
	 var area="";
	 <% }%>  
	 <% if (role == 20 || role == 21) { %>
	    var qtownshipStreets = "";
	    <%} else {%>
	    var qtownshipStreets =$('#qtownshipStreets').combobox('getValue'); 
	    <% }%>
	 var ywResult=$('#ywResultinfo option:selected').val();
     var userName=$('#userName').attr("value"); 

     var ywstatus ="100";
     <% if(ispcsuper == 1) {%>
      ywstatus =$('#ywstatus option:selected').val();
     <% }%>
	 if(qtownshipStreets==""){
	    	qtownshipStreets = 0;
	        }           
  //   grid.datagrid("options").url='/tcweb/yw/query';
     grid.datagrid("options").url='/tcweb/yw/queryByOrder';
     grid.datagrid("options").queryParams={'registNumber':registNumber,'address':address,'buildingName':buildingName,'loginName':loginName,'companyId':ywCompanyId,'companyId2':wgCompanyId,'area':area,'ywResult':ywResult,'qstartTime':qstartTime,'qendTime':qendTime,'townshipStreets':qtownshipStreets,'userName':userName,'ywstatus':ywstatus};
     
    $('#tt').datagrid('reload');
	}


var sheheWindIndex =1; //1代表主界面维保记录打开的审核窗口，2代表维保详情中维保记录打开的审核窗口
var shenheid=0;
var ywstatusValue=0;
function openShenheInfo(registNumber,startTime,id,ywstatus,flexStartx,flexStarty,flexEndx,flexEndy,ywResult,role,userId,windex){
	sheheWindIndex =windex;
	shenheid=id;
	ywstatusValue=ywstatus;
	shenhewin.window('open');
	var ywimg="";
//	ywing ='<img height="480" align="center" style="width:100%" src="<%=request.getContextPath()%>'+'/servlet/ywImage.jpg?registNumber='+registNumber+'&startTime='+startTime+'"/>';
	ywing ="<iframe id='flex' src='flex.jsp?registNumber="+registNumber+"&startTime="+startTime+"&id="+id+"&flexStartx="+flexStartx+"&flexStarty="+flexStarty+"&flexEndx="+flexEndx+"&flexEndy="+flexEndy+"&ywResult="+ywResult+"&role="+role+"&userId="+userId+"' width='100%' height='100%' scrolling='no' ></iframe>";
	document.all.ywImg.innerHTML=ywing;
	if(ywstatus==0){
		<%if(role==2 || role==1) {%>
		document.all.shenheDiv.innerHTML='<a href="javascript:void(0)" onclick="shenhe()" id="btn-ok" icon="icon-ok">通过</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="noshenhe()" id="btn-no" icon="icon-no">不通过</a><a href="javascript:void(0)" onclick="closeshenhe()" id="btn-no2" icon="icon-no">关闭</a>';
	 //   $('#btn-ok').html("审核");
		$('#btn-ok').linkbutton();
		$('#btn-no').linkbutton();
		$('#btn-no2').linkbutton();
		<%} else {%>
		document.all.shenheDiv.innerHTML='<a href="javascript:void(0)" onclick="shenhe()" id="btn-no" icon="icon-ok">关闭</a>';
		$('#btn-no').linkbutton();
		<%}%>
		}
	else{ 
		document.all.shenheDiv.innerHTML='<a href="javascript:void(0)" onclick="shenhe()" id="btn-no" icon="icon-no">关闭</a>';
	//	$('#btn-no').html("关闭");
		$('#btn-no').linkbutton();
	}
	if(sheheWindIndex ==1)
	ywPoint();
	else
	hisywPoint();
}
	
function openCarinfoDetail(id){ 
	win.window('open'); 
	
	form.form('load', '/czweb/car/edit/'+id);
	showWinDetail();
	
/*	$.post("/czweb/image/findImagePath",{"id":id},
			  function(data){
		     $('#image_path').attr("src",data);   
		         },"text");	 
	*/
	
}

function closeWindow(){ 
	win.window('close');
	}

function showWinDetail(){
	$("form input").css({border:'0px solid' });
	
	$('#dev_idinfo').attr("readonly","readonly");
	$('#carnuminfo').attr("readonly","readonly");
	$('#qy_nameinfo').attr("readonly","readonly");
	$('#simnuminfo').attr("readonly","readonly");
	$('#dev_typeinfo').attr("readonly","readonly");
	$('#car_typeinfo').attr("readonly","readonly");
	$('#carnum_colorinfo').attr("readonly","readonly");
	$('#car_colorinfo').attr("readonly","readonly");
	$('#longitudeinfo').attr("readonly","readonly");
	$('#latitudeinfo').attr("readonly","readonly");
	$('#angleinfo').attr("readonly","readonly");
	$('#speedinfo').attr("readonly","readonly");
	$('#gps_timeinfo').attr("readonly","readonly");
	$('#personinfo').attr("readonly","readonly");
	$('#phoneinfo').attr("readonly","readonly");
	
	$(".fontShow").hide();
	$('#btn-save').hide();
	$('#btn-cancel').show();
	$('#table2').show();

	$('#qy_nameinfo').combobox('disable');   
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

		var ywCompanyId=$('#ywCompanyId').attr("value"); 
	//	var ywCompanyId =$('#ywCompanyId').combobox('getValue');
	//	alert("ywCompanyId:"+ywCompanyId);
		
	//	var userId =$('#userId').combobox('getValue');
	   var userId=$('#userId').attr("value");
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

function closeshenhe(){
	 shenhewin.window('close'); 
}

function noshenhe(){
	if(sheheWindIndex ==1){
	 <%if(role==1 || role==2) {%>
	 if(ywstatusValue==0){ //判断状态，如果是待审核状态才拒绝审核
		 jQuery.post('/tcweb/yw/newshenheByid3',
		    	 {'id':shenheid,'ywResult':$('#ywResult option:selected').val()},
		    	 function(data){
		    		eval("data="+"'"+data+"'");  
		    		if("success"==data){
		    		//	$.messager.alert("操作成功",'谢谢');
		    		 $.messager.show({   
				 title:'提示信息',
				 timeout:1000,
				 msg:'操作成功，谢谢。' 
			 });  	
		    		$('#ywPoint').empty();
		    		shenhewin.window('close');
		    		if(sheheWindIndex==1)
	 	            grid.datagrid('reload');
		    		else
		    		gridyw.datagrid('reload');	
		    		}
		    		else{
		    			$.messager.alert('操作失败','拒绝失败','error');
	    	    		}
	    	       });

		 }
	  <%} else{%>
	 $('#ywPoint').empty();
	 shenhewin.window('close');
	 <%}%>
	}
	else{
		$.messager.alert('操作失败','历史运维信息中不能修改','error');
		 shenhewin.window('close');   //历史运维信息不提供修改
		}
}

function shenhe(){
	if(sheheWindIndex ==1){
	 <%if(role==1 || role==2) {%>
	 var ifrm = document.getElementById("flex");
	 var flexLineStr =ifrm.contentWindow.getPointString();
	 var strArray;
	 if(flexLineStr !=""){
  //	 alert(flexLineStr); 
	 strArray =flexLineStr.split(","); 
	 
	 if(ywstatusValue==0){ //判断状态，如果是待审核状态才进行状态更新
     
     if(plng>0 && plat>0){
		   if(plng!=map_x || plat!=map_y){
			   $.messager.confirm('Confirm','你将通过审核修订电梯位置坐标 ?',function(r){
				   if (r){ 
	    jQuery.post('/tcweb/yw/newshenheByid',
	    	 {'id':shenheid,'map_x':plng,'map_y':plat,'ywResult':$('#ywResult option:selected').val(),'flexStartx':strArray[0],'flexStarty':strArray[1],'flexEndx':strArray[2],'flexEndy':strArray[3]},
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
	    		if(sheheWindIndex==1)
 	              grid.datagrid('reload');
	    		else
	    		  gridyw.datagrid('reload');
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
    	   jQuery.post('/tcweb/yw/newshenheByid2',
    		    	 {'id':shenheid,'ywResult':$('#ywResult option:selected').val(),'flexStartx':strArray[0],'flexStarty':strArray[1],'flexEndx':strArray[2],'flexEndy':strArray[3]},
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
    		    		if(sheheWindIndex==1)
    	 	            grid.datagrid('reload');
    		    		else
    		    		gridyw.datagrid('reload');
    		    		}
    		    		else{
    		    			$.messager.alert('操作失败','审核失败','error');
    	    	    		}
    	    	       });

           }
		 	 
	 } 
	 else{  //无效和通过状态只查看图像
		 /*
		 $('#ywPoint').empty();
		  jQuery.post('/tcweb/yw/updateYwResultById',
			    	 {'id':shenheid,'ywResult':$('#ywResult option:selected').val(),'flexStartx':strArray[0],'flexStarty':strArray[1],'flexEndx':strArray[2],'flexEndy':strArray[3]},
			    	 function(data){
			    		eval("data="+"'"+data+"'");  
			    		if("success"==data){
			    		//	$.messager.alert("操作成功",'谢谢');
			    		 $.messager.show({   
					 title:'提示信息',
					 timeout:1000,
					 msg:'操作成功，谢谢。' 
				 });  	
						if(sheheWindIndex==1)
		 	            grid.datagrid('reload');
						else
						gridyw.datagrid('reload');
			    		}
			    		else{
			    			$.messager.alert('操作失败','审核结果修改失败','error');
		    	    		}
		    	       });
		 shenhewin.window('close');  */
		 }
	 } 
	 <%} else{%>
	 $('#ywPoint').empty();
	 shenhewin.window('close');
	 <%}%>
   
	}
	else{
		$.messager.alert('操作失败','历史运维信息中不能修改','error');
		 shenhewin.window('close');   //历史运维信息不提供修改
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
	jQuery.post('/tcweb/yw/shenhePointByReg', {'id':shenheid},function(data){ 
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

function hisywPoint(){
	jQuery.post('/tcweb/yw/shenhePointByhisReg', {'id':shenheid},function(data){ 
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
	$('#ywPoint').append("<iframe src='mapinfo.jsp?map_x="+map_x+"&map_y="+map_y+"&map_x0="+map_x0+"&map_y0="+map_y0+"&map_x1="+map_x1+"&map_y1="+map_y1+"&map_x2="+map_x2+"&map_y2="+map_y2+"' width='100%' height='100%' scrolling='no'></iframe>");
    
}

function showMap(){
	var map = new BMap.Map("container");// 创建地图实例
	var point = new BMap.Point(map_x, map_y);
	map.centerAndZoom(point, 15); // 初始化地图，设置中心点坐标和地图级别   
}

function openYwDetail(registNumber){
	winyw.window('open'); 
	 gridyw=$('#ywttmap').datagrid({
	 	    title:'',
	 	    pageSize:15,
	 	    pageList:[15,20,25,30,35,40],
	 	    url:'/tcweb/yw/ywlistByreg',
	 	    queryParams:{'registNumber':registNumber},
	 	   columns:[[
	 		        {field:'registNumber',title:'电梯编号',width:60,formatter: function(value,rec,index) {
	                	<% if("1".equals(cityName)){ %>
                        return "N"+value;
                        <% } else {%>
                        return value;
                        <% }%>
	                }},
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
	 			         }},

	 			  
	 			   {field:'ywstatus',title:'状态',width:80,formatter: function(value,rec,index) {
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
	 		                 if(('0' == value) || ('8' == value) ){
	 			                // if(picNum>0)
	 			                 <% if(role==1 || role==2){
	 			                	  if(ispcsuper == 1){
	 			                 %>   
	 		                 //         return "审核中"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"0"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+2+")'/>";
	 			                   return '审核中'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'0'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+2+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	  
	 			                 <%}  else {  %>  
	 			                    return "审核中";
	 			                	<% }  }
	 			                    else {  
	 			                	    if(flag){ %>
	 			                 //   	 return "审核中"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"0"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+2+")'/>";	
	 			                        return '审核中'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'0'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+2+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	   
	 			                       <% }
	 			                	    else { %> 
	 			                        return "审核中";
	 			                        <% }%>
	 			                    <% }%>
	 		                    // else
	 			                // return "审核中";     
	 			             }else if('4'==value){
	 			            	// if(picNum>0)
	 			            	 <%if(role==1 || role==2){
	 			            		  if(ispcsuper == 1){
	 			            	 %>
	 	                      //      return "无效"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"4"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+2+")'/>";
	 	                           return '无效'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'4'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+2+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	  
	 	                         <%} else { %>
	 	                        return "无效";
	 	                        <%	 }
	 	                         }
	 			            		 else {
	 	                        	 if(flag) { %>
	 			            	//	   return "无效"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"4"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+2+")'/>";
	 			            		  return '无效'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'4'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+2+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	  
	 			            		   <% }	
	 			            		   else  { %>  
	 			            	        return "无效";
	 			            	      <% }%> 
	 			            	   <% }%>
	 			             }
	 		                 else{
	 			                // if(picNum>0)
	 			                 <%if(role==1 || role==2){
	 			                	  if(ispcsuper == 1){
	 			                 %>
	 	                   //        return "通过"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"1"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+2+")'/>";
	 	                         return '通过'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'1'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+2+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	  
	 	                         <%} else{ %>
	 	                        return "通过";
	 	                       <%	 }
	 	                         }
	 			                   else {   
	 			                	   if(flag) { %> 
	 			            	   //      return "通过"+""+"<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='审核' style='cursor:hand;' onclick='openShenheInfo("+"\""+registNumber+"\""+","+"\""+startTime+"\""+","+id+","+"1"+","+flexStartx+","+flexStarty+","+flexEndx+","+flexEndy+","+ywResult+","+role+","+userId+","+2+")'/>";
	 			            	       return '通过'+'<a href="#" onclick="openShenheInfo('+'\''+registNumber+'\''+','+'\''+startTime+'\''+','+id+','+'1'+','+flexStartx+','+flexStarty+','+flexEndx+','+flexEndy+','+ywResult+','+role+','+userId+','+2+')"><i class="fa fa-pencil fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	  
	 			            	       <% }
	 				            	   else { %>      
	 			            	         return "通过";
	 			            	           <%}%> 
	 			            	   <% }%>
	 		                 }
	 				         }} 
	 				        <%  if(isyanshi != 1){
	 				        if(role==1 || role==2){%>
	 				         ,{field:'ywResult',title:'结论',formatter: function(value,rec,index) {
	 						        var ywResult = rec.ywResult;
	 						        if(ywResult==17)
	 						  		    return "图象清晰正常";
	 						        else if(ywResult == 18)
	 							  		return "无法分辨数据";
	 						        else if(ywResult == 80)
	 							  		return "未知";
	 						        else if(ywResult == 19)
	 							  		return "遮挡数据";
	 						        else if(ywResult == 20)
	 							  		return "无图像数据";
	 						        else if(ywResult == 21)
	 							  		return "GIS位置错误";
	 						        else if(ywResult == 22)
	 							  		return "非现场运维数据";
	 						        else if(ywResult == 23)
	 							  		return "纸制复印件数据";
	 						        else if(ywResult == 24)
	 							  		return "电子复印件数据"; 
	 						        else if(ywResult == 0)
	 						        	return "未检测"; 
	 						        else if(ywResult == 1)
	 						        	return "有效图片"; 
	 						        else if(ywResult == 2)
	 						        	return "无效图片"; 
	 						        else if(ywResult == 4)
	 						        	return "疑似图片";
	 						        else if(ywResult == 8)
 						        	    return "图片不存在"; 
	 						        else if(ywResult == 16)
						        	    return "前端GIS通过";
	 						       else if(ywResult == 25)
						        	    return "不需要审核";
	 						      else if(ywResult == 26)
						        	    return "前端防伪通过";
	 						        else
		 						        return "未知";       
	 							         }}
	 							         <%}
	 				            else {
	 							   if(flag){ %>
	 							   {field:'ywResult',title:'结论',formatter: function(value,rec,index) {
	 							        var ywResult = rec.ywResult;
	 							       if(ywResult==17)
	 		 						  		return "图象清晰正常";
	 							       else if(ywResult == 18)
	 		 							  		return "无法分辨数据";
	 							       else if(ywResult == 80)
	 		 							  		return "未知";
	 							       else if(ywResult == 19)
	 		 							  		return "遮挡数据";
	 							       else if(ywResult == 20)
	 		 							  		return "无图像数据";
	 							       else if(ywResult == 21)
	 		 							  		return "GIS位置错误";
	 							       else if(ywResult == 22)
	 		 							  		return "非现场运维数据";
	 							       else if(ywResult == 23)
	 		 							  		return "纸制复印件数据";
	 							       else if(ywResult == 24)
	 		 							  		return "电子复印件数据"; 
	 							        else if(ywResult == 0)
		 						        	return "未检测"; 
		 						        else if(ywResult == 1)
		 						        	return "有效图片"; 
		 						        else if(ywResult == 2)
		 						        	return "无效图片"; 
		 						        else if(ywResult == 4)
		 						        	return "疑似图片";
		 						        else if(ywResult == 8)
	 						        	    return "图片不存在"; 
		 						        else if(ywResult == 16)
							        	    return "前端GIS通过";
		 						       else if(ywResult == 25)
							        	    return "不需要审核";
		 						      else if(ywResult == 26)
							        	    return "前端防伪通过";
	 							      else
			 						        return "未知";    
	 								         }}   
	 								   		   
	 							 <%  }
	 							  
	 						  } }
	 						  %>
	 		    ]],
	          nowrap:true,
	 	    pagination:true
	 	
	 });

	     $('#ywttmap').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  
	}

/**  
 * 计算传入时间和当前时间差  
 * @param d 时间 格式：2010-04-10 10:22:36  
 * @return  
 */  
function getDateDiff(d){      
    var now = new Date().getTime();   
    var diffValue = now - Date.parse(d.replace(/-/g,'/').replace(/：/g,":"));   
    if(diffValue < 0){        
        return "";       
    }      
    var minute = 1000 * 60;     
    var hour = minute * 60;     
    var day = hour * 24;    
    var halfamonth = day * 15;    
    var month = day * 30;    
    var monthC =diffValue/month;      
    var weekC =diffValue/(7*day);      
    var dayC =diffValue/day;      
    var hourC =diffValue/hour;     
    var minC =diffValue/minute;          
    if(monthC>=1){       
       result=parseInt(monthC) + "个月前";       
    }else if(weekC>=1){   
       result=parseInt(weekC) + "个星期前";       
   }else if(dayC>=1){       
        result= parseInt(dayC) +"天前";       
   }else if(hourC>=1){       
      result= parseInt(hourC) +"个小时前";      
   }else if(minC>=1){       
      result= parseInt(minC) +"分钟前";       
   }else{   
       result="";       
    }    
 //   return result;   
    if(dayC>15)
        return true;
  //  else if(dayC ==15){
  //      if(minC>=1)
   //         return true;
 //       else 
  //          return false;
   //     }
    else
        return false;
        
        
}   

var rp;
function ywtj2(userId,loginName){
	winywtj.window('open');
//	jQuery.post('/tcweb/yw/ywtj', {'id':userId},function(data){ 
//        data = eval(data);//POST方法必加，ajax方法自动处理了   
//	   }, 'json');
//	rp =data.xcb;
 //   alert(loginName);
    $('#loginName').attr("value",loginName);
    $("#ywtjwindow").empty();
    
	$('#ywtjwindow').append("<iframe src='jfreeChart.jsp?userId="+userId+"' width='100%' height='100%' scrolling='no'></iframe>");
}


function ywts(userId){
	$('#ywuserId').attr("value",userId);
	winywts.window('open');
	
}

function closeywshenheWindow(){
	winywts.window('close');
}

function saveywshenheMessage(){
	var ywshenheMessage= $('#ywshenheMessage').attr("value");  
	if(ywshenheMessage == ""){
		$.messager.alert('操作失败', '推送信息不能为空', 'error');
        return ;
	}
	else{
		jQuery.post('<%=ywshenhetuisongServer%>',
		    	 {'userId':$('#ywuserId').attr("value"),'ywshenheMessage':$('#ywshenheMessage').attr("value")},
		    	 function(data){
		    	 $.messager.show({   
				 title:'提示信息',
				 timeout:1000,
				 msg:'操作成功，谢谢。' 
			      });  	
		    	 winywts.window('close');
	    	       });

		}
	/* 
    $('#ywtsform').form('submit', {  
    url:'/tcweb/yw/ywshenheMessage',
	onSubmit:function(){   
    	if(ywshenheMessage == ""){
    		$.messager.alert('操作失败', '推送信息不能为空', 'error');
            return  false;
    	}
    	    return true;
     },
     success : function(data) {
			if ("success" == data) {
			$.messager.show( {
				title : '提示信息',
				timeout : 1000,
				msg : '操作成功，谢谢。'
			});
			$('#ywuserId').attr("value",0);
			winywts.window('close');
		} else {
			$.messager.alert('操作失败', '操作失败', 'error');
		}
	}
		}); */
	
}

function setwidthSousuoInput(){
 /* var inputwidth=($(document.body).width()-150)*0.107;
    $('#sousuo input').css('width', inputwidth);
    $('#sousuo input').width(inputwidth);
    $('#sousuo select').width(inputwidth);
    $('#sousuo .easyui-combobox').width(inputwidth); */
}

function ybianjixiala(companyName,userName){
	$('#ywCompanyId2').attr("value",companyName);
	$('#ywCompanyId').attr("value",companyName);   
	$('#userId2').attr("value",userName);
	$('#userId').attr("value",userName); 
}


function jyinfo(registNumber){   
	  var registNumber =registNumber;
	  jQuery.post('/tcweb/elevator/jyinfotishi',
		    	 {'registNumber':registNumber},
		    	 function(data){
		    		 jyinfowin.window('open');
		    		 $('#jyCompanyName').attr("value",data.jyCompanyName); 
		    		 $('#inspector').attr("value",data.inspector); 
		    		 $('#checkReportNum').attr("value",data.checkReportNum);
		    		 $('#checkCategory').attr("value",data.checkCategory);
		    		 $('#checkResult').attr("value",data.checkResult);
		    		 $('#inspectDate').attr("value",data.inspectDate);
		    		 $('#nextInspectDate').attr("value",data.nextInspectDate);
		    		},'json'
	    	        );
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
 /* background: #F9F0DA; */
   padding-left: 2px; 
}

.form_input {
  display: block;
 /* width: 100%; */
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
 <fieldset id="addDiv" style="margin-left:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
     <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%;"> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td>
    <% if("1".equals(cityName)){ %> 
   <td nowrap>N<input id="registNumber" name="registNumber" ></input></td>
    <% } else {%>
    <td nowrap><input id="registNumber" name="registNumber" ></input></td>
     <% }%>
   <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="qaddress" name="qaddress" ></input></td>

    <td nowrap align="right">审核结论：</td>
   <td> 
   <select id="ywResultinfo"   name="ywResultinfo" style="height:25px;">
    <option value="100" selected ="selected"></option>
    <option value="80">未知</option> 
    <option value="0">未检测</option>
    <option value="1">有效图片</option>
    <option value="2">无效图片</option>
    <option value="4">疑似图片</option>
    <option value="8">图片不存在</option>
    <option value="16">前端GIS通过</option>
    <option value="26">前端防伪通过</option>
    <option value="17">图象清晰正常</option>
    <option value="18">无法分辨数据</option>
    <option value="19">遮挡数据</option> 
    <option value="20">无图像数据</option>
    <option value="21">GIS位置错误</option>
    <option value="22">非现场运维数据</option>
    <option value="23">纸制复印件数据</option> 
    <option value="24">电子复印件数据</option>
    <option value="25">不需要审核</option>   
   </select>
   </td>
    <% if(ispcsuper == 1) {%>
    <td align="right" nowrap>审核状态：</td>
   <td> 
   <select id="ywstatus"   name="ywstatus" style="height:25px;">
   <option value="100"></option>
   <option value="0">审核中</option>
   <option value="4">无效</option>
   <option value="1">通过</option>
   </select>
   </td>
   <% } %>
    
    <td align="right" nowrap>维保人员：</td> 
   <td><input id="userName" name="userName"></input></td>
   </tr>
   <tr>
  <td align="right" nowrap>楼盘名称：</td> 
   <td nowrap><input id="buildingName" name="buildingName"></input></td>
    <td align="right" nowrap>登陆账号：</td> 
   <td nowrap><input id="loginName" name="loginName"></input></td>
  <% if((role == 1 || role ==2) || (role == 22 || role ==23)){ %> 
   <td nowrap align="right">行政区划：</td>
   <td><input id="areainfo" name="areainfo" style="height:25px;"/></td>
  <% } 
  if(role == 8 || role == 9){ %>
	  <td nowrap>行政区划：</td>
	   <td> <input id="areainfo" name="areainfo"  style="height:25px;"/></td> 
  <% } %>	     
  <% if (role != 20 && role != 21) { %>
  <td align="right">街道办：</td>
 <!-- <td><select id="qtownshipStreets"  class="easyui-combobox" name="qtownshipStreets" style="width:154px;"></select></td> -->
  <td><input id="qtownshipStreets"  name="qtownshipStreets" style="height:25px;" /></td>
   <% } %>
   </tr>
   <tr>
    <td nowrap align="right">维保单位：</td>
   <td> 
  <!--  <select id="ywCompanyIdinfo"  class="easyui-combobox" name="ywCompanyIdinfo" style="width:152px;"></select>  -->
  <input id="ywCompanyIdinfo"   placeholder="输入至少两个关键字从下拉列表中选择"></input>
  <input type ="hidden" id="ywCompanyIdinfo2"></input>
 </td>
 <td nowrap align="right">使用单位：</td>
 <td><input id="wgCompanyIdinfo" placeholder="输入至少两个关键字从下拉列表中选择"></input>
  <input type ="hidden" id="wgCompanyIdinfo2"></input>
 </td>
    <td  nowrap align="right">开始时间：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" style="height:25px;"></input></td>
   <td align="right" nowrap>结束时间：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime" style="height:25px;"></input></td>
    
   <td></td>
   <td></td>
   
    <td colspan="2" nowrap>
  <!--  <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>
    <a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a>  -->
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
       <table id="tt"></table>
   </div>  
        
    </div>  
</div> 
<div id="car-window3" title="详细信息" style="width:600px;height:410px;"> 
 <div class="easyui-layout" data-options="fit:true"> 
 <div data-options="region:'north'" style="height:370px;text-align:center;border:0px;" >     
  <form method="post">  
  <table style="width: 100%;margin:auto;">    
   <tr>      
   <td width="150" align="center" style="background: #F5F5F5;">电梯编号</td> 
    <% if("1".equals(cityName)){ %>     
   <td>
   <!-- <input id="registNumberinfo" name="registNumber" onchange="queryYwCompanyByReg()"></input> -->
   N<input class="form_input" id="registNumberinfo" name="registNumber" style="width:130px;">
  </td> 
    <% } else {%>
  <td><input class="form_input" id="registNumberinfo" name="registNumber" style="width:152px;"></td> 
 <% }%> 
    <td></td>
    <td></td>
    </tr>
    <tr>
    <td align="center" style="background: #F5F5F5;">种类</td>      
   <td><select id="ywKind"  class="easyui-combobox" name="ywKind" style="width:152px;height:34px;">
    <option value="0">维保</option>
     <option value="1">巡检</option> 
</select></td> 
    <td width="150" align="center" style="background: #F5F5F5;">类型</td>    
 <td><select id="maintainTypecode"  class="easyui-combobox" name="maintainTypecode" style="width:152px;height:34px;">
    <option value="半月保">半月保</option>
    <option value="季保">季保</option> 
    <option value="半年保">半年保</option>
    <option value="年保">年保</option> 
</select></td>
   </tr>
   <tr> 
    <td align="center" style="background: #F5F5F5;">开始时间</td>      
   <td><input id="startTime" name="startTime" class="easyui-datetimebox" style="width:152px;height:34px;" showSeconds="false"></input></td> 
   
   <td align="center" style="background: #F5F5F5;">结束时间</td>      
   <td> 
   <input id="endTime" name="endTime" class="easyui-datetimebox" style="width:152px;height:34px;" showSeconds="false"></input></td>
   </tr>
   <tr>
   <td align="center" style="background: #F5F5F5;">开始位置</td>      
   <td><select id="sPosition"  class="easyui-combobox" name="sPosition" style="width:152px;height:34px;">
    <option value="0">机房</option>
    <option value="1">轿厢</option>  
</select>
   </td>
    
    <td align="center" style="background: #F5F5F5;">结束位置</td>      
   <td>
   <select id="ePosition"  class="easyui-combobox" name="ePosition" style="width:152px;height:34px">
    <option value="0">机房</option>
    <option value="1">轿厢</option>  
</select>
   </td>
   </tr>
    <tr> 
    <td align="center" style="background: #F5F5F5;">维保单位</td>
<td colspan="3">  
<!-- <select id="ywCompanyId"  class="easyui-combobox" name="ywCompanyId" style="width:152px;"></select> -->
  <input id="ywCompanyId2" style="width:100%;height:34px;">
  <input type ="hidden" id="ywCompanyId" name ="ywCompanyId">
 </td>
 </tr>
  <tr>      
   <td align="center" style="background: #F5F5F5;">维保人员</td>      
   <td>
   <!--  <select id="userId"  class="easyui-combobox" name="userId" style="width:152px;"></select> -->
   <input id="userId2" style="width:152px;height:34px;">
  <input type ="hidden" id="userId" name="userId">
   </td>   
   </tr>
   <tr>
   <td align="center" style="background: #F5F5F5;">备注</td>
   <td colspan="3"><textarea cols="73" rows="6" id="remark" name="remark" ></textarea></td>
   </tr> 
   </table>
   <table style="width:100%">
   <tr style="height:10px"></tr>
    <tr>
    <td align="center">
  <!--    <a href="javascript:void(0)" onclick="saveCar3()" id="btn-save" icon="icon-save">保存</a>  -->
  <!--  <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">取消</a>  -->  
  <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-close fa-lg" onclick="closeWindow()" style="width:100px">取消</a>
    </td>
    </tr> 
   </table>   
   </form> 
    </div> 
    </div> 
   </div>
  
  
   <!--  
    <div style="text-align:center;padding:5px;">    
     <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">取消</a>  </div> 
    </div> -->
 <div id="shenhe-window" title="审核详细信息" style="width:780px;height:500px;">
 <div style="both:clear">
 <div id="ywImg"  style="overflow:hidden;width:49%;height:425px;float:left;border:1px solid #000;"></div>
 <div id="ywPoint" style="overflow:hidden;width:50%;height:425px;float:right;border:1px solid #00f;"></div>
 </div> 
 <div style="both:clear"></div>
 <div style="float:left">结论：<select id="ywResult"  name="ywResult" style="width:80px;">
    <option value="80" selected ="selected">未知</option> 
    <option value="17">图象清晰正常</option>
    <option value="18">无法分辨数据</option>
    <option value="19">遮挡数据</option> 
    <option value="20">无图像数据</option>
    <option value="21">GIS位置错误</option>
    <option value="22">非现场运维数据</option>
    <option value="23">纸制复印件数据</option> 
    <option value="24">电子复印件数据</option>   
</select></div>
 <div align="center" id="shenheDiv"><a href="javascript:void(0)" onclick="shenhe()" id="btn-ok" icon="icon-ok">通过</a><a href="javascript:void(0)" onclick="noshenhe()" id="btn-no" icon="icon-no">不通过</a><a href="javascript:void(0)" onclick="closeshenhe()" id="btn-no2" icon="icon-no">关闭</a></div>
</div>

  <div id="yw-window" title="维保信息" style="width:780px;height:500px;">
        <table id="ywttmap"></table>  
   </div>
   
   <div id="ywtjwindow" title="运维统计信息" style="width:780px;height:500px;overflow-x:hidden;overflow-y:hidden;"> 
   </div> 
   
  <div id="ywtswindow" title="运维推送信息" style="width:480px;height:350px;overflow-x:hidden;overflow-y:hidden;">
  <div class="easyui-layout" data-options="fit:true"> 
     <div data-options="region:'north',split:false" style="height:250px;text-align:center;overflow:hidden;" > 
     <form id ="ywtsform">
     <table style="width: 100%;margin:auto">
    <tr> 
    <td>
   <textarea rows="16" cols="88" id="ywshenheMessage" name="ywshenheMessage" style="width: 100%;">运维数据无效，请按运维流程规范操作。</textarea> 
   <input type="hidden" id="ywuserId" name="ywuserId"/></input>
    </td>
    </tr>  
<tr>
</tr>
<tr>
</tr>
<tr>
</tr>
 </table>
 </form>
  </div>
     <div data-options="region:'center'" style="overflow-x:auto;overflow-y:hidden">
     <table style="text-align:center;width:100%">
    <tr>
   <td  align="right">
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-save fa-lg" onclick="saveywshenheMessage()" style="width:100px;color:#3399FF;">保存</a>
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-close fa-lg" onclick="closeywshenheWindow()" style="width:100px">取消</a>
   </td>
   </tr>
    </table>
   </div>
   </div> 
   </div> 
   
   <div id="jyinfo-window" title="检验信息" style="width:700px;height:260px;overflow-x:auto;overflow-y:hidden">
 <table>
  <tr>      
    <td nowrap width="150" align="center" style="background: #F5F5F5;">检验单位：</td>
  <td colspan="3"><input  class="form_input"  id="jyCompanyName" name="jyCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
    <tr>
     <td nowrap width="150" align="center" style="background: #F5F5F5;">检验人员：</td>
  <td><input class="form_input" id="inspector" name="inspector" style="border-width :0px 0px 1px;" readonly></input></td>
    <td nowrap width="150" align="center" style="background: #F5F5F5;">报告编号：</td>
  <td><input class="form_input"  id="checkReportNum" name="checkReportNum" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
    <tr>
     <td nowrap width="150" align="center" style="background: #F5F5F5;">检验类别：</td>
   <td><input class="form_input"  id="checkCategory" name="checkCategory" style="border-width :0px 0px 1px;" readonly></input></td>
    <td nowrap width="150" align="center" style="background: #F5F5F5;">检验结论：</td>
   <td><input class="form_input"  id="checkResult" name="checkResult" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   <tr>
     <td nowrap width="150" align="center" style="background: #F5F5F5;">检验日期：</td>
   <td><input class="form_input"  id="inspectDate" name="inspectDate" style="border-width :0px 0px 1px;" readonly></input></td>
    <td nowrap width="150" align="center" style="background: #F5F5F5;">下次检验日期：</td>
   <td><input class="form_input"  id="nextInspectDate" name="nextInspectDate" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   
   
   
 </table>
</div> 
</body>
</html>