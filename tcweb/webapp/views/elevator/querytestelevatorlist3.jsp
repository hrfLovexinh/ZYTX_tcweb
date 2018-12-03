<%@ page import="com.zytx.models.UserInfo" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>


<% 
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int role=0;
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

var chushixialaliebiaoNumber =0;
$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	$('#imgpic').bind("click",testDid);
	$('#imgpic2').bind("click",testDid2);
	$('#imgpic3').bind("click",testDid3);

	 var jdburl = encodeURI(encodeURI('/tcweb/elevator/getNewAutoJdbCompanyList')); 
	 $("#townshipStreets2").autocomplete(  
			    jdburl,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars:2,
	                max:30,
	                scrollHeight:100,  
	                extraParams: {	q: function() {
					return $("#this").val();
				    },
				    areaType: function(){ return $('#area').combobox('getValue'); }},   
	                dataType: "json",  
	                mustMatch:false,  
	                parse: function(data) {  
	                    var rows = [];  
	                    for(var i=0; i<data.length; i++){  
	                     rows[rows.length] = {   
	             //        data:data[i].id +"-"+data[i].companyName,   
	                       data:data[i].companyName,
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
	        	 $('#townshipStreets').attr("value",formatted);
	            }
	            else{
	             $('#townshipStreets').attr("value",'');
	 	            }
    	        });

	 var zzurl = encodeURI(encodeURI('/tcweb/elevator/getAutoZzCompanyList')); 
	 $("#zzCompanyId2").autocomplete(  
			    zzurl,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2,
	                max:20,
	                scrollHeight:100,  
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
	        	 $('#zzCompanyId').attr("value",formatted);
	            }
	            else{
	             $('#zzCompanyId').attr("value",'');
	 	            }
    	        });

	
	 $("#qzzCompanyId2").autocomplete(  
			    zzurl,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2,
	                max:20,
	                scrollHeight:100,  
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
	        	 $('#qzzCompanyId').attr("value",formatted);
	            }
	            else{
	             $('#qzzCompanyId').attr("value",'');
	 	            }
    	        });

	 var jyurl = encodeURI(encodeURI('/tcweb/elevator/getAutoJyCompanyList')); 
	 $("#jyCompanyId2").autocomplete(  
			    jyurl,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2,
	                max:20,
	                scrollHeight:100,  
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
	        	 $('#jyCompanyId').attr("value",formatted);
	            }
	            else{
	             $('#jyCompanyId').attr("value",'');
	 	            }
    	        });


	 var azurl = encodeURI(encodeURI('/tcweb/elevator/getAutoAzCompanyList')); 
	 $("#azCompanyId2").autocomplete(  
			    azurl,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2,
	                max:20,
	                scrollHeight:100,  
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
	        	 $('#azCompanyId').attr("value",formatted);
	            }
	            else{
	             $('#azCompanyId').attr("value",'');
	 	            }
    	        });

	 $("#qazCompanyId2").autocomplete(  
			    azurl,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2,
	                max:20,
	                scrollHeight:100,  
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
	        	 $('#qazCompanyId').attr("value",formatted);
	            }
	            else{
	             $('#qazCompanyId').attr("value",'');
	 	            }
 	        });
  
  

	 var wgurl = encodeURI(encodeURI('/tcweb/elevator/getAutoWgCompanyList')); 
	 $("#wgCompanyId2").autocomplete(  
			    wgurl,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2,
	                max:20,
	                scrollHeight:100,  
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
	        	 $('#wgCompanyId').attr("value",formatted);
	            }
	            else{
	             $('#wgCompanyId').attr("value",'');
	 	            }
    	        });

	 $("#qwgCompanyId2").autocomplete(  
			    wgurl,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2,
	                max:20,
	                scrollHeight:100,  
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
	        	 $('#qwgCompanyId').attr("value",formatted);
	            }
	            else{
	             $('#qwgCompanyId').attr("value",'');
	 	            }
 	        });

	 var ywurl = encodeURI(encodeURI('/tcweb/elevator/getAutoYwCompanyList')); 
	 $("#ywCompanyId2").autocomplete(  
			    ywurl,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2,
	                max:20,
	                scrollHeight:100,  
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

	 $("#qywCompanyId2").autocomplete(  
			    ywurl,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2,
	                max:20,
	                scrollHeight:100,  
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
	        	 $('#qywCompanyId').attr("value",formatted);
	            }
	            else{
	             $('#qywCompanyId').attr("value",'');
	 	            }
 	        });

	 var zjurl = encodeURI(encodeURI('/tcweb/elevator/getAutoZjCompanyList')); 
	 $("#zjCompanyId2").autocomplete(  
			    zjurl,  
	            {  
	            scroll: false,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2,
	                max:20,
	                scrollHeight:100,  
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
	        	 $('#zjCompanyId').attr("value",formatted);
	            }
	            else{
	             $('#zjCompanyId').attr("value",'');
	 	            }
    	        });

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

   
   
    comb9 =$('#areainfo').combobox({
		 onSelect: function (record) {
		 comb7.combobox({
            url: '/tcweb/elevator/getCompanyListByArea?companyArea='+encodeURI(record.value),
            valueField: 'id',
            textField: 'companyName'
        }).combobox('clear');

	     }
		});
	*/

	
	$('#btn-save,#btn-cancel,#btn-saveq,#textbtn-save,#selectbtn-save,#select2btn-save,#datebtn-save').linkbutton(); 
	win = $('#car-window2').window({  closed:true,draggable:false,modal:true,onClose:function(){$('input').attr("disabled","");} }); 
	form = win.find('form');

	win8 = $('#car-window8').window({  closed:true,draggable:false,modal:true }); 
	companyinfowin = $('#companyinfo-window').window({  closed:true,draggable:false,modal:true });

	gjquerywin = $('#gjquery-window').window({  minimizable:false,maximizable:false,closed:true,draggable:true,modal:false,onClose:function(){gjquerywinsave();} });
//	gjform =gjquerywin.find('form');
	gjquerytextwin = $('#textinput-window').window({  closed:true,draggable:false,modal:true });
	gjqueryselectwin = $('#selectinput-window').window({  closed:true,draggable:false,modal:true });
	gjqueryselect2win = $('#selectinput2-window').window({  closed:true,draggable:false,modal:true });
	gjquerydatewin = $('#dateinput-window').window({  closed:true,draggable:false,modal:true });
	 
	grid=$('#tt').datagrid({
	    title:'电梯列表',
	    pageSize:10,
	    pageList:[10,20,25,30,35,40],
	    url:'/tcweb/elevator/elevatorlist',
	    queryParams:{},
	    columns:[[
	        {field:'registNumber',title:'电梯编号',width:60},
	        {field:'registCode',title:'注册代码',width:150},
	        {field:'address',title:'地址',width:200},
	        {field:'buildingName',title:'楼盘名称',width:200},
	        {field:'area',title:'行政区划',width:60},
	        {field:'useNumber',title:'单位内部编号'},
	        {field:'nextInspectDate',title:'下次检验日期'},
	  //      {field:'jdbCompanyName',title:'街道办',width:150},
	        {field:'wgCompanyName',title:'使用单位',width:200},
	        {field:'ywCompanyName',title:'维保单位',width:200},
	 //       {field:'zzCompanyName',title:'制造单位',width:70},
	//        {field:'azCompanyName',title:'安装单位',width:70},
	//        {field:'jyCompanyName',title:'检验单位',width:55},
	 //       {field:'inspector',title:'检验人员',width:55},
	 //       {field:'inspectDate',title:'检验日期',width:55},
	
	        {field:'subTime',title:'最近一次维保日期',width:135,formatter: function(value,rec,index) {
                if(value!=null)
                    return value.substring(0,16);
		         }},
	     //   {field:'zjCompanyName',title:'质监局'},
	     //   {field:'deviceId',title:'黑匣子设备'},
	        {field:'ischangInfo',title:'变更信息',align:'center',
	        	formatter: function(value,rec,index) { 
	        	var registNumber = ''+rec.registNumber;
  	        	if(value==1)
  	        	   { 
  			        return "<img src='<%=request.getContextPath()%>/images/yulan.png' alt='查看' style='cursor:hand;' onclick='openCarinfoDetail("+"\""+registNumber+"\""+")'/>";
  	        	   }
   			        else
  	  			        return "";
  	            }}
	    ]],
	    pagination:true,
	   
	    toolbar:[
	      <% if(role==2 || role==1){%>
	     	    {
	        text:'新增',
	        iconCls:'icon-add',
	        handler:function(){
	   // 	if(chushixialaliebiaoNumber == 0){
		//    	chushixialaliebiao();   
		//	    }
	    	win.window('open');  
	    //	form.form('clear');
	        addFun();
	    	form.url ='/tcweb/elevator/add';	
	    	colseWinDetail();	 
	        }
	    },{
	        text:'删除',
	        iconCls:'icon-cut',
	        handler:function(){
	    	 var row = grid.datagrid('getSelected'); 
	    	 if(row){
	    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
	    	 jQuery.post('/tcweb/elevator/delete',
	    	    	 {'id':row.id,'registNumber':row.registNumber},
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
	    },'-',
       <% }%>
	    {
	        text:'修改',
	        iconCls:'icon-edit',
	        handler:function(){
	    	var row = grid.datagrid('getSelected');   
	    	if (row){
	    	//	if(chushixialaliebiaoNumber == 0){
			//    	chushixialaliebiao();   
			//	    }   
			
			     bianjixiala(row.zzCompanyId,row.zzCompanyName,row.azCompanyId,row.azCompanyName,row.wgCompanyId,row.wgCompanyName,row.ywCompanyId,row.ywCompanyName,row.jyCompanyId,row.jyCompanyName,row.zjCompanyId,row.zjCompanyName,row.townshipStreets,row.jdbCompanyName);
	    		 win.window('open');   
	    		 colseWinDetail();
	    		 editFun(row.registNumber,row.deviceId);
	    		 form.form('load', '/tcweb/elevator/edit/'+row.id);
	    		 form.url = '/tcweb/elevator/update/'+row.id; 
	    		 opt =1;
	    	 } else {  
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。' 
	    		 });   
	    	 }  
	        }
	    },'-', 
        {
            text: '显示隐藏列',
            iconCls: 'icon-search',
            handler: function () {
                OpenGetShowColumnDlg();
            }
        }
	    <%  if(role ==10 || role ==11){%>
	    ,{
	        text:'移入待领区',
	        iconCls:'icon-cut',
	        handler:function(){
	    	var row = grid.datagrid('getSelected');   
	    	if (row){
	    		 $.messager.confirm('','确定要移入待领区',function(data){if(data){
	    		jQuery.post('/tcweb/elevator/putdailingele',
	    			   	 {'id':row.id},
	    			   	 function(data){
	    			   		eval("data="+"'"+data+"'");  
	    			   		if("success"==data){
	    			   		 $.messager.show({   
	    					 title:'提示信息',
	    					 timeout:1000,
	    					 msg:'操作成功，谢谢。' 
	    				 });  	
	    			         grid.datagrid('reload');
	    			   		}
	    			   		else{
	    			   			$.messager.alert('操作失败','操作失败','error');
	    				    		}
	    				       }); }}
	    	       );

		    	
		    	}
	    	else {  
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。' 
	    		 });   
	    	 } 
	        }
	    }
	    <% }  %>
	    ],
	   
	    onDblClickRow :function(rowIndex,rowData){
		 //   alert("双击事件发生了"+rowData.registNumber);
		 //    if(chushixialaliebiaoNumber == 0){
		 //   	chushixialaliebiao();   
		//	    }
		    bianjixiala(rowData.zzCompanyId,rowData.zzCompanyName,rowData.azCompanyId,rowData.azCompanyName,rowData.wgCompanyId,rowData.wgCompanyName,rowData.ywCompanyId,rowData.ywCompanyName,rowData.jyCompanyId,rowData.jyCompanyName,rowData.zjCompanyId,rowData.zjCompanyName,rowData.townshipStreets,rowData.jdbCompanyName);	
		    win.window('open');
		    form.form('load', '/tcweb/elevator/edit2/'+rowData.registNumber);
		    showWinDetail();
	    }
	});	
	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,25,30,35,40]});   
   	  
}
);

