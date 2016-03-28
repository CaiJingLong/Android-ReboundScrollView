package com.kikt.reboundscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by kikt on 2016/3/20.
 * 到顶部还可以继续下拉，实现动画效果的ScrollView
 */
public class ReboundScrollView extends ScrollView {
    private final Context mContext;
    private ScrollViewListener scrollViewListener;

    public ReboundScrollView(Context context) {
        this(context, null);
    }

    public ReboundScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReboundScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    private boolean enableAnimation = true;

    public boolean isEnableAnimation() {
        return enableAnimation;
    }

    public void setCanRebound(boolean enableAnimation) {
        this.enableAnimation = enableAnimation;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!enableAnimation) {
            return super.onTouchEvent(event);
        }
        if (!checkTop(event)) {
            return super.onTouchEvent(event);
        }
        return true;
    }

    protected float downY = -1;
    private float lastDiff;

    private boolean checkTop(MotionEvent event) {
        int scrollY = getScrollY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                updateDownY(event);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getRawY();
                if (scrollY == 0) {
                    updateDownY(event);
                    float diffY = moveY - downY;
                    if (diffY > 0) {
                        updateHeadView(diffY);
                        smoothScrollTo(0, 0);
                        return true;
                    }
                } else {
                    downY = -1;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                updateHeadView(0);
                downY = -1;
                break;
        }

        return false;
    }

    private void updateDownY(MotionEvent event) {
        if (downY == -1) {
            downY = event.getRawY();
        }
    }

    public void setMaxHeight(int headerHeight) {
        this.headerHeight = headerHeight;
    }

    private int headerHeight = 380;

    private void updateHeadView(float diffY) {
        int animationHeight = headerHeight;//高度
        if (diffY > animationHeight) {
            diffY = animationHeight;
        } else if (diffY == 0) {
            closeAnimator(animationHeight);
            return;
        }
        lastDiff = diffY;
        updateHeaderView(diffY, animationHeight);
    }

    private int closeDuration = 300;

    public void setCloseDuration(int closeDuration) {
        this.closeDuration = closeDuration;
    }

    //关闭的动画
    private void closeAnimator(final int animationHeight) {
        int duration = closeDuration;
        ValueAnimator animator = ValueAnimator.ofFloat(lastDiff, 0).setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float diffY = (Float) valueAnimator.getAnimatedValue();
                updateHeaderView(diffY, animationHeight);
            }
        });
        animator.start();
        lastDiff = 0;
    }

    private View mLayoutHead;

    public void setHeaderView(View mLayoutHead) {
        this.mLayoutHead = mLayoutHead;
        mLayoutHead.getViewTreeObserver().addOnGlobalLayoutListener(new LayoutListener(mLayoutHead));
    }

    public OnAnimListener getOnAnimListener() {
        return onAnimListener;
    }

    public void setOnAnimListener(OnAnimListener onAnimListener) {
        this.onAnimListener = onAnimListener;
    }

    class LayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private View view;

        LayoutListener(View view) {
            this.view = view;
        }

        @Override
        public void onGlobalLayout() {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            mLayoutHeadHeight = view.getHeight();
        }
    }

    private int mLayoutHeadHeight;

    private void updateHeaderView(float diffY, int animationHeight) {
        if (mLayoutHead != null) {
            float fraction = diffY / animationHeight;
            if (onAnimListener != null) {
                onAnimListener.onAnim(this, fraction, diffY);
            }

            float scale = 1 + diffY / animationHeight / 2 / 2;//缩放比例
            ViewHelper.setScaleX(mLayoutHead, scale);
            ViewHelper.setScaleY(mLayoutHead, scale);
            mLayoutHead.getLayoutParams().height = (int) (mLayoutHeadHeight + diffY);
            mLayoutHead.requestLayout();
        }
    }

    private OnAnimListener onAnimListener;

    public interface OnAnimListener{
        void onAnim(ReboundScrollView scrollView, float fraction, float height);
    }

    public interface ScrollViewListener {
        void onScrollChanged(ReboundScrollView scrollView, int x, int y, int oldx, int oldy);
    }

    private ScrollViewListener ScrollViewListener;

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
