import com.classbooking.web.dao.BookDao;
import com.classbooking.web.domain.BookInfo;
import com.classbooking.web.domain.CommentInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    @Test
    public  void testGetComments(){
        List<BookInfo> list = bookDao.getComments(1);
        list.stream().forEach(bookInfo -> {
            System.out.println(bookInfo.toString());
        });
    }

    @Test
    public  void testGetBookInfo(){
        List<BookInfo> list = bookDao.getBookInfo(1);
        list.stream().forEach(bookInfo -> {
            System.out.println(bookInfo.toString());
        });
    }

    @Test
    public void testGetBookList(){
        List<CommentInfo> commentInfos = bookDao.getBookList("2385046818@qq.com");
        commentInfos.forEach(commentInfo -> {
            System.out.println(commentInfo.toString());
        });
    }

    @Test
    public void testAddComment(){
        BookInfo bookInfo = new BookInfo();
        bookInfo.setComments("testt");
        bookInfo.setCommentStar(3);
        bookInfo.setCommentTime("2019-09-09 09:09:09");
        Integer bookId = 17;

        System.out.println(bookDao.addComment(bookId,bookInfo.getComments(),bookInfo.getCommentTime(),bookInfo.getCommentStar()));
    }

    @Test
    public void testDeleteBook(){
        System.out.println(bookDao.deleteBook(4));
    }


    @Test
    public void testTime(){
        String startTime = "2019-04-09 04:01:01";
        LocalDateTime startDateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now,startDateTime);
        System.out.println(duration.toDays());
    }
}
