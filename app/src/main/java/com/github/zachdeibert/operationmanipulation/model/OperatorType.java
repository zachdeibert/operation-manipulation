package com.github.zachdeibert.operationmanipulation.model;

import android.util.Log;

public enum OperatorType {
    ADDITION(Operator.ADDITION),
    SUBTRACTION(Operator.SUBTRACTION),
    MULTIPLICATION(Operator.MULTIPLICATION),
    DIVISION(Operator.DIVISION),
    EXPONENT(Operator.EXPONENT);

    private final Operator operator;

    public Operator getOperator() {
        return operator;
    }

    public static OperatorType valueOf(Operator operator) {
        for (OperatorType type : values()) {
            if (type.getOperator() == operator) {
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
