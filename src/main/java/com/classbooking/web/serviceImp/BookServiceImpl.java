package com.classbooking.web.serviceImp;

import com.classbooking.web.dao.BookDao;
import com.classbooking.web.dao.CourseDao;
import com.classbooking.web.domain.BookInfo;
import com.classbooking.web.domain.CommentInfo;
import com.classbooking.web.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CourseDao courseDao;
//    @Override
//    public List<BookInfo> getComment(Integer classId) {
//        return bookDao.getComment(classId);
//    }

    @Override
    public List<BookInfo> getBookInfo(Integer classId) {
        return bookDao.getBookInfo(classId);
    }

    @Override
    public List<BookInfo> getComments(Integer classId) {
        return bookDao.getComments(classId);
    }

    @Override
    public List<BookInfo> getCommentsByName(String className) {
        List<Integer> classIds = courseDao.getClassIdsByName(className);
        List<BookInfo> result = new LinkedList<>();
        classIds.stream().forEach(id->{
            List<BookInfo> temp = bookDao.getComments(id);
            temp.stream().forEach(bookInfo -> {
                if(bookInfo.getCommentTime() !=null && !bookInfo.getCommentTime().equals("")){
                    result.add(bookInfo);
                }
            });
        });
        return result;
    }

    @Override
    public boolean addBook(BookInfo bookInfo,String classStartTime,String className) {
        Integer classId = courseDao.getClassIdByStartTime(classStartTime,className);
        bookInfo.setClassId(classId);
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        bookInfo.setBookTime(time);
        //避免重复预约
        if(!bookDao.checkRepeat(bookInfo).isEmpty()){
            return false;
        }
        return bookDao.addBook(bookInfo) ==1;
    }

    @Override
    public boolean comment(Integer bookId, Integer commentStar, String comments) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return bookDao.addComment(bookId,comments,time,commentStar) == 1;
    }

    @Override
    public boolean cancelBook(Integer bookId) {
        return bookDao.deleteBook(bookId) == 1;
    }

    @Override
    public boolean checkTime(Integer classId) {
        String startTime = courseDao.getClassStartTimeById(classId);
        LocalDateTime startDateTime = LocalDateTime.parse(startTime,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now,startDateTime);
        long durationSeconds = duration.toMillis();
        if(durationSeconds >= 259200000L ){
            //课程开始3天前不可取修改、删除
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean checkCommentTime(Integer classId) {
        String startTime = courseDao.getClassStartTimeById(classId);
        LocalDateTime startDateTime = LocalDateTime.parse(startTime,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now,startDateTime);
        if(duration.getSeconds()  < 0 ){
            //课程开始后才可评论
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<CommentInfo> getBooksByEmail(String studentEmail) {
        return bookDao.getBookList(studentEmail);
    }

    @Override
    public boolean HasComment(Integer bookId) {
        String cTime = bookDao.getCommentTime(bookId);
        return cTime != null && !cTime.equals("");
    }

}
