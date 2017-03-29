package com.github.zachdeibert.operationmanipulation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.zachdeibert.operationmanipulation.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Equation implements Parcelable, Serializable {
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
    private Level level;

    public ExpressionItem[] getLeftSide() {
        return lhs.toArray(new ExpressionItem[0]);
    }

    private void setLeftSide(Operand... ops) {
        lhs.clear();
        lhs.addAll(Arrays.asList(ops));
        operandCount = ops.length;
    }

    public Operand getRightSide() {
        return rhs;
    }

    private void setRightSide(Operand rhs) {
        this.rhs = rhs;
    }

    public Level getLevel() {
        return level;
    }

    private void setLevel(Level level) {
        this.level = level;
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

    public void insertOperatorAt(int index, Operator operator) {
        lhs.add(index, operator);
    }

    public void insertOperatorBefore(ExpressionItem reference, Operator operator) {
        insertOperatorAt(lhs.indexOf(reference), operator);
    }

    public void insertOperatorAfter(ExpressionItem reference, Operator operator) {
        insertOperatorAt(lhs.indexOf(reference) + 1, operator);
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
        getRightSide().writeToParcel(dest, flags);
        dest.writeInt(getLevel().ordinal());
    }

    public Equation() {
        lhs = new ArrayList<>();
    }

    public Equation(Level level, Operand rhs, Operand... lhs) {
        this();
        setLevel(level);
        setLeftSide(lhs);
        setRightSide(rhs);
    }

    private Equation(Parcel parcel) {
        lhs = CollectionUtils.checkedAssignment(parcel.readArrayList(ClassLoader.getSystemClassLoader()), ExpressionItem.class);
        rhs = Operand.CREATOR.createFromParcel(parcel);
        level = Level.values()[parcel.readInt()];
    }
}
