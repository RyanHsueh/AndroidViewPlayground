package com.example.ryanhsueh.androidviewplayground.chartView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.ryanhsueh.androidviewplayground.R;
import com.example.ryanhsueh.androidviewplayground.customView.ViewBase;

/**
 * Created by ryanhsueh on 2018/8/14
 */
public class ChartBackgroundYearly extends ChartBackground {

    private final int[] RES_STRINGS_TIME_INDEX = new int[] {
            R.string.january_short,
            R.string.february_short,
            R.string.march_short,
            R.string.april_short,
            R.string.may_short,
            R.string.june_short,
            R.string.july_short,
            R.string.august_short,
            R.string.september_short,
            R.string.october_short,
            R.string.november_short,
            R.string.december_short,
    };

    public ChartBackgroundYearly(ViewBase viewBase, Canvas canvas,
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

        String text = mViewBase.getResources().getString(RES_STRINGS_TIME_INDEX[index]);

        left += mCanvasWidth / mCountOfData / 2;
        drawTextTime(text, left, bottom + mTimeIndexTextSize);
    }
}
