<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单发货商品表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
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
                            <div class="widget-title am-fl">订单发货商品表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <table class="am-u-sm-12">
                                    <tr>
                                        <td class="am-padding-sm">
                                            <shiro:hasPermission name="order:orderDeliveryDetail:create">
                                                <button type="button" class="am-btn am-btn-xs am-btn-default am-btn-success"
                                                        onclick="openModel(false,'${ctx}/order/orderDeliveryDetail/create')"><span class="am-icon-plus"></span> 新增
                                                </button>
                                            </shiro:hasPermission>
                                        </td>
                                    </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/order/orderDeliveryDetail/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                            <tr>
                                                <td class="am-padding-sm am-text-sm am-text-right">ID</td>
                                                <td>
                                                    <input type="text" class="am-input-sm"  name="id"  value="${page.entity.id}">
                                                </td>
                                                <td class="am-padding-sm"><button class="am-btn am-btn-xs am-btn-success am-icon-search"> 查询</button></td>
                                            </tr>
                                        </table>
                                    </div>
                                </form>
                            </div>

                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                    <thead>
                                    <tr>
                                            <th>编号</th>
                                            <th>订单id</th>
                                            <th>订单编号</th>
                                            <th>订单发货id</th>
                                            <th>订单明细id</th>
                                            <th>站点商品ID</th>
                                            <th>商品名称</th>
                                            <th>供应商ID</th>
                                            <th>供应商名称</th>
                                            <th>发货数量</th>
                                            <th></th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="orderDeliveryDetail" varStatus="status">
                                        <tr>
                                                <td>${orderDeliveryDetail.id}</td>
                                                <td>${orderDeliveryDetail.orderId}</td>
                                                <td>${orderDeliveryDetail.orderNo}</td>
                                                <td>${orderDeliveryDetail.deliveryId}</td>
                                                <td>${orderDeliveryDetail.orderDetailId}</td>
                                                <td>${orderDeliveryDetail.itemId}</td>
                                                <td>${orderDeliveryDetail.itemName}</td>
                                                <td>${orderDeliveryDetail.supplyId}</td>
                                                <td>${orderDeliveryDetail.supplyName}</td>
                                                <td>${orderDeliveryDetail.deliveryNum}</td>
                                                <td>${orderDeliveryDetail.status}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                <shiro:hasPermission name="order:orderDeliveryDetail:update">
                                                    <a role="oper" href="javascript:;" onclick="openModel('修改订单发货商品表','${ctx}/order/orderDeliveryDetail/update?id=${orderDeliveryDetail.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 编辑</a>
                                                </shiro:hasPermission>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="am-u-lg-12 am-cf">
                                <%@ include file="../../utils/pagination.jsp"%>
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
    });
</script>
</body>
</html>
