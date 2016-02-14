package com.onefactor.service;

import com.onefactor.api.ApiErrorCode;

/**
 * User: Yaroslav Frolikov
 * Date: 12.02.16 0:39
 */
public class ServiceException extends Exception {
    private ApiErrorCode errorCode;

    public ServiceException(String message, ApiErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiErrorCode getErrorCode() {
        return errorCode;
    }
}
