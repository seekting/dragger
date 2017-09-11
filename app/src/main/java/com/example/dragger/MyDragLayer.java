package com.example.dragger;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.seekting.dragdrop.DragController;
import com.seekting.dragdrop.TouchDispatcher;

/**
 * Created by Administrator on 2017/9/11.
 */

public class MyDragLayer extends LinearLayout implements TouchDispatcher {
    DragController mDragController;

    public MyDragLayer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDragController.onTouch(this, event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragController.onInterceptTouchEvent(ev);
    }

    @Override
    public void setDragTouchListener(DragController dragController) {
        this.mDragController = dragController;

    }
}
