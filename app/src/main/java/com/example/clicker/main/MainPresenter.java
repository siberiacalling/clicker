package com.example.clicker.main;

import android.util.Log;

import com.example.clicker.data.BaseCallback;


public class MainPresenter implements MainContract.Presenter {
    private final String tag = MainPresenter.class.getName();

    private MainContract.View mView;
    private MainContract.Repository mRepository;


    MainPresenter(MainContract.View view, MainContract.Repository repository) {
        this.mView = view;
        this.mRepository = repository;
    }

    @Override
    public void onStartGameButtonClicked() {
        mView.showScreenGame();
    }


    @Override
    public void onSettingsButtonClicked() {
        mView.showSettingsScreen();
    }

    @Override
    public void onAchievementsButtonClicked() {
        mView.showAchievementsScreen();
    }

    @Override
    public void onLeaderboardButtonClicked() {
        mView.showLeaderboardScreen();
    }
}
