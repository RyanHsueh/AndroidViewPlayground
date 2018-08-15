package com.example.ryanhsueh.androidviewplayground.chartView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.ryanhsueh.androidviewplayground.customView.ViewBase;

/**
 * Created by ryanhsueh on 2018/8/13
 */
public abstract class ChartBackground {

    protected static final int STROKE_BACKGROUND_LINE = 2;

    protected ViewBase mViewBase;
    protected Canvas mCanvas;

    protected float mCanvasWidth;
    protected float mCanvasHeight;

    protected Rect mCanvasRect;
    protected float mCanvasMiddleY;

    protected float mTimeIndexTextSize;

    protected ChartType mChartType;

    protected Paint mPaintTextTimeIndex;
    protected Paint mPaintLineDivider;

    protected int mCountOfData;

    private int mYMax, mYMin;

    public ChartBackground(ViewBase viewBase, Canvas canvas,
                           Rect rect, float indexTextSize,
                           int backgroundColor, ChartType chartType,
                           int yMax, int yMin) {
        mViewBase = viewBase;
        mCanvas = canvas;
        mCanvasRect = new Rect(rect);
        mTimeIndexTextSize = indexTextSize;

        mCanvasWidth = rect.width();
        mCanvasHeight = rect.height();
        mCanvasMiddleY = rect.bottom - mCanvasHeight/2;

        mChartType = chartType;

        mYMax = yMax;
        mYMin = yMin;

        initPaints(backgroundColor);
    }

    private void initPaints(int backgroundColor) {
        mPaintTextTimeIndex = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPaintTextTimeIndex.setTextSize(mTimeIndexTextSize);
        mPaintTextTimeIndex.setColor(backgroundColor);

        mPaintLineDivider = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPaintLineDivider.setStyle(Paint.Style.STROKE);
        mPaintLineDivider.setStrokeWidth(STROKE_BACKGROUND_LINE);
        mPaintLineDivider.setColor(backgroundColor);
    }

    protected Paint getRectPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    public void drawChartBackground(final int countOfData, boolean showDateDivider) {
        if (mCanvas == null) {
            return;
        }

        mCountOfData = countOfData;

        float section = mCanvasWidth/countOfData;

        float[] point = new float[4];
        for (int i=0; i<=countOfData; ++i) {
            float left = mCanvasRect.left + i*section;
            float right = left;

            // draw divider lines
            if (showDateDivider) {
                point[0] = left;
                point[1] = mCanvasRect.top;
                point[2] = right;
                point[3] = mCanvasRect.bottom - 20;
                mCanvas.drawLines(point, mPaintLineDivider);
            }

            drawTimeIndex(countOfData, i, left, mCanvasRect.bottom);
        }

        drawHorizontalDivider(0);
    }

    abstract void drawTimeIndex(int countOfData, int index, float left, float bottom);

    protected void drawYAxisIndex(int index, float right, float top) {
        if (mYMax > 0) {
            String text = "";
            if (index == 0) {
                text = Utility.getStringOfNumber(mYMax);
            } else if (index == 4) {
                text = String.valueOf(mYMin);
            }
            mPaintTextTimeIndex.setTextAlign(Paint.Align.LEFT);
            drawTextTime(text, right, top);
        }
    }

    protected void drawTextTime(String text, float left, float bottom) {
        if (text != null) {
            mCanvas.drawText(text, left, bottom, mPaintTextTimeIndex);
        }
    }

    protected void drawHorizontalDivider(int xStartOffset) {
        float[] point = new float[4];

        float left = mCanvasRect.left + xStartOffset;
        float right = mCanvasRect.right;

        point[0] = left;
        point[2] = right;

        // draw top line
        point[1] = STROKE_BACKGROUND_LINE + mTimeIndexTextSize;
        point[3] = point[1];
        mCanvas.drawLines(point, mPaintLineDivider);
        drawYAxisIndex(0, left-10, point[1]); // TOP

        point[1] = mCanvasMiddleY - mCanvasHeight/4;
        point[3] = point[1];
        mCanvas.drawLines(point, mPaintLineDivider);
        drawYAxisIndex(1, left-10, point[1]);

        // draw middle line
        point[1] = mCanvasMiddleY;
        point[3] = point[1];
        mCanvas.drawLines(point, mPaintLineDivider);
        drawYAxisIndex(2, left-10, point[1]); // MIDDLE

        point[1] = mCanvasMiddleY + mCanvasHeight/4;
        point[3] = point[1];
        mCanvas.drawLines(point, mPaintLineDivider);
        drawYAxisIndex(3, left-10, point[1]);

        // draw bottom line
        point[1] = mCanvasRect.bottom;
        point[3] = point[1];
        mCanvas.drawLines(point, mPaintLineDivider);
        drawYAxisIndex(4, left-10, point[1]); // BOTTOM

    }

}