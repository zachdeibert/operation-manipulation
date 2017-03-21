package com.github.zachdeibert.operationmanipulation.model;

import android.util.Log;

public enum OperatorType {
    ADDITION(Operator.ADDITION),
    SUBTRACTION(Operator.SUBTRACTION),
    MULTIPLICATION(Operator.MULTIPLICATION),
    DIVISION(Operator.DIVISION),
    EXPONENT(Operator.EXPONENT),
    FACTORIAL(Operator.FACTORIAL),
    LEFT_PARENTHESIS(Operator.LEFT_PARENTHESIS),
    RIGHT_PARENTHESIS(Operator.RIGHT_PARENTHESIS),
    ABSOLUTE_VALUE(Operator.ABSOLUTE_VALUE),
    LEFT_CEILING(Operator.LEFT_CEILING),
    RIGHT_CEILING(Operator.RIGHT_CEILING),
    LEFT_FLOOR(Operator.LEFT_FLOOR),
    RIGHT_FLOOR(Operator.RIGHT_FLOOR);

    private final Operator operator;

    public Operator getOperator() {
        return operator;
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

    OperatorType(Operator operator) {
        this.operator = operator;
    }
}
