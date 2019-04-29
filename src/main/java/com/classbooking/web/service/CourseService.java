package com.classbooking.web.service;

import com.classbooking.web.domain.Course;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourse();

    List<String> getTypes();

    List<String> getAllTeachersName();

    boolean addCourse(Course course);
}
