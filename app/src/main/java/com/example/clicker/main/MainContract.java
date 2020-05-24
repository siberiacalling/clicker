package com.example.clicker.main;

import android.content.Context;

import com.example.clicker.data.BaseCallback;

public interface MainContract {

    interface View {
        void hideAuthorizationButtons();
        void showScreenGame();
        void showSettingsScreen();
        void showAchievementsScreen();
        void showLeaderboardScreen();
        Context getViewContext();
        Context getAppContext();
    }

    interface Presenter {
        void onStartGameButtonClicked();
        void onSettingsButtonClicked();
        void onAchievementsButtonClicked();
        void onLeaderboardButtonClicked();
    }

    interface Repository {
        void wasAuthorized(final BaseCallback callback);
    }

}
