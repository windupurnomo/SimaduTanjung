package com.windupurnomo.simadutanjung.entities;

/**
 * Created by Windu Purnomo on 7/9/14.
 */
public class CommonResponse {
    private Object data;
    private String message;

    public CommonResponse(Object data, String message) {
        this.data = data;
        this.message = message;
    }

    public CommonResponse() {

    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
