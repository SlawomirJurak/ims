<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Zintegrowany System Zarządzania</title>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/ims.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
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
                <div class="div-label inline-block">Użytkownik:</div> <div class="div-input inline-block"><input type="text" name="username"/></div>
            </div>
            <div style="padding-bottom: 5px">
                <div class="div-label inline-block">Hasło:</div> <div class="div-input inline-block"><input type="password" name="password"/></div>
            </div>
            <div style="display: flex; justify-content: center"><input type="submit" value="Zaloguj"/></div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>
</body>
</html>
