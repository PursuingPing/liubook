package com.classbooking.web.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.classbooking.web.util.Constants.TEACHERTABLE;

@Repository
public interface TeacherDao {

    @Select("select distinct teacher_name from " + TEACHERTABLE )
    List<String> getAllTeachersName();

}
