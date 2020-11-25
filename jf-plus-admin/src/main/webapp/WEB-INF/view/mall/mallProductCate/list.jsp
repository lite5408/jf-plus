<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
   <title>商品分类</title>
   <%@ include file="../../include/head.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/3rd-lib/jquery-ztree/css/zTreeStyle.css">
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8" />
    <style type="text/css">
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
                            <div class="widget-title am-fl">商品分类树</div>
                        </div>
                        <div class="widget-body widget-body-lg am-fr">
                            <div class="am-scrollable-horizontal">
                                <ul id="tree" class="ztree"></ul><!-- 商品分类树 -->
                            </div>
                        </div>
                    </div>
                </div>

                <div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">分类列表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <div class="am-btn-toolbar">
                                    <div class="am-btn-group am-btn-group-xs">
                                        <shiro:hasPermission name="mall:mallProductCate:create">
                                            <button type="button" class="am-btn am-btn-default am-btn-success"
                                                    onclick="openModel(false,'${ctx}/mall/mallProductCate/create?catPid=${page.entity.catPid}')"><span class="am-icon-plus"></span> 新增
                                            </button></shiro:hasPermission>
                                    </div>
                                </div>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/mall/mallProductCate/list?catPid=${page.entity.catPid}" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <div class="am-u-sm-12 am-u-md-3 am-u-lg-3 tagsinput nobg">
											<div class="am-input-group am-input-group-sm">
											  <span class="am-input-group-label nobg">分类名称</span>
											  <input type="text" class="am-form-field"  name="catName"  value="${page.entity.catName }">
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
                                        <th>编码</th>
                                        <th>分类名称</th>
                                        <th>上级分类</th>
                                        <th>启用</th>
                                        <th>排序</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="mallProductCate" varStatus="status">
                                        <tr>
                                            <td>${mallProductCate.catCode}</td>
                                            <td>${mallProductCate.catName}</td>
                                            <td>${mallProductCate.catPidName}</td>
                                            <td>${mallProductCate.isFront eq '0' ? '禁用':'启用'}</td>
                                            <td>${mallProductCate.sort}</td>
                                            <td>
                                                <a href="javascript:;" onclick="openModel(false,'${ctx}/mall/mallProductCate/update?id=${mallProductCate.id}')" title="编辑"><span class="am-icon-pencil"></span></a> 
<%--                                                 <a href="${ctx}/mall/mallProductCate/${mallProductCate.id}/delete?pageNo=${page.pageNo}&pageSize=${page.pageSize}" --%>
<!--                                                     onclick="return confirm('确认要删除该条数据吗？', this.href)" title="删除"><span class="am-text-danger am-icon-trash-o"></span></a></td> -->
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
                    location.href = "${ctx}/mall/mallProductCate?catPid="+treeNode.id;
                }
            }
        };
        var zNodes =[
            <c:forEach items="${mallProductCateList}" var="o" varStatus="status">
            { id:${o.id}, pId:${o.catPid}, name:"${o.catName}", open:${o.rootNode}}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ];
        $(document).ready(function(){
            var ztree = $.fn.zTree.init($("#tree"), setting, zNodes);
            //ztree.expandAll(true);
        });
    });
</script>
<script>
  $(document).ready(function () {
      var msg = '${msg}';
      if(msg!=''){
          showMsg(msg);
      }
  });
</script>
</body>
</html>