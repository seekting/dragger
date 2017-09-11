package com.seekting.dragdrop;

/**
 * Created by seekting on 17-5-22.
 */

public interface DragRender<T> {

    void beginRenderDrag(T t, float renderAlpha, float x, float y, int width, int height);

    void renderDragAt(float x, float y, int width, int height);

    void endRenderDrag();

}
