package com.example.gymsplit;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MuscleGroupAdapter extends RecyclerView.Adapter<MuscleGroupAdapter.ViewHolder> {

    private List<MuscleGroup> muscleGroups;
    private int currentPointerIndex;

    public MuscleGroupAdapter(List<MuscleGroup> muscleGroups, int pointerIndex) {
        this.muscleGroups = muscleGroups;
        this.currentPointerIndex = pointerIndex;
        movePointerToFirstIncomplete();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_muscle_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MuscleGroup muscleGroup = muscleGroups.get(position);
        holder.textView.setText(muscleGroup.getName());

        if (muscleGroup.isCompleted()) {
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textView.setAlpha(0.5f);
        } else {
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.textView.setAlpha(1.0f);
        }

        holder.pointer.setVisibility(position == currentPointerIndex ? View.VISIBLE : View.INVISIBLE);

        holder.itemView.setOnClickListener(v -> {
            muscleGroup.setCompleted(!muscleGroup.isCompleted());
            if (allItemsCompleted()) {
                resetAllItems();
            } else {
                movePointerToFirstIncomplete();
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return muscleGroups.size();
    }

    private void movePointerToFirstIncomplete() {
        for (int i = 0; i < muscleGroups.size(); i++) {
            if (!muscleGroups.get(i).isCompleted()) {
                currentPointerIndex = i;
                return;
            }
        }
        // If all are completed, reset to the first item
        currentPointerIndex = 0;
    }

    private boolean allItemsCompleted() {
        for (MuscleGroup group : muscleGroups) {
            if (!group.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    private void resetAllItems() {
        for (MuscleGroup group : muscleGroups) {
            group.setCompleted(false);
        }
        currentPointerIndex = 0;
    }

    public int getCurrentPointerIndex() {
        return currentPointerIndex;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView pointer;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.muscleGroupName);
            pointer = itemView.findViewById(R.id.pointer);
        }
    }
}