function clearQuery(){
	$('#registNumber').attr("value","");
	$('#addressinfo').attr("value","");
	$('#registCodeinfo').attr("value","");
	$('#useNumberInfo').attr("value","");
	$('#ywCompanyIdinfo').attr("value","");
	$('#ywCompanyIdinfo2').attr("value","");
	
//	$('#ywCompanyIdinfo').combobox('clear');
//	$('#ywCompanyIdinfo').combobox({
//		url:'/tcweb/elevator/getYwCompanyList',
//	    valueField:'id',
//	    textField:'companyName'
//	});
	//$('#areainfo').combobox('clear');
	$('#areainfo option:first').attr('selected','selected');
}

function query(){  
	var registNumber=$('#registNumber').attr("value");
	var address=$('#addressinfo').attr("value");
	var registCode =$('#registCodeinfo').attr("value");
 //   var ywCompanyId=$('#ywCompanyIdinfo').combobox('getValue'); 
    var ywCompanyId=$('#ywCompanyIdinfo2').attr("value");
  //  var area=$('#areainfo').combobox('getValue'); 
    var area=$('#areainfo option:selected').val();
    var useNumber =$('#useNumberInfo').attr("value");
  //  alert("ywCompanyId---"+ywCompanyId);
 //   alert("ywCompanyId2---"+document.all.ywCompanyIdinfo.value);
 //   alert("ywCompanyId3---"+ywName);
 //     alert(registNumber);
    if (!ywCompanyId){
    	ywCompanyId =0;
   // 	alert("0");
    	}
	if(""==registNumber && ywCompanyId==0){
	   if(""==ywName && ""==area){
		   if(""==address){	
			   if(""==registCode){	
				   if(""==useNumber){	
		               grid.datagrid("options").url='/tcweb/elevator/elevatorlist';
				   }
		            else{
		            	  grid.datagrid("options").url='/tcweb/elevator/query';
			              grid.datagrid("options").queryParams={'useNumber':useNumber};
			            }
				   }
				   else{
	                grid.datagrid("options").url='/tcweb/elevator/query';
	                grid.datagrid("options").queryParams={'registCode':registCode,'useNumber':useNumber};
				   }
	       }
		   else{
			  grid.datagrid("options").url='/tcweb/elevator/query';
			  grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'registCode':registCode,'useNumber':useNumber};
		  }
	   }
	   else{
		 if(""==address){
			 grid.datagrid("options").url='/tcweb/elevator/query';
			 grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'registCode':registCode,'useNumber':useNumber};
				 
		 }
		 else{
		    grid.datagrid("options").url='/tcweb/elevator/query';
		    grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'registCode':registCode,'useNumber':useNumber};
		   }
	    }
	}
	else{
     grid.datagrid("options").url='/tcweb/elevator/query';
     grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'registCode':registCode,'useNumber':useNumber};
	}
    $('#tt').datagrid('reload');
	}
	

	
function openCarinfoDetail(registNumber){ 
//	win.window('open'); 
	
//	form.form('load', '/czweb/car/edit/'+id);
//	showWinDetail();
	
/*	$.post("/czweb/image/findImagePath",{"id":id},
			  function(data){
		     $('#image_path').attr("src",data);   
		         },"text");	 
	*/

 //	alert(registNumber);
	win8.window('open'); 
//	$('#registNumberMap').html(registNumber);
     gridmap=$('#ttmap').datagrid({
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
     $('#ttmap').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[5,10,15,20,25,30]});  


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
	$('input').attr("disabled","");
	}

function showWinDetail(){  
	$("#myform input").css({border:'0px solid' });

	$('input').attr("disabled","disabled");

	$('#registNumber').attr("disabled","");
	$('#addressinfo').attr("disabled","");
	$('#registCodeinfo').attr("disabled","");
	$('#useNumberInfo').attr("disabled","");
	
//	$('#ywCompanyIdinfo').combobox('enable');
	
	$('#eleType').combobox('disable');     
	$('#inoutDoor').combobox('disable');   
//	$('#wgCompanyId').combobox('disable');
//	$('#zzCompanyId').combobox('disable');
//	$('#jyCompanyId').combobox('disable');
//	$('#azCompanyId').combobox('disable');
//	$('#ywCompanyId').combobox('disable');
//	$('#zjCompanyId').combobox('disable');
//	$('#townshipStreets').combobox('disable');

	$('#manufactDate').datebox('disable');
	$('#nextInspectDate').datebox('disable');
	$('#inspectDate').datebox('disable');
	
	
	$(".fontShow").hide();
	$('#btn-save').hide();
	$('#btn-cancel').show();

	$('#imgpic').hide();      
	<%if(role != 2 && role != 1){%>   
	$('#imgpic2').bind("click",testDid2);  
	<%}%>
	
}

function colseWinDetail(){
	
	$("#myform input").css({border:'1px solid' });

	$('input').attr("disabled","");
	$('#eleType').combobox('enable');
	$('#inoutDoor').combobox('enable');
//	$('#wgCompanyId').combobox('enable');
//	$('#zzCompanyId').combobox('enable');
//	$('#jyCompanyId').combobox('enable');
//	$('#azCompanyId').combobox('enable');
//	$('#ywCompanyId').combobox('enable');
//	$('#zjCompanyId').combobox('enable');
//	$('#townshipStreets').combobox('enable');

	$('#manufactDate').datebox('enable');
	$('#nextInspectDate').datebox('enable');
	$('#inspectDate').datebox('enable');
	
	$(".fontShow").show();	
	$('#btn-save,#btn-cancel').show();  

	$('#imgpic').show();

	<%if(role != 2 && role != 1){%>
    $('#registNumberinfo').attr("readonly","readonly");
    $('#registCode').attr("readonly","readonly");
    $('#map_X').attr("readonly","readonly");
    $('#map_Y').attr("readonly","readonly");
    $('#imgpic2').unbind("click");
	<%}%>
	
}

