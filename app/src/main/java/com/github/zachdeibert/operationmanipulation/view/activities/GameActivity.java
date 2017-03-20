package com.github.zachdeibert.operationmanipulation.view.activities;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.zachdeibert.operationmanipulation.*;
import com.github.zachdeibert.operationmanipulation.controller.EquationGenerator;
import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.GameSession;
import com.github.zachdeibert.operationmanipulation.model.Level;
import com.github.zachdeibert.operationmanipulation.view.views.EquationContainer;
import com.github.zachdeibert.operationmanipulation.view.views.EquationListLayout;
import com.github.zachdeibert.operationmanipulation.view.views.OperationListView;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends Activity {
    private EquationListLayout equationList;
    private EquationGenerator generator;
    private OperationListView operationList;
    private GameSession session;
    private TextView scoreLabel;
    private ScrollView equationContainer;
    private int solvedCorrectly;
    private int solvedIncorrectly;
    private boolean loading;

    public EquationGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(EquationGenerator generator) {
        this.generator = generator;
    }

    public void addEquation() {
        Equation equation = getGenerator().generate();
        EquationContainer view = new EquationContainer(this);
        view.setEquation(equation);
        equationList.addView(view);
    }

    public void loadLevel(Level level) {
        generator.setOperands(level.getNumberOfOperands());
        generator.setOperators(level.getAllowedOperators());
        operationList.loadLevel(level);
    }

    public void onSolvedEquation() {
        if (!loading) {
            session.addScore(session.getLevel().getEquationSolvingScore());
            scoreLabel.setText(String.format("Score: %d", session.getScore()));
            ++solvedCorrectly;
            if (session.getScore() >= session.getLevel().getMinimumAdvancePoints() && ((float) solvedCorrectly) / (float) (solvedCorrectly + solvedIncorrectly) >= session.getLevel().getMinimumAdvanceAccuracy()) {
                session.setLevel(Level.values()[session.getLevel().ordinal() + 1]);
                loadLevel(session.getLevel());
                solvedCorrectly = 0;
                solvedIncorrectly = 0;
                List<EquationContainer> unsolved = new ArrayList<>();
                for (int i = 0; i < equationList.getChildCount(); ++i) {
                    EquationContainer equation = (EquationContainer) equationList.getChildAt(i);
                    if (!equation.isSolved()) {
                        unsolved.add(equation);
                    }
                }
                for (EquationContainer equation : unsolved) {
                    equationList.removeView(equation);
                }
                for (int i = 0; i < session.getLevel().getMaximumUnsolvedPuzzles(); ++i) {
                    addEquation();
                }
            } else {
                addEquation();
            }
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            equationContainer.smoothScrollBy(0, metrics.heightPixels / 4);
        }
    }

    public void onFailedSolution() {
        if (!loading) {
            ++solvedIncorrectly;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("GameSession", session);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            session = new GameSession();
            loadLevel(session.getLevel());
            for (int i = 0; i < session.getLevel().getMaximumUnsolvedPuzzles(); ++i) {
                addEquation();
            }
            solvedCorrectly = 0;
            solvedIncorrectly = 0;
        } else if (session == null) {
            session = savedInstanceState.getParcelable("GameSession");
            loading = true;
            super.onRestoreInstanceState(savedInstanceState);
            loading = false;
            loadLevel(session.getLevel());
            solvedCorrectly = savedInstanceState.getInt("SolvedCorrectly");
            solvedIncorrectly = savedInstanceState.getInt("SolvedIncorrectly");
        }
        scoreLabel.setText(String.format("Score: %d", session.getScore()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        equationList = (EquationListLayout) findViewById(R.id.equationList);
        operationList = (OperationListView) findViewById(R.id.operationList);
        scoreLabel = (TextView) findViewById(R.id.scoreView);
        equationContainer = (ScrollView) findViewById(R.id.equationContainer);
        setGenerator(new EquationGenerator());
        onRestoreInstanceState(savedInstanceState);
    }
}
