package com.classbooking.web.dao;

import com.classbooking.web.domain.Course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.classbooking.web.util.Constants.CLASSTABLE;
import static com.classbooking.web.util.Constants.TEACHERTABLE;

@Repository
public interface CourseDao {

    @Select("select * from " + CLASSTABLE )
    @Results({
            @Result(column = "class_id",property = "classId"),
            @Result(column = "class_name",property = "className"),
            @Result(column = "class_startTime",property = "classStartTime"),
            @Result(column = "class_endTime",property = "classEndTime"),
            @Result(column = "teacher_email",property = "teacherEmail"),
            @Result(column = "class_type",property = "classType"),
            @Result(column = "class_img",property = "classImg"),
            @Result(column = "class_nums",property = "classNums"),
            @Result(column = "class_info",property = "classInfo")

    })
    List<Course> getAllCourses();

    @Select("select distinct class_type from " + CLASSTABLE)
    List<String> getAllTypes();

    @Insert("insert into class_info(class_name,class_startTime,class_endTime,class_info,teacher_email,class_type,class_img,class_nums) " +
            " values(#{className},#{classStartTime},#{classEndTime},#{classInfo},#{teacherEmail},#{classType},#{classImg},#{classNums})")
    int addCourse(Course course);

    @Update("update class_info set class_name=#{className},class_startTime=#{classStartTime},class_endTime=#{classEndTime}," +
            "class_info=#{classInfo} , class_type=#{classType}, class_img=#{classImg}, class_nums=#{classNums} " +
            " where class_id=#{classId}")
    int updateCourse(Course course);

    @Select("select * from class_info where teacher_email = #{teacherEmail}")
    @Results({
            @Result(column = "class_id",property = "classId"),
            @Result(column = "class_name",property = "className"),
            @Result(column = "class_startTime",property = "classStartTime"),
            @Result(column = "class_endTime",property = "classEndTime"),
            @Result(column = "teacher_email",property = "teacherEmail"),
            @Result(column = "class_type",property = "classType"),
            @Result(column = "class_img",property = "classImg"),
            @Result(column = "class_nums",property = "classNums"),
            @Result(column = "class_info",property = "classInfo")

    })
    List<Course> getCourseByTEmail(String teacherEmail);

    @Select("select * from class_info where class_id = #{classId}")
    @Results({
            @Result(column = "class_id",property = "classId"),
            @Result(column = "class_name",property = "className"),
            @Result(column = "class_startTime",property = "classStartTime"),
            @Result(column = "class_endTime",property = "classEndTime"),
            @Result(column = "teacher_email",property = "teacherEmail"),
            @Result(column = "class_type",property = "classType"),
            @Result(column = "class_img",property = "classImg"),
            @Result(column = "class_nums",property = "classNums"),
            @Result(column = "class_info",property = "classInfo")

    })
    Course getCourseById(@Param("classId") int classId);

    @Delete("delete from class_info where class_id=#{classId}")
    int deleteCourseById(@Param("classId") int classId);
}
