package com.classbooking.web.dao;


import com.classbooking.web.domain.BookInfo;
import com.classbooking.web.domain.CommentInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao {

//    @Select("select student_name, comment_time, comment_star, comments from booking_info where class_id = #{classId}")
//    @Results({
//            @Result(column = "student_name", property = "studentName"),
//            @Result(column = "comment_time", property = "commentTime"),
//            @Result(column = "comment_star", property = "commentStar"),
//            @Result(column = "comments", property = "comments")
//    })
//    List<BookInfo> getComment(@Param("classId") Integer classId);

    @Select("select b.student_email , s.student_name, b.book_time from booking_info b left join student_info s " +
            " on b.student_email = s.student_email where class_id = #{classId}")
    @Results({
            @Result(column = "student_email", property = "studentEmail"),
            @Result(column = "student_name", property = "studentName"),
            @Result(column = "book_time", property = "bookTime")
    })
    List<BookInfo> getBookInfo(@Param("classId") Integer classId);


    @Select("select b.student_email , s.student_name, b.comment_time , b.comment_star , b.comments from booking_info b left join student_info s " +
            " on b.student_email = s.student_email where class_id = #{classId}")
    @Results({
            @Result(column = "student_email", property = "studentEmail"),
            @Result(column = "student_name", property = "studentName"),
            @Result(column = "comment_time", property = "commentTime"),
            @Result(column = "comment_star", property = "commentStar"),
            @Result(column = "comments", property = "comments")
    })
    List<BookInfo> getComments(@Param("classId") Integer classId);

    @Insert("insert into booking_info(student_email,teacher_email,class_id,book_time) values(#{studentEmail},#{teacherEmail},#{classId},#{bookTime}) ")
    Integer addBook(BookInfo bookInfo);

    @Update("update booking_info set comments=#{comments},comment_time = #{commentTime},comment_star=#{commentStar} where id =#{bookId}")
    Integer addComment(@Param("bookId") Integer bookId,
                       @Param("comments") String comments,
                       @Param("commentTime") String commentTime,
                       @Param("commentStar") Integer commentStar);


    @Select("select id from booking_info where student_email=#{studentEmail} and teacher_email=#{teacherEmail} and class_id=#{classId}")
    List<Integer> checkRepeat(BookInfo bookInfo);

    @Select("select b.id , c.* ,t.teacher_name from booking_info b left join class_info c on b.class_id = c.class_id " +
            " left join teacher_info t on c.teacher_email = t.teacher_email" +
            " where b.student_email = #{studentEmail}")
    @Results({
            @Result(column = "id",property = "bookId")
    })
    List<CommentInfo> getBookList(@Param("studentEmail") String studentEmail);

    @Delete("delete from booking_info where id=#{bookId}")
    Integer deleteBook(@Param("bookId") Integer bookId);

}
