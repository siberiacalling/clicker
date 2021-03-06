package com.example.clicker.data.sqlite;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UpgradeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Upgrade... upgrades);

    @Query("update Upgrade set mCount = mCount + 1 where mId = :id")
    void increaseCounter(int id);

    @Update
    void upgradeWorker(Upgrade upgrade);

    @Query("update Upgrade set mCount = 0," +
            "mValue = 0, " +
            "mInterval = 5000, " +
            "mPrice = 2000," +
            "mPicPath = :picPath where mId = :workerId")
    void layOffWorker(int workerId, String picPath);

    @Query("select * from Upgrade order by mId")
    List<Upgrade> all();

    @Query("select * from Upgrade where mName == 'upgrade' order by mId")
    List<Upgrade> allUpgrades();

    @Query("select * from Upgrade where mName == 'speeder' order by mId")
    List<Upgrade> allSpeeders();

    @Query("select * from Upgrade where mId = :id")
    List<Upgrade> worker(int id);

    @Query("select * from Upgrade where mName == 'worker' order by mId")
    List<Upgrade> allWorkers();

    @Query("select MAX(mCount) from Upgrade where mName  = 'worker'")
    int getMaxWorkerLVL();

    @Query("delete from Upgrade where mName  = 'worker'")
    void deleteAllWorkers();

    @Query("delete from Upgrade where mName  = 'speeder'")
    void deleteAllSpeeders();
}
