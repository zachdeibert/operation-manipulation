package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;

import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;

public class AbsoltueValueOperator extends GroupingOperator {
    public static final Creator<AbsoltueValueOperator> CREATOR = new Creator<AbsoltueValueOperator>() {
        @Override
        public AbsoltueValueOperator createFromParcel(Parcel source) {
            return new AbsoltueValueOperator();
        }

        @Override
        public AbsoltueValueOperator[] newArray(int size) {
            return new AbsoltueValueOperator[size];
        }
    };

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

    public AbsoltueValueOperator() {
        super(1);
    }
}
