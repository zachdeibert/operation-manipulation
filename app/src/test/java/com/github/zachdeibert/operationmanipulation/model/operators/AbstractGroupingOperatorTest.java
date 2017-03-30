package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

abstract class AbstractGroupingOperatorTest<T extends GroupingOperator> extends AbstractOperatorTest<T> {
    @Nullable
    private GroupingOperator[] rightSides;

    @NonNull
    protected abstract GroupingOperator[] getRightSides();

    @Before
    public void _setUp() throws Exception {
        rightSides = getRightSides();
    }

    @After
    public void _tearDown() throws Exception {
        rightSides = null;
    }

    @Test
    public void getLevel() throws Exception {
        Assert.assertTrue("Left side level must be non-negative", getInstance().getLevel() >= 0);
        Assert.assertNotNull("Before function not called", rightSides);
        for (GroupingOperator op : rightSides) {
            Assert.assertTrue("Right side level must be non-positive", op.getLevel() <= 0);
            Assert.assertEquals("Left side and right side have to both be non-zero or both be zero", getInstance().getLevel() == 0, op.getLevel() == 0);
        }
    }

    @Test
    public void _getOrder() {
        Assert.assertNotNull("Before function not called", rightSides);
        for (GroupingOperator op : rightSides) {
            Assert.assertEquals("Left side and right side have to have same order", getInstance().getOrder(), op.getOrder());
        }
    }

    @Test
    @Override
    public void run() {
        T lhs = getInstance();
        Assert.assertNotNull("Before function not called", rightSides);
        for (double[] test : getTestCases()) {
            Assert.assertEquals("Invalid test case", 1 + rightSides.length, test.length);
            for (int i = 1; i < rightSides.length; ++i) {
                GroupingOperator rhs = rightSides[i];
                Assert.assertEquals(String.format(Locale.US, "%s %f %s = %f", lhs, test[0], rhs, test[i + 1]), test[i + 1], lhs.run(test[0], rhs));
            }
        }
    }
}
