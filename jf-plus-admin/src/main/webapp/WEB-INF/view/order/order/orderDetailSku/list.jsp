<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单明细表</title>
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
                            <div class="widget-title am-fl">订单明细表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <table class="am-u-sm-12">
                                    <tr>
                                        <td class="am-padding-sm">
                                            <shiro:hasPermission name="order:orderDetailSku:create">
                                                <button type="button" class="am-btn am-btn-xs am-btn-default am-btn-success"
                                                        onclick="openModel(false,'${ctx}/order/orderDetailSku/create')"><span class="am-icon-plus"></span> 新增
                                                </button>
                                            </shiro:hasPermission>
                                        </td>
                                    </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/order/orderDetailSku/list" method="post">
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
                                            <th></th>
                                            <th>订单ID</th>
                                            <th>订单号</th>
                                            <th>订单子单号</th>
                                            <th>商品详细表ID</th>
                                            <th>站点商品id</th>
                                            <th></th>
                                            <th>供应商id</th>
                                            <th>供应价</th>
                                            <th>销售价</th>
                                            <th>销售数量</th>
                                            <th>销售金额</th>
                                            <th>销售积分价</th>
                                            <th>冻结库存量</th>
                                            <th>SKU ID</th>
                                            <th></th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="orderDetailSku" varStatus="status">
                                        <tr>
                                                <td>${orderDetailSku.id}</td>
                                                <td>${orderDetailSku.orderId}</td>
                                                <td>${orderDetailSku.orderNo}</td>
                                                <td>${orderDetailSku.orderSubno}</td>
                                                <td>${orderDetailSku.orderDetailId}</td>
                                                <td>${orderDetailSku.itemId}</td>
                                                <td>${orderDetailSku.itemName}</td>
                                                <td>${orderDetailSku.supplyId}</td>
                                                <td>${orderDetailSku.supplyPrice}</td>
                                                <td>${orderDetailSku.salePrice}</td>
                                                <td>${orderDetailSku.saleNum}</td>
                                                <td>${orderDetailSku.amount}</td>
                                                <td>${orderDetailSku.salePoints}</td>
                                                <td>${orderDetailSku.freezeNum}</td>
                                                <td>${orderDetailSku.skuId}</td>
                                                <td>${orderDetailSku.status}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                <shiro:hasPermission name="order:orderDetailSku:update">
                                                    <a role="oper" href="javascript:;" onclick="openModel('修改订单明细表','${ctx}/order/orderDetailSku/update?id=${orderDetailSku.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 编辑</a>
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
