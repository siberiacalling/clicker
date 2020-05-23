package com.example.clicker.leaderboard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clicker.R;
import com.example.clicker.data.api.UserApi;

import java.util.List;


public class LeaderboardElementsAdapter extends RecyclerView.Adapter<LeaderboardElementsViewHolder> {
    private List<UserApi.UserPlain> mLeaderboardUsers;

    LeaderboardElementsAdapter(List<UserApi.UserPlain> leaderboardUsers) {
        mLeaderboardUsers = leaderboardUsers;
    }

    @NonNull
    @Override
    public LeaderboardElementsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.leaderboard_element, viewGroup, false);

        return new LeaderboardElementsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardElementsViewHolder leaderboardElementsViewHolder, int i) {
        leaderboardElementsViewHolder.bind(mLeaderboardUsers.get(i), i);
    }

    @Override
    public int getItemCount() {
        return mLeaderboardUsers.size();
    }
}
