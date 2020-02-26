package com.example.ryanhsueh.androidviewplayground.customView

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.Color
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator


class PulseAnimationView: View {
    private var mX: Float = 0.0f
    private var mY: Float = 0.0f

    private val mPaint = Paint()
    private var radius: Float = 0.0f
        set(value) {
            field = value
            mPaint.color = (Color.GREEN + value.toInt() / COLOR_ADJUSTER)
            invalidate()
        }

    private val pulseAnimatorSet = AnimatorSet()

    companion object {
        const val COLOR_ADJUSTER: Int = 5
        const val ANIMATION_DURATION: Long = 4000L
        const val ANIMATION_DELAY: Long = 1000L
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.actionMasked == MotionEvent.ACTION_DOWN) {
            mX = event.x
            mY = event.y

            pulseAnimatorSet.apply {
                if (isRunning) {
                    cancel()
                }
                start()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val growAnimator = ObjectAnimator.ofFloat(this, "radius", 0.0f, width.toFloat()).apply {
            duration = ANIMATION_DURATION
            interpolator = LinearInterpolator()
        }
        val shrinkAnimator = ObjectAnimator.ofFloat(this, "radius", width.toFloat(), 0.0f).apply {
            duration = ANIMATION_DURATION
            interpolator = LinearOutSlowInInterpolator()
            startDelay = ANIMATION_DELAY
        }
        val repeatAnimator = ObjectAnimator.ofFloat(this, "radius", 0.0f, width.toFloat()).apply {
            startDelay = ANIMATION_DELAY
            duration = ANIMATION_DURATION
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
        }

        pulseAnimatorSet.apply {
            play(growAnimator).before(shrinkAnimator)
            play(repeatAnimator).after(shrinkAnimator)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawCircle(mX, mY, radius, mPaint)
        }
    }
}