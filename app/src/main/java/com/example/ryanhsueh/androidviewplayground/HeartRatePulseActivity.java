package com.example.ryanhsueh.androidviewplayground;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ryanhsueh.androidviewplayground.customView.HeartRatePulseView;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * Created by ryanhsueh on 2018/7/17
 */
public class HeartRatePulseActivity extends AppCompatActivity {
    private static final String TAG = HeartRatePulseActivity.class.getSimpleName();

    private static final int MSG_UPDATE_HR = 1;

    private TextView mTextHR;
    private HeartRatePulseView mPulseView;

    private PulseHandler mHandler = new PulseHandler(this);
    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_pulse);

        mTextHR = findViewById(R.id.text_hr);
        mPulseView = findViewById(R.id.hr_pulse_view);

        findViewById(R.id.btn_pulse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPulse1();
            }
        });

        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPulse1();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPulse1();
    }

    private void startPulse1() {
        if (mThread == null) {
            mThread = new Thread(mRunnable1, "Pulse Thread");
            mThread.start();
        }
    }

    private void stopPulse1() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }

        mHandler.removeMessages(MSG_UPDATE_HR);

        mTextHR.setText(String.valueOf(0));
        mPulseView.stopPulse();
    }


    private void startPulse2() {
        if (mRunnable2 == null) {
            mRunnable2 = new PulseRunnable2(mHandler);
            mThread = new Thread(mRunnable2, "Pulse Thread");
            mThread.start();
        }
    }

    private void stopPulse2() {
        if (mRunnable2 != null) {
            mRunnable2.cancel();
            mRunnable2 = null;
            mThread = null;
        }

        mHandler.removeMessages(MSG_UPDATE_HR);

        mTextHR.setText(String.valueOf(0));
        mPulseView.stopPulse();
    }

    public void updateHR(int hr) {
        mTextHR.setText(String.valueOf(hr));
        mPulseView.startPulse(hr);
    }


    // Using static inner class and WeakReference to prevent memory leak
    private static class PulseHandler extends Handler {
        private WeakReference<HeartRatePulseActivity> activityReference;

        PulseHandler(HeartRatePulseActivity activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_HR:
                    int hr = msg.arg1;
                    activityReference.get().updateHR(hr);
                    break;
            }
        }

    }


    // Interrupt thread 1 : Thread.interrupt
    private PulseRunnable1 mRunnable1 = new PulseRunnable1(mHandler);
    private static class PulseRunnable1 extends PulseRunnable {

        PulseRunnable1(Handler handler) {
            super(handler);
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {

                updateHR();

                Log.d(TAG, "run: PulseRunnable 1 is running");

                sleep(1000);
            }

            Log.d(TAG, "run: PulseRunnable 1 is canceled");
        }

    };


    // Interrupt thread 2 : volatile boolean
    private PulseRunnable2 mRunnable2;
    private static class PulseRunnable2 extends PulseRunnable {

        private volatile boolean isCancel;

        PulseRunnable2(Handler handler) {
            super(handler);
        }

        @Override
        public void run() {
            while (!isCancel) {
                updateHR();

                Log.d(TAG, "run: PulseRunnable 2 is running");

                sleep(1000);
            }

            Log.d(TAG, "run: PulseRunnable 2 is canceled");
        }

        void cancel() {
            isCancel = true;
        }

    }

    private abstract static class PulseRunnable implements Runnable {
        private Handler handler;

        PulseRunnable(Handler handler) {
            this.handler = handler;
        }

        protected void updateHR() {
            Random random = new Random();
            int hr = random.nextInt(180);

            Message msg = new Message();
            msg.what = MSG_UPDATE_HR;
            msg.arg1 = hr;
            handler.sendMessage(msg);
        }

        protected void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                // interrupt it !!
                Thread.currentThread().interrupt();
            }
        }
    }


}
