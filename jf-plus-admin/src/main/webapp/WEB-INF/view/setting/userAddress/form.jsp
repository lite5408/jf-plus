<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>用户地址表</title>
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
                            <div class="widget-title am-fl">用户地址表</div>
                        </div>
                        <div class="widget-body am-fr">
                            <form class="am-form tpl-form-border-form" data-am-validator modelAttribute="userAddress" action="${ctx}/setting/userAddress/<c:choose><c:when test="${empty userAddress.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                            <input type="hidden" name="id" value="${userAddress.id}"/>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">用户id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="userId" placeholder="用户id" value="${userAddress.userId}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">收货人：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="receiverName" placeholder="收货人" value="${userAddress.receiverName}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">收货人手机号：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="receiverMobile" placeholder="收货人手机号" value="${userAddress.receiverMobile}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">座机：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="receiverPhone" placeholder="座机" value="${userAddress.receiverPhone}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">收货人邮箱：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="receiverEmail" placeholder="收货人邮箱" value="${userAddress.receiverEmail}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">省份：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="province" placeholder="省份" value="${userAddress.province}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">省份名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="provinceName" placeholder="省份名称" value="${userAddress.provinceName}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">城市id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="city" placeholder="城市id" value="${userAddress.city}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">城市名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="cityName" placeholder="城市名称" value="${userAddress.cityName}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">区镇ID：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="county" placeholder="区镇ID" value="${userAddress.county}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">区镇名称：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="countyName" placeholder="区镇名称" value="${userAddress.countyName}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">街道id：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="town" placeholder="街道id" value="${userAddress.town}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">街道地址：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="townName" placeholder="街道地址" value="${userAddress.townName}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">详细地址：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="address" placeholder="详细地址" value="${userAddress.address}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">是否默认：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="isDefault" placeholder="是否默认" value="${userAddress.isDefault}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">渠道来源：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="source" placeholder="渠道来源" value="${userAddress.source}"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="am-u-sm-3 am-form-label">备注：</label>
                                        <div class="am-u-sm-9">
                                            <input type="text" name="remarks" placeholder="备注" value="${userAddress.remarks}"/>
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
    });
</script>
</body>
</html>
