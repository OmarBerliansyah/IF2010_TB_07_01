package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_PotatoSeeds extends Entity {

    public OBJ_PotatoSeeds(GamePanel gp){
        super(gp);
        name = "Potato Seeds";
        down1 = setup("objects/PotatoSeeds",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nBenih yang dapat\nditanam pada saat\nspring."; 
        isPickable = true;
        daysToHarvest = 3; 
        buyPrice = 50; 
    }
}