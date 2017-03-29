package com.github.zachdeibert.operationmanipulation.model;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;

import com.github.zachdeibert.operationmanipulation.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameSession implements Parcelable, Serializable {
    public static final Creator<GameSession> CREATOR = new Creator<GameSession>() {
        @Override
        public GameSession createFromParcel(Parcel in) {
            return new GameSession(in);
        }

        @Override
        public GameSession[] newArray(int size) {
            return new GameSession[size];
        }
    };

    private Level level;
    private int score;
    private final List<Equation> equations;
    private int solvedCorrectly;
    private int solvedIncorrectly;
    private Serializable serializedEquationContainer;
    private transient GameSettings settings;

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
        resetSolvedCounters();
    }

    public int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score = score;
    }

    public void addScore(int diff) {
        setScore(getScore() + diff);
    }

    private List<Equation> getEquations() {
        return equations;
    }

    public void addEquation(Equation equation) {
        getEquations().add(equation);
    }

    private int getSolvedCorrectly() {
        return solvedCorrectly;
    }

    private void setSolvedCorrectly(int solvedCorrectly) {
        this.solvedCorrectly = solvedCorrectly;
    }

    public void onSolvedCorrectly() {
        setSolvedCorrectly(getSolvedCorrectly() + 1);
    }

    private int getSolvedIncorrectly() {
        return solvedIncorrectly;
    }

    private void setSolvedIncorrectly(int solvedIncorrectly) {
        this.solvedIncorrectly = solvedIncorrectly;
    }

    public void onSolvedIncorrectly() {
        setSolvedIncorrectly(getSolvedIncorrectly() + 1);
    }

    private int getTotalSolved() {
        return getSolvedCorrectly() + getSolvedIncorrectly();
    }

    private void resetSolvedCounters() {
        setSolvedCorrectly(0);
        setSolvedIncorrectly(0);
    }

    public GameSettings getSettings() {
        return settings;
    }

    private void setSettings(GameSettings settings) {
        this.settings = settings;
    }

    public boolean canAdvance() {
        return !getSettings().isNotAdvancing() && getTotalSolved() >= 10 && ((float) getSolvedCorrectly()) / (float) getTotalSolved() >= getLevel().getMinimumAdvanceAccuracy();
    }

    public Serializable getSerializedEquationContainer() {
        return serializedEquationContainer;
    }

    public void setSerializedEquationContainer(Serializable serializedEquationContainer) {
        this.serializedEquationContainer = serializedEquationContainer;
    }

    public static void reset(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("GameSession");
        editor.apply();
    }

    public void save(SharedPreferences prefs) {
        getSettings().save(prefs);
        if (getSettings().isSavingProgress()) {
            byte[] data;
            ByteArrayOutputStream buffer = null;
            ObjectOutputStream stream = null;
            try {
                buffer = new ByteArrayOutputStream();
                stream = new ObjectOutputStream(buffer);
                stream.writeObject(this);
                stream.flush();
                data = buffer.toByteArray();
            } catch (IOException ex) {
                Log.w("GameSession", "Unable to save game", ex);
                return;
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException ex) {
                        Log.w("GameSession", "Error closing stream", ex);
                    }
                }
                if (buffer != null) {
                    try {
                        buffer.close();
                    } catch (IOException ex) {
                        Log.w("GameSession", "Error closing stream", ex);
                    }
                }
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("GameSession", Base64.encodeToString(data, 0));
            editor.apply();
            Log.d("GameSettings", "Saved game");
        } else {
            reset(prefs);
        }
    }

    public static GameSession load(SharedPreferences prefs) {
        String str = prefs.getString("GameSession", null);
        if (str == null) {
            Log.d("GameSession", "No game found");
            GameSession blank = new GameSession();
            blank.setSettings(GameSettings.load(prefs));
            return blank;
        } else {
            ByteArrayInputStream buffer = null;
            ObjectInputStream stream = null;
            try {
                buffer = new ByteArrayInputStream(Base64.decode(str, 0));
                stream = new ObjectInputStream(buffer);
                Object obj = stream.readObject();
                if (obj instanceof GameSession) {
                    GameSession session = (GameSession) obj;
                    session.setSettings(GameSettings.load(prefs));
                    return session;
                } else {
                    Log.w("GameSession", "Invalid serialized type");
                    GameSession blank = new GameSession();
                    blank.setSettings(GameSettings.load(prefs));
                    return blank;
                }
            } catch (Exception ex) {
                Log.w("GameSession", "Unable to load game", ex);
                GameSession blank = new GameSession();
                blank.setSettings(GameSettings.load(prefs));
                return blank;
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException ex) {
                        Log.w("GameSession", "Error closing stream", ex);
                    }
                }
                if (buffer != null) {
                    try {
                        buffer.close();
                    } catch (IOException ex) {
                        Log.w("GameSession", "Error closing stream", ex);
                    }
                }
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getLevel().ordinal());
        dest.writeInt(getScore());
        dest.writeList(getEquations());
        dest.writeInt(getSolvedCorrectly());
        dest.writeInt(getSolvedIncorrectly());
        getSettings().writeToParcel(dest, flags);
    }

    private GameSession() {
        setLevel(Level.TwoAddition);
        setScore(0);
        equations = new ArrayList<>();
        settings = new GameSettings();
    }

    private GameSession(Parcel parcel) {
        setLevel(Level.values()[parcel.readInt()]);
        setScore(parcel.readInt());
        equations = CollectionUtils.checkedAssignment(parcel.readArrayList(ClassLoader.getSystemClassLoader()), Equation.class);
        setSolvedCorrectly(parcel.readInt());
        setSolvedIncorrectly(parcel.readInt());
        setSettings(GameSettings.CREATOR.createFromParcel(parcel));
    }
}
