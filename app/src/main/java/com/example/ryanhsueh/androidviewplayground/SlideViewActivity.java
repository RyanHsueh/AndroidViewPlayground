package com.example.ryanhsueh.androidviewplayground;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ryanhsueh.androidviewplayground.customView.SlideView;

public class SlideViewActivity extends AppCompatActivity {

    boolean isUp = false;

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



        ImageView clickView = findViewById(R.id.clickview);
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUp) down(v); else up(v);
                Toast.makeText(SlideViewActivity.this, "Click it !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void up(View view) {
        isUp = true;

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationX", 0.0f, 300.0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -500.0f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 2.0f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 2.0f);
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(view, "rotationY", 0.0f, 90.0f, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.play(animator1).with(animator2).with(animator3).with(animator4).before(animator5);
        animatorSet.start();
    }

    private void down(View view) {
        isUp = false;

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationX", 300.0f, 0.0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "translationY", -500.0f, 0.0f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "scaleX", 2.5f, 1.0f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(view, "scaleY", 2.5f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.play(animator1).with(animator2).with(animator3).with(animator4);
        animatorSet.start();
    }
}
