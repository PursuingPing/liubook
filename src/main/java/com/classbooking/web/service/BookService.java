package com.classbooking.web.service;

import com.classbooking.web.domain.BookInfo;

import java.util.List;

public interface BookService {

    //List<BookInfo> getComment(Integer classId);

    List<BookInfo> getBookInfo(Integer classId);

    List<BookInfo> getComments(Integer classId);

    List<BookInfo> getCommentsByName(String className);

    boolean addBook(BookInfo bookInfo,String classStartTime,String className);
}
