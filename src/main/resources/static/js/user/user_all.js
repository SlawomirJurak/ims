const editRow = createEditRow();
let contextPath;

$(document).ready(function () {
    contextPath = $('meta[name="context-path"]').attr('content');
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });
    $('#btn-new-user').click(function () {
        showNewUserForm();
    });
    $('#btn-cancel').click(function () {
        hideNewUserForm();
    });
    $('#btn-save').click(function () {
        saveUser(false, -1);
    });
    let tableUsers = $('#table-users');
    tableUsers.on('click', '.btn-edit', function () {
        editUser($(this))
    });
    tableUsers.on('click', '#row-btn-cancel', function () {
        hideEditRow();
    })
    tableUsers.on('click', '#row-btn-save', function () {
        saveUser(true, $(this));
    })
});

function showNewUserForm() {
    $('#form-new-user').slideDown(400);
    $('#communicate').text('');
    setButtonsDisabled(true);
}

function hideNewUserForm() {
    clearNewUserForm(false);
    $('#form-new-user').slideUp(400);
    setButtonsDisabled(false);
}

function setUserButtonsDisabled(disabled) {
    $('.btn-edit').prop('disabled', disabled);
    $('.btn-change-password').prop('disabled', disabled);
}

function setButtonsDisabled(disabled) {
    $('#btn-new-user').prop('disabled', disabled);
    setUserButtonsDisabled(disabled);
}

function checkUserData(edit) {
    let prefix = edit ? 'row-' : '';

    if (!$('#' + prefix + 'user-name').val()) {
        showDialog('Proszę wpisać nazwę użytkownika');
        return false;
    }
    if (!$('#' + prefix + 'user-email').val()) {
        showDialog('Proszę wpisać e-mail użytkownika');
        return false;
    }
    if (!validateEmail($('#' + prefix + 'user-email').val())) {
        showDialog('Proszę wpisać poprawny e-mail użytkownika');
        return false;
    }
    if (!$('#' + prefix + 'user-type').val()) {
        showDialog('Proszę wybrać typ użytkownika')
        return false;
    }
    return true;
}

