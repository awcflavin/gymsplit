package com.example.gymsplit;

import android.os.Parcel;
import android.os.Parcelable;

public class MuscleGroup implements Parcelable {
    private String name;
    private boolean isCompleted;

    public MuscleGroup(String name) {
        this.name = name;
        this.isCompleted = false;
    }

    protected MuscleGroup(Parcel in) {
        name = in.readString();
        isCompleted = in.readByte() != 0;
    }

    public static final Creator<MuscleGroup> CREATOR = new Creator<MuscleGroup>() {
        @Override
        public MuscleGroup createFromParcel(Parcel in) {
            return new MuscleGroup(in);
        }

        @Override
        public MuscleGroup[] newArray(int size) {
            return new MuscleGroup[size];
        }
    };

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isCompleted ? 1 : 0));
    }
}
