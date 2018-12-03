<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
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
String cityName = GlobalFunction.cityName;
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int isliulan = 0;
int role=0;
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
var rukuid;
var ruKuValid;
$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	$('#imgpic').bind("click",testDid);
	$('#imgpic2').bind("click",testDid2);
	$('#imgpic3').bind("click",testDid3);	

    /*
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
    }); */

    var wgurl = encodeURI(encodeURI('/tcweb/elevator/getAutoWgCompanyList')); 
	 $("#wgCompanyId2").autocomplete(  
			    wgurl,  
	            {  
	            scroll: true,  
	                matchContains: true,  
	                width: 188,  
	                minChars: 2,
	                max:100,
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
	        	 $('#wgCompanyId').attr("value",formatted);
	            }
	            else{
	             $('#wgCompanyId').attr("value",'');
	 	            }
   	        });

	    /*
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
    }); */

    var ywurl = encodeURI(encodeURI('/tcweb/elevator/getAutoYwCompanyList')); 
	 $("#ywCompanyId2").autocomplete(  
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
	        	 $('#ywCompanyId').attr("value",formatted);
	            }
	            else{
	             $('#ywCompanyId').attr("value",'');
	 	            }
   	        });
   /*
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
	*/

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
    /*
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
	*/

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
    
   /*
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
   */

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

     /*
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
  */

  

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

    $('#area').combobox({   
        url:'/tcweb/elevator/areaInfoList',   
        valueField:'area',   
        textField:'area'  
    });  

 comb10 =$('#area').combobox({
	 onSelect: function (record) {
	 comb8.combobox({
//      url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.value),
    url: '/tcweb/elevator/getjdbCompanyListByNewarea?companyArea='+encodeURI(record.area),
    valueField: 'id',
    textField: 'companyName'
}).combobox('clear');

     }
	});
    
   /*
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
	  $('#areainfo').combobox({   
	        url:'/tcweb/elevator/areaInfoList',   
	        valueField:'area',   
	        textField:'area'  
	    });  
	
	$('#btn-save,#btn-cancel,#btn-ok,#btn-edit').linkbutton(); 
	win = $('#car-window2').window({  closed:true,draggable:false,modal:true }); 
	form = win.find('form');

	win8d = $('#car-window8d').window({  closed:true,draggable:false,modal:true }); 
	anzhuangwin = $('#anzhuang-window').window({  closed:true,draggable:false,modal:true });
	grid=$('#dtt').datagrid({
	    title:'电梯列表',
	    fitColumns:true,
 	    striped:true,
	    pageSize:10,
	    pageList:[10,15,20,25,30,35,40,250,500,1000],
	    url:'/tcweb/elevator/hechaddelevatorlist',
	    queryParams:{},
	    columns:[[
	        {field:'registNumber',title:'电梯编号',width:80,styler:function(value,row,index){
		        if (row.ruKuValid==1){return 'background-color:#99cc99;';} 
		        if (row.ruKuValid==2){return 'background-color:#FF3333;';}},
		        formatter: function(value,rec,index) {   
		        	 if(rec.ruKuValid==2){
		        		 <% if("1".equals(cityName)){ %>
		                    return "N"+value+"<img src='<%=request.getContextPath()%>/images/yulan.png' alt='查看' style='cursor:hand;' onclick='tishi("+"\""+rec.rukubeizhu+"\""+")'/>";
		                    <% } else {%>
		                    return value+"<img src='<%=request.getContextPath()%>/images/yulan.png' alt='查看' style='cursor:hand;' onclick='tishi("+"\""+rec.rukubeizhu+"\""+")'/>";
			                   
		                    <% }%>
		        	 }
		                else{
		                	 <% if("1".equals(cityName)){ %>
		                    return "N"+value;
		                    <% } else {%>
		                    return value;
		                    <% }%>
		                }
	            }
		        },
		    {field:'buildingName',title:'楼盘名称',width:150},
	        {field:'address',title:'地址',width:200},
	        {field:'useNumber',title:'单位内部编号'},
	        {field:'building',title:'栋',width:40},
	        {field:'unit',title:'单元',width:50},
	        {field:'recordSate',title:'状态',width:120,formatter: function(value,rec,index) {
                if(value==0)
                    return "已创建";
                if(value==1)
                	 return "已经分配，未粘贴";
                if(value==2)
               	 return "已粘贴";
		         }},
		    {field:'pasteNote',title:'修改内容',width:200,styler:function(value,row,index){if(value){return 'background-color:#c8d9f5;';}}},
		    {field:'subPersonName',title:'上传人',width:100},
		    {field:'mobileUploadbeizhu',title:'上传备注',width:140},
		    {field:'arrangePersonName',title:'任务分配人',width:80},
		    {field:'arrangeTime2',title:'任务分配时间',width:140},
		    {field:'pastePersonName',title:'粘贴人',width:100},
		    {field:'pasteTime',title:'粘贴时间',width:100},
	        {field:'registCode',title:'登记编号',width:150},
	    //    {field:'jdbCompanyName',title:'街道办',width:150},
	        {field:'wgCompanyName',title:'使用单位',width:150},
	        {field:'ywCompanyName',title:'维保单位',width:110},
	   //     {field:'zzCompanyName',title:'制造单位',width:70},
	   //     {field:'azCompanyName',title:'安装单位',width:70},
	   //     {field:'jyCompanyName',title:'检验单位',width:55},
	        {field:'inspector',title:'检验人员',width:55},
	        {field:'inspectDate',title:'检验日期',width:55},
	        {field:'subTime',title:'最近一次维保日期',width:135,formatter: function(value,rec,index) {
                if(value!=null)
                    return value.substring(0,16);
		         }},
	        {field:'zjCompanyName',title:'质监局'},
	        {field:'deviceId',title:'黑匣子设备'},
	        {field:'"1"',title:'图片',align:'center',
	            formatter: function(value,rec,index) {
    	        var registNumber = ''+rec.registNumber;
    	        var subTime = (''+rec.subTime2).substring(0,16); 
    	       	return  "<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='图片' style='cursor:hand;' onclick='openPicInfo("+"\""+registNumber+"\""+","+"\""+subTime+"\""+")'/>";
    	       	}}
	    ]],
	    nowrap:false,
	    pagination:true,
	    <% if(isliulan == 0){%>
	    <% if(role==2 || role==1){%>
	    toolbar:[{
	        text:'新增',
	        iconCls:'icon-add',
	        handler:function(){
	    	win.window('open');  
	    //	form.form('clear');
	        addFun();
	    	form.url ='/tcweb/elevator/ddadd';	
	    	colseWinDetail();	 
	        }
	    },{
	        text:'删除',
	        iconCls:'icon-cut',
	        handler:function(){
	    	 var row = grid.datagrid('getSelected'); 
	    	 if(row){
	    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
	    	 jQuery.post('/tcweb/elevator/dddelete',
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
	    },'-',{
	        text:'修改',
	        iconCls:'icon-edit',
	        handler:function(){
	    	var row = grid.datagrid('getSelected');   
	    	if (row){
	    		 jdbSelectValue2(row.area);
			     bianjixiala(row.zzCompanyId,row.zzCompanyName,row.azCompanyId,row.azCompanyName,row.wgCompanyId,row.wgCompanyName,row.ywCompanyId,row.ywCompanyName,row.jyCompanyId,row.jyCompanyName,row.zjCompanyId,row.zjCompanyName,row.townshipStreets,row.jdbCompanyName);	   
	    		 win.window('open');  
	    		 colseWinDetail();
	    		 editFun(row.registNumber,row.deviceId);  
	    		 form.form('load', '/tcweb/elevator/ddedit/'+row.id);
	    		 form.url = '/tcweb/elevator/ddupdate/'+row.id; 
	    		 opt =1;
	    	 } else {  
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。'
	    		 });   
	    	 }  
	        }
	    }
	    ],
	    <% } %> 
	    <%}%>
	    onDblClickRow :function(rowIndex,rowData){
		 //   alert("双击事件发生了"+rowData.registNumber);
		    win.window('open');
		    form.form('load', '/tcweb/elevator/ddedit2/'+rowData.id);
		    showWinDetail(rowData.registNumber,rowData.subTime2.substring(0,16));  
		    rukuid=rowData.id;   
		    ruKuValid=rowData.ruKuValid;
	    }
	});	
	$('#dtt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,15,20,25,30,35,40,250,500,1000]});     	  
}
);

function jdbSelectValue2(area){ 
	   comb8.combobox({  
 //    url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(area),
       url: '/tcweb/elevator/getjdbCompanyListByNewarea?companyArea='+encodeURI(area),
       valueField: 'id',
       textField: 'companyName'
   }).combobox('clear');
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
   /*
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
	  */
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

