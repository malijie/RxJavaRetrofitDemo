package com.vic.demo.http;

import rx.functions.Func1;

/**
 * Created by malijie on 2017/2/6.
 */

public class HttpResultFunc<T> implements Func1<HttpResult<T>,T>{
    @Override
    public T call(HttpResult<T> httpResult) {
        if(!httpResult.getError_code().equals("0")){
            try {
                throw new ApiException(httpResult.getReason());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return httpResult.getResult();
    }
}
