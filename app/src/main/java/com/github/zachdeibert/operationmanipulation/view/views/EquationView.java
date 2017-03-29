package com.github.zachdeibert.operationmanipulation.view.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.*;
import android.view.View;

import com.github.zachdeibert.operationmanipulation.model.*;

import java.io.Serializable;

public class EquationView extends View {
    static class SavedState extends BaseSavedState {
        static class SerializableState implements Serializable {
            private final Equation equation;
            private final int backgroundColor;

            @NonNull
            public SavedState toState() {
                return new SavedState(equation, backgroundColor);
            }

            private SerializableState(@NonNull SavedState state) {
                equation = state.equation;
                backgroundColor = state.backgroundColor;
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
                return new SavedState[size];
            }
        };

        private final Equation equation;
        private final int backgroundColor;

        public void apply(@NonNull EquationView view) {
            view.setEquation(equation);
        }

        @NonNull
        public SerializableState toSerializable() {
            return new SerializableState(this);
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            equation.writeToParcel(out, flags);
            out.writeInt(backgroundColor);
        }

        private SavedState(Equation equation, int backgroundColor) {
            super(EMPTY_STATE);
            this.equation = equation;
            this.backgroundColor = backgroundColor;
        }

        private SavedState(Parcelable state, @NonNull EquationView view) {
            super(state);
            equation = view.getEquation();
            backgroundColor = view.getBackgroundColor();
        }

        private SavedState(@NonNull Parcel parcel) {
            super(parcel);
            equation = Equation.CREATOR.createFromParcel(parcel);
            backgroundColor = parcel.readInt();
        }
    }

    private int width;
    private int height;
    private Equation equation;
    private int backgroundColor;
    private Rect bounds;
    private Rect inside;
    private Paint paint;
    private char[] chars;

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }

    public Equation getEquation() {
        return equation;
    }

    public void setEquation(@NonNull Equation equation) {
        this.equation = equation;
        chars = new char[2 + equation.getOperandCount()];
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = widthMeasureSpec & 0x3FFFFFFF;
        setMeasuredDimension(width, height);
        inside.right = bounds.right = width;
        bounds.bottom = height;
        inside.bottom = height - 5;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        paint.setARGB(255, 0, 0, 0);
        canvas.drawRect(bounds, paint);
        paint.setColor(getBackgroundColor());
        canvas.drawRect(inside, paint);
        paint.setARGB(255, 0, 0, 0);
        int i = 0;
        for (ExpressionItem item : equation.getLeftSide()) {
            if (item instanceof Operand) {
                Operand operand = (Operand) item;
                chars[i++] = (char) ('0' + operand.getValue());
            }
        }
        chars[i++] = '=';
        chars[i] = (char) ('0' + equation.getRightSide().getValue());
        int charWidth = width / chars.length;
        int textSize = height - 20;
        top: while (true) {
            paint.setTextSize(textSize);
            for (i = 0; i < chars.length; ++i) {
                int width = (int) paint.measureText(chars, i, 1);
                if (width >= charWidth / 2) {
                    textSize -= 10;
                    continue top;
                }
            }
            break;
        }
        for (i = 0; i < chars.length; ++i) {
            int width = (int) paint.measureText(chars, i, 1);
            canvas.drawText(chars, i, 1, charWidth * i + (charWidth - width) / 2, (height + textSize) / 2, paint);
        }
    }

    private void init() {
        equation = new Equation();
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels / 4;
        setBackgroundColor(0xFFFFFFFF);
        bounds = new Rect(0, 0, 0, 0);
        inside = new Rect(0, 0, 0, 0);
        paint = new Paint();
    }

    public EquationView(Context context) {
        super(context);
        init();
    }

    public EquationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EquationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
}
