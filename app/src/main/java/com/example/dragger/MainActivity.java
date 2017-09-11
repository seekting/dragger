package com.example.dragger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.seekting.dragdrop.DragController;
import com.seekting.dragdrop.DragRender;
import com.seekting.dragdrop.DragSource;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DragRender<Integer> {

    private DragController mDragController;
    private ImageView mMRander;
    private MyDropLayer mTop, mBottom;
    private View.OnLongClickListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTop = (MyDropLayer) findViewById(R.id.top);
        mBottom = (MyDropLayer) findViewById(R.id.bottom);
        mMRander = (ImageView) findViewById(R.id.rander);


        MyDragLayer dragLayer = (MyDragLayer) findViewById(R.id.drag_layer);

        mDragController = new DragController(this, dragLayer);
        mDragController.addDropLayer(mBottom);
        mDragController.addDropLayer(mTop);
        mListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mDragController.startDrag((DragSource) v);
                Log.d("seekting", "MainActivity.onLongClick()startDrag");
                return true;
            }
        };
        mTop.setOnLongClickListener(mListener);
        mBottom.setOnLongClickListener(mListener);
        List<Integer> items = new ArrayList<>();
        items.add(R.mipmap.p_1);
        items.add(R.mipmap.p_2);
        items.add(R.mipmap.p_3);
        items.add(R.mipmap.p_4);
        items.add(R.mipmap.p_5);
        items.add(R.mipmap.p_6);
        for (int i = 0; i < items.size(); i++) {
            ImageView im = new MyDragSource(this);
            im.setImageResource(items.get(i));
            im.setTag(items.get(i));
            mTop.addView(im);


        }

    }

    int item;

    @Override
    public void beginRenderDrag(Integer s, float renderAlpha, float x, float y, int width, int height) {
        item = s;
        mMRander.setImageResource(item);
        mMRander.setVisibility(View.VISIBLE);
        mMRander.setScaleX(1.5f);
        mMRander.setScaleY(1.5f);
        float x1 = (float) (x - 0.75 * width);
        float y1 = (float) (y - 0.75 * width);

        mMRander.setTranslationX(x1);
        mMRander.setTranslationY(y1);
        mMRander.setAlpha(0.5f);
        Log.d("seekting", "MainActivity.beginRenderDrag()x1=" + x1 + ",y1=" + y1);
    }

    @Override
    public void renderDragAt(float x, float y, int width, int height) {
        float x1 = (float) (x - 0.75 * width);
        float y1 = (float) (y - 0.75 * width);

        mMRander.setTranslationX(x1);
        mMRander.setTranslationY(y1);

        Log.d("seekting", "MainActivity.renderDragAt()x1=" + x1 + ",y1=" + y1);

    }

    @Override
    public void endRenderDrag() {
        mMRander.setVisibility(View.GONE);
        Log.d("seekting", "MainActivity.endRenderDrag()");

    }
}
