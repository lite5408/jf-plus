<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>机构规格库</title>
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
                            <div class="widget-title am-fl">机构规格库</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="specLib" action="${ctx}/product/specLib/<c:choose><c:when test="${empty specLib.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${specLib.id}"/>
                                    <div class="am-form-group" style="display:none;">
                                        <label class="am-u-sm-3 am-form-label">公司机构ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="orgId" placeholder="公司机构ID" value="<c:if test='${empty specLib.id }'>${loginUser.organizationId}</c:if><c:if test='${not empty specLib.id }'>${specLib.orgId}</c:if>"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group" style="display:none;">
                                        <label class="am-u-sm-3 am-form-label">分类ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="cateId" placeholder="分类ID" value="0"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">规格</label>
                                        <div class="am-u-sm-9">
                                            <select class="am-input-sm" name="specType" data="${specLib.specType}" required>
                                            	<option value="颜色">颜色</option>
                                            	<option value="尺码">尺码</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">规格值：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="specName" placeholder="规格值" value="${specLib.specName}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">编码：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="specCode" placeholder="编码" value="${specLib.specCode}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">排序：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="sort" placeholder="排序" value="${specLib.sort}"/>
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
        
        initSelectValue(true);
    });
</script>
</body>
</html>
