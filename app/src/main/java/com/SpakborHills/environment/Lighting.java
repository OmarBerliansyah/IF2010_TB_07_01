package com.SpakborHills.environment;

import com.SpakborHills.main.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Lighting {

    GamePanel gp;
    BufferedImage darknessFilter;
    ImageIcon rainGif;
    Image rainGifImage;
    int dayCounter;
    float filterAlpha = 0f;

    final int day = 0;
    final int night = 1;
    int dayState = day;

    int minute = 0;
    int hour = 0;
    int frameCounter = 0;
    int dayFrameCounter = 0;
    int hari = 1;

    String phase = "Siang";
    Season season = Season.SPRING;

    public Weather currentWeather = Weather.SUNNY;
    private int rainyDayCount = 0;

    public Lighting(GamePanel gp){
        this.gp = gp;
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
        loadGif();
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));

        g2.setColor(new Color(11,11,69, 150));

        g2.fill(screenArea);

        g2.dispose();
    }

    public void loadGif(){
        java.net.URL gifURL = getClass().getClassLoader().getResource("effect/rain.gif");
        if (gifURL == null) {
            System.out.println("rain.gif not found!");
        } else {
            rainGif = new ImageIcon(gifURL);
            rainGifImage = rainGif.getImage();
        }
    }

    public void updateFilterAlphaByTime(){
        // Jam 06:00 - 18:00 = filterAlpha = 0 (terang)
        // Jam 18:05 - 05:55 = filterAlpha = 1 (gelap)
        // Untuk jam antara 18:00 - 18:05 dan 05:55 - 06:00, kita bisa lakukan fade

        int totalMinutes = hour * 60 + minute;

        if (totalMinutes >= 360 && totalMinutes < 1080) {
            // 06:00 (360) sampai sebelum 18:00 (1080)
            filterAlpha = 0f;
        } else if (totalMinutes >= 1120 || totalMinutes < 355) {
            // 18:05 (1085) sampai 23:59 (1439) atau 00:00 sampai sebelum 05:55 (355)
            filterAlpha = 1f;
        } else if (totalMinutes >= 1080 && totalMinutes < 1120) {
            // Fade in dari jam 18:00 ke 18:05 (1080-1085)
            filterAlpha = (totalMinutes - 1080) / 5f; // naik dari 0 ke 1
        } else if (totalMinutes >= 320 && totalMinutes < 360) {
            // Fade out dari 05:55 ke 06:00 (355-360)
            filterAlpha = 1f - ((totalMinutes - 320) / 5f); // turun dari 1 ke 0
        }
    }

    public void update(){
        frameCounter++;
        if(frameCounter >= 60){
            frameCounter = 0;
            minute += 5; // coba
            if(minute>=60){
                minute = 0;
                hour++;
                if(hour >= 24){
                    hour = 0;
                }
            }
        }
        updateFilterAlphaByTime();

        if(hour>=6 && hour<18){
            dayState = day;
            phase = "Siang";
        } else{
            dayState = night;
            phase = "Malam";
        }

        dayFrameCounter++;
        if(dayFrameCounter >= 17280){ //coba
            dayFrameCounter =0;
            hari++;
            if(hari > 10){
                hari = 1;
                season = season.next();
                rainyDayCount = 0;
            }
            nextDay(hari);
        }
    }

    public void setTime(int hour, int minute){
        this.hour = hour;
        this.minute = minute;

        updateFilterAlphaByTime();

        if(hour >= 6 && hour < 18){
            phase = "Siang";
            this.dayState = day;
        } else{
            phase = "Malam";
            this.dayState = night;
        }
    }

    public int getHour(){
        return this.hour;
    }

    public int getMinute(){
        return this.minute;
    }

    public void nextDay(int currentDayInSeason){
        if(currentDayInSeason >= 30){
            rainyDayCount = 0;
        }
        if(rainyDayCount < 2 && currentDayInSeason >= 28){
            currentWeather = Weather.RAINY;
        }else{
            if(Math.random()<0.2 && rainyDayCount < 2){
                currentWeather = Weather.RAINY;
                rainyDayCount++;
            } else{
                currentWeather = Weather.SUNNY;
            }
        }
    }

    public void draw(Graphics2D g2){
        float clampedAlpha = filterAlpha;
        if (Float.isNaN(filterAlpha)) {
            clampedAlpha = 1f; // fallback default value
        } else {
            clampedAlpha = Math.max(0f, Math.min(1f, filterAlpha));
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, clampedAlpha));
        g2.drawImage(darknessFilter, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Draw the time
        String timeText = String.format("Time: %02d:%02d", hour, minute);
        String dayStr = "Day"+hari;
        String seasonStr = "Season: "+season.name().charAt(0) + season.name().substring(1).toLowerCase();
        String stateStr = "State: "+phase;

        g2.setFont(g2.getFont().deriveFont(30F));
        g2.setColor(Color.white);

        g2.drawString(timeText, 800, 200);
        g2.drawString(dayStr,800,300);
        g2.drawString(seasonStr, 800,  400);
        g2.drawString(stateStr, 800, 500);

        if(currentWeather == Weather.RAINY && rainGif != null && rainGifImage != null){
            g2.drawImage(rainGifImage,0,0,gp.screenWidth, gp.screenHeight, null);
        }else{
        }
    }
}
