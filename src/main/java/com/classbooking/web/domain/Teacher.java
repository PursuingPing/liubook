package com.classbooking.web.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Teacher implements Serializable {
    private Integer teacherId;
    private String teacherEmail;
    private String teacherName;
    private String teacherSex;
    private String teacherMajor;
    private String teacherDegree;
    private String teacherPhone;
    private String teacherCollege;
    private String password;

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", teacherEmail='" + teacherEmail + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", teacherSex='" + teacherSex + '\'' +
                ", teacherMajor='" + teacherMajor + '\'' +
                ", teacherDegree='" + teacherDegree + '\'' +
                ", teacherPhone='" + teacherPhone + '\'' +
                ", teacherCollege='" + teacherCollege + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
