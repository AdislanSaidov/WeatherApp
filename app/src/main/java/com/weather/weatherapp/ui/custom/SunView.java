package com.weather.weatherapp.ui.custom;

import android.animation.ValueAnimator;
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
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.weather.weatherapp.R;

import org.jetbrains.annotations.NotNull;

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
    private static final int TEXT_BOTTOM_MARGIN = (int) (10 * DP);
    private static final int TEXT_SIZE = (int) (14 * DP);
    private static final int SUN_RADIUS = (int) (8 * DP);

    private String sunRise = "";
    private String sunSet = "";
    private Bitmap sunBitmap;
    private Path path;
    private PathMeasure pm;


    private final RectF oval = new RectF();
    private int pointsCount;
    private int hours;
    private int currentHour;
    private int currentTimePoint;
    private final Map<Integer, Integer> indexedPoints = new HashMap<>();
    private int sunRiseHour;
    private int min;
    private float[][] points;
    private final float[][] boundPoints = new float[2][2];

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

    }

    private int findPart(int min){
        if(min > 40)
            return 2;
        if(min > 20)
            return 1;
        return 0;
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
        float[] xyCoordinate = new float[2];
        for (int i = 0; i < pointsCount; ++i) {
            pm.getPosTan(pathLength * i / (pointsCount -1), xyCoordinate, null);
            Timber.e("Point #%d = (%.0f,%.0f)", i + 1, xyCoordinate[0], xyCoordinate[1]);
            if( i == 0 || i == pointsCount){
                boundPoints[i] = new float[]{xyCoordinate[0], xyCoordinate[1]};
            }else{
                points[i] = new float[]{xyCoordinate[0], xyCoordinate[1]};
            }
        }
        createIndexedTimePoints();
//        Timber.e(Arrays.toString(arcPoints));
    }

    private void drawSun(@NotNull Canvas canvas) {
        int bitmapCenterHeight = sunBitmap.getHeight() / 2;
        int bitmapCenterWidth = sunBitmap.getWidth() / 2;

        float x = points[currentTimePoint][0];
        float y = points[currentTimePoint][1];
        canvas.drawBitmap(sunBitmap, x-bitmapCenterWidth, y-bitmapCenterHeight, paint);
    }

    private void drawLights(@NotNull Canvas canvas) {
        canvas.drawCircle(boundPoints[0][0], boundPoints[0][1], SUN_RADIUS, paint);
        canvas.drawCircle(boundPoints[1][0], boundPoints[1][1], SUN_RADIUS, paint);
    }

    public void init(int hours, int currentHour, int sunRiseHour, int min){
        this.currentHour = currentHour;
        this.sunRiseHour = sunRiseHour;
        this.min = min;
        Timber.e("current hour: "+currentHour);

        pointsCount = hours * 3;
        this.hours = hours;
        Timber.e("points: "+ pointsCount);

        points = new float[hours * 3][hours * 3];

        initSunPath();

        invalidate();

        Timber.e("end");
    }

    private void createIndexedTimePoints(){
        for (int i = sunRiseHour, j = 0; i < pointsCount; ++i, ++j){
            indexedPoints.put(i, j);
        }
    }


    public void startAnim() {

        int index = indexedPoints.get(currentHour);
        int timeIndex = index * 3 + findPart(min);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, timeIndex);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            currentTimePoint = ((int) animation.getAnimatedValue())+1;
            invalidate();
        });
        valueAnimator.start();
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


    static class SavedState extends BaseSavedState {

        String sunRise;
        String sunSet;
        int pointCount;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            sunRise = in.readString();
            sunSet = in.readString();
            pointCount = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(sunRise);
            out.writeString(sunSet);
            out.writeInt(pointCount);
        }

        public static final Parcelable.Creator<SunView.SavedState> CREATOR = new Parcelable.Creator<SunView.SavedState>() {
            public SunView.SavedState createFromParcel(Parcel in) {
                return new SunView.SavedState(in);
            }

            public SunView.SavedState[] newArray(int size) {
                return new SunView.SavedState[size];
            }
        };
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SunView.SavedState state = new SunView.SavedState(superState);
        state.sunRise = this.sunRise;
        state.sunSet = this.sunSet;
        state.pointCount = this.pointsCount;
        Timber.e("onsave");
        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SunView.SavedState savedState = (SunView.SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.sunRise = savedState.sunRise;
        this.sunSet = savedState.sunSet;
        this.pointsCount = savedState.pointCount;
        invalidate();
        Timber.e("restore");
//        setChecked(isChecked);
    }


}
