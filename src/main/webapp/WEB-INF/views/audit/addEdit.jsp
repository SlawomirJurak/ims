<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <%@include file="../../jspf/head.jspf"%>
</head>
<body>
<c:choose>
    <c:when test="${empty scheduleAudit.id}">
        <c:set var="additionalTitle" value="Nowy audit"/>
    </c:when>
    <c:otherwise>
        <c:set var="additionalTitle" value="Edycja auditu"/>
    </c:otherwise>
</c:choose>
<jsp:include page="../template/header.jsp">
    <jsp:param name="additionalTitle" value="${additionalTitle}"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleAudit" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <jsp:include page="../template/data_header.jsp"></jsp:include>
        <c:choose>
            <c:when test="${empty audit.id}">
                <h4>Tworzenie auditu - Procesy: ${scheduleAudit.assignedProcesses}</h4>
            </c:when>
            <c:otherwise>
                <h4>Edycja auditu - Procesy: ${scheduleAudit.assignedProcesses}</h4>
            </c:otherwise>
        </c:choose>
        <form:form method="post" modelAttribute="audit">
            <input type="hidden" name="scheduleAuditId" value="${scheduleAuditId}">
            <form:hidden path="id"/>
            <form:hidden path="state"/>
            <table>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Opis Auditu:
                    </td>
                    <td class="align-middle">
                        <form:textarea path="description" cols="70" rows="5"/> <br/>
                        <form:errors path="description"/> <br/>
                    </td>
                </tr>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Zatwierdzony przez:
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${empty audit.id}">
                                <form:input path="approvedBy" disabled="true"/><br/>
                            </c:when>
                            <c:otherwise>
                                <form:input path="approvedBy"/><br/>
                            </c:otherwise>
                        </c:choose>
                        <form:errors path="approvedBy"/> <br/>
                    </td>
                </tr>
            </table>
            <div class="left-padding">
                <button type="submit" class="btn-primary">Zapisz</button>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
