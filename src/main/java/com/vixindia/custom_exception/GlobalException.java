package com.vixindia.custom_exception;

import java.util.Map;

public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Integer status;
    private String message;
    private Object resultMap;


    public GlobalException(Integer status, String message, Object resultMap) {
        super();
        this.status = status;
        this.message = message;
        this.resultMap = resultMap;
    }


    public GlobalException() {
        super();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }
}