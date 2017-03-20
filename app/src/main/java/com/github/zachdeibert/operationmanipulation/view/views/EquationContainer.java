package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.zachdeibert.operationmanipulation.R;
import com.github.zachdeibert.operationmanipulation.controller.EquationSolver;
import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.ExpressionItem;
import com.github.zachdeibert.operationmanipulation.model.Operand;
import com.github.zachdeibert.operationmanipulation.model.Operator;
import com.github.zachdeibert.operationmanipulation.view.activities.GameActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquationContainer extends ViewGroup implements View.OnDragListener {
    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[0];
            }
        };

        private final Parcelable equationState;
        private final Map<Operator, PointF> operators;

        public void apply(EquationContainer view) {
            view.view.onRestoreInstanceState(equationState);
            view.view.getEquation().clear();
            for (Operator op : operators.keySet()) {
                OperatorView v = new OperatorView(view.getContext(), null, R.attr.operatorBtnStyle);
                v.setOperator(op);
                view.addOperator(v, operators.get(op));
            }
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            equationState.writeToParcel(out, flags);
            out.writeMap(operators);
        }

        private SavedState(Parcelable state, EquationContainer view) {
            super(state);
            equationState = view.view.onSaveInstanceState();
            operators = new HashMap<>();
            for (View v : view.operators.keySet()) {
                if (v instanceof OperatorView) {
                    OperatorView op = (OperatorView) v;
                    operators.put(op.getOperator(), view.operators.get(v));
                }
            }
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            equationState = EquationView.SavedState.CREATOR.createFromParcel(parcel);
            operators = parcel.readHashMap(ClassLoader.getSystemClassLoader());
        }
    }

    private Map<View, PointF> operators;
    private EquationView view;

    public Equation getEquation() {
        return view.getEquation();
    }

    public void setEquation(Equation equation) {
        view.setEquation(equation);
    }

    private void check() {
        Equation eq = getEquation();
        Log.i("EquationContainer", eq.toString());
        if (EquationSolver.isComplete(eq)) {
            if (EquationSolver.isCorrect(eq)) {
                this.view.setBackgroundColor(0xFF00FF00);
                setOnDragListener(null);
                for (int i = 0; i < getChildCount(); ++i) {
                    getChildAt(i).setOnTouchListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                }
                ((GameActivity) getContext()).onSolvedEquation();
            } else {
                this.view.setBackgroundColor(0xFFFF0000);
                ((GameActivity) getContext()).onFailedSolution();
            }
        } else {
            this.view.setBackgroundColor(0xFFFFFFFF);
        }
    }

    public boolean isSolved() {
        return this.view.getBackgroundColor() == 0xFF00FF00;
    }

    public void addOperator(OperatorView view, PointF center) {
        Equation eq = getEquation();
        List<Operand> operands = new ArrayList<>();
        for (ExpressionItem item : eq.getLeftSide()) {
            if (item instanceof Operand) {
                operands.add((Operand) item);
            }
        }
        if ((center.x * 2 * (2 + eq.getOperandCount()) + 1) / 2 > eq.getOperandCount() + 1) {
            return;
        }
        view.makeUnique();
        Map<Float, ExpressionItem> expression = new HashMap<>();
        int i = 0;
        float charWidth = 1f / (2 + eq.getOperandCount());
        for (ExpressionItem item : eq.getLeftSide()) {
            if (item instanceof Operand) {
                expression.put(charWidth * (i++ + 0.5f), item);
            }
        }
        for (View v : operators.keySet()) {
            expression.put(operators.get(v).x, ((OperatorView) v).getOperator());
        }
        ExpressionItem closestItem = null;
        float closestDistance = Float.MAX_VALUE;
        for (Float position : expression.keySet()) {
            float distance = center.x - position;
            if (distance > 0 && distance < closestDistance) {
                closestItem = expression.get(position);
                closestDistance = distance;
            }
        }
        if (closestItem == null) {
            eq.insertOperatorBefore(eq.getLeftSide()[0], view.getOperator());
        } else {
            eq.insertOperatorAfter(closestItem, view.getOperator());
        }
        operators.put(view, center);
        addView(view);
        view.setVisibility(VISIBLE);
        check();
    }

    public void addOperator(OperatorView view, Rect rect) {
        PointF center = new PointF(rect.exactCenterX() / getWidth(), rect.exactCenterY() / getHeight());
        addOperator(view, center);
    }

    public void removeOperator(OperatorView view) {
        operators.remove(view);
        removeView(view);
        getEquation().removeOperator(view.getOperator());
        check();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        ((SavedState) state).apply(this);
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
        int w = r - l;
        int h = b - t;
        for (View view : operators.keySet()) {
            PointF p = operators.get(view);
            view.measure(w, h);
            int mh = view.getMeasuredHeight() / 2;
            int mw = view.getMeasuredWidth() / 2;
            view.layout((int) (p.x * w - mw), (int) (p.y * h - mh), (int) (p.x * w + mw), (int) (p.y * h + mh));
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
