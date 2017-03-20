package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.github.zachdeibert.operationmanipulation.R;
import com.github.zachdeibert.operationmanipulation.model.Operator;

public class OperationListView extends LinearLayout {
    private void init(AttributeSet attrs, int defStyleAttr) {
        for (Operator op : Operator.VALUES) {
            OperatorView view = new OperatorView(getContext(), null, R.attr.operatorBtnStyle);
            view.setOperator(op);
            addView(view);
        }
    }

    public OperationListView(Context context) {
        super(context);
        init(null, 0);
    }

    public OperationListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public OperationListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }
}
