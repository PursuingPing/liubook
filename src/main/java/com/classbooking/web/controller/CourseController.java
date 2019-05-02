package com.classbooking.web.controller;

import com.classbooking.web.domain.BookInfo;
import com.classbooking.web.domain.CommentInfo;
import com.classbooking.web.domain.Course;
import com.classbooking.web.domain.LYPResult;
import com.classbooking.web.service.BookService;
import com.classbooking.web.service.CourseService;
import com.classbooking.web.util.MailUtil;
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
        Course course = new Course();
        course.setClassName(className);
        course.setClassStartTime(classStartTime);
        course.setClassEndTime(classEndTime);
        course.setClassInfo(classInfo);
        course.setClassType(classType);
        course.setClassImg(classImg);
        course.setClassNums(classNums);
        course.setClassId(classId);
        //3天前不能修改，判断，修改后发邮件

        if(bookService.checkTime(classId)){
            boolean flag = courseService.modifyCourse(course);
            List<BookInfo> bookInfos = bookService.getBookInfo(classId);
            if(!bookInfos.isEmpty()){
                Course c = courseService.getCourseById(classId);
                String notice = "您所预约的课程："+c.getClassName() +"开始时间为： "+c.getClassStartTime()+"已被修改，请登录LYP Booking Sys 查看！";
                bookInfos.forEach(bookInfo -> {
                    String email = bookInfo.getStudentEmail();
                    new Thread(new MailUtil(email,notice,"")).start();
                });

            }
            return flag ? new LYPResult().setSuccess(true) : new LYPResult().setMessage("修改课程失败，请检查所填信息是否有错");
        }else {
            return new LYPResult().setMessage("课程修改只能在开始时间3天前！");
        }

    }

    @PostMapping("/delete")
    @ResponseBody
    public LYPResult delete(Integer classId){

        // 3天前不能删除，判断，删除后发邮件
        boolean canDo = bookService.checkTime(classId);
        if(canDo){
            boolean flag = courseService.deleteCourse(classId);
            List<BookInfo> bookInfos = bookService.getBookInfo(classId);
            if(!bookInfos.isEmpty()){
                Course course = courseService.getCourseById(classId);
                String notice = "您所预约的课程："+course.getClassName() +"开始时间为： "+course.getClassStartTime()+"已被删除，请登录LYP Booking Sys 查看！";
                bookInfos.forEach(bookInfo -> {
                    String email = bookInfo.getStudentEmail();
                    new Thread(new MailUtil(email,notice,"")).start();
                });

            }
            return flag ? new LYPResult().setSuccess(true) : new LYPResult().setMessage("删除课程失败");
        }else{
            return new LYPResult().setMessage("课程删除只能在开始时间3天前！");
        }
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
        List<BookInfo> result = new ArrayList<>();
        list.forEach(bookInfo -> {
            if(bookInfo.getCommentTime()!=null && !bookInfo.getCommentTime().equals("")){
                result.add(bookInfo);
            }
        });
        return new LYPResult().setData(result);
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

    @PostMapping("/getBooks")
    @ResponseBody
    public LYPResult getBooks(String studentEmail){
        List<CommentInfo> books = bookService.getBooksByEmail(studentEmail);
//        return !books.isEmpty() ? new LYPResult().setData(books) : new LYPResult().setMessage("");
        return new LYPResult().setData(books);
    }

    @PostMapping("/comment")
    @ResponseBody
    public LYPResult comment(Integer bookId,Integer commentStar,String comments,Integer classId){
        //  课程开始后才能评论,评论过，不能再评论
        if(bookService.checkCommentTime(classId)){
            if(!bookService.HasComment(classId)){
                return bookService.comment(bookId,commentStar,comments) ? new LYPResult().setData(true) : new LYPResult().setMessage("评论失败啊");
            }else {
                return new LYPResult().setMessage("该课程已经评论过，不能重复评论!");
            }
        }else{
            return new LYPResult().setMessage("课程未开始，不能评论 Sorry");
        }

    }

    @PostMapping("/cancelBook")
    @ResponseBody
    public LYPResult cancelBook(Integer bookId,Integer classId){
        // 课程没开始才能取消预约
        if(!bookService.checkCommentTime(classId)){
            return bookService.cancelBook(bookId) ? new LYPResult().setData(true) : new LYPResult().setMessage("取消预约失败");
        }else {
            return new LYPResult().setMessage("课程已开始，不能取消预约！");
        }

    }
}
