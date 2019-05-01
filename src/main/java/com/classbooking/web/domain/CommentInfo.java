package com.classbooking.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentInfo {

    private Integer classId;
    private String className;
    private String classStartTime;
    private String classEndTime;
    private String classType;
    private String classImg;
    private String teacherName;
    private Integer bookId;

    @Override
    public String toString() {
        return "CommentInfo{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", classStartTime='" + classStartTime + '\'' +
                ", classEndTime='" + classEndTime + '\'' +
                ", classType='" + classType + '\'' +
                ", classImg='" + classImg + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", bookId=" + bookId +
                '}';
    }
}
