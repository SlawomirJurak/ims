const editRow = createEditRow();
const removeRow = createRemoveRow();
const changePasswordRow = createChangePasswordRow();

$(document).ready(function () {
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
    tableUsers.on('click', '.btn-remove', function () {
        initRemoveUser($(this));
    });
    tableUsers.on('click', '#row-btn-remove', function () {
        removeUser($(this));
    })
    tableUsers.on('click', '#row-btn-dont-remove', function () {
        hideRemoveRow();
    })
    tableUsers.on('click', '.btn-change-password', function () {
        initChangePassword($(this));
    })
    tableUsers.on('click', '#row-btn-change-password', function () {
        changePassword($(this));
    })
    tableUsers.on('click', '#row-btn-dont-change-password', function () {
        hideChangePasswordRow();
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
    $('.btn-remove').prop('disabled', disabled);
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
    if (!prefix) {
        if (!$('#user-password').val()) {
            showDialog('Proszę wpisać hasło.');
            return false
        }
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
            let userId = findUserId(button);

            newUser['id'] = userId;
        }
        let saveUrl = edit ? '/user/redit' : '/user/radd';
        $.ajax({
            url: saveUrl,
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
            console.log('Wystąpił błąd podczas rejestracji nowego użytkownika');
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        });
    }
}

function initRemoveUser(button) {
    let currentRow = button.closest('tr');

    currentRow.after(removeRow);
    setButtonsDisabled(true);
}

function removeUser(button) {
    let userId = findUserId(button);

    $.ajax({
        url: '/user/rremove/' + userId,
        method: 'DELETE'
    }).done(function (result) {
        button.closest('tr').prev().remove();
        hideRemoveRow();
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log('Wystąpił błąd podczas rejestracji nowego użytkownika');
        console.log(jqXHR);
        console.log(textStatus);
        console.log(errorThrown);
    });
}

function initChangePassword(button) {
    let currentRow = button.closest('tr');

    currentRow.after(changePasswordRow);
    setButtonsDisabled(true);
}

function changePassword(button) {
    let pass1 = $('#pass1').val();
    let pass2 = $('#pass2').val();
    let id = findUserId(button);

    if (pass1 !== pass2) {
        showDialog('Wpisane hasła nie są takie same');
    } else {
        $.ajax({
            url: '/user/changePassword/'+id+'/'+pass1,
            method: 'POST'
        }).done( function (result) {
            hideChangePasswordRow();
            setButtonsDisabled(false);
        })
    }
}

function hideChangePasswordRow() {
    changePasswordRow.remove();
    setButtonsDisabled(false);
}

function validateEmail(email) {
    let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

function editUser(button) {
    let currentRow = button.closest('tr');

    $.ajax({
        url: '/user/rget/' + button.data('id'),
        method: 'GET'
    }).done(function (result) {
        editRow.find('#row-user-name').val(result.userName);
        editRow.find('#row-user-first-name').val(result.firstName);
        editRow.find('#row-user-last-name').val(result.lastName);
        editRow.find('#row-user-password').val(result.password);
        editRow.find('#row-user-enabled').prop('checked', result.enabled);
        editRow.find('#row-user-email').val(result.email);
        editRow.find('#row-user-type').val(result.administrator ? 'y' : 'n');
        currentRow.after(editRow);
        setButtonsDisabled(true);
    });
}

function hideEditRow() {
    clearNewUserForm(true);
    editRow.remove();
    setButtonsDisabled(false);
}

function hideRemoveRow() {
    removeRow.remove();
    setButtonsDisabled(false);
}

function clearNewUserForm(edit) {
    let prefix = edit ? 'row-' : '';

    $('#' + prefix + 'user-name').val('');
    $('#' + prefix + 'user-first-name').val('');
    $('#' + prefix + 'user-last-name').val('');
    $('#' + prefix + 'user-password').val('');
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
                        <button class="btn-sm btn-primary btn-edit" data-id="${user.id}">Edycja</button>
                        <button class="btn-sm btn-danger btn-remove" data-id="${user.id}">Usuń</button>
                    </td>
                </tr>`);
    return row;
}

function getUserList() {
    let userList = [];

    $.ajax({
        url: '/user/rall',
        method: 'GET'
    }).done(function (result) {
        for (let i = 0; i < result.length; i++) {
            userList.push(result[i]);
        }
        showUserList(userList);
    }).fail(function () {
        console.log('Wystąpił błąd');
    });
}

function findUserId(button) {
    let row = button.closest('tr');
    row = row.prev();
    return row.find('.btn-edit').data('id');
}

function createEditRow() {
    let row = $('<tr>');
    let cell = $('<td colspan="7"></td>')
    cell.html(`<div id="row-form-new-user">
            <div>
                <div class="inline-block">
                    <label for="row-user-name">Nazwa</label><br/>
                    <input id="row-user-name" type="text" maxlength="60">
                </div>
                <div class="inline-block">
                    <label for="row-user-first-name">Imię</label> <br/>
                    <input id="row-user-first-name" type="text" maxlength="150">
                </div>
                <div class="inline-block">
                    <label for="row-user-last-name">Nazwisko</label> <br/>
                    <input id="row-user-last-name" type="text" maxlength="150">
                </div>
            </div>
            <div>
                <div class="inline-block">
                    <label for="row-user-email">E-mail</label> <br/>
                    <input id="row-user-email" type="email" maxlength="150">
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
                    <input id="row-user-enabled" type="checkbox">
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

function createRemoveRow() {
    let row = createEmptyRow();

    row.find('td').html(`<div id="row-remove-user">
                <p class="inline-block">Czy usunąć wybranego użytkownika?</p>
                <button id="row-btn-remove" class="btn-sm btn-danger">Tak</button>
                <button id="row-btn-dont-remove" class="btn-sm btn-light">Anuluj</button>
               </div>`);
    return row;
}

function createChangePasswordRow() {
    let row = createEmptyRow();

    row.find('td').html(`<div class="row-change-password">
                        <input id="pass1" type="password">
                        <input id="pass2" type="password">
                        <button id="row-btn-change-password" class="btn-sm btn-primary">Zapisz hasło</button>
                        <button id="row-btn-dont-change-password" class="btn-sm btn-light">Anuluj</button>
                    </div>`)
    return row;
}

function createEmptyRow() {
    let row = $('<tr>');
    let cell = $('<td colspan="7"></td>')

    row.append(cell);
    return row;
}

function showDialog(communicate) {
    $('.modal-body').text(communicate);
    $('#modalDialog').modal('show');
}