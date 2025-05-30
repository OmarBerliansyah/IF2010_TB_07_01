package com.SpakborHills.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.SpakborHills.data.EndGameStats;
import com.SpakborHills.data.ItemDefinition;
import com.SpakborHills.entity.Entity.FishableProperties;
import com.SpakborHills.environment.Location;
import com.SpakborHills.environment.Season;
import com.SpakborHills.environment.Weather;
import com.SpakborHills.main.GamePanel;
import com.SpakborHills.main.Inventory;
import com.SpakborHills.main.KeyHandler;
import com.SpakborHills.objects.OBJ_FishingRod;
import com.SpakborHills.objects.OBJ_Hoe;
import com.SpakborHills.objects.OBJ_Pickaxe;
import com.SpakborHills.objects.OBJ_WateringCan;
import com.SpakborHills.tile.SoilTile;
import com.SpakborHills.tile.TileType;


public class Player extends Entity{
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasWood = 0;
    int standCounter = 0;
    boolean moving = false;
    int pixelCounter  = 0;
    // player attributes
    public int energy;
    public int gold;
    public String gender;
    public String farmName;
    public String favoriteItem;
    public Entity partner;
    public String location;    
    public Inventory inventory;
    public Entity equippedItem;
    private List<ShippingBinItem> shippingBinItems = new ArrayList<>();
    private int maxShippingSlots = 16;
    private Random randomGenerator;
    public Entity fishBeingFishedProto;
    public String fishingRangeString;
    public boolean inFishingMinigame;
    public int maxFishingTries;
    public int currentFishingTry;
    private int numberToGuess;

    public boolean tillWithoutEnergy = false;
    public String fishingGuessHint = "";

    //End Game Statistics
    public boolean endGame = false;
    public boolean endGameForIncome = false;
    public boolean endGameForMarriage = false;
    public int endGameCount = 0;
    private boolean hasMarried = false;
    public int totalIncome;
    public int totalExpenditure;
    private int totalIncomePerSeason;
    private int totalExpenditurePerSeason;
    private int seasonalIncomeCount;
    private int seasonalExpenditureCount;
    private double avgSeasonalIncome;
    private double avgSeasonalExpenditure;
    private int totalDaysPlayed;
    public int totalCropHarvested;
    private List<FishableProperties> fishCaught;
    public Inventory.InventoryItem plantingSeedItem;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;
        this.inventory = new Inventory(gp, this);
        this.randomGenerator = new Random();
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        this.fishingGuessHint = "";

