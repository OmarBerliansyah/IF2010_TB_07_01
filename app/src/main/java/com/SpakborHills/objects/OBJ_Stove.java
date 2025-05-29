package com.SpakborHills.objects;
import java.awt.Graphics2D;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Stove extends Entity {
    public OBJ_Stove(GamePanel gp){
        super(gp);
        name = "Stove";
        down1 = setup("objects/Stove", gp.tileSize, gp.tileSize); 
        collision = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 1 * gp.tileSize;
        solidArea.height = 1 * gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        isPickable = false;
    }

    public void draw(Graphics2D g2){
        int drawX = worldX - gp.clampedCameraX;
        int drawY = worldY - gp.clampedCameraY;

        if (drawX + (1 * gp.tileSize) > 0 && drawX < gp.screenWidth && drawY + (1 * gp.tileSize) > 0 && drawY < gp.screenHeight) {
            g2.drawImage(down1, drawX, drawY, 1 * gp.tileSize, 1 * gp.tileSize, null);
        }
    }
}
