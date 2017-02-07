package com.vic.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vic.demo.R;
import com.vic.demo.bean.MovieInfo;
import com.vic.demo.http.RetrofitHttpRequest;
import com.vic.demo.http.URLContainer;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Button mButton =null;
    private List<String> list = null;
    private TextView mTextView = null;
    private Subscriber<List<MovieInfo>> mSubscriber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.tv);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubscriber = new Subscriber<List<MovieInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<MovieInfo> movieInfos) {
                        mTextView.setText(movieInfos.get(0).getQuestion());

                    }
                };
                RetrofitHttpRequest.getInstance().getMovieInfo(1,"c1", URLContainer.APP_KEY,"rand",mSubscriber);

            }
        });
     }

    @Override
    protected void onStop() {
        super.onStop();
        if(mSubscriber != null && !mSubscriber.isUnsubscribed()){
            mSubscriber.unsubscribe();
        }
    }
}
