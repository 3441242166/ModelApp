package com.example.modelapp.costomview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class MyViewPager extends ViewPager {
    private static final String TAG = "MyViewPager";

    private int mScaledTouchSlop;

    public MyViewPager(@NonNull Context context) {
        super(context);
        init();
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private float downX, downY;
    // 上下滑只判断一次
    private boolean initDirection = false;

    // 上下滑取决于子ViewGroup是否可以上下滑动
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.i(TAG, "dispatchTouchEvent: DOWN");
                downX = event.getX();
                downY = event.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                initDirection = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.i(TAG, "dispatchTouchEvent: MOVE");
                float difX = event.getX() - downX;
                float difY = event.getY() - downY;
                // 如果不满足最小滑动条件
                if (difX < mScaledTouchSlop && difY < mScaledTouchSlop) {
                    break;
                }

                // 当子ViewGroup不需要上划事件时
                // 当手指有偏移 但是view没有偏移 说明到顶了
                if (!initDirection && Math.abs(difX) < Math.abs(difY)) {
                    Log.i(TAG, "dispatchTouchEvent: True");

                    // 代表父ViewGroup拦截 事件交个父ViewGroup的onTouchEvent处理
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                initDirection = true;
                break;
            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
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




        return super.onInterceptTouchEvent(event);
    }

}
