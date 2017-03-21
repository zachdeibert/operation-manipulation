package com.github.zachdeibert.operationmanipulation.model;

import android.os.Parcel;
import android.util.Log;

import com.github.zachdeibert.operationmanipulation.model.operators.AbsoltueValueOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.AdditionOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.CeilingOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.DivisionOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.ExponentOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.FactorialOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.FloorOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.MultiplicationOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.ParenthesisOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.SubtractionOperator;

public abstract class Operator extends ExpressionItem implements Cloneable {
    public static final Operator ADDITION = new AdditionOperator();
    public static final Operator SUBTRACTION = new SubtractionOperator();
    public static final Operator MULTIPLICATION = new MultiplicationOperator();
    public static final Operator DIVISION = new DivisionOperator();
    public static final Operator EXPONENT = new ExponentOperator();
    public static final Operator FACTORIAL = new FactorialOperator();
    public static final Operator LEFT_PARENTHESIS = new ParenthesisOperator(Side.Left);
    public static final Operator RIGHT_PARENTHESIS = new ParenthesisOperator(Side.Right);
    public static final Operator ABSOLUTE_VALUE = new AbsoltueValueOperator();
    public static final Operator LEFT_CEILING = new CeilingOperator(Side.Left);
    public static final Operator RIGHT_CEILING = new CeilingOperator(Side.Right);
    public static final Operator LEFT_FLOOR = new FloorOperator(Side.Left);
    public static final Operator RIGHT_FLOOR = new FloorOperator(Side.Right);

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
