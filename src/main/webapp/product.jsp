<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="ctx" value="${pageContext.request.contextPath}" ></c:set>
    <link rel="stylesheet" type="text/css" href="${ctx}/statics/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/statics/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${ctx}/statics/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/statics/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/statics/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">


        function searchProduct(){
            $("#dg").datagrid('load',{
                "name":$("#s_productName").val()
            });
        }

        function exportProduct(){
            window.location.href="${ctx}/product/excel/export";
        }

        function openUploadFileDialog(){
            $("#dlg2").dialog('open').dialog('setTitle','excel批量导入数据');
        }

        function uploadExcel(){
            $("#uploadForm").form("submit",{
                success:function(result){
                    var result=eval('('+result+')');
                    if(result.code!=0){
                        $.messager.alert("系统提示",result.msg);
                    }else{
                        $.messager.alert("系统提示","上传成功");
                        $("#dlg2").dialog("close");
                        $("#dg").datagrid("reload");
                    }
                }
            });
        }

    </script>
    <title>产品信息页面</title>
</head>
<body style="margin: 1px">
<%-- <table id="dg" title="产品信息查询" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true"
        url="${ctx}/poi/list.do" method="get" fit="true" toolbar="#tb"> --%>

<table id="dg" title="产品信息列表" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true"
       fit="true" toolbar="#tb" data-options="singleSelect:true,collapsible:true,url:'${ctx}/product/list',method:'get'" >
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>

        <th data-options="field:'id',width:50">编号</th>
        <th data-options="field:'name',width:200">产品名称</th>
        <th data-options="field:'unit',width:50">单位</th>
        <th data-options="field:'price',width:80">价格</th>
        <th data-options="field:'stock',width:80">库存</th>
        <th data-options="field:'remark',width:200">备注</th>
        <th data-options="field:'purchaseDate',width:100">采购日期</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
         产品名称： <input type="text" id="s_productName" size="20" οnkeydοwn="if(event.keyCode==13) searchProduct()"/>
        <a href="javascript:searchProduct()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
        <a href="javascript:exportProduct()" class="easyui-linkbutton" iconCls="icon-search" plain="true">导出excel</a>
        <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openUploadFileDialog()">导入excel</a>
    </div>
</div>



<div id="dlg2" class="easyui-dialog" style="width:400px;height:180px;padding:10px 20px" closed="true" buttons="#dlg-buttons2">
    <form id="uploadForm" action="${ctx}/product/excel/upload" method="post" enctype="multipart/form-data" >
        <table>
            <!-- <tr>
                <td>下载模版：</td>
                <td><a href="javascript:void(0)" class="easyui-linkbutton"  οnclick="downloadTemplate()">下载模板文件</a></td>
            </tr>   -->
            <tr>
                <td>上传文件：</td>
                <td><input type="file" name="productFile"></td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons2">
    <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="uploadExcel()">上传excel</a>
    <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg2').dialog('close')">关闭</a>
</div>
</body>
</html>