package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.Operator;
import com.github.zachdeibert.operationmanipulation.model.Operators;

public class DivisionOperatorTest extends AbstractBinaryOperatorTest<DivisionOperator> {
    @Override
    protected Operator[] mustEvaluateBefore() {
        return new Operator[] {
                Operators.ADDITION,
                Operators.SUBTRACTION
        };
    }

    @Override
    protected Operator[] mustEvaluateAfter() {
        return new Operator[] {
                Operators.ABSOLUTE_VALUE,
                Operators.FACTORIAL,
                Operators.EXPONENT,
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
