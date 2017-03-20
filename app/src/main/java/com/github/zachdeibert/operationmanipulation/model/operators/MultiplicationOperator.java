package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;

import com.github.zachdeibert.operationmanipulation.model.BinaryOperator;

public class MultiplicationOperator extends BinaryOperator {
    public static final Creator<MultiplicationOperator> CREATOR = new Creator<MultiplicationOperator>() {
        @Override
        public MultiplicationOperator createFromParcel(Parcel source) {
            return new MultiplicationOperator();
        }

        @Override
        public MultiplicationOperator[] newArray(int size) {
            return new MultiplicationOperator[size];
        }
    };

    @Override
    public String toString() {
        return "x";
    }

    @Override
    public double run(double lhs, double rhs) {
        return lhs * rhs;
    }

    public MultiplicationOperator() {
        super(1);
    }
}
