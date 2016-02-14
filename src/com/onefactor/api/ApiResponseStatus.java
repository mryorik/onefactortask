package com.onefactor.api;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 20:52
 */
public class ApiResponseStatus {
    private int code = ApiErrorCode.NO_ERROR.getCode();
    private String message = "Success";

    public ApiResponseStatus() {

    }

    public ApiResponseStatus(ApiErrorCode code, String message) {
        this.code = code.getCode();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
