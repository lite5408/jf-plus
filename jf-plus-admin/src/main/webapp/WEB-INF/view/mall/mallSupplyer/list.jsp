<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>供应商表</title>
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
                            <div class="widget-title am-fl">供应商</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <div class="am-btn-toolbar">
                                    <div class="am-btn-group am-btn-group-xs">
                                        <shiro:hasPermission name="mall:mallSupplyer:create">
                                        <button type="button" class="am-btn am-btn-default am-btn-success"
                                                onclick="openModel(false,'${ctx}/mall/mallSupplyer/create')"><span class="am-icon-plus"></span> 新增
                                        </button>
                                        </shiro:hasPermission>
                                    </div>
                                </div>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/mall/mallSupplyer/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <div class="am-u-sm-12 am-u-md-3 am-u-lg-3 tagsinput nobg">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">联系人</span>
											  <input type="text" class="am-form-field"  name="contact"  value="${page.entity.contact }">
											</div>
                                        </div>
                                        <div class="am-u-sm-12 am-u-md-3 am-u-lg-3 tagsinput nobg">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">供应商名称</span>
											  <input type="text" class="am-form-field"  name="companyName"  value="${page.entity.companyName }">
											</div>
                                        </div>
                                        <span class="am-input-group-btn">
                                            <button class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-search" type="submit" onclick="initSearchForm()"></button>
                                        </span>
                                    </div>
                                </form>
                            </div>

                            <div class="am-u-sm-12">
                                <table id="contentTable" class="am-table am-table-compact am-table-striped tpl-table-black">
                                    <thead>
                                    <tr>
                                            <th>ID</th>
                                            <th>所属机构</th>
                                            <th>公司全称</th>
                                            <th>简称</th>
                                            <th>开户时间</th>
                                            <th>联系人</th>
                                            <th>联系方式</th>
                                            <th>地区</th>
                                            <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="mallSupplyer" varStatus="status">
                                        <tr>
                                                <td>${mallSupplyer.id}</td>
                                                <td>${mallSupplyer.orgName}</td>
                                                <td>${mallSupplyer.companyName}</td>
                                                <td>${mallSupplyer.companyTitle}</td>
                                                <td><fmt:formatDate value="${mallSupplyer.regTime}" pattern="yyyy-MM-dd"/></td>
                                                <td>${mallSupplyer.contact}</td>
                                                <td>${mallSupplyer.contactLink}</td>
                                                <td>${mallSupplyer.areaName}</td>
                                                <td class="am-text-warning">${fnc:getEnumText('com.jf.plus.common.core.enums.Status',mallSupplyer.status)}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                <shiro:hasPermission name="mall:mallSupplyer:update">
                                                    <a role="oper" href="javascript:;" onclick="openModel('修改','${ctx}/mall/mallSupplyer/update?id=${mallSupplyer.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 编辑</a>
                                                </shiro:hasPermission>
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
