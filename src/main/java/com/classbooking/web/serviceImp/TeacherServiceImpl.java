package com.classbooking.web.serviceImp;

import com.classbooking.web.dao.TeacherDao;
import com.classbooking.web.domain.Teacher;
import com.classbooking.web.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;


    @Override
    public boolean modifyInfo(Teacher teacher) {
        return teacherDao.modifyInfo(teacher) == 1;
    }

    @Override
    public Teacher getTeacherInfo(String email) {
        return teacherDao.getTeacherInfo(email);
    }

    @Override
    public boolean addTeacher(String email) {
        return teacherDao.addTeacher(email) ==1;
    }

    @Override
    public String getTEmailByName(String name) {
        return teacherDao.getTeacherEmailByName(name);
    }


}
