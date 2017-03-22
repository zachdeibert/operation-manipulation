package com.github.zachdeibert.operationmanipulation.view.activities;

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
    private EquationListLayout equationList;
    private EquationGenerator generator;
    private OperationListView operationList;
    private GameSession session;
    private TextView scoreLabel;
    private ScrollView equationContainer;
    private int solvedCorrectly;
    private int solvedIncorrectly;
    private boolean loading;
    private AddEquationView addEquationView;
    private InterstitialAd equationAd;

    public EquationGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(EquationGenerator generator) {
        this.generator = generator;
    }

    public void arrangeAddEquationView() {
        equationList.removeView(addEquationView);
        equationList.addView(addEquationView);
    }

    public void addEquation() {
        Equation equation = getGenerator().getEquation();
        EquationContainer view = new EquationContainer(this);
        view.setEquation(equation);
        equationList.addView(view);
        arrangeAddEquationView();
    }

    public void loadLevel(Level level) {
        generator.setLevel(level);
        operationList.loadLevel(level);
    }

    private AdRequest getAdRequest() {
        AdRequest.Builder ad = new AdRequest.Builder();
        if (BuildConfig.DEBUG) {
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID).getBytes());
                StringBuffer str = new StringBuffer();
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
            scoreLabel.setText(String.format("Score: %d", session.getScore()));
            ++solvedCorrectly;
            if (session.getScore() >= session.getLevel().getMinimumAdvancePoints() && ((float) solvedCorrectly) / (float) (solvedCorrectly + solvedIncorrectly) >= session.getLevel().getMinimumAdvanceAccuracy()) {
                session.setLevel(Level.values()[session.getLevel().ordinal() + 1]);
                loadLevel(session.getLevel());
                solvedCorrectly = 0;
                solvedIncorrectly = 0;
                List<EquationContainer> unsolved = new ArrayList<>();
                for (int i = 0; i < equationList.getChildCount(); ++i) {
                    View view = equationList.getChildAt(i);
                    if (view instanceof EquationContainer) {
                        EquationContainer equation = (EquationContainer) equationList.getChildAt(i);
                        if (!equation.isSolved()) {
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
