<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>消息发送记录表</title>
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
                            <div class="widget-title am-fl">消息发送记录表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="msgSendRecord" action="${ctx}/setting/msgSendRecord/<c:choose><c:when test="${empty msgSendRecord.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${msgSendRecord.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">消息ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="msgId" placeholder="消息ID" value="${msgSendRecord.msgId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">消息类型 (10:商品上架 20:订单销量提醒 21:订单发货 22:取消订单）：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="msgType" placeholder="消息类型 (10:商品上架 20:订单销量提醒 21:订单发货 22:取消订单）" value="${msgSendRecord.msgType}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">收件人ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="toUserId" placeholder="收件人ID" value="${msgSendRecord.toUserId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">消息关联对象ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="targetId" placeholder="消息关联对象ID" value="${msgSendRecord.targetId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">消息内容：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="content" placeholder="消息内容" value="${msgSendRecord.content}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">消息JSON值：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="msgJson" placeholder="消息JSON值" value="${msgSendRecord.msgJson}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">消息链接URL：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="link" placeholder="消息链接URL" value="${msgSendRecord.link}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">提醒方式：weixin : 微信  mobile：短信提醒：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="noticeWay" placeholder="提醒方式：weixin : 微信  mobile：短信提醒" value="${msgSendRecord.noticeWay}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">微信消息模板：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="weixinTemplate" placeholder="微信消息模板" value="${msgSendRecord.weixinTemplate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">发送状态：0 未发送 1：已发送 2：发送失败：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="sendStatus" placeholder="发送状态：0 未发送 1：已发送 2：发送失败" value="${msgSendRecord.sendStatus}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">发送时间：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="sendDate" placeholder="发送时间" value="${msgSendRecord.sendDate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">是否已读 0：未读 1：已读：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="isRead" placeholder="是否已读 0：未读 1：已读" value="${msgSendRecord.isRead}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">读取时间：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="readDate" placeholder="读取时间" value="${msgSendRecord.readDate}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">排序：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="sort" placeholder="排序" value="${msgSendRecord.sort}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="" value="${msgSendRecord.remarks}"/>
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
