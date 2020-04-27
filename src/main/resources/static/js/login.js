var MyUrl = "http://127.0.0.1:8081";

$(document).ready(function () {


    $("#form").submit(function () {
        var p = $("input[name='password']");
        var password = hex_md5(p.val());
        p.val(password);

    });

});