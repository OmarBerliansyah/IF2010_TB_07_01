package com.SpakborHills.objects;
import java.awt.Graphics2D;
import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Pond extends Entity {
    public OBJ_Pond(GamePanel gp){
        super(gp);
        name = "Pond";
        down1 = setup("objects/Pond",  gp.tileSize, gp.tileSize); // Your pond sprite file
        collision = true; // Players can walk through water, or set true if you want collision

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 3 * gp.tileSize;
        solidArea.height = 4 * gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        isPickable = false;
    }
    
    @Override
    public void draw(Graphics2D g2){
        int drawX = worldX - gp.clampedCameraX;
        int drawY = worldY - gp.clampedCameraY;

        if (drawX + (3 * gp.tileSize) > 0 && drawX < gp.screenWidth && drawY + (4 * gp.tileSize) > 0 && drawY < gp.screenHeight) {
            g2.drawImage(down1, drawX, drawY, 3 * gp.tileSize, 4 * gp.tileSize, null);
        }
    }
}