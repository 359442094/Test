package com.test.common.exception;

public class ServiceException extends RuntimeException {

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String errorCode, String message) {
        super(errorCode+" "+message);
    }

}
