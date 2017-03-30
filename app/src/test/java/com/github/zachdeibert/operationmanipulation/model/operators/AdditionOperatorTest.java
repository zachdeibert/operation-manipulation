package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

public class AdditionOperatorTest extends AbstractBinaryOperatorTest<AdditionOperator> {
    @NonNull
    @Override
    protected AdditionOperator create() {
        return new AdditionOperator();
    }

    @NonNull
    @Override
    protected double[][] getTestCases() {
        return new double[][] {
                new double[] { 2, 2, 4 },
                new double[] { 2, 3, 5 },
                new double[] { -1, 4, 3 }
        };
    }
}
