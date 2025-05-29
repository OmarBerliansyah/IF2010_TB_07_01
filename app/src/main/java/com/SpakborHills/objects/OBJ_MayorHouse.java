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
        int drawX = worldX - gp.clampedCameraX;
        int drawY = worldY - gp.clampedCameraY;

        if (drawX + (6 * gp.tileSize) > 0 && drawX < gp.screenWidth && drawY + (6 * gp.tileSize) > 0 && drawY < gp.screenHeight) {
            g2.drawImage(down1, drawX, drawY, 6 * gp.tileSize, 6 * gp.tileSize, null);
        }
    }
}