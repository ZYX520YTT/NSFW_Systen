<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>信息发布管理</title>
    <script type="text/javascript">
        function doAdd() {

            document.forms[0].action = "${basePath}info/info_addUI.action";
            document.forms[0].submit();
        }

        function doDelete(id) {
            document.forms[0].action = "${basePath}info/info_delete.action?info.infoId="+id;
            document.forms[0].submit();
        }

        function doDeleteAll(){
            document.forms[0].action = "${basePath}info/info_deleteSelect.action";
            document.forms[0].submit();
        }
        function doEdit(id) {
            document.forms[0].action = "${basePath}info/info_editUI.action?info.infoId="+id;
            document.forms[0].submit();
        }

        function doPublic (infoId,state){
            $.ajax(
                    {
                        url: "${basePath}info/info_doPublic.action",
                        data: { "info.infoId": infoId,"info.state": state},
                        type: "post",
                        success: function (backData) {

                            if ("更新成功" == backData) {

                                if (state == 0) {//如果用户点击的是停用

                                    //将超链接改成发布
                                    //$("#operator_"+infoId).html('<a href="javascript:doPublic(\''+infoId+'\',1)">发布</a>');
                                    $("#operator_"+infoId).html("<a href='javascript:doPublic(\""+infoId+"\",1)'>发布</a>");

                                    //将显示状态改成是停用
                                    $("#show_" + infoId).html("停用");

                                }else{//用户点击的是发布

                                    //将超链接改成停用
                                   //$("#operator_"+infoId).html('<a href="javascript:doPublic(\''+infoId+'\',0)">停用</a>');
                                    $("#operator_"+infoId).html("<a href='javascript:doPublic(\""+infoId +"\",0)'>停用</a>");

                                    //将显示状态改成是发布
                                    $("#show_" + infoId).html("发布");
                                }

                            }else {
                                alert("更新失败，稍后重试");
                            }
                        },
                        //如果失败了，就提示给用户，不要让用户继续操作了
                        error:function () {
                            alert("更新失败，稍后重试");
                        }

                    }
            );
        }

        function doSearch() {

            document.forms[0].action = "${basePath}info/info_listUI.action";
            document.forms[0].submit();
        }
    </script>
</head>
<body class="rightBody">
<form name="form1" action="" method="post">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
                <div class="c_crumbs"><div><b></b><strong>信息发布管理</strong></div> </div>
                <div class="search_art">
                    <li>
                        <%--这里是查询条件--%>
                        信息标题：<s:textfield name="info.title" cssClass="s_text" id="infoTitle"  cssStyle="width:160px;"/>
                    </li>
                    <li><input type="button" class="s_button" value="搜 索" onclick="doSearch()"/></li>
                    <li style="float:right;">
                        <input type="button" value="新增" class="s_button" onclick="doAdd()"/>&nbsp;
                        <input type="button" value="删除" class="s_button" onclick="doDeleteAll()"/>&nbsp;
                    </li>
                </div>

                <div class="t_list" style="margin:0px; border:0px none;">
                    <table width="100%" border="0">
                        <tr class="t_tit">
                            <td width="30" align="center"><input type="checkbox" id="selAll" onclick="doSelectAll()" /></td>
                            <td align="center">信息标题</td>
                            <td width="120" align="center">信息分类</td>
                            <td width="120" align="center">创建人</td>
                            <td width="140" align="center">创建时间</td>
                            <td width="80" align="center">状态</td>
                            <td width="120" align="center">操作</td>
                        </tr>
                        <s:iterator value="pageResult.list" status="st">
                            <tr <s:if test="#st.odd"> bgcolor="f8f8f8" </s:if> >
                                <td align="center"><input type="checkbox" name="selectedRow" value="<s:property value='infoId'/>"/></td>
                                <td align="center"><s:property value="title"/></td>
                                <td align="center">
                                	<s:property value="#infoTypeMap[type]"/>	
                                </td>
                                <td align="center"><s:property value="creator"/></td>
                                <td align="center"><s:date name="createTime" format="yyyy-MM-dd HH:mm"/></td>
                                <td id="show_<s:property value='infoId'/>" align="center"><s:property value="state==1?'发布':'停用'"/></td>
                                <td align="center">
                                	<span id="operator_<s:property value='infoId'/>">
                                      <s:if test="state==1">
                                            <a href="javascript:doPublic('<s:property value='infoId'/>',0)">停用</a>
                                        </s:if>
                                        <s:else>
                                            <a href="javascript:doPublic('<s:property value='infoId'/>',1)">发布</a>
                                        </s:else>
                                	</span>
                                    <a href="javascript:doEdit('<s:property value='infoId'/>')">编辑</a>
                                    <a href="javascript:doDelete('<s:property value='infoId'/>')">删除</a>
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </div>
        <div class="c_pate" style="margin-top: 5px;">
<%--		<table width="100%" class="pageDown" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td align="right">
                 	总共1条记录，当前第 1 页，共 1 页 &nbsp;&nbsp;
                            <a href="#">上一页</a>&nbsp;&nbsp;<a href="#">下一页</a>
					到&nbsp;<input type="text" style="width: 30px;" onkeypress="if(event.keyCode == 13){doGoPage(this.value);}" min="1"
					max="" value="1" /> &nbsp;&nbsp;
			    </td>
			</tr>
		</table>	--%>
    <jsp:include page="/common/pageNavigator.jsp"/>
        </div>

        </div>
    </div>
</form>

</body>
</html>