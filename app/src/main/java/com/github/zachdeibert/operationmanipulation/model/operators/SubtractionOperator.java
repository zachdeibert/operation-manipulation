package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;

import com.github.zachdeibert.operationmanipulation.model.Operator;

public class SubtractionOperator extends Operator {
    public static final Creator<SubtractionOperator> CREATOR = new Creator<SubtractionOperator>() {
        @Override
        public SubtractionOperator createFromParcel(Parcel source) {
            return new SubtractionOperator();
        }

        @Override
        public SubtractionOperator[] newArray(int size) {
            return new SubtractionOperator[size];
        }
    };

    @Override
    public String toString() {
        return "-";
    }

    @Override
    public double run(double lhs, double rhs) {
        return lhs - rhs;
    }

    public SubtractionOperator() {
        super(0);
    }
}
