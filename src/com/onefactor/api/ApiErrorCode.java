package com.onefactor.api;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 20:53
 */
public enum ApiErrorCode {
    NOT_FOUND(101),
    INVALID_ARGUMENTS(102),
    NO_ERROR(0);

    private int code;

    private ApiErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
