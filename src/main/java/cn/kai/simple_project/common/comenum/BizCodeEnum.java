package cn.kai.simple_project.common.comenum;

import lombok.Data;

/**
 * Author: chenKai
 * Date: 2022/12/30
 */
public enum BizCodeEnum {

    SUCCESS(0,"调用成功"),
    FULL(500,"调用失败"),
    CREATE_USER(0,"CREATE_USER"),
    PAY(0,"PAY"),

    PARAM_ERROR(400,"参数错误!")
    ;

    BizCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
