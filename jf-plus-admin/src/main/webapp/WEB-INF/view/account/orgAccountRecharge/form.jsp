<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>组织资金账户交易流水表</title>
    <%@ include file="../../include/head.jsp"%>
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
                            <div class="widget-title am-fl">组织资金账户交易流水表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="orgAccountRecharge" action="${ctx}/account/orgAccountRecharge/<c:choose><c:when test="${empty orgAccountRecharge.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${orgAccountRecharge.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">组织id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orgId" placeholder="组织id" value="${orgAccountRecharge.orgId}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">账户ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="accountId" placeholder="账户ID" value="${orgAccountRecharge.accountId}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">交易前金额：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="beforeAmount" placeholder="交易前金额" value="${orgAccountRecharge.beforeAmount}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">交易金额：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="amount" placeholder="交易金额" value="${orgAccountRecharge.amount}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">交易后金额：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="afterAmount" placeholder="交易后金额" value="${orgAccountRecharge.afterAmount}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">账户类型（采购账户purchase 积分账户points)：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="accountType" placeholder="账户类型（采购账户purchase 积分账户points)" value="${orgAccountRecharge.accountType}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">交易类型（充值：1，消费：2）：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="rechargeType" placeholder="交易类型（充值：1，消费：2）" value="${orgAccountRecharge.rechargeType}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">交易时间：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="operTime" placeholder="交易时间" value="${orgAccountRecharge.operTime}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">备注：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="备注" value="${orgAccountRecharge.remarks}" required/>
                                        </div>
                                    </div>
                            <div class="am-form-group">
                                <div class="am-u-sm-9 am-u-sm-push-3">
                                    <button type="submit" class="am-btn am-btn-xs am-btn-primary">保存</button>
                                    <button type="button" class="am-btn am-btn-xs am-btn-danger" onclick="closeModel(false)">关闭</button>
                                </div>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
    });
</script>
</body>
</html>
