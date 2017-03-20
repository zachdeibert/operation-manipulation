package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;

import com.github.zachdeibert.operationmanipulation.model.UnaryOperator;

import org.apache.commons.math3.special.Gamma;

public class FactorialOperator extends UnaryOperator {
    public static final Creator<FactorialOperator> CREATOR = new Creator<FactorialOperator>() {
        @Override
        public FactorialOperator createFromParcel(Parcel source) {
            return new FactorialOperator();
        }

        @Override
        public FactorialOperator[] newArray(int size) {
            return new FactorialOperator[size];
        }
    };

    @Override
    public String toString() {
        return "!";
    }

    @Override
    public double run(double val) {
        return Gamma.gamma(val + 1);
    }

    public FactorialOperator() {
        super(2);
    }
}
