package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;

public class AbsoluteValueOperator extends GroupingOperator {
    @NonNull
    public static final Creator<AbsoluteValueOperator> CREATOR = new Creator<AbsoluteValueOperator>() {
        @NonNull
        @Override
        public AbsoluteValueOperator createFromParcel(Parcel source) {
            return new AbsoluteValueOperator();
        }

        @NonNull
        @Override
        public AbsoluteValueOperator[] newArray(int size) {
            return new AbsoluteValueOperator[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "|";
    }

    @Override
    public double run(double val, GroupingOperator other) {
        return Math.abs(val);
    }

    @Override
    public int getLevel() {
        return 0;
    }

    public AbsoluteValueOperator() {
        super(1);
    }
}
