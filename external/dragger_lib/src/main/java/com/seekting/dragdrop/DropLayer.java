package com.seekting.dragdrop;

/**
 * Created by seekting on 17-5-22.
 */

public interface DropLayer<T> {
    boolean canAcceptDrop(T t, float x, float y);

    boolean isInDropRect(float x, float y);

    void onDropEnter(T t, float x, float y);

    void onDropOut(T t, float x, float y);

    T onDropComplete(T t, float x, float y);
}
