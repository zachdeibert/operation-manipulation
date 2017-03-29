package com.github.zachdeibert.operationmanipulation.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.github.zachdeibert.operationmanipulation.R;
import com.github.zachdeibert.operationmanipulation.model.Level;
import com.github.zachdeibert.operationmanipulation.view.views.LevelView;

public class LevelUpActivity extends AppCompatActivity {
    public void chooseLevel(View view) {
        Intent intent = new Intent();
        intent.putExtra("SelectedLevel", ((LevelView) view.getParent()).getNextLevel().ordinal());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void stayOnLevel(@SuppressWarnings("UnusedParameters") View view) {
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_up);
        Intent intent = getIntent();
        Level level = Level.values()[intent.getIntExtra("PassedLevel", -1)];
        LinearLayout container = (LinearLayout) findViewById(R.id.level_container);
        for (Level next : level.getNextLevels()) {
            LevelView view = new LevelView(this);
            view.setCurrentLevel(level);
            view.setNextLevel(next);
            container.addView(view);
        }
    }
}
