package com.example.gymsplit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MuscleGroupEditAdapter extends RecyclerView.Adapter<MuscleGroupEditAdapter.ViewHolder> {

    private List<MuscleGroup> muscleGroups;

    public MuscleGroupEditAdapter(List<MuscleGroup> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_muscle_group_edit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MuscleGroup muscleGroup = muscleGroups.get(position);
        holder.textView.setText(muscleGroup.getName());

        holder.deleteButton.setOnClickListener(v -> {
            muscleGroups.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, muscleGroups.size());
        });
    }

    @Override
    public int getItemCount() {
        return muscleGroups.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.muscleGroupName);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}