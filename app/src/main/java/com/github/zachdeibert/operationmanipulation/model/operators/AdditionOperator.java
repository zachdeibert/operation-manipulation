package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.BinaryOperator;

public class AdditionOperator extends BinaryOperator {
    @NonNull
    public static final Creator<AdditionOperator> CREATOR = new Creator<AdditionOperator>() {
        @NonNull
        @Override
        public AdditionOperator createFromParcel(Parcel source) {
            return new AdditionOperator();
        }

        @NonNull
        @Override
        public AdditionOperator[] newArray(int size) {
            return new AdditionOperator[size];
        }
    };

    @NonNull
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
