package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.graphics.Rect;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.zachdeibert.operationmanipulation.R;
import com.github.zachdeibert.operationmanipulation.controller.EquationSolver;
import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.ExpressionItem;
import com.github.zachdeibert.operationmanipulation.model.Operand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquationContainer extends ViewGroup implements View.OnDragListener {
    private Map<View, Rect> operators;
    private EquationView view;

    public Equation getEquation() {
        return view.getEquation();
    }

    public void setEquation(Equation equation) {
        view.setEquation(equation);
    }

    public void addOperator(OperatorView view, Rect rect) {
        addView(view);
        operators.put(view, rect);
        invalidate();
        view.setVisibility(VISIBLE);
        Equation eq = getEquation();
        List<Operand> operands = new ArrayList<>();
        for (ExpressionItem item : eq.getLeftSide()) {
            if (item instanceof Operand) {
                operands.add((Operand) item);
            }
        }
        int section = ((rect.left + rect.right) * (2 + eq.getOperandCount()) / getWidth() + 1) / 2;
        if (section == 0) {
            eq.insertOperatorBefore(operands.get(0), view.getOperator());
        } else {
            eq.insertOperatorAfter(operands.get(section - 1), view.getOperator());
        }
        Log.i("EquationContainer", eq.toString());
        if (EquationSolver.isCorrect(eq)) {
            this.view.setBackgroundColor(0xFF00FF00);
        }
    }

    public void removeOperator(OperatorView view) {
        operators.remove(view);
        removeView(view);
        getEquation().removeOperator(view.getOperator());
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            OperatorView view = (OperatorView) event.getLocalState();
            ViewGroup owner = (ViewGroup) view.getParent();
            int x = (int) event.getX();
            int y = (int) event.getY();
            int w = view.getWidth() / 2;
            int h = view.getHeight() / 2;
            if (owner instanceof EquationContainer) {
                ((EquationContainer) owner).removeOperator(view);
            } else if (owner instanceof OperationListView) {
                Parcelable state = view.onSaveInstanceState();
                view = new OperatorView(getContext(), null, R.attr.operatorBtnStyle);
                view.onRestoreInstanceState(state);
            }
            addOperator(view, new Rect(x - w, y - h, x + w, y + h));
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        view.measure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        view.layout(0, 0, r - l, b - t);
        for (View view : operators.keySet()) {
            Rect rect = operators.get(view);
            view.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        operators = new HashMap<>();
        view = new EquationView(getContext());
        addView(view);
        setOnDragListener(this);
    }

    public EquationContainer(Context context) {
        super(context);
        init(null, 0);
    }

    public EquationContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public EquationContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }
}
