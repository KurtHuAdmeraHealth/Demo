package com.example.andrew.demo;
//Created by Kurt on 8/31/2017.


import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class ZoomImageView extends AppCompatImageView {
    private ScaleGestureDetector scaleGestureDetector;
    private static final String TAG = ZoomImageView.class.getSimpleName();

    private float scaleFactor = 1.f;
    private static final float minScale = 1.0f;
    private static final float maxScale = 3.0f;

    private float focusX;
    private float focusY;

    public ZoomImageView(Context context, AttributeSet attrs) {
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

        //getFocus is only getting the position on the screen, not on the view itself - need to add the scroll amount
        focusX = scaleGestureDetector.getFocusX();
        focusY = scaleGestureDetector.getFocusY();


        //scales the display, centered on where the user is touching the display
        canvas.scale(scaleFactor, scaleFactor, focusX, focusY);
    }
}
