package com.reddit.POJO;

/**
 * Created by trevorBye on 10/11/16.
 */
public class AjaxResponse {
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AjaxResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public AjaxResponse() {
    }
}
