package com.bookrecommend.demo.Data;

public class MyUrl {


    public static String getLibraryUrl(String bookOrderType, int offset, int limit, int kindId, int labelId) {
        return "/library?&book_order_type=" + bookOrderType + "&offset=" + offset + "&limit=" + limit + "&kind_id=" + kindId + "&label_id=" + labelId;
    }

    public static String getBookUrl(int bookId, String commentOrderType, int offset, int limit) {
        return "/book?book_id=" + bookId + "&comment_order_type=" + commentOrderType + "&offset=" + offset + "&limit=" + limit;
    }
}
