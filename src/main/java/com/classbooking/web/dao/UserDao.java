package com.classbooking.web.dao;

import com.classbooking.web.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import static com.classbooking.web.util.Constants.LONGINTABLE;

@Repository
public interface UserDao {

    @Select("select * from "+LONGINTABLE+" where email = #{email} and password = #{password}")
    User selectByLoginnameAndPassword(
            @Param("email") String email,
            @Param("password") String password);

}
