package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_WheatSeeds extends Entity {

    public OBJ_WheatSeeds(GamePanel gp){
        super(gp);
        name = "Wheat Seeds";
        down1 = setup("objects/WheatSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nspring dan fall."; 
        isPickable = true;
        daysToHarvest = 1; 
        buyPrice = 60; 
    }
}