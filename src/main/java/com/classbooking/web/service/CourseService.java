package com.classbooking.web.service;

import com.classbooking.web.domain.CountInfo;
import com.classbooking.web.domain.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {
    List<Course> getAllCourse();

    List<String> getTypes();

    List<String> getAllTeachersName();

    boolean addCourse(Course course);

    boolean modifyCourse(Course course);

    boolean deleteCourse(Integer classId);

    List<Course> getCourses(String teacherEmail);

    Course getCourseById(Integer classId);

    String getTeacherEmailByClassId(Integer classId);

    List<Course> getTimes(String className);

    List<Course> getCourseByMenu(Map<String,Object> param);

    List<Course> getCourseByName(String className);

    List<CountInfo> getCounts(String teacherEmail);

}
