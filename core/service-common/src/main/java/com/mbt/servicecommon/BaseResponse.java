package com.mbt.servicecommon;

import lombok.Getter;

@Getter
public class BaseResponse<T> {

    private final Boolean success;

    private final ErrorDetail error;

    private final T body;

    public static <T> BaseResponse<T> success(T body) {
        return new BaseResponse<>(body);
    }

    public static <T> BaseResponse<T> error(ErrorDetail error) {
        return new BaseResponse<>(error);
    }

    private BaseResponse(ErrorDetail error) {
        this.body = null;
        this.error = error;
        this.success = false;
    }

    public BaseResponse(T result) {
        this.body = result;
        this.error = null;
        this.success = true;
    }
}
