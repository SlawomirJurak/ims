<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <%@include file="../../jspf/head.jspf" %>
    <script src="<c:url value="/js/functions.js"/>"></script>
    <script src="<c:url value="/js/process/all.js"/>"></script>
</head>
<body>
<%@include file="../template/modalDialog.jspf" %>
<%@include file="../template/confirmRemoveDialog.jspf" %>
<jsp:include page="../template/header.jsp">
    <jsp:param name="additionalTitle" value="Procesy"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleProcess" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <div class="item-header">
            <a href="<c:url value="/process/add"/>">
                <button class="btn-primary">Nowy proces</button>
            </a>
        </div>
        <table class="table font10pt">
            <thead class="thead-light">
            <tr>
                <th>#</th>
                <th>Kod</th>
                <th>Nazwa</th>
                <th>Właściciel</th>
                <th>Status</th>
                <th colspan="2">Akcje</th>
                <th>Dokument</th>
            </tr>
            </thead>
            <c:forEach items="${allProcesses}" var="process" varStatus="loop">
                <tr data-process-id="${process.id}" data-rv="${process.rowVersion}" class="data-row">
                    <td class="align-middle row-no">${loop.index + 1}</td>
                    <td class="align-middle">${process.code}</td>
                    <td class="align-middle">${process.name}</td>
                    <td class="align-middle">${process.owner}</td>
                    <td class="align-middle">${process.state}</td>
                    <td class="align-middle">
                        <c:if test="${process.state=='W przygotowaniu'}">
                            <form method="get" action="edit">
                                <input type="hidden" value="${process.id}" name="idToEdit">
                                <button class="btn-sm btn-primary">Edycja</button>
                            </form>
                            <button type="button" class="btn-sm btn-danger btn-remove">Usuń</button>
                        </c:if>
                    </td>
                    <td class="align-middle">
                        <form method="post" action="changeState">
                            <input type="hidden" value="${process.id}" name="idChangeState">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <c:choose>
                                <c:when test="${process.state=='W przygotowaniu'}">
                                    <input type="hidden" value="Aktywny" name="newState">
                                    <button class="btn-sm btn-secondary">Aktywuj</button>
                                </c:when>
                                <c:when test="${process.state=='Aktywny'}">
                                    <input type="hidden" value="Wycofany" name="newState">
                                    <button class="btn-sm btn-secondary">Wycofaj</button>
                                </c:when>
                            </c:choose>
                        </form>
                    </td>
                    <td>
                        <div>
                            <c:choose>
                                <c:when test="${empty process.documentFile.id}">
                                    <c:set var="labelCaption" value="Wgraj dokument"/>
                                    <c:set var="btnUploadCaption" value="Zapisz"/>
                                    <c:set var="btnShowStyle" value="none"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="labelCaption" value="Podmień"/>
                                    <c:set var="btnUploadCaption" value="Podmień"/>
                                    <c:set var="btnShowStyle" value="inline-block"/>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${process.state=='W przygotowaniu'}">
                                <label for="file${process.id}"
                                       class="btn-sm btn-light file-name">${labelCaption}</label>
                                <input id="file${process.id}" style="display: none" type="file" class="file-to-upload"
                                       name="documentFile" accept=".pdf">
                                <button class="btn-sm btn-light btn-upload" style="display: none"
                                        data-id="${process.id}">
                                        ${btnUploadCaption}
                                </button>
                            </c:if>
                            <button class="btn-sm btn-secondary btn-show" style="display: ${btnShowStyle}"
                                    data-id="${process.documentFile.id}">Pobierz
                            </button>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
