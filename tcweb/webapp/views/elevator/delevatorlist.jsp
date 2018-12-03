<%@ page import="com.zytx.models.UserInfo,com.zytx.models.UserInfoVO,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
 <!-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"> -->
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
var rukuid;
var ruKuValid;

var chushixialaliebiaoNumber =0;
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

	 $("#registCodeinfo").css("width", inputWidth); 
	 $("#buildingNameInfo").css("width", inputWidth); 
	 $("#useNumberInfo").css("width", inputWidth); 
	 
	 $("#shenhe").css("width", inputWidth);  
	 $("#qresource").css("width", inputWidth); 
	 

	$('#imgpic').bind("click",testDid);
	$('#imgpic2').bind("click",testDid2);
	$('#imgpic3').bind("click",testDid3);	

    /*
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

	 var url = encodeURI(encodeURI('/tcweb/elevator/getAutoYwCompanyList')); 
 	 $("#ywCompanyIdinfo").autocomplete(  
 	            url,  
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
 	        	 $('#ywCompanyIdinfo2').attr("value",formatted);
 	            }
 	            else{
 	             $('#ywCompanyIdinfo2').attr("value",'');
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
  
  comb =$('#wgCompanyId').combobox({
	    valueField:'id',
	    textField:'companyName',
	    onChange: function(rec){
      var url = '/tcweb/elevator/getWgCompanyList';
      $('#wgCompanyId').combobox('reload', url);
     }
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
	    valueField:'id',
	    textField:'companyName',
	    onChange: function(rec){
	      var url = '/tcweb/elevator/getYwCompanyList';
	      $('#ywCompanyId').combobox('reload', url);    
	}
	});

	$('#ywCompanyId').combobox({
  	filter: function(q, row){
  	//    ywName = q;
  		var opts = $(this).combobox('options');
  		return row[opts.textField].indexOf(q) >= 0;
  	}
  });


			
	comb3 =$('#zzCompanyId').combobox({
	    valueField:'id',
	    textField:'companyName',
	    onChange: function(rec){
	      var url = '/tcweb/elevator/getZzCompanyList';
	      $('#zzCompanyId').combobox('reload', url);
	      
	  	}
	});  


	$('#zzCompanyId').combobox({
  	filter: function(q, row){
  	//    ywName = q;
  		var opts = $(this).combobox('options');
  		return row[opts.textField].indexOf(q) >= 0;
  	}
  });


	

	comb4 =$('#azCompanyId').combobox({
	    valueField:'id',
	    textField:'companyName',
	    onChange: function(rec){
	      var url = '/tcweb/elevator/getAzCompanyList';
	      $('#azCompanyId').combobox('reload', url);
	      
	  	}
	});

	$('#azCompanyId').combobox({
  	filter: function(q, row){
  //	    ywName = q;
  		var opts = $(this).combobox('options');
  		return row[opts.textField].indexOf(q) >= 0;
  	}
  });
	
	

		
	comb5 =$('#jyCompanyId').combobox({
	    valueField:'id',
	    textField:'companyName',
	    onChange: function(rec){
	      var url = '/tcweb/elevator/getJyCompanyList';
	      $('#jyCompanyId').combobox('reload', url);
	      
	  	}
	});


	$('#jyCompanyId').combobox({
  	filter: function(q, row){
  //	    ywName = q;
  		var opts = $(this).combobox('options');
  		return row[opts.textField].indexOf(q) >= 0;
  	}
  });

	
		
	comb6 =$('#zjCompanyId').combobox({
	    valueField:'id',
	    textField:'companyName',
	    onChange: function(rec){
	      var url = '/tcweb/elevator/getZjCompanyList';
	      $('#zjCompanyId').combobox('reload', url);
	      
	  	}
	});

	$('#zjCompanyId').combobox({
  	filter: function(q, row){
  //	    ywName = q;
  		var opts = $(this).combobox('options');
  		return row[opts.textField].indexOf(q) >= 0;
  	}
  });


	 comb8=$('#townshipStreets').combobox({
		    valueField:'id',
		    textField:'companyName',
		    onChange: function(rec){
	      var url = '/tcweb/elevator/getjdbCompanyList';
	      $('#townshipStreets').combobox('reload', url);
	      
	  	}
		});

	    $('#townshipStreets').combobox({
	    	filter: function(q, row){
	    	//    ywName = q;
	    		var opts = $(this).combobox('options');
	    		return row[opts.textField].indexOf(q) >= 0;
	    	}
	    });
    */
  

    /*
    comb7=$('#ywCompanyIdinfo').combobox({
    	valueField:'id',
	    textField:'companyName',
	    onChange: function(rec){
        var url = '/tcweb/elevator/getYwCompanyList';
        $('#ywCompanyIdinfo').combobox('reload', url);
        
    	}
	});
		    
    $('#ywCompanyIdinfo').combobox({
    	filter: function(q, row){
    	    ywName = q;
    		var opts = $(this).combobox('options');
    		return row[opts.textField].indexOf(q) >= 0;
    	}
    });
    */
   
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

	 comb7=$('#townshipStreets').combobox({
	    	valueField:'id',
		    textField:'companyName'
		});
			    
	    $('#townshipStreets').combobox({
	    	filter: function(q, row){
	    	    ywName = q;
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
		 comb7.combobox({
    //       url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(record.value),
           url: '/tcweb/elevator/getjdbCompanyListByNewarea?companyArea='+encodeURI(record.area),
           valueField: 'id',
           textField: 'companyName'
       }).combobox('clear');

	     }
		});
	
	$('#btn-save,#btn-cancel,#btn-ok,#btn-edit').linkbutton(); 
	win = $('#car-window2').window({  closed:true,draggable:false,modal:true,minimizable:false,collapsible:false,maximizable:false,onClose:function(){$('input').attr("disabled","");} }); 
	
	form = win.find('form');
	//批量导入
	winDr = $('#car-windowDr').window({  closed:true,draggable:false,modal:true,minimizable:false,collapsible:false,maximizable:false});
	//导入异常
	winDryc = $('#daoruReturn-window').window({  closed:true,draggable:false,modal:true,minimizable:false,collapsible:false,maximizable:false,onClose:function(){$("#rukuCount").html("0");}});
	win8d = $('#car-window8d').window({  closed:true,draggable:false,modal:true }); 
	anzhuangwin = $('#anzhuang-window').window({  closed:true,draggable:false,modal:true });
	checktishiwin = $('#checktishi-window').window({  closed:true,draggable:false,modal:true });
	companyinfowin = $('#companyinfo-window').window({  closed:true,draggable:false,modal:true,minimizable:false,collapsible:false,maximizable:false });
	taskcompleteinfowin = $('#taskcompleteinfo-window').window({  closed:true,draggable:false,modal:true,minimizable:false,collapsible:false,maximizable:false });
	grid=$('#dtt').datagrid({
	    title:'电梯列表',
	    fitColumns:true,
 	    striped:true,
	    pageSize:15,
	    pageList:[15,30,45,60],
	    url:'/tcweb/elevator/ddelevatorlist',
	    queryParams:{},
	    columns:[[
	        {field:'registNumber',align:'center',halign:'center',title:'电梯编号',width:100,styler:function(value,row,index){
		        if (row.ruKuValid==1){return 'background-color:#99cc99;';} 
		        if (row.ruKuValid==2){return 'background-color:#FF3333;';}
		        if (row.isnormalFlag==1){return 'background-color:#969696;';}},
		        formatter: function(value,rec,index) {   
		        	 if(rec.ruKuValid==2){
		        		  <% if("1".equals(cityName)){ %>
		           //         return "N"+value+"<img src='<%=request.getContextPath()%>/images/yulan.png' alt='查看' style='cursor:hand;' onclick='tishi("+"\""+rec.rukubeizhu+"\""+")'/>";
		                     return 'N'+value+'<a href="#" onclick="tishi('+'\''+rec.rukubeizhu+'\''+')"><i class="fa fa-exchange" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	
		                    <% } else {%>
		             //       return value+"<img src='<%=request.getContextPath()%>/images/yulan.png' alt='查看' style='cursor:hand;' onclick='tishi("+"\""+rec.rukubeizhu+"\""+")'/>";
		                      return value+'<a href="#" onclick="tishi('+'\''+rec.rukubeizhu+'\''+')"><i class="fa fa-exchange" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	
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
		        <% if(!"0".equals(cityName)){ %>
		        {field:'shibieCode',title:'识别码',width:100},
		        <% }%>
		    {field:'buildingName',align:'left',halign:'center',title:'楼盘名称',width:$(this).width() * 0.09},
	        {field:'address',align:'left',halign:'center',title:'地址',width:$(this).width() * 0.09,formatter: function(value,rec,index) {   
	        	 if(rec.shenhe==1)
	                    return value+"<img src='<%=request.getContextPath()%>/images/usercheck.png' alt='现场核查' style='cursor:hand;' onclick='checktishi("+rec.id+")'/>";
	                else
	                    return value;
         }},
	        {field:'useNumber',align:'left',halign:'center',title:'内部编号',width:$(this).width() * 0.09},
	        {field:'building',align:'left',halign:'center',title:'栋',width:$(this).width() * 0.09},
	        {field:'unit',align:'left',halign:'center',title:'单元',width:$(this).width() * 0.09},
	        {field:'recordSate',align:'center',halign:'center',title:'状态',width:$(this).width() * 0.09,formatter: function(value,rec,index) {
                if(value==0)
                    return "已创建";
                if(value==1)
                	 return "已经分配，未粘贴";
                if(value==2)
               	 return "已粘贴";
		         }},
		 //   {field:'pasteNote',title:'修改内容',width:100,styler:function(value,row,index){if(value){return 'background-color:#c8d9f5;';}}},
		 //   {field:'subPersonName',title:'上传人',width:100},
		 //   {field:'mobileUploadbeizhu',title:'上传备注',width:140},
		//    {field:'arrangePersonName',title:'任务分配人',width:80},
		//    {field:'arrangeTime2',title:'任务分配时间',width:140},
		//    {field:'pastePersonName',title:'粘贴人',width:100},
		//    {field:'pasteTime2',title:'粘贴时间',width:140},
	        {field:'registCode',align:'center',halign:'center',title:'登记编号',width:200},
	     //   {field:'jdbCompanyName',title:'街道办',width:150},
	     //   {field:'wgCompanyName',title:'使用单位',width:150},
	    //    {field:'ywCompanyName',title:'维保单位',width:110},
	    //    {field:'zzCompanyName',title:'制造单位',width:70},
	    //    {field:'azCompanyName',title:'安装单位',width:70},
	    //    {field:'jyCompanyName',title:'检验单位',width:55},
	    //    {field:'inspector',title:'检验人员',width:55},
	    //    {field:'inspectDate',title:'检验日期',width:55},
	   //     {field:'subTime',title:'最近一次维保日期',width:135,formatter: function(value,rec,index) {
       //         if(value!=null)
       //             return value.substring(0,16);
	   //	         }},
	        {field:'2',title:'任务详情',width:$(this).width() * 0.09,align:'center',
	            formatter: function(value,rec,index) {
	       // 	return  "<img src='<%=request.getContextPath()%>/images/ddtaskcompleteinfo.png' alt='任务信息' style='cursor:hand;' onclick='taskcompleteinfo("+rec.id+")'/>";    
	            return '<a href="#" onclick="taskcompleteinfo('+rec.id+')"><i class="fa fa-tasks fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
	  	  	        
	            }
             },
	        {field:'1',title:'单位信息',width:$(this).width() * 0.09,align:'center',
	            formatter: function(value,rec,index) {
	      //  	return  "<img src='<%=request.getContextPath()%>/images/companyinfo.png' alt='公司信息' style='cursor:hand;' onclick='companyinfo("+rec.id+")'/>";    
	            return '<a href="#" onclick="companyinfo('+rec.id+')"><i class="fa fa-building fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
	  	        	
 	            }
             },
	   //     {field:'zjCompanyName',title:'质监局'},
	   //     {field:'deviceId',title:'黑匣子设备'},
             <% if("0".equals(cityName)){ %>
	          {field:'jyjyFlag',align:'center',halign:'center',title:'校验状态',width:100,formatter: function(value,rec,index) {
	                if(value==1)
	                    return "已校验";
	                else
	  	                return "未校验";
			         }},
			    <% }%>
	        
	        {field:'3',title:'图片',width:$(this).width() * 0.09,align:'center',formatter: function(value,rec,index) {
    	        var registNumber = ''+rec.registNumber;
    	        var picregistNumber=rec.picregistNumber;
    	        var subTime = (''+rec.subTime2).substring(0,16); 
    	        if(picregistNumber.length==6)
        	//    return   "<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='图片' style='cursor:hand;' onclick='openPicInfo("+"\""+picregistNumber+"\""+","+"\""+subTime+"\""+")'/>";
    	         return value+'<a href="#" onclick="openPicInfo('+'\''+picregistNumber+'\''+','+'\''+subTime+'\''+')"><i class="fa fa-camera-retro fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	
    	        else
    	    //   	return  "<img src='<%=request.getContextPath()%>/images/shenhe.png' alt='图片' style='cursor:hand;' onclick='openPicInfo("+"\""+registNumber+"\""+","+"\""+subTime+"\""+")'/>";
    	        	return '<a href="#" onclick="openPicInfo('+'\''+registNumber+'\''+','+'\''+subTime+'\''+')"><i class="fa fa-camera-retro fa-lg" aria-hidden="true" style="color:#61B5CF;"></i></a>';
  	        	
    	    	 }}
	    ]],
	    nowrap:true,
	    pagination:true,
	    singleSelect:true,
	    <% if(isliulan == 0){%>
	    <% if(role==2 || role==1){%>
	    toolbar:[{
	        text:'新增',
	        iconCls:'icon-add',
	        handler:function(){
	        
	     // if(chushixialaliebiaoNumber == 0){
		//    	chushixialaliebiao();   
		//	    } 
	    	win.window('open');  
	    	multopt =0;
	    //	form.form('clear');
	        jdbSelectValue();
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
	    		 multopt =1;
	    		 colseWinDetail();
	    	//	 editFun(row.registNumber,row.deviceId);
	    	     editFun(row.registNumber,row.deviceId,row.registCode,row.jyjyFlag);   //加入校验后，如果校验通过，不能修改注册代码
	    		 form.form('load', '/tcweb/elevator/ddedit/'+row.id);
	    		 form.url = '/tcweb/elevator/ddupdate/'+row.id; 
	    		 showWinDetail2(row.registNumber,row.subTime2.substring(0,16),row.picregistNumber);  
	    		 opt =1;
	    	 } else {  
	    		 $.messager.show({   
	    			 title:'警告',
	    			 msg:'请先选择记录行。'
	    		 });   
	    	 }  
	        }
	    },{
	        text:'导入',
	        iconCls:'icon-redo',
	        handler:function(){
	        	winDr.window('open');
	        }
	    }
	    ],
	    <%}%>
	    <%}%>
	    onDblClickRow :function(rowIndex,rowData){  
			bianjixiala(rowData.zzCompanyId,rowData.zzCompanyName,rowData.azCompanyId,rowData.azCompanyName,rowData.wgCompanyId,rowData.wgCompanyName,rowData.ywCompanyId,rowData.ywCompanyName,rowData.jyCompanyId,rowData.jyCompanyName,rowData.zjCompanyId,rowData.zjCompanyName,rowData.townshipStreets,rowData.jdbCompanyName);	
		    win.window('open');
		    form.form('load', '/tcweb/elevator/ddedit2/'+rowData.id);
		    showWinDetail(rowData.registNumber,rowData.subTime2.substring(0,16),rowData.picregistNumber);  
		    rukuid=rowData.id;   
		    ruKuValid=rowData.ruKuValid;
	    }
	});	
	$('#dtt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,30,45,60]}); 
	
	//导入数据识别码异常
	$('#shibieCodett').datagrid({
		    title:'导入数据识别码已存在',
		    //pageSize:5,
		    //pageList:[5,10,15,20,25,30],
		    url:'',
		    
		   columns:[[
			        {field:'address',title:'地址',width:200,align:'center'},
			        {field:'registNumber',title:'电梯编号',width:60,align:'center'},
			        {field:'registCode',title:'注册编号',width:150,align:'center'},
			        {field:'area',title:'区域',width:50,align:'center'},
			        {field:'buildingName',title:'楼盘名称',width:130,align:'center'},
			        {field:'building',title:'栋',width:30,align:'center'},
			        {field:'unit',title:'单元',width:25,align:'center'},
			        {field:'shibieCode',title:'识别码',width:60,align:'center'},
			    ]],
	        nowrap:true,
		    //pagination:true
		
	});

	//导入数据注册码异常
	$('#registCodett').datagrid({
		    title:'导入数据注册码已存在',
		    //pageSize:5,
		    //pageList:[5,10,15,20,25,30],
		    url:'',
		    
		   columns:[[
			    {field:'address',title:'地址',width:200,align:'center'},
		        {field:'registNumber',title:'电梯编号',width:60,align:'center'},
		        {field:'registCode',title:'注册编号',width:150,align:'center'},
		        {field:'area',title:'区域',width:50,align:'center'},
		        {field:'buildingName',title:'楼盘名称',width:130,align:'center'},
		        {field:'building',title:'栋',width:30,align:'center'},
		        {field:'unit',title:'单元',width:25,align:'center'},
		        {field:'shibieCode',title:'识别码',width:60,align:'center'},
			    ]],
	        nowrap:true,
		    //pagination:true
		
	});
}
);



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
	$('#shenhe option:first').attr('selected','selected');
	$('#qresource option:first').attr('selected','selected');
	$("#qstartTime").datebox("setValue","");  
	$("#qendTime").datebox("setValue","");  
}

function query(){  
	var registNumber=$('#registNumber').attr("value");
	var address=$('#addressinfo').attr("value");
	var buildingName=$('#buildingNameInfo').attr("value");
 // var ywCompanyId=$('#ywCompanyIdinfo').combobox('getValue'); 
    var ywCompanyId=$('#ywCompanyIdinfo2').attr("value"); 
    var useNumber =$('#useNumberInfo').attr("value");
    var area=$('#areainfo').combobox('getValue'); 
  //  var area=$('#areainfo option:selected').val();
    
    var registCode =$('#registCodeinfo').attr("value");
    if (!ywCompanyId){
    	ywCompanyId =0;
    	}
    var shenhe = $('#shenhe option:selected').val();
    var qresource = $('#qresource option:selected').val();
    var qstartTime=$('#qstartTime').datebox("getValue");  
	var qendTime=$('#qendTime').datebox("getValue"); 

    /*
	if(""==registNumber && ywCompanyId==0){
	   if(""==ywName && ""==area){	
		   if(""==address && ""==buildingName){
			   if(""==registCode){
				   if(""==useNumber){
					 if(shenhe ==2)	
	                    grid.datagrid("options").url='/tcweb/elevator/ddelevatorlist';
					 else{
						 grid.datagrid("options").url='/tcweb/elevator/ddquery';
						 grid.datagrid("options").queryParams={'shenhe':shenhe};  
					 }	 
				   }
				   else{
					   grid.datagrid("options").url='/tcweb/elevator/ddquery';
					   grid.datagrid("options").queryParams={'useNumber':useNumber,'shenhe':shenhe};  
					   }
			   }
			   else{
			   grid.datagrid("options").url='/tcweb/elevator/ddquery';
			   grid.datagrid("options").queryParams={'registCode':registCode,'useNumber':useNumber,'shenhe':shenhe};  
			   }
			   
	       }
		   else{
			   grid.datagrid("options").url='/tcweb/elevator/ddquery';
			   grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'buildingName':buildingName,'registCode':registCode,'useNumber':useNumber,'shenhe':shenhe};  
			   }
	   }
	   else{
		   if(""==address){
		   grid.datagrid("options").url='/tcweb/elevator/ddquery';
		   grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'buildingName':buildingName,'registCode':registCode,'useNumber':useNumber,'shenhe':shenhe};
		   }
		   else{
			grid.datagrid("options").url='/tcweb/elevator/ddquery';
			grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'buildingName':buildingName,'registCode':registCode,'useNumber':useNumber,'shenhe':shenhe};
			   }
	  }
	}
   else{
     grid.datagrid("options").url='/tcweb/elevator/ddquery';
     grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'buildingName':buildingName,'registCode':registCode,'useNumber':useNumber,'shenhe':shenhe};
   }
   
   */
   grid.datagrid("options").url='/tcweb/elevator/ddquery';
   grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName,'area':area,'address':address,'buildingName':buildingName,'registCode':registCode,'useNumber':useNumber,'shenhe':shenhe,'qresource':qresource,'qstartTime':qstartTime,'qendTime':qendTime};
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
	$('input').attr("disabled","");
	}

