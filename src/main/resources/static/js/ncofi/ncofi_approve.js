$(document).ready(function () {
    $('#btn-approve').click(function (event) {
        if(!$('#confirmedBy').val()) {
            alert('Proszę uzupełnić osobę potwierdzająca');
            event.preventDefault();
        }
    });
});