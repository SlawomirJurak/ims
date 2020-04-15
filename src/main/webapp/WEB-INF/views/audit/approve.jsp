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
    <link href="${pageContext.request.contextPath}/css/audit.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/ncofi.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/jquery/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/audit_approve.js"></script>
</head>
<body>
<jsp:include page="../template/header.jsp">
    <jsp:param name="additionalTitle" value="Zatwierdzanie auditu"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleAudit" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <h4>Zatwierdzenie auditu</h4>
        <table class="table font10pt">
            <thead class="thead-light">
            <tr>
                <th>Opis</th>
                <th>Procesy</th>
            </tr>
            </thead>
            <tr>
                <td>${audit.description}</td>
                <td>${audit.scheduleAudit.assignedProcesses}</td>
            </tr>
        </table>
        <c:choose>
            <c:when test="${empty ncofiList}">
                <form method="post">
                    <label for="approvedBy">Zatwierdzający</label> <br/>
                    <input hidden name="idToApprove" value="${audit.id}">
                    <input type="text" name="approvedBy" id="approvedBy">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="submit" value="Zatwierdzam" id="btnApprove">
                </form>
            </c:when>
            <c:otherwise>
                <p>Poniższe niezgodności/spotrzeżenia nie zostały potwierdzone. Nie można zatwierdzić auditu.</p>
                <table class="table font10pt">
                    <thead class="thead-light">
                    <tr>
                        <th>Opis</th>
                        <th>Proces</th>
                    </tr>
                    </thead>
                    <c:forEach items="${ncofiList}" var="ncofi">
                        <tr>
                            <td>${ncofi.description}</td>
                            <td>${ncofi.process.code}</td>
                        </tr>
                    </c:forEach>
                </table>
                <a class="nav-link" href="/audit/">Powrót</a>
            </c:otherwise>
        </c:choose>
        <a
    </div>
</div>
</body>
</html>
