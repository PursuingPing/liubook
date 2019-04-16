import com.classbooking.web.dao.CourseDao;
import com.classbooking.web.dao.TeacherDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class CourseDaoTest{

    @Autowired
    private CourseDao courseDao  ;

    @Autowired
    private TeacherDao teacherDao;

    @Test
    public void testType(){
        System.out.println(courseDao.getAllTypes().toString());
    }

    @Test
    public void testName(){
        System.out.println(teacherDao.getAllTeachersName().toString());
    }
}
