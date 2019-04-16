package com.classbooking.web.controller;

import com.classbooking.web.domain.LYPResult;
import com.classbooking.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="login",method= RequestMethod.POST)
    @ResponseBody
    public LYPResult login(String email,String password){
        return userService.login(email,password);
    }

    @RequestMapping(value = "signUp",method = RequestMethod.POST)
    @ResponseBody
    public LYPResult signUp(String email,String password){
        return null;
    }

}
