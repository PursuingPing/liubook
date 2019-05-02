package com.classbooking.web.service;

import com.classbooking.web.domain.Student;

public interface StudentService {

    Student getStudentInfo(String email);

    boolean modifyInfo(Student student);

    boolean addStudent(String email);


}
