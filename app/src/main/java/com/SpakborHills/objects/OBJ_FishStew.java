package com.SpakborHills.objects;

import com.SpakborHills.entity.Entity;
import com.SpakborHills.main.GamePanel;

public class OBJ_FishStew extends Entity {
    public int plusEnergy; 

    public OBJ_FishStew(GamePanel gp){
        super(gp);
        name = "FishStew ";
        down1 = setup("objects/FishStew",gp.tileSize, gp.tileSize);
        isPickable = true;
        plusEnergy = 70; 
        buyPrice = 280;
        salePrice = 260;
        description = "[" + name + "]\nDapat memulihkan energi\nsebanyak " + plusEnergy; 
    }
}