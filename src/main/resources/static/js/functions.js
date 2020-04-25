function showDialog(communicate) {
    $('.modal-body').text(communicate);
    $('#modalDialog').modal('show');
}

function showConfirmRemoveDialog(communicate) {
    $('#confirm-dialog-communicate').text(communicate);
    $('#confirm-dialog').modal('show');
}

function hideConfirmRemoveDialog() {
    $('#confirm-dialog').modal('hide');
}