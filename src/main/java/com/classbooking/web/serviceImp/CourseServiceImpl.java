package com.classbooking.web.serviceImp;

import com.classbooking.web.dao.CourseDao;
import com.classbooking.web.dao.TeacherDao;
import com.classbooking.web.domain.Course;
import com.classbooking.web.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private TeacherDao teacherDao;
    @Override
    public List<Course> getAllCourse() {
        return courseDao.selectAllClass();
    }

    @Override
    public List<String> getTypes() {
        return courseDao.getAllTypes();
    }

    @Override
    public List<String> getAllTeachersName() {
        return teacherDao.getAllTeachersName();
    }

}
