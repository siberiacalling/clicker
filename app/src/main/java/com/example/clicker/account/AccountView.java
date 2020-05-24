package com.example.clicker.account;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clicker.R;
import com.example.clicker.downloadimage.DownloadImageTask;


public class AccountView extends Fragment implements AccountContract.View {
    private AccountContract.Presenter mPresenter;
    private View mView;
    private String personName;
    private String personEmail;
    private String urlString;
    private Activity activity;

    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AccountPresenter(this);
        mPresenter.loadAchievements();

        Bundle bundle = getArguments();
        if (bundle != null) {
            personName = bundle.getString("personName");
            personEmail = bundle.getString("personEmail");
            urlString = bundle.getString("uri");
        } else {
            Toast.makeText(activity, "empty bundle", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.account, container, false);

        this.showName();
        this.showEmail();
        this.showPhoto();
        return mView;
    }

    @Override
    public void showName() {
        TextView textViewName = mView.findViewById(R.id.user_name);
        textViewName.setText(personName);
    }

    @Override
    public void showEmail() {
        TextView textViewEmail = mView.findViewById(R.id.user_email);
        textViewEmail.setText(personEmail);
    }

    @Override
    public void showPhoto() {
        new DownloadImageTask((ImageView) mView.findViewById(R.id.user_photo))
                .execute(urlString);
    }

    @Override
    public void showClicks(Integer clicks) {
        TextView textViewClicks = mView.findViewById(R.id.user_clicks);
        textViewClicks.setText("Total clicks: " + clicks);
    }

    @Override
    public void showMoney(Integer money) {
        TextView textViewMoney = mView.findViewById(R.id.user_money);
        textViewMoney.setText("Total money: " + money);
    }

    @Override
    public Context getAppContext() {
        return getContext().getApplicationContext();
    }
}
