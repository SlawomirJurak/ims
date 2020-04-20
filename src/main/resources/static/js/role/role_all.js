$(document).ready( function () {
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });
    $('#new-role-form').hide();
    $('#btn-new-role').click(function () {
        showNewRoleForm();
    });
    $('#btn-cancel').click(function () {
        hideNewRoleForm();
    });
    $('#btn-save').click(function () {
        saveRole();
    });
});

function showNewRoleForm() {
    $('#new-role-form').show();
    $('#role-name').val('');
    $('#role-description').val('');
}

function hideNewRoleForm() {
    $('#new-role-form').hide();
}

function saveRole() {
    let roleName = $('#role-name');
    let roleDescription = $('#role-description');
    let newRole = {
      name: roleName.val(),
      description: roleDescription.val()
    };

    $.ajax({
        url: '/role/radd',
        data: JSON.stringify(newRole),
        contentType: "application/json",
        method: "POST"
    }).done(function (result) {
        getRoleList(result);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log('Wystąpił błąd podczas rejestracji nowej roli');
        console.log(jqXHR);
        console.log(textStatus);
        console.log(errorThrown);
    });
}

function getRoleList(result) {
    let roleList = [];

    $.ajax({
        url: '/role/rroleList',
        method: 'GET'
    }).done(function (result) {
        for (let i = 0; i < result.length; i++) {
            roleList.push(result[i]);
        }
        hideNewRoleForm();
        showRolesTable(roleList);
    }).fail(function () {
        console.log('Wystąpił błąd');
    });
}

function showRolesTable(roleList) {
    let tableBody = $('#table-roles > tbody');

    tableBody.empty();
    for (let i = 0; i < roleList.length; i++) {
        tableBody.append(createRow(i+1, roleList[i].name, roleList[i].description));
    }
}

function createRow(rowNo, roleName, roleDescription) {
    let row = $('<tr>');

    row.append(createCell(rowNo));
    row.append(createCell(roleName));
    row.append(createCell(roleDescription));
    return row;
}

function createCell(content) {
    return $('<td>').text(content);
}