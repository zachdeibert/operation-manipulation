package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.Operator;
import com.github.zachdeibert.operationmanipulation.model.Operators;

public class AdditionOperatorTest extends AbstractBinaryOperatorTest<AdditionOperator> {
    @NonNull
    @Override
    protected Operator[] mustEvaluateBefore() {
        return new Operator[0];
    }

    @NonNull
    @Override
    protected Operator[] mustEvaluateAfter() {
        return new Operator[] {
            Operators.ABSOLUTE_VALUE,
            Operators.MULTIPLICATION,
            Operators.FACTORIAL,
            Operators.DIVISION,
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
