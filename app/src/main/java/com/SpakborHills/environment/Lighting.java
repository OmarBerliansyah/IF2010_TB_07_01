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

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.objects.OBJ_Blueberry;
import com.SpakborHills.objects.OBJ_Cauliflower;
import com.SpakborHills.objects.OBJ_Cranberry;
import com.SpakborHills.objects.OBJ_Eggplant;
import com.SpakborHills.objects.OBJ_Grape;
import com.SpakborHills.objects.OBJ_HotPepper;
import com.SpakborHills.objects.OBJ_Melon;
import com.SpakborHills.objects.OBJ_Parsnip;
import com.SpakborHills.objects.OBJ_Potato;
import com.SpakborHills.objects.OBJ_Pumpkin;
import com.SpakborHills.objects.OBJ_Tomato;
import com.SpakborHills.objects.OBJ_Wheat;
import com.SpakborHills.tile.SoilTile;

public class Lighting {

    GamePanel gp;
    BufferedImage darknessFilter;
    ImageIcon rainGif;
    Image rainGifImage;
    public int dayCount = 1;
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
            dayCount++;
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
        System.out.println("=== DAY INCREMENT CALLED ===");
        System.out.println("Old day count: " + dayCount);
        System.out.println("Old hari: " + hari);
        
        hari++;
        dayFrameCounter = 0; 
        gp.player.addDayPlayed();
        
        // PASTIKAN INI ADA DAN BERFUNGSI:
        dayCount++;  // ← CRITICAL: Pastikan ini ada!
        
        System.out.println("New day count: " + dayCount);
        System.out.println("New hari: " + hari);

        if (hari > 10) { 
            hari = 1;
            season = season.next();
            rainyDayCount = 0;  
            gp.player.seasonalStatsChange();
        }
        
