package com.classbooking.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private Integer id;			// id
    private String password;	// 密码
    private Integer state;		// 状态
    private String email;
    private String code;

}
