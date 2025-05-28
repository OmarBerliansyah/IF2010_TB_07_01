package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_HotPepperSeeds extends Entity {

    public OBJ_HotPepperSeeds(GamePanel gp){
        super(gp);
        name = "HotPepper Seeds";
        down1 = setup("objects/HotPepperSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nsummer."; 
        isPickable = true;
        daysToHarvest = 1; 
        buyPrice = 40; 
    }
}