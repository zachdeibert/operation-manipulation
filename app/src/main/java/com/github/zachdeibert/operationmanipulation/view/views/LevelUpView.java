package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.util.AttributeSet;

public class LevelUpView extends RainbowView {
    private void init(AttributeSet attrs, int defStyleAttr) {
        setText("Level Up!");
    }

    public LevelUpView(Context context) {
        super(context);
        init(null, 0);
    }

    public LevelUpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LevelUpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }
}
