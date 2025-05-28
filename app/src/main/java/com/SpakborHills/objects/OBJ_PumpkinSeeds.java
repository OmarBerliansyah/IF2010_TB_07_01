package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_PumpkinSeeds extends Entity {

    public OBJ_PumpkinSeeds(GamePanel gp){
        super(gp);
        name = "Pumpkin Seeds";
        down1 = setup("objects/PumpkinSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nfall."; 
        isPickable = true;
        daysToHarvest = 7; 
        buyPrice = 150; 
    }
}