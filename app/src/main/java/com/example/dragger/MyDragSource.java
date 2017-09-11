package com.example.dragger;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.seekting.dragdrop.DragSource;

/**
 * Created by Administrator on 2017/9/11.
 */

public class MyDragSource extends android.support.v7.widget.AppCompatImageView implements DragSource<Integer> {
    public MyDragSource(Context context) {
        super(context);
    }

    public MyDragSource(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canDrag() {
        return true;
    }

    @Override
    public Integer onBeginDragItem() {
        setVisibility(View.GONE);
        return (Integer) getTag();
    }

    @Override
    public int getItemWidth() {
        return getMeasuredWidth();
    }

    @Override
    public int getItemHeight() {
        return getMeasuredHeight();
    }

    @Override
    public void onCancelDrag() {
        setVisibility(View.VISIBLE);

    }

    @Override
    public void onCompleteDrag(Integer integer) {
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }


    }
}
