package com.SpakborHills.environment;

import java.awt.Graphics2D;
import java.util.List;

import com.SpakborHills.main.GamePanel;

public class EnvironmentManager implements DaySubject {

    GamePanel gp;
    Lighting lighting;
    public List<DayObserver> dayObservers;

    public EnvironmentManager(GamePanel gp){
        this.gp = gp;
        this.dayObservers = new java.util.ArrayList<>();
    }

    @Override
    public void addDayObserver(DayObserver observer) {
        if (observer != null && !dayObservers.contains(observer)) {
            dayObservers.add(observer);
            System.out.println("Added day observer: " + observer.getClass().getSimpleName());
        }
    }

    @Override
    public void removeDayObserver(DayObserver observer) {
        if (dayObservers.remove(observer)) {
            System.out.println("Removed day observer: " + observer.getClass().getSimpleName());
        }
    }

    @Override
    public void notifyDayObservers(int newDay, Season season, Weather weather) {
        System.out.println("Notifying " + dayObservers.size() + " observers about day " + newDay);
        for (DayObserver observer : dayObservers) {
            try {
                observer.onDayChanged(newDay, season, weather);
            } catch (Exception e) {
                System.err.println("Error notifying day observer: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void incrementDayAndAdvanceWeather() {
        System.out.println("=== DAY INCREMENT CALLED ===");
        System.out.println("Old day count: " + lighting.dayCount);
        System.out.println("Old hari: " + lighting.hari);
        
        lighting.hari++;
        lighting.dayFrameCounter = 0; 
        gp.player.addDayPlayed();
        
        // PASTIKAN INI ADA DAN BERFUNGSI:
        lighting.dayCount++;  // ← CRITICAL: Pastikan ini ada!
        
        System.out.println("New day count: " + lighting.dayCount);
        System.out.println("New hari: " + lighting.hari);

        if (lighting.hari > 10) { 
            lighting.hari = 1;
            lighting.season = lighting.season.next();
            lighting.rainyDayCount = 0;  
            gp.player.seasonalStatsChange();
        }
        
        System.out.println("Calling nextDay...");
        lighting.nextDay(lighting.hari);
        
        // ===== NOTIFY OBSERVERS ABOUT DAY CHANGE =====
        notifyDayObservers(lighting.dayCount, lighting.season, lighting.currentWeather);
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
            return lighting.getWeatherName();
        }
        return "SUNNY";
    }

    // public void incrementDayAndAdvanceWeather() {
    //     if (lighting != null) {
    //         lighting.incrementDayAndAdvanceWeather(); 
    //     }
    // }

    public int getDayCount() {
        if (lighting != null) {
            return lighting.dayCount;
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
