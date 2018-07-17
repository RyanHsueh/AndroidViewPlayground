package com.example.ryanhsueh.androidviewplayground.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import com.example.ryanhsueh.androidviewplayground.R;

/**
 * Created by ryanhsueh on 2018/7/17
 */
public class HeartRatePulseView extends ViewBase {
    private static final String TAG = HeartRatePulseView.class.getSimpleName();

    private static final int MSG_SHOW_PULSE = 1;
    private static final int MSG_HIDE_PULSE = 2;

    private static final int SIZE_OF_POINT = 50;

    private final float[] HR_PULSE_PATTERNS = new float[] {
            0.02f, //1
            0,
            0,
            0,
            0,
            0,
            0,
            0.05f,
            0,
            0,      //10
            0,
            0,
            0.08f,
            0,
            0.5f, // 15
            1,    // 16
            0.6f,
            0.2f,
            -0.1f,
            -0, //20
            -0.05f,
            0,
            0,
            0.1f,
    };

    private final float[] ZERO_PULSE_PATTERNS = new float[] {
            0,  //1
            0,
            0,
            0,
            0.02f, //5
            0,
            0,
            0,
            0,
            0,      //10
            0,
            0.1f,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,     //20
            0,
            0,
            0,
            0.05f,

    };

    private float mWidth;
    private float mHeight;
    private Rect mCanvasRect;
    private float mOffsetX;
    private float mBaseLineY;
    private float mMaxHeightPulse;

    private Bitmap mBmpHRPulse;

    private Paint mPaintBase;
    private Paint mPaintLinePath;

    private int mThemeColor = Color.RED;

    private int mIndexShowing = 1;
    private int mIndexHiding = 1;

    private float[] mPulseMatrix = new float[HR_PULSE_PATTERNS.length];
    private boolean mIsAnimationDrawing = false;

    private int mHeartRate = 0;

    public HeartRatePulseView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeartRatePulseView);
        mThemeColor = typedArray.getColor(R.styleable.HeartRatePulseView_themeColor, Color.RED);
        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        mPaintBase = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPaintBase.setStyle(Paint.Style.STROKE);
        mPaintBase.setStrokeWidth(2);
        mPaintBase.setColor(Color.GRAY);

        mPaintLinePath = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPaintLinePath.setStyle(Paint.Style.STROKE);
        mPaintLinePath.setStrokeWidth(10);
        mPaintLinePath.setColor(mThemeColor);
    }

    public void startPulse(int heartRate) {
        if (mIsAnimationDrawing) {
            return;
        }

        mHeartRate = heartRate;
        mPulseMatrix = getPulseMatrix();

        mHandler.removeMessages(MSG_SHOW_PULSE);
        showPulse();
    }

    public void stopPulse() {
        mIsAnimationDrawing = false;

        mHandler.removeMessages(MSG_SHOW_PULSE);
        mHandler.removeMessages(MSG_HIDE_PULSE);

        startPulse(0);
    }

    private float[] getPulseMatrix() {
        int size = HR_PULSE_PATTERNS.length;
        float[] pulseMatrix;
        if (mHeartRate > 0) {
            pulseMatrix = HR_PULSE_PATTERNS.clone();
        }else {
            pulseMatrix = ZERO_PULSE_PATTERNS.clone();
        }

        for (int i=0 ; i<size ; i++) {
            pulseMatrix[i] *= mMaxHeightPulse;
        }

        return pulseMatrix;
    }

    private void showPulse() {
        if (mWidth <= 0 || mHeight <= 0) {
            return;
        }

        if (mBmpHRPulse == null) {
            return;
        }

        mIsAnimationDrawing = true;

        // clean canvas
        Canvas canvas = new Canvas(mBmpHRPulse);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        float x = mCanvasRect.left;
        int pulseSize = mPulseMatrix.length;

        // draw divider lines
        Path path = new Path();
        path.moveTo(x, mBaseLineY);

        int indexPulse = 0;
        for (int i=1; i<=mIndexShowing; ++i) {
            x += mOffsetX;

            if (i > 8 && indexPulse<pulseSize) {
                float y = mBaseLineY - mPulseMatrix[indexPulse];
                indexPulse++;

                path.lineTo(x, y);
            } else  {
                path.lineTo(x, mBaseLineY);
            }

        }

        canvas.drawPath(path, mPaintLinePath);

        if (mIndexShowing < SIZE_OF_POINT) {
            mHandler.sendEmptyMessageDelayed(MSG_SHOW_PULSE, 25);
        } else {
            mIndexShowing = 1;
            mHandler.sendEmptyMessageDelayed(MSG_HIDE_PULSE, 25);
        }

        invalidate();
    }

    private void hidePulse() {
        if (mWidth <= 0 || mHeight <= 0) {
            return;
        }

        if (mBmpHRPulse == null) {
            return;
        }

        // clean canvas
        Canvas canvas = new Canvas(mBmpHRPulse);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        float x = mCanvasRect.right;
        int pulseSize = mPulseMatrix.length;
        int indexPulse = pulseSize-1;

        // draw divider lines
        Path path = new Path();
        path.moveTo(x, mBaseLineY);

        for (int i=SIZE_OF_POINT-1; i>mIndexHiding; --i) {
            x -= mOffsetX;

            if (i < 33 && indexPulse>=0) {
                float y = mBaseLineY - mPulseMatrix[indexPulse];
                indexPulse--;

                path.lineTo(x, y);
            } else  {
                path.lineTo(x, mBaseLineY);
            }

        }

        canvas.drawPath(path, mPaintLinePath);

        if (mIndexHiding < SIZE_OF_POINT) {
            mHandler.sendEmptyMessageDelayed(MSG_HIDE_PULSE, 25);
        } else {
            mHeartRate = 0;
            mIndexHiding = 1;
            mIsAnimationDrawing = false;
            mHandler.sendEmptyMessageDelayed(MSG_SHOW_PULSE, 3000);
        }

        invalidate();
    }

    @Override
    public void releaseResource() {
        releaseBitmap(mBmpHRPulse);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (w > 0 && h > 0) {
            // create again if needed
            if (mWidth!=w || mHeight!=h) {
                mBmpHRPulse = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            }

            mWidth = w;
            mHeight = h;

            mOffsetX = mWidth/SIZE_OF_POINT;
            mBaseLineY = mHeight*0.7f;
            mMaxHeightPulse = mBaseLineY;

            mCanvasRect = new Rect(
                    0,
                    0,
                    (int)mWidth,
                    (int)mHeight);

            startPulse(0);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBmpHRPulse != null) {
            canvas.drawBitmap(mBmpHRPulse, 0, 0, mPaintBase);
        }

        super.onDraw(canvas);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_SHOW_PULSE:
                    mIndexShowing++;
                    showPulse();
                    break;

                case MSG_HIDE_PULSE:
                    mIndexHiding++;
                    hidePulse();
                    break;
            }
        }
    };
}