function showWinDetail2(registNumber,subTime,picregistNumber){
	 if(picregistNumber.length == 6){
	 $('#img4').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+picregistNumber+"&subTime="+subTime+"&index="+1);
	 $('#img5').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+picregistNumber+"&subTime="+subTime+"&index="+2);	
	 }
	 else{
	  $('#img4').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+registNumber+"&subTime="+subTime+"&index="+1);
	  $('#img5').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+registNumber+"&subTime="+subTime+"&index="+2);	
		 }
}
function showWinDetail(registNumber,subTime,picregistNumber){  
	$("form input").css({border:'0px solid' });
    
	$('input').attr("disabled","disabled");
	
	$('#registNumber').attr("disabled","");
	$('#addressinfo').attr("disabled","");
	$('#buildingNameInfo').attr("disabled","");
	$('#useNumberInfo').attr("disabled","");
	$('#registCodeinfo').attr("disabled","");
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
	$('#btn-ok').show();
	$('#btn-edit').show();

	$('#imgpic').hide();

	//展示上传图片
	 if(picregistNumber.length == 6){ 
	  $('#img4').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+picregistNumber+"&subTime="+subTime+"&index="+1);
	  $('#img5').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+picregistNumber+"&subTime="+subTime+"&index="+2);		
	  }
	 else{
	   if(registNumber.length == 6){ 
	      $('#img4').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+registNumber+"&subTime="+subTime+"&index="+1);
	      $('#img5').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+registNumber+"&subTime="+subTime+"&index="+2);	
	   }
	   else{ 
		   $('#img4').attr("src","");
		   $('#img5').attr("src","");	
		   }
	 }
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
	$('#nextInspectDate').datebox('enable');
	$('#inspectDate').datebox('enable');
	
	$(".fontShow").show();	
	$('#btn-save,#btn-cancel').show();  
	$('#btn-ok').hide();
	$('#btn-edit').hide();

	$('#imgpic').show();
	
}

