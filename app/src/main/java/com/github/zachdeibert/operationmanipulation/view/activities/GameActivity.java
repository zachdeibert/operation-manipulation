package com.github.zachdeibert.operationmanipulation.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.zachdeibert.operationmanipulation.*;
import com.github.zachdeibert.operationmanipulation.controller.EquationGenerator;
import com.github.zachdeibert.operationmanipulation.model.Equation;
import com.github.zachdeibert.operationmanipulation.model.GameSession;
import com.github.zachdeibert.operationmanipulation.model.Level;
import com.github.zachdeibert.operationmanipulation.view.views.AddEquationView;
import com.github.zachdeibert.operationmanipulation.view.views.EquationContainer;
import com.github.zachdeibert.operationmanipulation.view.views.EquationListLayout;
import com.github.zachdeibert.operationmanipulation.view.views.LevelUpView;
import com.github.zachdeibert.operationmanipulation.view.views.OperationListView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends Activity {
    private static final int LEVEL_UP_REQUEST = 42;
    private EquationListLayout equationList;
    private EquationGenerator generator;
    private OperationListView operationList;
    private GameSession session;
    private TextView scoreLabel;
    private ScrollView equationContainer;
    private boolean loading;
    private boolean hideSystemUi;
    private AddEquationView addEquationView;
    private InterstitialAd equationAd;

    private EquationGenerator getGenerator() {
        return generator;
    }

    private void setGenerator(EquationGenerator generator) {
        this.generator = generator;
    }

    public void arrangeAddEquationView() {
        equationList.removeView(addEquationView);
        equationList.addView(addEquationView);
    }

    private void addEquation() {
        Equation equation = getGenerator().getEquation();
        EquationContainer view = new EquationContainer(this);
        view.setEquation(equation);
        equationList.addView(view);
        arrangeAddEquationView();
        session.addEquation(equation);
    }

    private boolean isHidingSystemUi() {
        return hideSystemUi;
    }

    public void setHideSystemUi(boolean hideSystemUi) {
        this.hideSystemUi = hideSystemUi;
        onWindowFocusChanged(true);
    }

    private void loadLevel(Level level) {
        generator.setLevel(level);
        operationList.loadLevel(level);
    }

    @SuppressLint("HardwareIds")
    private AdRequest getAdRequest() {
        AdRequest.Builder ad = new AdRequest.Builder();
        if (BuildConfig.DEBUG) {
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID).getBytes());
                StringBuilder str = new StringBuilder();
                for (byte b : digest.digest()) {
                    str.append(String.format("%02X", b));
                }
                ad.addTestDevice(str.toString());
            } catch (NoSuchAlgorithmException ex) {
                Log.w("GameActivity", "Unable to find device ID", ex);
            }
        }
        return ad.build();
    }

    private void requestEquationAd() {
        equationAd.loadAd(getAdRequest());
    }

    public void onRequestEquation() {
        if (equationAd.isLoaded()) {
            equationAd.show();
        } else {
            Toast.makeText(this, "Unable to show ad", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSolvedEquation() {
        if (!loading) {
            session.addScore(session.getLevel().getEquationSolvingScore());
            scoreLabel.setText(getResources().getString(R.string.score_format, session.getScore()));
            session.onSolvedCorrectly();
            if (session.canAdvance()) {
                Intent intent = new Intent(this, LevelUpActivity.class);
                intent.putExtra("PassedLevel", session.getLevel().ordinal());
                startActivityForResult(intent, LEVEL_UP_REQUEST);
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
            session.onSolvedIncorrectly();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        session.setSerializedEquationContainer(((EquationListLayout.SavedState) equationList.onSaveInstanceState()).toSerializable());
        session.save(getSharedPreferences("SavedState", MODE_PRIVATE));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LEVEL_UP_REQUEST) {
            if (data == null) {
                session.getSettings().setNoAdvancing(true);
            } else {
                session.setLevel(Level.values()[data.getIntExtra("SelectedLevel", -1)]);
                loadLevel(session.getLevel());
                List<EquationContainer> unsolved = new ArrayList<>();
                for (int i = 0; i < equationList.getChildCount(); ++i) {
                    View view = equationList.getChildAt(i);
                    if (view instanceof EquationContainer) {
                        EquationContainer equation = (EquationContainer) equationList.getChildAt(i);
                        if (equation.isUnsolved()) {
                            unsolved.add(equation);
                        }
                    }
                }
                for (EquationContainer equation : unsolved) {
                    equationList.removeView(equation);
                }
                equationList.addView(new LevelUpView(this));
                for (int i = 0; i < session.getLevel().getMaximumUnsolvedPuzzles(); ++i) {
                    addEquation();
                }
                arrangeAddEquationView();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("GameSession", session);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        loading = true;
        if (savedInstanceState == null) {
            session = GameSession.load(getSharedPreferences("SavedState", MODE_PRIVATE));
            loadLevel(session.getLevel());
            if (session.getSerializedEquationContainer() == null) {
                for (int i = 0; i < session.getLevel().getMaximumUnsolvedPuzzles(); ++i) {
                    addEquation();
                }
            } else {
                equationList.onRestoreInstanceState(((EquationListLayout.SavedState.SerializableState) session.getSerializedEquationContainer()).toState());
            }
        } else if (session == null) {
            session = savedInstanceState.getParcelable("GameSession");
            super.onRestoreInstanceState(savedInstanceState);
            loadLevel(session.getLevel());
        }
        for (int i = 0; i < equationList.getChildCount(); ++i) {
            final View view = equationList.getChildAt(i);
            if (view instanceof EquationContainer && ((EquationContainer) view).isUnsolved()) {
                equationContainer.post(new Runnable() {
                    @Override
                    public void run() {
                        equationContainer.smoothScrollTo(0, (int) view.getY());
                    }
                });
                break;
            }
        }
        scoreLabel.setText(getResources().getString(R.string.score_format, session.getScore()));
        loading = false;
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
                | (isHidingSystemUi() ? View.SYSTEM_UI_FLAG_IMMERSIVE : View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                onWindowFocusChanged(true);
            }
        });
        setContentView(R.layout.activity_game);
        equationList = (EquationListLayout) findViewById(R.id.equationList);
        operationList = (OperationListView) findViewById(R.id.operationList);
        scoreLabel = (TextView) findViewById(R.id.scoreView);
        equationContainer = (ScrollView) findViewById(R.id.equationContainer);
        addEquationView = new AddEquationView(this);
        equationAd = new InterstitialAd(this);
        equationList.addView(addEquationView);
        setGenerator(new EquationGenerator());
        getGenerator().start();
        onRestoreInstanceState(savedInstanceState);
        final AdView adView = (AdView) findViewById(R.id.adView);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                ((RelativeLayout.LayoutParams) scoreLabel.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_TOP);
                scoreLabel.requestLayout();
            }

            @Override
            public void onAdLoaded() {
                ((RelativeLayout.LayoutParams) scoreLabel.getLayoutParams()).removeRule(RelativeLayout.ALIGN_PARENT_TOP);
                scoreLabel.requestLayout();
            }
        });
        adView.loadAd(getAdRequest());
        equationAd.setAdUnitId("ca-app-pub-9531291675030995/4108050463");
        equationAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestEquationAd();
                addEquation();
            }
        });
        requestEquationAd();
    }
}
