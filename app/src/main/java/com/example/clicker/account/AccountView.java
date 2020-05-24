package com.example.clicker.account;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clicker.R;

public class AccountView extends Fragment implements AccountContract.View {
    private RecyclerView leaderboardView;
    private TextView connectionErrorView;
    private RecyclerView.LayoutManager layoutManager;
    //private AccountElementsAdapter leaderboardElementsAdapter;

    private AccountContract.Presenter mPresenter;
    private View mView;
    String personName;
    String personEmail;
    Activity activity;

    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AccountPresenter(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            personName = bundle.getString("personName");
            personEmail = bundle.getString("personEmail");
        } else {
            Toast.makeText(activity, "empty bundle", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.account, container, false);
        return mView;
    }

    @Override
    public void showName() {
        Toast.makeText(activity, personName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmail() {
        Toast.makeText(activity, personEmail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPhoto() {
        Toast.makeText(activity, personEmail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getAppContext() {
        return getContext().getApplicationContext();
    }
}
