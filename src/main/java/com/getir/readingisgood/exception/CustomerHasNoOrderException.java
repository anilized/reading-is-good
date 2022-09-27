package com.getir.readingisgood.exception;

import org.springframework.http.HttpStatus;

public class CustomerHasNoOrderException extends BusinessFault {

    private static final String CODE = "BF_CUST_NO_ORDER_CREATED";
    private static final String FAULT_DESC = "There is no order for the given customer.";

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