        // solidArea = new Rectangle();

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // tillingArea.width = 36;
        // tillingArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerTillingImage();
        getPlayerWateringImage(); 
        getPlayerPickAxeImage();
        getPlayerSeedsImage();
        inventory = new Inventory(gp, this);
        fishCaught = new ArrayList<>();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        energy = 100;//energi awal  & maks
        gold = 0;
        name = "";
        farmName = "";
        gender = "";
        favoriteItem = "";
        partner = null;
        updateLocation();
 // Initialize partner as null, can be set later
        totalIncome = 0;
        totalExpenditure = 0;
        seasonalIncomeCount = 0;
        seasonalExpenditureCount = 0;
        avgSeasonalIncome = 0.0;
        avgSeasonalExpenditure = 0.0;
        totalDaysPlayed = 0;
        totalCropHarvested = 0;
    }

    public void updateLocation() {
        if (gp.currentMap == 0) {
            this.location = "Farm";
        } else if (gp.currentMap == 1) {
            this.location = "Ocean";
        } else if (gp.currentMap == 2) {
            this.location = "House";
        }  else if (gp.currentMap == 3) {
            this.location = "Forest";
        }  else if (gp.currentMap == 4) {
            this.location = "npcMap";
        }  else if (gp.currentMap == 5) {
            this.location = "EmilyMap";
        }  else if (gp.currentMap == 6) {
            this.location = "PerryMap";
        }  else if (gp.currentMap == 7) {
            this.location = "DascoMap";
        }  else if (gp.currentMap == 8) {
            this.location = "AbigailMap";
        }  else if (gp.currentMap == 9) {
            this.location = "MayorMap";
        }  else if (gp.currentMap == 10) {
            this.location = "CarolineMap";
        } else if (gp.currentMap == 11) {
            this.location = "MountainLake";
        }else {
            this.location = "Unknown Area";
        }
    }
    public String getCurrentLocation() {
        return this.location;
    }

    public void getPlayerImage(){
        up1 = setup("player/PlayerUp1", gp.tileSize, gp.tileSize);
        up2 = setup("player/PlayerUp2", gp.tileSize, gp.tileSize);
        down1 = setup("player/PlayerDown1", gp.tileSize, gp.tileSize);
        down2 = setup("player/PlayerDown2", gp.tileSize, gp.tileSize);
        left1 = setup("player/PlayerLeft1", gp.tileSize, gp.tileSize);
        left2 = setup("player/PlayerLeft2",gp.tileSize, gp.tileSize);
        right1 = setup("player/PlayerRight1",gp.tileSize, gp.tileSize);
        right2 = setup("player/PlayerRight2",gp.tileSize, gp.tileSize);
    }

    public void getPlayerTillingImage(){
        tillingUp = setup("player/PlayerUpHoe", gp.tileSize, gp.tileSize);
        tillingDown = setup("player/PlayerDownHoe", gp.tileSize, gp.tileSize);
        tillingLeft = setup("player/PlayerLeftHoe",gp.tileSize, gp.tileSize);
        tillingRight = setup("player/PlayerRightHoe",gp.tileSize, gp.tileSize);
    }

    public void getPlayerWateringImage(){
        wateringUp = setup("player/PlayerLeftWateringCan", gp.tileSize, gp.tileSize);
        wateringDown = setup("player/PlayerRightWateringCan", gp.tileSize, gp.tileSize);
        wateringLeft = setup("player/PlayerLeftWateringCan",gp.tileSize, gp.tileSize);
        wateringRight = setup("player/PlayerRightWateringCan",gp.tileSize, gp.tileSize);
    }

    public void getPlayerSeedsImage(){
        parsnipSeeds = setup("player/PlayerParsnipSeed", gp.tileSize, gp.tileSize);
        cauliflowerSeeds = setup("player/PlayerCauliflowerSeeds", gp.tileSize, gp.tileSize);
        potatoSeeds = setup("player/PlayerPotatoSeeds", gp.tileSize, gp.tileSize);
        wheatSeeds = setup("player/PlayerWheatSeeds", gp.tileSize, gp.tileSize);
        blueberrySeeds = setup("player/PlayerBlueberrySeeds", gp.tileSize, gp.tileSize);
        tomatoSeeds = setup("player/PlayerTomatoSeeds", gp.tileSize, gp.tileSize);
        hotPepperSeeds = setup("player/PlayerHotPepperSeeds", gp.tileSize, gp.tileSize);
        melonSeeds = setup("player/PlayerMelonSeeds", gp.tileSize, gp.tileSize);
        cranberrySeeds = setup("player/PlayerCranberrySeeds", gp.tileSize, gp.tileSize);
        pumpkinSeeds = setup("player/PlayerPumpkinSeeds", gp.tileSize, gp.tileSize);
        grapeSeeds = setup("player/PlayerGrapeSeeds", gp.tileSize, gp.tileSize);
        eggplantSeeds = setup("player/EggplantSeeds", gp.tileSize, gp.tileSize);
    }

    public void getPlayerPickAxeImage(){
        recoverLandUp = setup("player/PlayerLeftPickAxe", gp.tileSize, gp.tileSize);
        recoverLandDown = setup("player/PlayerRightPickAxe", gp.tileSize, gp.tileSize);
        recoverLandLeft = setup("player/PlayerLeftPickAxe", gp.tileSize, gp.tileSize);
        recoverLandRight = setup("player/PlayerRightPickAxe", gp.tileSize, gp.tileSize);
    }

    public void addDayPlayed() {
        totalDaysPlayed++;
    }

    public void seasonalStatsChange(){
        if (gp.eManager.getCurrentSeason() != null) {
            avgSeasonalIncome = (double) totalIncomePerSeason / seasonalIncomeCount;
            avgSeasonalExpenditure = (double) totalExpenditurePerSeason / seasonalExpenditureCount;
        }
        totalIncomePerSeason = 0;
        totalExpenditurePerSeason = 0;
        seasonalIncomeCount = 0;
        seasonalExpenditureCount = 0;
    }

    public void equipItem(int inventoryIndex) {
        if (inventoryIndex >= 0 && inventoryIndex < inventory.getInventory().size()) {
            equippedItem = inventory.getInventory().get(inventoryIndex).item;
        }
    }
    
    public void unEquipItem() {
        equippedItem = null;
    }

    public Entity getEquippedItem() {
        return equippedItem;
    }
    public Inventory.InventoryItem getEquippedInventoryItem() {
        if (equippedItem == null) return null;
    
        for (Inventory.InventoryItem item : inventory.getInventory()) {
            if (item.item == equippedItem) {
                return item;
                }
            }
            return null;
    }
    public void update(){
        if(gp.ui.showingSleepConfirmDialog) {
            return;
        }
        if(gp.ui.showingWatchTV) {
            if (gp.keyH.enterPressed) {
                gp.gameState = gp.playState;
                gp.ui.showingWatchTV = false;
                gp.keyH.enterPressed = false;
                gp.eHandler.canTouchEvent = true;
            }
        }
        if (energy<=-20){
            gp.ui.addMessage("You're completely exhausted! You collapse and fall asleep...");
            sleeping();
            return;
        }
        if (totalIncome >= 17209 && !endGameForIncome && endGameCount < 2) {
            endGame = true;
            endGameForIncome = true;
            endGameTrigger();
            return;
        }
        
        if (hasMarried && !endGameForMarriage && endGameCount < 2) {
            endGame = true;
            endGameForMarriage = true;
            endGameTrigger();
            return;
        }

        if(tilling){
            tilling();
            return;
        }
        if(planting){
            planting();
            return;
        }
        if(watering){
            watering();
            return;
        }
        if(recoverLand){
            recoverLand();
            return;
        }
        if(moving){
            handleMovement();
        }

        else{
            handleInput();
        }

        if (gp.gameState == gp.fishingMinigameState){

            if (gp.keyH.useToolPressed) {

                if (!gp.fishingInputBuffer.isBlank()){
                    try{
                        int guessNumber = Integer.parseInt(gp.fishingInputBuffer);

                        this.processPlayerGuess(guessNumber);
                    } catch (NumberFormatException e) {
                        gp.ui.addMessage("Please enter a valid number!");
                    }
                } else {

                } 
                
            }
            if (gp.keyH.enterPressed){
                this.endFishingMinigame(false);
                gp.keyH.enterPressed = false; // Reset the enterPressed flag
            }
            return;
        }

        if(gp.ui.showingSleepConfirmDialog && gp.gameState == gp.playState) {
            return;
        }
        if(gp.gameState == gp.characterState){
            eating();
            return;
        }

 
    }

    public void handleMovement(){
        //CHECK TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // CHECK OBJECT COLLISION
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        // CHECK NPC COLLISION
        int npcIndex = gp.cChecker.checkEntity(this, gp.NPC);
        interactNPC(npcIndex);

        // CHECK EVENT
        gp.eHandler.checkEvent();
        gp.keyH.enterPressed = false;

        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        if(collisionOn == false && keyH.enterPressed == false){
            switch(direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed;break;
                case "right": worldX += speed; break;
            }
        }
        int currentMap = gp.currentMap;
        int currentMapWorldWidth = gp.tileM.mapCols[currentMap] * gp.tileSize;
        int currentMapWorldHeight = gp.tileM.mapRows[currentMap] * gp.tileSize;

         if (this.worldX + this.solidArea.x < 0) { // Jika tepi kiri solidArea akan keluar ke kiri peta
            this.worldX = -this.solidArea.x;      // Posisikan worldX agar tepi kiri solidArea tepat di 0
        } else if (this.worldX + this.solidArea.x + this.solidArea.width > currentMapWorldWidth) { // Jika tepi kanan solidArea akan keluar ke kanan peta
            this.worldX = currentMapWorldWidth - this.solidArea.width - this.solidArea.x; // Posisikan worldX agar tepi kanan solidArea tepat di batas kanan peta
        }

        if (this.worldY + this.solidArea.y < 0) { // Jika tepi atas solidArea akan keluar ke atas peta
            this.worldY = -this.solidArea.y;      // Posisikan worldY agar tepi atas solidArea tepat di 0
        } else if (this.worldY + this.solidArea.y + this.solidArea.height > currentMapWorldHeight) { // Jika tepi bawah solidArea akan keluar ke bawah peta
            this.worldY = currentMapWorldHeight - this.solidArea.height - this.solidArea.y; // Posisikan worldY agar tepi bawah solidArea tepat di batas bawah peta
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

        pixelCounter += speed;

        if(pixelCounter == gp.tileSize){
            moving = false;
            pixelCounter = 0;
        }
    }

    public void handleInput(){
        boolean movementKeyPressed = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

        if(keyH.useToolPressed && equippedItem != null && !inFishingMinigame) {
            useTool();
            // keyH.useToolPressed = false; // Reset the useToolPressed flag
            return;
        }
        // NPC INTERACTIONS
        if (keyH.enterPressed && !inFishingMinigame) {
            // Chat dengan NPC
            int npcIndex = gp.cChecker.checkEntity(this, gp.NPC);
            if (npcIndex != 999) {
                interactNPC(npcIndex);
            } else {
                // Check events jika tidak ada NPC
                gp.eHandler.checkEvent();
            }
            return;
        }
        if (keyH.giftPressed && !inFishingMinigame) {
            // Gift ke NPC
            int npcIndex = gp.cChecker.checkEntity(this, gp.NPC);
             if (npcIndex != 999) {
                giftToNPC(npcIndex);
            }
            keyH.giftPressed = false;
        }
        if (keyH.proposePressed && !inFishingMinigame) {
        // Propose ke NPC
        int npcIndex = gp.cChecker.checkEntity(this, gp.NPC);
        if (npcIndex != 999) {
            proposeToNPC(npcIndex);
        } else {
            gp.ui.addMessage("No NPC nearby to propose to!");
        }
        keyH.proposePressed = false;
        return;
    }
    if (keyH.marryPressed && !inFishingMinigame) {
        // Marry NPC
        int npcIndex = gp.cChecker.checkEntity(this, gp.NPC);
        if (npcIndex != 999) {
            marryNPC(npcIndex);
        } else {
            gp.ui.addMessage("No NPC nearby to marry!");
        }
        keyH.marryPressed = false;
        return;
    }
       
        
    if (movementKeyPressed && !inFishingMinigame) {
        // Hanya mengatur 'niat' untuk bergerak dan arahnya
        if (keyH.upPressed) direction = "up";
        else if (keyH.downPressed) direction = "down";
        else if (keyH.leftPressed) direction = "left";
        else if (keyH.rightPressed) direction = "right";

        moving = true; // Tandai bahwa pemain ingin bergerak. Aksi gerakan akan dihandle di Player.update() -> handleMovement()
        standCounter = 0;
    } else if (!inFishingMinigame) { // Jika tidak ada tombol gerakan ditekan DAN tidak sedang memancing
        // Logika animasi idle
        standCounter++;
        if (standCounter >= 20) { // Saya ganti '==' menjadi '>=' untuk sedikit lebih robas
            spriteNum = 1;
            standCounter = 0;
        }
    }
}


    public void useTool(){
        int targetCol = 0;
        int targetRow = 0;
        Entity currentTool = equippedItem;
        if (gp.currentMap == 2) { // In house
            int playerTileX = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
            int playerTileY = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
            
            // Check if near cooking station (adjust coordinates as needed)
            if (Math.abs(playerTileX - 8) <= 1 && Math.abs(playerTileY - 8) <= 1) {
                if (energy >= -15) {
                    gp.gameState = gp.cookingState;
                    gp.ui.showCookingInterface();
                    return;
                } else {
                    gp.ui.addMessage("Not enough energy to cook! (Need 10 energy)");
                    return;
                }
            }
        }
        if (currentTool instanceof OBJ_Hoe) {
            switch(direction) {
                case "up":
                    // Tile di atas area solid pemain
                    targetRow = (worldY + solidArea.y - 1) / gp.tileSize;
                    targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                    break;
                case "down":
                    // Tile di bawah area solid pemain
                    targetRow = (worldY + solidArea.y + solidArea.height + 1) / gp.tileSize;
                    targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                    break;
                case "left":
                    // Tile di kiri area solid pemain
                    targetCol = (worldX + solidArea.x - 1) / gp.tileSize;
                    targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                    break;
                case "right":
                    // Tile di kanan area solid pemain
                    targetCol = (worldX + solidArea.x + solidArea.width + 1) / gp.tileSize;
                    targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                    break;
            }

            int tileNum = gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow];
            if (energy >= -15 && gp.tileM.tile[tileNum].tileType == TileType.TILLABLE && gp.currentMap == 0) {
                tilling = true;
                energy-=5;
                tillWithoutEnergy = false;
            }
            else if(energy < -15){
                tillWithoutEnergy = true;
                tilling = true;
                gp.ui.addMessage("Not enough energy to use the hoe!");
            }
            else {
                tillWithoutEnergy = true;
                tilling = true;
                gp.ui.addMessage("You can't till this tile!");
            }
        }
        else if (currentTool.type == EntityType.SEED) {
            Inventory.InventoryItem equippedInventoryItem = getEquippedInventoryItem();
            if(equippedInventoryItem != null && equippedInventoryItem.count > 0){
                Entity equippedSeed = equippedInventoryItem.item;
                if (equippedSeed.type == EntityType.SEED &&
                equippedSeed.getAvailableSeasons().contains(gp.eManager.getCurrentSeason())){
                    if(energy >= -15 && canPlant() && gp.currentMap == 0){
                        plantingSeedItem = equippedInventoryItem;
                        planting = true;
                        energy -= 5;
                        equippedInventoryItem.count--;
                        tillWithoutEnergy = false;
                    }
                    else if (energy < -15){
                        tillWithoutEnergy = true;
                        planting = true;
                        gp.ui.addMessage("Not enough energy to plant!");
                    }
                    else{
                        tillWithoutEnergy = true;
                        planting = true;
                        gp.ui.addMessage("You can't plant here!");
                    }
                }
                else{
                    tillWithoutEnergy = true;
                    planting = true;
                    gp.ui.addMessage("This seed cannot be planted in this season!");
                }
            }
            else{
                planting = true;
                tillWithoutEnergy = true;
                gp.ui.addMessage("You don't have any seeds to plant!");
            }
        }
        else if (currentTool instanceof OBJ_WateringCan) {
            switch(direction) {
                case "up":
                    // Tile di atas area solid pemain
                    targetRow = (worldY + solidArea.y - 1) / gp.tileSize;
                    targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                    break;
                case "down":
                    // Tile di bawah area solid pemain
                    targetRow = (worldY + solidArea.y + solidArea.height + 1) / gp.tileSize;
                    targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                    break;
                case "left":
                    // Tile di kiri area solid pemain
                    targetCol = (worldX + solidArea.x - 1) / gp.tileSize;
                    targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                    break;
                case "right":
                    // Tile di kanan area solid pemain
                    targetCol = (worldX + solidArea.x + solidArea.width + 1) / gp.tileSize;
                    targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                    break;
            }

            int tileNum = gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow];
            if (energy >= -15 && gp.tileM.tile[tileNum].tileType == TileType.PLANTED && tileNum != 10 && gp.currentMap == 0) {
                watering = true;
                tillWithoutEnergy = false;
                energy -= 5; // Misal biaya energi untuk menyiram adalah 5
            } 
            else if(energy < -15) {
                watering = true;
                tillWithoutEnergy = true;
                gp.ui.addMessage("Not enough energy to use the watering can!");
            }
            else{
                watering = true;
                tillWithoutEnergy = true;
                gp.ui.addMessage("Cannot watering this tile!");
            }
        }
        else if (currentTool instanceof OBJ_Pickaxe) {
            switch(direction) {
                case "up":
                    // Tile di atas area solid pemain
                    targetRow = (worldY + solidArea.y - 1) / gp.tileSize;
                    targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                    break;
                case "down":
                    // Tile di bawah area solid pemain
                    targetRow = (worldY + solidArea.y + solidArea.height + 1) / gp.tileSize;
                    targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                    break;
                case "left":
                    // Tile di kiri area solid pemain
                    targetCol = (worldX + solidArea.x - 1) / gp.tileSize;
                    targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                    break;
                case "right":
                    // Tile di kanan area solid pemain
                    targetCol = (worldX + solidArea.x + solidArea.width + 1) / gp.tileSize;
                    targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                    break;
            }

            int tileNum = gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow];
            if (energy >= -15 && gp.tileM.tile[tileNum].tileType == TileType.TILLED && gp.currentMap == 0) {
                recoverLand = true;
                tillWithoutEnergy = false;
                energy -= 5; // Misal biaya energi untuk mencangkul adalah 5
            } 
            else if (energy < -15) {
                recoverLand = true;
                tillWithoutEnergy = true;
                gp.ui.addMessage("Not enough energy to recover this tile!");
            }
            else{
                recoverLand = true;
                tillWithoutEnergy = true;
                gp.ui.addMessage("Cannot recover this tile!");
            }
        } else if (currentTool instanceof OBJ_FishingRod) {
            if(this.energy >= -15){
                startFishing();
                if (gp.gameState != gp.fishingMinigameState){
                    keyH.useToolPressed = false; // Reset the useToolPressed flag
                }
            }
            else{
                gp.ui.addMessage("You're too exhausted to fish! (Need at least -15 energy)");
            }
        } 
        else{
            gp.ui.addMessage("No tool equipped!");
        }
    }

    public boolean canPlant(){
        int targetCol = 0;
        int targetRow = 0;

        switch (direction) {
            case "up":
                targetRow = (worldY + solidArea.y - 1) / gp.tileSize;
                targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                break;
            case "down":
                targetRow = (worldY + solidArea.y + solidArea.height + 1) / gp.tileSize;
                targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                break;
            case "left":
                targetCol = (worldX + solidArea.x - 1) / gp.tileSize;
                targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                break;
            case "right":
                targetCol = (worldX + solidArea.x + solidArea.width + 1) / gp.tileSize;
                targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                break;
        }

        if (targetCol >= 0 && targetRow >= 0 &&
            targetCol < gp.tileM.mapCols[gp.currentMap] &&
            targetRow < gp.tileM.mapRows[gp.currentMap]) {

            int tileNum = gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow];
            return (gp.tileM.tile[tileNum].tileType == TileType.TILLED);
        }

        return false;
    }

    public int indexOfEmptyTile() {
        Entity[] objects = gp.getCurrentMapObjects();
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                return i;
            }
        }
        return -1; // full
    }

    public int indexOf(Entity e) {
        Entity[] objects = gp.getCurrentMapObjects();
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == e) return i;
        }
        return -1;
    }


    //KALO MAU PICKUP OBJECT
    public void pickUpObject(int i){
        Entity[] currentMapObjects = gp.getCurrentMapObjects();
        if (i != 999 && currentMapObjects[i] != null) {
            if (currentMapObjects[i].isPickable) {
                if (inventory.getInventory().size() < inventory.getMaxInventorySize()) { // periksa inventorynya penuh ga
                    
                    //SISTEM MULTI HARVEST
                    String itemName = currentMapObjects[i].name;
                    int harvestAmount = getHarvestAmount(itemName);
                    boolean itemAlreadyInInventory = false;
                    System.out.println("Picking up: " + itemName + " x" + harvestAmount);

                    for (Inventory.InventoryItem invItem : inventory.getInventory()) { 
                        if (invItem.item.name.equals(currentMapObjects[i].name)) {
                            invItem.count++;
                            itemAlreadyInInventory = true;
                            break;
                        }
                    }
                    // klo item belum ada, tambahin ke inventory
                    if (!itemAlreadyInInventory) {
                       inventory.getInventory().add(new Inventory.InventoryItem(currentMapObjects[i], harvestAmount));
                    }
                    gp.playSE(1);

                    if (harvestAmount == 1) {
                        gp.ui.addMessage("Got a " + itemName + "!");
                    }
                    else {
                        gp.ui.addMessage("Got " + harvestAmount + " " + itemName + "!");
                    }
                    
                    currentMapObjects[i] = null;

                    gp.player.totalCropHarvested += harvestAmount;
                } 
                else {
                    gp.ui.addMessage("You cannot carry any more!"); // ini klo penuh
                }
            }
        }
    }

    public int getHarvestAmount(String cropName) {
        return switch(cropName) {
            case "Parsnip" -> 1;
            case "Cauliflower" -> 1;
            case "Potato" -> 1;
            case "Wheat" -> 3;
            case "Blueberry" -> 3;
            case "Tomato" -> 1;
            case "HotPepper" -> 1;
            case "Melon" -> 1;
            case "Cranberry" -> 10;
            case "Pumpkin" -> 1;
            case "Grape" -> 20;
            case "Eggplant" -> 1; // Tambahan untuk eggplant
            default -> 1; // Default 1 kalau crop tidak dikenal
        };
    }
    
    public void planting(){
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;
            if(!tillWithoutEnergy){
                int targetCol = 0;
                int targetRow = 0;

                switch(direction) {
                    case "up":
                        // Tile di atas area solid pemain
                        targetRow = (worldY + solidArea.y - 1) / gp.tileSize;
                        targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                        break;
                    case "down":
                        // Tile di bawah area solid pemain
                        targetRow = (worldY + solidArea.y + solidArea.height + 1) / gp.tileSize;
                        targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                        break;
                    case "left":
                        // Tile di kiri area solid pemain
                        targetCol = (worldX + solidArea.x - 1) / gp.tileSize;
                        targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                        break;
                    case "right":
                        // Tile di kanan area solid pemain
                        targetCol = (worldX + solidArea.x + solidArea.width + 1) / gp.tileSize;
                        targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                        break;
                }

                if (targetCol >= 0 && targetRow >= 0 && targetCol < gp.tileM.mapCols[gp.currentMap] && targetRow < gp.tileM.mapRows[gp.currentMap] && energy >= 5) {
                    gp.ui.addMessage("CONDITION PASSED - PLANTING NOW!");
                    int tileNumAtTarget = gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow]; // Ambil nomor tile di target
                    gp.ui.addMessage("Current tile at target: " + tileNumAtTarget);
                    if (gp.tileM.tile[tileNumAtTarget].tileType == TileType.TILLED) { // Periksa tipe tile tersebut
                        gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow] = 9; // 9 = TilledSoil
                        gp.ui.addMessage(""+plantingSeedItem);
                        if (plantingSeedItem != null){
                            Entity seed = plantingSeedItem.item.copy();
                            gp.ui.addMessage(seed.name);

                            SoilTile soilTile = gp.tileM.soilMap[gp.currentMap][targetCol][targetRow];
                            if (soilTile == null) {
                                gp.ui.addMessage("Creating new SoilTile");
                                soilTile = new SoilTile();
                                gp.tileM.soilMap[gp.currentMap][targetCol][targetRow] = soilTile;
                            }
                            soilTile.isTilled = true;
                            soilTile.isSeedPlanted = true;
                            soilTile.seedType = plantingSeedItem.item.name;
                            soilTile.plantedDay = gp.eManager.getDayCount();
                            System.out.println("cek");
                            gp.ui.addMessage("Seed planted at: (" + targetCol + "," + targetRow + ")");
                            gp.ui.addMessage("Seed type: " + soilTile.seedType);
                            gp.ui.addMessage("Planted day: " + soilTile.plantedDay);
                            gp.ui.addMessage("Current day: " + gp.eManager.getDayCount());
                            gp.ui.addMessage("Planted SoilTile" + soilTile);

                            //plantingSeedItem = null;
                        } else {
                            gp.ui.addMessage("No seed selected to plant!"); 
                        }
                        // Anda mungkin ingin mengurangi energi pemain di sini atau memainkan suara
                        // gp.playSE(indeksSuaraCangkul);
                    }
                }
            }
        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            tillWithoutEnergy = false; 
            planting = false;
            for (int c = 0; c < gp.tileM.mapCols[gp.currentMap]; c++) {
                for (int r = 0; r < gp.tileM.mapRows[gp.currentMap]; r++) {
                    SoilTile t = gp.tileM.soilMap[gp.currentMap][c][r];
                    if (t != null && t.isSeedPlanted) {
                     System.out.println("DEBUG AFTER PLANTING: (" + c + "," + r + ") = " + t.seedType + ", day=" + t.plantedDay);
                    }
                }
             }
        }
    }
    

    public void tilling(){
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;
            if(!tillWithoutEnergy){
                // Tentukan tile yang akan diolah berdasarkan arah pemain
                int targetCol = 0;
                int targetRow = 0;

                switch(direction) {
                    case "up":
                        // Tile di atas area solid pemain
                        targetRow = (worldY + solidArea.y - 1) / gp.tileSize;
                        targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                        break;
                    case "down":
                        // Tile di bawah area solid pemain
                        targetRow = (worldY + solidArea.y + solidArea.height + 1) / gp.tileSize;
                        targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                        break;
                    case "left":
                        // Tile di kiri area solid pemain
                        targetCol = (worldX + solidArea.x - 1) / gp.tileSize;
                        targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                        break;
                    case "right":
                        // Tile di kanan area solid pemain
                        targetCol = (worldX + solidArea.x + solidArea.width + 1) / gp.tileSize;
                        targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                        break;
                }
                if (targetCol >= 0 && targetRow >= 0 && targetCol < gp.tileM.mapCols[gp.currentMap] && targetRow < gp.tileM.mapRows[gp.currentMap]) {
                    int tileNumAtTarget = gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow]; // Ambil nomor tile di target
                    if (gp.tileM.tile[tileNumAtTarget].tileType == TileType.TILLABLE) { // Periksa tipe tile tersebut
                        gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow] = 8; // 8 = HoedSoil
                        // Anda mungkin ingin mengurangi energi pemain di sini atau memainkan suara
                        // gp.playSE(indeksSuaraCangkul);
                    }
                }
            }
        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            tillWithoutEnergy = false; 
            tilling = false;
        }
    }

    public void recoverLand(){
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;
            if(!tillWithoutEnergy){
                // Tentukan tile yang akan diolah berdasarkan arah pemain
                int targetCol = 0;
                int targetRow = 0;

                switch(direction) {
                    case "up":
                        // Tile di atas area solid pemain
                        targetRow = (worldY + solidArea.y - 1) / gp.tileSize;
                        targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                        break;
                    case "down":
                        // Tile di bawah area solid pemain
                        targetRow = (worldY + solidArea.y + solidArea.height + 1) / gp.tileSize;
                        targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                        break;
                    case "left":
                        // Tile di kiri area solid pemain
                        targetCol = (worldX + solidArea.x - 1) / gp.tileSize;
                        targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                        break;
                    case "right":
                        // Tile di kanan area solid pemain
                        targetCol = (worldX + solidArea.x + solidArea.width + 1) / gp.tileSize;
                        targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                        break;
                }

                if (targetCol >= 0 && targetRow >= 0 && targetCol < gp.tileM.mapCols[gp.currentMap] && targetRow < gp.tileM.mapRows[gp.currentMap]) {
                    int tileNumAtTarget = gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow]; // Ambil nomor tile di target
                    if (gp.tileM.tile[tileNumAtTarget].tileType == TileType.TILLED) { // Periksa tipe tile tersebut
                        gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow] = 0; // 0 = soil
                        // Anda mungkin ingin mengurangi energi pemain di sini atau memainkan suara
                        // gp.playSE(indeksSuaraRecoverLand);
                    }
                }
            }
        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            tillWithoutEnergy = false;
            recoverLand = false;
        }
    }

    public void watering(){
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;
            if(!tillWithoutEnergy){
                // Tentukan tile yang akan diolah berdasarkan arah pemain
                int targetCol = 0;
                int targetRow = 0;

                switch(direction) {
                    case "up":
                        // Tile di atas area solid pemain
                        targetRow = (worldY + solidArea.y - 1) / gp.tileSize;
                        targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                        break;
                    case "down":
                        // Tile di bawah area solid pemain
                        targetRow = (worldY + solidArea.y + solidArea.height + 1) / gp.tileSize;
                        targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                        break;
                    case "left":
                        // Tile di kiri area solid pemain
                        targetCol = (worldX + solidArea.x - 1) / gp.tileSize;
                        targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                        break;
                    case "right":
                        // Tile di kanan area solid pemain
                        targetCol = (worldX + solidArea.x + solidArea.width + 1) / gp.tileSize;
                        targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;
                        break;
                }

                if (targetCol >= 0 && targetRow >= 0 && targetCol < gp.tileM.mapCols[gp.currentMap] && targetRow < gp.tileM.mapRows[gp.currentMap]) {
                    int tileNumAtTarget = gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow]; // Ambil nomor tile di target
                    if (gp.tileM.tile[tileNumAtTarget].tileType == TileType.PLANTED && tileNumAtTarget != 10) { // Periksa tipe tile tersebut
                        gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow] = 10; // 10 = WateredSoil
                        // Anda mungkin ingin mengurangi energi pemain di sini atau memainkan suara
                        // gp.playSE(indeksSuaraWatering);
                    }
                }
            }
        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            tillWithoutEnergy = false;
            watering = false;
        }
    }

    public void sleeping(){
        gp.ui.setForceBlackScreen(true);

        if(gp instanceof javax.swing.JPanel){
            gp.repaint();
        }
        try {
            Thread.sleep(50); // Delay 50 milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sleepLogic();

        try{
            Thread.sleep(3000); // Delay 1 second to simulate sleep transition
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        gp.ui.setForceBlackScreen(false);
        if (gp.gameState != gp.playState && gp.gameState != gp.dialogueState) {
            gp.gameState = gp.playState;
        } else if (gp.gameState != gp.dialogueState) { 
            gp.gameState = gp.playState;
        }

        gp.ui.addMessage("You wake up feeling refreshed!");
        gp.ui.showingSleepConfirmDialog = false;

        if (gp instanceof javax.swing.JPanel) {
            gp.repaint();
        }
    }

    public void sleepLogic(){
        System.out.println("=== SLEEP LOGIC CALLED ===");
        System.out.println("Before sleep - Day: " + gp.eManager.getDayCount());
        boolean isPassOut = (gp.eManager.getHour() >= 2 && gp.eManager.getHour() < 6) && !gp.ui.showingSleepConfirmDialog;

        if(isPassOut|| energy <= -20){
           if (energy <= -20) {
                gp.ui.addMessage("You collapsed from exhaustion...");
            } else {
                gp.ui.addMessage("Sudah terlalu larut! Kamu pingsan....");
            }
        }
        if (energy < 10) {
            energy += 50;
        }
        else if (energy == 0) {
            energy += 10;
        }
        else {
            energy = 100;
        }

        processShippingBinSales();
        System.out.println("Calling incrementDayAndAdvanceWeather...");
        gp.eManager.incrementDayAndAdvanceWeather();
        gp.eManager.setTime(6,0);
        System.out.println("After sleep - Day: " + gp.eManager.getDayCount());
        if (gp.ui.showingSleepConfirmDialog) {
            gp.ui.closeSleepConfirmationDialog(); 
        }
    }

    public void watching(){
        gp.ui.showingWatchTV = true;
        gp.gameState = gp.dialogueState;
    }

    public void interactNPC(int i){
       if (gp.keyH.enterPressed && i != 999) {
            if (gp.NPC[i] instanceof NPC) {
                NPC currentNPC = (NPC) gp.NPC[i];
                 // DEBUG: Log current state
                System.out.println("=== INTERACTION DEBUG ===");
                System.out.println("Current energy: " + energy);
                System.out.println("NPC: " + currentNPC.name);
                System.out.println("Current dialogueIndex: " + currentNPC.dialogueIndex);
            
                // Check if this is start of new conversation
                boolean isNewConversation = (currentNPC.dialogueIndex == 0);
                System.out.println("Is new conversation: " + isNewConversation);
                // If it's a new conversation, check if player has enough energy
                if (isNewConversation && energy < -10) {
                    gp.ui.addMessage("You're too tired to start a new conversation! (Need 10 energy)");
                    gp.keyH.enterPressed = false;
                    System.out.println("Not enough energy for new conversation!");
                    return; // Exit without starting conversation
                }
                 // SAFETY CHECK: Make sure NPC has dialogue set up
                if (currentNPC.dialogue == null || currentNPC.dialogue.length == 0) {
                    System.out.println("ERROR: " + currentNPC.name + " has no dialogue!");
                    gp.ui.addMessage(currentNPC.name + " has nothing to say...");
                    gp.keyH.enterPressed = false;
                    return;
                }
                // Call chat method
                currentNPC.chat();
                
                // Set dialogue state
                gp.gameState = gp.dialogueState;
                // Only deduct energy and show messages for NEW conversations
                if (isNewConversation) {
                    energy -= 10;  // Only deduct energy for new conversations
                    gp.eManager.addMinutesToTime(10);
                    gp.ui.addMessage("You chatted with " + currentNPC.name + " (+10 heart points, -10 energy)");
                    gp.ui.addMessage("Heart points: " + currentNPC.getHeartPoints() + "/150");
                    gp.ui.addMessage("Energy: " + energy );
                }
            }
        }  
        gp.keyH.enterPressed = false;
        }

    public void draw(Graphics2D g2){
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage imageToDraw = null;
        int playerDrawScreenX = worldX - gp.clampedCameraX;
        int playerDrawScreenY = worldY - gp.clampedCameraY;
        switch (direction){
            case "up":
                if (!tilling && !planting && !watering && !recoverLand) { // Kondisi normal
                    if (spriteNum == 1) imageToDraw = up1;
                    if (spriteNum == 2) imageToDraw = up2;
                } else if (tilling) {
                    if (spriteNum == 1) imageToDraw = up1; // Atau sprite idle sebelum aksi
                    if (spriteNum == 2) imageToDraw = tillingUp; // Sprite aksi
                } else if (planting) {
                    // ... (logika sprite untuk planting)
                    if (spriteNum == 1) imageToDraw = up1;
                    if (spriteNum == 2) {
                        if(equippedItem != null && equippedItem.type == EntityType.SEED){
                            imageToDraw = getSeedImageByName(equippedItem.name);
                        }
                    }
                } else if (watering) {
                    // ... (logika sprite untuk watering)
                    if (spriteNum == 1) imageToDraw = up1;
                    if (spriteNum == 2) imageToDraw = wateringUp;
                } else if (recoverLand) {
                    // ... (logika sprite untuk recoverLand)
                    if (spriteNum == 1) imageToDraw = up1;
                    if (spriteNum == 2) imageToDraw = recoverLandUp;
                }
                break;
            case "down":
                // ... (logika serupa untuk arah "down") ...
                 if (!tilling && !planting && !watering && !recoverLand) {
                    if (spriteNum == 1) imageToDraw = down1;
                    if (spriteNum == 2) imageToDraw = down2;
                } else if (tilling) {
                    if (spriteNum == 1) imageToDraw = down1;
                    if (spriteNum == 2) imageToDraw = tillingDown;
                } else if (planting) {
                    if (spriteNum == 1) imageToDraw = down1;
                    if (spriteNum == 2){
                        if(equippedItem != null && equippedItem.type == EntityType.SEED){
                            imageToDraw = getSeedImageByName(equippedItem.name);
                        }
                    }
                } else if (watering) {
                    if (spriteNum == 1) imageToDraw = down1;
                    if (spriteNum == 2) imageToDraw = wateringDown;
                } else if (recoverLand) {
                    if (spriteNum == 1) imageToDraw = down1;
                    if (spriteNum == 2) imageToDraw = recoverLandDown;
                }
                break;
            case "left":
                // ... (logika serupa untuk arah "left") ...
                 if (!tilling && !planting && !watering && !recoverLand) {
                    if (spriteNum == 1) imageToDraw = left1;
                    if (spriteNum == 2) imageToDraw = left2;
                } else if (tilling) {
                    if (spriteNum == 1) imageToDraw = left1;
                    if (spriteNum == 2) imageToDraw = tillingLeft;
                } else if (planting) {
                    if (spriteNum == 1) imageToDraw = left1;
                    if (spriteNum == 2){
                        if(equippedItem != null && equippedItem.type == EntityType.SEED){
                            imageToDraw = getSeedImageByName(equippedItem.name);
                        }
                    }
                } else if (watering) {
                    if (spriteNum == 1) imageToDraw = left1;
                    if (spriteNum == 2) imageToDraw = wateringLeft;
                } else if (recoverLand) {
                    if (spriteNum == 1) imageToDraw = left1;
                    if (spriteNum == 2) imageToDraw = recoverLandLeft;
                }
                break;
            case "right":
                // ... (logika serupa untuk arah "right") ...
                 if (!tilling && !planting && !watering && !recoverLand) {
                    if (spriteNum == 1) imageToDraw = right1;
                    if (spriteNum == 2) imageToDraw = right2;
                } else if (tilling) {
                    if (spriteNum == 1) imageToDraw = right1;
                    if (spriteNum == 2) imageToDraw = tillingRight;
                } else if (planting) {
                     if (spriteNum == 1) imageToDraw = right1;
                    if (spriteNum == 2){
                        if(equippedItem != null && equippedItem.type == EntityType.SEED){
                            imageToDraw = getSeedImageByName(equippedItem.name);
                        }
                    }
                } else if (watering) {
                    if (spriteNum == 1) imageToDraw = right1;
                    if (spriteNum == 2) imageToDraw = wateringRight;
                } else if (recoverLand) {
                    if (spriteNum == 1) imageToDraw = right1;
                    if (spriteNum == 2) imageToDraw = recoverLandRight;
                }
                break;
        }
        
        // Jika tidak ada aksi khusus, dan tidak bergerak, kembali ke sprite berdiri normal
        if (imageToDraw == null && !moving && !tilling && !planting && !watering && !recoverLand) {
            switch(direction) {
                case "up": imageToDraw = up1; break;
                case "down": imageToDraw = down1; break;
                case "left": imageToDraw = left1; break;
                case "right": imageToDraw = right1; break;
                default: imageToDraw = down1; // Fallback
            }
        } else if (imageToDraw == null) { // Fallback jika belum ada gambar terpilih
             switch(direction) { // Fallback ke animasi jalan normal jika aksi belum punya sprite spesifik
                case "up": imageToDraw = (spriteNum == 1) ? up1 : up2; break;
                case "down": imageToDraw = (spriteNum == 1) ? down1 : down2; break;
                case "left": imageToDraw = (spriteNum == 1) ? left1 : left2; break;
                case "right": imageToDraw = (spriteNum == 1) ? right1 : right2; break;
                default: imageToDraw = down1;
            }
        }


        // Gambar pemain pada posisi screenX, screenY yang sudah ditentukan (final)
        // Menggunakan gp.tileSize untuk lebar dan tinggi agar konsisten
        if (imageToDraw != null) {
            g2.drawImage(imageToDraw, playerDrawScreenX, playerDrawScreenY, gp.tileSize, gp.tileSize, null);
        } else {
            // Jika imageToDraw masih null setelah semua logika, gambar fallback atau log error
            // Contoh: g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize); // Gambar kotak merah
            System.err.println("PLAYER DRAW: imageToDraw is null for direction " + direction + " and state.");
            if (down1 != null) g2.drawImage(down1, screenX, screenY, gp.tileSize, gp.tileSize, null); // Fallback paling aman
        }

        // Opsional: Gambar solidArea untuk debug (gunakan screenX, screenY dari Player)
        // g2.setColor(java.awt.Color.BLUE);
        // g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }

    private BufferedImage getSeedImageByName(String name){
        switch (name){
            case "Parsnip Seeds": return parsnipSeeds;
            case "Cauliflower Seeds": return cauliflowerSeeds;
            case "Potato Seeds": return potatoSeeds;
            case "Wheat Seeds": return wheatSeeds;
            case "Blueberry Seeds": return blueberrySeeds;
            case "Tomato Seeds": return tomatoSeeds;
            case "HotPepper Seeds": return hotPepperSeeds;
            case "Melon Seeds": return melonSeeds;
            case "Cranberry Seeds": return cranberrySeeds;
            case "Pumpkin Seeds": return pumpkinSeeds;
            case "Grape Seeds": return grapeSeeds;
            case "Eggplant Seeds": return eggplantSeeds;
        }
        return parsnipSeeds;
    }
    public boolean isInRelationship() {
        return partner != null;
    }
    public String getRelationshipStatus() {
        if (partner == null) {
            return "SINGLE";
        }
        if (partner instanceof NPC) {
            NPC npcPartner = (NPC) partner;
            return npcPartner.getRelationshipStatus().toString();
        }
        return "UNKNOWN";
    }

    public void proposeToNPC(int npcIndex){
        if (npcIndex != 999 && gp.NPC[npcIndex] instanceof NPC) {
            NPC currentNPC = (NPC) gp.NPC[npcIndex];
            if (isInRelationship()) {
                if (partner == currentNPC) {
                    // Trying to propose to current partner
                    if (getRelationshipStatus().equals("FIANCE")) {
                        gp.ui.addMessage("You're already engaged to " + currentNPC.name + "!");
                        gp.ui.addMessage("Time to plan the wedding! ");
                    } else if (getRelationshipStatus().equals("SPOUSE")) {
                        gp.ui.addMessage("You're already married to " + currentNPC.name + "! ");
                        gp.ui.addMessage("What a loving couple you are! ");
                    }
                } else {
                    // Trying to propose to someone else - CHEATING!
                    gp.ui.addMessage("Whoa! You're already in a relationship with " + partner.name + "!");
                    gp.ui.addMessage("jan selingkuh");
                }
                return; // Exit without proposing
            }
            if (hasProposalRing() && energy>=0) {// kalo ditolak masalahnya energinya 20, ntar ngutang kaga mungkin kan
                boolean accepted = currentNPC.propose();
                if (accepted) {
                    // LAMARAN DITERIMA
                    energy -= 10; // -10 energi sesuai spesifikasi
                    partner = currentNPC; // Set partner player
                    gp.eManager.addMinutesToTime(60);
                    gp.ui.addMessage("Congratulations! You are now engaged to " + currentNPC.name + "!");
                    gp.ui.addMessage("Energy: -10");
                    } else {
                    // LAMARAN DITOLAK
                    energy -= 20; // -20 energi sesuai spesifikasi
                    gp.eManager.addMinutesToTime(60);
                    gp.ui.addMessage("Your proposal was rejected...");
                    gp.ui.addMessage("Energy: -20");
                    
                    if (currentNPC.getHeartPoints() < 150) {
                        gp.ui.addMessage("You need " + (150 - currentNPC.getHeartPoints()) + " more heart points.");
                    }
                }
            }
        }
    }
    public void marryNPC(int npcIndex) {
        if (npcIndex != 999 && gp.NPC[npcIndex] instanceof NPC) {
            NPC currentNPC = (NPC) gp.NPC[npcIndex];
             // CHECK: Already married to someone?
            if (isInRelationship() && getRelationshipStatus().equals("SPOUSE")) {
                if (partner == currentNPC) {
                    // Trying to marry current spouse again
                    gp.ui.addMessage("You're already married to " + currentNPC.name + "! ");
                    gp.ui.addMessage("What a sweet reminder of your wedding day! ");
                    gp.ui.addMessage("Maybe bring flowers next time instead? ");
                } else {
                    // Trying to marry someone else - BIGAMY!
                    gp.ui.addMessage("Hold up! You're already married to " + partner.name + "!");
                    gp.ui.addMessage("Bigamy is not allowed in Spakbor Hills! ");
                    gp.ui.addMessage("You can only have one spouse at a time!");
                }
                return; // Exit without marrying
            } 
            if (isInRelationship() && partner != currentNPC) {
                gp.ui.addMessage("You're engaged to " + partner.name + ", not " + currentNPC.name + "!");
                gp.ui.addMessage("Marry your fiance first, or break up and start over!");
                return;
            }
            if (hasProposalRing() && currentNPC.getRelationshipStatus() == NPC.RelationshipStatus.FIANCE && energy>=60 && currentNPC.canMarryToday()) {
                boolean married = currentNPC.marry();
                
                if (married) {
                    energy -= 80; // -80 energi sesuai spesifikasi
                    partner = currentNPC;
                    hasMarried = true;
                    
                    gp.ui.addMessage("You married " + currentNPC.name + "! Congratulations!");
                    gp.ui.addMessage(" You spend the entire day together as newlyweds...");
                
                    // CALL WEDDING DAY METHOD (with black screen transition)
                    weddingDay();
        
                    }
            } else if (!hasProposalRing()) {
                gp.ui.addMessage("You need a Proposal Ring to marry!");
            } else if (currentNPC.getRelationshipStatus() != NPC.RelationshipStatus.FIANCE) {
                gp.ui.addMessage("You can only marry your fiance! Propose first.");
            }else if (energy<80){
                 gp.ui.addMessage("You're too tired to gift me! (Need 80 energy)");
            }else if (!currentNPC.canMarryToday()) {
                    gp.ui.addMessage("You need to wait until tomorrow to marry " + currentNPC.name + "!");
                    gp.ui.addMessage("Come back on a different day.");
            }
        }
    }

    public void weddingDay(){
        // FORCE BLACK SCREEN ON
        gp.ui.setForceBlackScreen(true);
        if(gp instanceof javax.swing.JPanel){
            gp.repaint();
        }
        
        try {
            Thread.sleep(1500); // Brief delay to show black screen
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        weddingLogic();
        // TURN OFF BLACK SCREEN
        gp.ui.setForceBlackScreen(false);
        
        // Return to play state
        if (gp.gameState != gp.playState && gp.gameState != gp.dialogueState) {
            gp.gameState = gp.playState;
        } else if (gp.gameState != gp.dialogueState) { 
            gp.gameState = gp.playState;
        }

        // WEDDING COMPLETION MESSAGES
        gp.ui.addMessage(" After a perfect wedding day, you return home in the evening.");
        gp.ui.addMessage(" What a wonderful day spent with your new spouse!");

        // Force repaint to show normal screen
        if (gp instanceof javax.swing.JPanel) {
            gp.repaint();
        }
    }
    public void weddingLogic(){
        gp.ui.addMessage("Having a magical wedding day together...");
        
        // TELEPORT PLAYER TO HOUSE (NPC stays where they are)
        gp.currentMap = 2; // House map
        gp.player.worldX = gp.tileSize * 11; // Center of house
        gp.player.worldY = gp.tileSize * 12;
        
        // Refresh house map objects and NPCs
        gp.aSetter.setObject();
        gp.aSetter.setNPC();
        // TIME SKIP TO 22:00 (10 PM)
        gp.eManager.setTime(22, 0);
        
    }
    public void giftToNPC(int npcIndex) {
        if (npcIndex != 999 && gp.NPC[npcIndex] instanceof NPC) {
            NPC currentNPC = (NPC) gp.NPC[npcIndex];
            if (inventory.getInventory().isEmpty()) {
                gp.ui.addMessage("You have no items to give!");
                return;
            }
            if (energy<-15){
                gp.ui.addMessage("You're too tired to gift me! (Need 5 energy)");
                return;
            }
            // Get the equipped item, or first item if nothing equipped
            String itemToGive = null;
            if (equippedItem != null) {
                itemToGive = equippedItem.name;
            } else {
                gp.ui.addMessage("You don't pick any item to gift");
                return;
            }
            if (itemToGive.equals("Ring")) {
                gp.ui.addMessage("You can't give away your Proposal Ring!");
                gp.ui.addMessage("Choose a different item to gift.");
                return; 
            }
            boolean hasItem = false;
            for (Inventory.InventoryItem invItem : inventory.getInventory()) {
                if (invItem.item.name.equals(itemToGive) && invItem.count > 0) {
                    hasItem = true;
                    break;
                }
            }
            if (hasItem) {
                // Remove item from inventory first
                boolean removed = removeItemFromInventory(itemToGive);
                if (removed) {
                    currentNPC.giveGift(itemToGive);
                    energy -= 5;
                    gp.eManager.addMinutesToTime(10);
                    // Show feedback
                    gp.ui.addMessage("You gave " + itemToGive + " to " + currentNPC.name + " (-5 energy)");
                    gp.ui.addMessage("Heart points: " + currentNPC.getHeartPoints() + "/150");
                    return;
                } else {
                    gp.ui.addMessage("Failed to give item!");
                    return;
                }
            } else {
                gp.ui.addMessage("You don't have " + itemToGive + " to give!");
            }
        }
    }
    // Helper method untuk cek Proposal Ring
    public boolean hasProposalRing() {
        for (Inventory.InventoryItem invItem : inventory.getInventory()) {
            if (invItem.item.name.equals("Ring")) {
                return true; // FOUND! Tapi JANGAN di-remove (reusable)
            }
        }
        return false; // Not found
    }

    private boolean removeItemFromInventory(String itemName) {
        // Akses inventory melalui player.inventory.getInventory()
        for (int i = 0; i < inventory.getInventory().size(); i++) {
            Inventory.InventoryItem invItem = inventory.getInventory().get(i);
            if (invItem.item.name.equals(itemName)) {
                if (invItem.count > 1) {
                    invItem.count--;
                } else {
                    inventory.getInventory().remove(i);
                }
                return true;
            }
        }
        return false;
    }

    // Method untuk shipping bin
    public void openShippingBin() {
        if (energy < -15) {
            gp.ui.addMessage("Not enough energy to use shipping bin!");
            return;
        }
        
        gp.gameState = gp.shippingBinState; 
        gp.ui.showShippingBinInterface(); 
    }

    public boolean addToShippingBin(String itemName, int quantity) {
        // Cari item definition berdasarkan nama
        ItemDefinition itemDef = gp.itemManager.getDefinitionByName(itemName);
        if (itemDef == null || !itemDef.isSellable) {
            gp.ui.addMessage("This item cannot be sold!");
            return false;
        }
        
        // Cek slot shipping bin
        if (shippingBinItems.size() >= maxShippingSlots) {
            gp.ui.addMessage("Shipping bin is full!");
            return false;
        }
        
        // Cek player punya item itu atau engga
        Inventory.InventoryItem playerItem = null;
        for (Inventory.InventoryItem invItem : inventory.getInventory()) {
            if (invItem.item.name.equals(itemName)) {
                playerItem = invItem;
                break;
            }
        }
        
        if (playerItem == null || playerItem.count < quantity) {
            gp.ui.addMessage("You don't have enough " + itemName + "!");
            return false;
        }
        
        // Cek ada ga itemnya di shipping bin
        ShippingBinItem existingItem = null;
        for (ShippingBinItem binItem : shippingBinItems) {
            if (binItem.itemName.equals(itemName)) {
                existingItem = binItem;
                break;
            }
        }
        
        if (existingItem != null) {
            existingItem.quantity += quantity;
        }
        else {
            shippingBinItems.add(new ShippingBinItem(itemDef.id,itemName,quantity, itemDef.sellPrice));
        }
        
        // Remove dari inventory
        playerItem.count -= quantity;
        if (playerItem.count <= 0) {
            inventory.getInventory().remove(playerItem);
        }
        
        energy -= 5;
        gp.ui.addMessage("Added " + quantity + " " + itemName + " to shipping bin!");
        return true;
    }


    public void processShippingBinSales() {
        if (shippingBinItems.isEmpty()) {
            return;
        }
        
        int totalEarnings = 0;
        StringBuilder salesReport = new StringBuilder("Items sold:\n");
        
        for (ShippingBinItem item : shippingBinItems) {
            int earnings = item.quantity * item.sellPrice;
            totalEarnings += earnings;
            salesReport.append("- ").append(item.quantity).append(" ")
                    .append(item.itemName).append(" (")
                    .append(earnings).append("g)\n");
        }
        
        gold += totalEarnings;
        totalIncome += totalEarnings;
        totalIncomePerSeason += totalEarnings;
        seasonalIncomeCount++;
        shippingBinItems.clear();
        
        salesReport.append("Total earned: ").append(totalEarnings).append("g");
        gp.ui.addMessage(salesReport.toString());
    }

    public List<ShippingBinItem> getShippingBinItems() {
        return new ArrayList<>(shippingBinItems);
    }

    public List<Inventory.InventoryItem> getSellableItems() {
        List<Inventory.InventoryItem> sellableItems = new ArrayList<>();
        
        for (Inventory.InventoryItem invItem : inventory.getInventory()) {
            // Cari item definition berdasarkan nama
            ItemDefinition itemDef = findItemDefinitionByName(invItem.item.name);
            if (itemDef != null && itemDef.isSellable) {
                sellableItems.add(invItem);
            }
        }
        
        return sellableItems;
    }

    private ItemDefinition findItemDefinitionByName(String itemName) {
        return gp.itemManager.getDefinitionByName(itemName);
    }
    public void eating() {
        if (equippedItem == null) {
            gp.ui.addMessage("No item selected to eat!");
            return;
        }
        if (equippedItem.isEdible) {
            this.energy += equippedItem.plusEnergy;
            if (this.energy > 100) this.energy = 100;

            gp.ui.addMessage("Ate " + equippedItem.name + " and restored " + equippedItem.plusEnergy + " energy!");

            removeItemFromInventory(equippedItem.name);
            unEquipItem();

        } else {
            gp.ui.addMessage("Its not edible!");
        }
    }

    public void startFishing(){
        if (energy < -15){
            return;
        } 
        if (!isPlayerFacingWater()){
            gp.ui.addMessage("You must be facing water to fish!");
            return;
        }

        energy -= 5;
        gp.eManager.addMinutesToTime(15);
    

        this.fishBeingFishedProto = selectFishToCatch();

        if (this.fishBeingFishedProto == null){
            gp.ui.addMessage("Nothing seems to be biting...");
            return;
        }

        if (!(this.fishBeingFishedProto instanceof FishableProperties)){
            this.fishBeingFishedProto = null;
            return;
        }

        FishableProperties fishProps = (FishableProperties) this.fishBeingFishedProto;
        String category = fishProps.getFishCategory();
        int rangeMax = 0 ;

        switch (category.toLowerCase()){
            case "common":
                rangeMax = 10;
                this.maxFishingTries = 10;
                break;
            case "regular":
                rangeMax = 100;
                this.maxFishingTries = 10;
                break;
            case "legendary":
                rangeMax = 500;
                this.maxFishingTries = 7;
                break;
            default:
                this.fishBeingFishedProto = null;
                return;
        }

        this.fishingRangeString = "1-" + rangeMax;
        this.numberToGuess = randomGenerator.nextInt(rangeMax) + 1; 
        this.currentFishingTry = 0;
        this.inFishingMinigame = true;

        gp.gameState = gp.fishingMinigameState;


    }

    public void attempToCatchFish(){
        Location playerLocation = getCurrentPlayerLocationEnum();
        Season currentSeason = gp.eManager.getCurrentSeason();
        Weather currentWeather = gp.eManager.getCurrentWeather();
        int currentHour = gp.eManager.getHour();

        List<Entity> potentialCatches = new ArrayList<>();

        if (playerLocation != null){
            for (Entity fishProto : gp.getAllFishPrototypes()){
                if (fishProto instanceof FishableProperties){
                    FishableProperties fishP = (FishableProperties) fishProto;
                    if (canCatchThisFish(fishP, playerLocation, currentSeason, currentWeather, currentHour)){
                        potentialCatches.add(fishProto);
                    }
                }
            }
        }

        if (potentialCatches.isEmpty() && (playerLocation == Location.MOUNTAIN_LAKE || playerLocation == Location.FARM || gp.currentMap == 0 || gp.currentMap == 11)){
            Location pondLocation = Location.POND;
            for (Entity fishProto : gp.getAllFishPrototypes()){
                if (fishProto instanceof FishableProperties){
                    FishableProperties fishP = (FishableProperties) fishProto;
                    if (canCatchThisFish(fishP, pondLocation, currentSeason, currentWeather, currentHour)){
                        potentialCatches.add(fishProto);
                    }
                }
            }
        }

        if (potentialCatches.isEmpty()){
            gp.ui.addMessage("Nothing seems to be biting...");
        } else {
            Entity caughtFishPrototype = potentialCatches.get(randomGenerator.nextInt(potentialCatches.size()));

            try {
                Entity newCatch = caughtFishPrototype.getClass().getDeclaredConstructor(GamePanel.class).newInstance(gp);

                if (newCatch instanceof FishableProperties){
                    newCatch.sellPrice = calculationFishSellPrice(newCatch);
                    newCatch.price = newCatch.sellPrice;


                    String originalDescription = (newCatch.description != null && !newCatch.description.startsWith("[" + newCatch.name + "]")) ? newCatch.description : ((FishableProperties) newCatch).getFishCategory() + " fish.";
                    newCatch.description = "[" + newCatch.name + "]\n" + originalDescription + "\nSell Price: " + newCatch.sellPrice + "g";
                    newCatch.value = 1;
                    
                    this.inventory.addItem(newCatch);
                }
            } catch (Exception e) {
                e.printStackTrace();
                gp.ui.addMessage("Failed to catch fish! Please try again.");
            }
        }
    }

    public void processPlayerGuess(int guessNumber){
        if (!inFishingMinigame || gp.gameState != gp.fishingMinigameState){
            System.err.println("Player.processPlayerGuess called when not in fishing minigame!");
            return;
        }

        this.currentFishingTry++;
        String fishName = (this.fishBeingFishedProto != null) ? this.fishBeingFishedProto.name : "fish";

        if (guessNumber == this.numberToGuess){
            gp.ui.addMessage("So Cool! You caught a " + fishName + "!");
            addCaughtFishToInventory();
            endFishingMinigame(true);
            this.fishingGuessHint = "";
        } else {
            if(this.currentFishingTry >= this.maxFishingTries){
                gp.ui.addMessage("Out of Tries!");
                
                gp.ui.addMessage("The correct number was " + this.numberToGuess + ".");
                endFishingMinigame(false);
                this.fishingGuessHint = "";
            } else {
                this.fishingGuessHint = (guessNumber < this.numberToGuess) ? "higher" : "lower";
                

            }
        }
        }
    
        private void addCaughtFishToInventory(){
            if (this.fishBeingFishedProto == null) {
                return;
            }
            try {
                Entity newCatch = this.fishBeingFishedProto.getClass().getDeclaredConstructor(GamePanel.class).newInstance(gp);
                if (newCatch instanceof FishableProperties) {
                    newCatch.sellPrice = calculationFishSellPrice(newCatch);
                    newCatch.price = newCatch.sellPrice;

                   String originalDescription = (newCatch.description != null && !newCatch.description.startsWith("[" + newCatch.name + "]")) ? newCatch.description : ((FishableProperties)newCatch).getFishCategory() + " fish.";
                    newCatch.description = "[" + newCatch.name + "]\n" + originalDescription + "\nSell Price: " + newCatch.sellPrice + "g";
                    newCatch.value = 1;

                    this.inventory.addItem(newCatch);
                    fishCaught.add((FishableProperties)newCatch);
                }
            } catch (Exception e) {
                e.printStackTrace();
                gp.ui.addMessage("Failed to add caught fish to inventory!");
            }
        }

        public void endFishingMinigame(boolean success){
            this.inFishingMinigame = false;
            this.fishBeingFishedProto = null;
            this.fishingRangeString = "N/A";
            this.fishingGuessHint = "";

            gp.gameState = gp.playState;
            gp.fishingInputBuffer = "";

    
        }

        public Location getCurrentPlayerLocationEnum(){
            if (gp == null){
                return null;
            }

            switch(gp.currentMap){
                case 0 : return Location.FARM;
                case 1 : return Location.OCEAN;
                case 2 : return Location.HOUSE;
                case 3 : return Location.FOREST_RIVER;
                case 4 : return Location.NPCMAP;
                case 5 : return Location.EMILY_MAP;
                case 6 : return Location.PERRY_MAP;
                case 7 : return Location.DASCO_MAP;
                case 8 : return Location.ABIGAIL_MAP;
                case 9 : return Location.MAYOR_MAP;
                case 10 : return Location.CAROLINE_MAP;
                case 11 : return Location.MOUNTAIN_LAKE;
                default : 
                return null;
            }
        }

        private Entity selectFishToCatch(){
            Location mapLocation = getCurrentPlayerLocationEnum();
            Season currSeason = gp.eManager.getCurrentSeason();
            Weather curWeather = gp.eManager.getCurrentWeather();
            int currentHour = gp.eManager.getHour();

            System.out.println("selectFishToCatch - Location: " + mapLocation +
                       ", Season: " + currSeason +
                       ", Weather: " + curWeather +
                       ", Hour: " + currentHour);

            List<Entity> potentialCatches = new ArrayList<>();

            if (mapLocation != null){
                for (Entity fishProto : gp.getAllFishPrototypes()){
                    if (fishProto instanceof FishableProperties){
                        FishableProperties fishP = (FishableProperties) fishProto;
                        if (canCatchThisFish(fishP, mapLocation, currSeason, curWeather, currentHour)){
                            potentialCatches.add(fishProto);
                        }
                    }
                }
            } else {
                System.err.println("Player Location is null");
                return null; // Lokasi tidak valid, tidak ada ikan yang bisa ditangkap
            }


            if (mapLocation == Location.FARM ){
                Location pondLocation = Location.POND;
                for (Entity fishProto : gp.getAllFishPrototypes()){
                    if (fishProto instanceof FishableProperties){
                        FishableProperties fishP = (FishableProperties) fishProto;
                        if (fishP.getAvailableLocations() != null && fishP.getAvailableLocations().contains(pondLocation) && !potentialCatches.contains(fishProto)) {
                            if (canCatchThisFish(fishP, pondLocation, currSeason, curWeather, currentHour)) {
                                potentialCatches.add(fishProto);
                            }
                        }
                    }
                }
            }

            if (potentialCatches.isEmpty()){
                return null;
            } else {
                return potentialCatches.get(randomGenerator.nextInt(potentialCatches.size()));
            }
        }


        /**
         * @param fish FishableProperties dari ikan yang dicek
         * @param pLoc Lokasi memancing (enum Location)
         * @param pSeason Musim saat ini (enum Season)
         * @param pWeather Cuaca saat ini (enum Weather)
         * @param pHour Jam saat ini (0-23)
         * @return true jika ikan bisa ditangkap, false jika tidak
         */

         private boolean canCatchThisFish(FishableProperties fish, Location pLoc, Season pSeason, Weather pWeather, int pHour) {
            EnumSet<Location> fishLocations = fish.getAvailableLocations();

            if (!(fishLocations.contains(pLoc))){
                return false;
            }

            if (fishLocations == null || fishLocations.isEmpty()){
                return false;
            }

            EnumSet<Season> fishSeasons = fish.getAvailableSeasons();

            if (!(fishSeasons.contains(pSeason))){
                return false;
            }

            if (fishSeasons == null || fishSeasons.isEmpty()){
                return false;
            }

            EnumSet<Weather> fishWeathers = fish.getAvailableWeathers();
            if (!(fishWeathers.contains(pWeather))){
                return false;
            }

            if (fishWeathers == null || fishWeathers.isEmpty()){
                return false;
            }

            List<Integer> startTimes = fish.getAvailableStartTimes();
            List<Integer> endTimes = fish.getAvailableEndTimes();
            boolean timeMatches = false;

            if (startTimes == null || startTimes.isEmpty()){
                timeMatches = true; // Tidak ada batas waktu, selalu cocok
            } else {
                for (int i = 0; i < startTimes.size(); i++) {
                    int start = startTimes.get(i);
                    int end = endTimes.get(i);

                    if (end < start) {
                        if (pHour >= start || pHour < end){
                            timeMatches = true; // Jam saat ini cocok dengan jam yang diberikan
                            break;
                        }
                    } else {
                        if (pHour >= start && pHour < end){
                            timeMatches = true; // Jam saat ini cocok dengan jam yang diberikan
                            break;
                        }
                    }
                }
            }
            if (!timeMatches) {
                return false; // Jam saat ini tidak cocok
            }

            return true;
         }

         public boolean isPlayerFacingWater(){
            int targetCol = worldX / gp.tileSize;
            int targetRow = worldY / gp.tileSize;

            int playerCenterX = worldX + solidArea.x + solidArea.width / 2;
            int playerCenterY = worldY + solidArea.y + solidArea.height / 2;

            switch(direction) {
                case "up":    targetRow = (playerCenterY - gp.tileSize) / gp.tileSize; targetCol = playerCenterX / gp.tileSize; break;
                case "down":  targetRow = (playerCenterY + gp.tileSize) / gp.tileSize; targetCol = playerCenterX / gp.tileSize; break;
                case "left":  targetCol = (playerCenterX - gp.tileSize) / gp.tileSize; targetRow = playerCenterY / gp.tileSize; break;
                case "right": targetCol = (playerCenterX + gp.tileSize) / gp.tileSize; targetRow = playerCenterY / gp.tileSize; break;
                default: return false; // Arah tidak valid
            }

            if (targetCol < 0 || targetCol >= gp.tileM.mapCols[gp.currentMap] || targetRow < 0 || targetRow >= gp.tileM.mapRows[gp.currentMap]) {
                return false; // Di luar batas peta
            }

            try {
                int tileNum = gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow];
                if (tileNum >= 0 && tileNum < gp.tileM.tile.length && gp.tileM.tile[tileNum] != null) {
                    return gp.tileM.tile[tileNum].tileType == TileType.WATER; // Cek apakah tile adalah air
                } 
            } catch (ArrayIndexOutOfBoundsException e){
                return false;
            }
            return false;
         }

         public int calculationFishSellPrice(Entity fishEntity){
            if (!(fishEntity instanceof FishableProperties)) {
                return fishEntity.price > 0 ? fishEntity.price : (fishEntity.sellPrice > 0 ? fishEntity.sellPrice : 0) ; // Tidak bisa dihitung, bukan ikan
            }

            FishableProperties fish = (FishableProperties) fishEntity;

            double cValue;
            switch(fish.getFishCategory().toLowerCase()) {
                case "common":
                    cValue = 10.0;
                    break;
                case "regular":
                    cValue = 5.0;
                    break;
                case "legendary":
                    cValue = 25.0;
                    break;
                default:
                    cValue = 1.0; // Default jika tidak dikenali
            }

            double numSeason = fish.getNumSeasonsFactor();
            double numHours = fish.getNumHoursFactor();
            double numWeather = fish.getNumWeatherFactor();
            double numLocation = fish.getNumLocationsFactor();

            if (numHours <= 0){
                numHours = 24;
            }

            if (numSeason <= 0){
                numSeason = 4; 
            }

            if (numWeather <= 0){
                numWeather = 2; 
            }

            if (numLocation <= 0){
                numLocation = 1; 
            }

            double price = (4.0 / numSeason) * (24.0 / numHours) * (2.0 / numWeather) * (4.0 / numLocation) * cValue;
            return (int) Math.round(price);
        }

        public Map<String, Integer> getFishCountByCategory() {
            Map<String, Integer> fishCount = new HashMap<>();
            for (FishableProperties fish : fishCaught) {
                String category = fish.getFishCategory().toLowerCase();
                fishCount.put(category, fishCount.getOrDefault(category, 0) + 1);
            }
            return fishCount;
        }

        public void endGameTrigger() {
            gp.ui.addMessage("We're in end game now! 🎉");
            // gp.sleep(1000);
            endGameCount++;
            gp.gameState = gp.endGameTriggerState;

        }

        public EndGameStats<List<Integer>, List<Double>, List<NPC>, Integer, List<FishableProperties>, Integer> endGameDisplay() {
            if (!endGame) {
                gp.ui.addMessage("You haven't reached the end game yet!");
                return null;
            }  
            
            List<Integer> moneyFlows = new ArrayList<>(2);
            moneyFlows.add(totalIncome);
            moneyFlows.add(totalExpenditure);

            List<Double> seasonalMoneyFlows = new ArrayList<>(2);
            seasonalMoneyFlows.add(avgSeasonalIncome);
            seasonalMoneyFlows.add(avgSeasonalExpenditure);

            List<NPC> npcs = new ArrayList<>(GamePanel.getNPCs().values());

            return new EndGameStats<>(moneyFlows, seasonalMoneyFlows, npcs, totalCropHarvested, fishCaught, totalDaysPlayed);
        }

            public void checkStoreInteraction() {
                if (gp.currentMap == 5) { 
                    int playerTileX = (gp.player.worldX + gp.player.solidArea.x + gp.player.solidArea.width/2) / gp.tileSize;
                    int playerTileY = (gp.player.worldY + gp.player.solidArea.y + gp.player.solidArea.height/2) / gp.tileSize;
                    
                    if (Math.abs(playerTileX - 8) <= 1 && Math.abs(playerTileY - 8) <= 1) {
                        if (gp.keyH.enterPressed) {
                            for (int i = 0; i < gp.NPC.length; i++) {
                                if (gp.NPC[i] != null && gp.NPC[i].name.equals("Emily")) {
                                    ((Emily)gp.NPC[i]).interactWithTable();
                                    break;
                                }
                            }
                            gp.keyH.enterPressed = false;
                        }
                    }
                }
            }

        //Testing End Game Stats
        public void cheatMoney(int amount) {
            gold += amount;
            totalIncome += amount; 
            totalIncomePerSeason += amount;
            seasonalIncomeCount++;
            gp.ui.addMessage("Cheat activated: Money increased by " + amount + "g. New gold: " + gold + "g");
        }

        public void forceMarry(String npcName) {
            NPC npc = GamePanel.getOrCreateNPC(npcName, gp);
            if (npc == null) {
                gp.ui.addMessage("Cheat error: NPC " + npcName + " not found.");
                return;
            }
            partner = npc;
            npc.relationshipStatus = NPC.RelationshipStatus.SPOUSE;
            hasMarried = true;
            gp.ui.addMessage("Cheat activated: You are now married to " + npcName + "!");
        }
}

