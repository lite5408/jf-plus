<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>商城专题</title>
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
                            <div class="widget-title am-fl">商城专题</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <div class="am-btn-toolbar">
                                    <div class="am-btn-group am-btn-group-xs">
                                        <shiro:hasPermission name="site:siteAdvert:create">
                                        <button type="button" class="am-btn am-btn-default am-btn-success"
                                                onclick="openModel(false,'${ctx}/site/siteAdvert/create?type=${page.condition.type}')"><span class="am-icon-plus"></span> 新增
                                        </button>
                                        </shiro:hasPermission>
                                    </div>
                                </div>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/site/siteAdvert/list?type=${page.condition.type}" method="post">
                                    
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <div class="am-u-sm-12 am-u-md-3 am-u-lg-3 tagsinput nobg">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">标题</span>
											  <input type="text" class="am-form-field"  name="name"  value="${page.condition.name }">
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
                                            <th>编号</th>
                                            <th>封面图</th>
                                            <th>标题</th>
                                            <th>专题类型</th>
                                            <th>商品数量</th>
                                            <th>排序</th>
                                            <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="siteAdvert" varStatus="status">
                                        <tr>
                                                <td>${siteAdvert.id}</td>
                                                <td>
                                                	<c:if test="${not empty siteAdvert.photo }">
                                                	<img class="am-img-thumbnail" width="140"  src="${siteAdvert.photoUrl}" />
                                                	</c:if>
                                                </td>
                                                <td>${siteAdvert.name}</td>
                                                <td>${fnc:getEnumText('com.jf.plus.common.core.enums.SpecialShowType',siteAdvert.showType)}</td>
                                                <td>${siteAdvert.itemsCount}</td>
                                                <td>${siteAdvert.sort}</td>
                                                <td>${fnc:getEnumText('com.jf.plus.common.core.enums.Status',siteAdvert.status)}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                <shiro:hasPermission name="site:siteAdvert:update">
                                                    <a role="oper" href="javascript:;" onclick="openModel('修改','${ctx}/site/siteAdvert/update?id=${siteAdvert.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 编辑</a>
                                                </shiro:hasPermission>
                                                <a class="am-btn am-btn-default am-btn-xs am-text-secondary" role="oper" onclick="openModelAdjust('选择商品','${ctx}/site/siteProduct/select?operStatus=3&source=2&advertId=${siteAdvert.id }&items=${siteAdvert.items }')"><span class="am-icon-plus"></span> 挑选商品</a>
										  		<a class="am-btn am-btn-default am-btn-xs am-text-secondary" role="oper" onclick="openModelAdjust('查看商品','${ctx}/site/siteAdvert/myProduct?advertId=${siteAdvert.id }')")><span class="am-icon-shopping-cart"></span> 已选商品</a>
                                                <shiro:hasPermission name="site:siteAdvert:delete">
                                                    <a role="oper" href="${ctx}/site/siteAdvert/${siteAdvert.id}/delete?pageNo=${page.pageNo}&pageSize=${page.pageSize}&type=${page.condition.type}" onclick="return confirm('确认要删除该条数据吗？', this.href)" class="am-btn am-btn-default am-btn-xs am-text-danger"><span class="am-text-danger am-icon-trash-o">删除</span></a>
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
