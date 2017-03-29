package com.github.zachdeibert.operationmanipulation.view.animation;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

public class RainbowAnimator extends Animator {
    @NonNull
    private final WeakReference<View> view;
    @Nullable
    private Thread thread;
    private float hue;
    private long lastFrame;

    public void start() {
        lastFrame = System.currentTimeMillis();
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (view.get() != null) {
                        long frame = System.currentTimeMillis();
                        hue += (frame - lastFrame) / 10f;
                        lastFrame = frame;
                        while (hue > 360) {
                            hue -= 360;
                        }
                        view.get().post(new Runnable() {
                            @Override
                            public void run() {
                                view.get().setBackgroundColor(Color.HSVToColor(new float[] { hue, 1, 1 }));
                                interrupt();
                            }
                        });
                        try {
                            Thread.sleep(Integer.MAX_VALUE);
                        } catch (InterruptedException ex) {
                            // This is expected to happen
                        }
                    }
                } catch (NullPointerException ex) {
                    Log.d("RainbowAnimator", "Garbage collected while animating", ex);
                }
            }
        };
        thread.start();
    }

    @Override
    public long getStartDelay() {
        return 0;
    }

    @Override
    public void setStartDelay(long startDelay) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    @Override
    public Animator setDuration(long duration) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public void setInterpolator(TimeInterpolator value) {
    }

    @Override
    public boolean isRunning() {
        return thread != null && thread.isAlive();
    }

    public RainbowAnimator(View view) {
        this.view = new WeakReference<>(view);
    }
}
