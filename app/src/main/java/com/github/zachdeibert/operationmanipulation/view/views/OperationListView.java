package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.github.zachdeibert.operationmanipulation.R;
import com.github.zachdeibert.operationmanipulation.model.Level;
import com.github.zachdeibert.operationmanipulation.model.OperatorType;
import com.github.zachdeibert.operationmanipulation.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class OperationListView extends LinearLayout {
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

        @NonNull
        private final List<OperatorType> operators;

        private void apply(@NonNull OperationListView view) {
            view.removeAllViews();
            for (OperatorType op : operators) {
                OperatorView v = new OperatorView(view.getContext(), null, R.attr.operatorBtnStyle);
                v.setOperator(op.getOperator());
                view.addView(v);
            }
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeList(operators);
        }

        private SavedState(Parcelable state, @NonNull OperationListView view) {
            super(state);
            operators = new ArrayList<>();
            for (int i = 0; i < view.getChildCount(); ++i) {
                OperatorView v = (OperatorView) view.getChildAt(i);
                operators.add(OperatorType.valueOf(v.getOperator()));
            }
        }

        private SavedState(@NonNull Parcel parcel) {
            super(parcel);
            operators = CollectionUtils.checkedAssignment(parcel.readArrayList(ClassLoader.getSystemClassLoader()), OperatorType.class);
        }
    }

    public void loadLevel(@NonNull Level level) {
        removeAllViews();
        for (OperatorType op : level.getAllowedOperators()) {
            OperatorView view = new OperatorView(getContext(), null, R.attr.operatorBtnStyle);
            view.setOperator(op.getOperator());
            addView(view);
        }
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

    public OperationListView(Context context) {
        super(context);
    }

    public OperationListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OperationListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
