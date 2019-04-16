package com.classbooking.web.dao;

import com.classbooking.web.domain.Course;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.classbooking.web.util.Constants.CLASSTABLE;
import static com.classbooking.web.util.Constants.TEACHERTABLE;

@Repository
public interface CourseDao {

    @Select("select * from " + CLASSTABLE )
    List<Course> selectAllClass();

    @Select("select distinct class_type from " + CLASSTABLE)
    List<String> getAllTypes();

}
