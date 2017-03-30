package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;
import com.github.zachdeibert.operationmanipulation.model.Operator;
import com.github.zachdeibert.operationmanipulation.model.Operators;
import com.github.zachdeibert.operationmanipulation.model.Side;

public class ParenthesisOperatorTest extends AbstractGroupingOperatorTest<ParenthesisOperator> {
    @Override
    protected Operator[] mustEvaluateBefore() {
        return new Operator[] {
                Operators.ADDITION,
                Operators.DIVISION,
                Operators.FACTORIAL,
                Operators.MULTIPLICATION,
                Operators.SUBTRACTION
        };
    }

    @Override
    protected Operator[] mustEvaluateAfter() {
        return new Operator[0];
    }

    @NonNull
    @Override
    protected ParenthesisOperator create() {
        return new ParenthesisOperator(Side.Left);
    }

    @NonNull
    @Override
    protected double[][] getTestCases() {
        return new double[][] {
                new double[] { 0, 0 },
                new double[] { 1, 1 },
                new double[] { -1, -1 },
                new double[] { 42, 42 }
        };
    }

    @NonNull
    @Override
    protected GroupingOperator[] getRightSides() {
        return new GroupingOperator[] {
                new ParenthesisOperator(Side.Right)
        };
    }
}