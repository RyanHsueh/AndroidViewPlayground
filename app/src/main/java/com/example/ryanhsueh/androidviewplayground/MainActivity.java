package com.example.ryanhsueh.androidviewplayground;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startSlideViewActivity(View view) {
        Intent intent = new Intent(this, SlideViewActivity.class);
        startActivity(intent);
    }
    public void startPulseAnimationActivity(View view) {
        Intent intent = new Intent(this, PulseAnimationActivity.class);
        startActivity(intent);
    }
    public void startSpringAnimActivity(View view) {
        Intent intent = new Intent(this, SpringAnimActivity.class);
        startActivity(intent);
    }
    public void startRectViewActivity(View view) {
        Intent intent = new Intent(this, RectViewActivity.class);
        startActivity(intent);
    }
    public void startTitleBarActivity(View view) {
        Intent intent = new Intent(this, TitleBarActivity.class);
        startActivity(intent);
    }
    public void startViewGroupActivity(View view) {
        Intent intent = new Intent(this, ViewGroupActivity.class);
        startActivity(intent);
    }
    public void startHeartRatePulseActivity(View view) {
        Intent intent = new Intent(this, HeartRatePulseActivity.class);
        startActivity(intent);
    }
    public void startBarChartActivity(View view) {
        Intent intent = new Intent(this, BarChartActivity.class);
        startActivity(intent);
    }

}
