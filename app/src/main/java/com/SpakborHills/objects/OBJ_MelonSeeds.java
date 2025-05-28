package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_MelonSeeds extends Entity {

    public OBJ_MelonSeeds(GamePanel gp){
        super(gp);
        name = "Melon Seeds";
        down1 = setup("objects/MelonSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nsummer."; 
        isPickable = true;
        daysToHarvest = 4; 
        buyPrice = 80; 
    }
}