package com.example.clicker.leaderboard;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clicker.R;
import com.example.clicker.data.api.UserApi;

import java.sql.Connection;
import java.util.List;

public class LeaderboardView extends Fragment implements LeaderboardContract.View {
    private RecyclerView leaderboardView;
    private TextView connectionErrorView;
    private RecyclerView.LayoutManager layoutManager;
    private LeaderboardElementsAdapter leaderboardElementsAdapter;

    private LeaderboardContract.Presenter mPresenter;

    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new LeaderboardPresenter(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.leaderboard_container, container, false);

        layoutManager = new LinearLayoutManager(this.getContext());

        mPresenter.fetchLeaderboard();

        return mView;
    }

    @Override
    public void showLeaderboardList(List<UserApi.UserPlain> leaderboardUsers) {
        leaderboardElementsAdapter = new LeaderboardElementsAdapter(leaderboardUsers);

        leaderboardView = mView.findViewById(R.id.leaderboard_view);
        leaderboardView.setAdapter(leaderboardElementsAdapter);
        leaderboardView.setLayoutManager(layoutManager);
    }

    @Override
    public void showConnectionError() {
        connectionErrorView = mView.findViewById(R.id.connection_error);
        connectionErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public Context getAppContext() {
        return getContext().getApplicationContext();
    }
}
