package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;

import com.github.zachdeibert.operationmanipulation.model.BinaryOperator;

public class ExponentOperator extends BinaryOperator {
    public static final Creator<ExponentOperator> CREATOR = new Creator<ExponentOperator>() {
        @Override
        public ExponentOperator createFromParcel(Parcel source) {
            return new ExponentOperator();
        }

        @Override
        public ExponentOperator[] newArray(int size) {
            return new ExponentOperator[size];
        }
    };

    @Override
    public String toString() {
        return "^";
    }

    @Override
    public double run(double lhs, double rhs) {
        return Math.pow(lhs, rhs);
    }

    public ExponentOperator() {
        super(2);
    }
}
