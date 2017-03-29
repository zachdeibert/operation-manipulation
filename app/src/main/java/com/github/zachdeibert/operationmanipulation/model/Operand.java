package com.github.zachdeibert.operationmanipulation.model;

import android.os.Parcel;
import android.support.annotation.NonNull;

public class Operand extends ExpressionItem {
    @NonNull
    public static final Creator<Operand> CREATOR = new Creator<Operand>() {
        @NonNull
        @Override
        public Operand createFromParcel(@NonNull Parcel source) {
            return new Operand(source);
        }

        @NonNull
        @Override
        public Operand[] newArray(int size) {
            return new Operand[size];
        }
    };

    private final int value;

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(getValue());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(value);
    }

    public Operand(int value) {
        this.value = value;
    }

    private Operand(@NonNull Parcel parcel) {
        this.value = parcel.readInt();
    }
}
