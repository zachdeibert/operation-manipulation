package com.github.zachdeibert.operationmanipulation.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Equation implements Parcelable {
    public static final Creator<Equation> CREATOR = new Creator<Equation>() {
        @Override
        public Equation createFromParcel(Parcel source) {
            return new Equation(source);
        }

        @Override
        public Equation[] newArray(int size) {
            return new Equation[size];
        }
    };

    private final List<ExpressionItem> lhs;
    private int operandCount;
    private Operand rhs;

    public ExpressionItem[] getLeftSide() {
        return lhs.toArray(new ExpressionItem[0]);
    }

    public void setLeftSide(Operand... ops) {
        lhs.clear();
        lhs.addAll(Arrays.asList(ops));
        operandCount = ops.length;
    }

    public Operand getRightSide() {
        return rhs;
    }

    public void setRightSide(Operand rhs) {
        this.rhs = rhs;
    }

    public int getOperandCount() {
        return operandCount;
    }

    public void clear() {
        List<ExpressionItem> items = new ArrayList<>();
        for (ExpressionItem item : lhs) {
            if (!(item instanceof Operand)) {
                items.add(item);
            }
        }
        lhs.removeAll(items);
    }

    public void insertOperatorBefore(ExpressionItem reference, Operator operator) {
        int index = lhs.indexOf(reference);
        lhs.add(index, operator);
    }

    public void insertOperatorAfter(ExpressionItem reference, Operator operator) {
        int index = lhs.indexOf(reference);
        lhs.add(index + 1, operator);
    }

    public void removeOperator(Operator operator) {
        lhs.remove(operator);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (ExpressionItem item : getLeftSide()) {
            str.append(item);
            str.append(' ');
        }
        str.append("= ");
        str.append(getRightSide());
        return str.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(lhs);
        rhs.writeToParcel(dest, flags);
    }

    public Equation() {
        lhs = new ArrayList<>();
    }

    public Equation(Operand rhs, Operand... lhs) {
        this();
        setLeftSide(lhs);
        setRightSide(rhs);
    }

    private Equation(Parcel parcel) {
        lhs = parcel.readArrayList(ClassLoader.getSystemClassLoader());
        rhs = Operand.CREATOR.createFromParcel(parcel);
    }
}
