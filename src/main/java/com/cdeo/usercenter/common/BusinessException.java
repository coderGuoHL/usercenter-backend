package com.cdeo.usercenter.common;

/**
 * 封装公用业务异常类
 * 因为RuntimeException中已经有message 属性，这里不用定义自己
 */
public class BusinessException extends RuntimeException {
    private String code;

    private String description;

    public BusinessException(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getMessage(), errorCode.getCode(), errorCode.getDescription());
    }

    public BusinessException(String message, String code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode, String description) {
        this(errorCode.getMessage(), errorCode.getCode(), description);
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
