package com.example.clicker.shop;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.clicker.R;
import com.example.clicker.data.sqlite.Upgrade;

class ShopUpgradeElementsViewHolder extends ShopBaseElementsViewHolder {
    private final String tag = ShopUpgradeElementsViewHolder.class.getName();

    private TextView mGoodsQuantity;

    ShopUpgradeElementsViewHolder(@NonNull final View itemView, final ShopContract.Presenter presenter) {
        super(itemView, presenter);

        mGoodsQuantity = itemView.findViewById(R.id.goods_quantity);
    }

    void bind(Upgrade upgrade, boolean enoughMoney) {
        super.bind(upgrade, enoughMoney);
        mDescriptionView.setText(upgrade.getDescription());
        mPriceView.setText(new StringBuilder("$ " + upgrade.getPrice()));
        mGoodsQuantity.setText(new StringBuilder("x " + upgrade.getCount()));
    }
}
