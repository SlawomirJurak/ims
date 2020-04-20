<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Zintegrowany System Zarządzania</title>
    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/ims.css" rel="stylesheet">
    <script src="/jquery/jquery-3.4.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <script src="/ncofi/ncofi_completed.js"></script>
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
        <c:choose>
            <c:when test="${ncofi.type=='Niezgodność'}">
                <c:set var="typeText" value="niezgodnością"/>
            </c:when>
            <c:otherwise>
                <c:set var="typeText" value="spostrzeżeniem"/>
            </c:otherwise>
        </c:choose>
        <h5>Potwierdzasz zakończenie działań związanych z ${typeText}</h5>
        <table class="table">
            <tr>
                <td colspan="2">${ncofi.description}</td>
            </tr>
            <tr>
                <td>Proces</td>
                <td>${ncofi.process.code}</td>
            </tr>
        </table>
        <p class="message-red" id="message">Wiadomość</p>
        <form method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div>
                <label for="complete-date">Data zakończenia</label>
                <input type="date" id="complete-date" name="completeDate">
            </div>
            <div>
                <label for="successfully">Skuteczność działań</label>
                <select id="successfully" name="successfully">
                    <option>Proszę wybrać</option>
                    <option>Skuteczne</option>
                    <option>Nieskuteczne</option>
                </select>
            </div>
            <div>
                <label for="taken-actions">Podjęte działania</label> <br/>
                <textarea id="taken-actions" name="takenActions" cols="50" rows="10"></textarea>
            </div>
            <button class="btn-sm btn-primary inline-block" type="submit" id="btn-completed">Działania zakończone</button>
            <a class="nav-link inline-block" href="/audit/">Powrót</a>
        </form>
    </div>
</body>
</html>
