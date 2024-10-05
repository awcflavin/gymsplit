package com.example.gymsplit;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MuscleGroupPrefs";
    private static final String KEY_POINTER_INDEX = "PointerIndex";
    private static final String KEY_COMPLETION_STATUS = "CompletionStatus";

    private RecyclerView recyclerView;
    private MuscleGroupAdapter adapter;
    private List<MuscleGroup> muscleGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        muscleGroups = new ArrayList<>();
        muscleGroups.add(new MuscleGroup("Chest"));
        muscleGroups.add(new MuscleGroup("Triceps"));
        muscleGroups.add(new MuscleGroup("Back"));
        muscleGroups.add(new MuscleGroup("Biceps"));
        muscleGroups.add(new MuscleGroup("Shoulders"));
        muscleGroups.add(new MuscleGroup("Quads"));
        muscleGroups.add(new MuscleGroup("Calves"));
        muscleGroups.add(new MuscleGroup("Hamstrings"));

        loadState();

        adapter = new MuscleGroupAdapter(muscleGroups, getPointerIndex());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        StringBuilder completionStatus = new StringBuilder();
        for (MuscleGroup group : muscleGroups) {
            completionStatus.append(group.isCompleted() ? "1" : "0");
        }

        editor.putString(KEY_COMPLETION_STATUS, completionStatus.toString());
        editor.putInt(KEY_POINTER_INDEX, adapter.getCurrentPointerIndex());
        editor.apply();
    }

    private void loadState() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String completionStatus = prefs.getString(KEY_COMPLETION_STATUS, null);
        int pointerIndex = prefs.getInt(KEY_POINTER_INDEX, 0);

        if (completionStatus != null && completionStatus.length() == muscleGroups.size()) {
            for (int i = 0; i < muscleGroups.size(); i++) {
                muscleGroups.get(i).setCompleted(completionStatus.charAt(i) == '1');
            }
        } else {
            // Reset if the saved state is invalid
            for (MuscleGroup group : muscleGroups) {
                group.setCompleted(false);
            }
            pointerIndex = 0;
        }

        adapter = new MuscleGroupAdapter(muscleGroups, pointerIndex);
    }

    private int getPointerIndex() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt(KEY_POINTER_INDEX, 0);
    }
}