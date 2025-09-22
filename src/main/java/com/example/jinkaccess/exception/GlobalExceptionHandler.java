package com.example.jinkaccess.exception;

import com.example.jinkaccess.util.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice = 全局异常处理 + 自动返回 JSON
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理参数错误异常
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> handleIllegalArgumentException(IllegalArgumentException e) {
        e.printStackTrace(); // 打印异常信息，方便调试
        return Result.error(400, "参数错误: " + e.getMessage());
    }

    // 处理运行时异常
    @ExceptionHandler(RuntimeException.class)
    public Result<String> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return Result.error(500, "运行时错误: " + e.getMessage());
    }

    // 处理所有 Exception 异常（兜底处理，优先级最低）
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        e.printStackTrace();
        return Result.error(500, "服务器错误: " + e.getMessage());
    }
}
