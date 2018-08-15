package com.example.ryanhsueh.androidviewplayground.chartView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.example.ryanhsueh.androidviewplayground.R;
import com.example.ryanhsueh.androidviewplayground.customView.ViewBase;

/**
 * Created by ryanhsueh on 2018/8/13
 */
public class ChartCrossHair {

    private static final String TAG = ChartCrossHair.class.getSimpleName();

    protected ViewBase mViewBase;
    protected Canvas mCanvas;

    protected float mCanvasWidth;
    protected float mCanvasHeight;

    protected Rect mCanvasRect;

    private float mHintHeight;
    private float mTextSize;
    private Bitmap mBmpHintBackground;

    private Paint mPaintTouchHint;
    private Paint mPaintHintText;

    private float mPercentage;

    private OnTouchListener mOnTouchListener;
    public interface OnTouchListener {
        String onTouchUpdated(float percentage, int x);
    }
    public void setOnTouchHintListener(OnTouchListener listener) {
        mOnTouchListener = listener;
    }

    public ChartCrossHair(ViewBase viewBase, Canvas canvas, Rect rect) {
        mViewBase = viewBase;
        mCanvas = canvas;

        mCanvasRect = rect;
        mCanvasWidth = rect.width();
        mCanvasHeight = rect.height();

        mHintHeight = mCanvasHeight / 6f;
        mTextSize = mHintHeight * 0.4f;

        mBmpHintBackground = BitmapFactory.decodeResource(viewBase.getResources(), R.drawable.touch_hint_bg);

        initPaint();

        mViewBase.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int iMask = event.getAction() & MotionEvent.ACTION_MASK;
                boolean isActionUp = (iMask & MotionEvent.ACTION_UP) == MotionEvent.ACTION_UP;
                // boolean bMove = (iMask & MotionEvent.ACTION_MOVE) == MotionEvent.ACTION_MOVE;

                float percentage = getTouchPercentage((int) event.getX(), isActionUp);

                String hintText = "";
                if (percentage != -1.0f) {
                    if (mOnTouchListener != null) {
                        hintText = mOnTouchListener.onTouchUpdated(percentage, (int) event.getX());
                    }
                }

                if (mPercentage != percentage) {
                    mPercentage = percentage;
                    draw(hintText);
                }

                return true;
            }
        });
    }

    public void release() {
        mBmpHintBackground.recycle();
        mBmpHintBackground = null;

        mViewBase.setOnTouchListener(null);
    }

    private void initPaint() {
        mPaintTouchHint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPaintTouchHint.setStyle(Paint.Style.STROKE);
        mPaintTouchHint.setStrokeWidth(2);
        mPaintTouchHint.setColor(mViewBase.getResources().getColor(R.color.black));
        mPaintTouchHint.setPathEffect(new DashPathEffect(new float[]{2, 4}, 0));

        mPaintHintText = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPaintHintText.setTextAlign(Paint.Align.CENTER);
        mPaintHintText.setColor(mViewBase.getResources().getColor(R.color.white));
        mPaintHintText.setTextSize(mTextSize);
    }

    private float getTouchPercentage(final int x, boolean isActionUp) {
//        LogTool.d(TAG, "getTouchPercentage > x : " + x);

        float percentage;
        if (isActionUp) {
            percentage = -1.0f;
        } else {
            percentage = (x - mCanvasRect.left) / mCanvasWidth;
            if (percentage > 1.0f) {
                percentage = 1.0f;
            } else if (percentage < 0.0f) {
                percentage = 0.0f;
            }
        }

        return percentage;
    }

    private void draw(final String hintText) {
//        LogTool.d(TAG, "drawTouchHint > mPercentage : " + mPercentage);

        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        if (mPercentage>=0.0 && mPercentage<=1.0) {
            float left = mCanvasRect.left + mCanvasWidth * mPercentage;
            mCanvas.drawLine(left, mCanvasRect.top, left, mCanvasRect.bottom, mPaintTouchHint);

            float textWidth = hintText.length() * mTextSize;

            float fTimeHintWidth = textWidth * 0.6f;
            if (fTimeHintWidth > mCanvasWidth) {
                fTimeHintWidth = mCanvasWidth;
            }

            left -= fTimeHintWidth / 2;
            left = getLeftBound(left, fTimeHintWidth);
            drawTimeHintBitmap(left, fTimeHintWidth);

            left += fTimeHintWidth/2;
            drawHintText(left, hintText);
        }

        mViewBase.invalidate();
    }

    private float getLeftBound(float left, float fTimeHintWidth) {
        if (left < mCanvasRect.left) {
            left = mCanvasRect.left;
        } else if (left+fTimeHintWidth > mCanvasRect.right)
            left = mCanvasRect.right - fTimeHintWidth;

        return left;
    }

    private void drawTimeHintBitmap(float fLeft, float fTimeHintWidth) {
        RectF rectDst = new RectF(fLeft, mCanvasRect.top, fLeft + fTimeHintWidth, mHintHeight);
        mCanvas.drawBitmap(mBmpHintBackground, null, rectDst, mPaintTouchHint);
    }

    private void drawHintText(float left, String hintText) {
        float x = left;
        float y = mCanvasRect.top + mTextSize;

        mCanvas.drawText(hintText, x, y, mPaintHintText);
    }

}
