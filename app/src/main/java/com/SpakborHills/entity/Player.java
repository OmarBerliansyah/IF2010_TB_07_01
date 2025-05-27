package com.SpakborHills.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.SpakborHills.main.GamePanel;
import com.SpakborHills.main.Inventory;
import com.SpakborHills.main.KeyHandler;
import com.SpakborHills.objects.OBJ_Hoe;
import com.SpakborHills.objects.OBJ_ParsnipSeeds;
import com.SpakborHills.objects.OBJ_Pickaxe;
import com.SpakborHills.objects.OBJ_WateringCan;
import com.SpakborHills.tile.TileType;

public class Player extends Entity {
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
    public Entity partner;
    public String location;    
    public Inventory inventory;
    public Entity equippedItem;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();

        solidArea.x = 1;
        solidArea.y = 1;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 46;
        solidArea.height = 46;
        tillingArea.width = 36;
        tillingArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerTillingImage();
        getPlayerSeedsImage();
        inventory = new Inventory(gp, this);
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        energy = 100;//energi awal  & maks
        gold = 0;
        name = "Player";
        farmName = "Spakbor Hills";
        gender = "tes";
        partner = null;
        updateLocation();
 // Initialize partner as null, can be set later
    }

    public void updateLocation() {
        if (gp.currentMap == 0) {
            location = "Farm";
        } else if (gp.currentMap == 1) {
            location = "Ocean";
        } else if (gp.currentMap == 2) {
            location = "House";
        } else {
            location = "Unknown Area";
        }
    }
    public String getCurrentLocation() {
        return location;
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
    }

    public void getPlayerPickAxeImage(){
        recoverLandUp = setup("player/PlayerLeftPickAxe", gp.tileSize, gp.tileSize);
        recoverLandDown = setup("player/PlayerRightPickAxe", gp.tileSize, gp.tileSize);
        recoverLandLeft = setup("player/PlayerLeftPickAxe", gp.tileSize, gp.tileSize);
        recoverLandRight = setup("player/PlayerRightPickAxe", gp.tileSize, gp.tileSize);
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
        if (sleeping){
            sleeping();
            return;
        }
        if (watching){
            watching();
            return;
        }
        if (proposing){
            proposing();
            return;
        }
        if (marry){
            marry();
            return;
        }
        if(moving){
            handleMovement();
        }
        else{
            handleInput();
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

        if(pixelCounter == 48){
            moving = false;
            pixelCounter = 0;
        }
    }

    public void handleInput(){
        boolean movementKeyPressed = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

        if(keyH.useToolPressed && equippedItem != null) {
            useTool();
            keyH.useToolPressed = false; // Reset the useToolPressed flag
            return;
        }

        if(movementKeyPressed || keyH.enterPressed) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }

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

            if(movementKeyPressed) {
                moving = true;
                standCounter = 0; // Reset the stand counter when moving
            }
            gp.keyH.enterPressed = false;
        }
        else{
            standCounter++;
            if(standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
        }
    }

    public void useTool(){
        Entity currentTool = equippedItem;
        if (currentTool instanceof OBJ_Hoe) {
            tilling = true;
            if(this.energy >= 5){
                int col = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                int row = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;

                int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
                if (gp.tileM.tile[tileNum].tileType == TileType.TILLABLE) {
                    tilling = true;
                    energy -= 5; // Misal biaya energi untuk mencangkul adalah 5
                } 
                else {
                    gp.ui.addMessage("Cannot till this tile!");
                }
            } 
            else {
                gp.ui.addMessage("Not enough energy to use the hoe!");
            }
        }
        else if (currentTool instanceof OBJ_ParsnipSeeds) {
            planting = true;
            Inventory.InventoryItem equippedInventoryItem = getEquippedInventoryItem();
            if(equippedInventoryItem != null && equippedInventoryItem.count > 0){
                if(canPlant()){
                    if(energy >= 5){
                        planting = true;
                        energy -= 5;
                    }
                    else{
                        gp.ui.addMessage("Not enough energy to plant!");
                    }
                }
                else{
                    gp.ui.addMessage("Cannot plant here!"); // Pes
                }
            }
            else{
                gp.ui.addMessage("You don't have any seeds to plant!");
            }
        }
        else if (currentTool instanceof OBJ_WateringCan) {
            watering = true;
            if(this.energy >= 5){
                int col = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                int row = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;

                int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
                if (gp.tileM.tile[tileNum].tileType == TileType.PLANTED) {
                    watering = true;
                    energy -= 5; // Misal biaya energi untuk menyiram adalah 5
                } 
                else {
                    gp.ui.addMessage("Cannot water this tile!");
                }
            } 
            else {
                gp.ui.addMessage("Not enough energy to use the watering can!");
            }
        }
        else if (currentTool instanceof OBJ_Pickaxe) {
            recoverLand = true;
            if(this.energy >= 5){
                int col = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;
                int row = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize;

                int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
                if (gp.tileM.tile[tileNum].tileType == TileType.TILLED) {
                    recoverLand = true;
                    energy -= 5; // Misal biaya energi untuk mencangkul adalah 5
                } 
                else {
                    gp.ui.addMessage("Cannot recover this tile!");
                }
            } 
            else {
                gp.ui.addMessage("Not enough energy to use the hoe!");
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

    //KALO MAU PICKUP OBJECT
    public void pickUpObject(int i){
        Entity[] currentMapObjects = gp.getCurrentMapObjects();
        if (i != 999 && currentMapObjects[i] != null) {
            if (currentMapObjects[i].isPickable) {
                if (inventory.getInventory().size() < inventory.getMaxInventorySize()) { // periksa inventorynya penuh ga
                    boolean itemAlreadyInInventory = false;
                   for (Inventory.InventoryItem invItem : inventory.getInventory()) { // Perbaiki akses ke InventoryItem{
                        if (invItem.item.name.equals(currentMapObjects[i].name)) {
                            invItem.count++;
                            itemAlreadyInInventory = true;
                            break;
                        }
                    }
                    // klo item belum ada, tambahin ke inventory
                    if (!itemAlreadyInInventory) {
                       inventory.getInventory().add(new Inventory.InventoryItem(currentMapObjects[i], 1));
                    }
                    gp.playSE(1);
                    gp.ui.addMessage("Got a " + currentMapObjects[i].name + "!");
                    currentMapObjects[i] = null;
                } else {
                    gp.ui.addMessage("You cannot carry any more!"); // ini klo penuh
                }
            }
            }
        }
    
    public void planting(){
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;
            int targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;  // Default ke tile di bawah pemain (tengah)
            int targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize; // Default ke tile di bawah pemain (tengah)

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
                int tileNumAtTarget = gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow]; // Ambil nomor tile di target
                if (gp.tileM.tile[tileNumAtTarget].tileType == TileType.TILLED) { // Periksa tipe tile tersebut
                    gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow] = 9; // 9 = TilledSoil
                    // Anda mungkin ingin mengurangi energi pemain di sini atau memainkan suara
                    // gp.playSE(indeksSuaraCangkul);
                }
            }
        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            planting = false;
        }
    }

    public void tilling(){
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;
            // Tentukan tile yang akan diolah berdasarkan arah pemain
            int targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;  // Default ke tile di bawah pemain (tengah)
            int targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize; // Default ke tile di bawah pemain (tengah)

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
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
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
            // Tentukan tile yang akan diolah berdasarkan arah pemain
            int targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;  // Default ke tile di bawah pemain (tengah)
            int targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize; // Default ke tile di bawah pemain (tengah)

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
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
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
            // Tentukan tile yang akan diolah berdasarkan arah pemain
            int targetCol = (worldX + solidArea.x + solidArea.width / 2) / gp.tileSize;  // Default ke tile di bawah pemain (tengah)
            int targetRow = (worldY + solidArea.y + solidArea.height / 2) / gp.tileSize; // Default ke tile di bawah pemain (tengah)

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
                if (gp.tileM.tile[tileNumAtTarget].tileType == TileType.PLANTED) { // Periksa tipe tile tersebut
                    gp.tileM.mapTileNum[gp.currentMap][targetCol][targetRow] = 10; // 10 = WateredSoil
                    // Anda mungkin ingin mengurangi energi pemain di sini atau memainkan suara
                    // gp.playSE(indeksSuaraWatering);
                }
            }
        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            watering = false;
        }
    }

    // public void interactWithFurniture(){
    //     if (){
    //         if (gp.keyH.enterPressed) {
                
    //             gp.keyH.enterPressed = false;
    //         }
    //     }
    // }

    public void sleeping(){
        gp.ui.drawBlackScreen();
        gp.ui.addMessage("You are sleeping...");

        if (energy < 10) {
            energy += 50;
        }
        else if (energy == 0) {
            energy += 10;
        }
        else {
            energy = 100;
        }

        gp.eManager.setTime(6,0);
        gp.ui.addMessage("You have rested and regained energy!");
    }

    public void watching(){
        
    }

    public void proposing(){

    }

    public void marry(){

    }

    public void interactNPC(int i){
        if(gp.keyH.enterPressed) {
            if (i != 999) {
                gp.gameState = gp.dialogueState;
                gp.NPC[i].speak();
            }
            gp.keyH.enterPressed = false;
        }
    }

    public void draw(Graphics2D g2){
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = null;
        switch (direction){
            case "up":
                if(tilling == false){
                    if(spriteNum == 1){
                        image = up1;
                    }
                    if(spriteNum == 2){
                        image = up2;
                    }
                }
                if(tilling == true){
                    if(spriteNum == 1){
                        image = up1;
                    }
                    if(spriteNum == 2){
                        image = tillingUp;
                    }
                }
                if(planting == true){
                    if(spriteNum == 1){
                        image = up1;
                    }
                    if(spriteNum == 2){
                        image = parsnipSeeds;
                    }
                }
                if(watering == true){
                    if(spriteNum == 1){
                        image = up1;
                    }
                    if(spriteNum == 2){
                        image = wateringUp;
                    }
                }
                if(recoverLand == true){
                    if(spriteNum == 1){
                        image = up1;
                    }
                    if(spriteNum == 2){
                        image = recoverLandUp;
                    }
                }
                break;
            case "down":
                if(tilling == false){
                    if(spriteNum == 1){
                        image = down1;
                    }
                    if(spriteNum == 2){
                        image = down2;
                    }
                }
                if(tilling == true){
                    if(spriteNum == 1){
                        image = down1;
                    }
                    if(spriteNum == 2){
                        image = tillingDown;
                    }
                }
                if(planting == true){
                    if(spriteNum == 1){
                        image = down1;
                    }
                    if(spriteNum == 2){
                        image = parsnipSeeds;
                    }
                }
                if(watering == true){
                    if(spriteNum == 1){
                        image = down1;
                    }
                    if(spriteNum == 2){
                        image = wateringDown;
                    }
                }
                if(recoverLand == true){
                    if(spriteNum == 1){
                        image = down1;
                    }
                    if(spriteNum == 2){
                        image = recoverLandDown;
                    }
                }
                break;
            case "left":
                if(tilling == false){
                    if(spriteNum == 1){
                        image = left1;
                    }
                    if(spriteNum == 2){
                        image = left2;
                    }
                }
                if(tilling == true){
                    if(spriteNum == 1){
                        image = left1;
                    }
                    if(spriteNum == 2){
                        image = tillingLeft;
                    }
                }
                if(planting == true){
                    if(spriteNum == 1){
                        image = left1;
                    }
                    if(spriteNum == 2){
                        image = parsnipSeeds;
                    }
                }
                if(watering == true){
                    if(spriteNum == 1){
                        image = left1;
                    }
                    if(spriteNum == 2){
                        image = wateringLeft;
                    }
                }
                if(recoverLand == true){
                    if(spriteNum == 1){
                        image = left1;
                    }
                    if(spriteNum == 2){
                        image = recoverLandLeft;
                    }
                }
                break;
            case "right":
                if(tilling == false){
                    if(spriteNum == 1){
                        image = right1;
                    }
                    if(spriteNum == 2){
                        image = right2;
                    }
                }
                if(tilling == true){
                    if(spriteNum == 1){
                        image = right1;
                    }
                    if(spriteNum == 2){
                        image = tillingRight;
                    }
                }
                if(planting == true){
                    if(spriteNum == 1){
                        image = right1;
                    }
                    if(spriteNum == 2){
                        image = parsnipSeeds;
                    }
                }
                if(watering == true){
                    if(spriteNum == 1){
                        image = right1;
                    }
                    if(spriteNum == 2){
                        image = wateringRight;
                    }
                }
                if(recoverLand == true){
                    if(spriteNum == 1){
                        image = right1;
                    }
                    if(spriteNum == 2){
                        image = recoverLandRight;
                    }
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}
