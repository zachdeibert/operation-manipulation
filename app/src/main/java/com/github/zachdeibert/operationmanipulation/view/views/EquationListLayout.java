package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;

import com.github.zachdeibert.operationmanipulation.model.Level;
import com.github.zachdeibert.operationmanipulation.util.CollectionUtils;
import com.github.zachdeibert.operationmanipulation.view.activities.GameActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EquationListLayout extends LinearLayout {
    public static class SavedState extends BaseSavedState {
        public static class SerializableState implements Serializable {
            @NonNull
            private final List<EquationContainer.SavedState.SerializableState> states;

            @NonNull
            public SavedState toState() {
                List<EquationContainer.SavedState> states = new ArrayList<>();
                for (EquationContainer.SavedState.SerializableState state : this.states) {
                    states.add(state.toState());
                }
                return new SavedState(states);
            }

            private SerializableState(@NonNull SavedState state) {
                states = new ArrayList<>();
                for (EquationContainer.SavedState s : state.states) {
                    states.add(s.toSerializable());
                }
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

        private final List<EquationContainer.SavedState> states;

        private void apply(@NonNull EquationListLayout view) {
            Level level = null;
            for (EquationContainer.SavedState state : states) {
                EquationContainer equationView = new EquationContainer(view.getContext());
                equationView.onRestoreInstanceState(state);
                if (level == null) {
                    level = equationView.getEquation().getLevel();
                } else if (level != equationView.getEquation().getLevel()) {
                    level = equationView.getEquation().getLevel();
                    view.addView(new LevelUpView(view.getContext()));
                }
                view.addView(equationView);
            }
            ((GameActivity) view.getContext()).arrangeAddEquationView();
        }

        @NonNull
        public SerializableState toSerializable() {
            return new SerializableState(this);
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeList(states);
        }

        private SavedState(List<EquationContainer.SavedState> states) {
            super(EMPTY_STATE);
            this.states = states;
        }

        private SavedState(Parcelable state, @NonNull EquationListLayout view) {
            super(state);
            states = new ArrayList<>();
            for (int i = 0; i < view.getChildCount(); ++i) {
                View v = view.getChildAt(i);
                if (v instanceof EquationContainer) {
                    states.add((EquationContainer.SavedState) ((EquationContainer) v).onSaveInstanceState());
                }
            }
        }

        private SavedState(@NonNull Parcel parcel) {
            super(parcel);
            states = CollectionUtils.checkedAssignment(parcel.readArrayList(ClassLoader.getSystemClassLoader()), EquationContainer.SavedState.class);
        }
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
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
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
