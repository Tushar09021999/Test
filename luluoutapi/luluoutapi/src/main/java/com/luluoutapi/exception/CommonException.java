package com.luluoutapi.exception;

public class CommonException extends RuntimeException {

    String responseCode;
    String responseText;

    public CommonException(String code) {
        super(code);
    }

    public CommonException(String code, String msg) {
        this.responseCode = code;
        this.responseText = msg;
    }

}