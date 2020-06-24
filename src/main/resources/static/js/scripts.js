$(document).ready(function () {
    $(".profile-contents").hide();
});

function toggleContent(which){
    $("#" + which.toString()).toggle();
}