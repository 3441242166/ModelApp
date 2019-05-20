package com.example.modelapp;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.mvvm.net.rx.RxRetrofitClient;

public class NoNameLayout extends ViewGroup {
    private static final String TAG = "NoNameLayout";

    private int layoutWidth;
    private int layoutHeight;

    private int state = State.TOP;

    private View topView;
    private View centerView;
    private View bottomView;

    private Scroller scroller;
    private int mScaledTouchSlop;

    public NoNameLayout(Context context) {
        super(context);
    }

    public NoNameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NoNameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST) {
            layoutWidth = width;
        } else {
            throw new RuntimeException("Width can not is match_parent in ScrollView");
        }

        if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST) {
            layoutHeight = height;
        } else {
            throw new RuntimeException("height can not is match_parent in ScrollView");
        }
        Log.i(TAG, "1.onLayout: width = " + layoutWidth + "  height = " + layoutHeight);
        setMeasuredDimension(layoutWidth, layoutHeight);

//        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
//        int screenWidth = displayMetrics.widthPixels;
//        int screenHeight = displayMetrics.heightPixels;

        LayoutParams topParams = topView.getLayoutParams();
        topParams.width = LayoutParams.MATCH_PARENT;
        topParams.height = LayoutParams.MATCH_PARENT;
        topView.setLayoutParams(topParams);

        LayoutParams centerParams = centerView.getLayoutParams();
        centerParams.width = LayoutParams.MATCH_PARENT;
        centerParams.height = (centerParams.height == LayoutParams.MATCH_PARENT) ?
                LayoutParams.WRAP_CONTENT : centerView.getMeasuredHeight();
        centerView.setLayoutParams(centerParams);

        LayoutParams bottomParams = bottomView.getLayoutParams();
        bottomParams.width = LayoutParams.MATCH_PARENT;
        bottomParams.height = layoutHeight - centerView.getMeasuredHeight();
        bottomView.setLayoutParams(bottomParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int startX = 0;
        int startY = 0;
        Log.i(TAG, "onLayout: topView.width = " + topView.getMeasuredWidth() + "  topView.height = " + topView.getMeasuredHeight());
        Log.i(TAG, "onLayout: centerView.width = " + centerView.getMeasuredWidth() + "  centerView.height = " + centerView.getMeasuredHeight());
        Log.i(TAG, "onLayout: bottomView.width = " + bottomView.getMeasuredWidth() + "  bottomView.height = " + bottomView.getMeasuredHeight());

        topView.layout(startX, startY, topView.getMeasuredWidth(), topView.getMeasuredHeight());
        startY += topView.getHeight();
        centerView.layout(startX, startY, startX + centerView.getMeasuredWidth(), startY + centerView.getMeasuredHeight());
        startY += centerView.getHeight();
        bottomView.layout(startX, startY, startX + bottomView.getMeasuredWidth(), startY + bottomView.getMeasuredHeight());
    }

    private float startX, startY;
    private float nowX, nowY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                startX = event.getX();
                startY = event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                // 计算滑动间隔的方向
                nowY = event.getY();
                float disY = nowY - startY;
                // 获取下次滑动的位置
                int toScrollY = (int) (getScrollY() - disY);

                if (toScrollY < 0) {
                    toScrollY = 0;
                }
                if (state != State.BOTTOM && toScrollY > centerView.getMeasuredHeight()) {
                    toScrollY = centerView.getMeasuredHeight();
                }
                if (toScrollY > layoutHeight) {
                    toScrollY = layoutHeight;
                }
                scrollTo(getScrollX(), toScrollY);

                startX = (int) event.getX();
                startY = (int) event.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                if (state == State.BOTTOM) {
                    if (getScrollY() - centerView.getMeasuredHeight() > bottomView.getMeasuredHeight() / 2) {
                        openBottomLayout();
                    } else {
                        closeBottomLayout();
                    }
                } else {
                    if (getScrollY() > centerView.getMeasuredHeight() / 2) {
                        openCenterLayout();
                    } else {
                        closeCenterLayout();
                    }
                }
            }

        }

        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        super.onInterceptTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.i(TAG, "onInterceptTouchEvent: DOWN");
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.i(TAG, "onInterceptTouchEvent: MOVE");
                break;
            }
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onInterceptTouchEvent: UP");
                break;

        }
        // DOWN事件 统统不拦截
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return false;
        }
        // 只有bottomView才考虑是否拦截
        Log.i(TAG, "onInterceptTouchEvent: event.getY = " + event.getRawY() + "  centerView.getHeight = " + centerView.getHeight());
        if (getScrollY() > centerView.getHeight() && event.getRawY() > centerView.getHeight()) {
            Log.i(TAG, "onInterceptTouchEvent: Return True");
            return true;
        }

        return false;
    }

    public void openBottomLayout() {
        Log.i(TAG, "openBottomLayout: ");
        if (state == State.CENTER) {

        }
        scroller.startScroll(getScrollX(), getScrollY(), getScrollX(), layoutHeight - getScrollY(), 500);
        invalidate();
        state = State.BOTTOM;
    }

    public void closeBottomLayout() {
        Log.i(TAG, "closeBottomLayout: ");
        if (state == State.BOTTOM) {

        }
        scroller.startScroll(getScrollX(), getScrollY(), getScrollX(), - getScrollY(), 500);
        invalidate();
        state = State.TOP;
    }

    private void closeCenterLayout() {
        Log.i(TAG, "closeCenterLayout: ");
        scroller.startScroll(getScrollX(), getScrollY(), getScrollX(), - getScrollY());
        invalidate();
        state = State.TOP;
    }

    private void openCenterLayout() {
        Log.i(TAG, "openCenterLayout: ");
        scroller.startScroll(getScrollX(), getScrollY(), getScrollX(), centerView.getMeasuredHeight() - getScrollY());
        invalidate();
        state = State.CENTER;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if( scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }

    private void init() {
        state = State.TOP;
        scroller = new Scroller(getContext());
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onFinishInflate() {
        int childCount = getChildCount();
        if (childCount != 3) {
            throw new RuntimeException("Layout mast have three child");
        }
        Log.i(TAG, "onFinishInflate: childCount = " + childCount);
        topView = getChildAt(0);
        centerView = getChildAt(1);
        bottomView = getChildAt(2);

        super.onFinishInflate();
    }


}
