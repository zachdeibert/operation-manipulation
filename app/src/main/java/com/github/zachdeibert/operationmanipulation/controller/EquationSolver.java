package com.github.zachdeibert.operationmanipulation.controller;

import android.util.Log;

import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.ExpressionItem;
import com.github.zachdeibert.operationmanipulation.model.Operand;
import com.github.zachdeibert.operationmanipulation.model.Operator;

public class EquationSolver {
    public static double solve(ExpressionItem... expression) {
        Operator op = Operator.ADDITION;
        double value = 0;
        for (ExpressionItem item : expression) {
            if (item instanceof Operand) {
                if (op == null) {
                    Log.d("EquationSolver", "Missing operator");
                    return Double.NaN;
                } else {
                    value = op.run(value, ((Operand) item).getValue());
                    op = null;
                }
            } else if (item instanceof Operator) {
                if (op == null) {
                    op = (Operator) item;
                } else {
                    Log.d("EquationSolver", "Multiple adjacent operators");
                    return Double.NaN;
                }
            }
        }
        return value;
    }

    public static boolean isCorrect(Equation equation) {
        double left = solve(equation.getLeftSide());
        double right = equation.getRightSide().getValue();
        return left == right && !Double.isNaN(left);
    }
}
