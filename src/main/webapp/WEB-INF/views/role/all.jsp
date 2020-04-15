<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Role</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"></head>
    <script src="jquery/jquery-3.4.1.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<a href="/role/add">Nowa rola</a>
<table class="table">
    <tr>
        <th>#</th>
        <th>Rola</th>
        <th>Opis</th>
    </tr>
    <c:forEach items="${roles}" var="role" varStatus="loop">
        <tr>
            <td>${loop.index+1}</td>
            <td>${role.name}</td>
            <td>${role.description}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
