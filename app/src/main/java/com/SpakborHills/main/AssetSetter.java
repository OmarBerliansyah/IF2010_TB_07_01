package com.SpakborHills.main;

import com.SpakborHills.entity.NPC_1;
import com.SpakborHills.objects.OBJ_Chest;
import com.SpakborHills.objects.OBJ_Door;
import com.SpakborHills.objects.OBJ_Wood;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_Door(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Chest(gp);
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 8 * gp.tileSize;

        gp.obj[2] = new OBJ_Wood(gp);
        gp.obj[2].worldX = 22 * gp.tileSize;
        gp.obj[2].worldY = 7 * gp.tileSize;

        gp.obj[3] = new OBJ_Wood(gp);
        gp.obj[3].worldX = 20 * gp.tileSize;
        gp.obj[3].worldY = 7 * gp.tileSize;

        gp.obj[4] = new OBJ_Wood(gp);
        gp.obj[4].worldX = 18 * gp.tileSize;
        gp.obj[4].worldY = 7 * gp.tileSize;
    }

    public void setNPC(){
        gp.NPC[0] = new NPC_1(gp);
        gp.NPC[0].worldX = gp.tileSize*21;
        gp.NPC[0].worldY = gp.tileSize*21;

        gp.NPC[1] = new NPC_1(gp);
        gp.NPC[1].worldX = gp.tileSize*11;
        gp.NPC[1].worldY = gp.tileSize*21;

        gp.NPC[2] = new NPC_1(gp);
        gp.NPC[2].worldX = gp.tileSize*9;
        gp.NPC[2].worldY = gp.tileSize*21;
    }
}
