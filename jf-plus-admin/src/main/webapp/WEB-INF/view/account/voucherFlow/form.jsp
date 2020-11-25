<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>电子券卡号交易记录表</title>
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
                            <div class="widget-title am-fl">电子券卡号交易记录表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="voucherFlow" action="${ctx}/account/voucherFlow/<c:choose><c:when test="${empty voucherFlow.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${voucherFlow.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">券id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="voucherId" placeholder="券id" value="${voucherFlow.voucherId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">组织id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orgId" placeholder="组织id" value="${voucherFlow.orgId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">券名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="name" placeholder="券名称" value="${voucherFlow.name}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">卡号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="account" placeholder="卡号" value="${voucherFlow.account}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">交易类型（1.充值 2.消费）：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="dealType" placeholder="交易类型（1.充值 2.消费）" value="${voucherFlow.dealType}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">交易金额：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="dealAmount" placeholder="交易金额" value="${voucherFlow.dealAmount}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">交易日期：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="dealDate" placeholder="交易日期" value="${voucherFlow.dealDate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">用户id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="userId" placeholder="用户id" value="${voucherFlow.userId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">关联表id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="targetId" placeholder="关联表id" value="${voucherFlow.targetId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">备注：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="备注" value="${voucherFlow.remarks}"/>
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
