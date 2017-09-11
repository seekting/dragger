package com.seekting.dragdrop;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.seekting.dragger.BuildConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seekting on 17-5-22.
 */

public class DragController<T> implements View.OnTouchListener {

    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final String TAG = "DragController";
    private DragRender mDragRender;
    private TouchDispatcher mTouchDispatcher;
    private List<DropLayer> mDropLayers = new ArrayList<>();
    private List<DropLayer> mAcceptDropLayers = new ArrayList<>();

    private float mLastX, mLastY;
    T mDragSourceSrc;
    private DropLayer mCurrentDropLayer;
    private DragSource mDragSource;

    public DragController(DragRender dragRender, TouchDispatcher touchDispatcher) {
        this.mTouchDispatcher = touchDispatcher;
        this.mDragRender = dragRender;
        this.mTouchDispatcher.setDragTouchListener(this);
    }

    public void addDropLayer(DropLayer dropLayer) {
        mDropLayers.add(dropLayer);
    }

    public void clearDropLayer() {
        mDropLayers.clear();
    }

    public boolean startDrag(DragSource<T> dragSource) {
        if (dragSource != null) {
            mDragSource = dragSource;
            try {
                mDragSourceSrc = dragSource.onBeginDragItem();
            } catch (Throwable t) {
                if (DEBUG) {
                    Log.d(TAG, "startDrag.", t);
                }


            }
        }

        if (mDragSource == null || mDragSourceSrc == null) {
            return false;
        }
        mDragRender.beginRenderDrag(mDragSourceSrc, 0.6f, mLastX, mLastY, mDragSource.getItemWidth(), mDragSource.getItemHeight());
        for (DropLayer dropLayer : mDropLayers) {
            if (dropLayer.canAcceptDrop(mDragSourceSrc, mLastX, mLastY)) {
                mAcceptDropLayers.add(dropLayer);

            }


        }


        return true;
    }

    private boolean isBeingDrag() {
        return mDragSource != null;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        mLastX = event.getX();
        mLastY = event.getY();

        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
        case MotionEvent.ACTION_UP:
            //6.0的手机当父容器抢了up事件，最终也不会走父容器的onTouch，所以在此处被了一个handleTouchUp
            if (isBeingDrag()) {
                handleTouchUp(event);
            }

            break;
        }
        return isBeingDrag();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean isBeingDrag = isBeingDrag();
        if (isBeingDrag) {
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            switch (action) {
            case MotionEvent.ACTION_MOVE:
                handleTouchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                handleTouchUp(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                handleTouchCancel();
                break;
            }
        }
        return isBeingDrag;
    }

    private void handleTouchMove(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        mDragRender.renderDragAt(x, y, mDragSource.getItemWidth(), mDragSource.getItemHeight());

        if (mCurrentDropLayer != null) {
            if (mCurrentDropLayer.isInDropRect(x, y)) {
                return;
            } else {
                DropLayer newLayer = findDropLayer(x, y);
                if (newLayer != null) {
                    newLayer.onDropEnter(mDragSourceSrc, x, y);
                    mCurrentDropLayer.onDropOut(mDragSourceSrc, x, y);
                    mCurrentDropLayer = newLayer;
                } else {
                    mCurrentDropLayer.onDropOut(mDragSourceSrc, x, y);
                    mCurrentDropLayer = null;
                }


            }

        } else {
            DropLayer newLayer = findDropLayer(x, y);
            if (newLayer == null) {
                //do nothing
            } else {
                newLayer.onDropEnter(mDragSourceSrc, x, y);
                mCurrentDropLayer = newLayer;
            }
        }


    }

    private DropLayer findDropLayer(float x, float y) {
        for (DropLayer acceptDropLayer : mAcceptDropLayers) {

            if (mDragSource != acceptDropLayer && acceptDropLayer.isInDropRect(x, y)) {
                return acceptDropLayer;
            }

        }
        return null;
    }

    private void handleTouchUp(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();

        if (mCurrentDropLayer != null) {
            boolean inMyRect = mCurrentDropLayer.isInDropRect(x, y);
            if (inMyRect) {
                Object o = mCurrentDropLayer.onDropComplete(mDragSourceSrc, x, y);
                mDragSource.onCompleteDrag(o);
            }


        } else {
            mDragSource.onCancelDrag();
        }

        resetDragStatus();

    }

    private void resetDragStatus() {
        mDragRender.endRenderDrag();
        mDragSource = null;
        mCurrentDropLayer = null;
        mDragSourceSrc = null;
        mAcceptDropLayers.clear();
    }

    private void handleTouchCancel() {
        mDragSource.onCancelDrag();
        resetDragStatus();
    }
}