function saveUser(edit, button) {
    if (checkUserData(edit)) {
        let prefix = edit ? 'row-' : '';
        let nameField = $('#' + prefix + 'user-name');
        let firstNameField = $('#' + prefix + 'user-first-name');
        let lastNameField = $('#' + prefix + 'user-last-name');
        let passwordField = $('#' + prefix + 'user-password');
        let enabledField = $('#' + prefix + 'user-enabled');
        let emailField = $('#' + prefix + 'user-email')
        let typeField = $('#' + prefix + 'user-type');

        let newUser = {
            userName: nameField.val(),
            firstName: firstNameField.val(),
            lastName: lastNameField.val(),
            password: passwordField.val(),
            enabled: enabledField.prop('checked'),
            email: emailField.val(),
            administrator: typeField.val() === 'y'
        }
        if (edit) {
            newUser['id'] = findUserId(button);
            newUser['rowVersion'] = findUserRowVersion(button);
        }
        let saveUrl = edit ? '/user/redit' : '/user/radd';
        $.ajax({
            url: contextPath + saveUrl,
            data: JSON.stringify(newUser),
            contentType: 'application/json',
            method: 'POST'
        }).done(function (result) {
            if (result === 'OK') {
                hideNewUserForm();
                getUserList();
            } else {
                showDialog(result);
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            showDialog('Wystąpił błąd podczas zapisu danych użytkownika');
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        });
    }
}

function validateEmail(email) {
    let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

function editUser(button) {
    let currentRow = button.closest('tr');

    $.ajax({
        url: contextPath + '/user/rget/' + button.data('id'),
        method: 'GET'
    }).done(function (result) {
        editRow.find('#row-user-name').val(result.userName);
        editRow.find('#row-user-first-name').val(result.firstName);
        editRow.find('#row-user-last-name').val(result.lastName);
        editRow.find('#row-user-enabled').prop('checked', result.enabled);
        editRow.find('#row-user-email').val(result.email);
        editRow.find('#row-user-type').val(result.administrator ? 'y' : 'n');
        if (result.administrator && result.userName === $('#logged-user-name').text()) {
            editRow.find('#row-user-enabled').attr('disabled', true);
        }
        currentRow.after(editRow);
        setButtonsDisabled(true);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showDialog('Wystąpił błąd podczas zapisywania zmian');
        console.log(jqXHR);
        console.log(textStatus);
        console.log(errorThrown);
    });
}

function hideEditRow() {
    clearNewUserForm(true);
    editRow.remove();
    setButtonsDisabled(false);
}

function clearNewUserForm(edit) {
    let prefix = edit ? 'row-' : '';

    $('#' + prefix + 'user-name').val('');
    $('#' + prefix + 'user-first-name').val('');
    $('#' + prefix + 'user-last-name').val('');
    $('#' + prefix + 'user-enabled').prop('checked', false);
    $('#' + prefix + 'user-email').val('');
    $('#' + prefix + 'user-type').val('');
}

function showUserList(userList) {
    let tableBody = $('#table-users > tbody');

    tableBody.empty();
    for (let i = 0; i < userList.length; i++) {
        tableBody.append(createRow(i + 1, userList[i]));
    }
}

function createRow(rowNo, user) {
    let row = $(`<tr>
                    <td class="align-middle">${rowNo}</td>
                    <td class="align-middle column-name user-name">${user.userName}</td>
                    <td class="align-middle column-full-name user-full-name">${user.firstName + ' ' + user.lastName}</td>
                    <td class="align-middle text-center user-enabled">
                        <input type="checkbox" disabled="disabled" ${user.enabled ? 'checked' : ''}>
                    </td>
                    <td class="align-middle column-email">${user.email}</td>
                    <td class="align-middle text-center">
                        <input type="checkbox" disabled="disabled" ${user.administrator ? 'checked' : ''}>
                    </td>
                    <td class="align-middle">
                        <button class="btn-sm btn-primary btn-edit" data-id="${user.id}" data-row-version="${user.rowVersion}">Edycja</button>
                    </td>
                </tr>`);
    return row;
}

function getUserList() {
    let userList = [];

    $.ajax({
        url: contextPath + '/user/rall',
        method: 'GET'
    }).done(function (result) {
        for (let i = 0; i < result.length; i++) {
            userList.push(result[i]);
        }
        showUserList(userList);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showDialog('Nie udało pobrać się listy użytkowników');
        console.log(jqXHR);
        console.log(textStatus);
        console.log(errorThrown);
    });
}

function findUserId(button) {
    let row = button.closest('tr');
    row = row.prev();
    return row.find('.btn-edit').data('id');
}

function findUserRowVersion(button) {
    let row = button.closest('tr');
    row = row.prev();
    return row.find('.btn-edit').data('rowVersion');
}

function createEditRow() {
    let row = $('<tr>');
    let cell = $('<td colspan="7"></td>')
    cell.html(`<div id="row-form-new-user">
            <div>
                <div class="inline-block">
                    <label for="row-user-name">Nazwa</label><br/>
                    <input id="row-user-name" type="text" maxlength="60"/>
                </div>
                <div class="inline-block">
                    <label for="row-user-first-name">Imię</label> <br/>
                    <input id="row-user-first-name" type="text" maxlength="150"/>
                </div>
                <div class="inline-block">
                    <label for="row-user-last-name">Nazwisko</label> <br/>
                    <input id="row-user-last-name" type="text" maxlength="150"/>
                </div>
            </div>
            <div>
                <div class="inline-block">
                    <label for="row-user-email">E-mail</label> <br/>
                    <input id="row-user-email" type="email" maxlength="150"/>
                </div>
                <div class="inline-block">
                    <label for="row-user-type">Typ</label> <br/>
                    <select id="row-user-type">
                        <option value="">Proszę wybrać</option>
                        <option value="y">Administrator</option>
                        <option value="n">Użytkownik</option>
                    </select>
                </div>
                <div class="inline-block">
                    <input id="row-user-enabled" type="checkbox"/>
                    <label for="row-user-enabled">Aktywny</label>
                </div>
            </div>
            <div>
                <button id="row-btn-save" class="btn-sm btn-primary">Zapisz</button>
                <button id="row-btn-cancel" class="btn-sm btn-light">Anuluj</button>
            </div>
        </div>`);
    row.append(cell);
    return row;
}
