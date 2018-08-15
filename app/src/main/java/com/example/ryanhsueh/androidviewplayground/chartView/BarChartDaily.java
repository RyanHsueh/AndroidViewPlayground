package com.example.ryanhsueh.androidviewplayground.chartView;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.ryanhsueh.androidviewplayground.customView.ViewBase;

/**
 * Created by ryanhsueh on 2018/8/14
 */
public class BarChartDaily extends BarChartAdapter {

    public BarChartDaily(ViewBase viewBase, Canvas canvas, Rect rect, int themeColor) {
        super(viewBase, canvas, rect, themeColor);
    }

    @Override
    void draw(int indexEnd) {
        drawBars(indexEnd);

        if (indexEnd < mBarChartData.size()) {
            updateDraw();
        }

        mViewBase.invalidate();
    }

    @Override
    public void drawBars(int indexEnd) {
        for (int i=0 ; i<indexEnd ; i++) {

            float[] point = getBarPoints(i);

            mCanvas.drawLines(point, mPaintDefault);
        }
    }
}
