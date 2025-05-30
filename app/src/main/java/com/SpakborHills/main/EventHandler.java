package com.SpakborHills.main;

// import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];

    int previousEventX, previousEventY;
    public boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;

                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent(){
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }


        // if (hit(27, 16, "right") == true){
        //     // event happens
        // }

         if (canTouchEvent == true) {
            // PIT DAMAGE
            // if (hit(0, 27, 16, "right") == true) {
            //     damagePit(gp.dialogueState);
            // }

            // TELEPORT TO OCEAN
            if (hit(0, 13, 31, "any") == true) {
                teleport(1, 11, 0);
            }

            // BACK TELEPORT TO MAIN MAP FROM OCEAN
            else if (hit(1, 12, 0, "any") == true) {
                teleport(0, 13, 31);
            }

            // TELEPORT TO HOUSE
            else if (hit(0, gp.aSetter.getDoorCol()+1+1, gp.aSetter.getDoorRow(), "any") == true) {
                teleport(2, 11, 23);
            }

            // TELEPORT TO MAIN FROM HOUSE
            else if (hit(2, 11, 23, "any") == true) {
                teleport(0, gp.aSetter.getDoorCol()+1, gp.aSetter.getDoorRow());

            }

            // TELEPORT TO FOREST RIVER
            else if (hit(0, 0, 17, "any") == true) {
                teleport(3, 15, 5);
            }

            // BACK TELEPORT TO MAIN MAP FROM FOREST RIVER
            else if (hit(3, 15, 6, "any") == true) {
                teleport(0, 0, 16);
            }

            // TELEPORT TO NPC MAP
            else if (hit(0, 0, 14, "any") == true) {
                teleport(4, 31, 18);
    
            }
            else if (hit(4, 31, 19, "any") == true) {
                teleport(0,0,13);
            
            }
            // TELEPORT FROM NPCMAP TO Mayor HOUSE
            else if (hit(4, 3, 7, "any") == true) {
                teleport(9, 11, 22);
            }
            // teleport back to infront of mayor house
            else if(hit(9,12,22, "any") == true){
                teleport(4,4,7);
            }
            // teleport to emily house
            else if (hit(4, 15,7, "any")==true){
                teleport(5, 11, 22);
            }
            else if(hit(5,12,22, "any") == true){
                teleport(4,16,7);
            }
            // TELEPORT KE RUMAH CAROLINE
            else if (hit(4, 3,18, "any")== true){
                teleport(10, 11, 22);
            }
            else if(hit(10,12,22, "any") == true){
                teleport(4,4,18);
            }
            // TELEPORT KE RUMAH ABIGAIL
            else if (hit(4, 14,18, "any")==true){
                teleport(8, 11, 22);
            }
            else if(hit(8,12,22, "any") == true){
                teleport(4,15,18);
            }
            // TELEPORT KE RUMAH PERRY
            else if (hit(4, 3, 28, "any") == true) {
                teleport(6, 11, 22);
            }
            else if (hit(6, 12, 22, "any") == true) {
                teleport(4, 4, 28);  // Balik ke koordinat (4,26)
            }

            // TELEPORT KE RUMAH DASCO  
            else if (hit(4, 14, 28, "any") == true) {
                teleport(7, 11, 22);
            }
            else if (hit(7, 12, 22, "any") == true) {
                teleport(4, 15, 28);  // Balik ke koordinat (15,26)
            }

            // TELEPORT TO MOUNTAIN LAKE
            else if (hit(0, 28, 0, "any") == true) {
                teleport(11, 8, 2);
            }

            // BACK TELEPORT TO MAIN MAP FROM MOUNTAIN LAKE
            else if (hit(11, 8, 2, "any") == true) {
                teleport(0, 28, 0);
            }

            // Interact with Bed
            else if (hit(2, 6, 11, "any") == true) {
                if(!gp.ui.showingSleepConfirmDialog){
                    canTouchEvent = false;
                    gp.gameState = gp.dialogueState;
                    gp.keyH.enterPressed = false;
                    gp.ui.showSleepConfirmationDialog();
                    // teleport(2, 8, 10);
                }
            }

            else if (hit(0, gp.aSetter.getDoorCol() + 6, gp.aSetter.getDoorRow() - 2, "any")) {
                System.out.println("Shipping bin interaction detected!");
                canTouchEvent = false;
                gp.gameState = gp.shippingBinState;
                gp.player.openShippingBin();
            }

            else if (hit(2, 20, 6, "any") == true) { // Cooking station in house
                if (gp.player.energy >= 10) {
                    canTouchEvent = false;
                    gp.gameState = gp.cookingState;
                    gp.ui.showCookingInterface();
                } else {
                    gp.ui.addMessage("Not enough energy to cook!");
                    canTouchEvent = false;
                }
            }
            else if(hit(2, 13, 6, "any") == true){
                if(gp.player.energy < 5){
                        canTouchEvent = false;
                        gp.ui.addMessage("Not enough energy to watch TV!");
                        gp.keyH.enterPressed = false;
                        gp.ui.showingWatchTV = false;
                        return;
                }
                gp.gameState = gp.dialogueState;
                canTouchEvent = false;
                gp.keyH.enterPressed = false;
                gp.ui.showingWatchTV = true;
                gp.player.energy -= 5;
            }
            // // NPC MERCHANT
            // else if (hit(1, 12, 9, "up") == true) {
            //     speak(gp.npc[1][0]);
            // }
        }
    }

    public boolean hit(int map,int col, int row, String reqDirection){
        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;

            // gp.ui.addMessage("Player Area: " + 
            // (gp.player.worldX + gp.player.solidArea.x) + ", " +
            // (gp.player.worldY + gp.player.solidArea.y) + " " + gp.player.solidArea.width + "x" + gp.player.solidArea.height);
            // gp.ui.addMessage("Event Area at (" + col + "," + row + "): " +
            //         (col * gp.tileSize + eventRect[map][col][row].x) + ", " +
            //         (row * gp.tileSize + eventRect[map][col][row].y) +
            //         " " + eventRect[map][col][row].width + "x" +
            //         eventRect[map][col][row].height);

            if(gp.player.solidArea.intersects(eventRect[map][col][row])){
                if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

    public void teleport(int map, int col, int row){
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;
        gp.aSetter.setObject();
        gp.aSetter.setNPC();
        gp.player.updateLocation();
        // tempMap = map;
        // tempCol = col;
        // tempRow = row;
        // canTouchEvent = false;
        // gp.playSE(13);
    }
}
