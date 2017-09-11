package com.seekting.dragdrop;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.seekting.dragger.BuildConfig;


/**
 * Created by seekting on 17-6-12.
 */

public class DragDrawable extends Drawable {


    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final String TAG = "DragDrawable";

    private Drawable wrap;
    private Rect mBounds;

    public DragDrawable(Drawable wrap) {
        this.wrap = wrap;
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.wrap != null) {
            this.wrap.draw(canvas);
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return wrap.getOpacity();
    }


    public void setViewWidthHeight(int measuredWidth, int measuredHeight) {
        if (this.wrap == null) {
            return;

        }
        mBounds = wrap.getBounds();
        int drawableWidth = 0;
        int drawableHeight = 0;
        int intrinsicWidth = mBounds.width();
        int intrinsicHeight = mBounds.height();
        if (intrinsicHeight > 0 && intrinsicWidth > 0) {
            float factorWidth = (float) measuredWidth / intrinsicWidth;
            float factorHeight = (float) measuredHeight / intrinsicHeight;
            if (factorWidth <= factorHeight) {
//                    //宽度不够
                drawableWidth = measuredWidth;
                drawableHeight = Math.round(intrinsicHeight * factorWidth);
//
            } else {
                drawableWidth = Math.round(intrinsicWidth * factorHeight);
                drawableHeight = measuredHeight;
            }
        }
        setBounds(0, 0, drawableWidth, drawableHeight);
    }

    public Drawable getDrawable() {
        return wrap;
    }
}
