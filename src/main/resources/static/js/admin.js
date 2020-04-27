$(document).ready(function () {
    $(".MALL").hide();
    $(".MHover").mouseover(function (e) {
        $(this).next(".MALL").css({"position": "absolute", "top": e.pageY + 5, "left": e.pageX + 5}).show();
    });
    $(".MHover").mousemove(function (e) {
        $(this).next(".MALL").css({
            "color": "fff",
            "position": "absolute",
            "opacity": "0.7",
            "background-color": "666",
            "top": e.pageY + 5,
            "left": e.pageX + 5
        });
    });

    $(".MHover").mouseout(function () {
        $(this).next(".MALL").hide();
    });


});