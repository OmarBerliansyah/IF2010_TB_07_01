package com.SpakborHills.main;

import com.SpakborHills.entity.Entity;

public class CollisionChecker {

    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;
        
        int currentMap = gp.currentMap;
        int maxCol = gp.tileM.mapCols[currentMap];
        int maxRow = gp.tileM.mapRows[currentMap];


        int checkLeftCol = Math.max(0, Math.min(entityLeftCol, maxCol - 1));
        int checkRightCol = Math.max(0, Math.min(entityRightCol, maxCol - 1));
        int checkTopRow = Math.max(0, Math.min(entityTopRow, maxRow - 1));
        int checkBottomRow = Math.max(0, Math.min(entityBottomRow, maxRow - 1));

        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                checkTopRow = Math.max(0, Math.min(entityTopRow, maxRow-1));
                tileNum1 = gp.tileM.mapTileNum[currentMap][checkLeftCol][checkTopRow];
                tileNum2 = gp.tileM.mapTileNum[currentMap][checkRightCol][checkTopRow];

                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                checkBottomRow = Math.max(0, Math.min(entityBottomRow, maxRow-1));
                tileNum1 = gp.tileM.mapTileNum[currentMap][checkLeftCol][checkBottomRow];
                tileNum2 = gp.tileM.mapTileNum[currentMap][checkRightCol][checkBottomRow];

                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                checkLeftCol = Math.max(0, Math.min(entityLeftCol, maxCol-1));
                tileNum1 = gp.tileM.mapTileNum[currentMap][checkLeftCol][checkTopRow];
                tileNum2 = gp.tileM.mapTileNum[currentMap][checkLeftCol][checkBottomRow];

                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                checkRightCol = Math.max(0, Math.min(entityRightCol, maxCol-1));
                tileNum1 = gp.tileM.mapTileNum[currentMap][checkRightCol][checkTopRow];
                tileNum2 = gp.tileM.mapTileNum[currentMap][checkRightCol][checkBottomRow];

                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player){
        int index = 999;
        Entity[] currentMapObjects = gp.getCurrentMapObjects();
        for(int i = 0; i < currentMapObjects.length; i++){
            if(currentMapObjects[i] != null){
                //get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //get the object's solid area position
                currentMapObjects[i].solidArea.x = currentMapObjects[i].worldX + currentMapObjects[i].solidArea.x;
                currentMapObjects[i].solidArea.y = currentMapObjects[i].worldY + currentMapObjects[i].solidArea.y;

                switch (entity.direction){
                    case"up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(currentMapObjects[i].solidArea)){
                            if(currentMapObjects[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if(player == true){
                                index = i;
                            }
                        }
                        break;
                    case"down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(currentMapObjects[i].solidArea)){
                            if(currentMapObjects[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if(player == true){
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(currentMapObjects[i].solidArea)){
                            if(currentMapObjects[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if(player == true){
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(currentMapObjects[i].solidArea)){
                            if(currentMapObjects[i].collision == true){
                                entity.collisionOn = true;
                            }
                            if(player == true){
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                currentMapObjects[i].solidArea.x = currentMapObjects[i].solidAreaDefaultX;
                currentMapObjects[i].solidArea.y = currentMapObjects[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    //NPC
    public int checkEntity(Entity entity, Entity[] target){
        int index = 999;

        for(int i =0; i< target.length; i++){
            if(target[i] != null && target[i] != entity){
                //get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //get the object's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (entity.direction){
                    case"up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case"down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public void checkPlayer(Entity entity){
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        //get the object's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction){
            case"up":
                entity.solidArea.y -= entity.speed;
                if(entity.solidArea.intersects(gp.player.solidArea)){
                    entity.collisionOn = true;
                }
                break;
            case"down":
                entity.solidArea.y += entity.speed;
                if(entity.solidArea.intersects(gp.player.solidArea)){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                if(entity.solidArea.intersects(gp.player.solidArea)){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                if(entity.solidArea.intersects(gp.player.solidArea)){
                    entity.collisionOn = true;
                }
                break;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    }
}