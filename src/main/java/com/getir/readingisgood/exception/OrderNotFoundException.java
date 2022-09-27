package com.getir.readingisgood.exception;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BusinessFault {

    private static final String CODE = "BF_ORDER_NOT_FOUND";
    private static final String FAULT_DESC = "Requested order cannot be founded.";

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
