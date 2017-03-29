package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.github.zachdeibert.operationmanipulation.view.activities.GameActivity;

public class AddEquationView extends RainbowView implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, @NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ((GameActivity) getContext()).onRequestEquation();
        }
        return true;
    }

    private void init() {
        setOnTouchListener(this);
        setText("Get More Equations");
    }

    public AddEquationView(Context context) {
        super(context);
        init();
    }

    public AddEquationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AddEquationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
