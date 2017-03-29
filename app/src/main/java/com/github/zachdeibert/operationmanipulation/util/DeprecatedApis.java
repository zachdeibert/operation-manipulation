package com.github.zachdeibert.operationmanipulation.util;

import android.content.ClipData;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DeprecatedApis {
    @Nullable
    private static final Method startDrag = resolveStartDrag();

    private static Method resolveStartDrag() {
        try {
            return View.class.getMethod("startDragAndDrop", ClipData.class, View.DragShadowBuilder.class, Object.class, int.class);
        } catch (ReflectiveOperationException e) {
            try {
                return View.class.getMethod("startDrag", ClipData.class, View.DragShadowBuilder.class, Object.class, int.class);
            } catch (ReflectiveOperationException ex) {
                Log.wtf("DeprecatedApis", "Reflection doesn't work", ex);
                return null;
            }
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean startDrag(View _this, ClipData data, View.DragShadowBuilder shadowBuilder, Object myLocalState, @SuppressWarnings("SameParameterValue") int flags) {
        try {
            return startDrag != null && (boolean) startDrag.invoke(_this, data, shadowBuilder, myLocalState, flags);
        } catch (IllegalAccessException ex) {
            Log.wtf("DeprecatedApis", "Reflection access error", ex);
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            if (ex.getTargetException() instanceof RuntimeException) {
                throw (RuntimeException) ex.getTargetException();
            } else {
                Log.wtf("DeprecatedApis", "Java doesn't check exceptions correctly");
                throw new RuntimeException(ex.getTargetException());
            }
        }
    }
}
