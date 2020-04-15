$(document).ready(function() {
    $('#btnApprove').click(function (event) {
        let txtApprovedBy = $('#approvedBy')
        if (!txtApprovedBy.val()) {
            alert('Proszę wpisać osobę zatwierdzającą.');
            event.preventDefault();
        }
    })
});