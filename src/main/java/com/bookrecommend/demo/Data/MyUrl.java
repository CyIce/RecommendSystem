package com.bookrecommend.demo.Data;

public class MyUrl {


    public static String getLibraryUrl(String bookOrderType, int offset, int limit, int kindId, int labelId, String keyword) {
        return "/library?&book_order_type=" + bookOrderType + "&offset=" + offset + "&limit=" + limit + "&kind_id=" + kindId + "&label_id=" + labelId + "&keyword=" + keyword;
    }

    public static String getBookUrl(int bookId, String commentOrderType, int offset, int limit) {
        return "/book?book_id=" + bookId + "&comment_order_type=" + commentOrderType + "&offset=" + offset + "&limit=" + limit;
    }

    public static String getUserCollectionUrl(String status, String bookOrderType, int offser, int limit) {
        return "/user/collection?status=" + status + "&book_order_type=" + bookOrderType + "&offset=" + offser + "&limit=" + limit;
    }

    public static String getUserCommentUrl(String commentOrderType, int offser, int limit) {
        return "/user/comment?&comment_order_type=" + commentOrderType + "&offset=" + offser + "&limit=" + limit;
    }

    public static String getUserOrderUrl(int offser, int limit) {
        return "/user/userorder?offset=" + offser + "&limit=" + limit;
    }
}
