package com.cdeo.usercenter.common;

import java.awt.image.BandedSampleModel;

public class ResultUtils {

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse("0", data, "success", "success");
    }

    public static <T> BaseResponse<T> fail(T data) {
        return new BaseResponse("1", data, "fail");
    }

    public static <T> BaseResponse<T> fail(String code, T data) {
        return new BaseResponse(code, data, "fail");
    }

    public static <T> BaseResponse<T> fail(String code, T data, String message) {
        return new BaseResponse(code, data, message);
    }


    public static <T> BaseResponse<T> fail(String code, T data, String message, String description) {
        return new BaseResponse(code, data, "fail", description);
    }

    public static  <T> BaseResponse<T> fail(ErrorCode errorCode, T data) {
        return new BaseResponse(errorCode, data);
    }

    public static  <T> BaseResponse<T> fail(ErrorCode errorCode) {
        return new BaseResponse(errorCode);
    }
}
