package com.example.andrew.demo;
//Created by Kurt on 7/28/2017.


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class FragmentDiagonalHorizontalScrollView extends HorizontalScrollView {
    private static final String TAG = DiagonalHorizontalScrollView.class.getSimpleName();
    private FragmentZoomRecyclerView zoomRecyclerView;

    public void setFragmentZoomRecyclerView(FragmentZoomRecyclerView zoomRecyclerView) {
        this.zoomRecyclerView = zoomRecyclerView;
    }

    public FragmentDiagonalHorizontalScrollView(Context context)
    {
        super(context);
    }

    public FragmentDiagonalHorizontalScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FragmentDiagonalHorizontalScrollView(Context context, AttributeSet attrs, int defStyle)
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
