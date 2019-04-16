package com.classbooking.web.service;

import com.classbooking.web.domain.LYPResult;

public interface UserService {

    LYPResult login(String email,String password);
    LYPResult signUp(String email,String passowrd);

}
