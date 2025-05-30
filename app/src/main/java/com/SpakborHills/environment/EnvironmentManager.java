package com.SpakborHills.environment;

import java.awt.Graphics2D;
import com.SpakborHills.environment.Season;
import com.SpakborHills.environment.Weather;

import java.awt.*;
import com.SpakborHills.entity.Entity;
import com.SpakborHills.objects.*;
import com.SpakborHills.tile.SoilTile;

import com.SpakborHills.main.GamePanel;

public class EnvironmentManager {

    GamePanel gp;
    Lighting lighting;

    public EnvironmentManager(GamePanel gp){
        this.gp = gp;
    }

    public void setup(){
        lighting = new Lighting(gp);
        lighting.setTime(6,0);
    }

    public void update(){
        lighting.update();
    }

    public void setTime(int hour, int minute){
        lighting.setTime(hour, minute);
    }
    public void addMinutesToTime(int minute){
        lighting.addMinutes(minute);
    }
    public int getHour() {
        if (lighting != null) {
            return lighting.getHour();
        }
        return 0; // Nilai default jika lighting null (seharusnya tidak terjadi setelah setup)
    }

    public int getMinute() {
        if (lighting != null) {
            return lighting.getMinute();
        }
        return 0; // Nilai default
    }

    public int getHari() {
        if (lighting != null) {
            return lighting.getHari(); // Asumsi getHari() ada di Lighting.java
        }
        return 1; // Nilai default
    }

    public String getWeatherName(){
        if( lighting != null) {
            return lighting.getWeatherName(); // Asumsi getWeatherName() ada di Lighting.java
        }
        return "SUNNY";
    }

    public void incrementDayAndAdvanceWeather() {
        if (lighting != null) {
            lighting.incrementDayAndAdvanceWeather(); // Panggil metode yang ada di Lighting.java
        }
    }

    public int getDayCount() {
        if (lighting != null) {
            return lighting.dayCount; // Asumsi dayCount ada di Lighting.java
        }
        return 0; // Nilai default jika lighting null
    }

    public void draw(Graphics2D g2){
        lighting.draw(g2);
    }


    public String getSeasonName(){
        if (lighting != null){
            return lighting.getSeasonName();
        } else {
            return "SPRING"; // Nilai default jika lighting null
        }
    }

    public Season getCurrentSeason(){
        if(lighting!= null && lighting.season != null){
            return lighting.season;
        } 
        return Season.SPRING;
    }

    public Weather getCurrentWeather(){
        if (lighting != null && lighting.currentWeather != null) {
            return lighting.currentWeather;
        } 
        return Weather.SUNNY; // Nilai default jika lighting null
    }
}
