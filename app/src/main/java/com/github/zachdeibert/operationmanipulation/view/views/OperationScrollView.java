package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

public class OperationScrollView extends HorizontalScrollView implements View.OnDragListener {
    private int scrollX;

    @Override
    public boolean onDrag(View v, DragEvent event) {
        OperatorView view = (OperatorView) event.getLocalState();
        ViewGroup owner = (ViewGroup) view.getParent();
        if (event.getAction() == DragEvent.ACTION_DROP) {
            if (owner instanceof EquationContainer) {
                ((EquationContainer) owner).removeOperator(view);
            }
        } else if (event.getAction() == DragEvent.ACTION_DRAG_LOCATION && owner instanceof OperationListView) {
            if (view.getStartDragX() == 0 && view.getStartDragY() == 0) {
                view.setStartDragEvent(event);
                scrollX = getScrollX();
            } else {
                double dx = view.getStartDragX() - event.getX();
                double dy = view.getStartDragY() - event.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                double angle = Math.atan2(dy, dx);
                if (Math.abs(angle / Math.PI) < 1 && distance > getWidth() / 10) {
                    smoothScrollTo((int) (scrollX + dx), 0);
                }
            }
        }
        return true;
    }

    private void init() {
        setOnDragListener(this);
    }

    public OperationScrollView(Context context) {
        super(context);
        init();
    }

    public OperationScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OperationScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
