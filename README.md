#Android-ReboundScrollView

## 前言
    本项目为一个可以在scrollview到顶部后，还可以继续下拉的控件
    
## 引用其他第三方类库
    'com.nineoldandroids:library:2.4.0' 兼容API9以下的动画库
    
## 截图
![gif](http://ww4.sinaimg.cn/large/844036b9jw1f24drakv1mg20dc0m813o.gif)

## 使用时的核心代码

#### xml
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context="com.kikt.parallax.MainActivity">
    
        <com.kikt.reboundscrollview.ReboundScrollView
            android:id="@+id/sv_content"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
    
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="#f0f"
                android:orientation="vertical">
    
                <FrameLayout
                    android:id="@+id/fl_head"
                    android:layout_width="match_parent"
                    android:layout_height="80dp"
                    android:background="#f00">
    
                    <ImageButton
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_gravity="center"
                        android:src="@mipmap/ic_launcher"/>
                </FrameLayout>
    
                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="600dp"
                    android:background="#0f0"/>
    
                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="600dp"
                    android:background="#0ff"/>
    
            </LinearLayout>
    
        </com.kikt.reboundscrollview.ReboundScrollView>
    
    </LinearLayout>


#### java
    FrameLayout mFlHead;
    mSvContent.setHeaderView(mFlHead);//设置需要弹性的头布局
    mSvContent.setHeaderView(mFlHead);
        mSvContent.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
            //滚动侦听
            }
        });

    mSvContent.setCloseDuration(300);//关闭动画的速率
    mSvContent.setMaxHeight(200);//最大的增加高度
    mSvContent.setCanRebound(false);//设定开启弹性功能与否

    mSvContent.setOnAnimListener(new ReboundScrollView.OnAnimListener() {//侦听动画
        @Override
        public void onAnim(ReboundScrollView scrollView, float fraction, float height) {
            Log.d("MainActivity", "fraction:" + fraction);//下拉的百分比
            Log.d("MainActivity", "height:" + height);//下拉的高度
        }
    });



## 关于自定义属性
    暂时未加入，后续会考虑加入自定义属性