function openPicInfo(registNumber,subTime){
	anzhuangwin.window('open');
    $('#img1').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+registNumber+"&subTime="+subTime+"&index="+1);
    $('#img2').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+registNumber+"&subTime="+subTime+"&index="+2);
    $('#img3').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+registNumber+"&subTime="+subTime+"&index="+3);
	$('#btn-no2').linkbutton();
}

function closepic(){
	anzhuangwin.window('close'); 
}

function clearQuery(){
	$('#registNumber').attr("value","");
	$('#addressinfo').attr("value","");
	$('#buildingNameInfo').attr("value","");
	$('#registCodeinfo').attr("value","");
	$('#useNumberInfo').attr("value","");
//	$('#ywCompanyIdinfo').combobox('clear');
//	$('#ywCompanyIdinfo').combobox({
	//	url:'/tcweb/elevator/getYwCompanyList',
	//    valueField:'id',
	//    textField:'companyName'
//	});
    $('#ywCompanyIdinfo').attr("value","");
	$('#ywCompanyIdinfo2').attr("value","");
	//$('#areainfo').combobox('clear');
//	$('#areainfo option:first').attr('selected','selected');
	$('#addressinfo').attr("value","");
}

function query(){  
	var registNumber=$('#registNumber').attr("value");
	var address=$('#addressinfo').attr("value");
	var buildingName=$('#buildingNameInfo').attr("value");
  //  var ywCompanyId=$('#ywCompanyIdinfo').combobox('getValue'); 
   var ywCompanyId=$('#ywCompanyIdinfo2').attr("value"); 
    var useNumber =$('#useNumberInfo').attr("value");
  //  var area=$('#areainfo').combobox('getValue'); 
 //     var area=$('#areainfo option:selected').val();
  var area =$('#areainfo').combobox('getValue');
  //  alert("ywCompanyId---"+ywCompanyId);
 //   alert("ywCompanyId2---"+document.all.ywCompanyIdinfo.value);
 //   alert("ywCompanyId3---"+ywName);
    var registCode =$('#registCodeinfo').attr("value");
    if (!ywCompanyId){
    	ywCompanyId =0;
    	}
	if(""==registNumber && ywCompanyId==0){
	   if(""==ywName && ""==area){	
		   if(""==address && ""==buildingName){
			   if(""==registCode){
				   if(""==useNumber){	
	                 grid.datagrid("options").url='/tcweb/elevator/hechaddelevatorlist';
				   }
				   else{
					   grid.datagrid("options").url='/tcweb/elevator/hechaddquery';
					   grid.datagrid("options").queryParams={'useNumber':useNumber};  
					   }
			   }
			   else{
			   grid.datagrid("options").url='/tcweb/elevator/hechaddquery';
			   grid.datagrid("options").queryParams={'registCode':registCode,'useNumber':useNumber};  
			   }
			   
	       }
		   else{
			   grid.datagrid("options").url='/tcweb/elevator/hechaddquery';
			   grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'buildingName':buildingName,'registCode':registCode,'useNumber':useNumber};  
			   }
	   }
	   else{
		   if(""==address){
		   grid.datagrid("options").url='/tcweb/elevator/hechaddquery';
		   grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'registCode':registCode,'useNumber':useNumber};
		   }
		   else{
			grid.datagrid("options").url='/tcweb/elevator/hechaddquery';
			grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'buildingName':buildingName,'registCode':registCode,'useNumber':useNumber};
			   }
	  }
	}
   else{
     grid.datagrid("options").url='/tcweb/elevator/hechaddquery';
     grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'buildingName':buildingName,'registCode':registCode,'useNumber':useNumber};
   }
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
 		        {field:'registNumber',title:'电梯编号',width:80,formatter: function(value,rec,index) {
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

function showWinDetail(registNumber,subTime){
	$("form input").css({border:'0px solid' });
    
	$('input').attr("disabled","disabled");
	
	$('#registNumber').attr("disabled","");
	$('#addressinfo').attr("disabled","");
	$('#buildingNameInfo').attr("disabled","");
	$('#useNumberInfo').attr("disabled","");
	$('#registCodeinfo').attr("disabled","");
	$('#ywCompanyIdinfo').combobox('enable');
	
	
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
//	$('#nextInspectDate').datebox('disable');
//	$('#inspectDate').datebox('disable');
	
	
	$(".fontShow").hide();
	$('#btn-save').hide();
	$('#btn-cancel').show();
	$('#btn-ok').show();
	$('#btn-edit').show();

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
//	$('#wgCompanyId').combobox('enable');
//	$('#zzCompanyId').combobox('enable');
//	$('#jyCompanyId').combobox('enable');
//	$('#azCompanyId').combobox('enable');
//	$('#ywCompanyId').combobox('enable');
//	$('#zjCompanyId').combobox('enable');
//	$('#townshipStreets').combobox('enable');

	$('#manufactDate').datebox('enable');
//	$('#nextInspectDate').datebox('enable');
//	$('#inspectDate').datebox('enable');
	
	$(".fontShow").show();	
	$('#btn-save,#btn-cancel').show();  
	$('#btn-ok').hide();
	$('#btn-edit').hide();

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
           /*
			 var nextInspectDate = $("#nextInspectDate").attr("value");
				if (nextInspectDate != "") {
					if (!strDateTime(nextInspectDate)) {
						$.messager.alert('操作失败', '下次检验日期格式形如：2013-01-04', 'error');
						return false;
					}
				} */

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
			  
             //表单验证
            return $("#form1").form('validate');
		//	return true;
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
  

  function ruku(){    
	if (rukuid > 0){   
		if(ruKuValid==0)
			$.messager.alert('操作失败','该行不能入库，必须选择电梯编号背景为绿色的记录进行入库','error');
		else{
    		 var id = rukuid;
			 jQuery.post('/tcweb/elevator/ddrukucheck',
			    	 {'id':id},
			    	 function(data){
			    		eval("data="+"'"+data+"'");  
			    		if("success"==data){
			    		 $.messager.show({   
					        title:'提示信息',
					        timeout:1000,
					        msg:'操作成功，谢谢。' 
				         });  	
		 	             grid.datagrid('reload');
		 	             win.window('close');
			    		}
			    		else{
			    			$.messager.alert('操作失败',data,'error');
		    	    		}
		    	       });
    		}
	 } else {  
		 $.messager.show({   
			 title:'警告',
			 msg:'请双击要入库的记录' 
		 });   
	 }  
    }

  function tishi(rukubeizhu){
		$.messager.alert('入库失败原因',rukubeizhu,'error');
	  }

  function hecha(){
     if(rukuid > 0){
         var id =rukuid;
         jQuery.post('/tcweb/elevator/ddhecha',
		    	 {'id':id},
		    	 function(data){
		    		eval("data="+"'"+data+"'");  
		    		if("success"==data){
		    		 $.messager.show({   
				        title:'提示信息',
				        timeout:1000,
				        msg:'操作成功，谢谢。' 
			         });  	
	 	             grid.datagrid('reload');
	 	             win.window('close');
		    		}
		    		else{
		    			$.messager.alert('操作失败',data,'error');
	    	    		}
	    	       });
         }
     else {  
		 $.messager.show({   
			 title:'警告',
			 msg:'请双击要选择的记录' 
		 });   
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
</style>
</head>
<body class="easyui-layout" data-options="fit:true">
<div region="north" style="overflow:hidden;background-color:rgb(201,220,245);">  
 <fieldset id="addDiv" style="width: 100%;margin:0px"><legend>查询条件</legend>
    
     <table> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
   <% if("1".equals(cityName)){ %>
   <td nowrap>N<input id="registNumber" name="registNumber" size="20" class="easyui-validatebox"></input></td>
    <% } else {%>
   <td nowrap><input id="registNumber" name="registNumber" size="20" class="easyui-validatebox"></input></td>
 <% }%>
    <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="addressinfo" name="addressinfo" size="23" class="easyui-validatebox"></input></td>
   <td align="right" nowrap>楼盘：</td>
   <td nowrap><input id="buildingNameInfo" name="buildingName" size="24" class="easyui-validatebox"></input></td>
    <td align="right" nowrap>内部编号：</td>
   <td nowrap><input id="useNumberInfo" name="useNumberName" size="24" class="easyui-validatebox"></input></td>
   </tr>  
  <tr>  
 
 <td nowrap>行政区划：</td>
    <!-- <td> 
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
</select>
</td> -->  
<td><input id="areainfo" name="areainfo" /></td>
 <td nowrap>维保单位：</td>
   <td> 
 <!--   <select id="ywCompanyIdinfo"  class="easyui-combobox" name="ywCompanyIdinfo" style="width:152px;"></select> -->
  <input id="ywCompanyIdinfo" style="width:152px;background-color:#87CEEB;" >
  <input type ="hidden" id="ywCompanyIdinfo2"> 
 </td>
 <td nowrap>登记编号：</td>
 <td nowrap><input id="registCodeinfo" name="registCodeinfo" size="24" class="easyui-validatebox"></input></td>
 <td colspan="2"><a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
	<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 				
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
<div id="car-window2" title="详细信息" style="width:750px;height:500px;"> 
  <div style="padding:20px 20px 40px 80px;">
  <div style="width:530px;height:240px;overflow-x:auto;overflow-y:hidden;border: 1px solid #000000;">
   <div style="width:800px;border:0px;">
 <div id="ywImg4" style="float:left"><img src="" id="img4" style="width:265px;height:240px;"></div>
 <div id="ywImg5" style="float:left"><img src="" id="img5" style="width:265px;height:240px;"></div>
 </div> 
  </div>
  <form method="post" id="form1">    
  <table>   
   <tr>      
   <td width="70">电梯编号：</td>      
   <td><input id="registNumberinfo" name="registNumber" class="easyui-validatebox textbox" data-options="validType:'length[0,6]'"></input><span class="fontShow"><font color="red">*</font></span>（必填）</td>  
    <td>登记编号：</td>      
   <td><input id="registCode" name="registCode" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
   <tr> 
    <td>地址：</td>      
   <td><input id="address" name="address" class="easyui-validatebox textbox" data-options="validType:'length[0,100]'"></input><span class="fontShow"><font color="red">*</font></span></td> 
   <td>名称：</td>      
   <td><input id="name" name="name" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input><span class="fontShow"><font color="red">*</font></span></td> 
   </tr>
   <tr> 
    <td>行政区划：</td> 
    <td><input id="area" name="area"></input><span class="fontShow"><font color="red">*</font></span>（必选）</td>      
 <!--  <td nowrap><select id="area"  class="easyui-combobox" name="area" style="width:152px;">
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
</select><span class="fontShow"><font color="red">*</font></span>（必选）</td>  -->
   <td>所在乡镇/街道：</td>      
     <td><select id="townshipStreets"  class="easyui-combobox" name="townshipStreets" style="width:152px;"> 
</select><span class="fontShow"><font color="red">*</font></span>（必选）</td>
   </tr>
    <tr> 
    <td>楼盘名称：</td>      
   <td><input id="buildingName" name="buildingName" class="easyui-validatebox textbox" data-options="validType:'length[0,100]'"></input><span class="fontShow"><font color="red">*</font></span></td> 
   <td colspan="2">      
        经度：<span style="padding-left:75px"><input id="map_X" name="map_X" style="width:100px;"></input></span><span class="fontShow"><font color="red">*</font></span><br> 
        纬度：<span style="padding-left:75px"><input id="map_Y" name="map_Y" style="width:100px;"></input></span><span class="fontShow"><font color="red">*</font></span>
   <img id="imgpic2"  name="imgpic2" src="../../images/html.png" style="cursor: hand"/>
   </td>
   </tr>
    <tr> 
    <td>所在栋：</td>      
   <td><input id="building" name="building" class="easyui-validatebox textbox" data-options="validType:'length[0,10]'"></input><span class="fontShow"><font color="red">*</font></span></td> 
   <td>所在单元：</td>      
   <td><input id="unit" name="unit" class="easyui-validatebox textbox" data-options="validType:'length[0,10]'"></input><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
   <tr>
   <td>电梯类别：</td>      
   <td> 
   <select id="eleType"  class="easyui-combobox" name="eleType" style="width:152px;">
    <option value="客梯">客梯</option>
    <option value="货梯">货梯</option>
    <option value="扶梯">扶梯</option>  
    <option value="观光梯">观光梯</option>
    <option value="消防梯">消防梯</option>  
    <option value="服务梯">服务梯</option>    
</select><span class="fontShow"><font color="red">*</font></span>（必选）
   </td>
   <td>室内外：</td>      
   <td> 
   <select id="inoutDoor"  class="easyui-combobox" name="inoutDoor" style="width:152px;">
    <option value="室内">室内</option>
    <option value="室外">室外</option>  
</select><span class="fontShow"><font color="red">*</font></span>（必选）
   </td>
   </tr>
   <tr>
    
   </tr>
    <tr>
   <td>注册代码：</td>      
   <td><input id="registCode2" name="registCode2" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input><span class="fontShow"><font color="red">*</font></span></td>
   <td>注册登记人员：</td>      
   <td><input id="registor" name="registor" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
   <tr>
   <td colspan="4"><hr></td>
   </tr>
   <tr>
    <td>产权单位：</td>
    <td><input id="propertyRightsUnit" name="propertyRightsUnit" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td> 
   </tr>
    <tr>      
   <td>制造单位：</td>      
   <td>
    <!-- <select id="zzCompanyId"  class="easyui-combobox" name="zzCompanyId2" style="width:152px;"></select>  -->
    <input id="zzCompanyId2" style="background-color:#87CEEB"><input type ="hidden" id="zzCompanyId" name="zzCompanyId">
   <span class="fontShow"><font color="red">*</font></span>（必选）</td>
      <td>出厂编号：</td>      
  <td><input id="factoryNum" name="factoryNum" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input><span class="fontShow"><font color="red">*</font></span></td>  
   </tr>
  
    <tr>      
   <td>制造日期：</td>      
   <td><select id="manufactDate"  class="easyui-datebox" name="manufactDate" style="width:152px;"> 
</select><span class="fontShow"><font color="red">*</font></span></td>
   
   </tr> 
   <tr> 
<td>检验单位：</td>      
   <td>
   <!--  <select id="jyCompanyId"  class="easyui-combobox" name="jyCompanyId2" style="width:152px;"></select>  -->
    <input id="jyCompanyId2" style="background-color:#87CEEB"><input type ="hidden" id="jyCompanyId" name="jyCompanyId">
   <span class="fontShow"><font color="red">*</font></span></td>
 <td>检验人员：</td>      
   <td><input id="inspector" name="inspector" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input></td>
   </tr>
   <tr>
   <td>检验类别：</td>      
   <td><input id="checkCategory" name="checkCategory" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input><span class="fontShow"><font color="red">*</font></span></td>
   <td>检验结论：</td>      
   <td><input id="checkResult" name="checkResult" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
   <tr>
     <td>检验日期：</td>      
   <td><input id="inspectDate"  type="text"  name="inspectDate" style="width:152px;"></input><span class="fontShow"><font color="red">*</font></span></td>
    <td>下次检验日期：</td>      
   <td><input id="nextInspectDate"  type="text"  name="nextInspectDate" style="width:152px;"></input><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
   <tr>
   <td>报告编号：</td>      
   <td><input id="checkReportNum" name="checkReportNum" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
   <tr>
     <td>安装单位：</td>      
   <td>
   <!-- <select id="azCompanyId"  class="easyui-combobox" name="azCompanyId2" style="width:152px;"></select> -->
   <input id="azCompanyId2" style="background-color:#87CEEB"><input type ="hidden" id="azCompanyId" name="azCompanyId">
   <span class="fontShow"><font color="red">*</font></span>（必选）</td>
<td>投用日期：</td>      
   <td><input id="useDate" name="useDate" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
   <tr>
   <td>竣工验收日期：</td>      
   <td><input id="completeAcceptanceDate"  type="text" class="easyui-datebox" name="completeAcceptanceDate" style="width:152px;"></input></td>
<td>验收检验机构：</td>      
   <td><input id="acceptanceDateDepart" name="acceptanceDateDepart" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>
   </tr>
   <tr>
   <td>验收报告编号：</td>      
   <td><input id="acceptanceReportNum" name="acceptanceReportNum" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>
   </tr>
     <tr>
   <td>使用单位：</td>       
   <td>
    <!-- <select id="wgCompanyId"  class="easyui-combobox" name="wgCompanyId2" style="width:152px;"></select>  -->
    <input id="wgCompanyId2" style="background-color:#87CEEB"><input type ="hidden" id="wgCompanyId" name="wgCompanyId"><span class="fontShow"><font color="red">*</font></span>（必选）</td> 
    <td>单位内部编号：</td>      
   <td><input id="useNumber" name="useNumber" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input></td> 
   </tr>
   <tr>
   <td>安全管理部门：</td>
   <td><input id="safetyManDepart" name="safetyManDepart" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>
   </tr>
    <tr>
   <td>安全管理人员：</td>
   <td><input id="safetyManPerson" name="safetyManPerson" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input><span class="fontShow"><font color="red">*</font></span></td>
   <td>安全管理人员电话：</td>
   <td><input id="safetyManPersonTel" name="safetyManPersonTel" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
   <tr>
   <td>维保单位：</td>      
   <td>
 <!--  <select id="ywCompanyId"  class="easyui-combobox" name="ywCompanyId2" style="width:152px;"></select> -->
  <input id="ywCompanyId2" style="background-color:#87CEEB"><input type ="hidden" id="ywCompanyId" name="ywCompanyId">
   <span class="fontShow"><font color="red">*</font></span>（必选）</td>
  <td>维保合同有效期：</td>
  <td><input id="mContractVdate"  type="text" class="easyui-datebox" name="mContractVdate" style="width:152px;" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input></td>
   </tr>
    <tr>
   <td>维保人员 <img id="imgpic3"  name="imgpic3" src="../../images/html.png" style="cursor: hand"/>
   </td>
   </tr>
   <tr>
  <!--  <td>市场与质量监管局：</td>    -->      
   <td>
 <!--   <select id="zjCompanyId"  class="easyui-combobox" name="zjCompanyId2" style="width:152px;"></select> --> 
   <input type="hidden" id="zjCompanyId2" style="background-color:#87CEEB"><input type ="hidden" id="zjCompanyId" name="zjCompanyId" value="0"></td>
   </tr>
   <tr>
   <td>变更承办单位：</td>
    <td><input id="handleCompany" name="handleCompany" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>
   <td>变更承办单位代码</td>
   <td><input id="handleCompanyCode" name="handleCompanyCode" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>
   </tr>
   <tr>
   <td>变更方式：</td>
   <td><input id="changeWay" name="changeWay" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>
   </tr>
   <tr>
   <td colspan="4"><hr></td>
   </tr>
   <tr>
    <td>电梯型号：</td>      
   <td><input id="eleMode" name="eleMode"></input><span class="fontShow" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"><font color="red">*</font></span></td> 
   </tr>
   <tr>
   <td>额定荷载：</td>      
   <td><input id="eleLoad" name="eleLoad" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input><span class="fontShow"><font color="red">*</font></span></td>
   <td>速度：</td>      
   <td><input id="speed"  name="speed" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
    <tr>      
   <td>提升高度：</td>      
   <td><input id="eleheight" name="eleheight" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input></td> 
    <td>宽度：</td> 
   <td><input id="elewidth" name="elewidth" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input></td>
   </tr>
    <tr>  
     <td>层站：</td>      
   <td><input id="eleStop" name="eleStop" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input><span class="fontShow"><font color="red">*</font></span></td>    
  <td></td>
  <td></td>
   </tr>  
   <tr>
   <td colspan="4"><hr></td>
   </tr>
   <tr>
    <td>黑匣子设备：</td>      
   <td><input id="deviceId" name="deviceId" readonly style="background-color:#c0c0c0"></input>
  <img id="imgpic"  name="imgpic" src="../../images/search2.gif" style="cursor: hand" />
   </td> 
   <td>备注：</td> 
   <td><input id="note" name="note" class="easyui-validatebox textbox" data-options="validType:'length[0,300]'"></input></td>
   </tr>
    <tr>
    <td></td><td><input id="luruPersonID" name="luruPersonID" type="hidden" value="<%=userId%>"></input></td>
    </tr>
   </table>
   <table width=70%>
    <tr>
    <td align="center">
      <a href="javascript:void(0)" onclick="saveCar2d()" id="btn-save" icon="icon-save">保存</a>  
     <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">关闭</a> 
      <a href="javascript:void(0)" onclick="ruku()" id="btn-ok" icon="icon-ok">入库</a> 
      <a href="javascript:void(0)" onclick="hecha()" id="btn-edit" icon="icon-edit">核查</a> 
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