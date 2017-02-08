package com.vic.demo.http;

import com.vic.demo.bean.QuestionInfo;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by malijie on 2017/2/6.
 */

public interface RetrofitService {

    @POST("jztk/query")
    Observable<HttpResult<List<QuestionInfo>>> getMovieInfo(@Query("subject") int subject, @Query("model") String model, @Query("key") String appKey, @Query("testType") String type);

}
