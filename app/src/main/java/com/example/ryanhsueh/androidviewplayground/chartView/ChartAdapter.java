package com.example.ryanhsueh.androidviewplayground.chartView;

import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;

import com.example.ryanhsueh.androidviewplayground.chartView.data.ChartData;
import com.example.ryanhsueh.androidviewplayground.customView.ViewBase;

/**
 * Created by ryanhsueh on 2018/8/14
 */
public abstract class ChartAdapter {

    private static final int MSG_UPDATE_CHART = 1;

    private static final int DELAY_START_DRAW = 200;
    private static final int DELAY_UPDATE_DRAW = 20;

    protected static final float BAR_RADIUS = 8.0f;

    protected ViewBase mViewBase;
    protected Canvas mCanvas;

    protected float mCanvasWidth;
    protected float mCanvasHeight;

    protected Rect mCanvasRect;
    protected int mCanvasMiddleY;

    protected int mThemeColor;

    private int mIndexToDraw;
    private int mIndexEnd;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_UPDATE_CHART:
                    mIndexToDraw++;
                    draw(mIndexToDraw);
                    break;
            }
        }
    };

    public ChartAdapter(ViewBase viewBase, Canvas canvas, Rect rect, int themeColor) {
        mViewBase = viewBase;
        mCanvas = canvas;
        mCanvasRect = new Rect(rect);

        mCanvasWidth = rect.width();
        mCanvasHeight = rect.height();
        mCanvasMiddleY = (int) (rect.bottom - mCanvasHeight/2);

        mThemeColor = themeColor;

        initPaint();
    }

    public void startDrawing(boolean animationShow) {
        if (animationShow) {
            mIndexToDraw = 1;
        } else {
            mIndexToDraw = mIndexEnd;
        }

        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_CHART, DELAY_START_DRAW);
    }

    public void stopDrawing() {
        mHandler.removeMessages(MSG_UPDATE_CHART);
    }

    protected Paint getLinePaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        return paint;
    }

    protected Paint getRectPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    protected Paint getRoundPathPaint() {
        /*
            How to draw smooth / rounded path
            refers :  http://stackoverflow.com/questions/7608362/how-to-draw-smooth-rounded-path
         */
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setDither(true);                    // set the dither to true
        paint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
        paint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        paint.setPathEffect(new CornerPathEffect(BAR_RADIUS));   // set the path effect when they join.

        return paint;
    }

    protected void updateDraw() {
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_CHART, DELAY_UPDATE_DRAW);
    }

    protected void initChartData(ChartData chartData) {
        mIndexEnd = chartData.size() - 1;
    }

    abstract void initPaint();
    abstract void draw(int indexToDraw);

}
