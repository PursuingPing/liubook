package com.classbooking.web.domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class Course implements Serializable {
    private Integer classId;
    private String className;
    private String classScheular;
    private String classLength;
    private String classInfo;
    private Integer classPrice;
    private String teacherEmail;
    private String classType;
    private String classImg;
}
