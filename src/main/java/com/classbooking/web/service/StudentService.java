package com.classbooking.web.service;

import com.classbooking.web.domain.Student;

import java.util.List;

public interface StudentService {

    Student getStudentInfo(String email);

    boolean modifyInfo(Student student);

    boolean addStudent(String email);

    List<Student> getAllStudent();

    boolean deleteStudent(String email);
}
