package com.luluoutapi.exception;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<Map<String, Object>> CommonException(CommonException ex) {
        Map<String, Object> resp = new TreeMap<>();
        resp.put("responseCode", ex.responseCode);
        resp.put("responseText", ex.responseText);
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

}
