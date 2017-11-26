package com.example.andrew.demo;
//Created by Kurt on 7/28/2017.


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class DiagonalHorizontalScrollView extends HorizontalScrollView {
    private static final String TAG = DiagonalHorizontalScrollView.class.getSimpleName();
    private ZoomRecyclerView zoomRecyclerView;

    public void setZoomRecyclerView(ZoomRecyclerView zoomRecyclerView) {
        this.zoomRecyclerView = zoomRecyclerView;
    }

    public DiagonalHorizontalScrollView(Context context)
    {
        super(context);
    }

    public DiagonalHorizontalScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DiagonalHorizontalScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        zoomRecyclerView.onTouchEvent(event);
        zoomRecyclerView.setScrolledX(getScrollX());
        return true;
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent event) {
        super.onInterceptTouchEvent(event);
        zoomRecyclerView.onInterceptTouchEvent(event);
        zoomRecyclerView.setScrolledX(getScrollX());
        return true;
    }
}
