package com.github.zachdeibert.operationmanipulation.model;

public abstract class UnaryOperator extends Operator {
    public abstract double run(double val);

    @SuppressWarnings("unused")
    protected UnaryOperator() {
        super(3);
    }
}
