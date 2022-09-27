package com.getir.readingisgood.controller.advice;

import com.getir.readingisgood.data.domain.response.ErrorResponse;
import com.getir.readingisgood.exception.AuthNotSupported;
import com.getir.readingisgood.exception.BusinessFault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class GeneralControllerAdvice {

    @ExceptionHandler(BusinessFault.class)
    public ResponseEntity<ErrorResponse> handleBusinessFault(BusinessFault bf) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(bf.getCode());
        response.setMessage(bf.getMessage());
        response.setStatus(bf.getStatus());
        return new ResponseEntity<>(response,bf.getStatus());
    }

    /*@ExceptionHandler(AuthNotSupported.class)
    public ResponseEntity<ErrorResponse> handleAuthError(AuthNotSupported ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        response.setCode(ex.getCode());
        response.setStatus(ex.getStatus());
        return new ResponseEntity<>(response,ex.getStatus());
    }*/

}
