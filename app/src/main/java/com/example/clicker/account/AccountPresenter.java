package com.example.clicker.account;

import com.example.clicker.achievements.AchievementsContract;
import com.example.clicker.data.AchievementsRepositoryImpl;
import com.example.clicker.data.sqlite.Achievement;

import java.util.List;

class AccountPresenter implements AccountContract.Presenter {
    private AccountContract.View mView;
    private AchievementsContract.Repository mRepository;
    public Integer clicks;
    public Integer money;

    public AccountPresenter(AccountContract.View view) {
        mView = view;
        mRepository = new AchievementsRepositoryImpl(view.getAppContext());
    }

    @Override
    public void loadAchievements() {
        mRepository.getAchievements(new AchievementsContract.Repository.AchievementCallback() {
            @Override
            public void onSuccess(List<Achievement> achievements) {
                Achievement first = achievements.get(0);
                clicks = first.mProgress;
                mView.showClicks(clicks);

                Achievement lastWithMoney = achievements.get(10);
                money = lastWithMoney.mProgress;
                mView.showMoney(money);
            }

            @Override
            public void onError() {
            }
        });
    }
}
