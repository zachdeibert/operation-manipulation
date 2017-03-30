package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.Side;

public class CeilingOperatorTest extends AbstractSidedOperatorTest<CeilingOperator> {
    @NonNull
    @Override
    protected CeilingOperator create(Side side) {
        return new CeilingOperator(side);
    }

    @NonNull
    @Override
    protected Parcelable.Creator<CeilingOperator> getCreator() {
        return CeilingOperator.CREATOR;
    }
}
