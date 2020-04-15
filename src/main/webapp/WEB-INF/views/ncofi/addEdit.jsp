<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

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
    <c:when test="${empty ncofi.id}">
        <c:set var="additionalTitle" value="Nowa niezgodność/spostrzeżenie"/>
    </c:when>
    <c:otherwise>
        <c:set var="additionalTitle" value="Edycja niezgodności/spostrzeżenia"/>
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
        <form:form method="post" modelAttribute="ncofi">
            <input type="hidden" name="auditId" value="${auditId}">
            <form:hidden path="id"/>
            <table>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Typ:
                    </td>
                    <td class="align-middle">
                        <form:select path="type">
                            <form:option value="" label="Proszę wybrać"/>
                            <form:options items="${ncofiTypes}"/>
                        </form:select>
                        <form:errors path="type"/> <br/>
                    </td>
                </tr>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Proces:
                    </td>
                    <td class="align-middle">
                        <form:select path="process.id">
                            <form:option value="-1" label="Proszę wybrać"/>
                            <form:options items="${assignedProcesses}" itemLabel="code" itemValue="id"/>
                        </form:select>
                        <form:errors path="process"/> <br/>
                    </td>
                </tr>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Opis:
                    </td>
                    <td>
                        <form:textarea path="description" cols="50" rows="3"/>
                        <form:errors path="description"/> <br/>
                    </td>
                </tr>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Termin:
                    </td>
                    <td>
                        <form:input path="dueDate" type="date" value="${ncofi.dueDate.toString()}"/>
                        <form:errors path="dueDate"/> <br/>
                    </td>
                </tr>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Odpowiedzialny:
                    </td>
                    <td>
                        <form:input path="responsible"/>
                        <form:errors path="responsible"/> <br/>
                    </td>
                </tr>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Typ:
                    </td>
                    <td class="align-middle">
                        <form:select path="area">
                            <form:option value="" label="Proszę wybrać"/>
                            <form:options items="${ncofiAreas}"/>
                        </form:select>
                        <form:errors path="area"/> <br/>
                    </td>
                </tr>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Uwagi:
                    </td>
                    <td>
                        <form:textarea path="notes" cols="50" rows="5"/>
                        <form:errors path="notes"/> <br/>
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
