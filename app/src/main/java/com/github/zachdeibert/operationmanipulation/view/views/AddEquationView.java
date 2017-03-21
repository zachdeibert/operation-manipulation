package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.github.zachdeibert.operationmanipulation.view.activities.GameActivity;

public class AddEquationView extends RainbowView implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ((GameActivity) getContext()).onRequestEquation();
        }
        return true;
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        setOnTouchListener(this);
        setText("Get More Equations");
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
