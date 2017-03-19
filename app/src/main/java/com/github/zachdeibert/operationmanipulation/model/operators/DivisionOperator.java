package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;

import com.github.zachdeibert.operationmanipulation.model.Operator;

public class DivisionOperator extends Operator {
    public static final Creator<DivisionOperator> CREATOR = new Creator<DivisionOperator>() {
        @Override
        public DivisionOperator createFromParcel(Parcel source) {
            return new DivisionOperator();
        }

        @Override
        public DivisionOperator[] newArray(int size) {
            return new DivisionOperator[size];
        }
    };

    @Override
    public String toString() {
        return "\u00f7";
    }

    @Override
    public double run(double lhs, double rhs) {
        return lhs / rhs;
    }

    public DivisionOperator() {
    }
}
