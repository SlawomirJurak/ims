<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="header">
    <div class="title">
        <a href="/">
            <c:choose>
                <c:when test="${empty param.additionalTitle}">
                    Zintegrowany system zarządzania
                </c:when>
                <c:otherwise>
                    Zintegrowany system zarządzania - ${param.additionalTitle}
                </c:otherwise>
            </c:choose>
        </a>
    </div>
    <sec:authorize access="isAuthenticated()">
        <div class="info">
            <div class="user-info font10pt font-weight-bold">
                Witaj: <sec:authentication property="principal.username"/>
            </div>
            <div>
                <a href="<c:url value="/logout"/>">Wyloguj</a>
            </div>
        </div>
    </sec:authorize>
</nav>
