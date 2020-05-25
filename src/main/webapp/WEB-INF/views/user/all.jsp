<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <%@include file="../../jspf/head.jspf"%>
    <link href="<c:url value="/css/user.css"/>" rel="stylesheet">
    <script src="<c:url value="/js/functions.js"/>"></script>
    <script src="<c:url value="/js/user/user_all.js"/>"></script>
</head>
<body>
<%@include file="../template/modalDialog.jspf"%>
<jsp:include page="../template/header.jsp">
    <jsp:param name="additionalTitle" value="Użytkownicy"/>
</jsp:include>
<div class="content">
    <jsp:include page="../template/menu.jsp">
        <jsp:param name="moduleUser" value="yes"/>
    </jsp:include>
    <div class="data-view column">
        <div class="item-header">
            <button id="btn-new-user" class="btn-sm btn-primary">Nowy użytkownik</button>
        </div>
        <div id="form-new-user" style="display: none">
            <p id="communicate" class="message-red font-weight-bold"></p>
            <div>
                <div class="inline-block">
                    <label class="field-label" for="user-name">Nazwa</label><br/>
                    <input id="user-name" type="text" maxlength="60">
                </div>
                <div class="inline-block">
                    <label class="field-label" for="user-first-name">Imię</label> <br/>
                    <input id="user-first-name" type="text" maxlength="150">
                </div>
                <div class="inline-block">
                    <label class="field-label" for="user-last-name">Nazwisko</label> <br/>
                    <input id="user-last-name" type="text" maxlength="150">
                </div>
            </div>
            <div>
                <div class="inline-block">
                    <label class="field-label" for="user-password">Hasło</label> <br/>
                    <input id="user-password" type="password" maxlength="150">
                </div>
                <div class="inline-block">
                    <label class="field-label" for="user-email">E-mail</label> <br/>
                    <input id="user-email" type="email" maxlength="150">
                </div>
                <div class="inline-block">
                    <label class="field-label" for="user-type">Typ</label> <br/>
                    <select id="user-type">
                        <option value="">Proszę wybrać</option>
                        <option value="y">Administrator</option>
                        <option value="n">Użytkownik</option>
                    </select>
                </div>
                <div class="inline-block">
                    <input id="user-enabled" type="checkbox">
                    <label for="user-enabled">Aktywny</label>
                </div>
            </div>
            <div>
                <button id="btn-save" class="btn-sm btn-primary">Zapisz</button>
                <button id="btn-cancel" class="btn-sm btn-light">Anuluj</button>
            </div>
        </div>
        <table id="table-users" class="table font10pt">
            <thead class="thead-light">
            <tr>
                <th>#</th>
                <th>Nazwa</th>
                <th>Nazwisko Imie</th>
                <th>Aktywny</th>
                <th>E-mail</th>
                <th>Administrator</th>
                <th>Akcje</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user" varStatus="loop">
                <tr>
                    <td class="align-middle">${loop.index + 1}</td>
                    <td class="align-middle column-name user-name">${user.userName}</td>
                    <td class="align-middle column-full-name user-full-name">${user.fullName}</td>
                    <td class="align-middle text-center user-enabled">
                        <c:choose>
                            <c:when test="${user.enabled}">
                                <input type="checkbox" disabled="disabled" checked>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" disabled="disabled">
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="align-middle column-email">${user.email}</td>
                    <td class="align-middle text-center">
                        <c:choose>
                            <c:when test="${user.administrator}">
                                <input type="checkbox" disabled="disabled" checked>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" disabled="disabled">
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="align-middle">
                        <button class="btn-sm btn-primary btn-edit" data-id="${user.id}">Edycja</button>
                        <button class="btn-sm btn-danger btn-remove" data-id="${user.id}">Usuń</button>
                        <button class="btn-sm btn-dark btn-change-password" data-id="${user.id}">Zmiana hasła</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
