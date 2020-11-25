<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>财务对账簿表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
        .am-tabs-default .am-tabs-nav a{
        	height:40px!important;
        }
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
                            <div class="widget-title am-fl">订单结算</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                                <form id="searchForm" action="${ctx}/order/financeBook/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                            <tr>
                                                <td class="am-padding-sm am-text-sm am-text-right">时间</td>
                                                <td>
												  	<input type="datetime" style="width:100px;" class="am-u-sm-5" name="startTime" placeholder="开始时间" data-am-datepicker readonly required value="<fmt:formatDate pattern="yyyy-MM-dd" value="${page.entity.id }"/>"/>
												</td>
												<td class="am-padding-sm am-text-sm am-text-right">
												  	<input type="datetime"  style="width:100px;" class="am-u-sm-5" name="endTime" placeholder="结束时间" data-am-datepicker readonly required value="<fmt:formatDate pattern="yyyy-MM-dd" value="${page.entity.id }"/>"/>
												</td>
                                                <td class="am-padding-sm am-text-sm am-text-right">订单号</td>
                                                <td>
                                                    <input type="text" class="am-input-sm"  name="orderNo"  value="${page.entity.id}">
                                                </td>
                                                <td class="am-padding-sm am-text-sm am-text-right">供应商</td>
                                                <td>
                                                    <input type="text" class="am-input-sm"  name="supplyName"  value="${page.entity.supplyName}">
                                                </td>
                                                <td class="am-padding-sm"><button class="am-btn am-btn-xs am-btn-success am-icon-search"> 查询</button></td>
                                            </tr>
                                             <tr>
                                                <td class="am-padding-sm am-text-sm am-text-right">状态</td>
                                                <td>
                                                	<div class="am-btn-toolbar">
													    <div class="am-btn-group am-btn-group-xs">未结算</div>
													    <div class="am-btn-group am-btn-group-xs">已结算</div>
													</div>
                                                </td>
                                             </tr>
                                        </table>
                                    </div>
                                </form>
                            </div>
                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                    <thead>
                                    <tr>
                                            <th class="table-check"><input type="checkbox" id="checkAll"/></th>
                                            <th>订单号</th>
                                            <th>订单人</th>
                                            <th>机构</th>
                                            <th>订单金额</th>
                                            <th>订单日期</th>
                                            <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="financeOrder" varStatus="status">
                                        <tr>
                                                <td><input type="checkbox" name="rowId" value="${financeOrder.id }"/></td>
                                                <td>${financeOrder.id}</td>
                                                <td>${financeOrder.id}</td>
                                                <td>${financeOrder.id}</td>
                                                <td>${financeOrder.id}</td>
                                                <td>${financeOrder.id}</td>
                                                <td>${financeOrder.id}</td>
                                                <td>${financeOrder.id}</td>
                                                <td>${financeOrder.id}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                    <a role="oper" href="javascript:;" onclick="openModel('修改财务对账簿表','${ctx}/order/financeBook/update?id=${financeBook.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 编辑</a>
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
    
    //复选框所有
	$('#checkAll').click(function(){
		var checked = $(this).prop('checked') ? true:false;
		$('input[name="rowId"]').prop('checked',checked);
	});
</script>
</body>
</html>
