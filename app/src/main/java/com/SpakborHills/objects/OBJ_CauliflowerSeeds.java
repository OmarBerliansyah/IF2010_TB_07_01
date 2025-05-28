package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_CauliflowerSeeds extends Entity {

    public OBJ_CauliflowerSeeds(GamePanel gp){
        super(gp);
        name = "Cauliflower Seeds";
        down1 = setup("objects/CauliflowerSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nspring."; 
        isPickable = true;
        daysToHarvest = 5; 
        buyPrice = 80; 
    }
}