package com.vic.demo.http;

import com.vic.demo.bean.QuestionInfo;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
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

    public void getMovieInfo(int subject, String model, String appKey, String type, Subscriber<List<QuestionInfo>> subscriber){
        Observable observable = mRetrofitService.getMovieInfo(subject,model,appKey,type)
                                .map(new HttpResultFunc<List<QuestionInfo>>());
        toSubscribe(observable,subscriber);
    }

    private void toSubscribe(Observable observable,Subscriber subscriber){
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }

}
