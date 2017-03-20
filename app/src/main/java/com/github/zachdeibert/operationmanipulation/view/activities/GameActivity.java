package com.github.zachdeibert.operationmanipulation.view.activities;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.github.zachdeibert.operationmanipulation.*;
import com.github.zachdeibert.operationmanipulation.controller.EquationGenerator;
import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.GameSession;
import com.github.zachdeibert.operationmanipulation.model.Level;
import com.github.zachdeibert.operationmanipulation.view.views.EquationContainer;
import com.github.zachdeibert.operationmanipulation.view.views.EquationListLayout;
import com.github.zachdeibert.operationmanipulation.view.views.OperationListView;

public class GameActivity extends Activity {
    private EquationListLayout equationList;
    private EquationGenerator generator;
    private OperationListView operationList;
    private GameSession session;
    private TextView scoreLabel;

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
        addEquation();
        session.addScore(session.getLevel().getEquationSolvingScore());
        scoreLabel.setText(String.format("Score: %d", session.getScore()));
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
        } else {
            super.onRestoreInstanceState(savedInstanceState);
            session = savedInstanceState.getParcelable("GameSession");
            loadLevel(session.getLevel());
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
        setGenerator(new EquationGenerator());
        onRestoreInstanceState(savedInstanceState);
    }
}
