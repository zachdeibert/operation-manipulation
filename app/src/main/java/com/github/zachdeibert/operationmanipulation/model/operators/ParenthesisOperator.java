package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;

import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;
import com.github.zachdeibert.operationmanipulation.model.Side;

public class ParenthesisOperator extends GroupingOperator {
    public static final Creator<ParenthesisOperator> CREATOR = new Creator<ParenthesisOperator>() {
        @Override
        public ParenthesisOperator createFromParcel(Parcel source) {
            return new ParenthesisOperator(Side.values()[source.readInt()]);
        }

        @Override
        public ParenthesisOperator[] newArray(int size) {
            return new ParenthesisOperator[size];
        }
    };

    private final Side side;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(side.ordinal());
    }

    @Override
    public String toString() {
        return side == Side.Left ? "(" : ")";
    }

    @Override
    public double run(double val) {
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
