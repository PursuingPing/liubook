package com.classbooking.web.service;

import com.classbooking.web.domain.LYPResult;
import com.classbooking.web.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    User login(String email, String password, HttpServletRequest request);

    int  register(User user);

    User findByCode(String code);

    int update(User user);

    String findByEmail(String email);

    boolean updatePassword(String email,String password);

    boolean isPasswordCorrect(String email,String password);

    List<String> getAllSEmail();

    List<String> getAllTEmail();

    List<String> getAllMEmail();

}
