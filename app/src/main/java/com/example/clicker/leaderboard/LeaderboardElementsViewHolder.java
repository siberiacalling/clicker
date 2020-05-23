package com.example.clicker.leaderboard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.clicker.R;
import com.example.clicker.data.api.UserApi;

class LeaderboardElementsViewHolder extends RecyclerView.ViewHolder {
    private TextView mPlaceNumber;
    private TextView mUsername;
    private TextView mScore;

    LeaderboardElementsViewHolder(@NonNull View itemView) {
        super(itemView);

        mPlaceNumber = itemView.findViewById(R.id.leaderboard_place);
        mUsername = itemView.findViewById(R.id.leaderboard_username);
        mScore = itemView.findViewById(R.id.leaderboard_score);
    }

    void bind(UserApi.UserPlain leaderboardUser, int i) {
        mPlaceNumber.setText(String.valueOf(i + 1));
        mUsername.setText(leaderboardUser.getUsername());
        mScore.setText(String.valueOf(leaderboardUser.getScore()));
    }
}
