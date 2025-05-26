package com.SpakborHills.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.SpakborHills.main.GamePanel;
import com.SpakborHills.main.KeyHandler;
import com.SpakborHills.objects.OBJ_FishingRod;
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
    public ArrayList<InventoryItem> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
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
        setItems(); 
        getPlayerSeedsImage();
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


    public void setItems(){
        inventory.add(new InventoryItem(new OBJ_ParsnipSeeds(gp), 15));
        inventory.add(new InventoryItem(new OBJ_Hoe(gp), 1));
        inventory.add(new InventoryItem(new OBJ_WateringCan(gp), 1));
        inventory.add(new InventoryItem(new OBJ_Pickaxe(gp), 1));
        inventory.add(new InventoryItem(new OBJ_FishingRod(gp), 1));
    }

    public class InventoryItem {
        public Entity item;
        public int count;

        public InventoryItem(Entity item, int count) {
            this.item = item;
            this.count = count;
        }
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
        tillingLeft = setup("player/PlayerLeftPickAxe",gp.tileSize, gp.tileSize);
        tillingRight = setup("player/PlayerRightHoe",gp.tileSize, gp.tileSize);
    }

    public void getPlayerSeedsImage(){
        blueberrySeeds = setup("player/PlayerBlueberrySeeds", gp.tileSize, gp.tileSize);
    }

    public void equipItem(int inventoryIndex) {
        if (inventoryIndex >= 0 && inventoryIndex < inventory.size()) {
            equippedItem = inventory.get(inventoryIndex).item;
        }
    }

    public void unEquipItem() {
        equippedItem = null;
    }

    public void update(){

        if(keyH.tillingPressed){
            if(energy >= 5){
                tilling = true;
                keyH.tillingPressed = false; // Reset the tillingPressed flag
                tilling();
            }
            else{
                gp.ui.addMessage("Need more energy to till this soil!");
            }
        }

        else if(keyH.plantingPressed){
            if(energy >= 5){
                planting = true;
                planting();
            }
            else{
                gp.ui.addMessage("Need more energy to plant this seed!");
            }
            keyH.plantingPressed = false; // Reset the plantingPressed flag
        }

        if(moving == false){
            boolean movementKeyPressed = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;
            if(movementKeyPressed || keyH.enterPressed ) {
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
                }

                gp.keyH.enterPressed = false;
                gp.keyH.tillingPressed = false;
                gp.keyH.plantingPressed = false;
            }
            else{
                standCounter++;
                if(standCounter == 20){
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
        }

        if(moving == true){
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
            gp.keyH.tillingPressed = false;
            gp.keyH.plantingPressed = false;
        

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
    }

    //KALO MAU PICKUP OBJECT
    public void pickUpObject(int i){
         Entity[] currentMapObjects = gp.getCurrentMapObjects();
        if (i != 999 && currentMapObjects[i] != null) {
            if (currentMapObjects[i].isPickable) {
                if (inventory.size() < maxInventorySize) { // periksa inventorynya penuh ga
                    boolean itemAlreadyInInventory = false;
                    for (InventoryItem invItem : inventory) {
                        if (invItem.item.name.equals(currentMapObjects[i].name)) {
                            invItem.count++;
                            itemAlreadyInInventory = true;
                            break;
                        }
                    }
                    // klo item belum ada, tambahin ke inventory
                    if (!itemAlreadyInInventory) {
                        inventory.add(new InventoryItem(currentMapObjects[i], 1));
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
                    energy -= 5;
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
                    // energy--;
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
                        image = blueberrySeeds;
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
                        image = up1;
                    }
                    if(spriteNum == 2){
                        image = blueberrySeeds;
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
                        image = up1;
                    }
                    if(spriteNum == 2){
                        image = blueberrySeeds;
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
                        image = up1;
                    }
                    if(spriteNum == 2){
                        image = blueberrySeeds;
                    }
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}
