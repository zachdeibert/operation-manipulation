package com.github.zachdeibert.operationmanipulation.controller;

import android.util.Log;

import com.github.zachdeibert.operationmanipulation.BuildConfig;
import com.github.zachdeibert.operationmanipulation.model.BinaryOperator;
import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.ExpressionItem;
import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;
import com.github.zachdeibert.operationmanipulation.model.Operand;
import com.github.zachdeibert.operationmanipulation.model.Operator;
import com.github.zachdeibert.operationmanipulation.model.Operators;
import com.github.zachdeibert.operationmanipulation.model.UnaryOperator;
import com.github.zachdeibert.operationmanipulation.model.operators.SubtractionOperator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EquationSolver {
    private static final boolean HIDE_PARSE_ERRORS = true;

    private static void parseError(String error) {
        if (!HIDE_PARSE_ERRORS && BuildConfig.DEBUG) {
            Log.d("EquationSolver", error);
        }
    }

    private static void solve(List<Object> expression, int order) {
        for (int i = 0; i < expression.size(); ++i) {
            Object item = expression.get(i);
            if (item instanceof Operator && ((Operator) item).getOrder() == order) {
                if (item instanceof BinaryOperator) {
                    Object lhsObj = i > 0 ? expression.get(i - 1) : null;
                    Object rhsObj = expression.get(i + 1);
                    double lhs;
                    double rhs;
                    if (lhsObj == null || !(lhsObj instanceof Double && rhsObj instanceof Double)) {
                        if (rhsObj instanceof SubtractionOperator) {
                            expression.remove(i + 1);
                            lhs = (double) lhsObj;
                            rhs = -(double) expression.get(i + 1);
                        } else {
                            expression.remove(i);
                            expression.set(i, -((double) rhsObj));
                            continue;
                        }
                    } else {
                        lhs = (double) lhsObj;
                        rhs = (double) rhsObj;
                    }
                    double result = ((BinaryOperator) item).run(lhs, rhs);
                    expression.set(i - 1, result);
                    expression.remove(i);
                    expression.remove(i);
                    --i;
                } else if (item instanceof UnaryOperator) {
                    double val = (double) expression.get(i - 1);
                    double result = ((UnaryOperator) item).run(val);
                    expression.set(i - 1, result);
                    expression.remove(i);
                    --i;
                } else if (item instanceof GroupingOperator) {
                    List<ExpressionItem> subexpression = new ArrayList<>();
                    int levels = ((GroupingOperator) item).getLevel();
                    if (levels == 0) {
                        levels = Integer.MIN_VALUE;
                    }
                    expression.remove(i);
                    GroupingOperator other = null;
                    while (levels != 0) {
                        Object next = expression.get(i);
                        expression.remove(i);
                        if (next instanceof GroupingOperator && ((GroupingOperator) next).getType() == ((GroupingOperator) item).getType()) {
                            other = (GroupingOperator) next;
                            levels += ((GroupingOperator) next).getLevel();
                            if (levels == Integer.MIN_VALUE) {
                                break;
                            } else if (levels != 0) {
                                subexpression.add((ExpressionItem) next);
                            }
                        } else if (next instanceof ExpressionItem) {
                            subexpression.add((ExpressionItem) next);
                        } else {
                            subexpression.add(new Operand((int) (double) next));
                        }
                    }
                    double val = ((GroupingOperator) item).run(solve(subexpression.toArray(new ExpressionItem[0])), other);
                    expression.add(i, val);
                    if (i > 0 && !(expression.get(i - 1) instanceof Operator)) {
                        expression.add(i++, Operators.MULTIPLICATION);
                    }
                    if (i < expression.size() - 1 && !(expression.get(i + 1) instanceof Operator)) {
                        expression.add(++i, Operators.MULTIPLICATION);
                    }
                }
            }
        }
        if (order > 0) {
            solve(expression, order - 1);
        }
    }

    private static double solve(ExpressionItem... expression) {
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

    private static boolean isComplete(ExpressionItem... expression) {
        boolean wasOperator = true;
        boolean canUnary = false;
        boolean implicitMultiply = false;
        boolean wasNegate = false;
        for (int i = 0; i < expression.length; ++i) {
            ExpressionItem item = expression[i];
            if (item instanceof BinaryOperator) {
                if (wasOperator) {
                    if (item instanceof SubtractionOperator) {
                        if (wasNegate) {
                            parseError("Multiple negations on a single operand");
                            return false;
                        } else {
                            wasNegate = true;
                        }
                    } else {
                        parseError("Multiple adjacent operators");
                        return false;
                    }
                } else {
                    wasOperator = true;
                    implicitMultiply = false;
                }
            } else if (item instanceof UnaryOperator) {
                //noinspection ConstantConditions
                if (wasOperator || wasNegate) {
                    parseError("Multiple adjacent operators");
                    return false;
                } else //noinspection ConstantConditions
                    if (!canUnary) {
                    parseError("Started with an operator");
                    return false;
                } else {
                    implicitMultiply = false;
                }
            } else if (item instanceof GroupingOperator) {
                List<ExpressionItem> subexpression = new ArrayList<>();
                int levels = ((GroupingOperator) item).getLevel();
                if (levels == 0) {
                    levels = Integer.MIN_VALUE;
                }
                while (levels != 0 && i < expression.length - 1) {
                    ExpressionItem next = expression[++i];
                    if (next instanceof GroupingOperator && ((GroupingOperator) next).getType() == ((GroupingOperator) item).getType()) {
                        levels += ((GroupingOperator) next).getLevel();
                        if (levels == Integer.MIN_VALUE) {
                            levels = 0;
                            break;
                        } else if (levels != 0) {
                            subexpression.add(next);
                        }
                    } else {
                        subexpression.add(next);
                    }
                }
                if (levels != 0) {
                    parseError("Unmatched group");
                    return false;
                }
                if (isComplete(subexpression.toArray(new ExpressionItem[0]))) {
                    wasOperator = false;
                    canUnary = true;
                    wasNegate = false;
                    implicitMultiply = true;
                } else {
                    parseError("Subexpression failed");
                    return false;
                }
            } else {
                if (wasOperator || implicitMultiply) {
                    wasOperator = false;
                    canUnary = true;
                    wasNegate = false;
                    implicitMultiply = false;
                } else {
                    parseError("Missing operator");
                    return false;
                }
            }
        }
        //noinspection ConstantConditions
        if (wasOperator || wasNegate) {
            parseError("Ended with an operator");
            return false;
        } else {
            return true;
        }
    }

    public static boolean isComplete(Equation equation) {
        return isComplete(equation.getLeftSide());
    }

    public static boolean isCorrect(Equation equation) {
        double left = solve(equation.getLeftSide());
        double right = equation.getRightSide().getValue();
        return left == right && !Double.isNaN(left);
    }
}
