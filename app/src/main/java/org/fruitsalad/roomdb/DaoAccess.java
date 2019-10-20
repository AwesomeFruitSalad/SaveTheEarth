package org.fruitsalad.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.fruitsalad.model.SaviourOfEarth;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insertSaviourOfEarth(SaviourOfEarth saviourOfEarth);

    @Query("SELECT name from SaviourOfEarth WHERE name = :name")
    List<String> getSaviourOfEarth(String name);

    @Query("SELECT * from SaviourOfEarth")
    List<SaviourOfEarth> getAllSavioursOfEarth();

    @Query("SELECT achievements from SaviourOfEarth")
    List<String> getAllAchievements();

    @Query("SELECT score from SaviourOfEarth where name = :name")
    int getCurrentSaviourOfEarthScore(String name);

    @Query("SELECT score from SaviourOfEarth")
    List<Integer> getAllScores();
}
