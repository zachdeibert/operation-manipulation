package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.UnaryOperator;

import org.apache.commons.math3.special.Gamma;

public class FactorialOperator extends UnaryOperator {
    @NonNull
    public static final Creator<FactorialOperator> CREATOR = new Creator<FactorialOperator>() {
        @NonNull
        @Override
        public FactorialOperator createFromParcel(Parcel source) {
            return new FactorialOperator();
        }

        @NonNull
        @Override
        public FactorialOperator[] newArray(int size) {
            return new FactorialOperator[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "!";
    }

    @Override
    public double run(double val) {
        return Gamma.gamma(val + 1);
    }
}
