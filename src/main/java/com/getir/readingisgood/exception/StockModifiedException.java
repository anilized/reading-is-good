package com.getir.readingisgood.exception;

import org.springframework.http.HttpStatus;

public class StockModifiedException extends BusinessFault {

    private static final String CODE = "BF_STOCK_MODIFIED";
    private static final String FAULT_DESC = "Stock modified before request. Try again.";

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
