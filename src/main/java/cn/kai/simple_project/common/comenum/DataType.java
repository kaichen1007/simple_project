package cn.kai.simple_project.common.comenum;

/**
 * 数据类型
 * Author: chenKai
 * Date: 2022/12/31
 */
public enum DataType {
    STRING("STRING","字符串"),
    INT("INT","数字")
    ;

    DataType(String code, String message) {
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
