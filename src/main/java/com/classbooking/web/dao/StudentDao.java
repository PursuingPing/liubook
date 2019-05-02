package com.classbooking.web.dao;


import com.classbooking.web.domain.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao {

    @Select("select * from student_info where student_email=#{studentEmail}")
    @Results({
            @Result(column = "id",property = "studentId"),
            @Result(column = "student_email",property = "studentEmail"),
            @Result(column = "student_name",property = "studentName"),
            @Result(column = "student_sex",property = "studentSex"),
            @Result(column = "student_major",property = "studentMajor"),
            @Result(column = "student_phone",property = "studentPhone"),
            @Result(column = "student_college",property = "studentCollege")
    })
    Student getStudentInfo(@Param("studentEmail") String studentEmail);

    @Update("update student_info set student_name=#{studentName},student_sex=#{studentSex} , student_major=#{studentMajor},  student_phone=#{studentPhone}," +
            " student_college=#{studentCollege} where student_email=#{studentEmail}")
    int modifyInfo(Student student);

    @Insert("insert into student_info(student_email) values(#{studentEmail})")
    Integer addStudent(@Param("studentEmail") String studentEmail);

    @Delete("delete from student_info where student_email=#{studentEmail}")
    Integer deleteStudent(@Param("studentEmail") String studentEmail);
}
