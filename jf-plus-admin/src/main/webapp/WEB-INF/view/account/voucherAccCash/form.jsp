<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>电子券卡号信息表</title>
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
                            <div class="widget-title am-fl">电子券卡号信息表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="voucherAccCash" action="${ctx}/account/voucherAccCash/<c:choose><c:when test="${empty voucherAccCash.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${voucherAccCash.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">券id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="voucherId" placeholder="券id" value="${voucherAccCash.voucherId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">组织id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orgId" placeholder="组织id" value="${voucherAccCash.orgId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">券名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="name" placeholder="券名称" value="${voucherAccCash.name}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">卡号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="account" placeholder="卡号" value="${voucherAccCash.account}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">密码：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="password" placeholder="密码" value="${voucherAccCash.password}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">绑定用户：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="bindUser" placeholder="绑定用户" value="${voucherAccCash.bindUser}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">绑定日期：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="bindDate" placeholder="绑定日期" value="${voucherAccCash.bindDate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">余额：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="blance" placeholder="余额" value="${voucherAccCash.blance}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">面值：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="distAmount" placeholder="面值" value="${voucherAccCash.distAmount}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">是否兑换：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="isCash" placeholder="是否兑换" value="${voucherAccCash.isCash}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">备注：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="备注" value="${voucherAccCash.remarks}"/>
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
