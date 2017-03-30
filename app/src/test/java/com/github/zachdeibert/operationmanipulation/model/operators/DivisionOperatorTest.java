package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

public class DivisionOperatorTest extends AbstractBinaryOperatorTest<DivisionOperator> {
    @NonNull
    @Override
    protected DivisionOperator create() {
        return new DivisionOperator();
    }

    @NonNull
    @Override
    protected double[][] getTestCases() {
        return new double[][] {
                new double[] { 1, 2, 0.5 },
                new double[] { 1, 0, Double.POSITIVE_INFINITY },
                new double[] { -2, 0, Double.NEGATIVE_INFINITY },
                new double[] { 0, 0, Double.NaN },
                new double[] { 4, 0.5, 8 }
        };
    }
}
