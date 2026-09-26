package com.itheima.exception;

import com.itheima.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result handleException(Exception e){
        //记录异常信息
        log.error("发生未知异常", e);
        return Result.error("发生未知异常，请联系管理员!");
    }

    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e){
        log.error("发生唯一约束异常", e);
        String str = e.getCause().getMessage();
        String[] arr = str.split("'");
        System.out.println("arr[1] = " + arr[1]);
        return Result.error(arr[1] + " 已经存在了!");
    }

    /**
     * 业务异常处理
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result handleBizException(BizException e){
        log.error("发生业务异常", e);
        return Result.error(e.getMessage());
    }

    /**
     * 请求的路径不存在（如后端尚未实现的接口），按404处理，避免当成未知异常打印堆栈
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<Result> handleNoResourceFoundException(NoResourceFoundException e){
        log.warn("请求的资源不存在: {}", e.getResourcePath());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Result.error("请求的资源不存在"));
    }
}
