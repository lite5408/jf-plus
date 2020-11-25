<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>用户地址表</title>
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
                            <div class="widget-title am-fl">用户地址表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-u-sm-12 am-u-md-3 am-u-lg-3">
                                <div class="am-btn-toolbar">
                                    <div class="am-btn-group am-btn-group-xs">
                                        <shiro:hasPermission name="setting:userAddress:create">
                                        <button type="button" class="am-btn am-btn-default am-btn-success"
                                                onclick="openModel(false,'${ctx}/setting/userAddress/create')"><span class="am-icon-plus"></span> 新增
                                        </button>
                                        </shiro:hasPermission>
                                    </div>
                                </div>
                            </div>

                            <div class="am-u-sm-12 am-u-md-9 am-u-lg-9">
                                <form id="searchForm" action="${ctx}/setting/userAddress/list" method="post">
                                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <div class="tagsinput">
                                            <c:if test="${not empty page.key}"><span class="tags"><input type="hidden" name="key" value="${page.key}" />关键字=${page.key} <a href="javascript:;" onclick="$(this).parent().remove()">x</a></span></c:if>
                                                <span class="am-select am-input-group-sm">
                                                     <input type="text" class="am-select-input" autocomplete="off" style="border: none;"
                                                            placeholder="关键字" am-data='[{"field":"key","desc":"关键字","type":"string"}]'>
                                                    <ul class="am-select-ul"></ul>
                                                </span>
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
                                            <th>用户id</th>
                                            <th>收货人</th>
                                            <th>收货人手机号</th>
                                            <th>座机</th>
                                            <th>收货人邮箱</th>
                                            <th>省份</th>
                                            <th>省份名称</th>
                                            <th>城市id</th>
                                            <th>城市名称</th>
                                            <th>区镇ID</th>
                                            <th>区镇名称</th>
                                            <th>街道id</th>
                                            <th>街道地址</th>
                                            <th>详细地址</th>
                                            <th>是否默认</th>
                                            <th>渠道来源</th>
                                            <th></th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.list}" var="userAddress" varStatus="status">
                                        <tr>
                                                <td>${userAddress.id}</td>
                                                <td>${userAddress.userId}</td>
                                                <td>${userAddress.receiverName}</td>
                                                <td>${userAddress.receiverMobile}</td>
                                                <td>${userAddress.receiverPhone}</td>
                                                <td>${userAddress.receiverEmail}</td>
                                                <td>${userAddress.province}</td>
                                                <td>${userAddress.provinceName}</td>
                                                <td>${userAddress.city}</td>
                                                <td>${userAddress.cityName}</td>
                                                <td>${userAddress.county}</td>
                                                <td>${userAddress.countyName}</td>
                                                <td>${userAddress.town}</td>
                                                <td>${userAddress.townName}</td>
                                                <td>${userAddress.address}</td>
                                                <td>${userAddress.isDefault}</td>
                                                <td>${userAddress.source}</td>
                                                <td>${userAddress.status}</td>
                                            <td>
                                                <div class="am-btn-group am-btn-group-xs">
                                                <shiro:hasPermission name="setting:userAddress:update">
                                                    <a role="oper" href="javascript:;" onclick="openModel('修改用户地址表','${ctx}/setting/userAddress/update?id=${userAddress.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil"></span> 编辑</a>
                                                </shiro:hasPermission>
                                                <shiro:hasPermission name="setting:userAddress:delete">
                                                    <a role="oper" href="${ctx}/setting/userAddress/${userAddress.id}/delete?pageNo=${page.pageNo}&pageSize=${page.pageSize}" onclick="return confirm('确认要删除该条数据吗？', this.href)" class="am-btn am-btn-default am-btn-xs am-text-danger"><span class="am-text-danger am-icon-trash-o">删除</span></a>
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
