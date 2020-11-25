<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>代发货订单</title>
    <%@ include file="../../include/head.jsp"%>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<script src="${ctxStatic}/3rd-lib/amazeui/js/amazeui.min.js"></script>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">已发货订单</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-2 am-u-lg-2">
                                <table class="am-u-sm-12">
	                                <tr>
										<td class="am-padding-sm">
											<a href="javascript:void(0)" onclick="exportFun()" 
												class="am-btn am-btn-xs am-btn-success am-icon-file-excel-o"> 导出</a>
										</td>
	                                </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-10 am-u-lg-10">
                                <form id="searchForm" action="${ctx}/order/orderDelivery/hasDeliveryList" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">包裹号</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="ckNo"  value="${page.entity.ckNo }">
												</td>
                                    			<td class="am-padding-sm am-text-sm am-text-right">代理商</td>
                                    			<td>
											  		<input type="text" class="am-input-sm"  name="orgName"  value="${page.entity.orgName }">
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
                                            <th>代理商</th>
                                            <th>发货数量</th>
                                            <th>发货时间</th>
                                        	<th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="row" varStatus="status">
                                        <tr>
                                                <td>${row.ckNo}</td>
                                                <td>${row.orderNo }</td>
                                                <td>${row.orgName }</td>
                                                <td class="am-text-danger">${row.deliveryNum }</td>
                                                <td><fmt:formatDate value="${row.deliveryDate }" pattern="yyyy-MM-dd"/></td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                    	<a role="oper" href="javascript:;" onclick="openModelAdjust('发货详情','${ctx }/order/orderDelivery/deliveryDetailProduct?deliveryId=${row.id }',false,false)" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 发货详情</a> 
                                                    	<a role="oper" href="${ctx}/order/orderDelivery/${row.id }/delete?&pageNo=${page.pageNo}&pageSize=${page.pageSize}" onclick="return confirm('确认要删除该条数据吗？', this.href)" title="删除" class="am-btn am-btn-default am-btn-xs am-text-danger"><span class="am-icon-trash-o"></span> 删除</a>
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
    
    function exportFun(){
    	
    }
</script>
</body>
</html>
