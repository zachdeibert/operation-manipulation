package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.ClipData;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import com.github.zachdeibert.operationmanipulation.model.Operator;
import com.github.zachdeibert.operationmanipulation.model.OperatorType;
import com.github.zachdeibert.operationmanipulation.util.DeprecatedApis;
import com.github.zachdeibert.operationmanipulation.view.activities.GameActivity;

public class OperatorView extends AppCompatButton implements View.OnTouchListener {
    private static class SavedState extends BaseSavedState {
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

        private final Operator operator;

        private void apply(@NonNull OperatorView view) {
            view.setOperator(operator);
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(OperatorType.valueOf(operator).ordinal());
        }

        private SavedState(Parcelable state, @NonNull OperatorView view) {
            super(state);
            operator = view.getOperator();
        }

        private SavedState(@NonNull Parcel parcel) {
            super(parcel);
            operator = OperatorType.values()[parcel.readInt()].getOperator();
        }
    }

    private Operator operator;
    private float startDragX;
    private float startDragY;

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(@NonNull Operator operator) {
        this.operator = operator;
        setText(operator.toString());
    }

    public float getStartDragX() {
        return startDragX;
    }

    public float getStartDragY() {
        return startDragY;
    }

    public void setStartDragEvent(@Nullable DragEvent event) {
        if (event == null) {
            startDragX = 0;
            startDragY = 0;
        } else {
            startDragX = event.getX();
            startDragY = event.getY();
        }
    }

    public void makeUnique() {
        setOperator(getOperator().clone());
    }

    @NonNull
    @Override
    public Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Parcelable state) {
        super.onRestoreInstanceState(state);
        ((SavedState) state).apply(this);
    }

    @Override
    public boolean onTouch(View v, @NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            DeprecatedApis.startDrag(v, ClipData.newPlainText("", ""), new View.DragShadowBuilder(v), v, 0);
            final GameActivity activity = (GameActivity) getContext();
            activity.setHideSystemUi(true);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.setHideSystemUi(false);
                }
            }, 500);
            return true;
        } else {
            return false;
        }
    }

    private void init() {
        setOnTouchListener(this);
    }

    public OperatorView(@NonNull Context context) {
        super(context);
        init();
    }

    public OperatorView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OperatorView(@NonNull Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
}
