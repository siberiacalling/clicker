package com.example.clicker.main;

import android.util.Log;

import com.example.clicker.data.BaseCallback;


public class MainPresenter implements MainContract.Presenter {
    private final String tag = MainPresenter.class.getName();

    private MainContract.View mView;


    MainPresenter(MainContract.View view) {
        this.mView = view;
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
    public void onAccountButtonClicked() {
        mView.showAccountScreen();
    }
}
