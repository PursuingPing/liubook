import com.classbooking.web.dao.StudentDao;
import com.classbooking.web.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class StudentDaoTest {

    @Autowired
    private StudentDao studentDao;

    @Test
    public void testGetStudentInfo(){

        Student student = studentDao.getStudentInfo("2385046818@qq.com");
        System.out.println(student.toString());

    }

    @Test
    public void testModifyInfo(){

        Student student = new Student();
        student.setStudentCollege("广东工业大学");
        student.setStudentName("lyp");
        student.setStudentSex("男");
        student.setStudentEmail("2385046818@qq.com");
        student.setStudentMajor("wangluogongcheng");
        student.setStudentPhone("111");

        System.out.println(studentDao.modifyInfo(student));
    }

    @Test
    public void testAddStudent(){
        String email = "999@qq.com";
        System.out.println(studentDao.addStudent(email));
    }

    @Test
    public void testDeleteStudent(){
        String email = "999@qq.com";
        System.out.println(studentDao.deleteStudent(email));
    }

    @Test
    public void testGetAllStudent(){
        List<Student> list = studentDao.getAllStudent();
        list.stream().forEach(s -> {
            System.out.println(s.toString());
        });
    }
}
