package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_FishSandwich extends Entity {

    public OBJ_FishSandwich(GamePanel gp){
        super(gp);
        name = "FishSandwich";
        down1 = setup("objects/FishSandwich",gp.tileSize, gp.tileSize);
        isPickable = true;
        plusEnergy = 50; 
        buyPrice = 200;
        salePrice = 180;
        isEdible = true;
        description = "[" + name + "]\nDapat memulihkan energi\nsebanyak " + plusEnergy; 
    }
}