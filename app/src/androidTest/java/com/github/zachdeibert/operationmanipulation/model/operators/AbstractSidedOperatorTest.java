package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;
import com.github.zachdeibert.operationmanipulation.model.Side;

import junit.framework.Assert;

import org.junit.Test;

abstract class AbstractSidedOperatorTest<T extends GroupingOperator> {
    @NonNull
    protected abstract T create(Side side);

    @NonNull
    protected abstract Parcelable.Creator<T> getCreator();

    @Test
    public void writeToParcel() throws Exception {
        Parcel parcel = Parcel.obtain();
        T op = create(Side.Left);
        op.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        T op2 = getCreator().createFromParcel(parcel);
        Assert.assertNotSame("Serialized/deserialized objects should not be the same", op, op2);
        Assert.assertEquals("Serialization fail", op.toString(), op2.toString());
        op = create(Side.Right);
        parcel.setDataPosition(0);
        op.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        op2 = getCreator().createFromParcel(parcel);
        parcel.recycle();
        Assert.assertNotSame("Serialized/deserialized objects should not be the same", op, op2);
        Assert.assertEquals("Serialization fail", op.toString(), op2.toString());
    }
}
