package com.vixindia.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

    @JsonProperty("message")
    private String message;


    @JsonProperty("result")
    private Object result;


    @JsonProperty("status")
    private Integer status;




    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", result=" + result +
                ", status=" + status +
                '}';
    }
}
