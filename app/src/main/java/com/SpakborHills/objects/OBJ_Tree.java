package com.SpakborHills.objects;

import java.awt.Graphics2D;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Tree extends Entity {

    public OBJ_Tree(GamePanel gp){
        super(gp);
        name = "Tree";
        down1 = setup("objects/pohon",  gp.tileSize, gp.tileSize); 
        collision = true;

        solidArea.x = gp.tileSize / 4; // Start collision area slightly inset from left edge
        solidArea.y = 2 * gp.tileSize; // Start collision at the trunk (bottom row of the 3-tile height)
        solidArea.width = (int)(1.5 * gp.tileSize); // Trunk width (most of the 2-tile width)
        solidArea.height = gp.tileSize; // Only the bottom tile row has collision
        

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
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