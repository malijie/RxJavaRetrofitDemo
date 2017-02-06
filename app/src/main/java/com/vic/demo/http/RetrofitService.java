package com.vic.demo.http;

import com.vic.demo.bean.MovieInfoResult;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by malijie on 2017/2/6.
 */

public interface RetrofitService {

    @POST("jztk/query")
    Observable<MovieInfoResult> getMovieInfo(@Query("subject") int subject, @Query("model") String model, @Query("key") String appKey, @Query("testType") String type);

}
