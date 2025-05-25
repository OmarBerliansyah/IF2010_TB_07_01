package com.SpakborHills.objects;

import java.awt.Graphics2D;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Tree2 extends Entity {

    public OBJ_Tree2(GamePanel gp){
        super(gp);
        name = "Tree2";
        down1 = setup("objects/pohon2",  gp.tileSize, gp.tileSize); 
        collision = true;

        solidArea.x = gp.tileSize / 4; // Start collision area slightly inset from left edge
        solidArea.y = 2 * gp.tileSize; // Start collision at the trunk (bottom row of the 3-tile height)
        solidArea.width = (int)(1.5 * gp.tileSize); // Trunk width (most of the 2-tile width)
        solidArea.height = gp.tileSize; // Only the bottom tile row has collision
        

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    @Override
    public void draw(Graphics2D g2){
        
        // Kalkulasi posisi di layar relatif terhadap pemain
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Cek apakah pohon berada di dalam layar (optimisasi)
        // Kita gunakan ukuran pohon besar (3 * gp.tileSize) agar tidak hilang terlalu cepat dari layar
        if(worldX + (2 * gp.tileSize) > gp.player.worldX - gp.player.screenX &&
           worldX - (2 * gp.tileSize) < gp.player.worldX + gp.player.screenX &&
           worldY + (3 * gp.tileSize) > gp.player.worldY - gp.player.screenY &&
           worldY - (3 * gp.tileSize) < gp.player.worldY + gp.player.screenY){
            
            // Gambar pohon dengan ukuran yang benar
            g2.drawImage(down1, screenX, screenY, 2 * gp.tileSize, 3 * gp.tileSize, null);
        }
    }
}