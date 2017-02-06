package com.vic.demo.http;

import android.util.Log;

import com.vic.demo.bean.MovieInfoResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import rx.Observable;
import rx.Scheduler;
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

    public void getMovieInfo(int subject,String model,String appKey,String type){
        Observable<MovieInfoResult> call = mRetrofitService.getMovieInfo(subject,model,appKey,type);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MovieInfoResult>() {
                    @Override
                    public void call(MovieInfoResult movieInfoResult) {
                        Log.d("MLJ","movieInfoResult Reason=" + movieInfoResult.getReason());
                    }
                });
//        call.enqueue(new Callback<MovieInfoResult>() {
//            @Override
//            public void onResponse(Call<MovieInfoResult> call, Response<MovieInfoResult> response) {
//                Log.d("MLJ","response===" + response.body());
//                for(int i=0;i<response.body().getResult().size();i++){
//                    Log.d("MLJ","bean===" + i + "===" +response.body().getResult().get(i).getQuestion() );
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieInfoResult> call, Throwable t) {
//                Log.d("MLJ","onFailure" );
//
//            }
//        });

    }

}
