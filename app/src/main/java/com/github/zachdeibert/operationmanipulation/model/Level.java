package com.github.zachdeibert.operationmanipulation.model;

public enum Level {
    Level1(2, 10, OperatorType.ADDITION, OperatorType.SUBTRACTION),
    Level2(3, 10, OperatorType.ADDITION, OperatorType.SUBTRACTION),
    Level3(2, 8, OperatorType.MULTIPLICATION, OperatorType.DIVISION),
    Level4(3, 8, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION),
    Level5(3, 6, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT),
    Level6(4, 4, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT);

    private final int numOperands;
    private final int unsolvedAmount;
    private final OperatorType[] operators;

    public int getNumberOfOperands() {
        return numOperands;
    }

    public int getMaximumUnsolvedPuzzles() {
        return unsolvedAmount;
    }

    public OperatorType[] getAllowedOperators() {
        return operators;
    }

    Level(int numOperands, int unsolvedAmount, OperatorType... operators) {
        this.numOperands = numOperands;
        this.unsolvedAmount = unsolvedAmount;
        this.operators = operators;
    }
}
