package com.example.gymsplit;

public class MuscleGroup {
    private String name;
    private boolean isCompleted;

    public MuscleGroup(String name) {
        this.name = name;
        this.isCompleted = false;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
