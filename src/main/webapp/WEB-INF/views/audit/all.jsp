<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Zintegrowany System Zarządzania</title>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/ims.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/scheduleperiod.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/audit.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/ncofi.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/jquery/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/audit.js"></script>
</head>
<body>
<jsp:include page="../template/header.jsp">
    <jsp:param name="additionalTitle" value="Audity"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleAudit" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <div class="data-line div-data-header">
            <div class="audit-id inline-block">#</div>
            <div class="period-year">Rok</div>
            <div class="audit-description inline-block">Opis</div>
            <div class="audit-approved-by inline-block">Zatwierdzony przez</div>
            <div class="audit-approve-date inline-block">Data zatw.</div>
            <div class="audit-state inline-block">Status</div>
            <div class="inline-block">Akcje</div>
        </div>
        <c:forEach items="${allAudits}" var="audit">
            <div class="data-line">
                <div class="audit-id inline-block">${audit.id}(${audit.ncofiList.size()})(${audit.completedNcofiCount})</div>
                <div class="period-year">${audit.scheduleAudit.schedulePeriod.year}</div>
                <div class="audit-description inline-block">${audit.description}</div>
                <div class="audit-approved-by inline-block">${audit.approvedBy}</div>
                <div class="audit-approve-date inline-block">${audit.approveDate}</div>
                <div class="audit-state inline-block">${audit.state}</div>
                <c:if test="${audit.state=='W przygotowaniu'}">
                    <div class="inline-block">
                        <form class="inline-block" method="get" action="edit">
                            <input type="hidden" value="${audit.id}" name="idToEdit">
                            <button class="btn btn-primary font10pt">Edycja</button>
                        </form>
                        <form class="inline-block" method="get" action="approve">
                            <input type="hidden" value="${audit.id}" name="idToApprove">
                                <%--                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                            <button class="btn btn-secondary font10pt">Zatwierdź</button>
                        </form>
                    </div>
                    <div class="inline-block">
                        <form method="get" action="/ncofi/add">
                            <input type="hidden" value="${audit.id}" name="auditId">
                            <button class="btn btn-link font10pt">Nowa niezgodność/spostrzeżenie</button>
                        </form>
                    </div>
                </c:if>
                <div class="ncofi-list">
                    <table class="table font10pt">
                        <thead class="thead-light">
                        <tr>
                            <th>Typ</th>
                            <th>Proces</th>
                            <th class="desc-column">Opis</th>
                            <th>Termin realizacji</th>
                            <th>Odpowiedzialny</th>
                            <th>Obszar</th>
                            <th class="notes-column">Uwagi</th>
                            <th>Akcje</th>
                        </tr>
                        </thead>
                        <c:forEach items="${audit.ncofiList}" var="ncofi">
                            <tr>
                                <c:choose>
                                    <c:when test="${empty ncofi.completeDate}">
                                        <td>${ncofi.type}</td>
                                        <td>${ncofi.process.code}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td rowspan="2">${ncofi.type}</td>
                                        <td rowspan="2">${ncofi.process.code}</td>
                                    </c:otherwise>
                                </c:choose>
                                <td class="desc-column">${ncofi.description}</td>
                                <td>${ncofi.dueDate}</td>
                                <td>${ncofi.responsible}</td>
                                <td>${ncofi.area}</td>
                                <td class="notes-column">${ncofi.notes}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty ncofi.confirmedBy}">
                                            <form method="get" action="/ncofi/edit" class="inline-block">
                                                <input type="hidden" name="toEditNcofiId" value="${ncofi.id}">
                                                <button class="btn-sm btn-primary">Edycja</button>
                                            </form>
                                            <form method="get" action="/ncofi/remove" class="inline-block">
                                                <input type="hidden" name="toRemoveNcofiId" value="${ncofi.id}">
                                                <button class="btn-sm btn-danger">Usuń</button>
                                            </form>
                                            <form method="get" action="/ncofi/confirm/${ncofi.id}" class="inline-block">
                                                <button class="btn-sm btn-dark">Potwierdź</button>
                                            </form>
                                        </c:when>
                                        <c:when test="${empty ncofi.completeDate}">
                                            <form method="get" action="/ncofi/completed/${ncofi.id}">
                                                <c:choose>
                                                    <c:when test="${ncofi.type=='Niezgodność'}">
                                                        <button class="btn-sm btn-lg">Usunięta</button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button class="btn-sm btn-lg">Poprawione</button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </form>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:if test="${not empty ncofi.completeDate}">
                                <tr>
                                    <td>Data zakończenia: ${ncofi.completeDate}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${ncofi.successfully}">Skuteczne</c:when>
                                            <c:otherwise>Nieskuteczne</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td colspan="3">${ncofi.takenActions}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
