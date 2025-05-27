package com.SpakborHills.objects;
import java.awt.Graphics2D;
import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_ShippingBin extends Entity {
    public OBJ_ShippingBin(GamePanel gp){
        super(gp);
        name = "ShippingBin";
        down1 = setup("objects/ShippingBin",  gp.tileSize, gp.tileSize); // Your shipping bin sprite file
        collision = true;
        
        // Shipping bin is 3x2 tiles
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 2 * gp.tileSize;
        solidArea.height = 3 * gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        isPickable = false;
    }
    
    @Override
    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        if(worldX + (3 * gp.tileSize) > gp.player.worldX - gp.player.screenX &&
           worldX - (3 * gp.tileSize) < gp.player.worldX + gp.player.screenX &&
           worldY + (2 * gp.tileSize) > gp.player.worldY - gp.player.screenY &&
           worldY - (2 * gp.tileSize) < gp.player.worldY + gp.player.screenY){
            
            g2.drawImage(down1, screenX, screenY, 3 * gp.tileSize, 2 * gp.tileSize, null);
        }
    }
}