var multopt =0;    //0:新建，1修改 

function saveCar2d(){ 
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){
		var registNumber=$('#registNumberinfo').attr("value"); 
		var address= $('#address').attr("value"); 
		var area=$('#area').combobox('getValue');


		var wgCompanyId=$('#wgCompanyId').attr("value"); 
	    var ywCompanyId=$('#ywCompanyId').attr("value"); 
	    var zzCompanyId=$('#zzCompanyId').attr("value"); 
	    var azCompanyId=$('#azCompanyId').attr("value"); 
	    var jyCompanyId=$('#jyCompanyId').attr("value"); 
	    var zjCompanyId=$('#zjCompanyId').attr("value");
	    var townshipStreets=$('#townshipStreets').combobox('getValue');
	    var eleType =$('#eleType').combobox('getValue');
	    var inoutDoor=$('#inoutDoor').combobox('getValue');
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
		
        if(multopt == 0){
		if(!eleType)
		$('#eleType').combobox('setValue','曳引与强制驱动电梯');
        if (!inoutDoor)
        $('#inoutDoor').combobox('setValue','室内');
     	
  	    if(!wgCompanyId)
  	    	$('#wgCompanyId').attr("value",0); 
		if(!ywCompanyId)
			$('#ywCompanyId').attr("value",0);
        if(!zzCompanyId)
        	$('#zzCompanyId').attr("value",0); 
  	    if(!azCompanyId)
  	    	$('#azCompanyId').attr("value",0);
		if(!jyCompanyId)
			 $('#jyCompanyId').attr("value",0); 
		if(!zjCompanyId)
			 $('#zjCompanyId').attr("value",0);
		
        }
		
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

		if(opt==1){
			  <% if("0".equals(cityName)){ %>
	        	if(oldRegistCode != registCode && oldRegistCode != ""){
	            	$('#registCode').attr("value",oldRegistCode);
	        		$.messager.alert('操作失败', '已校验电梯不能修改注册代码', 'error');
	        		return false;
	            	}
	        	<% }%>
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
         
			if (!area) {
				$.messager.alert('操作失败', '行政区划不能为空', 'error');
				return false;
			}
          
			if (!townshipStreets) {
				$.messager.alert('操作失败', '街道办不能为空', 'error');
				return false;
			}
			
			/*
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
			$.messager.alert('操作失败', '操作失败', 'error');
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
  var oldRegistCode;

  /*
  function editFun(registNumber,deviceId){
      opt =1;
      oldRegistNumber =registNumber;
      oldDeviceId =deviceId;
	  }
	  */
   function editFun(registNumber,deviceId,registCode,jyjyFlag){
	      opt =1;
	      oldRegistNumber =registNumber;
	      oldDeviceId =deviceId;
	      if(jyjyFlag == 1)
	    	  oldRegistCode = registCode;
	      else
	    	  oldRegistCode ="";
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
		 	            $('input').attr("disabled","");
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

  function checktishi(pid){ 
	  var id =pid;
	  jQuery.post('/tcweb/elevator/checktishi',
		    	 {'id':id},
		    	 function(data){
		    	 	 checktishiwin.window('open');
		    		 $('#shenHeState').attr("value",data.shenHeState);
		    		 $('#shenHeBeiZhu').attr("value",data.shenHeBeiZhu);
		    		 $('#shehePersonName').attr("value",data.shehePersonName);
		    		 $('#shenheTime').attr("value",data.shenheTime);
		    		 $('#img6').attr("src","<%=request.getContextPath()%>"+"/servlet/ywImage2.jpg?registNumber="+data.registNumber+"&subTime="+data.shenheTime+"&index="+1);	
		    		// alert(data.shenHeBeiZhu);
		    		},'json'
	    	        );
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


  function taskcompleteinfo(pid){   
	  var id =pid;
	  jQuery.post('/tcweb/elevator/taskcompleteinfotishi',
		    	 {'id':id},
		    	 function(data){
		    		 taskcompleteinfowin.window('open');
		    		 $('#arrangePersonName').attr("value",data.arrangePersonName);
		    		 $('#arrangeTime2').attr("value",data.arrangeTime2);
		    		 $('#pastePersonName').attr("value",data.pastePersonName);
		    		 $('#pasteTime2').attr("value",data.pasteTime2);
		    		 $('#subPersonName').attr("value",data.subPersonName);
		    		 $('#mobileUploadbeizhu').attr("value",data.mobileUploadbeizhu); 
		    		 $('#pasteNote').attr("value",data.pasteNote);   
		    		},'json'
	    	        );
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

		    chushixialaliebiaoNumber = 1;

	  }

 /*
 function chushixialaliebiao2(){
     var cmbData =[{
    	    "id":66,
    	    "text":"上海三菱"
    	}];
    	comb3 =$('#zzCompanyId').combobox({
			data:cmbData,
		    valueField:'id',
		    textField:'text'
		});  

	 }
*/

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
	    //    zzId = zzcompanyId;
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
	      //  azId = azcompanyId;
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
	  //      wgId = wgcompanyId;
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
	  //      ywId = ywcompanyId;
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
	     //   jyId = jycompanyId;
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
	   //     zjId = zjcompanyId;
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
		 }  */
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
		 }  */
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
		 }  */
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

   function jdbSelectValue(){
	   comb7.combobox({
           url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI($('#area').combobox('getValue')),
           valueField: 'id',
           textField: 'companyName'
       }).combobox('clear');
	   }

   function jdbSelectValue2(area){ 
	   comb7.combobox({
           url: '/tcweb/elevator/getjdbCompanyListByarea?companyArea='+encodeURI(area),
           valueField: 'id',
           textField: 'companyName'
       }).combobox('clear');
	   }
   
   //批量导入
   function daoruZhantieData(){
	   $('#drFrom').form('submit', {  	
		   url: '/tcweb/elevator/daoruZhantieData',  	
		   onSubmit: function(){ 
			   var filename = $("#zhantieData").val();
			   var ext = filename.substring(filename.lastIndexOf(".") + 1);
			   //alert(ext);
			   if(ext == "xlsx" || ext == 'xls' ){
				    $.messager.progress();   
				    //alert("tongduo");
			   } else {
				    $.messager.alert('Warning','上传文件格式错误！','warning'); 
			   		return false;
			   }
			   
		   },  	
		   success: function(data){  
			   $.messager.progress('close');   
			   //关闭窗口
			   winDr.window('close');
			   //console.log(data);
			   //处理异常窗口
			   var dataObject = JSON.parse(data);
			   var shibieCodeyc = dataObject.shibieCodes;
			   var registCodeyc = dataObject.registCodes;
			   $('#shibieCodett').datagrid('loadData',shibieCodeyc);
			   $('#registCodett').datagrid('loadData',registCodeyc);
			   /* for(var i=0;i<shibieCodeyc.length;i++) {
				   $('#shibieCodett').datagrid('appendRow',shibieCodeyc[i]);
			   }
			   for(var i=0;i<registCodeyc.length;i++) {
				   $('#registCodett').datagrid('appendRow',registCodeyc[i]);
			   } */
			   $('#rukuCount').html(dataObject.daoruCount);
			   winDryc.window('open');
		   }  
		});  
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
 <fieldset id="addDiv" style="width: 100%;margin:0px;border: 1px solid #61B5CF;"><legend style="#c8d9f5;font-weight:bold">查询条件</legend>
    
      <table border="0" id="sousuo" style="border-collapse:separate; border-spacing:0px 5px;width:100%"> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
   <% if("1".equals(cityName)){ %>
   <td nowrap>N<input id="registNumber" name="registNumber" ></input></td>
    <% } else {%>
   <td nowrap><input id="registNumber" name="registNumber" ></input></td>
 <% }%>
 <td align="right" nowrap>登记编号：</td>
 <td nowrap><input id="registCodeinfo" name="registCodeinfo"></input></td>
   
   <td align="right" nowrap>楼盘名称：</td>
   <td nowrap><input id="buildingNameInfo" name="buildingName" ></input></td>
    <td align="right" nowrap>内部编号：</td>
   <td nowrap><input id="useNumberInfo" name="useNumberName" ></input></td>
   </tr>  
  <tr>  
  <td align="right" nowrap>地址：</td> 
   <td nowrap><input id="addressinfo" name="addressinfo" ></input></td>
 <td align="right" nowrap>行政区划：</td>
   <td> 
<!--     <select id="areainfo"   name="areainfo" style="width:100px;" onchange="getCompanyListByArea()"> -->
<!--    <select id="areainfo"   name="areainfo" style="width:100px;">
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
</select>  -->
<input id="areainfo" name="areainfo" style="height: 25px"/>
</td>
 
 
 <td  align="right" nowrap>核查状态：</td> 
   <td>
   <select id="shenhe"   name="shenhe" style="height:25px">
   <option value="2"></option>
   <option value="0">未核查</option>
   <option value="1">已核查</option>
</select>
</td> 
 <td align="right" nowrap>数据来源：</td>
   <td>
   <select id="qresource"   name="qresource" style="height:25px;">
   <option value="2"></option>
   <option value="0">特检院</option>
   <option value="1">非特检院</option>
   </select>
   </td>
					
   </tr>
   <tr>
   <td align="right" nowrap>维保单位：</td>
   <td> 
   <!--  <select id="ywCompanyIdinfo"  class="easyui-combobox" name="ywCompanyIdinfo" style="width:152px;"> </select>-->
   <input id="ywCompanyIdinfo" style="height:25px" placeholder="输入至少两个关键字从下拉列表中选择">
   <input type ="hidden" id="ywCompanyIdinfo2">
 </td>
   
    <td align="right" nowrap>开始时间：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" style="height:25px"></input></td>
   <td align="right" nowrap>结束时间：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime" style="height:25px"></input></td> 
   <td></td>
   <td></td>
   <td colspan="2">
 <!--   <a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
	<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 		-->	
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
<div id="car-window2" title="详细信息" style="width:680px;height:500px;"> 
  <div class="easyui-layout" data-options="fit:true" style="overflow-y:auto;">
  <div style="width:632px;height:240px;overflow-x:auto;overflow-y:hidden;border: 1px solid #000000;">  
 <div id="ywImg4" style="float:left"><img src="" id="img4" style="width:316px;height:240px;"></div>
 <div id="ywImg5" style="float:left"><img src="" id="img5" style="width:316px;height:240px;"></div>
  </div>
  <form method="post" id="form1">    
  <table>   
   <tr>      
   <td width="150" align="center" style="background-color:#F5F5F5;">电梯编号：</td> 
   <% if("1".equals(cityName)){ %>     
   <td>N<input class="form_input" id="registNumberinfo" name="registNumber" class="easyui-validatebox textbox" data-options="validType:'length[0,6]'"></input></td>  
     <% } else {%>
    <td><input class="form_input" id="registNumberinfo" name="registNumber" class="easyui-validatebox textbox" data-options="validType:'length[0,6]'"></input></td>  
 <% }%>
    <td align="center" style="background-color:#F5F5F5;">登记编号：</td>      
   <td><input class="form_input" id="registCode" name="registCode" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input></td>
   </tr>
   <% if(!"0".equals(cityName)){ %>
    <tr> 
    <td align="center" style="background-color:#F5F5F5;">识别码：</td>      
   <td><input class="form_input" id="shibieCode" name="shibieCode" class="easyui-validatebox textbox" data-options="validType:'length[0,10]'"></input></td> 
   </tr>
   <% }%>
    <tr>
   <td style="background-color:#F5F5F5;height:20px;" colspan="4"></td>
   </tr>
   <tr> 
    <td align="center" style="background-color:#F5F5F5;">地址：</td>      
   <td colspan="3"><input class="form_input" id="address" name="address" class="easyui-validatebox textbox" data-options="validType:'length[0,100]'"></input></td> 
   </tr>
   <tr> 
    <td align="center" style="background-color:#F5F5F5;">行政区划：</td>
          
 <!--  <td nowrap><select id="area"  class="easyui-combobox" name="area" style="width:152px;">
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
</select><span class="fontShow"><font color="red">*</font></span>（必选）</td>   --> 
   <td><input id="area" name="area" style="height:34px;width:186px;"></input></td> 
   <td align="center" style="background-color:#F5F5F5;">所在乡镇/街道：</td>      
     <td><select id="townshipStreets"  class="easyui-combobox" name="townshipStreets" style="width:186px;height:34px;"> 
</select>  <!--  <a href="javascript:xialaSelect(1)">修改</a></td>  --> 
  <!--   <td><input id="townshipStreets2" style="background-color:#87CEEB"><input type ="hidden" id="townshipStreets" name="townshipStreets">
     <span class="fontShow"><font color="red">*</font></span></td> -->
   </tr>
    <tr> 
    <td align="center" style="background-color:#F5F5F5;">楼盘名称：</td>      
   <td><input class="form_input" id="buildingName" name="buildingName" class="easyui-validatebox textbox" data-options="validType:'length[0,100]'"></input></td> 
   </tr>
    <tr> 
    <td align="center" style="background-color:#F5F5F5;">所在栋：</td>      
   <td><input class="form_input" id="building" name="building" class="easyui-validatebox textbox" data-options="validType:'length[0,10]'"></input></td> 
   <td align="center" style="background-color:#F5F5F5;">所在单元：</td>      
   <td><input class="form_input" id="unit" name="unit" class="easyui-validatebox textbox" data-options="validType:'length[0,10]'"></input></td>
   </tr>
   <tr>
   <td align="center" style="background-color:#F5F5F5;">经度：</td>
   <td><input class="form_input" id="map_X" name="map_X"></input></td>
   </tr>
   <tr> 
   <td align="center" style="background-color:#F5F5F5;">纬度：</td>
   <td><input class="form_input" id="map_Y" name="map_Y"></input></td>
   <td><img id="imgpic2"  name="imgpic2" src="../../images/html.png" style="cursor: hand"/> </td>
   </tr>
    <tr>
   <td style="background-color:#F5F5F5;height:20px;" colspan="4"></td>
   </tr>
   <tr>
   <td align="center" style="background-color:#F5F5F5;">名称：</td>      
   <td><input class="form_input" id="name" name="name" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input></td> 
   </tr>
   <tr>
   <td align="center" style="background-color:#F5F5F5;">电梯类别：</td>      
   <td> 
   <select id="eleType"  class="easyui-combobox" name="eleType" style="width:186px;height:34px;">
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
</select>
   </td>
   <td align="center" style="background-color:#F5F5F5;">室内外：</td>      
   <td> 
   <select id="inoutDoor"  class="easyui-combobox" name="inoutDoor" style="width:186px;height:34px;">
    <option value="室内">室内</option>
    <option value="室外">室外</option>  
</select>
   </td>
   </tr>
   <tr>
    
   </tr>
    <tr>
   <td align="center" style="background-color:#F5F5F5;">注册代码：</td>      
   <td><input class="form_input" id="registCode2" name="registCode2" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input></td>
   <td align="center" style="background-color:#F5F5F5;">注册登记人员：</td>      
   <td><input class="form_input" id="registor" name="registor" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input></td>
   </tr>
   <tr>
   <td style="background-color:#F5F5F5;height:20px;" colspan="4"></td>
   </tr>
   <tr>
    <td align="center" style="background-color:#F5F5F5;">产权单位：</td>
    <td><input class="form_input" id="propertyRightsUnit" name="propertyRightsUnit" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td> 
   </tr>
   <tr>
   <td style="background-color:#F5F5F5;height:20px;" colspan="4"></td>
   </tr>
    <tr>      
   <td align="center" style="background-color:#F5F5F5;">制造单位：</td>      
   <td>
   <!--  <select  id="zzCompanyId"  class="easyui-combobox" name="zzCompanyId2" style="width:102px;"></select> -->
    <input id="zzCompanyId2" name="zzCompanyId2" style="height:34px;width:186px;"><input type ="hidden" id="zzCompanyId" name="zzCompanyId"></td>
    </tr>
    <tr>
      <td align="center" style="background-color:#F5F5F5;">出厂编号：</td>      
  <td><input class="form_input" id="factoryNum" name="factoryNum" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>  
       
   <td align="center" style="background-color:#F5F5F5;">制造日期：</td>      
   <td><select id="manufactDate"  class="easyui-datebox" name="manufactDate" style="width:186px;height:34px;"> 
</select></td>
   </tr> 
    <tr>
   <td style="background-color:#F5F5F5;height:20px;" colspan="4"></td>
   </tr>
   <tr> 
<td align="center" style="background-color:#F5F5F5;">检验单位：</td>      
   <td>
   <!-- <select id="jyCompanyId"  class="easyui-combobox" name="jyCompanyId2" style="width:102px;"></select>  -->
 <input id="jyCompanyId2"  name="jyCompanyId2" style="height:34px;width:186px;"><input type ="hidden" id="jyCompanyId" name="jyCompanyId">
 <!--  <a href="javascript:xialaSelect(3)">修改</a> --> 
 </td>
 </tr>
 <tr>
 <td align="center" style="background-color:#F5F5F5;">检验人员：</td>      
   <td><input class="form_input" id="inspector" name="inspector" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'" style="width:186px;"></input></td>
  <td align="center" style="background-color:#F5F5F5;">报告编号：</td>      
   <td><input class="form_input" id="checkReportNum" name="checkReportNum" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>
  
   </tr>
   <tr>
   <td align="center" style="background-color:#F5F5F5;">检验类别：</td>      
   <td><input class="form_input" id="checkCategory" name="checkCategory" class="easyui-validatebox textbox" style="width:186px;" data-options="validType:'length[0,50]'"></input></td>
   <td align="center" style="background-color:#F5F5F5;">检验结论：</td>      
   <td><input class="form_input" id="checkResult" name="checkResult" class="easyui-validatebox textbox" style="width:186px;" data-options="validType:'length[0,50]'"></input></td>
   </tr>
   <tr>
     <td align="center" style="background-color:#F5F5F5;">检验日期：</td>      
   <td><input id="inspectDate"  type="text" class="easyui-datebox" name="inspectDate" style="width:186px;height:34px"></input></td>
    <td align="center" style="background-color:#F5F5F5;">下次检验日期：</td>      
   <td><input id="nextInspectDate"  type="text" class="easyui-datebox" name="nextInspectDate" style="width:186px;height:34px;"></input></td>
   </tr>
   <tr>
   <td style="background-color:#F5F5F5;height:20px;" colspan="4"></td>
   </tr>
   <tr>
     <td align="center" style="background-color:#F5F5F5;">安装单位：</td>      
  
  <!--  <select id="azCompanyId"  class="easyui-combobox" name="azCompanyId2" style="width:102px;"> </select>  -->
  <td> <input id="azCompanyId2"  name="azCompanyId2" style="height:34px;width:186px;"><input type ="hidden" id="azCompanyId" name="azCompanyId"></td>
<!--  <a href="javascript:xialaSelect(4)">修改</a> --> 
  </tr>
  <tr>
  <td align="center" style="background-color:#F5F5F5;">投用日期：</td>      
   <td><input class="form_input" id="useDate" name="useDate" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input></td>
   <td align="center" style="background-color:#F5F5F5;">竣工验收日期：</td>      
   <td><input id="completeAcceptanceDate"  type="text" class="easyui-datebox" name="completeAcceptanceDate" style="width:186px;height:34px;"></input></td>
   </tr>
   <tr>
<td align="center" style="background-color:#F5F5F5;">验收检验机构：</td>      
   <td><input class="form_input" id="acceptanceDateDepart" name="acceptanceDateDepart" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>
   <td align="center" style="background-color:#F5F5F5;">验收报告编号：</td>      
   <td><input class="form_input" id="acceptanceReportNum" name="acceptanceReportNum" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>
   </tr>
    <tr>
   <td style="background-color:#F5F5F5;height:20px;" colspan="4"></td>
   </tr>
     <tr>
   <td align="center" style="background-color:#F5F5F5;">使用单位：</td>       
   <td>
  <!--   <select id="wgCompanyId"  class="easyui-combobox" name="wgCompanyId2" style="width:102px;"></select> -->
    <input id="wgCompanyId2" name="wgCompanyId2" style="height:34px;width:186px;"><input type ="hidden" id="wgCompanyId" name="wgCompanyId">
   <!--  <a href="javascript:xialaSelect(5)">修改</a> -->
   </td>
   </tr>
   <tr> 
    <td align="center" style="background-color:#F5F5F5;">单位内部编号：</td>      
   <td><input class="form_input" id="useNumber" name="useNumber" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input></td> 
   </tr>
   <tr>
   <td align="center" style="background-color:#F5F5F5;">安全管理部门：</td>
   <td><input class="form_input" id="safetyManDepart" name="safetyManDepart" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>
   <td align="center" style="background-color:#F5F5F5;">安全管理人员：</td>
   <td><input class="form_input" id="safetyManPerson" name="safetyManPerson" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input></td>
    </tr>
   <tr>
   <td align="center" style="background-color:#F5F5F5;">安全管理人员电话：</td>
   <td><input class="form_input" id="safetyManPersonTel" name="safetyManPersonTel" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input></td>
   </tr>
    <tr>
   <td style="background-color:#F5F5F5;height:20px;" colspan="4"></td>
   </tr>
   <tr>
   <td align="center" style="background-color:#F5F5F5;">维保单位：</td>      
   <td>
  <!--  <select id="ywCompanyId"  class="easyui-combobox" name="ywCompanyId2" style="width:102px;"></select>  -->
   <input id="ywCompanyId2" name="ywCompanyId2" style="height:34px;width:186px"><input type ="hidden" id="ywCompanyId" name="ywCompanyId">
   <!-- <a href="javascript:xialaSelect(6)">修改</a> -->
   </td>
   </tr>
   <tr>
  <td align="center" style="background-color:#F5F5F5;">维保合同有效期：</td>
  <td><input class="form_input" id="mContractVdate"  type="text" class="easyui-datebox" name="mContractVdate" style="width:186px;" class="easyui-validatebox textbox" data-options="validType:'length[0,30]'"></input></td>
   <td align="center" style="background-color:#F5F5F5;height:34px;">维保人员:</td>
   <td> <img id="imgpic3"  name="imgpic3" src="../../images/html.png" style="cursor: hand;"/>
   </td>
   </tr>
    <tr>
   <td style="background-color:#F5F5F5;height:20px;" colspan="4"></td>
   </tr>
   <tr>
   <td align="center" style="background-color:#F5F5F5;">市场与质量监管局：</td>      
   <td>
  <!-- <select id="zjCompanyId"  class="easyui-combobox" name="zjCompanyId2" style="width:102px;"></select>  -->
  <input id="zjCompanyId2" name="zjCompanyId2" style="height:34px;width:186px;"><input type ="hidden" id="zjCompanyId" name="zjCompanyId" value="0">
 <!-- <a href="javascript:xialaSelect(7)">修改</a> -->
 </td>
   </tr>
    <tr>
   <td style="background-color:#F5F5F5;height:20px;" colspan="4"></td>
   </tr>
   <tr>
   <td align="center" style="background-color:#F5F5F5;">变更承办单位：</td>
    <td><input class="form_input" id="handleCompany" name="handleCompany" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>
   </tr>
   <tr>
   <td align="center" style="background-color:#F5F5F5;">变更承办单位代码：</td>
   <td><input class="form_input" id="handleCompanyCode" name="handleCompanyCode" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>
   </tr>
   <tr>
   <td align="center" style="background-color:#F5F5F5;">变更方式：</td>
   <td><input class="form_input" id="changeWay" name="changeWay" class="easyui-validatebox textbox" data-options="validType:'length[0,50]'"></input></td>
   </tr>
     <tr>
   <td style="background-color:#F5F5F5;height:20px;" colspan="4"></td>
   </tr>
   <tr>
    <td align="center" style="background-color:#F5F5F5;">电梯型号：</td>      
   <td><input class="form_input" id="eleMode" name="eleMode" class="fontShow" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input></td> 
   </tr>
   <tr>
   <td align="center" style="background-color:#F5F5F5;">额定荷载：</td>      
   <td><input class="form_input" id="eleLoad" name="eleLoad" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input></td>
   <td align="center" style="background-color:#F5F5F5;">速度：</td>      
   <td><input class="form_input" id="speed"  name="speed" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input></td>
   </tr>
    <tr>      
   <td align="center" style="background-color:#F5F5F5;">提升高度：</td>      
   <td><input class="form_input" id="eleheight" name="eleheight" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input></td> 
    <td align="center" style="background-color:#F5F5F5;">宽度：</td> 
   <td><input class="form_input" id="elewidth" name="elewidth" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input></td>
   </tr>
    <tr>  
     <td align="center" style="background-color:#F5F5F5;">层站：</td>      
   <td><input class="form_input" id="eleStop" name="eleStop" class="easyui-validatebox textbox" data-options="validType:'length[0,20]'"></input></td>    
  <td></td>
  <td></td>
   </tr>  
   <tr>
   <td style="background-color:#F5F5F5;height:20px;" colspan="4"></td>
   </tr>
   <tr>
    <td align="center" style="background-color:#F5F5F5;">黑匣子设备：</td>      
   <td><input class="form_input" id="deviceId" name="deviceId" readonly style="background-color:#c0c0c0"></input>
  <img id="imgpic"  name="imgpic" src="../../images/search2.gif" style="cursor: hand" />
   </td> 
   <td align="center" style="background-color:#F5F5F5;">备注：</td> 
   <td><input class="form_input" id="note" name="note" class="easyui-validatebox textbox" data-options="validType:'length[0,300]'"></input></td>
   </tr>
    <tr>
    <td></td><td><input id="luruPersonID" name="luruPersonID" type="hidden" value="<%=userId%>"></input></td>
    </tr>
    <tr>
    <td align="center" style="background-color:#F5F5F5;">微信码：</td>      
   <td><input class="form_input" id="deviceId2" name="deviceId2"></input></td></tr>
   </table>
    <table width=100%>
     <tr style="height:10px"></tr>
    <tr>
    <td align="center">
   <!--    <a href="javascript:void(0)" onclick="saveCar2d()" id="btn-save" icon="icon-save">保存</a>   --> 
     <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-save fa-lg" onclick="saveCar2d()" style="width:100px;color:#3399FF;">保存</a>
   <!--    <a href="javascript:void(0)" onclick="closeWindow()" id="btn-cancel" icon="icon-cancel">关闭</a>  -->
    <a href="#" class="easyui-linkbutton" icon="e-icon fa fa-close fa-lg" onclick="closeWindow()" style="width:100px">取消</a>
      <a href="javascript:void(0)" onclick="ruku()" id="btn-ok" icon="icon-ok">入库</a> 
      <a href="javascript:void(0)" onclick="hecha()" id="btn-edit" icon="icon-edit">核查</a> 
    </td>
    </tr> 
   </table>   
   </form> 
    </div> 
     
   </div>
  
   <!--  批量导入 -->
   <div id="car-windowDr" title="详细信息" style="width:300px;height:200px;">
   		<form id='drFrom' enctype="multipart/form-data" action="/tcweb/elevator/daoruZhantieData" method="post">
   			<table align="center">
   				<tr>
   					<td>
	   					<input type="file" name="zhantieData" id='zhantieData'/>
   					</td>
   				</tr>
   				<tr align="center">
   					<td>
				   		<input type="button" value="导入" onclick='daoruZhantieData()'/>
   					</td>
   				</tr>
   			</table>
   		</form>
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

<div id="checktishi-window" title="现场核查结果" style="width:470px;height:460px;overflow-x:auto;overflow-y:hidden">
 <div style="width:450px;">
 <div>
  <table>
  <tr>      
  <td nowrap>现场核查人：</td>
   <td><input  id="shehePersonName" name="shehePersonName"></input></td>
  <td nowrap>核查上传时间：</td>
   <td><input  id="shenheTime" name="shenheTime"></input></td>
   </tr>
   <tr>      
   <td nowrap>现场核查结果：</td>
   <td><textarea rows="3" cols="18" id="shenHeState" name="shenHeState"></textarea></td>
    <td nowrap>现场核查备注：</td>
   <td><textarea rows="3" cols="18" id="shenHeBeiZhu" name="shenHeBeiZhu"></textarea></td>
   </tr>
   
   
 </table>
 <hr>
 </div>
 <div id="ywImg6" style="float:left"><img src="" id="img6" style="width:445px;height:430px"></div>
 </div> 
</div>

<div id="companyinfo-window" title="公司信息" style="width:600px;height:430px;overflow-x:auto;overflow-y:hidden">
 <table>
  <tr>      
  <td nowrap width="150" align="center" style="background: #F5F5F5;">街道办</td>
  <td colspan="3"><input  class="form_input" id="jdbCompanyName" name="jdbCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
  </tr>
  <tr>
  <td nowrap width="150" align="center" style="background: #F5F5F5;"> 使用单位</td>
   <td colspan="3"><input  class="form_input" id="wgCompanyName" name="wgCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   <tr>      
   <td nowrap width="150" align="center" style="background: #F5F5F5;">维保单位</td>
   <td colspan="3"><input class="form_input" id="ywCompanyName" name="ywCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   <tr>
   <td nowrap width="150" align="center" style="background: #F5F5F5;">制造单位</td>
   <td colspan="3"><input  class="form_input" id="zzCompanyName" name="zzCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   <tr>
   <td nowrap width="150" align="center" style="background: #F5F5F5;">安装单位</td>
   <td colspan="3"><input class="form_input"  id="azCompanyName" name="azCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   <tr>
   <td nowrap width="150" align="center" style="background: #F5F5F5;">质监单位</td>
    <td colspan="3"><input class="form_input" id="zjCompanyName" name="zjCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
    <tr>
    <td nowrap width="150" align="center" style="background: #F5F5F5;">检验单位</td>
   <td colspan="3"><input  class="form_input" id="jyCompanyName" name="jyCompanyName" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   <tr>
    <td nowrap width="150" align="center" style="background: #F5F5F5;">检验人员</td>
   <td><input class="form_input"  id="inspector" name="inspector" style="border-width :0px 0px 1px;" readonly></input></td>
   <td nowrap width="150" align="center" style="background: #F5F5F5;">检验日期</td>
   <td><input class="form_input" id="inspectDate" name="inspectDate" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>

 </table>
</div>

<div id="taskcompleteinfo-window" title="任务完成信息" style="width:600px;height:430px;overflow-x:auto;overflow-y:hidden">
 <table>
  <tr>      
  <td  nowrap width="150" align="center" style="background: #F5F5F5;">任务分配人：</td>
  <td colspan="3"><input class="form_input" id="arrangePersonName" name="arrangePersonName" style="border-width :0px 0px 1px;" readonly></input></td>
  <tr>
  </tr>
  <td nowrap width="150" align="center" style="background: #F5F5F5;"> 任务分配时间：</td>
   <td><input class="form_input" id="arrangeTime2" name="arrangeTime2" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   <tr>      
   <td nowrap width="150" align="center" style="background: #F5F5F5;">粘贴人：</td>
   <td colspan="3"><input class="form_input"id="pastePersonName" name="pastePersonName" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   <tr>
   <td nowrap width="150" align="center" style="background: #F5F5F5;">粘贴时间：</td>
   <td><input class="form_input" id="pasteTime2" name="pasteTime2" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
   <tr>
   <td nowrap width="150" align="center" style="background: #F5F5F5;">上传人：</td>
   <td><input class="form_input" id="subPersonName" name="subPersonName" style="border-width :0px 0px 1px;" readonly></input></td>
   <td nowrap width="150" align="center" style="background: #F5F5F5;">上传备注：</td>
    <td><input class="form_input" id="mobileUploadbeizhu" name="mobileUploadbeizhu" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
    <tr>
    <td nowrap width="150" align="center" style="background: #F5F5F5;">修改内容：</td>
   <td colspan="3"><input  class="form_input" id="pasteNote" name="pasteNote" style="border-width :0px 0px 1px;" readonly></input></td>
   </tr>
 </table>
</div>

<div id="daoruReturn-window" class="easyui-layout" title="导入异常显示" style="width:1000px;height:700px;overflow-y:hidden">
	<div region="north" style="font-size: large;height: 30px;">共有<span style="color: #A10D0D;font-size: large;" id="rukuCount">0</span>条数据导入成功</div>
	<div region="center" class="middle" >
	       <div id="main-center" style="float:left;margin:0px;overflow:auto;width:50%" class="column">      
	       <table id="shibieCodett"></table>    
	    </div>  
	    
	    <div id="main-center2" style="float:left;margin:0px;overflow:auto;width:50%" class="column">      
	       <table id="registCodett"></table>    
	    </div>
	</div>
	
 
</div>
</body>
</html>