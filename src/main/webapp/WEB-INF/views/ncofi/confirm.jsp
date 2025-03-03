<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <%@include file="../../jspf/head.jspf"%>
    <link href="<c:url value="/css/scheduleperiod.css"/>" rel="stylesheet">
    <script src="<c:url value="/js/ncofi/ncofi_approve.js"/>"></script>
</head>
<body>
<jsp:include page="../template/header.jsp">
    <jsp:param name="additionalTitle" value="Potwierdzenie niezgodności/spostrzeżenia"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleAudit" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <h5>Potwierdzasz ${ncofi.type}</h5>
        <table class="table">
            <tr>
                <td colspan="2">${ncofi.description}</td>
            </tr>
            <tr>
                <td>Proces</td>
                <td>${ncofi.process.code}</td>
            </tr>
        </table>
        <form:form modelAttribute="viewHelper" method="post">
            <form:hidden path="option" value="yes"/>
            <label for="confirmedBy">Potwierdzający</label>
            <input type="text" placeholder="Potwierdzający" name="confirmedBy" id="confirmedBy">
            <button class="btn-sm btn-primary" type="submit" id="btn-approve">Potwierdź</button>
            <a class="nav-link" href="<c:url value="/audit/"/>">Powrót</a>
        </form:form>
    </div>
</div>
</body>
</html>
