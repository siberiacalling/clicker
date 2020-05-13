package com.example.clicker.data.sqlite;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


import com.example.clicker.ClickerApplication;

@Database(entities = {Achievement.class, Upgrade.class}, version = 8)
public abstract class DBRepository extends RoomDatabase {
    public abstract UpgradeDao getUpgradeDao();
    public abstract AchievementDao geAchievementDao();

    public static DBRepository from(Context context) {
        return ClickerApplication.from(context).getDb();
    }
}