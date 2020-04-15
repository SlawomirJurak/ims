<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Zintegrowany System Zarządzania</title>
    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/jquery/jquery-3.4.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <link href="/css/ims.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../template/header.jsp">
    <jsp:param name="additionalTitle" value="Użytkownicy"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleUser" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <div class="item-header">
            <a href="/user/add">
                <button class="btn-primary">Nowy użytkownik</button>
            </a>
        </div>
        <table class="table">
            <tr>
                <th>#</th>
                <th>Nazwa</th>
                <th>Nazwisko Imie</th>
                <th>Aktywny</th>
                <th colspan="2">Akcje</th>
            </tr>
            <c:forEach items="${users}" var="user" varStatus="loop">
                <tr>
                    <td class="align-middle">${loop.index + 1}</td>
                    <td class="align-middle">${user.userName}</td>
                    <td class="align-middle">${user.fullName}</td>
                    <td class="align-middle text-center">
                        <c:choose>
                            <c:when test="${user.enabled}">
                                <input type="checkbox" disabled="disabled" checked>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" disabled="disabled">
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="align-middle">
                        <form>
                            <button class="btn btn-primary">Edycja</button>
                        </form>
                    </td>
                    <td class="align-middle">
                        <form>
                            <button class="btn btn-danger">Usuń</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>

