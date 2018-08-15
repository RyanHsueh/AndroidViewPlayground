package com.example.ryanhsueh.androidviewplayground.chartView;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.ryanhsueh.androidviewplayground.R;
import com.example.ryanhsueh.androidviewplayground.customView.ViewBase;

/**
 * Created by ryanhsueh on 2018/8/14
 */
public class ChartBackgroundDaily extends ChartBackground {

    private static final int DAILY_TIME_INDEX_12AM_BEGIN = 0;
    private static final int DAILY_TIME_INDEX_6AM = 6;
    private static final int DAILY_TIME_INDEX_12PM = 12;
    private static final int DAILY_TIME_INDEX_6PM = 18;
    private static final int DAILY_TIME_INDEX_12AM_END = 24;

    private String[] mTimeIndexTexts;

    public ChartBackgroundDaily(ViewBase viewBase, Canvas canvas,
                                Rect rect, float indexTextSize,
                                int backgroundColor, ChartType chartType,
                                int yMax, int yMin) {
        super(viewBase, canvas, rect, indexTextSize, backgroundColor, chartType, yMax, yMin);
        mPaintTextTimeIndex.setTextAlign(Paint.Align.CENTER);

        Resources res = viewBase.getResources();
        String am = res.getString(R.string.am);
        String pm = res.getString(R.string.pm);

        switch (mChartType) {
//            case SLEEP:
//            case V_SCORE:
//                mTimeIndexTexts = new String[] {
//                        "3pm",
//                        "9pm",
//                        "3am",
//                        "9am",
//                        "3pm",
//                };
//                break;
            default:
                mTimeIndexTexts = new String[] {
                        "12"+am,
                        "6"+am,
                        "12"+pm,
                        "6"+pm,
                        "12"+am,
                };
        }

    }

    @Override
    protected void drawTimeIndex(int countOfData, int index, float left, float bottom) {

        String text = null;

        // draw time index of Daily activity
        if (countOfData == 144) {
            if (index==DAILY_TIME_INDEX_12AM_BEGIN*6) {
                text = mTimeIndexTexts[0];
            } else if (index==DAILY_TIME_INDEX_6AM*6) {
                text = mTimeIndexTexts[1];
            } else if (index==DAILY_TIME_INDEX_12PM*6) {
                text = mTimeIndexTexts[2];
            } else if (index==DAILY_TIME_INDEX_6PM*6) {
                text = mTimeIndexTexts[3];
            } else if (index==DAILY_TIME_INDEX_12AM_END*6) {
                text = mTimeIndexTexts[4];
            }
        } else {
            if (index==DAILY_TIME_INDEX_12AM_BEGIN) {
                text = mTimeIndexTexts[0];
            } else if (index==DAILY_TIME_INDEX_6AM) {
                text = mTimeIndexTexts[1];
            } else if (index==DAILY_TIME_INDEX_12PM) {
                text = mTimeIndexTexts[2];
            } else if (index==DAILY_TIME_INDEX_6PM) {
                text = mTimeIndexTexts[3];
            } else if (index==DAILY_TIME_INDEX_12AM_END) {
                text = mTimeIndexTexts[4];
            }
        }

        drawTextTime(text, left, bottom + mTimeIndexTextSize);
    }
}
