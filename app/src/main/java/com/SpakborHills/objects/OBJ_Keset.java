package com.SpakborHills.objects;

import java.awt.Graphics2D;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Keset extends Entity {

    public OBJ_Keset(GamePanel gp){
        super(gp);
        name = "Keset";
        down1 = setup("objects/keset", gp.tileSize, gp.tileSize); 
        collision = false;
        isPickable = false;
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
            g2.drawImage(down1, screenX, screenY, 2 * gp.tileSize, 2 * gp.tileSize, null);
        }
    }
}