package com.github.zachdeibert.operationmanipulation.controller;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.github.zachdeibert.operationmanipulation.model.BinaryOperator;
import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.ExpressionItem;
import com.github.zachdeibert.operationmanipulation.model.GroupingOperator;
import com.github.zachdeibert.operationmanipulation.model.Level;
import com.github.zachdeibert.operationmanipulation.model.Operand;
import com.github.zachdeibert.operationmanipulation.model.OperatorType;
import com.github.zachdeibert.operationmanipulation.model.Operators;
import com.github.zachdeibert.operationmanipulation.model.UnaryOperator;
import com.github.zachdeibert.operationmanipulation.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class EquationGenerator extends Thread {
    private static final int QUEUE_SIZE = 3;
    @NonNull
    private final Random random;
    private int operands;
    private OperatorType[] operators;
    private Level level;
    @NonNull
    private final Queue<Equation> generatedEquations;
    private volatile boolean runThread;

    private boolean solve(@NonNull Equation eq, @NonNull BinaryOperator[] binaryOperators, @NonNull UnaryOperator[] unaryOperators, @NonNull boolean[] negate) {
        if (eq.getOperandCount() == negate.length) {
            int numUnary = 0;
            for (UnaryOperator op : unaryOperators) {
                if (op != null) {
                    ++numUnary;
                }
            }
            SparseArray<List<GroupingOperator>> operatorTypes = new SparseArray<>();
            for (OperatorType op : getOperators()) {
                if (op.getOperator() instanceof GroupingOperator) {
                    int type = ((GroupingOperator) op.getOperator()).getType();
                    List<GroupingOperator> list = operatorTypes.get(type);
                    if (list == null) {
                        list = new ArrayList<>();
                        operatorTypes.put(type, list);
                    }
                    list.add((GroupingOperator) op.getOperator());
                }
            }
            if (operatorTypes.size() == 0) {
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
                    Log.i("EquationGenerator", eq.toString());
                    return true;
                }
            }
            for (int i = 0; i < operatorTypes.size(); ++i) {
                int type = operatorTypes.keyAt(i);
                List<GroupingOperator> list = operatorTypes.get(type);
                for (int start = 0; start < 2 * eq.getOperandCount() + numUnary - 1; ++start) {
                    for (int end = start + type == 0 ? 3 : 2; end < 2 * eq.getOperandCount() + numUnary; ++end) {
                        for (GroupingOperator startOp : list) {
                            if (startOp.getLevel() >= 0) {
                                for (GroupingOperator endOp : list) {
                                    if (endOp.getLevel() <= 0) {
                                        eq.clear();
                                        int j = 0;
                                        for (ExpressionItem item : eq.getLeftSide()) {
                                            if (item instanceof Operand) {
                                                ExpressionItem op = item;
                                                if (unaryOperators[j] != null) {
                                                    unaryOperators[j] = (UnaryOperator) unaryOperators[j].clone();
                                                    eq.insertOperatorAfter(op, unaryOperators[j]);
                                                    op = unaryOperators[j];
                                                }
                                                eq.insertOperatorAfter(op, binaryOperators[j++]);
                                                if (j >= binaryOperators.length) {
                                                    break;
                                                }
                                            }
                                        }
                                        eq.insertOperatorAt(start, startOp);
                                        eq.insertOperatorAt(end, endOp);
                                        j = 0;
                                        for (ExpressionItem item : eq.getLeftSide()) {
                                            if (item instanceof Operand) {
                                                if (negate[j++]) {
                                                    eq.insertOperatorBefore(item, Operators.SUBTRACTION);
                                                }
                                            }
                                        }
                                        if (EquationSolver.isComplete(eq) && EquationSolver.isCorrect(eq)) {
                                            Log.i("EquationGenerator", eq.toString());
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        } else {
            boolean[] subs = new boolean[negate.length + 1];
            System.arraycopy(negate, 0, subs, 0, negate.length);
            subs[negate.length] = false;
            if (solve(eq, binaryOperators, unaryOperators, subs)) {
                return true;
            }
            subs[negate.length] = true;
            return solve(eq, binaryOperators, unaryOperators, subs);
        }
    }

    private boolean solve(@NonNull Equation eq, @NonNull BinaryOperator[] binaryOperators, @NonNull UnaryOperator[] unaryOperators) {
        if (eq.getOperandCount() == binaryOperators.length + 1) {
            return solve(eq, binaryOperators, unaryOperators, new boolean[0]);
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

    private boolean solve(@NonNull Equation eq, @NonNull UnaryOperator[] unaryOperators) {
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

    private boolean solve(@NonNull Equation eq) {
        try {
            return solve(eq, new UnaryOperator[0]);
        } finally {
            eq.clear();
        }
    }

    @NonNull
    private Equation generate() {
        Operand[] operands = new Operand[getOperands()];
        int result;
        while (true) {
            for (int i = 0; i < operands.length; ++i) {
                operands[i] = new Operand(random.nextInt(10));
            }
            result = random.nextInt(10);
            Equation eq = new Equation(getLevel(), new Operand(result), operands);
            if (solve(eq)) {
                return eq;
            }
        }
    }

    @NonNull
    public Equation getEquation() {
        Equation eq = generatedEquations.poll();
        return eq == null ? generate() : eq;
    }

    void generateCachedEquation() {
        Equation eq = null;
        try {
            if (generatedEquations.size() < QUEUE_SIZE) {
                eq = generate();
            }
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            runThread = false;
        }
        if (eq != null) {
            generatedEquations.offer(eq);
        }
    }

    @Override
    public void run() {
        runThread = true;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                runThread = false;
            }
        });
        while (runThread) {
            generateCachedEquation();
        }
    }

    private int getOperands() {
        return operands;
    }

    private OperatorType[] getOperators() {
        return operators;
    }

    private Level getLevel() {
        return level;
    }

    public void setLevel(@NonNull Level level) {
        this.level = level;
        this.operands = level.getNumberOfOperands();
        this.operators = level.getAllowedOperators();
        interrupt();
        generatedEquations.clear();
    }

    public EquationGenerator() {
        random = new Random();
        generatedEquations = new ArrayBlockingQueue<>(QUEUE_SIZE);
        setLevel(Level.TwoAddition);
    }
}
