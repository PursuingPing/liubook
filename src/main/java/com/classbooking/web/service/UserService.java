package com.classbooking.web.service;

import com.classbooking.web.domain.LYPResult;
import com.classbooking.web.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    User login(String email, String password, HttpServletRequest request);

    int  register(User user);

    User findByCode(String code);

    int update(User user);

    String findByEmail(String email);

}
