<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Role</title>
    <meta name="csrf-token" content="${_csrf.token}">
    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/ims.css" rel="stylesheet">
    <script src="/jquery/jquery-3.4.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <script src="/js/role/role_all.js"></script>
</head>
<body>
<jsp:include page="../template/header.jsp">
    <jsp:param name="additionalTitle" value="Role"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleRole" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <div class="item-header">
<%--            <a href="/role/add">--%>
                <button id="btn-new-role" class="btn-sm btn-primary">Nowa rola</button>
<%--            </a>--%>
        </div>
        <div id="new-role-form">
            <div class="inline-block">
                <label for="role-name">Nazwa</label><br/>
                <input id="role-name" type="text">
            </div>
            <div class="inline-block">
                <label for="role-description">Opis</label><br/>
                <input id="role-description" type="text">
            </div>
            <button id="btn-save" class="btn-sm btn-primary left-padding inline-block">Zapisz</button>
            <button id="btn-cancel" class="btn-sm btn-light left-padding inline-block">Anuluj</button>
        </div>
        <table id="table-roles" class="table font10pt">
            <thead class="thead-light">
            <tr>
                <th>#</th>
                <th>Rola</th>
                <th>Opis</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${roles}" var="role" varStatus="loop">
                <tr>
                    <td>${loop.index+1}</td>
                    <td>${role.name}</td>
                    <td>${role.description}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
