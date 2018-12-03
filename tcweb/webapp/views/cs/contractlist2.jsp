<%@ page import="com.zytx.models.UserInfo" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电梯安全公共服务平台</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>



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
			UserInfo user =UserInfo.findFirstBySql(UserInfo.class, "select role from TwoCodeUserInfo where loginName= ?",new Object[] { userName });
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

	 $(function(){
		 $.ajaxSetup ({
			    cache: false 
			});
	win = $('#contract-window').window({  closed:true,draggable:false,modal:true});
	$('#btn-save').linkbutton(); 
	 form = win.find('myform');

	 grid=$('#tt').datagrid({
		    title:'合同列表',
		    pageSize:10,
		    pageList:[10,20,25,30,35,40],
		    url:'/tcweb/cs/contractlist',
		    queryParams:{},
		    columns:[[
		        {field:'contractNumber',title:'合同编号',width:160},
		        {field:'contractName',title:'合同名称',width:100},
		        {field:'contractAttribute',title:'合同属性',width:80},
		        {field:'contractCompanyName',title:'公司名称',width:160},
		        {field:'contractCustomerName',title:'客户单位',width:160},
		        {field:'contractSigneddate',title:'合同签订日期',width:140},
		        {field:'contractEnddate',title:'合同到期日期',width:140},
		     //   {field:'contractEnddate2',title:'到期天数',
		     //   	formatter: function(value,rec,index) {
		    //    	return  DateDiff(rec.contractEnddate,rec.contractSigneddate); }
			//    },
		        {field:'1',title:'电梯列表',width:60,
			    	formatter: function(value,rec,index) {
			  //  	var contractNumber =''+rec.contractNumber;
		        	return  "<img src='<%=request.getContextPath()%>/images/cselevatorlist.png' alt='电梯列表' style='cursor:hand;' onclick='cselevatorlist("+rec.id+")'/>";     
		            }
			        },
		        {field:'2',title:'付款情况',width:160,      
			        formatter: function(value,rec,index) {  
			        return  "<img src='<%=request.getContextPath()%>/images/cspayment.png' alt='付款情况' style='cursor:hand;' onclick='cspaymentlist("+rec.id+","+"\""+rec.contractNumber+"\""+","+"\""+rec.contractName+"\""+","+"\""+rec.contractAmount+"\""+")'/>";    

				       }
				        }
		        
		    ]],
		    nowrap:true,
		    pagination:true,
		    toolbar:[{
		        text:'新增',
		        iconCls:'icon-add',
		        handler:function(){
		    	win.window('open');  
		    //	form.form('clear');
		    	form.url ='/tcweb/cs/add';
		        }
		    }]
	 });
	 $('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[10,20,25,30,35,40]});   
	  	 
});

	 function contractinfosave(){ 
	 var contractform =$('#myform');	  
	 contractform.form('submit', {  
			url:form.url,
			onSubmit:function(){   
			return true;  
	    },
			  
			success:function(data){   
			
				grid.datagrid('reload');
				win.window('close');   
				}		
		});
		 }

	 
	//计算天数差的函数，通用  
	   function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2002-12-18格式  
	       var  aDate,  oDate1,  oDate2,  iDays;  
	       aDate  =  sDate1.split("-");  
	       oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);    //转换为12-18-2002格式  
	       aDate  =  sDate2.split("-");  
	       oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);  
	       iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数  
	       return  iDays;  
	  } 
	 
</script>
</head>
<body class="easyui-layout">
<div region="north" style="overflow:hidden">
<fieldset id="addDiv" style="width: 100%;margin:0px"><legend>查询条件</legend>
<table> 
     <tr>      
   <td align="right" nowrap>合同编号：</td> 
   <td nowrap><input id="contractNumberinfo" name="contractNumberinfo" size="24"></input></td>
   <td align="right" nowrap>合同属性：</td> 
   <td><select id="contractAttributeinfo" name="contractAttributeinfo">
   <option value="自营">自营</option>
   <option value="转包">转包</option>
   </select></td> 
    <td align="right" nowrap>客户单位：</td> 
   <td nowrap><input id="contractCustomerNameinfo" name="contractCustomerNameinfo" size="24" class="easyui-validatebox"></input></td>
   </tr>
   <tr>
   <td  nowrap>开始时间：</td>
   <td><input id="qstartTime"  type="text" class="easyui-datebox" name="qstartTime" style="width:152px;"></input></td>
   <td align="right" nowrap>结束时间：</td>
   <td><input id="qendTime"  type="text" class="easyui-datebox" name="qendTime" style="width:152px;"></input></td>
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
       <table id="tt"></table>
   </div>  
        
    </div>  
</div>

<div id="contract-window" title="电梯合同" style="width:750px;height:550px;"> 

<form method="post" id="myform"> 

  
  <table>    
   <tr>      
   <td width="90" align="right" nowrap>合同编号：</td>      
   <td><input id="contractNumber" name="contractNumber"></input></td> 
   </tr>
       <tr>
       <td align="center" colspan="59">
       <a href="javascript:void(0)" onclick="contractinfosave()" id="btn-save" icon="icon-save">保存</a></td>  
      </tr>
    </table>  
 
  </form>  

  </div>
</body>
</html>