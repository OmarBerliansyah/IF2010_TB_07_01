package com.SpakborHills.objects;
import java.awt.Graphics2D;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_QueenBed extends Entity {
    public OBJ_QueenBed(GamePanel gp){
        super(gp);
        name = "Queen Bed";
        down1 = setup("objects/QueenBed", gp.tileSize, gp.tileSize); 
        collision = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 4 * gp.tileSize;
        solidArea.height = 6 * gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        isPickable = false;
    }

    public void draw(Graphics2D g2){
        int drawX = worldX - gp.clampedCameraX;
        int drawY = worldY - gp.clampedCameraY;

        if (drawX + (4 * gp.tileSize) > 0 && drawX < gp.screenWidth && drawY + (6 * gp.tileSize) > 0 && drawY < gp.screenHeight) {
            g2.drawImage(down1, drawX, drawY, 4 * gp.tileSize, 6 * gp.tileSize, null);
        }
    }
}
