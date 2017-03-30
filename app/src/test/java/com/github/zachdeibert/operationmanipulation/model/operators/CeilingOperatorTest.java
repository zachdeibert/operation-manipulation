package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;
import com.github.zachdeibert.operationmanipulation.model.Side;

public class CeilingOperatorTest extends AbstractGroupingOperatorTest<CeilingOperator> {
    @NonNull
    @Override
    protected GroupingOperator[] getRightSides() {
        return new GroupingOperator[] {
                new CeilingOperator(Side.Right),
                new FloorOperator(Side.Right)
        };
    }

    @NonNull
    @Override
    protected CeilingOperator create() {
        return new CeilingOperator(Side.Left);
    }

    @NonNull
    @Override
    protected double[][] getTestCases() {
        return new double[][] {
                new double[] { 0, 0, 0 },
                new double[] { 0.25, 1, 0 },
                new double[] { 0.5, 1, 1 },
                new double[] { 0.75, 1, 1 },
                new double[] { 1, 1, 1 },
                new double[] { 1.25, 2, 1 }
        };
    }
}
