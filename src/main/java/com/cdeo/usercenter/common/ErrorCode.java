package com.cdeo.usercenter.common;

/**
 * 定义错误码的枚举类
 */
public enum ErrorCode {

    SUCCESS("0", "OK", "success"),
    PARAM_ERROR("40000", "请求参数错误", "请求参数错误"),
    NULL_PARAM_ERROR("40001", "请求参数为空", "请求参数为空"),
    NO_LOGIN("40100", "用户尚未登录", "用户尚未登录"),
    NO_AUTH("40100", "没有授权", "没有授权");


    private final String code;
    private final String message;
    private final String description;


    ErrorCode(String code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
