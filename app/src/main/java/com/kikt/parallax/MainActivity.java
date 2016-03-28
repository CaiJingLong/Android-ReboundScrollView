package com.kikt.parallax;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.kikt.reboundscrollview.ReboundScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    @Bind(R.id.fl_head)
    FrameLayout mFlHead;
    @Bind(R.id.sv_content)
    ReboundScrollView mSvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSvContent.setHeaderView(mFlHead);
        mSvContent.setScrollViewListener(new ReboundScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ReboundScrollView scrollView, int x, int y, int oldx, int oldy) {
            }
        });

        mSvContent.setOnAnimListener(new ReboundScrollView.OnAnimListener() {
            @Override
            public void onAnim(ReboundScrollView scrollView, float fraction, float height) {
                Log.d("MainActivity", "fraction:" + fraction);
                Log.d("MainActivity", "height:" + height);
            }
        });

        mSvContent.setCloseDuration(300);
        mSvContent.setMaxHeight(200);
    }
}
