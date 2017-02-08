package com.vic.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vic.demo.R;
import com.vic.demo.bean.QuestionInfo;
import com.vic.demo.http.RetrofitHttpRequest;
import com.vic.demo.http.SubscriberOnNextListener;
import com.vic.demo.http.URLContainer;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button mButton =null;
    private List<String> list = null;
    private TextView mTextView = null;
    private SubscriberOnNextListener<List<QuestionInfo>> mSubscriber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.tv);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTestData();
            }
        });
     }

    private void getTestData(){
        RetrofitHttpRequest.getInstance().getMovieInfo(1,"c1", URLContainer.APP_KEY,"rand",new SubscriberOnNextListener<List<QuestionInfo>>(){
            @Override
            public void onNext(List<QuestionInfo> movieInfos) {
                mTextView.setText(movieInfos.get(0).getQuestion());
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
