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
    <jsp:param name="additionalTitle" value="Usuwanie pozycji z harmonogramu"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleSchedulePeriod" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <jsp:include page="../template/data_header.jsp"></jsp:include>
        <h2>Czy usunąć z harmonogramu - ${scheduleAudit.description} - dla procesów ${scheduleAudit.assignedProcesses}</h2>
        <form:form method="post" modelAttribute="viewHelper" class="inline-block">
            <input type="hidden" name="toRemoveId" value="${scheduleAudit.id}">
            <form:hidden path="option" value="yes"/>
            <input class="btn btn-danger" type="submit" value="Tak!">
        </form:form>
        <form:form method="post" modelAttribute="viewHelper" class="inline-block">
            <input type="hidden" name="toRemoveId" value="${scheduleAudit.id}">
            <form:hidden path="option" value="no"/>
            <input class="btn btn-primary" type="submit" value="Nie!">
        </form:form>
    </div>
</div>
</body>
</html>
