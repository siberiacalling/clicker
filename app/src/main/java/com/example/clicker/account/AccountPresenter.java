package com.example.clicker.account;

//import com.example.clicker.data.LeaderboardRepositoryImpl;
import com.example.clicker.data.api.UserApi;

import java.util.List;

class AccountPresenter implements AccountContract.Presenter {
    private AccountContract.View mView;
    //private AccountContract.Repository mRepository;

    public AccountPresenter(AccountContract.View view) {
        mView = view;
    }
}
