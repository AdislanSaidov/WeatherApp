package com.weather.weatherapp.ui.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.weather.weatherapp.R;

public class SunView extends View {

    private Paint paint = new Paint();
    private Paint arcPaint = new Paint();
    private Paint linePaint = new Paint();
    private static final float DP = Resources.getSystem().getDisplayMetrics().density;
    private RectF rectF;
    private static final int DEFAULT_CIRCLE_MARGIN = (int) (10 * DP);
    private static final int DEFAULT_CIRCLE_RADIUS = (int) (10 * DP);
    private float circleMargin = DEFAULT_CIRCLE_MARGIN;
    private float circleRadius = DEFAULT_CIRCLE_RADIUS;

//    private String


    public SunView(Context context) {
        super(context);
        init();
    }

    private void init() {
        rectF = new RectF();
        paint.setColor(Color.YELLOW);
        arcPaint.setColor(Color.YELLOW);
        linePaint.setColor(Color.WHITE);
        arcPaint.setStyle(Paint.Style.STROKE);
        linePaint.setStyle(Paint.Style.STROKE);
    }


    public SunView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SunView, defStyleAttr, 0);

        circleMargin = a.getDimensionPixelSize(R.styleable.SunView_circleMargin, DEFAULT_CIRCLE_MARGIN);
        circleRadius = a.getDimensionPixelSize(R.styleable.SunView_circleRadius, DEFAULT_CIRCLE_RADIUS);

        a.recycle();
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectF.left = circleRadius + circleMargin;
        rectF.top = 0;
        rectF.right = getWidth() - circleRadius - circleMargin;
        rectF.bottom = 2 * getHeight();
        canvas.drawCircle(circleRadius + circleMargin, getHeight() - circleRadius, circleRadius, paint);
        canvas.drawCircle(getWidth() - circleRadius - circleMargin, getHeight() - circleRadius, circleRadius, paint);

        canvas.drawLine(0, getHeight() - circleRadius, getWidth(), getHeight() - circleRadius, linePaint);
        canvas.drawArc(rectF, 180F, 180F, false, arcPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = (int) (getPaddingLeft() + getPaddingRight());
        int desiredHeight = (int) (getPaddingTop() + getPaddingBottom() + 100 * DP);

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

    public float getCircleMargin() {
        return circleMargin;
    }

    public void setCircleMargin(float circleMargin) {
        this.circleMargin = circleMargin;
    }

    public float getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }
}
