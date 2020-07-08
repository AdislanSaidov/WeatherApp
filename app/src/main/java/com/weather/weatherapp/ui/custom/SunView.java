package com.weather.weatherapp.ui.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.weather.weatherapp.R;

public class SunView extends View {

    private final Paint paint = new Paint();
    private final Paint arcPaint = new Paint();
    private final Paint linePaint = new Paint();
    private final Paint textPaint = new Paint();
    private static final float DP = Resources.getSystem().getDisplayMetrics().density;
    private RectF rectF;
    private static final int DEFAULT_ARC_MARGIN = (int) (10 * DP);
    private static final int DEFAULT_ARC_RADIUS = (int) (10 * DP);
    private float arcHorizontalMargin = DEFAULT_ARC_MARGIN;
    private float pointRadius = DEFAULT_ARC_RADIUS;
    private String sunRise = "";
    private String sunSet = "";
    private static final int TEXT_BOTTOM_MARGIN = (int) (10 * DP);
    private static final int TEXT_SIZE = (int) (14 * DP);
    private Bitmap sunBitmap;



    public SunView(Context context) {
        super(context);
        init();
    }

    private void init() {
        rectF = new RectF();
        paint.setColor(Color.YELLOW);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(TEXT_SIZE);

        arcPaint.setColor(Color.YELLOW);
        arcPaint.setStrokeWidth(2*DP);
        arcPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.WHITE);
        arcPaint.setStyle(Paint.Style.STROKE);
        linePaint.setStyle(Paint.Style.STROKE);

        sunBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sun);
    }


    public SunView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SunView, defStyleAttr, 0);

        arcHorizontalMargin = a.getDimensionPixelSize(R.styleable.SunView_arcMargin, DEFAULT_ARC_MARGIN);
        pointRadius = a.getDimensionPixelSize(R.styleable.SunView_pointRadius, DEFAULT_ARC_RADIUS);

        a.recycle();
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectF.left = pointRadius + arcHorizontalMargin;
        rectF.top = 2*DP;
        rectF.right = getWidth() - pointRadius - arcHorizontalMargin;
        rectF.bottom = 2 * getHeight();

        canvas.drawCircle(pointRadius + arcHorizontalMargin, getHeight() - pointRadius, pointRadius, paint);
        canvas.drawCircle(getWidth() - pointRadius - arcHorizontalMargin, getHeight() - pointRadius, pointRadius, paint);

        canvas.drawArc(rectF, 180F, 180F, false, arcPaint);

        canvas.drawLine(0, getHeight() - pointRadius, getWidth(), getHeight() - pointRadius, linePaint);

        canvas.drawText(sunRise, 0, getHeight() - TEXT_BOTTOM_MARGIN, textPaint);
        canvas.drawText(sunSet, getWidth() - textPaint.measureText(sunSet), getHeight() - TEXT_BOTTOM_MARGIN, textPaint);

        canvas.drawBitmap(sunBitmap, 0, 0, paint);


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

    public String getSunRise() {
        return sunRise;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
        invalidate();
    }

    public String getSunSet() {
        return sunSet;
    }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet;
        invalidate();
    }

    public float getArcHorizontalMargin() {
        return arcHorizontalMargin;
    }

    public void setArcHorizontalMargin(float arcHorizontalMargin) {
        this.arcHorizontalMargin = arcHorizontalMargin;
    }

    public float getPointRadius() {
        return pointRadius;
    }

    public void setPointRadius(float pointRadius) {
        this.pointRadius = pointRadius;
    }
}
