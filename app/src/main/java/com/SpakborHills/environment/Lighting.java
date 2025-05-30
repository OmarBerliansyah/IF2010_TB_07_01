package com.SpakborHills.environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.SpakborHills.main.GamePanel;

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

        if(hour == 2 && minute == 0){
            if(gp.gameState == gp.playState && !gp.ui.showingSleepConfirmDialog){
                gp.player.sleeping();
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
    public void addMinutes(int minutesToAdd) {
        this.minute += minutesToAdd;
        while (this.minute >= 60) {
            this.minute -= 60;
            this.hour++;
            if (this.hour >= 24) {
                this.hour = 0;
            }
        } updateFilterAlphaByTime(); // Pastikan filter pencahayaan diperbarui
        // Perbarui juga fase hari
        if(this.hour >= 6 && this.hour < 18){
            this.dayState = day;
            this.phase = "Siang";
        } else{
            this.dayState = night;
            this.phase = "Malam";
        }
        System.out.println("Time manually advanced by " + minutesToAdd + " mins. New time: " + String.format("%02d:%02d", this.hour, this.minute));
    }
        

    public int getHour(){
        return this.hour;
    }

    public int getMinute(){
        return this.minute;
    }

    public int getHari(){
        return this.hari;
    }

    public String getSeasonName(){
        return this.season.name();
    }

    public String getWeatherName(){
        return this.currentWeather.name();
    }

    public void incrementDayAndAdvanceWeather() {
        hari++;
        dayFrameCounter = 0; 

        if (hari > 10) { 
            hari = 1;
            season = season.next();
            rainyDayCount = 0;  
        }
        nextDay(hari);
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
         // sub-window dimensions and position (top-right corner)
        int windowWidth = gp.tileSize * 3;  
        int windowHeight = gp.tileSize * 3; 
        int windowX = gp.screenWidth - windowWidth - 20; 
        int windowY = 20; 
         // Draw chocolate brown background sub-window
        Color backgroundColor = new Color(210, 125, 44,230);  // Orange-brown background
        Color borderColor = new Color(143, 87, 27);           // Darker brown border
        Color innerBorderColor = new Color(255, 178, 107);    // Lighter inner highlight
        g2.setColor(backgroundColor);
        g2.fillRoundRect(windowX, windowY, windowWidth, windowHeight, 8, 8);
         // Draw border
        g2.setColor(borderColor); // Darker brown for border
        g2.drawRoundRect(windowX, windowY, windowWidth, windowHeight, 8, 8);
        // Draw inner border highlight (lighter inner)
        g2.setColor(innerBorderColor);
        g2.drawRoundRect(windowX + 1, windowY + 1, windowWidth - 2, windowHeight - 2, 6, 6);
        // Draw the time
        String timeText = String.format("Time: %02d:%02d", hour, minute);
        String dayStr = "Day"+hari;
        String seasonStr = "Season: "+season.name().charAt(0) + season.name().substring(1).toLowerCase();
        String stateStr = "State: "+phase;

        Font customFont = gp.ui.getenviFont();
        g2.setFont(customFont);
        g2.setColor(new Color(86, 22, 12));
        int textX = windowX + 25; // 15px padding from left edge of window
        int textY = windowY + 55;  // Start 30px from top of window
        int lineSpacing = 30;      // Space between lines


        g2.drawString(timeText, textX, textY);
        textY+=lineSpacing;
        g2.drawString(dayStr,textX,textY);
        textY+=lineSpacing;
        g2.drawString(seasonStr, textX,  textY);
        textY+=lineSpacing;
        g2.drawString(stateStr, textX, textY);

        if(currentWeather == Weather.RAINY && rainGif != null && rainGifImage != null){
            g2.drawImage(rainGifImage,0,0,gp.screenWidth, gp.screenHeight, null);
        }else{
        }
    }
}
