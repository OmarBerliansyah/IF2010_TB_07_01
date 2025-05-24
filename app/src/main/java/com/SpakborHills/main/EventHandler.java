package com.SpakborHills.main;

// import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
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

                if (row == gp.maxWorldCol) {
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
            if (hit(0, 10, 31, "any") == true) {
                teleport(1, 10, 5);
                gp.aSetter.setObject(); 
                gp.aSetter.setNPC();
            }

            // BACK TELEPORT TO MAIN MAP
            else if (hit(1, 10, 1, "any") == true) {
                teleport(0, 10, 31);
                gp.aSetter.setObject(); 
                gp.aSetter.setNPC();
            }

            else if (hit(0, gp.aSetter.getDoorCol(), gp.aSetter.getDoorRow(), "any") == true) {
                teleport(2, 11, 23);
                gp.aSetter.setObject(); 
                gp.aSetter.setNPC();
            }

            else if (hit(2, 12, 23, "any") == true) {
                teleport(0, gp.aSetter.getDoorCol()+1, gp.aSetter.getDoorRow());
                gp.aSetter.setObject(); 
                gp.aSetter.setNPC();
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
        // tempMap = map;
        // tempCol = col;
        // tempRow = row;
        // canTouchEvent = false;
        // gp.playSE(13);
    }
}
