package com.onefactor.api;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 20:46
 */
public class ApiResponse {
    private Object response;
    private ApiResponseStatus status;

    public ApiResponse() {
        status = new ApiResponseStatus();
    }

    public ApiResponse(Object response) {
        this(response, new ApiResponseStatus());
    }

    public ApiResponse(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder("");

        for (ObjectError oe : bindingResult.getAllErrors()) {
            if (oe instanceof FieldError) {
                sb.append(((FieldError) oe).getField()).append(" ");
            }

            sb.append(oe.getDefaultMessage()).append(";\n ");
        }

        status = new ApiResponseStatus(ApiErrorCode.INVALID_ARGUMENTS, sb.toString());
    }

    public ApiResponse(Object response, ApiResponseStatus status) {
        this.response = response;
        this.status = status;
    }

    public ApiResponseStatus getStatus() {
        return status;
    }
}
