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
                                            <shiro:hasPermission name="product:mallSiteRuleRecord:create">
                                                <button type="button" class="am-btn am-btn-xs am-btn-default am-btn-success"
                                                        onclick="openModel(false,'${ctx}/product/mallSiteRuleRecord/create')"><span class="am-icon-plus"></span> 新增
                                                </button>
                                            </shiro:hasPermission>
                                        </td>
                                    </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/product/mallSiteRuleRecord/list" method="post">
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
                                            <th>机构Id</th>
                                            <th>商品渠道(1:京东  2供应商  3苏宁 4严选 5齐心)</th>
                                            <th>本次记录的类型(1新增记录，2修改前记录，3修改后记录)</th>
                                            <th>批次号</th>
                                            <th>分销类型(1:按折扣 2:按固定值)</th>
                                            <th>分销值</th>
                                            <th>是否允许超出市场价</th>
                                            <th>超出市场价比例</th>
                                            <th>执行状态(0不执行，1未执行，2已执行)</th>
                                            <th>执行时间</th>
                                            <th>是否可用</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="mallSiteRuleRecord" varStatus="status">
                                        <tr>
                                                <td>${mallSiteRuleRecord.id}</td>
                                                <td>${mallSiteRuleRecord.orgId}</td>
                                                <td>${mallSiteRuleRecord.productSource}</td>
                                                <td>${mallSiteRuleRecord.recordType}</td>
                                                <td>${mallSiteRuleRecord.batchNo}</td>
                                                <td>${mallSiteRuleRecord.distributeType}</td>
                                                <td>${mallSiteRuleRecord.distributeValue}</td>
                                                <td>${mallSiteRuleRecord.allowExceedMarketPrice}</td>
                                                <td>${mallSiteRuleRecord.exceedRatio}</td>
                                                <td>${mallSiteRuleRecord.operStatus}</td>
                                                <td>${mallSiteRuleRecord.operTime}</td>
                                                <td>${mallSiteRuleRecord.status}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                <shiro:hasPermission name="product:mallSiteRuleRecord:update">
                                                    <a role="oper" href="javascript:;" onclick="openModel('修改','${ctx}/product/mallSiteRuleRecord/update?id=${mallSiteRuleRecord.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 编辑</a>
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
