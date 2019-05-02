package com.classbooking.web.serviceImp;

import com.classbooking.web.dao.StudentDao;
import com.classbooking.web.domain.Student;
import com.classbooking.web.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;


    @Override
    public Student getStudentInfo(String email) {
        return studentDao.getStudentInfo(email);
    }

    @Override
    public boolean modifyInfo(Student student) {
        return studentDao.modifyInfo(student) == 1;
    }

    @Override
    public boolean addStudent(String email) {
        return studentDao.addStudent(email) ==1;
    }
}
