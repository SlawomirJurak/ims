<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="menu column">
    <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'administrator')">
        <c:choose>
            <c:when test="${empty param.moduleUser}">
                <c:set var="cssClass" value="menu-item"/>
            </c:when>
            <c:otherwise>
                <c:set var="cssClass" value="menu-item-selected"/>
            </c:otherwise>
        </c:choose>
        <div class="${cssClass}">
            <a href="<c:url value="/user/"/>">Użytkownicy</a>
        </div>

        <c:choose>
            <c:when test="${empty param.moduleRole}">
                <c:set var="cssClass" value="menu-item"/>
            </c:when>
            <c:otherwise>
                <c:set var="cssClass" value="menu-item-selected"/>
            </c:otherwise>
        </c:choose>
        <div class="${cssClass}">
            <a href="<c:url value="/role/"/>">Role</a>
        </div>
    </sec:authorize>

    <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'administrator', 'all_modules')">
        <c:choose>
            <c:when test="${empty param.moduleProcess}">
                <c:set var="cssClass" value="menu-item"/>
            </c:when>
            <c:otherwise>
                <c:set var="cssClass" value="menu-item-selected"/>
            </c:otherwise>
        </c:choose>
        <div class="${cssClass}">
            <a href="<c:url value="/process/"/>">Procesy</a>
        </div>
    </sec:authorize>

    <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'administrator', 'all_modules')">
        <c:choose>
            <c:when test="${empty param.moduleSchedulePeriod}">
                <c:set var="cssClass" value="menu-item"/>
            </c:when>
            <c:otherwise>
                <c:set var="cssClass" value="menu-item-selected"/>
            </c:otherwise>
        </c:choose>
        <div class="${cssClass}">
            <a href="<c:url value="/scheduleperiod/"/>">Harmonogramy</a>
        </div>
    </sec:authorize>

    <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'administrator', 'all_modules')">
        <c:choose>
            <c:when test="${empty param.moduleAudit}">
                <c:set var="cssClass" value="menu-item"/>
            </c:when>
            <c:otherwise>
                <c:set var="cssClass" value="menu-item-selected"/>
            </c:otherwise>
        </c:choose>
        <div class="${cssClass}">
            <a href="/audit/">Audity</a>
        </div>
    </sec:authorize>

</div>
