import com.classbooking.web.dao.TeacherDao;
import com.classbooking.web.domain.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class TeacherDaoTest {

    @Autowired
    private TeacherDao teacherDao;

    @Test
    public void testGetTeacherInfo(){
        List<Teacher> list = teacherDao.getTeacherInfo("123@qq.com");
        list.stream().forEach(teacher -> {
            System.out.println(teacher.toString());
        });
    }
}
