package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;
import com.github.zachdeibert.operationmanipulation.model.Side;

public class CeilingOperator extends GroupingOperator {
    @NonNull
    public static final Creator<CeilingOperator> CREATOR = new Creator<CeilingOperator>() {
        @NonNull
        @Override
        public CeilingOperator createFromParcel(@NonNull Parcel source) {
            return new CeilingOperator(Side.values()[source.readInt()]);
        }

        @NonNull
        @Override
        public CeilingOperator[] newArray(int size) {
            return new CeilingOperator[size];
        }
    };

    private final Side side;

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(side.ordinal());
    }

    @NonNull
    @Override
    public String toString() {
        return side == Side.Left ? "\u2308" : "\u2309";
    }

    @Override
    public double run(double val, GroupingOperator other) {
        if (other instanceof CeilingOperator) {
            return Math.ceil(val);
        } else {
            return Math.round(val);
        }
    }

    @Override
    public int getLevel() {
        return side == Side.Left ? 1 : -1;
    }

    public CeilingOperator(Side side) {
        super(2);
        this.side = side;
    }
}
