package com.itheima.exception;

public class BizException extends RuntimeException{
    public BizException() {
    }

    public BizException(String s) {
        super(s);
    }
}
