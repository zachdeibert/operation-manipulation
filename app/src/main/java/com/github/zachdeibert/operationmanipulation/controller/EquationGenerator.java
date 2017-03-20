package com.github.zachdeibert.operationmanipulation.controller;

import android.util.Log;

import com.github.zachdeibert.operationmanipulation.model.BinaryOperator;
import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.ExpressionItem;
import com.github.zachdeibert.operationmanipulation.model.Operand;
import com.github.zachdeibert.operationmanipulation.model.OperatorType;
import com.github.zachdeibert.operationmanipulation.model.UnaryOperator;

import java.util.Random;

public class EquationGenerator {
    private final Random random;
    private int operands;
    private OperatorType[] operators;

    private boolean solve(Equation eq, BinaryOperator[] binaryOperators, UnaryOperator[] unaryOperators) {
        if (eq.getOperandCount() == binaryOperators.length + 1) {
            eq.clear();
            int i = 0;
            for (ExpressionItem item : eq.getLeftSide()) {
                if (item instanceof Operand) {
                    ExpressionItem op = item;
                    if (unaryOperators[i] != null) {
                        unaryOperators[i] = (UnaryOperator) unaryOperators[i].clone();
                        eq.insertOperatorAfter(op, unaryOperators[i]);
                        op = unaryOperators[i];
                    }
                    eq.insertOperatorAfter(op, binaryOperators[i++]);
                    if (i >= binaryOperators.length) {
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
            BinaryOperator[] subs = new BinaryOperator[binaryOperators.length + 1];
            System.arraycopy(binaryOperators, 0, subs, 0, binaryOperators.length);
            for (OperatorType op : getOperators()) {
                if (op.getOperator() instanceof BinaryOperator) {
                    subs[binaryOperators.length] = (BinaryOperator) op.getOperator();
                    if (solve(eq, subs, unaryOperators)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private boolean solve(Equation eq, UnaryOperator[] unaryOperators) {
        if (eq.getOperandCount() == unaryOperators.length) {
            return solve(eq, new BinaryOperator[0], unaryOperators);
        } else {
            UnaryOperator[] subs = new UnaryOperator[unaryOperators.length + 1];
            System.arraycopy(unaryOperators, 0, subs, 0, unaryOperators.length);
            subs[unaryOperators.length] = null;
            if (solve(eq, subs)) {
                return true;
            }
            for (OperatorType op : getOperators()) {
                if (op.getOperator() instanceof UnaryOperator) {
                    subs[unaryOperators.length] = (UnaryOperator) op.getOperator();
                    if (solve(eq, subs)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean solve(Equation eq) {
        try {
            return solve(eq, new UnaryOperator[0]);
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
