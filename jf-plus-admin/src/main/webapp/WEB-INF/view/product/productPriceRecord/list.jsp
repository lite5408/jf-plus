<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title></title>
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
                            <div class="widget-title am-fl"></div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <table class="am-u-sm-12">
                                    <tr>
                                        <td class="am-padding-sm">
                                            <shiro:hasPermission name="product:productPriceRecord:create">
                                                <button type="button" class="am-btn am-btn-xs am-btn-default am-btn-success"
                                                        onclick="openModel(false,'${ctx}/product/productPriceRecord/create')"><span class="am-icon-plus"></span> 新增
                                                </button>
                                            </shiro:hasPermission>
                                        </td>
                                    </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/product/productPriceRecord/list" method="post">
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
                                            <th>商品Id</th>
                                            <th>商城规则配置记录Id(价格变更记录0)</th>
                                            <th>记录变更来源（1.商城配置修改2.接口供应价修改）</th>
                                            <th>修改前供应价</th>
                                            <th>修改后供应价</th>
                                            <th>执行状态(1未执行，2已执行)</th>
                                            <th>执行时间</th>
                                            <th>是否可用</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="productPriceRecord" varStatus="status">
                                        <tr>
                                                <td>${productPriceRecord.id}</td>
                                                <td>${productPriceRecord.productId}</td>
                                                <td>${productPriceRecord.ruleRecordId}</td>
                                                <td>${productPriceRecord.recordSource}</td>
                                                <td>${productPriceRecord.preSupplyPrice}</td>
                                                <td>${productPriceRecord.sufSupplyPrice}</td>
                                                <td>${productPriceRecord.operStatus}</td>
                                                <td>${productPriceRecord.operTime}</td>
                                                <td>${productPriceRecord.status}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                <shiro:hasPermission name="product:productPriceRecord:update">
                                                    <a role="oper" href="javascript:;" onclick="openModel('修改','${ctx}/product/productPriceRecord/update?id=${productPriceRecord.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 编辑</a>
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
