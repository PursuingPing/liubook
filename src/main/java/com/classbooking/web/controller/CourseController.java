package com.classbooking.web.controller;

import com.classbooking.web.domain.LYPResult;
import com.classbooking.web.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return new LYPResult().setData(courseService.getAllCourse());
    }

    @RequestMapping(value="getTypes",method = RequestMethod.GET)
    @ResponseBody
    public LYPResult getMenu(){
        HashMap<String , List<String>> menuMap = new HashMap<>();
        menuMap.put("type",courseService.getTypes());
        menuMap.put("teacher",courseService.getAllTeachersName());
        return new LYPResult().setData(menuMap);
    }
}
