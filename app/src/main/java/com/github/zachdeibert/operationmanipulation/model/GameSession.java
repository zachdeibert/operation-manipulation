package com.github.zachdeibert.operationmanipulation.model;

import android.os.Parcel;
import android.os.Parcelable;

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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getLevel().ordinal());
        dest.writeInt(getScore());
    }

    public GameSession() {
        setLevel(Level.TwoAddition);
        setScore(0);
    }

    private GameSession(Parcel parcel) {
        setLevel(Level.values()[parcel.readInt()]);
        setScore(parcel.readInt());
    }
}
