<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fnc" uri="/WEB-INF/tld/fnc.tld" %>
<%@taglib prefix="fns" uri="/WEB-INF/tld/fns.tld" %>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static1/mobile/front"/>
<c:set var="ctxS" value="${pageContext.request.contextPath}${fnc:getConfig('supplyPath')}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}${fnc:getConfig('adminPath')}"/>

<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no"/>
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" href="${ctxStatic }/css/amazeui.min.css" />
<link rel="stylesheet" href="${ctxStatic }/css/common.css" />
<link rel="stylesheet" href="${ctxStatic }/css/style.css" />
<link rel="stylesheet" href="${ctxStatic }/css/dropload.css" />
<script type="text/javascript" src="${ctxStatic }/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/js/amazeui.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/js/jf.js"></script>
<script src="${ctxStatic }/js/dropload.js"></script>

<link rel="stylesheet" href="${ctxStatic }/custom/css/style.css" />
