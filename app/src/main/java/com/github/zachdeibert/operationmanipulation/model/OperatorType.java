package com.github.zachdeibert.operationmanipulation.model;

import android.util.Log;

public enum OperatorType {
    ADDITION(Operator.ADDITION, 1),
    SUBTRACTION(Operator.SUBTRACTION, 1),
    MULTIPLICATION(Operator.MULTIPLICATION, 2),
    DIVISION(Operator.DIVISION, 2),
    EXPONENT(Operator.EXPONENT, 3),
    FACTORIAL(Operator.FACTORIAL, 4),
    LEFT_PARENTHESIS(Operator.LEFT_PARENTHESIS, -1),
    RIGHT_PARENTHESIS(Operator.RIGHT_PARENTHESIS, -1),
    ABSOLUTE_VALUE(Operator.ABSOLUTE_VALUE, -1),
    LEFT_CEILING(Operator.LEFT_CEILING, -1),
    RIGHT_CEILING(Operator.RIGHT_CEILING, -1),
    LEFT_FLOOR(Operator.LEFT_FLOOR, -1),
    RIGHT_FLOOR(Operator.RIGHT_FLOOR, -1);

    private final Operator operator;
    private final int score;

    public Operator getOperator() {
        return operator;
    }

    public int getScore() {
        return score;
    }

    public static OperatorType valueOf(Operator operator) {
        for (OperatorType type : values()) {
            if (type.getOperator().getClass() == operator.getClass()) {
                return type;
            }
        }
        Log.w("OperatorType", "Unknown operator type ".concat(operator.getClass().getName()));
        return null;
    }

    @Override
    public String toString() {
        return getOperator().toString();
    }

    OperatorType(Operator operator, int score) {
        this.operator = operator;
        this.score = score;
    }
}
