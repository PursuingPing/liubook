package com.classbooking.web.serviceImp;

import com.classbooking.web.dao.CourseDao;
import com.classbooking.web.dao.TeacherDao;
import com.classbooking.web.domain.Course;
import com.classbooking.web.service.CourseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private TeacherDao teacherDao;

    private static Logger LOG = Logger.getLogger(CourseServiceImpl.class);

    @Override
    public List<Course> getAllCourse() {
        return courseDao.getAllCourses();
    }

    @Override
    public List<String> getTypes() {
        return courseDao.getAllTypes();
    }

    @Override
    public List<String> getAllTeachersName() {
        return teacherDao.getAllTeachersName();
    }

    @Override
    public boolean addCourse(Course course) {
        int r = courseDao.addCourse(course);
        LOG.info("增加成功");
        return r == 1;
    }

    @Override
    public boolean modifyCourse(Course course) {
        int r = courseDao.updateCourse(course);
        LOG.info("修改课程成功");
        return r == 1;
    }

    @Override
    public boolean deleteCourse(Integer classId) {
        int r = courseDao.deleteCourseById(classId);
        LOG.info("删除课程成功");
        return r == 1;
    }

}
