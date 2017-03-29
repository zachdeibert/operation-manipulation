package com.github.zachdeibert.operationmanipulation.view.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.github.zachdeibert.operationmanipulation.view.animation.RainbowAnimator;

public class RainbowView extends View {
    private int width;
    private int height;
    private String text;
    private Paint paint;

    private String getText() {
        return text;
    }

    void setText(String text) {
        this.text = text;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        paint.setARGB(255, 255, 255, 255);
        int textSize = height - 20;
        int width;
        String text = getText();
        while (true) {
            paint.setTextSize(textSize);
            width = (int) paint.measureText(text);
            if (width >= this.width) {
                textSize -= 10;
                continue;
            }
            break;
        }
        canvas.drawText(text, (this.width - width) / 2, (textSize + height) / 2, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = widthMeasureSpec & 0x3FFFFFFF;
        setMeasuredDimension(width, height);
    }

    private void init() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels / 4;
        new RainbowAnimator(this).start();
        paint = new Paint();
    }

    public RainbowView(Context context) {
        super(context);
        init();
    }

    public RainbowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RainbowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
