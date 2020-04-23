package com.learn.spring.cloud.core;

/**
 * @author bill
 * @version 1.0
 * @date 2020/4/22 18:03
 */
public class GlobalException extends RuntimeException{
    protected String state;
    protected String type;
    protected String message;

    public GlobalException(Throwable cause) {
        super(cause);
    }

    public GlobalException(String state, String message) {
        this.state = state;
        this.message = message;
    }

    public GlobalException(String message) {
        this.message = message;
    }

    public GlobalException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }

    public GlobalException(String state, String msg, String type) {
        this.state = state;
        this.type = type;
        this.message = msg;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
