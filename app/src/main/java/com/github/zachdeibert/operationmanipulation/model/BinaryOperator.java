package com.github.zachdeibert.operationmanipulation.model;

public abstract class BinaryOperator extends Operator {
    public abstract double run(double lhs, double rhs);

    protected BinaryOperator(int order) {
        super(order);
    }
}
