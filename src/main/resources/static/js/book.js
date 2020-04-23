var MyUrl = "http://127.0.0.1:8081";

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
        var span = $(this).children("span")[0];
        var bookId = parseInt($("#book_id").val());

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
            })
        }
    });

});

function collect(button, collectedStatus) {
    var span = $(button).children("span")[0];
    var bookId = parseInt($("#book_id").val());
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
}

function unMarkAll() {

    var bList = ['collection', 'want', 'reading', 'have_read'];
    for (var i = 0; i <= 3; i++) {
        var span = $("button[name=" + bList[i] + "]").children("span")[0];
        console.log(span);
        if ($(span).hasClass("glyphicon-star")) {
            console.log("111");
            $(span).removeClass("glyphicon-star");
            $(span).addClass("glyphicon-star-empty");
        }
    }
}
