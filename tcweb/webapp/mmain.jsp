<%@ page import="com.zytx.models.*,com.zytx.init.GlobalFunction" language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyuibootstrap.css"> 
<!--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/> -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<% 
String cityName = GlobalFunction.cityName;
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int role=0;
int ispcsuper = 0;
String userName2="";
String userName = "";
String password = "";
int iskaoping = 0;
if(userinfo!=null){
	 role = userinfo.getRole();
	 ispcsuper =userinfo.getIspcsuper();
	 userName2 =userinfo.getLoginName();
	}
	else{
		 Cookie[] cookies =  request.getCookies();
		 
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
	}
    UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select t.*,te.userName,te.ispcsuper from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id=te.userid where loginName= ?",new Object[] { userName2 });
    if(user != null) {
       role = user.getRole();
       userName2 = user.getLoginName();
       iskaoping = user.getIskaoping();
       ispcsuper = user.getIspcsuper();
    }
   else
	response.sendRedirect(request.getContextPath() +"/index.jsp");
/*
if(userinfo!=null){
	 role = userinfo.getRole();
	 userName2 =userinfo.getLoginName();
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
			UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select t.*,te.userName from TwoCodeUserInfo t left join TwoCodeUserExtInfo te on t.id=te.userid where loginName= ?",new Object[] { userName });
		    role = user.getRole();
		    userName2 = user.getLoginName();
	}
*/
%>
<style type="text/css">
	*{margin: 0;padding: 0}
	body{font-size: 12px;font-family: "宋体","微软雅黑";}
	ul,li{list-style: none;}
	a:link,a:visited{text-decoration: none;}
	.list{width: 140px;border-bottom:solid 1px #316a91;margin:5px auto 0 auto;}
	.list ul li{background-color:rgb(39,76,138); border:solid 1px #316a91; border-bottom:0;}
	.list ul li a{padding-left: 10px;color: #fff; font-size:13px; display: block; font-weight:bold; height:36px;line-height: 36px;position: relative;
	}
	.list ul li .inactive{background:url(images/off.png) no-repeat 110px center;}
	.list ul li .inactives{background:url(images/off.png) no-repeat 110px center;} 
	.list ul li ul{display: none;}
	.list ul li ul li { border-left:0; border-right:0; background-color:rgb(195,219,255); border-color:#467ca2;}
	.list ul li ul li .inactives{background:url(images/on.png) no-repeat 2px center;}
	.list ul li ul li ul{display: none;}
	.list ul li ul li a{ color:rgb(39,76,138);padding-left:20px;}
	.list ul li ul li ul li { background-color:rgb(226,237,255); border-color:#6196bb; }
	.list ul li ul li ul li.inactives{background:url(images/on.png) no-repeat 2px center;}
	.last{ background-color:rgb(226,237,255); border-color:#6196bb;}
	.list ul li ul li ul li a{ color:rgb(39,76,138); padding-left:30px;}
</style>

<script type="text/javascript">

$(function(){
    
	$.ajaxSetup ({
	    cache: false 
	});

	 <% if((role != 16 && role != 17) && (role !=30 && role != 31)) {
	    if(role == 8 || role == 9){%>
	location.href="<%=request.getContextPath()%>/wgpt.jsp";
	    <% } else {%>
	addTab('电梯管理','views/elevator/elevatorlist.jsp');
     <% }}%>
     
	$('.inactive,.last,.slast').click(function(){
		if($(this).attr("class") == 'last'){
			 $(this).css("background", "rgb(255,229,141)"); 
		     $(this).parent('li').siblings('li').children('a').css("background", "rgb(226,237,255)"); 
			} 
		else if($(this).attr("class") == 'slast'){
			$(this).addClass('inactives');  
			$(this).parent('li').siblings('li').children('a').removeClass('inactives');
			$(this).parent('li').siblings('li').children('ul').slideUp(100);
		//	$(this).parent('li').siblings('li').children('a').removeClass('inactives');
		//	$(this).parent('li').siblings('li').children('a').addClass('inactive');
			
			} 
		else{    
		if($(this).siblings('ul').css('display')=='none'){ 
			$(this).parent('li').siblings('li').removeClass('inactives');
			$(this).parent('li').siblings('li').children('a').removeClass('inactives');
			$(this).addClass('inactives');
			$(this).siblings('ul').slideDown(100).children('li');

			$(this).parents('li').siblings('li').children('ul').parent('li').children('a').removeClass('inactives');  
			$(this).parents('li').siblings('li').children('ul').slideUp(100);
		//	$(this).parents('li').siblings('li').slideUp(100);
			
			if($(this).parents('li').siblings('li').children('ul').css('display')=='block'){ 
				$(this).parents('li').siblings('li').children('ul').parent('li').children('a').removeClass('inactives');  
				$(this).parents('li').siblings('li').children('ul').slideUp(100);  
			}  
		}else{                
			//控制自身变成+号
			$(this).removeClass('inactives');
			//控制自身菜单下子菜单隐藏
			$(this).siblings('ul').slideUp(100);
			//控制自身子菜单变成+号
			$(this).siblings('ul').children('li').children('ul').parent('li').children('a').addClass('inactives');
			//控制自身菜单下子菜单隐藏
			$(this).siblings('ul').children('li').children('ul').slideUp(100);
			//控制同级菜单只保持一个是展开的（-号显示）
			$(this).siblings('ul').children('li').children('a').removeClass('inactives');
		}
		}
		
	});
	
	var messagerole =<%=role%>;

	var lName = '<%=userName2%>'; 
	
	if(messagerole == 10 || messagerole == 11){
      $.post('/tcweb/company/newsIshave', {'loginName':lName},  
		            function(data) {
			             eval("data="+"'"+data+"'");    
		                 if ("success"==data){
		                        newm=$.messager.show({
		         				title:'消息通知',
		         				msg:'<a href="#" onclick="newsinfo2()">你有新的消息，请查看</a>',
		         				timeout:0,
		         				showType:'show',
		         				style:{
		         					left:'',
		         					right:0,
		         					top:document.body.scrollTop+document.documentElement.scrollTop,
		         					bottom:''
		         				}
		         			}); 
         			
		    	    		}
		                 });
   

	}
	
	 
	$.get("/tcweb/user/queryUnameByLname",{loginName:lName},function (data, textStatus){
//	alert(data);
//   	$('#tUName').html(" 你好！"+data);
		$('#tUName').html(data);
	  });

///	$('#tUName2').html("<a href='#' class='tileHead2' onclick='newsinfo()' >消息</a>");

	dd=$('#newsinfodd').dialog({   
	    title: '消息窗',   
	    width: 780,   
	    height: 380,   
	    closed: true,   
	    cache: false,     
	    modal: true  
	});   
	win = $('#news-window').window({  closed:true,draggable:false,modal:true}); 
	newsdetailwin = $('#newsdetailinfo-window').dialog({  closed:true,draggable:false,modal:true});
	rnewsdetailwin = $('#receivernewsdetailinfo-window').dialog({  closed:true,draggable:false,modal:true});
	
//	form = win.find('newsform');
	$('#btn-save').linkbutton(); 
	comb =$('#zjCompanyId').combobox({
		url:'/tcweb/elevator/getZjCompanyList',
	    valueField:'id',
	    textField:'companyName',
	    multiple:true 
	});

	$('#zjCompanyId').combobox({
    	filter: function(q, row){
    		var opts = $(this).combobox('options');
    		return row[opts.textField].indexOf(q) >= 0;
    	}
    });
});


function newsinfo2(){
	newm.window('close');
	newsinfo();
	
}

function newsinfo(){
	dd.dialog('open');
	newsgrid=$('#newstt').datagrid({
	    title:'消息列表',
	    pageSize:15,
	    pageList:[15,20,25,30,35,40],
	    url:'/tcweb/company/zhijiannewslist',
	    queryParams:{},
	    columns:[[
	        {field:'newsSender',title:'发送人',width:60},
	        {field:'newsTitle',title:'标题',width:200},
	        {field:'newsSendTime',title:'时间',width:200},
	        {field:'4',title:'内容',
	            formatter: function(value,rec,index) {
	        	 <% if(role==10 || role==11){%>
	        	 	return  "<img src='<%=request.getContextPath()%>/images/newsdetail.png' alt='详情' style='cursor:hand;' onclick='detailinfo2("+rec.id+","+rec.id2+","+index+")'/>";    
	        	 <% } else {%>	 
	        	return  "<img src='<%=request.getContextPath()%>/images/newsdetail.png' alt='详情' style='cursor:hand;' onclick='detailinfo("+rec.id+")'/>"; 
	        	<% }%> 
	            }}
	        <% if(role==10 || role==11){%>
	        ,{field:'newsStateName',title:'状态'}
	        
	        <% } 
	        if(role==22 || role==23){ %>
	        ,{field:'5',title:'接收人',
	            formatter: function(value,rec,index) {
	        	return  "<img src='<%=request.getContextPath()%>/images/newsreceiver.png' alt='详情' style='cursor:hand;' onclick='receiverdetailinfo("+rec.id+")'/>"; 
	            }}
            <% } %>
	        
	    ]],
	    pagination:true,
	    
	    toolbar:[
        <% if(role==22 || role==23){%>
	     	{
	        text:'新增',
	        iconCls:'icon-add',
	        handler:function(){
	    	win.window('open');  
	   // 	form.url ='/tcweb/company/newsadd';		 
	        }
	    },
        <%}%>
	    {
	        text:'删除',
	        iconCls:'icon-cut',
	        handler:function(){
	    	 var row = newsgrid.datagrid('getSelected'); 
	    	 if(row){
		     <%if(role==22 || role==23){ %>
		     var nid = row.id;
		     <% } else { %>
		     var nid = row.id2;
		     <% }%>
	    	 $.messager.confirm('','确定要删除',function(data){if(data){	 
	    	 jQuery.post('/tcweb/company/newsdelete',
	    	    	 {'id':nid},
	    	    	 function(data){
	    	    		eval("data="+"'"+data+"'");  
	    	    		if("success"==data){
	    	    		//	$.messager.alert("操作成功",'谢谢');
	    	    		 $.messager.show({   
	    			 title:'提示信息',
	    			 timeout:1000,
	    			 msg:'操作成功，谢谢。' 
	    		 });  	
		    	            newsgrid.datagrid('reload');
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
	    }]
	    	    
	});
	$('#newstt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  
	
}

function showpage(url){
	
	urlPath="/scbaweb/views/"+url;
	//document.all.mainFrame.src=urlPath;
	$('#mainFrame').attr("src", urlPath); 
}

function addTab(title, href){ 
    var tt = $('#main-center');   
    if (tt.tabs('exists', title)){   
    tt.tabs('select', title);   
    } else {   
    if (href){   
    var content = '<iframe scrolling="no" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>';   
    } else {   
    var content = '维护中...';   
    }   
    tt.tabs('add',{   
    title:title,   
    closable:true,   
    content:content   
    });   
    }   
}

function addTab2(title, href){ 
	  window.open('<%=request.getContextPath()%>/views/elevator/changsha.jsp','newwindow','width='+(window.screen.availWidth)+',height='+(window.screen.availHeight-30)+ ',top=0,left=0,fullscreen = yes,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');  
      
}

function exitSystem(){
	$.messager.defaults = { ok: "是", cancel: "否" };  
	$.messager.confirm("退出系统", "您确定要执行操作吗？", function(data) {if (data){   
 	location.href="/tcweb/join/systemExit";	
    if (window.opener && !window.opener.closed){
 	window.opener.location.href=window.opener.location.href;   
    }
	window.close();
	}
	});
}


function newssave(){
	
	   var r = $('#zjCompanyId').combobox('getValues'); 
	   var rr=escape(r);  
	   var newsTitle = $('#newsTitle').attr("value");
       var newsContext =$('#newsContext').attr("value");
	 $.post('/tcweb/company/newsadd', {'zjCompanyId':rr,'newsTitle':newsTitle,'newsContext':newsContext},  
	            function(data) {
		             eval("data="+"'"+data+"'");    
	                 if ("success"==data){
	    	    		 $.messager.show({   
	    			         title:'提示信息',
	    			         timeout:1000,
	    			         msg:'操作成功，谢谢。' 
	    		         });  	
	    	    		
	    	    		win.window('close');
	     	            newsgrid.datagrid('reload');
	    	    		}
	    	    		else{
	    	    			$.messager.alert('操作失败','操作失败','error');
	        	    		}
	                 });
	
	/*
	 var newsform =$('#newsform'); 
	 newsform.form('submit', {  
			url:form.url,
			onSubmit:function(){
	             //表单验证
	            return $("#newsform").form('validate');
			},
			success : function(data) { 
				eval("data=" + "'" + data + "'");  
				if ("exist" == data) {
					$.messager.alert('操作失败', '该合同编号已经存在，不能重复添加', 'error');
				} else if ("success" == data) {   
				$.messager.show( {
					title : '提示信息',
					timeout : 1000,
					msg : '操作成功，谢谢。'
				});
				grid.datagrid('reload');
				win.window('close');
			} else {
				$.messager.alert('操作失败', '操作失败', 'error');
			}
		}
			}); */
}

function detailinfo(pid){ 
	   var id =pid;
	   var id2 = 0;
	  jQuery.post('/tcweb/company/newsinfotishi',
		    	 {'id':id,'id2':0},
		    	 function(data){
		    		 newsdetailwin.window('open');
		    		 $('#newsdetailContext').html(data.newsdetailContext);  
		    		},'json'
	    	        );
	  }


function detailinfo2(pid,pid2,rowindex){
	 var id =pid;
	 var id2 =pid2;
	  $('#newstt').datagrid('updateRow',{
			index: rowindex,
			row: {
		    newsStateName: '已读'
			}
		});
	  jQuery.post('/tcweb/company/newsinfotishi',
		    	 {'id':id,"id2":pid2},
		    	 function(data){
		    		 newsdetailwin.window('open');
		    		 $('#newsdetailContext').html(data.newsdetailContext);  
		    		},'json'
	    	        );  
}

function receiverdetailinfo(id){
	var rid =id;
	rnewsdetailwin.window('open');
	newsgrid=$('#rntt').datagrid({
	    title:'接收人列表',
	    pageSize:15,
	    pageList:[15,20,25,30,35,40],
	    url:'/tcweb/company/zhijianreceiverlist',
	    queryParams:{'id':rid},
	    columns:[[
	        {field:'newsReceiverName',title:'接收人',width:200},
	        {field:'newsStateName',title:'消息状态',width:200}
	    ]],
	    pagination:true	    
	});
	$('#rntt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  
	
	
}

  


</script>
<style>
			.nav-item{
				text-align:center;
				background:#fff;
				height:80px;
			}
			.nav-item img{
				border:0;
			}
			.exitinHead {
			margin:10px;  
	padding: 5px 5px 0px 20px;
	background: transparent url('images/tuichu.png') no-repeat center  left;
	text-decoration:none;
	color:#ffffff;
	border:0px solid #91a7b4;
}
.tileHead {
    margin:10px; 
	padding: 5px 5px 0px 20px;
	background: transparent url('images/guanliyuan.png') no-repeat center  left;
	text-decoration:none;
	color:#ffffff;
	border:0px solid #91a7b4;
}

.tileHead2 { 
    margin:10px; 
	padding: 5px 5px 0px 20px;
	background: transparent url('images/zhijianshannews2.gif') no-repeat center  left;
	text-decoration:none;
	color:#ffffff;
	border:0px solid #91a7b4;
}
.tablist {position:relative;margin-top:1px;left:200px;min-height:1px;}

#tab1:target, #tab2:target, #tab3:target {
	z-index: 1;
}


/* tabmenu style */
.tabmenu {position:absolute;top:62px;right:30%;margin:0;}
	.tabmenu li{display:inline-block;}
	}

		
</style>
</head>

 <body class="easyui-layout" style="text-align:center">
    <div region="north" style="background:#fafafa;color:#2d5593;height:80px;">
    <div style="height:78px;float:right;both:clear;width:100%;background-color: silver;background-repeat: no-repeat;background-positon: 100%, 100%; position:relative;">
    <% if("1".equals(cityName)){ %>
    <img src="images/ntheadbg.png" style="width:100%;height:100%"/>
    <% } else if("2".equals(cityName)){ %>
    <img src="images/csheadbg.png" style="width:100%;height:100%"/>
    <% } else if("3".equals(cityName)){ %>
    <img src="images/njheadbg.png" style="width:100%;height:100%"/>
    <% } else {%>
    <img src="images/headbg.png" style="width:100%;height:100%"/>
    <% }%>
      <ul class="tabmenu">
      <li><a href="#tab1" class="tileHead" id="tUName" style="width:80px;"></a></li>
      <li><a href="#tab2" id="tUName2" class="tileHead2" onclick="newsinfo()" style="width:40px;">消息</a></li>
      <li><a href="#tab3" class="exitinHead" style="width:40px;" onclick="exitSystem()">退出</a></li>
     </ul>
    </div>  
  
   </div> 
      
   
    <div region="west" title="导航菜单" split="true" style="width:150px;background-color:#c8d9f5;">
      <div class="list">
         <ul class="yiji">
          
        <%if(role != 20 && role != 21) {  
              if (role != 8 && role != 9) { %>     <!--不等于街道办 ,物管-->
             <li><a href="#" class="inactive" style="font-size:14px;">监管概况</a>
               <ul style="display: none;">
             <% // if(role != 1 && role != 2) { 
               if(role == 22 || role == 23) { 
                 if("0".equals(cityName)){ %>
                <li><a href="javascript:addTab('考评概况','views/elevator/ywkaoping.jsp')" class="slast">考评概况</a></li>
                <li><a href="javascript:addTab('维保信用','views/elevator/testywsyssetings2.jsp')" class="slast">维保信用</a></li>
            <!--    <li><a href="javascript:addTab('维保信用','views/elevator/ywsyssetings2.jsp')" class="slast">维保信用</a></li>  --> 
                <% }} %>
               
                
              <%  if(role == 30 || role == 31) {
                   if (iskaoping ==1) {%>
                <li><a href="javascript:addTab('考评概况','views/elevator/ywkaopinghyxh.jsp')" class="slast">考评概况</a></li> 
                <li><a href="javascript:addTab('维保信用','views/elevator/ywsyssetings2hyxh.jsp')" class="slast">维保信用</a></li>
                <li><a href="javascript:addTab('维保黑名单','views/elevator/ywkaopinghmd.jsp')" class="slast">维保黑名单</a></li>
                <li><a href="javascript:addTab('维保排位','views/yw/ywRanking2.jsp')" class="slast">维保排位</a></li>
                <% if("0".equals(cityName)){ %>
                <li><a  href="javascript:addTab('维保单位信息备案','views/company/wbcompanylistba.jsp')" class="slast">维保单位信息备案</a></li>
                <%} %>
                <% } } %>
                
                <% if(role == 10 || role == 11) {
                    if (iskaoping ==1) {%> 
            <li><a href="javascript:addTab('考评概况','views/elevator/ywkaopinghyxh.jsp')" class="slast">考评概况</a></li> 
            <li><a href="javascript:addTab('维保信用','views/elevator/ywsyssetings2qzj.jsp')" class="slast">维保信用</a></li>
                    <% } %>
                <%if("0".equals(cityName)){ %>
             <li><a href="javascript:addTab('维保黑名单','views/elevator/ywkaopinghmd.jsp')" class="slast">维保黑名单</a></li>    
                <% }} %>
                
                   <% if(role == 16 || role == 17) {
                    if (iskaoping ==1) {%> 
              <li><a href="javascript:addTab('考评概况','views/elevator/ywkaopinghyxh.jsp')" class="slast">考评概况</a></li> 
              <li><a href="javascript:addTab('维保信用','views/elevator/ywsyssetings2tjy.jsp')" class="slast">维保信用</a></li> 
               <%if("0".equals(cityName)){ %>
              <li><a href="javascript:addTab('维保黑名单','views/elevator/ywkaopinghmd.jsp')" class="slast">维保黑名单</a></li>     
                <%} } }%>
                  
                <%if((role ==2 || role==1) || (role == 22 || role ==23)){ %>   <!--系统管理员和市质监 -->
             <!--     <li><a href="javascript:addTab('系统统计','views/elevator/systongji.jsp')" class="slast">系统统计</a></li>  --> 
                 
                 <%if(!"0".equals(cityName) && !"2".equals(cityName)){ %>
                 <li><a href="javascript:addTab('系统统计','views/elevator/systongji.jsp')" class="slast">系统统计</a></li>
                 <li><a href="#" class="slast" style="background-color:#CCCCCC;">系统地图</a></li>  
                  <% } 
                 
                 else {%>
             <!--   <li><a href="javascript:addTab('系统地图','views/elevator/eleMapInfo4.jsp')" class="slast">系统地图</a></li>  --> 
                   <%if("0".equals(cityName)){ %>
                      <li><a href="javascript:addTab('维保黑名单','views/elevator/ywkaopinghmd.jsp')" class="slast">维保黑名单</a></li>
                   <% } %>
              
                      <li><a href="javascript:addTab('系统统计','views/elevator/systongji2.jsp')" class="slast">系统统计</a></li>
              
                  <%if("0".equals(cityName)){ %>
                     <li><a href="javascript:addTab('系统地图','views/elevator/eleMapInfo4_2.jsp')" class="slast">系统地图</a></li>
                      <li><a href="javascript:addTab('电梯坐标统计','views/elevator/elevatorCoordinate.jsp')" class="slast">电梯坐标统计</a></li>  
                  <% } %>
                  <%if("2".equals(cityName)){ %>
                     <li><a href="javascript:addTab2('系统地图','views/elevator/changsha.jsp')" class="slast">系统地图</a></li> 
                  <% } %>
                <% }%>
                  
                  <%if(!"0".equals(cityName) && !"2".equals(cityName)){ %>
                     <li><a href="#" class="slast" style="background-color:#CCCCCC;">电梯分布</a></li>
                  <% } else {
                       if("0".equals(cityName)){ %>
                         <li><a href="javascript:addTab('电梯分布','views/elevator/eleMapInfo.jsp')" class="slast">电梯分布</a></li>
                        <% } %>
                      <% if("2".equals(cityName)){ %>
                         <li><a href="javascript:addTab('电梯分布','views/elevator/cseleMapInfo.jsp')" class="slast">电梯分布</a></li>
                        <% } %>
                     
                   <% }%>
                   
                   
              <% }%>
                  <%if(role ==10 || role==11) { %>   <!--区县质监 -->
                   <li><a href="javascript:addTab('系统统计','views/elevator/zjsystongji.jsp')" class="slast">系统统计</a></li>
                   <% }%>
               </ul>
             </li>
             <% } %>
           
             <% if((role != 16 && role != 17 ) && (role != 30 && role != 31)) {%>  <!--不等于检验,行业协会  -->
             <li><a href="#" class="inactive" style="font-size:14px;">业务管理</a>
               <% if(role != 8 &&  role != 9){ %>
               <ul style="display: none;">
               <li><a href="#" class="inactive active">基础信息管理</a>
                     <ul>
                     <%if(role ==2 || role==1){ %>
                	<li><a  href="javascript:addTab('标签发放','views/twocode/twocodelist.jsp')" class="last">标签管理</a></li>  
                     <!-- <li><a  href="javascript:addTab('96933管理','views/twocode/twocode96333list.jsp')" class="last">96933管理</a></li>  -->    
                     <li><a  href="javascript:addTab('单位管理','views/company/companylist.jsp')" class="last">单位管理</a></li> 
                     <% }%>
                    <%if((role == 22 || role ==23) || (role == 10 || role ==11)){ %> 
                     <li><a  href="javascript:addTab('维保公司','views/company/zjwbcompanylist.jsp')" class="last">维保公司</a></li> 
                     <li><a  href="javascript:addTab('使用公司','views/company/zjsycompanylist.jsp')" class="last">使用公司</a></li> 
                    <% }%>
                    <%if(role != 20 && role != 21) { %> 
                     <li><a href="javascript:addTab('用户管理','views/user/userlist.jsp')" class="last">用户管理</a></li>
                    <% }%>
                    
                     <% if(ispcsuper == 1){ %> 
                      <li><a href="javascript:addTab('app升级','views/user/applist.jsp')" class="last">app升级</a></li>
                     <% }%>
                    <%if(role == 22 || role ==23){ %>
                    <li><a href="javascript:addTab('日志管理','views/log/zhijianloglist.jsp')" class="last">日志管理</a></li>
                    <% }%> 
                    <%if(role ==2){ %>    
                    <li><a href="javascript:addTab('系统设置','views/elevator/syssetings.jsp')" class="last">系统设置</a></li>
                    <% }%>
                    </ul>
                 </li>
                    
                <%if(role ==2){ %> 
                <li><a href="#" class="inactive active">粘贴信息管理</a>
                     <ul>
                     <li><a href="javascript:addTab('预录数据','views/elevator/delevatorlist.jsp')" class="last">预录数据</a></li>
                     <li><a href="javascript:addTab('任务分配','views/elevator/delevatortasklist.jsp')" class="last">任务分配</a></li>
                     <li><a href="javascript:addTab('完成统计','views/elevator/detaskliststatistic.jsp')" class="last">完成统计</a></li>
                     <li><a href="javascript:addTab('粘贴统计','views/elevator/ztststatistic.jsp')" class="last">粘贴统计</a></li>
                      <%if("1".equals(cityName)){ %>
                     <li><a href="javascript:addTab('关联入库','views/elevator/nrelationrukudelevatorlist.jsp')" class="last">关联入库</a></li>
                     <% } else {%>
                    <li><a href="javascript:addTab('关联入库','views/elevator/relationrukudelevatorlist.jsp')" class="last">关联入库</a></li>
                     <% }%>
                     <li><a href="javascript:addTab('入库信息','views/elevator/rukudelevatorlist.jsp')" class="last">入库信息</a></li>
                      <%if(!"0".equals(cityName)){ %>
               <!--   <li><a href="#"  style="background-color:#CCCCCC;">特检院核查</a></li> -->     
                     <% } else {%>
                       <li><a href="javascript:addTab('特检院核查','views/elevator/hechadelevatorlist.jsp')" class="last">特检院核查</a></li>
                     <% }%> 
                     </ul>
                 </li>
                     <% }%>
                       
                   <%if((role ==23 || role ==22) || (role ==10 || role ==11)){ %>
                      <li><a href="#" class="inactive active">数据核实管理</a>
                        <ul>
                        <li><a href="javascript:addTab('数据核实','views/elevator/relationrukudelevatorlist.jsp')" class="last">数据核实</a></li>
                       <%if(role ==23 || role ==22){
                    	   if("0".equals(cityName)){
                       %>
                        <li><a href="javascript:addTab('两院粘贴数据','views/elevator/liangyuanevatorlist.jsp')" class="last">两院粘贴数据</a></li>
                        <li><a href="javascript:addTab('两院新增数据','views/elevator/tjyaddelevatorlist.jsp')" class="last">两院新增数据</a></li>
                       <% }}%> 
                       </ul>
                     </li>
                   <% }%> 
                   
                   <%if(role == 2){ %>
                    <li><a href="#" class="inactive">数据校验管理</a>
                      <ul style="display: none;">
                        <% if("1".equals(cityName)){ %>
                      <li><a href="#" style="background-color:#CCCCCC;">数据校验</a></li>
                       <% } else {%>
                       <li><a href="javascript:addTab('数据校验','views/elevator/tjyjyelevatorlist.jsp')" class="last">数据校验</a></li>
                        <% }%> 
                      </ul>
                     </li>
                   <% } %>
                  
                    <% if(role != 20 && role != 21) { %>
                    <li><a href="#" class="inactive active">巡检信息管理</a>
                     <ul>
                     <li><a href="javascript:addTab('巡检记录','views/yw/xclist.jsp')" class="last">巡检记录</a></li>
                     <li><a href="javascript:addTab('年检记录','views/elevator/ycheck.jsp')" class="last">年检记录</a></li>
                     </ul>
                   </li>
                   
                   <li><a href="#" class="inactive active">报警信息管理</a>
                     <ul>
                     <li><a href="javascript:addTab('报警记录','views/alarm/alarmlist.jsp')" class="last">报警记录</a></li>
                     </ul>
                   </li>
                   
                    <li><a href="#" class="inactive active">留言信息管理</a>
                     <ul>
                     <li><a href="javascript:addTab('留言记录','views/remark/liuyanlist.jsp')" class="last">留言记录</a></li>
                     </ul>
                   </li>
                   <% }%> 
                    <%if(role ==2){ %> 
                    <li><a href="#" class="inactive active">PC端信息管理</a>
                     <ul>
                     <li><a href="javascript:addTab('PC软件','views/yw/pcywsoftsetings.jsp')" class="last">PC软件</a></li>
                     <li><a href="javascript:addTab('PC维保日志','views/yw/pcloglist.jsp')" class="last">维保日志</a></li>
                     </ul>
                   </li>
                    <%}%> 
                </ul>
             </li>
             <%}%> <!--物管  -->
             <% if(role == 8 ||  role == 9){ %>
              <ul style="display: none;">
               <li><a href="#" class="inactive active">分组管理</a>    
                <ul>
                <li><a href="javascript:addTab('分组列表','views/wg/group.jsp')" class="last">分组列表</a></li>
                </ul>
               </li>
              </ul>
             <%}%>
           <%}}%> <!--不等于检验,行业协会-->
             
            <% if((role != 16 && role != 17) && (role != 30 && role != 31)) {
                  %>  <!--不等于检验 ,行业协会，物管  -->
            <li><a href="#" class="inactive" style="font-size:14px;">电梯管理</a>
               <ul style="display: none;">
               
                    <li><a href="#" class="inactive active">电梯管理</a>
                     <ul>
                       <li><a href="javascript:addTab('电梯管理','views/elevator/elevatorlist.jsp')" class="last">电梯列表</a></li>
                       <%if((role != 20 && role != 21) && (role != 8 && role != 9) ) {%>   <!--不等于街道办,物管  -->
                       <li><a href="javascript:addTab('停用管理','views/elevator/selevatorlist.jsp')" class="last">停用电梯</a></li>
                       <li><a href="javascript:addTab('涉秘管理','views/elevator/smelevatorlist.jsp')" class="last">涉秘电梯</a></li>   
                       <% }%>
            <!--     <li><a href="javascript:addTab('注销管理','views/elevator/zxelevatorlist.jsp')" class="last">注销电梯</a></li>  --> 
                    </ul>
                   </li>
                   
                    <%if(ispcsuper == 1){ if("0".equals(cityName)){ %>
                    <li><a href="#" class="inactive active">维保审核</a>
                     <ul>
                      <li><a href="javascript:addTab('审核统计','views/yw/ywshenhetj.jsp')" class="last">审核统计</a></li>
                     </ul>
                   </li>
                    <% }} %>
                 
                   <li><a href="#" class="inactive active">维保记录</a>
                     <ul>
                     <li><a href="javascript:addTab('维保记录','views/yw/ywlist.jsp')" class="last">维保记录</a></li>
                    <% if(role != 8 && role != 9) {  %>
                     <li><a href="javascript:addTab('临时维保','views/yw/tempywlist.jsp')" class="last">临时维保</a></li>
                    <% } %>
                     </ul>
                   </li>
                 <% if(role != 8 && role != 9) {       
                  if(role != 20 && role != 21) {%>    <!--不等于街道办,不等于物管  -->
                    <li><a href="#" class="inactive active">维保超期</a>
                     <ul>
                     <li><a href="javascript:addTab('当前超期','views/yw/nywcqlist.jsp')" class="last">当前超期</a></li>
                     <li><a href="javascript:addTab('历史超期','views/yw/ywcqlist.jsp')" class="last">历史超期</a></li>
                     </ul>
                   </li>
                  
                   
                  <li><a href="javascript:addTab('维保统计','views/yw/ywstatistics.jsp')" class="slast">维保统计</a></li>
                   <% if("0".equals(cityName) || "2".equals(cityName)){%>
                     <% if(role == 10 || role ==11){%>
                      <li><a href="#" class="inactive active">维保排位</a>
                     <ul>  
                      <li><a href="javascript:addTab('区县排位','views/yw/ywRanking.jsp')" class="last">区县排位</a></li> 
                  <!--  <li><a href="javascript:addTab('区县排位','views/yw/ywRanking2.jsp')" class="last">区县排位</a></li>  -->
                    <li><a href="javascript:addTab('全市排位','views/yw/qsywRanking.jsp')" class="last">全市排位</a></li> 
                   <!--    <li><a href="javascript:addTab('全市排位','views/yw/qsywRanking2.jsp')" class="last">全市排位</a></li> -->
                        </ul> 
                      </li> 
               <!--    <li><a href="javascript:addTab('维保排位','views/yw/qsywRanking2.jsp')" class="slast">维保排位</a></li>  -->
                    <%} else { %>
                       <% if(role == 22 || role == 23) {%> 
                        <li><a href="javascript:addTab('维保排位','views/yw/ywRanking2.jsp')" class="slast">维保排位</a></li>
                        <% } else {%>
                        <li><a href="javascript:addTab('维保排位','views/yw/ywRanking.jsp')" class="slast">维保排位</a></li> 
                           <%} %>
                    <%} %>
                 <%}  
                 else {%>
                     <li><a href="#" class="slast" style="background-color:#CCCCCC;">维保排位</a></li>
                 <% } %>
                 
                   <li><a href="#" class="inactive active">维保人员</a>
                     <ul>
                     <li><a href="javascript:addTab('维保人员','views/user/ywpersonlist.jsp')" class="last">维保人员</a></li>
                     <li><a href="javascript:addTab('维保日志','views/yw/ywloglist.jsp')" class="last">维保日志</a></li>
                     </ul>
                   </li>
                <% } }%>    <!--不等于街道办  不等于物管 -->
               </ul>
         </li>
        <% }%>     <!--不等于检验 ,行业协会  -->
             
         <%if(role ==2){ %> 
          <li><a href="#" class="inactive" style="font-size:14px;">指标趋势</a>
                <% if(!"0".equals(cityName)){ %>
                <ul style="display: none;" style="background-color:#CCCCCC;">
                  <li><a href="#" style="background-color:#CCCCCC;">指标概况</a></li>
                  <li><a href="#" style="background-color:#CCCCCC;">隐患率</a></li>
                  <li><a href="#" style="background-color:#CCCCCC;">登记率</a></li>
                  <li><a href="#" style="background-color:#CCCCCC;">检验率</a></li>
                  <li><a href="#" style="background-color:#CCCCCC;">完成检验数</a></li>
                </ul>
                 <% } else {%>
                 <ul style="display: none;">
                  <li><a href="javascript:addTab('指标概况','views/trend/zbtrend.jsp')" class="slast">指标概况</a></li>
                  <li><a href="#" class="slast">隐患率</a></li>
                  <li><a href="#" class="slast">登记率</a></li>
                  <li><a href="#" class="slast">检验率</a></li>
                  <li><a href="#" class="slast">完成检验数</a></li>
                </ul>
                
                <% }%>
           </li>
             
             
         <li><a href="#" class="inactive" style="font-size:14px;">工作绩效</a>
               <% if(!"0".equals(cityName)){ %>
                 <ul style="display: none;" style="background-color:#CCCCCC;">
               <li><a href="#" style="background-color:#CCCCCC;">应急平台救援</a></li>
              </ul>
                 <% } else {%>
               <ul style="display: none;">
               <li><a href="javascript:addTab('应急平台救援','views/job/jobPerformance.jsp')" class="slast">应急平台救援</a></li>
              </ul>
                <% }%>
          </li>
             
         <li><a href="#" class="inactive" style="font-size:14px;">工作指导</a>
               <% if(!"0".equals(cityName)){ %>
                 <ul style="display: none;" style="background-color:#CCCCCC;">
                 <li><a href="#" style="background-color:#CCCCCC;">工作指导</a></li>
               </ul>
                 <% } else {%>
                <ul style="display: none;">
                 <li><a href="javascript:addTab('工作指导','views/work/workInstruction.jsp')" class="slast">工作指导</a></li>
               </ul>
                 <% }%>
          </li>
        <% }%>
        
       </ul>
    </div>
</div> 
   
         
       <div region="center">
       <div id="main-center" class="easyui-tabs" fit="true" border="false">  
     <!--      <div title="首页" style="padding:20px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/mbg.png',sizingMethod='scale');background-repeat: no-repeat;background-positon: 100%, 100%; ">  
          <div style="margin-top:20px" >  
            <p></p>          
          </div>  
        </div>   -->
    </div>  
</div> 
<div id="newsinfodd">
<div style="margin-top:1px;">  
       <table id="newstt"></table>
   </div> 
</div>

<div id="news-window" title="添加消息" style="width:550px;height:350px;"> 
  <div class="easyui-layout" data-options="fit:true">
   <form method="post" id="newsform"> 
    <div data-options="region:'north'" style="height:220px">
  <table>
   <tr>
    <td align="right" nowrap>接收者：</td>
   <td><select id="zjCompanyId"  class="easyui-combobox" name="zjCompanyId" style="width:138px;"> 
</select><span><font color="red">（支持多选）</font></span>
   </td>
   </tr>    
   <tr>      
   <td width="90" align="right" nowrap>标题：</td> 
   <td><input id="newsTitle" name="newsTitle" class="easyui-validatebox"  data-options="required:true,validType:'length[1,50]'"></input></td>
   </tr>
   <tr>
   <td width="90" align="right" nowrap>正文：</td> 
   <td colspan><textarea id="newsContext" name="newsContext" rows="8" cols="57" class="easyui-validatebox textbox"  data-options="validType:'length[0,500]'"></textarea></td>
   </tr> 
   </table>
   </div>
   
     <div data-options="region:'center'" style="overflow-x:auto;overflow-y:hidden"> 
     <table width="100%">
      <tr>
       <td align="right">
       <a href="javascript:void(0)" onclick="newssave()" id="btn-save" icon="icon-save">发送</a>
       </td>  
      </tr>
     </table>
     </div>
     </form>
  </div>
  
  </div> 
  <div id="newsdetailinfo-window" title="详情" style="width:270px;height:150px;overflow-x:auto;overflow-y:hidden">
 <div id="newsdetailContext"></div>
</div>	
 <div id="receivernewsdetailinfo-window" title="详情" style="width:450px;height:380px;overflow-x:auto;overflow-y:hidden">
 <table id="rntt"></table>
</div>


</body>
</html>