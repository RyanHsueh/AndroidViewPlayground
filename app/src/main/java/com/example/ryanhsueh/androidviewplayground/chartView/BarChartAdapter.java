package com.example.ryanhsueh.androidviewplayground.chartView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.ryanhsueh.androidviewplayground.R;
import com.example.ryanhsueh.androidviewplayground.chartView.ChartAdapter;
import com.example.ryanhsueh.androidviewplayground.chartView.data.BarChartData;
import com.example.ryanhsueh.androidviewplayground.chartView.data.ChartData;
import com.example.ryanhsueh.androidviewplayground.customView.ViewBase;

/**
 * Created by ryanhsueh on 2018/8/14
 */
public abstract class BarChartAdapter extends ChartAdapter implements NeedDrawBars {

    protected BarChartData mBarChartData;

    protected float mWidthSection;
    protected float mBarStrokeWidth;
    protected float mBarMaxHeight;
    protected float mBarMinHeight;
    protected float mBarBottom;

    protected Paint mPaintDefault;
    protected Paint mPaintGoalNotHit;
    protected Paint mPaintTodayGoalHit;
    protected Paint mPaintTodayGoalNotHit;

    public BarChartAdapter(ViewBase viewBase, Canvas canvas, Rect rect, int themeColor) {
        super(viewBase, canvas, rect, themeColor);
    }

    @Override
    void initPaint() {
        mPaintDefault = getRoundPathPaint();
        mPaintDefault.setColor(mThemeColor);

        mPaintGoalNotHit = getRoundPathPaint();
        mPaintGoalNotHit.setColor(mViewBase.getResources().getColor(R.color.bar_default_grey));

        mPaintTodayGoalHit = getRoundPathPaint();
        mPaintTodayGoalHit.setColor(mThemeColor);

        mPaintTodayGoalNotHit = getRoundPathPaint();
        mPaintTodayGoalNotHit.setColor(mViewBase.getResources().getColor(R.color.white));
    }

    @Override
    public void initChartData(ChartData chartData) {
        super.initChartData(chartData);

        if (chartData instanceof BarChartData) {
            mBarChartData = (BarChartData)chartData;
        }

        mWidthSection = mCanvasWidth/chartData.size();
        mBarStrokeWidth = mWidthSection/3;
        mBarMaxHeight = mCanvasHeight - mBarStrokeWidth - 2;
        mBarMinHeight = BAR_RADIUS;
        mBarBottom = mCanvasRect.bottom - mBarStrokeWidth/2;

        mPaintDefault.setStrokeWidth(mBarStrokeWidth);
        mPaintGoalNotHit.setStrokeWidth(mBarStrokeWidth);
        mPaintTodayGoalHit.setStrokeWidth(mBarStrokeWidth);
        mPaintTodayGoalNotHit.setStrokeWidth(mBarStrokeWidth);
    }

    protected float[] getBarPoints(int barIndex) {
        float max = mBarChartData.getMaxBar();
        int value = mBarChartData.getBarValue(barIndex);

        float left = mCanvasRect.left + mWidthSection/2 + barIndex*mWidthSection;
        float right = left;
        float bottom = mBarBottom;
        float top = bottom;

        if (value > 0) {
            top -= (mBarMaxHeight * value/max);
        } else {
            top -= mBarMinHeight;
        }

//        LogTool.d("RYAN", "drawBars > " + barIndex + " > (" + bottom + "," + top + ")");
//        LogTool.d("RYAN", "drawBars > mBarMaxHeight * value/max : " + (mBarMaxHeight * value / max));

        return new float[]{left, top, right, bottom};
    }

}

