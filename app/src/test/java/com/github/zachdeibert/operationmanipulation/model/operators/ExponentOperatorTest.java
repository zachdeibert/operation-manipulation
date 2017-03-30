package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.Operator;
import com.github.zachdeibert.operationmanipulation.model.Operators;

public class ExponentOperatorTest extends AbstractBinaryOperatorTest<ExponentOperator> {
    @Override
    protected Operator[] mustEvaluateBefore() {
        return new Operator[] {
                Operators.ADDITION,
                Operators.DIVISION,
                Operators.MULTIPLICATION,
                Operators.SUBTRACTION
        };
    }

    @Override
    protected Operator[] mustEvaluateAfter() {
        return new Operator[] {
                Operators.ABSOLUTE_VALUE,
                Operators.FACTORIAL,
                Operators.LEFT_CEILING,
                Operators.LEFT_FLOOR,
                Operators.LEFT_PARENTHESIS,
                Operators.RIGHT_CEILING,
                Operators.RIGHT_FLOOR,
                Operators.RIGHT_PARENTHESIS
        };
    }

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
