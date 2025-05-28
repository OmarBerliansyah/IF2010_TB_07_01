package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_FishSandwich extends Entity {
    public int plusEnergy; 

    public OBJ_FishSandwich(GamePanel gp){
        super(gp);
        name = "FishSandwich ";
        down1 = setup("objects/FishSandwich",gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nDapat memulihkan energi\nsebanyak" + plusEnergy; 
        isPickable = true;
        plusEnergy = 50; 
        buyPrice = 200;
        salePrice = 180;
    }
}