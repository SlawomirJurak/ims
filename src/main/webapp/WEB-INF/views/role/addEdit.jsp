<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Rola</title>
</head>
<body>
<c:choose>
    <c:when test="${empty role.id}">
        <h2>Nowa rola</h2>
    </c:when>
    <c:otherwise>
        <h2>Edycja roli</h2>
    </c:otherwise>
</c:choose>
<form:form method="post" modelAttribute="role">
    <form:hidden path="id"/>
    <c:choose>
        <c:when test="${empty role.id}">
            Nazwa: <form:input path="name"/> <br/>
            <form:errors path="name"/>
        </c:when>
        <c:otherwise>
            Nazwa: <form:input path="name" readonly=""/> <br/>
        </c:otherwise>
    </c:choose>
    Opis: <form:input path="description"/> <br/>
    <input type="submit" value="Zapisz">
</form:form>
</body>
</html>
