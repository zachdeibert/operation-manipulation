package com.github.zachdeibert.operationmanipulation.model.operators;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.github.zachdeibert.operationmanipulation.model.Side;

public class ParenthesisOperatorTest extends AbstractSidedOperatorTest<ParenthesisOperator> {
    @NonNull
    @Override
    protected ParenthesisOperator create(Side side) {
        return new ParenthesisOperator(side);
    }

    @NonNull
    @Override
    protected Parcelable.Creator<ParenthesisOperator> getCreator() {
        return ParenthesisOperator.CREATOR;
    }
}
