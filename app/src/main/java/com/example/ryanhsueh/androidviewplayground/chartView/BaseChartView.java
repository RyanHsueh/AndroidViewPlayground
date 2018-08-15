package com.example.ryanhsueh.androidviewplayground.chartView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.example.ryanhsueh.androidviewplayground.R;
import com.example.ryanhsueh.androidviewplayground.chartView.data.ChartData;
import com.example.ryanhsueh.androidviewplayground.customView.ViewBase;

/**
 * Created by ryanhsueh on 2018/8/13
 */
public abstract class BaseChartView extends ViewBase {
    private static final String TAG = BaseChartView.class.getSimpleName();

    protected int mWidth;
    protected int mHeight;
    protected Rect mCanvasRect;

    protected int mCanvasPaddingTop;
    protected int mCanvasPaddingBottom;
    protected int mCanvasPaddingLR;

    protected Paint mPaintBase;

    protected Bitmap mBmpBackground;
    protected Bitmap mBmpChart;
    protected Bitmap mBmpCrossHair;

    private ChartBackground mTrendBackground;
    private ChartCrossHair mChartCrossHair;

    protected ChartTimeUnit mChartTimeUnit = ChartTimeUnit.DAILY;
    protected ChartType mChartType = ChartType.UNKNOWN;
    protected ChartData mChartData;

    protected int mThemeColor;
    private int mBackgroundColor;

    private boolean mEnableAnimation = false;

    abstract void updateChart(boolean animation);

    public BaseChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mThemeColor = Color.WHITE;
        mBackgroundColor = getResources().getColor(R.color.app_grey_blue);

        if (attrs!=null) {
            // must get attributes (attrs) in constructor,
            // it will not be valid anymore after construction finished
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseChartView);
            mThemeColor = a.getColor(R.styleable.BaseChartView_mainColor, mThemeColor);
            mBackgroundColor = a.getColor(R.styleable.BaseChartView_backgroundColor, mBackgroundColor);
            a.recycle();
        }

        initPaintBase();
    }

    private void initPaintBase() {
        mPaintBase = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPaintBase.setStyle(Paint.Style.STROKE);
        mPaintBase.setStrokeWidth(2);
        mPaintBase.setColor(getResources().getColor(R.color.grey_75));
    }

    public void setThemeColor(int resColor) {
        mThemeColor = getResources().getColor(resColor);
    }

    public void enableAnimation(boolean enable) {
        mEnableAnimation = enable;
    }

    public void setChartTimeUnit(ChartTimeUnit timeUnit) {
        mChartTimeUnit = timeUnit;
    }

    public void setChartType(ChartType chartType) {
        mChartType = chartType;
    }

    public void setChartData(ChartData chartData) {
        mChartData = chartData;
    }

    public void draw() {
        updateBackground();

        if (mChartData != null) {
            updateChart(mEnableAnimation);
        }
    }

    private void updateBackground() {
        Log.d(TAG, "updateBackground > mWidth : " + mWidth + ", mHeight : " + mHeight);

        if (mWidth <= 0 || mHeight <= 0) {
            return;
        }

        // clean canvas
        Canvas canvas = new Canvas(mBmpBackground);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

//        LogTool.d(TAG, "updateBackground > mChartData max : " + mChartData.max());

        int max = (mChartData != null ? mChartData.max() : 0);
        int min = 0;

        switch (mChartTimeUnit) {
            case DAILY:
                mTrendBackground = new ChartBackgroundDaily(
                        this, canvas, mCanvasRect, mCanvasPaddingTop,
                        mBackgroundColor, mChartType, max, min);
                break;

            case WEEKLY:
                mTrendBackground = new ChartBackgroundWeekly(
                        this, canvas, mCanvasRect, mCanvasPaddingTop,
                        mBackgroundColor, mChartType, max, min);
                break;

            case MONTHLY:
                mTrendBackground = new ChartBackgroundMonthly(
                        this, canvas, mCanvasRect, mCanvasPaddingTop,
                        mBackgroundColor, mChartType, max, min);
                break;

            case YEARLY:
                mTrendBackground = new ChartBackgroundYearly(
                        this, canvas, mCanvasRect, mCanvasPaddingTop,
                        mBackgroundColor, mChartType, max, min);
                break;
        }

        int countOfData = (mChartData==null ? 0 : mChartData.size());
        mTrendBackground.drawChartBackground(countOfData, false);
    }

    public void enableCrossHair(boolean enable, ChartCrossHair.OnTouchListener listener) {
        if (enable) {
            initCrossHair(listener);
        } else {
            if (mChartCrossHair != null) {
                mChartCrossHair.release();
                mChartCrossHair = null;
            }
        }
    }

    private void initCrossHair(ChartCrossHair.OnTouchListener listener) {
        if (mWidth <= 0 || mHeight <= 0) {
            return;
        }

        // clean canvas
        Canvas canvas = new Canvas(mBmpCrossHair);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        if (mChartCrossHair != null) {
            mChartCrossHair.release();
            mChartCrossHair = null;
        }

        mChartCrossHair = new ChartCrossHair(this, canvas, mCanvasRect);
        mChartCrossHair.setOnTouchHintListener(listener);
    }

    @Override
    public void releaseResource() {
        releaseBitmap(mBmpBackground);
        releaseBitmap(mBmpChart);
        releaseBitmap(mBmpCrossHair);

        if (mChartCrossHair != null) {
            mChartCrossHair.release();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w>0 && h>0) {
            // create again if needed
            if (mWidth!=w || mHeight!=h) {
                mBmpBackground = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                mBmpChart = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                mBmpCrossHair = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            }
            mWidth = w;
            mHeight = h;

            mCanvasPaddingLR = mWidth / 20;
            mCanvasPaddingBottom = (int) (mHeight * 0.16f);
            mCanvasPaddingTop = (int) (mCanvasPaddingBottom*0.36f);

            mCanvasRect = new Rect(
                    mCanvasPaddingLR,
                    mCanvasPaddingTop,
                    mWidth - mCanvasPaddingLR,
                    mHeight - mCanvasPaddingBottom);

            draw();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBmpBackground != null) {
            canvas.drawBitmap(mBmpBackground, 0, 0, mPaintBase);
        }
        if (mBmpChart != null) {
            canvas.drawBitmap(mBmpChart, 0, 0, mPaintBase);
        }
        if (mBmpCrossHair != null) {
            canvas.drawBitmap(mBmpCrossHair, 0, 0, mPaintBase);
        }

        super.onDraw(canvas);
    }
}
