$(document).ready(function () {

    $("#content").summernote({
        placeholder: 'Start Writing Post...',
        height: 300
    });

    $(".fancybox").fancybox({
        openEffect: "none",
        closeEffect: "none"
    });

    $(".img-zoom").hover(function () {

        $(this).addClass('transition');
    }, function () {

        $(this).removeClass('transition');
    });

});

function toggleContent(which) {
    $("#" + which.toString()).toggle();
}