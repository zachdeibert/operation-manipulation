package com.github.zachdeibert.operationmanipulation.model.operators;

import com.github.zachdeibert.operationmanipulation.model.UnaryOperator;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Locale;

abstract class AbstractUnaryOperatorTest<T extends UnaryOperator> extends AbstractOperatorTest<T> {
    @Test
    @Override
    public void run() {
        for (double[] test : getTestCases()) {
            Assert.assertEquals("Invalid test case", 2, test.length);
            Assert.assertEquals(String.format(Locale.US, "%f %s = %f", test[0], getInstance(), test[1]), test[1], getInstance().run(test[0]));
        }
    }
}
