package com.classbooking.web.domain;

import java.util.HashMap;

public class LYPResult extends HashMap<String,Object> {
    public LYPResult(boolean success, Object obj) {
        this.put("success", success);
        this.put("data", obj);
    }

    public LYPResult(boolean success, String message) {
        this.put("message", message);
        this.put("success", success);
    }

    public LYPResult() {
        this.put("message", "");
        this.put("success", false);
        this.put("code", 0);
    }

    public LYPResult(Throwable throwable) {
        this.put("message", throwable.getMessage());
        this.put("success", false);
        this.put("code", 0);
    }

    public LYPResult(String message) {
        this.put("message", message);
        this.put("success", false);
        this.put("code", 0);
    }

    public LYPResult setMessage(String msg) {
        this.setSuccess(false);
        this.put("message", msg);
        return this;
    }

    public LYPResult setErrCode(int code) {
        this.setSuccess(false);
        this.put("code", code);
        return this;
    }

    public LYPResult setSuccess(boolean ret) {
        this.put("success", ret);
        return this;
    }

    public Boolean succeed() {
        return (Boolean)this.get("success");
    }

    public LYPResult setData(Object obj) {
        this.put("data", obj);
        this.setSuccess(true);
        return this;
    }

    public Object getData() {
        return this.get("data");
    }

    public Boolean getSuccess() {
        return (Boolean)this.get("success");
    }
}
