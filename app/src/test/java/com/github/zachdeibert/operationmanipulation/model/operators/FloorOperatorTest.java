package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;
import com.github.zachdeibert.operationmanipulation.model.Operator;
import com.github.zachdeibert.operationmanipulation.model.Operators;
import com.github.zachdeibert.operationmanipulation.model.Side;

public class FloorOperatorTest extends AbstractGroupingOperatorTest<FloorOperator> {
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
    protected FloorOperator create() {
        return new FloorOperator(Side.Left);
    }

    @NonNull
    @Override
    protected double[][] getTestCases() {
        return new double[][] {
                new double[] { 0, 0, 0 },
                new double[] { 0.25, 0, 0 },
                new double[] { 0.5, 0, 1 },
                new double[] { 0.75, 0, 1 },
                new double[] { 1, 1, 1 },
                new double[] { 1.25, 1, 1 }
        };
    }

    @NonNull
    @Override
    protected GroupingOperator[] getRightSides() {
        return new GroupingOperator[] {
                new FloorOperator(Side.Right),
                new CeilingOperator(Side.Right)
        };
    }
}
