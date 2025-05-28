package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_GrapeSeeds extends Entity {

    public OBJ_GrapeSeeds(GamePanel gp){
        super(gp);
        name = "Grape Seeds";
        down1 = setup("objects/GrapeSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nfall."; 
        isPickable = true;
        daysToHarvest = 3; 
        buyPrice = 60; 
    }
}