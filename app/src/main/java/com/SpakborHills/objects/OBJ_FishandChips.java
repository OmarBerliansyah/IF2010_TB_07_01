package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_FishandChips extends Entity {
    public int plusEnergy; 

    public OBJ_FishandChips(GamePanel gp){
        super(gp);
        name = "FishandChips ";
        down1 = setup("objects/FishandChips",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat memulihkan energi\nsebanyak" + plusEnergy; 
        isPickable = true;
        plusEnergy = 50; 
        buyPrice = 50;
        salePrice = 35;
    }
}