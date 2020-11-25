<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>礼包赠送记录</title>
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
                            <div class="widget-title am-fl">礼包赠送记录</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="packsPresent" action="${ctx}/mallSetting/packsPresent/<c:choose><c:when test="${empty packsPresent.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${packsPresent.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">赠送人：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="fromId" placeholder="赠送人" value="${packsPresent.fromId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">被赠送人：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="toId" placeholder="被赠送人" value="${packsPresent.toId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">礼包ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="packsId" placeholder="礼包ID" value="${packsPresent.packsId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">分发记录ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="accId" placeholder="分发记录ID" value="${packsPresent.accId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">赠送日期：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="presentDate" placeholder="赠送日期" value="${packsPresent.presentDate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">赠送语：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="presentText" placeholder="赠送语" value="${packsPresent.presentText}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">赠送封面图：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="presentPhoto" placeholder="赠送封面图" value="${packsPresent.presentPhoto}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="" value="${packsPresent.remarks}"/>
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
