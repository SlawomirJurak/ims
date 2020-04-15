<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Zintegrowany System Zarządzania</title>
    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/jquery/jquery-3.4.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script></head>
<body>
<c:choose>
    <c:when test="${empty user.id}">
        <h2>Nowy użytkownik</h2>
    </c:when>
    <c:otherwise>
        <h2>Edycja użytkownika</h2>
    </c:otherwise>
</c:choose>
<form:form method="post" modelAttribute="user">
    <form:hidden path="id"/>
    Nazwa: <form:input path="userName"/> <br/>
    <form:errors path="userName"/> <br/>
    Imie: <form:input path="firstName"/> <br/>
    <form:errors path="firstName"/> <br/>
    Nazwisko: <form:input path="lastName"/> <br/>
    <form:errors path="lastName"/><br/>
    Hasło: <form:password path="password"/> <br/>
    <form:errors path="lastName"/><br/>
    <c:if test="${not empty user.id}">
        Aktywny: <form:checkbox path="enabled"/> <br/>
    </c:if>
    Role:
    <form:select path="roles" items="${allRoles}" itemLabel="name" itemValue="id" multiple="true"/>
    <from:errors path="roles"/>
    <input type="submit" value="Zapisz"/>
</form:form>
</body>
</html>
