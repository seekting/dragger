package com.seekting.dragdrop;

/**
 * Created by seekting on 17-5-22.
 */

public interface DragSource<T> {


    boolean canDrag();

    T onBeginDragItem();

    int getItemWidth();

    int getItemHeight();

    void onCancelDrag();

    void onCompleteDrag(T t);

}
