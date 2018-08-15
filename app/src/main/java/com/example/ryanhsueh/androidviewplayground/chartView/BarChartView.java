package com.example.ryanhsueh.androidviewplayground.chartView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;

import com.example.ryanhsueh.androidviewplayground.chartView.data.BarChartData;

/**
 * Created by ryanhsueh on 2018/8/14
 */
public class BarChartView extends BaseChartView implements ChartCrossHair.OnTouchListener {

    private BarChartAdapter mBarChartAdapter;

    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void updateChart(boolean animation) {
        if (mWidth <= 0 || mHeight <= 0)
            return;

        // clean canvas
        Canvas canvas = new Canvas(mBmpChart);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        if (mBarChartAdapter != null) {
            mBarChartAdapter.stopDrawing();
        }

        if (mChartTimeUnit == ChartTimeUnit.DAILY) {
            mBarChartAdapter = new BarChartDaily(this, canvas, mCanvasRect, mThemeColor);
        } else {
            boolean showGoal = (mChartTimeUnit != ChartTimeUnit.YEARLY);
            mBarChartAdapter = new BarChartWMY(this, canvas, mCanvasRect, mThemeColor, showGoal);
        }

        enableCrossHair(true, this);

        mBarChartAdapter.initChartData(mChartData);
        mBarChartAdapter.startDrawing(animation);
    }

    @Override
    public String onTouchUpdated(float percentage, int x) {
        int size = mChartData.size();
        int index = (int)(size * percentage);
        if (index == size) {
            index--;
        }

        String hintText = "";
        if (mChartData instanceof BarChartData) {
            BarChartData barData = (BarChartData) mChartData;
            int value = barData.getBarValue(index);

            hintText = String.valueOf(Utility.getStringOfNumber(value));
        }

        if (mChartTimeUnit == mChartTimeUnit.DAILY) {
            int dayTimeInMin = (int)(1440 * percentage);
            long timeMillis = Utility.getMillisForIntTime(dayTimeInMin);
            String time = DateTimeFormatter.millisecondToString(getContext(), timeMillis, DateTimeFormatter.DateFormat.HMa);
            hintText += " (" + time + ")";
        }

        return hintText;
    }
}
