package com.vic.demo.http;

import android.util.Log;

import com.vic.demo.bean.MovieInfo;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by malijie on 2017/2/6.
 */

public class RetrofitHttpRequest {
    private static final String TAG = RetrofitHttpRequest.class.getSimpleName();
    private static final Object sObject = new Object();
    private static RetrofitHttpRequest sInstance = null;
    private RetrofitService mRetrofitService = null;

    private RetrofitHttpRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLContainer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mRetrofitService = retrofit.create(RetrofitService.class);
    }

    public static RetrofitHttpRequest getInstance() {
        if (sInstance == null) {
            synchronized (sObject) {
                if (sInstance == null) {
                    sInstance = new RetrofitHttpRequest();
                }
            }
        }
        return sInstance;
    }

    public void getMovieInfo(int subject, String model, String appKey, String type, Subscriber<MovieInfo> subscriber){
        Observable<HttpResult<List<MovieInfo>>> call = mRetrofitService.getMovieInfo(subject,model,appKey,type);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<List<MovieInfo>>>() {
                    @Override
                    public void call(HttpResult<List<MovieInfo>> httpResult) {
                        if(httpResult.getError_code().equals("0")){
                            subscriber.onNext(httpResult.getResult().get(0));
                        }else{
                            subscriber.onError(new ApiException("api访问出现问题"));
                        }
                    }
                });
    }

}
