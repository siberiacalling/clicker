package com.example.clicker.data.sqlite;

import androidx.room.Database;
import androidx.room.RoomDatabase;
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