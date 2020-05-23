package com.example.clicker.main;

import android.media.MediaPlayer;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.clicker.AppActions;
import com.example.clicker.ClickerApplication;
import com.example.clicker.R;
import com.example.clicker.Router;
import com.example.clicker.achievements.AchievementsView;
import com.example.clicker.authorization.AuthContract;
import com.example.clicker.authorization.AuthView;
import com.example.clicker.data.PlayerRepository;
import com.example.clicker.game.GameView;
import com.example.clicker.leaderboard.LeaderboardView;
import com.example.clicker.settings.SettingsView;
import com.example.clicker.shop.ShopView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

public class MainActivity extends AppCompatActivity implements Router, AppActions {
    String tag = MainActivity.class.getName();

    private MediaPlayer musicPlayer;
    private boolean isGameScreenStarted = false;

    private PlayerRepository playerRepository;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private GameView gameView;
    private GoogleApiClient googleApiClient;

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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                //.enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            Toast.makeText(getApplicationContext(), "Success login", Toast.LENGTH_SHORT).show();

            //GoogleSignInAccount account = result.getSignInAccount();

        } else {
            Toast.makeText(getApplicationContext(), "Not success login", Toast.LENGTH_SHORT).show();

            // goLogInScreen();
        }
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
    public void openSignUpScreen() {
        Bundle args = new Bundle();
        args.putString(AuthContract.authMethodKey, AuthContract.authMethodSignUp);

        AuthView fragment = new AuthView();
        fragment.setArguments(args);

        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_activity, fragment)
                .commit();
    }

    @Override
    public void openLoginScreen() {
        Bundle args = new Bundle();
        args.putString(AuthContract.authMethodKey, AuthContract.authMethodLogin);

        AuthView fragment = new AuthView();
        fragment.setArguments(args);

        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_activity, fragment)
                .commit();
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
    public void openLeaderboardScreen() {
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_activity, new LeaderboardView())
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