package com.SpakborHills.Main;

import com.SpakborHills.Entity.*;
import com.SpakborHills.Objects.OBJ_Chest;
import com.SpakborHills.Objects.OBJ_Door;
import com.SpakborHills.Objects.OBJ_Wood;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_Door();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Chest();
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 8 * gp.tileSize;

        gp.obj[2] = new OBJ_Wood();
        gp.obj[2].worldX = 22 * gp.tileSize;
        gp.obj[2].worldY = 7 * gp.tileSize;

        gp.obj[3] = new OBJ_Wood();
        gp.obj[3].worldX = 20 * gp.tileSize;
        gp.obj[3].worldY = 7 * gp.tileSize;

        gp.obj[4] = new OBJ_Wood();
        gp.obj[4].worldX = 18 * gp.tileSize;
        gp.obj[4].worldY = 7 * gp.tileSize;
    }

    public void setNPC(){
        gp.NPC[0] = new NPC_1(gp);
        gp.NPC[0].worldX = gp.tileSize*21;
        gp.NPC[0].worldY = gp.tileSize*21;
    }
}
