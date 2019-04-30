package com.classbooking.web.serviceImp;

import com.classbooking.web.dao.BookDao;
import com.classbooking.web.domain.BookInfo;
import com.classbooking.web.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;
    @Override
    public List<BookInfo> getComment(Integer classId) {
        return bookDao.getComment(classId);
    }

    @Override
    public List<BookInfo> getBookInfo(Integer classId) {
        return bookDao.getBookInfo(classId);
    }

    @Override
    public List<BookInfo> getComments(Integer classId) {
        return bookDao.getComments(classId);
    }
}
