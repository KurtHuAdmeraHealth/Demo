package com.example.andrew.demo;
//Created by Kurt on 7/25/2017.

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class RecyclerViewHeaderDecorator extends ZoomRecyclerView.ItemDecoration {
    private static final String TAG = RecyclerViewHeaderDecorator.class.getSimpleName();
    private View header;
    private float scaleFactor;
    private float relativeX;
    private float relativeY;


    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public RecyclerViewHeaderDecorator(View header) {
        this.header = header;
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildCount() > 0) {

            // Specs for parent (RecyclerView)
            int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

            // Specs for children (headers)
            int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), header.getLayoutParams().width);
            int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams().height);

            header.measure(childWidthSpec, childHeightSpec);

            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());

            //keeps header on top left of RecyclerView
            float[] values = new float[9];
            float[] values2 = new float[9];
            Matrix matrix;
            Matrix matrix2;
            matrix = parent.getMatrix();
            matrix2 = canvas.getMatrix();
            matrix.getValues(values);
            matrix2.getValues(values2);
            relativeX = values[2];
            relativeY = values2[5];

            canvas.translate((-relativeX) / scaleFactor, (-relativeY) / scaleFactor);

            canvas.save();
            header.draw(canvas);
            canvas.restore();
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        header.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int height = header.getMeasuredHeight();
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = height;
        }
    }
}
