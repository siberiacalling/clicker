package com.example.clicker.achievements;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.clicker.R;
import com.example.clicker.data.api.UserApi;
import com.example.clicker.data.sqlite.Achievement;

class AchievementsElementsViewHolder extends RecyclerView.ViewHolder {
    private TextView mTitle;
    private TextView mDescription;
    private TextView mProgress;

    AchievementsElementsViewHolder(@NonNull View itemView) {
        super(itemView);

        mTitle = itemView.findViewById(R.id.achievements_title);
        mDescription = itemView.findViewById(R.id.achievements_description);
        mProgress = itemView.findViewById(R.id.achievements_progress);
    }

    void bind(Achievement achievement) {
        mTitle.setText(achievement.getTitle());
        mDescription.setText(achievement.getDescription());
        
        if (achievement.isIsDone()) {
            mProgress.setText("DONE");
            mProgress.setBackgroundColor(Color.parseColor("#2E7D32"));
        } else {
            if (achievement.getType().equals("sound")) {
                mProgress.setText(achievement.getUnit());
            } else {
                mProgress.setText(String.valueOf(achievement.getProgress() + "/" + String.valueOf(achievement.getGoal()) + " " + achievement.getUnit()));
                mProgress.setBackgroundColor(Color.parseColor("#1D3F6B"));
            }
        }
    }
}
