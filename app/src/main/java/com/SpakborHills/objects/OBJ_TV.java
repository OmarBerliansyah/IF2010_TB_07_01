package com.SpakborHills.objects;
import java.awt.Graphics2D;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_TV extends Entity {
    public OBJ_TV(GamePanel gp){
        super(gp);
        name = "TV";
        down1 = setup("objects/TV", gp.tileSize, gp.tileSize); 
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
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        if(worldX + (1 * gp.tileSize) > gp.player.worldX - gp.player.screenX &&
           worldX - (1 * gp.tileSize) < gp.player.worldX + gp.player.screenX &&
           worldY + (1 * gp.tileSize) > gp.player.worldY - gp.player.screenY &&
           worldY - (1 * gp.tileSize) < gp.player.worldY + gp.player.screenY){
            
            g2.drawImage(down1, screenX, screenY, 1 * gp.tileSize, 1 * gp.tileSize, null);
        }
    }
}
