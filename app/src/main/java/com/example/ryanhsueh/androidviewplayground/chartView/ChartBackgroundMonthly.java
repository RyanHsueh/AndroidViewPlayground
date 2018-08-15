package com.example.ryanhsueh.androidviewplayground.chartView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.ryanhsueh.androidviewplayground.customView.ViewBase;

/**
 * Created by ryanhsueh on 2018/8/14
 */
public class ChartBackgroundMonthly extends ChartBackground {

    public ChartBackgroundMonthly(ViewBase viewBase, Canvas canvas,
                                  Rect rect, float indexTextSize,
                                  int backgroundColor, ChartType chartType,
                                  int yMax, int yMin) {
        super(viewBase, canvas, rect, indexTextSize, backgroundColor, chartType, yMax, yMin);
        mPaintTextTimeIndex.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void drawTimeIndex(int countOfData, int index, float left, float bottom) {
        if (index >= mCountOfData) {
            return;
        }

        String text = null;
        if (index == 0) {
            text = "1";
        } else if (index == 4) {
            text = "5";
        } else if (index == 9) {
            text = "10";
        } else if (index == 14) {
            text = "15";
        } else if (index == 19) {
            text = "20";
        } else if (index == 24) {
            text = "25";
        } else if (index == 29) {
            text = "30";
        }

        left += mCanvasWidth / mCountOfData / 2;
        drawTextTime(text, left, bottom + mTimeIndexTextSize);
    }
}
