package com.classbooking.web.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;			// id
    private String username;	// 用户名
    private String loginname;	// 登录名
    private String password;	// 密码
    private Integer userstatus;		// 状态
    private String email;

    private Date createDate;	// 建档日期
    // 无参数构造器
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", loginname="
                + loginname + ", password=" + password + ", userstatus=" + userstatus
                + ", createDate=" + createDate + "]";
    }


}
