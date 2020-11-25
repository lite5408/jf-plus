<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>用户额度管理</title>
    <%@ include file="../../include/head.jsp" %>
    <link rel="stylesheet" href="${ctxStatic}/3rd-lib/jquery-ztree/css/zTreeStyle.css">
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style>
        .tpl-content-wrapper{margin-left:0}
        .theme-black .widget .ztree li a{color: #ffffff}
    </style>
</head>
<body>
<script src="${ctxStatic}/assets/js/theme.js"></script>
<div class="am-g tpl-g">
    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">组织机构</div>
                        </div>
                        <div class="widget-body widget-body-lg am-fr">
                            <div class="am-scrollable-horizontal">
                                <ul id="tree" class="ztree"></ul><!-- 组织机构列表 -->
                            </div>
                        </div>
                    </div>
                </div>
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">用户列表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <div class="am-btn-toolbar">
                                    <div class="am-btn-group am-btn-group-xs" style="display:none;">
                                        <shiro:hasPermission name="sys:user:create">
                                            <button type="button" class="am-btn am-btn-default am-btn-success"
                                                    onclick="openModel(false,'${ctx}/user/create?organizationId=${page.entity.organizationId}')"><span class="am-icon-plus"></span> 新增
                                            </button></shiro:hasPermission>
                                    </div>
                                </div>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/account/userAccount/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <div class="am-u-sm-12 am-u-md-3 am-u-lg-3 tagsinput nobg">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">姓名</span>
											  <input type="text" class="am-form-field"  name="name"  value="${page.entity.name }">
											</div>
										</div>
										<div class="am-u-sm-12 am-u-md-3 am-u-lg-3 tagsinput nobg">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">账号</span>
											  <input type="text" class="am-form-field"  name="no"  value="${page.entity.no }">
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
                                        <th>序号</th>
                                        <th>编码</th>
                                        <th>账号</th>
                                        <th>姓名</th>
                                        <th>额度</th>
                                        <th>归属机构</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="user" varStatus="status">
                                        <tr>
                                            <td>${status.index+1}</td>
                                            <td>${user.no}</td>
                                            <td>${user.username}</td>
                                            <td>${user.name}</td>
                                            <td>${user.blance}</td>
                                            <td>${user.organizationNames}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
							                      	<a role="oper" href="javascript:" class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="openModel('充值','${ctx}/account/userAccount/recharge?id=${user.id }')"><span class="am-icon-pencil-square-o"></span> 充值</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="am-u-lg-12 am-cf">
                                <%@ include file="../../utils/pagination.jsp" %>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../../include/bottom.jsp"%>
<script src="${ctxStatic}/3rd-lib/jquery-ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/custom/js/amazeui.select.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if (msg != '') {
            showMsg(msg);
        }
    });
</script>
<script>
    $(function () {
        var setting = {
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback : {
                onClick : function(event, treeId, treeNode) {
                    location.href = "${ctx}/user/list?organizationId="+treeNode.id;
                }
            }
        };
        var zNodes =[
            <c:forEach items="${organizationList}" var="o" varStatus="status">
            { id:${o.id}, pId:${o.parentId}, name:"${o.name}", open:${o.rootNode}}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ];
        $(document).ready(function(){
            var ztree = $.fn.zTree.init($("#tree"), setting, zNodes);
            //ztree.expandAll(true);
            //展开2级
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var nodes = treeObj.getNodes();
            for (var i = 0; i < nodes.length; i++) { //设置节点展开
                treeObj.expandNode(nodes[i], true, false, true);
            }
        });
    });
</script>
</body>
</html>