function saveCar2(){ 
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){
     //   return $(this).form('validate');
		var registNumber=$('#registNumberinfo').attr("value");  
		var address= $('#address').attr("value"); 
		var registCode =$('#registCode').attr("value");
		var useNumber =$('#useNumber').attr("value");
		var safetyManDepart =$('#safetyManDepart').attr("value");
		var safetyManPerson =$('#safetyManPerson').attr("value");
		var safetyManPersonTel =$('#safetyManPersonTel').attr("value");
		var name =$('#name').attr("value");
		var eleMode =$('#eleMode').attr("value");
	//	var eleType =$('#eleType').attr("value");
		var eleStop =$('#eleStop').attr("value");
		var speed =$('#speed').attr("value");
		var eleLoad =$('#eleLoad').attr("value");
    //    var mobileCode =$('#mobileCode').attr("value");

        var eleType =$('#eleType').combobox('getValue');
   //     var wgCompanyId=$('#wgCompanyId').combobox('getValue'); 
        var wgCompanyId=$('#wgCompanyId').attr("value"); 
   //     var ywCompanyId=$('#ywCompanyId').combobox('getValue'); 
        var ywCompanyId=$('#ywCompanyId').attr("value"); 
  //      var zzCompanyId=$('#zzCompanyId').combobox('getValue'); 
        var zzCompanyId=$('#zzCompanyId').attr("value"); 
   //     var azCompanyId=$('#azCompanyId').combobox('getValue'); 
        var azCompanyId=$('#azCompanyId').attr("value"); 
  //      var jyCompanyId=$('#jyCompanyId').combobox('getValue'); 
        var jyCompanyId=$('#jyCompanyId').attr("value"); 
  //      var zjCompanyId=$('#zjCompanyId').combobox('getValue');
        var zjCompanyId=$('#zjCompanyId').attr("value"); 
 //       var townshipStreets=$('#townshipStreets').combobox('getValue'); 
        var townshipStreets=$('#townshipStreets').attr("value");
         
        var inoutDoor=$('#inoutDoor').combobox('getValue');
        var area=$('#area').combobox('getValue');

        var eleheight =$('#eleheight').attr("value");
        var elewidth  =$('#elewidth').attr("value");
        var inspector =$('#inspector').attr("value");

       
        if(opt==1){
        	if(oldRegistNumber != registNumber){
            	$('#registNumberinfo').attr("value",oldRegistNumber);
        		$.messager.alert('操作失败', '电梯编码不能修改', 'error');
        		return false;
            	}
            }
		/*
		if(opt==1){
			$('#qy_nameinfo').combobox('setValue',qy_id2);
		//	alert("----qyName---"+$('#qy_nameinfo').combobox('getValue'));
			opt=0;
			}  */
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
			

			if (!registNumber) {
				$.messager.alert('操作失败', '电梯编码不能为空', 'error');
				return false;
			}
			if (!address) {
				$.messager.alert('操作失败', '地址不能为空', 'error');
				return false;
			}

			if (!area) {
				$.messager.alert('操作失败', '所属区域不能为空', 'error');
				return false;
			}

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
	
	 $('#p').append("<iframe src='../../test3.jsp?longitude="+longitude+"&latitude="+latitude+"' width='100%' height='100%' scrolling='no'></iframe>");
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

  function companyinfo(pid){   
	  var id =pid;
	  jQuery.post('/tcweb/elevator/companyinfotishi',
		    	 {'id':id},
		    	 function(data){
		    		 companyinfowin.window('open');
		    		 $('#jdbCompanyName').attr("value",data.jdbCompanyName);
		    		 $('#wgCompanyName').attr("value",data.wgCompanyName);
		    		 $('#ywCompanyName').attr("value",data.ywCompanyName);
		    		 $('#zzCompanyName').attr("value",data.zzCompanyName);
		    		 $('#azCompanyName').attr("value",data.azCompanyName);
		    		 $('#jyCompanyName').attr("value",data.jyCompanyName); 
		    		 $('#zjCompanyName').attr("value",data.zjCompanyName);   
		    		 $('#inspector').attr("value",data.inspector);
		    		 $('#inspectDate').attr("value",data.inspectDate);
		    		},'json'
	    	        );
	  }

  function chushixialaliebiao(){
	  comb =$('#wgCompanyId').combobox({
			url:'/tcweb/elevator/getWgCompanyList',
		    valueField:'id',
		    textField:'companyName'
		}
		);

		$('#wgCompanyId').combobox({
	    	filter: function(q, row){
	    	//    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });

		comb2 =$('#ywCompanyId').combobox({
			url:'/tcweb/elevator/getYwCompanyList',
		    valueField:'id',
		    textField:'companyName'
		});

		$('#ywCompanyId').combobox({
	    	filter: function(q, row){
	    	//    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });

		comb3 =$('#zzCompanyId').combobox({
			url:'/tcweb/elevator/getZzCompanyList',
		    valueField:'id',
		    textField:'companyName'
		});

		$('#zzCompanyId').combobox({
	    	filter: function(q, row){
	    	//    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });
		

		comb4 =$('#azCompanyId').combobox({
			url:'/tcweb/elevator/getAzCompanyList',
		    valueField:'id',
		    textField:'companyName'
		});

		$('#azCompanyId').combobox({
	    	filter: function(q, row){
	    //	    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });
		

		comb5 =$('#jyCompanyId').combobox({
			url:'/tcweb/elevator/getJyCompanyList',
		    valueField:'id',
		    textField:'companyName'
		});


		$('#jyCompanyId').combobox({
	    	filter: function(q, row){
	    //	    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });

		comb6 =$('#zjCompanyId').combobox({
			url:'/tcweb/elevator/getZjCompanyList',
		    valueField:'id',
		    textField:'companyName'
		});

		$('#zjCompanyId').combobox({
	    	filter: function(q, row){
	    //	    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });
		    
		 comb8=$('#townshipStreets').combobox({
				url:'/tcweb/elevator/getjdbCompanyList',
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

		    chushixialaliebiaoNumber = 1;   //表示已经执行过初始化页面
	  }

     var zzId =0;
	 var azId =0;
	 var wgId =0;
	 var ywId =0;
	 var jyId =0;
	 var zjId =0;
	 var jdId =0;

	 var zzIdNum = 0;
	 var azIdNum = 0;
	 var wgIdNum = 0;
	 var ywIdNum = 0;
	 var jyIdNum = 0;
	 var zjIdNum = 0;
	 var jdIdNum = 0;

  function  bianjixiala(zzcompanyId,zzCompanyName,azcompanyId,azCompanyName,wgcompanyId,wgCompanyName,ywcompanyId,ywCompanyName,jycompanyId,jyCompanyName,zjcompanyId,zjCompanyName,jdcompanyId,jdCompanyName){
	  if(zzcompanyId >0)
	    { 
	      if(zzcompanyId != zzId){
	        zzxialafuzhi(zzcompanyId,zzCompanyName);
	   //   zzId = zzcompanyId;
	      }   
	    }
	  else{
		  $('#zzCompanyId2').attr("value","");
		  $('#zzCompanyId').attr("value",0);
		  }
	  
	  if(azcompanyId >0)
	    { 
	      if(azcompanyId != azId){
	        azxialafuzhi(azcompanyId,azCompanyName);
	   //     azId = azcompanyId;
	      }   
	    }
	  else{
		  $('#azCompanyId2').attr("value","");
		  $('#azCompanyId').attr("value",0);
		  }

	  if(wgcompanyId >0)
	    { 
	      if(wgcompanyId != wgId){   
	        wgxialafuzhi(wgcompanyId,wgCompanyName);
	      //  wgId = wgcompanyId;
	      }   
	    }
	  else{
		  $('#wgCompanyId2').attr("value","");
		  $('#wgCompanyId').attr("value",0);
		  }

	  if(ywcompanyId >0)
	    { 
	      if(ywcompanyId != ywId){
	        ywxialafuzhi(ywcompanyId,ywCompanyName);
	     //   ywId = ywcompanyId;
	      }   
	    }
	  else{
		  $('#ywCompanyId2').attr("value","");
		  $('#ywCompanyId').attr("value",0);
		  } 

	  if(jycompanyId >0)
	    { 
	      if(jycompanyId != jyId){
	        jyxialafuzhi(jycompanyId,jyCompanyName);
	   //     jyId = jycompanyId;
	      }   
	    }
	  else{
		  $('#jyCompanyId2').attr("value","");
		  $('#jyCompanyId').attr("value",0);
		  }
	  

	  if(zjcompanyId >0)
	    { 
	      if(zjcompanyId != zjId){
	        zjxialafuzhi(zjcompanyId,zjCompanyName);
	    //    zjId = zjcompanyId;
	      }   
	    }
	  else{
		  $('#zjCompanyId2').attr("value","");
		  $('#zjCompanyId').attr("value",0);
		  } 

	  if(jdcompanyId >0)
	    { 
	      if(jdcompanyId != jdId){
	        jdxialafuzhi(jdcompanyId,jdCompanyName);
	   //     jdId = jdcompanyId;
	      }   
	    }
	  else{
		  $('#townshipStreets2').attr("value","");
		  $('#townshipStreets').attr("value",0);
		  } 
	  
	 }

  function zzxialafuzhi(id,zzName){  
      /*
      if(zzIdNum == 0) {     
		 var zzcmbData =[{
	    	    "id":id,
	    	    "text":zzName
	    	}];                    
	    	$('#zzCompanyId').combobox({
				data:zzcmbData,
			    valueField:'id',
			    textField:'text'
			}); 
      } */
	  $('#zzCompanyId2').attr("value",zzName);
	  $('#zzCompanyId').attr("value",id);
   
	 }

	 function azxialafuzhi(id,azName){
		 /*
		 if(azIdNum == 0) {
		 var azcmbData =[{
	    	    "id":id,
	    	    "text":azName
	    	}];
	    	$('#azCompanyId').combobox({
				data:azcmbData,
			    valueField:'id',
			    textField:'text'
			}); 
		 } */
		 $('#azCompanyId2').attr("value",azName);
		 $('#azCompanyId').attr("value",id);
	 }

	 function wgxialafuzhi(id,wgName){
		 /*
		 if(wgIdNum == 0){
		 var wgcmbData =[{
	    	    "id":id,
	    	    "text":wgName
	    	}];
	    	$('#wgCompanyId').combobox({
				data:wgcmbData,
			    valueField:'id',
			    textField:'text'
			}); 
		 } */
		 $('#wgCompanyId2').attr("value",wgName);
		 $('#wgCompanyId').attr("value",id);
	 }

	 function ywxialafuzhi(id,ywName){
		 /*
		 if(ywIdNum == 0){
		 var ywcmbData =[{
	    	    "id":id,
	    	    "text":ywName
	    	}];
	    	$('#ywCompanyId').combobox({
				data:ywcmbData,
			    valueField:'id',
			    textField:'text'
			}); 
		 } */
		 $('#ywCompanyId2').attr("value",ywName);
		 $('#ywCompanyId').attr("value",id);
	 }

	 function jyxialafuzhi(id,jyName){
		 /*
		 if(jyIdNum == 0){
		 var jycmbData =[{
	    	    "id":id,
	    	    "text":jyName
	    	}];
	    	$('#jyCompanyId').combobox({
				data:jycmbData,
			    valueField:'id',
			    textField:'text'
			}); 
		 } */
		 $('#jyCompanyId2').attr("value",jyName);
		 $('#jyCompanyId').attr("value",id);
		 }

	 function zjxialafuzhi(id,zjName){
		 /*
		 if(zjIdNum == 0){
		 var zjcmbData =[{
	    	    "id":id,
	    	    "text":zjName
	    	}];
	    	$('#zjCompanyId').combobox({
				data:zjcmbData,
			    valueField:'id',
			    textField:'text'
			}); 
		 } */
		 $('#zjCompanyId2').attr("value",zjName);
		 $('#zjCompanyId').attr("value",id); 
		 } 
	

	 function jdxialafuzhi(id,jdName){
		 /*
		 if(jdIdNum == 0) {
		 var jdcmbData =[{
	    	    "id":id,
	    	    "text":jdName
	    	}];
	    	$('#townshipStreets').combobox({
				data:jdcmbData,
			    valueField:'id',
			    textField:'text'
			}); 
		 } */
		 $('#townshipStreets2').attr("value",jdName);
		 $('#townshipStreets').attr("value",id); 
		 } 

  function xialaSelect(id){   
    if(id ==1){
 	   $('#townshipStreets').combobox('clear');
 		$('#townshipStreets').combobox({
 			url:'/tcweb/elevator/getjdbCompanyList',
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

	 jdIdNum =1;
   }

    if(id ==2){
 	   $('#zzCompanyId').combobox('clear');
 		$('#zzCompanyId').combobox({
 			url:'/tcweb/elevator/getZzCompanyList',
 		    valueField:'id',
 		    textField:'companyName'
 		});

	    $('#zzCompanyId').combobox({
	    	filter: function(q, row){
	    	//    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });

	 zzIdNum =1;
   }

    if(id ==3){
 	   $('#jyCompanyId').combobox('clear');
 		$('#jyCompanyId').combobox({
 			url:'/tcweb/elevator/getJyCompanyList',
 		    valueField:'id',
 		    textField:'companyName'
 		});

	    $('#jyCompanyId').combobox({
	    	filter: function(q, row){
	    	//    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });
	 jyIdNum =1;
   }

    if(id ==4){
 	   $('#azCompanyId').combobox('clear');
 		$('#azCompanyId').combobox({
 			url:'/tcweb/elevator/getAzCompanyList',
 		    valueField:'id',
 		    textField:'companyName'
 		});

	    $('#azCompanyId').combobox({
	    	filter: function(q, row){
	    	//    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });

	 azIdNum = 1;
   }

    if(id ==5){
 	   $('#wgCompanyId').combobox('clear');
 		$('#wgCompanyId').combobox({
 			url:'/tcweb/elevator/getWgCompanyList',
 		    valueField:'id',
 		    textField:'companyName'
 		});

	    $('#wgCompanyId').combobox({
	    	filter: function(q, row){
	    	//    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });

	 wgIdNum = 1;
   }


    if(id ==6){
 	   $('#ywCompanyId').combobox('clear');
 		$('#ywCompanyId').combobox({
 			url:'/tcweb/elevator/getYwCompanyList',
 		    valueField:'id',
 		    textField:'companyName'
 		});

	    $('#ywCompanyId').combobox({
	    	filter: function(q, row){
	    	//    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });

	 ywIdNum = 1;
   }


    if(id ==7){
 	   $('#zjCompanyId').combobox('clear');
 		$('#zjCompanyId').combobox({
 			url:'/tcweb/elevator/getZjCompanyList',
 		    valueField:'id',
 		    textField:'companyName'
 		});

	    $('#zjCompanyId').combobox({
	    	filter: function(q, row){
	    	//    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });
	 ywIdNum = 1;
   }
  }


  function  GetEnt(){
	 var datainfo =[{ value: 0, text:'企业1'},{ value: 2, text:'企业1' }];
     return datainfo;
	 	 
  }    

  function gaojiquery(){
	  gjquerywin.window('open');

	  $('#gjform').form('load', '/tcweb/elevator/gjqueryedit');
    //  gjform.form('load', '/tcweb/elevator/gjqueryedit');
	//  gjform.url ='/tcweb/elevator/gjquery';
	  /*
	  qgrid=$('#qtt').datagrid({
		    title:'',
		    pageSize:10,
		    pageList:[10,20,25,30,35,40],
		    url:'/tcweb/elevator/queryconditionlist',
		    queryParams:{},
		    columns:[[
		        {field:'quname',title:'名称',width:60},
		        {field:'qswitch',title:'开关',width:50,editor:{
			        type:'combobox',                                         
			        options:{}                                     
                    }
                },   
		        {field:'qshow',title:'是否显示',width:50},
		        {field:'qindex',title:'顺序',width:50},
		        {field:'qconditions',title:'条件',width:50},
		        {field:'qcontent',title:'内容',width:50}
		    ]]
	
		}); */
	  }

  function savequery(){
	 gjquerywin.window('close');
	 if($('#fcheckbox').attr("checked")==true)
		addTr2('tab', -1); 
	  }
  /**
   * 为table指定行添加一行
   *
   * tab 表id
   * row 行数，如：0->第一行 1->第二行 -2->倒数第二行 -1->最后一行
   * trHtml 添加行的html代码
   *
   */
  function addTr(tab, row, trHtml){
     //获取table最后一行 $("#tab tr:last")
     //获取table第一行 $("#tab tr").eq(0)
     //获取table倒数第二行 $("#tab tr").eq(-2)
     var $tr=$("#"+tab+" tr").eq(row);
     if($tr.size()==0){
        alert("指定的table id或行数不存在！");
        return;
     }
     $tr.after(trHtml);
  }

   function addTr2(tab, row){
	    var trHtml="<tr><td align='right' nowrap>使用单位: </td><td nowrap><input id='wgCompanyIdinfo' name='wgCompanyIdinfo' size='12' /></td></tr>";
	    addTr(tab, row, trHtml);
	  }

 //  var textinputindex =0;
   var selectinputindex=0;
   var dateinputindex=0;
   var textinputname;

   function textByNameinput(textname){
	   textinputname = textname;
	   gjquerytextwin.window('open'); 
	   }

   function savetextByNameinput(){
	   var textv =$('#textinputinfo').attr("value");
	   eval("var inputObject =$('#"+textinputname+"')");
       inputObject.attr("value",textv);
       
       gjquerytextwin.window('close'); 

	   }
   /*
   function textinput(inputdex){
	   textinputindex =inputdex;
	   gjquerytextwin.window('open'); 
	   }

   
 
   function savetextinput(){
	   var textv =$('#textinputinfo').attr("value");
	   if(textinputindex == 1)
       $('#qregitstNumber').attr("value",textv);
       if(textinputindex == 2)
       $('#qregistCode').attr("value",textv);
       
       gjquerytextwin.window('close'); 

	   }
  */
   function selectinput(inputdex){
	   selectinputindex =inputdex;
	   gjqueryselectwin.window('open'); 
	   }
   

   function saveselectinput(){
	   var selectv =$('#areainputinfo').combobox('getValue');
	   if(selectinputindex == 1)
       $('#qarea').attr("value",selectv);
       
       
       gjqueryselectwin.window('close'); 

	   }

   function selectinput2(inputdex){
	   selectinputindex =inputdex;
	   gjqueryselect2win.window('open'); 
	   }

   function saveselect2input(){
	   var selectv =$('#qqywCompanyId2').attr("value");
	   if(selectinputindex == 1)
       $('#qywCompanyId2').attr("value",selectv);
       
       
       gjqueryselect2win.window('close'); 

	   }

   function dateinput(inputdex){
	   dateinputindex =inputdex;
	   gjquerydatewin.window('open'); 
	   }

   function savedateinput(){
	   var datev =$('#dateinfo').datebox("getValue");  
	   if(dateinputindex == 11)   
       $('#qinspectDate').attr("value",datev);
	   if(dateinputindex == 12)   
	   $('#qinspectDate2').attr("value",datev);
	   
	   gjquerydatewin.window('close'); 

	   }


   function gjinfoquery(){
	    var qregistNumber=$('#qregistNumber').attr("value");    
		var qregistCode =$('#qregistCode').attr("value");
		var qarea  =$('#qarea option:selected').val();
		var qbuildingName  =$('#qbuildingName ').attr("value"); 
		var qywCompanyId   = $('#qywCompanyId').attr("value");
	    var qmContractVdate=$('#qmContractVdate').datebox("getValue");
	    var qmContractVdate2=$('#qmContractVdate2').datebox("getValue"); 
	    var qinspectDate=$('#qinspectDate').datebox("getValue");
	    var qinspectDate2=$('#qinspectDate2').datebox("getValue");
	    var qeleType  =$('#qeleType option:selected').val();
	    var qregistCode2 =$('#qregistCode2').attr("value");
	    var qzzCompanyId=$('#qzzCompanyId').attr("value");
	    var qfactoryNum=$('#qfactoryNum').attr("value");
	    var qazCompanyId=$('#qazCompanyId').attr("value");
	    var quseDate=$('#quseDate').datebox("getValue");
	    var quseDate2=$('#quseDate2').datebox("getValue");
	    var qwgCompanyId=$('#qwgCompanyId').attr("value");
	    var quseNumber =$('#quseNumber').attr("value");    
	
	  grid.datagrid("options").url='/tcweb/elevator/gjquery';
	  //grid.datagrid("options").queryParams={'qregitstNumber':qregitstNumber,'qregistCode':qregistCode,'qarea':qarea,'qbuildingName':qbuildingName,'qywCompanyId':qywCompanyId,'qinspectDate':qinspectDate,'qinspectDate2':qinspectDate2,'qeleType':qeleType,'qregistCode2':qregistCode2,'qzzCompanyId':qzzCompanyId,'qfactoryNum':qfactoryNum,'qazCompanyId':qazCompanyId,'quseDate':quseDate,'qquseDate2':qquseDate2,'qwgCompanyId':qwgCompanyId,'quseNumber':quseNumber};
	   grid.datagrid("options").queryParams={'qregistNumber':qregistNumber,'qregistCode':qregistCode,'qarea':qarea,'qbuildingName':qbuildingName,'qywCompanyId':qywCompanyId,'qmContractVdate':qmContractVdate,'qmContractVdate2':qmContractVdate2,'qinspectDate':qinspectDate,'qinspectDate2':qinspectDate2,'qeleType':qeleType,'qregistCode2':qregistCode2,'qzzCompanyId':qzzCompanyId,'qfactoryNum':qfactoryNum,'qazCompanyId':qazCompanyId,'quseDate':quseDate,'quseDate2':quseDate2,'qwgCompanyId':qwgCompanyId,'quseNumber':quseNumber};
	    $('#tt').datagrid('reload');
	}


  function gjinfoclearQuery(){
	  $('#qregistNumber').attr("value","");
	  $('#qregistCode').attr("value","");
	  $('#qarea option:first').attr('selected','selected');
	  $('#qbuildingName').attr("value","");
	  $('#qywCompanyId2').attr("value","");
	  $('#qywCompanyId').attr("value",0);
	  $("#qmContractVdate").datebox("setValue","");  
	  $("#qmContractVdate2").datebox("setValue","");
	  $("#qinspectDate").datebox("setValue","");  
	  $("#qinspectDate2").datebox("setValue","");  
	  $('#qeleType option:first').attr('selected','selected');
	  $("#qregistCode2").attr("value","");
	  $('#qzzCompanyId2').attr("value","");
	  $('#qzzCompanyId').attr("value",0);
	  $("#qfactoryNum").attr("value","");
	  $('#qazCompanyId2').attr("value","");
	  $('#qazCompanyId').attr("value",0);
	  $("#quseDate").datebox("setValue","");  
	  $("#quseDate2").datebox("setValue",""); 
	  $('#qwgCompanyId2').attr("value","");
	  $('#qwgCompanyId').attr("value",0);
	  $("#quseNumber").attr("value","");
	    
	  }

  function gjquerywinsave(){
	  var qregistNumber=$('#qregistNumber').attr("value");    
		var qregistCode =$('#qregistCode').attr("value");
		var qarea  =$('#qarea option:selected').val();
		var qbuildingName  =$('#qbuildingName ').attr("value"); 
		var qywCompanyId   = $('#qywCompanyId').attr("value");
	    var qmContractVdate=$('#qmContractVdate').datebox("getValue");
	    var qmContractVdate2=$('#qmContractVdate2').datebox("getValue"); 
	    var qinspectDate=$('#qinspectDate').datebox("getValue");
	    var qinspectDate2=$('#qinspectDate2').datebox("getValue");
	    var qeleType  =$('#qeleType option:selected').val();
	    var qregistCode2 =$('#qregistCode2').attr("value");
	    var qzzCompanyId=$('#qzzCompanyId').attr("value");
	    var qfactoryNum=$('#qfactoryNum').attr("value");
	    var qazCompanyId=$('#qazCompanyId').attr("value");
	    var quseDate=$('#quseDate').datebox("getValue");
	    var quseDate2=$('#quseDate2').datebox("getValue");
	    var qwgCompanyId=$('#qwgCompanyId').attr("value");
	    var quseNumber =$('#quseNumber').attr("value");    
	
	$('#gjform').form('submit', {  
			url:'/tcweb/elevator/gjquerysave',
			onSubmit:function(){
				return true;
				
			}
	    });
  }

  function OpenGetShowColumnDlg() {
	    $('#GetShowColumn').dialog('open').dialog('setTitle', '设置显示隐藏列');
	}

  function hideColumn() {
	    var cbx = $("#GetShowColumn_Form input[type='checkbox']"); //获取Form里面是checkbox的Object
	    var checkedValue = "";
	    var unCheckValue = "";
	    for (var i = 0; i < cbx.length; i++) {
	        if (cbx[i].checked) {//获取已经checked的Object
	            if (checkedValue.length > 0) {   
	                checkedValue += "," + cbx[i].value.substring(2); //获取已经checked的value
	            }
	            else {
	                checkedValue = cbx[i].value.substring(2);
	            }
	        }
	        if (!cbx[i].checked) {//获取没有checked的Object
	            if (unCheckValue.length > 0) {
	                unCheckValue += "," + cbx[i].value.substring(2); //获取没有checked的value
	            }
	            else {
	                unCheckValue = cbx[i].value.substring(2);
	            }
	        }
	    }
	    var checkeds = new Array();
	    if (checkedValue != null && checkedValue != "") {   
	        checkeds = checkedValue.split(',');    
	        for (var i = 0; i < checkeds.length; i++) {
	            $('#tt').datagrid('showColumn', checkeds[i]); //显示相应的列
	        }
	    }
	    var unChecks = new Array();
	    if (unCheckValue != null && unCheckValue != "") {
	        unChecks = unCheckValue.split(',');
	        for (var i = 0; i < unChecks.length; i++) {
	            $('#tt').datagrid('hideColumn', unChecks[i]); //隐藏相应的列
	        }
	    }
	    $('#GetShowColumn').dialog('close');
	}

//全选
  function ChooseAll() {
      $("#GetShowColumn_Form input[type='checkbox']").attr("checked", "checked");
  }
  //取消全选
  function ClearAll() {
      $("#GetShowColumn_Form input[type='checkbox']").removeAttr("checked", "checked");
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
<body class="easyui-layout">
<div region="north" style="overflow:hidden"> 
   
    <fieldset id="addDiv" style="margin:0px"><legend>查询条件</legend>
    <table id="tab"> 
    <tr>      
    <td align="right" nowrap>电梯编号：</td> 
    <td nowrap><input id="registNumber" name="registNumber" size="12" class="easyui-validatebox"></input></td>
    <td align="right" nowrap>地址：</td> 
    <td nowrap><input id="addressinfo" name="addressinfo" size="24" class="easyui-validatebox"></input></td>
    <td nowrap>注册代码：</td>
    <td nowrap><input id="registCodeinfo" name="registCodeinfo" size="24" class="easyui-validatebox"></input></td>
    </tr>  
   <tr>  
   <td nowrap>所在区域：</td>
   <td> 
   <select id="areainfo"   name="areainfo" style="width:100px;" onchange="getCompanyListByArea()">
   <option value=""></option>
   <option value="锦江">锦江</option>
   <option value="青羊">青羊</option>
   <option value="金牛">金牛</option>
   <option value="成华">成华</option>
   <option value="武侯">武侯</option>
   <option value="高新">高新</option>
   <option value="天府新">天府新</option>
   <option value="青白江">青白江</option>
   <option value="龙泉驿">龙泉驿</option>
   <option value="新都">新都</option> 
   <option value="温江">温江</option> 
   <option value="双流">双流</option> 
   <option value="郫县">郫县</option> 
   <option value="金堂">金堂</option>
   <option value="大邑">大邑</option>
   <option value="蒲江">蒲江</option>
   <option value="新津">新津</option>
   <option value="都江堰">都江堰</option>
   <option value="崇州">崇州</option>
   <option value="邛崃">邛崃</option>
   <option value="彭州">彭州</option>
   <!--  <option value="安仁">安仁</option> -->
   <!-- <option value="大丰">大丰</option>  -->
</select>
</td>
 <td nowrap>维保单位：</td>
   <td> 
  <!--  <select id="ywCompanyIdinfo"  class="easyui-combobox" name="ywCompanyIdinfo" style="width:152px;"> </select>-->
  <input id="ywCompanyIdinfo" style="width:152px;">
  <input type ="hidden" id="ywCompanyIdinfo2">
 </td>
 <td align="right" nowrap>内部编号：</td>
   <td nowrap><input id="useNumberInfo" name="useNumberName" size="24" class="easyui-validatebox"></input></td>
  
 <td colspan="2">
				<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 
				<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="gaojiquery()">高级</a> 
			<!--		<input type="submit" value="Submit Comment" /> -->						
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
<div id="car-window2" title="详细信息" style="width:750px;height:500px;"> 
  <div style="padding:20px 20px 40px 80px;">   
  <form method="post" id="myform">    
  <table>
  <tr>
  <td colspan="4"><font color="red">1.灰色背景的选项不能修改</font>
  </td>
  </tr>
  <tr>
  <td colspan="4"><font color="red">2.蓝色背景的选项均需要输入至少两个关键字从下拉列表中选择</font>
  </td>
  </tr>
  <tr>
  <td colspan="4"><font color="red">3."所在栋","所在单元"栏，只有一个就是一幢一单元</font>
  </td>
  </tr> 
  <tr>
   <td colspan="4"><hr></td>
   </tr>   
   <tr>      
   <td width="70">电梯编号：</td>      
   <td><input id="registNumberinfo" name="registNumber" style="background-color:#CCCCCC"></input><span class="fontShow"><font color="red">*</font></span></td>  
    <td>注册代码：</td>      
   <td><input id="registCode" name="registCode" style="background-color:#CCCCCC"></input></td>
   </tr>
   <tr> 
    <td>地址：</td>      
   <td><input id="address" name="address"></input><span class="fontShow"><font color="red">*</font></span></td> 
   <td>电梯名称：</td>      
   <td><input id="name" name="name"></input></td> 
   </tr>
   <tr> 
    <td>所属区域：</td>      
  <!-- <td><input id="area" name="area"></input>  --> 
   <td><select id="area"  class="easyui-combobox" name="area" style="width:152px;">
   <option value="锦江">锦江</option>
   <option value="青羊">青羊</option>
   <option value="金牛">金牛</option>
   <option value="成华">成华</option>
   <option value="武侯">武侯</option>
   <option value="高新">高新</option>
   <option value="天府新">天府新</option>
   <option value="青白江">青白江</option>
   <option value="龙泉驿">龙泉驿</option>
   <option value="新都">新都</option> 
   <option value="温江">温江</option> 
   <option value="双流">双流</option> 
   <option value="郫县">郫县</option> 
   <option value="金堂">金堂</option>
   <option value="大邑">大邑</option>
   <option value="蒲江">蒲江</option>
   <option value="新津">新津</option>
   <option value="都江堰">都江堰</option>
   <option value="崇州">崇州</option>
   <option value="邛崃">邛崃</option>
   <option value="彭州">彭州</option>
   <!-- <option value="安仁">安仁</option> -->
   <!-- <option value="大丰">大丰</option> -->
</select><span class="fontShow"><font color="red">*</font></span></td> 
   <td>所在乡镇/街道：</td>      
   <!-- <td><input id="" name="townshipStreets"></input></td>  -->
     <td>
  <!--   <select id="townshipStreets"  class="easyui-combobox" name="townshipStreets" style="width:102px;"></select>  -->
  <input id="townshipStreets2" style="background-color:#87CEEB"><input type ="hidden" id="townshipStreets" name="townshipStreets">
     <span class="fontShow"><font color="red">*</font></span>
  <!--   <a href="javascript:xialaSelect(1)">修改</a>  -->
     </td>
   </tr>
    <tr> 
    <td>楼盘名称：</td>      
   <td><input id="buildingName" name="buildingName"></input></td> 
   <td colspan="2">      
  <!--  经度：<span style="padding-left:62px"><input id="coordinates" name="coordinates" style="width:100px;"></input></span><br> -->
        经度：<span style="padding-left:62px"><input id="map_X" name="map_X" style="width:100px;background-color:#CCCCCC"></input></span><br> 
        纬度：<span style="padding-left:62px"><input id="map_Y" name="map_Y" style="width:100px;background-color:#CCCCCC"></input></span>
   <img id="imgpic2"  name="imgpic2" src="../../images/html.png" style="cursor: hand"/>
   </td>
   </tr>
    <tr> 
    <td>所在栋：</td>      
   <td><input id="building" name="building" class="easyui-validatebox" data-options="required:true,validType:'length[1,15]'"></input></td> 
   <td>所在单元：</td>      
   <td><input id="unit" name="unit" class="easyui-validatebox" data-options="required:true,validType:'length[1,15]'"></input></td>
   </tr>
   <tr>
   <td>电梯类别：</td>      
   <td> <!--  <input id="eleType" name="eleType"></input>-->
   <select id="eleType"  class="easyui-combobox" name="eleType" style="width:152px;">
    <option value="客梯">客梯</option>
    <option value="货梯">货梯</option>
    <option value="扶梯">扶梯</option>  
    <option value="观光梯">观光梯</option>
    <option value="消防梯">消防梯</option>  
    <option value="服务梯">服务梯</option>    
</select><span class="fontShow"><font color="red">*</font></span>
   </td>
   <td>室内外：</td>      
   <td> 
   <select id="inoutDoor"  class="easyui-combobox" name="inoutDoor" style="width:152px;">
    <option value="室内">室内</option>
    <option value="室外">室外</option>  
</select><span class="fontShow"><font color="red">*</font></span>
   </td>
   </tr>
   <tr>
    
   </tr>
    <tr>
   <td>登记编号：</td>      
   <td><input id="registCode2" name="registCode2"></input></td>
   <td>注册登记人员：</td>      
   <td><input id="registor" name="registor"></input></td>
   </tr>
   <tr>
   <td colspan="4"><hr></td>
   </tr>
   <tr>
    <td>产权单位：</td>
    <td><input id="propertyRightsUnit" name="propertyRightsUnit"></input></td> 
   </tr>
    <tr>      
   <td>制造单位：</td>      
   <td>
   <!-- <select id="zzCompanyId"  class="easyui-combobox" name="zzCompanyId" style="width:102px;"></select>  -->
   <input id="zzCompanyId2" style="background-color:#87CEEB"><input type ="hidden" id="zzCompanyId" name="zzCompanyId">
   <span class="fontShow"><font color="red">*</font></span>
   <!--  <a href="javascript:xialaSelect(2)">修改</a> -->
   </td>
      <td>出厂编号：</td>      
  <td><input id="factoryNum" name="factoryNum"></input></td>  
   </tr>
  
    <tr>      
   <td>制造日期：</td>      
   <td><select id="manufactDate"  class="easyui-datebox" name="manufactDate" style="width:152px;"> 
</select></td>
   
   </tr> 
   <tr> 
<td>检验单位：</td>      
   <td>
   <!--<select id="jyCompanyId"  class="easyui-combobox" name="jyCompanyId" style="width:102px;"></select> -->
   <input id="jyCompanyId2" style="background-color:#87CEEB"><input type ="hidden" id="jyCompanyId" name="jyCompanyId">
   <span class="fontShow"><font color="red">*</font></span>
    <!--  <a href="javascript:xialaSelect(3)">修改</a> -->
   </td>
 <td>检验人员：</td>      
   <td><input id="inspector" name="inspector"></input></td>
   </tr>
   <tr>
   <td>检验类别：</td>      
   <td><input id="checkCategory" name="checkCategory"></input></td>
   <td>检验结论：</td>      
   <td><input id="checkResult" name="checkResult"></input></td>
   </tr>
   <tr>
     <td>检验日期：</td>      
   <td><input id="inspectDate"  type="text" class="easyui-datebox" name="inspectDate" style="width:152px;"></input></td>
    <td>下次检验日期：</td>      
   <td><input id="nextInspectDate"  type="text" class="easyui-datebox" name="nextInspectDate" style="width:152px;"></input></td>
   </tr>
   <tr>
   <td>报告编号：</td>      
   <td><input id="checkReportNum" name="checkReportNum"></input></td>
   </tr>
   <tr>
     <td>安装单位：</td>      
   <td>
    <!--<select id="azCompanyId"  class="easyui-combobox" name="azCompanyId" style="width:102px;"></select>-->
   <input id="azCompanyId2" style="background-color:#87CEEB"><input type ="hidden" id="azCompanyId" name="azCompanyId">
   <span class="fontShow"><font color="red">*</font></span>
    <!--<a href="javascript:xialaSelect(4)">修改</a>-->
   </td>
<td>投用日期：</td>      
   <td><input id="useDate" name="useDate"></input></td>
   </tr>
   <tr>
   <td>竣工验收日期：</td>      
   <td><input id="completeAcceptanceDate"  type="text" class="easyui-datebox" name="completeAcceptanceDate" style="width:152px;"></input></td>
<td>验收检验机构：</td>      
   <td><input id="acceptanceDateDepart" name="acceptanceDateDepart"></input></td>
   </tr>
   <tr>
   <td>验收报告编号：</td>      
   <td><input id="acceptanceReportNum" name="acceptanceReportNum"></input></td>
   </tr>
     <tr>
   <td>使用单位：</td>      
  <!--  <td><input id="qy_nameinfo" name="qy_name"></input><span class="fontShow"><font color="red">*</font></span></td>   -->  
   <td>
 <!--  <select id="wgCompanyId"  class="easyui-combobox" name="wgCompanyId" style="width:102px;"></select> -->
 <input id="wgCompanyId2" style="background-color:#87CEEB"><input type ="hidden" id="wgCompanyId" name="wgCompanyId">
<span class="fontShow"><font color="red">*</font></span>
<!--  <a href="javascript:xialaSelect(5)">修改</a> -->
 </td> 
    <td>单位内部编号：</td>      
   <td><input id="useNumber" name="useNumber"></input></td> 
   </tr>
   <tr>
   <td>安全管理部门：</td>
   <td><input id="safetyManDepart" name="safetyManDepart"></input></td>
   </tr>
    <tr>
   <td>安全管理人员：</td>
   <td><input id="safetyManPerson" name="safetyManPerson" class="easyui-validatebox" data-options="required:true,validType:'length[1,20]'"></input></td>
   <td>安全管理人员电话：</td>
   <td><input id="safetyManPersonTel" name="safetyManPersonTel" class="easyui-validatebox" data-options="required:true,validType:'length[1,30]'"></input></td>
   </tr>
   <tr>
   <td>维保单位：</td>      
   <td>
 <!--  <select id="ywCompanyId"  class="easyui-combobox" name="ywCompanyId" style="width:102px;"></select>  -->
   <input id="ywCompanyId2" style="background-color:#87CEEB"><input type ="hidden" id="ywCompanyId" name="ywCompanyId">
   <span class="fontShow"><font color="red">*</font></span>
 <!--   <a href="javascript:xialaSelect(6)">修改</a>  -->
   </td>
  <td>维保合同有效期：</td>
  <td><input id="mContractVdate"  type="text" class="easyui-datebox" name="mContractVdate" style="width:152px;"></input></td>
   </tr>
   <tr>
   <td>维保人员 <img id="imgpic3"  name="imgpic3" src="../../images/html.png" style="cursor: hand"/>
   </td>
   </tr>
   <tr>
   <td>质监单位：</td>      
   <td>
   <!--  <select id="zjCompanyId"  class="easyui-combobox" name="zjCompanyId" style="width:102px;"></select>  -->
   <input id="zjCompanyId2" style="background-color:#87CEEB"><input type ="hidden" id="zjCompanyId" name="zjCompanyId">
   <span class="fontShow"><font color="red">*</font></span>
    <!-- <a href="javascript:xialaSelect(7)">修改</a>  -->
   </td>
   </tr>
   <tr>
   <td>变更承办单位：</td>
    <td><input id="handleCompany" name="handleCompany"></input></td>
   <td>变更承办单位代码</td>
   <td><input id="handleCompanyCode" name="handleCompanyCode"></input></td>
   </tr>
   <tr>
   <td>变更方式：</td>
   <td><input id="changeWay" name="changeWay"></input></td>
   </tr>
   <tr>
   <td colspan="4"><hr></td>
   </tr>
   <tr>
    <td>电梯型号：</td>      
   <td><input id="eleMode" name="eleMode"></input></td> 
   </tr>
   <tr>
   <td>额定荷载：</td>      
   <td><input id="eleLoad" name="eleLoad"></input></td>
   <td>速度：</td>      
   <td><input id="speed"  name="speed"></input></td>
   </tr>
    <tr>      
   <td>提升高度：</td>      
   <td><input id="eleheight" name="eleheight"></input></td> 
    <td>宽度：</td> 
   <td><input id="elewidth" name="elewidth"></input></td>
   </tr>
    <tr>  
     <td>层站：</td>      
   <td><input id="eleStop" name="eleStop"></input></td>    
   
  <!-- <td>手机唯一码：</td>  -->     
  <!--   <td><input id="mobileCode" name="mobileCode"></input></td>  -->
  <td></td>
  <td></td>
   </tr>  
   <tr>
   <td colspan="4"><hr></td>
   </tr>
   <tr>
    <td>黑匣子设备：</td>      
   <td><input id="deviceId" name="deviceId"></input>
  <img id="imgpic"  name="imgpic" src="../../images/search2.gif" style="cursor: hand" />
   </td> 
   <td>备注：</td> 
   <td><input id="note" name="note"></input></td>
   </tr>
   <tr>
    <td>微信码：</td>      
   <td><input id="deviceId2" name="deviceId2"></input></td></tr>
   </table>
   <table width=70%>
    <tr>
    <td align="center">
      <a href="javascript:void(0)" onclick="saveCar2()" id="btn-save" icon="icon-save">保存</a>  
     <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">关闭</a> 
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
    <div id="p" class="easyui-window" title="地图信息" closed="true" style="width:600px;height:480px;padding:5px;overflow:hidden">   
    </div>  
    
   <div id="car-window8" title="变更详细信息" style="width:780px;height:550px;"> 
   <div style="padding:20px 10px 40px 10px;">
   
   <div id="bb" class="easyui-accordion">
   <div style="margin-top:1px;" title="变更信息列表">  
       <table id="ttmap"></table>
   </div> 
   </div>
   
   
   <div id="aa" class="easyui-accordion" style="border-top:0px;">
   <div title="维保信息">  
        <table id="ttmap2"></table>
    </div>    
   </div>  
    
   </div>
   </div>   

<div id="companyinfo-window" title="公司信息" style="width:470px;height:460px;overflow-x:auto;overflow-y:hidden">
 <table>
  <tr>      
  <td nowrap>街道办：</td>
  <td><input  id="jdbCompanyName" name="jdbCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
  <td nowrap> 使用单位：</td>
   <td><input  id="wgCompanyName" name="wgCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   <tr>      
   <td nowrap>维保单位：</td>
   <td><input id="ywCompanyName" name="ywCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
   <td nowrap>制造单位：</td>
   <td><input  id="zzCompanyName" name="zzCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   <tr>
   <td nowrap>安装单位：</td>
   <td><input  id="azCompanyName" name="azCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
   <td nowrap>质监单位：</td>
    <td><input  id="zjCompanyName" name="zjCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
    <tr>
    <td nowrap>检验单位：</td>
   <td><input  id="jyCompanyName" name="jyCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   <tr>
    <td nowrap>检验人员：</td>
   <td><input  id="inspector" name="inspector" style="border-width :0px 0px 1px;" readonly></input></td>
   <td nowrap>检验日期：</td>
   <td><input  id="inspectDate" name="inspectDate" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   
   
   
 </table>
</div>

<div id="gjquery-window" title="高级查询" style="width:420px;height:460px;overflow-x:hidden;overflow-y:auto">
     <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'north'" style="height:80px">  
       <table id="qtt"> 
       <tr>
    <td align="left">
    <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="gjinfoquery()">查询</a>   
    <a href="#" class="easyui-linkbutton" icon="icon-no" onclick="gjinfoclearQuery()">清空</a>  
    </td>
    </tr>
    <tr>
    <td colspan ="10"><font color="red">蓝色背景得项需要输入2个关键字从下拉列表中选择</font></td>
    </tr>
    <tr>
    <td colspan ="10"><hr></td>
    </tr>
     
        <tr>  
            <td>字段名称</td>  
            <td>查询条件</td>  
        </tr>  
        </table>
        </div>
         <div data-options="region:'center'" style="overflow-x:hidden;overflow-y:auto">
         <form id="gjform">
         <table>
        <tr>  
            <td>电梯编号</td><td><input  id="qregistNumber" name="qregistNumber" style="width:100%"></input></td>
        </tr>  
        <tr>  
            <td>注册代码</td><td><input  id="qregistCode" name="qregistCode" style="width:100%"></input></td>
        </tr>
        <tr>  
            <td>行政区划</td>
            <td>
   <select id="qarea"  name="qarea" style="width:100%;">
   <option value=""></option>
   <option value="锦江">锦江</option>
   <option value="青羊">青羊</option>
   <option value="金牛">金牛</option>
   <option value="成华">成华</option>
   <option value="武侯">武侯</option>
   <option value="高新">高新</option>
   <option value="天府新">天府新</option>
   <option value="青白江">青白江</option>
   <option value="龙泉驿">龙泉驿</option>
   <option value="新都">新都</option> 
   <option value="温江">温江</option> 
   <option value="双流">双流</option> 
   <option value="郫县">郫县</option> 
   <option value="金堂">金堂</option>
   <option value="大邑">大邑</option>
   <option value="蒲江">蒲江</option>
   <option value="新津">新津</option>
   <option value="都江堰">都江堰</option>
   <option value="崇州">崇州</option>
   <option value="邛崃">邛崃</option>
   <option value="彭州">彭州</option>
   </select>
 </td>
        </tr>
         <tr>  
            <td>楼盘名称</td><td><input  id="qbuildingName" name="qbuildingName" style="width:100%"></input></td>
        </tr> 
        <tr>  
            <td>维保单位</td><td><input  id="qywCompanyId2" name="qywCompanyId2" style="background-color:#87CEEB;width:100%"></input><input type="hidden" id="qywCompanyId" name="qywCompanyId" value="0"></input></td>
        </tr>
        <tr>  
            <td>维保合同有效期</td><td>从<input  id="qmContractVdate" name="qmContractVdate"  type="text" class="easyui-datebox" data-options="editable:false"></input>到<input  id="qmContractVdate2" name="qmContractVdate2" type="text" class="easyui-datebox" data-options="editable:false"></input></td>
        </tr>
        <tr>  
            <td>检验日期</td><td>从<input  id="qinspectDate" name="qinspectDate"  type="text" class="easyui-datebox" data-options="editable:false"></input>到<input  id="qinspectDate2" name="qinspectDate2" type="text" class="easyui-datebox" data-options="editable:false"></input></td>
        </tr>
         
        <tr>  
            <td>电梯类别</td><td><select id="qeleType"  name="qeleType" style="width:100%;">
    <option value=""></option>
    <option value="曳引与强制驱动电梯">曳引与强制驱动电梯</option>
    <option value="曳引驱动乘客电梯">曳引驱动乘客电梯</option>
    <option value="曳引驱动载货电梯">曳引驱动载货电梯</option>  
    <option value="强制驱动载货电梯">强制驱动载货电梯</option>
    <option value="液压驱动电梯">液压驱动电梯</option>  
    <option value="液压乘客电梯">液压乘客电梯</option>
    <option value="液压载货电梯">液压载货电梯</option> 
    <option value="自动扶梯与自动人行道">自动扶梯与自动人行道</option>
    <option value="自动扶梯">自动扶梯</option>
    <option value="自动人行道">自动人行道</option>
    <option value="其它类型电梯">其它类型电梯</option>
    <option value="防爆电梯">防爆电梯</option>
    <option value="消防员电梯">消防员电梯</option>
    <option value="杂物电梯">杂物电梯</option>
</select></td>
        </tr>
        <tr>  
            <td>登记编号</td><td><input  id="qregistCode2" name="qregistCode2" style="width:100%;"></input></td>
        </tr> 
        <tr>  
            <td>制造单位</td><td><input id="qzzCompanyId2" style="background-color:#87CEEB;width:100%"><input type ="hidden" id="qzzCompanyId" name="qzzCompanyId" value="0"></td>
        </tr>
         <tr>  
            <td>出厂编号</td><td><input  id="qfactoryNum" name="factoryNum" style="width:100%"></input></td>
        </tr>
        <tr>  
            <td>安装单位</td><td> <input id="qazCompanyId2" style="background-color:#87CEEB;width:100%"><input type ="hidden" id="qazCompanyId" name="qazCompanyId" value="0">
  </td>
        </tr>
         <tr>  
            <td>投用日期</td><td>从<input  id="quseDate" name="quseDate"  type="text" class="easyui-datebox" data-options="editable:false"></input>到<input  id="quseDate2" name="quseDate2" type="text" class="easyui-datebox" data-options="editable:false"></input></td>
        </tr>
        <tr>  
            <td>使用单位</td><td><input id="qwgCompanyId2" style="background-color:#87CEEB;width:100%"><input type ="hidden" id="qwgCompanyId" name="qwgCompanyId" value="0"></td>
        </tr>  
       
        <tr>  
            <td>内部编号</td><td><input  id="quseNumber" name="quseNumber" style="width:100%"></input></td>
        </tr> 
      
        
        
   
       </table>
        </form> 
   </div>  
 </div>
 </div>
 
 <div id="textinput-window" title="文本内容查询" style="width:400px;height:200px;overflow-x:hidden;overflow-y:auto">
 <div class="easyui-layout" data-options="fit:true">
 <div data-options="region:'north'" style="height:100px">
 <table>
 <tr>
 <td>
 <textarea  id ="textinputinfo" rows="6" cols="58" style="border:0px"></textarea>
 </td>
 </tr>
 </table>
 </div>
 
 <div data-options="region:'center'" style="overflow-x:auto;overflow-y:hidden"> 
     <table width="100%">
      <tr>
       <td align="right">
      <a href="javascript:void(0)" onclick="savetextByNameinput()" id="textbtn-save" icon="icon-save">保存</a></td>
     </tr>
 </table>
 </div>
</div>
</div>

 
 <div id="selectinput-window" title="下拉列表容查询" style="width:400px;height:200px;overflow-x:hidden;overflow-y:auto">
  <div class="easyui-layout" data-options="fit:true">
   <div data-options="region:'north'" style="height:100px">
 <table>
 <tr>
 <td>
 <select id="areainputinfo"  class="easyui-combobox" name="area" style="width:152px;">
   <option value="锦江">锦江</option>
   <option value="青羊">青羊</option>
   <option value="金牛">金牛</option>
   <option value="成华">成华</option>
   <option value="武侯">武侯</option>
   <option value="高新">高新</option>
   <option value="天府新">天府新</option>
   <option value="青白江">青白江</option>
   <option value="龙泉驿">龙泉驿</option>
   <option value="新都">新都</option> 
   <option value="温江">温江</option> 
   <option value="双流">双流</option> 
   <option value="郫县">郫县</option> 
   <option value="金堂">金堂</option>
   <option value="大邑">大邑</option>
   <option value="蒲江">蒲江</option>
   <option value="新津">新津</option>
   <option value="都江堰">都江堰</option>
   <option value="崇州">崇州</option>
   <option value="邛崃">邛崃</option>
   <option value="彭州">彭州</option>
   </select>
 </td>
 </tr>
  </table>
 </div>
 
 <div data-options="region:'center'" style="overflow-x:auto;overflow-y:hidden"> 
   <table width="100%">
      <tr>
      <td align="right">
      <a href="javascript:void(0)" onclick="saveselectinput()" id="selectbtn-save" icon="icon-save">保存</a>
      </td>
     </tr>
 </table>
 </div>
 </div>
 </div>
 
 <div id="selectinput2-window" title="下拉列表容查询" style="width:400px;height:200px;overflow-x:hidden;overflow-y:auto">
<div class="easyui-layout" data-options="fit:true">
 <div data-options="region:'north'" style="height:100px">
 <table>
 <tr>
 <td>
 <input id="qqywCompanyId2" style="background-color:#87CEEB;width:100%">
 </td>
 </tr>
 </table>
 </div>
<div data-options="region:'center'" style="overflow-x:auto;overflow-y:hidden"> 
   <table width="100%">
      <tr>
      <td align="right">
      <a href="javascript:void(0)" onclick="saveselect2input()" id="select2btn-save" icon="icon-save">保存</a></td>
 </tr>
 </table>
 </div>
 </div>
 </div>
 
 <div id="dateinput-window" title="日期选择" style="width:200px;height:200px;overflow-x:hidden;overflow-y:auto">
 <table>
 <tr>
 <td>
 <input id="dateinfo"  type="text" class="easyui-datebox" name="inspectDate" style="width:152px;"></input></td>
 </tr>
 <tr>
 <td align="center">
      <a href="javascript:void(0)" onclick="savedateinput()" id="datebtn-save" icon="icon-save">保存</a></td>
 </tr>
 </table>
 </div>
 
       <div id="GetShowColumn" class="easyui-dialog" style="width: 520px; height: 350px;
            padding: 10px 20px" data-options="closed:true,buttons:'#dlg-GetShowColumn',modal:true">
            <form id="GetShowColumn_Form" method="post" novalidate>
            <input type="hidden" id="hideValues" />
            <input type="hidden" id="showValues" />
            <div class="fitem">
                <input type="checkbox" name="qiregistNumber" id="qiregistNumber" value="qiregistNumber" /><label
                    for="registNumber">编号</label>
            </div>
            <div class="fitem">
                <input type="checkbox" name="qiregistCode" id="qiregistCode" value="qiregistCode" /><label
                    for="registCode">注册代码</label>  
            </div>
            <div class="fitem">
                <input type="checkbox" name="qiaddress" id="qiaddress" value="qiaddress" /><label
                    for="address">地址</label>  
            </div>
            <div class="fitem">
                <input type="checkbox" name="qibuildingName" id="qibuildingName" value="qibuildingName" /><label
                    for="buildingName">楼盘名称</label>  
            </div>
            <div class="fitem">
                <input type="checkbox" name="qiarea" id="qiarea" value="qiarea" /><label
                    for="area">行政区划</label>  
            </div>
            <div class="fitem">
                <input type="checkbox" name="qiuseNumber" id="qiuseNumber" value="qiuseNumber" /><label
                    for="useNumber">单位内部编号</label>  
            </div>
             <div class="fitem">
                <input type="checkbox" name="qinextInspectDate" id="qinextInspectDate" value="qinextInspectDate" /><label
                    for="nextInspectDate">下次检验日期</label>  
            </div>
             <div class="fitem">
                <input type="checkbox" name="qiwgCompanyName" id="qiwgCompanyName" value="qiwgCompanyName" /><label
                    for="wgCompanyName">使用单位</label>  
            </div>
            <div class="fitem">
                <input type="checkbox" name="qiywCompanyName" id="qiywCompanyName" value="qiywCompanyName" /><label
                    for="ywCompanyName">维保单位</label>  
            </div>
            <div class="fitem">
                <input type="checkbox" name="qisubTime" id="qisubTime" value="qisubTime" /><label
                    for="subTime">最近一次维保日期</label>  
            </div>
            <div class="fitem">
                <input type="checkbox" name="qiischangInfo" id="qiischangInfo" value="qiischangInfo" /><label
                    for="ischangInfo">变更信息</label>  
            </div>
            <div class="fitem">
                <input type="button" value="全选" onclick="ChooseAll()" />
                <input type="button" value="清空" onclick="ClearAll()" />
            </div>
            </form>
        </div>
        <div id="dlg-GetShowColumn">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-ok" onclick="hideColumn()">
                保存</a> <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel"
                    onclick="javascript:$('#GetShowColumn').dialog('close')">取消</a>
        </div>
</body>
</html>