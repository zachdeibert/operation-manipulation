package com.github.zachdeibert.operationmanipulation.view.activities;

import android.content.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;

import com.github.zachdeibert.operationmanipulation.*;

public class MainMenuActivity extends AppCompatActivity {
    public void playGame(@SuppressWarnings("UnusedParameters") View v) {
        startActivity(new Intent(this, GameActivity.class));
    }

    public void showSettings(@SuppressWarnings("UnusedParameters") View v) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }
}
