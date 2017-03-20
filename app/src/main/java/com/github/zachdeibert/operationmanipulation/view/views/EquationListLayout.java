package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class EquationListLayout extends LinearLayout {
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

        private final List<EquationContainer.SavedState> states;

        private void apply(EquationListLayout view) {
            for (EquationContainer.SavedState state : states) {
                EquationContainer equationView = new EquationContainer(view.getContext());
                equationView.onRestoreInstanceState(state);
                view.addView(equationView);
            }
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeList(states);
        }

        private SavedState(Parcelable state, EquationListLayout view) {
            super(state);
            states = new ArrayList<>();
            for (int i = 0; i < view.getChildCount(); ++i) {
                View v = view.getChildAt(i);
                if (v instanceof EquationContainer) {
                    states.add((EquationContainer.SavedState) ((EquationContainer) v).onSaveInstanceState());
                }
            }
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            states = parcel.readArrayList(ClassLoader.getSystemClassLoader());
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

    @Override
    protected void dispatchSaveInstanceState(SparseArray container) {
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray container) {
        super.dispatchThawSelfOnly(container);
    }

    public EquationListLayout(Context context) {
        super(context);
    }

    public EquationListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EquationListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
