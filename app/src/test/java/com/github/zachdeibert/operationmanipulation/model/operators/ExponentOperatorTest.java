package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

public class ExponentOperatorTest extends AbstractBinaryOperatorTest<ExponentOperator> {
    @NonNull
    @Override
    protected ExponentOperator create() {
        return new ExponentOperator();
    }

    @NonNull
    @Override
    protected double[][] getTestCases() {
        return new double[][] {
                new double[] { 1, 42, 1 },
                new double[] { 42, 0, 1 },
                new double[] { 2, 8, 256 },
                new double[] { 2, 0.5, Math.sqrt(2) },
                new double[] { 0, 0, Double.NaN }
        };
    }
}
