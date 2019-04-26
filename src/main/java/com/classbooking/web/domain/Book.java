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
public class Book implements Serializable {

    private Integer bookId;
    private String studentEmail;
    private String teacherEmail;
    private Integer classId;
    private String comments;
}
