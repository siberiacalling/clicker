package com.example.clicker.data.sqlite;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AchievementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Achievement... achievements);

    @Query("select * from Achievement order by mType, mGoal asc")
    List<Achievement> all();

    @Query("select * from Achievement where mIsDone != 1 order by mType, mGoal asc")
    List<Achievement> fetchNotDone();

    @Query("select * from Achievement where mType = :type and mIsDone != 1 order by mType, mGoal asc")
    List<Achievement> fetchTypeNotDone(String type);

    @Query("update Achievement set mProgress = :progress where mType = :type")
    void updateProgress(int progress, String type);

    @Query("update Achievement set mProgress = :progress, mIsDone = 1 where mType = :type and :progress >= mGoal and mIsDone != 1")
    void achieveType(int progress, String type);
}
