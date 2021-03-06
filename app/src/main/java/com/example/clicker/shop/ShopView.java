package com.example.clicker.shop;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clicker.R;
import com.example.clicker.data.sqlite.Upgrade;

import java.util.List;

public class ShopView extends Fragment implements ShopContract.View {
    private TextView moneyValView;

    private RecyclerView shopView;
    private RecyclerView.LayoutManager layoutManager;

    private ShopContract.Presenter mPresenter;

    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new ShopPresenter(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.shop_container, container, false);

        moneyValView = mView.findViewById(R.id.money_val);
        mPresenter.getMoney();

        layoutManager = new LinearLayoutManager(this.getContext());

        showUpgradeList();
//        mPresenter.fetchUpgrades();

        return mView;
    }

    @Override
    public void showUpgradeList() {
        shopView = mView.findViewById(R.id.shop_view);
        shopView.setAdapter(mPresenter.getShopElementsAdapter());
        shopView.setLayoutManager(layoutManager);
    }

    @Override
    public Context getAppContext() {
        return getContext().getApplicationContext();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onViewClosed();
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void showMoney(int money) {
        moneyValView.setText(String.valueOf(money));
    }

    @Override
    public void incrementUpgradeCounter(int upgradeID) {
        mPresenter.getShopElementsAdapter().incrementUpgradeCounter(upgradeID);
    }
}
