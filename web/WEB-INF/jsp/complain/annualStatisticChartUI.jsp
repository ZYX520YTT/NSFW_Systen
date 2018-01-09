<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

    Calendar calendar = Calendar.getInstance();

    //得到当前年的值
    int  year = calendar.get(Calendar.YEAR);

    //把年份用一个集合装载
    List yearList = new ArrayList();

    //获取近5年的值
    for(int i =0; i<5; i++) {
        yearList.add(i, year--);
    }
    request.setAttribute("yearList", yearList);
    request.setAttribute("year", year);
%>

<!DOCTYPE HTML>
<html>
<head>
    <%@include file="/common/header.jsp" %>
    <title>年度投诉统计图</title>
    <script type="text/javascript" src="${basePath}js/fusioncharts.js"></script>
    <script type="text/javascript" src="${basePath}js/fusioncharts.charts.js"></script>
    <script type="text/javascript" src="${basePath}js/themes/fusioncharts.theme.fint.js"></script>
    <script>

        //页面一加载就执行方法
        $(function () {
            doAnnualStatistic();
        });


        //根据年份获取投诉数
        function doAnnualStatistic() {
            //获取当前年份
            var $year = $("#year option:selected").val();

            //一进来，如果没有选择任何的年数，就显示当前年份的
            if($year=="" || $year==undefined) {
               $year = "${year}";
            }
            //2、统计年度投诉数据并展示图表
            $.ajax({
                url: "${basePath}complain/complain_getAnnualStatisticData.action",
                type: "post",
                dataType: "json",
                data: {"year":$year},
                success: function (backData) {
                    if(backData!=null && backData!=""){
                        var revenueChart = new FusionCharts({
                            "type": "line",
                            "renderAt": "chartContainer",
                            "width": "600",
                            "height": "400",
                            "dataFormat": "json",
                            "dataSource": {
                                "chart": {
                                    "caption": "年度统计投诉数",
                                    "xAxisName": "月   份",
                                    "yAxisName": "投  诉 数",
                                    "theme": "fint"
                                },
                                "data":backData.chartData
                            }
                        });
                        revenueChart.render();
                    }
                },
                error:function () {
                    alert("统计投诉数失败！");
                }
            });
        }
    </script>
</head>


<body>
<br>
<s:select id="year" list="#request.yearList" onchange="doAnnualStatistic()" cssStyle="margin-left:300px;"></s:select>
<br>
<div id="chartContainer" style="text-align: center;width: 100%"></div>
</body>
</html>
