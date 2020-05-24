package com.example.clicker.achievements;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.clicker.R;
import com.example.clicker.data.sqlite.Achievement;

import java.util.List;
import java.util.Objects;

public class AchievementsView extends Fragment implements AchievementsContract.View {
    private RecyclerView achievementsView;
    private RecyclerView.LayoutManager layoutManager;
    private AchievementsElementsAdapter achievementsElementsAdapter;

    private AchievementsContract.Presenter mPresenter;

    private View mView;
    private String UserName;

    Activity activity;

    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AchievementsPresenter(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            UserName = bundle.getString("userName");
            Toast.makeText(activity, UserName, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "empty UserName", Toast.LENGTH_SHORT).show();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.achievements_container, container, false);

        layoutManager = new LinearLayoutManager(this.getContext());

        mPresenter.fetchAchievements();

        return mView;
    }

    @Override
    public void showAchievements(List<Achievement> achievements) {
        achievementsElementsAdapter = new AchievementsElementsAdapter(achievements);

        achievementsView = mView.findViewById(R.id.achievements_view);
        achievementsView.setAdapter(achievementsElementsAdapter);
        achievementsView.setLayoutManager(layoutManager);
    }

    @Override
    public Context getAppContext() {
        return Objects.requireNonNull(getContext()).getApplicationContext();
    }
}
