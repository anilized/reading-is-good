package com.getir.readingisgood.exception;
import org.springframework.http.HttpStatus;
public class AuthNotSupported extends BusinessFault {

    private static final String CODE = "AUTH_TYPE_NOT_SUPPORTED";
    private static final String FAULT_DESC = "Auth type not supported";

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
