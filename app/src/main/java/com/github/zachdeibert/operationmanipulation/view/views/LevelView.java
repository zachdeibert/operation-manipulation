package com.github.zachdeibert.operationmanipulation.view.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.zachdeibert.operationmanipulation.R;
import com.github.zachdeibert.operationmanipulation.model.Level;
import com.github.zachdeibert.operationmanipulation.model.OperatorType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LevelView extends RelativeLayout {
    private Level currentLevel;
    private Level nextLevel;

    private void updateView() {
        Level currentLevel = getCurrentLevel();
        Level nextLevel = getNextLevel();
        if (currentLevel != null && nextLevel != null) {
            GridView added = (GridView) findViewById(R.id.added_container);
            GridView removed = (GridView) findViewById(R.id.removed_container);
            List<OperatorType> addedOperators = new ArrayList<>();
            List<OperatorType> removedOperators = new ArrayList<>();
            Set<OperatorType> currentOperators = new HashSet<>();
            currentOperators.addAll(Arrays.asList(currentLevel.getAllowedOperators()));
            Set<OperatorType> nextOperators = new HashSet<>();
            nextOperators.addAll(Arrays.asList(nextLevel.getAllowedOperators()));
            for (OperatorType op : nextOperators) {
                if (!currentOperators.contains(op)) {
                    addedOperators.add(op);
                }
            }
            for (OperatorType op : currentOperators) {
                if (!nextOperators.contains(op)) {
                    removedOperators.add(op);
                }
            }
            added.setAdapter(new ArrayAdapter<>(getContext(), R.layout.view_operator, addedOperators));
            findViewById(R.id.added_label).setVisibility(addedOperators.isEmpty() ? INVISIBLE : VISIBLE);
            removed.setAdapter(new ArrayAdapter<>(getContext(), R.layout.view_operator, removedOperators));
            findViewById(R.id.removed_label).setVisibility(removedOperators.isEmpty() ? INVISIBLE : VISIBLE);
            ((TextView) findViewById(R.id.score_multiplier)).setText(getResources().getString(R.string.score_multiplier_format, ((double) nextLevel.getEquationSolvingScore()) / (double) currentLevel.getEquationSolvingScore()));
            ((TextView) findViewById(R.id.num_operands)).setText(getResources().getString(R.string.integer, nextLevel.getNumberOfOperands()));
            requestLayout();
        }
    }

    private Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
        updateView();
    }

    public Level getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(Level nextLevel) {
        this.nextLevel = nextLevel;
        updateView();
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_level, this, true);
    }

    public LevelView(Context context) {
        super(context);
        init();
    }

    public LevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LevelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
