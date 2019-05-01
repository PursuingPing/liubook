package com.classbooking.web.controller;

import com.classbooking.web.domain.BookInfo;
import com.classbooking.web.domain.Course;
import com.classbooking.web.domain.LYPResult;
import com.classbooking.web.service.BookService;
import com.classbooking.web.service.CourseService;
import com.classbooking.web.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private BookService bookService;

    @RequestMapping(value ="getAll",method = RequestMethod.GET)
    @ResponseBody
    public LYPResult getAll(){
        List<Course> courses = courseService.getAllCourse();
        return !courses.isEmpty() ? new LYPResult().setData(courses) : new LYPResult().setMessage("获取课程列表失败");
    }

    @RequestMapping(value ="getCourses",method = RequestMethod.POST)
    @ResponseBody
    public LYPResult getCourses(String teacherEmail){
        List<Course> courses = courseService.getCourses(teacherEmail);
        return !courses.isEmpty() ? new LYPResult().setData(courses) : new LYPResult().setMessage("获取课程列表失败");
    }

    @RequestMapping(value ="getCourseInfo",method = RequestMethod.POST)
    @ResponseBody
    public LYPResult getCourses(Integer classId){
        Course  courseInfo = courseService.getCourseById(classId);
        return courseInfo !=null ? new LYPResult().setData(courseInfo) : new LYPResult().setMessage("获取课程信息失败");
    }

    @RequestMapping(value="getTypes",method = RequestMethod.GET)
    @ResponseBody
    public LYPResult getTypes(){
        HashMap<String , List<String>> menuMap = new HashMap<>();
        menuMap.put("type",courseService.getTypes());
        menuMap.put("teacher",courseService.getAllTeachersName());
        return new LYPResult().setData(menuMap);
    }

    @RequestMapping(value="getCourseTypes",method = RequestMethod.GET)
    @ResponseBody
    public LYPResult getCourseTypes(){
        List<Map<String,String>> list = new ArrayList<>();
        List<String> types = courseService.getTypes();
        types.stream().forEach(type->{
            Map<String,String> map = new HashMap<>();
            map.put("value",type);
            list.add(map);
        });
        return new LYPResult().setData(list);
    }

    @PostMapping(value="addCourse")
    @ResponseBody
    public LYPResult addCourse(String className, String classStartTime,
                               String classEndTime, String classInfo, String teacherEmail,
                               String classType, String classImg, Integer classNums
    ) {
        Course course = new Course();
        course.setClassName(className);
        course.setTeacherEmail(teacherEmail);
        course.setClassStartTime(classStartTime);
        course.setClassEndTime(classEndTime);
        course.setClassInfo(classInfo);
        course.setClassType(classType);
        course.setClassImg(classImg);
        course.setClassNums(classNums);
        boolean flag = courseService.addCourse(course);
        return flag ? new LYPResult().setSuccess(true) : new LYPResult().setMessage("添加失败，请检查所填信息是否有错");
    }

    @PostMapping(value="addCycleCourse")
    @ResponseBody
    public LYPResult addCycleCourse(String className, String[] range, String[] times,
                                    String[] units, String classInfo, String teacherEmail,
                                    String classType, String classImg, Integer classNums){

        Course course = new Course();
        course.setClassName(className);
        course.setTeacherEmail(teacherEmail);
        course.setClassInfo(classInfo);
        course.setClassType(classType);
        course.setClassImg(classImg);
        course.setClassNums(classNums);
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

    @PostMapping("/modify")
    @ResponseBody
    public LYPResult modify(String className, String classStartTime,Integer classId,
                            String classEndTime, String classInfo,
                            String classType, String classImg, Integer classNums){
        //TODO 3天前不能修改，判断
        Course course = new Course();
        course.setClassName(className);
        course.setClassStartTime(classStartTime);
        course.setClassEndTime(classEndTime);
        course.setClassInfo(classInfo);
        course.setClassType(classType);
        course.setClassImg(classImg);
        course.setClassNums(classNums);
        course.setClassId(classId);

        boolean flag = courseService.modifyCourse(course);
        return flag ? new LYPResult().setSuccess(true) : new LYPResult().setMessage("修改课程失败，请检查所填信息是否有错");
    }

    @PostMapping("/delete")
    @ResponseBody
    public LYPResult delete(Integer classId){

        //TODO 3天前不能删除，判断
        boolean flag = courseService.deleteCourse(classId);
        return flag ? new LYPResult().setSuccess(true) : new LYPResult().setMessage("删除课程失败");

    }

    @PostMapping("/getBookInfo")
    @ResponseBody
    public LYPResult getBookInfo(Integer classId){

        List<BookInfo> list = bookService.getBookInfo(classId);

        return new LYPResult().setData(list);
    }

    @PostMapping("/getComments")
    @ResponseBody
    public LYPResult getComments(Integer classId){

        List<BookInfo> list = bookService.getComments(classId);

        return new LYPResult().setData(list);
    }


    @PostMapping("/getCommentsByName")
    @ResponseBody
    public LYPResult getCommentsByName(String className){

        List<BookInfo> list = bookService.getCommentsByName(className);

        return new LYPResult().setData(list);
    }


    @PostMapping("/getTeacherEmail")
    @ResponseBody
    public LYPResult getTeacherEmail(Integer classId){
        String teacherEmail = courseService.getTeacherEmailByClassId(classId);

        return teacherEmail!=null&& !teacherEmail.equals("") ? new LYPResult().setData(teacherEmail) : new LYPResult().setMessage("获取email出错");
    }

    @PostMapping("/getTimes")
    @ResponseBody
    public LYPResult getTimes(String className){

        List<String> times = new LinkedList<>();
        List<Course> courses = courseService.getTimes(className);
        courses.stream().forEach(course -> {
            String temp = course.getClassStartTime() +"至" + course.getClassEndTime();
            times.add(temp);
        });
        return new LYPResult().setData(times);
    }

    @PostMapping("/book")
    @ResponseBody
    public LYPResult book(String[] classStartTimes,String className,String studentEmail,String teacherEmail){
        BookInfo bookInfo = new BookInfo();
        bookInfo.setStudentEmail(studentEmail);
        bookInfo.setTeacherEmail(teacherEmail);
        for(int i = 0 ;i<classStartTimes.length;i++){
            String[] sande = classStartTimes[i].split("至");
            if(!bookService.addBook(bookInfo,sande[0],className)){
                return new LYPResult().setMessage("您已预约该时间段，请去预约列表查看");
            }
        }
        return new LYPResult().setSuccess(true);
    }


}
