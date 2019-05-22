import com.classbooking.web.dao.CourseDao;
import com.classbooking.web.dao.TeacherDao;
import com.classbooking.web.domain.BookInfo;
import com.classbooking.web.domain.Course;
import com.classbooking.web.util.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    @Test
    public void testInsertCourse(){
        Course course = new Course();

        course.setClassName("test");
        course.setClassEndTime("2019-06-01 03:00:00");
        course.setClassStartTime("2019-06-01 03:00:00");
        course.setClassImg("1.jpg");
        course.setClassInfo("test");
        course.setClassType("编程开发");
        course.setTeacherEmail("123@qq.com");
        course.setClassNums(5);
        System.out.println(courseDao.addCourse(course));
    }

    @Test
    public void testTimeUtil(){
        String[] ranges = new String[]{"2019-05-01","2019-05-17"};
        String[] uints = new String[]{"THURSDAY","MONDAY"};
        String[] times = new String[]{"01:01:02","03:04:05"};

        List<String[]> list = TimeUtil.getTime(ranges,uints,times);
        list.stream().forEach(r->{
            System.out.println(r[0]+"---"+r[1]);
        });
    }

    @Test
    public void testSelectAll(){
        List<Course> list = courseDao.getAllCourses();
        list.stream().forEach(r->{
            System.out.println(r.toString());
        });
    }

    @Test
    public void testGetCourseByTEmail(){
        List<Course> list = courseDao.getCourseByTEmail("123@qq.com");
        list.stream().forEach(r->{
            System.out.println(r.toString());
        });
    }

    @Test
    public void testGetCourseById(){
        Course list = courseDao.getCourseById(2);
        System.out.println(list.toString());

    }

    @Test
    public void testUpdateCourse(){
        Course course = new Course();

        course.setClassName("testUpdate");
        course.setClassEndTime("2019-06-01 03:00:00");
        course.setClassStartTime("2019-06-01 03:00:00");
        course.setClassImg("1.jpg");
        course.setClassInfo("test");
        course.setClassType("编程开发");
        course.setTeacherEmail("123@qq.com");
        course.setClassNums(6666);
        course.setClassId(25);

        courseDao.updateCourse(course);
        testGetCourseById();
    }

    @Test
    public void testDeleteCourseById(){
        System.out.println(courseDao.deleteCourseById(25));
    }

    @Test
    public void testGetTeacherEmailByClassId(){
        System.out.println(courseDao.getTeacherEmailByClassId(3));
    }

    @Test
    public void testGetTimes(){
        List<Course> courses = courseDao.getCourseTimes("test");
        List<String> times = new LinkedList<>();
        courses.stream().forEach(course -> {
            String temp = course.getClassStartTime()+"-" + course.getClassEndTime();
            times.add(temp);
        });
        times.forEach(System.out::println);
    }

    @Test
    public void testGetCommentsByName(){
        List<Integer> list = courseDao.getClassIdsByName("test");
        list.forEach(System.out::println);
    }

    @Test
    public void testSelectWithParam(){
        Map<String,Object> param = new HashMap<>();
        param.put("type","生活兴趣");
        param.put("teacherEmail","2@qq.com");
        List<Course> list = courseDao.getClasses(param);
        list.stream().forEach(course -> {
            System.out.println(course.getClassType() +"--"+course.getTeacherEmail());
        });
    }

    @Test
    public void testHourCompare(){
        String time = "2019-01-02 01:00:03";
        LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalTime localTime = localDateTime.toLocalTime();
        int hour = localTime.getHour();
        System.out.println(hour);
        System.out.println(TimeUtil.getHour(time));
    }

    @Test
    public void testStringToHour(){
        String s = "09:00";
        LocalTime localTime = LocalTime.parse(s, DateTimeFormatter.ofPattern("HH:mm"));
        int hour = localTime.getHour();
        System.out.println(hour);
        System.out.println(TimeUtil.getHourByString(s));
    }

    @Test
    public void testGetCourseByName(){
        String className = "te";
        List<Course> list = courseDao.getCourseByName(className);
        list.forEach(course -> {
            System.out.println(course.toString());
        });
    }
}
