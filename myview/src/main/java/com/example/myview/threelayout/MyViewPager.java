package com.example.myview.threelayout;

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

    private float startX, startY;
    private boolean isInit = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.i(TAG, "dispatchTouchEvent: DOWN");
                startX = ev.getX();
                startY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                isInit = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.i(TAG, "dispatchTouchEvent: MOVE");
                float difX = ev.getX() - startX;
                float difY = ev.getY() - startY;
                if (difX < mScaledTouchSlop && difY < mScaledTouchSlop) {
                    break;
                }
                if (!isInit && Math.abs(difX) < Math.abs(difY)) {
                    Log.i(TAG, "dispatchTouchEvent: True");
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                isInit = true;
                break;
            }

        }

        return super.dispatchTouchEvent(ev);
    }
}
