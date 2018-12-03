<%@ page import="com.zytx.models.*" language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>成都市电梯安全公共服务平台</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<% 
UserInfo userinfo=(UserInfo)session.getAttribute("sessionAccount");
int role=0;
String userName2="";
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

%>
<script type="text/javascript">

$(function(){
    
	$.ajaxSetup ({
	    cache: false 
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

	$('#tUName2').html("<a href='#' class='tileHead2' onclick='newsinfo()' >消息</a>");

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

function exitSystem(){
 	location.href="/tcweb/join/systemExit";	
    if (window.opener && !window.opener.closed){
 	window.opener.location.href=window.opener.location.href;   
    }
	window.close();
 
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
	padding: 5px 0px 5px 20px;
	background: transparent url('images/exit2.png') no-repeat center  left;
	text-decoration:none;
	color:#ffffff;
}
.tileHead { 
	padding: 5px 0px 5px 20px;
	background: transparent url('images/tuname.png') no-repeat center  left;
	text-decoration:none;
	color:#ffffff;
}

.tileHead2 { 
	padding: 5px 0px 5px 20px;
	background: transparent url('images/zhijianshannews.gif') no-repeat center  left;
	text-decoration:none;
	color:#ffffff;
}

</style>
</head>

 <body class="easyui-layout" style="text-align:center">
    <div region="north" style="background:#fafafa;color:#2d5593;height:80px;">
    <div style="height:55px;float:right;both:clear;width:100%;background-color: silver;background-repeat: no-repeat;background-positon: 100%, 100%; "><img src="images/headbg.png" style="width:100%;height:100%"/></div>  
    <div style="font-size:16px;font-weight:bold;padding:0;float:right;background-color:#0066CC;width:100%;text-align:right;">
   <span class="tileHead" id="tUName"></span>  &nbsp;&nbsp;|&nbsp;&nbsp;<span  id="tUName2"></span>&nbsp;&nbsp;|&nbsp;&nbsp;
    <a href="#" class="exitinHead" onclick="exitSystem()" style="margin-right:20px;">退出系统</a>
    </div>  
   </div> 
        
        
   <div region="west" title="导航菜单" split="true" style="width:150px;text-align:center;">  
   <div class="easyui-accordion" fit="true" border="false" style="background-color:#c8d9f5;">
        <% if(role!=100){%>
        <div title="基础信息管理" selected="true" style="overflow:auto;background-color:#c8d9f5;"> 
           <%if(role ==2 || role==1){ %> 
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('单位管理','views/company/companylist.jsp')" style="text-decoration:none;">  
                    <img src="images/company.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">单位管理</span>  
                </a>  
            </div>
            <% }%>
           
              <%if(role == 22 || role ==23){ %> 
             
              <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('维保公司','views/company/zjwbcompanylist.jsp')" style="text-decoration:none;">  
                    <img src="images/zjwbcompany.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">维保公司</span>  
                </a>  
            </div>   
            
              <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('使用公司','views/company/zjsycompanylist.jsp')" style="text-decoration:none;">  
                    <img src="images/zjsycompany.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">使用公司</span>  
                </a>  
            </div>      
            
             <% }%>  
             
              <%if(role == 10 || role ==11){ %> 
             
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('维保公司','views/company/zjwbcompanylist.jsp')" style="text-decoration:none;">  
                    <img src="images/zjwbcompany.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">维保公司</span>  
                </a>  
            </div> 
            
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('使用公司','views/company/zjsycompanylist.jsp')" style="text-decoration:none;">  
                    <img src="images/zjsycompany.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">使用公司</span>  
                </a>  
            </div>    
            
             <% }%>  
             
             <% if(role != 20 && role != 21) { %>
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('用户管理','views/user/userlist.jsp')" style="text-decoration:none;">  
                    <img src="images/usergroup.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">用户管理</span>  
                </a>  
            </div> 
            <% } %>
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('电梯管理','views/elevator/elevatorlist.jsp')" style="text-decoration:none;">  
                    <img src="images/elevatorlabel.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">电梯管理</span>  
                </a>  
            </div> 
             <% if(role != 20 && role != 21) { %>
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('停用管理','views/elevator/selevatorlist.jsp')" style="text-decoration:none;">  
                    <img src="images/selevatorlabel.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">停用管理</span>  
                </a>  
            </div> 
             
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('涉秘管理','views/elevator/smelevatorlist.jsp')" style="text-decoration:none;">  
                    <img src="images/secretelevator.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">涉秘管理</span>  
                </a>  
            </div> 
              
              <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('注销管理','views/elevator/zxelevatorlist.jsp')" style="text-decoration:none;">  
                    <img src="images/zhuxiao.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">注销管理</span>  
                </a>  
            </div>   
            <% } %>    
            <%if(role == 22 || role ==23){ %> 
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('日志管理','views/log/zhijianloglist.jsp')" style="text-decoration:none;">  
                    <img src="images/zhijianlog.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">日志管理</span>  
                </a>  
            </div> 
             <% }%>
            
             <%if(role ==2){ %> 
            
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('系统设置','views/elevator/syssetings.jsp')" style="text-decoration:none;">  
                    <img src="images/syssetings.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">系统设置</span>  
                </a>  
            </div>
            <% }%>
            </div>
           <%if(role ==2){ %> 
            <div title="粘贴信息管理" style="overflow:auto;background-color:#c8d9f5;">  
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('预录数据','views/elevator/delevatorlist.jsp')" style="text-decoration:none;">  
                    <img src="images/bulu.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">预录数据</span>  
                </a>  
            </div> 
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('任务分配','views/elevator/delevatortasklist.jsp')" style="text-decoration:none;">  
                    <img src="images/bulutask.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">任务分配</span>  
                </a>  
            </div> 
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('完成统计','views/elevator/detaskliststatistic.jsp')" style="text-decoration:none;">  
                    <img src="images/bulutaskstatistics.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">完成统计</span>  
                </a>  
            </div>
            
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('粘贴统计','views/elevator/ztststatistic.jsp')" style="text-decoration:none;">  
                    <img src="images/ztstatistics.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">粘贴统计</span>  
                </a>  
            </div>   
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('关联入库','views/elevator/relationrukudelevatorlist.jsp')" style="text-decoration:none;">  
                    <img src="images/relationruku.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">关联入库</span>  
                </a>  
            </div>
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('入库信息','views/elevator/rukudelevatorlist.jsp')" style="text-decoration:none;">  
                    <img src="images/rukudelevator.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">入库信息</span>  
                </a>  
            </div>
               <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('特检院核查','views/elevator/hechadelevatorlist.jsp')" style="text-decoration:none;">  
                    <img src="images/hechadelevator.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">特检院核查</span>  
                </a>  
            </div>
        </div> 
        <% }%>
          
        <%if(role ==23 || role ==22){ %>
         <div title="数据核实管理" style="overflow:auto;background-color:#c8d9f5;">  
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('数据核实','views/elevator/relationrukudelevatorlist.jsp')" style="text-decoration:none;">  
                    <img src="images/relationruku.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">数据核实</span>  
                </a>  
            </div>
           
              <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('两院数据','views/elevator/liangyuanevatorlist.jsp')" style="text-decoration:none;">  
                    <img src="images/liangyuan.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">两院数据</span>  
                </a>  
            </div> 
         </div> 
         <% }%>
         
          <%if(role ==10 || role ==11){ %>
         <div title="数据核实管理" style="overflow:auto;background-color:#c8d9f5;">  
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('数据核实','views/elevator/relationrukudelevatorlist.jsp')" style="text-decoration:none;">  
                    <img src="images/relationruku.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">数据核实</span>  
                </a>  
            </div>
            
         </div> 
         
        
         <% }%>
        
             <div title="维保信息管理" style="overflow:auto;background-color:#c8d9f5;">  
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('维保记录','views/yw/ywlist.jsp')" style="text-decoration:none;">  
                    <img src="images/yw.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">维保记录</span>  
                </a>  
            </div>
              <% if(role != 20 && role != 21) { %>
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('当前超期','views/yw/nywcqlist.jsp')" style="text-decoration:none;">  
                    <img src="images/nowYwOutdate.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">当前超期</span>  
                </a>  
            </div>   
            
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('历史超期','views/yw/ywcqlist.jsp')" style="text-decoration:none;">  
                    <img src="images/ywcq.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">历史超期</span>  
                </a>  
            </div> 
            <% } %>
             <%if(role == 22 || role ==23){ %>
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('运维统计','views/yw/ywstatistics.jsp')" style="text-decoration:none;">  
                    <img src="images/ywstatistics.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">运维统计</span>  
                </a>  
            </div> 
             <% }%>
             
            <%if(role ==2){ %>
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('运维统计','views/yw/ywstatistics.jsp')" style="text-decoration:none;">  
                    <img src="images/ywstatistics.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">运维统计</span>  
                </a>  
            </div> 
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('临时维保','views/yw/tempywlist.jsp')" style="text-decoration:none;">  
                    <img src="images/tempyw.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">临时维保</span>  
                </a>  
            </div> 
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('维保人员','views/user/ywpersonlist.jsp')" style="text-decoration:none;">  
                    <img src="images/ywperson.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">维保人员</span>  
                </a>  
            </div> 
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('维保日志','views/yw/ywloglist.jsp')" style="text-decoration:none;">  
                    <img src="images/log.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">维保日志</span>  
                </a>  
            </div> 
             <% }%>
             <%if(role ==10 || role==11){ %>
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('运维统计','views/yw/ywstatistics.jsp')" style="text-decoration:none;">  
                    <img src="images/ywstatistics.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">运维统计</span>  
                </a>  
            </div> 
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('临时维保','views/yw/tempywlist.jsp')" style="text-decoration:none;">  
                    <img src="images/tempyw.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">临时维保</span>  
                </a>  
            </div> 
              <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('维保人员','views/user/ywpersonlist.jsp')" style="text-decoration:none;">  
                    <img src="images/ywperson.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">维保人员</span>  
                </a>  
            </div> 
             <% }%>
             <%if(role ==22 || role==23){ %>
              <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('临时维保','views/yw/tempywlist.jsp')" style="text-decoration:none;">  
                    <img src="images/tempyw.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">临时维保</span>  
                </a>  
            </div> 
              <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('维保人员','views/user/ywpersonlist.jsp')" style="text-decoration:none;">  
                    <img src="images/ywperson.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">维保人员</span>  
                </a>  
            </div> 
             <% }%>
            </div>
              <% if(role != 20 && role != 21) { %>
            <div title="巡检信息管理" style="overflow:auto;background-color:#c8d9f5;">  
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('巡检记录','views/yw/xclist.jsp')" style="text-decoration:none;">  
                    <img src="images/xc.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">巡检记录</span>  
                </a>  
            </div>  
            <div title="年检记录" style="overflow:auto;background-color:#c8d9f5;">  
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('年检记录','views/elevator/ycheck.jsp')" style="text-decoration:none;">  
                    <img src="images/ycheck.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">年检记录</span>  
                </a>  
            </div> 
        </div>   
        </div> 
       
        <div title="报警信息管理" style="overflow:auto;background-color:#c8d9f5;">  
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('报警记录','views/alarm/alarmlist.jsp')" style="text-decoration:none;">  
                    <img src="images/alarm.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">报警记录</span>  
                </a>  
            </div> 
        </div>
        <div title="留言信息管理" style="overflow:auto;background-color:#c8d9f5;">  
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('留言记录','views/remark/liuyanlist.jsp')" style="text-decoration:none;">  
                    <img src="images/liuyan.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">留言记录</span>  
                </a>  
            </div> 
        </div>
         <% } %>
      <!--  <div title="待领信息管理" style="overflow:auto;background-color:#c8d9f5;">  
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('待领记录','views/elevator/dailingelelist.jsp')" style="text-decoration:none;">  
                    <img src="images/dailingelevator.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">待领记录</span>  
                </a>  
            </div> 
        </div>  -->  
       <%} %>
       
    	 
     <!--    <div title="业务操作" style="overflow:auto;background-color:#c8d9f5;">  
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('信息发布')">  
                    <img src="images/newsinfo.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">信息发布</span>  
                </a>  
            </div>   
        <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('单位关联')">  
                    <img src="css/images/user_manage.png"></img><br/>  
                    <span>单位关联</span>  
                </a>  
            </div>  >
         
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('电梯变更','views/elevator/change.jsp')">  
                    <img src="images/change.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">电梯变更</span>  
                </a>  
            </div>  
        </div>  -->
        
<% if(role==100){%>
     <div title="门贴信息管理"  style="overflow:auto;background-color:#c8d9f5;"> 
    	<div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('门贴关联查询','views/advertise/advertiselist.jsp')" style="text-decoration:none;">  
                    <img src="images/advertisesearch.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">关联查询</span>  
                </a>  
        </div>
        <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('门贴关联统计','views/advertise/adverliststatistic.jsp')" style="text-decoration:none;">  
                    <img src="images/adverliststatistics.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">关联统计</span>  
                </a>  
        </div>
            
   </div> 
    	 
    <% } %>
    
      <%if(role ==2){ %> 
            <div title="PC端信息管理"  style="overflow:auto;background-color:#c8d9f5;"> 
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('PC软件','views/yw/pcywsoftsetings.jsp')" style="text-decoration:none;">  
                    <img src="images/pcywcompanysetings.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">PC软件</span>  
                </a>  
            </div>
              <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('PC维保日志','views/yw/pcloglist.jsp')" style="text-decoration:none;">  
                    <img src="images/log.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">维保日志</span>  
                </a>  
            </div>
            
            
            </div>
            
            <div title="监管概况"  style="overflow:auto;background-color:#c8d9f5;"> 
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('系统统计','views/elevator/systongji.jsp')" style="text-decoration:none;">  
                    <img src="images/systongji.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">系统统计</span>  
                </a>  
            </div>
           <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('系统地图','views/elevator/eleMapInfo3.jsp')" style="text-decoration:none;">  
                    <img src="images/sttjmap.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">系统地图</span>  
                </a>  
            </div>
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('电梯分布','views/elevator/eleMapInfo.jsp')" style="text-decoration:none;">  
                    <img src="images/eleposition.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">电梯分布</span>  
                </a>  
            </div>
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('维保排名','views/yw/ywRanking.jsp')" style="text-decoration:none;">  
                    <img src="images/ywranking.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">维保排名</span>  
                </a>  
            </div>  
            </div>
             <div title="指标趋势"  style="overflow:auto;background-color:#c8d9f5;"> 
              <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('指标趋势','views/trend/zbtrend.jsp')" style="text-decoration:none;">  
                    <img src="images/zbtrend.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">指标趋势</span>  
                </a>  
            </div>
              </div>
                <div title="工作绩效"  style="overflow:auto;background-color:#c8d9f5;"> 
              <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('工作绩效','views/job/jobPerformance.jsp')" style="text-decoration:none;">  
                    <img src="images/jobperformance.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">工作绩效</span>  
                </a>  
            </div>
              </div>  
            
              <div title="工作指导"  style="overflow:auto;background-color:#c8d9f5;"> 
              <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('工作指导','views/work/workInstruction.jsp')" style="text-decoration:none;">  
                    <img src="images/workinstruction.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">工作指导</span>  
                </a>  
            </div>
              </div>  
      
            <% }%>
             <%if(role==22 || role ==23){ %> 
            <div title="监管概况"  style="overflow:auto;background-color:#c8d9f5;"> 
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('系统统计','views/elevator/systongji.jsp')" style="text-decoration:none;">  
                    <img src="images/systongji.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">系统统计</span>  
                </a>  
            </div>
              <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('系统地图','views/elevator/eleMapInfo3.jsp')" style="text-decoration:none;">  
                    <img src="images/sttjmap.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">系统地图</span>  
                </a>  
            </div>
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('电梯分布','views/elevator/eleMapInfo.jsp')" style="text-decoration:none;">  
                    <img src="images/eleposition.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">电梯分布</span>  
                </a>  
            </div>
            <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('维保排名','views/yw/ywRanking.jsp')" style="text-decoration:none;">  
                    <img src="images/ywranking.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">维保排名</span>  
                </a>  
            </div>  
            </div>  
            <% }%>
            
             <%if(role==10 || role ==11){ %>  
              <div title="监管概况"  style="overflow:auto;background-color:#c8d9f5;"> 
               <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('系统统计','views/elevator/zjsystongji.jsp')" style="text-decoration:none;">  
                    <img src="images/systongji.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">系统统计</span>  
                </a>  
            </div>
             <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('维保排名','views/yw/ywRanking.jsp')" style="text-decoration:none;">  
                    <img src="images/ywranking.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">维保排名</span>  
                </a>  
            </div>  
              </div>  
             <% }%>
             
             <%if(role==20 || role ==21){ %>  
              <div title="监管概况"  style="overflow:auto;background-color:#c8d9f5;"> 
               <div class="nav-item" style="background-color:#c8d9f5;">  
                <a href="javascript:addTab('系统统计','views/elevator/jdbsystongji.jsp')" style="text-decoration:none;">  
                    <img src="images/systongji.png"></img><br/>  
                    <span style="color:#000000;text-decoration:none;">系统统计</span>  
                </a>  
            </div>
              </div>  
             <% }%>
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