package com.example.clicker.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clicker.R;
import com.example.clicker.Router;
import com.example.clicker.data.UserRepositoryImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class MainFragment extends Fragment implements MainContract.View {
    private String logTag = MainActivity.class.getName();

    private MainContract.Presenter presenter;

    private Button loginButton;
    private Button signUpButton;
    private Button gameStartButton;
    private Button settingsButton;
    private Button LogoutButton;
    private com.google.android.gms.common.SignInButton googleLoginButton;

    private ImageButton achievementsButton;
    private ImageButton leaderboardButton;
    View view;

    private GoogleSignInClient mGoogleSignInClient;
    Activity activity;
    int RC_SIGN_IN = 1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this, new UserRepositoryImpl(getContext().getApplicationContext()));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
        updateUI(account);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        TextView nameGame = view.findViewById(R.id.game_name);
        Typeface customFont = Typeface.createFromAsset(
                Objects.requireNonNull(getActivity()).getAssets(), "fonts/Equestria.ttf"
        );
        nameGame.setTypeface(customFont);

        gameStartButton = view.findViewById(R.id.button_start_game);
        gameStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStartGameButtonClicked();
            }
        });

        googleLoginButton = view.findViewById(R.id.sign_in_button);
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        LogoutButton = view.findViewById(R.id.button_logout);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut().addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
            }
        });

        settingsButton = view.findViewById(R.id.button_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSettingsButtonClicked();
            }
        });

        achievementsButton = view.findViewById(R.id.achievements_button);
        achievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAchievementsButtonClicked();
            }
        });

        leaderboardButton = view.findViewById(R.id.leaderboard_button);
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLeaderboardButtonClicked();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("REGISTRATION", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        TextView textView = view.findViewById(R.id.player_name);

        if (account == null) {
            googleLoginButton.setVisibility(View.VISIBLE);
            LogoutButton.setVisibility(View.GONE);
            textView.setVisibility(View.INVISIBLE);
        } else {
            googleLoginButton.setVisibility(View.GONE);
            LogoutButton.setVisibility(View.VISIBLE);
            String personName = account.getDisplayName();
            textView.setVisibility(View.VISIBLE);
            textView.setText("Welcome, " + personName + "!");
        }
    }

    @Override
    public void hideAuthorizationButtons() {
        loginButton.setVisibility(View.GONE);
        signUpButton.setVisibility(View.GONE);
    }

    @Override
    public void showScreenGame() {
        Router router = (Router) getActivity();
        if (router != null) {
            router.openGameScreen();
        } else {
            Log.e(logTag, "This activity is not a Router");
        }
    }

    @Override
    public void showSettingsScreen() {
        Router router = (Router) getActivity();
        if (router != null) {
            router.openSettingsScreen();
        } else {
            Log.e(logTag, "This activity is not a Router");
        }
    }

    @Override
    public void showAchievementsScreen() {
        Router router = (Router) getActivity();
        if (router != null) {
            router.openAchievementsScreen();
        } else {
            Log.e(logTag, "This activity is not a Router");
        }
    }

    @Override
    public void showLeaderboardScreen() {
        Router router = (Router) getActivity();
        if (router != null) {
            router.openLeaderboardScreen();
        } else {
            Log.e(logTag, "This activity is not a Router");
        }
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public Context getAppContext() {
        return getContext().getApplicationContext();
    }
}
