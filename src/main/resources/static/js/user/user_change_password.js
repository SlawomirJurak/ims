let contextPath;

$(document).ready(function () {
    contextPath = $('meta[name="context-path"]').attr('content');
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });
    $('#btn-change-password').click(function () {
        changePassword();
    });
    $('#btn-landing-page').click(function () {
        toLandingPage();
    });
    $('#btn-landing-page').hide();
});

function changePassword() {
    let newPassword = {
        newPassword1: $('#new-password1').val(),
        newPassword2: $('#new-password2').val()
    }

    $.ajax({
        url: contextPath + '/user/setPassword/' + $('#btn-change-password').data('userId'),
        data: JSON.stringify(newPassword),
        contentType: 'application/json',
        method: 'POST'
    }).done(function (result) {
        if (result === 'OK') {
            $('#btn-change-password').hide();
            $('#btn-landing-page').show();
            showDialog('Hasło zostało zmienione');
        } else {
            showDialog(result);
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showDialog('Wystąpił błąd podczas zmiany hasła');
        console.log(jqXHR);
        console.log(textStatus);
        console.log(errorThrown);
    });
}

function toLandingPage() {
    window.location.href = contextPath+'/'
}
