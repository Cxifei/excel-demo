package com.cxf.easyexcel.dto;


/**
 * 通用状态码enum
 * @author cxf
 *
 */
public enum StatusCode {

    Success(0,"成功"),
    Fail(-1,"失败"),
    Invalid_Param(1001,"无效的参数"),
    WorkBook_Version_Invalid(1002,"无效的Excel文件版本"),
    System_Error(1003,"系统错误");

    private Integer code;
    private String msg;

    private StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}

