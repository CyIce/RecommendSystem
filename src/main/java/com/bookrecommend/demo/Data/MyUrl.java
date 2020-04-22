package com.bookrecommend.demo.Data;

public class MyUrl {


    public static String getLibraryUrl(String bookOrderType, int offset, int limit, int kindId, int labelId) {
        return "/library?&book_order_type=" + bookOrderType + "&offset=" + offset + "&limit=" + limit + "&kind_id=" + kindId + "&label_id=" + labelId;
    }

    public static String getBookUrl(int bookId, String commentOrderType, int offset, int limit) {
        return "/book?book_id=" + bookId + "&comment_order_type=" + commentOrderType + "&offset=" + offset + "&limit=" + limit;
    }

    public static String getUserCollectionUrl(String collectType, int userId, String bookOrderType, int offser, int limit) {
        return "/user/" + collectType + "?user_id=" + userId + "&book_order_type=" + bookOrderType + "&offset=" + offser + "&limit=" + limit;
    }

    public static String getUserUrl(int userId) {
        return "/user?user_id=" + userId;
    }

    public static String getUserCommentUrl(int userId, String commentOrderType, int offser, int limit) {
        return "/user/comment?user_id=" + userId + "&comment_order_type=" + commentOrderType + "&offset=" + offser + "&limit=" + limit;
    }
}
