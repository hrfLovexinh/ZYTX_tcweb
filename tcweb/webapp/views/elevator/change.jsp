<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>车载系统</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>


<% 
String carnum = request.getParameter("carnum");
System.out.println("carnum----"+carnum);

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
var opt =0; //0:增加 ；1：编辑
$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	$('#imgpic').bind("click",testDid);
	$('#imgpic2').bind("click",testDid2);	
     
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
	
	$('#btn-save,#btn-cancel').linkbutton(); 
	win = $('#car-window2').window({  closed:true,draggable:false,modal:true }); 
	form = win.find('form');
	
	var carnum='<%=carnum%>';
	if(carnum!='null'){ 
		$('#carnum').attr("value",carnum);
		grid=$('#tt').datagrid({
		    title:'车辆记录列表',
		    pageSize:15,
		    pageList:[15,20,25,30,35,40],
		    url:'/czweb/image/query',
		    queryParams:{'carnum':carnum,'dev_id':"",'startTime':"",'endTime':""},
		    columns:[[
		  	        {field:'carnum',title:'车牌号',width:60},
		  	        {field:'dev_id',title:'设备ID',width:110},
		  	        {field:'qy_name',title:'所属部门',width:70},
		  	        {field:'simnum',title:'通讯卡号',width:110},
		  	        {field:'dev_type',title:'终端类型',width:70},
		  	        {field:'car_type',title:'车辆类型',width:70},
		  	        {field:'car_color',title:'车身颜色',width:55},
		  	        {field:'carnum_color',title:'车牌颜色',width:55},
		        {field:'null',title:'操作',width:80,align:'center',
		        	formatter: function(value,rec,index) {
		  	        	var carnum = rec.data_id;//获取属性值
		  		        //	return "<a href='customerRemind/toadd.do?data_id="+id+"'>详情</a>";
		  			        return "<img src='<%=request.getContextPath()%>/images/unfold.gif' alt='查看' style='cursor:hand;' onclick='openCarinfoDetail("+carnum+")'/>";
		  	            }}
		    ]],
		    pagination:true
		});	
		$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  
	}
	else{ 
	grid=$('#tt').datagrid({
	    title:'二维码标签列表',
	    pageSize:15,
	    pageList:[15,20,25,30,35,40],
	    url:'/tcweb/elevator/elevatorlist',
	    queryParams:{},
	    columns:[[
	        {field:'registNumber',title:'电梯编号',width:60},
	        {field:'address',title:'地址',width:200},
	        {field:'registCode',title:'使用登记编号',width:200},
	        {field:'useNumber',title:'内部使用编号'},
	        {field:'jdbCompanyName',title:'街道办',width:150},
	        {field:'wgCompanyName',title:'物管单位',width:150},
	        {field:'ywCompanyName',title:'运维单位',width:110},
	        {field:'zzCompanyName',title:'制造单位',width:70},
	        {field:'azCompanyName',title:'安装单位',width:70},
	        {field:'jyCompanyName',title:'检验单位',width:55},
	        {field:'inspector',title:'检验人员',width:55},
	        {field:'nextInspectDate',title:'下次检验日期',width:55},
	        {field:'zjCompanyName',title:'质监局',width:55},
	        {field:'deviceId',title:'黑匣子设备'},
	    ]],
	    pagination:true,
	    toolbar:[{
	        text:'新增',
	        iconCls:'icon-add',
	        handler:function(){
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
	    },'-',{
	        text:'修改',
	        iconCls:'icon-edit',
	        handler:function(){
	    	var row = grid.datagrid('getSelected');   
	    	if (row){   
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
	    }],
	    onDblClickRow :function(rowIndex,rowData){
		 //   alert("双击事件发生了"+rowData.registNumber);
		    win.window('open');
		    form.form('load', '/tcweb/elevator/edit2/'+rowData.registNumber);
		    showWinDetail();
	    }
	});	
	$('#tt').datagrid('getPager').pagination({displayMsg:'显示 {from} 至 {to} 条  共 {total} 条记录', afterPageText:'/{pages}', beforePageText:'页',pageList:[15,20,25,30,35,40]});  
  }	  	  
}
);

function clearQuery(){
	$('#registNumber').attr("value","");
	$('#ywCompanyIdinfo').combobox('clear');
}

function query(){  
	var registNumber=$('#registNumber').attr("value");
    var ywCompanyId=$('#ywCompanyIdinfo').combobox('getValue'); 
  //  alert("ywCompanyId---"+ywCompanyId);
 //   alert("ywCompanyId2---"+document.all.ywCompanyIdinfo.value);
 //   alert("ywCompanyId3---"+ywName);
    if (!ywCompanyId){
    	ywCompanyId =0;
   // 	alert("0");
    	}
	if(""==registNumber && ywCompanyId==0){
	   if(""==ywName){	
	   grid.datagrid("options").url='/tcweb/elevator/elevatorlist';
	   }
	   else{
		 grid.datagrid("options").url='/tcweb/elevator/query';
		 grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName};
		   }
	}
	else{
     grid.datagrid("options").url='/tcweb/elevator/query';
     grid.datagrid("options").queryParams={'registNumber':registNumber,'ywCompanyId':ywCompanyId,'ywCompanyName':ywName};
	}
    $('#tt').datagrid('reload');
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

function saveCar2(){ 
	form.form('submit', {  
		url:form.url,
		onSubmit:function(){
     //   return $(this).form('validate');
		var registNumber=$('#registNumberinfo').attr("value"); 
		var address= $('#address').attr("value"); 
		var registCode =$('#registCode').attr("value");
		var useNumber =$('#useNumber').attr("value");
		var name =$('#name').attr("value");
		var eleMode =$('#eleMode').attr("value");
	//	var eleType =$('#eleType').attr("value");
		var eleStop =$('#eleStop').attr("value");
		var speed =$('#speed').attr("value");
		var eleLoad =$('#eleLoad').attr("value");
    //    var mobileCode =$('#mobileCode').attr("value");

        var eleType =$('#eleType').combobox('getValue');
        var wgCompanyId=$('#wgCompanyId').combobox('getValue'); 
        var ywCompanyId=$('#ywCompanyId').combobox('getValue'); 
        var zzCompanyId=$('#zzCompanyId').combobox('getValue'); 
        var azCompanyId=$('#azCompanyId').combobox('getValue'); 
        var jyCompanyId=$('#jyCompanyId').combobox('getValue'); 
        var zjCompanyId=$('#zjCompanyId').combobox('getValue');
        var townshipStreets=$('#townshipStreets').combobox('getValue');  
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
				$.messager.alert('操作失败', '物管单位不能为空', 'error');
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

				if (!azCompanyId) {
					$.messager.alert('操作失败', '安装单位不能为空', 'error');
					return false;
				}

				if (!ywCompanyId) {
					$.messager.alert('操作失败', '运维单位不能为空', 'error');
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
 <fieldset id="addDiv" style="width: 100%;margin:0px"><legend>查询条件</legend>
    
     <table> 
     <tr>      
   <td align="right" nowrap>电梯编号：</td> 
   <td nowrap><input id="registNumber" name="registNumber" size="12" class="easyui-validatebox"></input></td>
   <td nowrap>运维单位：</td>
   <td> 
   <select id="ywCompanyIdinfo"  class="easyui-combobox" name="ywCompanyIdinfo" style="width:152px;"> 
</select>
				<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="query()">查询</a>  
				<a href="#" class="easyui-linkbutton" icon="icon-no" onclick="clearQuery()">清空</a> 
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
<div id="car-window2" title="详细信息" style="width:750px;height:550px;"> 
  <div style="padding:20px 20px 40px 80px;">   
  <form method="post">    
  <table>    
   <tr>      
   <td width="70">电梯编号：</td>      
   <td><input id="registNumberinfo" name="registNumber"></input><span class="fontShow"><font color="red">*</font></span></td>  
    <td>使用登记证号码：</td>      
   <td><input id="registCode" name="registCode"></input></td>
   </tr>
   <tr> 
    <td>地址：</td>      
   <td><input id="address" name="address"></input><span class="fontShow"><font color="red">*</font></span></td> 
   <td>名称：</td>      
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
   <option value="青白江">青白江</option>
   <option value="龙泉驿">龙泉驿</option>
   <option value="新都">新都</option> 
</select><span class="fontShow"><font color="red">*</font></span></td> 
   <td>所在乡镇/街道：</td>      
   <!-- <td><input id="townshipStreets" name="townshipStreets"></input></td>  -->
     <td><select id="townshipStreets"  class="easyui-combobox" name="townshipStreets" style="width:152px;"> 
</select><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
    <tr> 
    <td>楼盘名称：</td>      
   <td><input id="buildingName" name="buildingName"></input></td> 
   <td colspan="2">      
  <!--  经度：<span style="padding-left:62px"><input id="coordinates" name="coordinates" style="width:100px;"></input></span><br> -->
        经度：<span style="padding-left:62px"><input id="map_X" name="map_X" style="width:100px;"></input></span><br> 
        纬度：<span style="padding-left:62px"><input id="map_Y" name="map_Y" style="width:100px;"></input></span>
   <img id="imgpic2"  name="imgpic2" src="../../images/html.png" style="cursor: hand"/>
   </td>
   </tr>
    <tr> 
    <td>栋：</td>      
   <td><input id="building" name="building"></input></td> 
   <td>单元：</td>      
   <td><input id="unit" name="unit"></input></td>
   </tr>
   <tr>
   <td>类别：</td>      
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
   <td>注册代码：</td>      
   <td><input id="registCode2" name="registCode2"></input></td>
   <td>注册登记人员：</td>      
   <td><input id="registor" name="registor"></input></td>
   </tr>
   <tr>
   <td colspan="4"><hr></td>
   </tr>
    <tr>      
   <td>制造单位：</td>      
   <td><select id="zzCompanyId"  class="easyui-combobox" name="zzCompanyId" style="width:152px;"> 
</select><span class="fontShow"><font color="red">*</font></span></td>
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
   <td><select id="jyCompanyId"  class="easyui-combobox" name="jyCompanyId" style="width:152px;"> 
</select><span class="fontShow"><font color="red">*</font></span></td>
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
   <td><select id="azCompanyId"  class="easyui-combobox" name="azCompanyId" style="width:152px;"> 
</select><span class="fontShow"><font color="red">*</font></span></td>
<td>投用日期：</td>      
   <td><input id="useDate" name="useDate"></input></td>
   </tr>
     <tr>
   <td>物管单位：</td>      
  <!--  <td><input id="qy_nameinfo" name="qy_name"></input><span class="fontShow"><font color="red">*</font></span></td>   -->  
   <td><select id="wgCompanyId"  class="easyui-combobox" name="wgCompanyId" style="width:152px;"> 
</select><span class="fontShow"><font color="red">*</font></span></td> 
    <td>内部使用编号：</td>      
   <td><input id="useNumber" name="useNumber"></input></td> 
   </tr>
   <tr>
   <td>运维单位：</td>      
   <td><select id="ywCompanyId"  class="easyui-combobox" name="ywCompanyId" style="width:152px;"> 
</select><span class="fontShow"><font color="red">*</font></span></td>
  
   </tr>
   <tr>
   <td>质监单位：</td>      
   <td><select id="zjCompanyId"  class="easyui-combobox" name="zjCompanyId" style="width:152px;"> 
</select><span class="fontShow"><font color="red">*</font></span></td>
   </tr>
   <tr>
   <td colspan="4"><hr></td>
   </tr>
   <tr>
    <td>型号：</td>      
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
     <td>层站（楼层）：</td>      
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
    <div id="p" class="easyui-window" title="地图信息" closed="true" style="width:600px;height:480px;padding:5px;"> 
     
    </div>  

</body>
</html>