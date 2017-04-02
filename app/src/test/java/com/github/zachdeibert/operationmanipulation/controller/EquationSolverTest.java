package com.github.zachdeibert.operationmanipulation.controller;

import com.github.zachdeibert.operationmanipulation.AbstractUnitTest;
import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.ExpressionItem;
import com.github.zachdeibert.operationmanipulation.model.Operand;

import junit.framework.Assert;

import org.apache.commons.math3.special.Gamma;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static com.github.zachdeibert.operationmanipulation.model.Operators.ABSOLUTE_VALUE;
import static com.github.zachdeibert.operationmanipulation.model.Operators.ADDITION;
import static com.github.zachdeibert.operationmanipulation.model.Operators.DIVISION;
import static com.github.zachdeibert.operationmanipulation.model.Operators.EXPONENT;
import static com.github.zachdeibert.operationmanipulation.model.Operators.FACTORIAL;
import static com.github.zachdeibert.operationmanipulation.model.Operators.LEFT_CEILING;
import static com.github.zachdeibert.operationmanipulation.model.Operators.LEFT_FLOOR;
import static com.github.zachdeibert.operationmanipulation.model.Operators.LEFT_PARENTHESIS;
import static com.github.zachdeibert.operationmanipulation.model.Operators.MULTIPLICATION;
import static com.github.zachdeibert.operationmanipulation.model.Operators.RIGHT_CEILING;
import static com.github.zachdeibert.operationmanipulation.model.Operators.RIGHT_FLOOR;
import static com.github.zachdeibert.operationmanipulation.model.Operators.RIGHT_PARENTHESIS;
import static com.github.zachdeibert.operationmanipulation.model.Operators.SUBTRACTION;

public class EquationSolverTest extends AbstractUnitTest {
    private static final Operand OPERAND = new Operand(0);
    private static final ExpressionItem[][] IS_COMPLETE_TESTS = new ExpressionItem[][] {
            new ExpressionItem[] { OPERAND, ADDITION, OPERAND },
            new ExpressionItem[] { OPERAND, FACTORIAL },
            new ExpressionItem[] { OPERAND, FACTORIAL, SUBTRACTION, OPERAND },
            new ExpressionItem[] { SUBTRACTION, OPERAND },
            new ExpressionItem[] { LEFT_PARENTHESIS, OPERAND, FACTORIAL, ADDITION, OPERAND, RIGHT_PARENTHESIS, OPERAND },
            new ExpressionItem[] { SUBTRACTION, LEFT_PARENTHESIS, OPERAND, FACTORIAL, ADDITION, OPERAND, RIGHT_PARENTHESIS, FACTORIAL, EXPONENT, OPERAND },
            new ExpressionItem[] { LEFT_FLOOR, OPERAND, RIGHT_CEILING },
            new ExpressionItem[] { ABSOLUTE_VALUE, OPERAND, ABSOLUTE_VALUE }
    };
    private static final ExpressionItem[][] ACTUALLY_COMPLETE = new ExpressionItem[][] {
            new ExpressionItem[] { OPERAND },
            new ExpressionItem[] { OPERAND, SUBTRACTION, OPERAND },
            new ExpressionItem[] { LEFT_PARENTHESIS, OPERAND, ADDITION, OPERAND, RIGHT_PARENTHESIS, OPERAND },
            new ExpressionItem[] { LEFT_PARENTHESIS, OPERAND, FACTORIAL, ADDITION, OPERAND, RIGHT_PARENTHESIS },
            new ExpressionItem[] { LEFT_PARENTHESIS, OPERAND, FACTORIAL, ADDITION, OPERAND, RIGHT_PARENTHESIS, FACTORIAL, EXPONENT, OPERAND },
            new ExpressionItem[] { SUBTRACTION, LEFT_PARENTHESIS, OPERAND, ADDITION, OPERAND, RIGHT_PARENTHESIS, FACTORIAL, EXPONENT, OPERAND },
            new ExpressionItem[] { SUBTRACTION, LEFT_PARENTHESIS, OPERAND, FACTORIAL, ADDITION, OPERAND, RIGHT_PARENTHESIS, EXPONENT, OPERAND }
    };
    private static final ExpressionItem[][] SOLVE_TESTS = new ExpressionItem[][] {
            new ExpressionItem[] { new Operand(17), ADDITION, new Operand(34) },
            new ExpressionItem[] { new Operand(17), SUBTRACTION, new Operand(34) },
            new ExpressionItem[] { new Operand(17), MULTIPLICATION, new Operand(34) },
            new ExpressionItem[] { new Operand(17), DIVISION, new Operand(34) },
            new ExpressionItem[] { new Operand(17), EXPONENT, new Operand(34) },
            new ExpressionItem[] { new Operand(17), FACTORIAL },

            new ExpressionItem[] { LEFT_PARENTHESIS, new Operand(3), DIVISION, new Operand(4), RIGHT_PARENTHESIS },
            new ExpressionItem[] { LEFT_CEILING, new Operand(3), DIVISION, new Operand(4), RIGHT_CEILING },
            new ExpressionItem[] { LEFT_FLOOR, new Operand(3), DIVISION, new Operand(4), RIGHT_FLOOR },
            new ExpressionItem[] { ABSOLUTE_VALUE, new Operand(3), SUBTRACTION, new Operand(4), ABSOLUTE_VALUE },
            new ExpressionItem[] { LEFT_CEILING, new Operand(3), DIVISION, new Operand(4), RIGHT_FLOOR },
            new ExpressionItem[] { LEFT_FLOOR, new Operand(3), DIVISION, new Operand(4), RIGHT_CEILING },
            new ExpressionItem[] { LEFT_FLOOR, new Operand(5), DIVISION, new Operand(4), RIGHT_CEILING },

            new ExpressionItem[] { new Operand(2), ADDITION, new Operand(2), MULTIPLICATION, new Operand(11) },
            new ExpressionItem[] { new Operand(2), LEFT_PARENTHESIS, new Operand(2), ADDITION, new Operand(11), RIGHT_PARENTHESIS },
            new ExpressionItem[] { new Operand(3), FACTORIAL, EXPONENT, new Operand(2) }
    };
    private static final Double[] SOLVE_ANSWERS = new Double[] {
            51., -17., 578., 0.5, Math.pow(17, 34), Gamma.gamma(18),
            0.75, 1., 0., 1., 1., 1., 1.,
            24., 26., 36.
    };

