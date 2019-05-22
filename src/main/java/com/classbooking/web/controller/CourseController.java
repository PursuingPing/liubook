package com.classbooking.web.controller;

import com.classbooking.web.domain.*;
import com.classbooking.web.service.BookService;
import com.classbooking.web.service.CourseService;
import com.classbooking.web.service.TeacherService;
import com.classbooking.web.util.MailUtil;
import com.classbooking.web.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private BookService bookService;

    @Autowired
    private TeacherService teacherService;

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
        return new LYPResult().setData(courses);
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
        Teacher teacher = teacherService.getTeacherInfo(teacherEmail);
        if(teacher !=null && (teacher.getTeacherEmail() == null || teacher.getTeacherEmail().equals(""))){
            return new LYPResult().setMessage("添加失败，该教师账号不存在");
        }else{
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

        String path = request.getSession().getServletContext().getRealPath("/upload");
        File filePath = new File(path);
        System.out.println("文件保存路径："+path);
        if(!filePath.exists() && !filePath.isDirectory()){
            System.out.println("目录不存在，创建目录"+filePath);
            filePath.mkdir();
        }

        //获取原始文件名称、格式
        String originalFileName = picture.getOriginalFilename();
        System.out.println("原始文件名称:"+originalFileName);

        //获取文件类型，以最后一个.为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
        System.out.println("文件类型: "+type);
        //获取文件名称
        String name = originalFileName.substring(0,originalFileName.lastIndexOf("."));

        //设置文件名称：当前时间+文件名称（不包含格式）
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateS = simpleDateFormat.format(date);
        String fileName = dateS + name +"."+type;
        System.out.println("新文件名称："+fileName);

        //在指定路径下创建一个文件
        File targetFile = new File(path,fileName);

        //将文件保存到服务器指定位置
        try{
            picture.transferTo(targetFile);
            System.out.println("上传成功");
            return new LYPResult().setData("/upload/"+fileName);
        } catch (IOException e) {
            System.out.println("上传失败");
            e.printStackTrace();
            return new LYPResult().setMessage("上传失败");
        }
    }

    @PostMapping("/modify")
    @ResponseBody
    public LYPResult modify(String className, String classStartTime,Integer classId,
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
        List<BookInfo> bookInfos = bookService.getBookInfo(classId);
        if(canDo){
            boolean flag = courseService.deleteCourse(classId);
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
        List<Course> results = courses.stream()
                .filter(course -> TimeUtil.getMillonSecond(course.getClassStartTime()) >= System.currentTimeMillis())
                .collect(Collectors.toList());
        results.stream().forEach(course -> {
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
            //判断是否人数已满
            if(!bookService.checkNums(sande[0],className)){
                return new LYPResult().setMessage("课程该时间段已预约满人，请选择其他时间段");
            }
            //判断是否重复预约
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

    @PostMapping("/getClasses")
    @ResponseBody
    public LYPResult getClasses(String type ,String teacherName,String timeRange){

        Map<String,Object> param = new HashMap<>();
        if(!type.equals("全部")){
            param.put("type",type);
        }
        if(!teacherName.equals("全部")){
            String tEmail= teacherService.getTEmailByName(teacherName);
            param.put("teacherEmail",tEmail);
        }
        List<Course> courses = courseService.getCourseByMenu(param);
        if(courses.isEmpty()){
            return new LYPResult().setData(courses);
        }else{
            if(!timeRange.equals("全部")){
                String[] range =  timeRange.split("-");
                int start = TimeUtil.getHourByString(range[0]);
                int end = TimeUtil.getHourByString(range[1]);
                List<Course> result = courses.stream()
                        .filter(course ->
                                TimeUtil.getHour(course.getClassStartTime()) >= start && TimeUtil.getHour(course.getClassEndTime()) <= end
                                        && TimeUtil.getHour(course.getClassStartTime()) <= end
                        ).collect(Collectors.toList());

                return new LYPResult().setData(result);
            }else {
                return new LYPResult().setData(courses);
            }
        }
    }

    @PostMapping("/getCourse")
    @ResponseBody
    public LYPResult getCourse(String className){
        return new LYPResult().setData(courseService.getCourseByName(className));
    }

    @PostMapping("/getCounts")
    @ResponseBody
    public LYPResult getCounts(String teacherEmail){
        return new LYPResult().setData(courseService.getCounts(teacherEmail));
    }
}
