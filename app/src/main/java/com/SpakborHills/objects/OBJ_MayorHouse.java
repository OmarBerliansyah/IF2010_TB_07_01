package com.SpakborHills.objects;
import java.awt.Graphics2D;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_MayorHouse extends Entity {
    public OBJ_MayorHouse(GamePanel gp){
        super(gp);
        name = "MayorHouse";
        down1 = setup("objects/MayorHouse", gp.tileSize, gp.tileSize); // Your house sprite file
        collision = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 6 * gp.tileSize;
        solidArea.height = 6 * gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        isPickable = false;
    }
    
    @Override
    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        if(worldX + (6 * gp.tileSize) > gp.player.worldX - gp.player.screenX &&
           worldX - (6 * gp.tileSize) < gp.player.worldX + gp.player.screenX &&
           worldY + (6 * gp.tileSize) > gp.player.worldY - gp.player.screenY &&
           worldY - (6 * gp.tileSize) < gp.player.worldY + gp.player.screenY){
            
            g2.drawImage(down1, screenX, screenY, 6 * gp.tileSize, 6 * gp.tileSize, null);
        }
    }
}