package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.Side;

public class FloorOperatorTest extends AbstractSidedOperatorTest<FloorOperator> {
    @NonNull
    @Override
    protected FloorOperator create(Side side) {
        return new FloorOperator(side);
    }

    @NonNull
    @Override
    protected Parcelable.Creator<FloorOperator> getCreator() {
        return FloorOperator.CREATOR;
    }
}
