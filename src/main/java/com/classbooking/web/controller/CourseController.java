package com.classbooking.web.controller;

import com.classbooking.web.domain.Course;
import com.classbooking.web.domain.LYPResult;
import com.classbooking.web.service.CourseService;
import com.classbooking.web.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping(value ="getAll",method = RequestMethod.GET)
    @ResponseBody
    public LYPResult getAll(){
        List<Course> courses = courseService.getAllCourse();
        return !courses.isEmpty() ? new LYPResult().setData(courseService.getAllCourse()) : new LYPResult().setMessage("获取课程列表失败");
    }

    @RequestMapping(value="getTypes",method = RequestMethod.GET)
    @ResponseBody
    public LYPResult getTypes(){
        HashMap<String , List<String>> menuMap = new HashMap<>();
        menuMap.put("type",courseService.getTypes());
        menuMap.put("teacher",courseService.getAllTeachersName());
        return new LYPResult().setData(menuMap);
    }

    @PostMapping(value="addCourse")
    @ResponseBody
    public LYPResult addCourse(String className, String classStartTime,
                               String classEndTime, String classInfo, String teacherEmail,
                               String classType, String classImg, Integer calssNums
    ) {
        Course course = new Course();
        course.setClassName(className);
        course.setTeacherEmail(teacherEmail);
        course.setClassStartTime(classStartTime);
        course.setClassEndTime(classEndTime);
        course.setClassInfo(classInfo);
        course.setClassType(classType);
        course.setClassImg(classImg);
        course.setClassNums(calssNums);
        boolean flag = courseService.addCourse(course);
        return flag ? new LYPResult().setSuccess(true) : new LYPResult().setMessage("添加失败，请检查所填信息是否有错");
    }

    @PostMapping(value="addCycleCourse")
    @ResponseBody
    public LYPResult addCycleCourse(String className, String[] range, String[] times,
                                    String[] units, String classInfo, String teacherEmail,
                                    String classType, String classImg, Integer calssNums){

        Course course = new Course();
        course.setClassName(className);
        course.setTeacherEmail(teacherEmail);
        course.setClassInfo(classInfo);
        course.setClassType(classType);
        course.setClassImg(classImg);
        course.setClassNums(calssNums);
        List<String[]> caculateTimes = TimeUtil.getTime(range,units,times);
        if (!caculateTimes.isEmpty()) {
            for (String[] r : caculateTimes) {
                course.setClassStartTime(r[0]);
                course.setClassEndTime(r[1]);
                boolean flag = courseService.addCourse(course);
                if (!flag) {
                    return new LYPResult().setMessage("添加失败，请检查所填信息是否有错");
                }
            }
            return new LYPResult().setSuccess(true);
        }
        return new LYPResult().setMessage("周期设置出错，请检查");
    }

    @RequestMapping("/upload")
    @ResponseBody
    public LYPResult upload(@RequestParam("file") MultipartFile picture, HttpServletRequest request){

        String path = request.getSession().getServletContext().getRealPath("/");
        path = path +"";


        return new LYPResult().setData(path);
    }
}
