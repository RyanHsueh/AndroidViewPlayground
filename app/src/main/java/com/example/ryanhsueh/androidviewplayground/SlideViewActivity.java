package com.example.ryanhsueh.androidviewplayground;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ryanhsueh.androidviewplayground.customView.SlideView;

public class SlideViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_view);


        SlideView slideView = findViewById(R.id.customview);


        //使用属性动画使view滑动
//        ObjectAnimator.ofFloat(customView,"translationX",0,300).setDuration(2000).start();

        //使用屬性動畫，AnimatorSet
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(slideView, "translationX", 0.0f, 200.0f, 0.0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(slideView, "scaleX", 1.0f, 2.0f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(slideView, "rotationX", 0.0f, 90.0f, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000);
        animatorSet.play(animator1).with(animator2).after(animator3);
        animatorSet.start();

        //使用View动画使view滑动
//      customView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));

        //使用Scroll来进行平滑移动
//        customView.smoothScrollTo(-400,0);
    }
}
