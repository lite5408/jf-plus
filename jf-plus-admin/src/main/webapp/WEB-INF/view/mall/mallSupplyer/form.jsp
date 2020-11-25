<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>供应商</title>
    <%@ include file="../../include/head.jsp"%>
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
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="mallSupplyer" action="${ctx}/mall/mallSupplyer/<c:choose><c:when test="${empty mallSupplyer.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${mallSupplyer.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>公司全称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="companyName" placeholder="公司全称" value="${mallSupplyer.companyName}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>公司简称(前台展示)：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="companyTitle" placeholder="公司简称" value="${mallSupplyer.companyTitle}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>开户时间：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="regTime" placeholder="开户时间" value="<fmt:formatDate value="${mallSupplyer.regTime}" pattern="yyyy-MM-dd"/>" data-am-datepicker required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>有效期：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="endDate" placeholder="有效期" value="<fmt:formatDate value="${mallSupplyer.endDate}" pattern="yyyy-MM-dd"/>" data-am-datepicker required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>联系人：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="contact" placeholder="联系人" value="${mallSupplyer.contact}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>联系方式：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="contactLink" placeholder="联系方式" value="${mallSupplyer.contactLink}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>货源地区：</label>
                                        <div class="am-u-sm-9">
                                            <select class="am-input-sm"  name="area" data="${mallSupplyer.area }" data-sm-selected>
                                            	<c:forEach items="${area }" var="item">
                                            		<option value="${item.id }">${item.val }</option>
                                            	</c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>经营品牌：</label>
                                        <div class="am-u-sm-9">
                                            <select data-am-selected="{searchBox: 1,maxHeight:120}" multiple name="brandIds" data="${mallSupplyer.brandIds }">
                                            	<c:forEach items="${brandIds }" var="item">
                                            		<option value="${item.id }" <c:if test="${fnc:inStr(mallSupplyer.brandIds,item.id) }">selected</c:if>>${item.brandName }</option>
                                            	</c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>启用：</label>
                                        <div class="am-u-sm-9">
                                            <select name="status">
                                            		<option value="1" <c:if test="${mallSupplyer.status eq 1}">selected</c:if>>启用</option>
                                            		<option value="0" <c:if test="${mallSupplyer.status eq 0}">selected</c:if>>禁用</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="am-form-group" style="display:none">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>管理员账号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="adminLoginname" placeholder="管理员账号" value="${mallSupplyer.adminLoginname}" required/>
                                        </div>
                                    </div>
                                    <div class="am-form-group" style="display:none">
                                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>管理员密码：</label>
                                        <div class="am-u-sm-9">
                                        	<c:if test="${empty mallSupplyer.adminPwd }">
                                        		<input type="password" name="adminPwd" placeholder="管理员密码" value="" required/>
                                        	</c:if>
                                        	<c:if test="${not empty mallSupplyer.adminPwd }">
                                        		<input type="password" name="adminPwd" placeholder="管理员密码（为空代表不修改）" value=""/>
                                        	</c:if>
                                            
                                        </div>
                                    </div>
                                    <div class="am-form-group" style="display:none">
                                        <label class="am-u-sm-3 am-form-label">备注：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="备注" value="${mallSupplyer.remarks}"/>
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
<%@ include file="../../include/bottom.jsp"%>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
        
        initSelectValue(true)
    });
</script>
</body>
</html>
