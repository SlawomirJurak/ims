<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Zintegrowany System Zarządzania</title>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/ims.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/jquery/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<c:choose>
    <c:when test="${empty process.id}">
        <c:set var="additionalTitle" value="Nowy proces"/>
    </c:when>
    <c:otherwise>
        <c:set var="additionalTitle" value="Edycja procesu"/>
    </c:otherwise>
</c:choose>
<jsp:include page="../template/header.jsp">
    <jsp:param name="additionalTitle" value="${additionalTitle}"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleProcess" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <jsp:include page="../template/data_header.jsp"></jsp:include>
        <form:form method="post" modelAttribute="process">
            <form:hidden path="id"/>
            <form:hidden path="state"/>
            <table>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Kod:
                    </td>
                    <td class="align-middle">
                        <form:input path="code" oninput="let p = this.selectionStart; this.value = this.value.toUpperCase();this.setSelectionRange(p, p)"/> <br/>
                        <form:errors path="code"/>
                    </td>
                </tr>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Nazwa:
                    </td>
                    <td class="align-middle">
                        <form:input path="name"/> <br/>
                        <form:errors path="name"/>
                    </td>
                </tr>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Właściciel:
                    </td>
                    <td>
                        <form:input path="owner"/><br/>
                        <form:errors path="owner"/>
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
