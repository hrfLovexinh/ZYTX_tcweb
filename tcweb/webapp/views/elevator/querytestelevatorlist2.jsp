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
<script type="text/javascript">

$(function () {
    $('#List').datagrid({
        frozenColumns: [[
            { title: 'StationId', field: 'StationId', checkbox: true },
            { title: '基站名称', field: 'StationName', width: 100, sortable: true, align: 'center' }
        ]],
        toolbar: [{
            text: '立即召唤所选基站',
            iconCls: 'icon-add',
            handler: function () {
                var ids = [];
                var rows = $('#List').datagrid('getChecked');
                for (var i = 0; i < rows.length; i++) {
                    ids.push(rows[i].StationId);
                }
                alert(ids);
            }
        }, '-',
        {
            text: '显示隐藏项',
            iconCls: 'icon-edit',
            handler: function () {
                OpenGetShowColumnDlg();
            }
        }
        ]
    });
});
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
                checkedValue += "," + cbx[i].value; //获取已经checked的value
            }
            else {
                checkedValue = cbx[i].value;
            }
        }
        if (!cbx[i].checked) {//获取没有checked的Object
            if (unCheckValue.length > 0) {
                unCheckValue += "," + cbx[i].value; //获取没有checked的value
            }
            else {
                unCheckValue = cbx[i].value;
            }
        }
    }
    var checkeds = new Array();
    if (checkedValue != null && checkedValue != "") {
        checkeds = checkedValue.split(',');
        for (var i = 0; i < checkeds.length; i++) {
            $('#List').datagrid('showColumn', checkeds[i]); //显示相应的列
        }
    }
    var unChecks = new Array();
    if (unCheckValue != null && unCheckValue != "") {
        unChecks = unCheckValue.split(',');
        for (var i = 0; i < unChecks.length; i++) {
            $('#List').datagrid('hideColumn', unChecks[i]); //隐藏相应的列
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
function MoreInfo(rowIndex, rowData) {
    return '<a href="javascript:fAlert(' + rowData.StationId + ')">详细</a>';
}
function fAlert(id) {
    alert('这里用于以后详细信息拓展,点击的行的ID为:'+id);
}


</script>
</head>
<body>
        <div style="float: left; width: 1450px; height:auto;  ">
            <table id="List" class="easyui-datagrid" title="基站信息列表" width="1450px" style="height:580px;" data-options="rownumbers:true,pagination:true,pageSize:20,pagePosition:'top',striped:true,url:'MonitoringOfListType.aspx'">
                <thead>
                    <tr>
                      <%--  <th data-options="field:'StationId',checkbox:true,sortable:true,width:50,align:'center'">
                            ID
                        </th>
                        <th data-options="field:'StationName',sortable:true,width:100,align:'center'">
                            基站名称
                        </th>--%>
                        <th data-options="field:'LineName',sortable:true,width:110,align:'center'">
                            回路名称
                        </th>
                        <th data-options="field:'SIM_Number',sortable:true,width:100,align:'center'">
                            SIM卡号
                        </th>
                        <th data-options="field:'GPRS_Status',sortable:true,width:65,align:'center'">
                            GPRS状态
                        </th>
                        <th data-options="field:'MeterStatus',sortable:true,width:60,align:'center'">
                            电表状态
                        </th>
                        <th data-options="field:'ElePreHour',sortable:true,width:95,align:'center'">
                            上一小时用电量
                        </th>
                        <th data-options="field:'EleCurHour',sortable:true,width:85,align:'center'">
                            本小时用电量
                        </th>
                        <th data-options="field:'ElePreDay',sortable:true,width:85,align:'center'">
                            昨天用电量
                        </th>
                        <th data-options="field:'EleCurDay',sortable:true,width:85,align:'center'">
                            今天用电量
                        </th>
                        <th data-options="field:'ElePreMonth',sortable:true,width:85,align:'center'">
                            上月用电量
                        </th>
                        <th data-options="field:'EleCurMonth',sortable:true,width:85,align:'center'">
                            本月用电量
                        </th>
                        <th data-options="field:'Ia',sortable:true,width:50,align:'center'">
                            Ia
                        </th>
                        <th data-options="field:'Ib',sortable:true,width:50,align:'center'">
                            Ib
                        </th>
                        <th data-options="field:'Ic',sortable:true,width:50,align:'center'">
                            Ic
                        </th>
                        <th data-options="field:'Ua',sortable:true,width:50,align:'center'">
                            Ua
                        </th>
                        <th data-options="field:'Ub',sortable:true,width:50,align:'center'">
                            Ub
                        </th>
                        <th data-options="field:'Uc',sortable:true,width:50,align:'center'">
                            Uc
                        </th>
                        <th data-options="field:'P',sortable:true,width:50,align:'center'">
                            P
                        </th>
                        <th data-options="field:'E',sortable:true,width:50,align:'center'">
                            E
                        </th>
                        <th data-options="field:'Ranking',sortable:true,width:85,align:'center',hidden:true">
                            能耗排名
                        </th>
                        <th data-options="field:'EnergyLevel',sortable:true,width:85,align:'center',hidden:true">
                            能耗等级
                        </th>
                        <th data-options="field:'InfoID',sortable:true,width:85,align:'center',formatter:MoreInfo">
                            详细信息
                        </th>
                    </tr>
                </thead>
            </table>
        </div>
        <div id="GetShowColumn" class="easyui-dialog" style="width: 520px; height: 350px;
            padding: 10px 20px" data-options="closed:true,buttons:'#dlg-GetShowColumn',modal:true">
            <form id="GetShowColumn_Form" method="post" novalidate>
            <input type="hidden" id="hideValues" />
            <input type="hidden" id="showValues" />
            <div class="fitem">
                <input type="checkbox" name="StationName" id="StationName" value="StationName" /><label
                    for="StationName">基站名称</label>
                <input type="checkbox" name="SIM_Number" id="SIM_Number" value="SIM_Number" /><label for="SIM_Number">SIM卡号</label>
                <input type="checkbox" name="GPRS_Status" id="GPRS_Status" value="GPRS_Status" /><label for="GPRS_Status">GPRS状态</label>
                <input type="checkbox" name="MeterStatus" id="MeterStatus" value="MeterStatus" /><label for="MeterStatus">电表状态</label>
            </div>
            <div class="fitem">
                <input type="checkbox" name="PreHour" id="PreHour" value="ElePreHour" /><label
                    for="PreHour">上小时用电量</label>
                <input type="checkbox" name="CurHour" id="CurHour" value="EleCurHour" /><label for="CurHour">本小时用电量</label>
                <input type="checkbox" name="Yesterday" id="Yesterday" value="ElePreDay" /><label for="Yesterday">昨天用电量</label>
                <input type="checkbox" name="ToDay" id="ToDay" value="EleCurDay" /><label for="ToDay">今天用电量</label>
            </div>
            <div class="fitem">
                <input type="checkbox" name="PreMonth" id="PreMonth" value="ElePreMonth" /><label
                    for="PreMonth">上月用电量</label>
                <input type="checkbox" name="CurMonth" id="CurMonth" value="EleCurMonth" /><label for="CurMonth">本月用电量</label>
                <input type="checkbox" name="Ia" id="Ia" value="Ia" /><label for="Ia">Ia</label>
                <input type="checkbox" name="Ib" id="Ib" value="Ib" /><label for="Ib">Ib</label>
            </div>
            <div class="fitem">
                <input type="checkbox" name="Ic" id="Ic" value="Ic" /><label
                    for="Ic">Ic</label>
                <input type="checkbox" name="Ua" id="Ua" value="Ua" /><label for="Ua">Ua</label>
                <input type="checkbox" name="Ub" id="Ub" value="Ub" /><label for="Ub">Ub</label>
                <input type="checkbox" name="Uc" id="Uc" value="Uc" /><label for="Uc">Uc</label>
            </div>
            <div class="fitem">
                <input type="checkbox" name="P" id="P" value="P" /><label
                    for="Names">P</label>
                <input type="checkbox" name="E" id="E" value="E" /><label for="E">E</label>
                <input type="checkbox" name="Ranking" id="Ranking" value="Ranking" /><label for="Ranking">能耗排名</label>
                <input type="checkbox" name="InfoID" id="InfoID" value="InfoID" /><label for="InfoID">详细信息</label>
            </div>
            <div class="fitem">
                <input type="checkbox" name="LineName" id="LineName" value="LineName" /><label for="LineName">回路名称</label>
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