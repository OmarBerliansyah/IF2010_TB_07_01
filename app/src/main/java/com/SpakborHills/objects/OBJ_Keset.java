package com.SpakborHills.objects;

import java.awt.Graphics2D;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Keset extends Entity {

    public OBJ_Keset(GamePanel gp){
        super(gp);
        name = "Keset";
        down1 = setup("objects/keset", gp.tileSize, gp.tileSize); 
        collision = false;
        isPickable = false;
    }
    @Override
    public void draw(Graphics2D g2){
        int drawX = worldX - gp.clampedCameraX;
        int drawY = worldY - gp.clampedCameraY;

        if (drawX + (2 * gp.tileSize) > 0 && drawX < gp.screenWidth && drawY + (3 * gp.tileSize) > 0 && drawY < gp.screenHeight) {
            g2.drawImage(down1, drawX, drawY, 2 * gp.tileSize, 3 * gp.tileSize, null);
        }
    }
}