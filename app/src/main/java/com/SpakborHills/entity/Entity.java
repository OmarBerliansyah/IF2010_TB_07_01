package com.SpakborHills.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.List;

import javax.imageio.ImageIO;

import com.SpakborHills.environment.Season;
import com.SpakborHills.environment.Weather;
import com.SpakborHills.environment.Location;

import com.SpakborHills.main.GamePanel;
import com.SpakborHills.main.UtilityTool;

public class Entity {
    public GamePanel gp;
    public int worldX,worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage tillingUp, tillingDown, tillingLeft, tillingRight;
    public BufferedImage wateringUp, wateringDown, wateringLeft, wateringRight;
    public BufferedImage recoverLandUp, recoverLandDown, recoverLandLeft, recoverLandRight;
    public BufferedImage parsnipSeeds, cauliflowerSeeds, potatoSeeds, wheatSeeds;
    public BufferedImage blueberrySeeds, tomatoSeeds, hotPepperSeeds, melonSeeds, eggplantSeeds;
    public BufferedImage cranberrySeeds, pumpkinSeeds, grapeSeeds;
    public String direction = "down";
    public EntityType type;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 56);
    public Rectangle tillingArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public int actionLockCounter = 0;

    String dialogue[]= new String[20];
    int dialogueIndex= 0;

    public BufferedImage image;
    public String name;
    public int daysToHarvest; 
    public int buyPrice; 
    public int salePrice;
    public boolean isEdible; 
    public int plusEnergy;
    public int cropCount; //Jumlah Crop per Panen
    public boolean collision = false;
    boolean tilling = false;
    boolean planting = false;
    boolean watering = false;
    boolean recoverLand = false;
    boolean sleeping = false;
    boolean watching = false;
    boolean proposing = false;
    boolean marry = false;
    public int price = 0;
    public int sellPrice = 0;
    public int value = 0;


    public boolean isPickable = true;
    public Entity harvestProduct;
    public int plantedDay;

    // ITEM ATTRIBUTES 
    public String description = "";
    private boolean dialogueInProgress;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){

    }

    public void speak(){
        if(dialogue[dialogueIndex]==null){
            System.out.println("DIALOGUE FINISHED - RESETTING FLAGS");
            dialogueIndex = 0;
            dialogueInProgress = false;
        }
        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public Entity copy(){
        return null;
    }

    public EnumSet<Season> getAvailableSeasons(){
        return EnumSet.noneOf(Season.class);
    }

    public void update(){
        setAction();

        collisionOn = false;

        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);


        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        if(collisionOn == false){
            switch(direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed;break;
                case "right": worldX += speed; break;
            }
        }
        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if (spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage imageToDraw = null;

        int entityDrawScreenX = worldX - gp.clampedCameraX;
        int entityDrawScreenY = worldY - gp.clampedCameraY;

        if (entityDrawScreenX + gp.tileSize > 0 &&   // Tepi kanan entitas > tepi kiri layar
            entityDrawScreenX < gp.screenWidth &&    // Tepi kiri entitas < tepi kanan layar
            entityDrawScreenY + gp.tileSize > 0 &&   // Tepi bawah entitas > tepi atas layar
            entityDrawScreenY < gp.screenHeight) { 
            switch (direction) {
                case "up":
                    if (spriteNum == 1) imageToDraw = up1;
                    if (spriteNum == 2) imageToDraw = up2;
                    break;
                case "down":
                    if (spriteNum == 1) imageToDraw = down1;
                    if (spriteNum == 2) imageToDraw = down2;
                    break;
                case "left":
                    if (spriteNum == 1) imageToDraw = left1;
                    if (spriteNum == 2) imageToDraw = left2;
                    break;
                case "right":
                    if (spriteNum == 1) imageToDraw = right1;
                    if (spriteNum == 2) imageToDraw = right2;
                    break;
            }
            if (imageToDraw != null){
            g2.drawImage(imageToDraw, entityDrawScreenX, entityDrawScreenY, gp.tileSize , gp.tileSize, null);
            }
        }
    }

    public BufferedImage setup(String imageName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage localImage = null;

        try {
            // Pastikan path lengkap ada di imageName, misal "NPC/abibelakang" bukan "NPC/abibelakang.png"
            InputStream is = getClass().getClassLoader().getResourceAsStream(imageName + ".png");
            if (is == null) {
                System.err.println("ERROR ENTITY: Gambar tidak ditemukan: " + imageName + ".png");
                return null; // Kembalikan null jika gambar tidak ditemukan
            }
            localImage = ImageIO.read(is);
            localImage = uTool.scaleImage(localImage, width, height);
            is.close();
        } catch (IOException e) {
            System.err.println("ERROR ENTITY: Gagal memuat gambar: " + imageName + ".png");
            e.printStackTrace();
        }
        return localImage;
    }

    public static interface FishableProperties{
        String getFishName();
        String getFishCategory();
        EnumSet<Season> getAvailableSeasons();
        EnumSet<Weather> getAvailableWeathers();
        List<Integer> getAvailableStartTimes();
        List<Integer> getAvailableEndTimes();
        EnumSet<Location> getAvailableLocations();

        default int getNumSeasonsFactor() {
        if (getAvailableSeasons() == null || getAvailableSeasons().isEmpty() || getAvailableSeasons().size() == Season.values().length) {
            return 4; // "Any" atau semua season
        }
        return getAvailableSeasons().size();
        }

        default int getNumHoursFactor() {
        if (getAvailableStartTimes() == null || getAvailableStartTimes().isEmpty()) {
            return 24; // "Any" time implicitly, but formula expects actual hours.
                       // For "Any" time, the condition check will pass, for price calc, use 24 if no specific hours.
                       // However, fish usually have specific hours.
        }
        int totalHours = 0;
        for (int i = 0; i < getAvailableStartTimes().size(); i++) {
            int start = getAvailableStartTimes().get(i);
            int end = getAvailableEndTimes().get(i);
            if (end == 0) end = 24; // Midnight Carp 20:00 - 02:00 means end is effectively next day 2am.
                                   // Or, treat 00:00 as start of day. Pufferfish 00:00-16:00 is 16 hours.
                                   // Halibut: 06-11 (5 jam), 19-02 (7 jam) -> 12 jam
            if (end < start) { // Overnight
                totalHours += (24 - start) + end;
            } else {
                totalHours += (end - start);
            }
        }
        return totalHours == 0 ? 24 : totalHours; // if 0, assume "Any" for formula, though rare.
    }

        default int getNumWeatherFactor() {
            if (getAvailableWeathers() == null || getAvailableWeathers().isEmpty() || getAvailableWeathers().size() == Weather.values().length) {
                return 2; // "Any" atau semua weather (Sunny, Rainy)
            }
            return getAvailableWeathers().size();
        }

        default int getNumLocationsFactor() {
            if (getAvailableLocations() == null || getAvailableLocations().isEmpty()) {
                return 4; // Should not happen, fish always has locations
            }
            return getAvailableLocations().size();
        }
    }
}
