package com.github.zachdeibert.operationmanipulation.util;

import android.support.annotation.NonNull;

@SuppressWarnings("SameParameterValue")
public class Log {
    public static boolean LOGCAT = true;

    public static void d(String tag, String msg) {
        if (LOGCAT) {
            android.util.Log.d(tag, msg);
        } else {
            System.out.printf("D/%s: %s\n", tag, msg);
        }
    }

    public static void d(String tag, String msg, @NonNull Throwable tr) {
        if (LOGCAT) {
            android.util.Log.d(tag, msg, tr);
        } else {
            System.out.printf("D/%s: %s\n", tag, msg);
            tr.printStackTrace();
        }
    }

    public static void i(String tag, String msg) {
        if (LOGCAT) {
            android.util.Log.i(tag, msg);
        } else {
            System.out.printf("I/%s: %s\n", tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOGCAT) {
            android.util.Log.w(tag, msg);
        } else {
            System.err.printf("W/%s: %s\n", tag, msg);
        }
    }

    public static void w(String tag, String msg, @NonNull Throwable tr) {
        if (LOGCAT) {
            android.util.Log.w(tag, msg, tr);
        } else {
            System.err.printf("W/%s: %s\n", tag, msg);
            tr.printStackTrace();
        }
    }

    public static void e(String tag, String msg) {
        if (LOGCAT) {
            android.util.Log.e(tag, msg);
        } else {
            System.err.printf("E/%s: %s\n", tag, msg);
        }
    }

    public static void wtf(String tag, String msg) {
        if (LOGCAT) {
            android.util.Log.wtf(tag, msg);
        } else {
            System.err.printf("WTF/%s: %s\n", tag, msg);
        }
    }

    public static void wtf(String tag, String msg, @NonNull Throwable tr) {
        if (LOGCAT) {
            android.util.Log.wtf(tag, msg, tr);
        } else {
            System.err.printf("WTF/%s: %s\n", tag, msg);
            tr.printStackTrace();
        }
    }
}
