package com.classbooking.web.dao;

import com.classbooking.web.domain.Teacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.classbooking.web.util.Constants.TEACHERTABLE;

@Repository
public interface TeacherDao {

    @Select("select distinct teacher_name from " + TEACHERTABLE )
    List<String> getAllTeachersName();

    @Select("select * from teacher_info where teacher_email=#{teacherEmail}")
    @Results({
            @Result(column = "id",property = "teacherId"),
            @Result(column = "teacher_email",property = "teacherEmail"),
            @Result(column = "teacher_name",property = "teacherName"),
            @Result(column = "teacher_sex",property = "teacherSex"),
            @Result(column = "teacher_major",property = "teacherMajor"),
            @Result(column = "teacher_degree",property = "teacherDegree"),
            @Result(column = "teacher_phone",property = "teacherPhone"),
            @Result(column = "teacher_college",property = "teacherCollege")
    })
    Teacher getTeacherInfo(@Param("teacherEmail") String teacherEmail);

    @Update("update teacher_info set teacher_name=#{teacherName},teacher_sex=#{teacherSex} , teacher_major=#{teacherMajor}, teacher_degree=#{teacherDegree}, teacher_phone=#{teacherPhone}," +
            " teacher_college=#{teacherCollege} where teacher_email=#{teacherEmail}")
    int modifyInfo(Teacher teacher);

}
