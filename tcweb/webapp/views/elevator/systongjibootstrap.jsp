<%@ page import="com.zytx.models.UserInfo" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta name="viewport" content="width=device-width, 
                                     initial-scale=1.0, 
                                     maximum-scale=1.0, 
                                     user-scalable=no"> 
   <title>在线尝试 Bootstrap 实例</title>
   <link rel="stylesheet" href="<%=request.getContextPath()%>/bsscripts/css/bootstrap.min.css">
   <link rel="stylesheet" href="<%=request.getContextPath()%>/bsscripts/plugins/bootstrap-table.css">    
   <script type="text/javascript" src="<%=request.getContextPath()%>/bsscripts/js/jquery-3.1.1.min.js"></script>
   <script  type="text/javascript" src="<%=request.getContextPath()%>/bsscripts/js/bootstrap.min.js"></script>
   <script  type="text/javascript" src="<%=request.getContextPath()%>/bsscripts/plugins/bootstrap-table.js"></script>
   <script  type="text/javascript" src="<%=request.getContextPath()%>/bsscripts/plugins/bootstrap-table-export.js"></script>
   <script type="text/javascript">
   $(function(){

		$.ajaxSetup ({
		    cache: false 
		});

		
		
			
			
			
                	
        	jQuery.post({
                  url : "/tcweb/elevator/systongji",
                  type : "post",
                  dataType : "json",
                  success : function(value) {

                      var obj = [];
                      $.each(value.rows, function(i, d) {

                          obj.push(d);
                          // alert(obj.coursename);
                      });      	
		                      
		 $('#systongjitable').bootstrapTable({
	            method: 'post',
	            data : obj, //最终的JSON数据放在这里  
	            striped: true,     //使表格带有条纹
	    //        pagination: true,    //在表格底部显示分页工具栏
	   //         pageSize: 22,
	   //         pageNumber: 1,
	    //        pageList: [10, 20, 50, 100, 200, 500],
	    //        idField: "ProductId",  //标识哪个字段为id主键
	            showToggle: false,   //名片格式
	            cardView: false,//设置为True时显示名片（card）布局
	            showColumns: true, //显示隐藏列  
	            showRefresh: true,  //显示刷新按钮
	            showExport: true, //是否显示表格导出功能，
	        	exportDataType : "all", //basic'导出当前页, 'all'导出全部, 'selected'导出选中项
	        	exportTypes: ['txt', 'excel'], //设置表格导出格式，
	        	exportFooter : true,
	            singleSelect: true,//复选框只能选择一条记录
	            search: false,//是否显示右上角的搜索框
	            clickToSelect: true,//点击行即可选中单选/复选框
	       //     sidePagination: "server",//表格分页的位置
	            queryParams: function queryParams(params) {   //设置查询参数  
	                            
	                  }, //参数
	            queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
	            toolbar: "#toolbar", //设置工具栏的Id或者class
	            columns: [{field:'area',title:'行政区划'},
	                      {field:'etotal',title:'电梯数量'},
	          	          {field:'setotal',title:'停用数量'},
	          	          {field:'ncqetotal',title:'维保超期数量'},
	          	          {field:'tsrtotal',title:'投诉数量'},
	          	          {field:'undotsrtotal',title:'投诉未处理'},
	          	          {field:'nutotal',title:'投诉未处理率(%)'},
	          	          {field:'cql',title:'维保超期率(%)'}
		      	            ], //列
	            silent: true,  //刷新事件必须设置
	            formatLoadingMessage: function () {
	                return "请稍等，正在加载中...";
	            },
	            formatNoMatches: function () {  //没有匹配的结果
	                return '无符合条件的记录';
	            },
	            onLoadError: function (data) {
	                $('#systongjitable').bootstrapTable('removeAll');
	            },
	            onClickRow: function (row) {
	       //         window.location.href = "/qStock/qProInfo/" + row.ProductId;
	            }
	        });
                	}});
		
		}
		);
  
   </script>
</head>
<body>
<div class="container">
<table class="table table-hover" id ="systongjitable" ></table>
</div>

  

</body>
</html>