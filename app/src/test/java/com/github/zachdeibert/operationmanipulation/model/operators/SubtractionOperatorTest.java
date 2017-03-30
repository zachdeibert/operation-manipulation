package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

public class SubtractionOperatorTest extends AbstractBinaryOperatorTest<SubtractionOperator> {
    @NonNull
    @Override
    protected SubtractionOperator create() {
        return new SubtractionOperator();
    }

    @NonNull
    @Override
    protected double[][] getTestCases() {
        return new double[][] {
                new double[] { 2, 2, 0 },
                new double[] { 2, 3, -1 },
                new double[] { -1, 4, -5 },
                new double[] { 1, -4, 5 }
        };
    }
}
