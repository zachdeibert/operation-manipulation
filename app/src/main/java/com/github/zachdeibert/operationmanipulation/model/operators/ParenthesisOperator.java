package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;
import com.github.zachdeibert.operationmanipulation.model.Side;

public class ParenthesisOperator extends GroupingOperator {
    @NonNull
    public static final Creator<ParenthesisOperator> CREATOR = new Creator<ParenthesisOperator>() {
        @NonNull
        @Override
        public ParenthesisOperator createFromParcel(@NonNull Parcel source) {
            return new ParenthesisOperator(Side.values()[source.readInt()]);
        }

        @NonNull
        @Override
        public ParenthesisOperator[] newArray(int size) {
            return new ParenthesisOperator[size];
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
        return side == Side.Left ? "(" : ")";
    }

    @Override
    public double run(double val, GroupingOperator other) {
        return val;
    }

    @Override
    public int getLevel() {
        return side == Side.Left ? 1 : -1;
    }

    public ParenthesisOperator(Side side) {
        super(0);
        this.side = side;
    }
}