    @Test
    public void solve() {
        Assert.assertEquals("Testing arrays are bad", SOLVE_TESTS.length, SOLVE_ANSWERS.length);
        for (int i = 0; i < SOLVE_TESTS.length; ++i) {
            Assert.assertEquals(String.format(Locale.US, "%s = %f", Arrays.deepToString(SOLVE_TESTS[i]), SOLVE_ANSWERS[i]), SOLVE_ANSWERS[i], EquationSolver.solve(SOLVE_TESTS[i]));
        }
    }

    @Test
    public void isComplete() {
        Set<Integer> actuallyComplete = new HashSet<>();
        for (ExpressionItem[] expr : ACTUALLY_COMPLETE) {
            actuallyComplete.add(Arrays.deepHashCode(expr));
        }
        for (ExpressionItem[] positive : IS_COMPLETE_TESTS) {
            Equation eq = new Equation(OPERAND, positive);
            Assert.assertTrue(eq.toString(), EquationSolver.isComplete(eq));
            ExpressionItem[] negative = new ExpressionItem[positive.length - 1];
            for (int i = 0; i < positive.length; ++i) {
                System.arraycopy(positive, 0, negative, 0, i);
                System.arraycopy(positive, i + 1, negative, i, negative.length - i);
                if (!actuallyComplete.contains(Arrays.deepHashCode(negative))) {
                    eq = new Equation(OPERAND, negative);
                    Assert.assertFalse(eq.toString(), EquationSolver.isComplete(eq));
                }
            }
        }
    }

    @Test
    public void isCorrect() {
        Equation eq = new Equation(new Operand(4), new Operand(2), ADDITION, new Operand(2));
        Assert.assertTrue(eq.toString(), EquationSolver.isCorrect(eq));
        eq = new Equation(new Operand(5), new Operand(2), ADDITION, new Operand(2));
        Assert.assertFalse(eq.toString(), EquationSolver.isCorrect(eq));
        eq = new Equation(new Operand(4), new Operand(1), DIVISION, new Operand(0));
        Assert.assertFalse(eq.toString(), EquationSolver.isCorrect(eq));
        eq = new Equation(new Operand(4), new Operand(-1), FACTORIAL);
        Assert.assertFalse(eq.toString(), EquationSolver.isCorrect(eq));
    }
}