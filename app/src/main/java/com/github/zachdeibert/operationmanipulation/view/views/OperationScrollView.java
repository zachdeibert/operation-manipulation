package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

public class OperationScrollView extends HorizontalScrollView implements View.OnDragListener {
    @Override
    public boolean onDrag(View v, DragEvent event) {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            OperatorView view = (OperatorView) event.getLocalState();
            ViewGroup owner = (ViewGroup) view.getParent();
            if (owner instanceof EquationContainer) {
                ((EquationContainer) owner).removeOperator(view);
            }
        }
        return true;
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        setOnDragListener(this);
    }

    public OperationScrollView(Context context) {
        super(context);
        init(null, 0);
    }

    public OperationScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public OperationScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }
}
