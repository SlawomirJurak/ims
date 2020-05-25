<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <%@include file="../../jspf/head.jspf" %>
    <link href="<c:url value="/css/scheduleperiod.css"/>" rel="stylesheet">
    <script src="<c:url value="/js/scheduleperiod.js"/>"></script>
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
                    <input type="submit" class="btn-sm btn-primary" value="Zatwierdź">
                </form>
            </c:when>
            <c:otherwise>
                <p class="alert-warning">Nie wszystkie procesy mają przypisany audit. Nie można zatwierdzić harmonogramu</p>
                <a href="<c:url value="/scheduleperiod/"/>">Powrót</a>
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
