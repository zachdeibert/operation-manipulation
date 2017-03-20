package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.github.zachdeibert.operationmanipulation.R;
import com.github.zachdeibert.operationmanipulation.model.Level;
import com.github.zachdeibert.operationmanipulation.model.OperatorType;

import java.util.ArrayList;
import java.util.List;

public class OperationListView extends LinearLayout {
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

        private final List<OperatorType> operators;

        private void apply(OperationListView view) {
            view.removeAllViews();
            for (OperatorType op : operators) {
                OperatorView v = new OperatorView(view.getContext(), null, R.attr.operatorBtnStyle);
                v.setOperator(op.getOperator());
                view.addView(v);
            }
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
        }

        private SavedState(Parcelable state, OperationListView view) {
            super(state);
            operators = new ArrayList<>();
            for (int i = 0; i < view.getChildCount(); ++i) {
                OperatorView v = (OperatorView) view.getChildAt(i);
                operators.add(OperatorType.valueOf(v.getOperator()));
            }
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            operators = parcel.readArrayList(ClassLoader.getSystemClassLoader());
        }
    }

    public void loadLevel(Level level) {
        removeAllViews();
        for (OperatorType op : level.getAllowedOperators()) {
            OperatorView view = new OperatorView(getContext(), null, R.attr.operatorBtnStyle);
            view.setOperator(op.getOperator());
            addView(view);
        }
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

    private void init(AttributeSet attrs, int defStyleAttr) {
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
