package com.github.zachdeibert.operationmanipulation.model;

import android.os.Parcel;

import com.github.zachdeibert.operationmanipulation.model.operators.AdditionOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.DivisionOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.ExponentOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.MultiplicationOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.SubtractionOperator;

public abstract class Operator extends ExpressionItem {
    public static final Operator ADDITION = new AdditionOperator();
    public static final Operator SUBTRACTION = new SubtractionOperator();
    public static final Operator MULTIPLICATION = new MultiplicationOperator();
    public static final Operator DIVISION = new DivisionOperator();
    public static final Operator EXPONENT = new ExponentOperator();

    public static final Operator[] VALUES = new Operator[] {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION,
        EXPONENT
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public abstract double run(double lhs, double rhs);
}
