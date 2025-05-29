package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_Fugu extends Entity {
    public int plusEnergy; 

    public OBJ_Fugu(GamePanel gp){
        super(gp);
        name = "Fugu";
        down1 = setup("objects/Fugu",gp.tileSize, gp.tileSize);
        isPickable = true;
        plusEnergy = 50; 
        salePrice = 135;
        buyPrice = -1; 
        description = "[" + name + "]\nDapat memulihkan energi\nsebanyak " + plusEnergy; 
    }
}