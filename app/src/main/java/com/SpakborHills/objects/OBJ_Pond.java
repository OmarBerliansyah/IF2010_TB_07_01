package com.SpakborHills.objects;
import java.awt.Graphics2D;
import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Pond extends Entity {
    public OBJ_Pond(GamePanel gp){
        super(gp);
        name = "Pond";
        down1 = setup("objects/Pond"); // Your pond sprite file
        collision = true; // Players can walk through water, or set true if you want collision

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 4 * gp.tileSize;
        solidArea.height = 3 * gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    
    @Override
    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        if(worldX + (4 * gp.tileSize) > gp.player.worldX - gp.player.screenX &&
           worldX - (4 * gp.tileSize) < gp.player.worldX + gp.player.screenX &&
           worldY + (3 * gp.tileSize) > gp.player.worldY - gp.player.screenY &&
           worldY - (3 * gp.tileSize) < gp.player.worldY + gp.player.screenY){
            
            g2.drawImage(down1, screenX, screenY, 4 * gp.tileSize, 3 * gp.tileSize, null);
        }
    }
}