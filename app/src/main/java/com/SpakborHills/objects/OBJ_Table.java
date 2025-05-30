package com.SpakborHills.objects;
import java.awt.Graphics2D;
import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Table extends Entity {
    public OBJ_Table(GamePanel gp){
        super(gp);
        name = "Table";
        down1 = setup("objects/Table", gp.tileSize, gp.tileSize); // Your house sprite file
        collision = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 2 * gp.tileSize;
        solidArea.height = 2 * gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        isPickable = false;
        isEdible = false;
    }
    
    @Override
    public void draw(Graphics2D g2){
        int drawX = worldX - gp.clampedCameraX;
        int drawY = worldY - gp.clampedCameraY;

        if (drawX + (2 * gp.tileSize) > 0 && drawX < gp.screenWidth && drawY + (2 * gp.tileSize) > 0 && drawY < gp.screenHeight) {
            g2.drawImage(down1, drawX, drawY, 2 * gp.tileSize, 2 * gp.tileSize, null);
        }
    }
}