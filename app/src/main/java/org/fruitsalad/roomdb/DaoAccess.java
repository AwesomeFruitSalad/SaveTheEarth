package org.fruitsalad.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import org.fruitsalad.model.SaviourOfEarth;

@Dao
public interface DaoAccess {

    @Insert
    void insertSaviourOfEarth(SaviourOfEarth saviourOfEarth);

    @Insert
    void insertSavioursOfEarth(List<SaviourOfEarth> savioursOfEarth);

    @Query("SELECT * from SaviourOfEarth WHERE name = :name")
    SaviourOfEarth getSaviourOfEarthByName(String name);

    @Query("SELECT * from SaviourOfEarth")
    List<SaviourOfEarth> getAllSavioursOfEarth();

    @Query("SELECT achievements from SaviourOfEarth")
    List<String> getAllAchievements();

    @Query("SELECT score from SaviourOfEarth where name = :name")
    int getScoreByName(String name);

    @Query("SELECT score from SaviourOfEarth")
    List<Integer> getAllScores();

    @Query("UPDATE SaviourOfEarth SET score = :score WHERE name = :name")
    int updateSaviourOfEarth(String name, int score);

    @Query("SELECT achievements from SaviourOfEarth where name = :name")
    List<String> getAchievementsByUser(String name);
}
