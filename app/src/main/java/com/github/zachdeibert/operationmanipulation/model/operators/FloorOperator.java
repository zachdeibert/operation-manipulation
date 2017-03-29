package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;
import com.github.zachdeibert.operationmanipulation.model.Side;

public class FloorOperator extends GroupingOperator {
    @NonNull
    public static final Creator<FloorOperator> CREATOR = new Creator<FloorOperator>() {
        @NonNull
        @Override
        public FloorOperator createFromParcel(@NonNull Parcel source) {
            return new FloorOperator(Side.values()[source.readInt()]);
        }

        @NonNull
        @Override
        public FloorOperator[] newArray(int size) {
            return new FloorOperator[size];
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
        return side == Side.Left ? "\u230A" : "\u230B";
    }

    @Override
    public double run(double val, GroupingOperator other) {
        if (other instanceof FloorOperator) {
            return Math.floor(val);
        } else {
            return Math.round(val);
        }
    }

    @Override
    public int getLevel() {
        return side == Side.Left ? 1 : -1;
    }

    public FloorOperator(Side side) {
        super(2);
        this.side = side;
    }
}
