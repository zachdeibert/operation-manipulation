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
    private CheckBox noAdvancing;
    private GameSettings settings;

    private void save() {
        settings.save(getSharedPreferences("SavedState", MODE_PRIVATE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        saveProgress = (CheckBox) findViewById(R.id.save_progress);
        resetProgress = (Button) findViewById(R.id.reset_progress);
        noAdvancing = (CheckBox) findViewById(R.id.no_advancing);
        settings = GameSettings.load(getSharedPreferences("SavedState", MODE_PRIVATE));
        saveProgress.setChecked(settings.isSavingProgress());
        noAdvancing.setChecked(settings.isNotAdvancing());
        saveProgress.setOnCheckedChangeListener(this);
        resetProgress.setOnTouchListener(this);
        noAdvancing.setOnCheckedChangeListener(this);
        onCheckedChanged(saveProgress, settings.isSavingProgress());
    }

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        if (v == resetProgress) {
            GameSession.reset(getSharedPreferences("SavedState", MODE_PRIVATE));
        } else {
            return false;
        }
        save();
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
        } else if (buttonView == noAdvancing) {
            settings.setNoAdvancing(isChecked);
        } else {
            return;
        }
        save();
    }
}
