package com.github.zachdeibert.operationmanipulation.view.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.github.zachdeibert.operationmanipulation.*;
import com.github.zachdeibert.operationmanipulation.model.GameSession;
import com.github.zachdeibert.operationmanipulation.model.GameSettings;

public class SettingsActivity extends Activity implements View.OnTouchListener, CompoundButton.OnCheckedChangeListener {
    private CheckBox saveProgress;
    private Button resetProgress;
    private GameSettings settings;

    @Override
    protected void onPause() {
        super.onPause();
        settings.save(getSharedPreferences("SavedState", MODE_PRIVATE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        saveProgress = (CheckBox) findViewById(R.id.save_progress);
        resetProgress = (Button) findViewById(R.id.reset_progress);
        settings = GameSettings.load(getSharedPreferences("SavedState", MODE_PRIVATE));
        saveProgress.setChecked(settings.isSavingProgress());
        saveProgress.setOnCheckedChangeListener(this);
        resetProgress.setOnTouchListener(this);
        onCheckedChanged(saveProgress, settings.isSavingProgress());
    }

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        if (v == resetProgress) {
            GameSession.reset(getSharedPreferences("SavedState", MODE_PRIVATE));
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == saveProgress) {
            settings.setSaveProgress(isChecked);
            resetProgress.setEnabled(isChecked);
            if (!isChecked) {
                onTouch(resetProgress, null);
            }
        }
    }
}
