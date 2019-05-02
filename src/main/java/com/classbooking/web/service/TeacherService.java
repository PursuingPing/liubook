package com.classbooking.web.service;

import com.classbooking.web.domain.Teacher;

public interface TeacherService {

    boolean modifyInfo(Teacher teacher);

    Teacher getTeacherInfo(String email);

    boolean addTeacher(String email);
}
