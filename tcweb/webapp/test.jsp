<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0
Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-
transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>jQuery Ajax</title>
<script type="text/javascript">
$(function()
{
var xhr = new AjaxXmlHttpRequest();
$("#btnAjaxOld").click(function(event)
{
var xhr = new AjaxXmlHttpRequest();
xhr.onreadystatechange = function()
{
if (xhr.readyState == 4)
{
document.getElementById("divResult").innerHTML
= xhr.responseText;
}
}
xhr.open("GET",
"data/AjaxGetCityInfo.aspx?resultType=html", true);
xhr.send(null);
});
})
//跨浏览器获取XmlHttpRequest 对象
function AjaxXmlHttpRequest()
{
var xmlHttp;
try
{
// Firefox, Opera 8.0+, Safari
xmlHttp = new XMLHttpRequest();
}
catch (e)
{
// Internet Explorer
try
{
xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
}
catch (e)
{
try
{
xmlHttp = new
ActiveXObject("Microsoft.XMLHTTP");
}
catch (e)
{
alert("您的浏览器不支持AJAX！");
return false;
}
}
}
return xmlHttp;
}

$("#divResult").load("data/AjaxGetCityInfo.aspx", { "resultType": "html" });
</script>
</head>
<body>
<button id="btnAjaxOld">原始Ajax 调用</button><br />
<br />
<div id="divResult"></div>
</body>
</html>