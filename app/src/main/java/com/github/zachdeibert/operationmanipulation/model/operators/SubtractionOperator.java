package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.BinaryOperator;

public class SubtractionOperator extends BinaryOperator {
    @NonNull
    public static final Creator<SubtractionOperator> CREATOR = new Creator<SubtractionOperator>() {
        @NonNull
        @Override
        public SubtractionOperator createFromParcel(Parcel source) {
            return new SubtractionOperator();
        }

        @NonNull
        @Override
        public SubtractionOperator[] newArray(int size) {
            return new SubtractionOperator[size];
        }
    };

    @NonNull
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
