import com.classbooking.web.dao.BookDao;
import com.classbooking.web.domain.BookInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
}
