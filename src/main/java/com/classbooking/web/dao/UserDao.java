package com.classbooking.web.dao;

import com.classbooking.web.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.classbooking.web.util.Constants.LONGINTABLE;

@Repository
public interface UserDao {

    @Select("select * from "+LONGINTABLE+" where email = #{email} and password = #{password} and state=1")
    User login(
            @Param("email") String email,
            @Param("password") String password);


    @Insert("insert into login_info(email,password,state,code) values(#{email},#{password},#{state},#{code})")
    int register(User user);

    @Select("select * from login_info where code=#{code}")
    User findByCode(@Param("code") String code);

    @Update("update login_info set email=#{email}, password=#{password},state=#{state},code=#{code} where id=#{id}")
    int updateUser(User user);

    @Select("select email from login_info where email=#{email}")
    String findByEmail(@Param("email") String email);

    @Update("update login_info set password=#{password} where email = #{email}")
    int updatePassword(@Param("email") String email,@Param("password") String password);

    @Select("select student_email from student_info")
    List<String> getAllSEmail();

    @Select("select teacher_email from teacher_info")
    List<String> getAllTEmail();

    @Select("select manager_email from manager_info")
    List<String> getAllMEmail();
}
