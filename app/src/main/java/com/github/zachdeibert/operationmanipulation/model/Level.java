package com.github.zachdeibert.operationmanipulation.model;

public enum Level {
    Level1(2, 10, 1, 10, 0.6f, OperatorType.ADDITION, OperatorType.SUBTRACTION),
    Level2(3, 10, 2, 25, 0.7f, OperatorType.ADDITION, OperatorType.SUBTRACTION),
    Level3(2, 8, 4, 100, 0.8f, OperatorType.MULTIPLICATION, OperatorType.DIVISION),
    Level4(3, 8, 8, 200, 0.85f, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION),
    Level5(3, 6, 16, 500, 0.9f, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS, OperatorType.ABSOLUTE_VALUE),
    Level6(3, 6, 32, 1000, 0.9f, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.FACTORIAL, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS, OperatorType.ABSOLUTE_VALUE),
    Level7(4, 4, 64, 2000, 2, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.FACTORIAL);

    private final int numOperands;
    private final int unsolvedAmount;
    private final int equationScore;
    private final int minimumAdvancePoints;
    private final float minimumAdvanceAccuracy;
    private final OperatorType[] operators;

    public int getNumberOfOperands() {
        return numOperands;
    }

    public int getMaximumUnsolvedPuzzles() {
        return unsolvedAmount;
    }

    public int getEquationSolvingScore() {
        return equationScore;
    }

    public int getMinimumAdvancePoints() {
        return minimumAdvancePoints;
    }

    public float getMinimumAdvanceAccuracy() {
        return minimumAdvanceAccuracy;
    }

    public OperatorType[] getAllowedOperators() {
        return operators;
    }

    Level(int numOperands, int unsolvedAmount, int equationScore, int minimumAdvancePoints, float minimumAdvanceAccuracy, OperatorType... operators) {
        this.numOperands = numOperands;
        this.unsolvedAmount = unsolvedAmount;
        this.equationScore = equationScore;
        this.minimumAdvancePoints = minimumAdvancePoints;
        this.minimumAdvanceAccuracy = minimumAdvanceAccuracy;
        this.operators = operators;
    }
}
