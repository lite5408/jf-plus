<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>机构品牌</title>
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
                            <div class="widget-title am-fl">品牌管理</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <table class="am-u-sm-12">
                                    <tr>
                                        <td class="am-padding-sm">
                                            <shiro:hasPermission name="product:mallProductBrand:create">
                                                <button type="button" class="am-btn am-btn-xs am-btn-default am-btn-success"
                                                        onclick="openModel(false,'${ctx}/product/mallProductBrand/create')"><span class="am-icon-plus"></span> 新增
                                                </button>
                                            </shiro:hasPermission>
                                        </td>
                                    </tr>
                                </table>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/product/mallProductBrand/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <table class="am-u-sm-12">
                                            <tr>
                                                <td class="am-padding-sm am-text-sm am-text-right">品牌编码</td>
                                                <td>
                                                    <input type="text" class="am-input-sm"  name="brandCode"  value="${page.entity.brandCode}">
                                                </td>
                                                <td class="am-padding-sm am-text-sm am-text-right">品牌名称</td>
                                                <td>
                                                    <input type="text" class="am-input-sm"  name="brandName"  value="${page.entity.brandName}">
                                                </td>
                                                <td class="am-padding-sm am-text-sm am-text-right">品牌类型</td>
                                                <td>
                                                    <select name="type">
                                                    	<option value="">全部</option>
                                                    	<option value="PUBLIC" <c:if test="${page.entity.type eq 'PUBLIC' }">selected</c:if>>平台共有</option>
                                                    	<option value="PRIVATE" <c:if test="${page.entity.type eq 'PRIVATE' }">selected</c:if>>供应商私有</option>
                                                    </select>
                                                </td>
                                                 <td class="am-padding-sm am-text-sm am-text-right">归属公司</td>
                                                <td>
                                                    <input type="text" class="am-input-sm"  name="orgName"  value="${page.entity.orgName}">
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
                                            <th>品牌编码</th>
                                            <th>品牌名称</th>
                                            <th>类型</th>
                                            <th>排序</th>
                                            <th>归属公司</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="mallProductBrand" varStatus="status">
                                        <tr>
                                                <td>${mallProductBrand.brandCode}</td>
                                                <td>${mallProductBrand.brandName}</td>
                                                <td>${mallProductBrand.type eq 'PUBLIC' ? '公有':'私有'}</td>
                                                <td>${mallProductBrand.sort}</td>
                                                <td>${mallProductBrand.orgName}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                <shiro:hasPermission name="product:mallProductBrand:update">
                                                    <a role="oper" href="javascript:;" onclick="openModel('修改品牌','${ctx}/product/mallProductBrand/update?id=${mallProductBrand.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 编辑</a>
                                                </shiro:hasPermission>
                                                <shiro:hasPermission name="product:mallProductBrand:delete">
													<a role="oper" href="${ctx}/product/mallProductBrand/${mallProductBrand.id}/delete?pageNo=${page.pageNo}&pageSize=${page.pageSize}" onclick="return confirm('确认要删除该条数据吗？', this.href)" title="删除" class="am-btn am-btn-default am-btn-xs am-text-danger"><span class="am-text-danger am-icon-trash-o"></span> 删除</a>
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
