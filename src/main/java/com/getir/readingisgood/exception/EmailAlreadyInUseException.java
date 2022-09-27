package com.getir.readingisgood.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyInUseException extends BusinessFault {

    private static final String CODE = "BF_EMAIL_ALREADY_IN_USE";
    private static final String FAULT_DESC = "Given email already registered";

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getMessage() {
        return FAULT_DESC;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }


}
