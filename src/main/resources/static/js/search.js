var MyUrl = "http://127.0.0.1:8081";

$(document).ready(function () {

    $("#submit").click(function () {

        console.log("ClickSearch");

        var keyword = $("#keyword").val();
        var labelId = $("#label-id").val();
        var kindId = $("#kind-id").val();
        var bookOrderType = $("#book-order-type").val();

        if (labelId === undefined) {
            labelId = -1;
        } else {
            labelId = parseInt(labelId);
        }
        if (kindId === undefined) {
            kindId = -1;
        } else {
            kindId = parseInt(kindId);
        }
        if (bookOrderType === undefined) {
            bookOrderType = "hot";
        }

        var u = MyUrl + "/library?&book_order_type=" + bookOrderType + "&offset=1&limit=10&kind_id=" + kindId + "&label_id=" + labelId + "&keyword=" + keyword;

        $.ajax({
            type: "GET",
            url: u,
            dataType: "text",
            success: function (data) {
                document.write(data);
                console.log("搜索成功");
            }
        });
    });
});

