package com.weather.weatherapp.ui.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SunView extends View {

    private Paint paint = new Paint();
    private Paint arcPaint = new Paint();
    private Paint linePaint = new Paint();
    RectF rectF = new RectF(10*DP, 0, getWidth()-10*DP, 2*getHeight());

    private static final float DP = Resources.getSystem().getDisplayMetrics().density;

    public SunView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint.setColor(Color.YELLOW);
        arcPaint.setColor(Color.YELLOW);
        linePaint.setColor(Color.WHITE);
        arcPaint.setStyle(Paint.Style.STROKE);
        linePaint.setStyle(Paint.Style.STROKE);
    }


    public SunView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SunView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rectF, 180F, 180F, false, arcPaint);
        canvas.drawCircle(10*DP,getHeight()-10*DP, 10*DP, paint);
        canvas.drawCircle(getWidth()-10*DP, getHeight()-10*DP, 10*DP, paint);

        canvas.drawLine(0, getHeight()-10*DP, getWidth(), getHeight()-10*DP, linePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = (int) (getPaddingLeft() + getPaddingRight());
        int desiredHeight = (int) (getPaddingTop() + getPaddingBottom()+100*DP);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

}
