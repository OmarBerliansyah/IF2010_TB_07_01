package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_CranberrySeeds extends Entity {

    public OBJ_CranberrySeeds(GamePanel gp){
        super(gp);
        name = "Cranberry Seeds";
        down1 = setup("objects/CranberrySeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nfall."; 
        isPickable = true;
        daysToHarvest = 2; 
        buyPrice = 100; 
    }
}