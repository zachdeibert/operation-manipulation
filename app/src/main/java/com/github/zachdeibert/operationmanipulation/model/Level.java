package com.github.zachdeibert.operationmanipulation.model;

public enum Level {
    FiveAllFactorial(5, 2, 2, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.FACTORIAL),

    FiveAll(5, 2, 0.95f, FiveAllFactorial, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT),
    FourAllFactorial(4, 2, 0.95f, FiveAllFactorial, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.FACTORIAL),

    FiveAllParen(5, 3, 2, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS),
    FourAll(4, 3, 0.95f, FiveAll, FourAllFactorial, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT),
    ThreeAllFactorial(3, 3, 0.95f, FourAllFactorial, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.FACTORIAL),
    FourAllFactorialParen(4, 3, 0.95f, FourAllFactorial, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.FACTORIAL, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS),
    FiveAllFactorialParenAbs(5, 3, 2, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.FACTORIAL, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS, OperatorType.ABSOLUTE_VALUE),

    FourAllParen(4, 3, 0.90f, FiveAllParen, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS),
    ThreeAll(3, 3, 0.90f, FourAll, ThreeAllFactorial, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT),
    ThreeAllFactorialParen(3, 3, 0.90f, ThreeAllFactorial, FourAllFactorialParen, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.FACTORIAL, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS),
    FourAllFactorialParenAbs(4, 3, 0.90f, FourAllFactorialParen, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.FACTORIAL, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS, OperatorType.ABSOLUTE_VALUE),
    FiveEverythingFactorial(5, 3, 0.90f, FiveAllFactorialParenAbs, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.FACTORIAL, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS, OperatorType.ABSOLUTE_VALUE, OperatorType.LEFT_CEILING, OperatorType.RIGHT_CEILING, OperatorType.LEFT_FLOOR, OperatorType.RIGHT_FLOOR),

    ThreeAllParen(3, 4, 0.85f, FourAllParen, ThreeAll, ThreeAllFactorialParen, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS),
    ThreeAllFactorialParenAbs(3, 4, 0.85f, ThreeAllFactorialParen, FourAllFactorialParenAbs, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.FACTORIAL, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS, OperatorType.ABSOLUTE_VALUE),
    FourEverythingFactorial(4, 4, 0.85f, FiveEverythingFactorial, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.FACTORIAL, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS, OperatorType.ABSOLUTE_VALUE, OperatorType.LEFT_CEILING, OperatorType.RIGHT_CEILING, OperatorType.LEFT_FLOOR, OperatorType.RIGHT_FLOOR),

    ThreeAllParenAbs(3, 4, 0.80f, ThreeAllParen, ThreeAllFactorialParenAbs, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS, OperatorType.ABSOLUTE_VALUE),
    ThreeEverythingFactorial(3, 4, 0.80f, ThreeAllFactorialParenAbs, FourEverythingFactorial, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.FACTORIAL, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS, OperatorType.ABSOLUTE_VALUE, OperatorType.LEFT_CEILING, OperatorType.RIGHT_CEILING, OperatorType.LEFT_FLOOR, OperatorType.RIGHT_FLOOR),

    ThreeEverything(3, 5, 0.80f, ThreeAllParenAbs, ThreeEverythingFactorial, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS, OperatorType.ABSOLUTE_VALUE, OperatorType.LEFT_CEILING, OperatorType.RIGHT_CEILING, OperatorType.LEFT_FLOOR, OperatorType.RIGHT_FLOOR),

    FiveMultiplicationExp(5, 5, 2, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT),
    TwoEverything(2, 5, 0.80f, ThreeEverything, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS, OperatorType.ABSOLUTE_VALUE, OperatorType.LEFT_CEILING, OperatorType.RIGHT_CEILING, OperatorType.LEFT_FLOOR, OperatorType.RIGHT_FLOOR),

    FiveMultiplication(5, 6, 2, OperatorType.MULTIPLICATION, OperatorType.DIVISION),
    FourMultiplicationExp(4, 6, 0.75f, FiveMultiplicationExp, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT),
    TwoAllParenAbs(2, 6, 0.75f, ThreeAllParenAbs, TwoEverything, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS, OperatorType.ABSOLUTE_VALUE),

