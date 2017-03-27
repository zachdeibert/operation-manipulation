package com.github.zachdeibert.operationmanipulation.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Parcelable {
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
    private boolean noAdvancing;

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

    public boolean isNotAdvancing() {
        return noAdvancing;
    }

    public void setNoAdvancing(boolean noAdvancing) {
        this.noAdvancing = noAdvancing;
    }

    public boolean canAdvance() {
        return !isNotAdvancing() && getTotalSolved() >= 10 && ((float) getSolvedCorrectly()) / (float) getTotalSolved() >= getLevel().getMinimumAdvanceAccuracy();
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
        dest.writeByte((byte) (isNotAdvancing() ? 1 : 0));
    }

    public GameSession() {
        setLevel(Level.TwoAddition);
        setScore(0);
        equations = new ArrayList<>();
    }

    private GameSession(Parcel parcel) {
        setLevel(Level.values()[parcel.readInt()]);
        setScore(parcel.readInt());
        equations = parcel.readArrayList(ClassLoader.getSystemClassLoader());
        setSolvedCorrectly(parcel.readInt());
        setSolvedIncorrectly(parcel.readInt());
        setNoAdvancing(parcel.readByte() == 1);
    }
}
