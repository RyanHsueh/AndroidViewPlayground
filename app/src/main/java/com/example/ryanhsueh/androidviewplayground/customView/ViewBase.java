package com.example.ryanhsueh.androidviewplayground.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ryanhsueh on 2018/7/17
 */
public abstract class ViewBase extends View {
    private static final String TAG = ViewBase.class.getSimpleName();

    protected Context mContext;

    public ViewBase(Context context) {
        super(context);
        mContext = context;
    }

    public ViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ViewBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public final <E extends View> E findView(int id) {
        return (E) findViewById(id);
    }

    // force the derived class, to implement the resource releasing function, when it is detached

    protected abstract void releaseResource();

    // some helper function
    protected void releaseBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

}
