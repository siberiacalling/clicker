package com.example.clicker.main;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.clicker.AppActions;
import com.example.clicker.ClickerApplication;
import com.example.clicker.R;
import com.example.clicker.Router;
import com.example.clicker.achievements.AchievementsView;
import com.example.clicker.data.PlayerRepository;
import com.example.clicker.game.GameView;
import com.example.clicker.account.AccountView;
import com.example.clicker.settings.SettingsView;
import com.example.clicker.shop.ShopView;

public class MainActivity extends AppCompatActivity implements Router, AppActions, MainFragment.OnDataPass, MainFragment.OnImagePass {
    String tag = MainActivity.class.getName();

    private MediaPlayer musicPlayer;
    private boolean isGameScreenStarted = false;

    private PlayerRepository playerRepository;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private GameView gameView;

    String personEmail;
    String personName;
    Uri personPhoto;

    @Override
    public void onDataPass(String dataType, String data) {
        if (dataType.equals("personEmail")){
            personEmail = data;
        }

        if (dataType.equals("personName")){
            personName = data;
        }
    }

    @Override
    public void onImagePass(Uri photo) {
        personPhoto = photo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerRepository = ClickerApplication.from(getApplicationContext()).getPlayerRepository();

        fragmentManager.beginTransaction()
                .replace(R.id.main_activity, new MainFragment())
                .commit();

        musicOnMainSound();

        gameView = new GameView();

    }

    private void clearMusicPlayer() {
        if (musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.release();
            musicPlayer = null;
        }
    }

    private void musicOnMainSound() {
        if (playerRepository.isMusicSoundState()) {
            Log.d(tag, "musicOnMainSound: soundOn");
            clearMusicPlayer();
            musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.main_sound_128kbit);
            musicPlayer.start();
        } else {
            Log.d(tag, "musicOnMainSound: soundOff");
        }
    }

    private void musicOnGameSound() {
        if (playerRepository.isMusicSoundState()) {
            Log.d(tag, "musicOnGameSound: soundOn");
            clearMusicPlayer();
            musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.game_sound);
            musicPlayer.setLooping(true);
            musicPlayer.start();
        } else {
            Log.d(tag, "musicOnMainSound: soundOff");
        }
    }

    private void musicOnShopSound() {
        if (playerRepository.isMusicSoundState()) {
            Log.d(tag, "musicOnGameSound: soundOn");
            clearMusicPlayer();
            musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.shop_sound);
            musicPlayer.setLooping(true);
            musicPlayer.start();
        } else {
            Log.d(tag, "musicOnMainSound: soundOff");
        }
    }

    @Override
    public void openSettingsScreen() {
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_activity, new SettingsView())
                .commit();
    }

    @Override
    public void openAchievementsScreen() {
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_activity, new AchievementsView())
                .commit();
    }

    @Override
    public void openAccountScreen() {
        Bundle bundle = new Bundle();
        bundle.putString("personEmail", personEmail);
        bundle.putString("personName", personName);

        AccountView accountView = new AccountView();
        accountView.setArguments(bundle);

        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_activity, accountView)
                .commit();
    }

    @Override
    public void openGameScreen() {
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_activity, gameView, "Game")
                .commit();
        musicOnGameSound();
        isGameScreenStarted = true;
    }

    @Override
    public void openShopScreen() {
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_activity, new ShopView(), "Shop")
                .commit();
        musicOnShopSound();
    }

    @Override
    public void openMainScreen() {
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
        musicOnMainSound();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (musicPlayer != null) {
            musicPlayer.start();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 1 && isGameScreenStarted) {
            openMainScreen();
            isGameScreenStarted = false;
        } else {
            super.onBackPressed();
            if (gameView.isVisible()) {
                musicOnGameSound();
            }
        }
    }

    @Override
    protected void onPause() {
        if (musicPlayer != null) {
            musicPlayer.pause();
        }
        super.onPause();
    }

    @Override
    public void musicSoundOff() {
        Log.d(tag, "Music Off");
        playerRepository.setMusicSoundStateOff();
        clearMusicPlayer();
    }

    @Override
    public void musicSoundOn() {
        Log.d(tag, "Music On");
        playerRepository.setMusicSoundStateOn();
        musicOnMainSound();
    }
}
