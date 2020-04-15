<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="header">
    <c:choose>
        <c:when test="${empty param.additionalTitle}">
            Zintegrowany system zarządzania
        </c:when>
        <c:otherwise>
            Zintegrowany system zarządzania - ${param.additionalTitle}
        </c:otherwise>
    </c:choose>
</nav>