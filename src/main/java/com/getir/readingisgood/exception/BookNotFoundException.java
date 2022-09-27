package com.getir.readingisgood.exception;

import org.springframework.http.HttpStatus;

public class BookNotFoundException extends BusinessFault {

    private static final String CODE = "BF_BOOK_NOT_FOUND";
    private static final String FAULT_DESC = "Requested book cannot be founded.";

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
