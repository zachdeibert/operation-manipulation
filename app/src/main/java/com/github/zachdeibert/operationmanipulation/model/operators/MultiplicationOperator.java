package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.BinaryOperator;

public class MultiplicationOperator extends BinaryOperator {
    @NonNull
    public static final Creator<MultiplicationOperator> CREATOR = new Creator<MultiplicationOperator>() {
        @NonNull
        @Override
        public MultiplicationOperator createFromParcel(Parcel source) {
            return new MultiplicationOperator();
        }

        @NonNull
        @Override
        public MultiplicationOperator[] newArray(int size) {
            return new MultiplicationOperator[size];
        }
    };

    @NonNull
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
