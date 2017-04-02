package com.github.zachdeibert.operationmanipulation.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.zachdeibert.operationmanipulation.AbstractUnitTest;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LogTest extends AbstractUnitTest {
    private interface NormalTester {
        void run(String tag, String msg);
    }

    private interface ExceptionalTester {
        void run(String tag, String msg, Throwable tr);
    }

    @NonNull
    private static final String[] STRINGS = new String[] {
            "Hello", "World", "This", "Is", "A", "Test"
    };
    @Nullable
    private PrintStream stdOut;
    @Nullable
    private PrintStream stdErr;

    @Before
    public void setUp() throws Exception {
        stdOut = System.out;
        stdErr = System.err;
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(stdOut);
        stdOut = null;
        System.setErr(stdErr);
        stdErr = null;
    }

    private void test(String level, boolean shouldErr, @NonNull NormalTester method) throws Exception {
        Log.LOGCAT = false;
        for (String tag : STRINGS) {
            for (String msg : STRINGS) {
                if (tag != msg) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ByteArrayOutputStream err = new ByteArrayOutputStream();
                    System.setOut(new PrintStream(out));
                    System.setErr(new PrintStream(err));
                    method.run(tag, msg);
                    System.out.flush();
                    System.err.flush();
                    byte[] outB = out.toByteArray();
                    byte[] errB = err.toByteArray();
                    System.out.close();
                    System.err.close();
                    out.close();
                    err.close();
                    Assert.assertEquals("Printing to wrong stream", shouldErr, errB.length != 0);
                    Assert.assertEquals("Wrong output", String.format("%s/%s: %s\n", level, tag, msg), new String(shouldErr ? errB : outB));
                }
            }
        }
        Log.LOGCAT = true;
        for (String tag : STRINGS) {
            for (String msg : STRINGS) {
                if (tag != msg) {
                    RuntimeException ex = null;
                    try {
                        method.run(tag, msg);
                    } catch (RuntimeException e) {
                        ex = e;
                    } finally {
                        Assert.assertNotNull("Log not calling logcat", ex);
                    }
                }
            }
        }
        Log.LOGCAT = false;
    }

    private void test(String level, boolean shouldErr, @NonNull ExceptionalTester method) throws Exception {
        Log.LOGCAT = false;
        for (String tag : STRINGS) {
            for (String msg : STRINGS) {
                if (tag != msg) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ByteArrayOutputStream err = new ByteArrayOutputStream();
                    System.setOut(new PrintStream(out));
                    System.setErr(new PrintStream(err));
                    Exception ex = new Exception();
                    method.run(tag, msg, ex);
                    System.out.flush();
                    System.err.flush();
                    byte[] outB = out.toByteArray();
                    byte[] errB = err.toByteArray();
                    System.out.close();
                    System.err.close();
                    out.close();
                    err.close();
                    Assert.assertEquals("Printing to wrong stream", shouldErr, errB.length != 0);
                    ByteArrayOutputStream expected = new ByteArrayOutputStream();
                    PrintStream stream = new PrintStream(expected);
                    stream.printf("%s/%s: %s\n", level, tag, msg);
                    ex.printStackTrace(stream);
                    stream.flush();
                    Assert.assertEquals("Wrong output", new String(expected.toByteArray()), new String(shouldErr ? errB : outB));
                    stream.close();
                    expected.close();
                }
            }
        }
        Log.LOGCAT = true;
        for (String tag : STRINGS) {
            for (String msg : STRINGS) {
                if (tag != msg) {
                    RuntimeException ex = null;
                    try {
                        method.run(tag, msg, new Exception());
                    } catch (RuntimeException e) {
                        ex = e;
                    } finally {
                        Assert.assertNotNull("Log not calling logcat", ex);
                    }
                }
            }
        }
        Log.LOGCAT = false;
    }

    @Test
    public void d() throws Exception {
        test("D", false, new NormalTester() {
            @Override
            public void run(String tag, String msg) {
                Log.d(tag, msg);
            }
        });
    }

    @Test
    public void d1() throws Exception {
        test("D", false, new ExceptionalTester() {
            @Override
            public void run(String tag, String msg, @NonNull Throwable tr) {
                Log.d(tag, msg, tr);
            }
        });
    }

    @Test
    public void i() throws Exception {
        test("I", false, new NormalTester() {
            @Override
            public void run(String tag, String msg) {
                Log.i(tag, msg);
            }
        });
    }

    @Test
    public void w() throws Exception {
        test("W", true, new NormalTester() {
            @Override
            public void run(String tag, String msg) {
                Log.w(tag, msg);
            }
        });
    }

    @Test
    public void w1() throws Exception {
        test("W", true, new ExceptionalTester() {
            @Override
            public void run(String tag, String msg, @NonNull Throwable tr) {
                Log.w(tag, msg, tr);
            }
        });
    }

    @Test
    public void e() throws Exception {
        test("E", true, new NormalTester() {
            @Override
            public void run(String tag, String msg) {
                Log.e(tag, msg);
            }
        });
    }

    @Test
    public void wtf() throws Exception {
        test("WTF", true, new NormalTester() {
            @Override
            public void run(String tag, String msg) {
                Log.wtf(tag, msg);
            }
        });
    }

    @Test
    public void wtf1() throws Exception {
        test("WTF", true, new ExceptionalTester() {
            @Override
            public void run(String tag, String msg, @NonNull Throwable tr) {
                Log.wtf(tag, msg, tr);
            }
        });
    }
}