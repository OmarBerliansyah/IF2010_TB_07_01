package environment;

import main.java.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Lighting {

    GamePanel gp;
    BufferedImage darknessFilter;
    BufferedImage rainGif;
    int dayCounter;
    float filterAlpha = 0f;

    final int day = 0;
    final int dusk = 1;
    final int night = 2;
    final int dawn = 3;
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
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
        loadGif();

        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));

        g2.setColor(new Color(11,11,69, 150));

        g2.fill(screenArea);

        g2.dispose();
    }

    public void loadGif(){
        try{
            rainGif = ImageIO.read(getClass().getClassLoader().getResourceAsStream("effect/rain.gif"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void update(){
        frameCounter++;
        if(frameCounter >= 60){
            frameCounter = 0;
            minute += 5;
            if(minute>=60){
                minute = 0;
                hour++;
                if(hour >= 24){
                    hour = 0;
                }
            }
        }

        dayFrameCounter++;
        if(dayFrameCounter >= 17280){
            dayFrameCounter =0;
            hari++;
            if(hari > 10){
                hari = 1;
                season = season.next();
                rainyDayCount = 0;
            }
            nextDay(hari);
        }

        if(dayState == day){
            dayCounter++;
            if(dayCounter > 8640){
                dayState = dusk;
                dayCounter = 0;
            }
        }
        if (dayState == dusk) {
            filterAlpha += 0.001f;
            if(filterAlpha > 1f){
                filterAlpha = 1f;
                dayState = night;
            }
        }
        if(dayState == night){
            dayCounter++;
            if(dayCounter > 8640){
                dayState = dawn;
                dayCounter = 0;
            }
        }
        if(dayState == dawn){
            filterAlpha -= 0.001f;
            if(filterAlpha < 0f){
                filterAlpha = 0f;
                dayState = day;
            }
        }

        if(hour>=6 && hour<18){
            phase = "Siang";
        } else{
            phase = "Malam";
        }
    }

    public void setTime(int hour, int minute){
        this.hour = hour;
        this.minute = minute;

        if(hour >= 6 && hour < 18){
            phase = "Siang";
            this.dayState = day;
            this.filterAlpha = 0f;
        } else{
            phase = "Malam";
            this.dayState = night;
            this.filterAlpha = 1f;
        }
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
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
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

        if(currentWeather == Weather.RAINY){
            g2.drawImage(rainGif,0,0,gp.screenWidth, gp.screenHeight, null);
        }else{
        }
    }
}
