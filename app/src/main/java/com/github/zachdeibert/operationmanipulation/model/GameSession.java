package com.github.zachdeibert.operationmanipulation.model;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;

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
    private GameSettings settings;

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

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int diff) {
        setScore(getScore() + diff);
    }

    public List<Equation> getEquations() {
        return equations;
    }

    public void addEquation(Equation equation) {
        getEquations().add(equation);
    }

    public int getSolvedCorrectly() {
        return solvedCorrectly;
    }

    public void setSolvedCorrectly(int solvedCorrectly) {
        this.solvedCorrectly = solvedCorrectly;
    }

    public void onSolvedCorrectly() {
        setSolvedCorrectly(getSolvedCorrectly() + 1);
    }

    public int getSolvedIncorrectly() {
        return solvedIncorrectly;
    }

    public void setSolvedIncorrectly(int solvedIncorrectly) {
        this.solvedIncorrectly = solvedIncorrectly;
    }

    public void onSolvedIncorrectly() {
        setSolvedIncorrectly(getSolvedIncorrectly() + 1);
    }

    public int getTotalSolved() {
        return getSolvedCorrectly() + getSolvedIncorrectly();
    }

    public void resetSolvedCounters() {
        setSolvedCorrectly(0);
        setSolvedIncorrectly(0);
    }

    public GameSettings getSettings() {
        return settings;
    }

    public void setSettings(GameSettings settings) {
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
        } else {
            reset(prefs);
        }
    }

    public static GameSession load(SharedPreferences prefs) {
        String str = prefs.getString("GameSession", null);
        GameSession blank = new GameSession();
        blank.setSettings(GameSettings.load(prefs));
        if (str == null) {
            return blank;
        } else {
            ByteArrayInputStream buffer = null;
            ObjectInputStream stream = null;
            try {
                buffer = new ByteArrayInputStream(Base64.decode(str, 0));
                stream = new ObjectInputStream(buffer);
                Object obj = stream.readObject();
                if (obj instanceof GameSession) {
                    return (GameSession) obj;
                } else {
                    Log.w("GameSession", "Invalid serialized type");
                    return blank;
                }
            } catch (Exception ex) {
                Log.w("GameSession", "Unable to load game", ex);
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

    public GameSession() {
        setLevel(Level.TwoAddition);
        setScore(0);
        equations = new ArrayList<>();
        settings = new GameSettings();
    }

    private GameSession(Parcel parcel) {
        setLevel(Level.values()[parcel.readInt()]);
        setScore(parcel.readInt());
        equations = parcel.readArrayList(ClassLoader.getSystemClassLoader());
        setSolvedCorrectly(parcel.readInt());
        setSolvedIncorrectly(parcel.readInt());
        setSettings(GameSettings.CREATOR.createFromParcel(parcel));
    }
}
