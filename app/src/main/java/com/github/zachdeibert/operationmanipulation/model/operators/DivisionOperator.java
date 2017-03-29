package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.BinaryOperator;

public class DivisionOperator extends BinaryOperator {
    @NonNull
    public static final Creator<DivisionOperator> CREATOR = new Creator<DivisionOperator>() {
        @NonNull
        @Override
        public DivisionOperator createFromParcel(Parcel source) {
            return new DivisionOperator();
        }

        @NonNull
        @Override
        public DivisionOperator[] newArray(int size) {
            return new DivisionOperator[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "\u00f7";
    }

    @Override
    public double run(double lhs, double rhs) {
        return lhs / rhs;
    }

    public DivisionOperator() {
        super(1);
    }
}
