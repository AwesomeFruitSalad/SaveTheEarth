package org.fruitsalad.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class SaviourOfEarth {

    @PrimaryKey
    private String name;

    private List<String> achievements;

    private int score;

    public SaviourOfEarth() {

    }

    public SaviourOfEarth(String name, List<String> achievements, int score) {
        this.name = name;
        this.achievements = achievements;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<String> achievements) {
        this.achievements = achievements;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "SaviourOfEarth{" +
                "name='" + name + '\'' +
                ", achievements=" + achievements +
                ", score=" + score +
                '}';
    }
}
