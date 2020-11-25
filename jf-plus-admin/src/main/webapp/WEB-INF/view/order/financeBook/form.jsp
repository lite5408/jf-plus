<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>财务对账簿表</title>
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
                            <div class="widget-title am-fl">财务对账簿表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="financeBook" action="${ctx}/order/financeBook/<c:choose><c:when test="${empty financeBook.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${financeBook.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">对账标题：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="title" placeholder="对账标题" value="${financeBook.title}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">批次号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="batchNo" placeholder="批次号" value="${financeBook.batchNo}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">对账时间：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="operTime" placeholder="对账时间" value="${financeBook.operTime}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">供应商：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="supplyId" placeholder="供应商" value="${financeBook.supplyId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">供应商名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="supplyName" placeholder="供应商名称" value="${financeBook.supplyName}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">对账状态（1：对账中 2：已对账  3：已开票  4：取消  41：商家拒绝确认）：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="operStatus" placeholder="对账状态（1：对账中 2：已对账  3：已开票  4：取消  41：商家拒绝确认）" value="${financeBook.operStatus}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">确认时间：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="confirmDate" placeholder="确认时间" value="${financeBook.confirmDate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">备注：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="备注" value="${financeBook.remarks}"/>
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
