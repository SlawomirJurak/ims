<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <%@include file="../../jspf/head.jspf" %>
    <link href="<c:url value="/css/scheduleperiod.css"/>" rel="stylesheet">
    <script src="<c:url value="/js/scheduleperiod.js"/>"></script>
</head>
<body>
<jsp:include page="../template/header.jsp">
    <jsp:param name="additionalTitle" value="Harmonogramy auditów"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleSchedulePeriod" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <div class="item-header">
            <a href="<c:url value="/scheduleperiod/add"/>">
                <button class="btn-primary">Nowy harmonogram</button>
            </a>
        </div>
        <div class="data-line div-data-header">
            <div class="period-year">Rok</div>
            <div class="period-description">Opis</div>
            <div class="period-state">Status</div>
            <div class="inline-block">Akcje</div>
        </div>
        <c:forEach items="${allPeriods}" var="period">
            <div class="data-line">
                <div class="period-year">${period.year}(${period.scheduleAudits.size()})</div>
                <div class="period-description">${period.description}</div>
                <div class="period-state">${period.state}</div>
                <div class="inline-block">
                    <c:if test="${period.state=='W przygotowaniu'}">
                        <form class="inline-block" method="get" action="edit">
                            <input type="hidden" value="${period.id}" name="idToEdit">
                            <button class="btn-sm btn-primary">Edycja</button>
                        </form>
                        <form class="inline-block" method="get" action="approve">
                            <input type="hidden" value="${period.id}" name="toApproveId">
                            <button class="btn-sm btn-secondary">Zatwierdź</button>
                        </form>
                        <form class="inline-block" method="get" action="<c:url value="/scheduleaudit/add"/>">
                            <input type="hidden" value="${period.id}" name="periodId">
                            <button class="btn-sm btn-link">Nowy audit</button>
                        </form>
                    </c:if>
                </div>
                <div class="period-schedule">
                    <table class="table font10pt">
                        <thead class="thead-light">
                        <tr class="font10pt">
                            <th>Miesiąc</th>
                            <th>Opis</th>
                            <th>Procesy</th>
                            <th>Audit utworzony</th>
                            <th>Akcje</th>
                        </tr>
                        </thead>
                        <c:forEach items="${period.scheduleAudits}" var="scheduleAudit">
                            <tr>
                                <td>${scheduleAudit.monthName}</td>
                                <td>${scheduleAudit.description}</td>
                                <td>${scheduleAudit.assignedProcesses}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty scheduleAudit.audit}">
                                            <input type="checkbox" disabled/>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" disabled checked/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${period.state=='W przygotowaniu'}">
                                        <form method="get" action="<c:url value="/scheduleaudit/edit"/>" class="inline-block">
                                            <input type="hidden" name="toEditScheduleAuditId"
                                                   value="${scheduleAudit.id}">
                                            <button class="btn-sm btn-primary">Edycja</button>
                                        </form>
                                        <form method="get" action="<c:url value="/scheduleaudit/remove"/>" class="inline-block">
                                            <input type="hidden" name="toRemoveScheduleAuditId"
                                                   value="${scheduleAudit.id}">
                                            <button class="btn-sm btn-danger">Usuń</button>
                                        </form>
                                    </c:if>
                                    <c:choose>
                                        <c:when test="${empty scheduleAudit.audit and period.state=='Zatwierdzony'}">
                                            <form method="get" action="<c:url value="/audit/add"/>" class="inline-block">
                                                <input type="hidden" name="scheduleAuditId" value="${scheduleAudit.id}">
                                                <button class="btn-sm btn-secondary">Utwórz audit</button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
