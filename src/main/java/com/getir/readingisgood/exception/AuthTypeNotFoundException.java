package com.getir.readingisgood.exception;

import org.springframework.http.HttpStatus;

public class AuthTypeNotFoundException extends BusinessFault{
    private static final String CODE = "AUTH_TYPE_NOT_FOUND";
    private static final String FAULT_DESC = "Given Authorization type not found in database. Ask administrator to create new Authorization type.";

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
        return HttpStatus.NOT_FOUND;
    }
}
