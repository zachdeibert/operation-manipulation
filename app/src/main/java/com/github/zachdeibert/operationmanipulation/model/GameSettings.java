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

public class GameSettings implements Parcelable, Serializable {
    public static final Creator<GameSettings> CREATOR = new Creator<GameSettings>() {
        @Override
        public GameSettings createFromParcel(Parcel in) {
            return new GameSettings(in);
        }

        @Override
        public GameSettings[] newArray(int size) {
            return new GameSettings[size];
        }
    };

    private boolean noAdvancing;
    private boolean saveProgress;

    public boolean isNotAdvancing() {
        return noAdvancing;
    }

    public void setNoAdvancing(boolean noAdvancing) {
        this.noAdvancing = noAdvancing;
    }

    public boolean isSavingProgress() {
        return saveProgress;
    }

    public void setSaveProgress(boolean saveProgress) {
        this.saveProgress = saveProgress;
    }

    public void save(SharedPreferences prefs) {
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
            Log.w("GameSettings", "Unable to save game", ex);
            return;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ex) {
                    Log.w("GameSettings", "Error closing stream", ex);
                }
            }
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException ex) {
                    Log.w("GameSettings", "Error closing stream", ex);
                }
            }
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("GameSettings", Base64.encodeToString(data, 0));
        editor.apply();
    }

    public static GameSettings load(SharedPreferences prefs) {
        String str = prefs.getString("GameSettings", null);
        if (str == null) {
            return new GameSettings();
        } else {
            ByteArrayInputStream buffer = null;
            ObjectInputStream stream = null;
            try {
                buffer = new ByteArrayInputStream(Base64.decode(str, 0));
                stream = new ObjectInputStream(buffer);
                Object obj = stream.readObject();
                if (obj instanceof GameSettings) {
                    return (GameSettings) obj;
                } else {
                    Log.w("GameSettings", "Invalid serialized type");
                    return new GameSettings();
                }
            } catch (Exception ex) {
                Log.w("GameSettings", "Unable to load settings", ex);
                return new GameSettings();
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException ex) {
                        Log.w("GameSettings", "Error closing stream", ex);
                    }
                }
                if (buffer != null) {
                    try {
                        buffer.close();
                    } catch (IOException ex) {
                        Log.w("GameSettings", "Error closing stream", ex);
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
        dest.writeByte((byte) (isNotAdvancing() ? 1 : 0));
        dest.writeByte((byte) (isSavingProgress() ? 1 : 0));
    }

    private GameSettings(Parcel in) {
        setNoAdvancing(in.readByte() == 1);
        setSaveProgress(in.readByte() == 1);
    }

    public GameSettings() {
        setNoAdvancing(false);
        setSaveProgress(true);
    }
}
