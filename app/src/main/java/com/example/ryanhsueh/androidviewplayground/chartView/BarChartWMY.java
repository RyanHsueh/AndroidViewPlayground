package com.example.ryanhsueh.androidviewplayground.chartView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.ryanhsueh.androidviewplayground.customView.ViewBase;

/**
 * Created by ryanhsueh on 2018/8/14
 */
public class BarChartWMY extends BarChartAdapter {

    private boolean mNeedShowGoal;

    private Paint mPaintGoal;

    public BarChartWMY(ViewBase viewBase, Canvas canvas, Rect rect, int themeColor, boolean showGoal) {
        super(viewBase, canvas, rect, themeColor);
        mNeedShowGoal = showGoal;
    }

    @Override
    void initPaint() {
        super.initPaint();

        mPaintGoal = getLinePaint();
        mPaintGoal.setColor(mThemeColor);
//        mPaintGoal.setPathEffect(new DashPathEffect(new float[]{2, 4}, 0));
    }

    @Override
    void draw(int indexEnd) {
        drawBars(indexEnd);

        if (mNeedShowGoal) {
            drawGoalLine();
        }

        if (indexEnd < mBarChartData.size()) {
            updateDraw();
        }

        mViewBase.invalidate();
    }

    @Override
    public void drawBars(int indexEnd) {
        for (int i=0 ; i<indexEnd ; i++) {

            float[] points = getBarPoints(i);

            if (mNeedShowGoal) {
                int value = mBarChartData.getBarValue(i);
                if (value >= mBarChartData.getDailyGoal()) {
                    mCanvas.drawLines(points, mPaintDefault);
                } else {
                    mCanvas.drawLines(points, mPaintGoalNotHit);
                }
            } else {
                mCanvas.drawLines(points, mPaintDefault);
            }
        }
    }

    private void drawGoalLine() {
        if (mBarChartData.getDailyGoal() > mBarChartData.getMaxBar()) {
            return;
        }

        float max = mBarChartData.getMaxBar();
        float y = mBarBottom - mBarMaxHeight * mBarChartData.getDailyGoal()/max - mBarStrokeWidth/2;

        float[] points = new float[]{
                mCanvasRect.left, y,
                mCanvasRect.right, y};

        mCanvas.drawLines(points, mPaintGoal);
    }
}
