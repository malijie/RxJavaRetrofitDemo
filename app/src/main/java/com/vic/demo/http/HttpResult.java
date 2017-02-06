package com.vic.demo.http;

/**
 * Created by malijie on 2017/2/6.
 */

public class HttpResult<T> {
    public String reason;
    public String error_code;
    public T result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
