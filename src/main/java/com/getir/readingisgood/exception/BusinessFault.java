package com.getir.readingisgood.exception;

import org.springframework.http.HttpStatus;

public abstract class BusinessFault extends RuntimeException{
    public abstract String getCode();
    public HttpStatus getStatus() {return HttpStatus.INTERNAL_SERVER_ERROR;}

}
