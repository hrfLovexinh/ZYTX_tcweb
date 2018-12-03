<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="ww" uri="/webwork"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript">
$(function(){

	$.ajaxSetup ({
	    cache: false 
	});

	$("#tt").datagrid({
        height: 300,
        width: 800,
        title: '信息表',
        collapsible: true,
        singleSelect: true,
        url: '/Home/StuList',
        columns: [[
         { field: 'odd1', title: 'odd1', width: 100 },
            { field: 'pankou', title: '----', width: 100, editor: { type: 'text', options: { required: true } } },
            { field: 'odd2', title: 'odd2', width: 100, align: 'center', editor: { type: 'text', options: { required: true } } },
            { field: 'score', title: 'score', width: 100, align: 'center', editor: { type: 'text', options: { required: true } } },
            { field: 's', title: 's', width: 100, align: 'center', editor: { type: 'text', options: { required: true } } },
            { field: 'p', title: 'p', width: 100, align: 'center', editor: { type: 'text', options: { required: true } } },
            { field: 'f', title: 'f', width: 100, align: 'center', editor: { type: 'text', options: { required: true } } }
        ]],
        toolbar: [{
            text: '添加', iconCls: 'icon-add', handler: function () {
                if (editRow != undefined) {
                    $("#Student_Table").datagrid('endEdit', editRow);
                }
                if (editRow == undefined) {
                    $("#Student_Table").datagrid('insertRow', {
                        index: 0,
                        row: {}
                    });
 
                    $("#Student_Table").datagrid('beginEdit', 0);
                    editRow = 0;
                }
            }
        }, '-', {
            text: '保存', iconCls: 'icon-save', handler: function () {
                $("#Student_Table").datagrid('endEdit', editRow);
 
                //如果调用acceptChanges(),使用getChanges()则获取不到编辑和新增的数据。
 
                //使用JSON序列化datarow对象，发送到后台。
                var rows = $("#Student_Table").datagrid('getChanges');
 
                var rowstr = JSON.stringify(rows);
                $.post('/Home/Create', rowstr, function (data) {
                     
                });
            }
        }, '-', {
            text: '撤销', iconCls: 'icon-redo', handler: function () {
                editRow = undefined;
                $("#Student_Table").datagrid('rejectChanges');
                $("#Student_Table").datagrid('unselectAll');
            }
        }, '-', {
            text: '删除', iconCls: 'icon-remove', handler: function () {
                var row = $("#Student_Table").datagrid('getSelections');
                 
            }
        }, '-', {
            text: '修改', iconCls: 'icon-edit', handler: function () {
                var row = $("#Student_Table").datagrid('getSelected');
                if (row !=null) {
                    if (editRow != undefined) {
                        $("#Student_Table").datagrid('endEdit', editRow);
                    }
 
                    if (editRow == undefined) {
                        var index = $("#Student_Table").datagrid('getRowIndex', row);
                        $("#Student_Table").datagrid('beginEdit', index);
                        editRow = index;
                        $("#Student_Table").datagrid('unselectAll');
                    }
                } else {
                     
                }
            }
        }, '-', {
            text: '上移', iconCls: 'icon-up', handler: function () {
                MoveUp();
            }
        }, '-', {
            text: '下移', iconCls: 'icon-down', handler: function () {
                MoveDown();
            }
        }],
        onAfterEdit: function (rowIndex, rowData, changes) {
            editRow = undefined;
        },
        onDblClickRow:function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#Student_Table").datagrid('endEdit', editRow);
            }
 
            if (editRow == undefined) {
                $("#Student_Table").datagrid('beginEdit', rowIndex);
                editRow = rowIndex;
            }
        },
        onClickRow:function(rowIndex,rowData){
            if (editRow != undefined) {
                $("#Student_Table").datagrid('endEdit', editRow);
 
            }
            
        }
        
    }); 
  
	});
</script>
</head>
<body>

<div region="center">
       <div id="main-center"  fit="true" border="false">  
        
          <div style="margin-top:1px;">  
       <table id="tt"></table>
   </div>  
        
    </div>  
</div>   

</body>
</html>