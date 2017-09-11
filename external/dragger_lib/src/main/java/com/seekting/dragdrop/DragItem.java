package com.seekting.dragdrop;

import android.annotation.NonNull;

/**
 * Created by seekting on 17-5-23.
 */

public class DragItem {
    public DragDrawable mDragDrawable;
    public String mSrc;

    public DragItem(@NonNull DragDrawable drawable,@NonNull String src) {
        mDragDrawable = drawable;
        mSrc = src;
    }



}
