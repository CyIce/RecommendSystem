var MyUrl = "http://127.0.0.1:8081";
var bookId = parseInt($("#book_id").val());
var isLogin = $("#is_login").val();
var userScore = parseInt($("#user_score").val());

$(document).ready(function () {


    $("button[name='collection']").click(function () {
        collect(this, 0);
    });

    $("button[name='want']").click(function () {
        collect(this, 1);
    });

    $("button[name='reading']").click(function () {
        collect(this, 2);
    });

    $("button[name='have_read']").click(function () {
        collect(this, 3);
    });

    $("button[name='shoping_cart']").click(function () {

        if (isLogin === "true") {
            var span = $(this).children("span")[0];

            if ($(span).text() === "加入购物车") {
                $(span).text("已加入购物车");

                $.ajax({
                    type: "POST",
                    url: MyUrl + "/user/addshopingcart",
                    dataType: "text",
                    data: JSON.stringify({"bookId": bookId}),
                    contentType: 'application/json; charset=UTF-8',
                    success: function (data) {
                        if (data === "1") {
                            console.log("加入购物车成功");
                        } else if (data === "-1") {
                            console.log("该书籍已在购物车中");
                        }
                    }
                })

            } else if ($(span).text() === "已加入购物车") {
                $(span).text("加入购物车");
                $.ajax({
                    type: "POST",
                    url: MyUrl + "/user/deleteshopingcart",
                    dataType: "text",
                    data: JSON.stringify({"bookId": bookId}),
                    contentType: 'application/json; charset=UTF-8',
                    success: function (data) {
                        if (data === "1") {
                            console.log("删除成功");
                        } else if (data === "-1") {
                            console.log("该书籍不在购物车中");
                        }
                    }
                });
            }
        } else {
            confirm("请先登录");
        }

    });

    var star = $("a[name='star-hollow']");

    for (var i = 0; i < userScore / 2; i++) {
        $(star[i]).children("img").attr("src", MyUrl + "/img/star.png");
    }

    $(star).hover(function () {
        var i = parseInt(this.id);
        for (var j = 0; j <= i; j++) {
            $(star[j]).children("img").attr("src", MyUrl + "/img/star.png");
        }
        for (var j = i + 1; j <= 4; j++) {
            $(star[j]).children("img").attr("src", MyUrl + "/img/star_hollow.png");
        }
    });

    $("#evaluate").mouseout(function () {

        for (var i = 0; i < userScore / 2; i++) {
            $(star[i]).children("img").attr("src", MyUrl + "/img/star.png");
        }


        for (var i = 4; i >= userScore / 2; i--) {
            $(star[i]).children("img").attr("src", MyUrl + "/img/star_hollow.png");
        }
    });



});

function collect(button, collectedStatus) {
    if (isLogin === "true") {
        var span = $(button).children("span")[0];
        // 取消收藏
        if ($(span).hasClass("glyphicon-star")) {
            $(span).removeClass("glyphicon-star");
            $(span).addClass("glyphicon-star-empty");


            $.ajax({
                type: "POST",
                url: MyUrl + "/user/deletecollection",
                dataType: "text",
                data: JSON.stringify({"bookId": bookId, "collectedStatus": collectedStatus}),
                contentType: 'application/json; charset=UTF-8',
                success: function (data) {
                    if (data === "1") {
                        console.log("取消收藏成功");
                    } else if (data === "-1") {
                        console.log("该书籍未被收藏");
                    }
                }
            })


        }
        // 加入收藏
        else {
            unMarkAll();
            $(span).removeClass("glyphicon-star-empty");
            $(span).addClass("glyphicon-star");

            $.ajax({
                type: "POST",
                url: MyUrl + "/user/addcollection",
                dataType: "text",
                data: JSON.stringify({"bookId": bookId, "collectedStatus": collectedStatus}),
                contentType: 'application/json; charset=UTF-8',
                success: function (data) {
                    if (data === "1") {
                        console.log("收藏成功");
                    } else if (data === "-1") {
                        console.log("该书籍已经收藏过了");
                    }
                }
            })
        }
    } else {
        confirm("请先登录");
    }
}

function unMarkAll() {

    var bList = ['collection', 'want', 'reading', 'have_read'];
    for (var i = 0; i <= 3; i++) {
        var span = $("button[name=" + bList[i] + "]").children("span")[0];
        if ($(span).hasClass("glyphicon-star")) {
            $(span).removeClass("glyphicon-star");
            $(span).addClass("glyphicon-star-empty");
        }
    }
}


function comment() {
    if (isLogin === "false") {
        confirm("请先登录再进行评论");
        return;
    }
    if (userScore === 0) {
        confirm("请先给书籍评分再进行评论");
        return;
    }
    var comment = $("textarea[name='comment_text']");

    if (comment.val() === "") {
        confirm("请输入评论内容");
        return;
    }

    $.ajax({
        type: "POST",
        url: MyUrl + "/user/addcomment",
        dataType: "text",
        data: JSON.stringify({"bookId": bookId, "comment": comment.val(), "score": userScore}),
        contentType: 'application/json; charset=UTF-8',
        success: function (data) {
            if (data === "1") {
                window.location.reload();
                console.log("评论成功");

            }
        }
    })
}


function pscore(obj) {
    if (isLogin === "true") {
        var score = parseInt(obj.id) * 2 + 2;
        if (score == userScore) {
            console.log("评分未更改");
            return;
        }
        userScore = score;
        $.ajax({
            type: "POST",
            url: MyUrl + "/user/addscore",
            dataType: "text",
            data: JSON.stringify({"bookId": bookId, "score": score}),
            contentType: 'application/json; charset=UTF-8',
            success: function (data) {
                if (data === "1") {
                    console.log("评分成功");
                }
            }
        });

    } else {
        confirm("请先登录");
    }
}