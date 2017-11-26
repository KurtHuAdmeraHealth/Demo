package com.example.andrew.demo;
//Created by Kurt on 7/18/2017.

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class ZoomRecyclerView extends RecyclerView {
    private RecyclerViewHeaderDecorator recyclerViewHeaderDecorator;
    private ScaleGestureDetector scaleGestureDetector;
    private static final String TAG = FragmentZoomRecyclerView.class.getSimpleName();

    private float scaleFactor = 1.f;
    private static final float minScale = 1.0f;
    private static final float maxScale = 3.0f;

    private float focusX;
    private float focusY;

    private int dpMaxHeight;

    public void setMaxHeight(int dpMaxHeight) {
        this.dpMaxHeight = dpMaxHeight;
    }

    //gets amount of horizontal scroll to get correct focus for zooming
    private float scrolledX;

    public void setScrolledX(float scrolledX) {
        this.scrolledX = scrolledX;
    }

    public void setRecyclerViewHeaderDecorator(RecyclerViewHeaderDecorator recyclerViewHeaderDecorator) {
        this.recyclerViewHeaderDecorator = recyclerViewHeaderDecorator;
    }

    public ZoomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);


        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                scaleFactor *= detector.getScaleFactor();

                //makes sure user does not zoom in or out past a certain amount
                scaleFactor = Math.max(minScale, Math.min(scaleFactor, maxScale));

                //refresh the view and compute the size of the view in the screen
                invalidate();

                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
            }
        });
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        //uses DisplayMetrics to convert dp into actual pixels
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int maxHeight = Math.round(dpMaxHeight * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        if (maxHeight > 0){
            int hSize = Math.round(MeasureSpec.getSize(heightSpec) * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
            int hMode = Math.round(MeasureSpec.getMode(heightSpec) * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));

            switch (hMode){
                case MeasureSpec.AT_MOST:
                    heightSpec = MeasureSpec.makeMeasureSpec(Math.min(hSize, maxHeight), MeasureSpec.AT_MOST);
                    break;
                case MeasureSpec.UNSPECIFIED:
                    heightSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
                    break;
                case MeasureSpec.EXACTLY:
                    heightSpec = MeasureSpec.makeMeasureSpec(Math.min(hSize, maxHeight), MeasureSpec.EXACTLY);
                    break;
            }
        }
        super.onMeasure(widthSpec, heightSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (event.getPointerCount() > 1) {
            //notify the scaleGestureDetector that an event has happened
            scaleGestureDetector.onTouchEvent(event);
        }
        return true;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        recyclerViewHeaderDecorator.setScaleFactor(scaleFactor); //tells the header what the scale factor is

        //getFocus is only getting the position on the screen, not on the view itself - need to add the scroll amount
        focusX = scaleGestureDetector.getFocusX();
        focusY = scaleGestureDetector.getFocusY();


        //scales the display, centered on where the user is touching the display
        canvas.scale(scaleFactor, scaleFactor, focusX + scrolledX, focusY);
    }
}