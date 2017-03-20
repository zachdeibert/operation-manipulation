package com.github.zachdeibert.operationmanipulation.view.activities;

import android.os.Bundle;
import android.app.Activity;

import com.github.zachdeibert.operationmanipulation.*;
import com.github.zachdeibert.operationmanipulation.controller.EquationGenerator;
import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.Level;
import com.github.zachdeibert.operationmanipulation.view.views.EquationContainer;
import com.github.zachdeibert.operationmanipulation.view.views.EquationListLayout;
import com.github.zachdeibert.operationmanipulation.view.views.OperationListView;

public class GameActivity extends Activity {
    private EquationListLayout equationList;
    private EquationGenerator generator;
    private OperationListView operationList;

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
        for (int i = 0; i < level.getMaximumUnsolvedPuzzles(); ++i) {
            addEquation();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (equationList.getChildCount() == 0) {
            loadLevel(Level.Level1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        equationList = (EquationListLayout) findViewById(R.id.equationList);
        operationList = (OperationListView) findViewById(R.id.operationList);
        setGenerator(new EquationGenerator());
        if (savedInstanceState == null) {
            loadLevel(Level.Level1);
        }
    }
}
