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
});

function setFileName(fileInput) {
    let fileBtn = fileInput.siblings('.file-name');
    let fileName = fileInput[0].files[0].name;

    if (fileName.length > 20) {
        fileName = fileName.substr(0, 8)+"..."+fileName.slice(-8);
    }
    fileBtn.text(fileName);
    fileInput.siblings('.btn-upload').show();
}

function uploadFile(buttonUpload) {
    let formData = new FormData();
    let file = buttonUpload.siblings('.file-to-upload')[0].files[0];

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
        buttonUpload.hide();
        buttonUpload.siblings('.file-name').text('Podmień');
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log('Wystąpił błąd podczas rejestracji nowego użytkownika');
        console.log(jqXHR);
        console.log(textStatus);
        console.log(errorThrown);
    });
}