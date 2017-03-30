package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.zachdeibert.operationmanipulation.R;
import com.github.zachdeibert.operationmanipulation.controller.EquationSolver;
import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.ExpressionItem;
import com.github.zachdeibert.operationmanipulation.model.Operand;
import com.github.zachdeibert.operationmanipulation.model.Operator;
import com.github.zachdeibert.operationmanipulation.util.CollectionUtils;
import com.github.zachdeibert.operationmanipulation.util.Log;
import com.github.zachdeibert.operationmanipulation.view.activities.GameActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EquationContainer extends ViewGroup implements View.OnDragListener {
    static class SavedState extends BaseSavedState {
        private static class Point implements Serializable {
            private final float x;
            private final float y;

            @NonNull
            private PointF toPoint() {
                return new PointF(x, y);
            }

            private Point(@NonNull PointF pt) {
                x = pt.x;
                y = pt.y;
            }
        }

        static class SerializableState implements Serializable {
            @NonNull
            private final EquationView.SavedState.SerializableState equationState;
            private final Map<Operator, Point> operators;

            @NonNull
            public SavedState toState() {
                return new SavedState(equationState.toState(), operators);
            }

            private SerializableState(@NonNull SavedState state) {
                equationState = state.equationState.toSerializable();
                operators = state.operators;
            }
        }

        @NonNull
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @NonNull
            @Override
            public SavedState createFromParcel(@NonNull Parcel source) {
                return new SavedState(source);
            }

            @NonNull
            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[0];
            }
        };

        private final EquationView.SavedState equationState;
        private final Map<Operator, Point> operators;

        public void apply(@NonNull EquationContainer view) {
            view.view.onRestoreInstanceState(equationState);
            view.view.getEquation().clear();
            for (Operator op : operators.keySet()) {
                OperatorView v = new OperatorView(view.getContext(), null, R.attr.operatorBtnStyle);
                v.setOperator(op);
                view.addOperator(v, operators.get(op).toPoint());
            }
        }

        @NonNull
        public SerializableState toSerializable() {
            return new SerializableState(this);
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            equationState.writeToParcel(out, flags);
            out.writeMap(operators);
        }

        private SavedState(EquationView.SavedState equationState, Map<Operator, Point> operators) {
            super(EMPTY_STATE);
            this.equationState = equationState;
            this.operators = operators;
        }

        private SavedState(Parcelable state, @NonNull EquationContainer view) {
            super(state);
            equationState = (EquationView.SavedState) view.view.onSaveInstanceState();
            operators = new HashMap<>();
            for (View v : view.operators.keySet()) {
                if (v instanceof OperatorView) {
                    OperatorView op = (OperatorView) v;
                    operators.put(op.getOperator(), new Point(view.operators.get(v)));
                }
            }
        }

        private SavedState(@NonNull Parcel parcel) {
            super(parcel);
            equationState = EquationView.SavedState.CREATOR.createFromParcel(parcel);
            operators = CollectionUtils.checkedAssignment(parcel.readHashMap(ClassLoader.getSystemClassLoader()), Operator.class, Point.class);
        }
    }

    private Map<View, PointF> operators;
    private EquationView view;

    public Equation getEquation() {
        return view.getEquation();
    }

    public void setEquation(@NonNull Equation equation) {
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
                    final View view = getChildAt(i);
                    view.setOnTouchListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                    if (view instanceof TextView) {
                        ((TextView) view).setTextColor(Color.BLACK);
                        view.setBackgroundColor(Color.TRANSPARENT);
                    }
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

    public boolean isUnsolved() {
        return view.getBackgroundColor() != 0xFF00FF00;
    }

    private void addOperator(@NonNull OperatorView view, @NonNull PointF center) {
        Equation eq = getEquation();
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

    private void addOperator(@NonNull OperatorView view, @NonNull Rect rect) {
        PointF center = new PointF(rect.exactCenterX() / getWidth(), rect.exactCenterY() / getHeight());
        addOperator(view, center);
    }

    public void removeOperator(@NonNull OperatorView view) {
        operators.remove(view);
        removeView(view);
        getEquation().removeOperator(view.getOperator());
        check();
    }

    @NonNull
    @Override
    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Parcelable state) {
        super.onRestoreInstanceState(state);
        ((SavedState) state).apply(this);
    }

    @Override
    public boolean onDrag(View v, @NonNull DragEvent event) {
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

    private void init() {
        operators = new HashMap<>();
        view = new EquationView(getContext());
        addView(view);
        setOnDragListener(this);
    }

    public EquationContainer(Context context) {
        super(context);
        init();
    }

    public EquationContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EquationContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
