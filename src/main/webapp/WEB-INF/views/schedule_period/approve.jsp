<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Zintegrowany System Zarządzania</title>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/ims.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/scheduleperiod.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/jquery/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/scheduleperiod.js"></script>
</head>
<body>
<jsp:include page="../template/header.jsp">
    <jsp:param name="additionalTitle" value="Harmonogramy auditów"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleSchedulePeriod" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <h4>Zatwierdzenie harmonogramu auditów na rok ${schedulePeriod.year}</h4>
        <c:choose>
            <c:when test="${allProcessesAssigned}">
                <p>Wszystkie aktywne procesy mają przypisany audit. Harmonogram jest gotowy do zatwierdzenia</p>
                <form method="post">
                    <input type="hidden" name="toApproveId" value="${schedulePeriod.id}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="submit" class="btn-sm btn-primary" value="Zatwierdż">
                </form>
            </c:when>
            <c:otherwise>
                <p class="alert-warning">Nie wszystkie procesy mają przypisany audit. Nie można zatwierdzić harmonogramu</p>
                <a href="/scheduleperiod/">Powrót</a>
            </c:otherwise>
        </c:choose>
        <table class="table">
            <thead class="thead-light">
            <tr>
                <th>Proces</th>
                <th>Miesiące</th>
            </tr>
            </thead>
            <c:forEach items="${processesMap}" var="process">
                <tr>
                    <td>${process.key}</td>
                    <td>${process.value}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
