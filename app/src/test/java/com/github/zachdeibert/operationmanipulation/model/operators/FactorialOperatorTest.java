package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

import org.apache.commons.math3.special.Gamma;

public class FactorialOperatorTest extends AbstractUnaryOperatorTest<FactorialOperator> {
    @NonNull
    @Override
    protected FactorialOperator create() {
        return new FactorialOperator();
    }

    @NonNull
    @Override
    protected double[][] getTestCases() {
        return new double[][] {
                new double[] { -1, Double.NaN },
                new double[] { 0, 1 },
                new double[] { 1, 1 },
                new double[] { 2, 2 },
                new double[] { 3, 6 },
                new double[] { 4, 24 },
                new double[] { 0.5, Gamma.gamma(1.5) }
        };
    }
}
