package com.classbooking.web.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {
    private Integer classId;
    private String className;
    private String classStartTime;
    private String classEndTime;
    private String classInfo;
    private String teacherEmail;
    private String classType;
    private String classImg;
    private Integer classNums;

    @Override
    public String toString() {
        return "Course{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", classStartTime='" + classStartTime + '\'' +
                ", classEndTime='" + classEndTime + '\'' +
                ", classInfo='" + classInfo + '\'' +
                ", teacherEmail='" + teacherEmail + '\'' +
                ", classType='" + classType + '\'' +
                ", classImg='" + classImg + '\'' +
                ", classNums=" + classNums +
                '}';
    }
}
