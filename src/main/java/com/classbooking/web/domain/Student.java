package com.classbooking.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

    private Integer studentId;
    private String studentEmail;
    private String studentName;
    private String studentPhone;
    private String studentMajor;
    private String studentSex;
}
