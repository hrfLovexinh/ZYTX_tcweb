<%@ page import="com.zytx.models.UserInfo" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>分组统计</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/myeasyuiicon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.autocomplete.css">

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.autocomplete.js"></script>
<style type="text/css">

</style>
<script type="text/javascript">
$(function(){

	$.ajaxSetup ({
	    cache: false 
	});
   /*
	var data =[{
		"id":0,
		"text":"Foods",
		"children":[{
			"id":1,
			"text":"zu1",
			"children":[{
				"id":11,
				"text":"zu11"
			},{
				"id":12,
				"text":"zu12"
			}]
		},{
			"id":2,
			"text":"zu2",
			"state":"closed",
			"children":[{
				"id":21,
				"text":"zu21"
			},{
				"id":22,
				"text":"zu22"
			},{
				"id":23,
				"text":"zu23"
			},{
				"id":24,
				"text":"zu24"
			},{
				"id":25,
				"text":"zu25"
			}]
		}]
	}];
  
	$("#tt").treegrid({
		                     data: data,
		                     width: "auto",
		                     height: 455,
		                     iconCls: 'icon icon-org',
		                     idField: 'id', //必须有的  这个字段实际为 tree树的层级结构 
		                     fitColumns: false,
		                     striped: true,
		                     animate: true,
		                     nowrap: true,
		                     treeField: 'gName',//必须有 该字段将是渲染后的tree节点
		                     loadMsg: "加载中......",
		                     //queryParams: { "queryParams": JSON.stringify(params) },
		                     frozenColumns: [[
		                       { title: 'id', field: 'id', hidden: true },
		                        {
		                            title: '组名', field: 'text', width: 240, rowspan: 2
		                        }]],
		                     columns: [[
		                          {
		                              field: 'text', title: '负责人 ', align: "center", width: 150, rowspan: 2 
		                          }
		                     

		                     ]],
		                     onLoadSuccess: function () {
		                         $("#tt").treegrid("collapseAll");//默认加载完成后 全部折叠
		                         $("span.tree-icon").removeClass("tree-folder");
		                         $("span.tree-icon").removeClass("tree-file");//去掉tree前面的图标
		                     }
		                 });   */
	
});



//得到父节点
var node = $('#tt').tree('getSelected');

//附加节点
var node = $('#tt').tree('getSelected');
	if (node){
		var nodes = [{
			"id":13,
			"text":"Raspberry"
		},{
			"id":14,
			"text":"Cantaloupe"
		}];
		$('#tt').tree('append', {
			parent:node.target,
			data:nodes
		});
	}
</script>
</head>
<body><!--  
	<h2>Tree Context Menu</h2>
	<p>Right click on a node to display context menu.</p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" style="padding:5px">
		<ul id="tt" class="easyui-tree" data-options="
				url: 'tree_data.json',
				method: 'get',
				animate: true,
				onContextMenu: function(e,node){
					e.preventDefault();
					$(this).tree('select',node.target);
					$('#mm').menu('show',{
						left: e.pageX,
						top: e.pageY
					});
				}
			"></ul>
	</div>
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="append()" data-options="iconCls:'icon-add'">Append</div>
		<div onclick="removeit()" data-options="iconCls:'icon-remove'">Remove</div>
		<div class="menu-sep"></div>
		<div onclick="expand()">Expand</div>
		<div onclick="collapse()">Collapse</div>
	</div> -->
	<script type="text/javascript">
		function append(){
			var t = $('#tt');
			var node = t.tree('getSelected');
			t.tree('append', {
				parent: (node?node.target:null),
				data: [{
					text: 'new item1'
				},{
					text: 'new item2'
				}]
			});
		}
		function removeit(){
			var node = $('#tt').tree('getSelected');
			$('#tt').tree('remove', node.target);
		}
		function collapse(){
			var node = $('#tt').tree('getSelected');
			$('#tt').tree('collapse',node.target);
		}
		function expand(){
			var node = $('#tt').tree('getSelected');
			$('#tt').tree('expand',node.target);
		}
	</script>
</body>
</html>