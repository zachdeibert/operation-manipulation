package com.github.zachdeibert.operationmanipulation.view.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.*;
import android.view.View;

import com.github.zachdeibert.operationmanipulation.model.*;

import java.io.Serializable;

public class EquationView extends View {
    static class SavedState extends BaseSavedState {
        static class SerializableState implements Serializable {
            private final Equation equation;
            private final int backgroundColor;

            public SavedState toState() {
                return new SavedState(equation, backgroundColor);
            }

            private SerializableState(SavedState state) {
                equation = state.equation;
                backgroundColor = state.backgroundColor;
            }
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        private final Equation equation;
        private final int backgroundColor;

        public void apply(EquationView view) {
            view.setEquation(equation);
        }

        public SerializableState toSerializable() {
            return new SerializableState(this);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            equation.writeToParcel(out, flags);
            out.writeInt(backgroundColor);
        }

        private SavedState(Equation equation, int backgroundColor) {
            super(EMPTY_STATE);
            this.equation = equation;
            this.backgroundColor = backgroundColor;
        }

        private SavedState(Parcelable state, EquationView view) {
            super(state);
            equation = view.getEquation();
            backgroundColor = view.getBackgroundColor();
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            equation = Equation.CREATOR.createFromParcel(parcel);
            backgroundColor = parcel.readInt();
        }
    }

    private int width;
    private int height;
    private Equation equation;
    private int backgroundColor;

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

    public void setEquation(Equation equation) {
        this.equation = equation;
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = widthMeasureSpec & 0x3FFFFFFF;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect bounds = new Rect(0, 0, width, height);
        Rect inside = new Rect(0, 5, width, height - 5);
        Paint paint = new Paint();
        paint.setARGB(255, 0, 0, 0);
        canvas.drawRect(bounds, paint);
        paint.setColor(getBackgroundColor());
        canvas.drawRect(inside, paint);
        paint.setARGB(255, 0, 0, 0);
        int nChars = 2 + equation.getOperandCount();
        char[] chars = new char[nChars];
        int i = 0;
        for (ExpressionItem item : equation.getLeftSide()) {
            if (item instanceof Operand) {
                Operand operand = (Operand) item;
                chars[i++] = (char) ('0' + operand.getValue());
            }
        }
        chars[i++] = '=';
        chars[i] = (char) ('0' + equation.getRightSide().getValue());
        int charWidth = width / nChars;
        int textSize = height - 20;
        top: while (true) {
            paint.setTextSize(textSize);
            for (i = 0; i < nChars; ++i) {
                int width = (int) paint.measureText(chars, i, 1);
                if (width >= charWidth / 2) {
                    textSize -= 10;
                    continue top;
                }
            }
            break;
        }
        for (i = 0; i < nChars; ++i) {
            int width = (int) paint.measureText(chars, i, 1);
            canvas.drawText(chars, i, 1, charWidth * i + (charWidth - width) / 2, (height + textSize) / 2, paint);
        }
    }

    private void init(AttributeSet attrs, int defStyle) {
        equation = new Equation();
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels / 4;
        setBackgroundColor(0xFFFFFFFF);
    }

    public EquationView(Context context) {
        super(context);
        init(null, 0);
    }

    public EquationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public EquationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }
}
