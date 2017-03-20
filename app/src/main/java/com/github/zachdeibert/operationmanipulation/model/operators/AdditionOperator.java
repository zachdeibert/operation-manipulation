package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;

import com.github.zachdeibert.operationmanipulation.model.Operator;

public class AdditionOperator extends Operator {
    public static final Creator<AdditionOperator> CREATOR = new Creator<AdditionOperator>() {
        @Override
        public AdditionOperator createFromParcel(Parcel source) {
            return new AdditionOperator();
        }

        @Override
        public AdditionOperator[] newArray(int size) {
            return new AdditionOperator[size];
        }
    };

    @Override
    public String toString() {
        return "+";
    }

    @Override
    public double run(double lhs, double rhs) {
        return lhs + rhs;
    }

    public AdditionOperator() {
        super(0);
    }
}
