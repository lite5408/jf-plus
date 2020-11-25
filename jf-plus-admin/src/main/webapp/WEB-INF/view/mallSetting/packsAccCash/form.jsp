<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>礼包卡券表</title>
    <%@ include file="/WEB-INF/view/include/head.jsp"%>
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
                            <div class="widget-title am-fl">礼包卡券表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="packsAccCash" action="${ctx}/mallSetting/packsAccCash/<c:choose><c:when test="${empty packsAccCash.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${packsAccCash.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">礼包ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="packsId" placeholder="礼包ID" value="${packsAccCash.packsId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">分发机构ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="distOrgId" placeholder="分发机构ID" value="${packsAccCash.distOrgId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">卡券密码：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="account" placeholder="卡券密码" value="${packsAccCash.account}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">绑定用户：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="bindUser" placeholder="绑定用户" value="${packsAccCash.bindUser}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">绑定订单号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="bindOrder" placeholder="绑定订单号" value="${packsAccCash.bindOrder}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">批次号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="batchNo" placeholder="批次号" value="${packsAccCash.batchNo}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">是否兑换：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="isCash" placeholder="是否兑换" value="${packsAccCash.isCash}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">兑换日期：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="cashDate" placeholder="兑换日期" value="${packsAccCash.cashDate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">绑定日期：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="bindDate" placeholder="绑定日期" value="${packsAccCash.bindDate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">分发人ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="distUserId" placeholder="分发人ID" value="${packsAccCash.distUserId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="" value="${packsAccCash.remarks}"/>
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
<%@ include file="/WEB-INF/view/include/bottom.jsp"%>
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
