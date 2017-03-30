package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.Operator;
import com.github.zachdeibert.operationmanipulation.model.Operators;

public class MultiplicationOperatorTest extends AbstractBinaryOperatorTest<MultiplicationOperator> {
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
