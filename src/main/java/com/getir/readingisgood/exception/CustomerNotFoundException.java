package com.getir.readingisgood.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends BusinessFault {

    private static final String CODE = "BF_CUST_NOT_FOUND";
    private static final String FAULT_DESC = "Customer not found.";

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
