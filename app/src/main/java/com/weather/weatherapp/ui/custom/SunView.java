package com.weather.weatherapp.ui.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.weather.weatherapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class SunView extends View {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float DP = Resources.getSystem().getDisplayMetrics().density;
    private static final int DEFAULT_ARC_MARGIN = (int) (10 * DP);
    private float arcHorizontalMargin = DEFAULT_ARC_MARGIN;
    private String sunRise = "";
    private String sunSet = "";
    private static final int TEXT_BOTTOM_MARGIN = (int) (10 * DP);
    private static final int TEXT_SIZE = (int) (14 * DP);
    private Bitmap sunBitmap;
    private Path path;
    private PointF[] arcPoints;
    private PathMeasure pm;
    private final float[] xyCoordinate = new float[2];

    private static final int SUN_RADIUS = (int) (8 * DP);

    private final RectF oval = new RectF();
    private int pointsCount;
    private int hours;
    private int currentHour;
    private final Map<Integer, PointF> indexedPoints = new HashMap<>();
    private int sunRiseHour;

    public SunView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint.setColor(Color.YELLOW);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(TEXT_SIZE);

        arcPaint.setColor(Color.YELLOW);
        arcPaint.setStrokeWidth(2*DP);
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(2*DP);
        arcPaint.setStyle(Paint.Style.STROKE);
        linePaint.setStyle(Paint.Style.STROKE);

        sunBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sun);

        path = new Path();

        pm = new PathMeasure();

    }


    public SunView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SunView, defStyleAttr, 0);

        arcHorizontalMargin = a.getDimensionPixelSize(R.styleable.SunView_arcMargin, DEFAULT_ARC_MARGIN);

        a.recycle();
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(pointsCount == 0)
            return;
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();

        canvas.drawLine(0, canvasHeight, canvasWidth, canvasHeight, linePaint);

        int textY = canvasHeight - TEXT_BOTTOM_MARGIN;
        canvas.drawText(sunRise, 0, textY, textPaint);
        canvas.drawText(sunSet, canvasWidth - textPaint.measureText(sunSet), textY, textPaint);

        canvas.drawPath(path, arcPaint);
        drawLights(canvas);

        if(currentHour < sunRiseHour + hours) {
            drawSun(canvas);
        }

//        for(PointF p: arcPoints){
//            canvas.drawCircle(p.x, p.y, SUN_RADIUS, paint);
//        }
    }

    private void initSunPath() {
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();

        oval.left = arcHorizontalMargin;
        oval.top = 5*DP;
        oval.right = canvasWidth - arcHorizontalMargin;
        oval.bottom = canvasHeight * 2;

        path.addArc(oval, 180, 180);

        Timber.e(oval.toString());

        pm.setPath(path, false);
        float pathLength = pm.getLength();
        for (int i = 0; i < pointsCount; ++i) {
            pm.getPosTan(pathLength * i / (pointsCount -1), xyCoordinate, null);
            Timber.e("Point #%d = (%.0f,%.0f)", i + 1, xyCoordinate[0], xyCoordinate[1]);
            arcPoints[i].x = xyCoordinate[0];
            arcPoints[i].y = xyCoordinate[1];
        }
        createIndexedPoints();
        Timber.e(Arrays.toString(arcPoints));
    }

    private void drawSun(@NotNull Canvas canvas) {
        int bitmapCenterHeight = sunBitmap.getHeight() / 2;
        int bitmapCenterWidth = sunBitmap.getWidth() / 2;
        PointF currentHourPoint = indexedPoints.get(currentHour);
        canvas.drawBitmap(sunBitmap, currentHourPoint.x-bitmapCenterWidth, currentHourPoint.y-bitmapCenterHeight, paint);
    }

    private void drawLights(Canvas canvas) {
        if(arcPoints == null || arcPoints.length == 0) return;
        canvas.drawCircle(arcPoints[0].x, arcPoints[0].y, SUN_RADIUS, paint);
        canvas.drawCircle(arcPoints[arcPoints.length-1].x, arcPoints[arcPoints.length-1].y, SUN_RADIUS, paint);
    }

    public void init(int hours, int currentHour, int sunRiseHour){
        this.currentHour = currentHour;
        this.sunRiseHour = sunRiseHour;
        Timber.e("current hour: "+currentHour);

        pointsCount = hours + 2;
        this.hours = hours;
        Timber.e("points: "+ pointsCount);
        arcPoints = new PointF[pointsCount];
        for (int i = 0; i < pointsCount; ++i){
            arcPoints[i] = new PointF();
        }
        initSunPath();

        invalidate();

        Timber.e("end");
    }

    private void createIndexedPoints(){
        for (int i = sunRiseHour, j = 0; i < sunRiseHour + hours; ++i, ++j){
            indexedPoints.put(i, arcPoints[j]);
        }
    }


    public void startAnim() {

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




}
