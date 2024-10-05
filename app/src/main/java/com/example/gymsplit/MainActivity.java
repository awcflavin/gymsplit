package com.example.gymsplit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int EDIT_REQUEST_CODE = 1;
    private static final String PREFS_NAME = "MuscleGroupPrefs";
    private static final String KEY_MUSCLE_GROUPS = "MuscleGroups";
    private static final String KEY_COMPLETION_STATUS = "CompletionStatus";
    private static final String KEY_POINTER_INDEX = "PointerIndex";

    private RecyclerView recyclerView;
    private MuscleGroupAdapter adapter;
    private List<MuscleGroup> muscleGroups;
    private ImageButton editButton;
    private int pointerIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editButton = findViewById(R.id.editButton);

        muscleGroups = new ArrayList<>();
        loadState();

        adapter = new MuscleGroupAdapter(muscleGroups, getPointerIndex());
        recyclerView.setAdapter(adapter);

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditMuscleGroupsActivity.class);
            intent.putParcelableArrayListExtra("muscleGroups", new ArrayList<>(muscleGroups));
            startActivityForResult(intent, EDIT_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<MuscleGroup> updatedMuscleGroups = data.getParcelableArrayListExtra("updatedMuscleGroups");
            if (updatedMuscleGroups != null) {
                muscleGroups.clear();
                muscleGroups.addAll(updatedMuscleGroups);
                adapter.notifyDataSetChanged();
                saveState();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        StringBuilder muscleGroupNames = new StringBuilder();
        StringBuilder completionStatus = new StringBuilder();
        for (MuscleGroup group : muscleGroups) {
            muscleGroupNames.append(group.getName()).append(",");
            completionStatus.append(group.isCompleted() ? "1" : "0");
        }

        editor.putString(KEY_MUSCLE_GROUPS, muscleGroupNames.toString());
        editor.putString(KEY_COMPLETION_STATUS, completionStatus.toString());
        editor.putInt(KEY_POINTER_INDEX, adapter.getCurrentPointerIndex());
        editor.apply();
    }

    private void loadState() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (!prefs.contains(KEY_MUSCLE_GROUPS) || !prefs.contains(KEY_COMPLETION_STATUS)) {
            return;
        }
        String muscleGroupNames = prefs.getString(KEY_MUSCLE_GROUPS, null);
        String completionStatus = prefs.getString(KEY_COMPLETION_STATUS, null);

        assert muscleGroupNames != null;
        String[] names = muscleGroupNames.split(",");
        muscleGroups.clear();
        for (int i = 0; i < names.length; i++) {
            if (!names[i].isEmpty()) {
                MuscleGroup group = new MuscleGroup(names[i]);
                assert completionStatus != null;
                if (i < completionStatus.length()) {
                    group.setCompleted(completionStatus.charAt(i) == '1');
                }
                muscleGroups.add(group);
            }
        }

        adapter = new MuscleGroupAdapter(muscleGroups, getPointerIndex());
    }

    private int getPointerIndex() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt(KEY_POINTER_INDEX, 0);
    }

}