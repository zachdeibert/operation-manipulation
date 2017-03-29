package com.github.zachdeibert.operationmanipulation.model;

import android.os.Parcel;
import android.util.Log;

public abstract class Operator extends ExpressionItem implements Cloneable {
    private final int order;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public int getOrder() {
        return order;
    }

    @Override
    public Operator clone() {
        try {
            return (Operator) super.clone();
        } catch (CloneNotSupportedException ex) {
            Log.w("Operator", "Unable to clone", ex);
            return this;
        }
    }

    protected Operator(int order) {
        this.order = order;
    }
}
