package com.example.ryanhsueh.androidviewplayground;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

}
