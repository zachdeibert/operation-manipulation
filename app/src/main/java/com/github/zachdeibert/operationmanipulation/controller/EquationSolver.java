package com.github.zachdeibert.operationmanipulation.controller;

import android.util.Log;

import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.ExpressionItem;
import com.github.zachdeibert.operationmanipulation.model.Operand;
import com.github.zachdeibert.operationmanipulation.model.Operator;

import java.util.LinkedList;
import java.util.List;

public class EquationSolver {
    private static void solve(List<Object> expression, int order) {
        for (int i = 0; i < expression.size(); ++i) {
            Object item = expression.get(i);
            if (item instanceof Operator && ((Operator) item).getOrder() == order) {
                double lhs = (double) expression.get(i - 1);
                double rhs = (double) expression.get(i + 1);
                double result = ((Operator) item).run(lhs, rhs);
                expression.set(i - 1, result);
                expression.remove(i);
                expression.remove(i);
                --i;
            }
        }
        if (order > 0) {
            solve(expression, order - 1);
        }
    }

    public static double solve(ExpressionItem... expression) {
        int maxOrder = 0;
        List<Object> expr = new LinkedList<>();
        for (ExpressionItem item : expression) {
            if (item instanceof Operator) {
                maxOrder = Math.max(maxOrder, ((Operator) item).getOrder());
                expr.add(item);
            } else {
                expr.add((double) ((Operand) item).getValue());
            }
        }
        solve(expr, maxOrder);
        return (double) expr.get(0);
    }

    public static boolean isComplete(Equation equation) {
        boolean wasOperator = true;
        for (ExpressionItem item : equation.getLeftSide()) {
            if (item instanceof Operator) {
                if (wasOperator) {
                    Log.d("EquationSolver", "Multiple adjacent operators");
                    return false;
                } else {
                    wasOperator = true;
                }
            } else {
                if (wasOperator) {
                    wasOperator = false;
                } else {
                    Log.d("EquationSovler", "Missing operator");
                    return false;
                }
            }
        }
        return !wasOperator;
    }

    public static boolean isCorrect(Equation equation) {
        double left = solve(equation.getLeftSide());
        double right = equation.getRightSide().getValue();
        return left == right && !Double.isNaN(left);
    }
}
