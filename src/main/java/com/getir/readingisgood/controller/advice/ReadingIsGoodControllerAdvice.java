package com.getir.readingisgood.controller.advice;

import com.getir.readingisgood.data.domain.response.ErrorResponse;
import com.getir.readingisgood.exception.BusinessFault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.net.BindException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ReadingIsGoodControllerAdvice {

    @ExceptionHandler(BusinessFault.class)
    public ResponseEntity<ErrorResponse> handleBusinessFault(BusinessFault bf) {
        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(new Date());
        response.setCode(bf.getCode());
        response.setMessage(bf.getMessage());
        response.setStatus(bf.getStatus());
        return new ResponseEntity<>(response,bf.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class, BindException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(ConstraintViolationException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setTimeStamp(new Date());
        response.setMessage(ex.getMessage());
        response.setCode("CONSTRAINT_VIOLATION_FAULT");
        response.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidArgumentEx(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errorMap.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
    }


}
