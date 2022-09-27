package com.getir.readingisgood.exception;

import org.springframework.http.HttpStatus;

public class NoAvailableStockException extends BusinessFault {

    private static final String CODE = "BF_NO_AVAILABLE_STOCK";
    private static final String FAULT_DESC = "No available stock for given amount";

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
        return HttpStatus.BAD_REQUEST;
    }

}