    FourMultiplication(4, 7, 0.75f, FiveMultiplication, OperatorType.MULTIPLICATION, OperatorType.DIVISION),
    ThreeMultiplicationExp(3, 7, 0.75f, FourMultiplicationExp, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT),
    TwoAllParen(2, 7, 0.75f, ThreeAllParen, TwoAllParenAbs, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT, OperatorType.LEFT_PARENTHESIS, OperatorType.RIGHT_PARENTHESIS),
    FiveAddition(5, 7, 2, OperatorType.ADDITION, OperatorType.SUBTRACTION),

    ThreeMultiplication(3, 8, 0.70f, FourMultiplication, ThreeMultiplicationExp, OperatorType.MULTIPLICATION, OperatorType.DIVISION),
    TwoMultiplicationExp(2, 8, 0.70f, ThreeMultiplicationExp, TwoAllParen, OperatorType.MULTIPLICATION, OperatorType.DIVISION, OperatorType.EXPONENT),
    TwoAdditionMultiplication(2, 8, 0.70f, TwoAllParen, OperatorType.ADDITION, OperatorType.SUBTRACTION, OperatorType.MULTIPLICATION, OperatorType.DIVISION),
    FourAddition(4, 8, 0.70f, FiveAddition, OperatorType.ADDITION, OperatorType.SUBTRACTION),

    TwoMultiplication(2, 9, 0.65f, ThreeMultiplication, TwoMultiplicationExp, TwoAdditionMultiplication, OperatorType.MULTIPLICATION, OperatorType.DIVISION),
    ThreeAddition(3, 9, 0.65f, TwoAdditionMultiplication, FourAddition, OperatorType.ADDITION, OperatorType.SUBTRACTION),

    TwoAddition(2, 10, 0.60f, TwoMultiplication, ThreeAddition, OperatorType.ADDITION, OperatorType.SUBTRACTION);

    private final int numOperands;
    private final int unsolvedAmount;
    private final int equationScore;
    private final float minimumAdvanceAccuracy;
    private final Level[] nextLevels;
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

    public float getMinimumAdvanceAccuracy() {
        return minimumAdvanceAccuracy;
    }

    public Level[] getNextLevels() {
        return nextLevels;
    }

    public OperatorType[] getAllowedOperators() {
        return operators;
    }

    Level(int numOperands, int unsolvedAmount, float minimumAdvanceAccuracy, Level[] nextLevels, OperatorType... operators) {
        this.numOperands = numOperands;
        this.unsolvedAmount = unsolvedAmount;
        int operatorScore = 0;
        for (OperatorType operator : operators) {
            operatorScore += operator.getScore();
        }
        this.equationScore = (int) Math.pow(operatorScore, Math.sqrt(numOperands));
        this.minimumAdvanceAccuracy = minimumAdvanceAccuracy;
        this.nextLevels = nextLevels;
        this.operators = operators;
    }

    Level(int numOperands, int unsolvedAmount, float minimumAdvanceAccuracy, OperatorType... operators) {
        this(numOperands, unsolvedAmount, minimumAdvanceAccuracy, new Level[0], operators);
    }

    Level(int numOperands, int unsolvedAmount, float minimumAdvanceAccuracy, Level nextLevel0, OperatorType... operators) {
        this(numOperands, unsolvedAmount, minimumAdvanceAccuracy, new Level[] {
            nextLevel0
        }, operators);
    }

    Level(int numOperands, int unsolvedAmount, float minimumAdvanceAccuracy, Level nextLevel0, Level nextLevel1, OperatorType... operators) {
        this(numOperands, unsolvedAmount, minimumAdvanceAccuracy, new Level[] {
            nextLevel0,
            nextLevel1
        }, operators);
    }

    Level(int numOperands, int unsolvedAmount, float minimumAdvanceAccuracy, Level nextLevel0, Level nextLevel1, Level nextLevel2, OperatorType... operators) {
        this(numOperands, unsolvedAmount, minimumAdvanceAccuracy, new Level[] {
            nextLevel0,
            nextLevel1,
            nextLevel2
        }, operators);
    }
}
