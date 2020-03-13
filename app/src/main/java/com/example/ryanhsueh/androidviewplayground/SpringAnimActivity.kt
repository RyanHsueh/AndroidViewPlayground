package com.example.ryanhsueh.androidviewplayground

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import kotlinx.android.synthetic.main.activity_spring_anim.*


class SpringAnimActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spring_anim)
    }

    override fun onResume() {
        super.onResume()

        startSpringAnimation(img_view)
    }

    private fun startSpringAnimation(view: View) { // create an animation for your view and set the property you want to animate
        // create a spring with desired parameters
        val springForce = SpringForce().apply {
            // can also be passed directly in the constructor
            finalPosition = 200f
            // optional, default is STIFFNESS_MEDIUM
            stiffness = SpringForce.STIFFNESS_MEDIUM
            // optional, default is DAMPING_RATIO_MEDIUM_BOUNCY
            dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        }

        val animation = SpringAnimation(view, SpringAnimation.Y).apply {
            // set your animation's spring
            spring = springForce
            // animate!
            start()
        }

    }
}
