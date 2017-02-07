package com.vic.demo.http;

import com.vic.demo.bean.MovieInfo;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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

    public void getMovieInfo(int subject, String model, String appKey, String type, Subscriber<List<MovieInfo>> subscriber){
        mRetrofitService.getMovieInfo(subject,model,appKey,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<List<MovieInfo>>())
                .subscribe(subscriber);


    }



}
