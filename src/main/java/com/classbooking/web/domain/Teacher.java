package com.classbooking.web.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Teacher implements Serializable {
    private Integer id;
    private String teacherEmail;
    private String teacherName;
    private String teacherSex;
    private String teacherMajor;
    private String teacherDegree;
    private String teacherPhone;
    private String teacherCollege;
}
