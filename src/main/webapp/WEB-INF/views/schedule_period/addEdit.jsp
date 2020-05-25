<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <%@include file="../../jspf/head.jspf" %>
</head>
<body>
<c:choose>
    <c:when test="${empty schedulePeriod.id}">
        <c:set var="additionalTitle" value="Nowy harmongram"/>
    </c:when>
    <c:otherwise>
        <c:set var="additionalTitle" value="Edycja Harmongramu"/>
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
        <form:form method="post" modelAttribute="schedulePeriod">
            <form:hidden path="id"/>
            <form:hidden path="state"/>
            <table>
                <tr>
                    <td class="align-middle left-padding right-padding">
                        Rok:
                    </td>
                    <td class="align-middle">
                        <form:input type="number" path="year"/> <br/>
                        <form:errors path="year"/> <br/>
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
            </table>
            <div class="left-padding">
                <button type="submit" class="btn-primary">Zapisz</button>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
