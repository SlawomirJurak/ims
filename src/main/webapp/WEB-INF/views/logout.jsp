<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <%@include file="../jspf/head.jspf" %>
</head>
<body>
<div class="header">
    <div class="title">
        <div class="inline-block">
            Wylogowanie z systemu
        </div>
        <div class="inline-block" style="width: 20px"></div>
        <form action="<c:url value="/logout"/>" method="post" class="inline-block" style="margin-block-end: 0em">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Wyloguj"/>
        </form>
    </div>
</div>
</body>
</html>
