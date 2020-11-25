<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单审批</title>
    <%@ include file="../../include/head.jsp"%>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">订单明细</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                    <thead>
                                    <tr>
                                            <th>商品信息</th>
                                            <th>供应商</th>
                                            <th>商品单价</th>
                                            <th>购买数量</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${orderDetailList}" var="orderDetail" varStatus="status">
                                        <tr>
                                                <td><a onclick="openModelAdjust('商品详情','${ctx }/product/detail?productNo=${orderDetail.productNo}',false,false)">${orderDetail.itemName }</a></td>
                                                <td>${orderDetail.supplyName}</td>
                                                <td>${orderDetail.salePrice}</td>
                                                <td>${orderDetail.saleNum}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript" src="${ctxStatic}/custom/js/amazeui.select.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
        }
        
        initSelectValue(true);
    });
</script>
</body>
</html>
