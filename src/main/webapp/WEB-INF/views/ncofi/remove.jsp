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
    <jsp:param name="additionalTitle" value="Usuwanie niezgodności/spostrzeżenia"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleAudit" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <jsp:include page="../template/data_header.jsp"></jsp:include>
        <h2>Czy usunąć ${ncofi.type.toLowerCase()} - ${ncofi.description}</h2>
        <form:form method="post" modelAttribute="viewHelper" class="inline-block">
            <input type="hidden" name="toRemoveNcofiId" value="${scheduleAudit.id}">
            <form:hidden path="option" value="yes"/>
            <input class="btn btn-danger" type="submit" value="Tak">
        </form:form>
        <form:form method="post" modelAttribute="viewHelper" class="inline-block">
            <input type="hidden" name="toRemoveNcofiId" value="${scheduleAudit.id}">
            <form:hidden path="option" value="no"/>
            <input class="btn btn-primary" type="submit" value="Nie">
        </form:form>
    </div>
</div>
</body>
</html>
