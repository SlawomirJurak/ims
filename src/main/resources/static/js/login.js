let contextPath;

$(document).ready(function () {
    contextPath = $('meta[name="context-path"]').attr('content');
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });
    $('#btn-login').click(function (event) {
        event.preventDefault();
        login();
    });
});


function login() {
    $.ajax({
        url: contextPath + '/login?username='+$('#username').val()+'&password='+$('#password').val(),
        method: 'POST'
    }).done(function (result) {
        if ('OK' === result) {
            window.location.href = contextPath + "/";
        } else {
            showDialog(result);
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showDialog('Wystąpił błąd podczas logowania');
        console.log(jqXHR);
        console.log(textStatus);
        console.log(errorThrown);
    });
}
