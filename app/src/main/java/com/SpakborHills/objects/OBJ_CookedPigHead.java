package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_CookedPigHead extends Entity {
    public int plusEnergy; 

    public OBJ_CookedPigHead(GamePanel gp){
        super(gp);
        name = "CookedPigHead ";
        down1 = setup("objects/CookedPigHead",gp.tileSize, gp.tileSize);
        isPickable = true;
        plusEnergy = 100; 
        buyPrice = 1000;
        salePrice = 0;
        description = "[" + name + "]\nDapat memulihkan energi\nsebanyak " + plusEnergy; 
    }
}