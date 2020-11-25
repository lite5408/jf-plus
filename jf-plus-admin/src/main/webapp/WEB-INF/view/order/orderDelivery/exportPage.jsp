<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>订单收货导出</title>
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
                            <div class="widget-title am-fl">订单收货导出</div>
                        </div>
                        <div class="widget-body am-fr">
<!--                             <div class="am-u-sm-12 am-u-md-3 am-u-lg-3"> -->
<!--                                 <table class="am-u-sm-12"> -->
<!--                                     <tr> -->
<!--                                         <td class="am-padding-sm"> -->
<!--                                         </td> -->
<!--                                     </tr> -->
<!--                                 </table> -->
<!--                             </div> -->

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/order/orderDelivery/exportPage" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                            <tr>
                                                <td class="am-padding-sm am-text-sm am-text-right">包裹号</td>
                                                <td>
                                                    <input type="text" class="am-input-sm"  name="deliveryExpressNo"  value="${page.entity.deliveryExpressNo}">
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
                                            <th>包裹号</th>
                                            <th>订单号</th>
                                            <th>发货时间</th>
                                            <th>状态</th>
                                            <th>是否导出</th>
                                        	<th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="orderDelivery" varStatus="status">
                                        <tr>
                                                <td><a href="javascript:" onclick="openModelAdjust('发货详情','${ctx }/order/orderDelivery/deliveryDetailProduct?deliveryId=${orderDelivery.id }',false,false)">${orderDelivery.deliveryExpressNo}</a></td>
                                                <td>${orderDelivery.orderNo}</td>
                                                <td><fmt:formatDate value="${orderDelivery.deliveryDate}" pattern="yyyy-MM-dd"/></td>
                                                <td>${empty orderDelivery.isConfirm  ? '<span class=am-text-warning>待收货</span>':'<span class=am-text-primary>已收货</span>'}</td>
                                                <td>${orderDelivery.operStatus eq '0' ? '未导出':'已导出'}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                	<c:if test="${empty orderDelivery.isConfirm}">
                                                    <a role="oper" href="${ctx}/order/orderDelivery/confirm?id=${orderDelivery.id}&pageNo=${page.pageNo}&pageSize=${page.pageSize}" class="am-btn am-btn-default am-btn-xs am-text-secondary"
                                                       onclick="return confirm('确认要收货吗？', this.href)" title="确认收货"><span
                                                            class=" am-icon-check"> 确认收货</span></a>
                                                     </c:if>
                                                    <c:if test="${orderDelivery.isConfirm eq 1 && orderDelivery.operStatus eq 0}">
                                                    <a role="oper" href="javascript:;" onclick="openModelAdjust('发货详情','${ctx }/order/orderDelivery/exportProduct?deliveryId=${orderDelivery.id }',false,false)" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 导出订单</a>
                                                    </c:if>
                                                    <c:if test="${orderDelivery.operStatus eq 1}">
                                                    <a role="oper" href="${ctx}/export/exportProduct?deliveryId=${orderDelivery.id }" onclick="" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 打印订单</a>
                                                	</c:if>
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
    
    function confirmDelivyer(id){
    	
    }
</script>
</body>
</html>
