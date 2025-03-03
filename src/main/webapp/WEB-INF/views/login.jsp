<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <%@include file="../jspf/head.jspf" %>
    <link href="<c:url value="/css/login.css"/>" rel="stylesheet">
    <script src="<c:url value="/js/functions.js"/>"></script>
    <script src="<c:url value="/js/login.js"/>"></script>
</head>
<body>
<div class="header">
    <div class="title">
        Logowanie do systemu
    </div>
</div>
<div class="content">
    <div style="width: 100%; height: 10px"></div>
    <div class="div-form-login">
        <form method="post" class="form-login">
            <c:if test="${not empty error}">
                <div class="div-error-message">
                    ${error}
                </div>
            </c:if>
            <div style="padding-bottom: 5px">
                <div class="div-label inline-block">Użytkownik:</div>
                <div class="div-input inline-block">
                    <input type="text" name="username" autofocus id="username"/>
                </div>
            </div>
            <div style="padding-bottom: 5px">
                <div class="div-label inline-block">Hasło:</div>
                <div class="div-input inline-block">
                    <input type="password" name="password" id="password"/>
                </div>
            </div>
            <div style="display: flex; justify-content: center">
                <input type="submit" value="Zaloguj" id="btn-login"/>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>
<%@include file="template/modalDialog.jspf"%>
</body>
</html>
