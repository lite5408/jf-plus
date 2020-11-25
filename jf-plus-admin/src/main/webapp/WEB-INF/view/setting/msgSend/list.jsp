<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>消息发送记录表</title>
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
                            <div class="widget-title am-fl">消息发送记录</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <table class="am-u-sm-12">
                                    <tr>
                                        <td class="am-padding-sm">
                                                <button type="button" class="am-btn am-btn-xs am-btn-default am-btn-success"
                                                        onclick="openModel(false,'${ctx}/setting/msgSend/create')"><span class="am-icon-plus"></span> 新增
                                                </button>
                                        </td>
                                    </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/setting/msgSendRecord/list" method="post">
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
                                            <th></th>
                                            <th>消息ID</th>
                                            <th>消息类型 (10:商品上架 20:订单销量提醒 21:订单发货 22:取消订单）</th>
                                            <th>收件人ID</th>
                                            <th>消息关联对象ID</th>
                                            <th>消息内容</th>
                                            <th>消息JSON值</th>
                                            <th>消息链接URL</th>
                                            <th>提醒方式：weixin : 微信  mobile：短信提醒</th>
                                            <th>微信消息模板</th>
                                            <th>发送状态：0 未发送 1：已发送 2：发送失败</th>
                                            <th>发送时间</th>
                                            <th>是否已读 0：未读 1：已读</th>
                                            <th>读取时间</th>
                                            <th>排序</th>
                                            <th></th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="msgSendRecord" varStatus="status">
                                        <tr>
                                                <td>${msgSendRecord.id}</td>
                                                <td>${msgSendRecord.msgId}</td>
                                                <td>${msgSendRecord.msgType}</td>
                                                <td>${msgSendRecord.toUserId}</td>
                                                <td>${msgSendRecord.targetId}</td>
                                                <td>${msgSendRecord.content}</td>
                                                <td>${msgSendRecord.msgJson}</td>
                                                <td>${msgSendRecord.link}</td>
                                                <td>${msgSendRecord.noticeWay}</td>
                                                <td>${msgSendRecord.weixinTemplate}</td>
                                                <td>${msgSendRecord.sendStatus}</td>
                                                <td>${msgSendRecord.sendDate}</td>
                                                <td>${msgSendRecord.isRead}</td>
                                                <td>${msgSendRecord.readDate}</td>
                                                <td>${msgSendRecord.sort}</td>
                                                <td>${msgSendRecord.status}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                    <a role="oper" href="javascript:;" onclick="openModel('修改消息发送记录表','${ctx}/setting/msgSend/update?id=${msgSendRecord.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 编辑</a>
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
