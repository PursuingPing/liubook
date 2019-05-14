package com.classbooking.web.service;

import com.classbooking.web.domain.Teacher;

import java.util.List;

public interface TeacherService {

    boolean modifyInfo(Teacher teacher);

    Teacher getTeacherInfo(String email);

    boolean addTeacher(String email);

    String getTEmailByName(String name);

    List<Teacher> getAllTeacher();

    boolean deleteTeacher(String email);
}
