package com.example.clicker.settings;

import android.util.Log;
import android.widget.ToggleButton;

import com.example.clicker.ClickerApplication;
import com.example.clicker.achievements.AchievementsManager;
import com.example.clicker.data.PlayerRepository;

public class SettingsPresenter implements SettingsContract.Presenter {
    String tag = SettingsPresenter.class.getName();

    private SettingsContract.View view;

    public SettingsPresenter(SettingsContract.View view) {
        this.view = view;
    }

    @Override
    public void onSoundsSettingButtonClicked(ToggleButton button) {
        if (button.isChecked()) {
            view.musicSoundOn();
            AchievementsManager.getInstance().IncProgress("sound", 1, view.getAppContext());
        } else {
            view.musicSoundOff();
            AchievementsManager.getInstance().IncProgress("sound", 1, view.getAppContext());
        }
    }

    @Override
    public void checkMusicSoundState() {
        PlayerRepository playerRepository = ClickerApplication.from(view.getAppContext()).getPlayerRepository();
        if (playerRepository.isMusicSoundState()) {
            view.setMusicSoundButtonStateOn();
        }
    }

    @Override
    public void onLogoutButtonClicked() {
        // TODO
    }

    @Override
    public void onFeedbackButtonClicked() {
        // TODO
    }

    @Override
    public void onAboutAppButtonClicked() {
        // TODO
    }
}
