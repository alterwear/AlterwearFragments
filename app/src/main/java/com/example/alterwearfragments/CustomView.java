package com.example.alterwearfragments;

import android.graphics.Path;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.Color;

public class CustomView extends View {

    //tag
    private static final String TAG = "CustomView";

    //drawing path
    private Path drawPath;

    //defines what to draw
    private Paint canvasPaint;

    //defines how to draw
    private Paint drawPaint;

    //initial color
    private int paintColor = 0xFF660000;

    //canvas - holding pen, holds your drawings
    //and transfers them to the view
    private static Canvas drawCanvas;

    //canvas bitmap
    private static Bitmap canvasBitmap;

    //photo bitmap
    private static Bitmap photoBitmap;
    private static int photoWidth;

    //brush size
    private float currentBrushSize, lastBrushSize;


    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        currentBrushSize = 20.0f;
        lastBrushSize = currentBrushSize;

        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(currentBrushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);

    }

    public void clear() {
        drawCanvas.drawColor(Color.WHITE);
    }

    public static void setDrawBitmap(Bitmap bm) {
        Log.e(TAG, "setDrawBitmap in custom view");
        if (bm != null && bm != photoBitmap) {
            photoBitmap = bm;

            //apply bitmap to graphic to start drawing.
            Rect bitmapFrame = new Rect(0, 0, photoWidth, photoWidth);
            drawCanvas.drawBitmap(photoBitmap, null, bitmapFrame, null);
//            drawCanvas.drawBitmap(photoBitmap, 0, 0, null);
            Log.e(TAG, "set drawCanvas to photoBitmap in custom view");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0 , 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
        Log.e(TAG, "onDraw called in customView");

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.e(TAG, "onSizeChanged in customView");

        //create canvas of certain device size.
        super.onSizeChanged(w, h, oldw, oldh);

        //create Bitmap of certain w,h
        // reference: https://stackoverflow.com/questions/6908604/android-crop-center-of-bitmap
        if (w >= h) {
            canvasBitmap = Bitmap.createBitmap(h, h, Bitmap.Config.ARGB_8888);
        } else {
            canvasBitmap = Bitmap.createBitmap(w, w, Bitmap.Config.ARGB_8888);
        }

        //apply bitmap to graphic to start drawing.
        drawCanvas = new Canvas(canvasBitmap);
    }

    // reference: https://stackoverflow.com/questions/36728553/setting-view-height-dependent-on-its-width-in-android-in-layout-xml
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        photoWidth = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        //respond to down, move and up events
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        //redraw
        invalidate();
        return true;
    }

}