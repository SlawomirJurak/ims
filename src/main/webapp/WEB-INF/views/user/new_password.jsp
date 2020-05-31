<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <%@include file="../../jspf/head.jspf" %>
    <link href="<c:url value="/css/login.css"/>" rel="stylesheet">
    <script src="<c:url value="/js/functions.js"/>"></script>
    <script src="<c:url value="/js/user/user_change_password.js"/>"></script>
</head>
<body>
<%@include file="../template/modalDialog.jspf"%>
<nav class="header">
    <div class="title">
        Zintegrowany system zarządzania - Zmiana hasła startowego
    </div>
    <sec:authorize access="isAuthenticated()">
        <div class="info">
            <div class="user-info font10pt font-weight-bold">
                Witaj: <sec:authentication property="principal.username"/>
            </div>
            <div>
                <a href="<c:url value="/logout"/>">Wyloguj</a>
            </div>
        </div>
    </sec:authorize>
</nav>
<div class="content">
    <div style="width: 100%; height: 10px"></div>
    <div class="div-form-login">
        <div class="form-login">
            <div style="padding-bottom: 5px">
                <div class="div-label inline-block">Nowe hasło:</div>
                <div class="div-input inline-block">
                    <input type="password" id="new-password1" autofocus/>
                </div>
            </div>
            <div style="padding-bottom: 5px">
                <div class="div-label inline-block">Powtórz hasło:</div>
                <div class="div-input inline-block">
                    <input type="password" id="new-password2"/>
                </div>
            </div>
            <div style="display: flex; justify-content: center">
                <button class="btn-sm btn-primary" id="btn-change-password" data-user-id="${user.id}">Zmień hasło</button>
                <button class="btn-sm btn-primary" id="btn-landing-page">Strona główna</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
