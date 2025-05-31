package com.SpakborHills.objects;
import java.awt.Graphics2D;
import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_ShippingBin extends Entity {
    public static class ShippingBinItem {
        public String itemId;
        public String itemName;
        public int quantity;
        public int sellPrice;
        
        public ShippingBinItem(String itemId, String itemName, int quantity, int sellPrice) {
            this.itemId = itemId;
            this.itemName = itemName;
            this.quantity = quantity;
            this.sellPrice = sellPrice;
        }
    }
    
    public OBJ_ShippingBin(GamePanel gp){
        super(gp);
        name = "ShippingBin";
        down1 = setup("objects/ShippingBin",  gp.tileSize, gp.tileSize); // Your shipping bin sprite file
        collision = true;
        isEdible = false;
        
        // Shipping bin is 3x2 tiles
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 3 * gp.tileSize;
        solidArea.height = 2 * gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        isPickable = false;
    }
    
    @Override
    public void draw(Graphics2D g2){
        int drawX = worldX - gp.clampedCameraX;
        int drawY = worldY - gp.clampedCameraY;

        if (drawX + (3 * gp.tileSize) > 0 && drawX < gp.screenWidth && drawY + (2 * gp.tileSize) > 0 && drawY < gp.screenHeight) {
            g2.drawImage(down1, drawX, drawY, 3 * gp.tileSize, 2 * gp.tileSize, null);
        }
    }
}