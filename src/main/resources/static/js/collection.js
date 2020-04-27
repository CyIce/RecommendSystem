var MyUrl = "http://127.0.0.1:8081";


$(document).ready(function () {

});

function delete_collection(obj) {
    var bookId = parseInt(obj.id);
    var collectedStatus = $("#collected-status").val();
    $.ajax({
        type: "POST",
        url: MyUrl + "/user/deletecollection",
        dataType: "text",
        data: JSON.stringify({"bookId": bookId, "collectedStatus": collectedStatus}),
        contentType: 'application/json; charset=UTF-8',
        success: function (data) {
            if (data === "1") {
                console.log("取消收藏成功");
                window.location.reload();
            } else if (data === "-1") {
                console.log("该书籍未被收藏");
            }
        }
    })

}