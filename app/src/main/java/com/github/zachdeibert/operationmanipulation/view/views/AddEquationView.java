package com.github.zachdeibert.operationmanipulation.view.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.github.zachdeibert.operationmanipulation.view.activities.GameActivity;
import com.github.zachdeibert.operationmanipulation.view.animation.RainbowAnimator;

public class AddEquationView extends View implements View.OnTouchListener {
    private static final String MESSAGE = "Get More Equations";
    private int width;
    private int height;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ((GameActivity) getContext()).onRequestEquation();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setARGB(255, 255, 255, 255);
        int textSize = height - 20;
        int width;
        while (true) {
            paint.setTextSize(textSize);
            width = (int) paint.measureText(MESSAGE);
            if (width >= this.width) {
                textSize -= 10;
                continue;
            }
            break;
        }
        canvas.drawText(MESSAGE, (this.width - width) / 2, (textSize + height) / 2, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = widthMeasureSpec & 0x3FFFFFFF;
        setMeasuredDimension(width, height);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels / 4;
        setOnTouchListener(this);
        new RainbowAnimator(this).start();
    }

    public AddEquationView(Context context) {
        super(context);
        init(null, 0);
    }

    public AddEquationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public AddEquationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }
}
