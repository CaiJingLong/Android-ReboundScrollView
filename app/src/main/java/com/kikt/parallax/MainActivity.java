package com.kikt.parallax;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.kikt.reboundscrollview.ObservableScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.fl_head)
    FrameLayout mFlHead;
    @Bind(R.id.sv_content)
    ObservableScrollView mSvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSvContent.setLayoutHead(mFlHead);
    }
}
