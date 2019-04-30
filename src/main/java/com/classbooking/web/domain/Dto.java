package com.classbooking.web.domain;

public class Dto {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String token;

    private Long tokenCreatedTime;

    private Long tokenExpiryTime;

    public void setTokenCreatedTime(Long tokenCreatedTime) {
        this.tokenCreatedTime = tokenCreatedTime;
    }

    public Long getTokenExpiryTime() {
        return tokenExpiryTime;
    }

    public void setTokenExpiryTime(Long tokenExpiryTime) {
        this.tokenExpiryTime = tokenExpiryTime;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    private String isLogin;

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public Long getTokenCreatedTime(){
        return tokenCreatedTime;
    }

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
