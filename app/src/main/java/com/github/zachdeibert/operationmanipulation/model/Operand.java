package com.github.zachdeibert.operationmanipulation.model;

import android.os.Parcel;

public class Operand extends ExpressionItem {
    public static final Creator<Operand> CREATOR = new Creator<Operand>() {
        @Override
        public Operand createFromParcel(Parcel source) {
            return new Operand(source);
        }

        @Override
        public Operand[] newArray(int size) {
            return new Operand[size];
        }
    };

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
    }

    public Operand() {
    }

    public Operand(int value) {
        this.value = value;
    }

    private Operand(Parcel parcel) {
        this.value = parcel.readInt();
    }
}
