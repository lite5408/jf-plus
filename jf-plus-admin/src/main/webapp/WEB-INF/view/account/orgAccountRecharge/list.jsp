<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>机构资金流水</title>
    <%@ include file="../../include/head.jsp"%>
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
                            <div class="widget-title am-fl">机构资金流水</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                                <form id="searchForm" action="${ctx}/account/orgAccountRecharge/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <input name="accountId" type="hidden" value="${page.entity.accountId}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                    		<tr>
                                    			<td class="am-padding-sm am-text-sm am-text-right">交易类型</td>
                                    			<td>
											  		<select class="am-input-sm" name="dealType" data="${page.entity.dealType}" style="min-width:100px;">
														<option value=" ">全部</option>
														<option value="1">收入</option>
														<option value="2">支出</option>
													</select>
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
	                                            <th>交易类型</th>
	                                            <th>金额</th>
	                                            <th>摘要</th>
	                                            <th>交易时间</th>
	                                            <th>附件</th>
	                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="orgAccountRecharge" varStatus="status">
                                        <tr>
                                                <td>${orgAccountRecharge.id}</td>
                                                <td>${fnc:getEnumText('com.jf.plus.common.core.enums.DealType',orgAccountRecharge.dealType) }</td>
                                                <td>${orgAccountRecharge.amount}</td>
                                                <td>${orgAccountRecharge.abstractType}</td>
                                                <td><fmt:formatDate value="${orgAccountRecharge.operTime }" pattern="yyyy-MM-dd HH:mm"/></td>
                                                <td><a href="${orgAccountRecharge.attachmentsUrl}">${orgAccountRecharge.attachments}</a></td>
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
        
        initSelectValue(true);
    });
</script>
</body>
</html>
