package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;
import com.github.zachdeibert.operationmanipulation.model.Operator;
import com.github.zachdeibert.operationmanipulation.model.Operators;

public class AbsoluteValueOperatorTest extends AbstractGroupingOperatorTest<AbsoluteValueOperator> {
    @NonNull
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

    @NonNull
    @Override
    protected Operator[] mustEvaluateAfter() {
        return new Operator[0];
    }

    @NonNull
    @Override
    protected GroupingOperator[] getRightSides() {
        return new GroupingOperator[] {
                new AbsoluteValueOperator()
        };
    }

    @NonNull
    @Override
    protected AbsoluteValueOperator create() {
        return new AbsoluteValueOperator();
    }

    @NonNull
    @Override
    protected double[][] getTestCases() {
        return new double[][] {
                new double[] { 1, 1 },
                new double[] { -1, 1 },
                new double[] { 0, 0 },
                new double[] { -42, 42 }
        };
    }
}
