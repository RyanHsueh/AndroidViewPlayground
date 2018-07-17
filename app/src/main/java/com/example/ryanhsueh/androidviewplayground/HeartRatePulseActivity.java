package com.example.ryanhsueh.androidviewplayground;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ryanhsueh.androidviewplayground.customView.HeartRatePulseView;

import java.util.Random;

public class HeartRatePulseActivity extends AppCompatActivity {
    private static final String TAG = HeartRatePulseActivity.class.getSimpleName();

    private static final int MSG_UPDATE_HR = 1;

    private TextView mTextHR;
    private HeartRatePulseView mPulseView;

    private PulseHandler mHandler = new PulseHandler();
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
                startPulse();
            }
        });

        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPulse();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPulse();
    }

    private void startPulse() {
        if (mThread == null) {
            mThread = new Thread(mPulseRunnable, "Pulse Thread");
        }
        mThread.start();
    }

    private void stopPulse() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }

        mHandler.removeMessages(MSG_UPDATE_HR);

        mTextHR.setText(String.valueOf(0));
        mPulseView.stopPulse();
    }

    private class PulseHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_HR:
                    int hr = msg.arg1;
                    mTextHR.setText(String.valueOf(hr));
                    mPulseView.startPulse(hr);
                    break;
            }
        }

    };

    private Runnable mPulseRunnable = new Runnable() {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {

                Random random = new Random();
                int hr = random.nextInt(180);

                Message msg = new Message();
                msg.what = MSG_UPDATE_HR;
                msg.arg1 = hr;
                mHandler.sendMessage(msg);

                Log.d(TAG, "run: PulseRunnable is running");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // interrupt it !!
                    Thread.currentThread().interrupt();
                }
            }

            Log.d(TAG, "run: PulseRunnable is canceled");
        }

    };


}
