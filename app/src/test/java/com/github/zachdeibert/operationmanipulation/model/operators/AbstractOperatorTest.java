package com.github.zachdeibert.operationmanipulation.model.operators;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.zachdeibert.operationmanipulation.model.Operator;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

abstract class AbstractOperatorTest<T extends Operator> {
    @Nullable
    private T instance;
    @Nullable
    private T secondary;

    protected abstract T create();

    protected abstract double[][] getTestCases();

    @NonNull
    T getInstance() {
        Assert.assertNotNull("Before function not called", instance);
        return instance;
    }

    @Before
    public void setUp() throws Exception {
        instance = create();
        secondary = create();
    }

    @After
    public void tearDown() throws Exception {
        instance = null;
        secondary = null;
    }

    @Test
    public void _create() throws Exception {
        Assert.assertNotSame("Unit test create() needs to return new instances", instance, secondary);
    }

    @Test
    public void _toString() throws Exception {
        Assert.assertNotNull("Before function not called", instance);
        Assert.assertNotNull("Before function not called", secondary);
        Assert.assertEquals("toString must be a constant", instance.toString(), secondary.toString());
        Assert.assertEquals("toString must only return one character", 1, instance.toString().length());
    }

    @SuppressWarnings({"RedundantThrows", "unused"})
    public abstract void run() throws Exception;

    @Test
    public void getOrder() throws Exception {
        Assert.assertNotNull("Before function not called", instance);
        Assert.assertNotNull("Before function not called", secondary);
        Assert.assertEquals("getOrder must be a constant", instance.getOrder(), secondary.getOrder());
        Assert.assertTrue("getOrder must return a non-negative integer", instance.getOrder() >= 0);
    }
}
