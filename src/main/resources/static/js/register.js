var MyUrl = "http://127.0.0.1:8081";

$(document).ready(function () {

    console.log("启用js");

    $("#submit").click(function () {

        var name = $("input[name='name']");
        var email = $("input[name='email']");
        var password1 = $("input[name='password1']");
        var password2 = $("input[name='password2']");
        var gender = $("#male")[0];

        if (password1.val() === "") {
            confirm("请输入密码！");
        } else if (password1.val() !== password2.val()) {
            password1.val("");
            password2.val("");
            confirm("两次输入的密码不相同，请重新输入")
        } else {
            $.ajax({
                type: "GET",
                url: MyUrl + "/existemail?email=" + email.val(),
                dataType: "text",
                success: function (data) {
                    if (data === "true") {
                        console.log("用户名可以使用");
                        $.ajax({
                            type: "POST",
                            url: MyUrl + "/user/register",
                            data: JSON.stringify({
                                "name": name.val(),
                                "email": email.val(),
                                "password": password1.val(),
                                "gender": gender.checked
                            }),
                            dataType: "html",
                            contentType: 'application/json; charset=UTF-8',
                            success: function (message) {
                                console.log("注册成功");
                                document.write(message);
                            },
                            error: function (message) {
                                location.reload();
                                console.log("注册失败");
                            }
                        })


                    } else {
                        confirm("邮箱已被注册！");
                    }
                }

            })
        }

    })


});

