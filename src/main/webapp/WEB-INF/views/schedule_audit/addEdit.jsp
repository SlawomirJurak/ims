<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Zintegrowany System Zarządzania</title>
    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/ims.css" rel="stylesheet">
    <script src="/jquery/jquery-3.4.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<c:choose>
    <c:when test="${empty scheduleAudit.id}">
        <c:set var="additionalTitle" value="Harmongram - Nowy audit"/>
    </c:when>
    <c:otherwise>
        <c:set var="additionalTitle" value="Harmonogram - Edycja auditu"/>
    </c:otherwise>
</c:choose>
<jsp:include page="../template/header.jsp">
    <jsp:param name="additionalTitle" value="${additionalTitle}"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleSchedulePeriod" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <jsp:include page="../template/data_header.jsp"></jsp:include>
        <form:form method="post" modelAttribute="scheduleAudit">
            <input type="hidden" name="periodId" value="${periodId}">
            <form:hidden path="id"/>
            <table>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Miesiąc:
                    </td>
                    <td class="align-middle">
                        <form:select path="month">
                            <form:option value="-1" label="Proszę wybrać"/>
                            <form:options items="${monthList}" itemLabel="name" itemValue="id"/>
                        </form:select>
                        <form:errors path="month"/> <br/>
                    </td>
                </tr>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Opis:
                    </td>
                    <td>
                        <form:input path="description"/><br/>
                        <form:errors path="description"/> <br/>
                    </td>
                </tr>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Procesy:
                    </td>
                    <td>
                        <form:select path="processes" items="${allProcesses}" itemLabel="codeAndName" itemValue="id" multiple="true"/>
                        <form:errors path="processes"/>
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
