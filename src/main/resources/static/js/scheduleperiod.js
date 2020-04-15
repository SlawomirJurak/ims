$(document).ready(function() {
    $('.period-year').click(function (event) {
        $(this).siblings('.period-schedule').slideToggle(400);
    });
});