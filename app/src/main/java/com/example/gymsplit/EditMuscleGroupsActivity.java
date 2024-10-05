package com.example.gymsplit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class EditMuscleGroupsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MuscleGroupEditAdapter adapter;
    private List<MuscleGroup> muscleGroups;
    private EditText inputMuscleGroup;
    private Button addButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_muscle_groups);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        inputMuscleGroup = findViewById(R.id.inputMuscleGroup);
        addButton = findViewById(R.id.addButton);
        backButton = findViewById(R.id.backButton);

        muscleGroups = getIntent().getParcelableArrayListExtra("muscleGroups");
        if (muscleGroups == null) {
            muscleGroups = new ArrayList<>();
        }

        adapter = new MuscleGroupEditAdapter(muscleGroups);
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            String muscleName = inputMuscleGroup.getText().toString().trim();
            if (!TextUtils.isEmpty(muscleName) && muscleGroups.size() < 10) {
                muscleGroups.add(new MuscleGroup(muscleName));
                adapter.notifyDataSetChanged();
                inputMuscleGroup.setText("");
            } else if (muscleGroups.size() >= 10) {
                Toast.makeText(this, "Limit of 10 muscle groups reached", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a muscle group name", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putParcelableArrayListExtra("updatedMuscleGroups", new ArrayList<>(muscleGroups));
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}