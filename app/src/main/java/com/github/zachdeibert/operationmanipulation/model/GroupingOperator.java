package com.github.zachdeibert.operationmanipulation.model;

public abstract class GroupingOperator extends Operator {
    private final int type;

    public abstract double run(double val);

    public abstract int getLevel();

    public int getType() {
        return type;
    }

    protected GroupingOperator(int type) {
        super(4);
        this.type = type;
    }
}
