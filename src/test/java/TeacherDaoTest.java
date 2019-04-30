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
        Teacher teacher = teacherDao.getTeacherInfo("123@qq.com");
        System.out.println(teacher.toString());
    }

    @Test
    public void modifyInfo(){

        Teacher teacher = new Teacher();
        teacher.setTeacherCollege("gdut");
        teacher.setTeacherPhone("13535035493");
        teacher.setTeacherDegree("博士");
        teacher.setTeacherMajor("网络工程");
        teacher.setTeacherSex("男");
        teacher.setTeacherName("pursuing");
        teacher.setTeacherEmail("123@qq.com");

        System.out.println(teacherDao.modifyInfo(teacher));

    }
}
