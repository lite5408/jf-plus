<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>消息模板表</title>
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
                            <div class="widget-title am-fl">消息模板表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="msgTemplate" action="${ctx}/setting/msgTemplate/<c:choose><c:when test="${empty msgTemplate.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${msgTemplate.id}"/>
                                    <div class="am-form-group" style="display:none">
                                        <label class="am-u-sm-3 am-form-label">公司机构ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orgId" placeholder="公司机构ID" value="${loginUser.organization.id}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">消息类型：</label>
                                        <div class="am-u-sm-9">
                                            <select name="msgType" data-sm-selected data="${msgTemplate.msgType }">
                                            	<c:forEach items="${msgType }" var="item">
                                            		<option value="${item.type }">${item.description }</option>
                                            	</c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">模板内容：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="templateContent" placeholder="模板内容" value="${msgTemplate.templateContent}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">消息链接URL：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="link" placeholder="消息链接URL" value="${msgTemplate.link}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">启用：</label>
                                        <div class="am-u-sm-9">
                                            <select name="avaliable" data="${msgTemplate.avaliable }">
                                            		<option value="1">启用</option>
                                            		<option value="0">禁用</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="am-form-group" style="display:none">
                                        <label class="am-u-sm-3 am-form-label">提醒方式：weixin : 微信  mobile：短信提醒：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="noticeWay" placeholder="提醒方式：weixin : 微信  mobile：短信提醒" value="weixin"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group" style="display:none">
                                        <label class="am-u-sm-3 am-form-label">微信消息模板：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="weixinTemplate" placeholder="微信消息模板" value="${msgTemplate.weixinTemplate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group" style="display:none">
                                        <label class="am-u-sm-3 am-form-label">排序：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="sort" placeholder="排序" value="${msgTemplate.sort}"/>
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
        
        initSelectValue(true)
    });
</script>
</body>
</html>
