$(document).ready(function () {
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });

    $('.file-to-upload').change(function (event) {
        setFileName($(this))
    });
    $('.btn-upload').click(function () {
        uploadFile($(this));
    });
    $('.btn-show').click(function () {
        showFile($(this));
    });
    $('.btn-remove').click(function () {
        initRemoveProcess($(this));
    });
    $('#btn-confirm-remove').click(function () {
        removeProcess($(this).data('processId'));
    });
});

function setFileName(fileInput) {
    let fileBtn = fileInput.siblings('.file-name');
    let fileName = fileInput[0].files[0].name;

    if (fileName.length > 20) {
        fileName = fileName.substr(0, 8) + "..." + fileName.slice(-8);
    }
    fileBtn.text(fileName);
    fileInput.siblings('.btn-upload').show();
}

function uploadFile(buttonUpload) {
    let formData = new FormData();
    let file = buttonUpload.siblings('.file-to-upload')[0].files[0];
    let buttonShow = buttonUpload.siblings('.btn-show');

    formData.append('documentFile', file); // nazwa parametru musi być taka sama jak parametru w funkcji w kontrolerze
    formData.append('processId', buttonUpload.data('id'));
    $.ajax({
        url: '/documents/radd',
        method: 'POST',
        async: true,
        enctype: 'multipart/form-data',
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        timeout: 60000
    }).done(function (result) {
        if (result === 'OK') {
            buttonUpload.hide();
            buttonUpload.siblings('.file-name').text('Podmień');
            buttonShow.show();
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

function showFile(buttonShow) {
    $.ajax({
        url: '/documents/rcheckExists/' + buttonShow.data('id'),
        method: 'POST'
    }).done(function (result) {
        if (result == true) {
            window.location.href = '/documents/rget/' + buttonShow.data('id');
        } else {
            showDialog('Błąd podczas pobierania pliku');
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log('Wystąpił błąd podczas rejestracji nowego użytkownika');
        console.log(jqXHR);
        console.log(textStatus);
        console.log(errorThrown);
    });
}

function initRemoveProcess(buttonRemove) {
    let processId = buttonRemove.closest('tr').data('processId');
    $('#btn-confirm-remove').data('processId', processId);
    showConfirmRemoveDialog('Czy usunąć wybrany proces?');
}

function removeProcess(processId) {
    hideConfirmRemoveDialog();
    let row = $('tr[data-process-id="'+processId+'"]');

    row.remove();
    $('.data-row').each(function (index) {
        $(this).find('.row-no').text(index+1);
    });
}