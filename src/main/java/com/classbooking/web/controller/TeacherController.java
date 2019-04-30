package com.classbooking.web.controller;

import com.classbooking.web.domain.LYPResult;
import com.classbooking.web.domain.Teacher;
import com.classbooking.web.service.TeacherService;
import com.classbooking.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping("modifyInfo")
    @ResponseBody
    public LYPResult modifyInfo(
             String teacherEmail,
             String teacherName,
             String teacherSex,
             String teacherMajor,
             String teacherDegree,
             String teacherPhone,
             String teacherCollege){

        Teacher teacher = new Teacher();
        teacher.setTeacherEmail(teacherEmail);
        teacher.setTeacherName(teacherName);
        teacher.setTeacherSex(teacherSex);
        teacher.setTeacherMajor(teacherMajor);
        teacher.setTeacherDegree(teacherDegree);
        teacher.setTeacherPhone(teacherPhone);
        teacher.setTeacherCollege(teacherCollege);

        return teacherService.modifyInfo(teacher) ? new LYPResult().setSuccess(true) : new LYPResult().setMessage("修改信息错误");
    }
}
