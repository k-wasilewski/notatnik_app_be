package com.gft;

public class BadRequestException extends Exception {
    public BadRequestException(String msg) {
        super(msg);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
