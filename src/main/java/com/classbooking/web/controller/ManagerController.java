package com.classbooking.web.controller;

import com.classbooking.web.domain.*;
import com.classbooking.web.service.*;
import com.classbooking.web.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("manage")
public class ManagerController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/getAllCourse")
    @ResponseBody
    public LYPResult getAllCourse() {
        List<Course> courses = courseService.getAllCourse();
        return new LYPResult().setData(courses);
    }

    @GetMapping("/getAllStudent")
    @ResponseBody
    public LYPResult getAllStudent() {
        List<Student> students = studentService.getAllStudent();
        return new LYPResult().setData(students);
    }

    @GetMapping("/getAllTeacher")
    @ResponseBody
    public LYPResult getAllTeacher() {
        List<Teacher> teachers = teacherService.getAllTeacher();
        return new LYPResult().setData(teachers);
    }

    @PostMapping("/addStudent")
    @ResponseBody
    public LYPResult addStudent(String email, String password, String name, String sex, String major, String college, String phone) {

        User sUser = new User();
        sUser.setEmail(email);
        sUser.setPassword(password);
        sUser.setState(1);

        Student student = new Student();
        student.setStudentName(name);
        student.setStudentCollege(college);
        student.setStudentSex(sex);
        student.setStudentEmail(email);
        student.setStudentMajor(major);
        student.setStudentPhone(phone);

        if(userService.addUser(sUser) !=0 && studentService.addStudent(email) && studentService.modifyInfo(student)){
            return new LYPResult().setSuccess(true);
        }else {
            return new LYPResult().setMessage("增加学生失败");
        }

    }

    @PostMapping("/addTeacher")
    @ResponseBody
    public LYPResult addTeacher(String email, String password, String name, String sex, String major, String degree, String college, String phone) {

        User user = new User();
        user.setEmail(email);
        user.setState(1);
        user.setPassword(password);

        Teacher teacher = new Teacher();
        teacher.setTeacherEmail(email);
        teacher.setTeacherName(name);
        teacher.setTeacherSex(sex);
        teacher.setTeacherMajor(major);
        teacher.setTeacherDegree(degree);
        teacher.setTeacherPhone(phone);
        teacher.setTeacherCollege(college);

        if(userService.addUser(user) !=0 && teacherService.addTeacher(email) && teacherService.modifyInfo(teacher)){
            return new LYPResult().setSuccess(true);
        }else {
            return new LYPResult().setMessage("增加教师失败");
        }
    }

    @PostMapping("/modifyStudent")
    @ResponseBody
    public LYPResult modifyStudent(
            String studentEmail,
            String studentName,
            String studentSex,
            String studentMajor,
            String studentPhone,
            String studentCollege,
            String password) {
        userService.updatePassword(studentEmail,password);
        Student student = new Student();
        student.setStudentPhone(studentPhone);
        student.setStudentEmail(studentEmail);
        student.setStudentMajor(studentMajor);
        student.setStudentSex(studentSex);
        student.setStudentCollege(studentCollege);
        student.setStudentName(studentName);
        return studentService.modifyInfo(student) ? new LYPResult().setSuccess(true) : new LYPResult().setMessage("修改信息错误");
    }

    @PostMapping("/modifyTeacher")
    @ResponseBody
    public LYPResult modifyTeacher(
            String teacherEmail,
            String teacherName,
            String teacherSex,
            String teacherMajor,
            String teacherDegree,
            String teacherPhone,
            String teacherCollege,
            String password) {
        userService.updatePassword(teacherEmail,password);

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

    @PostMapping("/modifyCourse")
    @ResponseBody
    public LYPResult modifyCourse(String className, String classStartTime,Integer classId,
                            String classEndTime, String classInfo,
                            String classType, String classImg, Integer classNums,String teacherEmail){
        Course course = new Course();
        course.setClassName(className);
        course.setClassStartTime(classStartTime);
        course.setClassEndTime(classEndTime);
        course.setClassInfo(classInfo);
        course.setClassType(classType);
        course.setClassImg(classImg);
        course.setClassNums(classNums);
        course.setClassId(classId);
        course.setTeacherEmail(teacherEmail);

        List<BookInfo> bookInfos = bookService.getBookInfo(classId);
        boolean flag = courseService.modifyCourse(course);
        if(flag){
            if (!bookInfos.isEmpty()) {
                Course c = courseService.getCourseById(classId);
                String notice = "您所预约的课程：" + c.getClassName() + "开始时间为： " + c.getClassStartTime() + "已被修改，请登录LYP Booking  查看！";
                bookInfos.forEach(bookInfo -> {
                    String email = bookInfo.getStudentEmail();
                    new Thread(new MailUtil(email, notice, "")).start();
                });
            }
            return new LYPResult().setSuccess(true);
        }else{
            return new LYPResult().setMessage("修改课程失败，请检查所填信息是否有错");
        }

    }

    @PostMapping("/deleteStudent")
    @ResponseBody
    public LYPResult deleteStudent(String email){
        if(userService.deleteUser(email) ){
            return new LYPResult().setSuccess(true);
        }else {
            return new LYPResult().setMessage("删除学生失败");
        }
    }

    @PostMapping("/deleteTeacher")
    @ResponseBody
    public LYPResult deleteTeacher(String email){
        if(userService.deleteUser(email)){
            return new LYPResult().setSuccess(true);
        }else {
            return new LYPResult().setMessage("删除教师失败");
        }
    }

    @PostMapping("/deleteCourse")
    @ResponseBody
    public LYPResult deleteCourse(Integer classId){

        List<BookInfo> bookInfos = bookService.getBookInfo(classId);
        if(courseService.deleteCourse(classId)){
            if (!bookInfos.isEmpty()) {
                Course course = courseService.getCourseById(classId);
                String notice = "您所预约的课程：" + course.getClassName() + "--开始时间--： " + course.getClassStartTime() + "已被删除，请登录LYP Booking Sys 查看！";
                bookInfos.forEach(bookInfo -> {
                    String email = bookInfo.getStudentEmail();
                    new Thread(new MailUtil(email, notice, "")).start();
                });
            }
            return new LYPResult().setSuccess(true);
        }else {
            return new LYPResult().setMessage("删除课程失败");
        }

    }
}
