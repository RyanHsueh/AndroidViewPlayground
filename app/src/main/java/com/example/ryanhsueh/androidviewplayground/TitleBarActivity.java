package com.example.ryanhsueh.androidviewplayground;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ryanhsueh.androidviewplayground.customView.ITitleBarAction;
import com.example.ryanhsueh.androidviewplayground.customView.MyTitleBar;

public class TitleBarActivity extends AppCompatActivity implements ITitleBarAction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_bar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        MyTitleBar titleBar = findViewById(R.id.title_bar);
        titleBar.setTitleBarAction(this);
    }

    @Override
    public void onBackClicked() {

    }

    @Override
    public void onEditClicked() {

    }
}
