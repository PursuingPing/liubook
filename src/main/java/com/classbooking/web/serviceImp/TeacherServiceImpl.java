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
}
