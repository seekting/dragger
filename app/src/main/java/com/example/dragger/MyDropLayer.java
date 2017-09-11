package com.example.dragger;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.seekting.dragdrop.DropLayer;

/**
 * Created by Administrator on 2017/9/11.
 */

public class MyDropLayer extends LinearLayout implements DropLayer<Integer> {
    public MyDropLayer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canAcceptDrop(Integer integer, float x, float y) {
        return true;
    }

    @Override
    public boolean isInDropRect(float x, float y) {
        boolean in = x >= getLeft() && x <= getRight() && y > getTop() && y < getBottom();
        return in;
    }

    @Override
    public void onDropEnter(Integer integer, float x, float y) {

    }

    @Override
    public void onDropOut(Integer integer, float x, float y) {

    }

    @Override
    public Integer onDropComplete(Integer integer, float x, float y) {
        ImageView image = new MyDragSource(getContext());
        image.setImageResource(integer);
        image.setTag(integer);
        addView(image);
        return integer;
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        child.setOnLongClickListener(mOnLongClickListener);
    }

    OnLongClickListener mOnLongClickListener;

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
        this.mOnLongClickListener = l;
    }
}
