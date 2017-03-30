package com.github.zachdeibert.operationmanipulation;

import com.github.zachdeibert.operationmanipulation.util.Log;

import org.junit.After;
import org.junit.Before;

public abstract class AbstractUnitTest {
    @Before
    public void setupLogging() {
        Log.LOGCAT = false;
    }

    @After
    public void cleanupLogging() {
        Log.LOGCAT = true;
    }
}
