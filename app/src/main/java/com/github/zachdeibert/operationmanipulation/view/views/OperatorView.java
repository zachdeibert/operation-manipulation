package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.ClipData;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.github.zachdeibert.operationmanipulation.model.Operator;
import com.github.zachdeibert.operationmanipulation.model.OperatorType;

public class OperatorView extends Button implements View.OnTouchListener {
    private static class SavedState extends BaseSavedState {
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

        private final Operator operator;

        private void apply(OperatorView view) {
            view.setOperator(operator);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(OperatorType.valueOf(operator).ordinal());
        }

        private SavedState(Parcelable state, OperatorView view) {
            super(state);
            operator = view.getOperator();
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            operator = OperatorType.values()[parcel.readInt()].getOperator();
        }
    }

    private Operator operator;

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
        setText(operator.toString());
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        ((SavedState) state).apply(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            v.startDrag(ClipData.newPlainText("", ""), new View.DragShadowBuilder(v), v, 0);
            return true;
        } else {
            return false;
        }
    }

    private void init(AttributeSet attrs, int defStyle) {
        setOnTouchListener(this);
    }

    public OperatorView(Context context) {
        super(context);
        init(null, 0);
    }

    public OperatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public OperatorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }
}
