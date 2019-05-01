package com.classbooking.web.controller;

import com.classbooking.web.domain.LYPResult;
import com.classbooking.web.domain.Student;
import com.classbooking.web.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("getStudentInfo")
    @ResponseBody
    public LYPResult getStudentInfo(String studentEmail){

        Student student = studentService.getStudentInfo(studentEmail);
        return student !=null ? new LYPResult().setData(student) : new LYPResult().setMessage("获取学生信息出错");
    }

    @PostMapping("modifyInfo")
    @ResponseBody
    public LYPResult modifyInfo(String studentEmail,
                                String studentName,
                                String studentSex,
                                String studentMajor,
                                String studentPhone,
                                String studentCollege){


        Student student = new Student();
        student.setStudentPhone(studentPhone);
        student.setStudentEmail(studentEmail);
        student.setStudentMajor(studentMajor);
        student.setStudentSex(studentSex);
        student.setStudentCollege(studentCollege);
        student.setStudentName(studentName);

        return studentService.modifyInfo(student) ? new LYPResult().setSuccess(true) : new LYPResult().setMessage("修改信息错误");
    }
}
