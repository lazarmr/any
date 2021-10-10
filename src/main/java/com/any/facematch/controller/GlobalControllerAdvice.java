package com.any.facematch.controller;

import com.any.facematch.exceptions.PersonNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalControllerAdvice {

    @ExceptionHandler({PersonNotFoundException.class})
    public Object handlePersonNotFoundException(Exception ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
