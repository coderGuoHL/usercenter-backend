package com.cdeo.usercenter.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandle(BusinessException ex) {
        log.debug("BusinessException: code={0},message={0}, getDescription={0}", ex.getCode(), ex.getMessage(), ex.getDescription());
        return ResultUtils.fail(ex.getCode(), null, ex.getMessage(), ex.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandle(RuntimeException ex) {
        log.info("ex", ex);
        return ResultUtils.fail("1", null, ex.getMessage(), "SYSTEM_ERROR");
    }
}
