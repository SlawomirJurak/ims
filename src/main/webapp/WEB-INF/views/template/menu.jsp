<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="menu column">
    <c:choose>
        <c:when test="${empty param.moduleUser}">
            <c:set var="cssClass" value="menu-item"/>
        </c:when>
        <c:otherwise>
            <c:set var="cssClass" value="menu-item-selected"/>
        </c:otherwise>
    </c:choose>
    <div class="${cssClass}">
        <a href="/user/">Użytkownicy</a>
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
        <a href="/role/">Role</a>
    </div>

    <c:choose>
        <c:when test="${empty param.moduleProcess}">
            <c:set var="cssClass" value="menu-item"/>
        </c:when>
        <c:otherwise>
            <c:set var="cssClass" value="menu-item-selected"/>
        </c:otherwise>
    </c:choose>
    <div class="${cssClass}">
        <a href="/process/">Procesy</a>
    </div>

    <c:choose>
        <c:when test="${empty param.moduleSchedulePeriod}">
            <c:set var="cssClass" value="menu-item"/>
        </c:when>
        <c:otherwise>
            <c:set var="cssClass" value="menu-item-selected"/>
        </c:otherwise>
    </c:choose>
    <div class="${cssClass}">
        <a href="/scheduleperiod/">Harmonogramy</a>
    </div>

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

</div>
