<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单导出表</title>
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
                            <div class="widget-title am-fl">订单导出表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <table class="am-u-sm-12">
                                    <tr>
                                        <td class="am-padding-sm">
                                            <shiro:hasPermission name="order:orderDeliveryExport:create">
                                                <button type="button" class="am-btn am-btn-xs am-btn-default am-btn-success"
                                                        onclick="openModel(false,'${ctx}/order/orderDeliveryExport/create')"><span class="am-icon-plus"></span> 新增
                                                </button>
                                            </shiro:hasPermission>
                                        </td>
                                    </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/order/orderDeliveryExport/list" method="post">
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
                                            <th>站点商品ID</th>
                                            <th>商品名称</th>
                                            <th>商品数量</th>
                                            <th>导出销售价格</th>
                                            <th>商品编码</th>
                                            <th>订单商品SKU</th>
                                            <th>导出时间</th>
                                            <th></th>
                                            <th>操作状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="orderDeliveryExport" varStatus="status">
                                        <tr>
                                                <td>${orderDeliveryExport.id}</td>
                                                <td>${orderDeliveryExport.orderId}</td>
                                                <td>${orderDeliveryExport.orderNo}</td>
                                                <td>${orderDeliveryExport.deliveryId}</td>
                                                <td>${orderDeliveryExport.itemId}</td>
                                                <td>${orderDeliveryExport.itemName}</td>
                                                <td>${orderDeliveryExport.deliveryNum}</td>
                                                <td>${orderDeliveryExport.exportPrice}</td>
                                                <td>${orderDeliveryExport.itemCode}</td>
                                                <td>${orderDeliveryExport.orderDetailSkuId}</td>
                                                <td>${orderDeliveryExport.exportDate}</td>
                                                <td>${orderDeliveryExport.status}</td>
                                                <td>${orderDeliveryExport.operStatus}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                <shiro:hasPermission name="order:orderDeliveryExport:update">
                                                    <a role="oper" href="javascript:;" onclick="openModel('修改订单导出表','${ctx}/order/orderDeliveryExport/update?id=${orderDeliveryExport.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 编辑</a>
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
