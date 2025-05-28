package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_TomatoSeeds extends Entity {

    public OBJ_TomatoSeeds(GamePanel gp){
        super(gp);
        name = "Tomato Seeds";
        down1 = setup("objects/TomatoSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nsummer."; 
        isPickable = true;
        daysToHarvest = 3; 
        buyPrice = 50; 
    }
}