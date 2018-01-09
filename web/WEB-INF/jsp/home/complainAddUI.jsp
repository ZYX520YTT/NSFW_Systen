<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    pageContext.setAttribute("basePath", request.getContextPath() + "/");
%>
<html>
<head>
    <%@include file="/common/header.jsp" %>
    <title>我要投诉</title>

    <script type="text/javascript" charset="utf-8" src="${basePath}js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${basePath}js/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${basePath}js/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">
        //配置ueditor的根路径
        var UEDITOR_HOME_URL = "${basePath}js/ueditor/";
        var ue = UE.getEditor('editor');
    </script>


    <script type="text/javascript">
        function doSelectDept() {
            var $dept = $("#toCompDept option:selected").val();
            if($dept =="0"){
               $("#toCompName").empty();
            }
            $.ajax({
                type: "post",
                url: "${basePath}sys/home_getUserJson.action",
                data: {"dept":$dept},
                dataType: "json",
                success: function (data) {
                    if("success" == data.msg){
                        var toCompName = $("#toCompName");
                        toCompName.empty();
                        $.each(data.userList, function(index, user){
                            toCompName.append("<option value='" + user.name + "'>" + user.name + "</option>");
                        });
                    } else {alert("获取被投诉人列表失败！");}
                },
                error: function () {
                    alert("失败咯")
                }
            });
        }

        function saveComplain() {
            $.ajax({
                url: "${basePath}sys/home_saveComplain.action",
                /*将整个表单的属性转成是JSON*/
                data: $("form").serialize(),
                type: "post",
                success: function (backdata) {
                    if(backdata == "success"){
                        //告诉用户，保存成功了。
                        alert("投诉成功！！！");
                        //把父窗口刷新
                        window.opener.parent.location.reload(true);
                        //把本页面关闭
                        window.close();
                    }

                },
                error:function () {
                    alert("保存投诉信息失败了！");
                }
            });
        }



    </script>
</head>
<body>
<form id="form" name="form" action="" method="post" enctype="multipart/form-data">
    <div class="vp_d_1">
        <div style="width:1%;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
        <div class="vp_d_1_1">
            <div class="content_info">
                <div class="c_crumbs">
                    <div><b></b><strong>工作主页</strong>&nbsp;-&nbsp;我要投诉</div>
                </div>
                <div class="tableH2">我要投诉</div>
                <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0"
                       cellspacing="0">
                    <tr>
                        <td class="tdBg" width="250px">投诉标题：</td>
                        <td><s:textfield name="comp.compTitle"/></td>
                    </tr>
                    <tr>
                        <td class="tdBg">被投诉人部门：</td>
                        <td>
                            <s:select id="toCompDept" name="comp.toCompDept" list="#{'0':'请选择','部门A':'部门A','部门B':'部门B' }"
                                      onchange="doSelectDept()"/></td>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdBg">被投诉人姓名：</td>
                        <td>
                            <select name="comp.toCompName" id="toCompName">
                            </select>

                        </td>
                    </tr>
                    <tr>
                        <td class="tdBg">投诉内容：</td>
                        <td><s:textarea id="editor" name="comp.compContent" cssStyle="width:90%;height:160px;"/></td>
                    </tr>
                    <tr>
                        <td class="tdBg">是否匿名投诉：</td>
                        <td><s:radio name="comp.isNm" list="#{'0':'非匿名投诉','1':'匿名投诉' }" value="1"/></td>
                    </tr>
                </table>

                <div class="tc mt20">
                    <input type="button" class="btnB2" value="保存" onclick="saveComplain()"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" onclick="javascript:window.close()" class="btnB2" value="关闭"/>
                </div>
            </div>
        </div>
        <div style="width:1%;float:left;">&nbsp;&nbsp;&nbsp;&nbsp;</div>
    </div>

    <s:hidden name="comp.compCompany" value="%{#session.SYS_USER.dept}"></s:hidden>
    <s:hidden name="comp.compName" value="%{#session.SYS_USER.name}"></s:hidden>
    <s:hidden name="comp.compMobile" value="%{#session.SYS_USER.mobile}"></s:hidden>
</form>
</body>
</html>