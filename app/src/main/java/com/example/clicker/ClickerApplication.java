package com.example.clicker;

import android.app.Application;
import androidx.room.Room;
import android.content.Context;
import android.util.Log;


import com.example.clicker.achievements.AchievementsManager;
import com.example.clicker.data.AchievementsRepository;
import com.example.clicker.data.AchievementsRepositoryImpl;
import com.example.clicker.data.BaseCallback;
import com.example.clicker.data.PlayerRepository;
import com.example.clicker.data.PlayerRepositoryImpl;
import com.example.clicker.data.api.ApiRepository;
import com.example.clicker.data.executors.AppExecutors;
import com.example.clicker.data.sqlite.Achievement;
import com.example.clicker.data.sqlite.AchievementDao;
import com.example.clicker.data.sqlite.DBRepository;
import com.example.clicker.data.sqlite.Upgrade;
import com.example.clicker.data.sqlite.UpgradeDao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClickerApplication extends Application {
    private ApiRepository mApiRepository;
    private DBRepository mDbRepository;
    private PlayerRepository mPlayerRepository;
    private AchievementsRepository mAchievementsRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        mApiRepository = new ApiRepository(getApplicationContext());
        mDbRepository = Room.databaseBuilder(this, DBRepository.class, "main_db")
                .fallbackToDestructiveMigration()
                .build();
        mPlayerRepository = new PlayerRepositoryImpl(this);
        mAchievementsRepository = new AchievementsRepositoryImpl(this);

        AchievementsManager.getInstance().initAM(mAchievementsRepository);

        // TODO: Only for debug!
        /*mPlayerRepository.setNotLoggedIn(new BaseCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });*/

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                addBasicUpgradesAndAchievements();
            }
        });
        AppExecutors.getInstance().scheduled().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.d("ACHIEVEMENTS", "Update achievements");
                AchievementsManager.getInstance().UpdateProgressInDB();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public  Upgrade[] concat(Upgrade[] A, Upgrade[] B) {
        int aLen = A.length;
        int bLen = B.length;

        @SuppressWarnings("unchecked")
        Upgrade[] C = (Upgrade[]) Array.newInstance(A.getClass().getComponentType(), aLen+bLen);
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);

        return C;
    }

    public void addBasicUpgradesAndAchievements() {
        UpgradeDao dao = mDbRepository.getUpgradeDao();
        Upgrade[] basicUpgrades = {
                new Upgrade(100, "upgrade", "5$ / 1s", 0, 1000, 500, "img/new_1.webp"),
                new Upgrade(500, "upgrade", "100$ / 5s", 0, 5000, 100, "img/new_2.webp"),
                new Upgrade(2000, "upgrade", "1000$ / 2s", 0, 2000, 12, "img/new_3.webp"),
                new Upgrade(25, "upgrade", "220$ / 8s", 0, 8000, 220, "img/pivo.webp"),
                new Upgrade(1000, "upgrade", "20$ / 3s", 0, 3000, 20, "img/new_4.webp"),
                new Upgrade(3000000, "upgrade", "400$ / 12s", 0, 12000, 400, "img/new_5.webp"),
        };
        Upgrade[] basicSpeeders = {
                new Upgrade(50000, "speeder", "Inc. skill 2 times", 0, 0, 2, "img/tushenka.webp"),
//                new Upgrade(200000, "speeder", "Inc. skill 3 times", 0, 0, 3, "img/github.svg"),
        };
        Upgrade[] basicWorkers = {
                new Upgrade(2000, "worker", "ничего", 0, 5000, 0, "img/default_stalker.webp"),
                new Upgrade(2000, "worker", "ничего", 0, 5000, 0, "img/default_stalker.webp"),
                new Upgrade(2000, "worker", "ничего", 0, 5000, 0, "img/default_stalker.webp"),
                new Upgrade(2000, "worker", "ничего", 0, 5000, 0, "img/default_stalker.webp"),
                new Upgrade(2000, "worker", "ничего", 0, 5000, 0, "img/default_stalker.webp")
        };

        // TODO: Only for debug!
//         mDbRepository.clearAllTables();

        List<Upgrade> upgrades = dao.allUpgrades();
        if (upgrades.isEmpty()) {
            dao.insert(basicUpgrades);
        }

        List<Upgrade> speeders = dao.allSpeeders();
        if (speeders.isEmpty()) {
            dao.insert(basicSpeeders);
        }

        List<Upgrade> workers = dao.allWorkers();
        if (workers.isEmpty()) {
            dao.insert(basicWorkers);
        }


        AchievementDao achievementDao = mDbRepository.geAchievementDao();

        List<Achievement> achievements = achievementDao.all();
        if (achievements.isEmpty()) {
            Achievement[] basicAchievements = {
                    new Achievement("click", "Inception", "1 click", 1, "clicks", ""),
                    new Achievement("click", "From China with love", "100 clicks", 100, "clicks", ""),
                    new Achievement("click", "This is a new virus!", "1000 clicks", 1000, "clicks", ""),
                    new Achievement("click", "PANDEMIC!!!", "10000 clicks", 10000, "clicks", ""),
                    new Achievement("click", "Successful self-isolation", "100000 clicks", 100000, "clicks", ""),

                    new Achievement("money", "Own saving", "Put off from dinners", 1000, "$", ""),
                    new Achievement("money", "Salary saving", "Salary came, cheers!", 5000, "$", ""),
                    new Achievement("money", "Small business support", "Government loves you", 10000, "$", ""),
                    new Achievement("money", "Second wave of help", "OH MY GOOOOOD, 20000", 20000, "$", ""),
                    new Achievement("money", "Buy a vaccine", "Time to think...", 100000, "$", ""),
                    new Achievement("money", "You'll never die", "Congratulations! GAME OVER", 1000000000, "$", ""),
            };

            achievementDao.insert(basicAchievements);
            AchievementsManager.getInstance().initProcData();
        }
    }

    public ApiRepository getApis() {
        return mApiRepository;
    }

    public DBRepository getDb() {
        return mDbRepository;
    }

    public PlayerRepository getPlayerRepository() {
        return mPlayerRepository;
    }

    public static ClickerApplication from(Context context) {
        return (ClickerApplication) context.getApplicationContext();
    }
}
