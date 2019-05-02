package com.classbooking.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.classbooking.web.domain.Dto;
import com.classbooking.web.domain.LYPResult;
import com.classbooking.web.domain.User;
import com.classbooking.web.service.StudentService;
import com.classbooking.web.service.UserService;
import com.classbooking.web.serviceImp.TokenServiceImpl;
import com.classbooking.web.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenServiceImpl tokenService;

    @RequestMapping(value="login",method= RequestMethod.POST)
    @ResponseBody
    public String login(String email, String password){
        User user = userService.login(email,password,request);
        Dto dto = new Dto();
        if(user!=null){
            String userAgent = request.getHeader("user-agent");
            String token = tokenService.generateToken(userAgent,email);
            user.setPassword("******");
            tokenService.saveToken(token,user);

            List<String> studentEmails = userService.getAllSEmail();
            List<String> teacherEmails = userService.getAllTEmail();

            if(studentEmails.contains(email)){
                dto.setRole("s");
            }else if(teacherEmails.contains(email)){
                dto.setRole("t");
            }else{
                dto.setRole("m");
            }

            dto.setEmail(email);
            dto.setIsLogin("true");
            dto.setTokenCreatedTime(System.currentTimeMillis());
            dto.setTokenExpiryTime(System.currentTimeMillis() + 2*60*60*1000);
            dto.setToken(token);
        }else{
            dto.setIsLogin("false");
        }
        return JSONObject.toJSONString(dto);
        //return user!=null ? new LYPResult().setData(user.getEmail()) : new LYPResult().setMessage("登录失败!请检查邮箱、密码是否填错，或账号未激活");
    }

    @RequestMapping(value = "signUp",method = RequestMethod.POST)
    @ResponseBody
    public LYPResult signUp(String email,String password){
        //检查邮箱是否已经注册过
        String emailInDb = userService.findByEmail(email);
        if(emailInDb != null && !emailInDb.equals("")){
            //已经注册过
            return new LYPResult().setMessage("该邮箱已注册");
        }
        User user = new User();
        user.setState(0);
        user.setEmail(email);
        user.setPassword(password);
        String code = CodeUtil.generateUniqueCode();
        user.setCode(code);
        int ack = userService.register(user);
        return ack==1 ? new LYPResult().setSuccess(true) : new LYPResult().setMessage("注册失败");
    }

    @PostMapping(value = "active")
    @ResponseBody
    public LYPResult active(String code){

        User user = userService.findByCode(code);
        if(user!=null){
            user.setState(1);
            user.setCode(null);
            int ack =userService.update(user);
            if(ack ==1){
                return studentService.addStudent(user.getEmail()) ? new LYPResult().setSuccess(true) : new LYPResult().setMessage("激活失败");
            }else {
                return new LYPResult().setMessage("激活失败");
            }
        }else{
            return new LYPResult().setMessage("激活失败");
        }
    }

    @PostMapping("modifyPwd")
    @ResponseBody
    public LYPResult modifyPwd(String email,String password,String oldPassword){
        if(userService.isPasswordCorrect(email,oldPassword)){
        return userService.updatePassword(email,password) ? new LYPResult().setSuccess(true) : new LYPResult().setMessage("修改密码失败");
        }else {
            return new LYPResult().setMessage("原密码错误");
        }
    }

}
