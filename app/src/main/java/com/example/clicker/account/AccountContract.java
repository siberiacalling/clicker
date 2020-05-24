package com.example.clicker.account;

import android.content.Context;

import com.example.clicker.data.api.UserApi;

import java.util.List;

public interface AccountContract {
    interface View {
        void showName();
        void showEmail();
        void showPhoto();
        Context getAppContext();
    }

    interface Presenter {
        // void fetchLeaderboard();
    }
}
