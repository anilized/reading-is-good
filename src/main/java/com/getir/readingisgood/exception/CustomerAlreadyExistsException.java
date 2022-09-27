package com.getir.readingisgood.exception;

import org.springframework.http.HttpStatus;

public class CustomerAlreadyExistsException extends BusinessFault {

    private static final String CODE = "BF_CUSTOMER_EXISTS";
    private static final String FAULT_DESC = "Requested customer already exists.";

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
