package com.github.zachdeibert.operationmanipulation.model.operators;

import com.github.zachdeibert.operationmanipulation.model.BinaryOperator;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Locale;

abstract class AbstractBinaryOperatorTest<T extends BinaryOperator> extends AbstractOperatorTest<T> {
    @Test
    @Override
    public void run() {
        for (double[] test : getTestCases()) {
            Assert.assertEquals("Invalid test case", 3, test.length);
            Assert.assertEquals(String.format(Locale.US, "%f %s %f = %f", test[0], getInstance(), test[1], test[2]), test[2], getInstance().run(test[0], test[1]));
        }
    }
}
