package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

public class MultiplicationOperatorTest extends AbstractBinaryOperatorTest<MultiplicationOperator> {
    @NonNull
    @Override
    protected MultiplicationOperator create() {
        return new MultiplicationOperator();
    }

    @NonNull
    @Override
    protected double[][] getTestCases() {
        return new double[][] {
                new double[] { 2, 2, 4 },
                new double[] { 2, 4, 8 },
                new double[] { 2, 0.5, 1 },
                new double[] { 42, 0, 0 },
                new double[] { 42, 1, 42 },
                new double[] { 2, 3, 6 }
        };
    }
}
