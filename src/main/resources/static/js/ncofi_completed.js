$(document).ready(function () {
    $('#message').hide();
    $('#btn-completed').click(function (event) {
        if (!$('#complete-date').val()) {
            showMessage(event, 'Proszę wpisać datę zakończenia działań');
            return;
        }
        if ($('#successfully').val()==='Proszę wybrać') {
            showMessage(event, 'Proszę wybrać skuteczność działań');
            return;
        }
        if (!$('#taken-actions').val()) {
            showMessage(event, 'Proszę opisać podjęte działania');
            return;
        }
    });
});

function showMessage(event, messageText) {
    let message = $('#message');

    message.html(messageText);
    message.show();
    event.preventDefault();
}