        System.out.println("Calling nextDay...");
        nextDay(hari);
    }

    public void nextDay(int currentDayInSeason){
        updateEachDay();

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

    public int getDayCount() {
        System.out.println("getDayCount() called, returning: " + dayCount);
        return dayCount;
    }

    // TAMBAHKAN DEBUG INI KE updateEachDay() DI LIGHTING.JAVA

    public void updateEachDay(){
        System.out.println("========================================");
        System.out.println("=== UPDATE EACH DAY CALLED ===");
        System.out.println("Current day: " + dayCount);  // Gunakan dayCount, bukan getDayCount()
        System.out.println("Player current map: " + gp.currentMap);
        System.out.println("Current weather: " + currentWeather);
        System.out.println("========================================");
        
        gp.ui.addMessage("=== DAILY UPDATE STARTING ===");
        
        // SELALU CEK FARM MAP (map 0), tidak peduli player di mana
        int farmMapIndex = 0;
        
        int plantsFound = 0;
        int plantsGrown = 0;
        int plantsWithered = 0;
        
        System.out.println("Checking farm map (index 0) for plant growth...");
        
        // Loop melalui semua tile di FARM MAP
        for (int c = 0; c < gp.tileM.mapCols[farmMapIndex]; c++) {
            for (int r = 0; r < gp.tileM.mapRows[farmMapIndex]; r++) {
                
                SoilTile tile = gp.tileM.soilMap[farmMapIndex][c][r];
                
                if (tile == null) {
                    continue;
                }
                
                if (tile.isSeedPlanted) {
                    plantsFound++;

                    if (currentWeather == Weather.SUNNY) {
                        // Check if plant was adequately watered yesterday
                        if (tile.lastWateringDay != dayCount - 1 || tile.dailyWateringCount < 2) {
                            // Plant wasn't watered enough, it withers
                            System.out.println("PLANT WITHERED at (" + c + "," + r + ") - insufficient watering");
                            System.out.println("  Last watered day: " + tile.lastWateringDay);
                            System.out.println("  Daily watering count: " + tile.dailyWateringCount);
                            
                            // Reset to tilled state
                            tile.isSeedPlanted = false;
                            tile.seedType = null;
                            tile.plantedDay = 0;
                            tile.dailyWateringCount = 0;
                            tile.lastWateringDay = -1;
                            tile.adequatelyWatered = false;
                            
                            // Change tile back to tilled
                            gp.tileM.mapTileNum[farmMapIndex][c][r] = 8; // Assuming 8 is tilled tile
                            
                            plantsWithered++;
                            continue;
                        }
                    }

                    // Reset daily watering count for new day
                    tile.dailyWateringCount = 0;
                    tile.adequatelyWatered = false;
                    
                    int currentDay = dayCount;  // Gunakan dayCount langsung
                    int dayDiff = currentDay - tile.plantedDay;
                    int requiredDays = getGrowthDays(tile.seedType);
                    
                    System.out.println("FOUND PLANT #" + plantsFound);
                    System.out.println("  Position: (" + c + "," + r + ")");
                    System.out.println("  Seed type: " + tile.seedType);
                    System.out.println("  Planted day: " + tile.plantedDay);
                    System.out.println("  Current day: " + currentDay);
                    System.out.println("  Days grown: " + dayDiff);
                    System.out.println("  Required days: " + requiredDays);
                    System.out.println("  Ready to harvest: " + (dayDiff >= requiredDays));
                    
                    if (dayDiff >= requiredDays && requiredDays > 0) {
                        System.out.println("  -> HARVESTING PLANT!");
                        
                        Entity grownPlant = createGrownPlant(tile.seedType);
                        if (grownPlant != null) {
                            grownPlant.worldX = c * gp.tileSize;
                            grownPlant.worldY = r * gp.tileSize;

                            insertToMapObjects(farmMapIndex, grownPlant);
                            
                            // Reset soil tile
                            tile.isSeedPlanted = false;
                            tile.seedType = null;
                            tile.plantedDay = 0;
                            tile.dailyWateringCount = 0;
                            tile.lastWateringDay = -1;
                            tile.adequatelyWatered = false;
                            
                            // Update tile visual ke tile tanah biasa
                            gp.tileM.mapTileNum[farmMapIndex][c][r] = 0;
                            
                            plantsGrown++;
                            
                            System.out.println("  -> SUCCESS: " + grownPlant.name + " created!");
                            gp.ui.addMessage("Plant grown: " + grownPlant.name + " at (" + c + "," + r + ")");
                            gp.player.totalCropHarvested +=  gp.player.getHarvestAmount(grownPlant.name);
                        } else {
                            System.out.println("  -> ERROR: Failed to create plant for " + tile.seedType);
                        }
                    } else {
                        System.out.println("  -> Still growing...");
                    }
                    System.out.println();
                }
            }
        }
        
        System.out.println("========================================");
        System.out.println("DAILY UPDATE SUMMARY:");
        System.out.println("Plants found: " + plantsFound);
        System.out.println("Plants harvested: " + plantsGrown);
        System.out.println("Plants withered: " + plantsWithered);
        System.out.println("========================================");
        
        gp.ui.addMessage("Daily check: " + plantsFound + " plants, " + plantsGrown + " harvested");
    }

    public int getGrowthDays(String seedType){
        return switch(seedType){
            case "Parsnip Seeds" -> 1;
            case "Cauliflower Seeds" -> 5;
            case "Potato Seeds" -> 3;
            case "Wheat Seeds" -> 1;
            case "Blueberry Seeds" -> 7;
            case "Tomato Seeds" -> 3;
            case "Hot Pepper Seeds" -> 1;
            case "Melon Seeds" -> 4;
            case "Cranberry Seeds" -> 2;
            case "Pumpkin Seeds" -> 7;
            case "Grape Seeds" -> 3;
            case "Eggplant Seeds" -> 5;
            default -> 0; // Default jika tidak ada yang cocok
        };
    }

    public Entity createGrownPlant(String seedType) {
        return switch (seedType) {
            case "Parsnip Seeds" -> new OBJ_Parsnip(gp);
            case "Cauliflower Seeds" -> new OBJ_Cauliflower(gp);
            case "Potato Seeds" -> new OBJ_Potato(gp);
            case "Wheat Seeds" -> new OBJ_Wheat(gp);
            case "Blueberry Seeds" -> new OBJ_Blueberry(gp);
            case "Tomato Seeds" -> new OBJ_Tomato(gp);
            case "Hot Pepper Seeds" -> new OBJ_HotPepper(gp);
            case "Melon Seeds" -> new OBJ_Melon(gp);
            case "Cranberry Seeds" -> new OBJ_Cranberry(gp);
            case "Pumpkin Seeds" -> new OBJ_Pumpkin(gp);
            case "Grape Seeds" -> new OBJ_Grape(gp);
            case "Eggplant Seeds" -> new OBJ_Eggplant(gp);
            default -> null; // Jika tidak ada yang cocok
        };
    }

    public void insertToMapObjects(int mapIndex, Entity e) {
        System.out.println("=== INSERTING TO MAP OBJECTS ===");
        System.out.println("Map index: " + mapIndex);
        System.out.println("Entity: " + e.name);
        System.out.println("Position: " + e.worldX + "," + e.worldY);
        
        Entity[] mapObjects = gp.getMapObjects(mapIndex);
        for (int i = 0; i < mapObjects.length; i++) {
            if (mapObjects[i] == null) {
                mapObjects[i] = e;
                System.out.println("Inserted at slot: " + i);
                return;
            }
        }
        System.out.println("ERROR: No empty slots in map objects array!");
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

        if(currentWeather == Weather.RAINY && rainGif != null && rainGifImage != null && gp.currentMap != 2 && gp.currentMap != 5 && gp.currentMap != 6 && gp.currentMap != 7 && gp.currentMap != 8 && gp.currentMap != 9 && gp.currentMap != 10){
            g2.drawImage(rainGifImage,0,0,gp.screenWidth, gp.screenHeight, null);
        }else{
        }
    }
}
