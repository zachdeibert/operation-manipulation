package com.github.zachdeibert.operationmanipulation.model;

import android.support.annotation.NonNull;
import android.util.Log;

public enum OperatorType {
    ADDITION(Operators.ADDITION, 1),
    SUBTRACTION(Operators.SUBTRACTION, 1),
    MULTIPLICATION(Operators.MULTIPLICATION, 2),
    DIVISION(Operators.DIVISION, 2),
    EXPONENT(Operators.EXPONENT, 3),
    FACTORIAL(Operators.FACTORIAL, 4),
    LEFT_PARENTHESIS(Operators.LEFT_PARENTHESIS, -1),
    RIGHT_PARENTHESIS(Operators.RIGHT_PARENTHESIS, -1),
    ABSOLUTE_VALUE(Operators.ABSOLUTE_VALUE, -1),
    LEFT_CEILING(Operators.LEFT_CEILING, -1),
    RIGHT_CEILING(Operators.RIGHT_CEILING, -1),
    LEFT_FLOOR(Operators.LEFT_FLOOR, -1),
    RIGHT_FLOOR(Operators.RIGHT_FLOOR, -1),
    UNKNOWN(null, 0);

    private final Operator operator;
    private final int score;

    public Operator getOperator() {
        return operator;
    }

    public int getScore() {
        return score;
    }

    public static @NonNull OperatorType valueOf(@NonNull Operator operator) {
        for (OperatorType type : values()) {
            if (type.getOperator().getClass() == operator.getClass()) {
                return type;
            }
        }
        Log.w("OperatorType", "Unknown operator type ".concat(operator.getClass().getName()));
        return UNKNOWN;
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
