package cn.kai.simple_project.common.comenum;

/**
 * Author: chenKai
 * Date: 2023/2/21
 */
public enum LogEnum {
    SAVE("2021","SAVE"),
    SELECT("2021","SELECT"),
    UPDATE("2021","UPDATE"),
    DEL("2021","DEL"),
    UNKNOW("2021","UNKNOW");



    LogEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
