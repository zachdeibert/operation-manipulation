package com.github.zachdeibert.operationmanipulation.controller;

import android.util.Log;

import com.github.zachdeibert.operationmanipulation.model.BinaryOperator;
import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.ExpressionItem;
import com.github.zachdeibert.operationmanipulation.model.Operand;
import com.github.zachdeibert.operationmanipulation.model.OperatorType;

import java.util.Random;

public class EquationGenerator {
    private final Random random;
    private int operands;
    private OperatorType[] operators;

    private boolean solve(Equation eq, BinaryOperator[] ops) {
        if (eq.getOperandCount() == ops.length + 1) {
            eq.clear();
            int i = 0;
            for (ExpressionItem item : eq.getLeftSide()) {
                if (item instanceof Operand) {
                    eq.insertOperatorAfter(item, ops[i++]);
                    if (i >= ops.length) {
                        break;
                    }
                }
            }
            if (EquationSolver.isCorrect(eq)) {
                Log.d("EquationGenerator", eq.toString());
                return true;
            } else {
                return false;
            }
        } else {
            BinaryOperator[] subs = new BinaryOperator[ops.length + 1];
            System.arraycopy(ops, 0, subs, 0, ops.length);
            for (OperatorType op : getOperators()) {
                subs[ops.length] = (BinaryOperator) op.getOperator();
                if (solve(eq, subs)) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean solve(Equation eq) {
        try {
            return solve(eq, new BinaryOperator[0]);
        } finally {
            eq.clear();
        }
    }

    public Equation generate() {
        Operand[] operands = new Operand[getOperands()];
        int result;
        while (true) {
            for (int i = 0; i < operands.length; ++i) {
                operands[i] = new Operand(random.nextInt(10));
            }
            result = random.nextInt(10);
            Equation eq = new Equation(new Operand(result), operands);
            if (solve(eq)) {
                return eq;
            }
        }
    }

    public int getOperands() {
        return operands;
    }

    public void setOperands(int operands) {
        if (operands < 2) {
            throw new IllegalArgumentException("There must be at least two operands to the equation");
        }
        this.operands = operands;
    }

    public OperatorType[] getOperators() {
        return operators;
    }

    public void setOperators(OperatorType... operators) {
        this.operators = operators;
    }

    public EquationGenerator() {
        random = new Random();
        setOperands(3);
        setOperators(OperatorType.values());
    }
}
