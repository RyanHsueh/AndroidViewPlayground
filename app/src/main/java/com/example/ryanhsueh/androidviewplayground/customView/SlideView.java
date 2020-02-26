package com.example.ryanhsueh.androidviewplayground.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

public class SlideView extends View {

    private int lastX;
    private int lastY;
    private Scroller mScroller;

    public SlideView(Context context) {
        super(context);
    }

    public SlideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                int offsetX = x - lastX;
                int offsetY = y - lastY;

                //调用layout方法来重新放置它的位置
//                layout(getLeft()+offsetX, getTop()+offsetY,
//                       getRight()+offsetX , getBottom()+offsetY);


                //对left和right进行偏移
                offsetLeftAndRight(offsetX);
                //对top和bottom进行偏移
                offsetTopAndBottom(offsetY);


                //使用LayoutParams
//                LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + offsetX;
//                layoutParams.topMargin = getTop() + offsetY;
//                setLayoutParams(layoutParams);


                //使用MarginLayoutParams
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + offsetX;
//                layoutParams.topMargin = getTop() + offsetY;
//                setLayoutParams(layoutParams);


                //使用scrollBy (移動的對象是手機螢幕，而非View物件)
//                ((View)getParent()).scrollBy(-offsetX,-offsetY);


                break;
            default:
        }


        return true;
    }

    public void smoothScrollTo(int destX,int destY){
        int scrollX = getScrollX();
        int dx = destX - scrollX;

        int scrollY = getScrollY();
        int dy = destY - scrollY;

        //2000秒内滑dx, dy
        mScroller.startScroll(scrollX, scrollY, dx,dy,2000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        // View::draw()會呼叫到computeScroll()
        if(mScroller.computeScrollOffset()){
            ((View) getParent()).scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            //通过不断的重绘不断的调用computeScroll方法
            invalidate();
        }

    }

}
