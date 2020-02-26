package com.example.ryanhsueh.androidviewplayground.customView;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.ryanhsueh.androidviewplayground.R;

public class MyTitleBar extends LinearLayout {

    private TextView mTextTitle;

    private int mTitleBarColor;
    private int mTextColor;
    private String mText;

    private ITitleBarAction titleBarAction;

    public MyTitleBar(Context context) {
        super(context);
        initView(context);
    }

    public MyTitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyTitleBar);
        if (typedArray != null) {
            mTitleBarColor = typedArray.getColor(R.styleable.MyTitleBar_title_bg, Color.BLUE);
            mTextColor = typedArray.getColor(R.styleable.MyTitleBar_title_text_color, Color.WHITE);
            mText = typedArray.getString(R.styleable.MyTitleBar_title_text);
            typedArray.recycle();
        }

        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.title_bar, this);

        View view = findViewById(R.id.titlebar_layout);
        view.setBackgroundColor(mTitleBarColor);

        mTextTitle = findViewById(R.id.text_title);
        mTextTitle.setTextColor(mTextColor);
        mTextTitle.setText(mText);

        findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();

                if (titleBarAction != null) {
                    titleBarAction.onBackClicked();
                }
            }
        });

        findViewById(R.id.btn_edit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "What do you want to edit", Toast.LENGTH_SHORT).show();

                if (titleBarAction != null) {
                    titleBarAction.onEditClicked();
                }
            }
        });
    }

    public void setTitleBarAction(ITitleBarAction listener) {
        titleBarAction = listener;
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTextTitle.setText(title);
        }
    }

}
